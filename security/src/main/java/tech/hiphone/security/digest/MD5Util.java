package tech.hiphone.security.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tech.hiphone.security.utils.HexUtil;

public final class MD5Util {

    private MD5Util() {
    }

    public static String encode(String message) {
        return message == null ? null : HexUtil.byte2Hexstring(encode(message.getBytes()));
    }

    public static byte[] encode(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(data);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

}
