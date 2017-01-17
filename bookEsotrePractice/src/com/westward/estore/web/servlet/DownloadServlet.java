package com.westward.estore.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.westward.estore.domain.Product;
import com.westward.estore.domain.User;
import com.westward.estore.exception.PrivilegeException;
import com.westward.estore.factory.ProductServiceFactory;
import com.westward.estore.utils.DownloadUtils;

/**
 * 下载 servlet
 * */
@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.得到下载文件名称
		String filename=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "销售榜单.csv"; 
		//2.设置下载文件的mimeType
		resp.setContentType(this.getServletContext().getMimeType(filename));
		//3.设置Content-Dispostion
		String agent= req.getHeader("User-agent");
		resp.setHeader("Content-disposition", "attachement;filename="+ DownloadUtils.getDownloadFileName(filename, agent));
		
		//4.得到销售榜单
		List<Product> products= null;
		try {
			User user= (User) req.getSession().getAttribute("user");
			products= ProductServiceFactory.getInstance().findSell(user);
			
		}catch (PrivilegeException e) {
			resp.sendRedirect(req.getContextPath()+"/error/privilegeerror.jsp");//跳转到权限不足文件
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		//5.PrintWriter写入文件
		resp.setCharacterEncoding("gbk");
		resp.getWriter().println("商品名称，销售数量");//写到csv文件里
		for (Product product : products) {
			resp.getWriter().println(product.getName()+ ","+ product.getTotalSaleNum());
			resp.getWriter().flush();
		}
		resp.getWriter().close();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
