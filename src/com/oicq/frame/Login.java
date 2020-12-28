package com.oicq.frame;

import javax.swing.*;
import java.awt.*;

public final class Login extends JFrame { //1.继承JFrame类

	//2.创建该类所需的元素
	private static final long serialVersionUID = 1L;
	private JTextField userId;   //用户名填写文本框
	private JLabel   headPortrait;   //记住密码，自动登录  头像
	private JPasswordField passwd;  //密码填写区
	private JButton login;  //登录，找回密码，注册，关闭，最小化按钮。
	private JPanel upPanel, downPanel, textFiledPanel;  //上部面板，下部面板，文本区域面板。
	private LoginListener l;  	//事件监听对象。

	public Login() {    //构造方法
		setLayout(null);  //设置布局为空
		// 更改显示的小图标
		setIconImage(Toolkit.getDefaultToolkit().createImage("./res/mainpanel/qq_logo.png"));
		setTitle("登录窗口");
		upPanel = new JPanel();  //上面的面板
		upPanel.setLayout(null);  //布局设置为空
		upPanel.setBounds(0, 0, 430, 183);  //设置该面板所在的位置
		upPanel.setBackground(new Color(6, 157, 214));   //设置该面板的背景颜色

		downPanel = new JPanel(); //下部面板
		downPanel.setLayout(null);  //设置布局为空
		downPanel.setBounds(0, 184, 430, 152);  //设置该面板的位置
		downPanel.setBackground(new Color(255, 255, 255)); //设置该面板的背景颜色

		headPortrait = new JLabel();  //头像初始化
		headPortrait.setBounds(44, 11, 82, 83);  //设置头像在面板中的位置
		String headPortraitPostion = "./res/却却却却.jpg";    //头像的路径
		Image headPic = (new ImageIcon(headPortraitPostion)).getImage().getScaledInstance(82, 83, Image.SCALE_DEFAULT); //Image类一般尺寸过大，不适合作ImageIcon类
		headPortrait.setIcon(new ImageIcon(headPic));//Image类一般尺寸过大，不适合作ImageIcon类

		textFiledPanel = new JPanel();  //文本区域面板
		textFiledPanel.setBounds(135, 11, 195, 62); //设置位置
		textFiledPanel.setLayout(null);  //设置布局

		userId = new JTextField("");  //账号文本区域的初始化
		userId.setBounds(0, 0, 195, 31); //设置该文本区域的位置

		passwd = new JPasswordField("");  //密码文本区域的初始化
		passwd.setBounds(0, 31, 195, 31); //设置按钮位置
		passwd.setEchoChar('*');  // 设置此JPasswordField的回显字符

		login = new JButton("登   录");//登录按钮的创建
		login.setMargin(new Insets(0, 0, 0, 0));//设置按钮边框和标签之间的空白
		login.setBounds(135, 105, 195, 31);//设置按钮所在位置
		login.setBackground(new Color(6, 111, 18));// there will be annotation

		l = new LoginListener();  //初始化事件监听对象（登录事件）
		l.setNow(this);  //
		l.setUserId(userId); //将用户名传入该监听器类中
		l.setPasswd(passwd); //将密码传入该监听器类中
		userId.addActionListener(l);  //用户名文本区域添加监听器对象
		passwd.addActionListener(l); //密码文本区域添加监听器对象
		login.addActionListener(l);  //登录按钮添加监听器对象
		add(upPanel);
		downPanel.add(headPortrait);
		textFiledPanel.add(userId);
		textFiledPanel.add(passwd);
		downPanel.add(textFiledPanel);
		downPanel.add(login);
		add(downPanel);
		setSize(440, 380);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
