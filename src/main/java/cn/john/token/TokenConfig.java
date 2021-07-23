package cn.john.token;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
/**
 * @author John Yan
 */
@Data
@Component
public class TokenConfig {

    /**
     * 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
     */

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @NacosValue("${token-secretKey}")
    private String secretKey;

    public String getBase64SecretKey(){
        return Base64.encodeBase64String(secretKey.getBytes());
    }



}
