<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  	<head>
	    <title>系统登录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
  	</head>
	<body>
	<form id="loginForm" name="loginForm" method="post" action="<c:url value="/login"/>">
		<table border=0>
			<tr>
				<td align="right" width="70">帐&nbsp;号:</td>
				<td><input type="text" id="username" name="username"/></td>
			</tr>
			<tr>
				<td align="right">密&nbsp;码:</td>
				<td><input type="password" id="password" name="password"/></td>
			</tr>
			<tr>
				<td align="right">验证码:</td>
				<td>
					<input type="text" id="captcha" name="captcha" class="captcha"></input>
					<img id="captchaImg" alt="点击刷新验证码" src="<c:url value="/kaptcha/kaptcha.jpg?r=1"/>"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center; color:red;" id="showMsg">
					<%
						String errorMessage = (String)session.getAttribute("errorMessage");
						if(errorMessage != null && errorMessage.length() > 0){
							out.print(errorMessage);
						}
					%>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value=" 登 录 " onclick="_login();">
				</td>
			</tr>
		</table>
    </form>
    
	<script type="text/javascript">
		function _login(){
			jQuery("#loginForm").submit();
		}
	
		jQuery(function(){
		    jQuery("#username").focus();
		    
		    var img = jQuery("#captchaImg");
			img.click(function(){
				var url = img.attr("src");
				url = url.substring(0, url.indexOf("?")) + "?r=" + Math.random();
				img.attr("src",url);
			});
		});

		document.onkeydown = function(e){
		    var event = e || window.event;  
		    var code = event.keyCode || event.which || event.charCode;
		    if (code == 13) {
		    	_login();
		    }
		}
	</script>
		
	</body>
</html>
