package com.quinn.xmpp.smack;

import java.util.ArrayList;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;


/**
 * 
 * @author Quinn
 * @date 2015-1-28
 */
public interface Smack{
	
	public boolean connect(String ip, int port,String service);
	public boolean login(String account, String passoword);
	public boolean signUp(String account, String password);
	public ArrayList<RosterEntry> getAllRosterEntry();
	public XMPPConnection getConnection();
	
	/**
	 * get all
	 */
	public ArrayList<RosterGroup> getAllRosterGroup();
}
