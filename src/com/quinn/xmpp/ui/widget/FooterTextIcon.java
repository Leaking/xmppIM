/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.quinn.xmpp.R;

/**
 * @author Quinn
 * @date 2015-2-28
 */
public class FooterTextIcon extends View {

	
	private final static String DEFAULT_CHOSEN_COLOR = "#ff181818";
	private final static String BASE_COLOR = "#55888888";
	//四个属性
	private String text;
	private int themeColor;
	private int textSize;
	private int iconRid;
	//两个画笔
	private Paint bmpPaint;
	private Paint textPaint;
	//画图
	private Bitmap iconBmp;
	private Bitmap colorIconBmp;
	private Rect iconRect;
	private Rect textRect;
	private PorterDuffXfermode pdxFer;
	private Canvas mCanvas;
	//透明度控制变量
	private int mAlpha = 0;


	/**
	 * @param context
	 */
	public FooterTextIcon(Context context) {
		this(context, null);
	}

	public FooterTextIcon(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public FooterTextIcon(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		pdxFer = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FooterTextIcon);
		iconRid = a.getResourceId(R.styleable.FooterTextIcon_iconSrc, -1);
		text = a.getString(R.styleable.FooterTextIcon_text);
		textSize = (int) a.getDimension(R.styleable.FooterTextIcon_textSize, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
						getResources().getDisplayMetrics()));
		themeColor = a.getColor(R.styleable.FooterTextIcon_color, Color.parseColor(DEFAULT_CHOSEN_COLOR));
		a.recycle();
		//从资源文件获取图片
		iconBmp = BitmapFactory.decodeResource(getResources(), iconRid);
		//设置文字画笔的相关参数
		textRect = new Rect();
		textPaint = new Paint();
		textPaint.setTextSize(textSize);
		textPaint.setColor(themeColor);
		textPaint.getTextBounds(text, 0, text.length(), textRect);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - textRect.height());
		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (textRect.height() + iconWidth)
				/ 2;
		iconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//画底部图片
		Bitmap bm = ColorIconGenerator.generate(iconBmp, Color.parseColor(BASE_COLOR));	
		canvas.drawBitmap(bm, null, iconRect, null);
		//画变色图片
		drawFooterIcon();
		canvas.drawBitmap(colorIconBmp, null, iconRect, null);
		//画底部文字
		textPaint.setColor(Color.parseColor(BASE_COLOR));
		textPaint.setAlpha(255-mAlpha);
		int x = getMeasuredWidth() / 2 - textRect.width() / 2;
		int y = iconRect.bottom + textRect.height();
		canvas.drawText(text, x, y, textPaint);
		//画变色文字
		textPaint.setColor(themeColor);
		textPaint.setAlpha(mAlpha);
		int x1 = getMeasuredWidth() / 2 - textRect.width() / 2;
		int y1 = iconRect.bottom + textRect.height();
		canvas.drawText(text, x1, y1, textPaint);
		
	}
	
	
	public void drawFooterIcon() {
		colorIconBmp = Bitmap.createBitmap(iconRect.width(),
				iconRect.height(), Config.ARGB_8888);
		mCanvas = new Canvas(colorIconBmp);
		bmpPaint = new Paint();
		bmpPaint.setAntiAlias(true);
		bmpPaint.setDither(true);
		bmpPaint.setColor(themeColor);
		bmpPaint.setAlpha(mAlpha);
		mCanvas.drawRect(0, 0, iconRect.width(), iconRect.height(), bmpPaint);
		bmpPaint.setXfermode(pdxFer);
		bmpPaint.setAlpha(255);
		Rect rect = new Rect(0, 0, iconRect.width(),
				iconRect.height());
		
		mCanvas.drawBitmap(ColorIconGenerator.generate(iconBmp, Color.parseColor(BASE_COLOR)), null, rect, bmpPaint);// must be null

	}
	


	public void setIconAlpha(int alpha) {
		this.mAlpha = alpha;
		invalidateView();
	}

	/**
	 * 重绘
	 */
	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}
	
	private static class ColorIconGenerator {
		public static Bitmap generate(Bitmap srcBmp, int color) {
			PorterDuffXfermode pdxFer = new PorterDuffXfermode(
					PorterDuff.Mode.DST_IN);
			Bitmap destBmp = Bitmap.createBitmap(srcBmp.getWidth(),
					srcBmp.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(destBmp);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(color);
			canvas.drawRect(0, 0, srcBmp.getWidth(), srcBmp.getHeight(), paint);
			paint.setXfermode(pdxFer);
			paint.setAlpha(255);
			canvas.drawBitmap(srcBmp, 0, 0, paint);
			return destBmp;
		}
	}

}