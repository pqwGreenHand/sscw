<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<link rel="stylesheet" type="text/css" href="init.css" >
<script type="text/javascript" src="init.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<div id="left">
	<table id="left-certificategrid"></table>

	<!-- toolbar -->
	<div id="left-certificateToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="question-add" onclick="doAdd('新增问题','question/add.do','#left-certificate-form',0)">添加</a>
		</div>
		<div>
            <form  id="left-search" style="float: left;margin: 3px;">
                内容: <input name="content" class="easyui-validatebox" style="width:200px">
                类型: <input name="typeId" class="easyui-combobox" id="query-combobox" style="width:200px">
            </form>
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch('#left-certificategrid','#left-search')">查询</a>
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear('#left-search')">清除</a>

		</div>
	</div>
</div>
<div id="right">
	<table id="right-certificategrid"></table>
	

	<!-- toolbar -->
	<div id="right-certificateToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="answer-add" onclick="doAdd('新增答案','answer/add.do','#right-certificate-form',1)">添加</a>
		</div>
		<div>
			<form  id="right-search" style="float: left;margin: 3px;">
				内容: <input name="content" class="easyui-validatebox" style="width:200px">
			</form>
			<a href="#" id="answer-search-btn" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch('#right-certificategrid','#right-search')">查询</a>
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear('#right-search')">清除</a>

		</div>
	</div>
</div>

<div id="certificate-dialog" class="easyui-dialog"
	 style="width: 550px; height: 300px; padding: 10px 20px" closed="true"
	 buttons="#enterprise-buttons">
	<div style="height: 250px;">
		<form id="left-certificate-form" class="form-style" method="post"
			  style="height: 150px;display: none;">
			<input type="hidden" name="id"/>
			<div class="fitem">
				<label>问题类型:</label> <input name="typeId" class="easyui-combobox"
										  required="true" style="width: 173px;" id="save-combobox"  /><span style="color:#F00"> * </span>
			</div>
			<div class="fitem">
				<label>问题内容:</label> <textarea rows=5 name="content" class="textarea easyui-validatebox" style="width: 275px;height: 52px;" required="true"
                                               data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"></textarea><span style="color:#F00"> * </span>
			</div>
			<div class="fitem">
				<label>访问热度:</label> <input name="sortNo" class="easyui-validatebox"
											required="true"
											data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
			</div>
            <div class="fitem">
				<label>是否重点:</label> <input name="isMajor" class="easyui-validatebox"
											required="true"
											data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'" ><span style="color:#F00"> * </span>
											<span style="color:#F00"> 0为是，1为否</span>
			</div>
		</form>
		<form id="right-certificate-form" class="form-style" method="post"
			  style="height: 150px;">
			<input type="hidden" id="id" name="id" />
			<div class="fitem">
				<label>答案内容:</label> <textarea rows=5 name="content" class="textarea easyui-validatebox"  required="true" style="width: 275px;height: 52px;"></textarea><span style="color:#F00"> * </span>
				<%--<input  class="easyui-textbox"  style="height: 100px;"--%>
										  <%--required="true"  data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">--%>
			</div>
			<div class="fitem">
				<label>访问热度:</label> <input id="sort-no" name="sortNo" class="easyui-validatebox"
											required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
			</div>
		</form>
	</div>
	<div id="enterprise-buttons">
		<a href="#" class="easyui-linkbutton" id="save-btn" iconCls="icon-ok" name="noButton" data-type="0" data-url=""
		   onclick="saveEnterprise(this)">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
		   onclick="javascript:$('#certificate-dialog').dialog('close')">关闭</a>
	</div>
</div>
</body>
</html>