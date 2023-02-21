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
	<link type="text/css" rel="stylesheet" href="../security/css/css.css">
	<script type="text/javascript" src="security.js"></script>
	<script type="text/javascript" src="signpicture.js"></script>
	<script type="text/javascript" src="../security/js/jquery.webcam.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
</head>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartSignEx(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)" type="text/javascript">
	AfterSignStart(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64,3);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<div data-options="region:'west',split:false"   style="width: 100%; height: 100%;">
	<div class="main" style="width: 100%; height: 120px;">
		<div class="box" id="tj_btn" style="margin-bottom: 5px; width: 100%; height: 90px; margin-top: 15px" align="center">
			<a  onclick="securityAdd()" id="securityAdd" name="securityAdd"   iconCls="icon-add" class="myButton i1 m-box">
				<span>人身检查</span>
				<div class="edges">
					</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
				</div>
			</a>
		</div>
	</div>
	<table id="security_datagrid"></table>
</div>

<div id="toolbar" style="padding: 5px; height: auto">
	<div>
		<label>嫌疑人姓名:</label>
		<input id="ryxm" name="ryxm" class="easyui-validatebox" style="font: 14px arial; width: 100px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
		<label>证件号码:</label>
		<input id="zjhm" name="zjhm" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
		<label>案件性质:</label>
		<input id="ajmc" name="ajmc" class="easyui-validatebox" style="font: 14px arial; width: 180px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
		<label>办案场所:</label>
		<input id="interrogatearea" name="interrogatearea" class="easyui-combobox" data-options="" style="width: 180px;" editable="false">
		<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchAj()">查询</a>
		<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearAj()">清空</a>
	</div>
</div>
<div
	 style="width: 27%; height: 100%;display: none;">
	<!--身体照片区域 -->
	<div style="width: 100%; height: 100%;display: none;">
		         <iframe name="myiframephoto" id="yyy" src="<%=request.getContextPath()%>/zhfz/bacs/security/lunbophoto.jsp" frameborder="0" scrolling="no" style="height:100%;width:100%" ></iframe>
	</div>
</div>

<div id="security_data_dialog" class="m-popup m-info-popup" >
	<div class="popup-bg"></div>
	<div class="popup-content m-box" style="width: 700px;height: 500px;">
		<div class="popup-inner" >
			<div class="title" >
				<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>安检步骤</span></div>
				<a name="noButton" class="close" onclick="$('#security_data_dialog').hide()"></a>
			</div>
			<div style="display: flex" >
			<div id="security_info_dialog"   style="color: white;border-collapse: separate;font-family: '微软雅黑';width: 380px;margin-left: 153px;margin-top: 22px;">
					<form id="securityinfo" enctype="multipart/form-data"  method="post">
						<input type="hidden" name="lawCaseId" id="s_lawCaseId">
						<table class="main_form1" style="margin-left: 0px;margin-top: 10px" >
							<tr><td align="left" colspan="3"></td></tr>
							<tr id="scanCuff">
								<td style="width:106px;"><label for="txtname" >扫描嫌疑人手环：</label></td>
								<td colspan="6" ><input id="cuffNumber" name="cuffNumber"  onkeydown="enterKeyEventSecurity()"
														class="easyui-validatebox" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" />
							</tr>
							<tr>
								<td><label for="txtname">嫌疑人姓名：</label></td>
								<td>
									<select id="serialNo" class="easyui-combogrid" name="serialNo" style="width:170px;" required="true"  readonly="false" >
									</select>
									<font color="red">*</font>

								</td>
							</tr>
							<tr>
								<td><label for="txtname">安检民警：</label></td><td>
								<input  id="checkUserNo" onblur="finduser()"  name="checkUserNo" onfocus="if(this.value == '请输入警号') this.value = ''" class="easyui-validatebox"  required="true"/>
								<input id="checkUserId" name="checkUserId" type="hidden" >
								<font color="red">*</font>
							</td>
							</tr>
							<tr>
								<td style="width: 110px"><label for="txtname">自述症状(既往病史):</label></td>
								<td>
									<textarea id="medicalHistory" name="medicalHistory" rows="2" cols="27" class="easyui-validatebox"  data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
								</td>
							</tr>
							<tr>
								<td><label for="txtname">特殊体貌特征:</label></td>
								<td>
									<textarea id="physicalDescription" name="physicalDescription" rows="2" cols="27"  class="easyui-validatebox"  data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
								</td>
							</tr>
							<tr>
								<td  style="width:100px"><label for="txtname">携带危险品情况:</label></td>
								<td>
									<textarea id="dangerous" name="dangerous" rows="2" cols="27"  class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
								</td>
							</tr>
							<tr>
								<td><label for="txtname">检查情况:</label></td>
								<td>
									<textarea id="injuryDescription" name="injuryDescription" rows="2" cols="27" class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
								</td>
							</tr>
							<tr>
								<td><label for="txtname">其他说明情况:</label></td>
								<td >
									<textarea id="otherDescription" name="otherDescription" rows="2" cols="27" class="easyui-validatebox"  data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
								</td>
							</tr>
							<tr>
								<td></td>
								<td >
									<input type="checkbox" name="hasInjury" id="hasInjury" value="1"/>体表有伤痕&nbsp;&nbsp;
									<input type="checkbox" name="hasWine" id="hasWine" value="1"/>有饮酒&nbsp;&nbsp;
									<input type="checkbox" name="hasPhoto" id="hasPhoto" value="1"/>有拍照
								</td>
							</tr>
						</table>
					</form>
			</div>

			<!-- 图片标记开始 -->
			<%--<div style="border-collapse: separate;font-family: '微软雅黑';width: 180px;margin-top: 40px;display: none;">
							<div  title="双击此处进行体表标记"  ondblclick="loadPersonImg()" style="margin-left:0px;margin-top:0px;width:180px;height:355px;background:url(../security/image/click.png) no-repeat 0px 0px;;position:relative">
								<div name="contents"  contentEditable="false"  style="display:inline;margin:80px 0px 0px 40px;border:1px solid blue;width:90px;height:50px;position:absolute"><font id="tbbjm" color="red">双击图像以进行体表标记↗</fontcolor></div>
							</div>
			</div>--%>
<!-- 拍照开始-->
				<%--<div id="security_photo_dialog"  style="color: white;border-collapse: separate;font-family: '微软雅黑';width: 240px;margin-top: 0px">
					<form id="photoInfo" enctype="multipart/form-data"
						  action="ashx/login.ashx" method="post">

						<div style="margin-top: 40px;width: 240px">
						    <!-- 拍照框//用于预览画面 -->
						    <div id="webcam_view" style="width: 240px; height: 180px;">
								<div id="webcam" ></div>
						    </div>
							<div style="display: flex">
							<!-- 出区照片框 //用于定格画面-->
							<div id="canvas_view">
								<canvas id="canvas" width="240" height="175"></canvas>
							</div>
 <div >
	 <img src="image/xj.png" alt="拍照"  title="拍照" onclick="photoScan()" style="height: 30px;width: 30px;"/>
	 <img src="image/sc.png" alt="上传"  title="上传" onclick="uploadImg()" style="height: 30px;width: 30px;"/>
 </div>
							</div>
						</div>
					</form>
					<!--  展示已拍好的照片-->
					<div id="showPic_dialog">
					</div>
				</div>--%>
				<!-- 拍照结束-->
			</div>
			<!--  图片标记结束 -->
			<div class="next" id="firstDiv" style="text-align: center;margin-top:23px">
				<button class="m-btn-1 blue" onclick="saveInfo()">完成</button>
			</div>
			<div class="next"  id="repeatDiv" style="text-align: center;margin-top:50px">
				<button class="m-btn-1 blue" onclick="saveInfoRepeat()">复检</button>
			</div>
		</div>
		<input type="hidden" id="uuid" />
		<input type="hidden" id="bodyTag"  />
		<input type="hidden" id="bodyPhoto"  />
		<input type="hidden" id="checkedStartTime" name="checkedStartTime" />
		<input type="hidden" id="reason" name="reason" />
        <input type="hidden" id="count" />
		<input type="hidden" id="areaId"  />
		<input type="hidden" id="securityId"  />
		<input type="hidden" id="serialId" value="0" />
		<input type="hidden" id="serialNoo" value="0" />
	</div>
	<div id="userNoInfo" class="easyui-dialog" closed="true">
		<input type="hidden" id="userNo" name="userNo" />
	</div>
</div>
<!-- 隐藏部分-->
<div id="jibenxinxi" style="color: white;border-collapse: separate;font-family: '微软雅黑';margin-top: 10px">
	<div class="xx_change_div_1">
		<form id="caseinfo" enctype="multipart/form-data"  method="post" >
			<table class="main_form1" >
				<tr><td> <input type="hidden" id="id" name="id" /></td></tr>
				<tr>
					<td style="width:104px;"><label for="txtname">案件类别：</label><font color="red">*</font></td>
					<td>
						<input id="involvedReason" name="involvedReason" class="easyui-combobox"  style = "margin:-2px;width:160px;" required ="true" />
					</td>
					<td style="width:104px;display:none"><label for="txtname"><font color="red">同案请选择：</font></label></td>
					<td style="display:none">
						<input type="hidden" id="caseId" name="caseId" /></td>
				</tr>
				<tr>
					<td><label for="txtname">涉案人员：</label></td>
					<td>
						<input id="involvedPeople" name="involvedPeople" type="text"  style= "font: 14px arial;width: 150px; padding:3px"  data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'" required="true"/>
						<font color="red">*</font>
					</td>
					<td style="width:104px;"><label for="txtname">案发时间：</label></td><font color="red">*</font><td ><input id="involvedDate" name="involvedDate"  class="easyui-datetimebox" style= "font: 14px arial;width: 160px; padding:3px"   required="true" formatter="yyyy-mm-dd hh:mm:ss"/></td>
				</tr>
				<tr>

					<td><label for="txtname">案发地址：</label></td>
					<td colspan="3" style="width:260px">
						<textarea id="involvedAddress" name="involvedAddress" style="width: 100%;padding:0px;" ></textarea>
					</td>
					<font color="red">*</font>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="serialNoInfo" class="easyui-dialog" closed="true">
	<input type=text id="cserialNo" name="cserialNo" />
</div>
<form id="cuffInfo" method="post">
	<input type="hidden" id="cuffNo" name="cuffNo" />
</form>
<div  class="easyui-dialog" closed="true">
<input type="radio" checked="checked" value='0' name="type" id="common"/>
</div>
<!-- 隐藏部分结束-->

<div id="securityDetail" class="easyui-dialog"
	 style="width: 850px; height: 520px; padding: 10px 20px; overflow: hidden;background: url('/static/home-1.png')" closable="true" closed="true"></div>
<div id="checkHistory" class="easyui-dialog"
	 style="width: 1000px; height: 500px; padding: 10px 20px" closed="true">
	<table id="security_history_datagrid"></table>
</div>
</body>
<%--<script type="text/javascript">
    var pos = 0;
    var ctx = null;
    var cam = null;
    var image = null;

    var filter_on = false;
    var filter_id = 0;

    function changeFilter() {
        if (filter_on) {
            filter_id = (filter_id + 1) & 7;
        }
    }

    function toggleFilter(obj) {
        if (filter_on = !filter_on) {
            obj.parentNode.style.borderColor = "#c00";
        } else {
            obj.parentNode.style.borderColor = "#333";
        }
    }

    //摄像头
    jQuery("#webcam").webcam(
        {

            width : 240,
            height : 180,
            mode : "callback",
            swffile : "jscam_canvas_only.swf",

            onSave : function(data) {

                var col = data.split(";");
                var img = image;

                if (false == filter_on) {

                    for (var i = 0; i < 240; i++) {
                        var tmp = parseInt(col[i]);
                        img.data[pos + 0] = (tmp >> 16) & 0xff;
                        img.data[pos + 1] = (tmp >> 8) & 0xff;
                        img.data[pos + 2] = tmp & 0xff;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }

                } else {

                    var id = filter_id;
                    var r, g, b;
                    var r1 = Math.floor(Math.random() * 255);
                    var r2 = Math.floor(Math.random() * 255);
                    var r3 = Math.floor(Math.random() * 255);

                    for (var i = 0; i < 240; i++) {
                        var tmp = parseInt(col[i]);

						/* Copied some xcolor methods here to be faster than calling all methods inside of xcolor and to not serve complete library with every req */

                        if (id == 0) {
                            r = (tmp >> 16) & 0xff;
                            g = 0xff;
                            b = 0xff;
                        } else if (id == 1) {
                            r = 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = 0xff;
                        } else if (id == 2) {
                            r = 0xff;
                            g = 0xff;
                            b = tmp & 0xff;
                        } else if (id == 3) {
                            r = 0xff ^ ((tmp >> 16) & 0xff);
                            g = 0xff ^ ((tmp >> 8) & 0xff);
                            b = 0xff ^ (tmp & 0xff);
                        } else if (id == 4) {

                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            var v = Math.min(Math.floor(.35 + 13 * (r
                                + g + b) / 60), 255);
                            r = v;
                            g = v;
                            b = v;
                        } else if (id == 5) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            if ((r += 32) < 0)
                                r = 0;
                            if ((g += 32) < 0)
                                g = 0;
                            if ((b += 32) < 0)
                                b = 0;
                        } else if (id == 6) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            if ((r -= 32) < 0)
                                r = 0;
                            if ((g -= 32) < 0)
                                g = 0;
                            if ((b -= 32) < 0)
                                b = 0;
                        } else if (id == 7) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            r = Math.floor(r / 255 * r1);
                            g = Math.floor(g / 255 * r2);
                            b = Math.floor(b / 255 * r3);
                        }

                        img.data[pos + 0] = r;
                        img.data[pos + 1] = g;
                        img.data[pos + 2] = b;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }
                }

                if (pos >= 0x2A300) {
                    ctx.putImageData(img, 0, 0);
                    pos = 0;
                }
            },

            onCapture : function() {
                webcam.save();

                jQuery("#flash").css("display", "block");
                jQuery("#flash").fadeOut(100, function() {
                    jQuery("#flash").css("opacity", 1);
                });
            },
			/*
			 debug: function (type, string) {
			 jQuery("#status").html(type + ": " + string);
			 },
			 */

        });

    function getPageSize() {

        var xScroll, yScroll;

        if (window.innerHeight && window.scrollMaxY) {
            xScroll = window.innerWidth + window.scrollMaxX;
            yScroll = window.innerHeight + window.scrollMaxY;
        } else if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac
            xScroll = document.body.scrollWidth;
            yScroll = document.body.scrollHeight;
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
            xScroll = document.body.offsetWidth;
            yScroll = document.body.offsetHeight;
        }

        var windowWidth, windowHeight;

        if (self.innerHeight) { // all except Explorer
            if (document.documentElement.clientWidth) {
                windowWidth = document.documentElement.clientWidth;
            } else {
                windowWidth = self.innerWidth;
            }
            windowHeight = self.innerHeight;
        } else if (document.documentElement
            && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
            windowWidth = document.documentElement.clientWidth;
            windowHeight = document.documentElement.clientHeight;
        } else if (document.body) { // other Explorers
            windowWidth = document.body.clientWidth;
            windowHeight = document.body.clientHeight;
        }

        // for small pages with total height less then height of the viewport
        if (yScroll < windowHeight) {
            pageHeight = windowHeight;
        } else {
            pageHeight = yScroll;
        }

        // for small pages with total width less then width of the viewport
        if (xScroll < windowWidth) {
            pageWidth = xScroll;
        } else {
            pageWidth = windowWidth;
        }

        return [ pageWidth, pageHeight ];
    }

    window.addEventListener("load", function() {

        jQuery("body").append("<div id=\"flash\"></div>");

        var canvas = document.getElementById("canvas");

        if (canvas.getContext) {
            ctx = document.getElementById("canvas").getContext("2d");
            ctx.clearRect(0, 0, 240, 180);

            var img = new Image();
            img.src = "image/suspect.jpg";
            img.onload = function() {
                ctx.drawImage(img, -1, -1);
            }
            image = ctx.getImageData(0, 0, 240, 180);
        }

        var pageSize = getPageSize();
        jQuery("#flash").css({
            height : pageSize[1] + "px"
        });

    }, false);

    window.addEventListener("resize", function() {

        var pageSize = getPageSize();
        jQuery("#flash").css({
            height : pageSize[1] + "px"
        });

    }, false);
</script>--%>
</html>