package com.acongfly.study.utils;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * description: 数据校验工具类
 *
 * @param:
 * @return:
 * @author: shicong yang
 * @date: 2018/6/22
 */
public class ValidationUtils {


    //下面是常用的一些注解

//	Bean Validation 中内置的 constraint
//	@Null   被注释的元素必须为 null
//			*@NotNull    被注释的元素必须不为 null
//			* @AssertTrue     被注释的元素必须为 true
//			*@AssertFalse    被注释的元素必须为 false
//	@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//	@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//	@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//	@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//	@Size(max=, min=)   被注释的元素的大小必须在指定的范围内
//	@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
//	@Past   被注释的元素必须是一个过去的日期
//	@Future     被注释的元素必须是一个将来的日期
//	@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
//
//	Hibernate Validator 附加的 constraint
//	@NotBlank(message =)   验证字符串非null，且长度必须大于0
//	@Email  被注释的元素必须是电子邮箱地址
//	@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内
//	@NotEmpty   被注释的字符串的必须非空
// 	@Range(min=,max=,message=)  被注释的元素必须在合适的范围内

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验整个DTO,VO,BO等，需要添加hibernate valiate校验注解,当校验不满足时候，result的hasError值为true
     *
     * @param: [obj]
     * @return: ValidationResult
     * @author: shicong yang
     * @date: 2018/6/22
     */
    public static <T> ValidationResult validateEntity(T obj) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        buildErrorInfo(result, set);
        return result;
    }


    /**
     * description: 校验单个属性值,当校验不满足时候，result的hasError值为true<p>
     * param: [obj, propertyName]<p>
     * return: ValidationResult<p>
     * author: shicong yang<p>
     * date: 2018/6/22<p>
     */
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (CollectionUtils.isNotEmpty(set)) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }


    /**
     * description: 校验属性值，按照组校验
     * 注意：传入的class必须是接口，如果指定了interface那么就只会校验指定group中的属性，其他不会校验。
     * *      例如：group={example.class},那么就会只校验指定了group={example.class}的相关注解属性<p>
     * param: [obj, classes] <p>
     * return: com.ushareit.fintech.payment.common.utils.ValidationResult <p>
     * author: shicong yang <p>
     * date: 2019-04-12 <p>
     */
    public static <T> ValidationResult validateByGroup(T obj, Class<?>... classes) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, classes);
        buildErrorInfo(result, set);
        return result;
    }


    private static <T> void buildErrorInfo(ValidationResult result, Set<ConstraintViolation<T>> set) {
        if (CollectionUtils.isNotEmpty(set)) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
    }
}
