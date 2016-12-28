package com.westward.estore.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.westward.estore.domain.Product;
import com.westward.estore.factory.ProductServiceFactory;

/**
 * 购物车servlet
 * */
public class CartServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method= req.getParameter("method");
		if ("add".equals(method)) {
			add(req,resp);
		}else if ("".equals(method)) {
			
		}else {

		}
	}
	
	/**
	 * 添加商品到购物车
	 * */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			String id= req.getParameter("id");
			Product product= ProductServiceFactory.getInstance().findById(id);
			HttpSession session= req.getSession();
			Map<Product, Integer> cart= (Map<Product, Integer>) session.getAttribute("cart");
			if (cart==null) {
				cart= new HashMap<>();
			}
			Integer count = cart.put(product, 1);//关键语句
			if (count!=null) {
				cart.put(product, 1+count);//关键语句
			}
			session.setAttribute("cart", cart);//关键语句
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(req.getContextPath()+ "/index.jsp");
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
