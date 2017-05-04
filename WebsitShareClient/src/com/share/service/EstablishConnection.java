package com.share.service;

import com.share.main.Main;
import com.share.util.SocketUtil;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

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
			socket.setKeepAlive(true);
			SocketUtil.Send(socket, "{\"act\":\"regist\",\"code\":\" "+conCode+" \"}");
			//延时500ms
			Thread thread = Thread.currentThread();
			thread.sleep(500);
			//测试连接是否断开
			if (SocketUtil.isServerClose(socket)) {
				return false;
			}
			System.out.println("检测断开完成！");
			//建立心跳线程
			SocketHeart socketHeart = new SocketHeart(conCode);
			//启动心跳线程
			socketHeart.start();
			//建立监听线程
			new SocketListener(ip,port,conCode, socket,socketHeart).start();
			//发送至全局Socket
			Main.socket = socket;
			//发送至连接全局状态判断
			Main.isConnect = true;
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (SocketException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
}
