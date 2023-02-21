<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="PringTaizhangDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 300px;width:460px">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>选择打印台账类型</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#PringTaizhangDialog').hide();"></a>
            </div>
            <div id="PringTaizhangDiv1"  class="bd" style="margin-top: 50px;width: 98%;height: 90%;overflow:hidden" align="center">
                <input type="hidden" id="PringTaizhangSerialId" >
                <div class="page locker-home" style="height:250px;width: 660px">
                    <div class="main" style="height:250px;width: 660px">
                        <div class="box" style="text-align:left;width:660px">
                            <a id="rqTaizZhangPring" class="item i1 m-box" name="noButton" onclick="RuPrint()" style="margin-left: 10px;width: 150px">
                                <span>入区台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a>
                          <!--   <a id="whyTaizZhangPring" class="item i1 m-box" name="noButton" onclick="WuPrint()" style="margin-left: 10px;width: 150px">
                                <span>五合一台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a> -->
                            <a id="cqTaizZhangPring" class="item i1 m-box" name="noButton" onclick="CuPrint()" style="margin-left: 10px;width: 150px">
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
<div id="SignTaizhangDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 300px;width:460px">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>选择签字台账类型</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#SignTaizhangDialog').hide();"></a>
            </div>
            <div id="SignTaizhangDiv"  class="bd" style="margin-top: 50px;width: 98%;height: 90%;overflow:hidden" align="center">
                <input type="hidden" id="SignTaizhangSerialId" >
                <div class="page locker-home" style="height:250px;width: 660px">
                    <div class="main" style="height:250px;width: 660px">
                        <div class="box" style="text-align:left;width:660px">
                            <a id="rqTaizZhangSign" class="item i1 m-box" name="noButton" onclick="RuSign()" style="margin-left: 10px;width: 150px">
                                <span>入区台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a>
                            <%--<a id="whyTaizZhangSign" class="item i1 m-box" name="noButton" onclick="WUSign()" style="margin-left: 10px;width: 150px">--%>
                                <%--<span>五合一台账</span>--%>
                                <%--<div class="edges">--%>
                                    <%--<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>--%>
                                <%--</div>--%>
                            <%--</a>--%>
                            <a id="cqTaizZhangSign" class="item i1 m-box" name="noButton" onclick="CuSign()" style="margin-left: 10px;width: 150px">
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

<div id="cxSignTaizhangDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 300px;width:460px">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>选择重新签字台账类型</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#cxSignTaizhangDialog').hide();"></a>
            </div>
            <div id="cxSignTaizhangDiv"  class="bd" style="margin-top: 50px;width: 98%;height: 90%;overflow:hidden" align="center">
                <input type="hidden" id="cxSignTaizhangSerialId" >
                <div class="page locker-home" style="height:250px;width: 860px">
                    <div class="main" style="height:250px;width: 860px">
                        <div class="box" style="text-align:left;width:860px">
                            <a id="cxrqTaizZhangSign" class="item i1 m-box" name="noButton" onclick="cxRuSign()" style="margin-left: 10px;width: 150px">
                                <span>入区台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a>
                            <%--<a id="cxwhyTaizZhangSign" class="item i1 m-box" name="noButton" onclick="cxWUSign()" style="margin-left: 10px;width: 150px">--%>
                                <%--<span>五合一台账</span>--%>
                                <%--<div class="edges">--%>
                                    <%--<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>--%>
                                <%--</div>--%>
                            <%--</a>--%>
                            <a id="cxcqTaizZhangSign" class="item i1 m-box" name="noButton" onclick="cxCuSign()" style="margin-left: 10px;width: 150px">
                                <span>出区台账</span>
                                <div class="edges">
                                    <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                                </div>
                            </a>
                            <%--<a id="cxlscqTaizZhangSign" class="item i1 m-box" name="noButton" onclick="cxlsCuSign()" style="margin-left: 10px;width: 160px">--%>
                                <%--<span>临时出区台账</span>--%>
                                <%--<div class="edges">--%>
                                    <%--<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>--%>
                                <%--</div>--%>
                            <%--</a>--%>
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