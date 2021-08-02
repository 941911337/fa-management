package cn.john.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author John Yan
 * @Description InterfaceUrl
 * @Date 2021/8/1
 **/
public interface InterfaceUrl {

    @GET("/ip.jsp")
    Call<String> getIpAddress(@Query("ip") String ip);
}
