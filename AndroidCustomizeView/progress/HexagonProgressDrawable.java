
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

public class HexagonProgressDrawable extends Drawable implements Animatable {


    private final float sqrt_3 = (float) Math.sqrt(3);

    private final long ANIM_TIME = 400;

    private ValueAnimator mAnimator;

    //宽高相等，只保存宽
    private float mWidth;

    //间距
    private float mPadding;

    //原点，这里是中心点
    private float mOrigin_x;
    private float mOrigin_y;

    //六边形边长
    private float mHexgon_lenght;

    private Paint mPaint;

    private List<Hexagon> mHexagons;



    public HexagonProgressDrawable(@ColorInt int color, float width)
    {
        mWidth = width;

        mPadding = Math.max(width/3/2/10,1f);

        mHexgon_lenght = (width/3 - mPadding*2)/sqrt_3;

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
        mPaint.setColor(color);
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
        for (Hexagon hex:mHexagons)
        {
            canvas.drawPath(hex.getPath(),mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha)
    {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    private void setupAnimators()
    {
        mAnimator = ValueAnimator.ofFloat(1f,0f,-1f).setDuration(ANIM_TIME);
    }

    class Hexagon
    {
        //六边形边长
        private float lenght;

        //中心点
        private float origin_x;
        private float origin_y;

        private Path path;

        public Hexagon(float lenght,float x,float y)
        {
            this.lenght = lenght;
            origin_x = x;
            origin_y = y;

            //六边形路径
            path = new Path();
            path.moveTo(origin_x, origin_y - this.lenght);
            path.lineTo((float) (origin_x + Math.sqrt(3f)* this.lenght /2), origin_y - this.lenght /2);
            path.lineTo((float) (origin_x + Math.sqrt(3f)* this.lenght /2), origin_y + this.lenght /2);
            path.lineTo(origin_x, origin_y + this.lenght);
            path.lineTo((float) (origin_x - Math.sqrt(3f)* this.lenght /2), origin_y + this.lenght /2);
            path.lineTo((float) (origin_x - Math.sqrt(3f)* this.lenght /2), origin_y - this.lenght /2);
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
    }
}
