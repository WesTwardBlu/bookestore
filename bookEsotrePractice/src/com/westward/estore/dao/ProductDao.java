package com.westward.estore.dao;

import java.sql.SQLException;
import java.util.List;

import javax.activation.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.westward.estore.domain.Order;
import com.westward.estore.domain.OrderItem;
import com.westward.estore.domain.Product;
import com.westward.estore.utils.DataSourceUtils;

/**
 * data access object
 * @version 1.0
 * */
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
	 * 下订单，减少商品数量
	 * @throws SQLException 
	 * */
	public void subProductCount(Order order) throws SQLException{
		String sql="update products set pnum=pnum-? where id=?";
		List<OrderItem> orderItems= order.getOrderItems();
		Object[][] params= new Object[orderItems.size()][2];
		OrderItem orderItem;
		for (int i = 0; i < orderItems.size(); i++) {
			orderItem= orderItems.get(i);
			params[i][0]= orderItem.getBuynum();
			params[i][1]= orderItem.getProduct_id();
		}
		QueryRunner queryRunner= new QueryRunner();
		queryRunner.batch(DataSourceUtils.getConnection(), sql, params);
	}
	
	/**
	 * 取消订单，增加商品数量
	 * @throws SQLException 
	 * */
	public void addProductCount(List<OrderItem> orderItems) throws SQLException{
		String sql= "update products set pnum=pnum+? where id=?";
		Object[][] params= new Object[orderItems.size()][2];
		OrderItem orderItem;
		for (int i = 0; i < orderItems.size(); i++) {
			orderItem= orderItems.get(i);
			params[i][0]= orderItem.getBuynum();
			params[i][1]= orderItem.getProduct_id();
		}
		QueryRunner queryRunner= new QueryRunner();
		queryRunner.batch(DataSourceUtils.getConnection(), sql, params);
	}
	
	/**
	 * 查找商品榜单
	 * @throws SQLException 
	 * */
	public List<Product> findSell() throws SQLException{
		String sql= "select products.name,sum(orderitem.buynum) totalSaleNum from orders,orderitem,products where orders.id=orderitem.order_id and orderitem.product_id=products.id and orders.paystate=1 group by products.id order by totalSaleNum desc   ";
		QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<>(Product.class));
	}
	
	
}
