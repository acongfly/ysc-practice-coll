package com.acongfly.studyjava.controller;

import cn.hutool.json.JSONUtil;
import com.acongfly.studyjava.javaStudy.httpClientStudy.HttpClientUtil;
import com.acongfly.studyjava.javaStudy.other.FileTrans;

import java.io.*;


public class GetHttpclientControllerTest {

    public static void main(String[] args) throws IOException {
        FileTrans fileTran = new FileTrans();
        fileTran.setMerId("12345678");
//        FileItem fileItem = new FileItem("/Users/shicongyang/work/testWrite.csv");
//        String string = new String(fileItem.getContent(), "utf-8");
//        File file = new File("/Users/shicongyang/workword/pay_order_smoke.csv");
//        byte[] content = getContent(file);
//        System.out.println(string);string
//        fileTran.setFile(fileItem);
//        fileTran.setFileCharset("utf-8");
//        fileTran.setContent(content);
        String s = HttpClientUtil.post("http://localhost:8001/getFileItem/", JSONUtil.toJsonStr(fileTran));
        System.out.println(s);
    }

    public static byte[] getContent(File file) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] content;

        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                out.write(ch);
            }
            content = out.toByteArray();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return content;
    }
}