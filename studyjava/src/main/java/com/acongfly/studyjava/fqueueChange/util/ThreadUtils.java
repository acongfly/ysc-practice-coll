package com.acongfly.studyjava.fqueueChange.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public final class ThreadUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);

    private ThreadUtils() {/**/}

    public static final void shutdownExecutorService(ExecutorService service) {
        service.shutdown();
        try {
            if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                service.shutdownNow();
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    LOGGER.error("service did not terminate");
                }
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    LOGGER.error("service did not terminate");
                }
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    LOGGER.error("service did not terminate");
                }
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void interrupt() {
        Thread.currentThread().interrupt();
    }
}
