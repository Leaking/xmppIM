package com.quinn.xmpp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.Icon;

/**
 * @author Quinn
 * @date 2015-3-17
 */
public class ImageFormatUtils {

	
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	
	public static IconicFontDrawable buildIconFont(Context context,Icon icon, int color){
		IconicFontDrawable iconFont = new IconicFontDrawable(context);
		iconFont.setIcon(icon);
		iconFont.setIconColor(color);
		return iconFont;
	}
	


	public static Bitmap toRoundBitmap(Bitmap bitmap, boolean ifonline) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		// 4 elements of the RectSrc
		float left, top, right, bottom;
		// 4 elements of the RectDes
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
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		paint.setAntiAlias(true);
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));// 设置两张图片相交时的模式
		if (!ifonline) {
			ColorMatrix cm = new ColorMatrix();
			cm.setSaturation(0);
			ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
			paint.setColorFilter(f);
		}
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
		paint.setStrokeWidth(5);
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);
		bitmap.recycle();
		return output;
	}

	
	

	
	public static int readPictureDegree(String path) {  
	    int degree = 0;  
	    try {  
	        ExifInterface exifInterface = new ExifInterface(path);  
	        int orientation = exifInterface.getAttributeInt(  
	                ExifInterface.TAG_ORIENTATION,  
	                ExifInterface.ORIENTATION_NORMAL);  
	        switch (orientation) {  
	        case ExifInterface.ORIENTATION_ROTATE_90:  
	            degree = 90;  
	            break;  
	        case ExifInterface.ORIENTATION_ROTATE_180:  
	            degree = 180;  
	            break;  
	        case ExifInterface.ORIENTATION_ROTATE_270:  
	            degree = 270;  
	            break;  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return degree;  
	}  
	  
	public static Bitmap rotateBitmap(Bitmap bitmap, float rotateDegree){  
	    Matrix matrix = new Matrix();  
	    matrix.postRotate((float)rotateDegree);  
	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);  
	    return bitmap;  
	}
	
	
}
