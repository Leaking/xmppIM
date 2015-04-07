package com.quinn.xmpp.core.contacts;

import java.util.Collection;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.quinn.xmpp.App;
import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.util.LogcatUtils;

/**
 * 
 * 
 * @author Quinn
 * @date 2015-3-31
 */
public class PresenceListenerService extends Service {

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
		LogcatUtils.i("启动presenceListenerService");

		/**
		 * 1，登录时，该监听器会被回调，可以在这个时刻获取所有好友的service（比如是smack，或者是spark等等） 2. 用户上线下线？
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				listen();
			}
		}).start();

		return START_STICKY;
	}

	public void listen() {

		connection.getRoster().setSubscriptionMode(
				Roster.SubscriptionMode.manual);
		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet paramPacket) {

				if (paramPacket instanceof Presence) {
					Presence presence = (Presence) paramPacket;
					String jid = null;
					// request to add friend

					if (presence.getType().equals(Presence.Type.subscribe)) {
						LogcatUtils.i("Presence.Type.subscribe");

					} else if (presence.getType().equals(
							Presence.Type.subscribed)) {
						LogcatUtils.i("Presence.Type.subscribed");

					} else if (presence.getType().equals(
							Presence.Type.unsubscribe)) {
						LogcatUtils.i("Presence.Type.unsubscribe");

					} else if (presence.getType().equals(
							Presence.Type.unsubscribed)) {
						LogcatUtils.i("Presence.Type: unsubscribed");

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

		//删除好友时会调用entriesUpdated 和上面的  unsubscribe
		roster.addRosterListener(new RosterListener() {

			@Override
			public void presenceChanged(Presence presence) {
				LogcatUtils.i("presence change:from = " + presence.getFrom());
				// 保存各个用户的域

				// 发送广播更新在线状态（更新UI）

			}

			@Override
			public void entriesUpdated(Collection<String> presence) {
				LogcatUtils.i("entriesUpdated");

			}

			@Override
			public void entriesDeleted(Collection<String> presence) {
				LogcatUtils.i("entriesDeleted");

			}

			@Override
			public void entriesAdded(Collection<String> presence) {
				LogcatUtils.i("entriesAdded");

			}
		});
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
