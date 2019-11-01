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
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acongfly.studyjava.fqueueChange.exception.FileFormatException;
import com.acongfly.studyjava.fqueueChange.log.Message;

/**
 * 基于文件系统的持久化队列
 *
 * @author sunli
 * @version $Id$
 * @date 2010-8-13
 */
public class FQueue implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FQueue.class);
    private Lock lock = new ReentrantReadWriteLock().writeLock();
    private final FSQueue fsQueue;

    public FQueue(int id, String path) throws IOException, FileFormatException {
        this.fsQueue = new FSQueue(id, path, 1024 * 1024 * 300);
        this.fsQueue.init();
    }

    public FQueue(int id, String path, int logsize) throws IOException, FileFormatException {
        this.fsQueue = new FSQueue(id, path, logsize);
        this.fsQueue.init();
    }

    public int size() {
        return fsQueue.getQueueSize();
    }

    /**
     * 添加到队列中数据
     *
     * @param bytes
     * @return
     */
    public boolean add(byte[] bytes) {
        if (bytes.length == 0) {
            return true;
        }
        Message message = new Message(bytes);
        return add(message);
    }

    /**
     * 添加到队列中数据
     *
     * @param message
     * @return
     */
    public boolean add(Message message) {
        try {
            lock.lock();
            fsQueue.add(message);
            return true;
        } catch (IOException | FileFormatException e1) {
            LOGGER.error(e1.getMessage(), e1);
        } finally {
            lock.unlock();
        }
        return false;
    }

    public boolean addAll(Message[] messages) {
        try {
            lock.lock();
            fsQueue.addAll(messages);
            return true;
        } catch (IOException | FileFormatException e1) {
            LOGGER.error(e1.getMessage(), e1);
        } finally {
            lock.unlock();
        }
        return false;
    }

    public boolean addAll(byte[][] tbs) {
        if (tbs.length == 0) {
            return true;
        }
        Message[] messages = new Message[tbs.length];
        for (int i = 0; i < tbs.length; i++) {
            byte[] bytes = tbs[i];
            messages[i] = new Message(bytes);
        }
        return addAll(messages);
    }

    public boolean commitCursor() {
        try {
            lock.lock();
            return fsQueue.commitCursor();
        } catch (IOException | FileFormatException e1) {
            LOGGER.error(e1.getMessage(), e1);
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void refreshCursor() {
        try {
            lock.lock();
            fsQueue.refreshCursor();
        } finally {
            lock.unlock();
        }
    }

    public Message next() {
        try {
            lock.lock();
            Message message = fsQueue.readNext();
            if (message == null || message.getBytes().length == 0) {
                return null;
            }
            return message;
        } catch (IOException | FileFormatException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void close() {
        fsQueue.close();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FQueue{");
        sb.append("fsQueue=").append(fsQueue);
        sb.append('}');
        return sb.toString();
    }

    public int getReaderIndex() {
        return this.fsQueue.getReaderIndex();
    }

    public int getWriterIndex() {
        return this.fsQueue.getWriterIndex();
    }

    public int getReaderPosition() {
        return this.fsQueue.getReaderPosition();
    }

    public int getWriterPosition() {
        return this.fsQueue.getWriterPosition();
    }

    public int getQueueSize() {
        return this.fsQueue.getQueueSize();
    }

    public int getCursorSize() {
        return this.fsQueue.getCursorQueueSize();
    }
}
