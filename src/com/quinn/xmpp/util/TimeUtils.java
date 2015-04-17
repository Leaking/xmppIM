package com.quinn.xmpp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * 时间攻擂
 * @author Quinn
 * @date 2015-4-16
 */
public class TimeUtils {
	
	private final static String format = "MM-dd HH:mm";
	// x * 60000 = x min
	private final static long longBefore = 2 * 60000;
	
	public static String getCurrentTime2String(){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String strDate = sdf.format(Calendar.getInstance().getTime());	
		return strDate;	
	}
	
}


