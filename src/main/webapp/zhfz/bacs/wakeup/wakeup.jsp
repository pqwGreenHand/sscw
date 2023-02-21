<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="wakeup.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="certificategrid"></table>
	<%--<div id="certificate_dialog" class="easyui-dialog"--%>
		<%--style="width: 420px; height: 320px; padding: 10px 20px" closed="true"--%>
		<%--buttons="#enterprise-buttons">--%>
		<%--<div style="height: 150px;">--%>
			<%--<form id="in-form" class="form-style save-form" method="post" hidden--%>
				<%--style="height: 150px;">--%>
				<%--<input type="hidden" name="id" />--%>
				<%--<div class="fitem">--%>
					<%--<label>涉案人员:</label> <input id="save-serial" name="serial" class="easyui-combogrid"--%>
												<%--required="true" style="width: 158px;"--%>
												<%--data-options=""/><font color="red" style="margin-left: 2px;">*</font>--%>
				<%--</div>--%>
				<%--<div class="fitem">--%>
					<%--<label>押送民警:</label> <input name="inUserId" id="in-police" class="easyui-combobox"--%>
											  <%--required="true" style="width: 158px;"--%>
											  <%--data-options="data:[],valueField:'id',textField:'value'"/>--%>
					      					  <%--<font color="red" style="margin-left: 2px;">*</font>--%>
				<%--</div>--%>
				<%--<div class="fitem">--%>
					<%--<label>看押民警卡片--%>
						<%--:</label> <input name="cuffNumber" id="cuffNumber" class="easyui-validatebox"--%>
												<%--required="true" style="width: 148px;"/>--%>
					<%--<input type="hidden" id="cuffId" name="cuffId">--%>
					<%--<font color="red" style="margin-left: 2px;">*</font>--%>
					<%--&lt;%&ndash;<input id="cuffNumber" name="cuffNumber" type="text"&ndash;%&gt;--%>
						   <%--&lt;%&ndash;class="easyui-validatebox"    style="font: 14px arial; padding: 3px;width:165px;"/>&ndash;%&gt;--%>

				<%--</div>--%>
				<%--<div class="fitem">--%>
					<%--<label>入区时间:</label> <input name="startTimeStr" id="start-time" class="easyui-datetimebox"--%>
												<%--style="width: 158px;"/>--%>
				<%--</div>--%>

				<%--<div class="fitem">--%>
					<%--<label>入区描述:</label>--%>
					<%--<textarea rows=5 name="inDescription" class="textarea easyui-validatebox" style="width: 275px;height: 52px;" required="true"--%>
							  <%--data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"></textarea>--%>
								<%--<font color="red" style="margin-left: 2px;">*</font>--%>
				<%--</div>--%>
			<%--</form>--%>
			<%--<form id="out-form" class="form-style save-form" method="post"--%>
				  <%--style="height: 150px; display: none;">--%>
				<%--<input type="hidden" name="id" />--%>
				<%--<input type="hidden" name="serialId" />--%>
				<%--<div class="fitem">--%>
					<%--<label>涉案人员:</label> <input name="personName" class="easyui-validatebox"  style="width: 148px;"/>--%>
					<%--<font color="red" style="margin-left: 2px;">*</font>--%>
				<%--</div>--%>
				<%--<div class="fitem">--%>
					<%--<label>提取民警:</label> <input name="outUserId" id="out-police" class="easyui-combobox"--%>
												<%--required="true" style="width: 158px;"--%>
												<%--data-options="data:[],valueField:'id',textField:'value',"/>--%>
					<%--<font color="red" style="margin-left: 2px;">*</font>--%>
				<%--</div>--%>
				<%--<div class="fitem">--%>
					<%--<label>出区时间:</label> <input name="endTimeStr" id="end-time" class="easyui-datetimebox"--%>
												<%--style="width: 158px;"/>--%>
				<%--</div>--%>
				<%--<div class="fitem">--%>
					<%--<label>出区描述:</label>--%>
					<%--<textarea rows=5 name="outDescription" class="textarea easyui-validatebox" style="width: 275px;height: 52px;" required="true"--%>
							  <%--data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"></textarea>--%>
					<%--<font color="red" style="margin-left: 2px;">*</font>--%>
				<%--</div>--%>
			<%--</form>--%>
		<%--</div>--%>
		<%--<div id="enterprise-buttons">--%>
			<%--<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"--%>
				<%--onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"--%>
				<%--iconCls="icon-cancel" name="noButton"--%>
				<%--onclick="javascript:$('#certificate_dialog').dialog('close')">关闭</a>--%>
		<%--</div>--%>
	<%--</div>--%>

<div id="certificate_dialog" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box">
		<div class="popup-inner">
			<div class="title">
				<div><i style="background-image: url(../otherPersonEntrance/static/popup-2.png);"></i><span id="save-title">新增/修改醒酒记录</span></div>
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
				<form id="in-form" method="post" class="save-form" hidden>
					<input type="hidden"  name="id" />
					<input type="hidden" id="detainPoliceId"  name="detainPoliceId" />
					<table class="user_tab" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: 12px;">
						<tr>
							<td><label >嫌疑人姓名：</label></td>
							<td>
								<input type="text" id="save-serial" name="serial" class="easyui-combogrid" style= "font: 14px arial; padding:3px;width:180px;"
									  data-validate="true" data-methods="notNull" data-name="嫌疑人姓名"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >押送民警：</label></td>
							<td>
								<input type="text" class="easyui-validatebox" id="policeNo" name="policeNo" onblur="finduser1(0)" style="font: 14px arial; padding: 3px; width: 180px" required="true" placeholder="请输入警号" />
								<input type="hidden" id="inUserId" name="inUserId">
								<font color="red" style="margin-left: 2px;">*</font>
							</td>
						</tr>
						<tr id="cuffNumber-tr">
							<td><label >看押民警卡片：</label></td>
							<td>
								<input id="cuffNumber" data-validate="true" data-methods="notNull" data-name="看押民警" name="cuffNumber" style = "margin:-2px;width:175px; height:28px"  class="easyui-validatebox"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >进入时间：</label></td>
							<td>
								<input id="start-time" name="startTimeStr" type="text" class="easyui-datetimebox"
									   style= "font: 14px arial; padding:3px;width:180px;" data-validate="true" data-methods="notNull" data-name="进入时间" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >描述：</label></td>
							<td>
								<input  name="inDescription" type="text" class="easyui-validatebox"
									   style= "font: 14px arial; padding:3px;width:175px;" data-name="描述" />

							</td>
						</tr>
					</table>
				</form>
				<form id="out-form" method="post" class="save-form" hidden>
					<input type="hidden"  name="id" />
					<table class="user_tab" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: 12px;">
						<tr>
							<td><label >嫌疑人姓名：</label></td>
							<td>
								<input type="text" name="personName" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >提取民警：</label></td>
							<td>
								<input type="text" class="easyui-validatebox" id="e_policeNo" name="e_policeNo" onblur="finduser1(1)" style="font: 14px arial; padding: 3px; width: 180px" required="true" placeholder="请输入警号" />
								<input type="hidden" id="outUserId" name="outUserId">
								<font color="red" style="margin-left: 2px;">*</font>
								<font color="red" style="margin-left: 2px;">*</font>
							</td>
						</tr>
						<tr>
							<td><label >离开时间：</label></td>
							<td>
								<input id="end-time" name="endTimeStr" type="text" class="easyui-datetimebox"
									   style= "font: 14px arial; padding:3px;width:180px;" data-validate="true" data-methods="notNull" data-name="出区时间" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td><label >描述：</label></td>
							<td>
								<input  name="outDescription" type="text" class="easyui-validatebox"
										style= "font: 14px arial; padding:3px;width:175px;"  data-name="描述"/>

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
			<a onclick="add()" id="wakeupAdd"  name="wakeupAdd" class="myButton i1 m-box" style="width: 200px">
				<span>添加</span>
				<div class="edges">
					</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
		</div>

		<div>
			<form  id="search" style="float: left;margin: 3px;">

				人员名字: <input id="query-person-name" class="easyui-validatebox" style="width:200px" />
				办案场所:<input type="text" id="s_areaId" name="areaId" class="easyui-combobox">
			</form>

				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
				
			</div>
	</div>

</body>
</html>