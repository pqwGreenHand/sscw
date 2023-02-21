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
<link rel="stylesheet" type="text/css" href="css.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.webcam.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/single.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/fileprogress.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/handlers.js"></script>
<link href="<%=request.getContextPath()%>/swfUpload/css/default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="urinalysis.js"></script>
	<style type="text/css">
		#urinalysis_form1 *{
			font-size: 13px;
			color: white;
		}
		#urinalysis_form1 font{
			color: red;
		}

	</style>
</head>
<object classid="clsid:96BB8ADA-D348-4414-8892-DC79C8818841" id="GWQ" width="0" height="0"></object>
<script language="javascript" for="GWQ" event="OnAfterGWQ_StartSignEx(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)" type="text/javascript">
	AfterSignStart(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64,4);
</script>
<body class="easyui-layout" style="width: 100%; height: 100%;">

<!-- 数据显示层 start -->
	<div data-options="region:'north',split:true" >
		<div class="main" style="width: 100%; height: 120px;">
			<div class="box" id="tj_btn" style="margin-bottom: 5px; width: 100%; height: 90px; margin-top: 15px" align="center">
				<a  onclick="urinalysisAdd()" id="urinalysisCheck" name="urinalysisCheck" class="myButton i1 m-box"  iconCls="icon-add">
					<span>尿液检查</span>
					<div class="edges">
						</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
					</div>
				</a>
			</div>
		</div>
	</div>
	<div data-options="region:'center',split:true"
		style="width: 99%; height: 95%;">
		<table id="urinalysis_grid"></table>
	</div>
	<div id="urinalysis_dialog" class="m-popup m-info-popup">
		<div class="popup-bg"></div>
		<div class="popup-content m-box" style="height: 650px;width: 680px">
			<div class="popup-inner">
				<div class="title">
					<div><i style="background-image: url(image/popup-2.png);"></i><span>尿检流程</span></div>
					<a name="noButton" class="close" onclick="urinalysisEnd()"></a>
				</div>
				<div class="hd">
					<div class="steps">
						<div id="step1" class="i1 item m-box now">
							<div class="inner">
								<i></i>
							</div>
							<span>基本信息</span>
							<div class="edges">
								</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
							</div>
						</div>
						<div id="step2" class="i2 item m-box">
							<div class="inner">
								<i></i>
							</div>
							<span>照片采集</span>
							<div class="edges">
								</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
							</div>
						</div>
					</div>
				</div>
				<div id="step_div1" align="center">
					<div class="xx_change_div_1" style="text-align: left">
						<form id="urinalysis_form1" class="form-style" method="post"
							  style="height: 390px;margin-left: 80px">
							<input type="hidden" id="id" name="id" />
							<div class="fitem" style="margin-top: 15px">
								<label style="width:140px;">手环编号：</label>
								<input id="cuffNumber" name="cuffNumber" type="text"  class="easyui-validatebox"
									   style= "font: 14px arial; width: 150px;padding:3px;padding-left: 10px"
									   data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"/>
								<font color="red">*请扫描手环或选择嫌疑人姓名*</font>

							</div>
							<div class="fitem">
								<label style="width:140px;">嫌疑人姓名：</label>
								<select id="serialId" class="easyui-combogrid" name="serialId" style="width:160px;padding-left: 10px" ></select>
								<font color="red" style="margin-left: 2px;">*</font>
							</div>
							<div class="fitem">
								<label style="width:140px;">民警警号：</label>
								<input id="checkUserNo" name="checkUserNo"   onblur="finduser1()"  class="easyui-validatebox"
									   style="font: 14px arial;width:150px;padding-left: 10px" required="true" />
								<font color="red">*请输入尿检民警警号*</font>
							</div>
							<div class="fitem">
								<label style="width:140px;">检测方法：</label>
								<select id="testMethod" name="testMethod" class="easyui-combobox" style="width:160px;padding-left: 10px" required="true" panelHeight='180'>
									<option value="甲基苯丙胺类毒品快速诊断检测板检测尿液">甲基苯丙胺类毒品快速诊断检测板检测尿液</option>
									<option value="唾液">唾液</option>
									<option value="血液">血液</option>
									<option value="其它">其它</option>
								</select>
								<font color="red" style="margin-left: 2px;">*</font>
							</div>
							<div class="fitem">
								<table>
									<tr>
										<td> <label style="width: 140px">毒品种类及检测结果：<font color="red">*</font></label></td>
										<td><input style="padding-left: 10px" type="checkbox" id="is_MDMA" />&nbsp;&nbsp;&nbsp;摇头丸</td>
										<td> 检验结果：<select id="MDMA_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
									<tr>
										<td></td>
										<td><input style="padding-left: 10px"  type="checkbox" id="is_KET"/>&nbsp;&nbsp;&nbsp;氯胺酮</td>
										<td> 检验结果：<select id="KET_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
									<tr><td></td>
										<td><input style="padding-left: 10px"  type="checkbox" id="is_MOP"/>&nbsp;&nbsp;&nbsp;吗啡</td>
										<td>检验结果：<select id="MOP_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
									<tr><td></td>
										<td><input style="padding-left: 10px"  type="checkbox" id="is_MET"/>&nbsp;&nbsp;&nbsp;甲基苯丙胺</td>
										<td>检验结果：<select id="MET_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
									<tr><td></td>
										<td><input style="padding-left: 10px"  type="checkbox" id="is_COC"/>&nbsp;&nbsp;&nbsp;可卡因</td>
										<td> 检验结果：<select id="COC_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
									<tr>
										<td></td>
										<td><input style="padding-left: 10px"  type="checkbox" id="is_DAMA"/>&nbsp;&nbsp;&nbsp;大麻</td>
										<td> 检验结果：<select id="DAMA_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
									<tr>
										<td></td>
										<td><input style="padding-left: 10px"  type="checkbox" id="is_OTHER"/>&nbsp;&nbsp;&nbsp;其它</td>
										<td>检验结果：<select id="OTHER_Result" class="easyui-combobox" style="width:100px" panelHeight='150'></select></td>
									</tr>
								</table>
							</div>
							<div class="fitem">
								<label style="width:140px;">描述:</label>
								<textarea id="description"  class="easyui-validatebox"  name="description" cols="36" style="height: 30px;width: 330px" rows="3"></textarea>
							</div>
						</form>
						<div class="next"  style="text-align: center;margin-top: 10px">
							<button class="m-btn-1 blue" onclick="cleanUrinalysis(1)">清空</button>
							<button class="m-btn-1 blue" onclick="caseNext(1)">下一步</button>
						</div>
					</div>
				</div>

				<div id="step_div2"  >
					<div class="xx_change_div_2" style="margin-top: 25px;margin-left: 15px">
                        <div class="btn" style="margin-top: 15px">
                            <div style="float: left">
                                <span id="spanButtonPlaceholder1_swfupload"></span>
                                <input id="btnCancel1_swfupload" type="hidden" value="取消上传" onclick="cancelQueue(upload_swfupload);"
                                       disabled="disabled" style="margin-left:8px; height:26px; font-size: 12px; width:70px;" />
                            </div>
                            <div style="float: right;">
                                <button class="m-btn-1 blue" style="width: 125px;height: 55px;margin-left: 30px;font-size: 20px" onclick="openPhotoDialog()">拍照</button>
                            </div>
                        </div>
                        <span class="legend" style="color: white;font-size: 15px">图片列表</span>
                        <div id="photoList" class="datagrid-body" style="margin-top:15px;width:580px;height:260px;padding:20px;border: #33FFFF solid 1px;color: white;font-size: 15px">

                        </div>
						<div class="next"  style="text-align: center;margin-top: 20px">
							<button class="m-btn-1 blue" onclick="securityLast(0)" id="fastDiv">上一步</button>
							<button class="m-btn-1 blue" onclick="urinalysisEnd()">完成</button></br>
						</div>
					</div>
				</div>
                <div id="step_div3"  style="display: none;">

                </div>
			</div>
			<div class="edges">
				</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
			</div>
		</div>
	</div>
	<!-- toolbar -->
	<div id="urinalysisToolbar" style="padding:5px;height:auto">
				嫌疑人姓名:<input id="s_name" class="easyui-textbox" style="width:100px;height: 23px">
				证件号:<input id="s_no" class="easyui-textbox" style="width:150px;height: 23px">
				办案场所：<input id="interrogatearea" class="easyui-combobox" style="width: 175px"/>
				尿检时间:
		        <input type="text" id="start" name="start" class="easyui-datetimebox" style="width:120px"/>
		               到
		        <input type="text" id="end" name="end" class="easyui-datetimebox" style="width:120px"/>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="urinalysisSearch()">查询</a>
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="urinalysisClear()">清除</a>
		</div>
	</div>


	<form id="exprortForm" hidden="true" method="post" action="../../bacs/urinalysis/download.do">
		<input id="id_ef" name="id"  type="hidden"/>
	</form>

<div id="photo_dialog" class="easyui-dialog"
     style="width: 800px; height: 400px;" closed="true">
    <div style="height: 100px;">
        <form id="urinalysis_form2" enctype="multipart/form-data" action="ashx/login.ashx" method="post" style="height: 200px;">
            <table class="main_form1">
                <tr><td colspan="4"><input type="hidden" id="no" name="no" value="" /></td></tr>
                <tr>
                    <td >
                        <!-- 尿检照片框 -->
                        <div id="canvas_view">
                            <canvas id="canvas" width="220" height="170" ></canvas>
                        </div>
                    </td>
                    <td align="left" width="170px">
                        <div class="content_botton">
                            <div class="btn">
                                <input type="button" name="noButton"  onclick="javascript:photoScan();" class="xx_changephoto"  onmousemove="this.className='bc_photoon'" onmouseout="this.className='bc_photoout'"/>
                            </div>
                        </div>
                    </td>
                    <td >
                    <!-- 拍照框 -->
                    <div id="webcam_view" style="width: 220px;height: 170px;">
                        <div id="webcam" ></div>
                    </div>
                    </td>

                </tr>
            </table>
        </form>
        <div class="next"  style="text-align: center;margin-top: 20px">
            <button class="m-btn-1 blue" onclick="urinalysisSaveData(1)">保存</button></br>
        </div>
    </div>
</div>
</body>
</html>