package com.acongfly.studyjava.fqueueChange.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class IOUtils {
    private IOUtils() {/**/}

    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ioe) {
            // ignore
            LOGGER.warn("close failed:=>msg:" + ioe.getMessage(), ioe);
        }
    }

    public static void closeQuietly(AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ioe) {
            // ignore
            LOGGER.warn("close failed:=>msg:" + ioe.getMessage(), ioe);
        }
    }

    public static int readInt(InputStream in) throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        int ch3 = in.read();
        int ch4 = in.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4);
    }

    public static short readShort(InputStream in) throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short) ((ch1 << 8) + (ch2));
    }

    public static void writeShort(OutputStream out, short v) throws IOException {
        out.write((v >>> 8) & 0xFF);
        out.write((v) & 0xFF);
    }

    public static void writeBytes(OutputStream stream, byte[] bytes) throws IOException {
        stream.write(bytes);
    }

    public static void writeInt(OutputStream out, int v) throws IOException {
        out.write((v >>> 24) & 0xFF);
        out.write((v >>> 16) & 0xFF);
        out.write((v >>> 8) & 0xFF);
        out.write((v) & 0xFF);
    }
}
