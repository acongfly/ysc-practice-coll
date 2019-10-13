package com.acongfly.qlexpressdemo.demo;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 * @program: ysc-practice-coll
 * @description: hello world
 * @author: shicong yang
 * @create: 2019-09-26 21:06
 **/
public class HelloWorld {


    /**
     * 支持 +,-,*,/,<,>,<=,>=,==,!=,<>【等同于!=】,%,mod【取模等同于%】,++,--,&&，||
     * in【类似sql】,like【类似sql】,&&,||,!,等操作符
     * and、or 和java里面的&& || 等价
     * 支持for，break、continue、if then else 等标准的程序控制逻辑
     * n=10;
     * sum=0；
     * for(i=0;i<n;i++){
     * sum=sum+i;
     * }
     * return sum;
     * <p>
     * <p>
     * <p>
     * //逻辑三元操作
     * a=1;
     * b=2;
     * max = a>b?a:b;
     * <p>
     * <p>
     * <p>
     * 提醒：不支持的java语法
     * 不支持try{}catch{}
     * 不支持java8的lambda表达式
     * 不支持for循环集合操作for (GRCRouteLineResultDTO item : list)
     * 弱类型语言，请不要定义类型声明
     * <p>
     * int a = 10;
     * //-》转化后的逻辑
     * a=10;
     * <p>
     * <p>
     * FocFulfillDecisionReqDTO reqDTO = param.getReqDTO();
     * //-》转化后的逻辑
     * reqDTO = param.getReqDTO();
     * <p>
     * //数组遍历
     * for(GRCRouteLineResultDTO item : list) {
     * }
     * //-》转化后的逻辑
     * for(i=0;i<list.size();i++){
     * item = list.get(i);
     * }
     * <p>
     * //map遍历
     * for(String key : map.keySet()) {
     * System.out.println(map.get(key));
     * }
     * //-》转化后的逻辑
     * keySet = map.keySet();
     * objArr = keySet.toArray();
     * for (i=0;i<objArr.length;i++) {
     * key = objArr[i];
     * System.out.println(map.get(key));
     * }
     */


    public static void main(String[] args) throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);

        //service
        runner.addFunctionOfClassMethod("upper", HelloWorld.class.getName(), "upper", new String[]{"String"}, "error");
        String exp = "upper(test)";
        context.put("test", "aaaaaa");
        Object execute = runner.execute(exp, context, null, false, false);
        System.out.println(execute);

        runner.addFunctionOfClassMethod("staticUpper", HelloWorld.class.getName(), "staticUpper", new String[]{"String"}, null);
        //static service
        String staticExp = "staticUpper(staticUp)";
//        ExpressRunner runner = new ExpressRunner();
        context.put("staticUp", "bbbbb");
        //执行表达式，并将结果赋给
        String staticUpper = (String) runner.execute(staticExp, context, null, false, false);
        System.out.println(staticUpper);


    }

    public String upper(String a) {
        return a.toUpperCase();
    }

    public static String staticUpper(String a) {
        return a.toUpperCase();
    }

}
