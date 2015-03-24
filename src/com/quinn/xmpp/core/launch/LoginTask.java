package com.quinn.xmpp.core.launch;

import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.quinn.xmpp.smack.Smack;

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

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

}
