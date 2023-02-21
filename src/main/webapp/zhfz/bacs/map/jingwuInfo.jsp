<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zhixin.zhfz.common.entity.SessionInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>test Example</title>
<%
	String easyui_version = "jquery-easyui-1.4.3";
	SessionInfo role_info = (SessionInfo) request.getSession().getAttribute("sessionInfo");
%>

<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/js/jingwuMap/easyui.css">

<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/gridbutton.css">
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/jingwuMap/jquery.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/jingwuMap/jquery.easyui.min.js"></script>

<script type="text/javascript"	src="<%=request.getContextPath()%>/js/common.js"></script>
<script src="<%=request.getContextPath()%>/js/jingwuMap/color.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jingwuMap/echarts.js"></script>

<script src="jingwuInfo.js"></script>

<script type="text/javascript">
var servletPath="<%=request.getServletPath()%>";
var contextPath="<%=request.getContextPath()%>";
</script>

</head>
<body class="easyui-layout">    
    <div data-options="region:'center'" style="padding:5px;background:#eee;">
    	<div id="echartDiv1" style="height:50%;"></div> 
    	<div id="echartDiv2" style="height:50%;"></div> 
    </div>
	<div id="tabBox" class="easyui-tabs"  style="width:220px;"
		data-options="region:'east',iconCls:'icon-reload',split:false,collapsible:false">   
    
	</div> 
</body>
</html>