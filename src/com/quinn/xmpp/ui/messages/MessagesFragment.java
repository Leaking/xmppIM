/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.messages;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.launch.NetWorkSettingActivity;
import com.quinn.xmpp.ui.main.MainActivity;
import com.quinn.xmpp.ui.widget.RecycleItemClickListener;
import com.quinn.xmpp.ui.widget.RecycleItemLongClickListener;
import com.quinn.xmpp.ui.widget.SimpleDividerItemDecoration;
import com.quinn.xmpp.util.ToastUtils;

/**
 * @author Quinn
 * @date 2015-2-28
 * 
 * @description A Fragment to show the latest message(chatting to somebody, subscription to add friend,etc.)
 */
public class MessagesFragment extends Fragment implements RecycleItemClickListener, RecycleItemLongClickListener{

	
	@InjectView(R.id.messages_recycle_view)
	RecyclerView mRecyclerView;
	private MessagesAdapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private MainActivity mActivity;
	private ArrayList<MessagesDataItem> contactDataItems;
	
	//
	private int dividerHeight;
	private int dividerColor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactDataItems = new ArrayList<MessagesDataItem>();
		/* test data */
		for (int i = 0; i < 40; i++) {
			contactDataItems.add(new MessagesDataItem(null, "周杰伦","挺妈妈的话，啦啦啦啦啦啦啦","昨天20:38"));
		}
		/* test data */
		
		dividerHeight = mActivity.getResources().getDimensionPixelSize(R.dimen.recyclerView_small_divider);
		dividerColor = mActivity.getResources().getColor(R.color.color_gray);
	
		mAdapter = new MessagesAdapter(mActivity,contactDataItems);
		mAdapter.setOnItemClickListener(this);
		mAdapter.setOnItemLongClickListener(this);

	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		setRetainInstance(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_messages, container,
				false);
		ButterKnife.inject(this, view);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
				mActivity.getApplicationContext(),dividerColor,dividerHeight
	        ));
		
		return view;
	}
	

	public void addContactDataItem(MessagesDataItem item){
		contactDataItems.add(item);
		mAdapter.notifyDataSetChanged();
	}

	
	@Override
	public void onItemLongClick(View view, int position) {
		
	}

	
	@Override
	public void onItemClick(View view, int position) {
		Intent intent = PersonChatActivity.createIntent();
		mActivity.startActivity(intent);
	}

	
}
