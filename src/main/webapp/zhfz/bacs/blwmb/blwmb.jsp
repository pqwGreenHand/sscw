<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="blwmb.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="certificategrid"></table>
	<div id="certificate_dialog" class="easyui-dialog"
		style="width: 500px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#enterprise-buttons">
		<div style="height: 150px;">
			<form id="certificate_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>问题类型:</label> <input name="type" id="save-type" class="easyui-combobox"
												required="true" style="width: 173px;"
												data-options="data:[{'id':0,'text':'公用模板'},{'id':1,'text':'个人模板'}],valueField:'id',textField:'text'"/><span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>文档:</label> <input id="word-name" readonly="readonly" onclick="doSelectWord()" class="easyui-validatebox"
											   required="true"  data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
					<input type="file" id="word-file" onchange="doWordChange(this)" name="word" style="display: none" />
				</div>
				<div class="fitem">
					<label>访问热度:</label> <input id="sort-no" name="sortNo" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
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
			<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="recordTemplateAdd" onclick="templateAdd()">添加</a>
		</div>
		<div>
			<form  id="left-search" style="float: left;margin: 3px;">
				模板名称: <input id="q_name" class="easyui-validatebox" style="width:200px">
				类型: <input id="query-type" class="easyui-combobox" data-options="data:[{'id':0,'text':'未选择'},{'id':2,'text':'公用模板'},{'id':1,'text':'个人模板'}],valueField:'id',textField:'text'" style="width:200px">
			</form>

				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				
			</div>
	</div>
</body>
</html>