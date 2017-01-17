package com.westward.estore.utils;

import java.io.File;
import java.util.UUID;

/**
 * 上传工具类<br>
 * 得到文件的真实名称<br>
 * c:/a/a.txt a.txt
 * c:\a\a.txt a.txt<br>
 * 根据操作系统自动区分路径分隔符 File.separator
 * */
public class UploadUtils {
	private UploadUtils() {
	}
	
	/**
	 * 截取真实文件名
	 * */
	public static String subFileName(String fileName){
		return  fileName.substring(fileName.lastIndexOf(File.separator)+1);//路径分隔符：windows操作系统中为反斜线；linux系统为正斜线，所以需要根据操作系统自适应
	}
	
	/**
	 * 生成随机UUID文件名
	 * */
	public static String generateRandonFileName(String fileName){
		//获得扩展名
		String ext= fileName.substring(fileName.lastIndexOf("."));//a.txt -> .txt
		return UUID.randomUUID().toString() +ext;
	}
	
	/**
	 * 获得hashcode,生成二级目录
	 * */
	public static String generateRandomDir(String uuidFileName){
		int hashCode= uuidFileName.hashCode();
		//一级目录
		int d1= hashCode & 0xf;
		
		//二级目录
		int d2= (hashCode >> 4) & 0xf;
		return File.separator+ d1+ File.separator+ d2;
	}
	
	
	
}
