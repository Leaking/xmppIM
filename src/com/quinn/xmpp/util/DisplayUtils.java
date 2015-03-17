/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author Quinn
 * @date 2015-2-28
 */
public class DisplayUtils {

	public static int SCREENWIDTH;
	public static int SCREENHEIGHT;
	

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		int width = 0;	
		WindowManager window = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = window.getDefaultDisplay();
		if(Build.VERSION.SDK_INT > 12){
			Point point = new Point();
			display.getSize(point);
			width = point.x;
		}else{
			width = display.getWidth();
		}
		SCREENWIDTH = width;
		return width;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		int height = 0;	
		WindowManager window = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = window.getDefaultDisplay();
		if(Build.VERSION.SDK_INT > 12){
			Point point = new Point();
			display.getSize(point);
			height = point.y;
		}else{
			height = display.getHeight();
		}
		SCREENHEIGHT = height;
		return height;
	}
	/**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param pxValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }  
}
