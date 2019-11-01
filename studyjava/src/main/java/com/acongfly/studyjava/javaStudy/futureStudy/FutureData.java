package com.acongfly.studyjava.javaStudy.futureStudy;

/**
 * @author shicongyang
 * @Description:
 * @date 2018/6/4 16:07
 */
public class FutureData implements Data {

    private RealData realData;

    private boolean isReady = false;

    @Override
    public synchronized String getRequest() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notify();
    }
}
