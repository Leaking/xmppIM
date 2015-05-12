package com.quinn.xmpp.smack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.search.UserSearchManager;

import com.quinn.xmpp.ui.contacts.ContactsDataItem;
import com.quinn.xmpp.ui.drawer.UserVCard;
import com.quinn.xmpp.util.FileUtils;
import com.quinn.xmpp.util.LogcatUtils;

/**
 * 
 * 
 * @author Quinn
 * @date 2015-1-28
 */
public class SmackImpl implements Smack {

	private static final String TAG = "SmackImpl";
	private XMPPConnection xmppConn;
	private Roster roster;
	private UserVCard userVcard;
	private String ip;
	private int port;
	private String service;

	private HashMap<String, String> jid_service_map;

	public static SmackImpl instance;

	public SmackImpl() {
		jid_service_map = new HashMap<String, String>();
	}

	public static SmackImpl getInstance() {
		if (instance == null)
			instance = new SmackImpl();
		return instance;
	}

	/**
	 * Connect to server
	 */
	@Override
	public boolean connect(String ip, int port, String service) {
		this.ip = ip;
		this.port = port;
		this.service = service;
		if (xmppConn != null && xmppConn.isConnected())
			return true;
		xmppConn = ConnectionManager.connect(ip, port, service);
		return xmppConn.isConnected();
	}

	@Override
	public boolean login(String account, String password) {
		try {
			xmppConn.login(account, password);
			roster = xmppConn.getRoster();
			Presence presence = new Presence(Presence.Type.available);
			xmppConn.sendPacket(presence);
			VCard vcard = new VCard();
			SmackConfiguration.setPacketReplyTimeout(300000);
			ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp",
					new VCardProvider());
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
			acManager.createAccount(account, password, attrs);
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

	public XMPPConnection getConnection() {
		return xmppConn;
	}

	public UserVCard getUserVCard() {
		return userVcard;
	}

	@Override
	public byte[] getAvatarOfSomeone(RosterEntry rosterEntry) {
		String jid = rosterEntry.getUser();
		VCard vcard = new VCard();
		try {
			vcard.load(xmppConn, jid);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return vcard.getAvatar();
	}

	@Override
	public ContactsDataItem getContactData(String jid) {
		ContactsDataItem item = new ContactsDataItem();
		VCard vcard = new VCard();
		try {
			vcard.load(xmppConn, jid);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		item.setNickname(vcard.getNickName());
		item.setJid(jid);
		return item;
	}

	@Override
	public void putJID_Service(String fullIdentity) {
		int indexOfSplit = fullIdentity.lastIndexOf("/");
		String jid = fullIdentity.substring(0, indexOfSplit);
		String service = fullIdentity.substring(indexOfSplit + 1);
		LogcatUtils.i("保存JID与Service的映射 jid = " + jid);
		LogcatUtils.i("保存JID与Service的映射  Service = " + service);
		jid_service_map.put(jid, service);
	}

	@Override
	public String getServiceByJID(String jid) {
		return jid_service_map.get(jid);
	}

	@Override
	public String getFullIdentity(String jid) {
		return jid + "/" + getServiceByJID(jid);
	}

	@Override
	public ArrayList<String> searchUser(String user) {
		ArrayList<String> jidList = new ArrayList<String>();
		try {
			UserSearchManager search = new UserSearchManager(xmppConn);
			Form searchForm = search.getSearchForm("search."
					+ xmppConn.getServiceName());
			Form answerForm = searchForm.createAnswerForm();
			answerForm.setAnswer("Username", true);
			answerForm.setAnswer("search", user);
			ReportedData data = search.getSearchResults(answerForm, "search."
					+ xmppConn.getServiceName());
			Iterator<Row> it = data.getRows();
			Row row = null;
			String jid = "";
			while (it.hasNext()) {
				row = it.next();
				jid = row.getValues("Jid").next().toString();
				LogcatUtils.i("搜索结果用户： " + jid);
				jidList.add(jid);
			}

		} catch (Exception e) {
			LogcatUtils.e("搜索用户失败");
		}
		
		return jidList;
	}

	
	@Override
	public void subscribe(String jid) {
		LogcatUtils.i("申请添加好友：" + jid);
		Presence presence = new Presence(Presence.Type.subscribe);
		presence.setMode(Presence.Mode.available);
		presence.setPriority(24);
		presence.setTo(jid);
		xmppConn.sendPacket(presence);
	}

	@Override
	public void sendFile(File file, String fullJID) {
		if(FileUtils.isSendableFile(file) == false)
			return;
		FileTransferManager manager = new FileTransferManager(xmppConn);
		OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(fullJID);
		try {
			transfer.sendFile(file, "You won't believe this!");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	
}
