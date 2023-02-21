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
    <script type="text/javascript" src="postiondevice.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp" %>
<div data-options="region:'center',split:true"
     style="width: 49%; height: 95%;">
    <table id="datagrid"></table>
</div>
<!-- 按钮 -->
<!-- toolbar 	-->
<div id="deviceToolbar" style="padding:5px;height:auto">


    <!-- 条件查询-->
    <div>
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           id='deviceadd' name="noButton" onclick="deviceadd()">添加</a>
        设备编号:<input id="device_no" class="easyui-textbox" style="width: 100px"/>
        办案场所: <input
            id="s_device_interrogate_area_id" class="easyui-validatebox"
            style="width: 150px">
        功能室: <input
            id="room" class="easyui-combobox"
            style="width: 150px"><a href="#" class="easyui-linkbutton"
                                    iconCls="icon-search" name="noButton" onclick="doSearch()">查询</a> <a
            href="#" class="easyui-linkbutton" iconCls="icon-clear"
            onclick="doClear()" name="noButton">清除</a>

    </div>
</div>

<!-- 添加页面 -->
<div id="data_dialog" class="easyui-dialog"
     style="width: 600px; height: 500px; padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">

    <div style="height: 150px;">
        <form id="data_form" class="form-style" method="post"
              style="height: 150px;">
            <input type="hidden" id="id" name="id"/>
            <div class="fitem">
                <label>设备编号:</label> <input id="deviceNo" name="deviceNo"
                                            class="easyui-validatebox" required="true"
                                            data-options="validType:'maxLength[10]',invalidMessage:'最长10字节'">
                                            <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>设备类型:</label>

                <select style="width: 173px" editable="false" id="deviceType" required="true"
                        name="deviceType" class="easyui-combobox">
                    <option value="1">主基站930</option>
                    <option value="2">激活器</option>
                    <option value="3">从基站931</option>
                </select>
                <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>定位模式:</label>
                <select style="width: 173px" editable="false" id="mode" required="true"
                        name="mode" class="easyui-combobox">
                    <option>SINGLE</option>
                    <option>AB-WAY</option>
                    <option>AB-ROOM</option>
                    <option>GROUP-ON</option>
                    <option>GROUP-OFF</option>
                    <option>PATH</option>
                    <option>OTHER</option>
                </select>
                <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>组名称:</label> <input id="groupName" style="width: 172px"  name="groupName"
                                           class="easyui-numberbox"
                                           data-options="validType:'maxLength[10]',invalidMessage:'最长10字节'">
            </div>
            <div class="fitem">
                <label>组内序号:</label> <input id="groupNo" name="groupNo"
                                            class="easyui-validatebox"
                                            data-options="validType:'maxLength[10]',invalidMessage:'最长10字节'">
            </div>
            <div class="fitem">
                <label>设备ip:</label> <input id="ip" name="ip"
                                            class="easyui-validatebox"
                                            data-options="validType:'maxLength[15]',invalidMessage:'最长15字节'">
            </div>
            <div class="fitem">
                <label>办案场所:</label>
                <input
                        id="areaId" name="areaId" value="areaId" class="easyui-validatebox"
                        style="width: 173px">
                        <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>功能室:</label>
                <input
                        id="roomId" name="roomId" class="easyui-combobox" style="width: 173px">
                        <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>描述:</label> <input id="description" name="description" class="easyui-validatebox"
                                          data-options="validType:'maxLength[10]',invalidMessage:'最长20字节'">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveData()">保存</a> <a href="#" class="easyui-linkbutton"
                                          iconCls="icon-cancel" name="noButton"
                                          onclick="javascript:$('#data_dialog').dialog('close')">关闭</a>
    </div>
</div>

</body>
</html>