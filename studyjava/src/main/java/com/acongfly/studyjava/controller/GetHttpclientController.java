package com.acongfly.studyjava.controller;

import cn.hutool.core.io.FileUtil;
import com.acongfly.studyjava.javaStudy.other.FileTrans;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hc on 2018/8/8.
 */
@RestController
@RequestMapping("/")
public class GetHttpclientController {

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
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        byte[] content = tran.getContent();
//        FileUtil.isFile()
        File file = FileUtil.writeBytes(content, new File("/Users/shicongyang/work/testWrite2.csv"));
        stopWatch.stop();
        System.out.println("total==============:" + stopWatch.getTotalTimeSeconds());


    }

}
