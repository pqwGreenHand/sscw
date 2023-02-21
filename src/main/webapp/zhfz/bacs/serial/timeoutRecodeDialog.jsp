<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="timeoutRecodeDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 80%">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>首次审讯时间相隔入区时间超过两小时人员--历史数据</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#timeoutRecodeDialog').hide();"></a>
            </div>
            <div id="relateDiv1"  class="bd" style="margin-top: 30px;width: 98%;height: 90%">
                <div style="height:100%;">
                    <table id="timeoutRecodeDatagrid"></table>
                </div>
            </div>
            <!-- toolbar -->
            <div id="timeoutRecodetoolbar" style="height: auto;padding: 5px;">
                <div>
                    <label>嫌疑人姓名: </label><input id="out_person" class="easyui-validatebox" style="width: 70px">
                    <label>入区时间从:</label> <input id="out_start_date" class="easyui-datetimebox"style="width: 155px">
                    <label>到:</label> <input id="out_end_date" class="easyui-datetimebox" style="width: 155px">
                    <label>超时时间: </label><select class="easyui-combobox" editable="false" id="hour" name="hour" style="width: 100px;">
                    <option value="2">2小时</option>
                    <option value="3">3小时</option>
                    <option value="4">4小时</option>
                    <option value="5">5小时</option>
                    <option value="6">6小时</option>
                </select>

                    <a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchOutTimeRecord()">搜索</a>
                    <a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClearOutTimeRecord()">清空</a>
                    <a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="exportOutTimeRecord()">导出</a>
                </div>
            </div>
        </div>

    </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>