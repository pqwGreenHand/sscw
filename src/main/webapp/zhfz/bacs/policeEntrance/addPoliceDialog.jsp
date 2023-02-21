<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="addInPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box"  style="height: 300px;">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>添加民警入区</span></div>
                <a name="noButton" class="close" onclick="closeMpopupAddPolice()"></a>
            </div>        
            <div id="stepDiv3" class="bd">
              <form id="addToPoliceForm"  class="form-style" method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 147px;margin-top: 46px;">
                    <input id="orderRequestId1" name="orderRequestId" type="hidden" >
                    <input id="areaId1" name="areaId" type="hidden" >
                    <input id="opPid" name="opPid" type="hidden" >
                    <input id="opUserId" name="opUserId" type="hidden" >
                    <input id="policeType" name="policeType" type="hidden" >
                    <tr>
                        <td><label for="txtname">民警警号：</label></td>
                        <td><input type="text" class="easyui-validatebox" id="addpoliceNo" name="policeNo" onblur="finduser3(0)" style="font: 14px arial; padding: 3px" required="true" />
                            <input type="hidden" id="addpoliceId" name="policeId"><font color="red">*</font></td>
                    </tr>
                    <tr>
                        <td><label for="txtname">民警卡片：</label></td>
                        <td><input type="text" class="easyui-validatebox" id="addcuffNo" name="cuffNo" style="font: 14px arial; padding: 3px" required="true" />
                            <input type="hidden" id="addcuffId" name="cuffId" /><font color="red">*</font>
                        </td>
                        <td></td>
                    </tr>
				</table>
			 </form>
			 			 			
				<div class="next">
                    <button class="m-btn-1 blue" onclick="savePolice()">添加</button>
                    <button class="m-btn-1 blue" onclick="closeMpopupAddPolice()">取消</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
