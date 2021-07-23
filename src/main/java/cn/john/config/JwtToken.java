package cn.john.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author yanzhengwei
 * @Description JwtToken
 * @Date 2021/7/14
 **/
public class JwtToken implements AuthenticationToken {

    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    @Override//类似是用户名
    public Object getPrincipal() {
        return jwt;
    }

    @Override//类似密码
    public Object getCredentials() {
        return jwt;
    }
    //返回的都是jwt
}