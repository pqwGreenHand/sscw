<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <%--<link href="../../evaluation/css/zeroModal.css" rel="stylesheet">
    <link href="../../evaluation/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    </script>
    <script src="../../evaluation/js/plugins/sweetalert/sweetalert.min.js"></script>--%>
    <script type="text/javascript" src="belongYjjs.js"></script>
    <style>
        .zeromodal-container button{
            margin-right:20px;
        }
    </style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">

<div data-options="region:'center',split:true"
     style="width: 65%; height: 100%;">
    <table id="lawdocgrid"></table>
</div>

<div data-options="region:'east',split:true" style="width: 35%;">
    <table id="lawdocDetailgrid" ></table>
</div>

<!-- toolbar -->
<div id="lawdocToolbar" style="padding:5px;height:auto">
    <div>
        姓名:<input id="xm" class="easyui-textbox" style="width:100px;height: 23px">
        证件号码:<input id="sfzh" class="easyui-textbox" style="width:100px;height: 23px">
        <%--办案场所:<input type="text" id="s_areaId" name="areaId" class="easyui-combobox">--%>
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-search" onclick="lawdocSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-clear" onclick="doClear()">清除</a>
    </div>
</div>

<div id="lawdocDetailToolbar" style="padding:5px;height:auto;text-align:right">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="yjAccept()">一键接收</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-clear"  name="noButton" onclick="yjRefuse()">拒绝接收</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-search"  name="noButton" onclick="searchImage()">查看照片</a>
</div>
<div>
    <form id="lawdocInfo" class="form-style" method="post" action="../../lawdocProcess/download.do" accept-charset="UTF-8'">
        <input type="hidden" id="number" name="number" />
        <input type="hidden" id="userId" name="userId" />
        <input type="hidden" id="serialNo" name="serialNo" />
        <input type="hidden" id="serialId" name="serialId" />
        <input type="hidden" id="type" name="type" />
        <input type="hidden" id="name" name="name" />

    </form>

    <%--<form id="exportForm" method="post" action="../../jxkp/download.do">
        &lt;%&ndash;<input id="exportSerialId" name="caseNo" type="hidden"/>&ndash;%&gt;
        <input type="hidden" id="caseNo" name="caseNo" />
    </form>--%>
</div>

<div id="showPic_dialog" class="easyui-dialog"
     style="width: 600px; height: 500px; padding: 10px 20px" closed="true" buttons="#showPic-buttons">
</div>

<div>
    <form id="lawdocInfo_checkbody" class="form-style" method="post" action="download.do" accept-charset="UTF-8'">
        <input type="hidden" id="number1" name="number" />
       <%-- <input type="hidden" id="name" name="name" />
        <input type="hidden" id="type" name="type" />--%>
        <input type="hidden" id="userId1" name="userId" />
        <input type="hidden" id="checkbodyId" name="checkbodyId" />
    </form>
</div>

<div id="gm_dialog" class="easyui-dialog"
     style="width: 530px; height: 320px; padding: 10px 20px" closed="true"
     buttons="#qx-buttons">
    <div>
        <form id="gm_form" class="form-style" method="post"
              style="height: 115px;">
            <div class="fitem">
                <label>办案场所选择:</label>
                <input id="areaId" name="areaId" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="editable:false"><font color="red">*</font>

            </div>
            <div class="fitem">
                <label>储物柜编号:</label>
                <input id="lockerId" name="lockerId" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="editable:false"><font color="red">*</font>

            </div>
         </form>
    </div>
    <div id="qx-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveYjkg()">开柜</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveYj()">保存</a> <a href="#" class="easyui-linkbutton"
                                          iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#gm_dialog').dialog('close')">关闭</a>
    </div>
</div>

<div id="bty_dialog" class="easyui-dialog"
     style="width: 530px; height: 320px; padding: 10px 20px" closed="true"
     buttons="#bty-buttons">
    <div class="ftitle">不同意接收</div>
    <div>
        <form id="bty_form" class="form-style" method="post"
              style="height: 115px;">
            <input type="hidden" id="id" name="id"/>

            <div class="fitem">
                <label>不同意接收意见:</label> <input id="yjyj" name="yjyj" class="easyui-textbox"
                                               data-options="multiline:true"
                                               style="width: 230px;height: 80px;overflow-y: hidden;">
            </div>
        </form>
    </div>
    <div id="bty-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="jjYjyj()">保存</a> <a href="#" class="easyui-linkbutton"
                                          iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#bty_dialog').dialog('close')">关闭</a>
    </div>
</div>

<!--
<div id="showView">
      <iframe id="myIFramePrint" src="" align="center" style="width: 1200px;height: 700px;"/>
</div>
    -->
</body>
</html>