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
<script type="text/javascript" src="commonConfig.js"></script>
<script type="text/javascript" src="commonConfigDetail.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../common-loading.jsp"%>
	<div data-options="region:'center',split:true"
		style="width: 49%; height: 95%;">
		<table id="commonConfiggrid"></table>
	</div>
	
	<div data-options="region:'east',split:true" style="width: 550px;">
			<table id="commonConfigDetailgrid" ></table>
	</div>

	<div id="common_config_detail_dialog" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 10px 20px" closed="true"
		buttons="#organization-buttons">
		<div class="ftitle">新增配置详细信息</div>
				<div class="fitem">
					<label style="margin-left: 19px;">配置类型:</label> 
					<input style="margin-left: -62px;" onfocus="bukexigai()"  readonly="readonly" id="commConfigName1" name="commConfigName1"  class="easyui-validatebox" >
				</div>
		<div style="height: 150px;">
			<form id="common_config_detail_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="commonConfigId" name="commonConfigId" />
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>参数名:</label> <input id="paramKey" name="paramKey" class="easyui-validatebox" style="margin-left: 51px;margin-top: -17px;"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label style="margin-top: 6px;">参数值:</label> 
					<input id="paramValue" name="paramValue" class="easyui-validatebox" style="margin-left: 51px;margin-top: -17px;"
						required="true" data-options="validType:'maxLength[64]',invalidMessage:'最长64字节'"><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label style="margin-top: 6px;">描述:</label> 
					<input id="desc2" name="desc" class="easyui-validatebox" style="margin-left: 51px;margin-top: -17px;"
						required="true" data-options="validType:'maxLength[64]',invalidMessage:'最长64字节'"><span style=";color: red;">*</span>
				</div>				
			</form>
		</div>
		<div id="organization-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveCommonConfigDetail()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#common_config_detail_dialog').dialog('close')">关闭</a>
		</div>
	</div>

	<div id="commonConfig_dialog" class="easyui-dialog" style="width: 400px; height: 350px; padding: 10px 20px" closed="true"
		buttons="#commonConfig-buttons">
		<div class="ftitle">通用配置信息</div>
		<div style="height: 150px;">
			<form id="commonConfig_form" class="form-style" method="post" style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>配置类型:</label> 
					<input id="type" name="type" class="easyui-validatebox" style="margin-left: 59px;margin-top: -18px;" required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>配置描述:</label> 
					<input id="desc" name="desc" class="easyui-validatebox" style="margin-left: 59px;margin-top: -18px;" required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style=";color: red;">*</span>
				</div>
				<div class="fitem">
					<label>是否启用:</label>					
					<input type="radio" value='1' name="isEnable" id="isEnable1" style="margin-left: -56px;margin-top: 6px;"/>是 &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value='0' name="isEnable" id="isEnable2"/>否 		<span style=";color: red;">*</span>
				</div>
			</form>
		</div>
		<div id="commonConfig-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveCommonConfig()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#commonConfig_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
	<!-- toolbar -->
	<div id="commonConfigToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="commonConfigAdd" onclick="commonConfigAdd()">新增</a>
		</div>
		<div>
				配置类型:<input id="type" class="easyui-validatebox" style="width:100px">
				配置描述:<input id="desc" class="easyui-validatebox" style="width:100px">
				是否启用: <select id="isEnable" name="isEnable" class="easyui-combobox" style="width:80px"/>
						 <option value=""></option>
						 <option value="0">否</option>
                         <option value="1">是</option>                         
                        </select>
				<!-- <input id="isEnable" class="easyui-validatebox" style="width:80px"> -->
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="commonConfigSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear1()">清除</a>
		</div>
	</div>
	
	<div id="commonConfigDetailToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="commonConfigDetailAdd" onclick="commonConfigDetailAdd()">新增</a>
		</div>
		<div>
				参数名: <input id="s_commonConfigDetail_key" class="easyui-validatebox" style="width:100px">
				参数值: <input id="s_commonConfigDetail_value" class="easyui-validatebox" style="width:100px">
				<!-- 描述: <input id="s_desc" class="easyui-validatebox" style="width:100px"> -->
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="commonConfigDetailSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
		</div>
	</div>
</body>
</html>