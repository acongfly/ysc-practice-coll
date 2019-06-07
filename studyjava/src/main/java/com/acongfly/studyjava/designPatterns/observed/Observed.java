package com.acongfly.studyjava.designPatterns.observed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shicongyang
 * @Description: 被观察者
 * @date 2018年4月16日 下午5:50:45
 */
public class Observed {

    public Observed() {
    }

    List<Person> list = new ArrayList<Person>();

    public void addPerson(Person person) {
        list.add(person);
    }

    public void noticePeople() {
        for (Person person : list) {
            person.getMessage("come baby");
        }
    }

    public static void main(String[] args) {
        Observer01 observer01 = new Observer01();
        Observer02 observer02 = new Observer02();
        Observed observed = new Observed();
        observed.addPerson(observer01);
        observed.addPerson(observer02);
        observed.noticePeople();
    }

}
