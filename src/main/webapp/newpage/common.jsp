<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="always" name="referrer">

<link rel="shortcut icon" href="${ctx}/newpage/static/images/loginsj222.png" type="image/x-icon"/>

<link href="${ctx}/newpage/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${ctx}/newpage/static/plugin/easyui/themes/insdep/easyui1.css" rel="stylesheet" type="text/css">
<link href="${ctx}/newpage/static/plugin/easyui/themes/insdep/easyui_animation.css" rel="stylesheet" type="text/css">
<link href="${ctx}/newpage/static/plugin/easyui/themes/insdep/easyui_plus.css" rel="stylesheet" type="text/css">
<link href="${ctx}/newpage/static/plugin/easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet"
      type="text/css">
<link href="${ctx}/newpage/static/plugin/easyui/themes/insdep/icon.css" rel="stylesheet" type="text/css">
<link href="${ctx}/newpage/static/plugin/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<script src="${ctx}/newpage/static/plugin/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${ctx}/newpage/static/plugin/easyui/jquery.easyui-1.5.1.min.js" type="text/javascript"></script>
<script src="${ctx}/newpage/static/plugin/easyui/themes/insdep/jquery.insdep-extend.min.js"
        type="text/javascript"></script>
<script src="${ctx}/newpage/static/plugin/easyui/jquery.easyui.custom.extend.js" type="text/javascript"></script>

<script src="${ctx}/newpage/static/plugin/layer/layer.js" type="text/javascript"></script>
<script src="${ctx}/newpage/static/plugin/template/template.js" type="text/javascript"></script>

<script src="${ctx}/newpage/static/js/utils.js" type="text/javascript"></script>
<script src="${ctx}/newpage/static/js/ztab.js" type="text/javascript"></script>
<%@ page import="com.zhixin.zhfz.common.entity.SessionInfo" %>
<script>
    $(function () {
        $.get("${ctx}/common/getSessionInfo.do", function (data) {
            var sessionObj = eval('(' + data + ')');
            if (sessionObj.user == null) {
                $.messager.alert('错误', '网络已断开，请重新登录', 'warning', function () {
                    location.href = '${ctx}/newpage/login.jsp';
                });
            }
        });
    });
    function getTimeSign() {
        return timestamp = new Date().getTime();
    }
</script>