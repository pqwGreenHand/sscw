<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <%@page import="java.text.SimpleDateFormat"%>
  <%@page import="java.util.Calendar"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
	java.util.Date dateleft1 =new java.util.Date();
	SimpleDateFormat smfleft = new SimpleDateFormat("yyyy-MM-dd");
	String dateleft = smfleft.format(dateleft1); 
	
	Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.MONTH, -1);    //得到前一个月
	        int year = calendar.get(Calendar.YEAR);
	        int month = calendar.get(Calendar.MONTH)+1; 
	        int day = calendar.get(Calendar.DATE); 
	        
	        String dayStr="";
	        if(day<10){
	        	dayStr="0"+day;
	        	System.out.print(dayStr);
	        }else{
	        	dayStr=""+day;
	        }
	        String beginTime = year+"-"+month+"-"+day;
	        System.out.print("------------------"+dayStr);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据导出</title>
<%@ include file="../../../common/common-head.jsp"%>
<script type="text/javascript" src="export.js"></script>
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
			 <input type="button" id="securityAdd" name="tijiao" value="数据导出" class="bc_on9" onclick="wsexport()"/>
			<!--  <input type="button" id="securityAdd" name="tijiao" value="看押记录" class="bc_on9" onclick="exportData2()"/> -->
		</div>
			姓名: <input id="personName" class="easyui-validatebox" style="width: 100px">&nbsp;&nbsp;&nbsp;
				性别:
				<select class="easyui-combobox" id="personSex" name="personSex"
						style="width: 100px;">
						<option value="" selected="selected">全部</option>
						<option value="1">男</option>
						<option value="2">女</option>
				</select>&nbsp;&nbsp;&nbsp;
				案件性质:
				<select class="easyui-combobox" id="caseType" name="caseType"
						style="width: 100px;">
						<option value="" selected="selected">全部</option>
						<option value="2">刑事</option>
						<option value="1">行政</option>
				</select>&nbsp;&nbsp;&nbsp;
				案件类型: <input id="caseProperties" class="easyui-validatebox" style="width: 200px"> &nbsp;&nbsp;&nbsp;
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
		<input type="text" id="tempString" name="tempString">
		<input type="text" id="outBetweenTime1" name="outBetweenTime1">
		<input type="text" id="outBetweenTimeMax1" name="outBetweenTimeMax1">
	</form>
	<!-- 看押记录表单 -->
	<form id="exportEscortForm" name="exportEscortForm" method="post" action="../../export/exportEscortExcel.do">
		<input type="text" id="kyBeginTime" name="kyBeginTime">
		<input type="text" id="kyEndTime" name="kyEndTime">
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
	
	<!-- 选择时间段 -->
		 <div id="exportExce2_dialog" class="easyui-dialog" style="width: 520px; height: 300px; padding: 10px 20px" closed="true" buttons="#dlg-buttons3">
		<div class="ftitle" align="center">选择时间段</div>
			<table id="cellName_datagrid">
				<tr>
					<td>开始时间 :<input id="kyBeginTimeData" name="kyBeginTime" type="text" class="easyui-datebox" required="true" value="<%=dateleft%>"></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>结束时间 :<input id="kyEndTimeData" name="kyEndTime" type="text" class="easyui-datebox" required="true" value="<%=dateleft%>"></td>
				</tr>
			</table>
		<div id="dlg-buttons3">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton" onclick="savekyData()" >导出</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton" onclick="javascript:$('#exportExce2_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	
  <%-- <div id="exportData_dialog" class="easyui-layout" style="width:850px;height:620px;">
	    <div data-options="region:'north',title:'基本信息',split:true" style="height:130px;padding-top:3px">
			<form id="person_form" method="post">
			<input type="hidden" id="personId" name="personId" />
			<table id="personTable" align="left">
				<tr><!-- <input id="name" name="personName" class="easyui-textbox" required="true"/> -->
					<td>姓名:</td><td><input id="name" name="personName" class="easyui-textbox" required="true"/></td>
					<td>性别:</td><td>
						<select id="sex" class="easyui-combobox" name="personSex" style="width: 150px;" required="true">
							<option value="0">男</option>
							<option value="1">女</option>
						</select>
				  </td>
				 
				</tr>
				<tr>
				 <td>证件类型:</td><td><input id="certificateTypeId" name="certificateTypeId" class="easyui-combobox" required="true" >
				  </td>
				 <td>证件号码:</td><td><input id="personCertificateNo" name="personCertificateNo" class="easyui-textbox" required="true" >
				  </td>
				  <td colspan="4"></td>
				</tr>
				<tr>
				<td colspan="5"></td>
				<td align="right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
					onclick="savePerson()">保存</a> 
				</td></tr>
			</table>
			</form>
			
	
	    </div>   
	    <div data-options="region:'south',title:'安检信息',split:true" style="height:338px;">
	    	<form id="security_form" method="post">
            <table id="securityTable" pagination="true" fitColumns="true" singleSelect="true" width="100%">
            <tr><td align="left" colspan="4">
            <input type="hidden" id="securityId" name="securityId" /></td></tr>
        <thead align="left">
        <tr>
        <td align="left" >
            <label>检查类型:<font color="red">*</font></label>
            </td> <td colspan="3">
            <input type="radio"  value='0' name="editType" id="ecommon"/>一般检查 &nbsp;&nbsp;
            <input type="radio"  value='1' name="editType" id="etotal"/>全面检查
        </td>
        </tr>
        <tr >
        <td align="left" >
                <label>自述有无疾病、伤情:</label>
                </td> <td colspan="3">
                <textarea id="editMedicalHistory" name="meditMedicalHistory" rows="2" cols="60" class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
        </td>
        </tr>
        <tr>
        <td align="left" >
        	<label>特殊体貌特征:</label>
        	</td> <td colspan="3">
       		<textarea id="editPhysicalDescription" name="editPhysicalDescription" rows="2" cols="60" class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
         </td>
        </tr>
        <tr>
        <td align="left">
        	<label>检查发现伤情情况:</label>
        	</td> <td colspan="3">
       		<textarea id="editInjuryDescription" name="editInjuryDescription" rows="2" cols="60" class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
         </td>
        </tr>
        <tr>
        <td align="left" >
        	<label>携带危险品情况:</label>
        	</td> <td colspan="3">
       		<textarea id="editDangerous" name="editDangerous" rows="2" cols="60" class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
         </td>
          </tr>
        <tr>
        <td align="left" >
        	<label>其他说明情况:</label>
        	</td> <td colspan="3">
       		<textarea id="editOtherDescription" name="editOtherDescription" rows="2" cols="60" class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
         </td>
        </tr>
        <tr><td colspan="2"></td>
				<td align="right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
					onclick="saveSecurity()">保存</a> 
				</td><td></td></td></tr>
        </thead>
        </table>
        </form>
	    </div>   
	    <div data-options="region:'center',title:'入出区信息'" style="height:180px;">
	    	<form id="entrance_form" method="post">
			<input type="hidden" id="serialId" name="serialId" />
			<input type="hidden" id="userId" name="userId" />
			<table id="entranceTable" align="left">
				<tr>
					<td>民警姓名:</td><td><input id="policeman" name="policeman" class="easyui-validatebox" style="width: 104px;" required="true" ></td>
					<td>民警警号:</td><td><input id="policeNo" name="policeNo" onblur="finduser1()" class="easyui-validatebox" style="width: 110px;" required="true" ></td>
				
				</tr>
				<tr>
					<td>办案单位:</td><td><input id="organizationId" name="organizationId" class="easyui-combobox"  style="width: 105px;" required="true" ></td>
					<td>案件性质:</td><td>
						<select style="width: 110px;" id="caseType" name="caseType" onchange="changeValue(this.value);"  required="true" ">
							<option value="0">刑事</option>
							<option value="1">行政</option>
						</select>
						</td>
					<td>主案别:</td><td colspan="3">
						<input  id="crimeTypeId" name="crimeTypeId"  style="width: 170px; height: 28px" class="easyui-combobox" required="true"/>
				  </td>
				  
				</tr>
				<tr>
				 <td>出区类型:</td>
				 <td>
					<select id="edataType" name="edataType" onchange="getEditReason();"  style = "width:110px;"    required="true"> 
                            <option value="">选择出区类型 </option>
                            <option value="0" >正常出区 </option> 
                            <option value="1">临时出区</option>
                            <option value="2">特殊出区</option> 
                         </select>
				  </td>
				  <td>出区去向:</td>
				  <td>
				  <select id="edirection" name="edirection"  style = "width:170px;"   required="true"> 
                          <option value="">选择去向 </option> 
                     </select> 
				  </td>
				  <td>裁决结果:</td>
				  <td>
				  <select id="confirmResult" name="confirmResult"  style = "width:170px;"   required="true"> 
					        <option value="">选择裁决结果</option>
                            <option value="逮捕">逮捕</option>
                            <option value="监居">监居</option>
                            <option value="排除">排除</option>
                            <option value="刑拘">刑拘</option>
                            <option value="移交">移交</option>
                            <option value="在逃羁押">在逃羁押</option>
                            <option value="直保">直保</option>
                            <option value="治拘">治拘</option>
                            <option value="其他">其他</option>
                          </select>
				  </td>
				</tr>
				<tr><td colspan="5"></td>
				<td align="right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
					onclick="saveEntrance()" style="margin-right:-60px;">保存</a>
				</td></tr>
			</table>
			</form>
	    </div>  
	</div>  --%>
	
	<!-- 数据导出修改   start-->
	<!-- 添加页面 -->
<div id="exportData_dialog" class="easyui-dialog"
		style="width: 400px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">导出信息</div>
		<div style="height: 150px;">
			<form id="data_form" class="form-style" method="post"
				style="height: 150px;">
				<input type="hidden" id="serialId" name="serialId" />
				<div class="fitem">
					<label>姓名:</label> <input id="personName" name="personName" class="easyui-validatebox" required="true" >
				</div>
				<div class="fitem">
					<label>性别:</label>
				<select class="easyui-combobox" id="personSex" name="personSex" style="width: 100px;">
					<option value="0">男</option>
					<option value="1">女</option>
				</select>
				</div>
				<div class="fitem">
					<label>证件号码:</label> <input id="personCertificateNo" name="personCertificateNo" class="easyui-validatebox" required="true" >
				</div>
				<div class="fitem">
					<label>案件性质:</label> 
						<select style="width: 100px;" id="caseType" name="caseType" onchange="changeValue(this.value);"  required="true" ">
							<option value="0">刑事</option>
							<option value="1">行政</option>
						</select>
				</div>
				<div class="fitem">
					<label>主案别:</label>
					<input  id="crimeTypeId" name="crimeTypeId"  style="margin: -2px; width: 170px; height: 28px" class="easyui-combobox" required="true"/>
				</div>
				<div class="fitem">
					<label>出区类型:</label>
						<select id="edataType" name="edataType" onchange="getEditReason();"  style = "margin:-2px;width:170px;"    required="true"> 
                            <option value="">选择出区类型 </option>
                            <option value="0" >正常出区 </option> 
                            <option value="1">临时出区</option>
                            <option value="2">特殊出区</option> 
                         </select>
				</div>
				<div class="fitem">
					<label>出区去向:</label>
					 <input id="outPlace" name="outPlace" class="easyui-validatebox" required="true">
					 <select id="edirection" name="edirection"  style = "margin:-2px;width:170px;"   required="true"> 
                          <option value="">选择去向 </option> 
                     </select> 
				</div>
				<div class="fitem">
					<label>裁决结果:</label>
                          <select id="confirmResult" name="confirmResult"  style = "margin:-2px;width:100px;"   required="true"> 
					        <option value="">选择裁决结果</option>
                            <option value="逮捕">逮捕</option>
                            <option value="监居">监居</option>
                            <option value="排除">排除</option>
                            <option value="刑拘">刑拘</option>
                            <option value="移交">移交</option>
                            <option value="在逃羁押">在逃羁押</option>
                            <option value="直保">直保</option>
                            <option value="治拘">治拘</option>
                            <option value="其他">其他</option>
                          </select>
				</div>
				<div class="fitem">
					<label>办案民警:</label> <input id="policeman" name="policeman" class="easyui-validatebox" required="true" >
				</div>
				<div class="fitem">
					<label>办案单位:</label> <input id="workSpace" name="workSpace" class="easyui-validatebox" required="true" >
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
				onclick="saveEditExprtData()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" name="noButton"
				onclick="javascript:$('#exportData_dialog').dialog('close')">关闭</a>
		</div>
	</div>
	<!-- 数据导出修改 end -->	
</body>
</html>