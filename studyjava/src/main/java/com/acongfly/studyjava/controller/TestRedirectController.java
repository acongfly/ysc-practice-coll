package com.acongfly.studyjava.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: ysc-practice-coll
 * @description: 测试跳转页面
 * @author: shicong yang
 * @create: 2019-10-31 19:58
 **/
@Controller
public class TestRedirectController {

    /**
     * http://zetcode.com/springboot/freemarker/
     * 
     * @param name
     * @return
     */

    @GetMapping("/testRedirect")
    public ModelAndView testRedirect(@RequestParam("authStatus") String name) {

        System.out.println(name);

        Map<String, Object> params = new HashMap<>();

        return new ModelAndView("successPage");

    }
}
