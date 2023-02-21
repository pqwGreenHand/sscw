<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<%@ include file="../../common/common-head.jsp"%>
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<script type="text/javascript"	src="waitingmanage.js"></script>
		<script type="text/javascript" src="readRingNew.js"></script>
</head>
<body  style="width: 100%; height: 101%;">
<object id = "ocx" codeBase=/comRD800.dll classid="clsid:638B238E-EB84-4933-B3C8-854B86140668" style="display:none;"></object>
<div id="objects"></div>
<div class="module interrogate">
	<div class="top">
		<div class="left">
			<div class="m-box">
				<div class="m-title-2">
					<i style="background-image: url(../../../static/title-2.png)"></i>
					<span>候问室</span>
				</div>
				<div class="control" style="display: inline;float: left">
					<div style="display:inline;float: left">
					<a style="cursor: default;" class="m-btn-2 blue" onclick="refreshRoom()" id="freshRoom">
						<span> <i style="background-image: url(../../../static/btn-1.png);"></i>刷新</span>
					</a>&nbsp&nbsp
					<a style="cursor: default;" class="m-btn-2 red" onclick="detain()" id="detainSuspect">
						<span><i  style="background-image: url(../../../static/btn-2.png);"></i>看押</span>
					</a>&nbsp&nbsp
					<a style="cursor: default;" class="m-btn-2 brown" onclick="tiXun()" id="detainSuspect">
						<span><i  style="background-image: url(../../../static/btn-5.png);"></i>提讯</span>
					</a>&nbsp&nbsp
					<a style="cursor: default;" class="m-btn-2 purple" onclick="history()" id="historySuspect">
						<span><i style="background-image: url(../../../static/btn-4.png);"></i>记录</span>
					</a>&nbsp&nbsp
					<a style="cursor: default;" class="m-btn-2 brown" id="roomRecord">
						<span onclick="doExport()"><i style="background-image: url(../../../static/btn-5.png);"></i>导出</span>
					</a>
					<form id="exportForm" name="exportForm" method="post" action="../export/exportExcelForWaiting.do">
						<input type="hidden" id="areaId_exp" name="areaId">
					</form>
					</div>
					<div class="m-select" style="display:inline">
						<span>办案场所选择：</span>
						<label>
							<input id="areaCombobox" name="interrogatearea" class="easyui-combobox" style="width: 140px;"  editable="false">
						</label>
					</div>
				</div>
				<div class="room">
					<div class="box" style="overflow-y: scroll" >
						<ul id="boxRoom">
						</ul>
					</div>
				</div>
				<div class="edges">
					</i><i class="i3"></i><i class="i4"></i>
				</div>
				<%--更换侯问室的div开始--%>
				
				<div id="changeRoomDiv" class="m-popup m-info-popup" style="z-index: 999">
					<div class="popup-bg"></div>
					<div class="popup-content m-box"  style="height: 300px;">
						<div class="popup-inner" >
							<div class="title">
								<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>房间列表</span></div>
								<a name="noButton" class="close" onclick="$('#changeRoomDiv').hide()"></a>
							</div>
							<div style="display: flex;margin-top: 20px">
								<div id="roomDiv" style="width: 100%" >
									<div class="room">
										<div class="box" style="overflow-y: scroll;" >
											<ul id="boxRoomChange">

											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="next" id="roomDivButton" style="text-align: center;margin-top:0px">
								<button class="m-btn-1 blue" onclick="saveChangeRoom()">确认更换</button>
								<button class="m-btn-1 blue" onclick="cancerChangeRoom()">取消</button>
							</div>
						</div>
						<div class="edges" >
							</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
						</div>
					</div>
				</div>
				<%--更换侯问室的div结束===--%>
				<%--选择去向的div开始========--%>
				<div id="directRoomDiv" class="m-popup m-info-popup" style="z-index: 20">
					<div class="popup-bg"></div>
					<div class="popup-content m-box"  style="height: 500px;">
						<div class="popup-inner" >
							<div class="title">
								<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>选择去向</span></div>
								<a name="noButton" class="close" onclick="$('#directRoomDiv').hide()"></a>
							</div>
							<div style="display: flex;margin-top: 20px">
								<div id="directDiv" style="width: 100%" >
									<div class="room" style="overflow-x:scroll">
										<div class="box">
											<div style="width: 184px;margin-left: 13px;"><i style="background-image: url(../security/image/popup-2.png);"></i><span>其它去向<font color="red">(双击选择)</font></span></div>
											<ul id="boxDirectChange">
												<li>
													<div ondblclick="selectRecordRoom('信息采集')" id="xxcj" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/xxcj.png);"></i>
														</div>
														<a name="noButton">
															<span>信息采集</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('尿检室')" id="njs" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/nj.png);"></i>
														</div>
														<a name="noButton">
															<span>尿检室</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('卫生间')" id="wsj" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/wsj.png);"></i>
														</div>
														<a name="noButton">
															<span>卫生间</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('辨认室')" id="brs" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/brs.png);"></i>
														</div>
														<a name="noButton">
															<span>辨认室</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('医务室')" id="yws" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/yws.png);"></i>
														</div>
														<a name="noButton">
															<span>医务室</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('醒酒室')" id="xjs" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/xjs.png);"></i>
														</div>
														<a name="noButton">
															<span>醒酒室</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('DNA')" id="dna" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/dna.png);"></i>
														</div>
														<a name="noButton">
															<span>DNA</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('出区')" id="cq" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/tc.png);"></i>
														</div>
														<a name="noButton">
															<span>出区</span>
														</a>
													</div>
												</li>
												<li>
													<div ondblclick="selectRecordRoom('其它')" id="qt" name="other">
														<div>
															<i style="background-image: url(../waitingmanage/image/qt.png);"></i>
														</div>
														<a name="noButton">
															<span>其它</span>
														</a>
													</div>
												</li>
											</ul>
										</div>
										<div class="box">
											<div style="width: 184px;margin-left: 13px;"><i style="background-image: url(../security/image/popup-2.png);"></i><span>审讯问室<font color="red">(双击选择)</font></span></div>
											<ul id="recordRoom"></ul>
										</div>
									</div>
								</div>
							</div>
							<%--<div class="next" id="directDivButton" style="text-align: center;margin-top:0px">
								<button class="m-btn-1 blue" onclick="saveChangeDirect()">确认</button>
								<button class="m-btn-1 blue" onclick="cancerChangeDirect()">取消</button>
							</div>--%>
						</div>
						<div class="edges" >
							</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
						</div>
					</div>
				</div>
				<%--选择去向的div结束===--%>
				<!-- 展示历史记录的div开始-->
				<div id="hisDiv" class="m-popup m-info-popup" style="z-index: 999">
					<div class="popup-bg"></div>
					<div class="popup-content m-box"  style="height: 450px;width: 1200px; ">
						<div class="popup-inner" >
							<div class="title">
								<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>历史记录</span></div>
								<a name="noButton" class="close" onclick="$('#hisDiv').hide()"></a>
							</div>
							<div style="display: flex;margin-top: 40px;height:92%">
								<div id="histroyDiv" style="width: 100%" >
									<table id="histroy_datagrid"></table>
									<!-- toolBar开始-->
										<div id="toolbar" style="padding: 5px; height: auto" hidden="true">
											<div>
												<form id="exportHistoryForm" name="exportHistoryForm" method="post" action="../export/waitingmanageExcel.do">
													<input type="hidden" id="s_areaId" name="areaId">
													<label>嫌疑人姓名:</label>
													<input id="s_personName_his" name="personName" class="easyui-validatebox" style="font: 14px arial; width: 100px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
													<label>证件号码:</label>
													<input id="zjhm" name="personcertNo" class="easyui-validatebox" style="font: 14px arial; width: 120px;padding:3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"  />
													<label>候问室：</label>
													<input id="room_his" name="roomId" class="easyui-combobox" editable="false" style="width:120px;">
													<label>开始时间:</label> <input id="d_begin_date_his" name="beginTime" class="easyui-datetimebox" style="width:185px">
													<label>结束时间:</label><input id="d_end_date_his" name="endTime" class="easyui-datetimebox" style="width:185px">
													<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchWait()">查询</a>
													<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearWairt()">清空</a>
													<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-print" onclick="waitingmanageExport()">导出</a>
												</form>
											</div>
										</div>
										<!--toolBar结束 -->
								</div>
							</div>
						</div>
						<div class="edges" >
							</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
						</div>
					</div>
				</div>
                      <!-- 展示历史记录的div结束===-->
			</div>
		</div>
		<div class="right">
			<div class="m-box">
				<div class="m-title-2">
					<i style="background-image: url(../../../static/title-2.png)"></i>
					<span>侯问室人员统计信息</span>
				</div>
				<div class="box" id="boxTj">

				</div>
				<div class="edges">
					</i><i class="i3"></i><i class="i4"></i>
				</div>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="m-box" style="height: 400px">
			<div class="m-title-2">
				<i style="background-image: url(../../../static/title-2.png)"></i>
				<span>看押人员</span>
			</div>
			<div class="personnel" id="personnel">
				<div class="man" id="suspectMan">

				</div>
				<div class="info">
					<div class="title">详细信息</div>
					<ul id="oneSuspectInfo">
						<li><i>请选择一个上方的侯问室</i></li>
					</ul>
					<div id="butBar">
						<%--<button class="m-btn-2 green" onclick="changeDirection()">
							<span><i style="background-image: url(../../../static/btn-3.png);"></i>选择去向</span>
						</button>--%>
						<button id="backRoomDiv" class="m-btn-2 red" onclick="goBackRoom()">
							<span><i style="background-image: url(../../../static/btn-1.png);"></i>返回侯问室</span>
						</button>
						<button id="changerRoomDiv" class="m-btn-2 blue" onclick="changeRecordRoom()">
							<span><i style="background-image: url(../../../static/btn-6.png);"></i>更换候问室</span>
						</button>
					</div>
				</div>

			</div>
			<div class="edges">
				</i><i class="i3"></i><i class="i4"></i>
			</div>
		</div>
	</div>
</div>

<%--看押的div--%>
<div id="detainDiv" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box"  style="height: 350px;width: 750px">
		<div class="popup-inner" >
			<div class="title">
				<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>看押信息</span></div>
				<a name="noButton" class="close" onclick="$('#detainDiv').hide()"></a>
			</div>
			<div>
			<div id="formDiv" style="width: 55%;float: left">
				<form id="detainForm" method="post" class="form-style">
					<table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 0px;margin-top: 46px;">
						<tr>
							<td><label for="txtname" >嫌疑人手环编号：</label></td>
							<td>
								<input id="suspectNoWait" name="cuffNoWait"  class="easyui-validatebox" placeholder="请扫描嫌疑人手环" style="font: 14px arial; padding: 3px;width: 100px" />
								<input type="button" name="noButton" value="扫描手环" onclick="readRingNo('detain')" />
							</td>

						</tr>
						<tr>
							<td><label for="txtname">嫌疑人姓名：</label></td>
							<td><input id="suspectNameWait" name="suspectNameWait"
									   class="easyui-combogrid"
									   data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节',missingMessage:'该输入项为必输项'"
									   onblur="queryBySerialNo(this.value)" style="width: 160px">
								<font color="red" style="margin-left: -2px;">*</font>
						</tr>
						<tr>
							<td><label for="txtname">侯问室：</label></td>
							<td><input id="roomWait" name="roomWait"
									   class="easyui-combobox"
									   data-options="required:true,missingMessage:'该输入项为必输项'"
									   editable="false" style="width: 160px">
						</tr>
						<tr>
							<td><label for="txtname">送押民警警号：</label></td>
							<td><input id="policeNoWait" onblur="finduser()" name="policeNoWait" type="text" class="easyui-validatebox" style="font: 14px arial; padding: 3px;width: 150px" data-options="required:true,missingMessage:'该输入项为必输项'"/>
								<font color="red" style="margin-left: -2px;">*</font>
							</td>
						</tr>
					</table>
					<!-- wirtingRecordForm开始-->
					<input type="hidden" id="serialId" name="serialId" />
					<input type="hidden" id="personId" name="personId">
					<input type="hidden" id="sendUserId1" name="sendUserId1" />
					<input type="hidden" id="areaId" name="areaId">
					<input type="hidden" id="caseId" name="caseId">
					<input type="hidden" id="roommId" name="roommId">
					<!-- wirtingRecordForm结束-->
				</form>
			</div>
			<div style="width: 45%;display:flex;float: right;">
				<div style="margin-top: 40px;margin-left: 10px" id="personInPhoto">
					<img alt="" src="../waitingmanage/image/suspect.jpg"  width="240" height="180" style="padding-left:0px;"/>
				</div>
			</div> 
			<div class="next" id="firstDiv" style="text-align: center;clear: both;margin-bottom:10px;">
				<button class="m-btn-1 blue" onclick="saveDetainMessage()">看押</button>
				<button class="m-btn-1 blue" onclick="cancerDetainMessage()">取消</button>
			</div>

		</div>
			
		</div>
		<div class="edges">
			</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
		</div>
	</div>
</div>
<!-- 提讯弹出框-->
<div id="tiXunDialog" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box"  style="height: 320px;width: 580px;z-index: 15">
		<div class="popup-inner" >
			<div class="title">
				<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>提讯信息</span></div>
				<a name="noButton" class="close" onclick="$('#tiXunDialog').hide()"></a>
			</div>
			<div style="display: flex">
				<div id="formTiXunDiv" style="width: 100%;text-align: center;" >
					<form id="arraign-form" class="form-style" method="post" style="height: 150px;">
						<input type="hidden" name="recordId" id="recordId"/>
						<input type="hidden" name="policeId" id="policeIdOut"/>
						<input type="hidden" name="serialId" id="serialId2"/>
						<input type="hidden" name="waitingRoomId" id="room-id">
						<table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 98px;margin-top: 46px;">
							<tr>
								<td><label for="txtname">嫌疑人姓名:</label></td>
								<td><input id="save-combgrid" class="easyui-validatebox" style="font: 14px arial; padding: 3px;width: 163px" readonly
										   data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节',missingMessage:'该输入项为必输项'">
								<font color="red" style="margin-left: -2px;">*</font></td>
								<td>
									<a href="#" class="easyui-linkbutton" name="noButton" iconCls="" onclick="showXianYiRenDialog()">选择嫌疑人</a>
								</td>
							</tr>
							<tr>
								<td><label for="txtname">嫌疑人去向:</label></td>
								<td><input id="room-name" class="easyui-validatebox"  data-options="" style="font: 14px arial; padding: 3px;width: 163px" readonly/>
									<input type="hidden" id="roomId3"  name="roomId3">
									<font color="red" style="margin-left: -2px;">*</font>
								</td>
								<td>
									<a href="#" class="easyui-linkbutton" name="noButton" iconCls="" onclick="tiXunChangeDirection()">选择去向</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div class="next" id="firstDivTiXun" style="margin-left: 212px;margin-top:64px">
				<button class="m-btn-1 blue" onclick="saveChangeDirect()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="m-btn-1 blue" onclick="closeTiXunDialog()">关闭</button>
			</div>
		</div>
		<div class="edges">
			</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
		</div>
	</div>
</div>
<!-- 提讯弹出框-->
<div id="xianyirenDialog" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box"  style="height: 520px;width: 880px;z-index: 15">
		<div class="popup-inner" >
			<div class="title">
				<div><i style="background-image: url(../security/image/popup-2.png);"></i><span>提讯信息</span></div>
				<a name="noButton" class="close" onclick="$('#xianyirenDialog').hide()"></a>
			</div>
			<div style="display: flex">
				<div id="formXianYiRenDiv" style="width: 100%;text-align: center;" >
					<div style="color: white;padding-top: 50px">
						<span>提讯民警1：</span><input id="user1Text" type="text" class="easyui-validatebox">
						<input type="button" name="noButton" value="扫描民警卡片" onclick="queryUserByCuff('user1')" />
						<input id="user1Val" type="hidden">
					</div>
					<hr/>
					<div style="height: 400px">
						<table id="xianYiRenDataGrid"></table>
					</div>
					<div>
						<input type="button" name="noButton" value="扫描嫌疑人手环" onclick="checkCuffNo()" />
					</div>
				</div>
			</div>
		</div>
		<div class="edges">
			</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
		</div>
	</div>
</div>
<!--隐藏区域 -->
<input type="hidden" id="roomId"  name="roomId">
<div id="userNoInfo" class="easyui-dialog" closed="true">
	<input type="hidden" id="userNo" name="userNo" />
</div>
<form id="cuffInfo" method="post">
	<input type="hidden" id="cuffNo" name="cuffNo" />
</form>
<input type="hidden" id="recordId2"  name="recordId2">
<input type="hidden" id="roomId1"  name="roomId1">
<input type="hidden" id="roomId2"  name="roomId2">
<input type="hidden" id="direction2"  name="direction2">
<input type="hidden" id="serialId1"  name="serialId1">
</body>