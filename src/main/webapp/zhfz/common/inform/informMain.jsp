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
<script type="text/javascript" src="informMain.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	
	<table id="informgrid"></table>

	<div id="inform_dialog" class="easyui-dialog"
		style="width: 600px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#enterprise-buttons">
		
		<div style="height: 400px;">
			<form id="inform_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>标题:</label>
						<input id="inform_title" name="title" class="easyui-validatebox" style="width:235px"
						required="true"/>
				</div>
				<div class="fitem">
					<label>内容:</label>
						 <input id="inform_content" name="content" class="easyui-textbox" required="true"
						data-options="multiline:true" style="width:380px;height:280px"/>
				</div>
			</form>
		</div>
		<div id="enterprise-buttons">
			 <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#inform_dialog').dialog('close'); $('#informgrid').datagrid('reload',{trefresh:new Date().getTime(),systemName:systemName} );">关闭</a>
		</div>
	</div>
	
	<!-- toolbar -->
	<div id="informToolbar" style="padding:5px;height:auto">
		<div>
			标题: <input id="s_title" name="title" class="easyui-textbox" style="width:120px">
			内容: <input id="s_content" name="content" class="easyui-textbox" style="width:120px">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>

			</div>
	</div>
</body>
</html>