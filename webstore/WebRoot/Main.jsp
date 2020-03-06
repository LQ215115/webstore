<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>主页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>

<body>
<table class="table" align="center">
	<tr class="trTop">
		<td colspan="2" class="tdTop">
			<iframe frameborder="0" src="top.jsp" name="top"></iframe>
		</td>
	</tr>
	<tr>
		<td class="tdLeft" rowspan="2">
			<iframe name="left" src="storeleft.jsp" frameborder="0" >" ></iframe>
		</td>
		<td class="tdSearch" style="border-bottom-width: 0px;">
			<iframe frameborder="0" src="search.jsp" name="search" scrolling="no"></iframe>
		</td>
	</tr>
	<tr>
		<td style="border-top-width: 0px;">
			<iframe  frameborder="0" src="/webstore/StoreServlet?method=findAll" name="body"></iframe>
		</td>
	</tr>
</table>
</body>
</html>
