package com.westward.estore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.westward.estore.domain.User;
import com.westward.estore.utils.DataSourceUtils;

/**
 * 操作user表的类
 * */
public class UserDao {
	/**
	 * 添加用户
	 * */
	public void addUser(User user) throws SQLException {
		QueryRunner runner= new QueryRunner();
		String sql= "insert into users  values (null,?,?,?,?,'user',0,?,null)";
		runner.update(DataSourceUtils.getConnection(), sql, user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getActivecode());
	}
	
	/**
	 * 根据激活码查找用户
	 * */
	public User findUserByActiveCode(String activecode) throws SQLException{
		QueryRunner runner= new QueryRunner(DataSourceUtils.getDataSource());
		String sql= "select * from users where activecode=?";
		return runner.query(sql, new BeanHandler<>(User.class), activecode);
	}
	
	/**
	 * 根据用户名和密码查找用户
	 * */
	public User findUserByUserNameAndPassword(String username,String password) throws SQLException{
		QueryRunner runner= new QueryRunner();
		String sql= "select * from users where username=? and password=?";
		return runner.query(DataSourceUtils.getConnection(), sql, new BeanHandler<>(User.class), username,password);
	}
	
	/**
	 * 根据激活码激活用户
	 * */
	public void activeUserByActivecode(String activecode) throws SQLException{
		QueryRunner runner= new QueryRunner();
		String sql= "update users set state=1 where activecode=?";
		runner.update(DataSourceUtils.getConnection(), sql, activecode);
	}
	
	
}
