package com.XMPP.smack;

import java.io.Serializable;
import java.util.Collection;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;

public interface Smack extends Serializable {
	
	public String getUsername();
	
	/**
	 * return the current XMPPConnection
	 */
	public XMPPConnection getConnection();

	/**
	 * connect to the server
	 */
	public void connect(String server, int port);
	/**
	 * login method
	 * @param username:username of the user who try to login
	 * @param password:password of the user who try to login
	 * @return
	 */
	public boolean login(String username,String password);
	/**
	 * set visible to all friends
	 */
	public void turnOnlineToAll();
	/**
	 * set visible to certain friend;
	 * @param username:the certain friend's id
	 */
	public void turnOnlineToSomeone(String username);
	/**
	 * set unvisible to all friend
	 */
	public void turnDownlineToAll();
	/**
	 * set unvisible to a certain friend
	 * @param username:the certain friend's id
	 */
	public void turnDownlineToSomeone(String username);
	
	public Collection<RosterEntry> getAllFriend();

}
