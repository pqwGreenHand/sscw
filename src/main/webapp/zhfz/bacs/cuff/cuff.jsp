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
<script type="text/javascript" src="cuff.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugs/ajaxfileupload.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<div data-options="region:'center',split:true"
		style="width: 49%; height: 95%;">
		<table id="datagrid"></table>
	</div>
	<!-- 按钮 -->
	<!-- toolbar 	-->
	<div id="cuffToolbar" style="padding:5px;height:auto">

		<div style="margin-bottom: 5px">

		</div>
<!-- 条件查询-->
		<div>
			<a href="#"  class="easyui-linkbutton" iconCls="icon-add" plain="true"
			   id='cuffadd' onclick="cuffadd()">添加</a>
			<a href="#" name="noButton" id="refrshAuthority" class="easyui-linkbutton"
			   iconCls="icon-reload" plain="true" onclick="refrshCuff()">同步手环</a>
			<a href="#" id="userImportBtn" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="cuffImport()">标签导入</a>
			手环序列号: <input id="s_cuff_no" class="easyui-validatebox"
				style="width: 100px">
			手环类型:
			<select class="easyui-combobox" id="s_cuff_type" name="s_cuff_type"
					style="width: 100px;">
				<option value="" selected="selected">全部</option>
				<option value="0">手环</option>
				<option value="1">卡片</option>
			</select>
			手环状态:
				<select class="easyui-combobox" id="s_cuff_stuts" name="s_cuff_stuts"
						style="width: 100px;">
						<option value="" selected="selected">全部</option>
						<option value="0">空闲</option>
						<option value="1">已绑定</option>
						<option value="2">已损坏</option>
				</select>
				办案场所: <input
				id="s_cuff_interrogate_area_id" class="easyui-validatebox"
				style="width: 200px"> <a href="#" class="easyui-linkbutton"
				iconCls="icon-search" name="noButton" onclick="doSearch()">查询</a> <a
				href="#" class="easyui-linkbutton" iconCls="icon-clear" 
				onclick="doClear()" name="noButton">清除</a>
		</div>
	</div>

	<!-- 添加页面 -->
		<div id="data_dialog" class="easyui-dialog"
		style="width: 400px; height: 380px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">手环信息</div>
		<div style="height: 150px;">
			<form id="data_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem"><span style="display:inline-block;width:90px;">手环序列号:</span>
					<input id="cuffNo" name="cuffNo"
						class="easyui-validatebox" required="true"
						data-options="validType:'maxLength[20]',invalidMessage:'最长20字节'">
						<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:93px;">IC卡编号:</span><input id="icNo" name="icNo"
						 class="easyui-validatebox" required="true"
						 data-options="validType:'maxLength[10]',invalidMessage:'最长10字节'">
						 <span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">手环类型:</span>
					<select class="easyui-combobox" id="type" name="type" style="width: 173px;">
						<option value="0">手环</option>
						<option value="1">卡片</option>
					</select>
					<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
				<span style="display:inline-block;width:90px;">手环状态:</span>
					<select class="easyui-combobox" id="status" name="status" style="width: 173px;">
						<option value="0">空闲</option>
						<option value="1">已绑定</option>
						<option value="2">已损坏</option>
					</select>
					<span style="color:#F00"> * </span>
				</div>

				<div class="fitem">
					 <span style="display:inline-block;width:90px;">办案场所:</span>
					<input
						id="interrogateAreaId" name="interrogateAreaId"  value="interrogateAreaId" class="easyui-validatebox"
						 style="width: 173px">
						 <span style="color:#F00"> * </span>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveData()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#data_dialog').dialog('close')">关闭</a>
		</div>
	</div>

<div id="import_dialog" class="easyui-dialog"
	 style="width: 700px; height: 400px; padding: 10px 20px" closed="true" buttons="#import-buttons">
	<form id="import_form" class="form-style"
		  method="post" enctype="multipart/form-data" target="importFrame">

		<div class="fitem">
			<label>导入文件:<font color="red">*</font></label>
			<input class='btn' value='浏览'
				   style="border: 1px solid #c2c0c0; width: 200px;" type="file" name="file"
				   id="file"  />

		</div>
		<div class="fitem">
			<label>提示:<font color="red">*</font></label>
			<span style="color: red;font-size: 16px">*Excel单元格格式为全文本*</span>

		</div>


		</table>

	</form>
	<div id="import-buttons">
		<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-ok"
		   onclick="doImport()">导入</a>
		<a href="#" class="easyui-linkbutton" name="noButton"
		   iconCls="icon-cancel"
		   onclick="javascript:$('#import_dialog').dialog('close')">取消</a>
	</div>
</div>
	
</body>
</html>