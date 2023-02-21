<%@ page import="com.zhixin.zhfz.common.utils.ControllerTool" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Integer areaId = ControllerTool.getCurrentAreaID(request);
%>

<html>
<head>
    <%@ include file="../../common/common-head.jsp" %>
    <link type="text/css" rel="stylesheet" href="css.css">
    <script type="text/javascript" src="orderRequestSelect.js?21101"></script>
    <script type="text/javascript" src="orderrequest.js?21101"></script>
    <script type="text/javascript" src="orderRequestCombox.js?21101"></script>
    <script type="text/javascript" src="buttonDialogAndCombox.js?21101"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
</head>
<style type="text/css">
    .griddelay {
        background: url(../../../css/images/newicons/arrive.png) no-repeat;
    }

    .gridarrive {
        background: url(image/arrive.png) no-repeat;
        background-size: 15px 15px;
    }

    #form_orderrequest .fitem {
        margin-left: 15px;
    }

    .panel-header {
        height: 30px;
        line-height: 30px;
    }

    .panel-header span {
        font-size: 15px;
        line-height: 30px;
    }
</style>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ" event="OnAfterGWQ_ReadID(ErrorCode,JsonData)" type="text/javascript">
    saveReadCardData(ErrorCode, JsonData);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp" %>
<%@ include file="../../common/case/case.jsp" %>
<%@ include file="../../common/case/jzAjxx.jsp" %>
<input type="hidden" id="sysAreaId" value="<%=areaId%>">
<!-- 数据显示层 start -->
<div data-options="region:'north',split:true">
    <div class="main" style="width: 100%; height: 120px;">
        <div class="box" id="tj_btn" style="margin-bottom: 5px; width: 100%; height: 90px; margin-top: 15px"
             align="center">
            <a onclick="orderRequestAdd()" id="orderRequestAdd" name="orderRequestAdd" class="myButton i1 m-box"
               iconCls="icon-add">
                <span>开始预约</span>
                <div class="edges">
                    </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
        </div>
    </div>
</div>
<div data-options="region:'center',split:true"
     style="width: 76%; height: 84%;">
    <table id="datagrid_orderrequest"></table>
</div>
<div data-options="region:'east',split:true"
     style="width: 24%; height: 84%;">
    <%--<!-- 按钮3 start -->--%>
    <table id="datagrid_orderstatus_person"></table>
</div>

<div id="dialog_add_person" class="easyui-dialog"
     style="width: 550px; height: 440px; padding: 10px 20px" closed="true"
     buttons="add_person-buttons">
    <div style="height: 170px;">
        <form id="addpersoninfoform" enctype="multipart/form-data" class="form-style"
              action="ashx/login.ashx" method="post">
            <input type="hidden" id="addorderPersonId" name="orderPersonId"/>
            <input type="hidden" id="addpersonId" name="personId"/>
            <input type="hidden" id="addpersonRequestId" name="orderRequestId">
            <input type="hidden" id="addareaId" name="areaId">

            <input type="hidden" id="ajbh1" name="ajbh"/>
            <input type="hidden" id="rybh1" name="rybh"/>
            <input type="hidden" id="ajmc1" name="ajmc"/>

            <%--<input type="hidden" id="ajbh2" name="ajbh" />
            <input type="hidden" id="rybh2" name="rybh" />
            <input type="hidden" id="ajmc2" name="ajmc" />--%>

            <div class="fitem">
                <input type="button" name="noButton" value="执法办案平台"
                       onclick="jingzong()" id="jzcq"
                       style="width:88px;height:32px;border:none;background:url(image/botton_b.png);">

                <span id="selectXqJzLable"><font style="color: red">未关联执法办案平台数据！</font></span>
            </div>

            <div class="fitem">
                <label>嫌疑人姓名:</label>
                <input id="addperson_name" name="person_name" class="easyui-validatebox"
                       style="width: 160px"><font color="red" style="margin-left: 2px;">*</font>
                <%--<a href="#" id="readCard" class="easyui-linkbutton" name="" iconCls="icon-tip"enterKeyEvents3
                   onclick="readCard()">读取身份证</a>--%>
            </div>
            <div class="fitem">
                <label>证件类型:</label>
                <input id="addperson_certificate_type" name="person_certificate_type"
                       value="person_certificate_type" class="easyui-combobox" style="width: 170px">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem">
                <label>证件号码:</label>
                <input id="addperson_certificate_no" name="person_certificate_no" <%--onblur="checkCertificateNo2()"--%>
                       onblur="checkCertificateNo2()" onkeydown="enterKeyEvents3()" value="person_certificate_no"
                       class="easyui-validatebox" style="width: 170px">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem">
                <label>性别:</label>
                <input class="easyui-combobox" id="addperson_sex" name="person_sex"
                       style="width: 170px;">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem">
                <label>人员类型：</label>
                <input id="addperson_personInfo" name="person_personInfo"
                       style="width: 170px" class="easyui-combobox">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>

            <div class="fitem">
                <label>国籍:</label>
                <input id="addperson_country" name="person_country" class="easyui-combobox" style="width: 170px"/>
            </div>
            <div class="fitem">
                <label>民族:</label>
                <input id="addperson_nation" name="person_nation" class="easyui-combobox" style="width: 170px"/>
            </div>
            <div class="fitem">
                <label>特殊身份:</label>
                <input id="addspecial_identity" name="other" class="easyui-combobox" style="width: 170px"/>
            </div>
            <div class="fitem">
                <label>是否未成年:</label>
                <%--<input id="addisJuvenile" name="isJuvenile" type="checkbox" value="1">--%>
                <input id="addisJuvenile" type="radio" value="1" name="isJuvenile" />是
                <input id="addisJuvenile" type="radio" value="0" name="isJuvenile"  />否
            </div>
            <div class="fitem">
                <label>是否孕妇:</label>
                <%--<input id="addisGravida" name="isGravida" type="checkbox" value="1">--%>
                <input id="addisGravida" type="radio" value="1" name="isGravida" />是
                <input id="addisGravida" type="radio" value="0" name="isGravida" />否
            </div>
            <div class="fitem">
                <label>怀孕月份:</label>
                <input id="addgravidaMonth" name="gravidaMonth" style="width: 50px" class="easyui-validatebox">个月&nbsp;
            </div>
            <div class="fitem">
                <label>是否有怀孕证明:</label>
                <%--<input id="addisGravidaProve" name="isGravidaProve" type="checkbox" value="1">--%>
                <input id="addisGravidaProve" type="radio" value="1" name="isGravidaProve" />是
                <input id="addisGravidaProve" type="radio" value="0" name="isGravidaProve" />否
            </div>
            <div class="fitem">
                <label>是否有其它疾病:</label>
                <%--<input id="addisDisease" name="isDisease" type="checkbox" value="1">--%>
                <input id="addisDisease" type="radio" value="1" name="isDisease" />是
                <input id="addisDisease" type="radio" value="0" name="isDisease" />否
            </div>
            <div class="fitem">
                <label>是否有疾病证明:</label>
                <%--<input id="addisDiseaseProve" name="isDiseaseProve" type="checkbox" value="1">--%>
                <input id="addisDiseaseProve" type="radio" value="1" name="isDiseaseProve" />是
                <input id="addisDiseaseProve" type="radio" value="0" name="isDiseaseProve" />否
            </div>
            <div class="fitem">
                <label>健康宝和行程卡是否异常:</label>
                <%--<input id="addjkb" name="jkb" type="checkbox" value="1">--%>
                <input id="addjkb" type="radio" value="1" name="jkb" />是
                <input id="addjkb" type="radio" value="0" name="jkb" />否
            </div>
            <div class="fitem">
                <label>是否出入中高风险地区:</label>
                <%--<input id="addsfcrgfxdq" name="sfcrgfxdq" type="checkbox" value="1">--%>
                <input id="addsfcrgfxdq1" type="radio" value="1" onchange="onclickRadio('addsfcrgfxdq','sfcrgfxdq1')" name="sfcrgfxdq1" />是
                <input id="addsfcrgfxdq1" type="radio" value="0" onchange="onclickRadio('addsfcrgfxdq','sfcrgfxdq1')"  name="sfcrgfxdq1" />否
                <input id="addsfcrgfxdq" name="sfcrgfxdq" style="width: 50px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>接种疫苗剂数:</label>
                <input id="addjzyms" name="jzyms" style="width: 50px" class="easyui-validatebox">剂&nbsp;
            </div>
            <div class="fitem">
                <label>嫌疑人体温:</label>
                <input id="addxyrtw" name="xyrtw" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>在京地址:</label>
                <input id="addzjdz" name="zjdz" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>抓获地址:</label>
                <input id="addzhdz" name="zhdz" style="width: 150px" class="easyui-validatebox">
            </div>
            <%--<div class="fitem">--%>
            <%--<label>敏感身份:</label>--%>
            <%--<input id="addmgsf"	name="mgsf"--%>
            <%--style="width: 170px" class="easyui-combobox">--%>
            <%--<font color="red" style="margin-left: 2px;">*</font>--%>
            <%--</div>--%>
            <%--</div>--%>
            <div class="fitem">
                <label>是否涉疫（案件类型）:</label>
                <%--<input id="addsfsjyqldaj" name="sfsjyqldaj" type="checkbox" value="1">--%>
                <input id="addsfsjyqldaj" type="radio" value="1" name="sfsjyqldaj" />是
                <input id="addsfsjyqldaj" type="radio" value="0" name="sfsjyqldaj" />否
            </div>
            <div class="fitem">
                <label>返京日期:</label>
                <input id="addzjfjrq" name="zjfjrq" style="width: 150px" class="easyui-datetimebox">
            </div>
            <div class="fitem">
                <label>近3个月是否有出京出国情况:</label>
                <%--<input id="addj3gysfyjjcgqk" name="j3gysfyjjcgqk" type="checkbox" value="1">--%>
                <input id="addj3gysfyjjcgqk1" type="radio" value="1" onchange="onclickRadio('addj3gysfyjjcgqk','j3gysfyjjcgqk1')" name="j3gysfyjjcgqk1" />是
                <input id="addj3gysfyjjcgqk1" type="radio" value="0" onchange="onclickRadio('addj3gysfyjjcgqk','j3gysfyjjcgqk1')" name="j3gysfyjjcgqk1" />否
                <input id="addj3gysfyjjcgqk" name="j3gysfyjjcgqk" style="width: 50px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>是否有密接接触史:</label>
                <%--<input id="addsfymjjcs" name="sfymjjcs" type="checkbox" value="1">--%>
                <input id="addsfymjjcs" type="radio" value="1" name="sfymjjcs" />是
                <input id="addsfymjjcs" type="radio" value="0" name="sfymjjcs" />否
            </div>
        </form>
    </div>
    <div id="add_person-buttons" style="position: relative; top: -1px; width: 376px;" class="dialog-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveAddperson('#addpersoninfoform')">保存</a> <a href="#"
                                                                   class="easyui-linkbutton" iconCls="icon-cancel"
                                                                   name="noButton"
                                                                   onclick="javascript:$('#dialog_add_person').dialog('close')">关闭</a>
    </div>
</div>

<!-- 预约xiugai  -->
<div id="editorder_data_dialog" class="easyui-dialog"
     style="width: 750px; height: 440px; padding: 10px 20px" closed="true"
     buttons="editorder-buttons">
    <div style="height: 170px;">
        <form id="editform_orderrequest" enctype="multipart/form-data"
              action="ashx/login.ashx" method="post" style="padding-top: 15px;margin-top: 15px;">
            <input type="hidden" id="editorderRequestId" name="id">
            <input type="hidden" id="editorderNo" name="orderNo">
            <input type="hidden" id="editstatus" name="status">
            <input type="hidden" id="editorderUserName" name="orderUserName">
            <div class="fitem" style="margin-top: 25px">
                <label style="width:100px;">预约民警警号：</label>
                <input type="text" id="editdataInUserNo" name="dataInUserNo"
                       onfocus="if(this.value == '请输入警号') this.value = ''" onblur="finduseredit('editdataInUserNo')"
                       class="easyui-validatebox" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'"
                       required="true">
                <input type="hidden" id="editorderUserId" name="orderUserId"><font color="red"
                                                                                   style="margin-left: 2px;">*</font>
                <label style="margin-left: 15px;width:100px;">电话:</label>
                <input id="edituserMobile" name="userMobile" style="font: 14px arial;" class="easyui-validatebox"
                       required="true" onblur="checkMobile(this.value)">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem">
                <label style="width:100px;">预约类型：</label>
                <select id="edittype" name="type" class="easyui-combobox" required="true" style="width:170px;">
                    <option value="0">电话预约</option>
                    <option value="1">现场预约</option>
                    <option value="2">网上预约</option>
                </select>
                <font color="red" style="margin-left: 2px;">*</font>
                <label style="margin-left: 15px;width:100px;">预计到达时间</label>
                <select class="easyui-combobox" id="editplanTime" style="width:170px;"
                        name="planTime" required="true">
                    <option value="1">30分钟内</option>
                    <option value="2">1小时内</option>
                    <option value="3">1.5小时内</option>
                    <option value="4">2小时内</option>
                </select>
            </div>
            <div class="fitem">
                <label style="width:100px;">办案场所：</label>
                <input id="editorderarea" style="width:170px;" name="areaId" class="easyui-combobox" panelHeight='150'
                       required="true"></input>
                <font color="red" style="margin-left: 2px;">*</font>
                <label style="width:100px;margin-left: 15px;">翻译语种：</label>
                <input id="editinterpreter" style="width:170px;" name="interpreter" class="easyui-combobox"
                       panelHeight='150'></input>
            </div>

            <div class="fitem">
                <label style="width:100px;">案件类型：</label>
                <select id="editajlx" name="ajlx" style="width: 170px" class="easyui-combobox" required="true">
                </select>
                <font color="red" style="margin-left: 2px;">*</font>
                <label style="width:100px;margin-left: 15px;">案由：</label>
                <input id="editab" name="ab" class="easyui-combobox" style="width: 170px" required="true">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>

            <div class="fitem" style="margin-top: 15px">
                <label style="width:100px;">基本案情：</label>
                <input id="editjbaq" name="jbaq" class="easyui-textbox" data-options="multiline:true"
                       style="width: 450px;height: 60px;overflow-y: hidden;">
            </div>
        </form>
    </div>
    <div id="editorder-buttons" style="position: relative; top: -1px; width: 376px;" class="dialog-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="editOrder('#editform_orderrequest')">保存</a> <a href="#"
                                                                   class="easyui-linkbutton" iconCls="icon-cancel"
                                                                   name="noButton"
                                                                   onclick="javascript:$('#editorder_data_dialog').dialog('close')">关闭</a>
    </div>
</div>
<!-- 预约 -->
<div id="order_data_dialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width:620px;height: 620px">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>预约信息</span></div>
                <a name="noButton" class="close" onclick="$('#order_data_dialog').hide()"></a>
            </div>
            <div id="jibenxinxi" style="color: white;border-collapse: separate;font-family: '微软雅黑';margin-top: 10px">
                <div class="xx_change_div_1">
                    <form id="form_orderrequest" enctype="multipart/form-data"
                          action="ashx/login.ashx" method="post" style="padding-top: 15px;margin-top: 15px;">
                        <input type="hidden" id="orderRequestId" name="id">
                        <input type="hidden" id="orderNo" name="orderNo">
                        <input type="hidden" id="status" name="status">
                        <input type="hidden" id="orderUserName" name="orderUserName">
                        <div class="fitem" style="margin-top: 25px">
                            <label style="width:100px;">预约民警警号：</label>
                            <input type="text" id="dataInUserNo" name="dataInUserNo"
                                   onfocus="if(this.value == '请输入警号') this.value = ''" onblur="finduser('dataInUserNo')"
                                   class="easyui-validatebox"
                                   data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true">
                            <input type="hidden" id="orderUserId" name="orderUserId"><font color="red"
                                                                                           style="margin-left: 2px;">*</font>
                            <label style="margin-left: 15px;width:100px;">电话:</label>
                            <input id="userMobile" name="userMobile" style="font: 14px arial;"
                                   class="easyui-validatebox" required="true" onblur="checkMobile(this.value)">
                            <font color="red" style="margin-left: 2px;">*</font>
                        </div>
                        <div class="fitem">
                            <label style="width:100px;">预约类型：</label>
                            <select id="type" name="type" class="easyui-combobox" required="true" style="width:170px;">
                                <option value="0">电话预约</option>
                                <option value="1">现场预约</option>
                                <option value="2">网上预约</option>
                            </select>
                            <font color="red" style="margin-left: 2px;">*</font>
                            <label style="margin-left: 15px;width:100px;">预计到达时间：</label>
                            <select class="easyui-combobox" id="planTime" style="width:170px;"
                                    name="planTime" required="true">
                                <option value="1">30分钟内</option>
                                <option value="2">1小时内</option>
                                <option value="3">1.5小时内</option>
                                <option value="4">2小时内</option>
                            </select>
                        </div>
                        <div class="fitem">
                            <label style="width:100px;">办案场所：</label>
                            <input id="orderarea" style="width:170px;" name="areaId" class="easyui-combobox"
                                   panelHeight='150' required="true"></input>
                            <font color="red" style="margin-left: 2px;">*</font>
                            <label style="width:100px;margin-left: 15px;">翻译语种：</label>
                            <input id="interpreter" style="width:170px;" name="interpreter" class="easyui-combobox"
                                   panelHeight='150'></input>
                        </div>
                        <div class="fitem">
                            <label style="width:100px;">案件类型：</label>
                            <select id="ajlxNew" name="ajlx" style="width: 170px" class="easyui-combobox"
                                    required="true">
                            </select>
                            <font color="red" style="margin-left: 2px;">*</font>
                            <label style="width:100px;margin-left: 15px;">案由：</label>
                            <input id="abNew" name="ab" class="easyui-combobox" style="width: 170px" required="true">
                            <font color="red" style="margin-left: 2px;">*</font>
                        </div>
                        <div class="fitem" style="margin-top: 15px">
                            <label style="width:100px;">基本案情：</label>
                            <input id="jbaq" name="jbaq" class="easyui-textbox" data-options="multiline:true"
                                   style="width: 450px;height: 60px;overflow-y: hidden;">
                        </div>
                    </form>
                    <a href="#" style="margin-left: 10px; margin-top: 20px;" class="easyui-linkbutton" name="noButton"
                       iconCls="icon-add" onclick="addPerson()">添加嫌疑人</a>
                    <div style="width: 600px; height: 200px; padding: 10px 20px;">
                        <table id="form_orderrequest_personList" class="easyui-datagrid" height="10px;"></table>
                    </div>
                    <div class="next" style="text-align: center;margin-top: 10px">
                        <button class="m-btn-1 blue" onclick="saveOrderRequest()">保&nbsp;&nbsp;&nbsp;存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button class="m-btn-1 blue" onclick="$('#order_data_dialog').hide()">取&nbsp;&nbsp;&nbsp;消
                        </button>
                    </div>
                </div>
            </div>

            <div id="suspectinfo" style="color: white;border-collapse: separate;font-family: '微软雅黑';margin-top: 10px">
                <!--  <div class="xx_change_div_2"> -->
                <div id="dialog_add_suspectinfo" class="easyui-dialog"
                     style="width: 550px; height: 440px; padding: 10px 20px" closed="true"
                     buttons="add_suspectinfo-buttons">
                    <div style="height: 170px;">
                        <input type="hidden" id="personEditIndex" name="personEditIndex">

                        <input type="hidden" id="ajbh" name="ajbh"/>
                        <input type="hidden" id="rybh" name="rybh"/>
                        <input type="hidden" id="ajmc" name="ajmc"/>

                        <form id="addsuspectinfoform" enctype="multipart/form-data" class="form-style"
                              action="ashx/login.ashx" method="post">
                            <input type="hidden" id="addorderRequestId" name="orderRequestId">
                            <input type="hidden" id="addareaId" name="areaId">

                            <div class="fitem">
                                <input type="button" name="noButton" value="执法办案平台"
                                       onclick="jingzong()" id="jzcq"
                                       style="width:88px;height:32px;border:none;background:url(image/botton_b.png);">
                                <span id="selectJzLable"><font style="color: red">未关联执法办案平台数据！</font></span>
                            </div>


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
                                <label>人员类型：</label>
                                <input id="person_personInfo" name="person_personInfo"
                                       style="width: 170px" class="easyui-combobox">
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
                            <div class="fitem">
                                <label>特殊身份:</label>
                                <input id="special_identity" name="other" class="easyui-combobox" style="width: 170px"/>
                            </div>
                            <div class="fitem">
                                <label>是否未成年:</label>
                                    <%--<input id="isJuvenile" name="isJuvenile" type="checkbox" value="1">--%>
                                <input id="isJuvenile" type="radio" value="1" name="isJuvenile" />是
                                <input id="isJuvenile" type="radio" value="0" name="isJuvenile" />否
                            </div>
                            <div class="fitem">
                                <label>是否孕妇:</label>
                                    <%--<input id="isGravida" name="isGravida" type="checkbox" value="1">--%>
                                <input id="isGravida" type="radio" value="1" name="isGravida" />是
                                <input id="isGravida" type="radio" value="0" name="isGravida" />否
                            </div>
                            <div class="fitem">
                                <label>怀孕月份:</label>
                                <td>
                                    <input id="gravidaMonth" name="gravidaMonth" style="width: 50px"
                                           class="easyui-validatebox">个月&nbsp;
                            </div>
                            <div class="fitem">
                                <label>是否有怀孕证明:</label>
                                    <%--<input id="isGravidaProve" name="isGravidaProve" type="checkbox" value="1">--%>
                                <input id="isGravidaProve" type="radio" value="1" name="isGravidaProve" />是
                                <input id="isGravidaProve" type="radio" value="0" name="isGravidaProve" />否
                            </div>
                            <div class="fitem">
                                <label>是否有其它疾病:</label>
                                    <%--<input id="isDisease" name="isDisease" type="checkbox" value="1">--%>
                                <input id="isDisease" type="radio" value="1" name="isDisease" />是
                                <input id="isDisease" type="radio" value="0" name="isDisease" />否
                            </div>
                            <div class="fitem">
                                <label>是否有疾病证明:</label>
                                    <%--<input id="isDiseaseProve" name="isDiseaseProve" type="checkbox" value="1">--%>
                                <input id="isDiseaseProve" type="radio" value="1" name="isDiseaseProve" />是
                                <input id="isDiseaseProve" type="radio" value="0" name="isDiseaseProve" />否
                            </div>

                            <div class="fitem">
                                <label>健康宝和行程卡是否异常:</label>
                                    <%--<input id="jkb" name="jkb" type="checkbox" value="1">--%>
                                <input id="jkb" type="radio" value="1" name="jkb" />是
                                <input id="jkb" type="radio" value="0" name="jkb" />否
                            </div>
                            <div class="fitem">
                                <label>是否出入中高风险地区:</label>
                                    <%--<input id="sfcrgfxdq" name="sfcrgfxdq" type="checkbox" value="1">--%>
                                <input id="sfcrgfxdq1" type="radio" value="1" onchange="onclickRadio('sfcrgfxdq','sfcrgfxdq1')" name="sfcrgfxdq1" />是
                                <input id="sfcrgfxdq1" type="radio" value="0" onchange="onclickRadio('sfcrgfxdq','sfcrgfxdq1')" name="sfcrgfxdq1" />否
                                <input id="sfcrgfxdq" name="sfcrgfxdq" style="width: 50px" class="easyui-validatebox">
                            </div>
                            <div class="fitem">
                                <label>接种疫苗剂数:</label>
                                <td>
                                    <input id="jzyms" name="jzyms" style="width: 50px" class="easyui-validatebox">剂&nbsp;
                            </div>

                            <div class="fitem">
                                <label>嫌疑人体温:</label>
                                <input id="xyrtw" name="xyrtw" style="width: 150px" class="easyui-validatebox">
                            </div>
                            <div class="fitem">
                                <label>在京地址:</label>
                                <input id="zjdz" name="zjdz" style="width: 150px" class="easyui-validatebox">
                            </div>
                            <div class="fitem">
                                <label>抓获地址:</label>
                                <input id="zhdz" name="zhdz" style="width: 150px" class="easyui-validatebox">
                            </div>
                            <div class="fitem">
                                <label>是否涉疫（案件类型）:</label>
                                <%--<input id="sfsjyqldaj" name="sfsjyqldaj" type="checkbox" value="1">--%>
                                <input id="sfsjyqldaj" type="radio" value="1" name="sfsjyqldaj" />是
                                <input id="sfsjyqldaj" type="radio" value="0" name="sfsjyqldaj" />否
                            </div>
                            <div class="fitem">
                                <label>返京日期:</label>
                                <input id="zjfjrq" name="zjfjrq" style="width: 150px" class="easyui-datetimebox">
                            </div>
                            <div class="fitem">
                                <label>近3个月是否有出京出国情况:</label>
                                <input id="j3gysfyjjcgqk2" type="radio" value="1" onchange="onclickRadio('j3gysfyjjcgqk','j3gysfyjjcgqk1')" name="j3gysfyjjcgqk1" />是
                                <input id="j3gysfyjjcgqk2" type="radio" value="0" onchange="onclickRadio('j3gysfyjjcgqk','j3gysfyjjcgqk1')" name="j3gysfyjjcgqk1" />否
                                <input id="j3gysfyjjcgqk" name="j3gysfyjjcgqk" style="width: 50px" class="easyui-validatebox">
                            </div>
                            <div class="fitem">
                                <label>是否有密接接触史:</label>
                                <%--<input id="sfymjjcs" name="sfymjjcs" type="checkbox" value="1">--%>
                                <input id="sfymjjcs" type="radio" value="1" name="sfymjjcs" />是
                                <input id="sfymjjcs" type="radio" value="0" name="sfymjjcs" />否
                            </div>
                        </form>
                    </div>
                    <div id="add_suspectinfo-buttons" style="position: relative; top: -1px; width: 376px;"
                         class="dialog-button">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
                           onclick="saveAddSuspectinfo('#addsuspectinfoform')">保存</a> <a href="#"
                                                                                         class="easyui-linkbutton"
                                                                                         iconCls="icon-cancel"
                                                                                         name="noButton"
                                                                                         onclick="javascript:$('#dialog_add_suspectinfo').dialog('close')">关闭</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
<!-- 数据显示层 end -->
<!-- 数据添加窗口2start -->
<div id="dialog_orderrequest_person" class="easyui-dialog"
     style="width: 600px; height: 460px; padding: 10px 20px" closed="true"
     buttons="#organization-buttons2">
    <!-- <div class="ftitle">嫌疑人信息</div> -->
    <div style="height: 170px;">
        <form id="form_orderrequest_person" class="form-style" method="post"
              style="height: 170px;">

            <%--<input type="hidden" id="ajbh1" name="ajbh" />
            <input type="hidden" id="rybh1" name="rybh" />
            <input type="hidden" id="ajmc1" name="ajmc" />--%>

            <input type="hidden" id="orderPersonEditIndex" name="orderPersonEditIndex"/>
            <input type="hidden" id="editorderPersonId" name="orderPersonId"/>
            <input type="hidden" id="editpersonId" name="personId"/>

            <div class="fitem">
                <!-- 填写预约内容 -->
                <input type="button" name="noButton" value="执法办案平台"
                       onclick="jingzong()" id="jzcq"
                       style="width:88px;height:32px;border:none;background:url(image/botton_b.png);">
                <span id="selectXqJzLable"><font style="color: red">未关联执法办案平台数据！</font></span>
            </div>

            <div class="fitem">
                <!-- 填写预约内容 -->
                <label>嫌疑人姓名:</label>
                <input id="edit_name" name="name" class="easyui-validatebox" required="true"
                       style="width: 170px">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>

            <div class="fitem">
                <label>证件类型:</label>
                <input id="edit_certificateTypeId" name="certificateTypeId"
                       value="" class="easyui-combobox"
                       style="width: 170px" required="true">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem">
                <label>证件号码:</label>
                <input id="edit_certificateNo" name="certificateNo"
                       onkeydown="enterKeyEvents()" value="" class="easyui-validatebox"
                       style="width: 170px" required="true">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem">
                <label>性别:</label>
                <select class="easyui-combobox" id="edit_sex" name="sex"
                        style="width: 170px;" required="true">
                    <font color="red" style="margin-left: 2px;">*</font>
                    <option value="1" selected="selected">男</option>
                    <option value="2">女</option>
                    <option value="0">未知的性别</option>
                    <option value="9">未说明的性别</option>
                </select>
            </div>
            <div class="fitem">
                <label>人员类型</label>
                <input id="edit_personInfo" name="personInfo" style="width: 170px"
                       class="easyui-combobox">
                <font color="red" style="margin-left: 2px;">*</font>
            </div>

            <div class="fitem">
                <label>国籍:</label>
                <input id="edit_country" name="country"
                       class="easyui-combobox"
                       style="width: 170px" required="true">
            </div>
            <div class="fitem">
                <label>民族:</label>
                <input id="edit_nation" name="nation"
                       class="easyui-combobox"
                       style="width: 170px" required="true">
            </div>
            <div class="fitem">
                <label>特殊身份:</label>
                <input id="edit_specialIdentity" name="other" class="easyui-combobox" style="width: 170px"/>
            </div>
            <div class="fitem">
                <label>是否未成年:</label>
                <%--<input id="edit_eisJuvenile" name="edit_eisJuvenile" type="checkbox" value="1">--%>
                <input id="edit_eisJuvenile" type="radio" value="1" name="edit_eisJuvenile" />是
                <input id="edit_eisJuvenile" type="radio" value="0" name="edit_eisJuvenile" />否
            </div>
            <div class="fitem">
                <label>是否孕妇:</label>
                <%--<input id="edit_eisGravida" name="edit_eisGravida" type="checkbox" value="1">--%>
                <input id="edit_eisGravida" type="radio" value="1" name="edit_eisGravida" />是
                <input id="edit_eisGravida" type="radio" value="0" name="edit_eisGravida" />否
            </div>
            <div class="fitem">
                <label>怀孕月份:</label>
                <input id="edit_egravidaMonth" name="edit_egravidaMonth" class="easyui-validatebox" style="width: 30px">个月&nbsp;
            </div>
            <div class="fitem">
                <label>是否有怀孕证明:</label>
                <%--<input id="edit_eisGravidaProve" name="edit_eisGravidaProve" type="checkbox" value="1">--%>
                <input id="edit_eisGravidaProve" type="radio" value="1" name="edit_eisGravidaProve" />是
                <input id="edit_eisGravidaProve" type="radio" value="0" name="edit_eisGravidaProve" />否
            </div>
            <div class="fitem">
                <label>是否有其它疾病:</label>
                <%--<input id="edit_eisDisease" name="edit_eisDisease" type="checkbox" value="1">--%>
                <input id="edit_eisDisease" type="radio" value="1" name="edit_eisDisease" />是
                <input id="edit_eisDisease" type="radio" value="0" name="edit_eisDisease" />否
            </div>
            <div class="fitem">
                <label>是否有疾病证明:</label>
                <%--<input id="edit_eisDiseaseProve" name="edit_eisDiseaseProve" type="checkbox" value="1">--%>
                <input id="edit_eisDiseaseProve" type="radio" value="1" name="edit_eisDiseaseProve" />是
                <input id="edit_eisDiseaseProve" type="radio" value="0" name="edit_eisDiseaseProve" />否
            </div>
            <div class="fitem">
                <label>健康宝和行程卡是否异常:</label> </td>
                <td>
                    <%--<input id="editjkb" name="jkb" type="checkbox" value="1">--%>
                        <input id="editjkb" type="radio" value="1" name="jkb" />是
                        <input id="editjkb" type="radio" value="0" name="jkb" />否
            </div>
            <div class="fitem">
                <label>是否出入中高风险地区:</label> </td>
                <td>
                    <%--<input id="editsfcrgfxdq" name="sfcrgfxdq" type="checkbox" value="1">--%>
                        <input id="editsfcrgfxdq1" type="radio" value="1" onchange="onclickRadio('editsfcrgfxdq','sfcrgfxdq1')" name="sfcrgfxdq1" />是
                        <input id="editsfcrgfxdq1" type="radio" value="0" onchange="onclickRadio('editsfcrgfxdq','sfcrgfxdq1')" name="sfcrgfxdq1" />否
                        <input id="editsfcrgfxdq" name="sfcrgfxdq" style="width: 50px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>接种疫苗剂数:</label> </td>
                <td>
                    <input id="editjzyms" name="jzyms" style="width: 50px" class="easyui-validatebox">剂&nbsp;
            </div>
            <div class="fitem">
                <label>嫌疑人体温:</label>
                <input id="editxyrtw" name="xyrtw" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>在京地址:</label>
                <input id="editzjdz" name="zjdz" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>抓获地址:</label>
                <input id="editzhdz" name="zhdz" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>是否涉疫（案件类型）:</label>
                <%--<input id="editsfsjyqldaj" name="sfsjyqldaj" type="checkbox" value="1">--%>
                <input id="editsfsjyqldaj" type="radio" value="1" name="sfsjyqldaj" />是
                <input id="editsfsjyqldaj" type="radio" value="0" name="sfsjyqldaj" />否
            </div>
            <div class="fitem">
                <label>返京日期:</label>
                <input id="editzjfjrq" name="zjfjrq" style="width: 150px" class="easyui-datetimebox">
            </div>
            <div class="fitem">
                <label>近3个月是否有出京出国情况:</label>
                <%--<input id="editj3gysfyjjcgqk" name="j3gysfyjjcgqk" type="checkbox" value="1">--%>
                <input id="editj3gysfyjjcgqk1" type="radio" value="1" onchange="onclickRadio('editj3gysfyjjcgqk','j3gysfyjjcgqk1')" name="j3gysfyjjcgqk1" />是
                <input id="editj3gysfyjjcgqk1" type="radio" value="0" onchange="onclickRadio('editj3gysfyjjcgqk','j3gysfyjjcgqk1')" name="j3gysfyjjcgqk1" />否
                <input id="editj3gysfyjjcgqk" name="j3gysfyjjcgqk" style="width: 50px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>是否有密接接触史:</label>
                <%--<input id="editsfymjjcs" name="sfymjjcs" type="checkbox" value="1">--%>
                <input id="editsfymjjcs" type="radio" value="1" name="sfymjjcs" />是
                <input id="editsfymjjcs" type="radio" value="0" name="sfymjjcs" />否
            </div>
        </form>
    </div>
    <div id="organization-buttons2">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveEditSuspectinfo('#form_orderrequest_person')">保存</a> <a href="#"
                                                                                class="easyui-linkbutton"
                                                                                iconCls="icon-cancel" name="noButton"
                                                                                onclick="javascript:$('#dialog_orderrequest_person').dialog('close')">关闭</a>
    </div>
</div>

<!-- 人员详情窗口 start -->
<div id="dialog_orderrequest_person_detail" class="easyui-dialog"
     style="width: 700px; height: 450px; padding: 10px 20px" closed="true"
     buttons="#person-buttons">
    <div class="ftitle">人员信息</div>
    <div style="height: 150px;">
        <form id="form_orderrequest_person_detai" class="form-style"
              method="post" style="height: 150px;font-size: 15px">
            <input type="hidden" id="contentId_person" name="contentId_person"/>
            <input type="hidden" id="requestId_person" name="requestId_person"/>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>嫌疑人:</label>
                <input id="name_person" name="name_person"
                       class="easyui-validatebox" style="width: 130px" readonly="readonly">
                <label style="margin-left: 20px">证件类型:</label>
                <input id="certificateTypeId_person" name="certificateTypeId_person"
                       value="certificateTypeId_person" class="easyui-combobox" style="width: 130px"
                       readonly="readonly">
            </div>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>证件号码:</label>
                <input id="certificateNo_person" name="certificateNo_person"
                       value="certificateNo_person" class="easyui-validatebox" style="width: 130px" readonly="readonly">
                <label style="margin-left: 20px">性别:</label>
                <select class="easyui-combobox" id="sex_person" name="sex_person" style="width: 130px;"
                        readonly="readonly">
                </select>
            </div>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>是否未成年:</label>
                <input id="xqisJuvenile" type="radio" value="1" name="xqisJuvenile" />是
                <input id="xqisJuvenile" type="radio" value="0" name="xqisJuvenile" />否
                <%--<input id="xqisJuvenile" name="xqisJuvenile" type="checkbox" value="1" readonly="readonly">--%>
                <label style="margin-left: 20px">是否有监护人:</label>
                <%--<input id="xqisGuardian" name="xqisGuardian" type="checkbox" value="1" readonly="readonly">--%>
                <input id="xqisGuardian" type="radio" value="1" name="xqisGuardian" />是
                <input id="xqisGuardian" type="radio" value="0" name="xqisGuardian" />否
            </div>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>是否孕妇:</label>
                <input id="xqisGravida" type="radio" value="1" name="xqisGravida" />是
                <input id="xqisGravida" type="radio" value="0" name="xqisGravida" />否
                <%--<input id="xqisGravida" name="xqisGravida" type="checkbox" value="1" readonly="readonly">--%>
                <label style="margin-left: 20px">怀孕月份:</label>
                <input id="xqgravidaMonth" name="xqgravidaMonth" class="easyui-validatebox" style="width: 60px"
                       readonly="readonly">个月&nbsp;
            </div>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>是否有怀孕证明:</label>
                <input id="xqisGravidaProve" type="radio" value="1" name="xqisGravidaProve" />是
                <input id="xqisGravidaProve" type="radio" value="0" name="xqisGravidaProve" />否
                <%--<input id="xqisGravidaProve" name="xqisGravidaProve" type="checkbox" value="1" readonly="readonly">--%>
                <label style="margin-left: 20px">是否有其它疾病:</label>
                <%--<input id="xqisDisease" name="xqisDisease" type="checkbox" value="1" readonly="readonly">--%>
                <input id="xqisDisease" type="radio" value="1" name="xqisDisease" />是
                <input id="xqisDisease" type="radio" value="0" name="xqisDisease" />否
            </div>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>是否有疾病证明:</label>
                <%--<input id="xqisDiseaseProve" name="xqisDiseaseProve" type="checkbox" value="1" readonly="readonly">--%>
                <input id="xqisDiseaseProve" type="radio" value="1" name="xqisDiseaseProve" />是
                <input id="xqisDiseaseProve" type="radio" value="0" name="xqisDiseaseProve" />否
                <label style="margin-left: 20px">健康宝和行程卡是否异常:</label>
                <%--<input id="xqjkb" name="xqjkb" type="checkbox" value="1">--%>
                <input id="xqjkb" type="radio" value="1" name="xqjkb" />是
                <input id="xqjkb" type="radio" value="0" name="xqjkb" />否
            </div>

            <div class="fitem">
                <label>是否出入中高风险地区:</label>
                <%--<input id="xqsfcrgfxdq" name="xqsfcrgfxdq" type="checkbox" value="1">--%>
                <input id="xqsfcrgfxdq1" type="radio" value="1" name="xqsfcrgfxdq" />是
                <input id="xqsfcrgfxdq1" type="radio" value="0" name="xqsfcrgfxdq" />否
                <input id="xqsfcrgfxdq" name="xqsfcrgfxdq1" style="width: 50px" class="easyui-validatebox">
                <label style="margin-left: 20px">接种疫苗剂数:</label>
                <input id="xqjzyms" name="xqjzyms" style="width: 50px" class="easyui-validatebox">剂
            </div>


            <div class="fitem">
                <label>嫌疑人体温:</label>
                <input id="xqxyrtw" name="xyrtw" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>在京地址:</label>
                <input id="xqzjdz" name="zjdz" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>抓获地址:</label>
                <input id="xqzhdz" name="zhdz" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>是否涉疫（案件类型）:</label>
                <input id="xqsfsjyqldaj" type="radio" value="1" name="xqsfsjyqldaj" />是
                <input id="xqsfsjyqldaj" type="radio" value="0" name="xqsfsjyqldaj" />否
                <%--<input id="xqsfsjyqldaj" name="xqsfsjyqldaj" type="checkbox" value="1">--%>
            </div>
            <div class="fitem">
                <label>返京日期:</label>
                <input id="xqzjfjrq" name="zjfjrq" style="width: 150px" class="easyui-datetimebox">
            </div>
            <div class="fitem">
                <label>近3个月是否有出京出国情况:</label>
                <%--<input id="xqj3gysfyjjcgqk" name="xqj3gysfyjjcgqk" type="checkbox" value="1">--%>
                <input id="xqj3gysfyjjcgqk1" type="radio" value="1" name="xqj3gysfyjjcgqk" />是
                <input id="xqj3gysfyjjcgqk1" type="radio" value="0" name="xqj3gysfyjjcgqk" />否
                <input id="xqj3gysfyjjcgqk" name="xqj3gysfyjjcgqk1" style="width: 50px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>是否有密接接触史:</label>
                <%--<input id="xqsfymjjcs" name="xqsfymjjcs" type="checkbox" value="1">--%>
                <input id="xqsfymjjcs" type="radio" value="1" name="xqsfymjjcs" />是
                <input id="xqsfymjjcs" type="radio" value="0" name="xqsfymjjcs" />否
            </div>

            <div class="fitem">
                <label>是否敏感身份:</label>
                <%--<input id="xqsfmgsf" name="xqsfmgsf" type="checkbox" value="1">--%>
                <input id="xqsfmgsf" type="radio" value="1" name="xqsfmgsf" />是
                <input id="xqsfmgsf" type="radio" value="0" name="xqsfmgsf" />否
            </div>
            <div class="fitem">
                <label>特殊身份:</label>
                <input id="xq_specialIdentity" name="mgsf" style="width: 150px" class="easyui-validatebox">
            </div>
            <div class="fitem">
                <label>审核状态:</label>
                <input id="shzt" name="shzt" style="width: 130px" readonly="readonly" class="easyui-validatebox">
                <label style="margin-left: 20px">审核结果:</label>
                <input id="shjg" name="shjg" style="width: 130px" readonly="readonly" class="easyui-validatebox">
            </div>
        </form>
    </div>
    <div id="#person-buttons" align="right">
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#dialog_orderrequest_person_detail').dialog('close')">关闭</a>
    </div>
</div>


<!-- 审核窗口 start -->
<div id="sh_dialog" class="easyui-dialog"
     style="width:400px; height: 300px; padding: 10px 20px" closed="true"
     buttons="#sh-buttons">
    <div class="ftitle">是否同意该预约下的嫌疑人入区</div>
    <div style="height: 150px;">
        <form id="shForm" class="form-style"
              method="post" style="height: 150px;font-size: 15px">
            <input type="hidden" id="orderPersonId" name="id"/>
            <input type="hidden" id="orderId" name="orderRequestId"/>
            <input type="hidden" id="orderUserId1" name="orderUserId"/>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>同意:</label>
                <input name="shzt" type="radio" value="1" checked="checked" style="margin-top:4px;"/>
            </div>
            <div class="fitem" style="font-size: 20px;height: 35px">
                <label>不同意:</label>
                <input type="radio" name="shzt" value="2"/>
            </div>
        </form>
    </div>
    <div id="#sh-buttons" align="right">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveOrdderSh()">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
           onclick="javascript:$('#sh_dialog').dialog('close')">关闭</a>
    </div>
</div>

<!-- 不同意审核窗口 start -->
<div id="shjg_dialog" class="easyui-dialog"
     style="width:400px; height: 300px; padding: 10px 20px" closed="true"
     buttons="#shjg-buttons">
    <div class="ftitle">填写不同意理由</div>
    <div style="height: 150px;">
        <form id="shjgForm" class="form-style"
              method="post" style="height: 150px;font-size: 15px">
            <div class="fitem" style="margin-top: 15px">
                <label style="width:100px;">不同意原因：</label>
                <input id="btyshjg" name="shjg" class="easyui-textbox" data-options="multiline:true"
                       style="width: 150px;height: 60px;overflow-y: hidden;">
            </div>
        </form>
    </div>
    <div id="#shjg-buttons" align="right">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveBtyOrdderSh()">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
           onclick="javascript:$('#shjg_dialog').dialog('close')">关闭</a>
    </div>
</div>

<!-- 审核窗口列表 start -->
<div id="shlist_dialog" class="easyui-dialog"
     style="width:750px; height: 500px; padding: 10px 20px" closed="true"
     buttons="#shlist-buttons">
    <%--<div data-options="region:'center',split:true"--%>
    <%--style="width: 100%; height: 100%;">--%>
    <table id="datagrid_orderstatus_person1"></table>
    <%--</div>--%>
    <div id="#shlist-buttons" align="right">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="yjSaveSh()">一键审核</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
           onclick="javascript:$('#shlist_dialog').dialog('close')">关闭</a>
    </div>
</div>

<!-- 数据添加窗口2start -->
<div id="delay_orderrequest" class="easyui-dialog"
     style="width: 300px; height: 300px; padding: 10px 20px" closed="true"
     buttons="#delay-buttons">
    <div>
        <form id="form_orderrequest_delay" class="form-style" method="post">
            <input type="hidden" id="delayId" name="delayId"/> <input
                type="hidden" id="planTimeDelay" name="planTimeDelay">
            <input type="hidden" id="delayStatus" name="delayStatus"/>
            <div class="fitem">
                <label style="font-size: 15px">延期时间:</label><font color="red" style="margin-left: 2px;">*</font>
            </div>
            <div class="fitem" style="margin-top: 20px">
                <select class="easyui-combobox" id="delayTime"
                        name="delayTime" style="width: 200px;height: 20px" panelHeight='150'>
                    <option value="1" selected="selected">30分钟内</option>
                    <option value="2">1小时内</option>
                    <option value="3">1.5小时内</option>
                    <option value="4">2小时内</option>
                </select>
            </div>
        </form>
    </div>
    <div id="delay-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveDelayRequest()">保存</a> <a href="#"
                                                  class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
                                                  onclick="javascript:$('#delay_orderrequest').dialog('close')">关闭</a>
    </div>
</div>
<!-- 人员详情窗口 end -->
<!-- 按钮2 start -->
<div id="Toolbar_orderrequest" style="padding: 5px; height: auto">
    预约民警: <input id="o_user_name" class="easyui-validatebox" style="width: 70px">
    嫌疑人: <input id="o_person_name" class="easyui-validatebox" style="width: 70px">
    预约编号:<input id="o_order_no"
                class="easyui-validatebox" style="width: 180px">
    </a>
    <label>办案场所:</label>
    <input id="interrogatearea" name="interrogatearea" class="easyui-combobox" data-options="" style="width: 180px;"
           editable="false">
    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="SearchOrderRequest" name="noButton"
       onclick="SearchOrderRequest()">查询</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-clear" id="doClearOrderRequest" name="noButton"
       onclick="doClearOrderRequest()">清除</a>
    <%--<a href="#"class="easyui-linkbutton" iconCls="icon-clear" id="doExportOrderRequest" name="noButton"onclick="doexportOrderRequest()">导出</a>--%>
    <a style="cursor: default;" class="m-btn-2 brown" id="roomRecord">
        <span onclick="doexportOrderRequest()"><i
                style="background-image: url(../../../static/btn-5.png);"></i>导出</span>
    </a>
</div>

<form id="exportForm" name="exportForm" method="post" action="../export/exportExcelForOrder.do">
    <input type="hidden" id="areaId_exp" name="areaId">
    <input type="hidden" id="userName_exp" name="userName">
    <input type="hidden" id="orderNo_exp" name="orderNo">
    <input type="hidden" id="personName_exp" name="personName">
</form>

<!--警综平台-->
<div id="jz_orderrequest" class="easyui-dialog"
     style="width: 1000px; height: 600px; padding: 0px;overflow:hidden;" closed="true"
     buttons="#jz-buttons">

    <div id="toolbar_jzInfo" style="padding: 5px; height: auto">
        案件名称: <input id="jzInfo_name" class="easyui-validatebox" style="width: 170px">
        案件类别: <select class="easyui-combobox" id="jzajlb"
                      name="jzajlb" style="width: 100px;">
        <option value="1">刑事</option>
        <option value="2">行政</option>
    </select>
        <a href="#"
           class="easyui-linkbutton" iconCls="icon-search"
           id="SearchJZInfo" name="noButton" onclick="SearchJZInfo()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-clear" id="doClearJZInfo" name="noButton"
           onclick="doClearJZInfo()">清除</a>
    </div>

    <div data-options="region:'center',split:true" style="width:100%; height: 60%;">
        <table id="jzInfo">

        </table>
    </div>
    <div data-options="region:'east',split:true" style="width:100%;height: 40%;">

        <table id="jzPerson">

        </table>

    </div>
    <div id="jz-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
           onclick="saveZJInfo()">保存</a> <a href="#"
                                            class="easyui-linkbutton" iconCls="icon-cancel" name="noButton"
                                            onclick="javascript:$('#jz_orderrequest').dialog('close')">取消</a>
    </div>
</div>


<!-- 按钮3 start -->
<div id="userNoInfo" class="easyui-dialog" closed="true">
    <input type="hidden" id="userNo" name="userNo"/>
</div>
<!-- 按钮2 end -->
<div>
    <input type="text" id="addIdValue">
</div>
</body>
</html>