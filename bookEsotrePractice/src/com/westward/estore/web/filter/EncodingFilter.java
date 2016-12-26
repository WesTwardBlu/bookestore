package com.westward.estore.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 编码过滤器，通用解决get ，post的中文乱码问题
 * */
public class EncodingFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request2= (HttpServletRequest) request;
		HttpServletRequest myRequest= new MyRequest(request2);
		
		response.setContentType("text/html;charset=utf-8");
		
		chain.doFilter(myRequest, response);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
}

class MyRequest extends HttpServletRequestWrapper{
	HttpServletRequest request;
	boolean hasEncode;
	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request= request;
	}
	/**
	 *解决post乱码和get乱码的方法不同 
	 * */
	@Override
	public Map<String, String[]> getParameterMap() {
		String methodName= request.getMethod();
		
		if ("post".equalsIgnoreCase(methodName)) {
			try {
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if ("get".equalsIgnoreCase(methodName)) {
			Map<String, String[]> map= request.getParameterMap();
			if (!hasEncode) {
				for ( String key : map.keySet()) {
					String[] values= map.get(key);
					for (int i = 0; i < values.length; i++) {
						try {
							values[i]= new String(values[i].getBytes("iso-8859-1"), "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						
					}
				}
				hasEncode= true;
			}
			return map;
		}
		return super.getParameterMap();
	}
	
	@Override
	public String[] getParameterValues(String name) {
		return getParameterMap().get(name);
	}
	
	@Override
	public String getParameter(String name) {
		return getParameterMap().get(name)== null ? null : getParameterMap().get(name)[0] ;
	}
}
