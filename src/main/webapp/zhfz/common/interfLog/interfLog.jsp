<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<%@ include file="../common-head.jsp"%>
<script type="text/javascript" src="interfLog.js"></script>
<script type="text/javascript">
	// dataebox扩展一个清空按钮
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: '清空',
        handler: function (target) {//target对象就是当前的inupt对象，不需要写死id
            $(target).datebox('setValue', '').datebox("hidePanel");
        }
    });
    $.extend($.fn.datebox.defaults, {
        buttons: buttons
    }); 
    
    // databox格式化日期
    $.fn.datebox.defaults.formatter = function(date){
    	var y = date.getFullYear();
    	var m = date.getMonth()+1;
    	var d = date.getDate();
    	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }
</script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../common-loading.jsp"%>	
	<table id="datagrid"></table>	
	<!-- toolbar -->
	<div id="toolbar" style="padding:5px;height:auto">
		<div>
			操作人: <input id="s_user" class="easyui-validatebox" style="width:100px">
			操作内容:<input id="s_content" class="easyui-validatebox" style="width:180px">
			开始时间: <input id="s_start_date" class="easyui-datebox" style="width:120px">
			结束时间: <input id="s_end_date" class="easyui-datebox" style="width:120px">
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">搜索</a>
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>
		</div>
	</div>
</body>
</html>