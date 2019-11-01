package com.acongfly.studyjava.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MvelUtilTest {

    private static Map<String, Object> context;

    /**
     * 初始化方法.
     */
    @BeforeClass
    public static void init() {
        context = new HashMap<String, Object>();
        context.put("merchantId", "M36977092609");
        context.put("payType", "91");
        context.put("payAmount", 3300);
    }

    /**
     * // 判断相等 Object objResult = MVEL.eval("name == 'Mr.Foo'", personInst); System.out.println("objResult=" +
     * objResult);
     * <p>
     * // 取值 String strResult = (String) MVEL.eval("name", personInst); System.out.println("strResult=" + strResult);
     * <p>
     * // 计算 Map vars = new HashMap(); vars.put("x", new Integer(5)); vars.put("y", new Integer(10));
     * <p>
     * // 第一种方式 Integer intResult = (Integer) MVEL.eval("x * y", vars); System.out.println("intResult=" + intResult); //
     * 第二种方式 ExecutableAccessor compiled = (ExecutableAccessor) MVEL.compileExpression("x * y"); intResult = (Integer)
     * MVEL.executeExpression(compiled, vars); System.out.println("intResult=" + intResult);
     */

    @Test
    public void parseExpress() {
        String s = JSONUtil.toJsonPrettyStr(context);

        // StringBuilder rulesb = new StringBuilder();
        // for (int i = 0; i < 11; i++) {
        // rulesb.append("(").append("merId=='M1234'").append(")||");
        // }
        // System.out.println(rulesb.toString());
        // System.out.println(rulesb.delete(rulesb.length()-2,rulesb.length()).toString());

        Object m123456 = MvelUtil.parseExpress(
            "((merchantId=='M36977092609')&&(payType=='91')&&(payAmount<1000))||(payType=='91')", context);
        System.out.println(m123456);
    }

    /**
     * 测试解析表达式会抛异常的方法.
     */
    @Test(expected = Exception.class)
    public void testParseExpressWithException() {
        assertEquals(null, MvelUtil.parseExpressWithException("abc", context));
    }

    /**
     * 测试解析表达式会抛异常的方法.
     */
    @Test(expected = Exception.class)
    public void testParseTemplate() {
        assertEquals("say world", MvelUtil.parseTemplate("say @{hello}", context));
        assertEquals(null, MvelUtil.parseTemplate("say @{abc}", context));
    }

    /**
     * 测试解析表达式会抛异常的方法.
     */
    @Test
    public void testIsMatch() {
        assertEquals(true, MvelUtil.isMatch(null, context));
        assertEquals(true, MvelUtil.isMatch("", context));
        assertEquals(false, MvelUtil.isMatch("merId=='M12345'", context));
        // assertEquals(false, MvelUtil.isMatch("?hello != 'world'", context));
        System.out.println();
    }

    /**
     * 测试计算表达式的值.
     */
    @Test
    public void testParseWithMvel() {
        // 构造查询的参数
        Map<String, Object> context = new HashMap<String, Object>(4);
        context.put("foo", "Hello");
        context.put("bar", "World");
        String result = (String)MvelUtil.parseExpressWithException("foo + bar", context);
        log.info("testParseWithMvel 结果:" + result);
        assertEquals("HelloWorld", result);
    }

    /**
     * 测试解析普通字符串的方法.
     */
    @Test
    public void testParseStr() {
        // String result = MvelUtil.parseTemplate("zhangsan", ParamWrapper.newInstance("aa", "张三").toMap());
        // log.info("testParseStr方法的结果:" + result);
        // assertEquals("zhangsan", result);
    }

    /**
     * 测试解析普通字符串的方法.
     */
    @Test
    public void testParseStr2() {
        // Boolean result = (Boolean) MvelUtil.parseExpress("sex == 1", ParamWrapper.newInstance("sex", "1").toMap());
        // assertEquals(true, result);
    }

    /**
     * 测试解析普通字符串的方法.
     */
    @Test
    public void testParseSpaceStr() {
        // Boolean result = (Boolean) MvelUtil.parseExpress("", ParamWrapper.newInstance("bb", "1").toMap());
        // assertEquals(null, result);
    }

    /**
     * 测试计算模版的值.
     */
    @Test
    public void testParseTemplate2() {
        // 构造查询的参数
        Map<String, Object> context = new HashMap<String, Object>(2);
        context.put("foo", "Hello");
        String result = MvelUtil.parseTemplate("@if{?foo != empty}@{foo} World!@end{}", context);
        log.info("testParseTemplate 结果:{}", result);
        assertEquals("Hello World!", result);
    }

}