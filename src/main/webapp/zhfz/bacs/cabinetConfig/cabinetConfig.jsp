<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>

<head>
	<%@ include file="../../common/common-head.jsp"%>
	<script type="text/javascript" src="cabinetConfig.js"></script>
	<script type="text/javascript" src="cabinetConfigDetail.js"></script>
</head>

<body class="easyui-layout" style="width: 100%; height: 100%;">
	<%@ include file="../../common/common-loading.jsp"%>
	<div data-options="region:'center',split:true" style="width: 69%; height: 95%;">
		<table id="cabinet_config_datagrid"></table>
	</div>
	<div data-options="region:'east',split:true"
		 style="width: 39%; height: 95%;">
		<table id="cabinet_config_detail_datagrid"></table>
	</div>
	
	<!-- toolbar -->
	<div id="cabinetConfigToolbar" style="padding:5px;height:auto">
		<div>
				<a id="cabinetConfigAdd" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="cabinetConfigAdd()">一键新增柜子</a>
				柜子名称: <input id="s_name" name="name" class="easyui-textbox" style="width:100px">
				柜子类型: <input id="s_type" name="type" class="easyui-combobox" data-options="url:'./combobox_data.json',valueField:'id',textField:'text'" editable="false">
				<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="cabinetSearch()">查询</a>
				<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清除</a>
		</div>
	</div>
	
	<div id="cabinetConfig_dialog" class="easyui-dialog"
		style="width: 500px; height: 470px; padding: 10px 20px" closed="true"
		buttons="#cabinetConfig-buttons">
		<div class="ftitle">柜子信息</div>
		<div style="height: 180px;">
			<form id="cabinetConfig_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>柜子名称:</label> <input id="name" name="name" class="easyui-validatebox"
						 data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
						 <span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>柜子类型:</label> <input id="type2" name="type" class="easyui-combobox" style="width: 173px"
					     data-options="required:true,url: './combobox_data.json',method: 'get',valueField: 'id',textField: 'text'" editable="false">
					     <span style="color:#F00"> * </span>
				</div>
				<div  class="fitem">
					<label>所属办案中心:</label> <input id="areaId" name="areaId" class="easyui-combobox" style="width: 173px"
					     data-options="required:true">
					     <span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>ip地址:</label> <input id="ip" name="ip" class="easyui-validatebox"
						 data-options="required:true,validType:'maxLength[256]',invalidMessage:'最长256字节'">
					<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>端口:</label> <input id="port" name="port" class="easyui-validatebox"
						 data-options="required:true,validType:'maxLength[32]',invalidMessage:'最长32字节'">
					<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>组号:</label> <input id="group" name="group" class="easyui-validatebox"
					     data-options="required:true,validType:'maxLength[16]',invalidMessage:'最长16字节'">
					<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>开柜IP:</label> <input id="openIp" name="openIp" class="easyui-validatebox"  data-options="required:true,validType:'maxLength[16]',invalidMessage:'最长16字节'" >
					<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>列数:</label> <input id="columnCount" name="columnCount" class="easyui-validatebox"  data-options="required:true,validType:'maxLength[16]',invalidMessage:'最长16字节'" >
					<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>行数:</label> <input id="rowCount" name="rowCount" class="easyui-validatebox"   data-options="required:true,validType:'maxLength[16]',invalidMessage:'最长16字节'">
					<span style="color:#F00"> * </span>
				</div>
				<!-- <div class="fitem">
					<label>排序方式:</label> <input id="orderType" name="orderType" class="easyui-validatebox"   data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'">
				</div> -->
				<div class="fitem" id ="div">
				    <label>排序方式:</label>
	        		<input type="radio" id="orderType" name="orderType"  data-options="required:true,validType:'length[0,128]',required:true" value="0">横向</input>
	        		<input type="radio" id="orderType" name="orderType"  data-options="required:true,validType:'length[0,128]',required:true" value="1">纵向</input>
	        	</div> 
				<!-- <div class="fitem">
					<label>是否启用:</label> 
					<select id="orgStatus" name="orgStatus" class="easyui-combobox" value="0" style="width: 100px;">
						<option value="0" selected="selected">否</option>   
						<option value="1">是</option>   
					</select>
				</div> -->
				<div class="fitem" id ="divml">
				    <label>命令编号:</label>
	        		<input type="radio"  id="mlNo" name="mlNo"  data-options= "required:true,validType:'length[0,128]',required:true" value="1">十进制</input>
	        		<input type="radio" id="mlNo" name="mlNo"  data-options="required:true,validType:'length[0,128]',required:true" value="0">十六进制</input>
	        	</div> 
			</form>
		</div>
		<div id="cabinetConfig-buttons">
			<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveCabinetConfig()">保存</a> <a name="noButton" href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#cabinetConfig_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
		<div id="cabinetConfig_detail_dialog" class="easyui-dialog"
		style="width: 500px; height: 350px; padding: 10px 20px" closed="true"
		buttons="#cabinetConfig-buttons1">
		<div class="ftitle">柜子信息</div>
		<div style="height: 150px;">
			<form id="cabinetConfigDetail_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id1" name="id" />
				<input type="hidden" id="id2" name="configId" />
				<div class="fitem">
					<label>柜子类型:</label> <input id="type1" name="type" class="easyui-combobox" 
					     data-options="required:true,url: './box_type.json',method: 'get',valueField: 'id',textField: 'text'" editable="false">
				</div>
			</form>
		</div>
		<div id="cabinetConfig-buttons1">
			<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveCabinetDetailConfig()">保存</a> <a name="noButton" href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#cabinetConfig_detail_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
</body>
</html>