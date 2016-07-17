package http.progress;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * 进度回调辅助类
 */
public class ProgressHelper {
    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @param client 待包装的OkHttpClient
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient，使用clone方法返回
     */
    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final ProgressResponseBody.DownloadProgressListener progressListener){
        OkHttpClient clone = client.newBuilder()
                //增加拦截器
                .addNetworkInterceptor(new Interceptor()
                {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                    //拦截
                    Response originalResponse = chain.proceed(chain.request());
                    //包装响应体并返回
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                    }
                })
                .build();
        return clone;
    }

    /**
     * 包装请求体用于上传文件的回调
     * @param requestBody 请求体RequestBody
     * @param progressRequestListener 进度回调接口
     * @return 包装后的进度回调请求体
     */
    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody, ProgressRequestBody.UpLoadProgressListener progressRequestListener)
    {
        return new ProgressRequestBody(requestBody,progressRequestListener);
    }

}

