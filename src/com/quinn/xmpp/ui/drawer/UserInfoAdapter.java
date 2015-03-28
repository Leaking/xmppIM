package com.quinn.xmpp.ui.drawer;

import android.content.Context;
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

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.widget.RecycleItemClickListener;
import com.quinn.xmpp.ui.widget.RecycleItemLongClickListener;

/**
 * @author Quinn
 * @date 2015-3-24
 * 
 *       Adapter of userInfoRecyclerView
 */
public class UserInfoAdapter extends
		RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {

	private final static int TYPE_IAMGE = 0;
	private final static int TYPE_TEXT = 1;
	public RecycleItemClickListener mItemClickListener;
	public RecycleItemLongClickListener mItemLongClickListener;

	private UserVCard userVCard;

	public UserInfoAdapter(UserVCard userVCard) {
		this.userVCard = userVCard;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener, OnLongClickListener {

		public TextView info_name;
		public TextView info_value_text;
		public ImageView info_value_icon;
		private RecycleItemClickListener mItemClickListener;
		private RecycleItemLongClickListener mItemLongClickListener;

		public ViewHolder(View view, int viewType,
				RecycleItemClickListener itemClickListener,
				RecycleItemLongClickListener itemLongClickListener) {
			super(view);
			info_name = (TextView) view.findViewById(R.id.name);
			if (viewType == TYPE_IAMGE)
				info_value_icon = (ImageView) view.findViewById(R.id.icon);
			else
				info_value_text = (TextView) view.findViewById(R.id.value);
			mItemClickListener = itemClickListener;
			mItemLongClickListener = itemLongClickListener;
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
	public int getItemViewType(int position) {
		return position == 0 ? TYPE_IAMGE : TYPE_TEXT;
	}

	@Override
	public int getItemCount() {
		return UserVCard.FIELDS_SUM;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		switch (position) {
		case 0:
			holder.info_name.setText("头像");
			if (userVCard.getAvatarBytes() != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(
						userVCard.getAvatarBytes(), 0,
						userVCard.getAvatarBytes().length);
				holder.info_value_icon.setImageBitmap(bitmap);
			} else {
				holder.info_value_icon.setImageResource(R.drawable.ic_launcher);
			}
			break;
		case 1:
			holder.info_name.setText("昵称");
			holder.info_value_text.setText(userVCard.getNickname());
			break;
		case 2:
			holder.info_name.setText("二维码名片");

			break;
		case 3:
			holder.info_name.setText("电子邮箱");
			holder.info_value_text.setText("742223410@qq.com");
			break;

		case 4:
			holder.info_name.setText("性别");
			holder.info_value_text.setText("男");

			break;
		default:
			holder.info_name.setText("待定");

			break;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final LayoutInflater mInflater = LayoutInflater.from(parent
				.getContext());
		View view = null;
		if (viewType == TYPE_IAMGE) {
			view = mInflater.inflate(R.layout.item_login_user_info_avatar,
					parent, false);
		} else {
			view = mInflater.inflate(R.layout.item_login_user_info_text,
					parent, false);
		}
		return new ViewHolder(view, viewType, mItemClickListener,
				mItemLongClickListener);
	}

	public void setOnItemClickListener(RecycleItemClickListener listener) {
		this.mItemClickListener = listener;
	}

	public void setOnItemLongClickListener(RecycleItemLongClickListener listener) {
		this.mItemLongClickListener = listener;
	}
}
