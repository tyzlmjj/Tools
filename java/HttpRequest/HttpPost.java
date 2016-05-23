package me.majiajie.httprequest.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpPost implements IRequest<HttpPost>
{
    String mUrl;
    Headers mHeaders;
    RequestBody mRequestBody;
    OkHttpClient mOkHttpClient;

    protected HttpPost(String url, OkHttpClient okHttpClient)
    {
        mUrl = url;
        mOkHttpClient = okHttpClient;
    }

    @Override
    public HttpPost head(Map<String, String> head)
    {
        mHeaders = Headers.of(head);
        return HttpPost.this;
    }

    @Override
    public HttpPost params(Map<String, String> params) {
        requestBodyBuild(
                HttpRequest.MEDIA_TYPE_DEFUALT,
                Utils.encodeParameters(params,HttpRequest.ENCODING_DEFAULT));
        return HttpPost.this;
    }

    public HttpPost params(String params) {
        requestBodyBuild(HttpRequest.MEDIA_TYPE_DEFUALT,params);
        return HttpPost.this;
    }

    public HttpPost params(MediaType mediaType, String params) {
        requestBodyBuild(mediaType,params);
        return HttpPost.this;
    }

    @Override
    public Result execute() {
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
                    try {
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

    private void requestBodyBuild(MediaType mediaType,String content)
    {
        mRequestBody = RequestBody.create(mediaType,content);
    }

    private Request buildRequest()
    {
        if(mHeaders != null)
        {
            return new Request.Builder().url(mUrl)
                    .post(mRequestBody == null? RequestBody.create(HttpRequest.MEDIA_TYPE_DEFUALT,""):mRequestBody)
                    .headers(mHeaders)
                    .build();
        }
        else
        {
            return new Request.Builder().url(mUrl)
                    .post(mRequestBody == null? RequestBody.create(HttpRequest.MEDIA_TYPE_DEFUALT,""):mRequestBody)
                    .build();
        }
    }


}