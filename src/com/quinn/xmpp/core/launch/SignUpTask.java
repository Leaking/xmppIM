/**
 * 2015-2-2
 * 2015-2-2
 */
package com.quinn.xmpp.core.launch;

import android.os.AsyncTask;

import com.quinn.xmpp.smack.Smack;

/**
 * @author Quinn
 * @date 2015-2-2
 */
public class SignUpTask extends AsyncTask<String, Integer, Boolean> {

	private Smack smack;

	public SignUpTask(Smack smack) {
		this.smack = smack;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		return smack.signUp(params[1], params[2]);
	}

}
