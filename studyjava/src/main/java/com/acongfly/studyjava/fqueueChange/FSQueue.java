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
package com.acongfly.studyjava.fqueueChange;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acongfly.studyjava.fqueueChange.exception.FileEOFException;
import com.acongfly.studyjava.fqueueChange.exception.FileFormatException;
import com.acongfly.studyjava.fqueueChange.log.FileRunner;
import com.acongfly.studyjava.fqueueChange.log.LogEntity;
import com.acongfly.studyjava.fqueueChange.log.LogIndex;
import com.acongfly.studyjava.fqueueChange.log.Message;
import com.acongfly.studyjava.fqueueChange.util.ThreadUtils;
import com.acongfly.studyjava.thread.threadStudy.NamedThreadFactory;

/**
 * 完成基于文件的先进先出的读写功能
 *
 * @author sunli
 * @version $Id$
 * @date 2010-8-13
 */
public class FSQueue implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FSQueue.class);
    private static final String filePrefix = "fqueue";
    private static final String dbName = "icqueue.db";
    private static final String fileSeparator = System.getProperty("file.separator");
    private final ExecutorService executor =
        Executors.newSingleThreadExecutor(new NamedThreadFactory("fs-queue", false));
    private final int id;

    private final String dataRootDir;
    private int fileLimitLength = 1024 * 1024 * 300;

    private String path = null;

    private FileRunner fileRunner;
    /**
     * 文件操作实例
     */
    private LogIndex db = null;
    /**
     * 文件存储的基本路径pattern,/tmp/data/history-app/fdata/1/fqueuedata_%s.idb
     */
    private String pathPattern;
    /**
     * 游标
     */
    private LogEntity cursorHandle = null;
    private LogEntity writerHandle = null;
    private LogEntity readerHandle = null;
    /**
     * 文件操作位置信息
     */
    private int readerIndex = -1;
    private int writerIndex = -1;

    /**
     * 经过cursor获取的消息的数量
     */
    private AtomicInteger cursorNum = new AtomicInteger(0);

    FSQueue(int id, String path) throws Exception {
        this(id, path, 1024 * 1024 * 300);
    }

    /**
     * 在指定的目录中，以fileLimitLength为单个数据文件的最大大小限制初始化队列存储
     *
     * @param dir
     *            队列数据存储的路径,目录下根据ID分子目录存储
     * @param fileLimitLength
     *            单个数据文件的大小，不能超过2G
     */
    FSQueue(int id, String dir, int fileLimitLength) {
        this.id = id;
        this.dataRootDir = String.format("%s%s%s", dir, fileSeparator, id);
        this.fileLimitLength = fileLimitLength;
    }

    public FSQueue init() throws IOException, FileFormatException {
        File fileDir = new File(dataRootDir);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            if (!fileDir.mkdirs()) {
                throw new IOException("create dir error");
            }
        }
        this.path = fileDir.getAbsolutePath();
        this.pathPattern = path + fileSeparator + filePrefix + "data_%s.idb";

        this.fileRunner = new FileRunner(fileLimitLength);
        this.executor.execute(fileRunner);

        // 打开db
        db = new LogIndex(path + fileSeparator + dbName);
        writerIndex = db.getWriterIndex();
        readerIndex = db.getReaderIndex();

        writerHandle = createLogEntity(db, writerIndex);
        if (readerIndex == writerIndex) {
            readerHandle = writerHandle;
        } else {
            readerHandle = createLogEntity(db, readerIndex);
        }
        cursorHandle = readerHandle;
        return this;
    }

    /**
     * 创建或者获取一个数据读写实例
     *
     * @param db
     * @param fileNumber
     * @return
     * @throws IOException
     * @throws FileFormatException
     */
    LogEntity createLogEntity(LogIndex db, int fileNumber) throws IOException, FileFormatException {
        LogEntity entity = new LogEntity(pathPattern, db, fileNumber, this.fileLimitLength);
        {
            String nextFilePath = String.format(pathPattern, fileNumber + 1);
            File file = new File(nextFilePath);
            if (!file.exists()) {
                fileRunner.addCreateFile(file.getAbsolutePath());
            }
        }
        return entity;
    }

    /**
     * 一个文件的数据写入达到fileLimitLength的时候，滚动到下一个文件实例
     *
     * @throws IOException
     * @throws FileFormatException
     */
    private void rotateNextLogWriter() throws IOException, FileFormatException {
        writerIndex = writerIndex + 1;
        writerHandle.putNextFile(writerIndex);

        int readerIndex = readerHandle.getCurrentFileNumber();
        int writerIndex = writerHandle.getCurrentFileNumber();
        int cursorIndex = cursorHandle.getCurrentFileNumber();
        if (writerIndex != readerIndex && writerIndex != cursorIndex) {
            writerHandle.close();
        }
        db.putWriterIndex(this.writerIndex);
        writerHandle = createLogEntity(db, this.writerIndex);
    }

    /**
     * 向队列存储添加一个byte数组
     *
     * @param message
     * @throws IOException
     * @throws FileFormatException
     */
    void add(Message message) throws IOException, FileFormatException {
        short status = writerHandle.write(message);
        if (status == LogEntity.WRITEFULL) {
            rotateNextLogWriter();
            status = writerHandle.write(message);
        }
        if (status == LogEntity.WRITESUCCESS) {
            db.incrementSize();
        }
    }

    void addAll(Message[] messages) throws IOException, FileFormatException {
        for (int i = 0; i < messages.length; i++) {
            add(messages[i]);
        }
    }

    /**
     * 提交当前文件的消费记录
     *
     * @return
     */
    boolean commitCursor() throws IOException, FileFormatException {
        int readerIndex = readerHandle.getCurrentFileNumber();
        int writerIndex = writerHandle.getCurrentFileNumber();
        int cursorIndex = cursorHandle.getCurrentFileNumber();
        // 更换文件索引
        if (readerIndex != cursorIndex && readerIndex != writerIndex) {
            this.readerHandle.close();
        }
        if (readerIndex != cursorIndex) {
            this.readerHandle = this.cursorHandle;
            db.putReaderIndex(cursorIndex);
        }
        this.readerHandle.commitCursor();
        // 维护未消费消息数
        db.decrementSize(cursorNum.get());
        cursorNum.set(0);
        // 消费状态刷盘
        db.force();
        // 没置文件清理任务
        for (int deleteNum = readerIndex; deleteNum < cursorIndex; deleteNum++) {// 不可能读位置在写的位置
            fileRunner.addDeleteFile(path + fileSeparator + filePrefix + "data_" + deleteNum + ".idb");
        }
        return true;
    }

    void refreshCursor() {
        this.cursorHandle.refreshCursor();// 避免当前读的文件位置没有更新
        int readerIndex = readerHandle.getCurrentFileNumber();
        int writerIndex = writerHandle.getCurrentFileNumber();
        int cursorIndex = cursorHandle.getCurrentFileNumber();
        if (cursorIndex != readerIndex && cursorIndex != writerIndex) {
            this.cursorHandle.close();
        }
        if (cursorIndex != readerIndex) {
            this.cursorHandle = this.readerHandle;
        }
        this.cursorHandle.refreshCursor();
        this.cursorNum.set(0);
    }

    /**
     * 从队列存储中取出最先入队的数据
     *
     * @return
     * @throws IOException
     * @throws FileFormatException
     */
    Message readNext() throws IOException, FileFormatException {
        // MessageAndMetadata m = null;
        Message message = null;
        try {
            message = cursorHandle.readNext();
        } catch (FileEOFException e) {
            // int deleteNum = readerHandle.getCurrentFileNumber();
            int readerIndex = readerHandle.getCurrentFileNumber();
            int writerIndex = writerHandle.getCurrentFileNumber();
            int cursorIndex = cursorHandle.getCurrentFileNumber();
            if (cursorIndex != readerIndex && cursorIndex != writerIndex) {
                cursorHandle.close();
            }
            int nextfile = cursorHandle.getNextFile();
            if (writerIndex == nextfile) {
                cursorHandle = writerHandle;
            } else {
                cursorHandle = createLogEntity(db, nextfile);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("change cursor file:=>id:[{}],r:[{}],c:[{}],w:[{}]", id,
                    readerHandle.getCurrentFileNumber(), cursorHandle.getCurrentFileNumber(),
                    writerHandle.getCurrentFileNumber());
            }
            try {
                message = cursorHandle.readNext();
            } catch (FileEOFException e1) {
                LOGGER.error("read new LOGGER file FileEOFException error occurred", e1);
            }
        }
        if (message != null) {
            // db.decrementSize();
            cursorNum.incrementAndGet();
        }
        return message;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(readerHandle);
        IOUtils.closeQuietly(writerHandle);
        IOUtils.closeQuietly(cursorHandle);
        fileRunner.exit();
        ThreadUtils.shutdownExecutorService(executor);
    }

    int getQueueSize() {
        if (db == null) {
            return -1;
        }
        return db.getSize();
    }

    int getCursorQueueSize() {
        if (db == null) {
            return -1;
        }
        return db.getSize() - cursorNum.get();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FSQueue{");
        sb.append("id=").append(id);
        sb.append(", dataRootDir='").append(dataRootDir).append('\'');
        sb.append(", fileLimitLength=").append(fileLimitLength);
        sb.append(", path='").append(path).append('\'');
        sb.append(", db=").append(db);
        sb.append(", cursorNum=").append(cursorNum);
        sb.append(", queueSize=").append(getQueueSize());
        sb.append(", cursorQueueSize=").append(getCursorQueueSize());
        sb.append('}');
        return sb.toString();
    }

    int getReaderIndex() {
        return this.db.getReaderIndex();
    }

    int getWriterIndex() {
        return this.db.getWriterIndex();
    }

    int getReaderPosition() {
        return this.db.getReaderPosition();
    }

    int getWriterPosition() {
        return this.db.getWriterPosition();
    }

    String getDataRootDir() {
        return dataRootDir;
    }
}
