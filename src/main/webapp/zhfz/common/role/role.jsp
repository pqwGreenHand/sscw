<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <%@ include file="../common-head.jsp" %>
    <script type="text/javascript" src="role.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../common-loading.jsp" %>
<div data-options="region:'center',split:true" style="width: 65%;">
    <table id="role_datagrid" class="easyui-datagrid"></table>
</div>

<div data-options="region:'east',split:true" style="width: 500px;"
     class="easyui-tabs">
    <div data-options="region:'east',split:true,border:false" style="width: 35%; height: 100%;" title="权限">
        <div class="easyui-panel" title="权限"
             style="height: 100%; overflow-y: hidden;">
            <div style="height: 5%; padding-top: 3px;">
                &nbsp;&nbsp; <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="allSelect(true)" name="noButton" id="allSelectTrue">全选选中</a>&nbsp;&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="allSelect(false)" name="noButton" id="allSelectFalse">全部取消</a>
                &nbsp;&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveRoleAuthority()" id="saveRoleAuthority">保存</a>
            </div>
            <hr>
            <div style="height: 90%; overflow-y: auto;">
                <ul id="authority-tree" class="easyui-tree"></ul>
            </div>
        </div>
    </div>
</div>

<!-- toolbar -->
<div id="toolbar" style="padding: 5px; height: auto">
    <div>
        <a href="#" id="addRole" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addRole()">添加</a>
        <label>角色名:</label>
        <input id="s_name" class="easyui-textbox" style="width: 80px"> <a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" id="doSearch" onclick="doSearch()">搜索</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton" id="doClear" onclick="doClear()">清空</a>
    </div>
</div>
<div id="role_dialog" class="easyui-dialog"
     style="width: 500px; height: 550px; padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle" align="center">角色信息</div>
    <form id="role_form" class="form-style" method="post" style="height: 270px;">
        <input type="hidden" id="id" name="id"/>
        <div class="fitem">
            <label>角色名:</label>
            <input id="name" name="name" class="easyui-validatebox" required="true" data-options="validType:'maxLength[128]'" style="width: 200px">
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>描述:</label>
            <input id="description" name="description" class="easyui-validatebox" data-options="validType:'maxLength[128]'" style="width: 200px">
        </div>
        <div class="fitem">
            <label>办案中心数据权限:</label>
            <select id="bacsDataAuth" class="easyui-combobox" name="bacsDataAuth" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="1">本办案场所</option>
                <option value="2">本办案场所及下级办案场所</option>
                <option value="3">上级办案场所及其下级办案场所</option>
                <option value="4">全部</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>涉案财物数据权限:</label>
            <select id="sacwDataAuth" class="easyui-combobox" name="sacwDataAuth" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="1">本涉案财物场所</option>
                <option value="2">本涉案财物场所及下级涉案财物场所</option>
                <option value="3">上级涉案财物场所及其下级涉案财物场所</option>
                <option value="4">全部</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>卷管中心数据权限:</label>
            <select id="jzglDataAuth" class="easyui-combobox" name="jzglDataAuth" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>考评数据权限:</label>
            <select id="jxkpDataAuth" class="easyui-combobox" name="jxkpDataAuth" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>受立案数据权限:</label>
            <select id="slaDataAuth" class="easyui-combobox" name="slaDataAuth" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <%--<option value="7">上级部门及其下级部门</option>绩效考评此处无本部门及下级部门--%>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>综合管理数据权限:</label>
            <select id="zhglDataAuth" class="easyui-combobox" name="zhglDataAuth" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>办案场所配置数据权限:</label>
            <select id="bacsConfigure" class="easyui-combobox" name="bacsConfigure" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>涉案财物配置数据权限:</label>
            <select id="sacwConfigure" class="easyui-combobox" name="sacwConfigure" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>卷宗管理配置数据权限:</label>
            <select id="jzglConfigure" class="easyui-combobox" name="jzglConfigure" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>绩效考评配置数据权限:</label>
            <select id="jxkpConfigure" class="easyui-combobox" name="jxkpConfigure" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>受立案配置数据权限:</label>
            <select id="slaConfigure" class="easyui-combobox" name="slaConfigure" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
        <div class="fitem">
            <label>系统配置数据权限:</label>
            <select id="xtConfigure" class="easyui-combobox" name="xtConfigure" required="true" data-options="editable:false,panelHeight:'auto'" style="width: 211px">
                <option value="0">本人</option>
                <option value="5">本部门</option>
                <option value="6">本部门及下级部门</option>
                <option value="7">上级部门及其下级部门</option>
                <option value="4">全部</option>
            </select>
            <font color="red">*</font>
        </div>
    </form>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-ok" onclick="saveData()">保存</a> <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton" onclick="javascript:$('#role_dialog').dialog('close')">取消</a>
    </div>
</div>

</body>
</html>