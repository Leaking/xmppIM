package com.quinn.xmpp.ui.contacts;


import java.util.ArrayList;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.VCardProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.main.MainActivity;

/**
 * @author Quinn
 * @date 2015-3-16
 * @description A adapter class of ContantsFragment's RecyclerView
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

	private ArrayList<ContactsDataItem> dataItems;
	private MainActivity activity;
	
	public ContactsAdapter(MainActivity activity, ArrayList<ContactsDataItem> dataItems){
		this.activity = activity;
		this.dataItems = dataItems;
	}
	
	
	public static class ViewHolder extends RecyclerView.ViewHolder{

		public ImageView icon;
		public TextView name;
		public ViewHolder(View view) {
			 super(view);
			 icon = (ImageView) view.findViewById(R.id.icon);
			 name = (TextView) view.findViewById(R.id.name);
		}
		
	}

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
    	holder.name.setText(dataItems.get(position).getName());
    	//holder.icon.setImageResource(R.drawable.ic_chziroy);
    	holder.icon.setImageBitmap(ff(dataItems.get(position).getName()));

    	
    	
    	
    }

    public Bitmap ff(String uid){
    	System.out.println("ff uid = " + uid);
    	VCard vCard = new VCard();
    	SmackConfiguration.setPacketReplyTimeout(300000);
    	ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp",
    	                    new VCardProvider());
    	try {
			vCard.load(activity.smack.getConnection(), uid);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
    	System.out.println(vCard.getEmailHome() + vCard.getJabberId() + "vcard nickname = " + vCard.getNickName());
    	byte[] bs = vCard.getAvatar();   // Avtar in byte array convert it to Bitmap
    	System.out.println("bs length =" + bs);
    	Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
    	return bitmap;
    }
    

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(sView);
	}


	
}


