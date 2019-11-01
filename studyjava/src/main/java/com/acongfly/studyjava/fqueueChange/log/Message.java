package com.acongfly.studyjava.fqueueChange.log;

import com.acongfly.studyjava.fqueueChange.util.Crc32;

/**
 * TODO:
 */
public class Message {
    private long crc;
    private byte[] bytes;

    public Message(byte[] bytes) {
        this.bytes = bytes;
        Crc32 crc32 = new Crc32();
        crc32.update(bytes, 0, bytes.length);
        this.crc = crc32.getValue();
    }

    public Message(long crc, byte[] bytes) {
        this.crc = crc;
        this.bytes = bytes;
    }

    public long getCrc() {
        return crc;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public boolean validate() {
        Crc32 crc32 = new Crc32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue() == this.crc;
    }
}
