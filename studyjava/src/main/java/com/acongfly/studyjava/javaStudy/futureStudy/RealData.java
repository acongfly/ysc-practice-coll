package com.acongfly.studyjava.javaStudy.futureStudy;

/**
 * @author shicongyang
 * @Description:future模式返回数据的接口实现
 * @date 2018/6/4 14:32
 */
public class RealData implements Data {

    private String result;

    public RealData(String request) {
        System.out.println("根据" + request + "查询需要花费一些时间");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("操作完成获取结果");
        result = "查询结果";
    }

    @Override
    public String getRequest() {
        return result;
    }
}
