<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html  style="height: 100%">
<head>
	<%@ include file="../../common/common-head.jsp"%>
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/css.css">
	<script type="text/javascript" src="suspectentrance.js"></script>
	<script type="text/javascript" src="common.js"></script>
	<script type="text/javascript" src="relate.js"></script>
	<script type="text/javascript" src="editphoto.js"></script>
	<script type="text/javascript" src="taizhangDialog.js"></script>
	<script type="text/javascript" src="putongtaizhangDialog.js"></script>
	<script type="text/javascript" src="entranceEdit.js"></script>
	<script type="text/javascript" src="outSerial.js"></script>
	<script type="text/javascript" src="inSerial.js"></script>
	<script type="text/javascript" src="fiveTaiZhangDialog.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
</head>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ" event="OnAfterGWQ_ReadID(ErrorCode,JsonData)" type="text/javascript">
	saveReadCardData(ErrorCode,JsonData);
</script>
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
	// afterSign(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64);
    AfterSignStart(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64,SignType);
</script>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartFaceCompare(ErrorCode,JsonData)" type="text/javascript">
	afterPhotoDistinguish(ErrorCode,JsonData);
</script>
<!--预览回调-->
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartPreview(PreviewBase64)" type="text/javascript">
	var imgId = "";
	//入区
	if(phototype == 'entrance'){
		imgId = "inSerialPreviewPhoto";
	}
	//出区
	if(phototype == 'exitBegin'){
		imgId = "outSerialPreviewPhoto";
	}
	//修改
	if(phototype == 'photoEdit'){
		imgId = "editPreviewPhoto";
	}
	afterPreview(PreviewBase64,imgId);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">
	<%@ include file="../../common/common-loading.jsp"%>
	<%@ include file="../../common/case/case.jsp"%>
	<%@ include file="../../common/case/jzAjxx.jsp"%>
	<div class="page locker-home" style="height: 100%">
		<div class="main" style="height: 25%;position:static">
			<div class="box">
				<a id="rqButton" class="item i1 m-box" name="rqButton" onclick="entrance()">
					<span>入区登记</span>
					<div class="edges">
						<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
					</div>
				</a>
				<a id="cqButton" class="item i2 m-box" name="cqButton" onclick="exitBegin()">
					<span>出区登记</span>
					<div class="edges">
						<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
					</div>
				</a>
				<a id="rqlsButton" name="noButton" class="item i3 m-box" href="index.jsp">
					<span>可视化</span>
					<div class="edges">
						<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
					</div>
				</a>
			</div>
		</div>
		<div style="height: 75%">
			<table id="datagrid" ></table>
		</div>
		<!-- toolbar -->
		<div id="toolbar" style="height: auto;padding: 5px;">
			<div>
				<label>嫌疑人姓名: </label><input id="e_person" class="easyui-validatebox" style="width: 70px">
				<label>证件号码:</label><input id="e_certificateNo" class="easyui-validatebox" style="width: 180px">
				<label>类型: </label><select class="easyui-combobox" editable="false" id="e_status" name="e_status" style="width: 100px;">
						<option selected="selected" value="">全部</option>
						<option value="11">未出区</option>
					</select>
					<label>案件类型: </label><select class="easyui-combobox" editable="false" id="e_ajlx" name="e_ajlx" style="width: 100px;">
						<option selected="selected" value="">全部</option>
						<option value="1">行政</option>
						<option value="2">刑事</option>
					</select>
				<label>入区时间从:</label> <input id="e_start_date" class="easyui-datetimebox"style="width: 155px">
				<label>到:</label> <input id="e_end_date" class="easyui-datetimebox" style="width: 155px">
				<label>办案场所选择:</label>
				<input id="interrogatearea" name="interrogatearea" class="easyui-combobox" style="width: 200px;" data-options="" editable="false">
				<label>是否送押解队: </label><select class="easyui-combobox" editable="false" id="e_sfsyjd" name="e_sfsyjd" style="width: 100px;">
						<option selected="selected" value="">全部</option>
						<option value="0">未送</option>
						<option value="1">已送</option>
					</select>

				<label>是否关联案件: </label><select class="easyui-combobox" editable="false" id="e_glaj" name="e_glaj" style="width: 100px;">
				<option selected="selected" value="">全部</option>
				<option value="0">已关联</option>
				<option value="1">未关联</option>
			    </select>
				
				<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a>
				<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清空</a>
				<a href="#" id="batchCF" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="confirmCheckBoxs()">批量裁决</a>
				<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="showHistoryTimeoutRecodeData()">历史超期审讯数据</a>
			</div>
		</div>
	</div>


	<!-- 第一个导出数据表单 -->
	<form id="exportFormOutTime" name="exportFormOutTime" method="post" action="../export/expOutTimeExecl.do">
		<input type="text" id="out_hour" name="hour">
		<input type="text" id="out_name" name="name">
		<input type="text" id="out_start_time" name="startTime">
		<input type="text" id="out_end_time" name="endTime">
	</form>
	<div id="jzxiangqing" class="easyui-dialog"
		 style="width: 450px; height: 320px; padding: 10px 20px; overflow: hidden;"
		 closable="true" closed="true" buttons="#jzxiangqing-buttons"></div>


	<div id="sdgl_dialog" class="easyui-dialog"
		 style="width: 520px; height: 430px; padding: 10px 20px"
		 closable="true" closed="true" buttons="#gl-buttons">
		<!-- <div class="ftitle">嫌疑人入区信息</div> -->

		<input type="hidden" id="sfz" name="sfz" />
		<input type="hidden" id="sId" name="sId" />

		<form id="GlForm" method="post">
			<table id="tb" pagination="true" fitColumns="true"
				   singleSelect="true" width="100%">
				<%--<thead align="left">--%>

				<tr>
					<td> <input type="button" name="noButton" value="执法办案平台" onclick="jingzongByzjhmGl()" id="jzcq" style="width:88px;height:32px;border:none;background:url(image/botton_b.png);"></td>
					<td><span id="selectJzLableGl"><font style="color: red">未关联执法办案平台数据！</font></span>

				</tr>
				<tr>
					<td><label for="txtname">手动关联案件编号：</label></td>
					<td><input id="glajbh"  name="ajbh"
							   type="text" style="font: 14px arial; padding: 3px;width: 220px" class="easyui-validatebox"

							   data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
							   required="true" /></td>
				</tr>
				<tr>
					<td><label for="txtname">手动关联人员编号：</label></td>
					<td><input id="glrybh"  name="rybh"
							   type="text" style="font: 14px arial; padding: 3px;width: 220px" class="easyui-validatebox"
							   data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
							   required="true" /></td>
				</tr>

					<tr>
						<%--<td><label for="txtname">未关联原因：</label></td>
						<td><input id="wgyy"  name="wgyy"
								   type="text" style="font: 14px arial; padding: 3px;width: 220px" class="easyui-validatebox"
								   data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
								   required="true" /></td>--%>
							<td>
								<label>未关联原因：</label>
							</td>
							<td>
								<select id="wgyy" class="easyui-combobox"
										name="wgyy" style="width: 230px;" required="true">
									<option selected="selected" value=""></option>
									<option value="外省市单位案件">外省市单位案件</option>
									<option value="涉密案件">涉密案件</option>
									<option value="2012年前逃犯">2012年前逃犯</option>
									<option value="教育释放">教育释放</option>
								</select>
							</td>
					</tr>

				<%-- </thead>--%>
			</table>
		</form>

	</div>
	<div id="gl-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" name="noButton"
		   iconCls="icon-ok" onclick="glSave()" style="width: 90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" name="noButton"
		   iconCls="icon-cancel"
		   onclick="javascript:$('#sdgl_dialog').dialog('close');$('#GlForm').form('clear');"
		   style="width: 90px">取消</a>
	</div>


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

		<div data-options="region:'center',split:true"
			 style="width:100%; height: 60%;">
			<table id="jzInfo">

			</table>
		</div>
		<div data-options="region:'east',split:true" style="width:100%;height: 40%;" >

			<table id="jzPerson">

			</table>

		</div>
		<div id="jz-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton"
			   onclick="saveZJInfo()">保存</a> <a href="#"
												class="easyui-linkbutton" iconCls="icon-cancel"  name="noButton"
												onclick="javascript:$('#jz_orderrequest').dialog('close')">取消</a>
		</div>
	</div>

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
						 <option value="治拘">治拘</option>
                                    <option value="刑拘">刑拘</option>
                                    <option value="逮捕">逮捕</option>
                                    <option value="监居">监居</option>
                                    <option value="排除">排除</option>
                                    <option value="移交">移交</option>
                                    <option value="在逃羁押">在逃羁押</option>
                                    <option value="直保">直保</option>
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


     <!-- 批量裁决 -->
	<div id="batchConfirm" class="easyui-dialog"
		 style="width: 250px; height: 250px; padding: 10px 20px" closed="true"
		 buttons="#batchConfirmDg_Button">
		<form id="choose_form" class="form-style" method="post">
		<input type="hidden" id="ids" name="ids">
			<div class="fitem"> 
				<select id="batchConfirm_pl" name="batchConfirm_pl"  class="easyui-combobox"
						style="margin: -2px; width: 150px; height: 28px" required="true">
					<option value="">选择裁决结果</option>
						 <option value="治拘">治拘</option>
                                    <option value="刑拘">刑拘</option>
                                    <option value="逮捕">逮捕</option>
                                    <option value="监居">监居</option>
                                    <option value="排除">排除</option>
                                    <option value="移交">移交</option>
                                    <option value="在逃羁押">在逃羁押</option>
                                    <option value="直保">直保</option>
                                    <option value="警告">警告</option>
                                    <option value="罚款">罚款</option>
                                    <option value="教育">教育</option>
                                    <option value="其他">其他</option>
				</select>
			</div>
		</form>
		<div id="batchConfirmDg_Button">
			<a name="noButton" href="#" class="easyui-linkbutton"
			   iconCls="icon-ok" onclick="batchConfirm()">确定</a> <a
				name="noButton" href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#batchConfirm').dialog('close');">关闭</a>
		</div>
	</div>



	<!-- 临时出区域返回确认 -->
	<div id="tempBack" class="easyui-dialog" style="width:500px;height:450px;padding:20px 40px" closable="true" closed="true" buttons="#tempback-buttons">
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

    <%--警综对话框--%>
	<%--<div id="jzDialog" class="easyui-dialog"
		 style="width: 450px; height: 250px; padding: 10px 20px;" closed="true"
		 buttons="#showConfirmDg_Button1">
		<form id="choose_form1" class="form-style" method="post">
			<div class="fitem">

				<label >关联警综：</label>
				<input id="jzRybh" class="easyui-validatebox" readonly="readonly"  style="width:160px">
					<a style="margin-left: 5px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"onclick="linkJzAjxx()">警综查询</a>

			</div>
		</form>
		<div id="showConfirmDg_Button1">
			<a name="noButton1" href="#" class="easyui-linkbutton"
			   iconCls="icon-ok" onclick="insertRybh()">确定22222</a> <a
				name="noButton1" href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javaScript:$('#jzDialog').dialog('close');">关闭222</a>
		</div>
	</div>--%>


    <div id="jzDialog" class="m-popup m-info-popup">
        <div class="popup-bg"></div>
        <div class="popup-content m-box" style="height: 350px;width: 450px">
            <div class="popup-inner">
                <div class="title">
                    <div><i style="background-image: url(static/popup-2.png);"></i><span>警综信息</span></div>
                    <a name="noButton" class="close" onclick="javaScript:$('#jzDialog').hide();"></a>
                </div>
                <div id="entranceEditDiv1"  class="bd" style="margin-top: 50px;">
                    <form id="entranceEdit" enctype="multipart/form-data" action="ashx/login.ashx" method="post" style="height:250px">
                        <table class="main_form1"  style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 30px">

                            <tr>
                                <td><label>警综：</label></td>
                                <td><input type="text" id="jzRybh" name="ename"  readonly="readonly"  class="easyui-validatebox" style="font: 14px arial; padding: 3px;width: 180px"
                                           data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'"  required="true" />
                                </td>

                                <td>
                                   <a style="margin-left: 5px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"onclick="linkJzAjxx()">警综查询</a>

                                </td>
                            </tr>

                        </table>
                    </form>
                    <div class="next"  style="margin-top: -100px">
                        <button class="m-btn-1 blue" onclick="insertRybh()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存</button>
                        <button class="m-btn-1 blue" onclick="javaScript:$('#jzDialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">取消</button>
                    </div>
                </div>
               <%-- <div class="edges">
                    </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                </div>--%>
            </div>
        </div>
    </div>
	<%@include file="common.jsp"%>
	<%@include file="relateDialog.jsp" %>
	<%@include file="editphotoDialog.jsp" %>
	<%@include file="taizhangDialog.jsp" %>
	<%@include file="putongtaizhangDialog.jsp" %>
	<%@include file="entranceEditDialog.jsp" %>
	<%@include file="outSerialDialog.jsp" %>
	<%@include file="inSerialDialog.jsp" %>
	<%@include file="fiveTaiZhangDialog.jsp" %>
	<%@include file="timeoutRecodeDialog.jsp" %>
</body>
</html>