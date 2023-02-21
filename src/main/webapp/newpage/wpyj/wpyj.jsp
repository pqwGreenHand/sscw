<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>移交接收</title>
    <%@ include file="../common.jsp" %>
    <script type="text/javascript" src="wpyj.js"></script>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="xm" class="easyui-textbox" data-options="label:'姓名'" style="width:250px;"/>
        <input id="sfzh" class="easyui-textbox" data-options="label:'证件号码'" style="width:250px;"/>
        <a id="cxShow" href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;display: none">查&nbsp;询</a>
        <a id="qcShow"  href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;display: none;">清&nbsp;除</a>
    </div>

    <div data-options="region:'center',border:false" style="width: 65%;height:100%">
        <table id="dg" style="width:100%;height:100%;">
        </table>
    </div>

    <div data-options="region:'east',split:true" style="width: 35%;">
        <table id="lawdocDetailgrid" style="width:100%;height:100%;">
        </table>
    </div>

</div>
<div id="lawdocDetailToolbar" style="padding:5px;height:auto;text-align:right;display: none">
    <a  href="javascript:void(0)" data-options="iconCls:'icon-ok'" onclick="yjAccept()"
       class="easyui-linkbutton button-line-green"
       style="width: 100px;height:35px;">一键接收</a>
    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" onclick="yjRefuse()"
       class="easyui-linkbutton button-line-red"
       style="width: 100px;height:35px;">拒绝接收</a>
    <a href="javascript:void(0)" data-options="iconCls:'icon-search'" onclick="searchImage()"
       class="easyui-linkbutton button-line-red"
       style="width: 100px;height:35px;">查看照片</a>
</div>
<div id="dlg"></div>
<div id="showPic_dialog" style="display: none"></div>
<div id="gm_dialog" style="display: none">
    <form id="gm_form">
        <table style="margin: 0 auto; padding: 10px;width: 90%">
            <tr>
                <td>办案场所选择:</td>
                <td><input id="areaId" name="areaId" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="editable:false"><font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td>储物柜编号:</td>
                <td><input id="lockerId" name="lockerId" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="editable:false"><font color="red">*</font>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="bty_dialog" style="display: none">
    <form id="bty_form">
        <input type="hidden" id="id" name="id"/>
        <table style="margin: 0 auto; padding: 10px;width: 90%">
            <tr>
                <td>不同意接收意见:</td>
                <td><input id="yjyj" name="yjyj" class="easyui-textbox"
                           data-options="multiline:true,required:true"
                           style="width: 230px;height: 80px;overflow-y: hidden;"></td>
            </tr>
        </table>
    </form>
</div>
<script>

</script>
</body>
</html>