package com.quinn.xmpp.core.contacts;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.quinn.xmpp.App;
import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.Intents.NotificationAction;
import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.util.LogcatUtils;

/**
 * 用户添加好友请求的后台监听
 * 
 * @author Quinn
 * @date 2015-3-31
 */
public class SubscriptionListenerService extends Service {

	private Smack smack;
	private XMPPConnection connection;
	private Roster roster;

	@Override
	public void onCreate() {
		super.onCreate();
		smack = ((App) getApplication()).getSmack();
		connection = smack.getConnection();
		roster = connection.getRoster();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogcatUtils.i("启动SubscriptionListenerService");
		connection.getRoster().setSubscriptionMode(
				Roster.SubscriptionMode.manual);
		connection.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(Packet packet) {
				if (packet instanceof Presence) {
					Presence presence = (Presence) packet;
					String fromJID = presence.getFrom();
					Presence.Type presenceType = presence.getType();
					if (presenceType.equals(Presence.Type.subscribe)) {
						// 发送广播，提醒有好友添加请求
						Builder builder = new Builder(
								NotificationAction.SUBSCRIBE_ACTION)
								.addJID(fromJID);
						Intent intent = builder.toIntent();
						sendBroadcast(intent);
					} else if (presenceType.equals(Presence.Type.unsubscribe)) {

					} else if (presenceType.equals(Presence.Type.subscribed)) {

					} else if (presenceType.equals(Presence.Type.unsubscribed)) {

					}
				}
			}
		}, new PacketFilter() {

			@Override
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

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
