<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="outPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box"  style="height: 300px;">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>来访人员出区</span></div>
                <a name="noButton" class="close" onclick="closeMpopupOut()"></a>
            </div>
            <div id="stepDiv3" class="bd">
                <%--<form id="otherPersonForm" method="post">
                    <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 147px;margin-top: 46px;">
                        <tr>
                            <td><label for="txtname">来访人员卡号：<font color="red">*</font></label></td>
                            <td>
                                <input id="card" name="card" type="text" class="easyui-validatebox" style="font: 14px arial; padding: 3px" required="true" />
                                <input type="hidden" id="exit_cuffId" name="cuffId" /></td>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="txtname">证件编号：</label></td>
                            <td><input type="text" class="easyui-validatebox" id="certificateNo" name="certificateNo" style="font: 14px arial; padding: 3px" readonly="readonly"/>
                                <input type="hidden" id="policeIdOut" name="policeId"></td>
                        </tr>
                    </table>
                </form>--%>
                    <div style="margin-top: 65px;margin-left: 120px">
                        <span style="color: white;">来访人员 :</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <select  id="outReception" name="outReception" class="easyui-combogrid" style="width:189px;height: 28px;" data-options="validType:'maxLength[128]'"></select>
                        <font color="red">*</font>
                    </div>

                    <div class="next" style="margin-top: 70px">
                        &nbsp;&nbsp;&nbsp;  <button class="m-btn-1 blue" onclick="outArea()">出区</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button class="m-btn-1 blue" onclick="closeMpopupOut()">取消</button>
                    </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
