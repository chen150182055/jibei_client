
package com.oicq.frame;

import com.oicq.client.ChatThread;
import com.oicq.client.InteractWithServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

class ExitNowFrameListenter implements ActionListener {
	private JFrame now;   //本窗口的对象
	public ExitNowFrameListenter(JFrame now) {
		this.now = now;
	}  //将本类窗口对象初始化
	@Override
	public void actionPerformed(ActionEvent e) {
		now.dispose();  //退出当前窗口
	}
}

class LoginListener implements ActionListener {
	JFrame now; //本类窗口对象
	JTextField userId; //用户ID文本域对象
	JPasswordField passwd; //用户密码文本域对象
	public void setNow(JFrame now) {
		this.now = now;
	}  //初始化本类窗口对象
	public void setUserId(JTextField userId) {
		this.userId = userId;
	}  //初始化用户ID文本域对象
	public void setPasswd(JPasswordField passwd) {
		this.passwd = passwd;
	} //初始化用户密码文本域对象
	@Override
	public void actionPerformed(ActionEvent e) {    //登录按钮的事件响应方法
//		System.out.println("try login");
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 获取文本框内容
				String userIdString = userId.getText().trim();  //获取用户名
				String userPasswordString = String.valueOf(passwd.getPassword()).trim();  //获取用户密码
				// 验证用户或密码是否正确
				Object isLoginSuccess = InteractWithServer.isLogin(userIdString, userPasswordString); //调用isLogin方法去将用户名和密码包装在ChatVerify类中，并发送给服务器。
				System.out.println("当前登录状态：" + isLoginSuccess);  //给程序员看的，在后台显示是否登录成功
				if (isLoginSuccess != null) { //如果服务器返回的对象不为空
					String loginResult = isLoginSuccess.toString();  //将该方法返回的值装换为一个String类型变量
					if (loginResult.equals("true")) { //如果返回值转换后等于true
						now.dispose();//该窗口退出
						// 创建线程接入聊天端口
						new Thread(new ChatThread(userIdString)).start();//在上述操作结束后，即已经登录成功，便开启这个线程
						System.out.println(1);
						new MainInterface(userIdString);   //进入主界面
						System.out.println(2);
					} else if (loginResult.equals("Repeat_login")) { //如果返回值转换后为Repeat_login
						JOptionPane.showMessageDialog(now, "重复登录");
					} else {  //其他情况则为登录失败
						JOptionPane.showMessageDialog(now, "您的登陆信息有误", "登陆失败", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(now, "与服务器建立连接失败");
				}
			}
		}).start();
	}
}

class SendFriend implements ActionListener {     //发送信息给好友的事件监听类
	private JTextArea message;
	private String mName;
	private String fid;
	private ChatWithFriend now;

	public SendFriend(String mName, String fid) {
		this.mName = mName;
		this.fid = fid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 发送消息
		if(this.message.getText().trim().length()!=0){   //如果该文本区域的内容长度不为0
		now.addMessage(mName, new Date().toString(), this.message.getText());
		ChatThread.getDataStream().send(this.message.getText(), fid);     //调用ChatThread.getDataStream.send()方法将信息传输给fid
		this.message.setText("");  //将文本域置为空
		}
		else
			{JOptionPane.showMessageDialog(now, "发送消息不能为空，请重新输入");}
	}

	public void setMessage(JTextArea message) {
		this.message = message;
	}

	public void setNow(ChatWithFriend now) {
		this.now = now;
	}
}