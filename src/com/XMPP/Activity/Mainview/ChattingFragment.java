package com.XMPP.Activity.Mainview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.util.CircleImage;

public class ChattingFragment extends Fragment {

	ListView listview;
	ChattingAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_chatting, container,
				false);
		listview = (ListView) view.findViewById(R.id.chatting_List);
		adapter = new ChattingAdapter();
		listview.setAdapter(adapter);
		return view;
	}

	class ChattingAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
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
			Bitmap circleBitmap = CircleImage.toRoundBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.channel_qq),
					true);
			itemImage.setImageBitmap(circleBitmap);

			
			
			return convertView;
		}

	}
}
