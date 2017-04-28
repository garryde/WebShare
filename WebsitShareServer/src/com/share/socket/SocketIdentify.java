package com.share.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.share.domain.JsonObj;
import com.share.staticresource.StaticResource;
import com.share.util.JsonHandle;
import com.share.util.SocketUtil;

public class SocketIdentify implements Runnable{
	private Socket socket = null;
	
	public SocketIdentify(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
			try {
				InputStream in = socket.getInputStream();
				String jsonString = SocketUtil.readStrFromStream(in);
				JsonObj jsonObj = JsonHandle.handle(jsonString);

				Socket delSocket = StaticResource.socketMap.get(jsonObj.getCode());
				
				if (delSocket == null) {
					//开始注册
					new SocketRegist(socket, jsonObj.getCode());

				}else {
					//确认是否为重连
					if(jsonObj.getAct().equals("recon")){
						//先注销该Socke
						StaticResource.socketMap.remove(jsonObj.getCode());
						delSocket.close();
						delSocket = null;
						//开始注册
						new SocketRegist(socket, jsonObj.getCode());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}