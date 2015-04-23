package com.quinn.xmpp.ui.contacts;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quinn.xmpp.R;
import com.quinn.xmpp.core.drawer.DownloadAvatarTask;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.RecycleItemClickListener;
import com.quinn.xmpp.ui.widget.RecycleItemLongClickListener;
import com.quinn.xmpp.util.ImageFormatUtils;

/**
 * @author Quinn
 * @date 2015-3-16
 * @description A adapter class of ContantsFragment's RecyclerView
 */
public class SearchUserAdapter extends
		RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

	private ArrayList<ContactsDataItem> dataItems;
	private BaseActivity activity;
	public RecycleItemClickListener mItemClickListener;
	public RecycleItemLongClickListener mItemLongClickListener;

	public SearchUserAdapter(BaseActivity activity,
			ArrayList<ContactsDataItem> dataItems) {
		this.activity = activity;
		this.dataItems = dataItems;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener, OnLongClickListener {

		public ImageView icon;
		public TextView name;
		private RecycleItemClickListener mItemClickListener;
		private RecycleItemLongClickListener mItemLongClickListener;

		public ViewHolder(View view,
				RecycleItemClickListener itemClickListener,
				RecycleItemLongClickListener itemLongClickListener) {
			super(view);
			icon = (ImageView) view.findViewById(R.id.icon);
			name = (TextView) view.findViewById(R.id.name);
			this.mItemClickListener = itemClickListener;
			this.mItemLongClickListener = itemLongClickListener;
			view.setOnClickListener(this);
			view.setOnLongClickListener(this);
		}

		@Override
		public boolean onLongClick(View v) {
			if (mItemLongClickListener != null)
				mItemLongClickListener.onItemLongClick(v, getPosition());
			return true;
		}

		@Override
		public void onClick(View v) {
			if (mItemClickListener != null) {
				mItemClickListener.onItemClick(v, getPosition());
			}
		}

	}

	@Override
	public int getItemCount() {
		return dataItems.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.name.setText(dataItems.get(position).getJid());
		holder.icon.setImageResource(R.drawable.ic_chziroy);
		new DownloadAvatarTask(activity.getSmack()) {
			@Override
			protected void onPostExecute(Bitmap result) {
				if (result != null)
					holder.icon.setImageBitmap(ImageFormatUtils.toRoundBitmap(
							result, true));
			}
		}.execute(dataItems.get(position).getJid());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final LayoutInflater mInflater = LayoutInflater.from(parent
				.getContext());
		final View sView = mInflater.inflate(R.layout.item_contact, parent,
				false);
		return new ViewHolder(sView, this.mItemClickListener,
				this.mItemLongClickListener);
	}

	public void setOnItemClickListener(RecycleItemClickListener listener) {
		this.mItemClickListener = listener;
	}

	public void setOnItemLongClickListener(RecycleItemLongClickListener listener) {
		this.mItemLongClickListener = listener;
	}

}
