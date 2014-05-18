package com.XMPP.Activity.Launcher;




import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.XMPP.R;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;

public class LearnMoreFragment_A extends Fragment {

	View areaGtalk;
	View areaOpenFire;
	ImageView gTalkImage;
	ImageView openFireImage;
	IconicFontDrawable iconYes;
	int chosenOne = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.learnmore_a_fragment, container, false);
        areaGtalk = rootView.findViewById(R.id.server_google);
        areaOpenFire = rootView.findViewById(R.id.server_openfire);
        gTalkImage = (ImageView) rootView.findViewById(R.id.google_talk_image);
        openFireImage = (ImageView) rootView.findViewById(R.id.openfire_image);
        
        init();
        return rootView;
    }
    public void init(){
    	iconYes = new IconicFontDrawable(this.getActivity());
    	iconYes.setIcon(FontAwesomeIcon.OK_SIGN);
    	iconYes.setIconColor(Color.WHITE);
    	
    	areaGtalk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				L.i("click a");
				gTalkImage.setBackground(iconYes);
				openFireImage.setBackground(null);
			}
		});
    	
    	areaOpenFire.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				L.i("click b");

				openFireImage.setBackground(iconYes);
				gTalkImage.setBackground(null);
			}
		});
    }
 
}