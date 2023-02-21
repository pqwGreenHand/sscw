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
	<%@ include file="../../common/common-head.jsp"%>
	<script type="text/javascript" src="sacwRecord.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<div data-options="region:'west',split:false"   style="width: 100%; height: 100%;">
	<table id="datagrid" ></table>
</div>

<div id="toolbar" style="padding: 5px; height: auto">
	<div>
		<label>用户名称:</label>
		<input id="xm" name="xm" class="easyui-validatebox" style="font: 14px arial; width: 100px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
		<label>证件号码:</label>
		<input id="zjhm" name="zjhm" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
		<label>设备号:</label>
		<input id="sensorId" name="sensorId" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
		<label>开始时间:</label>
		<input id="start_date" class="easyui-datetimebox"style="width: 155px">
		<label>结束时间:</label>
		<input id="end_date" class="easyui-datetimebox" style="width: 155px">
		<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>
	   <!-- <img src ="http://localhost:8080/test/qie.jpg" /> -->
	</div>
</div>

<div id="picture_dialog" class="easyui-dialog" style="width: 500px; height: 600px; padding: 10px 20px" closed="true" buttons="#data-buttons">
	<center>
	<div> 
       <img id="picDiv" alt="" src="" style="height: 580px;width:320px;" > 
	</div>
	</center>
	<div id="data-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"  onclick="javascript:$('#picture_dialog').dialog('close')">关闭</a>
	</div>
</div>



</body>
</html>