package com.westward.estore.service;

import java.util.List;

import com.westward.estore.annotation.PrivilegeInfo;
import com.westward.estore.domain.Product;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;

public interface ProductService {
	
	//添加商品
	//user的作用是为了校验权限
	@PrivilegeInfo("添加商品")
	void add(User user,Product product) throws PrivilegeException,Exception;
	
	//查找全部商品
	List<Product> findAll() throws Exception;
	
	//根据商品id查找商品
	Product findById(String id) throws Exception;
	
	//下载商品的销售榜单
	//user的作用是为了校验权限
	@PrivilegeInfo("下载榜单")
	List<Product> findSell(User user) throws PrivilegeException,Exception;
}
