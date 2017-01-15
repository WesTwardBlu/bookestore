package com.westward.estore.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码生成servlet
 * */
public class CheckImgServlet extends HttpServlet {
	//集合中保存所有的成语
	private List<String> words= new ArrayList<>();
	
	/**
	 * 在请求访问servlet的时候做初始化工作
	 * */
	@Override
	public void init() throws ServletException {
		//初始化阶段，读取new_words.txt
		//web工程中读取文件，必须使用绝对磁盘路径
		//D:\apache-tomcat-7.0.70-x64\wtpwebapps\bookEstore\WEB-INF\new_words.txt
		String path= getServletContext().getRealPath("/WEB-INF/new_words.txt");
		BufferedReader reader = null;
		try {
			reader= new BufferedReader(new InputStreamReader(new FileInputStream(path),"utf-8"));
			String line;
			while((line=reader.readLine())!=null){
				words.add(line);
			}
			reader.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//在doGet方法里，用java画笔技术来画图
		/**
		 * java画图技术
		 * */
		int width= 120;
		int height= 25;
		//步骤1：绘制一张内存中图片
		BufferedImage bufferedImage= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//步骤2：图片绘制背景颜色----通过绘图对象
		Graphics graphics= bufferedImage.getGraphics();
		//绘制任何图形之前，都必须指定一个颜色。
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		//步骤3：绘制白色边框
		graphics.setColor(Color.WHITE);
		graphics.drawRect(0, 0, width-1, height-1);
		//步骤4：四个随机数字
		Graphics2D graphics2d= (Graphics2D) graphics;
		//设置输出字体
		graphics2d.setFont(new Font("宋体", Font.BOLD, 16));
		
		Random random= new Random();//生成随机数
		int index= random.nextInt(words.size());
		String word= words.get(index);//随机过得成语
		
		//定义坐标
		int x= 10;
		for (int i = 0; i < word.length(); i++) {
			graphics2d.setColor(new Color(20+random.nextInt(110), 20+random.nextInt(110), 20+random.nextInt(110)));
			//2d画笔旋转-30--30度
			int jiaodu= random.nextInt(60)-30;
			//换算成弧度
			double theta= jiaodu*Math.PI/180;
			//获得字符
			char c= word.charAt(i);
			//将c输出到图片
			graphics2d.rotate(theta, x, 20);//旋转
			graphics2d.drawString(String.valueOf(c), x, 20);
			graphics2d.rotate(-theta, x, 20);//反旋转
			x+=30;//x坐标移动30
		}
		//将验证码内容保存session
		req.getSession().setAttribute("checkcode_session", word);
		System.out.println(word);
		
		//步骤5：绘制干扰线
		graphics.setColor(getRandColor(160, 200));
		int x1;
		int x2;
		int y1;
		int y2;
		for (int i = 0; i < 30; i++) {	//画出30条线
			x1= random.nextInt(width);
			x2= random.nextInt(12);
			y1= random.nextInt(height);
			y2= random.nextInt(12);
			graphics.drawLine(x1, y1, x2, y2);
		}
		// 步骤6：将上面图片输出到浏览器 ImageIO
		graphics.dispose();//释放资源
		ImageIO.write(bufferedImage, "jpg", resp.getOutputStream());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	/**
	 *取某一范围的color
	 *@param fc int 范围参数1
	 *@param bc int 范围参数2
	 *@return Color 
	 * */
	private Color getRandColor(int fc,int bc){
		//取其随机颜色
		Random random= new Random();
		//红(R)、绿(G)、蓝(B)  0~255
		if (fc>255) {
			fc=255;
		}
		if (fc<0) {
			fc=0;
		}
		if (bc>255) {
			bc= 255;
		}
		if (bc<0) {
			bc=0;
		}
		//获得三个随机int数，这三个数都将最为构造color对象的参数 生成一个随机int数[0,255]:fc+R(bc-fc)
		int r= fc+ random.nextInt(bc-fc);
		int g= fc+ random.nextInt(bc-fc);
		int b= fc+ random.nextInt(bc-fc);
		return new Color(r, g, b);
	}
	
	
	
}
