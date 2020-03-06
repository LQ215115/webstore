<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>food列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/storelist.css'/>">
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/foodlist.js'/>"></script>
</head>
<style>
input {
	text-align: center;
}
</style>
<body>
	<ul>
		<c:forEach items="${foods }" var="food">
			<li>
				<div class="inner">
				<p><img src="<c:url value='/${food.img }'/>" border="0" />
						</p>
						<p>名称:${food.fname}</p>
						<span>原价：</span>
					<span class="price_r"
						style="color:red;text-decoration: line-through">&yen;${food.price }</span>
					(<span class="price_s">${food.discount }折</span>)
					<p class="price">
						<span>当前价格：</span> <span class="price_n">&yen;${food.currPrice }</span>
					</p>
					<p></p>
					<p></p>
					<div align="center" >
						<a target="down" onClick=onClickJian(this)
							href="<c:url value='/CarItemServlet?method=moveCarItem&fid=${food.fid }'/>"><img
						 src="<c:url value='images/jian.png'/>" border="0" /></a>
						<input readonly="readonly" size="1" type="text" value="0"
							> <a target="down" onClick=onClickJia(this)
							href="<c:url value='./CarItemServlet?method=addCarItem&fid=${food.fid }'/>"><img
							src="<c:url value='images/jia.png'/>" border="0" /></a>
					</div>
				</div>
			</li>
		</c:forEach>

	</ul>
</body>

</html>

