package com.XMPP.smack;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class ConnectionHandler {
	private static XMPPConnection conn = null;

	public static XMPPConnection getConnection(){
		return conn;
	}
	private static void openConnection(String ip,int port) {
		try {
			ConnectionConfiguration connConfig = new ConnectionConfiguration(ip, port);			
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

	
	public static XMPPConnection connect(String ip,int port){
		if(conn == null){
			openConnection(ip,port);
		}
		return conn;
	}
	
	public static void disconnect(){
		conn.disconnect();
		conn = null;
	}
}
