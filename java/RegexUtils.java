import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具
 *
 * <p>常用修正模式：</p>
 * <p>Pattern.COMMENTS 忽略空白</p>
 * <p>Pattern.CASE_INSENSITIVE 忽略大小写</p>
 * <p>Pattern.DOTALL 让.匹配换行符</p>
 */
public class RegexUtils
{
    private RegexUtils() {}

    /**
     * 正则截取字符串
     * @param str   需要截取的字符串
     * @param regex 正则表达式
     * @return  不会返回原始字符串，所以截取下表可以从0开始。匹配失败不会返回空，但是可能长度为0。
     */
    public static String[] findOne(String str,String regex)
    {
        Matcher m = getMatcher(str,regex,0);
        return findOne(m);
    }

    /**
     * 正则截取字符串
     * @param str   需要截取的字符串
     * @param regex 正则表达式
     * @param flag  修正模式
     * @return  不会返回原始字符串，所以截取下表可以从0开始。匹配失败不会返回空，但是可能长度为0。
     */
    public static String[] findOne(String str,String regex,int flag)
    {
        Matcher m = getMatcher(str,regex,0);
        return findOne(m);
    }

    /**
     * 正则截取字符串
     * @param str   需要截取的字符串
     * @param regex 正则表达式
     * @return  不会返回原始字符串。匹配失败不会返回空，但集合大小为0
     */
    public static List<String[]> findAll(String str, String regex)
    {
        Matcher m = getMatcher(str,regex,0);
        return findAll(m);
    }

    /**
     * 正则截取字符串
     * @param str   需要截取的字符串
     * @param regex 正则表达式
     * @param flag  修正模式
     * @return  不会返回原始字符串。匹配失败不会返回空，但集合大小为0
     */
    public static List<String[]> findAll(String str, String regex,int flag)
    {
        Matcher m = getMatcher(str,regex,flag);
        return findAll(m);
    }

    private static String[] findOne(Matcher matcher)
    {
        String[] ret = new String[0];

        if(matcher.find())
        {
            ret = new String[matcher.groupCount()];
            for (int i = 0; i < ret.length; i++)
            {
                ret[i] = matcher.group(i+1);
            }
        }

        return ret;
    }

    private static List<String[]> findAll(Matcher matcher)
    {
        List<String[]> ret = new ArrayList<>();

        while (matcher.find())
        {
            String[] group = new String[matcher.groupCount()];
            for (int i = 0; i < group.length; i++)
            {
                group[i] = matcher.group(i+1);
            }
            ret.add(group);
        }
        return ret;
    }

    private static Matcher getMatcher(String str, String regex, int flags)
    {
        Pattern p = Pattern.compile(regex,flags);
        Matcher m = p.matcher(str);
        return m;
    }
}
