package tech.hiphone.security.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tech.hiphone.security.utils.HexUtil;

public final class Sha1Util {

    private Sha1Util() {
    }

    public static String encode(String data) {
        return data == null ? null : HexUtil.byte2Hexstring(encode(data.getBytes()));
    }

    public static byte[] encode(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(data);

            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

}
