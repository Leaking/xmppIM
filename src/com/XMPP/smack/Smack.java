package com.XMPP.smack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;

import com.XMPP.Database.RowContacts;

public interface Smack extends Serializable {
	
	public String getUsername();
	
	public String getJID();
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

	public String isOnline(String jid);
	
	
	public String getNickname(String jid);
	/**
	 * get all the friends and group
	 * @return
	 */
	public ArrayList<RowContacts> getContactsRows() ;
	
	/**
	 * disconnect
	 */
	public void disconnect();
	
	
	/**
	 * 
	 */
	public void addConnectionListener(ConnectionListener cListener);
	
	
	public void acceptFriend(String jid,String groupName);
	
	public void addGroup(String groupname);
	public void addEntry(String jid,String groupname);
	public RosterEntry getEntry(String u_jid);
	public void subscribe(String jid);
	
	public void subscribed(String jid);
	
	public void unSubscribe(String jid);
	public void unSubscribed(String jid);
	
	public void removeEntry(String jid);

	public void markResource(String fullyJID);

	public String getFullyJID(String bareJID);


	public FileTransferRequest getRequestList(String u_jid,String filename);
	
	public void insertRequest(FileTransferRequest request);
	
}
