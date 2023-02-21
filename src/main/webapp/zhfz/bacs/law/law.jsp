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
<script type="text/javascript" src="law.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	
	<table id="lawgrid"></table>

	<div id="law_dialog" class="easyui-dialog"
		style="width: 600px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#enterprise-buttons">
		
		<div style="height: 400px;">
			<form id="law_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>法律分类:</label> 
					<select style="width:250px" editable="true" id="type_cmb_id" required="true"
							name="type" class="easyui-combobox">
				 	</select>
				 	<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>法律名称:</label> 
					<select style="width:250px" editable="true" id="name_cmb_id" required="true"
							name="name" class="easyui-combobox">
				 	</select>
				 	<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>章节:</label> 
						<input id="law_chapter" name="chapter" class="easyui-validatebox" style="width:237px"
						required="true"/>
						<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<label>具体内容:<br/><font color="red">用br来换行</font></label> 
						 <input id="law_content" name="content" class="easyui-textbox" required="true"
						data-options="multiline:true" style="width:380px;height:280px"/>
						<span style="color:#F00"> * </span>
				</div>
			</form>
		</div>
		<div id="enterprise-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#law_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
	<!-- toolbar -->
	<div id="lawToolbar" style="padding:5px;height:auto">
		<div>
			<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="lawAdd" onclick="lawAdd()">添加</a>
			法律分类: <select style="width:200px" editable="true" id="type_cmb"
							name="type" class="easyui-combobox">
				 	   </select>&nbsp;&nbsp;&nbsp;&nbsp;
				法律名称: <select style="width: 200px;" editable="true" id="name_cmb"  
							name="name" class="easyui-combobox">
					    </select>&nbsp;&nbsp;&nbsp;&nbsp;
				关键字: <input id="s_content" name="content" class="easyui-textbox" style="width:120px">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				
			</div>
	</div>
</body>
</html>