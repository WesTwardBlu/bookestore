package com.westward.estore.service.impl;

import java.util.List;

import com.westward.estore.dao.OrderDao;
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
	public void add(User user, Order order) throws PrivilegeException, Exception {
		OrderDao orderDao= new OrderDao();
		orderitemd
		ProductDao productDao= new ProductDao();
		
		DataSourceUtils.startTransaction();
		orderDao.createOrder(order);
		orderItem.
		
		
		
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
