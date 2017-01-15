package com.westward.estore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.westward.estore.domain.User;
import com.westward.estore.service.UserService;
import com.westward.estore.service.impl.UserServiceImpl;

public class UserServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method= req.getParameter("method");
		if ("regist".equals(method)) {
			regist(req, resp);
		}else if ("activeuser".equals(method)) {
			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	private void regist(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//校验验证码
		String checkcode= request.getParameter("checkcode");
		String _checkcode= (String) request.getSession().getAttribute("checkcode_session");
		request.getSession().removeAttribute("checkcode_session");//去掉缓存
		if (_checkcode==null || !_checkcode.equals(checkcode)) {
			request.setAttribute("regist.errormessage", "验证码不正确");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		//封装用户
		Map<String, String[]> properties= request.getParameterMap();
		User user= new User();
		try {
			//powered by apache-commons-beanutils
			BeanUtils.populate(user, properties);//将request中的参数映射到user对象
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 进行服务器端数据校验
		Map<String, String> registerrorMap= user.validateRegist();
		if (registerrorMap.size()>0) {//说明有错误信息
			request.setAttribute("registerrorMap", registerrorMap);
			//对请求进行转发
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		//手动封装一个激活码
		user.setActivecode(UUID.randomUUID().toString());
		//调用service操作
		UserService userService= new UserServiceImpl();
		try {
			userService.regist(user);
			response.getWriter().write("注册成功，激活后请<a href='"+ request.getContextPath()+ "/index.jsp'>登录</a> ");
		} catch (Exception e) {
			// TODO 待补全
			request.setAttribute("regist.errormessage", e.getMessage());
			e.printStackTrace();
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
	}
	
	
}
