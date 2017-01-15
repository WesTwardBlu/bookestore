package com.westward.estore.service.impl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.westward.estore.dao.UserDao;
import com.westward.estore.domain.User;
import com.westward.estore.exception.RegistException;
import com.westward.estore.service.UserService;
import com.westward.estore.utils.MailUtils;

public class UserServiceImpl implements UserService{
	public UserServiceImpl() {
	}
	
	@Override
	public void regist(User user) throws RegistException {
		try {
			new UserDao().addUser(user);
			String emailMsg= "注册成功，请在12小时内<a href='http://localhost:8090/bookEstore/user?method=activeuser&activecode="+ user.getActivecode()+ "'>激活</a>，激活码是"+user.getActivecode();
			MailUtils.sendMail(user.getEmail(), emailMsg);
		} catch (SQLException e) {
			throw new RegistException("注册失败", e);
		} catch (AddressException e) {
			throw new RegistException("发送邮件失败", e);
		} catch (MessagingException e) {
			throw new RegistException("发送邮件失败", e);
		}
	}
	
	@Override
	public User login(String username, String password) throws Exception {
		// TODO 待补全
		return null;
	}
	
	@Override
	public void activeUser(String activecode) throws Exception {
		// TODO 待补全
		
	}
}
