package com.acongfly.studyjava.designPatterns.adapter;

/**
 * @author shicongyang
 * @ClassName: TestAdapter
 * @Description: 测试适配器模式
 * 将两个完全没有关系的事情练习在一起，就像是变压器一样,假使一个手机需要的是20V,
 * 但是电压是220V,就需要变压器去更改，将220V的电压变为20V
 * @date 2018年4月16日 下午10:39:47
 */
public class TestAdapter {

    public static void main(String args[]) {
        Phone phone = new Phone();
        VoltageAdapter voltageAdapter = new VoltageAdapter();
        phone.setAdapter(voltageAdapter);
        phone.charge();
    }

}
