package httprequest.utils;

/**
 * HTTP状态代码处理
 */
public class HttpStatusCode
{

    private HttpStatusCode(){}

    public static String getMsgByCode(int code)
    {
        switch (code/100)
        {
            case 1:
                return getInformationalMsg(code);
            case 2:
                return getSuccessMsg(code);
            case 3:
                return getRedirectionMsg(code);
            case 4:
                return getClientErrorMsg(code);
            case 5:
            case 6:
                return getServerErrorMsg(code);
            default:
                return "未知状态";
        }
    }

    /**
     * HTTPCODE:[100,200)
     */
    private static String getInformationalMsg(int code)
    {
        switch (code)
        {
            case 100:
                return "请求已被接受，需要继续处理";
            case 101:
                return "需要切换协议完成请求";
            case 102:
                return "请求将被继续执行";
            default:
                return "请求已被接受，需要继续处理";
        }
    }

    /**
     * HTTPCODE:[200,300)
     */
    private static String getSuccessMsg(int code)
    {
        switch (code)
        {
            case 200:
                return "请求成功";
            case 201:
                return "请求成功并且服务器创建了新的资源";
            case 202:
                return "服务器已接受请求，但尚未处理,请等待";
            case 203:
                return "服务器已成功处理了请求，但返回的信息可能来自另一来源";
            case 204:
            case 205:
                return "请求成功，但无任何内容返回";
            case 206 :
                return "服务器成功处理了部分 GET 请求";
            default:
                return "请求成功";
        }
    }

    /**
     * HTTPCODE:[300,400)
     */
    private static String getRedirectionMsg(int code)
    {
        switch (code)
        {
            case 300:
                return "需要进行重定向";
            default:
                return "需要进行重定向";
        }
    }

    /**
     * HTTPCODE:[400,500)
     */
    private static String getClientErrorMsg(int code)
    {
        switch (code)
        {
            case 400:
                return "请求参数有误或语义错误，服务端无法解析";
            case 401:
            case 407:
                return "请求要求身份验证";
            case 403:
                return "服务器拒绝请求";
            case 404:
                return "请求地址未找到";
            case 405:
                return "请求行中指定的请求方法不能被用于请求相应的资源";
            case 406:
                return "请求的资源的内容特性无法满足请求头中的条件，因而无法生成响应实体";
            case 408:
                return "请求超时";
            case 409:
                return "请求数据失败，产生冲突";
            case 410:
                return "请求的数据不存在";

            default:
                return "客户端请求异常";
        }
    }

    /**
     * HTTPCODE:[500,700)
     */
    private static String getServerErrorMsg(int code)
    {
        switch (code)
        {
            case 500:
                return "服务器遇到了一个未曾预料的状况";
            case 501:
                return "服务器不具备完成请求的功能";
            case 502:
                return "服务器作为网关或代理，从上游服务器收到无效响应";
            case 503:
                return "服务器目前无法使用（由于超载或停机维护）";
            case 504:
                return "服务器作为网关或代理，但是没有及时从上游服务器收到请求";
            case 505:
                return "服务器不支持请求中所用的 HTTP 协议版本";
            case 507:
                return "服务器无法存储完成请求所必须的内容";
            case 509:
                return "服务器达到带宽限制";
            case 600:
                return "未返回响应头信息";
            default:
                return "服务器异常";
        }
    }


}
