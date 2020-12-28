
package com.oicq.config;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public final class ChatVerify implements Serializable {   //该类被序列化 （聊天验证）


	private static final long serialVersionUID = -4490443980607193791L;


	private String userId;  //用户名


	private String userPassword;   //密码


	public ChatVerify(String userId, String userPassword) {  //构造方法，通过传入的用户名和密码创建一个加密后的对象
		this.userId = userId;          //将用户名包装进该类
		this.userPassword = getMd5(userPassword);  //将密码进行MD5加密后包装进该类
	}


	public String getUserId() {
		return userId;
	}


	public String getUserPassword() {
		return userPassword;
	}


	private String getMd5(String str) {  //MD5加密方法
		String mdPassword = "";  //声明一个String变量用来存储后面的密码
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");  //返回实现指定摘要算法的 MessageDigest 对象用于MD5加密的。

			// 计算md5函数
			md.update(str.getBytes()); //使用指定的字节数组更新摘要。

			// 保留16位
			mdPassword = new BigInteger(1, md.digest()).toString(16);  //取前16位保留
			/*MessageDigest 对象开始被初始化。该对象通过使用 update（）方法处理数据。任何时候都可以调用 reset（）方法重置摘要。
			一旦所有需要更新的数据都已经被更新了，应该调用digest() 方法之一完成哈希计算。
			对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。*/
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD5加密失败：" + e.getMessage());
		}
		return mdPassword;
	}
}
	/*java.security. MessageDigest 类用于为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。简单点说就是用于生成 散列码。 信息摘要是安全的单向哈希函数，它接收任意大小的数据，输出固定长度的哈希值。关于 信息摘要 和 散列码 请参照《 数字证书简介 》
		MessageDigest 通过其getInstance系列静态函数来进行实例化和初始化。MessageDigest 对象通过使用 update 方法处理数据。任何时候都可以调用 reset 方法重置摘要。一旦所有需要更新的数据都已经被更新了，应该调用 digest 方法之一完成哈希计算并返回结果。

		对于给定数量的更新数据，digest 方法只能被调用一次。digest 方法被调用后，MessageDigest  对象被重新设置成其初始状态。

		MessageDigest 的实现可随意选择是否实现 Cloneable 接口。客户端应用程可以通过尝试复制和捕获 CloneNotSupportedException 测试可复制性：*/