
import android.os.Handler;
import android.os.Looper;

/**
 * Android线程相关工具类
 */
public class ThreadUtils
{
    public ThreadUtils(){}

    /**
     * 判断当前是否为主线程
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 判断当前是否为子线程
     */
    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

    /**
     * 在主线程中运行
     */
    public static void runOnUiThread(Runnable action)
    {
        new Handler(Looper.getMainLooper()).post(action);
    }
}
