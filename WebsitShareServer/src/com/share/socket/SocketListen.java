package com.share.socket;

import com.share.util.SocketUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by plzwb on 2017/5/1.
 */
public class SocketListen implements Runnable{
	private Socket socket = null;
	private boolean isRun = true;

	public SocketListen(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (isRun) {
			try {
				String text = SocketUtil.readStrFromStream(socket.getInputStream());
				//测试代码
				System.out.println("心跳包："+text);
			} catch (IOException e) {
				isRun = false;
				System.out.println("心跳监听停止！");
			}
		}
	}
}
