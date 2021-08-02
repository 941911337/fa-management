package cn.john.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @Author John Yan
 * @Description RsaUtil
 * @Date 2021/7/21
 **/
public class RsaUtil {

    private final static String jdbcFaPubKey = "";
    private final static String jdbcFaPriKey = "";
    private final static String jdbcLogPubKey = "";
    private final static String jdbcLogPriKey = "";

    public static String pubDecrypt(String str, String publicKey){
        RSA rsa = new RSA(null, publicKey);
        byte[] decrypt2 = rsa.decrypt(str, KeyType.PublicKey);
        return StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8);
    }



    public static void main(String[] args) {
        RSA prrsa = new RSA(jdbcLogPriKey, null);
        RSA pursa = new RSA(null, jdbcLogPubKey);
        byte[] encrypt2 = prrsa.encrypt(StrUtil.bytes("123", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        System.out.println(HexUtil.encodeHexStr(encrypt2));
        byte[] decrypt2 = pursa.decrypt(HexUtil.encodeHexStr(encrypt2), KeyType.PublicKey);
        System.out.println(StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
    }
}
