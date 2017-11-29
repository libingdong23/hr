package com.icss.hr.common;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 电子邮件工具类
 * 
 * @author DLETC
 *
 */
public class MailUtil {

	/**
	 * 发送电子邮件
	 */
	public static void sendMail(String username, String from, String userpwd, String to, String title, String content) {

		// 配置参数
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");// 是否有身份验证
		props.setProperty("mail.transport.protocol", "smtp");// 使用的传输协议

		// 连接邮件服务器对象
		Session session = Session.getInstance(props);

		// 显示调试信息
		session.setDebug(true);

		// 信息对象
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(from));// 发送方的邮箱地址

			msg.setSubject(title);// 主题

			// 邮件内容
			msg.setContent(content, "text/html;charset=gbk;");

			// 传输对象
			Transport transport = session.getTransport();

			// 连接smtp服务器和端口以及账号和密码，注意此处是tom@icss.com，但某些邮件服务器写tom就可以
			transport.connect("localhost", 25, username, userpwd);

			// 接收方的邮箱地址
			transport.sendMessage(msg, new Address[] { new InternetAddress(to) });

			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}