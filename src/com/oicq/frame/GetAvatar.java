
package com.oicq.frame;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public final class GetAvatar {

	private static void download(String urlString, String filename, String savePath) throws Exception {  //使用URL类下载网路上的资源
		// 构造URL
		URL url = new URL(urlString); //创建一个URL对象，其传入的参数为一个网址
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream in = con.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File file = new File(savePath);  //创建一个File对象，用来保存头像所在的文件
		if (!file.exists()) {        //如果指定的savePath路径不存在
			file.mkdirs();         //则创建该savePath路径的目录
		}
		OutputStream out = new FileOutputStream(file.getPath() + "\\" + filename);

		// 开始读取
		while ((len = in.read(bs)) != -1) {
			out.write(bs, 0, len);
		}

		// 完毕，关闭所有链接
		out.close();
		in.close();
	}

	public static ImageIcon getAvatarImage(String id, String relativePath, String avatarUrl) {  //该方法用来获取头像
		ImageIcon avatar;
		try {
			String path = relativePath + id + ".jpg";
			if (!new File(path).exists()) {
				download(avatarUrl, id + ".jpg", relativePath);
			}
			avatar = new ImageIcon(path);
		} catch (Exception e) {
			avatar = new ImageIcon("./res/却却却却.jpg");
			System.out.println("获取头像失败，改为默认头像：" + avatarUrl);
		}
		return avatar;
	}
}
