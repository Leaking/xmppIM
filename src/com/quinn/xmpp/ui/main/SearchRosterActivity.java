package com.quinn.xmpp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.util.LogcatUtils;

public class SearchRosterActivity extends BaseActivity {

	private final static String TITLE = "Add Contacts";
	@InjectView((R.id.toolbar))
	Toolbar toolbar;
	@InjectView(R.id.et_user)
	ClearableAutoCompleteTextView et_username;
	@InjectView(R.id.bt_search)
	Button bt_search;
	
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
	}

	
	
	
	@OnClick(R.id.bt_search)
	void onSearch(){
		LogcatUtils.i("开始搜搜用于");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				smack.searchUser(et_username.getText().toString());
			}
		}).start();
	}
	
	public static Intent createIntent() {
		Builder builder = new Builder("main.SearchRoster.View");
		return builder.toIntent();
	}

}
