package com.quinn.xmpp.ui.chatroom;

import com.quinn.xmpp.ui.drawer.UserInfoAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Quinn
 * @date 2015-3-31
 */
public class PersonChatAdapter extends
		RecyclerView.Adapter<PersonChatAdapter.ViewHolder> {

	
	
	
	
	
	public static class ViewHolder extends RecyclerView.ViewHolder {

		/**
		 * @param view
		 */
		public ViewHolder(View view) {
			super(view);

		}

	}

	@Override
	public int getItemCount() {
		return 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		return null;
	}

}
