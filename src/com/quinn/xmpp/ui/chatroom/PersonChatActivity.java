package com.quinn.xmpp.ui.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.Intents;
import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.contacts.ContactsDataItem;

public class PersonChatActivity extends BaseActivity implements OnRefreshListener {

	@InjectView(R.id.toolbar)
	Toolbar toolbar;
	@InjectView(R.id.userchat_recycle_view)
	RecyclerView mRecyclerView;
	@InjectView(R.id.personchat_swipe_layout)
	SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.chatMsgInput)
	EditText input;
	@InjectView(R.id.chatMsgTextSend)
	Button send;
	
	private String jidChattingWithWho;
	private String nicknameChattingWithWho;
	private RecyclerView.LayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_chat);
		ButterKnife.inject(this);
		jidChattingWithWho = getStringExtra(Intents.EXTRA_JID_CHATTING_WITH_WHO);
		nicknameChattingWithWho = getStringExtra(Intents.EXTRA_NICKNAME_CHATTING_WITH_WHO);
		toolbar.setTitle(nicknameChattingWithWho);
		setSupportActionBar(toolbar);
		// 以下三行代码使activity有向上返回的按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		swipeRefreshLayout.setOnRefreshListener(this);
	}


	@Override
	public void onRefresh() {
		
		swipeRefreshLayout.setRefreshing(false);
	}
	
	/**
	 * Load message before s certain time :date
	 * @param date
	 */
	public void loadOlderChattingMessage(String date){
		
	}
	
	@OnClick(R.id.chatMsgTextSend)
	public void send(){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static Intent createIntent(ContactsDataItem dataitem) {
		Builder builder = new Builder("PersonChat.View")
		.add(Intents.EXTRA_JID_CHATTING_WITH_WHO,dataitem.getJid())
		.add(Intents.EXTRA_NICKNAME_CHATTING_WITH_WHO,dataitem.getNickname());
		return builder.toIntent();
	}
	
}
