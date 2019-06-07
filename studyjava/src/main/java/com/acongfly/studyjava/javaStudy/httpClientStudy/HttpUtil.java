package com.acongfly.studyjava.javaStudy.httpClientStudy;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by ecom on 2015/7/25.
 */
public class HttpUtil {
    /**
     * 默认重试次数
     */
    private static final int DEFAULT_HTTP_REQUEST_RETRY_COUNT = 3;
    /**
     * HTTP请求头最大个数
     */
    private static final int MAX_CON_COUNT_IN_TOTAL = 100;
    /**
     * HTTP请求头最大个数
     */
    private static final int MAX_CON_PER_ROUTE = 10;
    /**
     * HTTP请求头最大个数
     */
    private static final int MAX_HEADER_COUNT = 200;

    /**
     * 最大行长度，单位是byte
     */
    private static final int MAX_LINE_LENGTH = 2000;

    /**
     * 两个连续的数据包之间最长不活动时间
     */
    private static final int SOCKET_TIME_OUT = 20000;

    /**
     * 连接建立超时时间
     */
    private static final int CONNECT_TIME_OUT = 20000;

    /**
     * 请求获得连接超时时间
     */
    private static final int CONNECT_REQUEST_TIME_OUT = 20000;

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = Consts.UTF_8;

    private static final CloseableHttpClient httpclient;

    private final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final HttpUtil instance = new HttpUtil();

    public static final HttpUtil getInstance() {
        return instance;
    }

    private HttpUtil() {
    }

    static {
        // Client HTTP connection objects when fully initialized can be bound to
        // an arbitrary network socket. The process of network socket initialization,
        // its connection to a remote address and binding to a local one is controlled
        // by a connection socket factory.

        // SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();

        // Create a connection manager with custom configuration.
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);

        // Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoTimeout(SOCKET_TIME_OUT)
                .build();
        // Configure the connection manager to use socket configuration either
        // by default or for a specific host.
        connManager.setDefaultSocketConfig(socketConfig);
        // Validate connections after 1 sec of inactivity
        connManager.setValidateAfterInactivity(1000);

        // Create message constraints
        MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(MAX_HEADER_COUNT)
                .setMaxLineLength(MAX_LINE_LENGTH)
                .build();
        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(DEFAULT_CHARSET)
                .setMessageConstraints(messageConstraints)
                .build();
        // Configure the connection manager to use connection configuration either
        // by default or for a specific host.
        connManager.setDefaultConnectionConfig(connectionConfig);

        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connManager.setMaxTotal(MAX_CON_COUNT_IN_TOTAL);
        connManager.setDefaultMaxPerRoute(MAX_CON_PER_ROUTE);

        // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT)
                .build();

        // Create an HttpClient with the given custom dependencies and configuration.
        httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(DEFAULT_HTTP_REQUEST_RETRY_COUNT, false))
                .build();
    }

    public String doHttpGet(String url, Map<String, String> params) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "URL can not be empty or null.");
        RequestBuilder requestBuilder = RequestBuilder.get(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBuilder.addParameter(entry.getKey(), entry.getValue());
        }
        return doRequest(requestBuilder.build());
    }

    public String doHttpPost(String url, Map<String, String> params) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "URL can not be empty or null.");
        RequestBuilder requestBuilder = RequestBuilder.post(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBuilder.addParameter(entry.getKey(), entry.getValue());
        }
        return doRequest(requestBuilder.build());
    }

    private String doRequest(HttpUriRequest method) {
        logger.debug(method.getURI().toString());
        // Execution context can be customized locally.
        HttpClientContext context = HttpClientContext.create();

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };

        String response = null;
        try {
            response = httpclient.execute(method, responseHandler, context);
            // Once the request has been executed the local context can
            // be used to examine updated state and various objects affected
            // by the request execution.

//            // Last executed request
//            System.out.println(context.getRequest());
//            // Execution route
//            System.out.println(context.getHttpRoute());
//            // Target auth state
//            System.out.println(context.getTargetAuthState());
//            // Proxy auth state
//            System.out.println(context.getTargetAuthState());
//            // Cookie origin
//            System.out.println(context.getCookieOrigin());
//            // Cookie spec used
//            System.out.println(context.getCookieSpec());
//            // User security token
//            System.out.println(context.getUserToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
