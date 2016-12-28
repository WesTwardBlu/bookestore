package com.westward.estore.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.westward.estore.dao.ProductDao;
import com.westward.estore.domain.Product;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.service.ProductService;

public class ProductServiceImpl implements ProductService {
	public ProductServiceImpl() {
	}
	
	@Override
	public void add(User user, Product product) throws PrivilegeException, SQLException {
		ProductDao productDao= new ProductDao();
		productDao.addProduct(product);
	}

	@Override
	public List<Product> findAll() throws SQLException {
		ProductDao productDao= new ProductDao();
		return	productDao.findAll();
	}

	@Override
	public Product findById(String id) throws SQLException {
		ProductDao productDao= new ProductDao();
		return	productDao.findById(id);
		
	}

	@Override
	public List<Product> findSell(User user) throws PrivilegeException, SQLException {
		ProductDao productDao= new ProductDao();
		return 	productDao.findSell();
	}

}
