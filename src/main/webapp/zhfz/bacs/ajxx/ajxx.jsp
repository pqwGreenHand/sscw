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
    <script type="text/javascript" src="ajxx.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
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
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="caseAdd1()">添加</a>
    <div>
        案件名称:<input id="ajmc" class="easyui-textbox" style="width:150px;height: 23px">
        案件编号:<input id="ajbh" class="easyui-textbox" style="width:150px;height: 23px">
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-search" onclick="lawdocCaseSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-clear" onclick="doCaseClear()">清除</a>
    </div>
</div>

<div id="lawdocDetailToolbar" style="padding:5px;height:auto;">

    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="addPerson(1)">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-add"  name="noButton" onclick="addPersonNoCase(2)">无案件添加</a>
    <div>
        姓名:<input id="name" class="easyui-textbox" style="width:100px;height: 23px">
        证件号码:<input id="certificateNo" class="easyui-textbox" style="width:100px;height: 23px">
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-search" onclick="lawdocPersonSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" name="noButton"  iconCls="icon-clear" onclick="doPersonClear()">清除</a>
    </div>
</div>

<div id="suspectinfo" style="color: white;border-collapse: separate;font-family: '微软雅黑';margin-top: 10px">
    <!--  <div class="xx_change_div_2"> -->
    <div id="dialog_add_suspectinfo" class="easyui-dialog"
         style="width: 550px; height: 440px; padding: 10px 20px" closed="true"
         buttons="add_suspectinfo-buttons">
        <div style="height: 170px;">
            <input type="hidden" id="personEditIndex" name="personEditIndex">
            <input type="hidden" id="pareaId" name="ajbh"/>
            <input type="hidden" id="pajbh" name="ajbh"/>
            <input type="hidden" id="prybh" name="rybh"/>
            <input type="hidden" id="pajmc" name="ajmc"/>

            <form id="addsuspectinfoform" enctype="multipart/form-data" class="form-style"
                  action="ashx/login.ashx" method="post">
                <input type="hidden" id="addorderRequestId" name="orderRequestId">
                <input type="hidden" id="addareaId" name="areaId">
                <input type="hidden" id="addcaseId" name="caseId">
                <input type="hidden" id="addajbh" name="ajbh">
                <input type="hidden" id="addajmc" name="ajmc">
                <input type="hidden" id="personId" name="personId">
                <div class="fitem">
                    <label>嫌疑人姓名:</label>
                    <input id="person_name" name="person_name" class="easyui-validatebox"
                           style="width: 160px"><font color="red" style="margin-left: 2px;">*</font>
                    <%--<a href="#" name="noButton" class="easyui-linkbutton" name="" iconCls="icon-tip"
                       onclick="readCard()">读取身份证</a>--%>
                </div>
                <div class="fitem">
                    <label>证件类型:</label>
                    <input id="person_certificate_type" name="person_certificate_type"
                           value="person_certificate_type" class="easyui-combobox" style="width: 170px">
                    <font color="red" style="margin-left: 2px;">*</font>
                </div>
                <div class="fitem">
                    <label>证件号码:</label>
                    <input id="person_certificate_no"
                           name="person_certificate_no" <%--onblur="checkCertificateNo()" onkeydown--%>
                           onblur="checkCertificateNo()" onkeydown="enterKeyEvents2()"
                           value="person_certificate_no" class="easyui-validatebox" style="width: 170px">
                    <font color="red" style="margin-left: 2px;">*</font>
                </div>
                <div class="fitem">
                    <label>性别:</label>
                    <input class="easyui-combobox" id="person_sex" name="person_sex"
                           style="width: 170px;">
                    <font color="red" style="margin-left: 2px;">*</font>
                </div>
                <div class="fitem">
                    <label>国籍:</label>
                    <input id="person_country" name="person_country" class="easyui-combobox"
                           style="width: 170px"/>
                </div>
                <div class="fitem">
                    <label>民族:</label>
                    <input id="person_nation" name="person_nation" class="easyui-combobox"
                           style="width: 170px"/>
                </div>
            </form>
        </div>
        <div id="add_person-buttons" style="position: relative; top: -1px; width: 376px;" class="dialog-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
               onclick="saveAddSuspectinfo('#addsuspectinfoform')">保存</a> <a href="#"
                                                                       class="easyui-linkbutton" iconCls="icon-cancel"
                                                                       name="noButton"
                                                                       onclick="javascript:$('#dialog_add_suspectinfo').dialog('close')">关闭</a>
        </div>
</div>
</div>
<div>
    <form id="lawdocInfo" class="form-style" method="post" action="../../lawdocProcess/download.do" accept-charset="UTF-8'">
        <input type="hidden" id="number" name="number" />
        <input type="hidden" id="userId" name="userId" />
        <input type="hidden" id="serialNo" name="serialNo" />
        <input type="hidden" id="serialId" name="serialId" />
        <input type="hidden" id="type" name="type" />
        <input type="hidden" id="name1" name="name" />

    </form>

    <%--<form id="exportForm" method="post" action="../../jxkp/download.do">
        &lt;%&ndash;<input id="exportSerialId" name="caseNo" type="hidden"/>&ndash;%&gt;
        <input type="hidden" id="caseNo" name="caseNo" />
    </form>--%>
</div>

<!--关联案件弹框 -->
<div id="link_case_dialog" class="m-popup m-info-popup" style="z-index: 15">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 1450px;height: 650px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(/static/popup-2.png);"></i><span>添加/修改案件</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#link_case_dialog').hide();"></a>
            </div>
            <div id="linkCaseDiv1"  class="bd" style="margin-top: 50px;width: 98%;height: 80%" align="center">
                <table id="link_case_datagrid"></table>
                <!-- toolbar -->
                <div id="caseToolbar" style="padding:5px;height:auto">
                    <div>
                        <a  href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton" onclick="caseAdd1()">添加</a>
                        案件名称: <input name="case_name" id="caseName" class="easyui-textbox" style="width:100px;height: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        主办单位: <select style="width:150px;height: 30px" editable="true" id="org_cmb"
                                      name="org_cmb" class="easyui-combobox">
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchLinkCase()">查询</a>
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearLinkCase()">清除</a>
                    </div>
                </div>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="linkCaseSave()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#link_case_dialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
<!--添加案件弹框-->
<div id="add_case_dialog" class="m-popup m-info-popup" style="z-index: 20">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 750px; height: 550px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(/static/popup-2.png);"></i><span>添加/修改案件</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#add_case_dialog').hide();"></a>
                <%--<a style="margin-left: 50px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton" onclick="linkJzAjxx()">关联警综案件</a>--%>
            </div>
            <div id="relateDiv1" class="bd" style="margin-top: 50px;width: 98%;height:82%" align="center">
                <form id="form_case_add" enctype="multipart/form-data" action="ashx/login.ashx" method="post" style="height: 80%;overflow: auto">
                    <table class="main_form1"  style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 50px">
                        <input id="linkCaseId" name="id" type="hidden">
                        <input id="jzAjxx_ajmc" name="ajmc" type="hidden">
                        <input id="jzAjxx_ajbh" name="ajbh" type="hidden">
                        <input id="jzAjxx_ajzt" name="ajzt" type="hidden">
                        <tr>
                            <td><label>主办民警：</label></td>
                            <td>
                                <input id="zbmjCertificateNo" name="zbmjCertificateNo" class="easyui-validatebox" required="true"
                                       style="width: 150px"  onblur="checkUserId(this)" onfocus="if(this.value == '请输入警号') this.value = ''">
                                <input id="zbmjId" name="zbmjId" type="hidden">
                                <input id="zbmjName" name="zbmjName" type="hidden">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td colspan="2">
                                <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="addXbmj()">添加协办民警</a>
                            </td>
                        </tr>
                        <tr  style="height: 200px">
                            <td><label>协办民警：</label></td>
                            <td colspan="3"  style="height: 200px">
                                <%--                                <input id="xbmjCertificateNo" name="xbmjCertificateNo" class="easyui-validatebox" required="true"--%>
                                <%--                                       style="width: 150px" >--%>
                                <%--                                <input id="xbmjId" name="xbmjId" type="hidden">--%>
                                <table id="xbmjTable" style="height: 200px"> </table>
                            </td>
                        </tr>
                        <tr>
                            <td><label>案件类型：</label></td>
                            <td>
                                <select id="ajlx"	name="ajlx" style="width: 160px" class="easyui-combobox" >
                                </select>
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>案由：</label></td>
                            <td>
                                <input id="ab" name="ab" class="easyui-combobox" style="width: 160px" required="true">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                        </tr>
                        <tr>
                            <td><label>案发时间：</label></td>
                            <td>
                                <input id="afsj" name="afsj" class="easyui-datetimebox" style="width: 160px;height: 25px;"  required="true">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>警情号：</label></td>
                            <td>
                                <input id="jqh" name="jqh" class="easyui-validatebox"style="width: 150px" >
                            </td>
                        </tr>

                        <tr>
                            <td><label>主办单位：</label></td>
                            <td>
                                <input id="zbdwId" name="zbdwId" class="easyui-combobox" style="width: 160px" required="true">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>主要案情：</label></td>
                            <td>
                                <input id="zyaq" name="zyaq" class="easyui-validatebox" style="width: 150px" >
                            </td>
                        </tr>
                        <tr>
                            <td><label >案发地点：<font color="red" style="size: 15px">&nbsp;*</font></label></td>
                            <td>
                                <input class="easyui-combobox" id="province"   style="width:160px;" panelHeight='150'>
                            </td>
                            <td><label ></label></td>
                            <td>
                                <input class="easyui-combobox" id="city"  style="width:160px;" panelHeight='150'>
                            </td>
                        </tr>
                        <tr>
                            <td><label></label></td>
                            <td>
                                <input class="easyui-combobox" id="district"   style="width:160px;" panelHeight='150'>
                            </td>
                            <td><label></label></td>
                            <td>
                                <input class="easyui-combobox" id="street"  style="width:160px;" panelHeight='150'>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <input type="text" id="involvedAddress" class="easyui-validatebox"  name="involvedAddress" style="width:470px;height:50px"/>
                            </td>
                        </tr>
                        </thead>
                    </table>
                </form>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="saveCase()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存并选中</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#add_case_dialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
<!--添加协办民警弹框-->
<div id="addXbmjDialog" class="m-popup m-info-popup" style="z-index: 21">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width:950px;height: 500px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>添加/修改协办民警</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#addXbmjDialog').hide();"></a>
            </div>
            <div id="addXbmjDiv1"  class="bd" style="margin-top: 50px;height: 75%" align="center">
                <table id="addXbmjTable"></table>
                <!-- toolbar -->
                <div id="addXbmjToolbar" style="padding:5px;height:auto;color: white;">
                    <div>
                        民警名称: <input name="case_name" id="xb_name" class="easyui-validatebox" style="width:100px;height: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        民警警号: <input name="case_name" id="xb_certificateNo" class="easyui-validatebox" style="width:100px;height: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        主办单位: <select style="width:120px;height: 30px" editable="true" id="xb_org_cmb"
                                      name="org_cmb" class="easyui-combobox">
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchXbmj()">查询</a>
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearXbmj()">清除</a>
                    </div>
                </div>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="addXbmjSave()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">选择</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#addXbmjDialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
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
<!--
<div id="showView">
      <iframe id="myIFramePrint" src="" align="center" style="width: 1200px;height: 700px;"/>
</div>
    -->
</body>
</html>