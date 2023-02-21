<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="entranceList.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
	<%@ include file="../../common/common-loading.jsp"%>
	<table id="datagrid"></table>
	<!-- toolbar -->
	<div id="toolbar" style="height: auto;padding: 5px;">
		<div>
			姓名: <input id="s_name" class="easyui-validatebox" style="width: 85px;height:25px">
			证件号码:<input id="s_certificateNo" class="easyui-validatebox" style="width: 180px;height:25px">
			办案场所:<input type="text" id="s_areaId" name="areaId" class="easyui-combobox">
			入区时间从: <input id="s_start_date" class="easyui-datetimebox" style="width: 145px;height:30px"> 
			到: <input id="s_end_date" class="easyui-datetimebox" style="width: 145px;height:30px">
			<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a> 
			<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清空</a>
			<a href="index.jsp" name="noButton" class="easyui-linkbutton" iconCls="icon-back">返回</a>
		</div>
	</div>
	<div id="exit_dialog2" class="easyui-dialog"
		style="width: 550px; height:400px; padding: 10px 20px" closed="true">
		<div data-options="region:'center',split:true" style="width: 100%; height: 100%;">
			<table id="exitDatagrid2"></table>
		</div>
   </div>
</body>
</html>