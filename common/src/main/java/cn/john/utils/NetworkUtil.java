package cn.john.utils;

import cn.john.common.Constants;
import cn.john.common.SingletonManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author John Yan
 * @Description NetUtil
 * @Date 2021/8/1
 **/
@Slf4j
public abstract class NetworkUtil {

    /**
     * 根据ip地址获取归属地
     * @param ip ip地址
     * @return 归属地
     */
    public static String getIpAddress(String ip) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.IP_INTERFACE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(SingletonManagement.OkHttpClientObject.CLIENT.getClientInstance())
                .build();

        InterfaceUrl service = retrofit.create(InterfaceUrl.class);
        Call<String> callSync = service.getIpAddress(ip);
        Response<String>  response = null;
        try {
            response = callSync.execute();
            return response.body().trim();
        } catch (IOException e) {
           log.error(e.getMessage());
        }
        return "";

    }

    /**
     * 获取ip地址
     * @return ip 地址
     */
    public static String getIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return Constants.IP;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !Constants.UNKNOWN.equalsIgnoreCase(ip) && ip.indexOf(",") != -1) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //根据网卡取本机配置的IP
        if (ip == null) {
            ip = Constants.IP;
        }
        return ip;

    }

}
