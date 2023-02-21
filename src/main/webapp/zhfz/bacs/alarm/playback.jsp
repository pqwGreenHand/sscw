<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

    String xml = request.getParameter("xml");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");

%>

<html>
<head>
<title>视频回放</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common-head.jsp"%>
    <script type="text/javascript">
        var startpath = '<%=basePath%>';
        var xml = <%=xml%>;
        var startTime= '<%=startTime%>';
        var endTime = '<%=endTime%>';
    </script>
<script type="text/javascript" src="js/md5.js"></script>
<script type="text/javascript" src="playback.js"></script>
    <script type="text/javascript"  src="js/jquery.min_v1.0.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;" onunload="UninitSpb()">

<div class="ActiveX" style="height:100%;width:100%;alignment: center">
        <object classid="clsid:B9BF1ACC-28DD-431D-83BF-5B9EF96F1101" id="spb"  width="650px" height="500px" />
</div>
</body>
</html>