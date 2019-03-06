<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<header>
	<title>main page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</header>
<body>
	<%
		String contextPath = request.getContextPath();
	%>
	<a href="<%=contextPath%>/logout">Logout</a>
	<shiro:principal property="username"/>
	<shiro:authenticated>已登录</shiro:authenticated>
</body>
</html>