package com.XMPP.smack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;

import com.XMPP.Service.GroupProfile;

public interface Smack extends Serializable {
	
	public String getUsername();
	
	public void refresh();
	public void setConnection(XMPPConnection conn);
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
	public int login(String username,String password);
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
	

	public ArrayList<RosterGroup> getGroups();
	/**
	 * get all the friends
	 * @return
	 */
	public Collection<RosterEntry> getAllFriend();

	
	/**
	 * get all the friends and groups name
	 * @return
	 */
	public ArrayList<GroupProfile> getGroupList() ;
	
	/**
	 * disconnect
	 */
	public void disconnect();
	
	
	/**
	 * 
	 */
	public void addConnectionListener(ConnectionListener cListener);
}
