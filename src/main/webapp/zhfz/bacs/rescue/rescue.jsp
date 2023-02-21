<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="rescue.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="certificategrid"></table>


<div id="certificate_dialog" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box">
		<div class="popup-inner">
			<div class="title">
				<div><i style="background-image: url(../otherPersonEntrance/static/popup-2.png);"></i><span>新增/修改记录</span></div>
				<a name="noButton" class="close" onclick="$('#certificate_dialog').hide()"></a>
			</div>
			<div id="hd" class="hd">
				<div class="steps">
					<div id="step1" class="i1 item m-box now">
						<div class="inner">
							<i></i>
						</div>
						<span>基本信息</span>
						<div class="edges">
							<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
						</div>
					</div>
				</div>
			</div>
			<div id="stepDiv1"  class="bd">
				<form id="save-form" method="post">
					<input type="hidden" id="personId" name="personId" />
					<input type="hidden" id="id" name="id" />
					<table class="user_tab" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: 12px;">
						<tr>
							<td><label >嫌疑人姓名：</label></td>
							<td>
								<select id="save-serial" name="serial" style = "margin:-2px;width:188px; height:28px" class="easyui-combobox"   required="true">
								</select>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >押送民警：</label></td>
							<td>
								<input type="text" class="easyui-validatebox" id="policeNo" name="policeNo" onblur="finduser1(0)" style="font: 14px arial; padding: 3px; width: 180px" required="true" placeholder="请输入警号" />
								<input type="hidden" id="policeId" name="policeId">
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >救助时间：</label></td>
							<td>
								<input id="rescue-time" name="rescueTimeStr" style = "margin:-2px;width:188px; height:28px"  class="easyui-datetimebox"   required="true"/>

								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >救助医生：</label></td>
							<td>
								<input  name="doctor" type="text" class="easyui-validatebox"
									   style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="false"/><font color="red">*</font>

							</td>
						</tr>
						<tr>
							<td><label >救助事项：</label></td>
							<td>
								<select id="item" name="item" style = "margin:-2px;width:188px; height:28px" class="easyui-combobox" data-options="url:'../../common/code/listCodeByType.do?type=yljz',valueField:'codeValue',textField:'codeValue'"  required="true">
								</select>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >描述：</label></td>
							<td>
								<input id="description" name="description" type="text" class="easyui-validatebox"
									   style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" />
							</td>
						</tr>

					</table>
				</form>
				<div class="next">
					<button class="m-btn-1 blue" onclick="saveEnterprise()">保存</button>
					<button class="m-btn-1 blue" onclick="$('#certificate_dialog').hide()">取消</button>
				</div>
			</div>
		</div>
		<div class="edges">
			<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
		</div>
	</div>
</div>



	
	<!-- toolbar -->

	<div id="certificateToolbar" style="padding:5px;height:auto">
		<div id="tj_btn"
			 style="margin-bottom: 5px; width: 100%; height: 100%;" align="center">
			<a onclick="add()" id="rescueAdd" name="rescueAdd" class="myButton i1 m-box" style="width: 200px">
				<span>添加</span>
				<div class="edges">
					</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
		</div>
		<div>
			<form  id="search" style="float: left;margin: 3px;">

				救助人姓名: <input id="query-person-name" class="easyui-validatebox" style="width:200px" />
				<label>办案场所:</label>
                <input id="area" name="area" class="easyui-combobox" data-options="" editable="false">
			</form>

				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				
			</div>
		</div>
</body>
</html>