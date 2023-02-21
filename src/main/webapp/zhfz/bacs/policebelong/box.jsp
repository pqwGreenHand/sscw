<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <%
        String ssareaid = request.getParameter("ssareaid");
    %>
    <script type="text/javascript" src="box.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="../../common/common-head.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script type="text/javascript" src="../../../js/layer/layer.js"></script>
    <script>
        $(function () {
            mHead();
        });

        function mHead() {
            $(".m-head .user").hover(function () {
                $(this).addClass("open");
            }, function () {
                $(this).removeClass("open");
            })
            $(".m-head .user").on("mousemove", "*", function () {
                $(this).parents(".user").addClass("open");
            })
        }
    </script>
    <title>随身物品</title>
</head>
<body style="width: 100%; height: 100%;" onload="loadinfo();">
<input id="ssareaid" type="hidden" name="ssareaid" value="<%=ssareaid%>"></input>
<div class="page locker-box">
    <div class="main">
        <div class="control-bar">
            <div style=" overflow: hidden;width: 562px;left: 50%;margin-left: -281px;position: absolute;">
            <button class="m-btn-2" style="background: #08926a;" id="belongin" onclick="goBelongin('<%=ssareaid%>');">
                <span><i style="background-image: url(image/locker-8.png);"></i>存物</span>
            </button>
            <button class="m-btn-2" style="background: #ad6d31;" id="belongout" onclick="goBelongout();">
                <span><i style="background-image: url(image/locker-9.png);"></i>取物</span>
            </button>
            <button class="m-btn-2" style="background: #0f5896;" id="belongset" onclick="goBelongManage('<%=ssareaid%>');">
                <span><i style="background-image: url(image/locker-10.png);" ></i>管理</span>
            </button>

            <div class="tip i1">
                <i></i>
                <span>可储存</span>
            </div>
            <div class="tip i2">
                <i></i>
                <span>已储存</span>
            </div>
            <div class="tip i3">
                <i></i>
                <span>不可用</span>
            </div>
        </div>
        </div>
        <div class="box-all">
            <div class="box-all-in">
                <div class="box" id="box">
                </div>
            </div>
        </div>
    </div>
    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" src="image/page-1.png">
</div>
<script src="../../../js/plugs/sky/particles.min.js"></script>
<script src="../../../js/plugs/sky/app.js"></script>
<script>
    var ssareaid = $("#ssareaid").val();
</script>
</body>
</html>