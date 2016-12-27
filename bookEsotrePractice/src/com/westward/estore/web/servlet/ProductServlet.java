package com.westward.estore.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.westward.estore.domain.Product;
import com.westward.estore.factory.ProductServiceFactory;
import com.westward.estore.service.ProductService;

@SuppressWarnings("serial")
public class ProductServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method= req.getParameter("method");
		if ("findById".equals(method)) {
			findById(req,resp);
		}else if ("findAll".equals(method) || method==null) {
			findAll(req,resp);
		}
	}

	

	private void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id= req.getParameter("id");
		try {
			ProductService productService= ProductServiceFactory.getInstance();
			Product product= productService.findById(id);
			req.setAttribute("p", product);
			
			req.getRequestDispatcher("/productinfo.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ProductService productService= ProductServiceFactory.getInstance();//得到productservice的动态代理对象，可以对相关方法进行权限判断
			List<Product> products= productService.findAll();
			
			req.setAttribute("ps", products);
			
			req.getRequestDispatcher("/page.jsp").forward(req, resp);//转发
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	

}
