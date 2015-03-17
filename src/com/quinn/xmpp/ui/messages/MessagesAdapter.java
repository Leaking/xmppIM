package com.quinn.xmpp.ui.messages;

import java.util.ArrayList;

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
public class MessagesAdapter extends
		RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

	private ArrayList<MessagesDataItem> dataItems;
	private MainActivity activity;

	public MessagesAdapter(MainActivity activity,
			ArrayList<MessagesDataItem> dataItems) {
		this.activity = activity;
		this.dataItems = dataItems;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView icon;
		public TextView title;
		public TextView previewWords;
		public TextView timeStamp;

		public ViewHolder(View view) {
			super(view);
			icon = (ImageView) view.findViewById(R.id.messageIcon);
			title = (TextView) view.findViewById(R.id.title);
			previewWords = (TextView) view.findViewById(R.id.previewWords);
			timeStamp = (TextView) view.findViewById(R.id.timeStamp);
		}

	}

	@Override
	public int getItemCount() {
		return dataItems.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder , int position) {
    	holder.icon.setImageResource(R.drawable.ic_chziroy);	
		holder.title.setText(dataItems.get(position).getTitle());
		holder.previewWords.setText(dataItems.get(position).getPreviewWords());
		holder.timeStamp.setText(dataItems.get(position).getTimeStamp());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_message, parent, false);
        return new ViewHolder(sView);
	}

}
