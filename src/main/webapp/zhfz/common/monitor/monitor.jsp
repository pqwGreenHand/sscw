<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用监控</title>

<%@ include file="../common-head.jsp"%>
<script type="text/javascript" src="monitor.js"></script>
</head>

<body class="easyui-layout" style="width: 100%; height: 100%;">

   <table id="datagrid" ></table>
   
   	<!-- toolbar -->
	<div id="toolbar" style="padding:0px;height:auto">
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="addMonitor()">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="doAllCheck()">全部检查</a>
			<%--<label>主机名称:</label>
			<input id="host_name" class="easyui-textbox" style="width: 100px">
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">搜索</a>
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>--%>
		</div>
	</div>

   <div id="monitor_dialog" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 5px 5px" closed="true" buttons="#dlg-buttons">
	   <!-- <div class="ftitle" align="center">用户信息</div> -->
	   <form id="monitor_form" class="form-style" method="post" style="" >
		   <input type="hidden"id="id" name="id" />
		   <div class="fitem">
			   <label>主机名称:</label>
			   <input id="hostname" name="hostname" class="easyui-validatebox" data-options="validType:'maxLength[256]'">
		   </div>
		   <div class="fitem">
			   <label>IP:</label>
			   <input id="ip" name="ip" class="easyui-validatebox" data-options="validType:'maxLength[256]'">
		   </div>
		   <div class="fitem">
			   <label>应用名称:</label>
			   <input id="appname" name="appname" class="easyui-validatebox"   data-options="validType:'maxLength[256]'">
		   </div>
		   <div class="fitem">
			   <label>监控类型:</label>
			   <input id="monitorType" name="monitorType" class="easyui-validatebox"   data-options="validType:'maxLength[256]'">
		   </div>
		   <div class="fitem">
			   <label>监控方式:</label>
			   <input id="monitorWay" name="monitorWay" class="easyui-validatebox"  data-options="validType:'maxLength[256]'">
		   </div>
	   </form>
	   <div id="dlg-buttons">
		   <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-ok"
			  onclick="saveData()">保存</a>
		   <a href="#" class="easyui-linkbutton" name="noButton"
			  iconCls="icon-cancel"
			  onclick="javascript:$('#monitor_dialog').dialog('close')">取消</a>
	   </div>
   </div>
</body>

</html>  