<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
  <%@ include file="../../common/common-head.jsp"%>
  <link rel="stylesheet" type="text/css" href="css/common.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <script type="text/javascript" src="policeEntrance.js"></script>
  <script type="text/javascript" src="inPolice.js"></script>
  <script type="text/javascript" src="outPolice.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/case/case.jsp"%>
<%@ include file="../../common/case/jzAjxx.jsp"%>
  <div class="page locker-home">
    <div class="main">
      <div class="box">
        <a id="rqButton" class="item i1 m-box" name="noButton" onclick="entrance()">
          <span>民警入区</span>
          <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
          </div>
        </a>
        <a id="cqButton" class="item i2 m-box" name="noButton" onclick="out()">
          <span>民警出区</span>
          <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
          </div>
        </a>
        <a id="rqlsButton" name="noButton" class="item i3 m-box" href="suspectEntrance.jsp">
          <span>登记历史</span>
          <div class="edges">
          <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
        </a>
      </div>
      <div class="content m-box" style="margin-top: -6px;">
        <div class="control-bar">
        	<div class="m-input">
            <span>案件名称：</span>
            <label>
              <input type="text" id="ajmc2" name="ajmc" placeholder="请输入">
            </label>
          </div>
          <div class="m-input">
            <span>民警姓名：</span>
            <label>
              <input type="text" id="name2" name="name" placeholder="请输入">
            </label>
          </div>
          <div class="m-input">
            <span>民警警号：</span>
            <label>
              <input type="text" id="certificateNo2" name="certificateNo" placeholder="请输入">
            </label>
          </div>
          <div class="m-input">
            <span>办案场所：</span>
            <label>
              <input type="text" id="areaId" name="areaId" class="easyui-combobox">
            </label>
          </div>
          <button class="m-btn-1 blue" onclick="doSearch2()">查询</button>
          <button class="m-btn-1 blue" onclick="doClear2()">清除</button>
        </div>
        <div class="content-in">
          <div id="policeItems" class="list" style="overflow:auto;"></div>          
          <div class="paging">
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
	<div id="exit_dialog" class="easyui-dialog"
		style="width: 550px; height:400px; padding: 10px 20px" closed="true">
		<div data-options="region:'center',split:true" style="width: 100%; height: 100%;">
			<table id="exitDatagrid"></table>
		</div>
   </div>
    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" src="../../../static/page-1.png">
  </div>  
  <div id="policeDetail" class="easyui-dialog"
         style="width: 850px; height: 520px; padding: 10px 20px; overflow: hidden;background: url('/static/home-1.png')" closable="true" closed="true"></div>
  <%--添加加民警 start--%>
    <div id="addPolice_Popup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box"  style="height: 300px;">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>添加民警</span></div>
                <a name="noButton" class="close" onclick="closeAddPolice()"></a>
            </div>
            <div id="stepDiv3" class="bd">
              <form id="addOutPoliceForm" method="post">
                <input type="hidden" name="caseId" id="addPolice_caseId">
                <input type="hidden" name="areaId" id="addPolice_areaId">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 147px;margin-top: 46px;">
                  <tr>
                    <td><label for="txtname">民警警号：</label></td>
                    <td><input type="text" class="easyui-validatebox" id="addPolice_policeNo" name="policeNo" onblur="finduser2(3)" style="font: 14px arial; padding: 3px"  required="true" />
                      <input type="hidden" id="addPolice_policeId" name="policeId"><font color="red" style="margin-left: 2px;">*</font></td>
                  </tr>
                  <tr>
                    <td><label for="txtname">民警类型：</label></td>
                    <td><input type="radio" value='0' name="policeType" id="addPolice_main"
                               checked='checked' />主办民警 &nbsp;&nbsp;&nbsp;&nbsp;
                      <input type="radio" value='1' name="policeType" id="addPolice_assist" />协办民警</td>
                  </tr>
                  <tr>
                    <td><label for="txtname">民警卡片：</label></td>
                    <td>
                      <input id="addPolice_cuffNo" name="cuffNo" type="text" class="easyui-validatebox" style="font: 14px arial; padding: 3px" required="true" />
                      <input type="hidden" id="addPolice_cuffId" name="cuffId" /><font color="red" style="margin-left: 2px;">*</font></td>
                    </td>
                  </tr>
				</table>
			 </form>

				<div class="next">
                    <button class="m-btn-1 blue" onclick="saveAddPolice()">保存</button>
                    <button class="m-btn-1 blue" onclick="closeAddPolice()">取消</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>

  <%--添加民警 end--%>
  <%@include file="inPoliceDialog.jsp" %>
  <%@include file="outPoliceDialog.jsp" %>
  <script src="../../../js/plugs/sky/particles.min.js"></script>
  <script src="../../../js/plugs/sky/app.js"></script>
</body>
</html>