package com.oicq.client;

import com.oicq.config.ServerInfo;
import java.io.IOException;
import java.net.Socket;

public final class ChatThread implements Runnable {   //该类继承了Runnable接口，实现了多线程操作

	private String userId;
	private static DataStream dataStream;

	public ChatThread(String userId) {
		this.userId = userId;
	}

	/*
	 *  创建与服务器聊天端口的通讯，并创建线程开始(接收/发送)数据
	 *  如果与服务端建立Socket连接失败，则产生IOException.
	 */
	@Override
	public void run() {    //run方法定义了这个线程做什么事情
		Socket myHost = null;  //声明一个String变量作为客户端端口
		try {
			// 创建与服务端的连接
			myHost = new Socket(ServerInfo.SERVER_IP, ServerInfo.CHAT_PORT);  //创建一个流套接字，并将其连接到命名主机上的指定端口号IP为服务器地址，PORT为端口

			// 聊天数据信息流，用来接收或者发送信息
			dataStream = new DataStream(myHost, userId);
			new Thread(dataStream).start();//dataStream –启动此线程时调用run方法的对象。如果为null，则此类run方法不执行任何操作。
		} catch (IOException e) {
			System.out.println("创建与服务端的连接出错：" + e.getMessage());
		}
	}

	public static DataStream getDataStream() {
		return dataStream;
	}
}
