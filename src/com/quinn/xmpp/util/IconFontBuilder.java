package com.quinn.xmpp.util;

import android.content.Context;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.Icon;

/**
 * @author Quinn
 * @date 2015-3-20
 */
public class IconFontBuilder {

	private Context context;
	private IconicFontDrawable iconDraw;

	private static IconFontBuilder instance;
	private IconFontBuilder(Context context) {
		this.context = context;
		iconDraw = new IconicFontDrawable(context);
	}
	
	public static IconFontBuilder getInstance(Context context){
		if(instance == null)
			return new IconFontBuilder(context);
		else
			return instance;
	}
	

	public IconFontBuilder setIcon(Icon icon) {
		iconDraw.setIcon(icon);
		return this;
	}

	public IconFontBuilder setColor(int colorRsid) {
		iconDraw.setIconColor(context.getResources().getColor(colorRsid));
		return this;
	}

	public IconicFontDrawable toIconicFontDrawable() {
		return iconDraw;
	}

}
