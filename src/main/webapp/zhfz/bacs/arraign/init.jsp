<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<link rel="stylesheet" type="text/css" href="init.css" >
<script type="text/javascript" src="init.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%--<%@ include file="../../common/common-loading.jsp"%>--%>
<div id="left" style="width: 100%;height: 100%">
	<%--<table id="left-certificategrid"></table>--%>

	<!-- toolbar -->
	<div id="left-certificateToolbar" style="padding:5px;height:auto">
		<!-- <div id="arraignAdd"
			 style="margin-bottom: 5px; width: 100%; height: 100%;" align="center">
			<a onclick="doArraign()" id="wakeupAdd"  name="wakeupAdd" class="myButton i1 m-box" style="width: 200px">
				<span>提讯</span>
				<div class="edges">
					</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
		</div> -->
		<div>
			<div style="margin-bottom:5px;float: left;">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">刷新</a>
			</div>
            <form  id="search" style="float: left;margin: 3px;">
				办案场所选择: <input name="areaId" class="easyui-combobox" id="query-combobox" style="width:200px">
            </form>
		</div>
	</div>
	<div class="context">
	<!-- <div  id="box"></div> -->
	</div>
</div>
<!-- <div id="right" style="margin-top: 160px;">

</div> -->

<div id="certificate-dialog" class="easyui-dialog"
	 style="width: 560px; height: 300px; padding: 10px 20px" closed="true"
	 buttons="#enterprise-buttons">
	<div style="height: 150px;">
		<div class="form-panel">
			<form id="arraign-form" class="form-style" method="post"
				  style="height: 150px;">
				<input type="hidden" name="recordId" id="recordId"/>
				<input type="hidden" name="policeId" id="policeIdOut"/>
				<input type="hidden" name="serialId" id="serialId"/>
				<input type="hidden" name="waitingRoomId" id="room-id">
				<div class="fitem">
					<label>审讯室:</label> <input name="roomId" id="leisure-room" class="easyui-combobox"
											   required="true" style="width: 173px;"   />
					<font color="red" style="margin-left: -2px;">*</font>
				</div>
				<div class="fitem">
					<label>民警卡片:</label>  <input id="policCuff" onkeypress="policeCard(this,event)" class="easyui-validatebox" placeholder="扫描提讯民警卡片"
												 data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
				</div>
				<div class="fitem">
					<label>民警警号:</label>  <input id="policeNoOut" class="easyui-validatebox" placeholder="输入民警警号"
												 required="true" onblur="finduser()"
												 data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
					<font color="red" style="margin-left: -2px;">*</font>
				</div>
				<div class="fitem">
					<label>嫌疑人手环:</label>  <input onkeypress="personCard(this,event)" class="easyui-validatebox"  placeholder="请扫描手环"
												  data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
					<font color="red" style="margin-left: -2px;">*</font>
				</div>
				<div class="fitem">
					<label>嫌疑人姓名:</label> <input id="save-combgrid" class="easyui-combogrid"
												 required="true"  style="width: 173px;"
												 data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
					<font color="red" style="margin-left: -2px;">*</font>
				</div>
				<%--<div class="fitem">--%>
				<%--<label>查询嫌疑人:</label> <input name="isMajor" class="easyui-validatebox"--%>
				<%--required="true"--%>
				<%--data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">--%>
				<%--</div>--%>
				<div class="fitem">
					<label>所在侯问室:</label> <input id="room-name" class="easyui-validatebox"
												 required="true"
												 data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
					<font color="red" style="margin-left: -2px;">*</font>
				</div>
			</form>
		</div>
		<div class="img-panel"></div>
	</div>
	<div id="enterprise-buttons">
		<a href="#" class="easyui-linkbutton" id="save-btn" iconCls="icon-ok" name="noButton" data-type="0" data-url=""
		   onclick="saveEnterprise(this)">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
		   onclick="javascript:$('#certificate-dialog').dialog('close')">关闭</a>
	</div>
</div>
<script type="text/template"  >
	<div class="jzw-box {display}" onclick="look('{index}')" >
		<div class="jzw-bg">
			<div class="jzw-title">{name}(空闲)</div>
			<img src="./img/bg.png" alt="">
		</div>
		<div class="jzw-left">
			<div class="jzw-title">{name}</div>
			<div class="jzw-img">
				<img src="{url}" onerror="imgNotFind()" alt="">
				<div class="jzw-btn-group">
					<span class="jzw-btn" onclick="doClear('{id}','{serialId}')">置空</span>
				</div>
			</div>
		</div>
		<div class="jzw-right">
			<div class="jzw-text">嫌疑人姓名：</div>
			<div class="jzw-text name"><span class="name-panel">{personName}</span><div class="name-view">{personName}</div></div>
			<div class="jzw-text">提审人：</div>
			<div class="jzw-text name"><span class="name-panel">{sendName}</span><div class="name-view">{sendName}</div></div>
			<div class="jzw-text">预约时间：</div>
			<div class="jzw-text name"><span class="name-panel">{outTime}</span><div class="name-view">{outTime}</div></div>
		</div>
	</div>
</script>
<script type="text/template" id="info">
	<div class="info {display}">
		<div class="n-info">
			<div class="info-title">该房间未查询到嫌疑人！</div>
			<div class="info-img"></div>
		</div>
		<div class="b-info">
			<img src="{url}" onerror="imgNotFind()" alt="" />
			<div class="person-info box">
				<div class="line">
					<span class="key">嫌疑人姓名：</span>
					<span class="value">{personName}({sex})</span>
				</div>
				<div class="line">
					<span class="key">证件号码：</span>
					<span class="value">{certificateNo}</span>
				</div>
			</div>
			<div class="police-info box">
				<div class="line">
					<span class="key">提审人：</span>
					<span class="value">{sendName}</span>
				</div>
				<%--<div class="line">--%>
					<%--<span class="key">提审人：</span>--%>
					<%--<span class="value">{goName},{gtName}</span>--%>
				<%--</div>--%>
				<div class="line">
					<span class="key">预约时间：</span>
					<span class="value">{outTime}</span>
				</div>
				<div class="line">
					<span class="key">结束时间：</span>
					<span class="value">{endTime}</span>
				</div>
			</div>
			<div class="police-info box">
				<div class="line">
					<span class="key">案件名称：</span>
					<span class="value">{caseName}</span>
				</div>
				<div class="line">
					<span class="key">案件编号：</span>
					<span class="value">{caseNo}</span>
				</div>
				<div class="line">
					<span class="key">案件类别：</span>
					<span class="value">{caseClass}</span>
				</div>
				<div class="line">
					<span class="key">主办单位：</span>
					<span class="value">{organization}</span>
				</div>
			</div>
		</div>
	</div>
</script>
</body>
</html>