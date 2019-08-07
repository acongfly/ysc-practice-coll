package com.acongfly.studyjava.controller;

//import com.acongfly.study.annotation.CheckValue;
//import com.acongfly.study.annotation.LogSave;

import com.acongfly.studyjava.javaStudy.other.FileTrans;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-07-31 19:51
 **/
@Service
public class TestService {

    //    @LogSave
//    @CheckValue
    public String fileProcess(FileTrans tran) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(tran);
//        byte[] content = tran.getContent();
//        FileUtil.isFile()
//        File file = FileUtil.writeBytes(content, new File("/Users/shicongyang/work/testWrite2.csv"));
        stopWatch.stop();
        System.out.println("total==============:" + stopWatch.getTotalTimeSeconds());
        return "success";
    }
}
