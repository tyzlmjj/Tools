
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Drawable 工具类
 */
public class DrawableUtil
{
    /**
     * 将Drawable转换成Bitmap
     * @param context   上下文
     * @param drawable  {@link Drawable}
     * @return {@link Bitmap}
     */
    public static Bitmap toBitmap(@NonNull Context context,@DrawableRes int drawable)
    {
        return toBitmap(ContextCompat.getDrawable(context,drawable));
    }

    /**
     * 将Drawable转换成Bitmap
     * @param drawable  {@link Drawable}
     * @return {@link Bitmap}
     */
    public static Bitmap toBitmap(@NonNull Drawable drawable)
    {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Drawable转换成Bitmap并缩放到制定的宽高
     * @param context   上下文
     * @param drawable  {@link Drawable}
     * @param newWidth  指定宽度PX
     * @param newHeight 指定高度PX
     * @return  {@link Bitmap}
     */
    public static Bitmap toBitmapAndScale(@NonNull Context context,@DrawableRes int drawable,float newWidth,float newHeight)
    {
        return toBitmapAndScale(ContextCompat.getDrawable(context,drawable),newWidth,newHeight);
    }

    /**
     * 将Drawable转换成Bitmap并缩放到制定的宽高
     * @param drawable  {@link Drawable}
     * @param newWidth  指定宽度PX
     * @param newHeight 指定高度PX
     * @return  {@link Bitmap}
     */
    public static Bitmap toBitmapAndScale(@NonNull Drawable drawable,float newWidth,float newHeight)
    {
        int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();

        Bitmap oldbmp = toBitmap(drawable);

        Matrix matrix = new Matrix();
        float scaleWidth = (newWidth/ (float)width);
        float scaleHeight = (newHeight/ (float)height);

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        oldbmp.recycle();

        return newbmp;
    }

    /**
     * Drawable 染色
     * @param drawable  {@link Drawable}
     * @param color     颜色值
     * @return  染色后的Drawable
     */
    public static Drawable Tint(@NonNull Drawable drawable,int color)
    {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
}
