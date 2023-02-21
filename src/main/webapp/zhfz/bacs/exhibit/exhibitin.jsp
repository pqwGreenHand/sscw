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
    <script type="text/javascript">
        var startpath = '<%=basePath%>';
    </script>
    <script type="text/javascript" src="exhibitin.js"></script>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartSignEx(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)" type="text/javascript">
    AfterSignStart(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64,6);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%--<%@ include file="../../common/common-loading.jsp" %>--%>

<div data-options="region:'center',split:true"
     style="width: 49%; height: 50%;">
    <table id="detid"></table>
</div>
<div data-options="region:'south',split:true"
     style="width: 100%; height: 15%;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" type="text/css" href="2.css"/>
        <title>涉案物品存放</title>
        <script type="text/javascript">
            $(function () {
                var ssareaid = $("#ssareaid").val();
                var s = $('#id1').val();
                $('#cuffNumber').focus();
            })
        </script>
    </head>
    <% String ids = request.getParameter("s_lockerId");
        if (ids == null) ids = "";
        String ssareaid = request.getParameter("ssareaid");
    %>
    <div id="tj_btn">
        <input id="ssareaid" type="hidden" name="ssareaid" value="<%=ssareaid%>"></input>
       <%-- <input type="button" id="exhibitprint" value="预览" class="bc_out1" onmousemove="this.className='bc_on1'"
               onmouseout="this.className='bc_out1'" onclick="securityPrint()" style="margin-right: 15px;"/>--%>
        <%--<button id="exhibitprint" class="m-btn-1 blue" onclick="securityPrint()" style="margin-right: 15px;">预览</button>--%>
        <button id="exhibitPdfPrint" class="m-btn-1 blue" onclick="signPrint()" style="margin-right: 15px;">PDF预览</button>
        <button id="exhibitSign" class="m-btn-1 blue" onclick="securitySign()" style="margin-right: 15px;">签字</button>
        <button id="belongSign" class="m-btn-1 blue" onclick="cxsecuritySign()" style="margin-right: 15px;">重新签字</button>
        <button id="exhibitprint" class="m-btn-1 blue" onclick="securityDownLoad()" style="margin-right: 15px;">原始版打印</button>
        <button id="exhibitPdfPrint" class="m-btn-1 blue" onclick="PdfDownLoad()" style="margin-right: 15px;">签字版打印</button>
        <%--<input type="button" id="exhibitphoto" value="拍照" class="bc_out2" onmousemove="this.className='bc_on2'"
               onmouseout="this.className='bc_out2'" onclick="showphotowid()" style="margin-right: 15px;"/>--%>
        <%--<button id="exhibitphoto" class="m-btn-1 blue" onclick="showphotowid()" style="margin-right: 15px;">拍照</button>--%>
        <%--<input type="button" id="exhibitimage" value="照片" class="bc_out2" onmousemove="this.className='bc_on2'"
               onmouseout="this.className='bc_out2'" onclick="showImages()" style="margin-right: 15px;"/>--%>
        <%--<button id="exhibitimage" class="m-btn-1 blue" onclick="showImages()" style="margin-right: 15px;">照片</button>--%>
        <%--<input
                type="button"
                id="exhibitsavebox" value="开柜" class="bc_out5"
                onmousemove="this.className='bc_on5'"
                onmouseout="this.className='bc_out5'" onclick="belongsavebox()"/> <input type="button" name="noButton"
                                                                                         value="返回" class="bc_out11"
                                                                                         onmousemove="this.className='bc_on11'"
                                                                                         onmouseout="this.className='bc_out11'"
                                                                                         onclick="javascript:window.location.href ='indexMain.jsp';"/>--%>
        <button id="exhibitsavebox" class="m-btn-1 blue" onclick="belongsavebox()" style="margin-right: 15px;">开柜</button>
        <button  class="m-btn-1 blue" style="margin-right: 15px;"  onclick="javascript:window.location.href ='index.jsp';"/>返回</button>
    </div>
</div>
<div id="areaToolbar" style="height: 120px;">
   <div style="text-align: center;">
    <a class="myButton i1 m-box" name="noButton" href="javascript:void(0)" onclick="belongAddList()">
        <span>存物登记</span>
        <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
    </a>
  </div>
    <br/>
     <label>请扫描手环:</label>
    <input id="cuffNumberQuery" name="easyui2" class="easyui-textbox"
           type="text" style="margin: -2px; width: 170px; height: 28px"
           data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"
            />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <label>嫌疑人姓名:</label>
    <input id="serialIdQuery" name="serialId" class="easyui-combogrid" data-options="editable:false"
           style="margin: -2px; width: 170px; height: 28px">
    <script src="../../../js/plugs/sky/particles.min.js"></script>
    <script src="../../../js/plugs/sky/app.js"></script>
</div>


<div id="det_dialog" class="easyui-dialog"
     style="width: 430px; height: 290px; padding: 10px 20px" closed="true"
     buttons="#det-buttons">
    <div>
        <form id="det_form" class="form-style" method="post"
              style="height: 115px;">
            <input type="hidden" id="id" name="id"/>
            <div class="fitem">
                <label>物品名称:</label> <input id="nameEdit" name="name"
                                            class="easyui-validatebox" required="true"
                                            data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'">
            </div>
            <div class="fitem">
                <label>数量:</label> <input id="detailCount" name="detailCount"
                                          class="easyui-numberbox" required="true"
                                          data-options="min:0,validType:'maxLength[11]',invalidMessage:'最长11字节'">
            </div>
            <div class="fitem">
                <label>特征:</label> <input id="description" name="description"
                                          class="easyui-validatebox"
                                          data-options="validType:'maxLength[256]',invalidMessage:'最长256字节'">
            </div>
        </form>
    </div>
    <div id="det-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="savedet()">保存</a> <a href="#" class="easyui-linkbutton"
                                         iconCls="icon-cancel" name="noButton"
                                         onclick="javascript:$('#det_dialog').dialog('close')">关闭</a>
    </div>
</div>
<div id="showPic_dialog" class="easyui-dialog"
     style="width: 600px; height: 500px; padding: 10px 20px" closed="true" buttons="#showPic-buttons">
</div>
<!-- 下载隐藏信息 -->
<div>
    <form id="lawdocInfo_belonginfo" class="form-style" method="post" action="../../lawdocProcess/download.do"
          accept-charset="UTF-8'">
        <input type="hidden" id="number" name="number"/>
        <input type="hidden" id="name" name="name"/>
        <input type="hidden" id="type" name="type"/>
        <input type="hidden" id="userId" name="userId"/>
        <input type="hidden" id="serialIds" name="serialIds"/>
        <input type="hidden" id="serialNo" name="serialNo"/>
    </form>
</div>
<div id="photo_dialog" class="easyui-dialog"
     style="width: 550px; height: 450px; padding: 10px 20px" closed="true" buttons="#photo-buttons">
    <div class="ftitle">随身物品拍照</div>
    <form id="photo" enctype="multipart/form-data" action="ashx/login.ashx" method="post">
        <table class="main_form1">
            <tr>
                <td align="center">拍照框（每次点击“拍照”都将覆盖之前拍摄的照片）</td>
                <td></td>
            <tr>
                <td>
                    <!-- 拍照框 -->
                    <div id="in_view1">
                        <object id="view1" type="application/x-eloamplugin" width="250" height="250"
                                name="view"></object>
                    </div>
                </td>
                <td>
                    <!-- 照片框 -->
                    <div id="in_thumb1">
                        <object id="thumb1" type="application/x-eloamplugin" width="210" height="150"
                                name="thumb"></object>
                        <br/>
                    </div>
                </td>
            </tr>

        </table>
        <div class="photo_botton">
            <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton" onclick="inScan()">拍照</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton" onclick="uploadphoto()">上传</a>
        </div>
    </form>
</div>


<!--  手环object-->
    <form id="cuffInfo" method="post" class="form-style">
        <input type="hidden" id="cuffNo" name="cuffNo"/>
    </form>
</div>
<%@include file="inExhibitDialog.jsp" %>
</body>
</html>