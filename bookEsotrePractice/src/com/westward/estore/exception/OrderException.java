package com.westward.estore.exception;

/**
 * 订单异常
 * */
public class OrderException extends Exception {
	public OrderException() {
		super();
	}
	
	public OrderException(String message,Throwable throwable){
		super(message, throwable);
	}
	
	public OrderException(String message){
		super(message);
	}
	
	public OrderException(Throwable throwable){
		super(throwable);
	}
	
	
}
