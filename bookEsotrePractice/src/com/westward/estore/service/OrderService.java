package com.westward.estore.service;

import java.util.List;

import com.westward.estore.annotation.PrivilegeInfo;
import com.westward.estore.domain.Order;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;

public interface OrderService {
	//添加订单
	@PrivilegeInfo("生成订单")
	void add(User user,Order order) throws PrivilegeException,Exception;
	
	//查看订单
	@PrivilegeInfo("查看订单")
	List<Order> find(User user) throws PrivilegeException,Exception;
	
	//根据订单id删除订单
	void delete(String id) throws Exception;
	
	//更新订单状态
	void updateState(String id) throws Exception;
}
