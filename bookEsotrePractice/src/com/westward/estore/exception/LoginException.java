package com.westward.estore.exception;

/**
 * 登录错误专用异常
 * */
@SuppressWarnings("serial")
public class LoginException extends Exception {
	public LoginException() {
		super();
	}
	
	public LoginException(String msg){
		super(msg);
	}
	
	public LoginException(String msg,Throwable throwable){
		super(msg,throwable);
	}
	
	public LoginException(Throwable throwable){
		super(throwable);
	}
	
	
}
