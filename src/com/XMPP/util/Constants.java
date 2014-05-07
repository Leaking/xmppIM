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
	
	
	// three kinds of login request result
	public final static int LOGIN_CONNECT_FAIL = 0;
	public final static int LOGIN_USERNAME_PSW_ERROR = 1;
	public final static int LOGIN_SUCCESS = 2;
	
	
	// 
	public final static String PRESENCE_TYPE_SUBSCRIBE = "subcribe";
	public final static String PRESENCE_TYPE_SUBSCRIBED = "subcribed";
	
	public final static String PRESENCE_TYPE_UNSUBSCRIBE = "unsubcribe";
	public final static String PRESENCE_TYPE_UNSUBSCRIBED = "unsubcribed";

	// new group tag
	public static int newTag = 1;
	
	// common blue
	public final static int COLOR_COMMON_BLUE = Color.rgb(0x83, 0xed, 0xb8);
	public final static int COLOR_COMMON_WHITE = Color.argb(60, 0x83, 0xed, 0xb8);
	public final static int COLOR_COMMON_GOLD = Color.argb(0xff, 0xfe, 0xd7, 0x00);
	public final static int COLOR_COMMON_RED = Color.argb(0xff, 0xee, 0x42, 0x56);
	public final static int COLOR_COMMON_BLACK = Color.argb(0xee, 0x00, 0x00, 0x00);

	
	// message type
	public final static String MESSAGE_TYPE_TIME = "TIME";
	public final static String MESSAGE_TYPE_TEXT = "TEXT";
	public final static String MESSAGE_TYPE_FILE = "FILE";
	
	//
	public final static String DELETE_STH = "/Smack";
}	
