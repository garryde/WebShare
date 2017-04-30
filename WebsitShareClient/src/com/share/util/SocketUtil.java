package com.share.util;

import java.io.*;
import java.net.Socket;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class SocketUtil {

    /**
     * 发送数据，发送失败返回false,发送成功返回true
     *
     * @param socket
     * @param message
     * @return
     */
    public static Boolean Send(Socket socket, String message) throws IOException {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            return true;
    }

    /**
     * 读取数据，返回字符串类型
     *
     * @param socket
     * @return
     */
    public static String ReadText(Socket socket) {
        try {
            InputStream input = socket.getInputStream();
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
    public static Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }
}
