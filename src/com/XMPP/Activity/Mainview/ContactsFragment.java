package com.XMPP.Activity.Mainview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.XMPP.Activity.ChatRoom.ChatRoomActivity;
import com.XMPP.Database.RowContacts;
import com.XMPP.Database.TableChatting;
import com.XMPP.Model.ViewEntry;
import com.XMPP.Model.ViewRoster;
import com.XMPP.Model.ViewXMPPGroup;
import com.XMPP.Service.ContactsService;
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

	private static final String TOAST_FRIEND_NAME = " please input a legal friend name";
	private static final String TOAST_GROUP_NAME = "  please input a legal group name";
	ViewRoster viewRoster;
	Smack smack;
	// somebody request to add you ass friend
	String requestJID;

	//
	int groupPosition;
	int childPosition;
	String longclickJID;
	String longclickGROUP;
	// you request to add somebody to be you friend
	ExpandableListView expandableListView;
	ExpandableListAdapter expandAdapter;
	AdapterReceiver aReceiver;
	ListView list;
	MBaseAdapter adapter;
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
			String presenceType = intent.getStringExtra("type");
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
		ArrayList<RowContacts> rows;

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
			
			//ll.setBackgroundColor(Color.GRAY);
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

			ll.setTag(R.id.action_settings, "tag");
			return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	class MBaseAdapter extends BaseAdapter {

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
			TextView text = new TextView(ContactsFragment.this.getActivity());
			text.setText(viewRoster.getGroup(arg0).getGroupName());
			text.setTextSize(25);
			line.addView(text);
			return line;
		}

	};

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
					com.XMPP.R.color.pocket_red));
			reject.setBackground(iconicFontDrawable_2);

			list = (ListView) view.findViewById(R.id.group);
			adapter = new MBaseAdapter();
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
				final String newGroupName = edittext.getText().toString();
				if (newGroupName == null || newGroupName.length() == 0) {
					Toast.makeText(ContactsFragment.this.getActivity(),
							"pleace input a legal group name",
							Toast.LENGTH_SHORT).show();
					return;
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						smack.subscribed(requestJID);
						smack.addEntry(requestJID, newGroupName);
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
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					String groupName = viewRoster.getGroup(arg2).getGroupName();
					// smack.acceptFriend(requestJID, groupName);
					smack.subscribed(requestJID);
					smack.addEntry(requestJID, groupName);
					// smack.subscribe(requestJID);
					expandAdapter = new mBaseExpandableListAdapter();
					expandableListView.setAdapter(expandAdapter);
					SubscribedFragment.this.dismiss();
				}
			}).start();
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
					com.XMPP.R.color.pocket_red));
			reject.setBackground(iconicFontDrawable_2);

			list = (ListView) view.findViewById(R.id.group);

			adapter = new MBaseAdapter();
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
			final String name = friendName.getText().toString();
			final String groupName = viewRoster.getGroup(position)
					.getGroupName();
			if (name == null || name.length() == 0) {
				String toast = "please input a legal name";
				T.mToast(SubscribeFragment.this.getActivity(), toast);
			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						// subscribe
						String jid = ValueUtil.getID(name);
						smack.addEntry(jid, groupName);
						SubscribeFragment.this.dismiss();
					}
				}).start();
			}

		}

		// react to the 2 button in the subscribe-fragment buttom
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.addGroup) {
				final String nickname = friendName.getText().toString();
				final String groupname = groupName.getText().toString();
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
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String jid = ValueUtil.getID(nickname);
							smack.addEntry(jid, groupname);
							SubscribeFragment.this.dismiss();
						}
					}).start();
				}

			}
			if (v.getId() == R.id.cancel) {

				this.dismiss();
			}
		}
	}

	class ChangeGroupFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			final ArrayList<String> list = viewRoster.getGroupnames();
			list.add("To A New Group");
			builder.setItems(list.toArray(new String[list.size()]),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item
							final int mWhich = which;
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									XMPPConnection conn = ConnectionHandler
											.getConnection();
									Roster roster = conn.getRoster();
									RosterEntry entry = roster
											.getEntry(longclickJID);
									RosterGroup group;
									if (mWhich == list.size() - 1) {
										group = roster
												.createGroup("A New Group"
														+ Constants.newTag++);
									} else {
										group = roster.getGroup(list
												.get(mWhich));
									}
									try {
										group.addEntry(entry);
									} catch (XMPPException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Intent intent = new Intent();
									intent.setAction(ContactsFragment.UPDATE_LIST_ACTION);
									ChangeGroupFragment.this.getActivity()
											.sendBroadcast(intent);
									ChangeGroupFragment.this.dismiss();
								}
							}).start();

						}
					});
			return builder.create();
		}

	}

	class ChildLongClickFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setItems(new String[] { "Profile", "Delete",
					"To another group" },
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item

							switch (which) {
							case 0:
								break;
							case 1:
								new Thread(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										XMPPConnection conn = ConnectionHandler
												.getConnection();
										Roster roster = conn.getRoster();
										RosterEntry entry = roster
												.getEntry(longclickJID);
										try {
											roster.removeEntry(entry);
											smack.unSubscribe(longclickJID);
											
											TableChatting tableChatting = TableChatting.getInstance(ContactsFragment.this.getActivity());
											tableChatting.delete(smack.getJID(), longclickJID);
											Intent intent1 = new Intent();
											intent1.setAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
											ContactsFragment.this.getActivity().sendBroadcast(intent1);
											
											
											Intent intent = new Intent();
											intent.setAction(ContactsFragment.UPDATE_LIST_ACTION);
											ChildLongClickFragment.this
													.getActivity()
													.sendBroadcast(intent);
											ChildLongClickFragment.this
													.dismiss();
										} catch (XMPPException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}).start();
								break;
							case 2:
								new Thread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										XMPPConnection conn = ConnectionHandler
												.getConnection();
										Roster roster = conn.getRoster();
										RosterEntry entry = roster
												.getEntry(longclickJID);
										String groupName = viewRoster.getGroup(
												groupPosition).getGroupName();
										RosterGroup group = roster
												.getGroup(groupName);
										try {
											group.removeEntry(entry);
										} catch (XMPPException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}).start();
								ChangeGroupFragment f1 = new ChangeGroupFragment();
								f1.show(ContactsFragment.this.getActivity()
										.getSupportFragmentManager(), "tag");
								break;
							}

						}
					});

			ViewEntry viewEntry = viewRoster.getEntry(groupPosition,
					childPosition);
			String entryName = viewEntry.getNickname();
			builder.setTitle(entryName);
			return builder.create();
		}

	}

	class GroupLongClickFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(longclickGROUP).setItems(
					new String[] { "Rename", "Delete" },
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item

							switch (which) {
							case 0:
								GroupRenameFragment f1 = new GroupRenameFragment();
								f1.show(ContactsFragment.this.getActivity()
										.getSupportFragmentManager(), "tag");
								f1.setCancelable(false);
								break;
							case 1:
								
								DeleteGroupFragment f2 = new DeleteGroupFragment();
								f2.show(ContactsFragment.this.getActivity()
										.getSupportFragmentManager(), "tag");
								f2.setCancelable(false);
								
								break;
							}

						}
					});

			return builder.create();
		}

	}

	class GroupRenameFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			View view = (View) inflater.inflate(R.layout.setgroupname, null);
			builder.setView(view);
			final EditText text = (EditText) view
					.findViewById(R.id.new_group_name);

			builder.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							final String string = text.getText().toString();
							if (string == null || string.length() == 0) {
								T.mToast(getActivity(), TOAST_GROUP_NAME);
							} else {
								new Thread(new Runnable() {
									public void run() {
										RosterGroup group = ConnectionHandler
												.getConnection().getRoster()
												.getGroup(longclickGROUP);
										group.setName(string);
										Intent intent = new Intent();
										intent.setAction(ContactsFragment.UPDATE_LIST_ACTION);
										GroupRenameFragment.this.getActivity()
												.sendBroadcast(intent);

									}
								}).start();

							}

						}
					}).setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
						}
					});

			return builder.create();

		}

	}

	class DeleteGroupFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(longclickGROUP)
					.setMessage("Are you sure to delete this group")
					.setNegativeButton("NO",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {

								}
							})
					.setPositiveButton("YES",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {				
									DeleteGroupTowhereFragment f1 = new DeleteGroupTowhereFragment();
									f1.show(ContactsFragment.this.getActivity()
											.getSupportFragmentManager(), "tag");
								}
							});

			return builder.create();
		}
	}

	class DeleteGroupTowhereFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			final ArrayList<String> list = viewRoster.getGroupnames();
			list.remove(longclickGROUP);
			builder.setTitle("choose a group to locate the buddies").setItems(
					list.toArray(new String[list.size()]),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item
							final int mWhich = which;
							XMPPConnection conn = ConnectionHandler.getConnection();
							Roster roster = conn.getRoster();
							RosterGroup last_group = roster.getGroup(longclickGROUP);
						    RosterGroup next_group = roster.getGroup(list.get(mWhich));
							Collection<RosterEntry> collection = last_group.getEntries();
							Iterator<RosterEntry> iter = collection.iterator();
							while(iter.hasNext()){
								RosterEntry entry = iter.next();
								try {
									last_group.removeEntry(entry);
									next_group.addEntry(entry);
								} catch (XMPPException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
							Intent intent = new Intent();
							intent.setAction(ContactsFragment.UPDATE_LIST_ACTION);
							DeleteGroupTowhereFragment.this.getActivity().sendBroadcast(intent);
						}
					});

			return builder.create();
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ContactsFragment.this.getActivity(),ChatRoomActivity.class);
		
		intent.putExtra("online",viewRoster.getEntry(groupPosition, childPosition).getOnline());
		intent.putExtra("JID",viewRoster.getEntry(groupPosition, childPosition).getFriend_jID());
	
		ContactsFragment.this.getActivity().startActivity(intent);		
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
		long packedPos = ((ExpandableListView) parent)
				.getExpandableListPosition(position);
		groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
		childPosition = ExpandableListView.getPackedPositionChild(packedPos);
		// you click the child item;
		if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

			ViewEntry viewEntry = viewRoster.getEntry(groupPosition,
					childPosition);
			longclickJID = viewEntry.getFriend_jID();
			L.i("Longclick jid   " + longclickJID);
			ChildLongClickFragment f1 = new ChildLongClickFragment();
			f1.show(ContactsFragment.this.getActivity()
					.getSupportFragmentManager(), "tag");
		} else {
			// you click the parent item
			ViewXMPPGroup viewGroup = viewRoster.getGroup(groupPosition);
			longclickGROUP = viewGroup.getGroupName();

			GroupLongClickFragment f1 = new GroupLongClickFragment();
			f1.show(ContactsFragment.this.getActivity()
					.getSupportFragmentManager(), "tag");

		}
		// showDeleteAlertDialog((AccountInfo)
		// expAdapter.getChild(groupPosition, childPosition));
		return false;
	}

}
