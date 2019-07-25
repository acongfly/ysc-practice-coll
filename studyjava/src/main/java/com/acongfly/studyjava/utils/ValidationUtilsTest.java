package com.acongfly.studyjava.utils;

import javax.validation.groups.Default;
import java.util.Date;

/**
 * @Description: 校验数据工具列测试（演示demo）
 * @Param:
 * @return:
 * @Author: shicong yang
 * @Date: 2018/6/22
 */
public class ValidationUtilsTest {

    public static void validateSimpleEntity() {
        SimpleEntity se = new SimpleEntity();
        se.setDate(new Date());
        se.setEmail("123");
        se.setName("123");
        se.setPassword("123");
        se.setValid(false);
        se.setType(6);

        ValidationResult result = ValidationUtils.validateEntity(se);
        System.out.println("--------------------------");
        System.out.println(result);
    }

    public static void validateSimpleProperty() {
        SimpleEntity se = new SimpleEntity();
        ValidationResult result = ValidationUtils.validateProperty(se, "name");
        System.out.println("--------------------------");
        System.out.println(result);
        System.out.println(result.isHasErrors());
    }

    public static void main(String[] args) {
//		ValidationUtilsTest.validateSimpleEntity();
//		ValidationUtilsTest.validateSimpleProperty();
        SimpleEntity se = new SimpleEntity();
        se.setDate(new Date());
        se.setEmail("123@123");
        se.setName("123");
        se.setPassword("12345dd");
        se.setValid(false);
        se.setAmt(1L);
        se.setType(0);
        ValidationResult result = ValidationUtils.validateByGroup(se, ValidatorInterfaceColl.account.class);
//        ValidationResult result = ValidationUtils.validateEntity(se);
        if (result.isHasErrors()) {
            //此处设置响应码和响应信息
            //建议抛出
            System.out.println(result);
        }

    }

}
