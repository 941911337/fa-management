package cn.john.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @Author yanzhengwei
 * @Description RsaUtil
 * @Date 2021/7/21
 **/
public class RsaUtil {

    public static String pubDecrypt(String str, String publicKey){
        RSA rsa = new RSA(null, publicKey);
        byte[] decrypt2 = rsa.decrypt(str, KeyType.PublicKey);
        return StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8);
    }



    public static void main(String[] args) {
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALpD4KMa26Ozqro+7j3Wb/29fboHDz2Nr6GY/62Ol/fj9VynnvaNFZFVcX9sYx48LTo0RX/LlvDSoWq7r25KWGLgEdrc39AR8aU/bAVzURWx/YjmTsmPjphOpHMi6sy4ZhX3Zj+w+sKS3y7ofAKZPC0TsX22lQNbKtnzeqquplMjAgMBAAECgYBHdKG5QKTw7Ix67Yhs7ZZB889HLc0hcnjll/HB7ZmMDgYDOQSpFd+nxO0JEpYFs9Uv5nyg9YPeuBcXgI/g0iGMiJ3z0QUTKeFKMoT+nJztZzJnwJkIvZlvg2O+GpdGIzcshshivy/4Nun9WNUf3BPafOghFwRfUpEiikBS60hqrQJBAO+8vo1sCksNmF7w5iTB++++uNnIY9Q25U/HRVGcVVFXS4e1cCmN70JXTw0To12whiPLbUaHmf4eoS0YB4+v498CQQDG5op6kMPWb0cyxdwKgoPaSbK6kG8Kzx84e7/CEeEGPZtZLvWvynf1pV1xb1w4vkH/I/fLfY03WRPDYLdRC9k9AkBEO8dMfyyq0fLFSvFmEuQ/B1ybd8KaGwnHXCMpqEJMRXlU5dpHqYMosarpQOaleuGi/Hpcsamtv1AGgqlcRyanAkEAtS334qHP4ptG7dcSE3jEBqmvZwZ+QAhf1iHTkUKcCgVAZ0LBhjoWf+r8bYIVLZMRpwnqhLB/XHWF7SlCk08JuQJAMIC6TMI82IPS648xvJYAqmZ6L2nBsDvSuTsPSvXnLWhcCzPv/qS+WaPcjBENqpZFr3YUhMP2nSCf255UBMtFPQ==";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6Q+CjGtujs6q6Pu491m/9vX26Bw89ja+hmP+tjpf34/Vcp572jRWRVXF/bGMePC06NEV/y5bw0qFqu69uSlhi4BHa3N/QEfGlP2wFc1EVsf2I5k7Jj46YTqRzIurMuGYV92Y/sPrCkt8u6HwCmTwtE7F9tpUDWyrZ83qqrqZTIwIDAQAB";
        RSA prrsa = new RSA(privateKey, null);
        RSA pursa = new RSA(null, publicKey);
        byte[] encrypt2 = prrsa.encrypt(StrUtil.bytes("123@", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        System.out.println(HexUtil.encodeHexStr(encrypt2));
        byte[] decrypt2 = pursa.decrypt(HexUtil.encodeHexStr(encrypt2), KeyType.PublicKey);
        System.out.println(StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
    }
}
