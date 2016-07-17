package http.request;

import okhttp3.Headers;
import okhttp3.OkHttpClient;

import java.util.Map;


public class HttpGet extends AbsRequest
{
    public HttpGet(String url, OkHttpClient okHttpClient)
    {
        super(url, okHttpClient);
    }

    public HttpGet headers(Map<String, String> head)
    {
        mRequestBuild.headers(Headers.of(head));
        return HttpGet.this;
    }

    public HttpGet addHeader(String name, String value)
    {
        mRequestBuild.addHeader(name,value);
        return HttpGet.this;
    }
}
