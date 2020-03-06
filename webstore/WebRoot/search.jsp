<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>多条件查询</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
<style type="text/css">
body {
	margin-top: 5px;
	margin-bottom: 0px;
	margin-left: 200px;
	color: #404040;
}

input {
	width: 300px;
	height: 30px;
	border-style: solid;
	margin: 0px;
	border-color: #15B69A;
}


a {
	text-transform: none;
	text-decoration: none;
	border-width: 0px;
}

a:hover {
	text-decoration: underline;
	border-width: 0px;
}

span {
	margin: 0px;
}
</style>
<script type="text/javascript">
$(function() {
	$("#name").focus(function() {
		$("#name").val("");
	});
});
</script>
</head>

<body>
	<form action="<c:url value='/StoreServlet'/>" method="post" target="body"
		id="form1">
		<input type="hidden" name="method" value="findByName" /> 
		
		<input type="text" name="name" value="输入店名" id="name"/><span >  
		<a href="javascript:document.getElementById('form1').submit();"><img
				 border="0" src="/webstore/images/btn.bmp" style="height: 19px; "/></a> 
				 <a href="<c:url value='/gj.jsp?from=user'/>"
			style="font-size: 14pt; color: #404040;" target="body">高级搜索</a>
		</span>
	</form>

</body>
</html>
