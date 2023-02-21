<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="jsPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 900px">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(image/popup-2.png);"></i><span>接收流程</span></div>
                <a name="noButton" class="close" onclick="closeMpopup()"></a>
            </div>
            <div id="stepDiv1"  class="bd" style="overflow: auto;">
                <form id="jsdetinfo_form"  method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-top: 20px;">
                    <input type="hidden" id="id1" name="id1" value="<%=ids%>"/>
                    <input type="hidden" id="id" name="id"/>
                    <input type="hidden" id="caseId" name="caseId">
                    <input type="hidden" id="areaId" name="areaId">
                    <tr>
                        <td><label>请扫描手环:</label></td>
                        <td>
                            <input id="jscuffNumber" name="easyui" class="easyui-textbox"
                                   type="text" style="margin: -2px; width: 170px; height: 28px"
                                   data-options="validType:'maxLength[100]',invalidMessage:'最长100字节'"
                                   />
                        </td>
                        <td><label>嫌疑人姓名：</label></td>
                        <td><input id="jsserialId" name="serialId" class="easyui-combogrid" required="true" data-options="editable:false"
                                   style="margin: -2px; width: 170px; height: 28px"><font color="red">*</font></td>
                        <td><label>储物柜编号：</label></td>
                        <td><input id="jslockerId" name="lockerId" class="easyui-combobox" required="true" style="margin: -2px; width: 170px; height: 28px" data-options="editable:false"><font color="red">*</font></td>

                    </tr>
                </table>
                </form>
                <div style=" height: 180px;" >
                    <table id="djswp" style="height: 70%"></table>
                </div>
                <div style=" height: 180px;" >
                    <table id="jsbelonggrid" style="height: 70%"></table>
                </div>
                <div class="next">
                    <%--<button class="m-btn-1 blue" onclick="belongAdd()">增加物品</button>--%>
                    <button class="m-btn-1 blue" onclick="belongsave()">保存物品</button>
                </div>
            </div>
        </div>
    </div>
</div>
