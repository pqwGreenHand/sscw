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
<link rel="stylesheet" type="text/css" href="css.css" />
<script type="text/javascript" src="infocollection.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<div id="objects"></div>

    <div data-options="region:' west',split:true"
		style="width: 75%; height: 100%;">
		<table id="infocollection_datagrid"></table>
	</div>
	<div data-options="region:'east',split:true"
		style="width: 25%; height: 100%;">
		<table id="infocollection_detaial_datagrid"></table>
	</div>
	
	<!-- toolbar -->
	<div id="toolbar" style="padding: 5px; height: auto">
		<!-- <div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-info" onclick="addinfocollection()">信息采集</a>
		</div> -->
		<div id="tj_btn" style="margin-bottom:5px;width: 100%;height: 100%;" align="center">
				<div class="box">
					<a id="addinfocollection" class="myButton i1 m-box" name="noButton" onclick="addinfocollection()">
						<span>信息采集</span>
						<div class="edges">
							<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
						</div>
					</a>
				</div>
		</div>

		<div>
			<label>嫌疑人姓名:</label> 
			<%--<input id="personName" class="easyui-validatebox" style="width: 80px"> --%>
			<input id="personName" name="cuffNumber" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true" />
			<%--<label>专属编号:</label>
			<input id="serialNo" class="easyui-validatebox" style="width: 80px"> --%>
			<label>证件号码:</label>
			<input id="certificateNo" name="cuffNumber" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true" />
			<%--<input id="certificateNo" class="easyui-validatebox" style="width: 80px"> --%>
			<label>办案场所选择:</label>
            <input id="area" name="area" class="easyui-combobox" data-options="" editable="false">
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a> 
			<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>
		</div>
	</div>


	<div id="infocollection_dialog1" class="easyui-dialog" 
		style="width: 519px; height: 420px; padding: 10px 20px" closed="true" buttons="#dlg-buttons1">
		<div class="ftitle" align="center" style="color: #fff8c1">新增信息采集</div>
		<form id="infocollection_form1" class="form-style" method="post" style="height: 200px">
			<table id="infocollection_tab" pagination="true" fitColumns="true" singleSelect="true" >
				<tr>
					<td colspan="2" align="left">
					</td>
					
				</tr>
				<thead align="left">
			<tr>
				<td align="left">
					<div class="fitem" style="padding-left: 60px">

						<%--<a href="#" class="myButton  m-box"  class="easyui-linkbutton" name="noButton"  onclick="readRingNo()">扫描手环</a>--%>
                        <%--<input type="button" style="background-color: #000093"  name="noButton" value="扫描手环" onclick="readRingNo()" />--%>
                         <%--<button onclick="readRingNo()" style=" width: 80px;height:30px;background-color: #428bca;  margin-left: -10px; border-color: #0F0AFF; color: #fff;border-radius: 5px;text-align: center;vertical-align: middle;border: 1px solid transparent;font-weight: 900;font-size: 125%;">
							 扫描手环
						 </button>--%>
							<label id="cuffLabel">请扫描手环：</label>
							<input id="cuffNumber" name="cuffNumber" class="easyui-validatebox"
								   style="font: 14px arial; width: 180px;height:22px;padding:3px;margin-left: -25px"
								   data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true" />
								<font color="red" style="margin-left: 2px;">*</font>
							<br/>
							<div style="margin-top: 12px">
								<span>专属编号：</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<select  id="serialNo1" name="serialNo1" class="easyui-combogrid" style="width:189px;height: 28px;" data-options="validType:'maxLength[128]'"></select>
							</div>

						<!-- <input id="serialNo1" name="serialNo1" class="easyui-combogrid" style="width:140px;" required="true" data-options="validType:'maxLength[128]'"> -->
						<!-- <input type="button" value="确定" onclick="showdialog2()" class="xx_change" style="margin-left:40px"> -->
					</div>
				</td>
			</tr>
			</thead>
        	</table>
        	<div class="ftitle" align="center" style="color: #fff8c1">信息采集基本项</div>
        	<!-- <table id="caiji_detaial_datagrid"></table> -->
        	<table id="caiji_detaial_datagrid_new">
				
			</table>
        	</form>
		<div id="dlg-buttons1">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveData()">保存</a> 
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#infocollection_dialog1').dialog('close')">取消</a>
		</div>
	</div>
    <div id="infocollection_dialog3" class="easyui-dialog"
		style="width: 520px; height: 450px; padding: 10px 20px" closed="true" buttons="#dlg-buttons2">
		<div class="ftitle" align="center" style="color: #fff8c1">继续采集</div>
		<form id="infocollection_form2" class="form-style" method="post" style="height: 200px;" >
			<table id="infocollection_tab" pagination="true" fitColumns="true" singleSelect="true" 
			style="padding-left: 60px">
				<tr>
					<td colspan="2" align="left">
					</td>
				</tr>
				<thead align="left">
			<tr>
				<td align="left">
					<div class="fitem" style="align:center">
						<label>专属编号:</label>
						<input id="serialNo2" name="serialNo2" class="easyui-validatebox"
						data-options="validType:'maxLength[128]'">
						<!-- <input type="button" value="确定" onclick="showdialog2()" style="margin-left:40px"> -->
        			</div>
        	</table>
        	<div class="ftitle" align="center" style="color: #fff8c1">信息采集基本项</div>
			<table id="caiji_detaial_datagrid1"></table>
		</form>
		<div id="dlg-buttons2">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveData1()">保存</a> 
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#infocollection_dialog3').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>