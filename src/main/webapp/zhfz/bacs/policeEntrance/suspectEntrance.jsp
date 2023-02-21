<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<script type="text/javascript" src="policeEntrance.js"></script>
<script type="text/javascript" src="inPolice.js"></script>
<script type="text/javascript" src="outPolice.js"></script>
<script type="text/javascript" src="suspectEntrance.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<%@ include file="../../common/case/case.jsp"%>
<%@ include file="../../common/case/jzAjxx.jsp"%>
<div class="page locker-home" style="height: 100%">
	<div class="main" style="height: 25%;margin-top:-12px;">
		<div class="box">
			<a id="rqButton" class="item i1 m-box" name="noButton" onclick="entrance()">
				<span>民警入区</span>
				<div class="edges">
					<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
			<a id="cqButton" class="item i2 m-box" name="noButton" onclick="out()">
				<span>民警出区</span>
				<div class="edges">
					<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
			<a id="rqlsButton" name="noButton" class="item i3 m-box" href="suspectEntrance.jsp" style="display: none;">
				<span>登记历史</span>
				<div class="edges">
					<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
		</div>
	</div>
	<div style="height: 85%;margin-top: 150px;">
		<table id="datagrid"></table>
	</div>
	<!-- toolbar -->
	<div id="toolbar" style="height: auto;padding: 5px;">
		<div>
			姓名: <input id="s_name" class="easyui-validatebox" style="width: 85px;height:25px">
			警号:<input id="s_certificateNo" class="easyui-validatebox" style="width: 180px;height:25px">
			办案场所:<input type="text" id="s_areaId" name="areaId" class="easyui-combobox">
			入区时间从: <input id="s_start_date" class="easyui-datetimebox" style="width: 145px;height:30px">
			到: <input id="s_end_date" class="easyui-datetimebox" style="width: 145px;height:30px">
			<label>状态: </label>
			<select class="easyui-combobox" editable="false" id="s_status" name="s_status" style="width: 100px;">
						<option selected="selected" value="">全部</option>
						<option value="1">未出区</option>
						<option value="2">已出区</option>
			
            </select>
			
			<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a>
			<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清空</a>
			<%--<a href="policeEntrance.jsp" name="noButton" class="easyui-linkbutton" iconCls="icon-back">返回</a>--%>
		</div>
	</div>
</div>
	<div id="exit_dialog2" class="easyui-dialog"
		style="width: 550px; height:400px; padding: 10px 20px" closed="true">
		<div data-options="region:'center',split:true" style="width: 100%; height: 100%;">
			<table id="exitDatagrid2"></table>
		</div>
   </div>
<%@include file="inPoliceDialog.jsp" %>
<%@include file="outPoliceDialog.jsp" %>
<%@include file="addPoliceDialog.jsp" %>
</body>
</html>