<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>人员管理</title>
    <%@ include file="../common.jsp" %>
    <script type="text/javascript" src="user.js"></script>
    <%--<script type="text/javascript" src="ajxx.js"></script>--%>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="s_real_name" class="easyui-textbox" data-options="label:'姓名'" style="width:150px;"/>
        <input id="s_certificate_no" class="easyui-textbox" data-options="label:'证件号码'" style="width:220px;"/>
        <a href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
        <a href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;">清&nbsp;除</a>
        &nbsp;&nbsp;
      <%--  <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton button-line-blue" iconCls="icon-add"
           plain="true">添加</a>--%>

        <div id="systemShow">
            <a href="javascript:void(0)" onclick="useradd('1')" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">用户</a>
            <a href="javascript:void(0)" onclick="useradd('2')" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">角色</a>
            <a href="javascript:void(0)" onclick="useradd('3')" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">部门</a>
        </div>

    </div>

    <div data-options="region:'center',border:false" style="height:100%">
        <table id="dg" style="width:100%;height:100%;">
        </table>
        <div id="tb" style="padding:2px 5px;">
            <%--<a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-edit" href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-delete" href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>--%>
            <%--<a href="javascript:void(0)" onclick="getRoles()" class="easyui-linkbutton" iconCls="icon-user-config"
               plain="true">角色设置</a>--%>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true">剪切</a>--%>

        </div>
    </div>
</div>
<div id="dlg"></div>
<div id="ajdlg"></div>
<script>

</script>
<style scoped>
    #form tr {
        line-height: 35px;
    }

    #form tr input,select{
        width:220px;
    }
</style>
</body>
</html>