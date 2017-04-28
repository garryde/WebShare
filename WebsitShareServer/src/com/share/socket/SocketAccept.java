package com.share.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;


public class SocketAccept implements Runnable{
	
	ServerSocket serverSocket = null;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(2333);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			try {
				//阻塞接收Socket
				Socket socket =  serverSocket.accept();
				//启动Socket身份检测线程
				Executors.newCachedThreadPool().execute(new SocketIdentify(socket));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
