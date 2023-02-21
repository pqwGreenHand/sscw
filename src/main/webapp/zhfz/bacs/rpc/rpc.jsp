<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="rpc.js"></script>
	<style>
		.jzw-photo{
			width: 60px;
			height: 60px;
			margin: 0 auto;
		}
	</style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="certificategrid"></table>
	<!-- toolbar -->
	<div id="certificateToolbar" style="padding:5px;height:auto;overflow: hidden;">
		<div>
			<form  id="search" style="float: left;margin: 3px;">
				<div style="width: 100%;margin-bottom: 10px;">
					人员名字: <input name="name" class="easyui-validatebox" style="width:200px" />
					证件号码: <input name	="certificateNo" class="easyui-validatebox" style="width:200px" />
					户籍地址: <input name="censusRegister" class="easyui-validatebox" style="width:200px" />
					性别: <input name="sex" class="easyui-combobox"  style="width:88px" data-options="data:code.sex,valueField:'keyValue',textField:'keyName'" />
					年龄: <input name="age" class="easyui-validatebox" style="width:78px" />
					关联: <input name="" class="easyui-validatebox" style="width:78px" />
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				</div>
				<div style="width: 100%;">
					体<span style="opacity: 0">占位</span>型: <input name="shape" class="easyui-combobox" style="width:200px" data-options="data:code.shape,valueField:'keyName',textField:'keyName'" />
					发<span style="opacity: 0">占位</span>长: <input name="hairLength" class="easyui-combobox" style="width:78px" data-options="data:code.hairLength,valueField:'keyName',textField:'keyName'" />
					发色: <input name="hairColor" class="easyui-combobox" style="width:78px" data-options="data:code.hairColor,valueField:'keyName',textField:'keyName'"/>
					衣<span style="opacity: 0">占位</span>着: <input name="clothing" class="easyui-combobox" style="width:78px" data-options="data:code.clothing,valueField:'keyName',textField:'keyName'" />
					肤色: <input name="skin" class="easyui-combobox" style="width:78px" data-options="data:code.skin,valueField:'keyName',textField:'keyName'"/>
					脸型: <input name="faceStyle" class="easyui-combobox" style="width:78px" data-options="data:code.faceStyle,valueField:'keyName',textField:'keyName'" />
					包扎: <input class="easyui-combobox" style="width:200px" data-options="data:code.bindUp,valueField:'keyName',textField:'keyName'"/>
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSave()">保存</a>
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="$('#logoFile').trigger('click');">上传图片</a>
				</div>
				<input id="id" class="easyui-validatebox" name="id"
					   data-options="" style="width: 120px;display: none">
				<input id="uuid" class="easyui-validatebox" name="uuid"
					   data-options="" style="width: 120px;display: none">
				<input id="rid" class="easyui-validatebox" name="rid"
					   data-options="" style="width: 120px;display: none">
				<input type="file" style="display:none;" name="file" id="logoFile"
					   class="selectedLogoImgId"
					   onchange="setImgs(this)" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple/>

			</form>
		</div>
	</div>
</body>
</html>