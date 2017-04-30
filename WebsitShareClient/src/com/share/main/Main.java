package com.share.main;

import java.net.Socket;

import com.share.fram.MainFram;

public class Main {
	private static String ip = "182.254.157.166";
	private static Integer port = 2333;

	public static Socket socket = null;
	public static boolean isConnect;

	public static void main(String[] args) {
		new MainFram(ip,port);
	}

}
