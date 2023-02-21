<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp" %>
    <script type="text/javascript" src="belongout.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#cuffNumber').focus();
        });
    </script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp" %>
<div data-options="region:'center',split:true"
     style="width: 49%; height: 50%;">
    <table id="detid"></table>
</div>

<div data-options="region:'south',split:true"
     style="width: 100%; height: 15%;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" type="text/css" href="2.css"/>
        <title>无标题文档</title>
        <script type="text/javascript">
        </script>
    </head>
        <% String ids= request.getParameter("s_lockerId");if(ids == null) ids=""; %>
    <object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
    <script language="javascript" for="GWQ" event="OnAfterGWQ_StartSignEx(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)" type="text/javascript">
        AfterSignStart(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64,10);
    </script>
    <body>
    <div id="tj_btn">
        <%--<input type="button" id="pbelongprint" value="预览" class="bc_out1" onmousemove="this.className='bc_on1'"
               onmouseout="this.className='bc_out1'" onclick="securityPrint()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
            <button id="pbelongprint" class="m-btn-1 blue" onclick="securityPrint()" style="margin-right: 15px;">预览</button>
            <button id="policebelongPdfPrint" class="m-btn-1 blue" onclick="signPrint()" style="margin-right: 15px;">PDF预览</button>
            <button id="policebelongSign" class="m-btn-1 blue" onclick="securitySign()" style="margin-right: 15px;">签字</button>
            <button id="pbelongprint" class="m-btn-1 blue" onclick="securitydown()" style="margin-right: 15px;">下载</button>
            <button id="policebelongPdfPrint" class="m-btn-1 blue" onclick="PdfDownLoad()" style="margin-right: 15px;">PDF下载</button>
        <%--<input type="button" id="pbelongopenbox" value="开柜" class="bc_out3" onmousemove="this.className='bc_on3'"
               onmouseout="this.className='bc_out3'" onclick="boxoutopen()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
            <button id="pbelongopenbox" class="m-btn-1 blue" onclick="boxoutopen()" style="margin-right: 15px;">开柜</button>
        <%--<input type="button" id="pbelongoutall" value="提取全部" class="bc_out10" onmousemove="this.className='bc_on10'"
               onmouseout="this.className='bc_out10'" onclick="boxopen()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
            <button id="pbelongoutall" class="m-btn-1 blue" onclick="boxopen()" style="margin-right: 15px;">提取全部</button>
       <%-- <input type="button" name="noButton" value="返回" class="bc_out11" onmousemove="this.className='bc_on11'"
               onmouseout="this.className='bc_out11'" onclick="javascript:window.location.href ='indexMain.jsp';"/>--%>
            <button class="m-btn-1 blue" onclick="javascript:window.location.href ='index.jsp';" style="margin-right: 15px;">返回</button>
    </div>
    <div id="areaToolbar" style="padding: 5px; height: auto">
        <form id="detinfo_form" class="form-style" method="post">
            <div>
                <input type="hidden" id="id1" name="id1" value="<%=ids%>"/>
                <input type="hidden" id="id" name="id"/>
                <input type="hidden" id="lawCaseId" name="lawCaseId"/>
                <input type="hidden" id="interrogateAreaId" name="interrogateAreaId"/>
                <input type="hidden" id="s_lockerId" name="s_lockerId"/>
                <label id="cuffLabel">请扫描手环:</label>
                <input id="cuffNumber" name="" type="text" style="font: 14px arial; width: 170px;padding:3px" class="easyui-textbox"
                       data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true"/><font
                    id="cuffFont" color="red">*请扫描手环或选择民警姓名*</font>
                <br/>
                民警姓名&nbsp;&nbsp;&nbsp;:
                <input id="serialId" name="serialId" class="easyui-combogrid" required="true"
                       style="width: 170px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" name="noButton" onclick="doSearch()">搜索</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton"
                   onclick="areadoClear()">清除</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-undo" name="noButton"
                   onclick="javascript:window.location.href ='index.jsp';">返回</a>

            </div>
        </form>
    </div>
    <!-- 查看图片-->
    <div id="showPic_dialog" class="easyui-dialog"
         style="width: 600px; height: 350px; padding: 10px 20px" closed="true" buttons="#showPic-buttons">
    </div>
    <!-- 开柜单页面.start -->
    <div id="openbill_dialog" class="easyui-dialog"
         style="width: 400px; height: 290px; padding: 10px 20px" closed="true"
         buttons="#open-buttons">
        <div class="xx_change_div_2">
            <div class="ftitle">开柜单</div>
            <form id="openbill_form" class="form-style" method="post"
                  style="height: 130px;">
                <input type="hidden" id="id2" name="id"/>
                <input type="hidden" id="belongingsId" name="belongingsId"/>
                <input type="hidden" id="lockerId" name="lockerId"/>
                <div class="fitem">
                    <label>领取方式:</label>
                    <select class="easyui-combobox" editable="false" id="getWay" name="getWay" required="true"
                            style="width: 140px;">
                        <option value="1">本人领取</option>
                        <option value="2">委托他人代为领取</option>
                        <option value="3">其他</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>领取人:</label>
                    <input id="getPerson" name="getPerson" class="easyui-validatebox" required="true" style="width: 130px" />
                </div>
                <div id="open-buttons">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
                       onclick="saveOpeninfo()">保存</a> <a href="#"
                                                          class="easyui-linkbutton" iconCls="icon-cancel"
                                                          name="noButton"
                                                          onclick="javascript:$('#openbill_dialog').dialog('close')">关闭</a>
                </div>
            </form>

        </div>
        <!-- 开柜单页面.end -->
        <!--  手环object-->
        <form id="cuffInfo" method="post">
            <input type="hidden" id="cuffNo" name="cuffNo"/>
        </form>
        <div>
            <form id="lawdocInfo_belonginfo" class="form-style" method="post" action="../../lawdocProcess/download.do"
                  accept-charset="UTF-8'">
                <input type="hidden" id="number" name="number"/>
                <input type="hidden" id="name" name="name"/>
                <input type="hidden" id="type" name="type"/>
                <input type="hidden" id="userId" name="userId"/>
                <input type="hidden" id="dataId" name="dataId"/>
                <input type="hidden" id="serialNo" name="serialNo"/>
            </form>
        </div>
    </div>
    </body>
</html>