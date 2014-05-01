package com.XMPP.Activity.ChatRoom;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.XMPP.R;
import com.XMPP.Model.IconOnTouchListener;
import com.XMPP.Model.Message;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.SystemUtil;
import com.XMPP.util.ValueUtil;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

public class ChatRoomActivity extends FragmentActivity implements
		EmojiconGridFragment.OnEmojiconClickedListener,
		EmojiconsFragment.OnEmojiconBackspaceClickedListener {

	ImageView face;
	ImageView plus;
	EditText input;
	ImageView send;
	LinearLayout rootLayout;
	//
	LinearLayout plusLayout;
	View faceLayout;
	boolean open_plus = false;
	boolean open_face = false;
	//
	ListView bubbleList;
	Plus_appear_Listener plus_appear_listener;
	Plus_disappear_Listener plus_disappear_listener;
	Face_appear_Listener face_appear_listener;
	Face_disappear_Listener face_disappear_listener;
	ArrayList<Message> messages;
	BubbleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chatroom);
		init();

		bubbleList = (ListView) findViewById(R.id.bubbleList);

		messages = new ArrayList<Message>();
		messages.add(new Message("Hello", true));
		messages.add(new Message("HelloHelloHelloHello", true));
		messages.add(new Message("HelloHelloHelloHello", false));

		adapter = new BubbleAdapter(this, messages);
		bubbleList.setAdapter(adapter);

	}

	public void init() {

		face = (ImageView) findViewById(R.id.face);
		plus = (ImageView) findViewById(R.id.plus);
		input = (EditText) findViewById(R.id.input);
		send = (ImageView) findViewById(R.id.send);
		rootLayout = (LinearLayout) findViewById(R.id.root);
		send.setOnClickListener(new Send_Listener());

		plusLayout = getPlusLayout();
		faceLayout = getFaceView();

		IconicFontDrawable icon_face = new IconicFontDrawable(this);
		icon_face.setIcon(FontAwesomeIcon.GITHUB_ALT);
		icon_face.setIconColor(Constants.COLOR_COMMON_BLUE);
		face.setBackground(icon_face);

		IconicFontDrawable icon_plus = new IconicFontDrawable(this);
		icon_plus.setIcon(FontAwesomeIcon.RESIZE_FULL);
		icon_plus.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus.setBackground(icon_plus);

		IconicFontDrawable icon_send = new IconicFontDrawable(this);
		icon_send.setIcon(FontAwesomeIcon.PLAY);
		icon_send.setIconColor(Constants.COLOR_COMMON_BLUE);
		send.setBackground(icon_send);
		
		
		face_appear_listener = new Face_appear_Listener();
		face_disappear_listener = new Face_disappear_Listener();
		plus_appear_listener = new Plus_appear_Listener();
		plus_disappear_listener = new Plus_disappear_Listener();

		//input.setOnFocusChangeListener(new EditTextFocusChangeListener());
		input.setOnTouchListener(new EditOnTouchListener());
		input.addTextChangedListener(new ChatTextChangeListener());
		face.setOnTouchListener(new IconOnTouchListener(icon_face, face));
		plus.setOnTouchListener(new IconOnTouchListener(icon_plus, plus));
		face.setOnClickListener(face_appear_listener);
		plus.setOnClickListener(plus_appear_listener);
	}
	
	class EditOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			close_Face();
			close_Plus();
			return false;
		}
		
	}

	class Send_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String inputContent = input.getText().toString();
			Message message = new Message(inputContent, true);
			messages.add(message);
			adapter.notifyDataSetChanged();
			bubbleList.setSelection(messages.size() - 1);
			input.setText(null);
		}

	}

	class Plus_appear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			IconicFontDrawable icon_close = new IconicFontDrawable(
					ChatRoomActivity.this);
			icon_close.setIcon(FontAwesomeIcon.RESIZE_SMALL);
			icon_close.setIconColor(Constants.COLOR_COMMON_BLUE);
			plus.setBackground(icon_close);
			plus.setOnTouchListener(new IconOnTouchListener(icon_close, plus));
			
			SystemUtil.closeInputMethod(ChatRoomActivity.this);
			close_Face();
			open_plus = true;
			rootLayout.addView(plusLayout);
			plus.setOnClickListener(plus_disappear_listener);
		}

	}

	class Plus_disappear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			IconicFontDrawable icon_open = new IconicFontDrawable(
					ChatRoomActivity.this);
			icon_open.setIcon(FontAwesomeIcon.RESIZE_FULL);
			icon_open.setIconColor(Constants.COLOR_COMMON_BLUE);
			plus.setBackground(icon_open);
			plus.setOnTouchListener(new IconOnTouchListener(icon_open, plus));

			open_plus = false;
			rootLayout.removeView(plusLayout);
			plus.setOnClickListener(plus_appear_listener);

		}

	}

	class Face_appear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			SystemUtil.closeInputMethod(ChatRoomActivity.this);
			close_Plus();
			open_face = true;
			rootLayout.addView(faceLayout);
			face.setOnClickListener(new Face_disappear_Listener());
		}

	}

	class Face_disappear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			open_face = false;
			rootLayout.removeView(faceLayout);
			face.setOnClickListener(new Face_appear_Listener());
		}

	}

	public View getFaceView() {
		View faceView = new View(ChatRoomActivity.this);

		faceView = (View) View.inflate(this, R.layout.face_layout, null);
		return faceView;
	}

	public LinearLayout getPlusLayout() {
		LinearLayout footLayout = new LinearLayout(ChatRoomActivity.this);

		int plusHeight = (int) ValueUtil.convertDpToPixel(50,
				ChatRoomActivity.this);

		footLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				plusHeight));
		footLayout.setOrientation(0);
		footLayout.setPadding(20, 20, 20, 20);

		Button plus_picture = new Button(ChatRoomActivity.this);
		Button plus_camera = new Button(ChatRoomActivity.this);
		Button plus_file = new Button(ChatRoomActivity.this);
		Button plus_video = new Button(ChatRoomActivity.this);
		Button plus_locate = new Button(ChatRoomActivity.this);
		int size = (int) ValueUtil.convertDpToPixel(5, ChatRoomActivity.this);
		plus_picture.setLayoutParams(new LayoutParams(size, size));
		plus_camera.setLayoutParams(new LayoutParams(size, size));
		plus_file.setLayoutParams(new LayoutParams(size, size));
		plus_video.setLayoutParams(new LayoutParams(size, size));
		plus_locate.setLayoutParams(new LayoutParams(size, size));

		IconicFontDrawable icon_picture = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_picture.setIcon(EntypoIcon.PICTURE);
		icon_picture.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_picture.setBackground(icon_picture);
		plus_picture.setOnTouchListener(new IconOnTouchListener(icon_picture,
				plus_picture));

		IconicFontDrawable icon_camera = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_camera.setIcon(EntypoIcon.CAMERA);
		icon_camera.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_camera.setBackground(icon_camera);
		plus_camera.setOnTouchListener(new IconOnTouchListener(icon_camera,
				plus_camera));

		IconicFontDrawable icon_video = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_video.setIcon(FontAwesomeIcon.FACETIME_VIDEO);
		icon_video.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_video.setBackground(icon_video);
		plus_video.setOnTouchListener(new IconOnTouchListener(icon_video,
				plus_video));

		IconicFontDrawable icon_file = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_file.setIcon(FontAwesomeIcon.FOLDER_OPEN);
		icon_file.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_file.setBackground(icon_file);
		plus_file.setOnTouchListener(new IconOnTouchListener(icon_file,
				plus_file));

		IconicFontDrawable icon_locate = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_locate.setIcon(IconicIcon.LOCATION);
		icon_locate.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_locate.setBackground(icon_locate);
		plus_locate.setOnTouchListener(new IconOnTouchListener(icon_locate,
				plus_locate));

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.rightMargin = 7;
		plus_picture.setLayoutParams(layoutParams);
		plus_camera.setLayoutParams(layoutParams);
		plus_file.setLayoutParams(layoutParams);
		plus_video.setLayoutParams(layoutParams);
		plus_locate.setLayoutParams(layoutParams);

		footLayout.addView(plus_picture);
		footLayout.addView(plus_camera);
		footLayout.addView(plus_file);
		footLayout.addView(plus_video);
		footLayout.addView(plus_locate);

		return footLayout;
	}

	
	public void close_Plus(){
		if(open_plus)
			rootLayout.removeView(plusLayout);
	}
	public void close_Face(){
		if(open_face)
			rootLayout.removeView(faceLayout);
	}
	
	
	@Override
	public void onEmojiconBackspaceClicked(View v) {
		// TODO Auto-generated method stub
		EmojiconsFragment.backspace(input);
	}

	@Override
	public void onEmojiconClicked(Emojicon emojicon) {
		// TODO Auto-generated method stub
		EmojiconsFragment.input(input, emojicon);
	}
}
