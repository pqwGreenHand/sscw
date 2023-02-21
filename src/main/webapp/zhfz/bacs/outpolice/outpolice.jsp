<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@ include file="../../common/common-head.jsp"%>
    <script type="text/javascript" src="outpolice.js"></script>

</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<%@ include file="../../common/common-loading.jsp"%>


<div data-options="region:'center',split:true">
    <table id="user_datagrid"></table>
</div>
<!-- toolbar -->
<div id="toolbar" style="padding: 5px; height: auto">
    <div style="margin-bottom:5px">
        <div class="box" id="tj_btn" style="margin-bottom: 5px; width: 100%; height: 90px; margin-top: 15px" align="center">
            <a  onclick="addInfo()" id="addInfo" name="addInfo" class="myButton i1 m-box"  iconCls="icon-add">
                <span>添加民警</span>
                <div class="edges">
                    </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
                </div>
            </a>
        </div>
    </div>
    <div>
        <label>办案单位:</label>
        <input id="organizationId1" name="organizationId" class="easyui-combobox" style="width: 180px">
        <label>姓名:</label>
        <input id="s_real_name" class="easyui-textbox" style="width: 180px">
        <label>警号:</label>
        <input id="s_certificate_no" class="easyui-textbox" style="width: 180px">
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()">搜索</a>
        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClear()">清空</a>
    </div>
</div>

<div id="user_dialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box">
        <div class="popup-inner">
            <div class="title">
                <div><i style="background-image: url(../otherPersonEntrance/static/popup-2.png);"></i><span>新增/修改民警</span></div>
                <a name="noButton" class="close" onclick="$('#user_dialog').hide()"></a>
            </div>
            <div id="hd" class="hd">
                <div class="steps">
                    <div id="step1" class="i1 item m-box now">
                        <div class="inner">
                            <i></i>
                        </div>
                        <span>基本信息</span>
                        <div class="edges">
                            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                        </div>
                    </div>
                </div>
            </div>
            <div id="stepDiv1"  class="bd">
                <form id="user_form" method="post">
                    <input type="hidden" id="id" name="id" />
                    <table class="user_tab" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 140px;margin-top: 12px;">
                        <tr>
                            <td><label >姓名：</label></td>
                            <td>
                                <input type="text" id="realName" name="realName" class="easyui-validatebox" style= "font: 14px arial; padding:3px;width:180px;"
                                       data-options="validType:'maxLength[16]',invalidMessage:'最长16字节'" required="true"/>
                                <font color="red">*</font>
                            </td>
                        </tr>
                        <tr>
                            <td><label >性别：</label></td>
                            <td>
                                <select id="sex" name="sex" style = "margin:-2px;width:188px; height:28px" class="easyui-combobox"   required="true">
                                    <option value="1">男</option>
                                    <option value="2">女</option>
                                    <option value="0">未知的性别</option>
                                    <option value="9">未说明的性别</option>
                                </select>
                                <font color="red">*</font>
                            </td>
                        </tr>
                        <tr>
                            <td><label >办案单位：</label></td>
                            <td>
                                <select id="organizationId" name="organizationId" style = "margin:-2px;width:188px; height:28px"  class="easyui-combobox"   required="true">

                                </select>
                                <font color="red">*</font>
                                <a href="#" class="easyui-linkbutton" name="noButton" style= "font: 14px arial; padding:3px;margin-left:5px;width:100px;" iconCls="icon-add" onclick="addOrg()">添加部门</a>
                            </td>
                        </tr>
                        <tr>
                            <td><label >警号：</label></td>
                            <td>
                                <input id="certificateNo" name="certificateNo" type="text" class="easyui-validatebox"
                                       style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="true"/>
                                <font color="red">*</font>
                            </td>
                        </tr>
                        <tr>
                            <td><label >手机号：</label></td>
                            <td>
                                <input id="mobile" name="mobile" type="text" class="easyui-validatebox"
                                       style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label >邮箱：</label></td>
                            <td>
                                <input id="email" name="email" type="text" class="easyui-validatebox"
                                       style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label >描述：</label></td>
                            <td>
                                <input id="description" name="description" type="text" class="easyui-validatebox"
                                       style= "font: 14px arial; padding:3px;width:180px;" data-options="validType:'maxLength[18]',invalidMessage:'最长18字节'" required="false"/>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="next">
                    <button class="m-btn-1 blue" onclick="saveData()">保存</button>
                    <button class="m-btn-1 blue" onclick="$('#user_dialog').hide()">取消</button>
                </div>
            </div>
        </div>
        <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>



<div id="organization_dialog" class="easyui-dialog"
     style="width: 500px; height: 450px; padding: 10px 20px" closed="true"
     buttons="#organization-buttons">
    <div class="ftitle">部门信息</div>
    <div style="height: 180px;">
        <form id="organization_form" class="form-style" method="post"
              style="height: 150px;">
            <input type="hidden" id="id1" name="id" />
            <div class="fitem">
                <label>部门名称:</label> <input id="name" name="name" class="easyui-validatebox"
                                            data-options="required:true,validType:'maxLength[64]',invalidMessage:'最长64字节'">
                <font color="red">*</font>
            </div>
            <div class="fitem">
                <label>部门地址:</label> <input id="address" name="address" class="easyui-validatebox"
                                            data-options="required:false,validType:'maxLength[256]',invalidMessage:'最长256字节'">
            </div>
            <div class="fitem">
                <label>电话:</label> <input id="telephone" name="telephone" class="easyui-validatebox"
                                          data-options="required:false,validType:'maxLength[32]',invalidMessage:'最长32字节'">
            </div>


            <div class="fitem">
                <label>邮编:</label> <input id="postcode" name="postcode" class="easyui-validatebox"
                                          data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'">
            </div>
            <div class="fitem">
                <label>机构代码:</label> <input id="orgCode" name="orgCode" class="easyui-validatebox"  data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'" >
            </div>
            <div class="fitem">
                <label>机构简称:</label> <input id="orgAlias" name="orgAlias" class="easyui-validatebox"  data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'" >
            </div>
            <div class="fitem">
                <label>机关代字:</label> <input id="orgRep" name="orgRep" class="easyui-validatebox"   data-options="required:false,validType:'maxLength[16]',invalidMessage:'最长16字节'">
            </div>
        </form>
    </div>
    <div id="organization-buttons">
        <a name="noButton" href="#" class="easyui-linkbutton" iconCls="icon-ok"
           onclick="saveOrganization()">保存</a> <a name="noButton" href="#" class="easyui-linkbutton"
                                                  iconCls="icon-cancel"
                                                  onclick="javascript:$('#organization_dialog').dialog('close')">关闭</a>
    </div>
</div>

</body>
</html>