package com.westward.estore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.westward.estore.service.ProductService;

@SuppressWarnings("serial")
public class ProductServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method= req.getParameter("method");
		if ("findById".equals(method)) {
			findById(req,resp);
		}else if ("findAll".equals(method)) {
			findAll(req,resp);
		}
	}

	private void findById(HttpServletRequest req, HttpServletResponse resp) {
		String id= req.getParameter("id");
		ProductService productService= 
	}

	private void findAll(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	

}
