package com.share.service;

import com.share.socket.SocketAccept;
import com.share.staticresource.StaticResource;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by plzwb on 2017/4/28.
 */
public class SocketConnectCheck implements Runnable {
	@Override
	public void run() {
		while (true) {
			for (Map.Entry<String,Socket> emp:StaticResource.socketMap.entrySet() ) {
				boolean isClose = isServerClose(emp.getValue());
				if (isClose) {
					StaticResource.socketMap.remove(emp.getKey());
				}
			}
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public Boolean isServerClose(Socket socket) {
		try {
			socket.sendUrgentData(0);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			return true;
		}
	}
}
