package com.westward.estore.service.impl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.westward.estore.dao.UserDao;
import com.westward.estore.domain.User;
import com.westward.estore.exception.LoginException;
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
	
	/**
	 * <b>service层</b><br>
	 * 判断用户名秘密<br>
	 * 判断用户是否激活
	 * */
	@Override
	public User login(String username, String password) throws LoginException {
		UserDao userDao= new UserDao();
		User user= null;
		try {
			user= userDao.findUserByUserNameAndPassword(username, password);
			if (user==null) {
				throw new LoginException("用户名或密码不正确");
			}
			//判断用户状态
			if (0==user.getState()) {
				throw new LoginException("用户未激活");
			}
		} catch (SQLException e) {
			throw new LoginException("登录失败", e);
		}
		
		return user;
	}
	
	/**
	 * service层激活用户方法<br>
	 * 1.先判断激活码是否超时<br>
	 * 2.根据激活码激活用户
	 * */
	@Override
	public void activeUser(String activecode) throws Exception {
		UserDao userDao= new UserDao();
		User user= null;
		try {
			user= userDao.findUserByActiveCode(activecode);
		} catch (SQLException e) {
			throw new RegistException("根据激活码查找用户失败", e);
		}
		// 判断激活码是否超时 生产是12小时，测试 1分钟
		long time= System.currentTimeMillis()- user.getUpdatetime().getTime();
		if (time> 12*60*60*1000) {
			throw new RegistException("激活码过期");
		}
		//进行激活操作
		try {
			userDao.activeUserByActivecode(activecode);
		} catch (SQLException e) {
			throw new RegistException("激活失败！", e);
		}
		
	}
}
