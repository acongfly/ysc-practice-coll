package com.acongfly.studyjava.controller;

import com.acongfly.studyjava.javaStudy.other.FileTrans;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hc on 2018/8/8.
 */
@RestController
@RequestMapping("/")
public class GetHttpclientController {

    @Resource
    private TestService testService;

    @PostMapping("getHttpInfo")
    public String getHttpInfo(HttpServletRequest request) {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();


    }

    @PostMapping("getFileItem")
    public void getFileItem(@RequestBody FileTrans tran) {

//        try {
////            byte[] content = tran.getFile().getContent();
//
////            String s = new String(content, tran.getFileCharset());
////            System.out.println(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        testService.fileProcess(tran);


    }


}
