<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serialid = request.getParameter("serialid");
	String serialNo = request.getParameter("serialNo");
	String type = request.getParameter("type");
%>

<html>
<head>
<title>高拍仪</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript">
	var startpath = '<%=basePath%>';
	var serialid = <%=serialid%>;
	var serialNo= '<%=serialNo%>';
	var type= <%=type%>;
    var basePath = '<%=basePath%>';
    /* alert("startpath="+startpath); */
</script>
<script type="text/javascript" src="photo.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;" onunload="Unload()">

   <form id="photo" enctype="multipart/form-data" action="ashx/login.ashx" method="post">
       <table class="main_form1">
       <tr><td align="center">拍照框</td><td></td></tr>
       <tr>
       <td >
       	<!-- 拍照框 -->
        <div id="in_view1">
 			<object id="view1" type="application/x-eloamplugin" width="250" height="250" name="view"></object> 
	    </div>
       </td>
       <td >
        <!-- 照片框 -->
        <div id="in_thumb1">
			<object id="thumb1" type="application/x-eloamplugin" width="210" height="150" name="thumb"></object><br />
		</div>
       </td>
       </tr>
       </table>
       
       <div class="photo_botton">
       		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"onclick="inScan()">拍照</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"	onclick="uploadphoto()">上传</a>
       </div> 
   </form>    

   </body>
</html>