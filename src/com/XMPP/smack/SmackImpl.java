package com.XMPP.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.packet.VCard;

import com.XMPP.Database.RowContacts;
import com.XMPP.Database.TableHistory;
import com.XMPP.Model.BubbleMessage;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.Test;
import com.XMPP.util.ValueUtil;

public class SmackImpl implements Smack {

	private static volatile SmackImpl INSTANCE = null;

	private String username;
	private String password;
	private boolean isConnect;
	private HashMap<String, ArrayList<BubbleMessage>> bubbleMap = new HashMap<String, ArrayList<BubbleMessage>>();
	private HashMap<String, ArrayList<FileTransferRequest>> requestMap = new HashMap<String, ArrayList<FileTransferRequest>>();
	private static final HashMap<String, String> jid_resource_map = new HashMap<String, String>();

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
	public ArrayList<RowContacts> getContactsRows() {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		String jid = conn.getUser();
		L.i("jID:" + jid);
		ArrayList<RowContacts> rows = new ArrayList<RowContacts>();

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
			
				photo = null;
				signature = null;
				RowContacts row = new RowContacts(jid, group, friend_jID,
						nickname, online, photo, signature);
				rows.add(row);
			}
			if (intoFlag == 0) {
				RowContacts row = new RowContacts(jid, group, null, null, null,
						null, null);
				rows.add(row);
			}

		}
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
	public void acceptFriend(String requestJID, String groupName) {
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
		addEntry(requestJID, groupName);

	}

	@Override
	public void addGroup(String groupname) {
		// TODO Auto-generated method stub
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.getRoster().createGroup(groupname);
	}

	@Override
	public void addEntry(String jid, String groupname) {
		// TODO Auto-generated method stub

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
		L.i("into subscribeeeeeeeeeeeeeeeee");
		Presence newp = new Presence(Presence.Type.subscribe);
		newp.setMode(Presence.Mode.available);
		newp.setPriority(24);
		newp.setTo(jid);
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.sendPacket(newp);
		// addEntry(jid,"friend");
	}

	@Override
	public void unSubscribe(String jid) {
		// TODO Auto-generated method stub
		Presence newp = new Presence(Presence.Type.unsubscribe);
		newp.setMode(Presence.Mode.available);
		newp.setPriority(24);
		newp.setTo(jid);
		XMPPConnection conn = ConnectionHandler.getConnection();
		conn.sendPacket(newp);
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

	@Override
	public String getJID() {
		// TODO Auto-generated method stub
		String string = this.getConnection().getUser();
		return ValueUtil.deleteSth(string, Constants.DELETE_STH);
	}

	@Override
	public void removeEntry(String jid) {
		RosterEntry entry = this.getConnection().getRoster().getEntry(jid);
		try {
			this.getConnection().getRoster().removeEntry(entry);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public RosterEntry getEntry(String u_jid) {
		// TODO Auto-generated method stub
		return this.getConnection().getRoster().getEntry(u_jid);

	}

	@Override
	public void markResource(String fullyJID) {
		// TODO Auto-generated method stub
		String bareJID = fullyJID.substring(0, fullyJID.lastIndexOf("/"));
		this.jid_resource_map.put(bareJID, fullyJID);
	}

	@Override
	public String getFullyJID(String bareJID) {

		return jid_resource_map.get(bareJID) == null ? bareJID
				: jid_resource_map.get(bareJID);
	}

	@Override
	public FileTransferRequest getRequestList(String u_jid, String filename) {
		// TODO Auto-generated method stub

		ArrayList<FileTransferRequest> list = requestMap.get(u_jid);
		Test.outputRequestMap(requestMap);
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getFileName().equals(filename))
				return list.get(i);
		}
		return null;

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void addBubbleList(String jid, ArrayList<BubbleMessage> bubbleList) {
		// TODO Auto-generated method stub
		bubbleMap.put(jid, bubbleList);
	}

	@Override
	public HashMap<String, ArrayList<BubbleMessage>> getBubbleMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<BubbleMessage> getBubbleList(String jid) {
		// TODO Auto-generated method stub
		return bubbleMap.get(jid);
	}

	@Override
	public void addRequest(FileTransferRequest request) {
		// TODO Auto-generated method stub
		// the following line get the fully JID
		String u_jid = request.getRequestor();
		if (requestMap.containsKey(u_jid)) {
			requestMap.get(u_jid).add(request);
		} else {
			ArrayList<FileTransferRequest> requestList = new ArrayList<FileTransferRequest>();
			requestList.add(request);
			requestMap.put(u_jid, requestList);
		}
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		this.username = username;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
	}
	@Override
	public boolean isConnect() {
		return isConnect;
	}
	@Override
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}


}
