package com.XMPP.Activity.Mainview;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.VCard;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.Model.ViewRoster;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.CircleImage;
import com.XMPP.util.L;
import com.XMPP.util.Test;
import com.XMPP.util.ValueUtil;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;

public class ContactsFragment extends Fragment implements OnChildClickListener {
	
	ViewRoster viewRoster;
	
	String[] groups_Name;
	String[][] items_Name;
	Smack smack;
	ExpandableListAdapter expandAdapter;
	AdapterReceiver aReceiver;
	public static final String UPDATE_LIST_ACTION = "com.XMPP.action.UPDATE_lIST";

	// Container Activity must implement this interface


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_contacts, container,
				false);
		ExpandableListView expandableListView = (ExpandableListView) view
				.findViewById(R.id.contactExpandableList);
		smack = SmackImpl.getInstance();
		
		aReceiver = new AdapterReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_LIST_ACTION);
		getActivity().registerReceiver(aReceiver, filter);


		Test.output1levelString(groups_Name);
		Test.output2levelString(items_Name);
		Test.outputConnectedUser(smack);

		expandAdapter = new mBaseExpandableListAdapter(groups_Name, items_Name);
		// groups = getGroupsName(groupList);
		expandableListView.setAdapter(expandAdapter);
		expandableListView.setOnChildClickListener(this);

		return view;
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	class AdapterReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			L.i("accept broadcast receiver  ");
			((BaseExpandableListAdapter) expandAdapter).notifyDataSetChanged();
		}

	}

	// ExpandableListAdapter expandAdapter = new mBaseExpandableListAdapter();
	class mBaseExpandableListAdapter extends BaseExpandableListAdapter {
		public mBaseExpandableListAdapter(String[] groups, String[][] items) {
			this.groups = groups;
			this.items = items;
		}

		// names of the groups
		private String[] groups;
		// names of the items
		private String[][] items;

		// ��дExpandableListAdapter�еĸ�������
		@Override
		public int getGroupCount() {
			return groups.length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return items[groupPosition].length;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return items[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			LinearLayout ll = (LinearLayout) View.inflate(
					ContactsFragment.this.getActivity(),
					R.layout.expand_list_title, null);
			ImageView arrowImage = (ImageView) ll
					.findViewById(R.id.listTitle_arrow);
			TextView groupName = (TextView) ll
					.findViewById(R.id.listTitle_groupname);
			groupName.setText(getGroup(groupPosition).toString());
			IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(
					ContactsFragment.this.getActivity());
			if (!isExpanded) {
				iconicFontDrawable.setIcon(EntypoIcon.CHEVRON_THIN_RIGHT);
				iconicFontDrawable.setIconColor(getResources().getColor(
						com.XMPP.R.color.group_arrow_closed));
			} else {
				iconicFontDrawable.setIcon(EntypoIcon.CHEVRON_THIN_DOWN);
				iconicFontDrawable.setIconColor(getResources().getColor(
						com.XMPP.R.color.group_arrow_open));
			}
			arrowImage.setBackground(iconicFontDrawable);
			return ll;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			LinearLayout ll = (LinearLayout) View.inflate(
					ContactsFragment.this.getActivity(),
					R.layout.expand_list_item, null);
			//
			TextView itemName = (TextView) ll.findViewById(R.id.itemName);

			String user = getChild(groupPosition, childPosition).toString();
			boolean ifonline = online(user);

			VCard vcard = new VCard();

			try {

				vcard.load(ConnectionHandler.getConnection(), user);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String nickname = vcard.getNickName();
			// somebody have set a nickname,somebody have not
			if (nickname == null) {
				itemName.setText(ValueUtil.getItemName(user));
			} else {
				itemName.setText(vcard.getNickName());
			}
			ImageView itemImage = (ImageView) ll
					.findViewById(R.id.groupItemPhoto);
			Bitmap circleBitmap = CircleImage.toRoundBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.channel_qq),
					ifonline);
			itemImage.setImageBitmap(circleBitmap);

			return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	public boolean online(String user) {
		boolean online = false;
		Roster roster = smack.getConnection().getRoster();
		Presence p6e = roster.getPresence(user);

		if (p6e.getType().equals(Presence.Type.available))
			online = true;
		else
			online = false;

		return online;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		RosterGroup group = smack.getConnection().getRoster()
				.getGroup(groups_Name[groupPosition]);
		Test.outputCertainString("click group name", group.getName());

		RosterEntry rE = group
				.getEntry(items_Name[groupPosition][childPosition]);
		Test.outputCertainString("click item name", rE.getUser());
		return false;
	};

}
