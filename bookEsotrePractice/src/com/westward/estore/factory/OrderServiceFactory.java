package com.westward.estore.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.westward.estore.annotation.PrivilegeInfo;
import com.westward.estore.dao.PrivilegeDao;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.service.OrderService;
import com.westward.estore.service.impl.OrderServiceImpl;

/**
 * OrderService的动态代理工厂类
 * */
public class OrderServiceFactory {
	private static OrderService orderService= new OrderServiceImpl();
	
	public static OrderService newInstance(){
		return  (OrderService) Proxy.newProxyInstance(orderService.getClass().getClassLoader(), orderService.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.isAnnotationPresent(PrivilegeInfo.class)) {
					PrivilegeInfo privilegeInfo= method.getAnnotation(PrivilegeInfo.class);
					String privilegeName= privilegeInfo.value();//权限名称
					User user= (User) args[0];//用户
					if (user==null) {
						throw new PrivilegeException();//抛出权限不足异常
					}
					PrivilegeDao privilegeDao= new PrivilegeDao();
					if(!privilegeDao.checkPrivilege(user.getRole(), privilegeName)){
						throw new PrivilegeException();//抛出权限不足异常
					}
				}
				return method.invoke(orderService, args);
			}
		});
	}
}
