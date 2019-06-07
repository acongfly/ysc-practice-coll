package com.acongfly.studyjava.designPatterns.adapter;

/**
 * @author shicongyang
 * @ClassName: VoltageAdapter
 * @Description: 变压器
 * @date 2018年4月16日 下午10:33:56
 */
public class VoltageAdapter {

    public void changeVoltage() {
        System.out.println("正在充电");
        System.out.println("原始电压" + Phone.V + "V");
        System.out.println("经过变压器转换之后的电压" + (Phone.V - 200) + "V");
    }

}
