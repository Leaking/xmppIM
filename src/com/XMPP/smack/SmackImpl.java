package com.XMPP.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.XMPP.Service.GroupProfile;
import com.XMPP.util.Constants;
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

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		setConnection(ConnectionHandler.getConnection());
	}

	public XMPPConnection getConnection() {
		return conn;
	}

	public void connect(String server, int port) {
		conn = ConnectionHandler.connect(server, port);
		if(conn.isConnected()){
			L.i("connect successfully");
			L.i("server: " + server);
			L.i("port:   " + port);
			L.i("authen: " + conn.isAuthenticated());
		}else{
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
	public int login(String username, String password) {
		// TODO Auto-generated method stub
		if (conn == null || conn.isConnected() == false) {
			return Constants.LOGIN_CONNECT_FAIL;
		}
		this.username = username;
		this.password = password;
		try {
			conn.login(username, password);
			L.i("after login: " + conn.isAuthenticated());
			return Constants.LOGIN_SUCCESS;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			return Constants.LOGIN_USERNAME_PSW_ERROR;
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
		while (groupIter.hasNext()) {

			groupList.add(groupIter.next());
		}
		return groupList;
	}

	@Override
	public ArrayList<GroupProfile> getGroupList() {
		// TODO Auto-generated method stub
		ArrayList<GroupProfile> groups = new ArrayList<GroupProfile>();
		Roster roster = conn.getRoster();
		Iterator<RosterGroup> iter = roster.getGroups().iterator();
		while (iter.hasNext()) {
			GroupProfile gP = new GroupProfile();
			RosterGroup rG = iter.next();
			gP.setGroupName(rG.getName());
			gP.initPersonList(rG.getEntries());
			groups.add(gP);
		}
		// showGroupList(groups);
		return groups;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (conn != null) {
			conn.disconnect();
		}
	}

	@Override
	public void addConnectionListener(ConnectionListener cListener) {
		// TODO Auto-generated method stub
		conn.addConnectionListener(cListener);
	}

}
