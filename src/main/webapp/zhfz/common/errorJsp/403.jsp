<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>403错误</title>
<link href="<%=request.getContextPath()%>/css/errorstyle.css" rel="stylesheet" type="text/css"/>
</head>
<body style="overflow-y:hidden;">
	<div class="error_c">
        <img src="<%=request.getContextPath()%>/css/images/error/error_f.png"/><br/>
        <span>对不起，您没有权限访问此页面！</span></br><span>Error, Page Unauthorized!</span>
        <input type="button" name="xiayibu" value="返回首页" onmousemove="this.className='bc_on'" onmouseout="this.className='bc_out'"/>
    </div>
	<div class="loginfooter">Copyright Â© 2019<br/>版权所有  上海致昕信息科技有限公司</div>
</body>
</html>
