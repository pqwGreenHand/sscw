<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="tempBackDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 500px;width: 1000px">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>返回</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#tempBackDialog').hide();"></a>
            </div>
            <div id="tempBackDiv1"  class="bd" style="margin-top: 50px">
                <form id="tempBack" enctype="multipart/form-data" action="ashx/login.ashx" method="post">
                    
                </form>
                <div class="next" style="margin-top: 100px;">
                    <button class="m-btn-1 blue" onclick="sureBack()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存</button>
                    <button class="m-btn-1 blue" onclick="javascript:$('#tempBackDialog').dialog('close')" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">取消</button>
                </div>
            </div>
            <div class="edges">
                </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
            </div>
        </div>
    </div>
</div>