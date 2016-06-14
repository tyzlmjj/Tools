
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;


public class HexagonProgressView extends View implements Animatable
{
    private HexagonProgressDrawable mDrawable;

    public HexagonProgressView(Context context) {
        super(context);
    }

    public HexagonProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        int min_lenght = Math.min(w,h);
        mDrawable = new HexagonProgressDrawable(Color.GRAY,min_lenght);
        mDrawable.setCallback(this);
        mDrawable.start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        mDrawable.draw(canvas);
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        invalidate();
    }

    public void setProgressColor(@ColorInt int color)
    {
        if(mDrawable != null)
        {
            mDrawable.setColor(color);
        }
    }

    @Override
    public void start()
    {
        if(mDrawable != null)
        {
            mDrawable.start();
        }
    }

    @Override
    public void stop()
    {
        if(mDrawable != null)
        {
            mDrawable.stop();
        }
    }

    @Override
    public boolean isRunning()
    {
        if(mDrawable != null)
        {
            return mDrawable.isRunning();
        }
        return false;
    }
}
