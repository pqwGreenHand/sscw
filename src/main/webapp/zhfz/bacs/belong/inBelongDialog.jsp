<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="inPopup" class="m-popup m-info-popup" >
    <div class="popup-bg"></div>
    <div class="popup-content m-box">
        <div class="popup-inner" >
            <div class="title">
                <div><i style="background-image: url(image/popup-2.png);"></i><span>存物流程</span></div>
                <a name="noButton" class="close" onclick="closeMpopup()"></a>
            </div>
            <div id="stepDiv1"  class="bd" style="overflow: auto;">
                <form id="detinfo_form"  method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-top: 20px;">
                    <input type="hidden" id="id1" name="id1" value="<%=ids%>"/>
                    <input type="hidden" id="id" name="id"/>
                    <input type="hidden" id="caseId" name="caseId">
                    <input type="hidden" id="serialId" name="serialId">
                    <input type="hidden" id="areaId" name="areaId">
                    <input type="hidden" id="xzType" name="xzType">
                    <input type="hidden" id="bazxCaseId" name="bazxCaseId">
                    <input type="hidden" id="bazxSerialId" name="bazxSerialId">
                    <input type="hidden" id="zfbaAjId" name="zfbaAjId">
                    <input type="hidden" id="zfbaYyId" name="zfbaYyId">
                    <tr>
                        <td style="display:none;"><label>请扫描手环:</label></td>
                        <td style="display:none;">
                            <input id="cuffNumber" name="easyui" class="easyui-textbox"
                                   type="text" style="margin: -2px; width: 170px; height: 28px"/>
                        </td>

                        <td><label>选择嫌疑人:</label></td>
                        <td>
                            <a href="javascript:void(0)" name="noButton" id="mb" class="easyui-menubutton"
                               data-options="menu:'#mm',iconCls:'icon-search'">高级查询</a>
                            <div id="mm" style="width:150px;">
                                <div onclick="selectPerson()">选择人员</div>
                                <div onclick="selectAjxxAndPerson()">本系统案件人员</div>
                                <div onclick="selectBazxAjxxAndPerson()">办案中心人员</div>
                                <div onclick="selectZfbaAjxxAndPerson()">市局平台人员</div>
                            </div>
                        </td>
                        <td><label>嫌疑人姓名：</label></td>
                        <td>
                            <input id="personName" name="personName" class="easyui-validatebox" style="margin: -2px; width: 170px; height: 28px">
                        </td>
                        <%--<td><input id="serialId" name="serialId" class="easyui-combogrid" required="true" data-options="editable:false"--%>
                                   <%--style="margin: -2px; width: 170px; height: 28px"><font color="red">*</font></td>--%>
                    </tr>
                    <tr>
                        <td><label>储物柜编号：</label></td>
                        <td><input id="lockerId" name="lockerId" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="editable:false"><font color="red">*</font></td>
                        <td><label>物品名称：</label></td>
                        <td><input id="names1" name="name" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="url: './combobox_belongin_data.json',valueField: 'id',textField: 'text',validType:'maxLength[128]',invalidMessage:'最长128字节'"><font color="red">*</font></td>
                    </tr>
                    <tr>
                        <td>数量:</td>
                        <td><input id="detailCount" name="detailCount" class="easyui-numberbox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="min:0,validType:'maxLength[11]',invalidMessage:'最长11字节'"><font color="red">*</font></td>
                        <td>单位:</td>
                        <td><input id="unit" name="unit" class="easyui-combobox" style="margin: -2px; width: 170px; height: 28px"
                                   data-options="required:true,url: './combobox_belongin_unit.json',valueField: 'id',textField: 'text',validType:'maxLength[128]',invalidMessage:'最长128字节'"><font color="red">*</font></td>
                    </tr>
                    <tr>
                        <td><label>特征：</label></td>
                        <td colspan="3">
                            <input id="description" name="description" class="easyui-textbox" data-options="multiline:true"
                                   style="width: 450px;height: 60px;overflow-y: hidden;">
                        </td>
                    </tr>
                    <tr>
                        <td>保管措施:</td>
                        <td><input id="saveMethod" name="saveMethod" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="required:true,url: './combobox_belongin_saveMethod.json',valueField: 'id',textField: 'text',validType:'maxLength[128]',invalidMessage:'最长128字节'"></td>
                        <td></td>
                        <td >
                            <p class="m-btn-1 blue" style="font-size: 15px;width: 70px;line-height: 30px" onclick="belongAdd()">增加物品</p>
                        </td>
                    </tr>
                    <tr>
                        <td>扫描条码:</td>
                        <td colspan="3">  <input id="wutm" class="easyui-textbox" name="wutm"
                                     style="font: 14px arial; width: 170px;height: 23px;padding:3px"
                                     data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"/><font color="red">*请扫描条码信息，物品自动带入*</font>
                        </td>
                    </tr>
                </table>
                </form>
                <div style=" height: 180px;" >
                    <table id="belonggrid" style="height: 70%"></table>
                </div>
                <div class="next">
                    <%--<button class="m-btn-1 blue" onclick="belongAdd()">增加物品</button>--%>
                    <button class="m-btn-1 blue" onclick="belongsave()">保存物品</button>
                </div>
            </div>
        </div>
    </div>
</div>
