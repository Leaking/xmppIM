package com.quinn.xmpp.ui.widget;

import com.quinn.xmpp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * @author Quinn
 * @date 2015年4月27日
 */
public class PopupMenu extends FrameLayout{

	/**
	 * @param context
	 */
	public PopupMenu(Context context) {
		super(context);
		
	}


	/**
	 * @param context
	 * @param attrs
	 */
	public PopupMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		//LayoutInflater.from(context).inflate(R.layout.title, this); 
	}


	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public PopupMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
	}
}


