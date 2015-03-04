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

	private final static String DEFAULT_COLOR = "#ff00ff00";
	
	//四个属性
	private String text;
	private int themeColor;
	private int textSize;
	private int iconRid;
	//两个画笔
	private Paint mPaint;
	private Paint textPaint;
	
	
	private Bitmap iconBmp;
	private Bitmap colorIconBmp;
	private String mColor;
	private int mAlpha = 0;

	
	private Rect iconRect;
	private Rect textRect;
	
	private PorterDuffXfermode pdxFer;
	private Canvas mCanvas;

	/**
	 * @param context
	 */
	public FooterTextIcon(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
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
		mColor = DEFAULT_COLOR;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FooterTextIcon);
		iconRid = a.getResourceId(R.styleable.FooterTextIcon_iconSrc, -1);
		text = a.getString(R.styleable.FooterTextIcon_text);
		textSize = (int) a.getDimension(R.styleable.FooterTextIcon_textSize, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
						getResources().getDisplayMetrics()));
		themeColor = a.getColor(R.styleable.FooterTextIcon_color, Color.parseColor(DEFAULT_COLOR));
		
		
		iconBmp = BitmapFactory.decodeResource(getResources(), iconRid);
		a.recycle();
		
		
		
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
		// canvas.drawBitmap(iconBmp, 0, 0, null);
		Bitmap bm = ColorIconGenerator.generate(iconBmp, Color.BLACK);
		Rect rect = new Rect(0, 0, bm.getWidth() * 2,
				bm.getHeight() * 2);
		canvas.drawBitmap(bm, null, rect, null);
		
		drawFooterIcon();
		canvas.drawBitmap(colorIconBmp, 0, 0, null);

	}

	public void drawFooterIcon() {
		colorIconBmp = Bitmap.createBitmap(iconBmp.getWidth() * 2,
				iconBmp.getHeight() * 2, Config.ARGB_8888);
		mCanvas = new Canvas(colorIconBmp);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);

		mPaint.setColor(Color.parseColor(mColor));
		mPaint.setAlpha(mAlpha);
		// mPaint.setColor(Color.YELLOW);

		mCanvas.drawRect(0, 0, iconBmp.getWidth()*2, iconBmp.getHeight()*2, mPaint);
		mPaint.setXfermode(pdxFer);
		mPaint.setAlpha(255);

		Rect rect = new Rect(0, 0, iconBmp.getWidth() * 2,
				iconBmp.getHeight() * 2);

		mCanvas.drawBitmap(ColorIconGenerator.generate(iconBmp, Color.BLACK), null, rect, mPaint);// must be null

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

}