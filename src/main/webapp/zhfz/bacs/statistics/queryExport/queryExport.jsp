<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据导出</title>
<%@ include file="../../../common/common-head.jsp"%>
<script type="text/javascript" src="queryExport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
<link rel="stylesheet" type="text/css" href="../export/css.css" />
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../../common/common-loading.jsp"%>
<!-- 数据展示 -->
	<div data-options="region:'center',split:true" style="width: 100%; height: 100%;" align="center">
		<table id="datagrid"></table>
	</div>
	<!-- 按钮 -->
	<!-- toolbar 	-->
	<div id="exportToolbar" style="padding:5px;height:auto">

		<div style="margin-bottom: 5px">
		<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id:'cuffLogadd' onclick="cuffLogadd()">添加</a>  --> 
		</div>
<!-- 条件查询-->
		<div>
		<div id="tj_btn" style="margin-bottom:5px;width: 100%;height: 100%;" align="center">
			 <input type="button" id="securityAdd" name="tijiao" value="数据导出" class="bc_on9" onclick="exportData()"/>
			 <!-- <input type="button" id="securityAdd" name="tijiao" value="交班记录" class="bc_on9" onclick="exportData1()"/>
			 <input type="button" id="securityAdd" name="tijiao" value="看押记录" class="bc_on9" onclick="exportData2()"/> -->
		</div>
			姓名: <input id="personName" class="easyui-validatebox" style="width: 100px"> 
			人员类型:
				<select class="easyui-combobox" id="personType" name="personType"
						style="width: 100px;">
						<option value="" selected="selected">全部</option>
						<option value="0">嫌疑人</option>
						<option value="1">事主</option>
						<option value="2">证人</option>
						<option value="3">其他</option>
				</select>
				性别:
				<select class="easyui-combobox" id="personSex" name="personSex"
						style="width: 100px;">
						<option value="" selected="selected">全部</option>
						<option value="1">男</option>
						<option value="2">女</option>
				</select>
				案件性质:
				<select class="easyui-combobox" id="caseType" name="caseType"
						style="width: 100px;">
						<option value="" selected="selected">全部</option>
						<option value="2">刑事</option>
						<option value="1">行政</option>
				</select>
				案件类型: <input id="caseProperties" class="easyui-validatebox" style="width: 100px"> 
				办案单位: <input id="workSpace" class="easyui-validatebox" style="width: 100px"> 
				开始时间: <input id="betweenTime" class="easyui-datetimebox" style="width:150px">
				结束时间: <input id="betweenTimeMax" class="easyui-datetimebox" style="width:150px">
			     <br/>
			    自定义主体:
				<select class="easyui-combobox" id="subjectType" name="subjectType"
						style="width: 100px;">
						<option value="1" selected="selected">法律手续</option>
				</select> 
			   自定义时间:
				<input id="subjectTime" name="subjectTime" class="easyui-numberbox" style="width: 100px"> 
				 
				 <a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-search" onclick="doSearch()" name="noButton">查询</a> <a
				href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear"
				onclick="doClear()">清除</a>
		</div>
	</div>
	<!-- 第一个导出数据表单 -->
	<form id="exportForm" name="exportForm" method="post" action="../../export/exportExcel.do">
		<input type="text" id="personName1" name="personName1">
		<input type="text" id="personType1" name="personType1">
		<input type="text" id="personSex1" name="personSex1">
		<input type="text" id="caseType1" name="caseType1">
		<input type="text" id="caseProperties1" name="caseProperties1">
		<input type="text" id="workSpace1" name="workSpace1">
		<input type="text" id="betweenTime1" name="betweenTime1">
		<input type="text" id="betweenTimeMax1" name="betweenTimeMax1">
		<input type="text" id="workbetweenTime1" name="workbetweenTime1">
	    <input type="text" id="subjectType1" name="subjectType1">
	    <input type="text" id="subjectTime1" name="subjectTime1">
		<input type="text" id="tempString" name="tempString">
	</form>
 
	<!-- 弹出窗口 选择列名 -->
	 <div id="exportExcel_dialog" class="easyui-dialog"
		style="width: 520px; height: 450px; padding: 10px 20px" closed="true" buttons="#dlg-buttons2">
		<div class="ftitle" align="center">选择列名</div>
			<table id="cellName_datagrid">
				<tr align="center">
					<td colspan="3"><font color="red" size="2">导出的数据为通过查询条件所过滤的数据，请勾选需要导出的列。</font></td>
				</tr>
				<tr>
					<td width="120px" height="50px"><input type="checkbox" id="personTypeCell" name="checkTheme" value="cell0" checked="checked">人员类型</td>
					<td width="120px" height="50px"><input type="checkbox" id="personNameCell" name="checkTheme" value="cell1" checked="checked">姓名</td>
					<td width="120px" height="50px"><input type="checkbox" id="personSexCell" name="checkTheme" value="cell2" checked="checked">性别</td>
					<td width="120px" height="50px"><input type="checkbox" id="personCertificateNoCell" name="checkTheme" value="cell3" checked="checked">证件号码</td>
				</tr>	
				<tr>
					<td width="120px" height="50px"><input type="checkbox" id="caseTypeCell" name="checkTheme" value="cell4" checked="checked">案件性质</td>
					<td width="120px" height="50px"><input type="checkbox" id="casePropertiesCell" name="checkTheme" value="cell5" checked="checked">案件类型</td>
					<td width="120px" height="50px"><input type="checkbox" id="inTimeCell" name="checkTheme" value="cell6" checked="checked">入区时间</td>
					<td width="120px" height="50px"><input type="checkbox" id="outTimeCell" name="checkTheme" value="cell7" checked="checked">出区时间</td>
				</tr>
				<tr>
					<td width="120px" height="50px"><input type="checkbox" id="outPlaceCell" name="checkTheme" value="cell8" checked="checked">出区去向</td>
					<td width="120px" height="50px"><input type="checkbox" id="policemanCell" name="checkTheme" value="cell9" checked="checked">办案民警</td>
					<td width="120px" height="50px"><input type="checkbox" id="workspaceCell" name="checkTheme" value="cell10" checked="checked">办案单位</td>
					 <td width="120px" height="50px"><input type="checkbox" id="confirmTimeCell" name="checkTheme" value="cell11" checked="checked">是否成年</td>
				</tr>
				<tr>
				<td width="120px" height="50px"><input type="checkbox" id="outPlaceCellTime" name="checkTheme" value="cell13" checked="checked">裁决时间</td>
				
				<td width="120px" height="50px"><input type="checkbox" id="outPlaceCell2" name="checkTheme" value="cell14" checked="checked">裁决结果</td>
				<td width="120px" height="50px"><input type="checkbox" id="entranceCell" name="checkTheme" value="cell12" checked="checked">法律手续</td>
				<td width="120px" height="50px"><input type="checkbox" id="writtenCell" name="checkTheme" value="cell15" checked="checked">手续开具时间</td>
				</tr>
				<tr>
				<td width="120px" height="50px"><input type="checkbox" id="sfxxcjCell" name="checkTheme" value="cell16" checked="checked">是否信息采集</td>
					<td width="120px" height="50px"><input type="checkbox" id="sfsyjdCell" name="checkTheme" value="cell17" checked="checked">是否送押解队</td>
				<td width="120px" height="50px"><input type="checkbox" id="recordTimeCell" name="checkTheme" value="cell18" checked="checked">入区到第一次审讯时间</td>
				</tr>
			
			</table>
		<div id="dlg-buttons2">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveData()">导出</a> 
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#exportExcel_dialog').dialog('close')">取消</a>
		</div>
	</div> 
</body>
</html>