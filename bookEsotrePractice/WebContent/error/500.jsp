<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%
	String path= request.getContextPath();
	String basePath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort()+ path+ "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath %>">
<title>500.jsp</title>
</head>
<body>
	<div align="center">
		<h1>服务器内部错误，管理员不在，请稍后联系</h1>
	</div>
</body>
</html>