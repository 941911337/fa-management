package cn.john.token;

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

    /**
     * 类似是用户名
     * @return
     */
    @Override
    public Object getPrincipal() {
        return jwt;
    }

    /**
     * 类似密码
     * @return
     */
    @Override
    public Object getCredentials() {
        return jwt;
    }
}