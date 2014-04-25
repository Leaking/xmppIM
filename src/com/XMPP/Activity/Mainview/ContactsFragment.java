package com.XMPP.Activity.Mainview;

import java.util.ArrayList;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.XMPP.R;
import com.XMPP.Database.ContactsRow;
import com.XMPP.Model.ViewRoster;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.CircleImage;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.T;
import com.XMPP.util.ValueUtil;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class ContactsFragment extends Fragment implements OnClickListener,
		OnChildClickListener, OnGroupClickListener, OnItemLongClickListener {

	private static final String TOAST_FRIEND_NAME = "please input a legal friend name";
	private static final String TOAST_GROUP_NAME = "please input a legal group name";
	ViewRoster viewRoster;
	Smack smack;
	// somebody request to add you ass friend
	String requestJID;
	// you request to add somebody to be you friend
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
		TextView add = (TextView) view.findViewById(R.id.addFriend);
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.contactExpandableList);
		smack = SmackImpl.getInstance();
		expandAdapter = new mBaseExpandableListAdapter();
		// groups = getGroupsName(groupList);
		expandableListView.setAdapter(expandAdapter);
		expandableListView.setOnChildClickListener(this);
		expandableListView.setOnGroupClickListener(this);
		
		expandableListView.setLongClickable(true);
		expandableListView.setOnItemLongClickListener(this);
		add.setOnClickListener(this);

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
			L.i("recevie requestJID " + requestJID);
			String presenceType = intent.getStringExtra("type");
			L.i("recevie presenceType " + presenceType);
			if (requestJID == null) {
				expandAdapter = new mBaseExpandableListAdapter();
				expandableListView.setAdapter(expandAdapter);
				return;
			}
			if (presenceType != null) {
				if (presenceType.equals(Constants.PRESENCE_TYPE_SUBSCRIBE)) {
					if (requestJID != null) {
						XMPPConnection conn = ConnectionHandler.getConnection();
						if (conn.getRoster().getEntry(requestJID) != null) {
							smack.subscribed(requestJID);
						} else {
							SubscribedFragment f1 = new SubscribedFragment();
							f1.show(ContactsFragment.this.getActivity()
									.getSupportFragmentManager(), "tag");
							f1.setCancelable(false);
						}
					}
				} else if (presenceType
						.equals(Constants.PRESENCE_TYPE_SUBSCRIBED)) {

				} else if (presenceType
						.equals(Constants.PRESENCE_TYPE_UNSUBSCRIBE)) {
					XMPPConnection conn = ConnectionHandler.getConnection();
					if (conn.getRoster().getEntry(requestJID) != null) {
						L.i(" the request is rejected ");
					}
					smack.unSubscribed(requestJID);
					smack.unSubscribe(requestJID);
					
				} else if (presenceType
						.equals(Constants.PRESENCE_TYPE_UNSUBSCRIBED)) {

				}
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

		// Object instanceof Group
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

		// Object instanceof ViewEntry
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
			TextView groupOnline = (TextView) ll
					.findViewById(R.id.listTitle_groupMenberOnlinePercent);

			groupName
					.setText(viewRoster.getGroup(groupPosition).getGroupName());
			String onlinePercent = viewRoster.getGroup(groupPosition)
					.getOnlineSum()
					+ "/"
					+ viewRoster.getGroup(groupPosition).getChildrenCount();
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
			
			ll.setId(groupPosition);
			ll.setTag("group");
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
			String user = viewRoster.getEntry(groupPosition, childPosition)
					.getNickname();
			nickName.setText(user);
			String ifonline = viewRoster.getEntry(groupPosition, childPosition)
					.getOnline();
			boolean online;
			if (ifonline == Constants.ONLINE)
				online = true;
			else
				online = false;

			ImageView itemImage = (ImageView) ll
					.findViewById(R.id.groupItemPhoto);
			Bitmap circleBitmap = CircleImage.toRoundBitmap(BitmapFactory
					.decodeResource(getResources(), R.drawable.channel_qq),
					online);
			itemImage.setImageBitmap(circleBitmap);
			ll.setId(childPosition);
			ll.setTag("child");
			return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	class SubscribedFragment extends DialogFragment implements
			OnItemClickListener, OnClickListener {
		ImageView addGroup;
		ImageView reject;
		EditText edittext;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			// Inflate and set the layout for the dialog
			// Pass null as the parent view because its going in the dialog
			// layout
			View view = inflater.inflate(R.layout.subscribed, null);
			builder.setView(view);
			edittext = (EditText) view.findViewById(R.id.newGroupName);
			addGroup = (ImageView) view.findViewById(R.id.addGroup);
			reject = (ImageView) view.findViewById(R.id.reject);

			IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(
					ContactsFragment.this.getActivity());
			iconicFontDrawable.setIcon(IconicIcon.PLUS);
			iconicFontDrawable.setIconColor(getResources().getColor(
					com.XMPP.R.color.pocket_blue));
			addGroup.setBackground(iconicFontDrawable);

			IconicFontDrawable iconicFontDrawable_2 = new IconicFontDrawable(
					ContactsFragment.this.getActivity());
			iconicFontDrawable_2.setIcon(IconicIcon.CANCEL);
			iconicFontDrawable_2.setIconColor(getResources().getColor(
					com.XMPP.R.color.pocket_blue));
			reject.setBackground(iconicFontDrawable_2);

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
					LinearLayout line = new LinearLayout(
							ContactsFragment.this.getActivity());
					line.setOrientation(0);
					TextView text = new TextView(
							ContactsFragment.this.getActivity());
					text.setText(viewRoster.getGroup(arg0).getGroupName());
					text.setTextSize(25);
					line.addView(text);
					return line;

				}

			};
			list.setAdapter(adapter);
			list.setOnItemClickListener(SubscribedFragment.this);
			addGroup.setOnClickListener(this);
			reject.setOnClickListener(this);
			return builder.create();
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.addGroup) {
				/**
				 * 
				 */
				new Thread(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String newGroupName = edittext.getText().toString();
						if (newGroupName == null || newGroupName.length() == 0) {
							Toast.makeText(ContactsFragment.this.getActivity(),
									"pleace input a legal group name",
									Toast.LENGTH_SHORT).show();
							return;
						}
						// smack.acceptFriend(requestJID, newGroupName);						
						smack.subscribed(requestJID);
						smack.addEntry(requestJID, newGroupName);
						smack.subscribe(requestJID);
						expandAdapter = new mBaseExpandableListAdapter();
						expandableListView.setAdapter(expandAdapter);
						SubscribedFragment.this.dismiss();
					}
				}).start();
			}
			if (v.getId() == R.id.reject) {
				/**
				 * send a reject message
				 */
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						smack.unSubscribe(requestJID);
						SubscribedFragment.this.dismiss();
					}
				}).start();
			}
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String groupName = viewRoster.getGroup(arg2).getGroupName();
			L.i("you choose the group : "
					+ viewRoster.getGroup(arg2).getGroupName());
			// smack.acceptFriend(requestJID, groupName);
			smack.subscribed(requestJID);
			L.i("requestJID: 1" + requestJID);
			smack.addEntry(requestJID, groupName);
			// smack.subscribe(requestJID);
			expandAdapter = new mBaseExpandableListAdapter();
			expandableListView.setAdapter(expandAdapter);
			this.dismiss();
		}

	}

	class SubscribeFragment extends DialogFragment implements
			OnItemClickListener, OnClickListener {
		ImageView addGroup;
		ImageView reject;
		EditText friendName;
		EditText groupName;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			View view = (View) inflater.inflate(R.layout.subscribe, null);
			builder.setView(view);
			friendName = (EditText) view.findViewById(R.id.nickname);
			groupName = (EditText) view.findViewById(R.id.newGroupName);
			addGroup = (ImageView) view.findViewById(R.id.addGroup);
			reject = (ImageView) view.findViewById(R.id.cancel);

			IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(
					ContactsFragment.this.getActivity());
			iconicFontDrawable.setIcon(IconicIcon.PLUS);
			iconicFontDrawable.setIconColor(getResources().getColor(
					com.XMPP.R.color.pocket_blue));
			addGroup.setBackground(iconicFontDrawable);

			IconicFontDrawable iconicFontDrawable_2 = new IconicFontDrawable(
					ContactsFragment.this.getActivity());
			iconicFontDrawable_2.setIcon(IconicIcon.CANCEL);
			iconicFontDrawable_2.setIconColor(getResources().getColor(
					com.XMPP.R.color.pocket_blue));
			reject.setBackground(iconicFontDrawable_2);

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
					LinearLayout line = new LinearLayout(
							ContactsFragment.this.getActivity());
					line.setOrientation(0);
					TextView text = new TextView(
							ContactsFragment.this.getActivity());
					text.setText(viewRoster.getGroup(arg0).getGroupName());
					text.setTextSize(25);
					line.addView(text);
					return line;

				}

			};
			list.setAdapter(adapter);
			list.setOnItemClickListener(SubscribeFragment.this);
			addGroup.setOnClickListener(SubscribeFragment.this);
			reject.setOnClickListener(SubscribeFragment.this);
			return builder.create();

		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			final int postion_2 = position;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String name = friendName.getText().toString();
					String groupName = viewRoster.getGroup(postion_2).getGroupName();
					if (name == null || name.length() == 0) {
						String toast = "please input a legal name";
						T.mToast(SubscribeFragment.this.getActivity(), toast);
					} else {
						// subscribe
						String jid = ValueUtil.getID(name);
						// smack.subscribe(jid);
						smack.addEntry(jid, groupName);
					}
					SubscribeFragment.this.dismiss();
				}
			}).start();
			
		}

		// react to the 2 button in the subscribe-fragment buttom
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.addGroup) {
				new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String nickname = friendName.getText().toString();
						String groupname = groupName.getText().toString();
						String toast = new String();
						if (nickname == null || nickname.length() == 0) {
							toast += TOAST_FRIEND_NAME;
						}
						if (groupname == null || groupname.length() == 0) {
							toast += TOAST_GROUP_NAME;
							
						}
						if (toast.length() > 0) {
							T.mToast(SubscribeFragment.this.getActivity(), toast);
							return;
						}
						String jid = ValueUtil.getID(nickname);
						smack.addEntry(jid, groupname);
						SubscribeFragment.this.dismiss();
					}
					
				}).start();
				
				

			}
			if (v.getId() == R.id.cancel) {

				this.dismiss();
			}
		}
	}

	
	class ListAlertFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setItems(
					new String[] { "friend", "relative" },
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item
							System.out.println("you click " + which);
						}
					});
			return builder.create();
		}
	}
	
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		SubscribeFragment f1 = new SubscribeFragment();
		f1.show(ContactsFragment.this.getActivity()
				.getSupportFragmentManager(), "tag");
		f1.setCancelable(false);
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.addFriend) {

			SubscribeFragment f1 = new SubscribeFragment();
			f1.show(ContactsFragment.this.getActivity()
					.getSupportFragmentManager(), "tag");
			f1.setCancelable(false);
		}
	}
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		System.out.println("onGroupClick");
		return false;
	};



	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		ListAlertFragment f1 = new ListAlertFragment();
		f1.show(ContactsFragment.this.getActivity()
				.getSupportFragmentManager(), "tag");
		f1.setCancelable(true);
		return false;
	}




}
