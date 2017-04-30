package com.share.service;

import com.share.staticresource.StaticResource;
import com.share.util.SocketUtil;

import java.net.Socket;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class SocketTwiceConfirm implements Runnable {
	private Socket socket = null;
	private String conCode = null;

	public SocketTwiceConfirm(Socket socket, String conCode) {
		this.socket = socket;
		this.conCode = conCode;
	}

	@Override
	public void run() {
		//延迟一小时进行二次判断
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//二次检测，无连接，移除
		if (SocketUtil.isServerClose(socket)){
			StaticResource.socketMap.remove(conCode);
			socket = null;
		}
	}
}
