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
                <div class="box" id="box_area1" style="width: 240px;">
                    <div class="box-title">
                        <span>涉案财物智能储物柜</span>
                    </div>
                    <div class="list">
                        <div class="row">
                            <div class="item blank" id="box274" onclick="showin('274','01')">
                                <div class="item-in" id="item274">
                                    <i>01</i>
                                </div>
                            </div>
                            <div class="item blank" id="box275" onclick="showin('275','02')">
                                <div class="item-in" id="item275">
                                    <i>02</i>
                                </div>
                            </div>
                            <%--<div class="item blank" id="box276" onclick="showin('276','03')">
                                <div class="item-in" id="item276">
                                    <i>03</i>

                                </div>
                            </div>--%>
                        </div>
                        <div class="row">
                            <div class="item blank" id="box276" onclick="showin('276','03')">
                                <div class="item-in" id="item276">
                                    <i>03</i>
                                </div>
                            </div>
                            <div class="item blank" id="box277" onclick="showin('277','04')">
                                <div class="item-in" id="item277">
                                    <i>04</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank" id="box278" onclick="showin('278','05')">
                                <div class="item-in" id="item278">
                                    <i>05</i>
                                </div>
                            </div>
                            <div class="item blank" id="box279" onclick="showin('279','06')">
                                <div class="item-in" id="item279">
                                    <i>06</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank" id="box280" onclick="showin('280','07')">
                                <div class="item-in" id="item280">
                                    <i>07</i>
                                </div>
                            </div>
                            <div class="item blank" id="box281" onclick="showin('281','08')">
                                <div class="item-in" id="item281">
                                    <i>08</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank" id="box282" onclick="showin('282','09')">
                                <div class="item-in" id="item282">
                                    <i>09</i>
                                </div>
                            </div>
                            <div class="item blank" id="box283" onclick="showin('283','10')">
                                <div class="item-in" id="item283">
                                    <i>10</i>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="item blank da left" id="box284" onclick="showin('284','11')">
                                <div class="item-in" id="item284">
                                    <i>11</i>
                                </div>
                            </div>
                            <div class="item blank da left" id="box285" onclick="showin('285','12')">
                                <div class="item-in" id="item285">
                                    <i>12</i>

                                </div>
                            </div>
                        </div>
                    </div>
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