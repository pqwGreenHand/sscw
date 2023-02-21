<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日台账</title>
<%@ include file="../../../common/common-head.jsp"%>
<script type="text/javascript" src="generalAccount.js"></script>
<link rel="stylesheet" type="text/css" href="../export/css.css" />
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../../common/common-loading.jsp"%> 
<!-- 数据展示 -->
	<div data-options="region:'center',split:true" style="width: 100%; height: 100%;" align="center">
		<table id="datagrid"></table>
	</div>
	<!-- toolbar 	-->
	<div id="exportToolbar" style="padding:5px;height:auto">
<!-- 条件查询-->
		<div>
		<div id="tj_btn" style="margin-bottom:5px;width: 100%;height: 100%;" align="center">
			 <input type="button" id="securityAdd" name="tijiao" value="数据导出" class="bc_on9" onclick="saveData()"/>
		</div> 
				开始时间: <input id="startTime1" class="easyui-datetimebox" style="width:150px">
				结束时间: <input id="endTime1" class="easyui-datetimebox" style="width:150px">
		  
				 <a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-search" onclick="doSearch()" name="noButton">查询</a> <a
				href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear"
				onclick="doClear()">清除</a>
		</div>
	</div>
	<!-- 导出数据表单 -->
	<form id="exportForm" name="exportForm" method="post" action="../../export/accountExcel.do">
		<input type="text" id="startTime" name="startTime">
		<input type="text" id="endTime" name="endTime">
		<input type="text" id="type" name="type" value="yue">
	</form>
</body>
</html>