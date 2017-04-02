import org.junit.Test;

/**
 * Created by mjj on 2017/3/24.
 * 编码工具
 */
public class CodingUtils {

    private CodingUtils(){}

    /**
     * 获取Unicode码
     */
    public static String getUnicode(String str){

        StringBuffer sb = new StringBuffer();

        for(int i=0;i<str.length();i++) {
            if(str.charAt(i)<256){//ASC11表中的字符码值不够4位,补00
                sb.append("\\u00");
            } else {
                sb.append("\\u");
            }
            sb.append(Integer.toHexString(str.charAt(i)));
        }

        return sb.toString();
    }

    /**
     * 机器内码转字符串
     */
    public static String toStr(int codePoint){
        return new String(Character.toChars(codePoint));
    }

}
