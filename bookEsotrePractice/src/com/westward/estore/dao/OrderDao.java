package com.westward.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.westward.estore.domain.Order;
import com.westward.estore.domain.User;
import com.westward.estore.utils.DataSourceUtils;

public class OrderDao {
	
	//创建订单
	public void createOrder(Order order) throws SQLException{
		String sql= "insert into orders values (?,?,?,0,null,?)";
		QueryRunner queryRunner= new QueryRunner();
		queryRunner.update(DataSourceUtils.getConnection(), sql, order.getId(),order.getMoney(),order.getReceiverinfo(),order.getUser_id());
	}
	
	//查找user下的订单
	//权限不同，查找内容不同
	public List<Order> findOrder(User user) throws SQLException{
		String sql;
		List<Order> orders= null;
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		if ("admin".equals(user.getRole())) {
			sql= "select orders.*,users.username,users.nickname from orders,users where orders.user_id= users.id";
			orders= queryRunner.query(sql, new BeanListHandler<>(Order.class));
		} else if ("user".equals(user.getRole())) {
			sql= "select * from orders.*,users.username,users.nickname from orders,users where orders.user_id=users.id and orders.user_id=?";
			orders= queryRunner.query(sql, new BeanListHandler<>(Order.class), user.getId());
		}
		
		return orders;
	}
	
	//删除订单
	public void delOrder(String id) throws SQLException{
		String sql= "delete from orders where id=?";
		QueryRunner queryRunner= new QueryRunner();
		queryRunner.update(DataSourceUtils.getConnection(), sql, id);
		
	}
	
	//更新订单状态
	public void updateState(String id) throws SQLException{
		String sql= "update orders set paystate=1 where id=?";
		QueryRunner queryRunner= new QueryRunner();
		queryRunner.update(DataSourceUtils.getConnection(), sql, id);
	}
	
	
}
