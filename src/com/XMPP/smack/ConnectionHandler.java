package com.XMPP.smack;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class ConnectionHandler {
	private static XMPPConnection conn = null;

	
	private static void openConnection() {
		try {
			ConnectionConfiguration connConfig = new ConnectionConfiguration("192.168.1.102", 5222);			
			connConfig.setReconnectionAllowed(true);
            connConfig.setSecurityMode(SecurityMode.disabled); // SecurityMode.required/disabled
            connConfig.setSASLAuthenticationEnabled(false); // true/false
            connConfig.setCompressionEnabled(false);
			
			conn = new XMPPConnection(connConfig);
			conn.connect();
		} catch (XMPPException xe) {
			xe.printStackTrace();
		}
	}

	
	public static XMPPConnection connect(){
		if(conn == null){
			openConnection();
		}
		return conn;
	}
	
	public static void disconnect(){
		conn.disconnect();
		conn = null;
	}
}
