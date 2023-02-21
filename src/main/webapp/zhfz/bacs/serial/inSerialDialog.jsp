<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
    <div id="inPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 600px;width: 800px">
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
                    <input type="hidden" id="caseId" name="caseId" >

                    <input type="hidden" id="rqAjbh" name="ajbh" />
                    <input type="hidden" id="rqRybh" name="rybh" />

                    <input type="hidden" id="yyAjbh" name="yyAjbh" />
                    <input type="hidden" id="yyRybh" name="yyRybh" />

                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 50px">

                    <tr>
                       <%-- <td > <input type="button" name="noButton" value="执法办案平台" onclick="jingzong()" id="jz" style="width:88px;height:32px;border:none;background:url(image/botton_b.png);"></td>--%>
                        <td  colspan="4"><span id="selectJzLable"><font style="color: red">未关联执法办案平台数据！</font></span>
                            <input type="hidden" id="jzCaseNumber" name="jzCaseNumber"/></td>
                    </tr>
                <tr>
                <td><a href="#" id ="readCard" name="noButton" class="easyui-linkbutton" name="readCard" iconCls="icon-tip"
								   onclick="readCard()">读取身份证</a></td>
                <td></td>
                </tr>
                    <tr>
                      
                        <%--<td><label >关联案件：</label></td>
                        <td><input id="link_case_ajmc" class="easyui-validatebox" readonly="readonly"  style="width:160px">
                            <font color="red" style="margin-left: 2px;">*</font>
                            <a style="margin-left: 5px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"onclick="linkCase()">案件查询</a>
                        </td>--%>

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
                        <td colspan="4"><label>是否信息采集：</label>
                         <font color="red" style="margin-left: 2px;">*</font>
                            <input type="radio" value='0' name="sfxxcjin" id="sfxxcj0" checked='checked' />未采集 
                            <input type="radio" value='1' name="sfxxcjin" id="sfxxcj1" />已采集 
                            <input type="radio" value='2' name="sfxxcjin" id="sfxxcj2"  />所内采集
                            <input type="radio" value='3' name="sfxxcjin" id="sfxxcj3"  />办案中心内采集
                        </td>
                        </tr>
                    <tr>
                        <td><label >预约信息：</label></td>
                        <td><select id="dataOrderId" name="dataOrderId" style="margin: -2px; width: 170px; height: 28px"
                                    class="easyui-combogrid" required="true" ></select>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td>办案场所</td>
                        <td>
                            <input id="orderarea" style="width:170px;" name="areaId"class="easyui-combobox" panelHeight='150' required="true" ></input>
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td><label>入区手续：</label></td>
                        <%--<td><input id="entranceProcedure" name="entranceProcedure" style="margin: -2px; width: 170px; height: 28px"
                                   class="easyui-combobox" required="true" /><font color="red" style="margin-left: 2px;">*</font></td>--%>
                        <td><input id="entranceProcedure" name="entranceProcedure" style="margin: -2px; width: 170px; height: 28px"
                                   class="easyui-combobox" /></td>
                        <td><label>传唤起始时间：</label></td>
                        <%--<td><input id="writtenTime" name="writtenTime"style="margin: -2px; width: 170px; height: 28px"
                            class="easyui-datetimebox" required="true" formatter="yyyy-mm-dd hh:mm:ss" />
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>--%>
                        <td><input id="writtenTime" name="writtenTime"style="margin: -2px; width: 170px; height: 28px"
                                   class="easyui-datetimebox" formatter="yyyy-mm-dd hh:mm:ss" />
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
                        <td><input id="dataCertificateNo" name="dataCertificateNo" onkeydown="enterKeyEvents()" class="easyui-validatebox"
                                   type="text" style="font: 14px arial; padding: 3px"
                                   data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                   required="true" />
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                    </tr>

                    <tr>
                        <td><label>案件类型：</label></td>
                        <td>
                            <select id="ajlxNews"	name="ajlx" style="width: 170px" class="easyui-combobox" required="true">
                            </select>
                            <font color="red" style="size: 15px">&nbsp;*</font>
                        </td>
                        <td><label>案由：</label></td>
                        <td>
                            <input id="abNews" name="ab" class="easyui-combobox" style="width: 170px" required="true">
                            <font color="red" style="size: 15px">&nbsp;*</font>
                        </td>
                    </tr>

                    <tr>
                        <td><label >送押民警：</label></td>
                        <td><input id="sendUserNO" style="font: 14px arial; padding: 3px"	name="sendUserNO" onfocus="if(this.value == '请输入警号') this.value = ''" onblur="finduser()"
                                   class="easyui-validatebox" data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true">
                            <input id="sendUserId" name="sendUserId"  type="hidden">
                            <font color="red" style="margin-left: 2px;">*</font>
                        </td>
                        <td style="display: none;"><label >关联警综：</label></td>
                        <td style="display: none;"><input id="link_case_ajmc" name="personNo" class="easyui-validatebox" readonly="readonly"  style="width:160px">
                            <input type="hidden" id="jz_ajbh" name="ajbh">
                            <a style="margin-left: 5px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton"onclick="linkJzAjxx()">警综查询</a>
                        </td>
                    </tr>

                    <tr id="jz_person_inserial" style="display: none">
                        <td><label>警综嫌疑人：</label></td>
                        <td><select id="link_jzAjxx_person"
                                    name="dataCertificateTypeId"
                                    style="margin: -2px; width: 170px; height: 28px"
                                    class="easyui-combobox" ></select>
                        </td>
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
                            <td >
                                <img  id="inSerialPhoto" height="150px;" width="180px" src="image/default.jpg">
                            </td>
                            <td width="150px" align="center">
                                <a id="inSerialPhotoTips" class="item i1 m-box" name="noButton" onclick="getImgBase64()">
                                    <span>拍照</span>
                                    <div class="edges">
                                        <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                    </div>
                                </a>
                            </td>
                            <td>
                                <img  id="inSerialPreviewPhoto" height="150px;" width="180px" src="image/blackGround.jpg">
                            </td>
                        </tr>
                        <tr style="height: 100px">
                            <td align="right"><label>嫌疑人手环编号：</label></td>
                            <td colspan="2"><input id="dataCuffIdText" name="dataCuffIdText" onkeydown="enterKeyEvent('#dataCuffId', 0)" type="text"
                                       style="font: 14px arial; padding: 3px" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                       required="true" class="easyui-validatebox"/>
                                <select id="dataCuffId" name="dataCuffId"style="margin: -2px; width: 170px; height: 28px;"
                                       class="easyui-combobox"  required="true" panelHeight='200'/></select>
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
                            <img src="static/img-1.png" id="dialogSerialImg">
                        </div>
                        <ul class="info p8" >
                            <li class="i3">
                                <div class="num-text" id="renzheng">
                                    <span><i id="photoDistinguish"></i></span>
                                    <p>人证</p>
                                </div>
                            </li>
                            <li id="abc" class="i2">
                                <div class="icon-text" id="zaitao">
                                    <img src="static/locker-22.png">
                                    <p>在逃</p>
                                </div>
                            </li>
                            <%--<li class="i3">
                                <div class="icon-text without" id="feizaitao">
                                    <img src="static/locker-22.png">
                                    <p>非在逃</p>
                                </div>
                            </li>--%>
                            <li class="i8">
                                <div class="icon-text" id="historyResult">
                                    <img src="static/locker-22.png">
                                    <p>历史对比结果</p>
                                </div>
                            </li>

                            <li class="i6">
                                <div class="icon-text" id="qianke">
                                    <img src="static/locker-22.png">
                                    <p>前科</p>
                                </div>
                            </li>
                           <%-- <li class="i7">
                                <div class="icon-text without" id="feiqianke">
                                    <img src="static/locker-22.png">
                                    <p>非前科</p>
                                </div>
                            </li>--%>
                            <li class="i1">
                                <div class="icon-text" id="renkou" >
                                    <img src="static/locker-22.png">
                                    <p>人口</p>
                                </div>
                            </li>
                        </ul>
                </div>
                <div class="next" style="margin-top:5px">
                    <button class="m-btn-1 blue" onclick="ruquSign()">签字</button>
                    <button class="m-btn-1 blue" onclick="printSign()">签字版打印</button>
                    <button class="m-btn-1 blue" onclick="puTongInPrint()">原始版打印</button>
                    <button class="m-btn-1 blue" onclick="closeMpopup()">入区结束</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>