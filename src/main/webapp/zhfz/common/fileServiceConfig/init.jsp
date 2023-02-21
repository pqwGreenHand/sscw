<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="init.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="certificategrid"></table>
	<div id="certificate_dialog" class="easyui-dialog"
		 style="width: 550px; height: auto; padding: 10px 20px" closed="true"
		 buttons="#enterprise-buttons">
		<div style="height: 300px;">
			<form id="save-form" class="form-style save-form" method="post"
				  style="height: auto;">
				<input type="hidden" name="id" />
				<div class="fitem">
					<label>系统类型:</label> <input id="save-sys-type" name="sysType" class="easyui-combobox"
												required="true" style="width: 285px;"
												data-options=""/><span style=";color: red;">*</span>
				</div>
				<div class="fitem save-sys-id">
					<label>办案场所:</label> <input name="sysId" id="save-sys-id" class="easyui-combobox"
												 style="width: 285px;"/><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>文件服务Ip:</label> <input name="ip" onkeyup="keyUp()" class="easyui-validatebox"
												style="width: 275px;"/><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>文件服务端口:</label> <input name="port" onkeyup="keyUp()" class="easyui-validatebox"
												style="width: 275px;"/><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>文件服务地址:</label> <input onkeyup="keyUp()" name="url" class="easyui-validatebox"
												style="width: 275px;"/><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>文件保存地址:</label> <input name="fileSavePath" class="easyui-validatebox"
												  style="width: 275px;"/><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>描述:</label>
					<textarea rows=5 name="desc" class="textarea easyui-validatebox" style="width: 275px;height: 52px;" required="true"
							  data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"></textarea><span style=";color: red;">*</span>
				</div>
			</form>
		</div>
		<div id="enterprise-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
			   onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"
													iconCls="icon-cancel" name="noButton"
													onclick="javascript:$('#certificate_dialog').dialog('close')">关闭</a>
		</div>
	</div>

	<!-- toolbar -->
	<div id="certificateToolbar" style="padding:5px;height:auto;overflow: hidden;">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" id="fscAdd" onclick="add()">添加</a>
		</div>
		<div>
			<form  id="search" style="float: left;margin: 3px;">
				系统类型:<input class="easyui-combobox" id="query-sys-type" name="sysType" style="width:200px" />
				<%--办案场所: <input class="easyui-combobox" id="query-sys-id" name="sysId" style="width:200px" />--%>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
			</form>
		</div>
	</div>
</body>
</html>