package com.share.service;

import com.share.main.Main;
import com.share.util.SocketUtil;

import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class SocketListener extends Thread {

	private String ip = null;// 连接服务器的IP
	private Integer port = null;// 连接服务器的端口
	private String conCode = null;//识别代码
	private Socket socket = null;// 套节字对象
	private boolean close = false; // 关闭连接标志位，true表示关闭，false表示连接
	private SocketHeart socketHeart = null;
	private JsonHandle jsonDeal = new JsonHandle();

	//测试代码，记录断开次数
	private int disconnectNum = 0;

	public SocketListener(String ip, Integer port,String conCode,Socket socket,SocketHeart socketHeart) {
		this.ip = ip;
		this.port = port;
		this.conCode = conCode;
		this.socket = socket;
		this.socketHeart = socketHeart;
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
			// 已经断开，重新建立连接
			while (close) {
				//暂停心跳线程
				socketHeart.setRun(false);

				//测试代码，增加断开次数
				disconnectNum ++;

				//测试代码输出时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				System.out.println("第" + disconnectNum + "次断开连接，时间：" + df.format(new Date()));// new Date()为获取当前系统时间

				//记录失败连接次数
				Integer disConNum = 0;

				try {
					//第一次重连，气泡通知
					if (disConNum == 0) {
						new SendTips("连接已经断开\n尝试重新连接服务器");
					}
					//休眠5秒，防止临时网络故障
					Thread.sleep(5000);
					//重连服务器
					InetAddress address = InetAddress.getByName(ip);
					socket = new Socket(address, port);
					//发送重连socket
					close = !SocketUtil.Send(socket, "{\"act\":\"recon\",\"code\":\""+conCode+"\"}");
					//唤醒心跳线程
					socketHeart.setRun(true);
					//发送至全局Socket
					Main.socket.close();
					Main.socket = null;
					Main.socket = socket;
					new SendTips("建立连接成功：" + ip + ":" + port);
				} catch (Exception se) {
					//记录失败次数
					disConNum++;
					new SendTips("第"+ disConNum +"次尝试连接服务器失败");
					//System.out.println("fail:" + getIp() + ":" + getPort());
					try {
						//连接失败，休眠
						if (disConNum <= 5) {
							System.out.println("准备休眠30s");
							Thread.sleep(30000);
						} else if (disConNum > 5 && disConNum <= 10) {
							System.out.println("准备休眠60s");
							Thread.sleep(60000);
						} else {
							Main.isConnect = false;
							System.out.println("准备休眠30min");
							Thread.sleep(1800000);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
