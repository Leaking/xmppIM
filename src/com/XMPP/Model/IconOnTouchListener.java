package com.XMPP.Model;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.XMPP.util.Constants;
import com.atermenji.android.iconicdroid.IconicFontDrawable;

public class IconOnTouchListener implements OnTouchListener{
	IconicFontDrawable icon;
	View view;
	
	public IconOnTouchListener(IconicFontDrawable icon, View view){
		this.icon = icon;
		this.view = view;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			icon.setIconColor(Constants.COLOR_COMMON_WHITE);
			view.setBackground(icon);
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			icon.setIconColor(Constants.COLOR_COMMON_BLUE);
			view.setBackground(icon);
			v.performClick();
			return true;
		}
		return true;
	}		
}
