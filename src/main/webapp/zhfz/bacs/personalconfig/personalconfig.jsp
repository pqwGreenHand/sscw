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
<script type="text/javascript" src="personalconfig.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<div data-options="region:'center',split:true"
		style="width: 49%; height: 95%;">

		<table id="personalConfiggrid"></table>

	</div>

	<div data-options="region:'east',split:true" style="width: 500px;"
		class="easyui-tabs">
		<div title="个性化配置详情">
			<table id="personalConfigDetailgrid"></table>
		</div>
	</div>

	<div id="personal_config_detail_dialog" class="easyui-dialog"
		style="width: 600px; height: 350px; padding: 10px 20px" closed="true"
		buttons="#organization-buttons">
			<div class="fitem">
					<label style="width: 150px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配置名称:</label> <input readonly="readonly" id="personalConfigName1" name="personalConfigName1"  class="easyui-validatebox" >
			</div>
		<div style="height: 150px;">
			<form id="personal_config_detail_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="personalConfigId" name="personalConfigId" />
				<input type="hidden" id="personal_config_detai_id" name="id" />

				<div class="fitem">
					<label>参数名:</label> <input id="paramKey" name="paramKey" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>参数值:</label>
					<textarea id="paramValue" name="paramValue" rows="5"
						  cols="18" class="easyui-validatebox" required="true" style="width: 163px"
						  data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea><span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>描述:</label> <input id="personal_config_detail_desc" name="desc" class="easyui-validatebox"
						 data-options="validType:'maxLength[64]',invalidMessage:'最长64字节'">
				</div>

			</form>
		</div>
		<div id="organization-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="savepersonalConfigDetail()">保存</a> <a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-cancel"
				onclick="javascript:$('#personal_config_detail_dialog').dialog('close')">关闭</a>
		</div>
	</div>

	<div id="personalConfig_dialog" class="easyui-dialog"
		style="width: 400px; height:300px; padding: 10px 20px" closed="true"
		buttons="#personalConfig-buttons">
		<div class="ftitle">新增个性化配置</div>
		<div style="height: 150px;">
			<form id="personalConfig_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<table>
						<tr>
							<td>配置名称:</td>
							<td> <input id="personalConfigtype" name="type" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span></td>
						</tr>
						<tr>
							<td>配置描述:</td>
							<td> <input id="desc" name="desc" class="easyui-validatebox"
										required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span></td>
						</tr>
						<tr  >
							<td >办案场所:</td>
							<td >
                				<select style="width: 173px;" id="AreaId"  name="areaId" class="easyui-combobox" />
               				</td>
						</tr>
						<tr  >
							<td >是否有效:</td>
							<td >
								<input type="checkbox" name="isEnable" id="isEnable"/>
               				</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<div id="personalConfig-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="savepersonalConfig()">保存</a> <a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-cancel"
				onclick="javascript:$('#personalConfig_dialog').dialog('close')">关闭</a>
		</div>
	</div>

	<!-- 区域初始化 -->
	<div id="areaInitConfig_dialog" class="easyui-dialog"
		style="width: 400px; height:200px; padding: 10px 20px" closed="true"
		buttons="#areaInitConfig-buttons">
		<div class="ftitle">区域初始化</div>
		<div style="height: 50px;">
			<form id="areaInitConfig_form" class="form-style" method="post"
				style="height: 50px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">

					<table>

						<tr>
							<td >办案场所:</td>
							<td >
                				<select style="width: 170px;" id="a_AreaId"  name="areaId" class="easyui-combobox" />
               				</td>
						</tr>

					</table>
				</div>
			</form>
		</div>
		<div id="areaInitConfig-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="areaInitConfigSave()">保存</a> <a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-cancel"
				onclick="javascript:$('#areaInitConfig_dialog').dialog('close')">关闭</a>
		</div>
	</div>

	<!-- toolbar -->
	<div id="personalConfigToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="personalConfigAdd" onclick="personalConfigAdd()">新增</a>

			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-add" plain="true" id="areaInit" onclick="areaInit()">区域初始化</a>
		</div>
		<div>
				配置名称:<input id="s_personalConfig_type" class="easyui-validatebox" style="width:100px" name="type">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="personalConfigSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
		</div>
	</div>

	<div id="personalConfigDetailToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="personalConfigDetailAdd" onclick="personalConfigDetailAdd(0)">新增详情</a>
		</div>
		<div>
				参数名:<input id="s_personalConfigDetail_key" class="easyui-validatebox" style="width:100px">
				参数值:<input id="s_personalConfigDetail_value" class="easyui-validatebox" style="width:100px">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="personalConfigDetailSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
		</div>
	</div>

</body>
</html>