<%@ page language="java" import="java.util.*"	 pageEncoding="utf-8"		%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>商品详细信息页面</title>
	<link  rel="stylesheet" 
		   href="${pageContext.request.contextPath }/css/style.css" 
		   type="text/css" media="screen" />
		   
	<script type="text/javascript">
		function addProductToCart(id){
			location.href= "${pageContext.request.contextPath}/cart?method=add&id="+ id;
		}
	</script>
	
</head>
<body>
	<br>
	<br>
	<table>
		<tr>
			<td rowspan="5">
				<img alt="商品" src="${pageContext.request.contextPath+ ${p.imgurl_s }">
			</td>
			<td>
				商品名称：${p.name }	
			</td>
		</tr>
		<tr>
			<td>
				商品价格：${p.price }	
			
			</td>
		</tr>
		<tr>
			<td>
				商品类别：${p.category }	
			
			</td>
		</tr>
		<tr>
			<td>
				商品数量：${p.pnum }	
			
			</td>
		</tr>
		<tr>
			<td>
				商品描述：${p.description }	
			
			</td>
		</tr>
		
		
		<tr>
			<td colspan="2">
				<img alt="购买" src="${pageContext.request.contextPath }/images/buy.bmp" 
				 	onclick="addProductToCart('${p.id}')"	>
			</td>
		</tr>
	</table>

</body>
</html>