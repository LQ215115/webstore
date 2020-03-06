<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>商店列表</title>

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
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/pager.css'/>" />
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/list.js'/>"></script>
</head>
<style>
p {
	font-weight: bolder;
	text-align: center;
	opacity: 0.5;
}

h1 {
	text-align: center
}

a {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}
</style>
<body>
	<c:choose>
		<c:when test="${empty sessionScope.place }">
			<h1>
				<a href="body.jsp">点击这里填写地址</a>
			</h1>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>

	<ul>
		<c:forEach items="${pb.beanList }" var="store">
			<li>
				<div class="inner">
					<a class="pic" target="_parent" href="<c:url value='store.jsp?id=${store.id }'/>"><img
						src="<c:url value='/${store.img }'/>" border="0" /></a>
					<p>
						<a id="storename" title="点击进入店铺"
							href="<c:url value='store.jsp?id=${store.id }'/>">${store.name }</a>
					</p>
					<%-- url标签会自动对参数进行url编码 --%>
					<c:url value="/StoreServlet" var="authorUrl">
						<c:param name="method" value="findByAuthor" />
						<c:param name="author" value="${store.author }" />
					</c:url>
					<p>
						<span>店主：</span><a href="${authorUrl }" name='P_zz' title='点击查看该店家所有店铺'>${store.author }</a>
					</p>
					<p>
						<span>店铺位置：</span>${store.place.getSheng() }-${store.place.getShi() }-${store.place.getQu() }</p>
					<p>
						<span>开店时间：</span>${store.time }</p>
					<p>
						<span>店铺排名：</span>${store.orderby }</p>
				</div>
			</li>
		</c:forEach>
	</ul>

	<div style="float:left; width: 100%; text-align: center;">
		<hr />
		<br />
		<%@include file="pager.jsp"%>
	</div>

</body>

</html>

