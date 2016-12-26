package com.westward.estore.service.impl;

import java.util.List;

import com.westward.estore.domain.Product;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.service.ProductService;

public class ProductServiceImpl implements ProductService {
	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void add(User user, Product product) throws PrivilegeException, Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Product> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findSell() throws PrivilegeException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
