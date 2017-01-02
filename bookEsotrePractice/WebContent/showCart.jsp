<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>购物车</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="screen"/>
	<script type="text/javascript">
		function numbText(e){
			//考虑浏览器兼容问题
			if(e&& e.stopPropagation){ //e存在
				code= e.which;
			}else{
				code= window.event.keyCode;
			}
			//48--57代表的是数字0-9    8与46代表的是backspace 与 delete
			if(!((code >=48 && code<= 57) || code==8 || code==46)){
				//阻止事务的默认执行
				if(e&& e.stopPropagation){
					e.preventDefault();
				}else{
					window.event.returnValue= false;
				}
			}
			
		}
		
		//id代表要修改的商品    count代表商品的数量,max:商品最大数量，filed:当前元素
		function changeCount(id,count,max,field){
			if(parseInt(count)<0){
				alert('商品数量不能为负数');
				field.value= field.title;
				return;
			}
			if(parseInt(count)> parseInt(max)){
				count= max;
			}
			if(parseInt(count)==0){
				var flag= window.confirm('确认删除此商品吗');
				if(!flag){
					return;
				}
			}
			location.href= '${pageContext.request.contextPath }/cart?method=update&id='+id+'&count='+ count ;
		}
		
		function delConfirm(e){
			var flag= confirm('确认删除商品？');
			if(!flag){
				// e对象存在，preventDefault方法存在 ---- 火狐浏览器
				if(e&&e.preventDefault()){
					e.preventDefault();
				}
				// 不支持e对象，或者没有preventDefault方法 ---- IE
				else {
					window.event.returnValue= false;
				}
			}
		}
		
		//请求订单文件
		function gotoOrder(){
			location.href= '${pageContext.request.contextPath }/order.jsp';
		}
	</script>
</head>
<body>
	<br>
	<br>
	<div align="center">
		<c:if test="${empty cart }">
			购物车无商品
		</c:if>
		<c:if test="${not empty cart }">
			<c:set var="sumPrice" value="0"></c:set>
			<table align="center" border="1">
				<c:forEach items="${cart }" var="entry" >
					<tr>
						<td>商品名称：${entry.key.name }</td>
						<td>商品价格：${entry.key.price }</td>
						<td>
							<input type="button" value="-" onclick="changeCount('${entry.key.id}','${entry.value-1 }','${entry.key.pnum }',this)" >
						</td>
						<td>
							<input type="text" value="${entry.value }" title="${entry.value }" style="text-align: center;" 
								onkeydown="numbText(event)"  onchange="changeCount('${entry.key.id}',this.value,'${entry.key.pnum }',this" >
						</td>
						<td>
							<input type="button" value="+" onclick="changeCount('${entry.key.id}','${entry.value+1 }','${entry.key.pnum }',this)" >
						</td>
						<td>
							可购买数量：${entry.key.pnum }
						</td>
						<td>
							<a href="${pageContext.request.contextPath }/cart?method=remove&id=${entry.key.id}" onclick="delConfirm(event)">删除</a>
						</td>
					</tr>
					<c:set var="sumPrice" value="${sumPrice+ entry.key.price*entry.value }"></c:set>
				</c:forEach>
				<tr><td colspan="5" align="right">总价：￥${sumPrice }元</td></tr>
				<tr><td colspan="5" align="right">
					<img  alt="" src="${pageContext.request.contextPath }/images/gotoorder.bmp" onclick="gotoOrder()">
				</td></tr>
				
			</table>
				
			
		</c:if>
		
	</div>	
</body>
</html>