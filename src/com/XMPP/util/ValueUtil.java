package com.XMPP.util;

import com.XMPP.smack.ConnectionHandler;

public class ValueUtil {
	public static String getItemName(String entryName){
		String name = new String();
		name = entryName.substring(0, entryName.indexOf("@"));	
		return name;
	}
	
	public static String getID(String name){
		String jid = new String();
		String host = ConnectionHandler.getConnection().getServiceName();
		jid = name + "@" + host;	
		L.i("host jid " + jid);
		return jid;
	}
}
