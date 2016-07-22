package httprequest.request;



import httprequest.HttpRequest;
import httprequest.Result;
import httprequest.callback.HttpCallBack;
import httprequest.progress.ProgressHelper;
import httprequest.progress.ProgressRequestBody;
import httprequest.progress.ProgressResponseBody;
import httprequest.utils.HttpStatusCode;
import okhttp3.*;
import reflect.MD5Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                result.setMessage("请求成功!");
            }
            else
            {
                result.setCode(response.code());
                result.setMessage(HttpStatusCode.getMsgByCode(response.code()));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result.setCode(-1);
            result.setMessage("读取服务端返回数据失败");
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
                    callBack.onError(-2,"请求被取消");
                }
                else{
                    callBack.onError(-1,"请求失败");
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
                        callBack.onError(-1,"读取服务端返回数据失败");
                    }
                }
                else
                {
                    callBack.onError(response.code(),HttpStatusCode.getMsgByCode(response.code()));
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
        final String UserId = "A6971118873561";
        final String UserPassword = UserId + "UZ" + "8C757B31-A896-F477-C46D-4E27E05528D3" + "UZ";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //验证所需信息
        final String CurrentTime = sdf.format(new Date());
//        final String CurrentTime = "2016-07-20 9:55:30";

        if(this instanceof HttpPost)
        {
            mRequestBuild.post(mRequestBody)
                    .addHeader("UserId",UserId)
                    .addHeader("UserPassword", MD5Utils.getMD5(UserPassword+CurrentTime))
                    .addHeader("CurrentTime",CurrentTime);

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
