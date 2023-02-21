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
    <%--<link href="../../evaluation/css/zeroModal.css" rel="stylesheet">
    <link href="../../evaluation/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    </script>
    <script src="../../evaluation/js/plugins/sweetalert/sweetalert.min.js"></script>--%>
    <script type="text/javascript" src="lawdoc.js"></script>
    <style>
        .zeromodal-container button{
            margin-right:20px;
        }
    </style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">

<div data-options="region:'center',split:true"
     style="width: 70%; height: 100%;">
    <table id="lawdocgrid"></table>
</div>

<div data-options="region:'east',split:true" style="width: 30%;">
    <table id="lawdocDetailgrid" ></table>
</div>

<!-- toolbar -->
<div id="lawdocToolbar" style="padding:5px;height:auto">
    <div>
        姓名:<input id="s_lawdoc_personname" class="easyui-textbox" style="width:100px;height: 23px">
        证件号码:<input id="s_lawdoc_certifacateno" class="easyui-textbox" style="width:100px;height: 23px">
        办案场所:<input type="text" id="s_areaId" name="areaId" class="easyui-combobox">
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-search" onclick="lawdocSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-clear" onclick="doClear()">清除</a>
    </div>
</div>

<div id="lawdocDetailToolbar" style="padding:5px;height:auto">
</div>
<div>
    <form id="lawdocInfo" class="form-style" method="post" action="../../lawdocProcess/download.do" accept-charset="UTF-8'">
        <input type="hidden" id="number" name="number" />
        <input type="hidden" id="userId" name="userId" />
        <input type="hidden" id="serialNo" name="serialNo" />
        <input type="hidden" id="serialId" name="serialId" />
        <input type="hidden" id="dataId" name="dataId" />
        <input type="hidden" id="type" name="type" />
        <input type="hidden" id="name" name="name" />

    </form>

    <%--<form id="exportForm" method="post" action="../../jxkp/download.do">
        &lt;%&ndash;<input id="exportSerialId" name="caseNo" type="hidden"/>&ndash;%&gt;
        <input type="hidden" id="caseNo" name="caseNo" />
    </form>--%>
</div>


<div>
    <form id="lawdocInfo_checkbody" class="form-style" method="post" action="download.do" accept-charset="UTF-8'">
        <input type="hidden" id="number1" name="number" />
       <%-- <input type="hidden" id="name" name="name" />
        <input type="hidden" id="type" name="type" />--%>
        <input type="hidden" id="userId1" name="userId" />
        <input type="hidden" id="checkbodyId" name="checkbodyId" />
    </form>
</div>
<!--
<div id="showView">
      <iframe id="myIFramePrint" src="" align="center" style="width: 1200px;height: 700px;"/>
</div>
    -->
</body>
</html>