package com.westward.estore.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.westward.estore.utils.UploadUtils;

/**
 * 添加商品servlet
 * */
@SuppressWarnings("serial")
public class AddProductServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.封装数据
		Map<String, String[]> map= new HashMap<>();
		//2.apache-commons-fileupload 设置缓存区
		DiskFileItemFactory factory= new DiskFileItemFactory();
		factory.setSizeThreshold(5*1024*1024);//设置缓存区大小 5M 单位：byte
		//this.getServletContext().getRealPath("/temp")   得到服务器中此应用程序下/temp的绝对路径
		factory.setRepository(new File(this.getServletContext().getRealPath("/temp")));//设置临时文件存储位置
		
		//3.创建ServletFileUpload
		ServletFileUpload upload= new ServletFileUpload(factory);
		//判断是否是上传操作
		if (ServletFileUpload.isMultipartContent(req)) {
			upload.setHeaderEncoding("utf-8");//解决上传文件中文乱码问题
			
			//4.得到所有的FileItem
			try {
				List<FileItem> items= upload.parseRequest(req);
				//5.遍历items
				for (FileItem fileItem : items) {
					// 判断是否是上传项
					if (fileItem.isFormField()) {//非上传项组件
						String fieldName= fileItem.getFieldName();
						String value= fileItem.getString("utf-8");//取得utf-8编码的value
						// 封装非上传项组件信息到map
						map.put(fieldName, new String[]{value});
					}else { // 是上传组件
						String fileName= fileItem.getName();// 得到上传文件名称 注意：可以包含路径.
						//c:/a/a.txt  a.txt
						//得到真实名称
						fileName= UploadUtils.subFileName(fileName);//a.txt
						//得到随机名称
						String uuidFileName= UploadUtils.generateRandonFileName(fileName);
						//创建随机目录
						String randomDir= UploadUtils.generateRandomDir(uuidFileName);// /1/12
						String path= this.getServletContext().getRealPath("/upload"+ randomDir);// /upload/1/12
						File pathFile= new File(path);
						if (!pathFile.exists()) {// 目录不存在，创建
							pathFile.mkdirs();
						}
						//得到一个imgurl
						String imgurl= "/upload"+ randomDir+ File.separator +uuidFileName;
						map.put("imgurl", new String[]{imgurl});
						try {
							// 文件上传操作
							fileItem.write(new File(pathFile, uuidFileName));
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			//封装商品id
			
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
