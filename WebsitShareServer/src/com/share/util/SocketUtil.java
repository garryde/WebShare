package com.share.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;


public class SocketUtil {


	public static void writeStr2Stream(String str, OutputStream out) throws IOException {
		try {
			// add buffered writer
			BufferedOutputStream writer = new BufferedOutputStream(out);

			// write
			writer.write(str.getBytes());
			
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex);
			throw ex;
		}
	}

	public static String readStrFromStream(InputStream in) throws IOException {
		//System.out.println(getNowTime() + " : start to read string from stream");
		StringBuffer result = new StringBuffer("");

		// build buffered reader
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		// read 1024 bytes per time
		char[] chars = new char[2048];
		int len;
		
		try {
			while ((len = reader.read(chars)) != -1) {
				// if the length of array is 1M
				if (2048 == len) {
					//then append all chars of the array
					result.append(chars);
					System.out.println("readStrFromStream : " + result.toString());
				} 
				// if the length of array is less then 1M
				else {
					//then append the valid chars
					for (int i = 0; i < len; i++) {
						result.append(chars[i]);
						//System.out.println("readStrFromStream : " + result.toString());
					}
					break;
				}
			}

		} catch (IOException e) {
			System.out.println(e);
			throw e;
		}
		//System.out.println("end reading string from stream");
		return result.toString();
	}
	
	public static Boolean isServerClose(Socket socket) {
		try {
			socket.sendUrgentData(0);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			return true;
		}
	}
	
	public static String getNowTime()
	{
		return new Date().toString();
	}

}
