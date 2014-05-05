package com.XMPP.Activity.Mainview;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.Activity.ChatRoom.ChatRoomActivity;
import com.XMPP.Database.RowChatting;
import com.XMPP.Database.TableChatting;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.CircleImage;

public class ChattingFragment extends Fragment implements OnItemClickListener {

	Smack smack;
	ListView listView;
	ChattingAdapter adapter;
	AdapterRefreshReceiver aReceiver;
	IntentFilter filter;
	public static final String ACTION_FRESH_CHATTING_LISTVIEW = "fresh_chatting_listview";
	
	
	ArrayList<RowChatting> listData;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_chatting, container,
				false);
		smack = SmackImpl.getInstance();
		listData = getListData();
		listView = (ListView) view.findViewById(R.id.chatting_List);
		adapter = new ChattingAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		filter = new IntentFilter();
		aReceiver = new AdapterRefreshReceiver();
		filter.addAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
		this.getActivity().registerReceiver(aReceiver, filter);
		return view;
	}
	
	public ArrayList<RowChatting> getListData(){
		TableChatting table = TableChatting.getInstance(this.getActivity());
		return table.select(smack.getJID());
	}
	
	class AdapterRefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			listData = getListData();
			adapter.notifyDataSetChanged();
			listView.setSelection(listData.size() - 1);
		}
	}
	class ChattingAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = View.inflate(ChattingFragment.this.getActivity(),
						R.layout.chatting_list_item, null);
			}
			/**
			 * init the widget
			 */
			ImageView itemImage = (ImageView) convertView
					.findViewById(R.id.chattingItemPhoto);
			TextView unReadNum = (TextView)convertView.findViewById(R.id.notify);
			TextView friendName = (TextView)convertView.findViewById(R.id.itemName);
			TextView lastMsg = (TextView)convertView.findViewById(R.id.lastMSG);
			TextView lastTime = (TextView)convertView.findViewById(R.id.time);
			
			if(listData.get(position).getUnReadNum().equals("0"))
				unReadNum.setVisibility(View.INVISIBLE);
			unReadNum.setText(listData.get(position).getUnReadNum());
			String nickname = smack.getNickname(listData.get(position).getU_JID());
			friendName.setText(nickname);
			lastMsg.setText(listData.get(position).getLastMSG());
			lastTime.setText(listData.get(position).getLastTime());
			Bitmap circleBitmap = CircleImage.toRoundBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.channel_qq),
					true);
			itemImage.setImageBitmap(circleBitmap);

			
			return convertView;
		}

	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String u_jid = listData.get(position).getU_JID();
		Intent intent = new Intent(ChattingFragment.this.getActivity(),ChatRoomActivity.class);
		intent.putExtra("JID",u_jid);
		ChattingFragment.this.getActivity().startActivity(intent);		
	}
}
