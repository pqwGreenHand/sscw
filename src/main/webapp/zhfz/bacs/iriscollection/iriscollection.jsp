<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <link rel="stylesheet" type="text/css" href="css.css" />
    <script type="text/javascript" src="iriscollection.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>

     <div data-options="region:' west',split:true" style="width: 100%; height: 100%;">
         <table id="iris_datagrid"></table>
     </div>

    <div id="toolbar" style="height: 10%">
       <div style="margin-top: 20px;margin-left: 35px">
           <label>嫌疑人姓名:</label>
           &nbsp;&nbsp;
           <input id="personName" name="cuffNumber" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true" />
           &nbsp;&nbsp;

           <label>采集人姓名:</label>
           &nbsp;&nbsp;
           <input id="userName" name="cuffNumber" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true" />
           &nbsp;&nbsp;

           <label>案件名称:</label>
           &nbsp;&nbsp;
           <input id="ajmc" name="cuffNumber" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true" />
           &nbsp;&nbsp;
           <label>办案场所选择:</label>
           <input id="interrogatearea" name="interrogatearea" class="easyui-combobox" data-options="" style="width: 180px;" editable="false">
           <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
           <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>
       </div>
    </div>
</body>
</html>KK