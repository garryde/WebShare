package com.share.socket;

import com.share.staticresource.StaticResource;
import com.share.util.SocketUtil;

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
			//遍历Socket池
			for (Map.Entry<String,Socket> emp:StaticResource.socketMap.entrySet() ) {
				//判断是否已经断开
				boolean isClose = SocketUtil.isServerClose(emp.getValue());
				if (isClose) {
					//加载线程进行二次判断
					Executors.newCachedThreadPool().execute(new SocketTwiceConfirm(emp.getValue(),emp.getKey()));
				}
			}
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
