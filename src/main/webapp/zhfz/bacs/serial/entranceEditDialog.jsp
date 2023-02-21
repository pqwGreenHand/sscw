<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="entranceEditDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 550px;width: 800px">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>修改入区信息</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#entranceEditDialog').hide();"></a>
            </div>
            <div id="entranceEditDiv1"  class="bd" style="margin-top: 50px;">

                <input type="hidden" id="oldAjbh" name="oldAjbh" />
                <input type="hidden" id="oldRybh" name="oldRybh" />
                <input type="hidden" id="oldZjhm" name="oldZjhm" />
                <input type="hidden" id="sfzdgl" name="sfzdgl" />

                <input type="hidden" id="rssj" name="rssj" />

                <form id="entranceEdit" enctype="multipart/form-data" action="ashx/login.ashx" method="post" style="height:340px">
                    <table class="main_form1"  style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 50px">
                        <input type="hidden" id="eid" name="eid" />
                        <input type="hidden" id="epersonId" name="epersonId" />
                        <tr>
                            <td><label>姓名：</label></td>
                            <td><input type="text" id="ename" name="ename" class="easyui-validatebox" style="font: 14px arial; padding: 3px"
                                       data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'"  required="true" /><font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>性别：</label></td>
                            <td>
                                <select class="easyui-combobox" id="esex" name="esex" style="width: 170px;" panelHeight='170'>
                                    <option value="1">男</option>
                                    <option value="2">女</option>
                                    <option value="0">未知的性别</option>
                                    <option value="9">未说明的性别</option>
                                </select>
                        </tr>
                        <tr>
                            <td><label>证件类型：</label></td>
                            <td><input id="ecertificateTypeId" name="ecertificateTypeId"
                                       style="margin: -2px; width: 170px; height: 28px"
                                       class="easyui-combobox" required="true" panelHeight='250'><font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>证件号码：</label></td>
                            <td><input id="ecertificateNo" name="ecertificateNo"
                                       type="text" style="font: 14px arial; padding: 3px" class="easyui-validatebox"
                                       data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'"
                                       required="true" /><font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="txtname">案件编号：<font color="red">*</font></label></td>
                            <td><input id="editAjbh" name="editAjbh"
                                       type="text" style="font: 14px arial; padding: 3px;width: 200px" class="easyui-validatebox"
                                       data-options="validType:'maxLength[25]',invalidMessage:'最长18字节'"
                                       required="true" /></td>

                            <td><label for="txtname">人员编号：<font color="red">*</font></label></td>
                            <td><input id="editRybh" name="editRybh"
                                       type="text" style="font: 14px arial; padding: 3px;width: 200px" class="easyui-validatebox"
                                       data-options="validType:'maxLength[25]',invalidMessage:'最长18字节'"
                                       required="true" /></td>
                        </tr>


                        <tr>
                            <td><label>人员类型：</label></td>
                            <td>
                                <select id="epersonInfo" name="epersonInfo" panelHeight='170'
                                        style="margin: -2px; width: 170px; height: 28px"
                                        class="easyui-combobox" required="true">
                                    <option value='未成年人'>未成年人</option>
                                    <option value='老年人'>老年人</option>
                                    <option value='特殊疾病'>特殊疾病</option>
                                    <option value='孕妇'>孕妇</option>
                                    <option value='残疾人'>残疾人</option>
                                    <option value='成年人'>成年人</option>
                                </select><font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>入区手续：</label></td>
                            <td>
                                <select id="entranceProcedureCmb" name="entranceProcedureCmb"
                                        style="margin: -2px; width: 170px; height: 28px"
                                        class="easyui-combobox" panelHeight='170'>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="writtenTimeCmb">手续开具时间：</label></td>
                            <td><input id="writtenTimeCmb" name="writtenTimeCmb"
                                       style="margin: -2px; width: 170px; height: 28px"
                                       class="easyui-datetimebox" formatter="yyyy-mm-dd hh:mm:ss" /></td>
                            <td><label>押送民警：</label></td>
                            <td><input id="einUserNo1" name="einUserNo1" type="text"
                                       style="font: 14px arial; padding: 3px" onblur="findUserEdit()" class="easyui-validatebox"
                                       data-options="validType:'maxLength[10]',invalidMessage:'最长10字节'" />
                                <input id="einUserId1" name="einUserId1" type="hidden"></td>
                        </tr>
                        
                        <tr>
                            <td><label>是否信息采集：</label></td>
                            <td colspan="3"> 
                                <input type="radio" value='0' name="sfxxcj" id="sfxxcj0" checked='checked' />未采集 
                                 <input type="radio" value='1' name="sfxxcj" id="sfxxcj1" />已采集 
                                  <input type="radio" value='2' name="sfxxcj" id="sfxxcj2"  />所内采集
                                   <input type="radio" value='3' name="sfxxcj" id="sfxxcj3"  />办案中心内采集
                                &nbsp;&nbsp;&nbsp;&nbsp; 
                            </td>
                        </tr>
                        
                         <tr>
                            <td><label>是否送押解队：</label></td>
                            <td colspan="3"> 
                                <input type="radio" value='0' name="sfsyjdEdit" id="sfxxcj0" checked='checked' />未送 
                                 <input type="radio" value='1' name="sfsyjdEdit" id="sfxxcj1" />送 
                                &nbsp;&nbsp;&nbsp;&nbsp; 
                            </td>
                        </tr>
                        
                        
                        <tr>
                            <td><label for="writtenTimeCmb">正常出区去向：</label></td>
                            <td><input id="outReasonEdit" name="outReason"  panelHeight='250'  editable="false"
                                       style="margin: -2px; width: 170px; height: 28px"    class="easyui-combobox" />
                                 </td>
                            <td><label>裁决结果：</label></td>
                            <td>
                               <select id="confirmResultEdit" name="confirmResult" style="margin: -2px; width: 170px; height: 28px"
                                        class="easyui-combobox"   panelHeight='250'  editable="false">
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
                                 </td>
                        </tr>
                        
                        
                        <tr>
                            <td><label>基本案情：</label></td>
                            <td colspan="3">
                                <textarea id="editAjmc" name="ajmc" rows="5" readonly="readonly" cols="18" class="easyui-validatebox"
                                          style="width: 530px;height: 50px" data-options="validType:'maxLength[1000]',invalidMessage:'最长1000字节'"></textarea>
                                <input type="hidden" id="ecaseId" name="caseId" >
                                <input type="hidden" id="orderId" name="orderId" >
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="next" style="margin-top: 30px">
                    <button class="m-btn-1 blue" onclick="entranceSave()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#entranceEditDialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">取消</button>
                </div>
            </div>
            <div class="edges">
                </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
            </div>
        </div>
    </div>
</div>