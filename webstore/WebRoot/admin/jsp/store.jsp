<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>主页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<style>
*{font-size:10pt;}
body{text-align:center;background: url(/images/3.jpg); background-size:100% 100%;}
.table{width:1200px;height:100%;border:1px solid #15B69A;border-collapse: collapse;}
.table td{border:1px solid #408080;}
.tdLeft{width: 190px;padding:5px;height:110%;}
iframe {width: 100%;height: 100%;}
</style>
<body>
<table class="table" align="center">

	<tr>
		<td class="tdLeft" style="border-bottom-width: 0px;">
			<iframe name="left" frameborder="0" src="/admin/AdminFoodServlet?method=findLeft&&id=${param.id }"></iframe>
		</td>
				<td >
			<iframe  frameborder="0" src="/admin/AdminFoodServlet?method=findAll&&id=${param.id }" name="body"></iframe>
		</td>
	</tr>
</table>
</body>
</html>
