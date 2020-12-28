package com.oicq.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public final class DataStream implements Runnable { //该类实现了Runnable接口，实现了多线程的操作

	private Socket clientSocket; //Socket对象，用来与服务器连接
	private DataInputStream in; //数据输入流，从服务器接收
	private DataOutputStream out;//数据输出流，发送给服务器
	private String userId;		//用户ID
	private String scMessage;	//使用数据输入流所接收到的数据内容，可经过解析后展示到相应窗口


	public DataStream(Socket clientSocket, String userId) {   //构造方法 ，初始化连接对象与用户ID，使用该Socket对象创建数据输入输出流
		this.clientSocket = clientSocket;
		this.userId = userId;
		try {
			in = new DataInputStream(clientSocket.getInputStream());//返回此套接字的输入流。并将该输入流初始化给in
			out = new DataOutputStream(clientSocket.getOutputStream());//返回此套接字的输出流，并将该输出流初始化给out
		} catch (IOException e) {
			System.out.println("创建聊天数据流失败：" + e.getMessage());  // 如果与服务端之间建立数据输入输出流失败，则产生IOException.
		}
	}


	@Override
	public void run() {  //run方法,因为数据输入流读取时候会阻塞，所以将其单独分配在一个线程里面
		try {
			while (true) {  //死循环，一直执行下列操作
				scMessage = in.readUTF(); 	// 读取消息内容，从包含的输入流中读取此操作的字节。并将这些字节初始化给scMessage
				ChatExecute.execute(scMessage);// 解析处理消息
			}
		} catch (IOException e) {
			//如果程序执行到这里，可能是因为与服务器断开连接，所以需要关闭这些流
			try {
				in.close();  //关闭输入流
			} catch (Exception e2) {
				System.out.println("数据输入流关闭失败：" + e.getMessage());
			}
			try {
				out.close();  //关闭输出流
			} catch (Exception e2) {
				System.out.println("数据输出流关闭失败：" + e.getMessage());
			}
			try {
				clientSocket.close();  //关闭客户端套接字
			} catch (IOException e1) {
				System.out.println("服务器连接关闭失败：" + e.getMessage());
			}
			System.out.println("与服务端失去联系 " + e.getMessage());
		}
	}


	public void send(String message, String toId) { //发送信息到服务器的方法
		// 对发送内容进行特定编码
		message = "toFriend```" + userId + "```" + toId + "```" + message;    //例： "toFriend```10001```10002```你好" 这样的格式代表将“你好”从本用户发送给10002岁
		try {
			out.writeUTF(message); //将message写入输出流
		} catch (IOException e) {
			System.out.println("发送消息失败：" + e.getMessage());
		}
	}
}
