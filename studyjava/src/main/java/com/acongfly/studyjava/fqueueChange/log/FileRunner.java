/*
 *  Copyright 2011 sunli [sunli1223@gmail.com][weibo.com@sunli1223]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.acongfly.studyjava.fqueueChange.log;

import com.acongfly.studyjava.fqueueChange.util.MappedByteBufferUtil;
import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Queue;

/**
 * @author sunli
 * @version $Id$
 * @date 2011-5-18
 */
public class FileRunner implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRunner.class);
    private final Queue<String> createQueue = Queues.newConcurrentLinkedQueue();
    private final Queue<String> deleteQueue = Queues.newConcurrentLinkedQueue();
    private final int fileLimitLength;
    private volatile boolean keepRunning = true;

    public void addDeleteFile(String path) {
        LOGGER.info("add delete file:=>path:[{}]", path);
        deleteQueue.add(path);
    }

    public void addCreateFile(String path) {
        LOGGER.info("add create file:=>path:[{}]", path);
        createQueue.add(path);
    }

    public FileRunner(int fileLimitLength) {
        this.fileLimitLength = fileLimitLength;
    }

    @Override
    public void run() {
        while (keepRunning) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            do {
                String deletePath = this.deleteQueue.poll();
                String createPath = this.createQueue.poll();
                if (deletePath == null && createPath == null) {
                    break;
                }
                if (deletePath != null) {
                    File delFile = new File(deletePath);
                    if (!delFile.delete()) {
                        LOGGER.error("删除队列文件失败:=>file:[{}]", delFile.getAbsolutePath());
                    }
                }
                if (createPath != null) {
                    try {
                        create(createPath);
                    } catch (IOException e) {
                        LOGGER.error("预创建数据文件失败", e);
                    }
                }
            } while (true);
        }

    }

    private boolean create(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                return false;
            }
            try (RandomAccessFile raFile = new RandomAccessFile(file, "rwd")) {
                FileChannel fc = raFile.getChannel();
                MappedByteBuffer mappedByteBuffer = fc.map(MapMode.READ_WRITE, 0, this.fileLimitLength);
                mappedByteBuffer.put(LogEntity.MAGIC_BYTES);
                mappedByteBuffer.putInt(1);// 8 version
                mappedByteBuffer.putInt(-1);// 12next fileindex
                mappedByteBuffer.putInt(-2);// 16 endPosition=-2代表预分配文件
                mappedByteBuffer.force();
                MappedByteBufferUtil.clean(mappedByteBuffer);
                fc.close();
            }
            return true;
        } else {
            return false;
        }
    }

    public void exit() {
        keepRunning = false;
    }
}
