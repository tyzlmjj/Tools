package me.majiajie.httprequest.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpGet implements IRequest<HttpGet>
{
    String mUrl;
    Headers mHeaders;
    OkHttpClient mOkHttpClient;

    protected HttpGet(String url, OkHttpClient okHttpClient)
    {
        mUrl = url;
        mOkHttpClient = okHttpClient;
    }

    @Override
    public HttpGet head(Map<String, String> head)
    {
        mHeaders = Headers.of(head);
        return HttpGet.this;
    }

    @Override
    public HttpGet params(Map<String, String> params)
    {
        mUrl = mUrl + "?" + Utils.encodeParameters(params,HttpRequest.ENCODING_DEFAULT);
        return HttpGet.this;
    }

    @Override
    public Result execute()
    {
        Result result = new Result();
        try
        {
            Response response = mOkHttpClient.newCall(buildRequest()).execute();
            if(response.code() == 200)
            {
                result.setBody(response.body().string());
                result.setCode(response.code());
                result.setMessage("request succeed !");
            }
            else
            {
                result.setCode(response.code());
                result.setMessage(response.message());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result.setCode(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public void enqueue(final HttpCallBack callBack) {

        mOkHttpClient.newCall(buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(call.isCanceled())
                {
                    callBack.onError(-2,"request is canceled :" + e.getMessage());
                }
                else{
                    callBack.onError(-1,e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response){
                if(response.code() == 200)
                {
                    try{
                        callBack.onResponse(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callBack.onError(-1,e.getMessage());
                    }
                }
                else
                {
                    callBack.onError(response.code(),response.message());
                }
            }
        });
    }

    Request buildRequest()
    {
        if(mHeaders != null)
        {
            return new Request.Builder().url(mUrl)
                    .headers(mHeaders)
                    .build();
        }
        else
        {
            return new Request.Builder().url(mUrl).build();
        }
    }
}
