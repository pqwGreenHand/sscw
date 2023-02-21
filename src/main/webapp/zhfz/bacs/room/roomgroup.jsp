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
<script type="text/javascript" src="roomgroup.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="datagrid"></table>
	<div id="data_dialog" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#data-buttons">
		<div class="ftitle">功能室分组信息</div>
		<div style="height: 150px;">
			<form id="room_group_form" class="form-style" method="post"
				style="height: 80px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>分组名称:</label> <input id="name" name="name" class="easyui-textbox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
				</div>
				<div class="fitem">
					<label>所属办案场所:</label> 
					<select id="interrogateAreaId" name="interrogateAreaId" class="easyui-combobox" style = "margin:-2px;width:170px"  required="true"></select>
				</div>
			</form>
		</div>
		<div id="data-buttons">
			<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRoomGroup()">保存</a> 
				<a href="#" name="noButton" class="easyui-linkbutton"iconCls="icon-cancel" onclick="javascript:$('#data_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
	<!-- toolbar -->
	<div id="toolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" id="roomGroupAdd" onclick="roomGroupAdd()">添加</a>
		</div>
		<div>
				分组名称: <input id="s_room_group_name" class="easyui-validatebox" style="width:100px">
				所属办案场所: <input id="s_interrogate_area_name" class="easyui-validatebox" style="width:100px">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
			</div>
	</div>
</body>
</html>