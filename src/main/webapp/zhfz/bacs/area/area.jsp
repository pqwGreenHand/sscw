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
	<%@ include file="../../common/common-loading.jsp"%>
	<script type="text/javascript" src="area.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
	<div data-options="region:'center',split:true" style="width: 49%; height: 95%;">
		
		<table id="areaid"></table>
		
	</div>
	
	<div data-options="region:'east',split:true" style="width: 49%; height: 95%;">
		<table id="roomid" ></table>
	</div>

	<div id="organization_dialog" class="easyui-dialog"
		style="overflow: hidden; width: 400px; height: 380px; padding: 10px 20px "  closed="true"
		buttons="#organization-buttons" >
		<div class="ftitle">办案场所信息</div>
		<div style="height: 150px;">
			<form id="Area_form" class="form-style" method="post"
				style="height: 300px; overflow: hidden">
				<input type="hidden" id="id" name="id" />
				<!-- <input type="hidden" id="orgenpId" name="orgenpId" /> -->
				<div class="fitem">
					<span style="display:inline-block;width:90px;">名称:</span> <input id="name" name="name" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'"><span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">类型:</span>

						<select class="easyui-combobox" editable="false" id="type" name="type" required="true"
							style="width: 172px;">
							<option value="0">办案中心</option>
							<option value="1">办案区</option>
						</select>
						<span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">描述:</span>
					 <input id="description" name="description" class="easyui-validatebox"
						 data-options="validType:'maxLength[256]',invalidMessage:'最长256字节'">
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">所在公安机关:</span>

                	<select style="width: 172px;" editable="false" id="organizationId"  name="organizationId" class="easyui-combobox" >
                	</select>
                	<span style="color:#F00"> * </span>
				</div>
				<div class="fitem"><span style="display:inline-block;width:90px;">单位地址:</span>

					 <input id="address" name="address" class="easyui-validatebox"
						 data-options="validType:'maxLength[256]',invalidMessage:'最长256字节'">
				</div>
				<div class="fitem"><span style="display:inline-block;width:90px;">单位电话: </span>
					<input id="telephone" name="telephone" class="easyui-validatebox"
						 data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'">
				</div>
				<div class="fitem"><span style="display:inline-block;width:90px;">邮编:</span>
					 <input id="postcode" name="postcode" class="easyui-validatebox"
						 data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'">
				</div>
			
			</form>
		</div>
		<div id="organization-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveArea()">保存</a> <a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-cancel"
				onclick="javascript:$('#organization_dialog').dialog('close');">关闭</a>
		</div>
	</div>

	<!-- toolbar -->
	<div id="areaToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">

		</div>
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="areaAdd" onclick="areaAdd()">新增</a>

				名称: <input id="s_name" class="easyui-validatebox" style="width:100px">
				类型: <select class="easyui-combobox" editable="false" id="s_type" name="s_type"
						style="width: 100px;">
						<option selected="selected" value="">全部</option>
						<option value="0">办案中心</option>
						<option value="1">办案区</option>
						</select>

				<a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="areaSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton"  onclick="areadoClear()">清除</a>
		</div>
	</div>
	
	<div id="roomToolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">


		</div>
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"  id="roomBatchAdd" onclick="ledSaveAll()">LED一键设置</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="roomAdd" onclick="roomAdd()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="roomBatchAdd" onclick="roomBatchAdd()">批量新增功能室</a>
				名称: <input id="r_name" class="easyui-validatebox" style="width:100px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="roomSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton" onclick="areadoClear()">清除</a>
			</div>
	</div>
	<div id="enterprise_dialog" class="easyui-dialog"
		style="width: 400px; height: 540px; padding: 10px 20px; overflow: hidden" closed="true"
		buttons="#enterprise-buttons">
		<div class="ftitle">办案场所内的功能室信息</div>
		<div style="height: 150px;">
			<form id="Room_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="id" name="id" />
				<input type="hidden" id="interrogateAreaId" name="interrogateAreaId" />
				<div class="fitem">
					<span style="display:inline-block;width:90px;">名称:</span>  <input id="name" name="name" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'"><span style="color:#F00"> * </span>
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">类型:</span>
						<input id="roomTypeId" name="roomTypeId" class="easyui-combobox" required="true" style="width:172px;" >
					    <span style="color:#F00"> * </span>
				</div>

				<div class="fitem">
					<span style="display:inline-block;width:90px;">描述:</span>
						<input id="description" name="description" class="easyui-validatebox"
						 data-options="validType:'maxLength[256]',invalidMessage:'最长256字节'">
				</div>
				<div>
					<span style="display:inline-block;width:90px;">是否启用:</span>


				 	<select class="easyui-combobox" editable="false" id="isActive"  name="isActive" required="true"
							style="width:172px;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
						<span style="color:#F00"> * </span>
				 </div>	
				<div class="fitem">
					<span style="display:inline-block;width:90px;">IP:</span>
					<%--<textarea class="easyui-validatebox" id="ips" name="ips" required="true" cols="10px" rows="10px" style="height: 30px;width: 140px;" ></textarea>--%>
					<input id="ips" name="ips" class="easyui-validatebox"
						required="true" data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'"><span style="color:#F00"> * </span>

				 <br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 	&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*请录入该功能室多个电脑的ip，<br/>
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;并以分号分隔</span>
				 </div>	
				 <div class="fitem">
					<span style="display:inline-block;width:90px;">功能室容积:</span>
					 <input id="volume" name="volume" class="easyui-validatebox"  required="true" ><span style="color:#F00"> * </span>
					
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">定位分组:</span>
						<input id="ledAddress" name="ledAddress" class="easyui-validatebox" required="true" ><span style="color:#F00"> * </span>

				</div>
				<div class="fitem">
					<span style="display:inline-block;width:90px;">触发器编号:</span> <input id="triggerNo" name="triggerNo" class="easyui-validatebox"
												 data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'">
				</div>
				<div class="fitem">
					<div style="margin-left: 20px;margin-bottom: 5px"><span style="color: red;">&nbsp;&nbsp;坐标格式：[[1,2],[2.4,2.7],[4.3,4.6],[1,2]]</span></div>
					<span style="display:inline-block;width:90px;">
						XY坐标值:</span> <input id="axis" name="axis" class="easyui-textbox"  style="width:171px;height: 60px " >
				</div>
			</form>
		</div>
		<div id="enterprise-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveRoom()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#enterprise_dialog').dialog('close');">关闭</a>
		</div>
	</div>
	
	<div id="detail_dialog1" class="easyui-dialog"
		style="width: 400px; height: 540px; padding: 10px 20px; overflow: hidden" closed="true"
		buttons="#detail-buttons1">
		<div class="ftitle">功能室信息详情</div>
		<div style="height: 150px;">
			<div class="fitem">
				<span style="display:inline-block;width:90px;">名称:</span>  <input id="name1" name="name1" class="easyui-validatebox"
																				  required="true" data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'">
			</div>
			<div class="fitem">
				<span style="display:inline-block;width:90px;">类型:</span>
				<input id="roomTypeId1" name="roomTypeId1" class="easyui-combobox" required="true" >

			</div>

			<div class="fitem">
				<span style="display:inline-block;width:90px;">描述:</span>
				<input id="description1" name="description1" class="easyui-validatebox"
					   data-options="validType:'maxLength[256]',invalidMessage:'最长256字节'">
			</div>
			<div>
				<span style="display:inline-block;width:90px;">是否启用:</span>


			<select class="easyui-combobox" editable="false" id="isActive1"  name="isActive1" required="true"
						style="width:164px">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</div>
			<div class="fitem">
				<span style="display:inline-block;width:90px;">IP:</span>
				<%--<textarea class="easyui-validatebox" id="ips" name="ips" required="true" cols="10px" rows="10px" style="height: 30px;width: 140px;" ></textarea>--%>
				<input id="ips1" name="ips1" class="easyui-validatebox"
					   required="true" data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'">

				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;text-align: right">*请录入该功能室多个电脑的ip，<br/>
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;并以分号分隔</span>
			</div>
			<div class="fitem">
				<span style="display:inline-block;width:90px;">功能室容积:</span>
				<input id="volume1" name="volume1" class="easyui-validatebox"  required="true" >

			</div>
			<div class="fitem">
				<span style="display:inline-block;width:90px;">定位分组:</span>
				<input id="ledAddress1" name="ledAddress1" class="easyui-validatebox" required="true" >

			</div>
			<div class="fitem">
				<span style="display:inline-block;width:90px;">触发器编号:</span> <input id="triggerNo1" name="triggerNo" class="easyui-validatebox"
																					data-options="validType:'maxLength[32]',invalidMessage:'最长32字节'">
			</div>
			<div class="fitem">
				<div style="margin-left: 20px;margin-bottom: 5px"><span style="color: red;">&nbsp;&nbsp;坐标格式：[[1,2],[2.4,2.7],[4.3,4.6],[1,2]]</span></div>
				<span style="display:inline-block;width:90px;">
						XY坐标值:</span> <input id="axis1" name="axis1" class="easyui-textbox"   style="width:164px;height: 60px " />
			</div>
		</div>
		<div id="detail-buttons1">
			 <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#detail_dialog1').dialog('close');">关闭</a>
		</div>
	</div>
	
	<div id="batchAddRoom_dialog" class="easyui-dialog"
		style="width: 400px; height: 320px; padding: 10px 20px; overflow: hidden" closed="true"
		buttons="#batchAddRoom-buttons">
		<form id="batchAddRoom_form" class="form-style" method="post"
				style="height: 150px;">
			<div class="ftitle">批量添加功能室信息</div>
			<div id='batchAddRoom_box' style="height: 200px;text-align: center">

					<div class="fitem">

					</div>
			</div>
		</form>
		<div id="batchAddRoom-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveBatchRoom()">保存</a>
			<a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#batchAddRoom_dialog').dialog('close');">关闭</a>
		</div>
	</div>
	
	
	
	<div id="led_dialog" class="easyui-dialog"
		style="width: 600px; height: 340px; padding: 10px 20px; overflow: hidden" closed="true"
		buttons="#led-buttons">
		<form id="ledForm">
		  <input id="ledId" name = "id" type="hidden">
		  <input id="ledAreaId" name="areaId" type="hidden"> 
		  <input id="ledRoomId" name="roomId" type="hidden"> 
	
			<div style="height: 150px;">
				<div class="fitem">
				
					<span style="display:inline-block;width:190px;">LED设备IP:</span>  <input id="ip" name="ip" class="easyui-validatebox" >
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:190px;">LED设备PORT:</span>  <input id="port" name="port" class="easyui-validatebox"  value="6666">
				</div>
				<div class="fitem">
					<span style="display:inline-block;width:190px;">LED设备内容:</span>  <input id="lednr" name="lednr" class="easyui-validatebox" >
				</div>
			</div>
		 	</form>
		<div id="led-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveLed()">刷新</a>
			 <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#led_dialog').dialog('close');">关闭</a>
		</div>
	</div>
	
</body>
</html>