package com.acongfly.studyjava.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;
import org.mvel2.templates.TemplateRuntime;

/**
 * description: MVEL表达式相关的工具类.<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-05-10 <p>
 */
@Slf4j
public final class MvelUtil {


    /**
     * 私有构造方法.
     */
    private MvelUtil() {
        super();
    }

    /**
     * 通过MVEL来解析表达式的值，如果出错不抛出异常.
     *
     * @param exp      待解析表达式
     * @param paramObj 参数对象
     * @return 返回解析后的值
     */
    public static Object parseExpress(String exp, Object paramObj) {
        try {
            return MVEL.eval(exp, paramObj);
        } catch (Exception e) {
            throw new ParseExpressionException("解析Mvel表达式异常，解析出错的表达式为:" + exp, e);
        }
    }

    /**
     * 通过MVEL来解析表达式的值，如果出错则抛出异常.
     *
     * @param exp      待解析表达式
     * @param paramObj 参数对象
     * @return 返回解析后的值
     */
    public static Object parseExpressWithException(String exp, Object paramObj) {
        Object obj;
        try {
            obj = MVEL.eval(exp, paramObj);
        } catch (Exception e) {
            throw new ParseExpressionException("解析Mvel表达式异常，解析出错的表达式为:" + exp, e);
        }
        return obj;
    }

    /**
     * 通过MVEL来解析模板的值.
     *
     * @param template 待解析表达式
     * @param paramObj 参数对象
     * @return 返回解析后的结果
     */
    public static String parseTemplate(String template, Object paramObj) {
        String output;
        try {
            output = (String) TemplateRuntime.eval(template, paramObj);
        } catch (Exception e) {
            throw new ParseExpressionException("解析Mvel模板异常，解析出错的模板为:" + template, e);
        }
        return output;
    }

    /**
     * 中的表达式是否匹配通过.
     *
     * @param match    match表达式
     * @param paramObj 参数上下文对象
     * @return 布尔值
     */
    public static boolean isMatch(String match, Object paramObj) {
        return StringUtils.isBlank(match) || isTrue(match, paramObj);
    }

    /**
     * 表达式是否`不`匹配通过.
     *
     * @param match    match表达式
     * @param paramObj 参数上下文对象
     * @return 布尔值
     */
    public static boolean isNotMatch(String match, Object paramObj) {
        return !isMatch(match, paramObj);
    }

    /**
     * 表达式是否匹配通过.
     *
     * @param exp      表达式
     * @param paramObj 参数上下文对象
     * @return 布尔值
     */
    public static boolean isTrue(String exp, Object paramObj) {
        return Boolean.TRUE.equals(MvelUtil.parseExpressWithException(exp, paramObj));
    }

}