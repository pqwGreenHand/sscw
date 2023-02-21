<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../common-head.jsp"%>
<script type="text/javascript" src="operLog.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../common-loading.jsp"%>
	<table id="datagrid" ></table>
	
	<!-- toolbar -->
	<div id="toolbar" style="padding:0px;height:auto">
		<div>
		                        系统名称: <input id="s_name" class="easyui-validatebox" style="width:100px">
				操作人: <input id="s_user" class="easyui-validatebox" style="width:100px">
				操作内容:<input id="s_content" class="easyui-validatebox" style="width:180px" >
				开始时间: <input id="s_start_date" class="easyui-datetimebox" style="width:120px">
				结束时间: <input id="s_end_date" class="easyui-datetimebox" style="width:120px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="doSearch()">搜索</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton" onclick="doClear()">清空</a>
		</div>
	</div>
</body>
</html>