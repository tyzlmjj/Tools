package me.majiajie.qrcode;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * MJJ
 */
public class MyNameDrawable extends Drawable
{
    //宽高
    private float mWidth;
    private float mHeight;

    //文字高度
    private float mContentHeight;

    //起点
    private float mStart_x;
    private float mStart_y;

    //内边距
    private float mPadding;

    private Path mPath;

    private Paint mPaint;

    public MyNameDrawable(@ColorInt int color,float height)
    {
        mWidth = height/24f*50f;
        mHeight = height;
        mPadding = height/6f;

        mStart_x = mPadding;
        mStart_y = mHeight-mPadding;

        mContentHeight = mHeight - mPadding*2;

        float one_px = mHeight/24f;
        float two_px = mHeight/12f;

        //路径M
        mPath = new Path();
        mPath.moveTo(mStart_x, mStart_y);
        mPath.lineTo(mStart_x+mContentHeight/2,mPadding);
        mPath.lineTo(mStart_x+mContentHeight/2+one_px,mStart_y);
        mPath.lineTo(mStart_x+mContentHeight+one_px,mPadding);
        mPath.lineTo(mStart_x+mContentHeight+two_px,mStart_y);
        //J
        mPath.moveTo(mStart_x+mContentHeight+two_px + mPadding + mContentHeight/2f, mPadding);
        mPath.quadTo(mStart_x+mContentHeight+two_px + mPadding + mContentHeight/2f,mHeight+one_px,
                mStart_x+mContentHeight+two_px + mPadding,mStart_y - two_px);
        //J
        mPath.moveTo(mStart_x+mContentHeight+two_px + mPadding*2 + mContentHeight, mPadding);
        mPath.quadTo(mStart_x+mContentHeight+two_px + mPadding*2 + mContentHeight,mHeight+one_px,
                mStart_x+mContentHeight+two_px + mPadding*2 + mContentHeight/2f,mStart_y - two_px);

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStrokeCap(Paint.Cap.ROUND);//直线头尾圆滑
        mPaint.setStrokeJoin(Paint.Join.ROUND);//直线交界处圆滑处理
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(height/12);

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
