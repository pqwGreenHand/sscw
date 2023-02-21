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
<script type="text/javascript" src="recognizeRecord.js"></script>
	<style type="text/css">
		.ksbr_btn{border:none;color:#fff;cursor:pointer;padding-left:180px; background:url(../record/image/ksbr.jpg) no-repeat;background-size:180px 45px;font-size:36px;}
	</style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
	<%@ include file="../../common/common-loading.jsp"%>
	<table id="recognizegrid"></table>

	<div id="recognize_dialog" class="easyui-dialog"
		style="width: 650px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#enterprise-buttons">
		
		<div style="height: 330px;">
			<form id="recognize_form" class="form-style" method="post"
				style="height: 180px;">
				<input type="hidden" id="caseid" name="caseId" />
				<input type="hidden" id="personid" name="personId" />
				<input type="hidden" id="id" name="id" />
				<div class="fitem">
					<label>笔录类型</label>
					<input id="recognizeType" name="recognizeType"  class="easyui-combobox" required="true" style="width:235px"/>
					<font color="red">*</font>
				</div>
				<div class="fitem">
					<label>办案地址:</label>
					<select style="width:235px" editable="true" id="area_cmb_id" required="true"
							name="areaId" class="easyui-textbobox" >
				 	</select>
					<font color="red">*</font>
				</div>
				<%--<div class="fitem">
					<label>办案地址:</label>
					<input id="address" name="address" class="easyui-textbox" required="true" style="width:235px" />
				</div>--%>
				<div class="fitem">
					<label>办案民警或勘验</br>或检查人姓名:</label>
					<input id="policeman1" name="policeman" class="easyui-textbox" style="width:235px;height: 60px" required="true" data-options="multiline:true"/>
					<font color="red">*</font>
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="policeInfo()">民警查询</a>
				</div>
				<div class="fitem">
					<label>被辨认人:</label>
					<input id="serialId" name="serialId" class="easyui-combogrid" style="width:235px" required="true"/>
					<font color="red">*</font>
				</div>
				<div class="fitem">
					<label>检查或者辨认对象:</label>
					<input id="recognizeName" name="recognize" class="easyui-combobox" style="width:235px" required="true"/>
					<font color="red">*</font>
				</div>
				<div class="fitem">
					<label>当事人或辨认人:</label>
						 <input id="victim" name="victim" class="easyui-textbox" required="true" style="width:235px;height: 65px" data-options="multiline:true"/>
					<font color="red">*</font>
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="personInfo(1)">当事人查询</a>
				</div>
				<div class="fitem">
					<label>见证人基本情况:</label>
						 <input id="witniss" name="witniss" class="easyui-textbox" required="true" style="width:235px;height: 65px" data-options="multiline:true"/>
					<font color="red">*</font>
					<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="personInfo(2)">见证人查询</a>
				</div>
				
				<div class="fitem">
					<label>事由和目的:</label>
						 <input id="target" name="target" class="easyui-combobox" required="true" style="width:235px" /><font color="red">*</font>
				</div>
				
				
			</form>
		</div>
		<div id="enterprise-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#recognize_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
	<!-- toolbar -->
	<div id="recognizeToolbar" style="padding:5px;height:auto">
		<%--<div style="margin-bottom:5px">--%>
			<%--<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="recognizeAdd" onclick="recognizeAdd()">添加</a>--%>
		<%--</div>--%>
		<%--<div style="margin-bottom:5px;width: 100%;height: 100%;" align="center">
			<a id="recognizeAdd" href="#" class="ksbr_btn" onclick="recognizeAdd()"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>--%>

			<div id="tj_btn" style="margin-bottom:5px;width: 100%;height: 100%;" align="center">
				<div class="box">
					<a id="recognizeAdd" class="myButton i1 m-box" name="noButton" onclick="recognizeAdd()">
						<span>开始辨认</span>
						<div class="edges">
							<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
						</div>
					</a>
				</div>
			</div>

		<div>
				办案场所: <select style="width:200px" editable="true" id="area_cmb"
							name="area_cmb" class="easyui-combobox">
				 	   </select>&nbsp;&nbsp;
			    办案民警人员及其单位: <input id="policeman" name="policeName1" class="easyui-textbox" style="width:180px">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				
			</div>
	</div>

  <!-- 查询民警 -->
	<div id="searchPoliceman" class="easyui-dialog"
		 style="width: 700px; height: 550px; padding: 0px;overflow:hidden;" closed="true"
		 buttons="#police-buttons">

		<div id="toolbar_policeInfo" style="padding: 5px; height: auto">
			民警姓名:<input id="policeInfo_name" class="easyui-validatebox" style="width: 70px">
			民警警号: <input id="policeInfo_no" class="easyui-validatebox" style="width:70px">
			单位:<input id="policeInfo_organization" class="easyui-validatebox" style="width:120px">
			<a href="#"
			   class="easyui-linkbutton" iconCls="icon-search"
			   id="searchPoliceInfo" name="noButton" onclick="searchPoliceInfo()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" id="doClearPoliceInfo" name="noButton"
			   onclick="doClearPoliceInfo()">清除</a>
		</div>


	   <div data-options="region:'center'" style="width:100%; height: 100%;">
		<table id="policeInfo">

		</table>
	    </div>
		<div id="police-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
			   onclick="savePoliceInfo()">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"  name="noButton"
			   onclick="javascript:$('#searchPoliceman').dialog('close')">取消</a>
		</div>
	</div>
	<!-- 查询民警 -->

	<!-- 查询见证人、当事人 -->
	<div id="searchPerson" class="easyui-dialog"
		 style="width: 700px; height: 550px; padding: 0px;overflow:hidden;" closed="true"
		 buttons="#person-buttons">

		<div id="toolbar_personInfo" style="padding: 5px; height: auto">
			姓名:<input id="personInfo_name" class="easyui-validatebox" style="width: 70px">
			证件号码: <input id="personInfo_no" class="easyui-validatebox" style="width:120px">
			<a href="#"
			   class="easyui-linkbutton" iconCls="icon-search"
			   id="searchPersonInfo" name="noButton" onclick="searchPersonInfo()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-clear" id="doClearPersonInfo" name="noButton"
			   onclick="doClearPersonInfo()">清除</a>
		</div>


		<div data-options="region:'center'" style="width:100%; height: 100%;">
			<table id="personInfo">

			</table>
		</div>
		<div id="person-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
			   onclick="savePersonInfo()">确定</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"  name="noButton"
				onclick="javascript:$('#searchPerson').dialog('close')">取消</a>
		</div>
	</div>

</body>
</html>