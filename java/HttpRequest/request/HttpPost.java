package httprequest.request;

import httprequest.MT;
import httprequest.progress.ProgressRequestBody;
import httprequest.progress.ProgressResponseBody;
import httprequest.utils.Utils;
import okhttp3.*;

import java.util.Map;


public class HttpPost extends AbsRequest
{
    public HttpPost(String url, OkHttpClient okHttpClient) {
        super(url, okHttpClient);
        mRequestBody = RequestBody.create(MT.MEDIA_TYPE_DEFUALT, "");
    }

    public HttpPost headers(Map<String, String> head)
    {
        mRequestBuild.headers(Headers.of(head));
        return HttpPost.this;
    }

    public HttpPost addHeader(String name, String value)
    {
        mRequestBuild.addHeader(name,value);
        return HttpPost.this;
    }



    public HttpPost params(Map<String, String> params) {
        mRequestBody = RequestBody.create(MT.MEDIA_TYPE_DEFUALT, Utils.encodeParameters(params,"UTF-8"));
        return HttpPost.this;
    }

    public HttpPost params(String params) {
        mRequestBody = RequestBody.create(MT.MEDIA_TYPE_DEFUALT,params);
        return HttpPost.this;
    }

    public HttpPost params(byte[] params) {
        mRequestBody = RequestBody.create(MT.MEDIA_TYPE_DEFUALT,params);
        return HttpPost.this;
    }

    public HttpPost params(MediaType mediaType, String params)
    {
        mRequestBody = RequestBody.create(mediaType,params);
        return HttpPost.this;
    }

    /**
     * 多类型参数上传,例子：
     * <p>
     * MultipartBody multipartBody = new MultipartBody.Builder()
     *                  .setType(MultipartBody.FORM)
     *                  .addFormDataPart("title", "Square Logo")
     *                  .addFormDataPart("image", "logo-square.png",RequestBody.create(MT.MEDIA_TYPE_PNG, new File("logo-square.png")))
     *                  .build();
     * </p>
     */
    public HttpPost params(MultipartBody multipartBody)
    {
        mRequestBody = multipartBody;
        return HttpPost.this;
    }



    public HttpPost uploadListener(ProgressRequestBody.UpLoadProgressListener listener)
    {
        mUpLoadProgressListener = listener;
        return HttpPost.this;
    }

    public HttpPost downloadListerner(ProgressResponseBody.DownloadProgressListener listener)
    {
        mDownloadProgressListener = listener;
        return HttpPost.this;
    }



    /**
     * 连接超时时间，单位毫秒
     */
    public HttpPost connectTimeout(long connectTimeout)
    {
        mConnectTimeout = connectTimeout;
        return this;
    }

    /**
     * 读入超时时间，单位毫秒
     */
    public HttpPost readTimeout(long readTimeout)
    {
        mReadTimeout = readTimeout;
        return this;
    }

    /**
     * 写出超时时间，单位毫秒
     */
    public HttpPost writeTimeout(long writeTimeout)
    {
        mWriteTimeout = writeTimeout;
        return this;
    }




}