
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Https相关工具类
 */
public class HttpsUtil
{
    /**
     * 将Keytool生成的.crt证书导入
     */
    public static SSLSocketFactory getSslSocketFactory
    (InputStream certificates,InputStream key,String keyPassword)
    {
        SSLContext sslContext = null;

        Certificate ca = getCertificate(certificates);
        if(ca == null){return null;}

        try
        {
            TrustManagerFactory tmf = getTrustManagerFactory(ca,key,keyPassword);

            // Create an SSLContext that uses our TrustManager
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return sslContext != null? sslContext.getSocketFactory():null;
    }

    /**
     * 获得证书实体
     * @param certificate 证书
     * @return  {@link Certificate},如果输入流错误就返回null
     */
    private static Certificate getCertificate(InputStream certificate)
    {
        Certificate ca = null;
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try {
                ca = certificateFactory.generateCertificate(certificate);
            } finally {
                certificate.close();
            }
        }
        catch (CertificateException | IOException e)
        {
            e.printStackTrace();
        }

        return ca;
    }

    /**
     * 获取{@link TrustManagerFactory}（信任证书管理）
     * @return {@link TrustManagerFactory},如果输入流错误就返回null
     */
    private static TrustManagerFactory getTrustManagerFactory
    (Certificate ca,InputStream key,String keyPassword)
    {
        TrustManagerFactory tmf = null;
        try {
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();

            KeyStore keyStore = KeyStore.getInstance(keyStoreType);

            keyStore.load(key, keyPassword.toCharArray());
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tmf;
    }
}
