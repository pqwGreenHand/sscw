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
    <title>人员管理</title>
    <%@ include file="../common.jsp" %>
    <script type="text/javascript" src="sswpout.js"></script>
    <%
        String ssareaid = request.getParameter("ssareaid");
    %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'center',border:false" style="height:65%">
        <table id="detid" style="width:100%;height:100%;">
        </table>
        <div id="tb" style="display: none">
            <label>嫌疑人姓名:</label>
            <input id="serialIdQuery" name="serialId" class="easyui-combogrid" data-options="editable:false"
                   style="margin: -2px; width: 170px; height: 28px">
            &nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0)" onclick="shuaxin()" data-options="iconCls:'icon-update'"
               class="easyui-linkbutton button-line-blue"
               style="width: 160px;width:100px;">刷新</a>
        </div>
    </div>
    <div data-options="region:'south',border:false" style="height:20%;padding: 30px 5px;">

        <div style="text-align: center;display: none;" id="outshow" >
            <input id="ssareaid" type="hidden" name="ssareaid" value="<%=ssareaid%>">
            <a href="javascript:void(0)" onclick="boxopen()"  data-options="iconCls:'icon-recover-deleted-items'" class="easyui-linkbutton button-line-blue"
               style="width: 160px;height:40px;margin-left: 10px;">提取全部</a>
<%--            <a href="javascript:void(0)" onclick="showphotowid()"  data-options="iconCls:'icon-photo'" class="easyui-linkbutton button-line-blue"--%>
<%--               style="width: 100px;height:40px;margin-left: 10px;">拍&nbsp;照</a>--%>
            <a href="javascript:void(0)" onclick="boxoutopen()"  data-options="iconCls:'icon-lock-go'" class="easyui-linkbutton button-line-blue"
               style="width: 100px;height:40px;margin-left: 10px;">开&nbsp;柜</a>
            <a href="javascript:void(0)" onclick="securityDownLoad()"  data-options="iconCls:'icon-print'"  class="easyui-linkbutton button-line-blue"
               style="width: 100px;height:40px;margin-left: 10px;">台&nbsp;账</a>
            <a href="javascript:void(0)" onclick="showImages()"  data-options="iconCls:'icon-large-picture'"  class="easyui-linkbutton button-line-blue"
               style="width: 100px;height:40px;margin-left: 10px;">照&nbsp;片</a>
            <a href="javascript:void(0)" data-options="iconCls:'icon-back'" onclick="javascript:window.location.href ='index.jsp';" class="easyui-linkbutton button-line-red"
               style="width: 100px;height:40px;margin-left: 10px;">返回</a>
        </div>

    </div>
</div>
<div id="wpImage" style="display: none"></div>
<div id="lqwpxx" style="display: none">
    <form id="openbill_form">
        <input type="hidden" id="belongingsId" name="belongingsId"/>
        <input type="hidden" id="lockerId" name="lockerId"/>
        <table style="margin: 0 auto; padding: 10px;width: 90%">
            <tr>
                <td>领取方式:</td>
                <td> <select class="easyui-combobox" editable="false" id="getWay" name="getWay"  required="true"
                             style="width: 160px;">
                    <option value="1">本人领取</option>
                    <option value="2">委托他人代为领取</option>
                    <%--<option value="3">本人收到扣押物品清单</option>--%>
                    <option value="4">转涉案财物</option>
                    <option value="5">移交</option>
                    <option value="6">邮寄</option>
                </select></td>
            </tr>
            <tr>
                <td>领取人:</td>
                <td><input id="getPerson" name="getPerson" class="easyui-textbox" style="width: 160px" data-options="required:true"/></td>
            </tr>
            <tr style="display: none"  id="jsdwShow">
                <td>接收单位:</td>
                <td><input id="jsdwId" name="jsdwId" class="easyui-combobox" style="width: 160px" data-options="required:true"/></td>
            </tr>

        </table>
    </form>

</div>
<div id="dlgEdit" style="display: none">
    <form id="form">
        <table style="margin: 0 auto; padding: 10px;width: 90%">
            <tr>
                <td>物品名称:</td>
                <td><input id="nameEdit" name="name" class="easyui-textbox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>数量:</td>
                <td><input id="detailCount" name="detailCount" class="easyui-textbox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>特征:</td>
                <td><input id="description" name="description" class="easyui-textbox" data-options="required:true"/></td>
            </tr>

        </table>
    </form>
</div>

<!-- 下载隐藏信息 -->
<div style="display: none">
    <form id="lawdocInfo_belonginfo" class="form-style" method="get" action="/sscw/zhfz/lawdocProcess/download.do"
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
<script>

</script>
<style scoped>
    .m-btn-1 {
        height: 28px;
        border: 1px solid #000;
        padding: 0 20px;
        background: #000;
        color: #fff;
        box-shadow: 0px 4px 10px 0px rgba(8, 29, 64, 0.6);
    }
    .m-btn-1.blue {
        background: #0b74b0;
        background: -moz-linear-gradient( 90deg, rgba(32,83,151,1), rgba(5,128,190,1));
        background: -webkit-linear-gradient( 90deg, rgb(150, 187, 236), rgba(5,128,190,1));;
        background: -ms-linear-gradient( 90deg, rgba(32,83,151,1), rgba(5,128,190,1));
        border: 1px solid #1268bd;
    }

    #form tr {
        line-height: 35px;
    }

    #form tr input, select {
        width: 220px;
    }
</style>
</body>
</html>