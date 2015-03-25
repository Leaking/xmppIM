package com.quinn.xmpp.core.launch;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.quinn.xmpp.R;
import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.util.ImageUtils;

public class LoginTask extends AsyncTask<String, Integer, Boolean> {

	private Smack smack;
	private Resources res;

	public LoginTask(Resources res,Smack smack) {
		this.res = res;
		this.smack = smack;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		return smack.login(params[0], params[1]);
	}

}
