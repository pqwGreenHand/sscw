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
    <%@ include file="../../common/common-head.jsp"%>
    <script src="downLoadTask.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<div data-options="region:'center',split:true" style="width: 30%;">
    <div id="searchDiv" style="margin-left: 10px;margin-top: 50px">
        案件名称:
        <span style="margin-left: 10px">&nbsp;</span>
        <input id="s_case_name" class="easyui-validatebox" style="width: 150px">
        <span style="margin-left: 10px">&nbsp; </span>
        嫌疑人姓名:
        <span style="margin-left: 10px">&nbsp;</span>
        <input id="s_person_name" class="easyui-validatebox" style="width: 150px">
        <span style="margin-left: 10px"> &nbsp;</span>
        <p style="height: 10px"></p>
        案发时间:
        <span style="margin-left: 10px">&nbsp;</span>
        <input type="text" id="start_date" name="start" class="easyui-datetimebox" style="width: 160px"/>
        <span style="margin-left: 10px">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 到:</span>
        <span style="margin-left: 10px">&nbsp;</span>
        <input type="text" id="end_date" name="end" class="easyui-datetimebox" style="width: 160px;"/>
        <p style="height: 10px"></p>
        办案场所:
        <span style="margin-left: 10px">&nbsp;</span>
        <input id="s_area_id" class="easyui-combobox"  style="width: 160px">
        <a href="#" class="easyui-linkbutton" style="margin-left: 50px" iconCls="icon-search" name="noButton" onclick="doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear"  onclick="doClear()" name="noButton">清除</a>
    </div>
    <ul id="caseAndPersonTree" class="easyui-tree" style="border-top: gray solid 1px;margin-top: 10px;height: auto "></ul>
</div>

<div data-options="region:'east',split:true" style="width: 70%;">
    <table id="downLoadTaskDatagrid" class="easyui-datagrid"></table>
</div>
</body>
</html>