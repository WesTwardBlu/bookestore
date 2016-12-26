package com.westward.estore.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	private Md5Utils() {
	}
	
	public static String md5(String plainText){
		byte[] secrets= null;
		String md5Text;
		try {
			secrets= MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5算法");
		}
		md5Text= new BigInteger(1, secrets).toString(16);
		for (int i = 0; i < 32- md5Text.length(); i++) {
			md5Text= "0"+ md5Text;
		}
		
		return md5Text;
	}
	
	
}
