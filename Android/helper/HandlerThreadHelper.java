
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

public class HandlerThreadHelper
{
    private Handler mHandler;

    private HandlerThread mHandlerThread;

    public HandlerThreadHelper(String tag)
    {
        mHandlerThread = new HandlerThread(tag);
    }

    /**
     * 停止背景线程
     */
    public void stopBackgroundThread()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            mHandlerThread.quitSafely();
        }
        else
        {
            mHandlerThread.quit();
        }
        try {
            mHandlerThread.join();
            mHandlerThread = null;
            mHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启背景线程
     */
    public Handler startBackgroundThread()
    {
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        return mHandler;
    }


}
