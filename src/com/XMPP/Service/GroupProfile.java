package com.XMPP.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.RosterEntry;

import com.XMPP.util.L;

public class GroupProfile implements Serializable {
	String groupName;
	ArrayList<PersonProfile> personList;

	public void initPersonList(Collection<RosterEntry> collect_RosterEntry) {
		personList = new ArrayList<PersonProfile>();
		Iterator<RosterEntry> iter = collect_RosterEntry.iterator();
		while (iter.hasNext()) {
			RosterEntry rE = iter.next();
			PersonProfile pP = new PersonProfile();
			L.i("rosterEntry getUser = " + rE.getName());
			L.i("rosterEntry getName = " + rE.getUser());

			pP.setName(rE.getUser());
			personList.add(pP);
		}
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ArrayList<PersonProfile> getPersonList() {
		return personList;
	}

	public void setPersonList(ArrayList<PersonProfile> personList) {
		this.personList = personList;
	}

	public void add(PersonProfile personProfile) {
		personList.add(personProfile);
	}

	public void remove(PersonProfile personProfile) {
		personList.remove(personProfile);
	}

}
