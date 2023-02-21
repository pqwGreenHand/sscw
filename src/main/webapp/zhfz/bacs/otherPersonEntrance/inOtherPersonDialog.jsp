<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style>
<!-- 入区-->
.in .popup-inner .title2{
	display: none;
}
.in #stepDiv2 .main_form1{color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: -5px;}
.in #stepDiv2 .main_form1 .otherPersonInfo {
	display: none;
}
.in #stepDiv2 .main_form1 #canvas {
	margin-left: -157px;
}
.in #stepDiv2 .main_form1 #webcam_view {
	margin-left: 0px;
}
.in #stepDiv2 .main_form1 .btn input{
	margin-left: 0px;
} 
.in #stepDiv2 .main_form1 #in_photo{
	display: none; padding-left: 0px;
}
.in #stepDiv2 .next{
	margin-top: 101px;
}
.in #stepDiv2 .next2{
	display: none;
}

<!-- 出区-->
.out #hd{
	display: none; 
}
.out #stepDiv2 .main_form1{color: white;border-collapse: separate;border-spacing: 10px;margin-left: 89px;margin-top: 22px;}

.out #stepDiv2 .main_form1 #canvas {
	margin-left: -101px;
}
.out #stepDiv2 .main_form1 #webcam_view {
	margin-left: -102px;
}
.out #stepDiv2 .main_form1 .btn input{
	margin-left: -5px;
} 
.out #stepDiv2 .main_form1 #in_photo{
	margin-left: -101px;
}
.out #stepDiv2 .next{
	display: none;
}
.out #stepDiv2 .next2{
	margin-top: 16px;
	margin-left: 254px;
}
<!--快捷出区 -->
.exit #hd{
	display: none; 
}
.exit #stepDiv2{
    overflow: hidden;
}
.exit #stepDiv2 .main_form1{color: white;border-collapse: separate;border-spacing: 10px;margin-left: 89px;margin-top: 22px;}

.exit #stepDiv2 .main_form1 #canvas {
	margin-left: -101px;
}
.exit #stepDiv2 .main_form1 #webcam_view {
	margin-left: 0px;
}
.exit #stepDiv2 .main_form1 .btn input{
	margin-left: -5px;
} 
.exit #stepDiv2 .main_form1 #in_photo{
	margin-left: -101px;
}
.exit #stepDiv2 .next{
	display: none;
}
.exit #stepDiv2 .next2{
	margin-top: 57px;
	margin-left: 254px;
}
.exit .otherPersonInfo{
	display: none;
}
</style>
<div id="inPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>其他人出入区流程</span></div>
                <a name="noButton" class="close" onclick="closeMpopup()"></a>
            </div>          
            <div id="hd" class="hd">
                <div class="steps">
                    <div id="step1" class="i1 item m-box now">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>基本信息</span>
                        <div class="edges">
                            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <div id="step2" class="i2 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>人像采集</span>
                        <div class="edges">
                            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                </div>
            </div>
            <div id="stepDiv1"  class="bd">
            <form id="otherPersonForm" method="post">
                <input type="hidden" id="personId" name="personId" />
                <input type="hidden" id="birth" name="birth" />
                <input type="hidden" id="nation" name="nation" />
                <input type="hidden" id="country" name="country" />
                <input type="hidden" id="censusRegister" name="censusRegister" />
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: 12px;">
                    <tr>
                    	<td><label for="name">姓名：</label></td>
                    	<td>
                    		<input type="text" id="name1" name="name" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"  
                    		     data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="sex">性别：</label></td>
                        <td>
			                <select id="sex1" name="sex" style = "margin:-2px;width:188px; height:28px" class="easyui-combobox"   required="true">
			                    <option value="1">男</option>
			                    <option value="2">女</option>
			                    <option value="0">未知的性别</option>
			                    <option value="9">未说明的性别</option>
			                </select>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="type">人员类型：</label></td>
                        <td>
			                <select id="type1" name="type" style = "margin:-2px;width:188px; height:28px"  class="easyui-combobox"   required="true">
			                    <option value="1">事主</option>
			                    <option value="2">证人</option>
			                    <option value="3">见证人</option>
			                    <option value="4">被侵害人</option>
			                    <option value="5">报案人</option>
			                    <option value="7">信息采集人</option>
			                    <option value="8">民警</option>
			                    <%--<option value="0">嫌疑人</option>--%>
			                    <option value="9">社工</option>
			                    <option value="10">家属</option>
			                    <option value="6">其它</option>
			                </select>
                            <font color="red" style="margin-left: 2px;">*</font>
            			</td>
            		</tr>
            		<tr>
                     	<td><label for="certificateTypeId">证件类型：</label></td>
                     	<td>
                     	   <select id="certificateTypeId1" name="certificateTypeId" class="easyui-combobox" 
                     	         style = "margin:-2px;width:188px; height:28px" class="easyui-combobox" required="true">
                     	   </select>
                            <font color="red" style="margin-left: 2px;">*</font>
                   	   </td>
                   </tr>
                   <tr>
                		<td><label for="certificateNo">证件号码：</label></td>
                		<td>
                			<input id="certificateNo1" name="certificateNo" type="text" class="easyui-validatebox" 
                			     style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/>
                            <font color="red" style="margin-left: 2px;">*</font>
              			</td>
           		   </tr>                    
                   <tr>
                       <td><label for="txtname">案件信息：</label></td>
                       <td><select id="caseId" name="caseId" required="true"
						style="margin: -2px; width: 188px; height: 28px"
						class="easyui-combogrid" /></select>
                           <font color="red" style="margin-left: 2px;">*</font>
                       </td>
                    </tr>
                    <tr>
                        <td><label for="txtname">办案场所：</label></td>
                        <td>
                            <input id="s_areaId" name="areaId" class="easyui-combobox" style="margin: -2px; width: 180px; height: 28px">
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                </table>
                </form>
                <div class="next">
                	<button class="m-btn-1 blue" onclick="savePersonInfo()">保存</button>
                    <%--<button class="m-btn-1 blue" onclick="saveStepDiv1Data(1)">拍照111</button>--%>
                </div>
            </div>
            <div id="stepDiv2" class="bd" style="display: none;">
              <form id="addOtherPersonForm" enctype="multipart/form-data" method="post">
              	<input type="hidden" id="serialId" name="serialId" />
                <input type="hidden" id="serialNo" name="serialNo" />
                <input type="hidden" id="s_inPhotoId" name="inPhotoId" />
                <table class="main_form1">														
					<tr class="otherPersonInfo">
                       <td><label for="txtname" style="margin-left: 61px;">其他人信息：<font color="red">*</font></label></td>
                       <td><select id="otherPersonId" name="id" required="true"
						style="margin: -2px; width: 180px; height: 28px"
						class="easyui-combogrid" /></select>
                       </td>
                    </tr>
					<tr>                       
                        <td>
                            <!-- 入区照片框 -->
                            <div id="canvas_view">
                                <canvas id="canvas" width="240" height="180"></canvas>
                            </div>
                        </td>
                        <td align="left">
                            <%--<div class="content_botton">--%>
                                <%--<div class="btn">--%>
                                    <%--<input type="button" name="noButton"--%>
                                           <%--onclick="javascript:photoScan();" class="xx_changephoto"--%>
                                           <%--onmousemove="this.className='bc_photoon'"--%>
                                           <%--onmouseout="this.className='bc_photoout'"/>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                                <div >
                                    <button class="m-btn-1 blue" style="width: 74px" onclick="photoScan()">拍照</button>
                                </div>
                        </td>
                        <td>
                            <!-- 拍照框 -->
                            <div id="webcam_view" style="width: 240px; height: 180px;">
                                <div id="webcam"></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    	<td>
                            <!-- 入区照片框 -->
                            <div id="in_photo">
                                <img alt="" src="" id="inphoto" width="240" height="180"
                                     style="padding-left: 0px;">
                            </div>
                        </td>
                    </tr>
				</table>
			 </form>			 				
			
				<div class="next">
                    <button class="m-btn-1 blue" onclick="backStepDiv1()">上一步</button>
                    <button class="m-btn-1 blue" onclick="addPhoto()">完成</button>
                </div>
                
                <div class="next2">
                    <button class="m-btn-1 blue" onclick="addOutPhoto()">完成</button>
                </div>
            </div>
        </div>        
        <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
