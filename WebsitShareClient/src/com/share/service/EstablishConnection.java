package com.share.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EstablishConnection extends Thread {
	public String ip = null;// 连接服务器的IP
	public Integer port = null;// 连接服务器的端口
	public String conCode = null;//识别代码
	private Socket socket = null;// 套节字对象
	private boolean close = false; // 关闭连接标志位，true表示关闭，false表示连接
	private JsonHandle jsonDeal = new JsonHandle();
	// ------------------------------------------------------------------------------

	public EstablishConnection(String ip, Integer port,String conCode) {
		setIp(ip);
		setPort(port);
		setConCode(conCode);
	}

	/**
	 * 初始化socket对象
	 */
	public boolean connect() {
		try {
			InetAddress address = InetAddress.getByName(getIp());
			socket = new Socket(address, getPort());
			socket.setKeepAlive(true);
			close = !Send(socket, "{\"act\":\"regist\",\"code\":\""+conCode+"\"}");
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		} 
	}

	/**
	 * 读数据线程
	 */
	public void run() {
		while (true) {
			// ---------读数据---------------------------
			close = isServerClose(socket);// 判断是否断开
			if (!close) {// 没有断开，开始读数据
				String readtext = ReadText(socket);
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
					Thread.sleep(30000);
					//System.out.println("re-connect:" + getIp() + ":" + getPort());
					InetAddress address = InetAddress.getByName(getIp());
					socket = new Socket(address, getPort());
					socket.setKeepAlive(true);
					close = !Send(socket, "{\"act\":\"regist\",\"code\":\""+conCode+"\"}");
					new SendTips("建立连接成功：" + getIp() + ":" + getPort());
					//System.out.println("success:" + getIp() + ":" + getPort());
				} catch (Exception se) {
					//失败次数记录
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

	/**
	 * 发送数据，发送失败返回false,发送成功返回true
	 * 
	 * @param csocket
	 * @param message
	 * @return
	 */
	public Boolean Send(Socket csocket, String message) {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(message);
			return true;
		} catch (Exception se) {
			se.printStackTrace();
			return false;
		}
	}

	/**
	 * 读取数据，返回字符串类型
	 * 
	 * @param csocket
	 * @return
	 */
	public String ReadText(Socket csocket) {
		try {
			InputStream input = csocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			char[] sn = new char[1000];
			int len = in.read(sn);
			String sc = new String(sn,0,len);
			return sc;
		} catch (IOException se) {
			return null;
		}
	}

	/**
	 * 判断是否断开连接，断开返回true,没有返回false
	 * 
	 * @param socket
	 * @return
	 */
	public Boolean isServerClose(Socket socket) {
		try {
			socket.sendUrgentData(0);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			return true;
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}
	
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
