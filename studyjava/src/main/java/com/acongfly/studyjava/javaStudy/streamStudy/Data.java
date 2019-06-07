package com.acongfly.studyjava.javaStudy.streamStudy;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;

public class Data {
    private static List<PersonModel> list = null;

    static {
        PersonModel wu = new PersonModel("wu qi", 18, "男");
        PersonModel zhang = new PersonModel("zhang san", 19, "男");
        PersonModel wang = new PersonModel("wang si", 20, "女");
        PersonModel zhao = new PersonModel("zhao wu", 20, "男");
        PersonModel chen = new PersonModel("chen liu", 21, "男");
        list = Arrays.asList(wu, zhang, wang, zhao, chen);
    }

    public static List<PersonModel> getData() {
        return list;
    }


}

@lombok.Data
@Builder
class PersonModel {
    private String name;
    private int age;
    private String sex;

}