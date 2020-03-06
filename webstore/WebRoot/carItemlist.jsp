<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/caritemlist.css'/>">
<script type="text/javascript">
$(function() {
	showTotal();//计算总计
	
	/*
	给全选添加click事件
	*/
	$("#selectAll").click(function() {
		/*
		1. 获取全选的状态
		*/
		var bool = $("#selectAll").attr("checked");
		/*
		2. 让所有条目的复选框与全选的状态同步
		*/
		setItemCheckBox(bool);
		/*
		3. 让结算按钮与全选同步
		*/
		setJieSuan(bool);
		/*
		4. 重新计算总计
		*/
		showTotal();
	});
	
	/*
	给所有条目的复选框添加click事件
	*/
	$(":checkbox[name=checkboxBtn]").click(function() {
		var all = $(":checkbox[name=checkboxBtn]").length;//所有条目的个数
		var select = $(":checkbox[name=checkboxBtn][checked=true]").length;//获取所有被选择条目的个数

		if(all == select) {//全都选中了
			$("#selectAll").attr("checked", true);//勾选全选复选框
			setJieSuan(true);//让结算按钮有效
		} else if(select == 0) {//谁都没有选中
			$("#selectAll").attr("checked", false);//取消全选
			setJieSuan(false);//让结算失效
		} else {
			$("#selectAll").attr("checked", false);//取消全选
			setJieSuan(true);//让结算有效				
		}
		showTotal();//重新计算总计
	});
	
	$(".jian").click(function(){
	var id=$(this).attr("id").substring(0,32);
	var quantity=$("#"+id+"Quantity").val();
	if(quantity == 1) {
			if(confirm("您是否真要删除该条目？")) {
				location = "/webstore/CarItemServlet?method=batchDelete&carItemIds=" + id;
			}
		} else {
			sendUpdateQuantity(id, quantity-1);
		}
	});
	
	// 给加号添加click事件
	$(".jia").click(function() {
		// 获取carItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		sendUpdateQuantity(id, Number(quantity)+1);
	});
});
// 请求服务器，修改数量。
function sendUpdateQuantity(id, quantity) {
	$.ajax({
		async:false,
		cache:false,
		url:"./CarItemServlet",
		data:{method:"updateQuantity",carItemId:id,quantity:quantity},
		type:"POST",
		dataType:"json",
		success:function(result) {
			//1. 修改数量
			$("#" + id + "Quantity").val(result.quantity);
			//2. 修改小计
			$("#" + id + "Subtotal").text(result.subtotal);
			//3. 重新计算总计
			showTotal();
		}
	});
}

/*
 * 计算总计
 */
function showTotal() {
	var total = 0;
	/*
	1. 获取所有的被勾选的条目复选框！循环遍历之
	*/
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		//2. 获取复选框的值，即其他元素的前缀
		var id = $(this).val();
		//3. 再通过前缀找到小计元素，获取其文本
		var text = $("#" + id + "Subtotal").text();
		//4. 累加计算
		total += Number(text);
	});
	// 5. 把总计显示在总计元素上
	$("#total").text(round(total,2));//保留2为小数
}

/*
 * 统一设置所有条目的复选按钮
 */
function setItemCheckBox(bool) {
	$(":checkbox[name=checkboxBtn]").attr("checked", bool);
}

/*
 * 设置结算按钮样式
 */
function setJieSuan(bool) {
	if(bool) {
		$("#jiesuan").removeClass("kill").addClass("jiesuan");
		$("#jiesuan").unbind("click");//撤消当前元素止所有click事件
	} else {
		$("#jiesuan").removeClass("jiesuan").addClass("kill");
		$("#jiesuan").click(function() {return false;});
	}
	
}
function batchDelete(){
var carItemIdArray=new Array();
$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
carItemIdArray.push($(this).val());
});
location="/webstore/CarItemServlet?method=batchDelete&carItemIds="+carItemIdArray
}

function jiesuan() {
//判断总价是否为0
	if($("#total").text==""){
	alert("违规操作");
	return ;
	}
	// 1. 获取所有被选择的条目的id，放到数组中
	var carItemIdArray = new Array();
	var param = encodeURIComponent(encodeURIComponent($("#address").val()));//编码2次，服务器解码一次可以防止中文乱码
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		carItemIdArray.push($(this).val());//把复选框的值添加到数组中
	});	
	location="/webstore/OrderServlet?method=createOrder&carItemIds="+carItemIdArray+"&address="+param
}
</script>
  </head>
  <body>

<c:choose>
<c:when test="${empty carItemList }">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
			</td>
			<td>
				<span class="spanEmpty">您的购物车中暂时没有商品</span>
			</td>
		</tr>
	</table>  
	</c:when>
	<c:otherwise>


<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr align="center" bgcolor="#efeae5">
		<td align="left" width="50px">
			<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
		</td>
		<td colspan="2">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
		<td>操作</td>
	</tr>



<c:forEach items="${carItemList }" var="carItem">
	<tr align="center">
		<td align="left">
			<input value="${carItem.carItemId }" type="checkbox" name="checkboxBtn" checked="checked"/>
		</td>
		<td align="left" width="70px">
			<img border="0" width="54" align="top" src="<c:url value='/${carItem.food.img }'/>"/>
		</td>
		<td align="center" width="200px">
		    <span>${carItem.food.fname }</span>
		</td>
		<td><span>&yen;<span class="currPrice">${carItem.food.currPrice }</span></span></td>
		<td >
			<a class="jian" id="${carItem.carItemId }Jian"></a><input class="quantity" readonly="readonly" id="${carItem.carItemId }Quantity" type="text" value="${carItem.quantity }"/><a class="jia" id="${carItem.carItemId }Jia"></a>
		</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subTotal" id="${carItem.carItemId }Subtotal">${carItem.subtotal }</span></span>
		</td>
		<td>
			<a href="<c:url value='/CarItemServlet?method=batchDelete&carItemIds=${carItem.carItemId }'/>">删除</a>
		</td>
	</tr>
</c:forEach>
	<tr>
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDelete();">批量删除</a>
		</td>
		<td colspan="3" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
		</td>
	</tr>
	<tr>
	<td>
	收货人地址：<input type="text" id="address" value="${address }"/>
	</td>
		<td colspan="7" align="right">
			<a href="<c:url value='javascript:jiesuan();'/>" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	</c:otherwise>
</c:choose>


  </body>
</html>
