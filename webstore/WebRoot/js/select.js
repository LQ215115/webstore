$(function() {
	$("#sel1").append('<option value="">' + "请选择" + '</option>');
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		dataType : "text",
		data : {
			method : "findPlace"
		},
		url : "./PlaceServlet",
		success : function(result) {
			var obj = JSON.parse(result); //由JSON字符串转换为JSON对象
			for (var i = 0; i < obj.length; i++) {
				var val = obj[i].pname;
				$("#sel1").append(
					'<option value='+val+' >' + val + '</option>');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
	});
	$("#sel1").change(function() {
		$("#sel2").find("option").remove();//清空下拉框
		$("#sel2").append('<option value="">' + "请选择" + '</option>');
		var pname=$("#sel1").find("option:selected").text();//找到第一个下拉框文本，传给后台
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			dataType : "text",
			data : {
				method : "findShi","pname":pname
			},
			url : "./PlaceServlet",
			success : function(result) {
				var obj = JSON.parse(result); //由JSON字符串转换为JSON对象
				for (var i = 0; i < obj.length; i++) {
					var val = obj[i].pname;
					$("#sel2").append(
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
	$("#sel2").change(function() {
		$("#sel3").find("option").remove();//清空下拉框
		$("#sel3").append('<option value="">' + "请选择" + '</option>');
		var pname=$("#sel2").find("option:selected").text();
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			dataType : "text",
			data : {
				method : "findQu","pname":pname
			},
			url : "./PlaceServlet",
			success : function(result) {
				var obj = JSON.parse(result); //由JSON字符串转换为JSON对象
				for (var i = 0; i < obj.length; i++) {
					var val = obj[i].pname;
					$("#sel3").append(
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