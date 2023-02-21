<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <script type="text/javascript" src="camera.js"></script>

</head>
<style type="text/css">
    .fitem{
        height:25px;
        line-height: 25px;
    }
</style>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<table id="certificategrid"></table>
<!-- 单个添加摄像头-->
<div id="camera_dialog" class="easyui-dialog"
     style="width: 950px; height: 400px; padding: 10px 20px;overflow: hidden" closed="true"
     buttons="#camera_dialog-buttons">

    <div style="height: 100%;">
        <form id="camera_form" class="form-style" method="post"
              style="height: 150px;">
            <input type="hidden" id="id" name="id" />
            <div class="fitem">
                <label>所属办案场所:</label>
                <select style="width:240px" editable="false" id="area_cmb_id" required="true"
                        name="areaId" class="easyui-combobox">
                </select><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;">所属功能室:</label>
                <select style="width:240px" editable="false" id="room_cmb_id" required="true"
                        name="roomId" class="easyui-combobox">
                </select><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>所属NVR:</label>
                <select style="width:240px" editable="false" id="nvr_cmb_id" required="true"
                        name="nvrId" class="easyui-combobox">
                </select><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;">名称:</label>
                <input name="name" class="easyui-validatebox" style="width:230px" required="true"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>IP地址:</label>
                <input id="ip" name="ip" class="easyui-validatebox" style="width:230px" required="true"/><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;">端口:</label>
                <input name="port" class="easyui-numberbox" style="width:239px"
                       required="true" data-options="min:1,precision:0"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>账号:</label>
                <input name="account" class="easyui-validatebox" required="true" style="width:230px"/><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;">密码:</label>
                <input name="password" class="easyui-validatebox" required="true" style="width:230px"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>类型:</label>
                <select style="width:240px" editable="false" id="type_cmb_id" name="type" required="true"
                        class="easyui-combobox">
                    <option value="0">全景摄像头</option>
                    <option value="1">对焦摄像头</option>
                </select><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;">监控点编号:</label>
                <input name="cameraNo" class="easyui-validatebox" style="width:230px" required="true"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>通道:</label>
                <input name="channel" class="easyui-numberbox" required="true"
                       data-options="min:1,precision:0" style="width:240px"/><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;">厂商:</label>
                <select style="width:241px" editable="false" id="vender_cmb_id" required="true"
                        name="vender" class="easyui-combobox">
                    <option value="1">海康威视</option>
                    <option value="2">大华</option>
                    <option value="3">天地伟业</option>
                    <option value="4">科达</option>
                    <option value="5">宇视</option>
                </select><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>描述:</label>
                <input name="description" class="easyui-validatebox" style="width:230px"/>
                <label style="margin-left: 15px;">其它:</label>
                <input type="checkbox" name="screen" id="is_screen"/>是否主画面
                <input type="checkbox" name="download" id="is_download"/>是否下载<br />
            </div>
        </form>
    </div>
    <div id="camera_dialog-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"
                                                iconCls="icon-cancel" name="noButton"
                                                onclick="javascript:$('#camera_dialog').dialog('close')">关闭</a>
    </div>
</div>

<div id="certificate_dialog" class="easyui-dialog"
     style="width: 1300px; height: 600px; padding: 10px 20px" closed="true"
     buttons="#enterprise-buttons">
    <div style="height: 100px;">
        <form id="certificate_form" class="form-style" method="post"
              style="height: 100px;">
            <input type="hidden" id="list_id" name="id" />
            <div class="fitem">
                <label style="width:80px;">账号:</label> <input id="account" name="account" class="easyui-validatebox"
                                            required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;width:80px;">密码:</label> <input id="password" name="password" class="easyui-validatebox"
                                             required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
                <label  style="margin-left: 15px;width:80px;">端口:</label> <input id="port" name="port" class="easyui-numberbox" required="true" ><span style="color:#F00"> * </span>

                <label style="width:80px;margin-left: 15px;">所在办案场所:</label> <select style="width:170px"  editable="false" id="list_area_cmb_id" name="areaId" class="easyui-combobox">
                </select><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">

                <label style="width:80px;">所在NVR:</label> <select style="width:170px"  editable="false" id="list_nvr_cmb_id" name="nvrId" class="easyui-combobox"></select><span style="color:#F00"> * </span>

                <label  style="margin-left: 15px;width:80px;">设备厂商:</label>
                <select style="width:170px" editable="false" id="list_type_cmb_id" required="true" name="vender" class="easyui-combobox">
                    <option value="1">海康威视</option>
                    <option value="2">大华</option>
                    <option value="3">天地伟业</option>
                    <option value="4">科达</option>
                    <option value="5">宇视</option>
                </select><span style="color:#F00"> * </span>

                <label style="margin-left: 15px;width:80px;">添加个数:</label> <input id="addNumber" class="easyui-numberbox" ><span style="color:#F00"> 大于1 </span>
                <a  style="margin-left: 15px;width:80px;" href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="cameraAddDetail()">添加</a>
            </div>

        </form>
    </div>
    <div style="height: 400px;" style="border-top: 5px;bordertop-color:#046aa0">
        <form id="certificateDetail_form" class="form-style" method="post"
              style="height: 400px;">
        </form>
    </div>
    <div id="enterprise-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveCarmeraList()">保存</a> <a href="#" class="easyui-linkbutton"
                                                iconCls="icon-cancel" name="noButton"
                                                onclick="javascript:$('#certificate_dialog').dialog('close')">关闭</a>
    </div>
</div>
<!-- toolbar -->
<div id="certificateToolbar" style="padding:5px;height:auto">
    <div>
        <a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="cameraAdd" onclick="cameraAdd()">添加</a>
        <a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="cameraListAdd" onclick="cameraListAdd()">批量添加</a>
        办案场所: <select style="width:200px" editable="true" id="area_cmb"
                      name="area_cmb" class="easyui-combobox">
    </select>&nbsp;&nbsp;
        功能室: <select style="width: 150px;" editable="true" id="room_cmb"
                     name="room_cmb" class="easyui-combobox">
    </select>&nbsp;&nbsp;
        NVR设备: <select style="width: 150px;" editable="true" id="nvr_cmb"
                       name="nvr_cmb" class="easyui-combobox">
    </select>&nbsp;&nbsp;
        摄像头名称: <input id="s_name" name="name" class="easyui-textbox" style="width:120px">
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>
    </div>
</div>
</body>
</html>