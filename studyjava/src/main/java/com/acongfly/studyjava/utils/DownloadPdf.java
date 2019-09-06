package com.acongfly.studyjava.utils;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class DownloadPdf {

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadByUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");

    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        System.out.println("read");
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {

        try {
            File file = new File("/Users/shicongyang/work/ysc-practice-coll/python-demo/blog.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            List<String> urls = IOUtils.readLines(fileInputStream, "UTF-8");
            for (int i = 0; i < urls.size(); i++) {
                String s = urls.get(i);
//                downLoadByUrl(s,
//                        s.substring(s.lastIndexOf("/")), "download_pdf");
//                String mp3Url="http://win.web.rh01.sycdn.kuwo.cn/resource/n1/24/6/707126989.mp3"; //四叶草-好想你
                File fileDown = new File("/Users/shicongyang/work/ysc-practice-coll/python-demo/download/" + s.substring(s.lastIndexOf("/")));
                FileOutputStream fileOutputStream = new FileOutputStream(fileDown);
                HttpClientUtil.down(HttpConfig.custom().url(s).out(fileOutputStream));
                if (fileDown.exists()) {
                    System.out.println("下载成功了！存放在：" + fileDown.getPath());
                }
                fileOutputStream.flush();
                fileOutputStream.close();

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}