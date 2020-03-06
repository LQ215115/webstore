<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>分类列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/admin/css/categorylist.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
  </head>
  
  <body>
    <h2 style="text-align: center;">分类列表</h2>
    <table align="center" border="1" cellpadding="0" cellspacing="0">
    	<caption class="captionAddOneLevel">
    	  <a href="<c:url value='/admin/jsp/add.jsp?id=${id }'/>">添加一级分类</a>
    	</caption>
    	<tr class="trTitle">
    		<th>分类名称</th>
    		<th>描述</th>
    		<th>操作</th>
    	</tr>
    	
<c:forEach items="${parents }" var="parent">    	
    	<tr class="trOneLevel">
    		<td width="200px;">${parent.cname }</td>
    		<td>${parent.desc }</td>
    		<td width="200px;">
    		  <a href="<c:url value='/admin/AdminCategoryServlet?method=addChildPre&pid=${parent.cid }&id=${id }'/>">添加二级分类</a>
    		  <a href="<c:url value='/admin/AdminCategoryServlet?method=editParentPre&cid=${parent.cid }&id=${id }'/>">修改</a>
    		  <a onclick="return confirm('您是否真要删除该一级分类？')" href="<c:url value='/admin/AdminCategoryServlet?method=deleteParent&cid=${parent.cid }&id=${id }'/>">删除</a>
    		</td>
    	</tr>
   <c:forEach items="${parent.children }" var="child">
    	<tr class="trTwoLevel">
    		<td>${child.cname }</td>
    		<td>${child.desc }</td>
    		<td width="200px;" align="right">
    		 <a href="<c:url value='/admin/jsp/store.jsp?id=${id }'/>">食品管理</a>
    		  <a href="<c:url value='/admin/AdminCategoryServlet?method=editChildPre&cid=${child.cid }&id=${id }'/>">修改</a>
    		  <a onclick="return confirm('您是否真要删除该二级分类？')" href="<c:url value='/admin/AdminCategoryServlet?method=deleteChild&cid=${child.cid }&id=${id }'/>">删除</a>
    		</td>
    	</tr>
   </c:forEach>
</c:forEach>
    </table>
     <h1 style="text-align: center;"><a href="<c:url value='/admin/AdminStoreServlet?method=findById&id=${id }'/>">编辑店铺信息</a></h1>
  </body>
</html>
