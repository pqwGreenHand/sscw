<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zhixin.zhfz.common.entity.SessionInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>test Example</title>
<%
	String easyui_version = "jquery-easyui-1.7.0";

	SessionInfo role_info = (SessionInfo) request.getSession().getAttribute("sessionInfo");

%>
<link rel="stylesheet" href="openlayer/bootstrap.min.css">
<link rel="stylesheet" href="openlayer/ol.css" type="text/css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/js/<%=easyui_version%>/themes/gray/easyui.css">

<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/gridbutton.css">
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/<%=easyui_version%>/jquery.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/<%=easyui_version%>/jquery.easyui.min.js"></script>

<script src="openlayer/bootstrap.min.js"></script>
<script src="openlayer/ol.js"></script>
<script src="common/color.js"></script>
<script type="text/javascript">
var servletPath="<%=request.getServletPath()%>";
var contextPath="<%=request.getContextPath()%>";
</script>

</head>
<body>

	    <div id="map" class="map" style='width:100%;height:100%'></div>
	    <!-- style='width:1080px;height:800px;' -->


<div id="win"></div>  
<script src="jingwuMap.js"></script>
</body>
</html>