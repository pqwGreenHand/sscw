<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<html>
<head>
  <%@ include file="../../common/common-head.jsp"%>
  <link rel="stylesheet" type="text/css" href="css/common.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css/css.css">
  <script type="text/javascript" src="index.js"></script>
  <script type="text/javascript" src="outSerial.js"></script>
  <script type="text/javascript" src="inSerial.js"></script>
  <script type="text/javascript" src="editphoto.js"></script>
  <script type="text/javascript" src="relate.js"></script>
  <script type="text/javascript" src="entranceEdit.js"></script>
  <script type="text/javascript" src="taizhangDialog.js"></script>
  <script type="text/javascript" src="common.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
</head>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ" event="OnAfterGWQ_GetFrameWithIndex(ErrorCode,FrameBase64)" type="text/javascript">
  //修改入区图片
  if(phototype == 'photoEdit'){
    changeP(ErrorCode,FrameBase64);
  }
  //入区拍照
  if(phototype == 'entrance'){
    getInSerialPhoto(ErrorCode,FrameBase64);
  }
  //出区拍照
  if(phototype == 'exitBegin'){
    getOutSerialPhoto(ErrorCode,FrameBase64);
  }
</script>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartFace(ErrorCode,JsonData)" type="text/javascript">
  getInSerialPersonPhotoDistinguish(ErrorCode,JsonData);
  if(phototype == 'photoEdit'){
  }
  //入区人证识别拍照
  if(phototype == 'entrance'){
    getInSerialPersonPhotoDistinguish(ErrorCode,JsonData);
  }
  //出区拍照
  if(phototype == 'exitBegin'){
  }
</script>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartSignEx(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)" type="text/javascript">
  afterSign(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64);
</script>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartFaceCompare(ErrorCode,JsonData)" type="text/javascript">
  afterPhotoDistinguish(ErrorCode,JsonData);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/case/case.jsp"%>
  <div class="page locker-home">
    <div class="main">
      <div class="content m-box" style="top: 20px">
        <div class="control-bar">
            <div class="m-select" style="width: 240px">
                <span style="width: 80px">状态:&nbsp;&nbsp;</span>
                <input id="serial_status" class="easyui-combobox" panelHeight='150'>
            </div>
          <div class="m-input" style="width: 460px">
            <span>入区时间从：</span>
            <input type="text" id="start_date" name="start" class="easyui-datetimebox" style="width: 150px"/>
            <span style="color: white;width: 35px">&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;</span>
            <input type="text" id="end_date" name="end" class="easyui-datetimebox" style="width: 150px"/>
          </div>
          <div class="m-select" style="width: 340px">
            <span style="width: 80px">办案场所:&nbsp;&nbsp;</span>
            <input id="area_cmb_id" class="easyui-combobox" panelHeight='180' style="width: 240px;">
          </div>
          <div style="width: 20px"></div>
          <button class="m-btn-1 blue" onclick="doSearch()">查询</button>
          <button class="m-btn-1 blue" onclick="doClear()">清除</button>
           <a href="suspectentrance.jsp"  class="m-btn-1 blue" style="font-size: 14px;line-height: 27px"  name="noButton">返回</a>
        </div>
        <div class="content-in">
          <div id="serialItems" class="list" style="height: 530px"></div>
          <div class="paging" style="padding-top: 10px;display: none;">
            <a name="noButton" class="btn i1" title="首页" onclick="toPage('first')"></a>
            <a name="noButton" class="btn i2" title="上一页" onclick="toPage('last')"></a>
            <a name="noButton" class="num"><span>第</span></a>
            <a name="noButton" class="num active"><span id="pageLable">1/1</span></a>
            <a name="noButton" class="num"><span>页</span></a>
            <a name="noButton" class="btn i3" title="下一页" onclick="toPage('next')"></a>
            <a name="noButton" class="btn i4" title="尾页" onclick="toPage('end')"></a>
            <span id="totalSpan" style="right: 55px; color: rgb(0, 168, 255); font-size: 16px; position: fixed; bottom: 10px;"></span>
          </div>
        </div>
        <div class="edges">
          <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
      </div>
    </div>

    <%@include file="common.jsp"%>

    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" src="../../../static/page-1.png">
  </div>
  <%@include file="outSerialDialog.jsp" %>
  <%@include file="editphotoDialog.jsp" %>
  <%@include file="relateDialog.jsp" %>
  <%@include file="entranceEditDialog.jsp" %>
  <%@include file="taizhangDialog.jsp" %>
<!-- 裁决结果选择框 -->
<div id="showConfirmDg" class="easyui-dialog"
     style="width: 250px; height: 250px; padding: 10px 20px" closed="true"
     buttons="#showConfirmDg_Button">
    <form id="choose_form" class="form-style" method="post">
        <div class="fitem">
            <input type="hidden" id="confirm_serial_id">
            <select id="confirm_result_dg" name="confirmResult"  class="easyui-combobox"
                    style="margin: -2px; width: 150px; height: 28px" required="true">
                <option value="">选择裁决结果</option>
                <option value="逮捕">逮捕</option>
                <option value="监居">监居</option>
                <option value="排除">排除</option>
                <option value="刑拘">刑拘</option>
                <option value="移交">移交</option>
                <option value="在逃羁押">在逃羁押</option>
                <option value="直保">直保</option>
                <option value="治拘">治拘</option>
                <option value="警告">警告</option>
                <option value="罚款">罚款</option>
                <option value="教育">教育</option>
                <option value="其他">其他</option>
            </select>
        </div>
    </form>
    <div id="showConfirmDg_Button">
        <a name="noButton" href="#" class="easyui-linkbutton"
           iconCls="icon-ok" onclick="doConformResult()">确定</a> <a
            name="noButton" href="#" class="easyui-linkbutton"
            iconCls="icon-cancel"
            onclick="javascript:$('#showConfirmDg').dialog('close');">关闭</a>
    </div>
</div>


<!-- 临时出区域返回确认 -->
<div id="tempBack" class="easyui-dialog" style="width:500px;height:450px;padding:20px 40px" closable="true" closed="true" buttons="#tempback-buttons"tbname>
    <div class="ftitle" style="color: beige">嫌疑人入区信息</div>
    <form id="tempBackForm" method="post">
        <table id="tb" pagination="true" fitColumns="true" singleSelect="true" width="100%">
            <thead align="left">
            <tr><td colspan="2"><input type="hidden" id="tbid" name="tbid"/></td></tr>
            <tr>
                <td><label for="txtname">入区编号：<font color="red">*</font></label></td>
                <td><input id="tbSerialNo" name="tbSerialNo" type="text"  style= "font: 14px arial; padding:3px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/></td>
            </tr>
            <tr >
                <td style="padding-top: 10px"><label for="txtname">姓名：<font color="red">*</font></label></td>
                <td style="padding-top: 10px"><input type="text" id="tbname" name="tbname"style= "font: 14px arial; padding:3px"  data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/></td>
            </tr>
            <tr>
                <td style="padding-top: 10px"><label for="txtname">性别：<font color="red">*</font></label></td>
                <td style="padding-top: 10px">
                    <input type="radio" value='1' name="tbsex" id="tbmale"/>男 &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value='2' name="tbsex" id="tbfemale"/>女
                </td>
            </tr>
            <tr>
                <td style="padding-top: 10px"><label for="txtname">证件类型：<font color="red">*</font></label></td>
                <td style="padding-top: 10px"><select id="tbcertificateTypeId" name="tbcertificateTypeId" style = "margin:-2px;width:170px; height:28px" class="easyui-combobox" required="true"></select></td>
            </tr>
            <tr>
                <td style="padding-top: 10px"><label for="txtname">证件号码：<font color="red">*</font></label></td>
                <td style="padding-top: 10px"><input id="tbcertificateNo" name="tbcertificateNo" type="text"  style= "font: 14px arial; padding:3px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/></td>
            </tr>

            <tr>
                <td style="padding-top: 10px"><label for="txtname">手环编号：<font color="red">*</font></label></td>
                <td style="padding-top: 10px"><input id="tbcuffNo" name="tbcuffNo" type="text"  style= "font: 14px arial; padding:3px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/></td>
            </tr>
            <tr>
                <td colspan="2"><label for="txtname"><font color="red">*请工作人员为该嫌疑人佩戴对应编号手环*</font></label></td>
            </tr>
            </thead>
        </table>
    </form>
</div>
<div id="tempback-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" name="noButton" iconCls="icon-ok" onclick="sureBack()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" name="noButton" iconCls="icon-cancel" onclick="javascript:$('#tempBack').dialog('close')" style="width:90px">取消</a>
</div>

<form id="exportForm" method="post" action="downloadWord.do" style="display: none">
  <input id="exportSerialId" name="exportSerialId"/>
</form>
  <script src="../../../js/plugs/sky/particles.min.js"></script>
  <script src="../../../js/plugs/sky/app.js"></script>
</body>
</html>