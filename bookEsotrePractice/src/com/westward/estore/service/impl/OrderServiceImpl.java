package com.westward.estore.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.westward.estore.dao.OrderDao;
import com.westward.estore.dao.OrderItemDao;
import com.westward.estore.dao.ProductDao;
import com.westward.estore.domain.Order;
import com.westward.estore.domain.OrderItem;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.service.OrderService;
import com.westward.estore.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService{

	//添加订单
	@Override
	public void add(User user, Order order) throws PrivilegeException {
		OrderDao orderDao= new OrderDao();
		OrderItemDao orderItemDao= new OrderItemDao();
		ProductDao productDao= new ProductDao();
		
		try {
			DataSourceUtils.startTransaction();
			orderDao.createOrder(order);
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
	@Override
	public List<Order> find(User user) throws PrivilegeException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	//删除订单
	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	//更新订单状态
	@Override
	public void updateState(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
