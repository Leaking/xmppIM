package com.quinn.xmpp.ui.contacts;


import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quinn.xmpp.R;

/**
 * @author Quinn
 * @date 2015-3-16
 * @description A adapter class of ContantsFragment's RecyclerView
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

	private ArrayList<ContactsDataItem> dataItems;
	
	public ContactsAdapter(ArrayList<ContactsDataItem> dataItems){
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
    }


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(sView);
	}


	
}


