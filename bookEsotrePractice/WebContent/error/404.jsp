<%@ page language="java"  import="java.util.*" pageEncoding="utf-8"%>
<%
	String path= request.getContextPath();//资源在服务器程序中的上下文路径
	String basePath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort()+ path+ "/";//拼接资源的url
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath %>" >
<title>404.jsp</title>
</head>
<body>
您访问的资源不存在...
</body>
</html>