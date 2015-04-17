package com.quinn.xmpp.core.drawer;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.VCardProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.quinn.xmpp.smack.Smack;

/**
 * @author Quinn
 * @date 2015-3-22
 */
public class DownloadAvatarTask extends AsyncTask<String, Integer, Bitmap> {

	private Smack smack;
	
	public DownloadAvatarTask(Smack smack){
		this.smack = smack;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		VCard vCard = new VCard();
    	SmackConfiguration.setPacketReplyTimeout(300000);
    	ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp",
    	                    new VCardProvider());
    	try {
			vCard.load(smack.getConnection(), params[0]);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
    	byte[] bs = vCard.getAvatar();
    	System.out.println("获得某个用户头像结果 = " + bs);
    	Bitmap bitmap = null;
    	if(bs != null)
    		bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
		return bitmap;
	}

}


