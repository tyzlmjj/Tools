package com.example.administrator.myapplication;

import android.content.Context;
import android.util.TypedValue;

/**
 * 单位转换<p>
 * 可查看{@link TypedValue}的applyDimension()
 */
public class DensityUtil {

	public static float px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return pxValue / scale;
	}

	public static float dp2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return dipValue * scale;
	}

	public static float px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

		return pxValue / fontScale;
	}

	public static float sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return spValue * fontScale;
	}
}
