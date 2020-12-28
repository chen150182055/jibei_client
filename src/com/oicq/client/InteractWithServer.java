package com.oicq.client;

import com.oicq.config.ChatVerify;
import com.oicq.config.ServerInfo;
import com.oicq.user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public final class InteractWithServer {  //该类被声明为final

	private static Object postToServer(Object obj) {     //与服务器验证端口建立连接，并使用对象流传输数据。传入的参数是一个Object类的对象，该对象的好处是方便不同的对象传入
		Object result = null;   //创建Object对象result
		try {
			// 建立连接
			Socket sc = new Socket(ServerInfo.SERVER_IP, ServerInfo.VERIFY_PORT); //初始化Socket用于连接服务器
			// 创建对象输入输出流
			ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
			out.writeObject(obj);// 写对象到服务端
			// 获取返回信息
			result = in.readObject();   //返回服务器所发回的对象
			// 关闭流
			sc.close();
			in.close();
			out.close();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("在与服务器验证交互中出现了异常：" + e.getMessage());
		}
		return result;
	}

	public static Object isLogin(String userId, String userPassword) {  //传入的参数为用户要登录的用户名和密码
		// 构造身份信息
		ChatVerify userInfo = new ChatVerify(userId, userPassword);  //将用户名和密码包装进ChatVerifty将其加密，并生成该类的对象
		// 返回服务器产生的结果
		return postToServer(userInfo);  //返回给服务器这个密码加密后的用户对象
	}

	public static User getUserInfo(String userId) {
		User userInfo = null; //声明一个用户对象
		String fieldString = "getUserInfo" + userId;    //用户的ID用于发送给服务器，从而查询该用户的信息
		userInfo = (User) postToServer(fieldString);  	//发送给服务器并返回服务器查找到的对象，再将其强制转换为User类型
		return userInfo;
	}

	public static Vector<String> getChatRecord(String fromid, String toId) { //获取聊天记录
		String sendString = "getChatRecord```" + fromid + "```" + toId + "```" ;
		return (Vector<String>) postToServer(sendString);
	}

	public static void setMyTrades(String myId, String content) {
		postToServer("setMyTrades```" + myId + "```" + content);
	}
}
