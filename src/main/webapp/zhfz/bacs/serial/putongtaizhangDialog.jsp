<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="PringTaizhangDialog2" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 300px;width:460px">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>选择打印台账类型</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#PringTaizhangDialog2').hide();"></a>
            </div>
            <div id="PringTaizhangDiv2"  class="bd" style="margin-top: 50px;width: 98%;height: 90%;overflow:hidden" align="center">
                <input type="hidden" id="PringTaizhangSerialId2" >
                <div class="page locker-home" style="height:250px;width: 660px">
                    <div class="main" style="height:250px;width: 660px">
                        <div class="box" style="text-align:left;width:660px">
                            <a id="rqTaizZhangPring2" class="item i1 m-box" name="noButton" onclick="RuPrint2()" style="margin-left: 10px;width: 150px">
                                <span>入区台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a>
                            <!-- <a id="whyTaizZhangPring2" class="item i1 m-box" name="noButton" onclick="WuPrint2()" style="margin-left: 10px;width: 150px">
                                <span>五合一台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a> -->
                            <a id="cqTaizZhangPring2" class="item i1 m-box" name="noButton" onclick="CuPrint2()" style="margin-left: 10px;width: 150px">
                                <span>出区台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>