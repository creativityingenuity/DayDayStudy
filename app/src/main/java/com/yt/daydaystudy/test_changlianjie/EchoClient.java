package com.yt.daydaystudy.test_changlianjie;

import java.io.IOException;
import java.net.Socket;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/6/29.
 */

public class EchoClient {

    private final Socket socket;

    public EchoClient() throws IOException {
        socket = new Socket("localhost",9999);

//        DatagramSocket datagramSocket = new DatagramSocket();
//        datagramSocket.connect();
    }

    public void run(){
        //在读取用户输入的同时，我们又想读取服务器的响应。所以，这里创建了一个线程来读服务器的响应。
//        socket.
    }

    public static void main(String[] argv) {
        try {
            EchoClient  echoClient  = new EchoClient();
            echoClient.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
