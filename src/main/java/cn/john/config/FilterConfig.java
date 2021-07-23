package cn.john.config;

import cn.john.filter.GlobalFilter;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yanzhengwei
 * @Description FilterConfig
 * @Date 2021/7/21
 **/
@Configuration
public class FilterConfig {

    @NacosValue("${bucket.capacity}")
    private int capacity;

    @NacosValue("${bucket.refillTokens}")
    private int refillTokens;


    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new GlobalFilter(capacity,refillTokens));
        registration.addUrlPatterns("/*");
        registration.setName("globalFilter");
        registration.setOrder(1);
        return registration;
    }
}
