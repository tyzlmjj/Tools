
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.media.VolumeProviderCompat;
import android.util.TypedValue;

/**
 * 获取自定义属性值<p>
 * 基础值都可以通过{@link TypedValue}的coerceToString()方法输出<p>
 * 自定义属性集合（declare-styleable）的操作可以使用{@link TypedArrayUtils}
 */
public class AttrUtils
{
    /**
     * 获取单个自定义属性的TypedValue
     * @param context   上下文
     * @param attrRes   attrid
     * @return  {@link TypedValue}
     */
    public static TypedValue getTypedValue(@NonNull Context context, @VolumeProviderCompat.ControlType int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue;
    }

    /**
     * 获取字符串
     * @param context   上下文
     * @param attrRes   attrid
     * @return  String类型内容
     */
    public static String getString(@NonNull Context context, @VolumeProviderCompat.ControlType int attrRes)
    {
        return getTypedValue(context,attrRes).coerceToString().toString();
    }

    /**
     * 获取boolean类型值
     * @param context   上下文
     * @param attrRes   attrid
     * @return  boolean类型值
     */
    public static boolean getBoolean(@NonNull Context context, @VolumeProviderCompat.ControlType int attrRes)
    {
        if("true".equals(getTypedValue(context,attrRes).coerceToString()))
        {
            return true;
        }
        return false;
    }




    /**
     * 获取资源ID
     * @param context   上下文
     * @param attrRes   attrid
     * @return  ResourceId
     */
    public static int getResourceId(@NonNull Context context, @VolumeProviderCompat.ControlType int attrRes)
    {
        return getTypedValue(context,attrRes).resourceId;
    }

    /**
     * 获取Resource类型指向的颜色值
     * @param context   上下文
     * @param attrRes   attrid
     * @return  color
     */
    public static int getResColor(@NonNull Context context, @VolumeProviderCompat.ControlType int attrRes)
    {
        return ContextCompat.getColor(context,getResourceId(context,attrRes));
    }

    /**
     * 获取Resource类型指向的Drawable
     * @param context   上下文
     * @param attrRes   attrid
     * @return  {@link Drawable}
     */
    public static Drawable getResDrawable(@NonNull Context context, @VolumeProviderCompat.ControlType int attrRes)
    {
        return ContextCompat.getDrawable(context,getResourceId(context,attrRes));
    }
}
