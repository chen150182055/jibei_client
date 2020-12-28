package com.oicq.frame;

import com.oicq.client.InteractWithServer;
import com.oicq.config.UserInfo.Friends;
import com.oicq.user.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;

public final class MainInterface extends JFrame implements ActionListener { //继承JFrame类，实现ActionListener接口
	//创建该类所需的元素
	private static final long serialVersionUID = 1L;
	private JPanel upPanel, downPanel, friendPane;  //创建上面的面板，下面的面板，好友面板，群组面板
	private JButton  tradesButton;  //创建最小化，关闭，个性签名，好友，群组按钮。
	private JLabel  nameLabel;  //创建标签用以显示用户昵称
	private JButton headPortrait;  //单独设置按钮显示头像，并且点击后修改该按钮。
	private Box nameBox;  //
	private JTextField tradesTextField;    //个性签名文本框
	private User userInfo;  //用户信息
	private JScrollPane friendScrollPane; //滑块面板
	// 用户信息部分
	private static HashMap<String, FriendModel> friend;   //好友ID和该好友ID面板的HashMap
	private static HashMap<String, ChatWithFriend> withFriend;  //聊天的好友的Id和聊天好友聊天面板的HashMap
	static {
		//说明，该两个HashMap是为了能通过好友的Id从而去寻找到相关的好友面板，与该好友的聊天窗口。
		friend = new HashMap<String, FriendModel>();   //HashMap,保存了一个好友ID为键，FriendModel对象为值的集合。
		withFriend = new HashMap<String, ChatWithFriend>();  //HashMap,保存了一个好友Id为键，ChatWithFriend对象的值的集合。
	}

	public MainInterface(String userId) {
		// 获取用户信息
		userInfo = InteractWithServer.getUserInfo(userId); //与服务器进行交互，获取该用户的用户信息
		// 界面部分
		// 更改显示的小图标
		setTitle("主界面 --" + userInfo.getUserName() + "在线");   //将该窗口的标题设置为用户的在线状态
		upPanel = new JPanel();  //初始化上面的面板
		upPanel.setLayout(null); //设置布局为空
		upPanel.setBounds(0, 0, 288, 140); //设置位置
		upPanel.setBackground(new Color(6, 157, 214));//设置背景颜色
		headPortrait = new JButton();  //头像按钮
		headPortrait.setBounds(10, 43, 61, 60);  //设置位置
		headPortrait.setVisible(true); //设置是否可见
		Image headPic = (GetAvatar.getAvatarImage(userInfo.getUserId(), "./Data/Avatar/User/", userInfo.getUserAvatar())).getImage().getScaledInstance(61, 60, Image.SCALE_DEFAULT);  //设置头像为该用户的头像
		headPortrait.setIcon(new ImageIcon(headPic));  //将图片添加到按钮上
		headPortrait.addActionListener(this);  //实现事件监听
		nameBox = Box.createHorizontalBox(); // 便于以后添加别的组件
		nameBox.setBounds(77, 43, 158, 17);   //设置位置
		String username = userInfo.getUserName(); // 用户昵称
		nameLabel = new JLabel(username);  //用户名称标签
		nameLabel.setForeground(Color.WHITE);//设置前景色
		nameBox.add(nameLabel);  //将该标签添加进BOX中
		tradesButton = new JButton();  //初始化个性签名按钮
		tradesButton.setBounds(77, 64, 137, 19);  //设置位置
		tradesButton.setContentAreaFilled(false);
		tradesButton.setBorderPainted(false);
		tradesButton.setRolloverIcon(new ImageIcon("./res/MainInterface/ContactFilter_splitter.png"));
		String trades = userInfo.getUserTrades();
		if (trades.equals("")) { //如果用户信息没有个性签名
			trades = "编辑个性签名"; //则将其设置为"编辑个性签名"
		}
		tradesButton.setText(trades);  //将个性签名添加到按钮上去显示
		tradesButton.setToolTipText(trades);  //注册文本以显示在工具提示中。当光标停留在组件上方时，将显示文本。
		tradesTextField = new JTextField();  //初始化个性签名文本区域
		tradesTextField.setBounds(77, 64, 137, 19);  //设置位置，注意这里是和上面的个性签名按钮重合的
		tradesTextField.setVisible(false);  //设置该文本区域为不可视
		tradesButton.addActionListener(new ActionListener() { // Button
			// 点击之后变为TextField
			@Override
			public void actionPerformed(ActionEvent e) {
				tradesButton.setVisible(false);  //点击后设置按钮为不可视
				tradesTextField.setVisible(true);//此时文本区域可视
				if (tradesButton.getText().equals("编辑个性签名")) {  //如果按钮上的信息为"编辑个性签名"
					tradesTextField.setText("");//将文本区域置空
				} else {
					tradesTextField.setText(tradesButton.getText()); //直接获取按钮上的信息到文本区域
				}
				tradesTextField.requestFocus();//请求此Component获得输入焦点
			}
		});
		tradesTextField.addFocusListener(new FocusListener() { // TextField失去焦点之后变为Button并将更改后的内容传送给服务器

			@Override
			public void focusLost(FocusEvent e) {  //时间响应处理
				tradesTextField.setVisible(false);  //设置个性签名文本区域为不可视
				if (tradesTextField.getText().equals("")) { //如果个性签名文本区域为空
					tradesButton.setText("编辑个性签名"); //按钮上被置为"编辑个性签名"
				} else {
					tradesButton.setText(tradesTextField.getText());  //将按钮上的信息置为文本区域上的内容

					// 更新服务端数据
					InteractWithServer.setMyTrades(userInfo.getUserId(), tradesTextField.getText());  //将该个性签名同步到服务器
				}
				// Send the message to server

				tradesButton.setVisible(true); //将按钮设置为可视
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		tradesTextField.addActionListener(new ActionListener() {  //添加个性签名文本区域的上的事件监听

			@Override
			public void actionPerformed(ActionEvent e) {  //事件处理方法
				tradesTextField.setVisible(false);  //设置个性签名本文区域为不可视
				if (tradesTextField.getText().equals("")) {  //如果个性签名文本区域上的内容为空
					tradesButton.setText("编辑个性签名"); //将按钮上的内容置为"编辑个性签名"
				} else {
					tradesButton.setText(tradesTextField.getText());  //将按钮上的内容设置为文本区域输入的内容
				}

				tradesButton.setVisible(true); //设置按钮为可视
			}
		});

		downPanel = new JPanel();  //初始化下部面板
		downPanel.setLayout(null);  //设置布局为空
		downPanel.setBackground(new Color(0xFFFFFF));//设置背景颜色
		downPanel.setBounds(0, 140, 288, 400);  //设置位置


		/**
		 * friend model
		 */
		int friendsNumber = userInfo.getFriends().size();  //定义好友数量为该UserInfo中的好友数量（向量的大小）
		friendPane = new JPanel(); //初始化好友面板
		friendPane.setLayout(null);//设置布局为空
		friendPane.setBounds(0, 0, 288, friendsNumber * 51);//设置该面板的位置，长和宽。
		friendPane.setPreferredSize(new Dimension(270, friendsNumber * 51));//设置此组件的首选大小

		for (int i = 0; i < friendsNumber; i++) {
			Friends userFriend = userInfo.getFriends().get(i);
			String fAvatar = userFriend.getAvatar();  //取出该类封装的头像
			String fName = userFriend.getName();	//取出该类封装的名字
			String fTrades = userFriend.getTrades();	//取出该类封装的个性签名
			String fid = userFriend.getId();		//取出该类封装的用户ID
			String fOnline = userFriend.getStatus();//取出该类封装的用户登录状态
			friend.put(fid, new FriendModel(fAvatar, fName, fTrades, fid, fOnline));  //将以上信息以键为用户Id，值为以以上信息创建的FriendModel为对象插入HashMap中
			friend.get(fid).setBounds(0, i * 51, 288, 51);  //获取以fid为值的HashMap的键的FriendModel对象，并将该对象进行设置
			friend.get(fid).addMouseListener(new MouseListener() {  //添加该对象的事件处理
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
				}
				@Override
				public void mouseExited(MouseEvent e) {
				}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseClicked(MouseEvent e) { //事件监听
					if (e.getClickCount() == 2) { //单击两次
						//这里指对着想要与之聊天的好友的那个框进行点击两次，则将以该好友Id为值，相应ChatWithFriend对象为键插入到withFriend这个HashMap中，其中ChatWithFriend对象包含了该用户的Id，昵称，以及好友Id，好友头像，好友昵称，好友个性签名
						withFriend.put(fid, new ChatWithFriend(userInfo.getUserId(), userInfo.getUserName(), fid, fAvatar, fName, fTrades));
					}
				}
			});
			friendPane.add(friend.get(fid)); //将以好友ID为键，FriendModel对象为值的的FriendModel对象添加到好友面板中。
		}

		friendScrollPane = new JScrollPane(friendPane);
		friendScrollPane.setBounds(0, 0, 288, 400);

		setLayout(null);
		upPanel.add(headPortrait);
		upPanel.add(nameBox);
		upPanel.add(tradesButton);
		upPanel.add(tradesTextField);
		downPanel.add(friendScrollPane);
		add(upPanel);
		add(downPanel);
		setSize(305, 585);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public static HashMap<String, FriendModel> getFriend() {
		return friend;
	};

	public static HashMap<String, ChatWithFriend> getFriendChat() {
		return withFriend;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//设置JFileChooser以允许用户仅选择文件，仅选择目录或同时选择文件和目录。显示文件和目录的指令
		jfc.showDialog(new JLabel(), "选择");//弹出带有自定义批准按钮的自定义文件选择器对话框
		File file = jfc.getSelectedFile();   //创建一个文件类，返回所选文件。
		if (file.isDirectory()) { //如果选择的是一个目录。
			System.out.println("文件夹:" + file.getAbsolutePath()); //返回此抽象路径名的绝对路径名字符串
		} else if (file.isFile()) {  //如果选择的是一个文件
			System.out.println("文件:" + file.getAbsolutePath()); //返回此抽象路径名的绝对路径名字符串
		}
		System.out.println(jfc.getSelectedFile().getName());
	}
}