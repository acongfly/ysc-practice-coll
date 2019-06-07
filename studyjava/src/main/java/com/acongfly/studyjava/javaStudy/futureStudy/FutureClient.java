package com.acongfly.studyjava.javaStudy.futureStudy;

/**
 * @author shicongyang
 * @Description: future客户端
 * @date 2018/6/4 16:37
 */
public class FutureClient {
    public Data request(final String request) {
        final FutureData futureData = new FutureData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData(request);
                futureData.setRealData(realData);
            }
        }).start();

        return futureData;
    }

    public static void main(String[] args) {
        FutureClient futureClient = new FutureClient();
        Data data = futureClient.request("test Future model");
        System.out.println("请求发送成功");
        System.out.println("干其他的事情。。。。");
        String request = data.getRequest();
        System.out.println("返回的请求为：" + request);

    }
}


