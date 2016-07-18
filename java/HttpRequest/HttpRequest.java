package httprequest;

import httprequest.request.HttpGet;
import httprequest.request.HttpPost;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * 进行Http请求
 * 添加依赖
 * compile 'com.squareup.okhttp3:okhttp:3.3.1'
 */
public class HttpRequest
{
    private static final OkHttpClient mOkHttpClient;

    private static final long TIME_OUT = 10_000;//毫秒

    static {
        mOkHttpClient =  new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private HttpRequest(){}

    public static HttpGet doGet(String url)
    {
        return new HttpGet(url,mOkHttpClient);
    }

    public static HttpPost doPost(String url)
    {
        return new HttpPost(url,mOkHttpClient);
    }

    public static OkHttpClient getHttpClient()
    {
        return mOkHttpClient;
    }

    public static long getDefaultTimeout()
    {
        return TIME_OUT;
    }

}
