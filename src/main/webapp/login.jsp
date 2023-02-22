<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>登录</title>
    <link rel="stylesheet" href="./js/new_common/css/style2.css"/>
    <link rel="stylesheet" type="text/css" href="./css/common.css">
    <link rel="stylesheet" type="text/css" href="./css/main2.css">
    <link rel="stylesheet" type="text/css" href="./js/jquery-easyui-1.7.0/themes/bf/easyui2.css"/>
    <script type="text/javascript" src="./js/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="./js/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="./js/jquery.cookie.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
    <script type="text/javascript" src="js/extraFunction.js"></script>
    <script type="text/javascript" src="./js/common3.js"></script>
    <script type="text/javascript">
        $(function () {
            $.messager.progress({
                title: '请等待',
                msg: '正在登陆中...'
            });
            window.location.href = 'newpage/login.jsp';
            $.messager.progress('close');
            //防止页面后退
            history.pushState(null, null, document.URL);
            window.addEventListener('popstate', function () {
                history.pushState(null, null, document.URL);
            });
        })
    </script>
</head>
<body>
<div class="page login">

</div>
</body>