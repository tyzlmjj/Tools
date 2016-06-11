package com.example.mjj.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.ImageView;


public class HexagonProgressView extends ImageView
{

    public HexagonProgressView(Context context) {
        super(context);
    }

    public HexagonProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    HexagonProgressDrawable mDrawable;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int min_lenght = Math.min(getMeasuredHeight(),getMeasuredWidth());

        if(min_lenght > 0 && mDrawable == null)
        {
            mDrawable = new HexagonProgressDrawable(Color.GRAY,min_lenght);
            setImageDrawable(mDrawable);
            mDrawable.start();
        }
    }

    public void setProgressColor(@ColorInt int color)
    {
        if(mDrawable != null)
        {
            mDrawable.setColor(color);
        }
    }
}
