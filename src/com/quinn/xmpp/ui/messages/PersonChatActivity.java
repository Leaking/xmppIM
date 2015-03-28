package com.quinn.xmpp.ui.messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.quinn.xmpp.Intents;
import com.quinn.xmpp.R;
import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.contacts.ContactsDataItem;
import com.quinn.xmpp.ui.drawer.UserVCard;

public class PersonChatActivity extends BaseActivity {

	@InjectView(R.id.toolbar)
	Toolbar toolbar;
	@InjectView(R.id.userchat_recycle_view)
	RecyclerView mRecyclerView;
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
		// mRecyclerView.setHasFixedSize(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
