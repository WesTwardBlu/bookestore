package com.westward.estore.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.westward.estore.domain.User;
import com.westward.estore.service.UserService;
import com.westward.estore.service.impl.UserServiceImpl;
import com.westward.estore.utils.CookieUtils;

/**
 * 自动登录过滤器
 * */
public class AutoLoginFilter implements Filter{
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request2= (HttpServletRequest) request;
		HttpServletResponse response2= (HttpServletResponse) response;
		
		User user= (User) request2.getSession().getAttribute("user");
		if (user== null) {
			String path= request2.getServletPath();
			if ( !("/regist.jsp".equalsIgnoreCase(path) || "/regist".equalsIgnoreCase(path) || "/login".equalsIgnoreCase(path)  ) ) {
				Cookie cookie= CookieUtils.findCookieByName(request2.getCookies(), "autologin");
				if (cookie!=null) {
					String username= URLDecoder.decode(cookie.getValue().split("%%wtd")[0], "utf-8");
					String password= cookie.getValue().split("%wtd%")[1];
					UserService userService= new UserServiceImpl();
					try {
						User existUser= userService.login(username, password);
						if (existUser!=null) {
							request2.getSession().setAttribute("user", existUser);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		
		chain.doFilter(request2, response2);
	}
}
