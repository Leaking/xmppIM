package com.XMPP.Activity.Login;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class XMPPConnManager {
	private static XMPPConnection conn;
	
	private void connect(){
		ConnectionConfiguration connConfig = new ConnectionConfiguration("",5222);
	}
	public static XMPPConnection getConection(){
		return null;
	}
	
	
}
