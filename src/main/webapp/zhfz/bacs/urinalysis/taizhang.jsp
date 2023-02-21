<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--<%@page import="com.zhixin.rd.interrogate.web.entity.LawDocCategory"%>--%>
<%--<%@page import="com.zhixin.rd.interrogate.web.service.lawdoc.ILawDocCategoryManager"%>--%>
<%--<%@page import="com.zhixin.rd.interrogate.web.common.SpringContextUtil"%>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="taizhang.js"></script>
<!-- <script type="text/javascript" src="ajxx.js"></script> -->


<title></title>
</head>
<body>
<form id="lawdocInfoinframe" class="form-style" method="post" action="download.do" accept-charset="UTF-8'">
<input id="id" name="id" type="hidden" value="<%=request.getParameter("id") %>" />
<div id="dlg-buttons">
			<a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:80px;height:40px"
					onclick="downloadFile()">下载</a>
			<a name="noButton"  href="#" class="easyui-linkbutton" iconCls="icon-cancel" style="width:80px;height:40px"
				onclick="docClose();">关闭</a>	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;font-size:20px;">*请下载后打印</span>
		</div>
</form>
<div id="showView">
     <iframe id="myIFramePrint" name="myIFramePrint" src="" align="middle" style="width:100%; height: 600px;" scrolling="yes" ></iframe>
</div>

</body>

<script type="text/javascript" language="javascript">
    function iFrameHeight() {
		var ifm= document.getElementById("myIFramePrint");
		if(ifm != null ) {
			ifm.height =window.screen.availHeight;//屏幕可用工作区高度
		}
    }
</script> 
</html>