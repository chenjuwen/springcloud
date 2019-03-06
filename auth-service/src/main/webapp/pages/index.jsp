<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<header>
	<title>main page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</header>
<body>
	<shiro:principal property="loginName"/>
	
	<shiro:authenticated>
		已登录<br><br>
		<a href="<%=request.getContextPath()%>/logout">Logout</a>
	</shiro:authenticated>
</body>
</html>