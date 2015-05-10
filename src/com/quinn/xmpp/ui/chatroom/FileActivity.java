package com.quinn.xmpp.ui.chatroom;

import android.content.Intent;
import android.os.Bundle;
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
import com.quinn.xmpp.R.id;
import com.quinn.xmpp.R.layout;
import com.quinn.xmpp.R.menu;
import com.quinn.xmpp.ui.BaseActivity;

/**
 * 
 * @author Quinn
 * @date 2015-4-29
 */
public class FileActivity extends BaseActivity {

	@InjectView(R.id.toolbar)
	Toolbar toolbar;
	@InjectView(R.id.file_recycle_view)
	RecyclerView mRecyclerView;
	
	private RecyclerView.LayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		ButterKnife.inject(this);
		toolbar.setTitle("文件选择");
		setSupportActionBar(toolbar);
		// 以下三行代码使activity有向上返回的按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file, menu);
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

	public static Intent createIntent() {
		Builder builder = new Builder("chatroom.File.View");
		return builder.toIntent();
	}
}
