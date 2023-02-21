<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="alarm.js"></script>
	<style type="text/css">
		*{
			padding: 0;
			margin: 0;
		}
		.menu{
			border: 0px;
			background: none;
			margin-top: 30px;
			margin-left: 300px;

		}
		.menu li{
			float: left;
			margin-left: 25px;
			height: 150px;
			width: 150px;
			border: #008cd5 solid 3px;
			text-align: center;
			cursor: pointer;
			background-color: rgba(37,73,136,.6);
			border: 1px solid #064d79;
			background-image: url(image/locker-1.png);
			background-size: 100% 100%;
		}
		.menu li:hover{
			background-image: url(image/locker-16.png);
			background-size: 100% 100%;
		}
		.menu li.click{
			background-image: url(image/locker-16.png);
			background-size: 100% 100%;
		}
		.menu li img{
			background: none;
			margin-left: 25%;
			margin-top: 25px;
			float: left;
		}
		.menu li .number{
			margin-top: 15px;
			margin-left: 25px;
			font-size: 20px;
			color: red;
			height:40%;
		}
		.menu li .name{
			width: 100%;
			margin-top:25px;
			font-size: 15px;
			font-family: "微软雅黑";
			color: yellow;
		}
	</style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
	<%@ include file="../../common/common-loading.jsp"%>
	<div style="height: 30%">
		<ul class="menu" id="alarmTypeS">

		</ul>
	</div>
	<div style="height: 70%">
		<table id="datagrid"></table>
		<!-- toolbar -->
		<div id="toolbar" style="height: auto;padding: 5px;">
			<div>
				姓名: <input id="s_name" class="easyui-validatebox" style="width: 150px;height:25px">
				告警名称: <input id="s_alarm_name" class="easyui-validatebox" style="width: 150px;height:25px">
				告警类型:<input id="s_alarm_type" class="easyui-combobox" style="width:150px" />
				告警时间从: <input id="s_start_date" class="easyui-datetimebox" style="width: 145px;height:30px">
				到: <input id="s_end_date" class="easyui-datetimebox" style="width: 145px;height:30px">
				办案场所:<input type="text" id="s_areaId" name="areaId" class="easyui-combobox">
				<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a>
				<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清空</a>
			</div>
		</div>
	</div>
	<div id="exit_dialog2" class="easyui-dialog" style="width: 550px; height:400px; padding: 10px 20px" closed="true">
		<div data-options="region:'center',split:true" style="width: 100%; height: 100%;">
			<table id="exitDatagrid2"></table>
		</div>
   </div>
</body>
</html>