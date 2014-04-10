package com.XMPP.smack;

import java.io.Serializable;
import java.util.Collection;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.pubsub.PresenceState;

public class SmackImpl implements Smack {

	//initial in the login(,,) method
	private XMPPConnection conn;


	private String username;
	private String password;

	public SmackImpl(){
		
	}
	public XMPPConnection getConnection(){
		return conn;
	}
	
	public void refreshConnection(){
		connect();
	}
	public void connect(){
		conn = ConnectionHandler.connect();
	}
	/**
	 * method of login
	 * 
	 * @param username
	 *            :usernmae of the user
	 * @param password
	 *            :password of the user
	 * @return if login successfully
	 */
	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		connect();
		this.username = username;
		this.password = password;
		try {
			conn.login(username, password);
			
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	@Override
	public void turnOnlineToAll() {
		// TODO Auto-generated method stub
		Presence presence =  new Presence(Presence.Type.available);
		conn.sendPacket(presence);
	}

	@Override
	public void turnOnlineToSomeone(String username) {
		// TODO Auto-generated method stub
		Presence presence = new Presence(Presence.Type.available);
		presence.setTo(username);
		conn.sendPacket(presence);
	}

	@Override
	public void turnDownlineToAll() {
		// TODO Auto-generated method stub
		Presence presence =  new Presence(Presence.Type.unavailable);
		conn.sendPacket(presence);
	}

	@Override
	public void turnDownlineToSomeone(String username) {
		// TODO Auto-generated method stub
		Presence presence = new Presence(Presence.Type.unavailable);
		presence.setTo(username);
		conn.sendPacket(presence);
	}

	@Override
	public Collection<RosterEntry> getAllFriend() {
		// TODO Auto-generated method stub
		Collection<RosterEntry> collection;
		collection = conn.getRoster().getEntries();
		return collection;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}



}
