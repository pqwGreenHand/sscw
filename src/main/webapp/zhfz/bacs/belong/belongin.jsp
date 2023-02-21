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
    <script type="text/javascript" src="belongin.js"></script>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ"
        event="OnAfterGWQ_StartSignEx(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)"
        type="text/javascript">
    AfterSignStart(ErrorCode, SignPdfBase64, SignNameBase64, FingerPrintBase64, MixBase64, 5);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%--<%@ include file="../../common/common-loading.jsp" %>--%>
<object id="ocx" codeBase=/comRD800.dll classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"
        style="display:none;"></object>
<div data-options="region:'center',split:true"
     style="width: 49%; height: 50%;">
    <table id="detid"></table>
</div>
<div data-options="region:'south',split:true"
     style="width: 100%; height: 15%;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" type="text/css" href="2.css"/>
        <title>随身物品存放</title>
        <script type="text/javascript">
            $(function () {
                var ssareaid = $("#ssareaid").val();
                var s = $('#id1').val();
            })
        </script>
    </head>
    <% String ids = request.getParameter("s_lockerId");
        if (ids == null) ids = "";
        String ssareaid = request.getParameter("ssareaid");
    %>
    <div id="tj_btn">
        <input id="ssareaid" type="hidden" name="ssareaid" value="<%=ssareaid%>">
        <button id="belongphoto" class="m-btn-1 blue" onclick="showphotowid()" style="margin-right: 15px;">拍照</button>
        <button id="belongphoto" class="m-btn-1 blue" onclick="printcod()" style="margin-right: 15px;">条码</button>
        <button id="belongsavebox" class="m-btn-1 blue" onclick="belongsavebox()" style="margin-right: 15px;">开柜
        </button>
        <%--<button id="belongprint" class="m-btn-1 blue" onclick="securityPrint()" style="margin-right: 15px;">预览</button>--%>
        <button id="belongprint" class="m-btn-1 blue" onclick="securityDownLoad()" style="margin-right: 15px;">台账打印
        </button>
        <%--<button id="belongSign" class="m-btn-1 blue" onclick="securitySign()" style="margin-right: 15px;">签字</button>--%>
        <%--<button id="belongSign" class="m-btn-1 blue" onclick="cxsecuritySign()" style="margin-right: 15px;">重新签字</button>--%>
        <%--<button id="belongSignPrint" class="m-btn-1 blue" onclick="signPrint()" style="margin-right: 15px;">PDF预览</button>--%>
        <%--<button id="belongSignPrint" class="m-btn-1 blue" onclick="PdfDownLoad()" style="margin-right: 15px;">签字版打印</button>--%>
        <button id="belongimage" class="m-btn-1 blue" onclick="showImages()" style="margin-right: 15px;">查看照片</button>
        <button class="m-btn-1 blue" style="margin-right: 15px;"
                onclick="javascript:window.location.href ='index.jsp';"/>
        返回</button>
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
        <%--<a class="myButton i1 m-box" name="noButton" href="javascript:void(0)" onclick="belongJsAddList()">--%>
        <%--<span>物品接收</span>--%>
        <%--<div class="edges">--%>
        <%--<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>--%>
        <%--</div>--%>
        <%--</a>--%>
    </div>
    <br/>
    <div style="display:none">
        <label >请扫描手环:</label>
        <input  id="cuffNumberQuery" name="easyui2" class="easyui-textbox"
                type="text" style="display:none;margin: -2px; width: 170px; height: 28px"
                data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    <label>嫌疑人姓名:</label>
    <input id="serialIdQuery" name="serialId" class="easyui-combogrid" data-options="editable:false"
           style="margin: -2px; width: 170px; height: 28px">
    &nbsp;&nbsp;&nbsp;
    <button id="shuaxin" class="m-btn-1 blue" onclick="shuaxin()" style="margin-right: 15px;">刷新</button>
    <script src="../../../js/plugs/sky/particles.min.js"></script>
    <script src="../../../js/plugs/sky/app.js"></script>
</div>


<div id="det_dialog" class="easyui-dialog"
     style="width: 530px; height: 400px; padding: 10px 20px" closed="true"
     buttons="#det-buttons">
    <div class="ftitle">随身物品信息详情</div>
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
                <label>特征:</label> <input id="description" name="description" class="easyui-textbox"
                                          data-options="multiline:true"
                                          style="width: 180px;height: 80px;overflow-y: hidden;">
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
    <form id="lawdocInfo_belonginfo" class="form-style" method="get" action="../../lawdocProcess/download.do"
          accept-charset="UTF-8'">
        <input type="hidden" id="number" name="number"/>
        <input type="hidden" id="name" name="name"/>
        <input type="hidden" id="type" name="type"/>
        <input type="hidden" id="userId" name="userId"/>
        <input type="hidden" id="dataId" name="dataId"/>
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

<div id="qx_dialog" class="easyui-dialog"
     style="width: 530px; height: 320px; padding: 10px 20px" closed="true"
     buttons="#qx-buttons">
    <div class="ftitle">不同意接收详情</div>
    <div>
        <form id="qx_form" class="form-style" method="post"
              style="height: 115px;">
            <input type="hidden" id="wpid" name="id"/>

            <div class="fitem">
                <label>不同意接收意见:</label> <input id="yjyj" name="yjyj" class="easyui-textbox"
                                               data-options="multiline:true"
                                               style="width: 230px;height: 80px;overflow-y: hidden;">
            </div>
        </form>
    </div>
    <div id="qx-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveYjyj()">保存</a> <a href="#" class="easyui-linkbutton"
                                          iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#qx_dialog').dialog('close')">关闭</a>
    </div>
</div>


<!--选择嫌疑人列表平台-->
<div id="personDialog" class="easyui-dialog"
     style="width: 1000px; height: 600px; padding: 0px;overflow:hidden;" closed="true"
     buttons="#person-buttons">
    <div id="toolbar_personInfo" style="padding: 5px; height: auto">
        姓名: <input id="xm" name="name" class="easyui-validatebox" style="width: 170px">
        证件号码: <input id="certificateNo" name="certificateNo" class="easyui-validatebox" style="width: 170px">
        <a href="#"
           class="easyui-linkbutton" iconCls="icon-search"
           id="seachPersonInfo" name="noButton" onclick="doSearchPersonInfo()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" id="doClearPersonInfo" name="noButton"
           onclick="doClearPersonInfo()">清除</a>
    </div>
    <div data-options="region:'center',split:true"
         style="width:100%; height: 95%;">
        <table id="personInfo">
        </table>
    </div>
    <div id="person-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="savePersonInfo()">保存</a> <a href="#"
                                          class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#personDialog').dialog('close')">取消</a>
    </div>
</div>

<!--选择嫌疑人列表平台-->
<div id="ajxxAndPersonDialog" class="easyui-dialog"
     style="width: 1000px; height: 600px; padding: 0px;overflow:hidden;" closed="true"
     buttons="#jz-buttons">
    <div id="toolbar_jzInfo" style="padding: 5px; height: auto">
        案件名称: <input id="ajmc" name="ajmc" class="easyui-validatebox" style="width: 170px">
        案件编号: <input id="ajbh" name="ajbh" class="easyui-validatebox" style="width: 170px">
        <a href="#"
           class="easyui-linkbutton" iconCls="icon-search"
           id="SearchJZInfo" name="noButton" onclick="doSearchInfo()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" id="doClearJZInfo" name="noButton"
           onclick="doClearInfo()">清除</a>
    </div>
    <div data-options="region:'center',split:true"
         style="width:100%; height: 60%;">
        <table id="jzInfo">
        </table>
    </div>
    <div data-options="region:'east',split:true" style="width:100%;height: 40%;">
        <table id="jzPerson">
        </table>
    </div>
    <div id="jz-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveInfo()">保存</a> <a href="#"
                                            class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
                                            onclick="javascript:$('#ajxxAndPersonDialog').dialog('close')">取消</a>
    </div>
</div>

<!--选择嫌疑人列表平台-->
<div id="ajxxAndPersonDialogBazx" class="easyui-dialog"
     style="width: 80%; height: 600px; padding: 0px;overflow:hidden;" closed="true"
     buttons="#bazx-buttons">
    <div id="toolbar_bazx" style="padding: 5px; height: auto">
        案件名称: <input id="ajmcBazx" name="ajmc" class="easyui-validatebox" style="width: 170px">
        案件编号: <input id="ajbhBazx" name="ajbh" class="easyui-validatebox" style="width: 170px">
        <a href="#"
           class="easyui-linkbutton" iconCls="icon-search"
            name="noButton" onclick="doSearchBazxInfo()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton"
           onclick="doClearBazxInfo()">清除</a>
    </div>
    <div style="width: 100%;height: 100%" class="easyui-layout">
        <div data-options="region:'north',split:true"
             style="width:100%; height: 60%;">
            <table id="bazxAjxxTable">
            </table>
        </div>
        <div data-options="region:'west'" style="width:50%;height: 40%;">
            <table id="bazxRyxxTable">
            </table>
        </div>
        <div data-options="region:'center'" style="width:40%;height: 40%;">
            <table id="bazxWpxxTable">
            </table>
        </div>
    </div>
    <div id="bazx-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveInfoBazx()">保存</a> <a href="#"
                                          class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#ajxxAndPersonDialogBazx').dialog('close')">取消</a>
    </div>
</div>

<!--选择嫌疑人列表平台-->
<div id="ajxxAndPersonDialogZfba" class="easyui-dialog"
     style="width: 80%; height: 600px; padding: 0px;overflow:hidden;" closed="true"
     buttons="#zfba-buttons">
    <div id="toolbar_zfba" style="padding: 5px; height: auto">
        案件名称: <input id="ajmcZfba" name="ajmc" class="easyui-validatebox" style="width: 170px">
        案件编号: <input id="ajbhZfba" name="ajbh" class="easyui-validatebox" style="width: 170px">
        <a href="#"
           class="easyui-linkbutton" iconCls="icon-search"
           name="noButton" onclick="doSearchZfbaInfo()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" name="noButton"
           onclick="doClearZfbaInfo()">清除</a>
    </div>
    <div style="width: 100%;height: 100%" class="easyui-layout">
        <div data-options="region:'north',split:true"
             style="width:100%; height: 60%;">
            <table id="zfbaAjxxTable">
            </table>
        </div>
        <div data-options="region:'west'" style="width:50%;height: 40%;">
            <table id="zfbaRyxxTable">
            </table>
        </div>
        <div data-options="region:'center'" style="width:40%;height: 40%;">
            <table id="zfbaWpxxTable">
            </table>
        </div>
    </div>
    <div id="zfba-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveInfoZfba()">保存</a> <a href="#"
                                          class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#ajxxAndPersonDialogZfba').dialog('close')">取消</a>
    </div>
</div>

<div id="objects"></div>
<!--  手环object-->
<form id="cuffInfo" method="post" class="form-style">
    <input type="hidden" id="cuffNo" name="cuffNo"/>
</form>
</div>
<%@include file="inBelongDialog.jsp" %>
<%@include file="jsBelongDialog.jsp" %>
</body>
</html>