package com.quinn.xmpp.core.profile;

import java.net.MalformedURLException;
import java.net.URL;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import com.quinn.xmpp.R;
import com.quinn.xmpp.smack.Smack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * @author Quinn
 * @date 2015-3-22
 * 
 */
public class UploadAvatar extends AsyncTask<byte[], Integer, Boolean> {

	private Smack smack;
	
	public UploadAvatar(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Boolean doInBackground(byte[]... params) {
		VCard vCard = new VCard();
		vCard.setAvatar(params[0]);
		try {
			vCard.save(smack.getConnection());
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}


