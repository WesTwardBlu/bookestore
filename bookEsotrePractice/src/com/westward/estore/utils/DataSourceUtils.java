package com.westward.estore.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据源datasource和数据库连接connection工具类
 * */
public class DataSourceUtils {
	private static DataSource dataSource= new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl= new ThreadLocal<>();
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getConnection() throws SQLException{
		Connection connection= tl.get();
		if (connection== null) {
			connection= getDataSource().getConnection();
			tl.set(connection);
		}
		return connection;
	}
	
	public static void startTransaction() throws SQLException{
		getConnection().setAutoCommit(false);
	}
	
	public static void rollBack() throws SQLException{
		getConnection().rollback();
	}
	
	public static void commit() throws SQLException{
		getConnection().commit();
		getConnection().close();
		tl.remove();
	}
}
