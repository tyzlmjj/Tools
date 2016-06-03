
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

public class HexagonProgressDrawable extends Drawable implements Animatable {


    private static final float sqrt_3 = (float) Math.sqrt(3);

    private static final long ANIM_TIME = 2_000;//动画周期时间

    private static final long FRAME_DELAY = 30;//动画帧间隔

    private static final long WIDTH_DEFAULT = 200;//默认大小

    private static final int COLOR_DEFAULT = Color.GRAY;//默认颜色

    private ValueAnimator mAnimator;

    //宽高相等，只保存宽
    private float mWidth;

    //颜色
    private int mColor;

    //间距
    private float mPadding;

    //原点，这里是中心点
    private float mOrigin_x;
    private float mOrigin_y;

    //六边形边长
    private float mHexgon_lenght;

    private Paint mPaint;

    private List<Hexagon> mHexagons;

    public HexagonProgressDrawable()
    {
        mWidth = WIDTH_DEFAULT;
        mColor = COLOR_DEFAULT;

        init();
    }

    public HexagonProgressDrawable(@ColorInt int color, float width)
    {
        mWidth = width;
        mColor = color;

        init();
    }

    private void init() {

        mPadding = Math.max(mWidth/3/2/10,1f);

        mHexgon_lenght = (mWidth/3 - mPadding*2)/sqrt_3;

        mOrigin_x = mWidth/2;
        mOrigin_y = mWidth/2;

        mHexagons = new ArrayList<>();
        //  1
        float x = mOrigin_x - (sqrt_3*mHexgon_lenght*0.5f + mPadding);
        float y = mOrigin_y - (1.5f*mHexgon_lenght + sqrt_3*mPadding);
        mHexagons.add(new Hexagon(mHexgon_lenght,x,y));
        //  2
        x = mOrigin_x + (sqrt_3*mHexgon_lenght*0.5f + mPadding);
        y = mOrigin_y - (1.5f*mHexgon_lenght + sqrt_3*mPadding);
        mHexagons.add(new Hexagon(mHexgon_lenght,x,y));
        //  3
        x = mOrigin_x + (sqrt_3*mHexgon_lenght + 2*mPadding);
        y = mOrigin_y;
        mHexagons.add(new Hexagon(mHexgon_lenght,x,y));
        //  4
        x = mOrigin_x + (sqrt_3*mHexgon_lenght*0.5f + mPadding);
        y = mOrigin_y + (1.5f*mHexgon_lenght + sqrt_3*mPadding);
        mHexagons.add(new Hexagon(mHexgon_lenght,x,y));
        //  5
        x = mOrigin_x - (sqrt_3*mHexgon_lenght*0.5f + mPadding);
        y = mOrigin_y + (1.5f*mHexgon_lenght + sqrt_3*mPadding);
        mHexagons.add(new Hexagon(mHexgon_lenght,x,y));
        //  6
        x = mOrigin_x - (sqrt_3*mHexgon_lenght + 2*mPadding);
        y = mOrigin_y;
        mHexagons.add(new Hexagon(mHexgon_lenght,x,y));
        //  7
        mHexagons.add(new Hexagon(mHexgon_lenght,mOrigin_x,mOrigin_y));

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1f);
        //连线节点平滑处理
        PathEffect pathEffect = new CornerPathEffect(mHexgon_lenght/10);
        mPaint.setPathEffect(pathEffect);

        setupAnimators();
    }

    @Override
    public void draw(Canvas canvas)
    {
        if(mAnimator != null && mAnimator.isRunning())
        {
            for (Hexagon hex:mHexagons)
            {
                mPaint.setAlpha(hex.getAlpha());
                canvas.drawPath(hex.getPath(),mPaint);
            }
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start()
    {
        if(mAnimator != null && !mAnimator.isStarted())
        {
            mAnimator.start();
        }
    }

    @Override
    public void stop()
    {
        if(mAnimator != null && mAnimator.isStarted())
        {
            mAnimator.
        }
    }

    public void setColor(@ColorInt int color)
    {
        if(color != mColor)
        {
            mPaint.setColor(color);
            invalidateSelf();
        }
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    private void setupAnimators()
    {
        ValueAnimator.setFrameDelay(FRAME_DELAY);
        ValueAnimator animator = ValueAnimator.ofFloat(7f,0f,-7f).setDuration(ANIM_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        mAnimator = animator;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mWidth;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mWidth;
    }

    class Hexagon
    {
        //六边形边长
        private float lenght;
        //中心点
        private float origin_x;
        private float origin_y;
        //路径
        private Path path;
        //透明度
        private int alpha = 0xFF;

        public Hexagon(float lenght,float x,float y)
        {
            this.lenght = lenght;
            origin_x = x;
            origin_y = y;

            changePath(this.lenght);
        }

        public void changePath(float lenght)
        {
            this.lenght = lenght;

            path = new Path();
            path.moveTo(origin_x, origin_y - this.lenght);
            path.lineTo(origin_x + sqrt_3* this.lenght /2, origin_y - this.lenght /2);
            path.lineTo(origin_x + sqrt_3* this.lenght /2, origin_y + this.lenght /2);
            path.lineTo(origin_x, origin_y + this.lenght);
            path.lineTo(origin_x - sqrt_3* this.lenght /2, origin_y + this.lenght /2);
            path.lineTo(origin_x - sqrt_3* this.lenght /2, origin_y - this.lenght /2);
            path.close();
        }

        public float getLenght() {
            return lenght;
        }

        public float getOrigin_x() {
            return origin_x;
        }

        public float getOrigin_y() {
            return origin_y;
        }

        public Path getPath() {
            return path;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }
    }
}
