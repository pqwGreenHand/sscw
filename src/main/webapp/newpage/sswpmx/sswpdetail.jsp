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
    <script type="text/javascript" src="sswpdetail.js"></script>
    <%--<script type="text/javascript" src="ajxx.js"></script>--%>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="name" class="easyui-textbox" data-options="label:'姓名'" style="width:150px;"/>
        <input id="certificate_no" class="easyui-textbox" data-options="label:'证件号码'" style="width:220px;"/>
        <a href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
        <a href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;">清&nbsp;除</a>
    </div>

    <div data-options="region:'center',border:false" style="height:40%">
        <table id="dg" style="width:100%;">
        </table>
    </div>
    <div data-options="region:'south',border:false,title:'详情'" style="height:60%;">
        <table id="dgdetail" style="width:100%;">
        </table>
    </div>

</div>
<div id="dlg"></div>
<div id="ajdlg"></div>
<div id="wpImage" style="display: none"></div>
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