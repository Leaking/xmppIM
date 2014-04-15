package com.XMPP.util;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

public class Constants {
	
	
	//server ip and ip
	public final static String SERVER_IP = "192.168.1.8";
	public final static int SERVER_PORT = 5222;


	
	//three kinds of login request result
	public final static int LOGIN_CONNECT_FAIL = 0;
	public final static int LOGIN_USERNAME_PSW_ERROR = 1;
	public final static int LOGIN_SUCCESS = 2;
	
	
}	
