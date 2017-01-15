package com.westward.estore.exception;

@SuppressWarnings("serial")
public class RegistException extends Exception{
	public RegistException() {
		super();
	}
	
	public RegistException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
	public  RegistException(String msg){
		super(msg);
	}
	
	public RegistException(Throwable throwable){
		super(throwable);
	}
}
