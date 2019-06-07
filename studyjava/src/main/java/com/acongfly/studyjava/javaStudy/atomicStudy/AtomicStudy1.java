package com.acongfly.studyjava.javaStudy.atomicStudy;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author shicongyang
 * @Description:以原子性更新类中某一个属性 AtomicIntegerFieldUpdater
 * @date 2018/6/5 14:36
 */
public class AtomicStudy1 {

    private static AtomicIntegerFieldUpdater<AtomicStudy1> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicStudy1.class, "count");


    public volatile int count = 500;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static void main(String[] args) {

        AtomicStudy1 atomicStudy1 = new AtomicStudy1();

        //CAS实现compareAndSet
        if (updater.compareAndSet(atomicStudy1, 500, 600)) {
            System.out.println("1.更新成功，count=" + atomicStudy1.getCount());
        }

        if (updater.compareAndSet(atomicStudy1, 500, 600)) {
            System.out.println("2.更新成功，count=" + atomicStudy1.getCount());
        } else {
            System.out.println("3.更新失败，count=" + atomicStudy1.getCount());
        }


    }


}
