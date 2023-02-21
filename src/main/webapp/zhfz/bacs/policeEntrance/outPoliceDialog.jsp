<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="outPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box"  style="height: 300px;">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>民警出区</span></div>
                <a name="noButton" class="close" onclick="closeMpopupOut()"></a>
            </div>        
            <div id="stepDiv3" class="bd">
              <form id="addOutPoliceForm" method="post">
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 147px;margin-top: 46px;">
					<tr>
						<td><label for="txtname">民警卡片：</label></td>
				        <td>
				        	<input id="exit_cuffNo" name="cuffNo" type="text" class="easyui-validatebox" style="font: 14px arial; padding: 3px" required="true" />
					        <input type="hidden" id="exit_cuffId" name="cuffId" /><font color="red" style="margin-left: 2px;">*</font></td>
				        </td>													
					</tr>
					<tr>
						<td><label for="txtname">民警警号：</label></td>
						<td><input type="text" class="easyui-validatebox" id="policeNoOut" name="policeNo" style="font: 14px arial; padding: 3px" readonly="readonly"/> 
							<input type="hidden" id="policeIdOut" name="policeId"></td>
					</tr>										
				</table>
			 </form>
			 			 			
				<div class="next">
                    <button class="m-btn-1 blue" onclick="save()">出区</button>
                    <button class="m-btn-1 blue" onclick="closeMpopupOut()">取消</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
