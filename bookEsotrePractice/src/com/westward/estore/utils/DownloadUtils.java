package com.westward.estore.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

/**
 * 下载 工具类<br>
 * 对文件名进行utf-8编码
 * */
public class DownloadUtils {
	private DownloadUtils() {
	}
	
	/**
	 * 对filename进行utf-8编码
	 * */
	public static String getDownloadFileName(String filename,String agent) throws UnsupportedEncodingException{
		if (agent.contains("MSIE")) {
			filename= URLEncoder.encode(filename, "utf-8");
			filename= filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder= new BASE64Encoder();
			filename="=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8"))+ "?=";
		} else if (agent.contains("Chrome")) {
			filename= URLEncoder.encode(filename, "utf-8");
		} else {
			filename= URLEncoder.encode(filename, "utf-8");
		}
		
		
		
		return filename;
	}
}
