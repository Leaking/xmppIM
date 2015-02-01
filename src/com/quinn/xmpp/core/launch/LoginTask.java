package com.quinn.xmpp.core.launch;

import android.os.AsyncTask;

import com.quinn.xmpp.Smack;
import com.quinn.xmpp.bean.User;

public class LoginTask extends AsyncTask<User, Integer, Boolean>{

	private Smack smack;
	
	public LoginTask(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Boolean doInBackground(User... users) {
		// TODO Auto-generated method stub
		boolean isConnect = smack.connect("192.168.191.3", 5222, "Smack");
		if(isConnect)
			return smack.login(users[0].getAccount(), users[0].getPassword());
		else
			return isConnect;
	}

}
