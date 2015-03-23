package com.quinn.xmpp.ui.drawer;

import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaCodec.BufferInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.atermenji.android.iconicdroid.icon.Icon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;
import com.atermenji.android.iconicdroid.util.TypefaceManager.IconicTypeface;
import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseDataItem;
import com.quinn.xmpp.ui.main.MainActivity;
import com.quinn.xmpp.ui.messages.MessagesDataItem;

/**
 * 
 * 
 * @author Quinn
 * @date 2015-1-24
 */
public class DrawerFragment extends Fragment {

	@InjectView(R.id.drawListView)
	ListView listview;
	private DrawerAdapter drawerAdapter;
	private ArrayList<DrawerDataItem> drawerDataItems;
	private MainActivity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		setRetainInstance(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawerDataItems = new ArrayList<DrawerDataItem>();
		// test data
		DrawerDataItem headerItem = new DrawerDataItem();
		headerItem.setItemType(BaseDataItem.DRAWERITEM_TYPE_HEADER);
		drawerDataItems.add(headerItem);

		DrawerDataItem warningItem = new DrawerDataItem(EntypoIcon.BELL, "提醒");
		warningItem.setItemType(BaseDataItem.DRAWERITEM_TYPE_FUNCTION);
		drawerDataItems.add(warningItem);

		DrawerDataItem themeItem = new DrawerDataItem(FontAwesomeIcon.DASHBOARD, "主題");
		themeItem.setItemType(BaseDataItem.DRAWERITEM_TYPE_FUNCTION);
		drawerDataItems.add(themeItem);
		
		DrawerDataItem settingItem = new DrawerDataItem(IconicIcon.COG, "设置");
		settingItem.setItemType(BaseDataItem.DRAWERITEM_TYPE_FUNCTION);
		drawerDataItems.add(settingItem);
		
		DrawerDataItem aboutItem = new DrawerDataItem(IconicIcon.COMPASS, "关于");
		aboutItem.setItemType(BaseDataItem.DRAWERITEM_TYPE_FUNCTION);
		drawerDataItems.add(aboutItem);
		
		
		
		
		
		
		drawerAdapter = new DrawerAdapter(mActivity, drawerDataItems);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_draw, container, false);
		ButterKnife.inject(this, view);
		listview.setAdapter(drawerAdapter);
		return view;
	}

}
