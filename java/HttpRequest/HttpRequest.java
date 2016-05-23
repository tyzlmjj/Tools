package me.majiajie.httprequest.http;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * 进行Http请求
 * 添加依赖
 * compile 'com.squareup.okhttp3:okhttp:3.2.0'
 */
public class HttpRequest
{
    private static final OkHttpClient mOkHttpClient;

    protected static final MediaType MEDIA_TYPE_DEFUALT = MediaType.parse("application/x-www-form-urlencoded");

    protected static final String ENCODING_DEFAULT = "UTF-8";

    private static final int TIME_OUT = 10_000;//毫秒

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

}
