/**
 * 2015-2-2
 * 2015-2-2
 */
package com.quinn.xmpp.core.launch;

import android.os.AsyncTask;

import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.smack.SmackConstants;

/**
 * @author Quinn
 * @date 2015-2-2
 */
public class ConnectTask extends AsyncTask<String, Integer, Boolean>{

	private Smack smack;
	public ConnectTask(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		return smack.connect(params[0], SmackConstants.PORT, SmackConstants.SERVICE);
	}

}
