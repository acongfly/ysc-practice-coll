package com.acongfly.studyjava.javaStudy.httpClientStudy;

import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;


/**
 * description: 利用HttpClient进行post请求的工具类  <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2018/7/3 <p>
 */
public class HttpClientUtil {

    private static final String POST = "POST";
    private static final String ENC_UTF8 = "UTF-8";

    /**
     * 默认的http连接超时时间
     */
    private final static int DEFAULT_CONN_TIMEOUT = 10000; // 10s
    /**
     * 默认的http read超时时间
     */
    private final static int DEFAULT_READ_TIMEOUT = 60000; // 60s

    /**
     * 默认重试次数
     */
    private static final int DEFAULT_HTTP_REQUEST_RETRY_COUNT = 5;

    /**
     * HTTP请求头最大个数
     */
    private static final int MAX_CON_COUNT_IN_TOTAL = 100;
    /**
     * HTTP请求头最大个数
     */
    private static final int MAX_CON_PER_ROUTE = 10;


    /**
     * description: HTTP协议POST GZIP请求方法 <p>
     * param: [url, paramsMap, charset] <p>
     * return: java.lang.String <p>
     * author: shicong yang<p>
     * date: 2018/7/3 <p>
     */
    public static String postGZIP(String url, Map<String, String> paramsMap, String charset) {
        if (StringUtils.isBlank(charset)) {
            charset = ENC_UTF8;
        }
        BufferedReader in = null;
        HttpURLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            uc = (HttpURLConnection) new URL(url).openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setRequestMethod("POST");
            uc.setUseCaches(false);
            uc.connect();
            DataOutputStream out = new DataOutputStream(uc.getOutputStream());
            out.write(getParamStr(paramsMap).getBytes(charset));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(uc.getInputStream()), charset));
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(in, uc);
        }
        return sb.toString();
    }

    /**
     * description: HTTP协议POST请求方法
     * param: [url  请求地址, parmMap 请求参数map ] <p>
     * return: java.lang.String <p>
     * author: shicong yang<p>
     * date: 2018/7/3 <p>
     */
    public static String post(String url, Map<String, String> parmMap) {
        return post(url, getParamStr(parmMap), DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT, ENC_UTF8);
    }

    /**
     * HTTP协议POST请求方法
     *
     * @param url     请求地址
     * @param parmMap 请求参数map
     * @param charset 字符集
     * @return String
     * @throws
     */
    public static String post(String url, Map<String, String> parmMap, String charset) {
        return post(url, getParamStr(parmMap), DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT, charset);
    }


    /**
     * HTTP协议POST请求
     *
     * @param url   请求地址
     * @param param 参数字符串
     * @return String
     * @throws
     */
    public static String post(String url, String param) {
        return post(url, param, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT, ENC_UTF8);
    }

    /**
     * description: HTTP协议POST请求
     * param: [url 请求地址, param 参数字符串, connOut, readOut, charset 字符集] <p>
     * return: java.lang.String <p>
     * author: shicong yang<p>
     * date: 2018/7/3 <p>
     */
    public static String post(String url, String param, int connOut, int readOut, String charset) {
        BufferedReader in = null;
        HttpURLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            uc = (HttpURLConnection) new URL(url).openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            uc.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            uc.setRequestProperty("accept", "*/*");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setRequestMethod(POST);
            uc.setUseCaches(false);
            uc.setConnectTimeout(connOut);
            uc.setReadTimeout(readOut);
            uc.connect();
            DataOutputStream out = new DataOutputStream(uc.getOutputStream());
            out.write(param.getBytes("UTF-8"));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), charset));
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
        } catch (IOException e) {
            System.out.println("[HttpClient POST]请求异常" + e);
        } finally {
            close(in, uc);
        }
        return sb.toString();
    }

    /**
     * description: HTTP协议POST请求添加参数的封装方 <p>
     * param: [parmMap] <p>
     * return: java.lang.String <p>
     * author: shicong yang<p>
     * date: 2018/7/3 <p>
     */
    private static String getParamStr(Map<String, String> parmMap) {
        StringBuilder param = new StringBuilder();
        if (parmMap == null) {
            return param.toString();
        }
        for (Iterator<Map.Entry<String, String>> it = parmMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            param.append("&").append(e.getKey()).append("=").append(e.getValue());
        }
        return param.toString().substring(1);
    }

    /**
     * description: 关闭IO <p>
     * param: [in, uc] <p>
     * return: void <p>
     * author: shicong yang<p>
     * date: 2018/7/3 <p>
     */
    private static void close(BufferedReader in, HttpURLConnection uc) {
        try {
            if (in != null) {
                in.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
        } catch (IOException e) {
            System.out.println("[HttpClient]关闭流异常" + e);
        }
    }

    /**
     * 使用线程池的post请求(HTTP)
     *
     * @param url   地址
     * @param param json串
     * @return
     */
    public static String postUsePool(String url, String param) {
        try {
            HCB hcb = HCB.custom()
                    .timeout(DEFAULT_CONN_TIMEOUT)        //超时
                    .pool(MAX_CON_COUNT_IN_TOTAL, MAX_CON_PER_ROUTE)        //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
//                    .sslpv(SSLs.SSLProtocolVersion.TLSv1_2)    //可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
                    .ssl()                    //https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                    .retry(DEFAULT_HTTP_REQUEST_RETRY_COUNT);  //重试5次
            HttpClient client = hcb.build();
            //插件式配置请求参数（网址、请求参数、编码、client）
            HttpConfig config = HttpConfig.custom()
//											.headers(headers)	//设置headers，不需要时则无需设置
                    .url(url)                    //设置请求的url
//					.map(map)			//设置请求参数，没有则无需设置
                    .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
                    .client(client)                                                        //如果只是简单使用，无需设置，会自动获取默认的一个client对象
                    //.inenc("utf-8") 													//设置请求编码，如果请求返回一致，不需要再单独设置
//                .inenc("utf-8")													//设置返回编码，如果请求返回一致，不需要再单独设置
                    .json(param)                                                //json方式请求的话，就不用设置map方法，当然二者可以共用。
                    .headers(new Header[]{
                            new BasicHeader("Content-Type", "application/json;charset=UTF-8")
                    })
                    //.context(HttpCookies.custom().getContext()) 		//设置cookie，用于完成携带cookie的操作
                    //.out(new FileOutputStream("保存地址"))			 	//下载的话，设置这个方法,否则不要设置
                    //.files(new String[]{"d:/1.txt","d:/2.txt"})					//上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
                    ;
            //post请求
            return com.arronlong.httpclientutil.HttpClientUtil.post(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return null;
    }

}