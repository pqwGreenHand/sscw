<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>404错误</title>
<link href="<%=request.getContextPath()%>/css/errorstyle.css" rel="stylesheet" type="text/css"/>
</head>

<body style="overflow-y:hidden;background: none">
	<div class="error_a">
        <img src="<%=request.getContextPath()%>/css/images/error/error_a.png"/><br/>
        <span>对不起，系统找不到您要访问的页面！</span></br><span>Error, Page Not Found!</span>
    </div>
	<div class="loginfooter">Copyright © 2019<br/>版权所有 上海致昕信息科技有限公司  </div>
</body>
</html>
