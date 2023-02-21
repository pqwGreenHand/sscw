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
<script type="text/javascript" src="burnDvdLog.js"></script>
</head>    
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<!-- 左侧树结构 -->
	<div data-options="region:'west',title:'',split:true"  style="float:left;width:40%;height:100%"> 	  
	   <table id="treegrid"> </table>       
       <!-- toolbar -->
		<div id="toolbar" style="padding:5px;height:auto">
			<div>
				    <label>案件名称:</label> 
				    <input id="s_case_name" name="s_case_name" class="easyui-validatebox" style="width:100px">
					<label>办案场所选择:</label> 
	     			<input id="interrogatearea" name="interrogatearea" class="easyui-combobox" data-options="" editable="false" style="width: 130px">
					<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
					<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清除</a>
			</div>
		</div>
    </div>
    <!-- 中间结构 -->  
    <div data-options="region:'center',title:'',split:true" style="float:right;width:30%;height:100%">   
    	<table id="burngrid"></table> 
    </div>  
    <!-- 最右侧结构 -->
    <div data-options="region:'east',title:'',split:true" style="float:right;width:30%;height:100%">   
    	<table id="dvdLogTable"> </table> 
    	<!-- toolbar -->
		<div id="toolbarDvd" style="padding:5px;height:auto">
			<div>
				<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="dvdLogAdd()">添加</a>
			</div>
		</div>		
    </div>  
    <!-- 最右侧添加领取人信息 -->
    <div id="dvd_dialog" class="easyui-dialog" style="width: 400px; height: 250px; padding: 10px 20px" closed="true" buttons="#dvd-buttons">
		<div style="">
			<form id="dvd_form" class="form-style" method="post"
				style="">
				<div class="fitem">
					<label style="margin-left: -12px;">领取人:</label> <input id="receiveUser" name="receiveUser" class="easyui-validatebox"
						 data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'" style="margin-left: -79px;">
					<font color="red">*</font>
				</div>
				<div class="fitem" style="display:none;">
					<label>被刻录人id:</label> <input id="personId" name="personId" class="easyui-validatebox"
						 data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
					<font color="red">*</font>
				</div>
				<div class="fitem" style="display:none;">		
					 <label>案件id:</label> <input id="caseId" name="caseId" class="easyui-validatebox"
					 data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
					<font color="red">*</font>
				</div> 
				<div class="fitem" style="display:none;">
					 <label>办公场所id:</label> <input id="areaId" name="areaId" class="easyui-validatebox"
					 data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
					<font color="red">*</font>
				</div>				
			</form>
		</div>
		<div id="dvd-buttons">
			<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDvd()">保存</a> 
			<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dvd_dialog').dialog('close')">关闭</a>
		</div>
	</div>	
</body>
</html>