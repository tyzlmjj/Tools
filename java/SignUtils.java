
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签名生成
 * 参考：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
 */
public class SignUtils {

    private SignUtils(){}

    /**
     * 生成签名字符串
     *
     * @param params    参数
     * @param k         密钥
     */
    public static <V> String sign(Map<String, V> params, String k){
        String signStr = createLinkString(params,k);
        return MD5Encode(signStr,"UTF-8").toUpperCase();
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static <V> String createLinkString(Map<String, V> params, String k) {
        Map<String, V> newParams = paraFilter(params);
        List<String> keys = new ArrayList<>(newParams.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = newParams.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr+"&key="+k;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static <V> Map<String, V> paraFilter(Map<String, V> sArray) {

        Map<String, V> result = new HashMap<>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            V value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * MD5编码
     * @param origin        要编码的字符串
     * @param charsetname   字符集
     */
    private static String MD5Encode(String origin, String charsetname) {
        String resultString = origin;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
