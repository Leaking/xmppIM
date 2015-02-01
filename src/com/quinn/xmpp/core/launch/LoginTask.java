package com.quinn.xmpp.core.launch;

import android.os.AsyncTask;

import com.quinn.xmpp.Smack;
import com.quinn.xmpp.bean.User;

public class LoginTask extends AsyncTask<String, Integer, Boolean>{

	private Smack smack;
	
	public LoginTask(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		boolean isConnect = smack.connect(params[0], 5222, "Smack");
		if(isConnect)
			return smack.login(params[1], params[2]);
		else
			return isConnect;
	}

}
