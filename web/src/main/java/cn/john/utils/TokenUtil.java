package cn.john.utils;

import cn.john.exception.BusinessException;
import cn.john.token.TokenConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * description: TokenUtil
 * date: 2020/10/20 14:34
 * version: 1.0
 * @author John Yan
 */
public class TokenUtil {


    /**
     * token 配置对象
     */
    private static TokenConfig tokenConfig;


    public static void setTokenConfig(TokenConfig tokenConfig) {
        TokenUtil.tokenConfig = tokenConfig;
    }



    /**
     * 签发JWT
     * @param account 账号
     * @param claims 数据
     * @param ttlMillis 过期时间
     * @return token
     *
     */
    public static String createJwt(String account,  Map<String, Object> claims ,long ttlMillis) {
        if (claims == null) {
            claims = new HashMap<>(16);
        }
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(account)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(tokenConfig.getSignatureAlgorithm(), tokenConfig.getBase64SecretKey());
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            //4. 过期时间，这个也是使用毫秒生成的，使用当前时间+前面传入的持续时间生成
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }



    /**
     * 解析JWT字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJwt(String jwt) {
        return Jwts.parser().setSigningKey(tokenConfig.getBase64SecretKey()).parseClaimsJws(jwt).getBody();
    }

    /**
     * 校验Token是否合法
     * @param jwt token
     * @return
     */
    public static Boolean isVerify(String jwt) {
        //这个是官方的校验规则，这里只写了一个”校验算法“，可以自己加
        Algorithm algorithm = null;
        switch (tokenConfig.getSignatureAlgorithm()) {
            case HS256:
                algorithm = Algorithm.HMAC256(Base64.decodeBase64(tokenConfig.getBase64SecretKey()));
                break;
            default:
                throw new RuntimeException("不支持该算法");
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        // 校验不通过会抛出异常
        verifier.verify(jwt);
        //判断合法的标准：1. 头部和荷载部分没有篡改过。2. 没有过期
        return true;
    }







}
