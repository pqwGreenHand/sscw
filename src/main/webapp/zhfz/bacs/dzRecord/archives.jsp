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
    <script type="text/javascript" src="archives.js"></script>
    <script type="text/javascript" src="../../../js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../../js/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp" %>
<div data-options="region:'center',title:'',split:true">
    <table id="treegrid"></table>

    <!-- toolbar -->
    <div id="toolbar" style="padding:5px;height:auto">
        <div>
            案件名称: <input id="s_case_name" name="s_case_name" class="easyui-validatebox" style="width:100px">
            嫌疑人: <input id="s_person_name" name="s_person_name" class="easyui-validatebox" style="width:100px">
            <a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
            <a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清除</a>
        </div>
    </div>
</div>

<div id="uploadonlineinfo" class="easyui-dialog" style="width: 700px; height: 200px; padding: 10px 20px" closed="true"
     buttons="#enterprise-buttons">

    <div style="height: 100px;">
        <form id="fam" method="post" enctype="multipart/form-data">
            <table border="0" style="margin-top:4px;" width="100%" align="center">
                <tr>
                    <td>
                        <div class="fitem">
                            <label>文件类型:</label>
                            <select id="fileType" name="fileType" class="easyui-combobox" required="true"
                                   style="margin: -2px; width: 170px; "
                                   data-options="url: 'type_data.json',valueField: 'id',textField: 'text',validType:'maxLength[128]',invalidMessage:'最长128字节'">
                            </select>
                                <font color="red" style="margin-left: 2px;">*</font>
                        </div>
                    </td>
                    <td>
                        <div class="fitem">
                            <label>人员信息:</label>
                            <select style="width:150px" editable="true" id="person" required="true"
                                    name="type" class="easyui-combobox">
                            </select>
                            <span style="color:#F00"> * </span>
                        </div>
                    </td>

                </tr>
                <tr>
                    <td colspan="2">
                        <div class="fitem">
                            <input class="easyui-filebox" name="onlinefilename" data-options="prompt:'文件上传'"
                                   buttonText="选择文件"
                                   style="width:95%"></input>
                            <span style="color:#F00"> * </span>
                        </div>

                    </td>

                </tr>
            </table>
        </form>
    </div>
    <div id="enterprise-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="uploadonline()">上传</a> <a href="#" class="easyui-linkbutton"
                                              iconCls="icon-cancel" name="noButton"
                                              onclick="javascript:$('#uploadonlineinfo').dialog('close')">关闭</a>
    </div>
</div>


<%--<div id="uploadonlineinfo" class="easyui-dialog" style="width:580px;height:300px;padding:10px 40px" closed="true" buttons="#enterprise-buttons">--%>
<%--<div style="height: 200px;">--%>
<%--<form id="fam" method="post" enctype="multipart/form-data">--%>
<%--<table border="0" style="margin-top:4px;" width="80%"  align="center">--%>
<%--<tr>--%>
<%--<td>--%>
<%--<input class="easyui-filebox" name="onlinefilename" data-options="prompt:'文件上传'" buttonText="选择文件"--%>
<%--style="width:100%"></input>--%>
<%--</td>--%>

<%--</tr>--%>
<%--</table>--%>
<%--</form>--%>

<%--<div id="enterprise-buttons" >--%>
<%--<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"--%>
<%--onclick="uploadonline();" style="width:80px">上传</a>--%>
<%--</div>--%>
<%--</div>--%>

<%--</div>--%>


<div data-options="region:'south',split:true" class="easyui-tabs" pill="true" style="height:40%;">
    <div title="图片材料" style="padding:5px">
        <table id="imageDatagrid"></table>
    </div>
    <div title="视频材料" style="padding:5px">
        <table id="videoDatagrid"></table>
    </div>
    <div title="其它材料" style="padding:5px">
        <table id="qtDatagrid"></table>
    </div>
    <%--<div title="其它材料" style="padding:5px">--%>
        <%--<table id="interrogateCaseDetailgrid5"></table>--%>
    <%--</div>--%>
    <%--<div title="询/讯问视频" style="padding:5px">--%>
        <%--<table id="recordVideotree"></table>--%>
    <%--</div>--%>
    <%--<div title="案件视频" style="padding:5px">--%>
        <%--<table id="interrogateCaseDetailgrid4"></table>--%>
    <%--</div>--%>
    <%--<div title="轨迹视频" style="padding:5px">--%>
        <%--<table id="videotree"></table>--%>
    <%--</div>--%>
</div>

<div id="dvd_dialog" class="easyui-dialog"
     style="width: 400px; height: 200px; padding: 10px 20px" closed="true"
     buttons="#dvd-buttons">
    <div class="ftitle">领取信息</div>
    <div style="">
        <form id="dvd_form" class="form-style" method="post"
              style="">
            <div class="fitem">
                <label>领取人:</label> <input id="receiveUser" name="receiveUser" class="easyui-validatebox"
                                           data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
            </div>
            <div class="fitem" style="display:none;">
                <label>案件id:</label> <input id="lawCaseId" name="lawCaseId" class="easyui-validatebox"
                                            data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
            </div>
            <div class="fitem" style="display:none;">
                <label>办公场所id:</label> <input id="interrogateAreaId" name="interrogateAreaId" class="easyui-validatebox"
                                              data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
            </div>

        </form>
    </div>
    <div id="dvd-buttons">
        <a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok"
           onclick="saveDvd()">保存</a> <a name="noButton" href="#" class="easyui-linkbutton"
                                         iconCls="icon-cancel"
                                         onclick="javascript:$('#dvd_dialog').dialog('close')">关闭</a>
    </div>
</div>

<!--  条码打印机object-->
<div id="objects"></div>
<!-- <object id="print_epl" style="display: none; visibility: hidden"
		width="51" height="21" codebase="EPL.ocx#version=1,0,0,0"
		classid="CLSID:64CBDF17-E597-4309-A586-4BB8051452AB" visible="false">
</object> -->
<!--  条码打印机object-->

</body>
</html>