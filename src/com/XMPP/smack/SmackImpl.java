package com.XMPP.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.XMPP.util.L;

public class SmackImpl implements Smack {

	// initial in the login(,,) method
	private XMPPConnection conn;

	private String username;
	private String password;

	public SmackImpl() {
		if (conn == null) {
			conn = ConnectionHandler.getConnection();
		}
	}

	public void setConnection(XMPPConnection conn) {
		this.conn = conn;
	}

	public XMPPConnection getConnection() {
		return conn;
	}

	public void connect(String server, int port) {
		conn = ConnectionHandler.connect(server, port);

		if (this.getConnection() != null) {
			L.i("connect is built");
			L.i("server:" + server);
			L.i("port:" + port);
		} else {
			L.i("connect fail");
		}
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
		Presence presence = new Presence(Presence.Type.available);
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
		Presence presence = new Presence(Presence.Type.unavailable);
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

	@Override
	public ArrayList<RosterGroup> getGroups() {
		// TODO Auto-generated method stub
		Roster roster = conn.getRoster();
		ArrayList<RosterGroup> groupList = new ArrayList<RosterGroup>();
		Collection<RosterGroup> groupCollect = roster.getGroups();
		Iterator<RosterGroup> groupIter = groupCollect.iterator();
		while(groupIter.hasNext()){
			groupList.add(groupIter.next());
		}
		return groupList;
	}

}
