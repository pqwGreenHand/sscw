<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/css.css">
    <script type="text/javascript" src="indexWIthCamera.js"></script>
    <script type="text/javascript" src="inSerialWithCamera.js"></script>
    <script type="text/javascript" src="outSerialWithCamera.js"></script>.js
    <script type="text/javascript" src="entranceEdit.js"></script>
    <script type="text/javascript" src="  relate.js"></script>
    <script type="text/javascript" src="common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
    <script type="text/javascript" src="js/jquery.webcam.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/case/case.jsp"%>
<div class="page locker-home">
    <div class="main">
        <div class="box">
            <a id="cameraRqButton" class="item i1 m-box" name="cameraRqButton" onclick="entrance()">
                <span>入区登记</span>
                <div class="edges">
                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
            <a id="cameraCqButton" class="item i2 m-box" name="cameraCqButton" onclick="exitBegin()">
                <span>出区登记</span>
                <div class="edges">
                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
            <a id="rqlsButton" name="noButton" class="item i3 m-box" href="suspectentrance.jsp">
                <span>入区历史</span>
                <div class="edges">
                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
        </div>
        <div class="content m-box">
            <div class="control-bar">
                <div class="m-input" style="width: 240px">
                    <span>姓名：</span>
                    <label style="width: 160px">
                        <input type="text" id="person_name" name="" placeholder="请输入">
                    </label>
                </div>
                <div class="m-input">
                    <span>证件号码：</span>
                    <label>
                        <input type="text" id="certificate_no" name="" placeholder="请输入">
                    </label>
                </div>
                <div class="m-input" style="width: 460px">
                    <span>入区时间从：</span>
                    <input type="text" id="start_date" name="start" class="easyui-datetimebox" style="width: 150px"/>
                    <span style="color: white;width: 35px">&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;</span>
                    <input type="text" id="end_date" name="end" class="easyui-datetimebox" style="width: 150px"/>
                </div>
                <div class="m-select" style="width: 230px">
                    <span style="width: 60px">状态:&nbsp;&nbsp;</span>
                    <input id="status" class="easyui-combobox" panelHeight='150'>
                </div>
                <div class="m-select" style="width: 240px">
                    <span style="width: 80px">办案场所:&nbsp;&nbsp;</span>
                    <input id="area_cmb_id" class="easyui-combobox" panelHeight='150'>
                </div>
                <div style="width: 20px"></div>
                <button class="m-btn-1 blue" onclick="doSearch()">查询</button>
                <button class="m-btn-1 blue" onclick="doClear()">清除</button>
            </div>
            <div class="content-in">
                <div id="serialItems" class="list" style="height: 350px"></div>
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

    <%@include file="common.jsp"%>
    <%@include file="inSerialAndOutSerialWithCameraDialog.jsp" %>
    <%@include file="entranceEditDialog.jsp" %>
    <%@include file="relateDialog.jsp" %>
    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" src="../../../static/page-1.png">
</div>
<!-- 裁决结果选择框 -->
<div id="showConfirmDg" class="easyui-dialog"
     style="width: 250px; height: 250px; padding: 10px 20px" closed="true"
     buttons="#showConfirmDg_Button">
    <form id="choose_form" class="form-style" method="post">
        <div class="fitem">
            <input type="hidden" id="confirm_serial_id">
            <select id="confirm_result_dg" name="confirmResult"  class="easyui-combobox"
                    style="margin: -2px; width: 150px; height: 28px" required="true">
                <option value="">选择裁决结果</option>
                <option value="逮捕">逮捕</option>
                <option value="监居">监居</option>
                <option value="排除">排除</option>
                <option value="刑拘">刑拘</option>
                <option value="移交">移交</option>
                <option value="在逃羁押">在逃羁押</option>
                <option value="直保">直保</option>
                <option value="治拘">治拘</option>
                <option value="警告">警告</option>
                <option value="罚款">罚款</option>
                <option value="教育">教育</option>
                <option value="其他">其他</option>
            </select>
        </div>
    </form>
    <div id="showConfirmDg_Button">
        <a name="noButton" href="#" class="easyui-linkbutton"
           iconCls="icon-ok" onclick="doConformResult()">确定</a> <a
            name="noButton" href="#" class="easyui-linkbutton"
            iconCls="icon-cancel"
            onclick="javascript:$('#showConfirmDg').dialog('close');">关闭</a>
    </div>
</div>
<form id="exportForm" method="post" action="downloadWord.do" style="display: none">
    <input id="exportSerialId" name="exportSerialId"/>
</form>
<script src="../../../js/plugs/sky/particles.min.js"></script>
<script src="../../../js/plugs/sky/app.js"></script>
</body>
</html>