package com.westward.estore.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.westward.estore.domain.User;
import com.westward.estore.exception.LoginException;
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
			activeUser(req, resp);
		}else if ("login".equals(method)) {
			
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
	
	/**
	 * 激活新注册的用户
	 * @throws IOException 
	 * */
	private void activeUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String activeCode= request.getParameter("activecode");
		UserService userService= new UserServiceImpl();
		try {
			userService.activeUser(activeCode);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage()+ "，激活失败。<a href='"+ request.getContextPath()+ "/regist.jsp'>重新注册</a>");
			return;
		} 
		response.getWriter().write("用户激活成功。<a href='"+ request.getContextPath()+ "/index.jsp'>登录</a>");
	}
	
	/**
	 * servlet层，用户登录方法
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws LoginException 
	 * */
	private void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		User user= null;
		try {
			user = new UserServiceImpl().login(username,password);
			if (user!=null) {
				// 用户如果登录成功，判断是否勾选了记住用户名.
				String saveUsername= request.getParameter("remember");
				if ("on".equals(saveUsername)) {
					//记住用户名
					Cookie cookie= new Cookie("saveusername", URLEncoder.encode(username, "utf-8"));// 存储utf-8码
					cookie.setMaxAge(7*24*60*60);//单位：秒
					cookie.setPath("/");
					response.addCookie(cookie);
				}else {
					Cookie cookie= new Cookie("saveusername", URLEncoder.encode(username, "utf-8"));
					cookie.setMaxAge(0);//相当于清楚cookie
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				//自动登录功能
				String autologin= request.getParameter("autologin");
				if ("on".equals(autologin)) {
					Cookie cookie= new Cookie("autologin", URLEncoder.encode(username, "utf-8")+ "%split%"+ password);
					cookie.setMaxAge(7*24*60*60);
					cookie.setPath("/");
					response.addCookie(cookie);
				}else {
					Cookie cookie= new Cookie("autologin",URLEncoder.encode(username, "utf-8")+ "%split%"+ password );
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				
				// 登录成功后，将用户存储到session中.
				request.getSession().invalidate();//销毁session[清空session中数据]
				request.getSession().setAttribute("user", user);
				//跳转到主页
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}else {
				request.setAttribute("login.message", "用户名或密码错误");//只在一个request中有效
				request.getRequestDispatcher("/page.jsp").forward(request, response);//此处必须用转发，不能用跳转，不然取不到request的attribute的值
			}
		} catch (LoginException e) {
			e.printStackTrace();
			request.setAttribute("login.message", e.getMessage());
			request.getRequestDispatcher("/page.jsp").forward(request, response);
		}
		
	}
}
