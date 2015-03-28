package com.quinn.xmpp.core.drawer;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import android.os.AsyncTask;

import com.quinn.xmpp.smack.Smack;

/**
 * @author Quinn
 * @date 2015-3-22
 * 
 */
public class UploadAvatarTask extends AsyncTask<byte[], Integer, Boolean> {

	private Smack smack;
	
	public UploadAvatarTask(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Boolean doInBackground(byte[]... params) {
		System.out.println("uploading avatar");
		System.out.println("uploading bytes = " + params[0]);
		VCard vCard = new VCard();
		vCard.setAvatar(params[0]);
		vCard.setNickName("chenhuazhao");
		try {
			vCard.save(smack.getConnection());
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}


