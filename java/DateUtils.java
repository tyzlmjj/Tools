import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间日期处理
 */
public class DateUtils
{

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     * @return 输出格式 {@link #FORMAT_DEFAULT}
     */
    public static String getDate()
    {
        return format(Calendar.getInstance().getTime(),FORMAT_DEFAULT);
    }

    /**
     * 获取东八区时间(北京时间)
     * @return 输出格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getChinaDate()
    {
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar calendar = Calendar.getInstance(tz,Locale.CHINA);
        StringBuilder date = new StringBuilder();

        date.append(calendar.get(Calendar.YEAR));
        date.append("-");
        date.append(calendar.get(Calendar.MONTH)+1);//月份从0开始所以需要加1
        date.append("-");
        date.append(calendar.get(Calendar.DAY_OF_MONTH));
        date.append(" ");
        date.append(calendar.get(Calendar.HOUR_OF_DAY));
        date.append(":");
        date.append(calendar.get(Calendar.MINUTE));
        date.append(":");
        date.append(calendar.get(Calendar.SECOND));

        return date.toString();
    }

    /**
     * 格式化时间
     * @param date {@link Date}
     * @param formatStr 如 yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date,String formatStr)
    {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(date);
    }

    /**
     * 将时间日期字符串转换为Date
     * @param date      要转换的时间字符串
     * @param formatStr 字符串格式，如 yyyy-MM-dd HH:mm:ss
     * @return  {@link Date}
     * @throws ParseException
     */
    public static Date stringToDate(String date,String formatStr) throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.parse(date);
    }

    /**
     * 设置默认时区，影响全局
     * @param id 时区ID,可以通过{@link TimeZone#getAvailableIDs()}遍历所有支持的ID，
     *           也可以输入这样的 "GMT+8"
     */
    public static void setDefaultTimeZone(String id)
    {
        TimeZone.setDefault(TimeZone.getTimeZone(id));
    }
}
