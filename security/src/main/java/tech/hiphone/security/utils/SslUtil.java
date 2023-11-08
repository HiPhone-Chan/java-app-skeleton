package tech.hiphone.security.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

public class SslUtil {

    public static HttpClient sslRequestFactory(String certPath, String pwd) {
        try (FileInputStream instream = new FileInputStream(new File(certPath))) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(instream, pwd.toCharArray());
            SSLContext sslcontext = SSLContextBuilder.create().loadKeyMaterial(keyStore, pwd.toCharArray()).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
                    null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static HttpClient sslRequestFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            HttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
