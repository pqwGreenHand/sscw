<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <script type="text/javascript" src="signature.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.webcam.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/swfupload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/single.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/swfupload.queue.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/fileprogress.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/handlers.js"></script>
    <link href="<%=request.getContextPath()%>/swfUpload/css/default.css" rel="stylesheet" type="text/css" />
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<%--<div style="display: none">--%>
    <span id="spanButtonPlaceholder1_swfupload">555</span>
    <input id="btnCancel1_swfupload"  value="取消上传" onclick="cancelQueue(upload_swfupload);"
           disabled="disabled" style="margin-left:8px; height:26px; font-size: 12px; width:70px;" />
<%--</div>--%>
<%--<table id="signatureDatagrid"></table>--%>
<%--<!-- toolbar -->--%>
<%--<div id="certificateToolbar" style="padding:5px;height:auto;overflow: hidden;">--%>
<%--    <div>--%>
<%--        <form  id="search" style="float: left;margin: 3px;">--%>
<%--            办案中心:<input class="easyui-combobox" id="s_area" name="sysType" style="width:200px" />--%>
<%--            <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>--%>
<%--            <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</div>--%>
</body>
</html>