<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String ssareaid= request.getParameter("ssareaid");
%>

<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="belong.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;" >
<%@ include file="../../common/common-loading.jsp"%>
    <input id="ssareaid"  type="hidden" name="ssareaid" value="<%=ssareaid%>"></input>
	<div data-options="region:'north',split:true"
		style="width: 100%; height: 50%;">
		<table id="belongid"></table>
	</div>

	<div data-options="region:'center',split:true"
		style="width: 59%; ">
			<table id="detid"></table>
	</div>

	<div data-options="region:'east',split:true"
		style="width: 39%;">
			<table id="codid"></table>
	</div>

	<div id="det_dialog" class="easyui-dialog"
		style="width: 400px; height: 380px; padding: 10px 20px" closed="true"
		buttons="#det-buttons">
		<div class="ftitle">随身物品信息详情</div>
		<div style="height: 150px;">
			<form id="det_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<!-- <input type="hidden" id="orgenpId" name="orgenpId" /> -->
				<div class="fitem">
					<label>物品名称:</label> <input id="name" name="name"
						class="easyui-validatebox" required="true"
						data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
				</div>

				<div class="fitem">
					<label>数量:</label> <input id="detailCount" name="detailCount"
						class="easyui-validatebox" required="true">
				</div>

				<div class="fitem">
					<label>特征:</label> <input id="description" name="description"
						class="easyui-validatebox"
						data-options="validType:'maxLength[256]',invalidMessage:'最长256字节'">
				</div>
			</form>
		</div>
		<div id="det-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="savedet()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#det_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
	<!-- 开柜单页面.start -->
   <div id="openbill_dialog"  class="easyui-dialog"
		style="width: 400px; height: 380px; padding: 10px 20px" closed="true"
		buttons="#open-buttons">
		<div class="xx_change_div_2">
		<div class="ftitle">开柜单</div>
           <form id="openbill_form" class="form-style" method="post"
				style="height: 150px;">
<!-- 				<input type="hidden" id="id" name="id" /> -->
 				<input type="hidden"   id="interrogateSerialId" name="interrogateSerialId"  />
                <div class="fitem" style="display: none">
					<input type="hidden" id="lockerId" name="lockerId" class="easyui-combobox" readonly="readonly"/>
				</div>
                 <div class="fitem">
					<label>领取人:</label>
					 <input id="getPerson" name="getPerson" class="easyui-validatebox" required="true">
				</div>
				<div class="fitem">
					<label>领取方式:</label> 
						<select class="easyui-combobox" editable="false" id="getWay" name="getWay" required="true"
						style="width: 140px;" >
						<option value="1">本人领取</option>
						<option value="2">委托他人代为领取</option>
						<option value="3">其他</option>
					</select>
				</div>
               <div id="open-buttons">
			  <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveOpeninfo()">保存</a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#openbill_dialog').dialog('close')">关闭</a>
            </form>    
        </div>
		</div>
	<!-- 开柜单页面.end -->
	
	<!-- 打印机配置 -->
	<div id="print_dialog" class="easyui-dialog"
		style="width: 400px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#print-buttons">
		<div class="ftitle">打印机配置信息</div>
		<div style="height: 100px;">
			<form id="print_form" class="form-style" method="post"
				style="height: 80px;">
				<div class="fitem"></div>
				<table><tr><td>
					<label>类型名称:</label></td><td><input id="printerName" name="printerName" class="easyui-textbox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
					</td></tr>
					<tr><td>
					<label>端口名称:</label></td><td><input id="printerPort" name="printerPort" class="easyui-textbox"
						required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
						</td>
				</div>
				</tr></table>
			</form>
		</div>
		<div id="print-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="savePrintConfig()">保存</a> 
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#print_dialog').dialog('close')">关闭</a>
		</div>
	</div>

<div id="export_dialog" class="easyui-dialog"
	 style="width: 470px; height: 150px; padding: 10px 20px" closed="true"
	 buttons="#export_dialog-buttons">
	<form id="exportForm" name="exportForm" method="post" action="../../lawdocProcess/download.do">

		<input id="number" type="hidden" name="number"/>
		<input id="startTime" type="hidden" name="startTime" />
		<input id="endTime" type="hidden" name="endTime" />
		<input id="areaId" type="hidden" name="areaId" />



		保管时间：<input id="begin_date" class="easyui-datetimebox" style="width:160px">
		到：<input id="end_date" class="easyui-datetimebox" style="width:160px">
	</form>
	<div id="export_dialog-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
		   name="noButton" iconCls="icon-ok" onclick="exportSave()"
		   style="width: 90px">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
		   name="noButton" iconCls="icon-cancel" onclick="javascript:$('#export_dialog').dialog('close')"
										 style="width: 90px">取消</a>
	</div>
</div>
	<!-- toolbar -->
	<div id="areaToolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" name="noButton"
				id="enterprisebtnadd" onclick="javascript:window.location.href ='index.jsp';">返回</a>
		</div>
		<div>
			储物柜编号: <input id="lockerIdSeaerch" name="lockerId" class="easyui-combobox"  style="width: 100px">  
			民警姓名: <input id="s_person" class="easyui-validatebox" style="width:70px">
			存物时间从: <input id="d_start_date" class="easyui-datetimebox" style="width:140px">
				到: <input id="d_end_date" class="easyui-datetimebox" style="width:140px">
			<label>办案场所选择:</label>
			<input id="area" name="area" class="easyui-combobox" data-options="" editable="false">
				<a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-search" onclick="doSearch()">查询</a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-clear" name="noButton"
				onclick="doClear()">清除</a>
			<a href="#" name="noButton" onclick="belongExport();" class="easyui-linkbutton" iconCls="icon-redo" style="margin-right: 5px">导出</a>
		</div>
	</div>
	
	<div id="showPic_dialog" class="easyui-dialog"
		style="width: 600px; height: 500px; padding: 10px 20px" closed="true" buttons="#showPic-buttons">
	</div>
</body>
</html>