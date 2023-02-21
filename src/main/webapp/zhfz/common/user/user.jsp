<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<%@ include file="../common-head.jsp"%>
<script type="text/javascript" src="user.js"></script>
<script type="text/javascript" src="user_role.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height:100%;">
<%@ include file="../common-loading.jsp"%>
<!-- 用户信息表  -->
    <div data-options="region:'center',split:true">
		<table id="user_datagrid"></table>
	</div>
		<!-- 用户信息表  -->
    <div data-options="region:'center',split:true">
		<table id="user_datagrid"></table>
	</div>
	<!-- toolbar -->
	<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom:5px">
			<a href="#" id="addUser" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()">添加</a>
			<a href="#" id="resetPswBtn" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="resetPsw()">重置密码</a>
			<a href="#" name="noButton" id="refrshAuthority" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refrshAuthority()">刷新权限</a>
			<!-- <a href="#" id="userImportBtn" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="userImport()">民警导入</a> -->
		</div>
		<div>
			<label>登录名:</label> 
			<input id="s_login_name" class="easyui-textbox" style="width: 80px">
			<label>真实姓名:</label> 
			<input id="s_real_name" class="easyui-textbox" style="width: 80px">
			<label>角色:</label> 
			<input id="role_id" name="role" class="easyui-combobox" style="width: 120px">
			<label>证件类型:</label> 
			<input id="s_certificate_type_id" class="easyui-combobox" 
			data-options="editable:true,panelHeight:'auto'" style="width: 80px">
			<label>证件号码:</label> 
			<input id="s_certificate_no" class="easyui-textbox" style="width: 80px">
			<label>单位:</label> 
			<input id="s_organization_name" class="easyui-textbox"
			data-options="editable:true,panelHeight:'auto'" style="width: 80px">
			<label>手机号码:</label> 
			<input id="s_mobile" class="easyui-textbox" style="width: 80px">
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">搜索</a> 
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>
		</div>
	</div>

	    <div id="roletoolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom:5px">
			<a href="#" id="saveUserRole" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveUserRole()">保存</a>
			<a href="#" id="roleCancle" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="roleCancle()">取消</a>
		</div>
		</div>	
	<div data-options="region:'east',split:true" style="width:13%;">
			<table id="role_datagrid"></table>
		</div>
		
		<div id="user_dialog" class="easyui-dialog"
		style="width: 800px; height: 440px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<!-- <div class="ftitle" align="center">用户信息</div> -->
		<form id="user_form" class="form-style" method="post" style="" >
			<table id="user_tab" pagination="true" fitColumns="true" singleSelect="true" width="100%" height="100%">
				<tr>
					<td colspan="2" align="left">
						<input type="hidden"id="id" name="id" />
					</td>
					
				</tr>
				<thead align="left">
			<tr>
				<td align="left">
					<div class="fitem">
						<label>登录名:</label>
						<input id="loginName" name="loginName" class="easyui-validatebox" required="true" sty
						data-options="validType:'maxLength[128]'">
						<font color="red">*</font>
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<label>真实姓名:</label>
						<input id="realName" name="realName" class="easyui-validatebox" required="true"
						data-options="validType:'maxLength[128]'">
						<font color="red">*</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<div class="fitem">
						<label>密码:</label>
						<input id="password" name="password" class="easyui-validatebox" type="password" required="true"
						data-options="validType:'maxLength[256]'">
						<font color="red">*</font>
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<label>性别:</label>
						<select id="sex" name="sex" class="easyui-combobox" required="true" style="width: 173px">
							<option value="1">男</option>
							<option value="2">女</option>
							<option value="0">未知的性别</option>
							<option value="9">未说明的性别</option>
						</select>
						<font color="red">*</font>
					</div>
				</td>
			</tr>

			<tr>
				<td align="left">
					<div class="fitem">
						<label>职务:</label>
						<input id="jobTitle" name="jobTitle" class="easyui-validatebox" required="true"
						data-options="validType:'maxLength[128]'">
						<font color="red">*</font>
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<label>部门:</label>
					 <select id="organizationId" name="organizationId" style="width: 173px" class="easyui-combobox"  data-options="required:true,editable:true">
					 </select>
						<font color="red">*</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<div class="fitem">
						<label>证件类型:</label>
						<select id="certificateTypeId" name="certificateTypeId" style="width: 173px"
						data-options="required:true,editable:true"
						class="easyui-combobox" required="true" >
							<!-- <option value="0">警官证</option>
                			<option value="1">身份证</option>
                			<option value="2">护照</option>
                			<option value="3">港澳通行证</option> -->
						</select>
						<font color="red">*</font>
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<label>证件号:</label>
						<input id="certificateNo" name="certificateNo" class="easyui-validatebox" required="true"
						data-options="validType:'maxLength[64]'">
						<font color="red">*</font>
					</div>
				</td>
			</tr>
			<!-- <tr>
				<td align="left">
					<div class="fitem">
						<label>角色:</label>
						<select id="role" name="role" class="easyui-combobox" style="width: 170px" required="true">
						</select>
					</div>
				</td>
			</tr> -->

			<tr>
				<td align="left">
					<div class="fitem">
						<label>手机号:</label>
						<input id="mobile" name="mobile" class="easyui-validatebox"
						data-options="validType:'maxLength[32]'">
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<div class="fitem">
						<label>角色:</label>
						<select id="roleId" name="roleId" class="easyui-combobox" style="width: 173px" required="true">
						</select>
							<font color="red">*</font>
					</div>
					</div>
				</td>
			</tr>

			<tr>
				<td align="left">
					<div class="fitem">
						<label>邮箱:</label>
						<input id="email" name="email" class="easyui-validatebox"
						data-options="validType:'maxLength[64]'">
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<label>地址:</label>
						<input id="address" name="address" class="easyui-validatebox"
						data-options="validType:'maxLength[256]'">
					</div>
				</td>
			</tr>

			<tr>
			    <td align="left">
					<div class="fitem">
						<label>是否激活:</label>
						<input id="isActive" name="isActive" type="checkbox" checked="checked" required="true">
						<font color="red">*</font>
					</div>
				</td>
				<td align="left">
					<div class="fitem">
						<label>类型:</label>
						<select id="type" name="type" style="width: 173px"
						data-options="required:true,editable:false,panelHeight:'auto'"
						class="easyui-combobox" required="true"  >
							<option value="0">本所民警</option>
                			<option value="1">本所辅警</option>
                			<option value="2">他所民警</option>
						</select>
						<font color="red">*</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<div class="fitem">
						<label>描述:</label> 
						<textarea id="description" name="description"   class="easyui-validatebox" 
							style="width: 199px; height: 60px;" data-options="validType:'maxLength[512]' "></textarea>
					</div>
				</td>
			</tr>
			</thead>
        	</table>
			
		</form>
		<div id="dlg-buttons">
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-ok"
				onclick="saveData()">保存</a>
			<a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-cancel"
				onclick="javascript:$('#user_dialog').dialog('close')">取消</a>
		</div>
	</div>
		
	
</body>
</html>