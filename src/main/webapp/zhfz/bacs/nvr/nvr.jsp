<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <script type="text/javascript" src="nvr.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>
<table id="certificategrid"></table>
<div id="nvr_edit_dialog" class="easyui-dialog"
     style="width: 500px; height: 400px; padding: 10px 10px" closed="true" buttons="#nvrEdit-buttons">

    <div style="height: 280px;">
        <form id="nvr_edit_form" class="form-style" method="post">
            <input type="hidden" id="n_id" name="id" />
            <div class="fitem">
                <label>所属办案场所:</label>
                <select style="width:245px" editable="false" id="n_area_cmb_id" required="true"
                        name="areaId" class="easyui-combobox">
                </select><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>NVR名称:</label>
                <input name="name" class="easyui-validatebox" style="width:235px"
                        required="true"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>IP:</label>
                <input  id="nvr_edit_ip" name="ip" class="easyui-validatebox" style="width:235px"
                        required="true"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>备用IP:</label>
                <input id="nvr_edit_ipBack"  name="ipBack" class="easyui-validatebox" style="width:235px"/>
            </div>
            <div class="fitem">
                <label>账号:</label>
                <input name="account" class="easyui-validatebox" style="width:235px"
                        required="true"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>密码:</label>
                <input name="password" class="easyui-validatebox" style="width:235px"
                        required="true"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>端口:</label>
                <input  name="port" class="easyui-numberbox" style="width:245px"
                        required="true" data-options="min:1,precision:0"/><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>类型:</label>
                <select style="width:245px" editable="false"  required="true"
                        name="type" class="easyui-combobox">
                    <option value="1">海康威视</option>
                    <option value="2">大华</option>
                    <option value="3">天地伟业</option>
                    <option value="4">科达</option>
                    <option value="5">宇视</option>
                </select><span style="color:#F00"> * </span>

            </div>
            <div class="fitem">
                <label>厂商:</label>
                <select style="width:245px" editable="false"  required="true"
                        name="vender" class="easyui-combobox">
                    <option value="1">海康威视</option>
                    <option value="2">大华</option>
                    <option value="3">天地伟业</option>
                    <option value="4">科达</option>
                    <option value="5">宇视</option>
                </select><span style="color:#F00"> * </span>
            </div>
        </form>
    </div>
    <div id="nvrEdit-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveNvrEdit()">保存</a> <a href="#" class="easyui-linkbutton"
                                                iconCls="icon-cancel" name="noButton"
                                                onclick="javascript:$('#nvr_edit_dialog').dialog('close')">关闭</a>
    </div>
</div>

<div id="certificate_dialog" class="easyui-dialog"
     style="width: 1050px; height: 600px; padding: 10px 20px" closed="true"
     buttons="#enterprise-buttons">
    <div style="height: 100px;">
        <form id="certificate_form" class="form-style" method="post"
              style="height: 100px;">
            <input type="hidden" id="id" name="id" />
            <div class="fitem">
                <label style="width:80px;">nvr用户名:</label> <input id="account" name="account" class="easyui-validatebox"
                                            required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
                <label style="margin-left: 15px;width:80px;">nvr密码:</label> <input id="password" name="password" class="easyui-validatebox"
                                             required="true" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"><span style="color:#F00"> * </span>
                <label  style="margin-left: 15px;width:80px;">nvr端口:</label> <input id="port" name="port" class="easyui-numberbox" required="true" ><span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label style="width:80px;">办案场所:</label> <select style="width:170px"  editable="false" id="area_cmb_id" name="areaId" class="easyui-combobox"></select><span style="color:#F00"> * </span>
                <label  style="margin-left: 10px;width:80px;">NVR类型:</label><span style="color:#F00"> * </span>
                <select style="width:175px" editable="false" id="type_cmb_id" required="true" name="type" name="vender" class="easyui-combobox">
                    <option value="1" selected>海康威视</option>
                    <option value="2">大华</option>
                    <option value="3">天地伟业</option>
                    <option value="4">科达</option>
                    <option value="5">宇视</option>
                </select><span style="color:#F00"> * </span>
                <label style="margin-left: 10px;width:80px;">添加个数:</label> <input id="addNumber" class="easyui-numberbox"><span style="color:#F00"> 大于1 </span>
                <a  style="margin-left: 10px;width:80px;" href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="nvrAddListDetail()">添加</a>
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
           onclick="saveEnterprise()">保存</a> <a href="#" class="easyui-linkbutton"
                                                iconCls="icon-cancel" name="noButton"
                                                onclick="javascript:$('#certificate_dialog').dialog('close')">关闭</a>
    </div>
</div>

<!-- toolbar -->
<div id="certificateToolbar" style="padding:5px;height:auto">
    <div>
        <a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="nvrAdd" onclick="nvrAdd()">添加</a>
        <a  href="#" class="easyui-linkbutton" iconCls="icon-add" id="nvrAddList" onclick="nvrAddList()">批量添加</a>
        办案场所: <select style="width:150px" editable="true" id="area_cmb"
                      name="areaId" class="easyui-combobox">
                </select>&nbsp;&nbsp;&nbsp;&nbsp;
        NVR名称: <input name="name" id="s_name" class="easyui-textbox" style="width:100px"/>&nbsp;&nbsp;

        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清除</a>

    </div>
</div>
</body>
</html>