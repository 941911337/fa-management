package cn.john.filter;

import cn.hutool.json.JSONUtil;
import cn.john.token.JwtToken;
import cn.john.dto.JsonMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author John Yan
 * @Description JwtFilter
 * @Date 2021/7/14
 *  自定义一个Filter，用来拦截所有的请求判断是否携带Token
 *   isAccessAllowed()判断是否携带了有效的JwtToken
 *   onAccessDenied()是没有携带JwtToken的时候进行账号密码登录，登录成功允许访问，登录失败拒绝访问
 **/

@Slf4j
public class JwtFilter extends AccessControlFilter {
    /**
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.warn("isAccessAllowed 方法被调用");
        //这里先让它始终返回false来使用onAccessDenied()方法
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.warn("onAccessDenied 方法被调用");
        // Token 放在header里面
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        //header中token为空 直接失败
        if(StringUtils.isEmpty(jwt)){
            onLoginFail(servletResponse,false);
            return false;
        }
        JwtToken jwtToken = new JwtToken(jwt);
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            onLoginFail(servletResponse,true);
            return false;
        }
        return true;
    }

    /**
     * 登录失败时默认返回 401 状态码
     */
    private void onLoginFail(ServletResponse response,boolean isReLogin) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        JsonMessage result = JsonMessage.noLoginError(isReLogin ?"请重新登陆":"请登录");
        httpResponse.getWriter().write(JSONUtil.toJsonStr(result));
    }
}

