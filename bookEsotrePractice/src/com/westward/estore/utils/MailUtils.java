package com.westward.estore.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 发送邮件工具类
 * */
public class MailUtils {
	private MailUtils() {
	}
	
	public static void sendMail(String email,String emailMsg) throws AddressException, MessagingException{
		Properties properties= new Properties();
		properties.setProperty("mail.transport.protocol", "SMTP");
		properties.setProperty("mail.host", "smtp.sohu.com");
		properties.setProperty("mail.smtp.auth", "true");
		
		Authenticator authenticator= new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("####", "****");
			}
		};
		
		Session session= Session.getInstance(properties, authenticator);
		
		Message message= new MimeMessage(session);
		message.setFrom(new InternetAddress("westwardyao@sohu.com"));
		message.setRecipient(RecipientType.TO, new InternetAddress(email));
		message.setSubject("用户激活");
		message.setContent(emailMsg, "text/html;charset=utf-8");
		
		Transport.send(message);
		
	}
	
}
