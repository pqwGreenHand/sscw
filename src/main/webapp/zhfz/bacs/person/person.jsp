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
<script type="text/javascript" src="person.js"></script>
<script type="text/javascript" src="../../../js/IdcardUtil.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
	<div data-options="region:'center',split:true"
		style="width: 49%; height: 95%;">
		<table id="datagrid"></table>
	</div>
	
	<!-- toolbar -->
	<div id="toolbar" style="padding:5px;height:auto">
		<div>
                办案场所: <input id="s_area_id" class="easyui-validatebox" style="width: 150px">
				姓名: <input id="p_name" class="easyui-textbox" style="width:100px" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'">
				证件号码:<input id="p_certificateNo" class="easyui-textbox" style="width:180px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'">
				创建时间从: <input id="p_start_date" class="easyui-datetimebox" style="width:180px">
				到: <input id="p_end_date" class="easyui-datetimebox" style="width:180px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="doSearch()">搜索</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton" onclick="doClear()">清空</a>
		</div>
	</div>
	
	<!-- 添加或修改 -->
    <div id="person" class="easyui-dialog" style="width:800px;height:500px;padding:10px 20px" closed="true" buttons="#person-buttons">
        <form id="personform" method="post">
            <table id="tb" pagination="true" fitColumns="true" singleSelect="true" width="100%">
            <tr><td colspan="2" align="left"><input type="hidden" id="id" name="id" /></td></tr>
        <thead align="left">
        <tr>
        <td align="left">
            <div class="fitem">
                <label>姓名:</label>
                <input id="name" name="name" class="easyui-textbox" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/>
            </div>
        </td>
        <td align="left">
             <div class="fitem">
                <label>证件:</label>
                 <select id="certificateTypeId" name="certificateTypeId" class="easyui-combobox" style = "width:100px"  required="true"/>
                <input name="certificateNo" class="easyui-textbox" style = "width:160px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/>
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>曾用名:</label>
                <input id="oldName" name="oldName" class="easyui-textbox" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'"/>
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>绰号:</label>
                <input id="nickname" name="nickname" class="easyui-textbox" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'"/>
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>性别:</label>
                <select id="sex" name="sex" style = "margin:-2px;width:147px; height:28px" data-options="editable:false" class="easyui-combobox" required="true">
                </select>
                 
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>出生日期:</label>
               <input id="birth" name="birth" class="easyui-datebox" />
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>文化程度:</label>
                <select id="education" name="education" class="easyui-combobox" style="width: 147px"/>
                </select>
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>政治面貌:</label>
                <select id="politic" name="politic" class="easyui-combobox" style="width: 147px"/>
                </select>
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>特殊身份:</label>
                <select id="officer" name="officer" class="easyui-combobox" style="width: 147px"/>
                </select>
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>国籍:</label>
                <select id="country" name="country" class="easyui-combobox" style="width: 147px"/>
                </select>
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>民族:</label>
                <select id="nation" name="nation" class="easyui-combobox" style="width: 147px"/>
                </select>
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>户籍所在地:</label>
                <input id="censusRegister" name="censusRegister" class="easyui-textbox" data-options="validType:'maxLength[50]',invalidMessage:'最长50字节'" />
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>详细地址:</label>
                <input id="address" name="address" class="easyui-textbox"  data-options="validType:'maxLength[50]',invalidMessage:'最长50字节'"/>
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>工作单位:</label>
                <input id="job" name="job" class="easyui-textbox"  data-options="validType:'maxLength[50]',invalidMessage:'最长50字节'"/>
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>工作职务:</label>
                <input id="jobTitle" name="jobTitle" class="easyui-textbox"  data-options="validType:'maxLength[50]',invalidMessage:'最长50字节'"/>
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>手机号码:</label>
                <input id="mobile" name="mobile" class="easyui-textbox" data-options="validType:'maxLength[11]',invalidMessage:'最长11字节'" />
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>电话号码:</label>
                <input id="telephone" name="telephone" class="easyui-textbox" data-options="validType:'maxLength[12]',invalidMessage:'最长12字节'" />
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>电子邮箱:</label>
                <input id="email" name="email" class="easyui-textbox" data-options="validType:'maxLength[50]',invalidMessage:'最长50字节'"/>
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>QQ账号:</label>
                <input id="qq" name="qq" class="easyui-textbox" data-options="validType:'maxLength[13]',invalidMessage:'最长13字节'" />
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>微信账号:</label>
                <input id="weixin" name="weixin" class="easyui-textbox" data-options="validType:'maxLength[20]',invalidMessage:'最长20字节'" />
            </div>
        </td>
        </tr>
        <tr>
        <td align="left">
            <div class="fitem">
                <label>微博账号:</label>
                <input id="weibo"  name="weibo" class="easyui-textbox" />
            </div>
        </td>
        <td align="left">
            <div class="fitem">
                <label>其它互联网账号信息:</label>
                <input id="internetInfo" name="internetInfo" class="easyui-textbox" data-options="validType:'maxLength[20]',invalidMessage:'最长20字节'"/>
            </div>
        </td>
        </tr>

        <tr>
            <td align="left">
                <div class="fitem">
                    <label>警综人员编号:</label>
                    <input id="personNo" name="personNo" class="easyui-textbox" data-options="validType:'maxLength[30]',invalidMessage:'最长30字节'"/>
                </div>
            </td>
            <td align="left">
                <div class="fitem">
                    <label>办案场所:</label>
                    <select id="areaId" name="areaId" class="easyui-combobox" style="width: 147px"/>
                    </select>
                </div>
            </td>
        </tr>
        </thead>
        </table>
        </form>
    </div>
    <div id="person-buttons">
        <a href="javascript:void(0)" name="noButton" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="personSave()" style="width:90px">保存</a>
        <a href="javascript:void(0)"  name="noButton" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#person').dialog('close')" style="width:90px">取消</a>
    </div>
</body>
</html>