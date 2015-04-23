package com.quinn.xmpp.core.contacts;

import java.util.ArrayList;

import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.ui.contacts.ContactsDataItem;

import android.os.AsyncTask;


/**
 * @author Quinn
 * @date 2015-4-23
 */
public class SearchUserTask extends AsyncTask<String, Integer, ArrayList<String>>{

	private Smack smack;

	/**
	 * 
	 */
	public SearchUserTask(Smack smack) {
		this.smack = smack;
	}
	
	
	@Override
	protected ArrayList<String> doInBackground(String... params) {
		return smack.searchUser(params[0]);
	}
	
	

}


