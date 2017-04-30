package com.share.service;

import com.share.main.Main;
import com.share.util.SocketUtil;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class SocketListener implements Runnable {
	private String ip = null;// 连接服务器的IP
	private Integer port = null;// 连接服务器的端口
	private String conCode = null;//识别代码
	private Socket socket = null;// 套节字对象
	private boolean close = false; // 关闭连接标志位，true表示关闭，false表示连接
	private JsonHandle jsonDeal = new JsonHandle();

	public SocketListener(String ip, Integer port,String conCode,Socket socket) {
		this.ip = ip;
		this.port = port;
		this.conCode = conCode;
	}

	@Override
	public void run() {
		while (true) {
			// ---------读数据------------
			close = SocketUtil.isServerClose(socket);// 判断是否断开
			if (!close) {// 没有断开，开始读数据
				String readtext = SocketUtil.ReadText(socket);
				if (readtext != null && readtext.trim().length() > 0) {
					jsonDeal.deal(readtext);
				}
			}
			// ---------创建连接-------------------------
			while (close) {// 已经断开，重新建立连接
				//记录失败连接次数
				Integer disConNum = 0;
				try {
					//气泡通知
					if (disConNum == 0) {
						new SendTips("连接已经断开\n尝试重新连接服务器");
					}
					Thread.sleep(5000);
					//System.out.println("re-connect:" + getIp() + ":" + getPort());
					InetAddress address = InetAddress.getByName(ip);
					socket = new Socket(address, port);
					close = !SocketUtil.Send(socket, "{\"act\":\"recon\",\"code\":\""+conCode+"\"}");
					new SendTips("建立连接成功：" + ip + ":" + port);
					//System.out.println("success:" + getIp() + ":" + getPort());
				} catch (Exception se) {
					//记录失败次数
					disConNum++;
					new SendTips("第"+ disConNum +"次尝试连接服务器失败");
					//System.out.println("fail:" + getIp() + ":" + getPort());
					try {
						//连接失败，休眠
						if (disConNum <= 5) {
							Thread.sleep(30000);
						} else if (disConNum > 5 && disConNum <= 10) {
							Thread.sleep(60000);
						} else {
							Main.isConnect = false;
							Thread.sleep(1800000);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					close = true;
				}
			}
		}
	}
}
