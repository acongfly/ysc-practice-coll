/*
 * Copyright 2011 sunli [sunli1223@gmail.com][weibo.com@sunli1223]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.acongfly.studyjava.fqueueChange.log;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acongfly.studyjava.fqueueChange.exception.FileEOFException;
import com.acongfly.studyjava.fqueueChange.exception.FileFormatException;
import com.acongfly.studyjava.fqueueChange.util.MappedByteBufferUtil;
import com.acongfly.studyjava.thread.threadStudy.NamedThreadFactory;

/**
 * @author sunli
 * @version $Id$
 * @date 2011-5-18
 */
public class LogEntity implements Closeable {
    private final Logger LOGGER = LoggerFactory.getLogger(LogEntity.class);
    public static final byte WRITESUCCESS = 1;
    public static final byte WRITEFAILURE = 2;
    public static final byte WRITEFULL = 3;
    public static final String MAGIC = "GGFQUEUE";
    static final Charset MAGIC_CHARSET = Charset.forName("UTF-8");
    static final byte[] MAGIC_BYTES = MAGIC.getBytes(MAGIC_CHARSET);
    static int messageStartPosition = 20;
    private final ExecutorService executor;
    private File file;
    private RandomAccessFile raFile;
    private FileChannel fc;
    private MappedByteBuffer mappedByteBuffer;
    private final int fileLimitLength;

    private LogIndex db = null;
    /**
     * 文件操作位置信息
     */
    private String magicString = null;
    private int version = -1;
    private int readerPosition = -1;
    private int cursorPosition = -1;
    private int writerPosition = -1;
    private int nextFile = -1;
    private int endPosition = -1;
    private int currentFileNumber = -1;

    private volatile AtomicBoolean close = new AtomicBoolean(false);

    public LogEntity(final String pathPattern, LogIndex db, int fileNumber, int fileLimitLength)
        throws IOException, FileFormatException {
        this.executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
            new NamedThreadFactory("LOGGER-entity", false), new ThreadPoolExecutor.CallerRunsPolicy());

        this.currentFileNumber = fileNumber;
        this.fileLimitLength = fileLimitLength;
        this.db = db;
        final String path = String.format(pathPattern, fileNumber);
        file = new File(path);
        // 文件不存在，创建文件
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException(String.format("can not create queue file:=>path:[%s]", path));
            }
            raFile = new RandomAccessFile(file, "rwd");
            fc = raFile.getChannel();
            mappedByteBuffer = fc.map(MapMode.READ_WRITE, 0, this.fileLimitLength);
            mappedByteBuffer.put(MAGIC_BYTES);
            mappedByteBuffer.putInt(version);// 8 version
            mappedByteBuffer.putInt(nextFile);// 12next fileindex
            mappedByteBuffer.putInt(endPosition);// 16
            mappedByteBuffer.force();
            this.magicString = MAGIC;
            this.writerPosition = LogEntity.messageStartPosition;
            this.readerPosition = LogEntity.messageStartPosition;
            this.cursorPosition = LogEntity.messageStartPosition;
            db.putWriterPosition(this.writerPosition);
        } else {
            raFile = new RandomAccessFile(file, "rwd");
            if (raFile.length() < LogEntity.messageStartPosition) {
                throw new FileFormatException("file format error");
            }
            fc = raFile.getChannel();
            mappedByteBuffer = fc.map(MapMode.READ_WRITE, 0, this.fileLimitLength);
            // magicString
            byte[] b = new byte[8];
            mappedByteBuffer.get(b);
            magicString = new String(b, MAGIC_CHARSET);
            if (!magicString.equals(MAGIC)) {
                throw new FileFormatException("file format error");
            }
            // version
            version = mappedByteBuffer.getInt();
            // nextfile
            nextFile = mappedByteBuffer.getInt();
            endPosition = mappedByteBuffer.getInt();
            // 未写满
            if (endPosition == -1) {
                this.writerPosition = db.getWriterPosition();
            } else if (endPosition == -2) {// 预分配的文件
                this.writerPosition = LogEntity.messageStartPosition;
                db.putWriterPosition(this.writerPosition);
                mappedByteBuffer.position(16);
                mappedByteBuffer.putInt(-1);
                this.endPosition = -1;

            } else {
                this.writerPosition = endPosition;
            }
            if (db.getReaderIndex() == this.currentFileNumber) {
                this.readerPosition = db.getReaderPosition();
            } else {
                this.readerPosition = LogEntity.messageStartPosition;
            }
            this.cursorPosition = readerPosition;
        }
        executor.execute(new Sync());

    }

    public class Sync implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (mappedByteBuffer != null) {
                    try {
                        mappedByteBuffer.force();
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage(), e);
                        break;
                    }
                } else {
                    // LOGGER.warn("queue buffer is null:=>queue:[{}]", this);
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }

    }

    public int getCurrentFileNumber() {
        return this.currentFileNumber;
    }

    public int getNextFile() {
        return this.nextFile;
    }

    /**
     * 记录写位置
     *
     * @param pos
     */
    private void putWriterPosition(int pos) {
        db.putWriterPosition(pos);
    }

    private void putReaderPosition(int pos) {
        db.putReaderPosition(pos);
    }

    /**
     * write next File number id.
     *
     * @param number
     */
    public void putNextFile(int number) {
        mappedByteBuffer.position(12);
        mappedByteBuffer.putInt(number);
        this.nextFile = number;
    }

    private boolean isFull(int increment) {
        // confirm if the file is full
        if (this.fileLimitLength < this.writerPosition + increment) {
            return true;
        }
        return false;
    }

    public byte write(Message message) {
        final byte[] log = message.getBytes();
        int increment = log.length + 4 + 8;// 长度+CRC32+消息字节
        if (isFull(increment)) {
            mappedByteBuffer.position(16);
            mappedByteBuffer.putInt(this.writerPosition);
            this.endPosition = this.writerPosition;
            return WRITEFULL;
        }
        mappedByteBuffer.position(this.writerPosition);
        mappedByteBuffer.putInt(log.length);
        mappedByteBuffer.putLong(message.getCrc());
        mappedByteBuffer.put(log);
        this.writerPosition += increment;
        putWriterPosition(this.writerPosition);
        return WRITESUCCESS;
    }

    public boolean commitCursor() {
        this.readerPosition = this.cursorPosition;
        putReaderPosition(this.readerPosition);
        return true;
    }

    public void refreshCursor() {
        this.cursorPosition = this.readerPosition;
    }

    public Message readNext() throws FileEOFException {
        if (this.endPosition != -1 && this.cursorPosition >= this.endPosition) {
            throw new FileEOFException("file eof");
        }
        // cursorPosition must be less than writerPosition
        if (this.cursorPosition >= this.writerPosition) {
            return null;
        }
        mappedByteBuffer.position(this.cursorPosition);
        int length = mappedByteBuffer.getInt();
        long crc = mappedByteBuffer.getLong();
        this.cursorPosition += length + 4 + 8;
        byte[] b = new byte[length];
        mappedByteBuffer.get(b);
        // putReaderPosition(this.cursorPosition);
        return new Message(crc, b);
        // return b;
    }

    @Override
    public void close() {
        try {
            if (close.compareAndSet(false, true)) {
                LOGGER.info("close file:=>id:[{}],file:[{}]", this.currentFileNumber, this.file.getAbsolutePath());
                if (mappedByteBuffer == null) {
                    return;
                }
                mappedByteBuffer.force();
                MappedByteBufferUtil.clean(mappedByteBuffer);
                executor.shutdown();
                mappedByteBuffer = null;
                fc.close();
                raFile.close();
            }
        } catch (IOException e) {
            LOGGER.error("close logentity file error:", e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogEntity{");
        sb.append("magicString='").append(magicString).append('\'');
        sb.append(", version=").append(version);
        sb.append(", readerPosition=").append(readerPosition);
        sb.append(", cursorPosition=").append(cursorPosition);
        sb.append(", writerPosition=").append(writerPosition);
        sb.append(", nextFile=").append(nextFile);
        sb.append(", endPosition=").append(endPosition);
        sb.append(", currentFileNumber=").append(currentFileNumber);
        sb.append('}');
        return sb.toString();
    }

    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }
}
