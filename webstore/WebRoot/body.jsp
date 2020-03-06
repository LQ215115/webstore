<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="">

<title>body</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">

<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/select.js'/>"></script>
</head>
<body>
	<h1 align="center" style="color:#408080">欢迎进入外卖系统</h1>
	<p align="center" style="color:red">
		<font size="6">请先填写您的地址，提交后我们将更新您附近的店家！</font>
	</p>
	<form name=bodyform action="/webstore/StoreServlet" target="body"
		method="post">
		<input type="hidden" name="method" value="findByPlace" />
		<table align="center">
			<tr>
				<td>省：</td>
				<td><select id="sel1" name="sel1" style="width: 200px;"></select></td>
			</tr>
			<tr>
				<td>市：</td>
				<td><select id="sel2" name="sel2" style="width: 200px;"></select></td>
			</tr>
			<tr>
				<td>区：</td>
				<td><select id="sel3" name="sel3" style="width: 200px;"></select></td>
			</tr>
		</table>
	</form>
	<p align="center">
		<a href="<c:url value='javascript:document.bodyform.submit();'/>"><img
			src="<c:url value='/images/submit.png'/>"></a>
	</p>
</body>
</html>
