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
import com.quinn.xmpp.core.drawer.DownloadAvatarTask;
import com.quinn.xmpp.ui.main.MainActivity;
import com.quinn.xmpp.util.ImageUtils;

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
    public void onBindViewHolder(final ViewHolder holder , int position) {
    	holder.name.setText(dataItems.get(position).getName());
    	//在这里设置一个默认头像
    	holder.icon.setImageResource(R.drawable.ic_chziroy);
//    	new DownloadAvatarTask(activity.getSmack()){
//			@Override
//			protected void onPostExecute(Bitmap result) {			
//				if(result != null)
//					holder.icon.setImageBitmap(ImageUtils.toRoundBitmap(result, true));
//			}
//    	}.execute(dataItems.get(position).getName());
    }


    

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(sView);
	}


	
}


