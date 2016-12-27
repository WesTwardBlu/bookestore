package com.westward.estore.service;

import java.util.List;

import com.westward.estore.annotation.PrivilegeInfo;
import com.westward.estore.domain.Product;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;

public interface ProductService {
	@PrivilegeInfo("添加商品")
	void add(User user,Product product) throws PrivilegeException,Exception;
	
	List<Product> findAll() throws Exception;
	
	Product findById(String id) throws Exception;
	
	@PrivilegeInfo("下载榜单")
	List<Product> findSell(User user) throws PrivilegeException,Exception;
}
