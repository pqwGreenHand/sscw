<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
	String userNo = sessionInfo.getUser().getCertificateNo();
%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="record.js"></script>
<script type="text/javascript" src="../../../js/IdcardUtil.js"></script>
	<script type="text/javascript">
        // 开始审讯
        function recordAdd(){
            showDialog('#data_dialog','新增审讯记录');
            $('#recode_form').form('clear');
            $('#askNo').val('<%=userNo%>');
            $('#serialId').combogrid({
                panelWidth:360,
                mode: 'remote',
                url: '../combobox/getSerialList.do',
                idField: 'serialId',
                textField: 'personName',
                columns: [[
                    {field:'serialNo',title:'入区编号',width:135},
                    {field:'personName',title:'姓名',width:60},
                    {field:'certificateNo',title:'证件号码',width:150}
                ]],
                onChange : function(val){
                    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
                    var row = cg.datagrid('getSelected');
                    //var userNo1 = row.inUserNo1;
                    //var userNo2 = row.inUserNo2;
                    //$('#recodeNo').val(('<%=userNo%>'==userNo1)?userNo2:userNo1);
                }
            });
            url = 'addRecord.do';
            $("#isFlage").prop({checked:true});
        }
	</script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<table id="certificategrid"></table>
	<div id="certificate_dialog" class="easyui-dialog"
		style="width: 420px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#enterprise-buttons">
		<div style="height: auto;">
			<form id="save-form" class="form-style save-form" method="post">
				<input type="hidden" name="id" />
				<input type="hidden" name="recordTypeId" id="save-record-type-id" />
				<div class="fitem">
					<label>涉案人员:</label> <input id="save-serial" name="serial" class="easyui-combogrid"
												required="true" style="width: 158px;"
												data-options=""/>
					<font color="red" style="margin-left: 2px;">*</font>
				</div>
				<div class="fitem">
					<label>询问民警:</label> <input name="policeAskId" id="save-police-ask" class="easyui-combobox"
											  required="true" style="width: 158px;"
											  data-options="data:[],valueField:'id',textField:'value'"/>
					<font color="red" style="margin-left: 2px;">*</font>
				</div>
				<div class="fitem">
					<label>记录民警:</label> <input name="policeRecordId" id="save-police-record" class="easyui-combobox"
												required="true" style="width: 158px;"
												data-options="data:[],valueField:'id',textField:'value'"/>
					<font color="red" style="margin-left: 2px;">*</font>
				</div>
				<div class="fitem">
					<label>word模板:</label> <input name="recordTemplateId" id="save-word" class="easyui-combobox"
												  required="true" style="width: 158px;"
												  data-options="url:'../combobox/listRecordTemplate.do',valueField:'id',textField:'value'"/>
					<font color="red" style="margin-left: 2px;">*</font>
				</div>
				<div class="fitem">
					<label>开始时间:</label> <input name="startTimeStr" id="start-time" class="easyui-datetimebox"
												style="width: 158px;"/>
				</div>
			</form>
		</div>
		<div id="enterprise-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#certificate_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	<div id="data_dialog" class="easyui-dialog" style="width: 800px; height: 350px; padding: 10px 20px" closed="true" buttons="#data-buttons">
	<%--<div class="ftitle">审讯信息</div>--%>
	<div style="height: 100px;">
		<form id="recode_form" class="form-style" method="post" style="height: 80px;">
			<input type="hidden" id="id" name="id" />
			<table width="100%" height="100%">
				<tr>
					<td>
						<div class="fitem">
							<label>嫌疑人姓名:</label>
							<select id="serialId" name="serialId" class="easyui-combobox" style = "margin:-2px;width:170px"  required="true"></select>
						</div>
					</td>
					<td>
						<div class="fitem">
							<label>案件类型:</label>
							<select id="caseType" name="caseType" class="easyui-combobox" style = "margin:-2px;width:170px" required="true" />
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="fitem">
							<label>询问民警:</label> <input id="askNo" name="askNo" class="easyui-validatebox" onblur="esearchUser1()"
														required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><font color="red"> * 警号</font>
						</div>
					</td>
					<td>
						<div class="fitem">
							<label>记录民警:</label> <input id="recodeNo" name="recodeNo" class="easyui-validatebox" onblur="esearchUser2()"
														required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><font color="red"> * 警号</font>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="fitem">
							<label>审讯类型:</label>
							<select id="recordType" name="recordType" class="easyui-combobox" style = "margin:-2px;width:170px" required="true" />
						</div>
					</td>
					<td>
						<div class="fitem">
							<label>是否笔录:</label>
							<input type="checkbox" id="isFlage" name="isFlage" onclick="clickFlage(this)">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="fitem">
							<label>笔录类型:</label>
							<select id="flageType" name="flageType" class="easyui-combobox"  style = "margin:-2px;width:170px;" required ="true"/>
						</div>
					</td>
					<td>
						<div class="fitem">
							<label>问答模板:</label>
							<select id="recordTemp" name="recordTemp" class="easyui-combobox" style = "margin:-2px;width:170px" required ="true"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="data-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
		   onclick="saveRecord()">开始审问</a> <a href="#" class="easyui-linkbutton"
											  iconCls="icon-cancel" name="noButton"
											  onclick="javascript:$('#data_dialog').dialog('close')">关闭</a>
	</div>
</div>
	
	<!-- toolbar -->
	<div id="certificateToolbar" style="padding:5px;height:auto;overflow: hidden;">
		<div style="width: 100%;height: 100%;text-align: center;">
			<a class="myButton i1 m-box"  id="recordAdd"  href="javascript:void(0)" onclick="add()">
				<span>开始审讯</span>
				<div class="edges">
					<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="myButton i1 m-box" id="recordAddTd"  href="javascript:void(0)" onclick="recordAdd()">
				<span>开始审讯</span>
				<div class="edges">
					<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
		</div>
		<%--<div style="margin-bottom:5px">--%>
			<%--<a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="rescueAdd" onclick="add()">添加</a>--%>
		<%--</div>--%>
		<div>
			<form  id="search" style="float: left;margin: 3px;">
				人员名字: <input id="" name="personName" class="easyui-validatebox" style="width:200px" />
				案件: <input class="easyui-validatebox" name="caseNo" style="width:200px" />
				状态: <select class="easyui-combobox" name="status"  style="width:200px" >
						<option value="" selected>全部</option>
						<option value="0">初始化</option>
						<option value="1">审讯中</option>
						<option value="3">已完成</option>
					  </select>
				<!-- 办案场所:<input id="interrogatearea" name="areaId" class="easyui-combobox" data-options="" style="width: 180px;" editable="false"> -->
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
			</form>
		</div>
	</div>
<a href="" id="load" target="_blank" style="display: none;"><span></span></a>
<div id="userNoInfo" class="easyui-dialog" closed="true">
	<input type="text" id="userNo" name="userNo" />
</div>
</body>
</html>