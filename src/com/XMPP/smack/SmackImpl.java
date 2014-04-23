package com.XMPP.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.VCard;

import com.XMPP.Database.ContactsRow;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.Test;
import com.XMPP.util.ValueUtil;

public class SmackImpl implements Smack {

	private static volatile SmackImpl INSTANCE = null;
	// initial in the login(,,) method

	private String username;
	private String password;

	private SmackImpl() {

	}

	// thread safe and performance promote
	public static SmackImpl getInstance() {
		if (INSTANCE == null) {
			synchronized (SmackImpl.class) {
				// when more than two threads run into the first null check same
				// time, to avoid instanced more than one time, it needs to be
				// checked again.
				if (INSTANCE == null) {
					INSTANCE = new SmackImpl();
				}
			}
		}
		return INSTANCE;
	}

	public void connect(String server, int port) {

		ConnectionHandler.connect(server, port);
		XMPPConnection conn = ConnectionHandler.getConnection();
		if (conn.isConnected()) {
			L.i("connect successfully");
			L.i("server: " + server);
			L.i("port:   " + port);
			L.i("authen: " + conn.isAuthenticated());
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
	public int login(String username, String password) {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
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
		XMPPConnection conn = ConnectionHandler.getConnection();
		Presence presence = new Presence(Presence.Type.available);
		conn.sendPacket(presence);
	}

	@Override
	public void turnOnlineToSomeone(String username) {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		Presence presence = new Presence(Presence.Type.available);
		presence.setTo(username);
		conn.sendPacket(presence);
	}

	@Override
	public void turnDownlineToAll() {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		Presence presence = new Presence(Presence.Type.unavailable);
		conn.sendPacket(presence);
	}

	@Override
	public void turnDownlineToSomeone(String username) {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		Presence presence = new Presence(Presence.Type.unavailable);
		presence.setTo(username);
		conn.sendPacket(presence);
	}

	@Override
	public Collection<RosterEntry> getAllFriend() {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
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
		XMPPConnection conn = ConnectionHandler.getConnection();
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
	public String isOnline(String jid) {
		boolean online = false;
		Roster roster = ConnectionHandler.getConnection().getRoster();
		Presence p6e = roster.getPresence(jid);

		if (p6e.getType().equals(Presence.Type.available))
			online = true;
		else
			online = false;

		if (online == true)
			return Constants.ONLINE;
		else
			return Constants.OFF_LINE;

	}

	@Override
	public String getNickname(String jid) {
		String nickname = jid;
		VCard vcard = new VCard();
		try {

			vcard.load(ConnectionHandler.getConnection(), jid);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nickname = vcard.getNickName();
		// somebody have set a nickname,somebody have not
		if (nickname == null) {
			nickname = ValueUtil.getItemName(jid);
		}
		return nickname;
	}

	@Override
	public ArrayList<ContactsRow> getContactsRows() {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		String jid = conn.getUser();
		L.i("jID:" + jid);
		ArrayList<ContactsRow> rows = new ArrayList<ContactsRow>();
		
		Roster roster = conn.getRoster();
		Iterator<RosterGroup> iter = roster.getGroups().iterator();
		// go through each group
		while (iter.hasNext()) {
			RosterGroup rG = iter.next();
			String group = rG.getName();
			String friend_jID = null;
			String nickname = null;
			String online = null;
			String photo = null;
			String signature = null;
			Collection<RosterEntry> cE = rG.getEntries();
			int groupSize = cE.size();
			Iterator<RosterEntry> iR_iter = cE.iterator();
			// go through each friend
			int intoFlag = 0;
			while (iR_iter.hasNext()) {
				intoFlag = 1;
				RosterEntry rE = iR_iter.next();
				friend_jID = rE.getUser();
				nickname = getNickname(rE.getUser());
				online = isOnline(rE.getUser());
				//
				photo = null;
				signature = null;
				ContactsRow row = new ContactsRow(jid, group, friend_jID,
						nickname, online, photo, signature);
				rows.add(row);
			}
			if(intoFlag == 0){
				System.out.println("empty group " + group);
				ContactsRow row = new ContactsRow(jid,group,null,null,null,null,null);
				rows.add(row);
			}

		}
		// TEST
		L.i("--------END GET ROWS--------");
		Test.outputContactsRows(rows);
		return rows;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		if (conn != null) {
			conn.disconnect();
		}
	}

	@Override
	public void addConnectionListener(ConnectionListener cListener) {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.addConnectionListener(cListener);
	}

	@Override
	public XMPPConnection getConnection() {
		// TODO Auto-generated method stub
		return ConnectionHandler.getConnection();
	}

	@Override
	public void acceptFriend(String requestJID,String groupName) {
		// TODO Auto-generated method stub
		Presence newp = new Presence(Presence.Type.subscribed);
		newp.setMode(Presence.Mode.available);
		newp.setPriority(24);
		String nickname = getNickname(requestJID);
		newp.setTo(requestJID);
		XMPPConnection conn = ConnectionHandler.getConnection();

		Presence subscription = new Presence(Presence.Type.subscribe);
		subscription.setTo(requestJID);
		conn.sendPacket(subscription);		
		addEntry(requestJID,groupName);
		
	}
	@Override
	public void addEntry(String jid, String groupname) {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		String nickname = getNickname(jid);
		try {
			conn.getRoster().createEntry(jid, nickname,
					new String[] { groupname });
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void subscribed(String jid) {
		// TODO Auto-generated method stub
		Presence newp = new Presence(Presence.Type.subscribed);
		newp.setMode(Presence.Mode.available);
		newp.setPriority(24);
		newp.setTo(jid);
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.sendPacket(newp);
	}


	@Override
	public void subscribe(String jid) {
		// TODO Auto-generated method stub
		Presence newp = new Presence(Presence.Type.subscribe);
		newp.setMode(Presence.Mode.available);
		newp.setPriority(24);
		newp.setTo(jid);
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.sendPacket(newp);
		addEntry(jid,"friend");
	}

	@Override
	public void unSubscribe(String jid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unSubscribed(String jid) {
		// TODO Auto-generated method stub
		Presence newp = new Presence(Presence.Type.unsubscribed);
		newp.setMode(Presence.Mode.available);
		newp.setPriority(24);
		newp.setTo(jid);
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.sendPacket(newp);
	}
	
	

}
