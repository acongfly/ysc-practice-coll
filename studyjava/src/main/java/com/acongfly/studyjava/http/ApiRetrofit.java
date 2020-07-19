package com.acongfly.studyjava.http;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * https://mzeht.com/2017/01/22/%E5%9C%A8springboot%E4%B8%AD%E4%BD%BF%E7%94%A8retrofit%E5%8A%A0%E5%BF%AB%E4%B8%89%E6%96%B9%E6%8E%A5%E5%8F%A3%E8%B0%83%E7%94%A8/
 * https://github.com/luwojtaszek/springboot-retrofit/blob/master/src/main/java/com/luwojtaszek/springboot/retrofit/web/controller/GitHubController.java
 * https://blog.csdn.net/hanj456/article/details/53539724 https://www.jianshu.com/p/a3fed8138457
 */
@Component
public class ApiRetrofit {

    private static final Logger log = Logger.getLogger(ApiRetrofit.class);

    private CommonApi commonApi;

    public CommonApi getCommonApi() {
        return commonApi;
    }
    //
    // private CorpApi corpApi;
    //
    // public CorpApi getCorpApi() {
    // return corpApi;
    // }

    @Value("${api.common}")
    private String COMMON_BASE_URL;// = "http://10.10.168.79:10100/";

    @Value("${api.corp}")
    private String CROP_BASE_URL;

    @PostConstruct
    public void init() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().connectionPool(new ConnectionPool(5, 6, TimeUnit.MINUTES))
            .connectTimeout(60, TimeUnit.SECONDS) // 设置连接超时
            .readTimeout(60, TimeUnit.SECONDS) // 设置读超时
            .writeTimeout(60, TimeUnit.SECONDS) // 设置写超时
            .retryOnConnectionFailure(true) // 是否自动重连
            .addNetworkInterceptor(logInterceptor).build();

        Retrofit retrofit_common = new Retrofit.Builder().baseUrl(COMMON_BASE_URL).client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        commonApi = retrofit_common.create(CommonApi.class);

        // Retrofit retrofit_corp = new Retrofit.Builder().baseUrl(CROP_BASE_URL + "/").client(client)
        // .addConverterFactory(JacksonConverterFactory.create()).build();
        // corpApi = retrofit_corp.create(CorpApi.class);

    }

    private class HttpLogger implements HttpLoggingInterceptor.Logger {
        // private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            log.info(message);
            //
            // if (message.startsWith("--> POST")) {
            // mMessage.setLength(0);
            // mMessage.append("\n");
            // }
            // // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            // if ((message.startsWith("{") && message.endsWith("}"))
            // || (message.startsWith("[") && message.endsWith("]"))) {
            // message = JSONUtil.formatJson(message);
            // }
            // mMessage.append(message.concat("\n"));
            // // 请求或者响应结束，打印整条日志
            // if (message.startsWith("<-- END HTTP")) {
            // log.info(mMessage.toString());
            // }

        }
    }
}