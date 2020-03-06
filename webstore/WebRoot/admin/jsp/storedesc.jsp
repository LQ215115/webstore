<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>desc.jsp</title>

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
	href="<c:url value='/admin/css/desc.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/admin/css/jquery.datepick.css'/>">
<script type="text/javascript"
	src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/admin/js/jquery.datepick.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/admin/js/jquery.datepick-zh-CN.js'/>"></script>

<script type="text/javascript" src="<c:url value='/admin/js/desc.js'/>"></script>

<script type="text/javascript">

	$(function() {
		$("#box").attr("checked", false);
		$("#formDiv").css("display", "none");
		$("#show").css("display", "");



		$("#time").datepick({
			dateFormat : "yy-mm-dd"
		});
		// 操作和显示切换
		$("#box").click(function() {
			if ($(this).attr("checked")) {
				$("#show").css("display", "none");
				$("#formDiv").css("display", "");
			} else {
				$("#formDiv").css("display", "none");
				$("#show").css("display", "");
			}
		});
		
		
				//填写地址
		$("#sheng").append('<option value="">' + "请选择" + '</option>');
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		dataType : "text",
		data : {
			method : "findPlace"
		},
		url : "/PlaceServlet",
		success : function(result) {
			var obj = JSON.parse(result); //由JSON字符串转换为JSON对象
			for (var i = 0; i < obj.length; i++) {
				var val = obj[i].pname;
				$("#sheng").append(
					'<option value='+val+' >' + val + '</option>');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
	});
	$("#sheng").change(function() {
		$("#shi").find("option").remove();//清空下拉框
		$("#shi").append('<option value="">' + "请选择" + '</option>');
		var pname=$("#sheng").find("option:selected").text();//找到第一个下拉框文本，传给后台
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			dataType : "text",
			data : {
				method : "findShi","pname":pname
			},
			url : "/PlaceServlet",
			success : function(result) {
				var obj = JSON.parse(result); //由JSON字符串转换为JSON对象
				for (var i = 0; i < obj.length; i++) {
					var val = obj[i].pname;
					$("#shi").append(
						'<option value='+val+'>' + val + '</option>');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
	});
	$("#shi").change(function() {
		$("#qu").find("option").remove();//清空下拉框
		$("#qu").append('<option value="">' + "请选择" + '</option>');
		var pname=$("#shi").find("option:selected").text();
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			dataType : "text",
			data : {
				method : "findQu","pname":pname
			},
			url : "/PlaceServlet",
			success : function(result) {
				var obj = JSON.parse(result); //由JSON字符串转换为JSON对象
				for (var i = 0; i < obj.length; i++) {
					var val = obj[i].pname;
					$("#qu").append(
						'<option value='+val+'>' + val + '</option>');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});
	});
	});
	/*
	 * 点击编辑按钮时执行本函数
	 */
	function editForm() {
		$("#method").val("edit");
		$("#form").submit();
	}
	/*
	 * 点击删除按钮时执行本函数
	 */
	function deleteForm() {
		$("#method").val("delete");
		$("#form").submit();
	}
</script>
</head>

<body>
	<input type="checkbox" id="box">
	<label for="box">编辑或删除</label>
	<br />
	<br />
	<div id="show">
		<div class="sm">${store.name }</div>
		<img align="top" src="<c:url value='/${store.img }'/>" class="tp" />
		<div id="book" style="float:left;">
			<ul>
				<li>店主：${store.author }</li>
				<li>所属位置:${store.place.sheng }-${store.place.shi }-${store.place.qu }</li>
				<li>店铺分类：${store.category }</li>
				<li>店铺排名： ${store.orderby }</li>
				<li>开店时间：${store.time}</li>
			</ul>
		</div>
	</div>


	<div id='formDiv'>
		<div class="sm">&nbsp;</div>
		<form action="/admin/AdminStoreServlet" method="post" id="form">
			<input type="hidden" name="method" id="method" /> 
			<input type="hidden" name="id" value="${store.id }" /> 
			<img align="top" src="<c:url value='/${store.img }'/>" class="tp" />
			<div style="float:left;">
				<ul>
				<li>店铺名:<input id="name" type="text" name="name" value="${store.name }"/></li>
					<li>店主:<input id="author" type="text" name="author" value="${store.author }"/></li>
					<li>所属位置:<select id="sheng" name="sheng" style="width: 200px;"></select>
						<select id="shi" name="shi" style="width: 200px;"></select> <select
						id="qu" name="qu" style="width: 200px;"></select></li>

					<li>店铺分类：<select name="category" id="category">
							<option value="">===选择分类===</option>
							<option value="快餐店">快餐店</option>
							<option value="炒菜店">炒菜店</option>
							<option value="火锅店">火锅店</option>
							<option value="面馆">面馆</option>
							<option value="奶茶店">奶茶店</option>
							<option value="小吃店">小吃店</option>
							<option value="其他">其他</option>

					</select></li>
					<li>店铺排名： <input id="orderby" type="text" name="orderby" value="${store.orderby }"/></li>
					<li>开店时间：<input type="text" id="time" name="time"
						value="${store.time }" style="width:100px;" />
					</li>
				</ul>
				<hr style="margin-left: 50px; height: 1px; color: #dcdcdc" />
				<input onclick="editForm()" type="button" name="method" id="editBtn"
					class="btn" value="编　　辑"> <input onclick="deleteForm()"
					type="button" name="method" id="delBtn" class="btn" value="删　　除">
					</div>
		</form>
	</div>

</body>
</html>
