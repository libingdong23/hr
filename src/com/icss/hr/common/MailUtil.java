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
 * �����ʼ�������
 * 
 * @author DLETC
 *
 */
public class MailUtil {

	/**
	 * ���͵����ʼ�
	 */
	public static void sendMail(String username, String from, String userpwd, String to, String title, String content) {

		// ���ò���
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");// �Ƿ��������֤
		props.setProperty("mail.transport.protocol", "smtp");// ʹ�õĴ���Э��

		// �����ʼ�����������
		Session session = Session.getInstance(props);

		// ��ʾ������Ϣ
		session.setDebug(true);

		// ��Ϣ����
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(from));// ���ͷ��������ַ

			msg.setSubject(title);// ����

			// �ʼ�����
			msg.setContent(content, "text/html;charset=gbk;");

			// �������
			Transport transport = session.getTransport();

			// ����smtp�������Ͷ˿��Լ��˺ź����룬ע��˴���tom@icss.com����ĳЩ�ʼ�������дtom�Ϳ���
			transport.connect("localhost", 25, username, userpwd);

			// ���շ��������ַ
			transport.sendMessage(msg, new Address[] { new InternetAddress(to) });

			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}