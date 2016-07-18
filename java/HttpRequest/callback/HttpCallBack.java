package httprequest.callback;

/**
 * Http回调
 */
public interface HttpCallBack {

    /**
     * 请求成功, code:[200,300)
     * @param result 返回内容
     */
    void onResponse(String result);

    /**
     * 发生异常
     * @param code  HttpErrorCode
     * @param message 错误信息
     */
    void onError(int code, String message);
}
