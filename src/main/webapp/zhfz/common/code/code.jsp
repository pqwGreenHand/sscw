<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../common-head.jsp"%>
<script type="text/javascript" src="code.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../common-loading.jsp"%>
	<table id="certificategrid"></table>
	<div id="certificate_dialog" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#enterprise-buttons">
		<div style="height: 150px;">
			<form id="certificate_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>字典类型:</label> <input id="type" name="type" class="easyui-validatebox"
												required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color: red">*</span></span>
				</div>
				<div class="fitem">
					<label>字典键:</label> <input id="codeKey" name="codeKey" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color: red">*</span></span>
				</div>
				<div class="fitem">
					<label>字典值:</label> <input id="codeValue" name="codeValue" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color: red">*</span></span>
				</div>
				<div class="fitem">
					<label>描述:</label> <input id="keyDesc" name="keyDesc" class="easyui-validatebox"
											   required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color: red">*</span></span>
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
	<div id="certificateToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="codeAdd" onclick="codeAdd()">添加</a>
		</div>
		<div>
				字典类型: <input id="s_type" class="easyui-validatebox" style="width:100px">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				
			</div>
	</div>
</body>
</html>