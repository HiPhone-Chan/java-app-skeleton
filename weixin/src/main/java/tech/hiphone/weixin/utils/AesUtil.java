package tech.hiphone.weixin.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;

public class AesUtil {

    public static final int KEY_LENGTH_BYTE = 32;
    public static final int TAG_LENGTH_BIT = 128;
    private SecretKeySpec key;

    public AesUtil() {
    }

    public AesUtil(String key) {
        this.init(key);
    }

    public void init(String aesKeyStr) {
        byte[] aesKey = aesKeyStr.getBytes();
        if (aesKey.length != KEY_LENGTH_BYTE) {
            throw new IllegalArgumentException("无效的ApiV3Key，长度必须为32个字节");
        }
        key = new SecretKeySpec(aesKey, "AES");
    }

    public String decryptToString(String associatedData, String nonce, String base64Ciphertext) {
        try {
            return decryptToString(associatedData.getBytes(), nonce.getBytes(),
                    Base64.getDecoder().decode(base64Ciphertext));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Ciphertext;
    }

    public String decryptToString(byte[] associatedData, byte[] nonce, byte[] ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData);

            return new String(cipher.doFinal(ciphertext), "utf-8");
        } catch (Exception e) {
            throw new ServiceException(ErrorCodeContants.DECODE_FAIL);
        }
    }
}
