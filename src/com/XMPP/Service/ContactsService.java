package com.XMPP.Service;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.XMPP.Activity.Mainview.ContactsFragment;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.XMPP.util.L;

public class ContactsService extends Service {

	Smack smack;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		smack = SmackImpl.getInstance();
		L.i("come into friend service onStartCommand");
		new Thread(new Runnable() {
			@Override
			public void run() {
				admitFriendsRequest();
				test();
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
	
	public void test(){
		XMPPConnection connection = ConnectionHandler.getConnection();
		connection.getChatManager().addChatListener(
				new ChatManagerListener(){
					@Override
					public void chatCreated(Chat chat, boolean createLocally) {
						// TODO Auto-generated method stub
						if(!createLocally){
							chat.addMessageListener(new MessageListener(){
								@Override
								public void processMessage(Chat chat,
										Message message) {
									System.out.println("echo echo echo");
									String jid = chat.getParticipant();
									System.out.println("echo " + message.getBody());
									
									/**
									 * restore the message into DB and send notify to the UI
									 */
									
									
									
									
								}
								
							});;
						
						
						}else{
							L.i("chat create  already");
						}
					}
				}				
				);
	}

	public void admitFriendsRequest() {
		final XMPPConnection connection = ConnectionHandler.getConnection();
		connection.getRoster().setSubscriptionMode(
				Roster.SubscriptionMode.manual);
		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet paramPacket) {

				if (paramPacket instanceof Presence) {
					Presence presence = (Presence) paramPacket;
					String email = presence.getFrom();
					L.i("-------------accept an presence----------");
					L.i("from: " + email);
					L.i("type: " + presence.getType());
					L.i("to: " + presence.getTo());
					Roster roster = connection.getRoster();
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
						sendBroadcast(intent);

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
				L.i("------------presenceChanged--------------");
				L.i("from : " + presence.getFrom());
				L.i("to : " + presence.getTo());
				L.i("status : " + presence.getStatus());
				L.i("mode : " + presence.getMode());
				L.i("type : " + presence.getType());
				L.i("-----------------------------------------");
				/**
				 * send a broadcast to change the ui
				 */
				Intent sendIntent = new Intent(
						ContactsFragment.UPDATE_LIST_ACTION);
				sendBroadcast(sendIntent);
			}

			public void entriesUpdated(Collection<String> presence) {
				System.out.println("entriesUpdated");
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
