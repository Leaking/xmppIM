package com.XMPP.smack;

import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPException;

import com.XMPP.util.L;

// interface : setUpGroup();
public class GoogleTalkUtil {

	private final static String GTALK_GROUP = "GTalk";
	private Smack smack;
	Roster roster;

	public GoogleTalkUtil() {
		this.smack = SmackImpl.getInstance();
	}

	// set the entry which doesn't belong to any group into GTalk Group
	public void setUpGroup() {
		roster = smack.getConnection().getRoster();
		Collection<RosterEntry> col_entry = roster.getEntries();
		Iterator<RosterEntry> iter_entry = col_entry.iterator();
		while (iter_entry.hasNext()) {
			
			RosterEntry entry = iter_entry.next();
			L.i("gtalk " + entry.getName());
			if (!isInGroup(entry)) {
				setIntoGTalkGroup(entry);
			}
		}
	}

	// get or create the GTalk Group
	public RosterGroup getGTalkGroup() {
		RosterGroup group = roster.getGroup(GTALK_GROUP);
		if (group == null) {
			group = roster.createGroup(GTALK_GROUP);
		}
		return group;
	}

	public boolean isInGroup(RosterEntry entry) {
		Collection<RosterGroup> col_group = entry.getGroups();
		if (col_group == null || col_group.size() == 0) {
			return false;
		}
		return true;
	}

	public void setIntoGTalkGroup(RosterEntry entry) {
		try {
			getGTalkGroup().addEntry(entry);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
