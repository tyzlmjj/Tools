package me.majiajie.httprequest.http;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class Utils
{
    private Utils(){}

    /**
     * 对数据进行编码，并转成HTTP传递参数的格式
     * @param params            键值对参数
     * @param paramsEncoding    编码
     * @return  转换完成的字符串
     */
    public static String encodeParameters(Map<String, String> params, String paramsEncoding)
    {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.delete(encodedParams.length()-1,encodedParams.length()).toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
}
