package com.XMPP.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

public class CircleImage {

	
	/**
	 * turn the photo to circle
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		//4 elements of the RectSrc
		float left, top, right, bottom;
		//4 elements of the RectDes
		float dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			float clip = (width - height) / 2;
			
			left = 0;
			top = 0 + clip;
			right = width;
			bottom = top + width;

			height = width;

			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;

			float clip = (width - height) / 2;

			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;

			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		paint.setAntiAlias(true);
				
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
//		Shader mShader = 
//				new LinearGradient(0,0,40,60,new int[]{Color.BLACK,Color.BLACK,Color.WHITE},null,Shader.TileMode.CLAMP);
//		paint.setShader(mShader);
		paint.setStrokeWidth(5);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);
		return output;
	}

}
