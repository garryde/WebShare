package com.share.service;

import com.share.util.SocketUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class SocketHeart extends Thread{
	private Socket socket = null;
	private String conCode = null;
	boolean isRun = true;

	public SocketHeart(Socket socket,String conCode) {
		this.socket = socket;
		this.conCode = conCode;
	}

	@Override
	public void run() {
		while (isRun)
			try {
				SocketUtil.Send(socket,"{\"act\":\"heart\",\"code\":\""+conCode+"\"}");
				Thread.sleep(10000);
			} catch (IOException e) {
				isRun = false;
			} catch (InterruptedException e) {
				isRun = false;
		}
	}
}
