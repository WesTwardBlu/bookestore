package com.westward.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.westward.estore.domain.Product;
import com.westward.estore.utils.DataSourceUtils;

public class ProductDao {
	public ProductDao() {
	}
	
	/**
	 * 添加商品
	 * */
	public void addProduct(Product product) throws SQLException{
		String sql= "insert into products values (?,?,?,?,?,?,?)";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		queryRunner.update(sql, product.getId(),product.getName(),product.getPrice(),product.getCategory(),product.getPnum(),product.getImgurl(),product.getDescription());
		
	}
	
	/**
	 * 查找所有商品
	 * */
	public List<Product> findAll() throws SQLException{
		String sql= "select * from products";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<>(Product.class));
		
	}
	
	/**
	 * 根据id查找商品
	 * */
	public Product findById(String id) throws SQLException{
		String sql= "select * from products where id=?";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<>(Product.class), id);
	}
	
	/**
	 * 修改商品数量
	 * */
	public void updateProductCount(){
		//TODO updateProductCount
	}
	
	public List<Product> findSell(){
		//TODO findSell
		return null;
	}
	
	
}
