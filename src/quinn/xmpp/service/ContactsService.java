package quinn.xmpp.service;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import quinn.xmpp.activity.mainview.ChattingFragment;
import quinn.xmpp.activity.mainview.ContactsFragment;
import quinn.xmpp.database.TableChatting;
import quinn.xmpp.smack.ConnectionHandler;
import quinn.xmpp.smack.Smack;
import quinn.xmpp.smack.SmackImpl;
import quinn.xmpp.utils.Constants;
import quinn.xmpp.utils.L;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class ContactsService extends Service {

	Smack smack;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		smack = SmackImpl.getInstance();
		new Thread(new Runnable() {
			@Override
			public void run() {
				admitFriendsRequest();
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	public void admitFriendsRequest() {
		final XMPPConnection connection = ConnectionHandler.getConnection();
		connection.getRoster().setSubscriptionMode(
				Roster.SubscriptionMode.manual);
		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet paramPacket) {

				if (paramPacket instanceof Presence) {
					Presence presence = (Presence) paramPacket;
					String jid = null;
					// request to add friend

					if (presence.getType().equals(Presence.Type.subscribe)) {
						L.i("Presence.Type.subscribe");
						jid = presence.getFrom();
						XMPPConnection conn = ConnectionHandler.getConnection();
						// this friend exists in roster

						Intent intent = new Intent();
						intent.setAction(ContactsFragment.UPDATE_LIST_ACTION);
						intent.putExtra("type",
								Constants.PRESENCE_TYPE_SUBSCRIBE);
						intent.putExtra("jid", jid);
						sendBroadcast(intent);

					} else if (presence.getType().equals(
							Presence.Type.subscribed)) {
						L.i("Presence.Type.subscribed");

					} else if (presence.getType().equals(
							Presence.Type.unsubscribe)) {
						L.i("Presence.Type.unsubscribe");
						jid = presence.getFrom();
						Intent intent = new Intent();
						intent.setAction(ContactsFragment.UPDATE_LIST_ACTION);
						intent.putExtra("type",
								Constants.PRESENCE_TYPE_UNSUBSCRIBE);
						intent.putExtra("jid", jid);
						smack.removeEntry(jid);
						sendBroadcast(intent);

						TableChatting tableChatting = TableChatting.getInstance(ContactsService.this);
						tableChatting.delete(smack.getJID(), jid);
						Intent intent1 = new Intent();
						intent1.setAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
						sendBroadcast(intent1);
						
						
					} else if (presence.getType().equals(
							Presence.Type.unsubscribed)) {
						L.i("Presence.Type: unsubscribed");

					}

				}

			}
		}, new PacketFilter() {
			public boolean accept(Packet packet) {
				if (packet instanceof Presence) {
					Presence presence = (Presence) packet;
					if (presence.getType().equals(Presence.Type.subscribed)
							|| presence.getType().equals(
									Presence.Type.subscribe)
							|| presence.getType().equals(
									Presence.Type.unsubscribed)
							|| presence.getType().equals(
									Presence.Type.unsubscribe)) {
						return true;
					}
				}
				return false;
			}
		});

		connection.getRoster().addRosterListener(new RosterListener() {
			public void presenceChanged(Presence presence) {

				L.i("presenceChanged " + presence.getFrom());
				
				smack.markResource(presence.getFrom()); 
				Intent sendIntent = new Intent(
						ContactsFragment.UPDATE_LIST_ACTION);
				if(smack.getConnection().isAuthenticated())
					sendBroadcast(sendIntent);
			}

			public void entriesUpdated(Collection<String> presence) {
				L.i("entriesUpdated ");
			}

			public void entriesDeleted(Collection<String> presence) {
				System.out.println("entriesDeleted");

			}

			public void entriesAdded(Collection<String> presence) {
				System.out.println("entriesAdded");
			}
		});
	}

}
