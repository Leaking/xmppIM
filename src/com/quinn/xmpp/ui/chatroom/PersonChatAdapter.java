package com.quinn.xmpp.ui.chatroom;

import java.io.DataInput;
import java.util.ArrayList;

import com.quinn.xmpp.R;
import com.quinn.xmpp.core.drawer.DownloadAvatarTask;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.BaseDataItem;
import com.quinn.xmpp.ui.contacts.ContactsAdapter.ViewHolder;
import com.quinn.xmpp.ui.drawer.UserInfoAdapter;
import com.quinn.xmpp.ui.main.MainActivity;
import com.quinn.xmpp.ui.widget.RecycleItemClickListener;
import com.quinn.xmpp.ui.widget.RecycleItemLongClickListener;
import com.quinn.xmpp.util.ImageFormatUtils;
import com.quinn.xmpp.util.LogcatUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Quinn
 * @date 2015-3-31
 */
public class PersonChatAdapter extends
		RecyclerView.Adapter<PersonChatAdapter.ViewHolder> {

	private BaseActivity activity;
	private ArrayList<BaseDataItem> dataItems;
	public RecycleItemClickListener mItemClickListener;
	public RecycleItemLongClickListener mItemLongClickListener;
	public byte[] leftPortrait;
	public byte[] rightPortrait;
	public byte[] defaultPortrait;

	public PersonChatAdapter(BaseActivity activity,
			ArrayList<BaseDataItem> dataItems) {
		this.activity = activity;
		this.dataItems = dataItems;
		this.leftPortrait = null;
		this.rightPortrait = null;
		this.defaultPortrait = ImageFormatUtils
				.Bitmap2Bytes(BitmapFactory.decodeResource(
						activity.getResources(), R.drawable.ic_chziroy));
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener, OnLongClickListener {

		//文字
		public ImageView portrait;
		public TextView content;
		//文件
		public ImageView fileIcon;
		public TextView filename;
		public TextView filesize;
	
		
		private RecycleItemClickListener mItemClickListener;
		private RecycleItemLongClickListener mItemLongClickListener;

		public ViewHolder(View view,
				RecycleItemClickListener itemClickListener,
				RecycleItemLongClickListener itemLongClickListener) {
			super(view);
			portrait = (ImageView) view.findViewById(R.id.portrait);
			content = (TextView) view.findViewById(R.id.textMsg);
			this.mItemClickListener = itemClickListener;
			this.mItemLongClickListener = itemLongClickListener;
			view.setOnClickListener(this);
			view.setOnLongClickListener(this);

		}

		@Override
		public boolean onLongClick(View v) {
			return false;
		}

		@Override
		public void onClick(View v) {
		}
	}

	@Override
	public int getItemCount() {
		return dataItems.size();
	}

	public void setPortrait(byte[] portrait, ImageView image){
		Bitmap bitmap = BitmapFactory.decodeByteArray(portrait, 0,
				portrait.length);
		image.setImageBitmap(ImageFormatUtils.toRoundBitmap(
				bitmap, true));
	}
	
	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		// holder.portrait.setImageResource(R.drawable.ic_chziroy);
		boolean flag = false;
		switch (getItemViewType(position)) {
		case BaseDataItem.LEFT_BUBBLE_TEXT:{
			PersonChatDataItem dataitem = (PersonChatDataItem)dataItems.get(position);
			holder.content.setText(dataitem.getTextContent());
			if (leftPortrait != null) {
				setPortrait(leftPortrait,holder.portrait);
				flag = true;
			}
			break;
		}
		case BaseDataItem.RIGHT_BUBBLE_TEXT:{
			PersonChatDataItem dataItem = (PersonChatDataItem)dataItems.get(position);
			holder.content.setText(dataItem.getTextContent());
			if (rightPortrait != null) {
				setPortrait(rightPortrait,holder.portrait);
				flag = true;
			}
			break;
		}
		case BaseDataItem.RIGHT_BUBBLE_FILE:{
			FileDataItem dataItem = (FileDataItem)dataItems.get(position);
			if (rightPortrait != null) {
				setPortrait(rightPortrait,holder.portrait);
				flag = true;
			}
			break;
		}
		case BaseDataItem.LEFT_BUBBLE_FILE:{
			FileDataItem dataItem = (FileDataItem)dataItems.get(position);
			if (leftPortrait != null) {
				setPortrait(leftPortrait,holder.portrait);
				flag = true;
			}
			break;
		}
		default:
			break;
		}

		if (flag)
			return;
		
		new DownloadAvatarTask(activity.getSmack()) {
			@Override
			protected void onPostExecute(Bitmap result) {
				LogcatUtils.i("聊天窗口获取头像； = " + result);
				switch (getItemViewType(position)) {
				case BaseDataItem.LEFT_BUBBLE_TEXT: {
					if (result != null)
						leftPortrait = ImageFormatUtils.Bitmap2Bytes(result);
					else
						leftPortrait = defaultPortrait;
					Bitmap bitmap = BitmapFactory.decodeByteArray(leftPortrait,
							0, leftPortrait.length);
					holder.portrait.setImageBitmap(ImageFormatUtils
							.toRoundBitmap(bitmap, true));
					break;
				}
				case BaseDataItem.RIGHT_BUBBLE_TEXT: {
					if (result != null)
						rightPortrait = ImageFormatUtils.Bitmap2Bytes(result);
					else
						rightPortrait = defaultPortrait;
					Bitmap bitmap = BitmapFactory.decodeByteArray(
							rightPortrait, 0, rightPortrait.length);
					holder.portrait.setImageBitmap(ImageFormatUtils
							.toRoundBitmap(bitmap, true));
					break;
				}
				default:
					break;
				}

			}
		}.execute(dataItems.get(position).getJid());

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final LayoutInflater mInflater = LayoutInflater.from(parent
				.getContext());
		int rsid = 0;
		switch (viewType) {
		case BaseDataItem.LEFT_BUBBLE_TEXT:
			rsid = R.layout.item_personchat_left;
			break;
		case BaseDataItem.RIGHT_BUBBLE_TEXT:
			rsid = R.layout.item_personchat_right;
			break;
		case BaseDataItem.LEFT_BUBBLE_FILE:
			rsid = R.layout.item_personchat_left_file;
			break;
		case BaseDataItem.RIGHT_BUBBLE_FILE:
			rsid = R.layout.item_personchat_right_file;
			break;
		default:
			break;
		}
		final View sView = mInflater.inflate(rsid, parent, false);
		return new ViewHolder(sView, this.mItemClickListener,
				this.mItemLongClickListener);
	}

	@Override
	public int getItemViewType(int position) {
		return dataItems.get(position).getItemType();
	}

	public void setOnItemClickListener(RecycleItemClickListener listener) {
		this.mItemClickListener = listener;
	}

	public void setOnItemLongClickListener(RecycleItemLongClickListener listener) {
		this.mItemLongClickListener = listener;
	}

}
