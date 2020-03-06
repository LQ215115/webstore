<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'down.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<style>
div {
	text-align: center;
	position: absolute;
	top: 20%;
	left: 15%;
}
</style>
<body>

<c:choose>
<c:when test="${empty sessionScope.sessionUser }">
	<div align="center">
	<a target="_parent"
			href="<c:url value='/login.jsp'/>">
			<img src="<c:url value='images/login3.jpg'/>" border="0" />
		</a>
	</div>
	</c:when>
	<c:otherwise>
		   <div>
		<p class="price">
			<span>总价：</span> <span class="price_n">&yen;${totalprice }</span>
		</p>
		<a target="body" class="pic"
			href="<c:url value='/CarItemServlet?method=myCar'/>">
			<img src="<c:url value='images/buy.png'/>" border="0" />
		</a>
	</div>	
	</c:otherwise>
</c:choose>
	
</body>
</html>
