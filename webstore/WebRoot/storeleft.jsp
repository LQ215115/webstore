<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'storeleft.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/left.css'/>">
</head>
<style>
a {
	display: block;
	height: 58px;
	line-height: 38px;
	padding-left: 0px;
	color:black;
	text-decoration: none;
	border-bottom: 1px solid #51a9f18c;
}
a:hover {
		text-decoration: underline;
		color: #ffffff;
		font-weight: 900;
	}
</style>
<body>

	<form name="form1" action="<c:url value='/StoreServlet?method=findByCategory&category=快餐店'/>" method="post" target="body">
		<a href="javascript:document.form1.submit();"> 快餐店</a> </form>
			<form name="form2" action="<c:url value='/StoreServlet?method=findByCategory&category=炒菜店'/>" method="post" target="body">
		<a href="javascript:document.form2.submit();"> 炒菜店</a> </form>
			<form name="form3" action="<c:url value='/StoreServlet?method=findByCategory&category=面馆'/>" method="post" target="body">
		<a href="javascript:document.form3.submit();"> 面馆</a> </form>
			<form name="form4" action="<c:url value='/StoreServlet?method=findByCategory&category=火锅店'/>" method="post" target="body">
		<a href="javascript:document.form4.submit();"> 火锅店</a> </form>
			<form name="form5" action="<c:url value='/StoreServlet?method=findByCategory&category=奶茶店'/>" method="post" target="body">
		<a href="javascript:document.form5.submit();"> 奶茶店店</a> </form>
			<form name="form6" action="<c:url value='/StoreServlet?method=findByCategory&category=小吃店'/>" method="post" target="body">
		<a href="javascript:document.form6.submit();"> 小吃店</a> </form>
<form name="form7" action="<c:url value='/StoreServlet?method=findByCategory&category=其他'/>" method="post" target="body">
		<a href="javascript:document.form7.submit();"> 其他</a> </form>
</body>
</html>
