package com.yt.daydaystudy.test_changlianjie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/6/29.
 * 服务端
 */

public class EchoServer {

    private final ServerSocket mServerSocket;

    public EchoServer() throws IOException {
        //1.创建服务端  监听端口
        mServerSocket = new ServerSocket(9999);
    }

    public void run() throws IOException {
        //2.接收客户端
        Socket accept = mServerSocket.accept();
        handleClent(accept);
    }

    //获取从客户端拿到的数据
    private void handleClent(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = is.read(buf))!=-1){
            //将读到的内容发送到服务端
            os.write(buf,0,len);
        }

    }

    public static void main(String[] args) {
        try {
            EchoServer echoServe=  new EchoServer();
            echoServe.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
