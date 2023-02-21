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
    <script type="text/javascript" src="crimedefine.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp" %>


<div data-options="region:'center',split:true" style="width: 60%; height: 95%;">
    <table id="crimeDefineGrid"></table>
</div>


<div id="crime_define_dialog" class="easyui-dialog"
     style="width: 400px; height: 350px; padding: 10px 20px" closed="true"
     buttons="#crime-type-buttons">
    <div style="height: 150px;">
        <form id="crime_define_form" class="form-style" method="post"
              style="height: 150px;">
            <input type="hidden" id="id" name="id"/>
            <div class="fitem">
                <label>案件类型简称:</label>
                <input style="width:138px" id="codeClass" name="codeClass" required="true" class="easyui-validatebox"/>
                <span style="color:#F00"> * </span>
            </div>

            <div class="fitem">
                <label>案件类型 :</label>
                <select style="width:150px" editable="false" id="codeClassDesc" required="true"
                        name="codeClassDesc" class="easyui-combobox">
                    <option>行政案由</option>
                    <option>罪名</option>
                </select>
                <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>案件性质编号:</label>
                <input style="width:138px" id="code" name="code" required="true" class="easyui-validatebox"/>
                <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>案件性质:</label>
                <input style="width:138px" id="codeDesc" name="codeDesc" required="true" class="easyui-validatebox"/>
                <span style="color:#F00"> * </span>
            </div>
            <div class="fitem">
                <label>案件性质简称:</label>
                <input style="width:138px" id="spellCode" name="spellCode" required="true" class="easyui-validatebox"/>
                <span style="color:#F00"> * </span>
            </div>
        </form>
    </div>
    <div id="crime-type-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveCrimeDefine()">保存</a> <a href="#" class="easyui-linkbutton"
                                                 iconCls="icon-cancel" name="noButton"
                                                 onclick="javascript:$('#crime_define_dialog').dialog('close')">关闭</a>
    </div>
</div>

<div id="sub_crime_type_dialog" class="easyui-dialog"
     style="width: 420px; height: 300px; padding: 10px 20px" closed="true"
     buttons="#sub-crimetype-buttons">
    <div style="height: 150px;">
        <form id="subCrimeType_form" class="form-style" method="post"
              style="height: 150px;">
            <input type="hidden" id="id" name="id"/>
            <div class="fitem">
                <label>犯罪类型:</label>
                <select style="width:188px" id="crimeType_cmb" name="crimeTypeId" class="easyui-combobox"
                        required="true">
                </select>
            </div>
            <div class="fitem">
                <label>子类型名称:</label> <input id="subCrimetypeName" name="name" style="width:180px" style="width:180px"
                                             class="easyui-validatebox"
                                             required="true">
            </div>
        </form>
    </div>
    <div id="sub-crimetype-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveSubCrimeType()">保存</a> <a href="#" class="easyui-linkbutton"
                                                  iconCls="icon-cancel" name="noButton"
                                                  onclick="javascript:$('#sub_crime_type_dialog').dialog('close')">关闭</a>
    </div>
</div>


<!-- toolbar -->

<div id="crimeDefineToolbar" style="padding:5px;height:auto">
    <div></br>
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="crimeDefineAdd" name="noButton"
           onclick="crimeDefineAdd()">新增</a>
        案件类型 :<select style="width:140px" id="s_codeClassDesc_cmb" class="easyui-combobox">
            <option></option>
            <option>行政案由</option>
            <option>罪名</option>
        </select>
        案件性质:<input id="s_codeDesc_name" class="easyui-textbox" style="width:200px">

        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search"
           onclick="docrimeDefineSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="docrimeDefineClear()">清除</a>
    </div>
</div>


</body>
</html>