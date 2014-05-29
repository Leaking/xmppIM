package com.XMPP.Activity.Launcher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import com.XMPP.R;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;

public class More_NetWorkSettingFragment extends Fragment {

	Smack smack;
	View areaGtalk;
	View areaOpenFire;
	ImageView gTalkImage;
	ImageView openFireImage;
	EditText ipText;
	IconicFontDrawable iconYes;
	int chosenOne = 2;
	private final static String GTALK_SERVER = "talk.google.com";
	private final static String GTALK_SERVICE = "gmail.com";
	private final static String OPENFIRE_SERVICE = "Smack";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.learnmore_a_fragment, container, false);
		areaGtalk = rootView.findViewById(R.id.server_google);
		areaOpenFire = rootView.findViewById(R.id.server_openfire);
		gTalkImage = (ImageView) rootView.findViewById(R.id.google_talk_image);
		openFireImage = (ImageView) rootView.findViewById(R.id.openfire_image);
		ipText = (EditText) rootView.findViewById(R.id.server_ip);
		init();
		
		
		return rootView;
	}

	public void init() {
		smack = SmackImpl.getInstance();
		iconYes = new IconicFontDrawable(this.getActivity());
		iconYes.setIcon(FontAwesomeIcon.OK_SIGN);
		iconYes.setIconColor(Color.WHITE);
		openFireImage.setBackground(iconYes);
		gTalkImage.setBackground(null);
		showUp(ipText);
		Constants.SERVICE = OPENFIRE_SERVICE;
		
		areaGtalk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				smack.setServerMode(Constants.MODE_GTALK);
				gTalkImage.setBackground(iconYes);
				openFireImage.setBackground(null);
				fadeAway(ipText);
				Constants.SERVER_IP = GTALK_SERVER;
				Constants.SERVICE = GTALK_SERVICE;
				smack.setServerMode(Constants.MODE_GTALK);

			}
		});

		areaOpenFire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				smack.setServerMode(Constants.MODE_OPENFIRE);
				openFireImage.setBackground(iconYes);
				gTalkImage.setBackground(null);
				showUp(ipText);
				Constants.SERVICE = OPENFIRE_SERVICE;
				smack.setServerMode(Constants.MODE_OPENFIRE);
			}
		});
		ipText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Constants.SERVER_IP = s.toString();
			}
		});
	}

	public void fadeAway(View view) {
		AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
		animation1.setDuration(1000);
		view.startAnimation(animation1);
		view.setVisibility(View.INVISIBLE);
	}
	public void showUp(View view){
		AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
		animation1.setDuration(1000);
		view.startAnimation(animation1);
		view.setVisibility(View.VISIBLE);
		
	}
}