package com.oicq.client;

import com.oicq.frame.ChatWithFriend;
import com.oicq.frame.MainInterface;

import java.util.HashMap;

public final class ChatExecute { //聊天执行,该类被声明为一个final类

	private static String message;
	private static String fromId;
	private static String toId;
	private static String type;

	public static void execute(String scMessage) {   //该方法针对的是从服务器发送来的信息，进行不同的处理，其处理方式是区分res[]数组的长度来判断内容是什么
		String res[] = scMessage.split("```", 5);  // 对接收到的消息内容进行解码，split方法以```为分隔符，将该字符串分为5个
		// 从服务端发送的内容解码之后长度为5，代表该消息为聊天内容
		if (res.length == 5) {  //如果该数组长度为5
			type = res[1];  //将第res[1]的值赋给type
			System.out.println("type"+" " +type);
			fromId = res[2]; //将res[2]的值赋给fromId
			System.out.println(fromId);
			toId = res[3];  //将res[3]的值赋值给toId
			System.out.println(toId);
			message = res[4]; //将res[4]的值赋值给message
			System.out.println( "message  "+message);
			// 以ID为键，对应聊天面板为值的哈希映射
			HashMap<String, ChatWithFriend> model;   //ID为键，聊天面板为值

			// 接收到的消息是从好友发送来的
			if (type.equals("toFriend")) {   //如果消息类型为"toFriends"
				model = MainInterface.getFriendChat();  //对HashMap进行初始化,即将该好友对应的聊天窗口相关联
				// 展示在对应好友聊天面板中
				if (model.containsKey(fromId)) {   //如果此Map包含指定键的映射，则返回true。即如果fromId在model中存在，便返回这个HashMap
					 //将值fromId所对应的聊天窗口面板中的消息文本域添加消息。
					model.get(fromId).addMessage(MainInterface.getFriend().get(fromId).getfName(), res[0], message); //返回指定键所映射到的值，并将其好友名字，
				}
			}
		} // 接收的内容是为了改变用户状态（在线/离线）
		else if (res.length == 3) {  //如果该数组长度为3
			// res[0]:验证标识、res[1]:状态信息、res[2]:好友ID
			if (res[0].equals("OnlineSituation")) {   //并且res[0]为OnlineSituation
				if (MainInterface.getFriend().containsKey(res[2])) {  //如果值为res[2]即fromId的HashMap存在
					MainInterface.getFriend().get(res[2]).setfOnline(res[1]);   //返回值为res[2]的HashMap的键的FriendModel对象，并设置该对象的在线状态为res[1]所含的状态
				}
			}
		}
	}
}
