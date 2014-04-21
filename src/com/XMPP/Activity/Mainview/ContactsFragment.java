package com.XMPP.Activity.Mainview;

import java.util.ArrayList;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.Database.ContactsRow;
import com.XMPP.Model.ViewRoster;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.CircleImage;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;

public class ContactsFragment extends Fragment implements OnChildClickListener {
	
	ViewRoster viewRoster;
	Smack smack;
	//  somebody request to add you ass friend
	String requestJID;
	// you request to add somebody to be you friend
	String requestedJID;
	ExpandableListView expandableListView;
	ExpandableListAdapter expandAdapter;
	AdapterReceiver aReceiver;
	public static final String UPDATE_LIST_ACTION = "com.XMPP.action.UPDATE_lIST";

	// Container Activity must implement this interface


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_contacts, container,
				false);
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.contactExpandableList);
		smack = SmackImpl.getInstance();
		expandAdapter = new mBaseExpandableListAdapter();
		// groups = getGroupsName(groupList);
		expandableListView.setAdapter(expandAdapter);
		expandableListView.setOnChildClickListener(this);
		
		aReceiver = new AdapterReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_LIST_ACTION);
		getActivity().registerReceiver(aReceiver, filter);






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

			requestJID = intent.getStringExtra("jid");
			if(requestJID != null){
				L.i("commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
				SubscribedFragment f1 = new SubscribedFragment();
				f1.show(ContactsFragment.this.getActivity().getSupportFragmentManager(), "tag");
				f1.setCancelable(false);
			}

			expandAdapter = new mBaseExpandableListAdapter();
			expandableListView.setAdapter(expandAdapter);
		}

	}

	// ExpandableListAdapter expandAdapter = new mBaseExpandableListAdapter();
	class mBaseExpandableListAdapter extends BaseExpandableListAdapter {
		ArrayList<ContactsRow> rows;	
		
		public mBaseExpandableListAdapter() {
			rows = smack.getContactsRows();
			viewRoster = new ViewRoster(rows);
		}


		
		@Override
		public int getGroupCount() {
			return viewRoster.getGroupCount();
		}

		
		//Object instanceof Group
		@Override
		public Object getGroup(int groupPosition) {
			return viewRoster.getGroup(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return viewRoster.getGroup(groupPosition).getChildrenCount();
		}

		//Object instanceof ViewEntry
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return viewRoster.getEntry(groupPosition, childPosition);
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
			TextView groupOnline = (TextView) ll.findViewById(R.id.listTitle_groupMenberOnlinePercent);

			groupName.setText(viewRoster.getGroup(groupPosition).getGroupName());
			String onlinePercent = viewRoster.getGroup(groupPosition).getOnlineSum() + "/" + 
			viewRoster.getGroup(groupPosition).getChildrenCount();
			groupOnline.setText(onlinePercent);
			
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
			TextView nickName = (TextView) ll.findViewById(R.id.itemName);
			String user = viewRoster.getEntry(groupPosition, childPosition).getNickname();
			nickName.setText(user);
			String ifonline = viewRoster.getEntry(groupPosition, childPosition).getOnline();
			boolean online;
			if(ifonline == Constants.ONLINE)
				online = true;
			else
				online = false;
			
			ImageView itemImage = (ImageView) ll
					.findViewById(R.id.groupItemPhoto);
			Bitmap circleBitmap = CircleImage.toRoundBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.channel_qq),
					online);
			itemImage.setImageBitmap(circleBitmap);

			return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	class SubscribedFragment extends DialogFragment implements OnItemClickListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			// Inflate and set the layout for the dialog
			// Pass null as the parent view because its going in the dialog
			// layout
			View view = inflater.inflate(R.layout.choice, null);
			builder.setView(view);
			
			ListView list = (ListView) view.findViewById(R.id.group);

			
			BaseAdapter adapter = new BaseAdapter() {

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return viewRoster.getGroupCount();
				}

				@Override
				public Object getItem(int arg0) {
					// TODO Auto-generated method stub
					return viewRoster.getGroup(arg0).getGroupName();
				}

				@Override
				public long getItemId(int arg0) {
					// TODO Auto-generated method stub
					return arg0;
				}

				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					// TODO Auto-generated method stub
					LinearLayout line = new LinearLayout(ContactsFragment.this.getActivity());
					line.setOrientation(0);
					TextView text = new TextView(ContactsFragment.this.getActivity());

					text.setText(viewRoster.getGroup(arg0).getGroupName());

					text.setTextSize(25);
					line.addView(text);
					return line;

				}

			};
			list.setAdapter(adapter);
			list.setOnItemClickListener(SubscribedFragment.this);
			return builder.create();
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String groupName = viewRoster.getGroup(arg2).getGroupName();
			L.i("you choose the group : " + viewRoster.getGroup(arg2).getGroupName());
			Presence newp = new Presence(Presence.Type.subscribed);
			newp.setMode(Presence.Mode.available);
			newp.setPriority(24);
			String nickname = smack.getNickname(requestJID);
			newp.setTo(requestJID);
			XMPPConnection conn = ConnectionHandler.getConnection();
			try {
				conn.getRoster().createEntry(requestJID, nickname,  
				            new String[] {groupName});
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  			
			Presence subscription = new Presence(
					Presence.Type.subscribe);
			subscription.setTo(requestJID);
			conn.sendPacket(subscription);
			expandAdapter = new mBaseExpandableListAdapter();
			expandableListView.setAdapter(expandAdapter);
			this.dismiss();
		}



	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub

		return false;
	};

}
