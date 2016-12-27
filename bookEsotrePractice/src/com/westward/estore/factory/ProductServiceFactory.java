package com.westward.estore.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.westward.estore.annotation.PrivilegeInfo;
import com.westward.estore.dao.PrivilegeDao;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.service.ProductService;
import com.westward.estore.service.impl.ProductServiceImpl;

/**
 * 使用jdk自带的动态代理api
 * */
public class ProductServiceFactory {
	private static ProductService productService= new ProductServiceImpl();//动态代理  目标对象，即被代理对象
	
	public static ProductService getInstance() {
		return (ProductService) Proxy.newProxyInstance(productService.getClass().getClassLoader(), productService.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.isAnnotationPresent(PrivilegeInfo.class)) {
					PrivilegeInfo privilegeInfo= method.getAnnotation(PrivilegeInfo.class);//得到注解对象
					String privilegeName= privilegeInfo.value();
					User user= (User) args[0];
					if (user==null) {
						throw new PrivilegeException();// 抛出权限不足异常
					}
					
					PrivilegeDao privilegeDao= new PrivilegeDao();
					if ( !privilegeDao.checkPrivilege(user.getRole(), privilegeName)) {
						throw new PrivilegeException();// 抛出权限不足异常
					}
					
				}
				return method.invoke(productService, args);
			}
		});
	}
	
}
