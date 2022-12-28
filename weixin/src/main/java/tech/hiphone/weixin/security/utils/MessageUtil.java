package tech.hiphone.weixin.security.utils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import tech.hiphone.commons.utils.MessageDigestUtils;

public final class MessageUtil {

    private static Charset CHARSET = Charset.forName("utf-8");

    public static DecodeInfo aesDecrypt(String encodingAesKey, String text) {
        byte[] aesKey = Base64.getDecoder().decode(encodingAesKey);
        String xmlContent = null;
        String appId = null;
        try {
            // aes begin
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
            byte[] encrypted = Base64.getDecoder().decode(text);
            byte[] original = cipher.doFinal(encrypted);
            // aes end

            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

            int xmlLength = recoverNetworkBytesOrder(networkOrder);

            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            appId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DecodeInfo(appId, xmlContent);
    }

    public static String sha1Encode(String... params) {
        Arrays.sort(params);
        String sign = String.join("", params);
        return MessageDigestUtils.sha1Encode(sign);
    }

    private static int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }
}
