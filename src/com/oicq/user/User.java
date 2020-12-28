package com.oicq.user;

import com.oicq.config.UserInfo;

import java.util.Vector;

public final class User extends UserInfo {   //继承用户信息类


	private static final long serialVersionUID = -2844611810327524136L;

	public User(String userId, String userName, String userEmail, String userSex, String userBirthday,
			String userAvatar, String userTrades, String userRegistertime, Vector<Friends> friends) {   //构造方法，初始化用户信息内容
		this.userId = userId;  //将传入的用户ID包装进本类中
		this.userName = userName; //将传入的用户名包装进本类中
		this.userEmail = userEmail; //将传入的邮箱包装进本类中
		this.userSex = userSex;  //将传入的性别包装进本类中
		this.userBirthday = userBirthday;  //将传入的生日包装进本类中
		this.userAvatar = userAvatar; //将传入的头像(url)包装进本类中
		this.userTrades = userTrades; //将传入的个性签名包装进本类中
		this.userRegistertime = userRegistertime; //将用户的注册时间包装进本类中
		this.friends = friends; //将传入的好友列表包装进本类中
	} 					//这样，该类在完成构造方法的初始化后，就包装了一个完整的用户信息


	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserSex() {
		return userSex;
	}

	public String getUserBirthday() {
		return userBirthday;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public String getUserTrades() {
		return userTrades;
	}

	public String getUserRegistertime() {
		return userRegistertime;
	}

	public Vector<Friends> getFriends() {
		return friends;
	}

}
