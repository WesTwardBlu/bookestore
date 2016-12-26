package com.westward.estore.exception;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("serial")
public class PrivilegeException extends InvocationTargetException {
	public PrivilegeException() {
		super();
	}
	
	public PrivilegeException(Throwable target,String string){
		super(target, string);
	}
	
	public PrivilegeException(Throwable target){
		super(target);
	}
}
