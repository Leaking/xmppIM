package com.XMPP.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtil {
	private final static String format = "MM-dd HH:mm";
	// x * 60000 = x min
	private final static long longBefore = 2 * 60000;
	
	public static String getCurrentTime2String(){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String strDate = sdf.format(Calendar.getInstance().getTime());	
		return strDate;	
	}
	
	public static String getCertainTime2String(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String strDate = sdf.format(date);	
		return strDate;	
	}
	
	public static String getViewTime(String strDate){
		String viewTime = null;
		Date date;
		try {
			date = new SimpleDateFormat(format).parse(strDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day_past = cal.get(Calendar.DAY_OF_YEAR);
			int day_now = cal.get(Calendar.DAY_OF_YEAR);
			String hour_minite = strDate.substring(strDate.indexOf(" "));	

			if(day_now - day_past == 1){
				viewTime = "Yesterday" + hour_minite;
			}else if(day_now == day_past){
				viewTime = "Today" + hour_minite;
			}else{
				viewTime = hour_minite;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return viewTime;
	}
	public static String getCurrentViewTime(){
		String strDate = getCurrentTime2String();
		return getViewTime(strDate);
	}
	
	
	//
	public static boolean isLongBefore(String pastStr,String nowStr){
		Date datePast;
		Date dateNow;
		try {
			dateNow = new SimpleDateFormat(format).parse(nowStr);
			datePast = new SimpleDateFormat(format).parse(pastStr);
			long nowMill = dateNow.getTime();
			long pastMill = datePast.getTime();
			L.i("time long NOW   " + nowMill);
			L.i("time long PAST  " + pastMill);
			long a = nowMill - pastMill;
			L.i("difffference    " + a);
			if(nowMill - pastMill == 0 || nowMill - pastMill <= longBefore){
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return true;
	}

}
