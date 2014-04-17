package com.XMPP.util;

public class ValueUtil {
	public static String getItemName(String entryName){
		String name = new String();
		name = entryName.substring(0, entryName.indexOf("@"));	
		return name;
	}
}
