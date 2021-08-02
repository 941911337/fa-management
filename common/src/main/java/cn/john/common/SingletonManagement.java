package cn.john.common;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * @Author John Yan
 * @Description SingletonManagement
 * @Date 2021/8/1
 **/
@Slf4j
public abstract class SingletonManagement {


    public enum OkHttpClientObject {
        CLIENT;

        private OkHttpClient clientInstance;

        private Integer connectTimeout_time = 10;
        private Integer writeTimeout_time = 10;
        private Integer readTimeout_time = 30;

        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                log.info("OkHttp====Message:"+message);
            }
        });

        OkHttpClientObject() {
            loggingInterceptor.setLevel(level);
            clientInstance = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout_time, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout_time, TimeUnit.SECONDS)
                    .readTimeout(readTimeout_time, TimeUnit.SECONDS)
                    .addNetworkInterceptor(loggingInterceptor)  //设置打印拦截日志
                    //                  .addInterceptor(new HttpLoggingInterceptor())  //自定义的拦截日志，拦截简单东西用，后面会有介绍
                    .connectionPool(new ConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        }

        public OkHttpClient getClientInstance() {
            return clientInstance;
        }
    }
}
