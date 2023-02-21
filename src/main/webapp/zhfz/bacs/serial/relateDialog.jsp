<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="relateDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 80%">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>历史纪录</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#relateDialog').hide();"></a>
            </div>
            <div id="relateDiv1"  class="bd" style="margin-top: 30px;width: 98%;height: 90%">
                <div style="height:260px;">
                    <table id="relateDatagrid"></table>
                </div>
                <div  class="easyui-tabs"  style="width:1400px;height:300px;" >
                    <div title="同案人员">
                        <table id="sameCasePersonDatagrid" ></table>
                    </div>
                    <div title="事主/证人">
                        <table id="elsePersonCaseDatagrid"></table>
                    </div>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>