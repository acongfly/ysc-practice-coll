package com.acongfly.studyjava.javaStudy.fileStudy;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @program: study
 * @description: 文件学习
 * @author: shicong yang
 * @create: 2019-04-17 11:42
 **/
public class FileStudy {


    public static void main(String[] args) {

        writeCsv();


    }

    private static void writeCsv() {
        CsvWriter writer = CsvUtil.getWriter("/Users/shicongyang/work/testWrite.csv", CharsetUtil.CHARSET_UTF_8, false);
        Collection<String[]> linkedList = new ArrayList<>();
        Collection<String> subLinkedList = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 102400; i++) {

            if (i == 0) {
                subLinkedList.add("序号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
                subLinkedList.add("订单号");
            } else {
                subLinkedList.add(i + "");
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
                subLinkedList.add("test" + i);
            }

            String[] strings = subLinkedList.toArray(new String[subLinkedList.size()]);
            linkedList.add(strings);
            subLinkedList.clear();
        }
        writer.write(linkedList);
        writer.flush();
        writer.close();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}
