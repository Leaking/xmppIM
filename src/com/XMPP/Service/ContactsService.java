package com.XMPP.Service;

import java.util.Collection;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
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
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		L.i("come into friend service onCreate");

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
