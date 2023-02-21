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
<%@ include file="../common-head.jsp"%>
<script type="text/javascript" src="authorityData.js"></script>
<script type="text/javascript" src="authority.js"></script>

</head>
<body>
<%@ include file="../common-loading.jsp"%>
		<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom:5px">
			<a href="#" id="addAuthority" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addAuthority()">新增</a>
			<a href="#" id="editAuthority" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAuthority()">修改</a>
			<a href="#" id="removeAuthority" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeAuthority()">删除</a>
			<a href="#" id="rAuthority" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshAuthority()" name="noButton">刷新</a>
			<a href="#" id="openAllAuthority" class="easyui-linkbutton" iconCls="icon-openall" plain="true" onclick="openAllAuthority()" name="noButton">打开所有</a>
			<a href="#" id="closeAllAuthority" class="easyui-linkbutton" iconCls="icon-closeall" plain="true" onclick="closeAllAuthority()" name="noButton">关闭所有</a>
		</div>
		</div>
	<!-- 数据显示 s -->
	<table id="authorityTreegrid" class="easyui-treegrid"></table>
	<!-- 数据显示 e -->
	<!-- 数据添加 s -->
	<div id="data_dialog" class="easyui-dialog"
		style="width: 700px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">权限管理</div>
		<div style="height: 260px;width: 600px">
			<form id="data_form" class="form-style" method="post"
				style="height: 250px;">
				<input type="hidden" id="id" name="id" /> <input type="hidden"
					id="rootId" name="rootId" />

				<table id="tb" pagination="true" fitColumns="true"
					singleSelect="true" width="100%" >
					<tr>
						<td width="50%">
							<div class="fitem">
									<label><font id="font_authorityId">权限ID:</font></label> <input id="authorityId" name="authorityId"
										 class="easyui-validatebox"  style="width: 175px"
										data-options="validType:'maxLength[255]'"><span style="color: red">*</span></span>
							</div>
						</td>
						<td>
							<div class="fitem">
									<label><font id="font_title">标题:</font></label> <input id="title" name="title"
										class="easyui-validatebox" style="width: 175px"
										data-options="validType:'maxLength[255]'"><span style="color: red">*</span></span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="fitem">
								<label><font id="font_type">类型:</font></label> <input id="type" name="type" style="width: 185px" class="easyui-combobox" onSelect="changeType(this);">
							</div>
						</td>
						<td><div class="fitem">
								<label><font id="font_parentId">父节点:</font></label> <input id="parentId" name="parentId" style="width: 185px"
									class="easyui-combotree">
							</div></td>
					</tr>
					<tr>
						<td>
							<div class="fitem">
								<label><font id="font_sortNumber">排序:</font></label> <input id="sortNumber" name="sortNumber" class="easyui-validatebox"
																							style="width: 175px" data-options="validType:'maxLength[8]'">
							</div>
						</td>
						<td><div class="fitem">
								<label><font id="font_url">URL:</font></label> <input type="text" id="url" name="url" class="easyui-validatebox" required="true"
									style="width: 175px"></input><span style="color: red">*</span></span>
							</div></td>
					</tr>

					<tr>
						<td>
						<div class="fitem" style="height: 80px;">
								<label><font id="font_icon">图标:</font></label> <input id="icon" name="icon" style="width: 175px"
									class="easyui-validatebox">
								</textarea>
							</div>
						</td>
						<td>
							<div class="fitem" style="height: 80px;">
								<label><font id="font_description">描述:</font></label>
								<textarea id="description" name="description" cols=30 rows=3 class="easyui-validatebox" style="width: 175px"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td>
						<div class="fitem" style="height: 80px;">
								<label><font id="font_icon">大图标:</font></label> <input id="bigIcon" name="bigIcon" style="width: 175px"
									class="easyui-validatebox" />
							</div>
						</td>
						<td>
							<div class="fitem" style="height: 80px;">
								<label><font id="font_icon">小图标:</font></label> <input id="smallIcon" name="smallIcon" style="width: 175px"
									class="easyui-validatebox" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
						<div class="fitem" style="height: 80px;" id="hidden1">
								<label><font id="font_jsMethod">配置:</font></label>
								<input  type="text" id="jsMethod" name="jsMethod" style="width: 175px"></input>
						</div>
							
						<div class="fitem" style="height: 80px;" id="hidden2">
								<label><font id="font_son">子项:</font></label>
								<input type="radio" name="jsMethod" value="menu" id="jsMethod3">菜单
								<input type="radio" name="jsMethod" value="img" id="jsMethod4">图标
						</div>
					
						</td>
						<td>	<div class="fitem" style="height: 80px;"id="hidden3">
								<label><font id="font_jsMethod1">按钮标识:</font></label>
								<input type="text" name="jsMethod1" id="jsMethod1">
						</div>
								
						</td>
					</tr>
				</table>

			</form>
		</div>
		<div id="dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveData()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#data_dialog').dialog('close')" name="noButton">关闭</a>
		</div>
	</div>

	<!-- 数据添加 e -->
</body>
</html>