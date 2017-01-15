package com.westward.estore.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.westward.estore.dao.OrderDao;
import com.westward.estore.dao.OrderItemDao;
import com.westward.estore.dao.ProductDao;
import com.westward.estore.domain.Order;
import com.westward.estore.domain.OrderItem;
import com.westward.estore.domain.User;
import com.westward.estore.exception.OrderException;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.service.OrderService;
import com.westward.estore.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService{

	//生成订单，
	//需要权限
	@Override
	public void add(User user, Order order) throws PrivilegeException {
		OrderDao orderDao= new OrderDao();
		OrderItemDao orderItemDao= new OrderItemDao();
		ProductDao productDao= new ProductDao();
		
		try {
			DataSourceUtils.startTransaction();
			orderDao.createOrder(order);//添加订单和添加订单项的顺序不能变，因为订单项的外键指向订单的键
			orderItemDao.addOrderItem(order);
			productDao.subProductCount(order);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollBack();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				DataSourceUtils.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//查找订单
	//需要权限
	@Override
	public List<Order> find(User user) throws PrivilegeException, Exception {
		OrderDao orderDao= new OrderDao();
		OrderItemDao orderItemDao= new OrderItemDao();
		List<Order> orders = orderDao.findOrder(user);//查询订单信息，不包含订单中的商品信息，商品信息需要查询订单项
		List<OrderItem> orderItems= null;
		for (Order order : orders) {
			orderItems = orderItemDao.findOrderItemByOrder(order);
			order.setOrderItems(orderItems);
		}
		return orders;
	}

	/**
	 * 删除订单和订单项在库里的数据，
	 * 更改商品数量
	 * */
	@Override
	public void delete(String id) throws OrderException  {
		OrderDao orderDao= new OrderDao();
		OrderItemDao orderItemDao= new OrderItemDao();
		ProductDao productDao= new ProductDao();
		try {
			DataSourceUtils.startTransaction();
			List<OrderItem> orderItems= orderItemDao.findOrderItemByOrderId(id);
			//增加商品数量
			productDao.addProductCount(orderItems);
			//删除订单
			orderItemDao.delOrderItem(id);
			orderDao.delOrder(id);
			
		} catch (Exception e) {
			try {
				DataSourceUtils.rollBack();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new OrderException("删除订单失败！",e);
		} finally {
			try {
				DataSourceUtils.commit();//事务提交，释放连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//更新订单状态
	@Override
	public void updateState(String id) throws SQLException {
		OrderDao orderDao= new OrderDao();
		orderDao.updateState(id);
	}
	
}
