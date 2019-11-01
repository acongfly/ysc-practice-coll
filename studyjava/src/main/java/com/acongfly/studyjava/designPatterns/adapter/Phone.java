package com.acongfly.studyjava.designPatterns.adapter;

/**
 * @author shicongyang
 * @ClassName: phone
 * @Description: 适配器（手机）
 * @date 2018年4月16日 下午10:12:00
 */
public class Phone {

    public static final int V = 220; // 例如正常的手机的220V电压

    private VoltageAdapter adapter;

    public void setAdapter(VoltageAdapter adapter) {
        this.adapter = adapter;
    }

    public void charge() {
        adapter.changeVoltage();
    }

}
