
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script type="text/javascript" src="receptionentrance.js"></script>
    <script type="text/javascript" src="inReception.js"></script>
    <script type="text/javascript" src="outReception.js"></script>
    <script type="text/javascript" src="js/jquery.webcam.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">

<div class="page locker-home">
    <div class="main">

        <div class="box">
            <a id="rqButton" class="item i1 m-box" name="noButton" onclick="entrance()">
                <span>来访人员入区</span>
                <div class="edges">
                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
            <a id="cqButton" class="item i2 m-box" name="noButton" onclick="out()">
                <span>来访人员出区</span>
                <div class="edges">
                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
            <a id="rqlsButton" name="noButton" class="item i3 m-box" href="receptionHistory.jsp">
                <span>登记历史</span>
                <div class="edges">
                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
        </div>

        <div class="content m-box" >
            <div class="control-bar">
                <div class="m-input">
                    <span>姓名：</span>
                    <label>
                        <input type="text" id="name2" name="name" placeholder="请输入">
                    </label>
                </div>
                <div class="m-input">
                    <span>证件号：</span>
                    <label>
                        <input type="text" id="certificateNo2" name="certificateNo" placeholder="请输入">
                    </label>
                </div>
                <div class="m-input">
                    <span>办案场所：</span>
                    <label>
                        <input id="interrogatearea" name="interrogatearea" class="easyui-combobox" data-options="" style="width: 180px;" editable="false">
                    </label>
                </div>
                <button class="m-btn-1 blue" onclick="doSearch2()">查询</button>
                <button class="m-btn-1 blue" onclick="doClear2()">清除</button>
            </div>
            <div class="content-in">
                <div id="recrptionItems" class="list" style="overflow: auto;height: 80%;" ></div>
                <div class="paging">
                    <a name="noButton" class="btn i1" title="首页" onclick="toPage('first')"></a>
                    <a name="noButton" class="btn i2" title="上一页" onclick="toPage('last')"></a>
                    <a name="noButton" class="num"><span>第</span></a>
                    <a name="noButton" class="num active"><span id="pageLable">1/1</span></a>
                    <a name="noButton" class="num"><span>页</span></a>
                    <a name="noButton" class="btn i3" title="下一页" onclick="toPage('next')"></a>
                    <a name="noButton" class="btn i4" title="尾页" onclick="toPage('end')"></a>
                    <span id="totalSpan" style="right: 55px; color: rgb(0, 168, 255); font-size: 16px; position: fixed; bottom: 10px;"></span>
                </div>
            </div>
            <div class="edges">
                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
            </div>
        </div>
    </div>


    <%@include file="../../bacs/serial/common.jsp"%>
    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" src="../../../static/page-1.png">
</div>
<%@include file="inReceptionDialog.jsp" %>
<%@include file="outReceptionDialog.jsp" %>
<script src="../../../js/plugs/sky/particles.min.js"></script>
<script src="../../../js/plugs/sky/app.js"></script>

</body>
</html>
