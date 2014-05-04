package com.XMPP.Activity.Mainview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.Model.CornerListView;
import com.XMPP.smack.Smack;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;

public class SettingFragment extends Fragment {
	Smack smack;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_setting, container,
				false);
		CornerListView list = (CornerListView) view
				.findViewById(R.id.setting_list);
		list.setAdapter(new MBaseAdapter());

		return view;
	}
	
	
	
	class MBaseAdapter extends BaseAdapter {
		String[] items = { "Profile", "Photos", "About us" };

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LinearLayout ll = (LinearLayout) View.inflate(
					SettingFragment.this.getActivity(),
					R.layout.fragment_setting_item, null);
			ImageView itemArrow = (ImageView) ll.findViewById(R.id.item_arrow);
			TextView itemText = (TextView) ll.findViewById(R.id.item_content);
			itemText.setText(items[arg0]);

			IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(
					SettingFragment.this.getActivity());

			iconicFontDrawable.setIcon(EntypoIcon.CHEVRON_THIN_RIGHT);
			iconicFontDrawable.setIconColor(getResources().getColor(
					com.XMPP.R.color.group_arrow_closed));
			itemArrow.setBackground(iconicFontDrawable);

			return ll;

		}

	}
}
