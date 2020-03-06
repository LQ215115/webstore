function onClickJian(obj) {
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		dataType : "json",
		data : {
			method : "ajaxValidateLogin"
		},
		url : "./UserServlet",
		success : function(status) {
			if (status == "0") {
				alert("请点击左下角进行登录");
			}
			if (status == "1") {
				if ($(obj).next().attr("value") == "0") {
					alert("购买数量必须大于0")
				} else {
					$(obj).next().attr("value", Number($(obj).next().attr("value")) - Number(1));
				}
			}
		}
	});
}

function onClickJia(obj) {
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		dataType : "json",
		data : {
			method : "ajaxValidateLogin"
		},
		url : "./UserServlet",
		success : function(status) {
			if (status == "0") {
				alert("请点击左下角进行登录");
			}
			if (status == "1") {
				$(obj).prev().attr("value", Number($(obj).prev().attr("value")) + Number(1));
			}
		}
	});
}