package com.XMPP.Activity.ChatRoom;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.XMPP.R;
import com.XMPP.Model.IconOnTouchListener;
import com.XMPP.util.Constants;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoSocialIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;

public class ChatRoomActivity extends Activity {

	ImageView face;
	ImageView plus;
	Button send;
	LinearLayout rootLayout;
	LinearLayout footLayout;
	Plus_appear_Listener appear_listener;
	Plus_disappear_Listener disappear_listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chatroom);
		init();
	}

	public void init() {
		face = (ImageView) findViewById(R.id.face);
		plus = (ImageView) findViewById(R.id.plus);
		send = (Button) findViewById(R.id.send);
		rootLayout = (LinearLayout) findViewById(R.id.root);

		IconicFontDrawable iconfont = new IconicFontDrawable(this);
		iconfont.setIcon(FontAwesomeIcon.PLUS);
		iconfont.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus.setBackground(iconfont);
		appear_listener = new Plus_appear_Listener();
		disappear_listener = new Plus_disappear_Listener();
		plus.setOnTouchListener(new IconOnTouchListener(iconfont, plus));
		plus.setOnClickListener(appear_listener);
	}

	class Plus_appear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("appear");
			footLayout = getFootLayout();
			rootLayout.addView(footLayout);
			plus.setOnClickListener(disappear_listener);
		}

	}

	

	class Plus_disappear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			rootLayout.removeView(footLayout);
			plus.setOnClickListener(appear_listener);

		}

	}
	public LinearLayout getFootLayout() {
		LinearLayout footLayout = new LinearLayout(ChatRoomActivity.this);
		footLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		footLayout.setOrientation(0);
		footLayout.setPadding(20, 0, 20, 0);

		for (int i = 0; i < 5; i++) {
			Button imageview = new Button(ChatRoomActivity.this);
			imageview.setLayoutParams(new LayoutParams(150, 150));
			IconicFontDrawable iconDraw = new IconicFontDrawable(
					ChatRoomActivity.this);
			iconDraw.setIcon(EntypoSocialIcon.C_GITHUB);
			iconDraw.setIconColor(Color.RED);
			imageview.setBackground(iconDraw);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			layoutParams.rightMargin = 20;
			imageview.setLayoutParams(layoutParams);
			footLayout.addView(imageview);
		}
		return footLayout;
	}
}
