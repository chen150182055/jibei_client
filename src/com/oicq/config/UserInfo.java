package com.oicq.config;

import java.io.Serializable;
import java.util.Vector;


public class UserInfo implements Serializable {    //该类包装了个人信息和朋友列表，用以客户端和服务端的数据传输

	private static final long serialVersionUID = 4146085358128616967L;
	protected String userId;
	protected String userName;
	protected String userEmail;
	protected String userSex;
	protected String userBirthday;
	protected String userAvatar;
	protected String userTrades;
	protected String userRegistertime;
	protected Vector<Friends> friends = new Vector<Friends>();

	public static class Friends implements Serializable {  //内部类（好友列表）
		private static final long serialVersionUID = -1855195980029629286L;

		private String id;
		private String name;
		private String avatar;
		private String trades;
		private String status;


		public Friends(String id, String name, String avatar, String trades, String status) {  //构造方法，创建一个群信息对象或好友信息对象
			this.id = id;  //将好友/群ID包装进该内部类
			this.name = name; //将好友/群名包装进该内部类
			this.avatar = avatar; //好友/群头像（url）包装进内部类
			this.trades = trades; //将好友/群个性签名包装进该内部类
			this.status = status; //将好友/群登录状态包装进内部类
		}


		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getAvatar() {
			return avatar;
		}

		public String getTrades() {
			return trades;
		}

		public String getStatus() {
			return status;
		}
	}
}
