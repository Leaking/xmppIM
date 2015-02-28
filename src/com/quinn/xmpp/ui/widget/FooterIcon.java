/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.widget;

import com.quinn.xmpp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Quinn
 * @date 2015-2-28
 */
public class FooterIcon extends View {

	private Paint mPaint;
	private int iconRid;
	private Bitmap IconBmp;
	private PorterDuffXfermode pdxFer;
	
	public FooterIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		pdxFer = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
		
		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.FooterIcon);
		iconRid = a.getResourceId(R.styleable.FooterIcon_iconSrc, -1);
		IconBmp = BitmapFactory.decodeResource(getResources(), iconRid);
		setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setAntiAlias(true);	
		mPaint.setColor(Color.GREEN);
		canvas.drawRect(0, 0,IconBmp.getWidth() ,IconBmp.getHeight(), mPaint);
		mPaint.setXfermode( new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.drawBitmap(IconBmp, 0, 0, mPaint);
	}
	
	

}
