package com.quinn.xmpp.ui.contacts;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.core.contacts.SearchUserTask;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.ui.widget.RecycleItemClickListener;
import com.quinn.xmpp.ui.widget.SimpleDividerItemDecoration;
import com.quinn.xmpp.util.LogcatUtils;

/**
 * 
 * @author Quinn
 * @date 2015-4-23
 */
public class SearchUserActivity extends BaseActivity implements RecycleItemClickListener {

	private final static String TITLE = "Add Contacts";
	@InjectView((R.id.toolbar))
	Toolbar toolbar;
	@InjectView(R.id.et_user)
	ClearableAutoCompleteTextView et_username;
	@InjectView(R.id.bt_search)
	Button bt_search;
	@InjectView(R.id.searchUserResult)
	RecyclerView recyclerView;

	private RecyclerView.LayoutManager layoutManager;
	private ArrayList<ContactsDataItem> dataItems;
	private SearchUserAdapter adapter;
	private int dividerHeight;
	private int dividerColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_roster);
		ButterKnife.inject(this);
		toolbar.setTitle(TITLE);
		setSupportActionBar(toolbar);
		toolbar.setPopupTheme(R.color.theme_color);
		// 以下三行代码使activity有向上返回的按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		dataItems = new ArrayList<ContactsDataItem>();
		adapter = new SearchUserAdapter(this, dataItems);
		dividerHeight = getResources().getDimensionPixelSize(
				R.dimen.recyclerView_small_divider);
		dividerColor = getResources().getColor(R.color.color_gray);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
				getApplicationContext(), dividerColor, dividerHeight));
		adapter.setOnItemClickListener(this);
	}

	@OnClick(R.id.bt_search)
	void onSearch() {
		LogcatUtils.i("开始搜搜用于");
		new SearchUserTask(smack) {

			@Override
			protected void onPostExecute(ArrayList<String> result) {
				for (String jid : result) {
					ContactsDataItem dataItem = new ContactsDataItem();
					dataItem.setJid(jid);
					dataItems.add(0,dataItem);
				}
				adapter.notifyDataSetChanged();
				et_username.setText("");
			}

		}.execute(et_username.getText().toString());
	}

	public static Intent createIntent() {
		Builder builder = new Builder("main.SearchRoster.View");
		return builder.toIntent();
	}


	@Override
	public void onItemClick(View view, int position) {
		
	}

}
