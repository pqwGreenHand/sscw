<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="outPopup" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>其他人出区</span></div>
                <a name="noButton" class="close" onclick="closeOutMpopup()"></a>
            </div>            
            <div id="stepDiv3"  class="bd" style="overflow: hidden;">
            <form id="otherPersonForm" enctype="multipart/form-data" method="post">
            	<input type="hidden" id="serialId" name="id" />
                <input type="hidden" id="serialNo" name="serialNo" />
                <table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 89px;margin-top: 22px;">                                       
            		<tr>
                       <td><label for="txtname" style="margin-left: 61px;">其他人信息：<font color="red">*</font></label></td>
                       <td><select id="otherPersonId" name="id" required="true"
						style="margin: -2px; width: 180px; height: 28px"
						class="easyui-combogrid" ></select></td>
                    </tr>
            		<tr> 
            			<td>
                            <!-- 入区照片框 -->
                            <div id="in_photo" style="margin-left: -101px;">
                                <img alt="" src="" id="inphoto" width="240" height="180"
                                   style="padding-left: 0px;">
                            </div>
                        </td>                                              
                        <td align="left">
                            <div class="content_botton">
                                <div class="btn">
                                    <input type="button" name="noButton"
                                           onclick="javascript:photoScanOut();" class="xx_changephoto"
                                           onmousemove="this.className='bc_photoon'"
                                           onmouseout="this.className='bc_photoout'" style="margin-left: -5px;"/>
                                </div>
                            </div>
                        </td>
                        <td>
                            <!-- 拍照框 -->
                            <div id="webcam_out_view" style="width: 240px; height: 180px;margin-left: -102px;">
                                <div id="webcam_out"></div>
                            </div>
                        </td>
                    </tr> 
                    <tr>
                    	<td>
                            <!-- 出区照片框 -->
                            <div id="out_canvas_view">
                                <canvas id="out_canvas" width="240" height="180" style="margin-left: -101px;"></canvas>
                            </div>
                        </td>
                    </tr>           		                                                        					               
                </table>
                </form>
                <div class="next" style="margin-top: 16px;">
                    <button class="m-btn-1 blue" onclick="addOutPhoto()">完成</button>
                </div>
            </div>
            
        <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
    </div>
</div>