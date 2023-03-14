<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<%@ include file="../../zhfz/common/common-head.jsp"%>
<script type="text/javascript" src="organization.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugs/ajaxfileupload.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height:100%;">
<%@ include file="../../zhfz/common/common-loading.jsp"%>
	<div data-options="region:'west',split:true" style="width:70%">
		<table id="datagrid"></table>
	</div>
	
	
	<div data-options="region:'east',split:true" style="width:30%">
	    <table id="cdatagird"> </table>
	</div>
	

	<div id="organization_dialog" class="easyui-dialog"
		style="width: 500px; height: 450px; padding: 10px 20px" closed="true"
		buttons="#organization-buttons">
		<div class="ftitle">部门信息</div>
		<div style="height: 180px;">
			<form id="organization_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>部门名称:</label> <input id="name" name="name" class="easyui-validatebox"
						 data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
						 <span style="color:#F00"> *</span>
				</div>
				<div class="fitem">
					<label>部门类型:</label> <input id="type" name="type" class="easyui-combobox"  style="width:47%"
					     data-options="required:true,url: './combobox_data.json',method: 'get',valueField: 'id',textField: 'text'" editable="false">
					     <span style="color:#F00"> *</span>
				</div>
				<div id="UpOrgSelect" class="fitem" style="display:block;">
					<label>上级部门:</label> <input id="parentId" name="parentId" class="easyui-combobox" style="width:47%"
					     data-options="required:true">
					     <span style="color:#F00"> *</span>
				</div>
				<div class="fitem">
					<label>部门地址:</label> <input id="address" name="address" class="easyui-validatebox"
						 data-options="required:false,validType:'maxLength[256]',invalidMessage:'最长256字节'">
				</div>
				<div class="fitem">
					<label>电话:</label> <input id="telephone" name="telephone" class="easyui-validatebox"
						 data-options="required:false,validType:'maxLength[32]',invalidMessage:'最长32字节'">
				</div>
				<div class="fitem">
					<label>邮编:</label> <input id="postcode" name="postcode" class="easyui-validatebox"
					     data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'">
				</div>
				<div class="fitem">
					<label>机构代码:</label> <input id="orgCode" name="orgCode" class="easyui-validatebox"  data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'" >
				</div>
				<div class="fitem">
					<label>机构简称:</label> <input id="orgAlias" name="orgAlias" class="easyui-validatebox"  data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'" >
				</div>
				<div class="fitem">
					<label>机关代字:</label> <input id="orgRep" name="orgRep" class="easyui-validatebox"   data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'">
				</div>
				<div class="fitem">
					<label>是否启用:</label> 
					<select id="orgStatus" name="orgStatus" class="easyui-combobox" value="0"  data-options="required:true" style="width: 172px;">
						<option value="0" selected="selected">否</option>   
						<option value="1">是</option>   
					</select>
					<span style="color:#F00"> *</span>
				</div>
			</form>
		</div>
		<div id="organization-buttons">
			<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveOrganization()">保存</a> <a name="noButton" href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#organization_dialog').dialog('close')">关闭</a>
		</div>
	</div>

	<!-- toolbar -->
	<div id="organizationToolbar" style="padding:5px;height:auto">
		<div>
				<a id="organizationAdd" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="organizationAdd()">新增</a>
				<a href="#" id="userImportBtn" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="userImport()">民警部门导入</a>
				部门名称: <input id="s_organization" name="name" class="easyui-textbox" style="width:100px">
				部门类型: <input id="s_type" name="type" class="easyui-combobox" data-options="url:'./combobox_data.json',valueField:'id',textField:'text'" editable="false">
				<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="organizationgSearch()">查询</a>
				<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清除</a>
		</div>

	</div>
	<div id="import_dialog" class="easyui-dialog"
		style="width: 700px; height: 400px; padding: 10px 20px" closed="true" buttons="#import-buttons">
		<form id="import_form" class="form-style" 
		method="post" enctype="multipart/form-data" target="importFrame">

					<div class="fitem">
						<label>上传文件:<font color="red">*</font></label> 
						<input class='btn' value='浏览' 
						style="border: 1px solid #c2c0c0; width: 200px;" type="file" name="file"   class="easyui-textbox"
						id="file"  />
				    	  
					</div>


        	</table>
			
		</form>
		<div id="import-buttons">
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-ok"
				onclick="doImport()">上传</a>
			<a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-cancel"
				onclick="javascript:$('#import_dialog').dialog('close')">取消</a>
		</div>
	</div>	

	
</body>
</html>