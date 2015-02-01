package com.quinn.xmpp;


import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;


/**
 * 
 * @author Quinn
 * @date 2015-1-28
 */
public class SmackImpl implements Smack {

	private String username;
	private String password;
	private boolean isConnect;
	private ConnectionManager connManager;
	private XMPPConnection xmppConn;

	
	@Override
	public boolean connect(String ip, int port, String service) {
		xmppConn = ConnectionManager.connect(ip, port, service);
		//login("quinn", "123456");
		return xmppConn.isConnected();
	}
	@Override
	public boolean login(String account, String password) {
		try {
			xmppConn.login(account, password);	
			Presence presence = new Presence(Presence.Type.available);
			xmppConn.sendPacket(presence);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return xmppConn.isAuthenticated();	
	}

	

	

	
}
