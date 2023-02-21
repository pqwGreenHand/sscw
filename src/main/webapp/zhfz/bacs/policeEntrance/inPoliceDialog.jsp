<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="inPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>民警入区流程</span></div>
                <a name="noButton" class="close" onclick="closeMpopup()"></a>
            </div>
            <div class="hd">
                <div class="steps">
                    <div id="step1" class="i1 item m-box now">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>案件信息</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <div id="step2" class="i2 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>民警信息</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <!-- <div id="step3" class="i3 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>人脸识别</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div> -->
                </div>
            </div>
            <div id="stepDiv1"  class="bd">
            <form id="caseInfoForm" method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: 12px;">
                    <tr style="display: none;">
                        <td><label for="txtname">案件信息：</label></td>
                        <td><select id="caseId" name="id"
							style="margin: -2px; width: 180px; height: 28px"
							class="easyui-combogrid"></select><font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td>
                            <a style="margin-left: 5px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"onclick="linkCase()">案件查询</a>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="txtname">预约信息：</label></td>
                        <td><select id="orderRequestId" name="orderRequestId"
                                    style="margin: -2px; width: 180px; height: 28px"
                                    class="easyui-combogrid" required="true"></select><font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="txtname">办案场所：</label></td>
                        <td>
                            <input id="police_areaId" name="areaId" class="easyui-combobox" required="true" style="margin: -2px; width: 180px; height: 28px">
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
						<td><label for="txtname">案件类型：</label></td>
						<td><input id="policeXjlx" name="ajlx"
							class="easyui-combobox" style="margin: -2px; width: 180px"
							required="true" /><font color="red" style="margin-left: 2px;">*</font>
                        </td>
					</tr>
					<tr>
						<td><label for="txtname">案件性质：</label></td>
						<td><input id="policeAb" class="easyui-combobox"
							name="ab" style="width: 180px;" required="true"><font color="red" style="margin-left: 2px;">*</font>
						</td>
					</tr>
					<%--<tr>
						<td><label for="txtname">案发时间：</label></td>
						<td><input id="policeAfsj" class="easyui-datetimebox"
							name="afsj" style="width: 180px;" >
						</td>
					</tr>--%>
					<tr>
						<td><label for="txtname">基本案情：</label></td>
						<td><textarea id="afdd" name="afdd" class="easyui-validatebox"
								rows="6" cols="40" style="width: 168px;height: 50px;"></textarea></td>
					</tr>                
                </table>
                </form>
                <div class="next">
                    <button class="m-btn-1 blue" onclick="saveStepDiv1Data()">下一步</button>
                </div>
            </div>
            <div id="stepDiv2" class="bd" style="display: none">
              <form id="addPoliceForm" method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 3px;margin-top: -27px;">
					<tr>
						<td><label for="txtname">民警警号：</label></td>
						<td><input type="text" class="easyui-validatebox" id="policeNo" name="policeNo" onblur="finduser1(0)" style="font: 14px arial; padding: 3px" required="true" /> 
							<input type="hidden" id="policeId" name="policeId"><font color="red">*</font></td>
					</tr>
					<tr>
						<td><label for="txtname">民警类型：</label></td>
						<td><input type="radio" value='0' name="policeType" id="main"
							checked='checked' />主办民警 &nbsp;&nbsp;&nbsp;&nbsp; 
							<input type="radio" value='1' name="policeType" id="assist" />协办民警</td>
					</tr>
					<tr>
						<td><label for="txtname">民警卡片：</label></td>
						<td><input type="text" class="easyui-validatebox" id="cuffNo" name="cuffNo" style="font: 14px arial; padding: 3px" required="true" />
						   <input type="hidden" id="cuffId" name="cuffId" /><font color="red">*</font>
						</td>
						<!-- <select id="cuffId" name="cuffId" readonly="true"
							style="margin: -2px; width: 170px; height: 28px"
							class="easyui-combobox" required="true" /></select> 
							<input type="button" class="m-btn-1 blue" name="noButton" value="扫描卡片" onclick="readRingNo(1, '#cuffId')" />
							手动选择:<input id="selte" type="checkbox" onchange="cheack(this, '#cuffId')" /></td> -->						
						<td><a href="javascript:void(0)" class="easyui-linkbutton c6" name="noButton" iconCls="icon-add" onclick="AddPolice()" style="width: 68px">添加</a></td>
					</tr>
					<!-- <tr>						
						<td><a href="javascript:void(0)"
						class="easyui-linkbutton c6" name="noButton" iconCls="icon-add"
						onclick="AddPolice()" style="width: 68px">添加</a></td>
						<td><a href="javascript:void(0)"
							class="easyui-linkbutton c6" name="noButton"
							iconCls="icon-reload" onclick="RefeshPolice()"
							style="width: 68px">刷新</a></td>
					</tr> -->
				</table>
			 </form>
			 
				<div style="width: 580px; height: 180px;" >
					<table id="policegrid" height="30px;"></table>
				</div>
			
				<div class="next" style="margin-top: 15px;">
                    <button class="m-btn-1 blue" onclick="backStepDiv1()">上一步</button>
                    <button class="m-btn-1 blue" onclick="endEntrance()">完成</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
