

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;


/**
 * 参考博客：
 * http://www.jianshu.com/p/49e7292a2911
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0120/2334.html
 */
public class HexagonDrawable extends Drawable
{
    //宽高
    private float mWidth;
    private float mHeight;

    //六边形边长
    private float mLenght;

    //原点，这里是中心点
    private float mOrigin_x;
    private float mOrigin_y;

    //内边距
    private float mPadding;

    private Path mPath;

    private Paint mPaint;

    public HexagonDrawable(@ColorInt int color, float width, float height, float padding)
    {
        mWidth = width;
        mHeight = height;
        mPadding = padding;

        mLenght = width/2 - mPadding*2;

        mOrigin_x = mWidth/2;
        mOrigin_y = mHeight/2;

        //六边形路径
        mPath = new Path();
        mPath.moveTo(mOrigin_x, mOrigin_y - mLenght);
        mPath.lineTo((float) (mOrigin_x + Math.sqrt(3f)*mLenght/2), mOrigin_y - mLenght/2);
        mPath.lineTo((float) (mOrigin_x + Math.sqrt(3f)*mLenght/2), mOrigin_y + mLenght/2);
        mPath.lineTo(mOrigin_x, mOrigin_y + mLenght);
        mPath.lineTo((float) (mOrigin_x - Math.sqrt(3f)*mLenght/2), mOrigin_y + mLenght/2);
        mPath.lineTo((float) (mOrigin_x - Math.sqrt(3f)*mLenght/2), mOrigin_y - mLenght/2);
        mPath.close();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1f);
        //连线节点平滑处理
        PathEffect pathEffect = new CornerPathEffect(10);
        mPaint.setPathEffect(pathEffect);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
