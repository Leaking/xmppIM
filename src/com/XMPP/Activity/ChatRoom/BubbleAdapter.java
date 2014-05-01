package com.XMPP.Activity.ChatRoom;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.Model.Message;
import com.XMPP.util.L;

/**
 * AwesomeAdapter is a Custom class to implement custom row in ListView
 * 
 * @author Adil Soomro
 * 
 */
public class BubbleAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Message> mMessages;

	public BubbleAdapter(Context context, ArrayList<Message> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessages.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = (Message) this.getItem(position);
		View view = new View(mContext);
		switch (message.getType()) {
		case TEXT:
			L.i("message text");
			String content = message.getMessage();
			view = (View) View.inflate(mContext, R.layout.bubble_text, null);
			TextView text = (TextView)view.findViewById(R.id.message_text);
			if(message.isMine()){
				LayoutParams lp = (LayoutParams) text.getLayoutParams();
				text.setBackgroundResource(R.drawable.bubble_right);
				lp.gravity = Gravity.RIGHT;
				text.setLayoutParams(lp);
			}else{
				LayoutParams lp = (LayoutParams) text.getLayoutParams();
				text.setBackgroundResource(R.drawable.bubble_left);
				lp.gravity = Gravity.LEFT;
				text.setLayoutParams(lp);
			}
			text.setText(content);
			
			break;
		case PICTURE:
			L.i("message PICTURE");
			int sourceID = message.getPictureID();
			view = (View)View.inflate(mContext, R.layout.bubble_picture, null);
			ImageView image = (ImageView)view.findViewById(R.id.message_picture);
			image.setImageDrawable(mContext.getResources().getDrawable(sourceID));
			break;
		}
//		if(message.isMine()){
//			LayoutParams params = (LayoutParams) view.getLayoutParams();
//			params.gravity = Gravity.RIGHT;
//			view.setLayoutParams(params);
//		}else{
//			LayoutParams params = (LayoutParams) view.getLayoutParams();
//			params.gravity = Gravity.LEFT;
//			view.setLayoutParams(params);
//		}

		return view;
//		holder = (ViewHolder_Text) convertView.getTag();
//
//		holder.message.setText(message.getMessage());
//
//		LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
//		// check if it is a status message then remove background, and change
//		// text color.
//		if (message.getType() == MessageType.STATUS) {
//			holder.message.setBackgroundDrawable(null);
//			lp.topMargin = 40;
//			lp.gravity = Gravity.LEFT;
//		} else {
//			// Check whether message is mine to show green background and align
//			// to right
//			if (message.isMine()) {
//				holder.message.setBackgroundResource(R.drawable.bubble_right);
//				lp.gravity = Gravity.RIGHT;
//			}
//			// If not mine then it is from sender to show orange background and
//			// align to left
//			else {
//				holder.message.setBackgroundResource(R.drawable.bubble_left);
//				lp.gravity = Gravity.LEFT;
//
//			}
//			holder.message.setLayoutParams(lp);
//		}
//		return convertView;
	}

	@Override
	public long getItemId(int position) {
		// Unimplemented, because we aren't using Sqlite.
		return position;
	}

}
