package httprequest;


import httprequest.request.HttpGet;
import httprequest.request.HttpPost;
import httprequest.utils.HttpsUtils;
import okhttp3.OkHttpClient;
import okhttp3.internal.Platform;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class HttpsRequest
{
    private static OkHttpClient mOkHttpClient = HttpRequest.getHttpClient();

    private static final int TIME_OUT = 10_000;//毫秒

    /**
     * 初始化,单向验证
     */
    public static void initialization(InputStream certificates) throws FileNotFoundException
    {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(certificates);
        X509TrustManager trustManager = Platform.get().trustManager(sslSocketFactory);
        initialization(sslSocketFactory,trustManager);
    }

    /**
     * 初始化,双向认证
     */
    public static void initialization(InputStream certificates, InputStream key, String keyPassword) throws FileNotFoundException
    {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(certificates,key,keyPassword);
        X509TrustManager trustManager = Platform.get().trustManager(sslSocketFactory);
        initialization(sslSocketFactory,trustManager);
    }

    private static void initialization(SSLSocketFactory sslSocketFactory,X509TrustManager trustManager)
    {
        mOkHttpClient =  mOkHttpClient.newBuilder()
                .sslSocketFactory(sslSocketFactory,trustManager)
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private HttpsRequest(){}

    public static HttpGet doGet(String url)
    {
        return new HttpGet(url,mOkHttpClient);
    }

    public static HttpPost doPost(String url)
    {
        return new HttpPost(url,mOkHttpClient);
    }
}
