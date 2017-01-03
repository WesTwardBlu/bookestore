<%@ page language="java"   import="java.util.*"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>生成订单</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="screen" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<br>
	<div align="center">
		<font size="5">订单信息</font>
	</div>
	<br>
	<form action="${pageContext.request.contextPath }/order" method="post">
		<input type="hidden"  name="method" value="add">
		<table align="center" border="1" width="400px">
			<tr>
				<td>收货地址</td>
				<td>
					<input type="text" name="receiverinfo" >
					<%--利用apache commons beanutils来封装javabean，所以此处name的名字必须和javabean属性的名字保持一致 --%>
				</td>
			</tr>
			<tr>
				<td>
					<table align="center" border="1">
						<tr>
							<td>商品名称</td>
							<td>单价</td>
							<td>数量</td>
						</tr>
						<c:set var="sumPrice" value="0"></c:set>
						<c:forEach items="${cart }" var="entry">
							<tr>
								<td>${entry.key.name }</td>
								<td>${entry.key.price }</td>
								<td>${entry.value }</td>
							</tr>
							<c:set var="sumPrice" value="${sumPrice+ entry.key.price*entry.value }"></c:set>
						</c:forEach>
					</table>
				</td>
			</tr>
			
			<tr>
				<td>订单总价：￥${sumPrice }元
					<input name="money" type="hidden" value="${sumPrice }">
					<%--利用apache commons beanutils来封装javabean，所以此处name的名字必须和javabean属性的名字保持一致 --%>
				</td>
				<td align="right">
					<input type="submit" value="生成订单" >
				</td>
			</tr>
		</table>
	</form>
</body>
</html>