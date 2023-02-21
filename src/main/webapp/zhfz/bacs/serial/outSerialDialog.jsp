<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="outPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 700px;width: 1000px;">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>出区流程</span></div>
                <a name="noButton" class="close" onclick="closeOutMpopup()"></a>
            </div>
            <div class="hd">
                <div class="steps">
                    <div id="outStep1" class="i2 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>手环扫描</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <div id="outStep2" class="i1 item m-box now">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>类型选择</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <div id="outStep3" class="i3 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>人像比对</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <div id="outStep4" class="i4 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>台账打印</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i orderContentPeopleclass="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                </div>
            </div>
            <div id="stepOutDiv2"  class="bd" align="center">
                <form id="typeInfo" enctype="multipart/form-data"  action="ashx/login.ashx" method="post" style="margin-top: 60px">
                    <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;">
                        <tr>
                            <td>
                                <label>出区类型：</label>
                            </td>
                            <td>
                                <select id="dataType" name="dataType" class="easyui-combobox" style="margin: -2px; width: 170px; height: 28px"
                                        required="true" panelHeight='250'  editable="false">
                                    <option value="">选择出区类型</option>
                                    <option value="0">正常出区</option>
                                    <option value="1">临时出区</option>
                                    <option value="2">特殊出区</option>
                                    <option value="3">其他出区</option>
                                </select>
                                <font color="red">*</font>
                            </td>
                            <td>
                                <label >去向：</label>
                            </td>
                            <td>
                                <select id="direction" name="direction" class="easyui-combobox" style="margin: -2px; width: 170px; height: 28px"
                                        required="true" panelHeight='250'  editable="false">
                                    <option value="">请选择去向</option>
                                </select>
                                <font color="red">*</font>
                            </td>
                            <td>
                                <label >裁决结果：</label>
                            </td>
                            <td>
                                <select id="confirm_result" name="confirm_result" style="margin: -2px; width: 170px; height: 28px"
                                        class="easyui-combobox" required="true" panelHeight='250'  editable="false">
                                    <option value="">选择裁决结果</option>
                                    <option value="治拘">治拘</option>
                                    <option value="刑拘">刑拘</option>
                                    <option value="逮捕">逮捕</option>
                                    <option value="监居">监居</option>
                                    <option value="排除">排除</option>
                                    <option value="移交">移交</option>
                                    <option value="在逃羁押">在逃羁押</option>
                                    <option value="刑拘直保">刑拘直保</option>
                                    <option value="直保">直保</option>
                                    <option value="警告">警告</option>
                                    <option value="罚款">罚款</option>
                                    <option value="教育">教育</option>
                                    <option value="其他">其他</option>
                                </select>
                                <font color="red">*</font>
                            </td>
                        </tr>
                        <tr style="height: 20px">
                        </tr>
                        <tr>
                            <td>
                                <label>出区原因:</label>
                            </td>
                            <td colspan="5">
                                <textarea id="dataOutReason" name="dataOutReason" rows="6" cols="80" style="height: 80px;width: 700px"
                                  class="easyui-validatebox" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="outBeforeCuff()">上一步</button>
                    <button class="m-btn-1 blue" onclick="typeNext()">下一步</button>
                </div>
            </div>
            <div id="stepOutDiv1"  class="bd" align="center">
                <form id="cuffInfo" enctype="multipart/form-data" action="ashx/login.ashx" method="post" style="margin-top: 20px">

                    <input type="hidden" id="eNo" name="eNo" />
                    <input type="hidden" id="yy" name="yy" />

                    <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;">
                        <tr>
                            <td>
                                <label>嫌疑人手环编号：</label>
                            </td>
                            <td>
                                <input id="cuffNumber" name="cuffNumber" type="text" onkeydown="enterKeyEvent2('#cuffNumber')" class="easyui-validatebox"
                                       style="width:200px;font: 14px arial;padding: 3px" data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"
                                       required="true" /><font color="red">*请扫描手环或嫌疑人姓名*</font>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>嫌疑人姓名：</label>
                            </td>
                            <td>
                                <select id="serialNo" class="easyui-combogrid"
                                        name="serialNo" style="width: 210px;" required="true">
                                </select>
                                <font color="red" style="margin-left: 2px;">*</font>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>押送民警：</label>
                            </td>
                            <td>
                                <input id="outSendUserName" name="sendUserName" type="text" style="width:200px;font: 14px arial; padding: 3px"class="easyui-validatebox"
                                       data-options="validType:'maxLength[10]',invalidMessage:'最长10字节'"/>
                                <input id="outSendUserid" name="sendUserId" type="hidden">
                                <font color="red" style="margin-left: 2px;">*</font>
                            </td>
                        </tr>
                        <%--<tr>
                            <td>
                                <label>案件名称：</label>
                            </td>
                            <td>
                                <input class="easyui-validatebox" data-options="multiline:true,validType:'maxLength[1000]',invalidMessage:'最长1000字节'" id="outAjmc" name="ajmc"
                                       style="width:200px;height: 50px;white-space:nowrap;">
                                <input type="hidden" id="caseId" name="caseId" >
                            </td>
                        </tr>--%>
                        <tr>
                            <td>
                                <label>带出民警卡号：</label>
                            </td>
                            <td>
                                <input id="dataOutCuffId2Text" name="dataOutCuffId2Text" type="text" class="easyui-validatebox"
                                       onkeydown="enterKeyEventOutPolice('#dataOutCuffId2Text')" style="width:200px;font: 14px arial; padding: 3px"
                                       data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" />
                                <%--<font color="red">*请扫描带出民警卡片*</font>--%>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>带出民警：</label>
                            </td>
                            <td>
                                <input id="dataOutUserId2" name="dataOutUserId2" type="text" style="width:210px;font: 14px arial; padding: 3px" class="easyui-combogrid"/>
                            </td>
                        </tr>

                        <%--<tr id="jzTr" style="display: none">
                            <td> <input type="button" name="noButton" value="执法办案平台" onclick="jingzongByzjhmCq()" id="jzcq" style="width:88px;height:32px;border:none;background:url(image/botton_b.png);"></td>
                            <td><span id="selectJzLableCq"><font style="color: red">未关联执法办案平台数据！</font></span>
                                <input type="hidden" id="jzCaseNumberCq" name="jzCaseNumberCq"/></td>

                        </tr>--%>
                        <tr id="ajTr">
                            <td><label for="txtname">手动关联案件编号：</label></td>
                            <td><%--<input id="cqajbh"  name="cqajbh"  onblur="hideyyTr()"
                                       type="text" style="font: 14px arial; padding: 3px;width: 210px"
                                       data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                       required="true" />--%>
                                <input id="cqajbh" name="cqajbh" type="text" class="easyui-validatebox"
                                       style="width:200px;font: 14px arial; padding: 3px"
                                       data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" />
                                <input type="button" name="noButton" value="执法办案平台" onclick="jingzongByzjhmCq()" id="jzcq" style="width:88px;height:32px;border:none;background:url(image/botton_b.png);">
                            </td>
                        </tr>
                        <tr id="ryTr" >
                            <td><label for="txtname">手动关联人员编号：</label></td>
                            <td>
                                <%--<input id="cqrybh"  name="cqrybh"  onblur="hideyyTr()"
                                       type="text" style="font: 14px arial; padding: 3px;width: 210px"
                                       data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                       required="true" />--%>
                                    <input id="cqrybh" name="cqrybh" type="text" class="easyui-validatebox"
                                           style="width:200px;font: 14px arial; padding: 3px"
                                           data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" />
                            </td>
                        </tr>

                        <tr id="glTr">
                            <td>
                                <label>未关联原因：</label>
                            </td>
                            <td>
                                <select id="wglyy" class="easyui-combobox"
                                        name="wglyy" style="width: 210px;" required="true">
                                    <option selected="selected" value=""></option>
                                    <option value="外省市单位案件">外省市单位案件</option>
                                    <option value="涉密案件">涉密案件</option>
                                    <option value="2012年前逃犯">2012年前逃犯</option>
                                    <option value="教育释放">教育释放</option>
                                </select>
                                <font color="red" style="margin-left: 2px;">*请关联执法办案平台或选择或填写原因</font>
                            </td>
                        </tr>
                        <%--
                        <tr  id="wxcajTr" style="display: none">
                            <td></td>
                            <td ><label for="txtname"><font style="color: red">未形成案件请直接点击下一步!</font></label></td>
                        </tr>--%>

                          <tr>
                            <td>
                                <label>是否送押解队：</label>
                            </td>
                            <td>
                               <input type="radio" value='0' name="sfsyjd" id="sfsyjd0" />未送 
                               <input type="radio" value='1' name="sfsyjd" id="sfsyjd1" />送
                            </td>
                        </tr>
                        
                        
                         <%-- <tr>
                                <td><label>人员编号：</label></td>
                                <td><input type="text" id="outPersonNo" name="personNo"   class="easyui-combogrid" style="font: 14px arial; padding: 3px;width: 210px"
                                        /> 
                                </td>
                            </tr> 
                        --%>



                    </table>
                </form>
                <div class="next" style="margin-top: 20px">
                    <%--<button class="m-btn-1 blue" onclick="cuffNext()">下一步</button>--%>
                   <button class="m-btn-1 blue" onclick="glyyNext()">下一步</button>
                </div>
            </div>

            <%--w关联原因--%>
            <div id="glyy_dialog" class="easyui-dialog"
                 style="width: 400px; height: 330px; padding: 10px 20px"
                 closable="true" closed="true" buttons="#glyy-buttons">
                <form id="glyy_dialog" method="post">
                    <table id="tb" pagination="true" fitColumns="true"
                           singleSelect="true" width="100%">
                        <tr >
                            <td ><label for="txtname">未关联执法办案平台原因：<font color="red">*</font></label></td>
                            <td><textarea id="glyy" name="glyy" rows="5" cols="30"></textarea>
                            </td>
                        </tr>

                    </table>
                </form>
            </div>
            <div id="glyy-buttons">
                <a href="javascript:void(0)" class="easyui-linkbutton" name="noButton"
                   iconCls="icon-ok" onclick="glyySave()" style="width: 90px">保存</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" name="noButton"
                   iconCls="icon-cancel"
                   onclick="javascript:$('#glyy_dialog').dialog('close');"
                   style="width: 90px">取消</a>
            </div>

            <div id="stepOutDiv3"  class="bd" align="center">
                <form id="photoInfo" enctype="multipart/form-data"
                      action="ashx/login.ashx" method="post" style="margin-top: 25px">
                    <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;">
                        <tr style="height: 20px">
                            <td>
                                <label>入区照片</label>
                            </td>
                            <td style="width: 10px"></td>
                            <td>
                                <label>出区照片</label>
                            </td>
                            <td style="width: 20px"></td>
                            <td>
                                <label>预览</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <!-- 入区照片框 -->
                                <div style="padding-left: 0px;">
                                    <img alt="" id="inphoto" width="240" height="180"
                                         style="padding-left: 0px;" src="image/noPhoto.jpg" onerror='this.src="image/noPhoto.jpg"'>
                                </div>
                            </td>
                            <td style="width: 10px"></td>
                            <td>
                                <!-- 出区照片框 -->
                                <div style="padding-left: 0px;">
                                    <img alt="" id="outPhoto" width="240" height="180"
                                         style="padding-left: 0px;" src="image/default.jpg" onerror='this.src="image/noPhoto.jpg"'>
                                </div>
                            </td>
                            <td>
                                <a id="outSerialPhotoTips" class="item i1 m-box" name="noButton" onclick="outGetImgBase64()">
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拍照</span>
                                    <div class="edges">
                                        <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                    </div>
                                </a>
                            </td>
                            <td>
                                <!-- 预览框 -->
                                <div style="padding-left: 0px;">
                                    <img alt="" id="outSerialPreviewPhoto" width="240" height="180"
                                         style="padding-left: 0px;" src="image/blackGround.jpg" onerror='this.src="image/blackGround.jpg"'>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="outBeforePhoto()">上一步</button>
                    <button class="m-btn-1 blue" onclick="photoNext()">下一步</button>
                </div>
            </div>
            <div id="stepOutDiv4" class="bd" style="display: none">
                <div class="next" style="margin-top:400px">
                    <button class="m-btn-1 blue" onclick="cruquSign()">签字</button>
                    <button class="m-btn-1 blue" onclick="outPrintSign()">签字版打印</button>
                    <button class="m-btn-1 blue" onclick="puTongOutPrintSign()">原始版打印</button>
                    <button class="m-btn-1 blue" onclick="closeOutMpopup()">出区结束</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>

<form action="post" id="imageForm">
    <input type="hidden" id="imageData" name="imageData" />
    <input type="hidden" id="serialId" name="serialId" />
</form>