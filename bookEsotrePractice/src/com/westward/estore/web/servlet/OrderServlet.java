package com.westward.estore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.westward.estore.domain.Order;
import com.westward.estore.domain.OrderItem;
import com.westward.estore.domain.Product;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.factory.OrderServiceFactory;

public class OrderServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method= req.getParameter("method");
		if ("add".equals(method)) {
			add(req,resp);
		}
	}
	
	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//封装order
		Order order= new Order();
		try {
			BeanUtils.populate(order, req.getParameterMap());//从requset从取出receiveinfo和money,封装到javabean里。由于method Order类中并无此项，所以并不会封装到javabean里
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		order.setId(UUID.randomUUID().toString());
		//将订单与用户id关联
		User user= (User) req.getSession().getAttribute("user");
		if (user==null) {
			resp.sendRedirect(req.getContextPath()+ "/error/error.jsp");
		}
		order.setUser_id(user.getId());
		//将订单项装配到订单
		Map<Product, Integer> cart= (Map<Product, Integer>) req.getSession().getAttribute("cart");//取得购物车
		List<OrderItem> orderItems= new ArrayList<>();//订单项
		for (Product product : cart.keySet()) {
			OrderItem orderItem= new OrderItem();
			orderItem.setOrder_id(order.getId());
			orderItem.setProduct_id(product.getId());
			orderItem.setBuynum(cart.get(product));
			
			orderItems.add(orderItem);
		}
		
		order.setOrderItems(orderItems);
		
		//调用OrderService中方法，创建订单
		try {
			OrderServiceFactory.newInstance().add(user, order);//使用动态代理类service添加订单对象到数据库
			resp.getWriter().write("下单成功，<a href='"+ req.getContextPath()+ "/index.jsp'>继续购物</a>,<a href='"+ req.getContextPath()+ "/order?method=search'>查看订单</a>");
		} catch (PrivilegeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
