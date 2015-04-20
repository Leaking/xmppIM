package com.quinn.xmpp.ui.chatroom;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.quinn.xmpp.core.chatroom.TextMessageListener;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.BaseDataItem;
import com.quinn.xmpp.ui.contacts.ContactsDataItem;
import com.quinn.xmpp.util.DisplayUtils;
import com.quinn.xmpp.util.LogcatUtils;
import com.quinn.xmpp.util.TimeUtils;

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
	private String serviceChattingWithWho;
	private RecyclerView.LayoutManager mLayoutManager;
	private TextMessageListener textMessageListener;
	private ArrayList<PersonChatDataItem> dataItems;
	private PersonChatAdapter adapter;
	private Chat chat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_chat);
		ButterKnife.inject(this);
		jidChattingWithWho = getStringExtra(Intents.EXTRA_JID_CHATTING_WITH_WHO);
		nicknameChattingWithWho = getStringExtra(Intents.EXTRA_NICKNAME_CHATTING_WITH_WHO);
		serviceChattingWithWho = getStringExtra(Intents.EXTRA_SERVICE_CHATTING_WITH_WHO);
		LogcatUtils.v("Come in to a person-chatroom");
		LogcatUtils.v("jidChattingWithWho = " + jidChattingWithWho);
		LogcatUtils.v("serviceChattingWithWho = " + serviceChattingWithWho);

		
		toolbar.setTitle(nicknameChattingWithWho);
		setSupportActionBar(toolbar);
		// 以下三行代码使activity有向上返回的按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mLayoutManager = new LinearLayoutManager(this);
		dataItems = new ArrayList<PersonChatDataItem>();
		mRecyclerView.setLayoutManager(mLayoutManager);
		swipeRefreshLayout.setOnRefreshListener(this);
		adapter = new PersonChatAdapter(this,dataItems);
		mRecyclerView.setAdapter(adapter);
		
		init();
	}
	
	public void init(){
		ChatManager chatManager = smack.getConnection().getChatManager();
		textMessageListener = new TextMessageListener();
		
		//收到消息后，要发送到handler里更新，否则需要打开输入法或者关闭输入法，才会刷新
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);
				adapter.notifyDataSetChanged();
			}
		};
		
		
		
		//这里需要full id  用addPacketListener可以获得
		chat = chatManager.createChat(jidChattingWithWho+"/"+serviceChattingWithWho, new MessageListener() {

			@Override
			public void processMessage(Chat chat, Message message) {
				LogcatUtils.v("receive msg = " + message.getBody());
				//message.get
				PersonChatDataItem dataItem = new PersonChatDataItem();
				dataItem.setTextContent(message.getBody());
				dataItem.setJid(jidChattingWithWho);
				dataItem.setNickname(smack.getContactData(jidChattingWithWho).getNickname());
				dataItem.setHappenTime(TimeUtils.getCurrentTime2String());
				dataItem.setItemType(BaseDataItem.LEFT_BUBBLE_TEXT);
				dataItems.add(dataItem);
				handler.sendEmptyMessage(1);
			}});
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
	public void onSend(){
		try {
			chat.sendMessage(input.getText().toString());
			PersonChatDataItem dataItem = new PersonChatDataItem();
			dataItem.setHappenTime(TimeUtils.getCurrentTime2String());
			dataItem.setTextContent(input.getText().toString());
			dataItem.setJid(smack.getUserVCard().getJid());
			dataItem.setItemType(BaseDataItem.RIGHT_BUBBLE_TEXT);
			dataItems.add(dataItem);
			adapter.notifyDataSetChanged();
			input.setText("");
			DisplayUtils.closeInputMethod(this);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		.add(Intents.EXTRA_NICKNAME_CHATTING_WITH_WHO,dataitem.getNickname())
		.add(Intents.EXTRA_SERVICE_CHATTING_WITH_WHO,dataitem.getService());
		return builder.toIntent();
	}
	
}
