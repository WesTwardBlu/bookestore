<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户注册</title>
	<link href="${pageContext.request.contextPath }/css/style.css"  rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript">
		//对表单数据进行必要的校验
		function checkForm(){
			var spans= document.getElementsByTagName('span');
			for(var i=0;i<spans.length;i++){
				spans[i].innerHTML= '';
			}
			var flag1= checkNull('username');
			var flag2= checkNull('password');
			var flag3= checkNull('repassword');
			var flag4= checkNull('nickname');
			var flag5= checkNull('email');
			var flag6= checkNull('checkcode');
			
			var flag7= repasswordValidate();
				
			return flag1&&flag2&&flag3&&flag4&&flag5&&flag6&&flag7;//将所有的校验统一返回
		}
		
		//空校验
		function checkNull(idName){
			var value= document.getElementById(idName).value;
			var reg= /^\s*$/;//代表0个或多个空白字符
			if(reg.test(value)){
				document.getElementById(idName+'_span').innerHTML=(idName+'不能为空！');
				return false;
			}
			return true;
		}
		
		//校验两次密码是否一致
		function repasswordValidate(){
			var passwordValue= document.getElementById('password').value;
			var repasswordValue= document.getElementById('repassword').value;
			if(passwordValue!=repasswordValue){
				document.getElementById('repassword_span').innerHTML= '两次密码输入不一致！请重新输入。';
				return false;
			}	
			return true;
		}
		
		//该表验证码图片
		function changeCheckCode(){
			//改变图片的src属性来达到访问checkcode的目的。time的作用是为了改变src,在checkcode中并未使用
			document.getElementById('im').src= '${pageContext.request.contextPath}/checkcode?time'+new Date().getTime();
		}
	</script>
</head>
<body>
	${requestScope["regist.errormessage"] }<%--错误信息 --%>
	<br>
	<c:forEach items="${registerrorMap }" var="entry"><%--注册格式错误信息 --%>
		${entry.value }<br>
	</c:forEach>
	<form action="${pageContext.request.contextPath }/user" method="post" onsubmit="return checkForm();">
		<input  type="hidden"  value="regist" name="method">
		<table border="1" align="center" width="65%">
			<caption>用户注册</caption>
			<tr>
				<td>用户名</td>
				<td>
					<input type="text" name="username" id="username">
					<span id="username_span"></span><!-- span用来占位，以保持和下面的验证码列对齐 -->
				</td>
			</tr>
			<tr>
				<td>密码</td>
				<td>
					<input type="password" name="password" id="password">
					<span id="password_span"></span>
				</td>
			</tr>
			<tr>
				<td>确认密码</td>
				<td>
					<input type="password" name="repassword" id="repassword">
					<span id="repassword_span"></span>
				</td>
			</tr>
			<tr>
				<td>昵称</td>
				<td>
					<input type="text" name="nickname" id="nickname">
					<span id="nickname_span"></span>
				</td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td>
					<input type="text" name="email" id="email">
					<span id="email_span"></span>
				</td>
			</tr>
			<tr>
				<td>验证码</td>
				<td>
					<input type="text" name="checkcode" id="checkcode">
					<img alt="验证码" src="${pageContext.request.contextPath }/checkcode" id="im"
					  	onclick="changeCheckCode()"	>
					<span id="checkcode_span">
						<a href="javascript:void(0)" onclick="changeCheckCode()"><font color="black">看不清?换一张</font></a>
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="注册">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset"  value="取消">
				</td>
			</tr>
			
		</table>

	</form>
</body>
</html>