package com.quinn.xmpp.smack;

import java.util.ArrayList;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;

import com.quinn.xmpp.ui.contacts.ContactsDataItem;
import com.quinn.xmpp.ui.drawer.UserVCard;


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
	public UserVCard getUserVCard();
	/**
	 * get all
	 */
	public ArrayList<RosterGroup> getAllRosterGroup();
	
	/**
	 * Get byte[] of somebody's avatar
	 * @param rosterEntry
	 * @return
	 */
	public byte[] getAvatarOfSomeone(RosterEntry rosterEntry);
	
	/**
	 * Load nickname and email and etc. of some one;
	 * @param rosterEntry
	 * @return
	 */
	public ContactsDataItem getContactData(String jid);
	
}
