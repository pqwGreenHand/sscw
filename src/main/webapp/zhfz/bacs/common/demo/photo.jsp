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
	<%@ include file="../../../common/common-head.jsp"%>
<script type="text/javascript">
	var startpath = '<%=basePath%>';
	var serialid = <%=serialid%>;
	var serialNo= '<%=serialNo%>';
	var type= <%=type%>;
    var basePath = '<%=basePath%>';
    /* alert("startpath="+startpath); */
</script>
  <!-- SDK封装 -->
    <script src="js/globeVar.js"></script>
    <script src="js/mainH5.js"></script>
    <script src="js/WebSocket.js"></script>

    <!--页面逻辑-->
   <script src="js/singleCamera.js"></script> 
   
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;" >


    <div>
		
       <form id="photo" enctype="multipart/form-data" action="ashx/login.ashx" method="post">
			    <table class="main_form1">
			       <tr><td align="center">拍照框</td><td></td></tr>
			       <tr>
			       <td >
				       	<!-- 拍照框 -->
				     <div id="video1">
				 			       <img id="video" style="width:506px;height:380px" >
			       </div>
	           </td>
			       <td >
				        <!-- 照片框 -->
				        <div id="parentUl1" >
						         <div align="center" style="margin-top:-20px">照片框</div>
			               <div id="parentUl" style="width:506px;height:380px">
			               	
			               	</div>
											<input type="hidden"  id="saveText" value="C:\tmp\" style="margin-left:5px;width: 150px;text-align: left;"/>	
											<input type="hidden" id="base64"/>
						     </div> 
	
			       </td>
	          </tr>
	       </table>
	     <div class="photo_botton" style="margin-left:200px">
	       		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"onclick="Capture()">拍照</a> 
			     	<a href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"	onclick="uploadphoto(0)">上传</a>
	       </div> 
      </form>    
	  </div> 
   </body>
</html>