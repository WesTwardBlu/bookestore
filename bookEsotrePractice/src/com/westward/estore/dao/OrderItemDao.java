package com.westward.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.westward.estore.domain.Order;
import com.westward.estore.domain.OrderItem;
import com.westward.estore.utils.DataSourceUtils;

public class OrderItemDao {
	
	/**
	 * 添加订单项<br>
	 * 采用批量操作，提高效率
	 * @throws SQLException 
	 * */
	public void addOrderItem(Order order) throws SQLException{
		List<OrderItem> orderItems= order.getOrderItems();
		Object[][] params= new Object[orderItems.size()][3];
		OrderItem orderItem= null;
		for (int i = 0; i < orderItems.size(); i++) {
			orderItem= orderItems.get(i);
			params[i][0]= orderItem.getOrder_id();
			params[i][1]= orderItem.getProduct_id();
			params[i][2]= orderItem.getBuynum();
		}
		String sql= "insert into orderitem values(?,?,?)";
		QueryRunner queryRunner= new QueryRunner();
		queryRunner.batch(DataSourceUtils.getConnection(), sql, params);
	}
	
	/**
	 * 根据订单来查询订单项
	 * @throws SQLException 
	 * */
	public List<OrderItem> findOrderItemByOrder(Order order) throws SQLException{
		String sql= "select * from orderitem,products where orderitem.product_id=products.id and orderitem.order_id=?";
		QueryRunner queryRunner= new QueryRunner();
		return queryRunner.query(DataSourceUtils.getConnection(), sql, new BeanListHandler<>(OrderItem.class),order.getId());
	}
	
	
	/**
	 * 根据订单id来查询订单项
	 * @throws SQLException 
	 * */
	public List<OrderItem> findOrderItemByOrderId(String id) throws SQLException{
		String sql= "select * from orderitem where order_id=?  ";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<>(OrderItem.class), id);
	}
	
	/**
	 * 根据订单id删除订单项
	 * @throws SQLException 
	 * */
	public void delOrderItem(String id) throws SQLException{
		String sql= "delete from orderitem where orderitem.order_id=?";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		queryRunner.update(sql, id);
	}
}
