<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="inPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 660px;width: 800px">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>入区流程</span></div>
                <a name="noButton" class="close" onclick="closeMpopup()"></a>
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
                        <span>手环发放</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                    <div id="step3" class="i3 item m-box">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>人脸识别</span>
                        <div class="edges">
                            </i><i class="i1"></i><i class="i2"></i></i><i orderContentPeopleclass="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                </div>
            </div>
            <div id="stepDiv1"  class="bd">
                <form id="isBasicInfo" enctype="multipart/form-data" action="ashx/login.ashx" method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 50px">
                    <tr>
                        <td><label >关联案件：</label></td>
                        <td><input id="link_case_ajmc" class="easyui-validatebox" readonly="readonly"  style="width:160px">
                            <font color="red" style="margin-left: 2px;">*</font>
                            <a style="margin-left: 5px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"onclick="linkCase()">案件查询</a>
                        </td>
                        <td>办案场所</td>
                        <td>
                            <input id="orderarea" style="width:170px;" name="areaId"class="easyui-combobox" panelHeight='150' required="true" ></input>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label >嫌疑人姓名：</label></td>
                        <td><select id="orderPeople"  name="orderPeople" style="margin: -2px; width: 170px; height: 28px" class="easyui-combogrid" ></select>
                            <input type="hidden" id="personId" name="personId"/>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td><label for="wumingshi">无名氏：</label></td>
                        <td><input type="checkbox" id="wumingshi" name="wumingshi"
                                   onchange="setWuMingShi();"></td>
                    </tr>
                    <tr>
                        <td><label>入区手续：</label></td>
                        <td><input id="entranceProcedure" name="entranceProcedure" style="margin: -2px; width: 170px; height: 28px"
                            class="easyui-combobox" required="true" /><font color="red" style="margin-left: 2px;">*</font></td>
                        <td><label>手续开具时间：</label></td>
                        <td><input id="writtenTime" name="writtenTime"style="margin: -2px; width: 170px; height: 28px"
                            class="easyui-datetimebox" required="true" formatter="yyyy-mm-dd hh:mm:ss" />
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>

                    <tr>
                        <td><label >预约信息：</label></td>
                        <td><select id="dataOrderId" name="dataOrderId" style="margin: -2px; width: 170px; height: 28px"
                                 class="easyui-combogrid" required="true" ></select>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td><label >送押民警：</label></td>
                        <td><input id="sendUserNO" style="font: 14px arial; padding: 3px"	name="sendUserNO"  onfocus="if(this.value == '请输入警号') this.value = ''" onblur="finduser()"
                                   class="easyui-validatebox" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true" value="请输入警号">
                            <input id="sendUserId" name="sendUserId"  type="hidden">
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>

                    <tr>
                        <td><label >人员类型：</label></td>
                        <td><input id="dataPersonInfo"	name="dataPersonInfo" style="margin: -2px; width: 170px; height: 28px"
                                   class="easyui-combobox" required="true">
                            <input type="hidden" id="dataName" name="dataName" style="font: 14px arial; padding: 3px"
                                   data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true" />
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td><label >性别：</label></td>
                        <td>
                            <select class="easyui-combobox" id="dataSex" name="dataSex" style="width: 170px;">
                                <option value="1">男</option>
                                <option value="2">女</option>
                                <option value="0">未知的性别</option>
                                <option value="9">未说明的性别</option>
                            </select>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label>证件类型：</label></td>
                        <td><select id="dataCertificateTypeId"
                                    name="dataCertificateTypeId"
                                    style="margin: -2px; width: 170px; height: 28px"
                                    class="easyui-combobox" required="true"></select>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td><label >证件号码：</label></td>
                        <td><input id="dataCertificateNo" name="dataCertificateNo" class="easyui-validatebox"
                                   type="text" style="font: 14px arial; padding: 3px"
                                   data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                   required="true" />
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label>基本案情：</label></td>
                        <td colspan="3"><textarea id="ajmc" name="ajmc" rows="5" readonly="readonly" cols="18" class="easyui-validatebox"
                            style="width: 530px;height: 50px" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
                        <input type="hidden" id="caseId" name="caseId" ></td>
                    </tr>
                </table>
                </form>
                <div class="next">
                    <button class="m-btn-1 blue" onclick="saveStepDiv1Data()">下一步</button>
                </div>
            </div>
            <div id="stepDiv2" class="bd" style="display: none">
                <form id="cuff" enctype="multipart/form-data"
                      action="ashx/login.ashx" method="post">
                    <table  class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;" align="center">
                        <tr>
                            <td>
                                <!--照片框 -->
                                <div id="canvas_view" style="border: gray solid 1px">
                                    <canvas id="canvas" width="220" height="170" ></canvas>
                                </div>
                            </td>
                            <td width="100px">
                                <a id="inSerialPhotoTips" class="item i1 m-box" name="noButton" onclick="photoScan()">
                                    <span>&nbsp;&nbsp;拍照</span>
                                    <div class="edges">
                                        <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                    </div>
                                </a>
                            </td>
                            <td >
                                <!-- 拍照框 -->
                                <div id="webcam_view" style="width: 220px;height: 170px;">
                                    <div id="webcam" ></div>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 100px">
                            <td></td>
                            <td><label>嫌疑人手环编号：</label></td>
                            <td><input id="dataCuffIdText" name="dataCuffIdText" onkeydown="enterKeyEvent('#dataCuffId', 0)" type="text"
                                       style="font: 14px arial; padding: 3px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                       required="true" class="easyui-validatebox"/>
                                <select id="dataCuffId" name="dataCuffId"style="margin: -2px; width: 170px; height: 28px;"
                                       class="easyui-combobox" onkeydown="enterKeyEvent('#dataCuffId', 0)" required="true" panelHeight='200'/></select>
                                <font color="red" style="margin-left: 2px;">*</font>
                                <span style="color: white">手动选择:</span><input id="selte" type="checkbox"
                                            onchange="cheack(this, '#dataCuffId')" />
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="next">
                    <button class="m-btn-1 blue" onclick="backStepDiv1()">上一步</button>
                    <button class="m-btn-1 blue" onclick="saveStepDiv2Data()">下一步</button>
                </div>
            </div>
            <div id="stepDiv3" class="bd" style="display: none">
                <div class="locker-popup" style="margin-top: 10px; margin-left: 215px;">
                    <div class="title">对比结果</div>
                        <div class="img">
                            <img src="static/img-1.png">
                        </div>
                        <ul class="info p8">
                            <li class="i1">
                                <div class="num-text">
                                    <span><i>90</i>%</span>
                                    <p>指数</p>
                                </div>
                            </li>
                            <li class="i2">
                                <div class="icon-text">
                                    <img src="static/locker-22.png">
                                    <p>在逃</p>
                                </div>
                            </li>
                            <li class="i3">
                                <div class="icon-text without">
                                    <img src="static/locker-22.png">
                                    <p>指数指数指数</p>
                                </div>
                            </li>
                            <li class="i4">
                                <div class="num-text">
                                    <span><i>90</i>%</span>
                                    <p>指数指数指数</p>
                                </div>
                            </li>
                            <li class="i5">
                                <div class="icon-text">
                                    <img src="static/locker-22.png">
                                    <p>在逃</p>
                                </div>
                            </li>
                            <li class="i6">
                                <div class="icon-text without">
                                    <img src="static/locker-22.png">
                                    <p>指数</p>
                                </div>
                            </li>
                            <li class="i7">
                                <div class="icon-text">
                                    <img src="static/locker-22.png">
                                    <p>在逃</p>
                                </div>
                            </li>
                            <li class="i8">
                                <div class="icon-text without">
                                    <img src="static/locker-22.png">
                                    <p>指数</p>
                                </div>
                            </li>
                        </ul>
                </div>
                <div class="next" style="margin-top: 55px">
                    <button class="m-btn-1 blue" onclick="printSign()">台账打印</button>
                    <button class="m-btn-1 blue" onclick="closeMpopup()">入区结束</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>