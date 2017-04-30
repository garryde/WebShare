package com.share.service;

import com.share.main.Main;
import com.share.util.SocketUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EstablishConnection{
	private String ip = null;// 连接服务器的IP
	private Integer port = null;// 连接服务器的端口
	private String conCode = null;//识别代码
	private Socket socket = null;// 套节字对象

	public EstablishConnection(String ip, Integer port,String conCode) {
		this.ip = ip;
		this.port = port;
		this.conCode = conCode;
	}

	/**
	 * 初始化socket对象
	 */
	public boolean connect() {
		try {
			InetAddress address = InetAddress.getByName(ip);
			socket = new Socket(address, port);
			SocketUtil.Send(socket, "{\"act\":\"regist\",\"code\":\""+conCode+"\"}");
			//建立监听线程
			new SocketListener(ip,port,conCode, socket).run();
			//建立心跳线程
			//new SocketHeart(socket,conCode);
			//发送至全局Socket
			Main.socket = socket;
			//发送至连接全局状态判断
			Main.isConnect = true;
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
