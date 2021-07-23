package cn.john.token;

import cn.john.common.Constants;
import cn.john.exception.BusinessException;
import cn.john.model.Account;
import cn.john.token.JwtToken;
import cn.john.utils.RedisUtil;
import cn.john.utils.SysUtil;
import cn.john.utils.TokenUtil;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Author yanzhengwei
 * @Description JwtRealm
 * @Date 2021/7/14
 **/
@Slf4j
public class JwtRealm extends AuthorizingRealm {
    /**
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     * 这个token就是从过滤器中传入的jwtToken
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }

        boolean flag = false;
        try {
            flag = TokenUtil.isVerify(jwt);
        } catch (TokenExpiredException e) {
            throw new BusinessException("token过期,请重新登录");
        } catch (SignatureVerificationException e) {
            throw new BusinessException("token有误,请重新登录");
        }
        //判断
        if (!flag) {
            throw new UnknownAccountException();
        }
        //下面是验证这个user是否是真实存在的
        Claims claim =  TokenUtil.parseJwt(jwt);
        String username = (String) claim.get("username");
        Long id = Long.parseLong(claim.get("id").toString());
        String client = (String) claim.get("clientId");
        //判断数据库中username是否存在
        Account account = (Account) RedisUtil.hGet(Constants.LOGIN_CACHE_NAMESPACE+id,"account");
        if(!account.getAccount().equals(username)){
            throw new BusinessException("请重新登录");
        }
        String cacheClientId = (String) RedisUtil.hGet(Constants.LOGIN_CACHE_NAMESPACE+id,"clientId");
        if(!cacheClientId.equals(client)){
            throw new BusinessException("请重新登录");
        }
        SysUtil.putUser(account);
        return new SimpleAuthenticationInfo(jwt,jwt,"JwtRealm");
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名

    }

}
