package httprequest.request;



import httprequest.HttpRequest;
import httprequest.Result;
import httprequest.callback.HttpCallBack;
import httprequest.progress.ProgressHelper;
import httprequest.progress.ProgressRequestBody;
import httprequest.progress.ProgressResponseBody;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


abstract class AbsRequest
{
    String mUrl;

    Request.Builder mRequestBuild;

    RequestBody mRequestBody;

    ProgressRequestBody.UpLoadProgressListener mUpLoadProgressListener;

    ProgressResponseBody.DownloadProgressListener mDownloadProgressListener;

    long mConnectTimeout = 0;

    long mReadTimeout = 0;

    long mWriteTimeout = 0;

    OkHttpClient mOkHttpClient;

    AbsRequest(String url, OkHttpClient okHttpClient)
    {
        mUrl = url;
        mOkHttpClient = okHttpClient;

        mRequestBuild = new Request.Builder();
        mRequestBuild.url(mUrl);
    }

    public Result execute()
    {
        warp();

        Result result = new Result();

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
        warp();

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

    private void warp()
    {
        warpListener();

        warpRequest();

        warpClient();
    }

    private void warpListener()
    {
        if(mUpLoadProgressListener != null)
        {
            mRequestBody = ProgressHelper.addProgressRequestListener(mRequestBody,mUpLoadProgressListener);
        }

        if(mDownloadProgressListener != null)
        {
            mOkHttpClient = ProgressHelper.addProgressResponseListener(mOkHttpClient,mDownloadProgressListener);
        }
    }

    private void warpRequest()
    {
        if(this instanceof HttpPost)
        {
            mRequestBuild.post(mRequestBody);
        }
    }

    private void warpClient()
    {
        if( mConnectTimeout > 0 || mReadTimeout > 0 || mWriteTimeout > 0)
        {
            mOkHttpClient = mOkHttpClient.newBuilder()
                .connectTimeout(mConnectTimeout !=0 ?mConnectTimeout:HttpRequest.getDefaultTimeout(),TimeUnit.MILLISECONDS)
                .readTimeout(mReadTimeout !=0 ?mReadTimeout:HttpRequest.getDefaultTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(mWriteTimeout !=0 ?mWriteTimeout:HttpRequest.getDefaultTimeout(), TimeUnit.MILLISECONDS)
                .build();
        }
    }
}
