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
import android.view.View;

import com.quinn.xmpp.R;

/**
 * @author Quinn
 * @date 2015-2-28
 */
public class FooterIcon extends View {

	private final static String DEFAULT_COLOR = "#ff00ff00";
	private Paint mPaint;
	private int iconRid;
	private Bitmap IconBmp;
	private Bitmap mBmp;
	private String mColor;
	private int mAlpha = 0;

	private PorterDuffXfermode pdxFer;
	private Canvas mCanvas;

	/**
	 * @param context
	 */
	public FooterIcon(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public FooterIcon(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public FooterIcon(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		pdxFer = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
		mColor = DEFAULT_COLOR;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FooterIcon);
		iconRid = a.getResourceId(R.styleable.FooterIcon_iconSrc, -1);
		IconBmp = BitmapFactory.decodeResource(getResources(), iconRid);
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawBitmap(IconBmp, 0, 0, null);
		Bitmap bm = ColorIconGenerator.generate(IconBmp, Color.BLACK);
		Rect rect = new Rect(0, 0, bm.getWidth() * 2,
				bm.getHeight() * 2);
		canvas.drawBitmap(bm, null, rect, null);
		
		drawFooterIcon();
		canvas.drawBitmap(mBmp, 0, 0, null);

	}

	public void drawFooterIcon() {
		mBmp = Bitmap.createBitmap(IconBmp.getWidth() * 2,
				IconBmp.getHeight() * 2, Config.ARGB_8888);
		mCanvas = new Canvas(mBmp);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);

		mPaint.setColor(Color.parseColor(mColor));
		mPaint.setAlpha(mAlpha);
		// mPaint.setColor(Color.YELLOW);

		mCanvas.drawRect(0, 0, IconBmp.getWidth()*2, IconBmp.getHeight()*2, mPaint);
		mPaint.setXfermode(pdxFer);
		mPaint.setAlpha(255);

		Rect rect = new Rect(0, 0, IconBmp.getWidth() * 2,
				IconBmp.getHeight() * 2);

		mCanvas.drawBitmap(ColorIconGenerator.generate(IconBmp, Color.BLACK), null, rect, mPaint);// must be null

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