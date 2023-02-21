<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>500错误</title>
<link href="<%=request.getContextPath()%>/css/errorstyle.css" rel="stylesheet" type="text/css"/>
</head>

<body style="overflow-y:hidden;">
	<div class="error_b">
        <img src="<%=request.getContextPath()%>/css/images/error/error_d.png"/><br/>
        <span>对不起，系统发生未知错误！</span></br><span>Error, Page Unknown Error!</span>
        <input type="button" name="xiayibu" value="返回首页" onmousemove="this.className='bc_on'" onmouseout="this.className='bc_out'"/>
    </div>
	<div class="loginfooter">Copyright Â© 2019<br/>版权所有 上海致昕信息科技有限公司 </div>
</body>
</html>
