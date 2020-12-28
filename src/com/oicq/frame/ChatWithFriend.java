package com.oicq.frame;

import com.oicq.client.InteractWithServer;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public final class ChatWithFriend extends JFrame {  //1.继承JFrame类

	//3.创建该类所需要的的对象
	private static final long serialVersionUID = 1L;
	private JButton closeButton, sendButton;//最小化，关闭窗口，最小化，关闭，发送按钮
	private JPanel upPanel, inputPanel;  //上面的聊天记录面板，下面的聊天输入面板
	private JLabel friendAvatar, friendName, friendTrades;  //好友头像，好友昵称，好友个性签名标签。
	private String friendAvatarString, friendNameString, friendTradesString, fid, mid, mName; //好友头像，好友昵称，好友个性签名，好友id，个人id，个人昵称，都分别用String变量存储
	private Box showBox;   //两个次级容器，聊天记录
	private JScrollPane showPanel, inputScroll;
	private JTextArea input;			 //聊天输入文本框
	private Image headPic;				 //头像
	private int messageNum = 0;       	 //消息长度

    	//2.创建该类的构造方法
	public ChatWithFriend(String mid, String mName, String fid, String friendAvatarString, String friendNameString,
			String friendTradesString) {
		//4.初始化界面
		this.mid = mid;
		this.mName = mName;
		this.fid = fid;
		this.friendAvatarString = friendAvatarString;
		this.friendNameString = friendNameString;
		this.friendTradesString = friendTradesString;
		setTitle(friendNameString); //设置头像的名字
		setLayout(null);
		// 更改显示的小图标
		headPic = (GetAvatar.getAvatarImage(fid,"./Data/Avatar/User/", friendAvatarString)).getImage().getScaledInstance(41, 37, Image.SCALE_DEFAULT);
		setIconImage(headPic);
		setSize(560, 630);// 526 + 139
		upPanel = new JPanel();
		upPanel.setLayout(null);
		upPanel.setBounds(0, 0, 665, 85);
		upPanel.setBackground(new Color(15, 118, 196));

		friendAvatar = new JLabel(new ImageIcon(friendAvatarString));
		friendAvatar.setBounds(10, 6, 41, 37);
		friendAvatar.setIcon(new ImageIcon(headPic));
		upPanel.add(friendAvatar);

		friendName = new JLabel(friendNameString);
		friendName.setBounds(55, 6, 70, 22);
		upPanel.add(friendName);

		friendTrades = new JLabel(friendTradesString);
		friendTrades.setBounds(55, 31, 400, 15);
		upPanel.add(friendTrades);

		showBox = Box.createVerticalBox();
		showBox.setBackground(new Color(0, 0, 0));
		showPanel = new JScrollPane(showBox);
		showPanel.setBounds(0, 85, 526, 311);

		/*
		 * 获取聊天记录
		 */
		Vector<String> record = InteractWithServer.getChatRecord(mid, fid);   //调用InteractWithServer中的getChatRecord方法向服务器获取聊天记录，并将其存放在一个向量里面
		for (int i = 0; i < record.size(); i++) {  //遍历该向量
			/*
			 * res[0] 消息发送时间 res[1] fromId res[2] toId res[3] message
			 */
			String res[] = record.get(i).split("```", 4);   //将该向量中的字符串以给定规则拆分开
			// 聊天面板显示用户昵称
			String fromName = res[1].equals(mid) ? mName : MainInterface.getFriend().containsKey(res[1]) ? MainInterface.getFriend().get(res[1]).getfName() : ("陌生人:" + res[1]);
			if (res.length == 4) {
				addMessage(fromName, res[0], res[3]);
			}
		}

		inputPanel = new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBounds(0, 396, 526, 138);
		input = new JTextArea();
		input.setBounds(0, 0, 526, 106);
		input.setLineWrap(true);//设置文本区域的换行策略。如果设置为true，则如果线条太长而无法容纳在分配的宽度内，则会对其进行换行
		inputScroll = new JScrollPane(input);
		inputScroll.setBounds(0, 0, 526, 106);
		inputPanel.add(inputScroll);

		closeButton = new JButton("关闭");
		closeButton.setBackground(new Color(58, 77, 195));
		closeButton.addActionListener(new ExitNowFrameListenter(this));
		closeButton.setBounds(361, 106, 70, 24);
		inputPanel.add(closeButton);

		sendButton = new JButton("发送");
		sendButton.setBackground(new Color(58, 77, 195));
		sendButton.setBounds(435, 106, 70, 24);
		SendFriend l = new SendFriend(mName, fid);
		l.setMessage(input);
		l.setNow(this);
		sendButton.addActionListener(l);
		inputPanel.add(sendButton);

		this.add(upPanel);
		this.add(showPanel);
		this.add(inputPanel);

		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public int getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(int messagenum) {
		this.messageNum = messagenum;
	}

	public void addMessage(String userName, String sendTime, String message) {  //将聊天记录以label添加到Box中
		showBox.add(new JLabel(userName + sendTime));
		showBox.add(new JLabel(message));
	}
}