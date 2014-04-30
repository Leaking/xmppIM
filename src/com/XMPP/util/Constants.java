package com.XMPP.util;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import android.graphics.Color;

public class Constants {
	
	
	//server ip and ip
	public final static String SERVER_IP = "192.168.1.8";
	public final static int SERVER_PORT = 5222;

	//
	public final static String ONLINE = "online";
	public final static String OFF_LINE = "offline";
	
	
	//three kinds of login request result
	public final static int LOGIN_CONNECT_FAIL = 0;
	public final static int LOGIN_USERNAME_PSW_ERROR = 1;
	public final static int LOGIN_SUCCESS = 2;
	
	
	//
	public final static String PRESENCE_TYPE_SUBSCRIBE = "subcribe";
	public final static String PRESENCE_TYPE_SUBSCRIBED = "subcribed";
	
	public final static String PRESENCE_TYPE_UNSUBSCRIBE = "unsubcribe";
	public final static String PRESENCE_TYPE_UNSUBSCRIBED = "unsubcribed";

	//new group tag
	public static int newTag = 1;
	
	// common blue
	public final static int COLOR_COMMON_BLUE = Color.rgb(0x83, 0xed, 0xb8);
	public final static int COLOR_COMMON_WHITE = Color.argb(60, 0x83, 0xed, 0xb8);
	
}	
