package com.quinn.xmpp.core.launch;

import android.os.AsyncTask;

import com.quinn.xmpp.smack.Smack;

public class LoginTask extends AsyncTask<String, Integer, Boolean>{

	private Smack smack;
	
	public LoginTask(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		return smack.login(params[0], params[1]);
	}

}
