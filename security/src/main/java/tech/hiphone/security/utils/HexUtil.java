package tech.hiphone.security.utils;

public class HexUtil {

    public static String byte2Hexstring(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int i = b & 0XFF;
            if (i < 0x10) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }
}
