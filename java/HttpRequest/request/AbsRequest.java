package http.request;


import http.Result;
import http.callback.HttpCallBack;
import http.progress.ProgressHelper;
import http.progress.ProgressRequestBody;
import http.progress.ProgressResponseBody;
import okhttp3.*;

import java.io.IOException;


abstract class AbsRequest
{
    String mUrl;

    Request.Builder mRequestBuild;

    RequestBody mRequestBody;

    ProgressRequestBody.UpLoadProgressListener mUpLoadProgressListener;

    ProgressResponseBody.DownloadProgressListener mDownloadProgressListener;

    private OkHttpClient mOkHttpClient;

    AbsRequest(String url, OkHttpClient okHttpClient)
    {
        mUrl = url;
        mOkHttpClient = okHttpClient;

        mRequestBuild = new Request.Builder();
        mRequestBuild.url(mUrl);
    }

    public Result execute()
    {
        Result result = new Result();

        if(mUpLoadProgressListener != null)
        {
            mRequestBody = ProgressHelper.addProgressRequestListener(mRequestBody,mUpLoadProgressListener);
        }

        if(mDownloadProgressListener != null)
        {
            mOkHttpClient = ProgressHelper.addProgressResponseListener(mOkHttpClient,mDownloadProgressListener);
        }

        if(this instanceof HttpPost)
        {
            mRequestBuild.post(mRequestBody);
        }

        try
        {
            Response response = mOkHttpClient.newCall(mRequestBuild.build()).execute();
            if(response.isSuccessful())
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
            result.setMessage("IOException: " + "read data error");
        }
        return result;
    }

    public void enqueue(final HttpCallBack callBack)
    {
        if(mUpLoadProgressListener != null)
        {
            mRequestBody = ProgressHelper.addProgressRequestListener(mRequestBody,mUpLoadProgressListener);
        }

        if(mDownloadProgressListener != null)
        {
            mOkHttpClient = ProgressHelper.addProgressResponseListener(mOkHttpClient,mDownloadProgressListener);
        }

        if(this instanceof HttpPost)
        {
            mRequestBuild.post(mRequestBody);
        }

        mOkHttpClient.newCall(mRequestBuild.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
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
                if(response.isSuccessful())
                {
                    try
                    {
                        callBack.onResponse(response.body().string());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        callBack.onError(-1,"IOException: " + "read data error");
                    }
                }
                else
                {
                    callBack.onError(response.code(),response.message());
                }
            }
        });
    }
}
