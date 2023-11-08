package tech.hiphone.commons.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public final class NetUtil {

    public static final String LOCALHOST_IPV4 = "127.0.0.1";
    public static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    public static final String IP_HEADER = "x-forwarded-for";

    private NetUtil() {
    }

    public static String getClientIP(HttpServletRequest req) {
        String ip = req.getHeader(IP_HEADER);

        if (StringUtils.isEmpty(ip)) {
            ip = req.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ip) || LOCALHOST_IPV6.equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                }
            }
        }

        if (StringUtils.isEmpty(ip)) {
            ip = LOCALHOST_IPV4;
        }
        return ip;
    }

    public static boolean isLocalPortUsed(int port) {
        try {
            return isPortUsed(LOCALHOST_IPV4, port);
        } catch (UnknownHostException e) {
        }
        return false;
    }

    public static boolean isPortUsed(String host, int port) throws UnknownHostException {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (IOException e) {
        }
        return false;
    }
}
