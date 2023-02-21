<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


    <div id="inPopup" class="m-popup m-info-popup">
        <div class="popup-bg"></div>
        <div class="popup-content m-box">

            <div class="popup-inner">
                <div class="title">
                    <div><i style="background-image: url(static/popup-2.png);"></i><span id="title">来访人员登记信息</span></div>
                    <a name="noButton" class="close" onclick="closeMpopup()"></a>
                </div>
                <%-- <div class="hd">
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
                             <span>人像采集</span>
                             <div class="edges">
                                 </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                             </div>
                         </div>
                     </div>
                 </div>--%>
                <div id="stepDiv1"  class="bd"  style="margin-top: 20px">
                    <form id="otherPersonForm" method="post">
                        <input type="hidden" id="id" name="id" />
                        <input type="hidden" id="personId" name="personId" />
                        <input type="hidden" id="birth" name="birth" />
                        <input type="hidden" id="nation" name="nation" />
                        <input type="hidden" id="country" name="country" />
                        <input type="hidden" id="areaName" name="areaName" />
                        <input type="hidden" id="censusRegister" name="censusRegister" />
                        <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 100px;margin-top: 12px;">
                            <tr>
                                <td><label for="name">姓名：</label></td>
                                <td>
                                    <input type="text" id="name1" name="name" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"
                                           data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/>
                                    <font color="red">*</font>
                                </td>
                            </tr>

                            <tr>
                                <td><label for="department">访客单位：</label></td>
                                <td>
                                    <input type="text" id="department" name="department" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"
                                           data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/>
                                    <font color="red">*</font>
                                </td>
                            </tr>

                            <tr>
                                <td><label for="certificateNo">证件编号：</label></td>
                                <td>
                                    <input type="text" id="certificateNo" name="certificateNo" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"
                                           data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/>
                                    <font color="red">*</font>
                                </td>
                            </tr>

                            <tr>
                                <td><label for="phone">电话号码：</label></td>
                                <td>
                                    <input type="text" id="phone" name="phone" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"
                                           data-options="validType:'maxLength[11]',invalidMessage:'最长11字节'" required="true"/>
                                    <font color="red">*</font>
                                </td>
                            </tr>

                            <tr>
                                <td><label for="reason">事由：</label></td>
                                <td>
                                    <select id="reason" name="reason" style = "margin:-2px;width:188px; height:28px"  class="easyui-combobox"   required="true">
                                        <option value="1">接班</option>
                                        <option value="2">送货</option>
                                        <option value="3">维修</option>
                                        <option value="4">办案</option>
                                        <option value="5">开会</option>
                                        <option value="6">其它</option>
                                    </select>
                                    <font color="red">*</font>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="receiver">经办人：</label></td>
                                <%--<td>
                                    <select id="receiver" name="receiver" class="easyui-combobox"
                                            style = "margin:-2px;width:188px; height:28px" class="easyui-combobox" required="true">
                                    </select>
                                </td>--%>

                                <td>
                                    <input type="text" id="receiver" name="receiver" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"
                                           data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/>
                                    <font color="red">*</font>
                                </td>
                            </tr>
                            <%--<tr>
                                <td><label for="certificateNo">证件号码：<font color="red">*</font></label></td>
                                <td>
                                    <input id="certificateNo1" name="certificateNo" type="text" class="easyui-validatebox"
                                           style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/>
                                </td>
                            </tr>--%>
                            <tr>
                                <td style="padding-bottom: 8px;"><label for="card">临时卡卡号：</label></td>
                                <td style="padding-bottom: 8px;">
                                    <input id="card" name="card"  class="easyui-textbox" data-options="multiline:true"
                                           style="height:70px;width:186px">

                                    <%--<textarea type="text" id="card" name="card" style= "font: 14px arial;
                                    padding:3px ;width: 182px;height: 40px;background-color:
                                    #c6d2ff;border:none" data-options="validType:'maxLength[128]',invalidMessage:'最长128字节'"
                                    required="true" onblur="if(this.value=='')
                                    {this.value='';this.style.color='#333';}" value="文明上网，理性发言"/></textarea><br/>--%>
                                    <br/><font color="yellow"  >多个卡号请用,号分隔</font>
                                </td>



                            </tr>



                            <%-- <tr>
                                 <td><label for="txtname">案件信息：<font color="red">*</font></label></td>
                                 <td><select id="caseId" name="id" required="true"
                                             style="margin: -2px; width: 180px; height: 28px"
                                             class="easyui-combogrid" /></select></td>
                             </tr>--%>
                            <tr>
                                <td><label for="areaId">办案场所：</label></td>
                                <td><select id="areaId" name="areaId" required="true"
                                            style="margin: -2px; width: 180px; height: 28px"
                                            class="easyui-combobox" /></select>
                                    <font color="red">*</font>
                                </td>
                            </tr>

                        </table>
                    </form>
                    <div class="next">
                        <button class="m-btn-1 blue" onclick="savePersonInfo()">保存</button>
                        <%-- <button class="m-btn-1 blue" onclick="saveStepDiv1Data(1)">拍照</button>--%>
                    </div>
                </div>
                <div id="stepDiv2" class="bd" style="display: none">
                    <form id="addPoliceForm" enctype="multipart/form-data" method="post">
                        <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 3px;margin-top: -27px;">
                            <tr>
                                <td>
                                    <!-- 入区照片框 -->
                                    <div id="in_photo" style="display: none; padding-left: 0px;">
                                        <img alt="" src="" id="inphoto" width="240" height="180"
                                             style="padding-left: 0px;">
                                    </div>
                                </td>
                                <td>
                                    <!-- 出区照片框 -->
                                    <div id="canvas_view">
                                        <canvas id="canvas" width="240" height="180" style="margin-left: -30px;"></canvas>
                                    </div>
                                </td>
                                <td align="left">
                                    <div class="content_botton">
                                        <div class="btn">
                                            <input type="button" name="noButton"
                                                   onclick="javascript:photoScan();" class="xx_changephoto"
                                                   onmousemove="this.className='bc_photoon'"
                                                   onmouseout="this.className='bc_photoout'" style="margin-left: -3px;"/>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <!-- 拍照框 -->
                                    <div id="webcam_view" style="width: 240px; height: 180px;">
                                        <div id="webcam"></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>

                    <div style="width: 580px; height: 160px;" >
                        <table id="policegrid" height="10px;"></table>
                    </div>

                    <div class="next" style="margin-top: -32px;">
                        <button class="m-btn-1 blue" onclick="backStepDiv1()">上一步</button>
                        <button class="m-btn-1 blue" onclick="endEntrance()">完成</button>
                    </div>
                </div>
            </div>
            <form action="post" id="imageForm">
                <input type="hidden" id="imageData" name="imageData" />
                <input type="hidden" id="serialId" name="serialId" />
            </form>
            <input type="hidden" id="dataNo" name="dataNo"/>
            <input type="hidden" id="faceImgId" value="0" />
            <div class="edges">
                </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
            </div>
        </div>
    </div>





