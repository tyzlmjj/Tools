package me.majiajie.https;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 屏幕窗口工具类
 */
public class WindowUtil
{
    private WindowUtil(){}

    /**
     * 获取屏幕高度PX
     */
    public static int getHeight(Activity activity)
    {
        return getDisplayMetrics(activity).heightPixels;
    }

    /**
     * 获取屏幕宽度PX
     */
    public static int getWidth(Activity activity)
    {
        return getDisplayMetrics(activity).widthPixels;
    }

    /**
     * 获取屏幕宽度和高度,[0]宽，[1]高
     */
    public static int[] getWidthAndHeight(Activity activity)
    {
        return new int[]{getDisplayMetrics(activity).widthPixels,
                getDisplayMetrics(activity).heightPixels};
    }

    private static DisplayMetrics getDisplayMetrics(Activity activity)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }


}
