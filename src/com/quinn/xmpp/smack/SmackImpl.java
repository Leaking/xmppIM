package com.quinn.xmpp.smack;


import java.util.ArrayList;
import java.util.HashMap;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.VCardProvider;

import com.quinn.xmpp.ui.drawer.UserVCard;



/**
 * 
 * @author Quinn
 * @date 2015-1-28
 */
public class SmackImpl implements Smack {


	private XMPPConnection xmppConn;
	private Roster roster;
	private UserVCard userVcard;
	private String ip;
	private int port;
	private String service;
	
	
	/**
	 * Connect to server
	 */
	@Override
	public boolean connect(String ip, int port, String service) {
		this.ip = ip;
		this.port = port;
		this.service = service;
		if(xmppConn != null && xmppConn.isConnected())
			return true;
		xmppConn = ConnectionManager.connect(ip, port, service);
		return xmppConn.isConnected();
	}
	
	@Override
	public boolean login(String account, String password) {
		try {
			System.out.println("if connected 2= " + xmppConn.isConnected());
			xmppConn.login(account, password);
			roster = xmppConn.getRoster();
			Presence presence = new Presence(Presence.Type.available);
			xmppConn.sendPacket(presence);
			//
			VCard vcard = new VCard();
			vcard.load(xmppConn);
			userVcard = new UserVCard(vcard);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return xmppConn.isAuthenticated();	
	}

	@Override
	public boolean signUp(String account, String password) {
		AccountManager acManager = new AccountManager(xmppConn);
		try {
			HashMap<String, String> attrs = new HashMap<String, String>();
			attrs.put("name", account);
			acManager.createAccount(account, password,attrs);
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Get all rosterEntry of the current user
	 */
	@Override
	public ArrayList<RosterEntry> getAllRosterEntry() {
		ArrayList<RosterEntry> arrayList = new ArrayList<RosterEntry>();
		arrayList = new ArrayList<RosterEntry>(roster.getEntries());
		return arrayList;
	}

	/**
	 * Get all group of the current user
	 */
	@Override
	public ArrayList<RosterGroup> getAllRosterGroup() {
		ArrayList<RosterGroup> arrayList = new ArrayList<RosterGroup>();
		arrayList = new ArrayList<RosterGroup>(roster.getGroups());
		return arrayList;
	}
	
	
	public XMPPConnection getConnection(){
		return xmppConn;
	}

	public UserVCard getUserVCard(){
		return userVcard;
	}


	


	
}
