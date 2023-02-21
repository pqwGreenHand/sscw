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
<script type="text/javascript" src="roomtype.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="datagrid"></table>
	<div id="data_dialog" class="easyui-dialog"
		style="width: 400px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#data-buttons">
		<div class="ftitle">功能室类型信息</div>
		<div style="height: 100px;">
			<form id="room_type_form" class="form-style" method="post"
				style="height: 80px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>类型名称:</label> <input id="name" name="name" class="easyui-textbox"
						required="true" data-options="validType:'maxLength[32]',invalidMessage:'最长128字节'">
				</div>
			</form>
		</div>
		<div id="data-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveRoomType()">保存</a> 
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#data_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
	<!-- toolbar -->
	<div id="Toolbar" style="padding:5px;height:auto">
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" id="roomTypeAdd" onclick="roomTypeAdd()">添加</a>
				类型名称: <input id="s_room_type_name" class="easyui-validatebox" style="width:100px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton" onclick="doClear()">清除</a>
				
		</div>
	</div>
</body>
</html>