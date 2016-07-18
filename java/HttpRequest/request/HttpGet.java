package httprequest.request;

import okhttp3.Headers;
import okhttp3.OkHttpClient;

import java.util.Map;


public class HttpGet extends AbsRequest
{
    public HttpGet(String url, OkHttpClient okHttpClient)
    {
        super(url, okHttpClient);
    }

    /**
     * 请求头参数
     */
    public HttpGet headers(Map<String, String> head)
    {
        mRequestBuild.headers(Headers.of(head));
        return HttpGet.this;
    }

    /**
     * 添加请求头参数
     */
    public HttpGet addHeader(String name, String value)
    {
        mRequestBuild.addHeader(name,value);
        return HttpGet.this;
    }

    /**
     * 连接超时时间，单位毫秒
     */
    public HttpGet connectTimeout(long connectTimeout)
    {
        mConnectTimeout = connectTimeout;
        return this;
    }

    /**
     * 读入超时时间，单位毫秒
     */
    public HttpGet readTimeout(long readTimeout)
    {
        mReadTimeout = readTimeout;
        return this;
    }

    /**
     * 写出超时时间，单位毫秒
     */
    public HttpGet writeTimeout(long writeTimeout)
    {
        mWriteTimeout = writeTimeout;
        return this;
    }
}
