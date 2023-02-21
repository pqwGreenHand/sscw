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
<script type="text/javascript" src="positionCardData.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<div data-options="region:'center',title:'',split:true">   
	   <table id="treegrid"> </table> 
       
	       <!-- toolbar -->
			<div id="toolbar" style="padding:5px;height:auto">
				<div>
					    人员名称: <input id="s_name" name="s_name" class="easyui-textbox" style="width:100px;height: 23px">&nbsp;&nbsp;
					    手环/卡片编号: <input id="s_cuff_no" name="s_cuff_no" class="easyui-textbox" style="width:100px;height: 23px">&nbsp;&nbsp;
					    所属案件名称<input id="s_case_name" name="s_case_name" class="easyui-textbox" style="width:100px;height: 23px">&nbsp;&nbsp;
						<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
						<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清除</a>
				</div>
			</div>
    </div>  
    
     
    <div data-options="region:'south',split:true" class="easyui-tabs" pill="true" style="height:40%;">
           <div title="定位轨迹" style="padding:5px">
			   <table id="positionData"></table>
		   </div>
		   
		   <div title="定位告警" style="padding:5px">
			   <table id="positionAlarm"></table>
		   </div>
		   <div title="定位进出" style="padding:5px">
			   <table id="positionEntrance"></table>
		   </div>
    </div>
	
</body>
</html>