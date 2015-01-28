package com.quinn.xmpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;


/**
 * 
 * @author Quinn
 * @date 2015-1-28
 */
public class SmackImpl implements Smack {

	private static volatile SmackImpl INSTANCE = null;

	private String username;
	private String password;
	private boolean isConnect;
	private ConnectionManager connManager;
	private XMPPConnection xmppConn;

	
	@Override
	public boolean connect(String ip, int port, String service) {
		xmppConn = ConnectionManager.connect(ip, port, service);
		return xmppConn.isConnected();
	}
	@Override
	public boolean login(String account, String passoword) {
		try {
			xmppConn.login(account, passoword);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return xmppConn.isAuthenticated();	
	}

	

	

	
}
