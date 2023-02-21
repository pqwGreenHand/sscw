<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" +request.getServerName() + ":" +request.getServerPort()
    +path + "/";

    String content = request.getParameter("content");

    String scheduleid = request.getParameter("scheduleid");
%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/css.css">
    <script type="text/javascript" src="composeFight.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<div class="locker-home" style="height: 25%;position:static" >
    <%--<div class="main"  >

    </div>--%>

    <div class="box" id="tj_btn" style="width: 100%; height: 90px; margin-top: -72px" align="center">
        <a  onclick="faceCompareButton()" id="faceCompareButton" name="faceCompareButton" class="myButton i1 m-box"  iconCls="icon-add">
            <span>人像对比</span>
            <div class="edges">
                </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
            </div>
        </a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a  href="#" onclick="queryUrlT('aqjg-sxxt')" name="noButton" class="myButton i1 m-box" style="margin-top: 100px;">
            <span>审讯系统</span>
            <div class="edges">
                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
            </div>
        </a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a  href="#" onclick="queryUrlT('aqjg-zhcs')" name="noButton" class="myButton i1 m-box" style="margin-top: 100px;">
            <span>指挥参审</span>
            <div class="edges">
                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
            </div>
        </a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a  href="#" onclick="csStart()" name="noButton" class="myButton i1 m-box" style="margin-top: 100px;">
            <span>即时通讯</span>
            <div class="edges">
                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
            </div>
        </a>
    </div>
    </div>

    <div style="height: 75%;margin-top: 65px">
        <table id="datagrid" ></table>
    </div>

    <!-- toolbar -->
    <div id="toolbar" style="height: auto;padding: 5px;">
        <div>
            <label>嫌疑人姓名: </label><input id="e_person" class="easyui-validatebox" style="width: 70px">
            <label>证件号码:</label><input id="e_certificateNo" class="easyui-validatebox" style="width: 180px">
            <label>类型: </label><select class="easyui-combobox" editable="false" id="e_status" name="e_status" style="width: 100px;">
            <option selected="selected" value="">全部</option>
            <option value="11">未出区</option>
        </select>
            <label>案件类型: </label><select class="easyui-combobox" editable="false" id="e_ajlx" name="e_ajlx" style="width: 100px;">
            <option selected="selected" value="">全部</option>
            <option value="1">行政</option>
            <option value="2">刑事</option>
        </select>
            <label>入区时间从:</label> <input id="e_start_date" class="easyui-datetimebox"style="width: 155px">
            <label>到:</label> <input id="e_end_date" class="easyui-datetimebox" style="width: 155px">
            <label>办案场所选择:</label>
            <input id="interrogatearea" name="interrogatearea" class="easyui-combobox" style="width: 200px;" data-options="" editable="false">
            <a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">搜索</a>
            <a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-clear" onclick="doClear()">清空</a>
            <%--<a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="doClear()">导出</a>--%>

            <input type="hidden" id="transContent" value="<%=content%>">
            <input type="hidden" id="transScheduleid" value="<%=scheduleid%>">
        </div>
    </div>

    <!--  人像对比-->
    <%--<div id="constactImage_dialog" class="easyui-dialog" style="width:650px;height:450px;padding:10px 20px" closed="true" closable="false">
        <div id="stepDiv3" class="bd" style="display: none">
            <div class="locker-popup" style="margin-top: 10px; margin-left: 215px;">
                <div class="title">对比结果</div>
                &lt;%&ndash;<div class="img">
                    <img src="static/img-1.png" id="dialogSerialImg">
                </div>&ndash;%&gt;
                <ul class="info p8" >
                    <li class="i3">
                        <div class="num-text" id="renzheng">
                            <span><i id="photoDistinguish"></i></span>
                            <p>人证</p>
                        </div>
                    </li>
                    <li id="abc" class="i2">
                        <div class="icon-text" id="zaitao">
                            <img src="static/locker-22.png">
                            <p>在逃</p>
                        </div>
                    </li>
                    &lt;%&ndash;<li class="i3">
                        <div class="icon-text without" id="feizaitao">
                            <img src="static/locker-22.png">
                            <p>非在逃</p>
                        </div>
                    </li>&ndash;%&gt;
                    <li class="i8">
                        <div class="icon-text" id="historyResult">
                            <img src="static/locker-22.png">
                            <p>历史对比结果</p>
                        </div>
                    </li>

                    <li class="i6">
                        <div class="icon-text" id="qianke">
                            <img src="static/locker-22.png">
                            <p>前科</p>
                        </div>
                    </li>
                    &lt;%&ndash; <li class="i7">
                         <div class="icon-text without" id="feiqianke">
                             <img src="static/locker-22.png">
                             <p>非前科</p>
                         </div>
                     </li>&ndash;%&gt;
                    <li class="i1">
                        <div class="icon-text" id="renkou" >
                            &lt;%&ndash;<img src="static/locker-22.png">&ndash;%&gt;
                            <p>人口</p>
                        </div>
                    </li>
                </ul>
            </div>
            &lt;%&ndash;<div class="next" style="margin-top:5px">
                <button class="m-btn-1 blue" onclick="ruquSign()">签字</button>
                <button class="m-btn-1 blue" onclick="printSign()">签字版打印</button>
                <button class="m-btn-1 blue" onclick="puTongInPrint()">原始版打印</button>
                <button class="m-btn-1 blue" onclick="closeMpopup()">入区结束</button>
            </div>&ndash;%&gt;
        </div>
    </div>--%>


    <div id="inPopup1"   class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 600px;width: 800px">
        <div class="popup-inner">
            <div class="title">
               <div><i style="background-image: url(static/popup-2.png);"></i><span>对比结果</span></div>
                <a name="noButton" class="close" onclick="closeMpopup()"></a>
            </div>

            <div id="stepDiv3" class="bd" style="display: none;margin-top: 60px">
                <div class="locker-popup" style="margin-top: 10px; margin-left: 215px;">
                    <div class="title">对比结果</div>
                    <div class="img">
                        <img  src="static/img-1.png" id="dialogSerialImg">
                    </div>
                    <ul class="info p8" >
                        <li class="i3">
                            <div class="num-text" id="renzheng">
                                <span><i id="photoDistinguish"></i></span> 
                                <p>人证</p>
                            </div>
                        </li>
                        <li id="abc" class="i2">
                            <div class="icon-text" id="zaitao">
                                <img src="static/locker-22.png">
                                <p>在逃</p>
                            </div>
                        </li>
                        <%--<li class="i3">
                            <div class="icon-text without" id="feizaitao">
                                <img src="static/locker-22.png">
                                <p>非在逃</p>
                            </div>
                        </li>--%>
                        <li class="i8">
                            <div class="icon-text" id="historyResult">
                                <img src="static/locker-22.png">
                                <p>历史对比结果</p>
                            </div>
                        </li>

                        <li class="i6">
                            <div class="icon-text" id="qianke">
                                <img src="static/locker-22.png">
                                <p>前科</p>
                            </div>
                        </li>
                        <%-- <li class="i7">
                             <div class="icon-text without" id="feiqianke">
                                 <img src="static/locker-22.png">
                                 <p>非前科</p>
                             </div>
                         </li>--%>
                        <li class="i1">
                            <div class="icon-text" id="renkou" >
                                <img src="static/locker-22.png">
                                <p>人口</p>
                            </div>
                        </li>
                    </ul>
                </div>
                <%--<div class="next" style="margin-top:5px">
                    <button class="m-btn-1 blue" onclick="ruquSign()">签字</button>
                    <button class="m-btn-1 blue" onclick="printSign()">签字版打印</button>
                    <button class="m-btn-1 blue" onclick="puTongInPrint()">原始版打印</button>
                    <button class="m-btn-1 blue" onclick="closeMpopup()">入区结束</button>
                </div>--%>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>



        <div id="search_constactImage_dialog1" class="easyui-dialog" style="width:500px;height:200px;padding:10px 20px" closed="true" closable="false">
            <table id="tb" pagination="true" fitColumns="true" singleSelect="true" width="100%">
                <tr>
                    <td colspan="4" align="center">
                        <div id = "libaryDiv2"></div>
                    </td>
                </tr>
                <tr>
                    <td id="tj_btn" colspan="4">
                        <input name="noButton"  type="button" id="belongprint" height="20px" width="40px" value="检索" class="bc_out1" onmousemove="this.className='bc_on1'" onmouseout="this.className='bc_out1'" onclick="saveIamgeOther1()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input name="noButton"  type="button" id="belongphoto"value="关闭" class="bc_out2" onmousemove="this.className='bc_on2'" onmouseout="this.className='bc_out2'" onclick="closePage2();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </div>
</div>

    <form action="1v1Compare.do" id="form1v1" name="form1v1">
        <input id="path1" name="path1" type="hidden">
        <input id="path2" name="path2" type="hidden">
    </form>

</body>

</html>
