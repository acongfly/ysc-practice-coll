package com.acongfly.studyjava.javaStudy.httpClientStudy;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shicong yang
 * @Description: https客户端
 * @date 2017年8月1日 下午4:13:04
 */
public class HttpsClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpsClientUtil.class);
    private static final String ENC_UTF8 = "UTF-8";
    /**
     * 默认的http连接超时时间
     */
    private final static int DEFAULT_CONN_TIMEOUT = 10000; // 10s
    /**
     * 默认的http read超时时间
     */
    private final static int DEFAULT_READ_TIMEOUT = 120000; // 120s

    private static class MyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * @param url  请求地址
     * @param data 参数字符串
     * @return String 应答结果字符串
     * @throws
     * @Description: https协议 post zip
     * @author: shicong yang
     * @date: 2018年1月15日 下午3:47:03
     */
    public static String postZip(String url, String data) {
        return postZip(url, data, ENC_UTF8);
    }

    /**
     * @param url     请求地址
     * @param data    参数字符串
     * @param charset 字符集
     * @return String 应答结果字符串
     * @throws
     * @Description: https协议 post zip
     * @author: shicong yang
     * @date: 2018年1月15日 下午3:50:59
     */
    private static String postZip(String url, String data, String charset) {
        return postZip(url, data, charset, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 参数map
     * @return String 应答结果字符串
     * @throws
     * @Description: https协议 post zip
     * @author: shicong yang
     * @date: 2018年1月9日 下午8:37:22
     */
    public static String postZip(String url, Map<String, String> paramsMap) {
        return postZip(url, paramsMap, ENC_UTF8);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 参数map
     * @param charset   字符集
     * @return String 应答结果字符串
     * @throws
     * @Description: https协议 post zip
     * @author: shicong yang
     * @date: 2018年1月9日 下午8:35:57
     */
    public static String postZip(String url, Map<String, String> paramsMap, String charset) {
        return postZip(url, paramsMap, charset, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 参数map
     * @param charset   字符集
     * @param connOut   链接超时时间
     * @param readOut   请求超时 时间
     * @return String 应答结果字符串
     * @throws
     * @Description: https协议 post zip
     * @author: shicong yang
     * @date: 2018年1月9日 下午8:36:37
     */
    public static String postZip(String url, Map<String, String> paramMap, String charset, int connOut, int readOut) {
        return postZip(url, getParamStr(paramMap), charset, connOut, readOut);
    }

    /**
     * @param url       请求地址
     * @param paramData 参数数据
     * @param charset   字符集
     * @param connOut   链接超时时间
     * @param readOut   请求超时 时间
     * @return String 应答结果字符串
     * @throws
     * @Description: https协议 post zip
     * @author: shicong yang
     * @date: 2018年1月9日 下午8:36:37
     */
    public static String postZip(String url, String paramData, String charset, int connOut, int readOut) {
        BufferedReader in = null;
        DataOutputStream out = null;
        HttpsURLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new java.security.SecureRandom());
            uc = (HttpsURLConnection) new URL(url).openConnection();
            uc.setSSLSocketFactory(sc.getSocketFactory());
            uc.setHostnameVerifier(new TrustAnyHostnameVerifier());
            uc.setRequestMethod("POST");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setUseCaches(false);
            uc.setConnectTimeout(connOut);
            uc.setReadTimeout(readOut);
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.connect();
            out = new DataOutputStream(uc.getOutputStream());
            out.write(paramData.getBytes(charset));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(uc.getInputStream()), charset));
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
        } catch (Exception e) {
            LOG.error("[HttpsClient POST GZIP]请求异常", e);
        } finally {
            close(in, uc, null, out);
        }
        return sb.toString();
    }

    /**
     * @param url       请求地址
     * @param paramsMap 请求参数 map
     * @return String 应答结果字符串
     * @throws
     * @Description: HTTPS协议POST请求方法
     * @author: shicong yang
     * @date: 2018年1月9日 下午12:30:39
     */
    public static String post(String url, Map<String, String> paramMap) {
        return post(url, paramMap, ENC_UTF8);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 请求参数 map
     * @param charset   字符集
     * @return String 应答结果字符串
     * @throws
     * @Description: HTTPS协议POST请求方法
     * @author: shicong yang
     * @date: 2017年11月20日 下午5:02:08
     */
    public static String post(String url, Map<String, String> paramsMap, String charset) {
        return post(url, paramsMap, charset, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param url   请求地址
     * @param param 参数字符串
     * @return String
     * @throws
     * @Description: HTTPS协议POST请求方法
     * @author: shicong yang
     * @date: 2018年3月25日 下午7:16:34
     */
    public static String post(String url, String param) {
        return post(url, param, ENC_UTF8, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 参数map
     * @param charset   字符集
     * @param connOut   链接超时时间
     * @param readOut   响应超时时间
     * @return String 应答结果字符串
     * @throws
     * @Description: HTTPS协议POST请求方法
     * @author: shicong yang
     * @date: 2018年1月9日 下午12:27:13
     */
    public static String post(String url, Map<String, String> paramMap, String charset, int connOut, int readOut) {
        return post(url, getParamStr(paramMap), charset, connOut, readOut);
    }

    /**
     * @param url     请求地址
     * @param param   参数字符串
     * @param charset 字符集
     * @param connOut 链接超时时间
     * @param readOut 响应超时时间
     * @return String 应答结果字符串
     * @throws
     * @Description: HTTPS协议POST请求方法
     * @author: shicong yang
     * @date: 2018年1月9日 下午12:27:13
     */
    public static String post(String url, String param, String charset, int connOut, int readOut) {
        String result = null;
        BufferedReader in = null;
        DataOutputStream out = null;
        HttpsURLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new java.security.SecureRandom());
            uc = (HttpsURLConnection) new URL(url).openConnection();
            uc.setSSLSocketFactory(sc.getSocketFactory());
            uc.setHostnameVerifier(new TrustAnyHostnameVerifier());
            uc.setRequestMethod("POST");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setUseCaches(false);
            uc.setConnectTimeout(connOut);
            uc.setReadTimeout(readOut);
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.connect();
            out = new DataOutputStream(uc.getOutputStream());
            out.write(param.getBytes(charset));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), charset));
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            result = URLDecoder.decode(sb.toString(), charset);
        } catch (Exception e) {
            LOG.error("[HttpsClient POST]请求异常", e);
        } finally {
            close(in, uc, null, out);
        }
        return result;
    }

    /**
     * @param url  请求地址
     * @param data 请求参数
     * @return byte[] 二进制文件
     * @throws
     * @Description: postFile
     * @author: shicong yang
     * @date: 2018年1月16日 上午10:44:32
     */
    public static byte[] postFile(String url, String data) {
        return postFile(url, data, ENC_UTF8);
    }

    /**
     * @param url     请求地址
     * @param data    请求参数
     * @param charset 字符集
     * @return byte[]二进制文件
     * @throws
     * @Description: postFile
     * @author: shicong yang
     * @date: 2018年1月16日 上午10:23:29
     */
    private static byte[] postFile(String url, String data, String charset) {
        return postFile(url, data, charset, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param url     请求地址
     * @param data    请求参数
     * @param charset 字符集
     * @param connOut 连接超时时间
     * @param readOut 读取超时时间
     * @return byte []
     * @throws
     * @Description: postFile
     * @author: shicong yang
     * @date: 2018年1月16日 上午10:24:19
     */
    private static byte[] postFile(String url, String data, String charset, int connOut, int readOut) {

        byte[] btFile = null;
        BufferedReader in = null;
        DataOutputStream out = null;
        HttpsURLConnection uc = null;
        ByteArrayOutputStream outStream = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new java.security.SecureRandom());
            uc = (HttpsURLConnection) new URL(url).openConnection();
            uc.setSSLSocketFactory(sc.getSocketFactory());
            uc.setHostnameVerifier(new TrustAnyHostnameVerifier());
            uc.setRequestMethod("POST");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setUseCaches(false);
            uc.setConnectTimeout(connOut);
            uc.setReadTimeout(readOut);
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.connect();
            out = new DataOutputStream(uc.getOutputStream());
            out.write(data.getBytes(charset));
            out.flush();
            out.close();
            int respCode = uc.getResponseCode();
            if (respCode != 200) {
                in = new BufferedReader(new InputStreamReader(uc.getInputStream(), charset));
                StringBuilder sb = new StringBuilder();
                char[] buff = new char[2048];
                int cnt = 0;
                while ((cnt = in.read(buff)) != -1) {
                    sb.append(buff, 0, cnt);
                }
                in.close();
                LOG.warn("[HttpsClient POST FILE]{}", sb.toString());
                return null;
            }
            InputStream inStream = uc.getInputStream(); // 获取文件流二进制数据
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            btFile = outStream.toByteArray();
        } catch (Exception e) {
            LOG.error("[HttpsClient POST FILE]请求异常", e);
        } finally {
            close(in, uc, null, outStream);
        }
        return btFile;
    }

    /**
     * @param url       请求地址
     * @param paramsMap 参数map
     * @return byte[] 二进制文件
     * @throws
     * @Description: HTTPS协议POST File请求方法
     * @author: shicong yang
     * @date: 2018年1月9日 下午8:14:11
     */
    public static byte[] postFile(String url, Map<String, String> paramsMap) {
        return postFile(url, paramsMap, ENC_UTF8);
    }

    /**
     * @param url       请求地址
     * @param paramsMap 参数map
     * @param charset   字符集
     * @return byte[] 二进制文件
     * @throws
     * @Description: HTTPS协议POST File请求方法
     * @author: shicong yang
     * @date: 2017年11月20日 下午5:06:03
     */
    public static byte[] postFile(String url, Map<String, String> paramsMap, String charset) {
        return postFile(url, getParamStr(paramsMap), charset, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param paramsMap 参数map
     * @return String 应答结果
     * @throws
     * @Description: HTTP协议POST请求添加参数的封装方法
     * @author: shicong yang
     * @date: 2017年11月20日 下午3:46:11
     */
    private static String getParamStr(Map<String, String> paramsMap) {
        StringBuffer param = new StringBuffer();
        if (paramsMap == null) {
            return param.toString();
        }
        for (Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            param.append("&").append(e.getKey()).append("=").append(e.getValue());
        }
        return param.toString().substring(1);
    }

    /**
     * @param in
     * @param uc
     * @param inStream
     * @param outStream
     * @return void
     * @throws
     * @Description: 关闭IO
     * @author: shicong yang
     * @date: 2017年11月20日 下午5:15:35
     */
    private static void close(BufferedReader in, HttpsURLConnection uc, InputStream inStream, OutputStream outStream) {
        try {
            if (in != null) {
                in.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        } catch (IOException e) {
            LOG.error("[HttpsClient]关闭流异常", e);
        }
    }

}
