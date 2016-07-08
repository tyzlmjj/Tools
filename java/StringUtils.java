
import java.util.List;

public class StringUtils
{
    private StringUtils() {}

    /**
     * 添加分隔符拼接成字符串
     * @param separator 分隔符
     * @param list      字符集合
     * @return  当分隔符为"|" 集合有三个参数[aa,bb,cc] 返回
     *          "aa|bb|cc" .<p>如果集合为空返回空字符串</p>
     */
    public static String addSeparator(String separator, List<String> list)
    {
        if(list == null || list.size() == 0)
        {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (String str : list) {
            builder.append(str);
            builder.append(separator);
        }

        if(builder.length() > 0)
        {
            builder = builder.delete(builder.length()-1,builder.length());
        }

        return builder.toString();
    }
}
