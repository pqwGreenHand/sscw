<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!--关联案件弹框 -->
<div id="link_case_dialog" class="m-popup m-info-popup" style="z-index: 15">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 1450px;height: 650px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>添加/修改案件</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#link_case_dialog').hide();"></a>
            </div>
            <div id="linkCaseDiv1"  class="bd" style="margin-top: 50px;width: 98%;height: 80%" align="center">
                <table id="link_case_datagrid"></table>
                <!-- toolbar -->
                <div id="caseToolbar" style="padding:5px;height:auto">
                    <div>
                        <a  href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton" onclick="caseAdd()">添加</a>
                        案件名称: <input name="case_name" id="caseName" class="easyui-textbox" style="width:100px;height: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        主办单位: <select style="width:150px;height: 30px" editable="true" id="org_cmb"
                                      name="org_cmb" class="easyui-combobox">
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchLinkCase()">查询</a>
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearLinkCase()">清除</a>
                    </div>
                </div>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="linkCaseSave()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#link_case_dialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
<!--添加案件弹框-->
<div id="add_case_dialog" class="m-popup m-info-popup" style="z-index: 20">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 750px; height: 550px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>添加/修改案件</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#add_case_dialog').hide();"></a>
                <a style="margin-left: 50px" href="#" class="easyui-linkbutton" iconCls="icon-add" name="noButton" onclick="linkJzAjxx()">关联警综案件</a>
            </div>
            <div id="relateDiv1" class="bd" style="margin-top: 50px;width: 98%;height:82%" align="center">
                <form id="form_case_add" enctype="multipart/form-data" action="ashx/login.ashx" method="post" style="height: 80%;overflow: auto">
                    <table class="main_form1"  style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 50px">
                        <input id="linkCaseId" name="id" type="hidden">
                        <input id="jzAjxx_ajmc" name="ajmc" type="hidden">
                        <input id="jzAjxx_ajbh" name="ajbh" type="hidden">
                        <input id="jzAjxx_ajzt" name="ajzt" type="hidden">
                        <tr>
                            <td><label>主办民警：</label></td>
                            <td>
                                <input id="zbmjCertificateNo" name="zbmjCertificateNo" class="easyui-validatebox" required="true"
                                       style="width: 150px"  onblur="checkUserId(this)" onfocus="if(this.value == '请输入警号') this.value = ''">
                                <input id="zbmjId" name="zbmjId" type="hidden">
                                <input id="zbmjName" name="zbmjName" type="hidden">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td colspan="2">
                                <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="addXbmj()">添加协办民警</a>
                            </td>
                        </tr>
                        <tr  style="height: 200px">
                            <td><label>协办民警：</label></td>
                            <td colspan="3"  style="height: 200px">
<%--                                <input id="xbmjCertificateNo" name="xbmjCertificateNo" class="easyui-validatebox" required="true"--%>
<%--                                       style="width: 150px" >--%>
<%--                                <input id="xbmjId" name="xbmjId" type="hidden">--%>
                                <table id="xbmjTable" style="height: 200px"> </table>
                            </td>
                        </tr>
                        <tr>
                            <td><label>案件类型：</label></td>
                            <td>
                                <select id="ajlx"	name="ajlx" style="width: 160px" class="easyui-combobox" >
                                </select>
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>案由：</label></td>
                            <td>
                                <input id="ab" name="ab" class="easyui-combobox" style="width: 160px" required="true">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                        </tr>
                        <tr>
                            <td><label>案发时间：</label></td>
                            <td>
                                <input id="afsj" name="afsj" class="easyui-datetimebox" style="width: 160px;height: 25px;" >
                            </td>
                            <td><label>警情号：</label></td>
                            <td>
                                <input id="jqh" name="jqh" class="easyui-validatebox"style="width: 150px" >
                            </td>
                        </tr>

                        <tr>
                            <td><label>主办单位：</label></td>
                            <td>
                                <input id="zbdwId" name="zbdwId" class="easyui-combobox" style="width: 160px" required="true">
                                <font color="red" style="size: 15px">&nbsp;*</font>
                            </td>
                            <td><label>主要案情：</label></td>
                            <td>
                                <input id="zyaq" name="zyaq" class="easyui-validatebox" style="width: 150px" >
                            </td>
                        </tr>
                        <tr>
                            <td><label >案发地点：<font color="red" style="size: 15px">&nbsp;*</font></label></td>
                            <td>
                                <input class="easyui-combobox" id="province"   style="width:160px;" panelHeight='150'>
                            </td>
                            <td><label ></label></td>
                            <td>
                                <input class="easyui-combobox" id="city"  style="width:160px;" panelHeight='150'>
                            </td>
                        </tr>
                        <tr>
                            <td><label></label></td>
                            <td>
                                <input class="easyui-combobox" id="district"   style="width:160px;" panelHeight='150'>
                            </td>
                            <td><label></label></td>
                            <td>
                                <input class="easyui-combobox" id="street"  style="width:160px;" panelHeight='150'>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="3">
                                <input type="text" id="involvedAddress" class="easyui-validatebox"  name="involvedAddress" style="width:470px;height:50px"/>
                            </td>
                        </tr>
                        </thead>
                    </table>
                </form>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="saveCase()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存并选中</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#add_case_dialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
<!-- 添加案件弹框结束-->
<!--添加协办民警弹框-->
<div id="addXbmjDialog" class="m-popup m-info-popup" style="z-index: 21">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width:950px;height: 500px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>添加/修改协办民警</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#addXbmjDialog').hide();"></a>
            </div>
            <div id="addXbmjDiv1"  class="bd" style="margin-top: 50px;height: 75%" align="center">
                <table id="addXbmjTable"></table>
                <!-- toolbar -->
                <div id="addXbmjToolbar" style="padding:5px;height:auto;color: white;">
                    <div>
                        民警名称: <input name="case_name" id="xb_name" class="easyui-validatebox" style="width:100px;height: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        民警警号: <input name="case_name" id="xb_certificateNo" class="easyui-validatebox" style="width:100px;height: 30px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        主办单位: <select style="width:120px;height: 30px" editable="true" id="xb_org_cmb"
                                      name="org_cmb" class="easyui-combobox">
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchXbmj()">查询</a>
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearXbmj()">清除</a>
                    </div>
                </div>
                <div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="addXbmjSave()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">选择</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#addXbmjDialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
<!--添加协办民警弹框结束-->
<script type="text/javascript">
    var caseAjlyCode = "";
    function loadLinkCaseList() {
        $('#link_case_datagrid').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '100%',
            height: '80%',
            fitColumns: true,
            striped : true,
            nowrap: false,
            collapsible: false,
            loadMsg: '加载中.....',
            method: 'get',
            url: '../../common/case/listCase.do',
            queryParams: {
                trefresh: new Date().getTime()
            },
            sortName: 'id',
            sortOrder: 'id',
            remoteSort: false,
            idField: 'id',
            singleSelect: true,
            frozenColumns: [[
                {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
            ]],
            columns: [[
                {field: 'ajmc', title: '案件名称', width: 19},
                {field: 'zbdwName', title: '主办单位', width: 10},
                {field: 'afsj', title: '案发时间', width: 12,
                    formatter : function(value, rec) {
                        return valueToDate(value);
                    }
                 },
                {field: 'ajlxDesc', title: '案件类型',width: 6 },
                {field: 'caseNature', title: '案件性质', width: 13},
                {field: 'zbmjName', title: '主办民警', width: 10},
                {field: 'xbmjXm', title: '协办民警', width: 10,formatter:function(value,row,index){
                        if(value != null && value != '' && value.indexOf(",")>=0){
                            value = value.substring(1,value.length);
                            return value;
                        }
                 }},
                {
                    field: 'id',
                    title: '操作',
                    width: 10,
                    align: 'center',
                    formatter: function (value, row, index) {
                        var e = "";
                        var f = "";
                        if (isGridButton("linkCaseRemove")) {
                             e = '<span class="spanRow"><a href="#" class="griddel" id="orderRequestPersonRemove" onclick="linkCaseRemove('
                                + index
                                + ')">删除</a></span>';
                        }
                        if (isGridButton("linkCaseEdit")) {
                            f = '<span class="spanRow"><a href="#" class="gridedit" id="orderRequestPersonEdit" onclick="linkCaseEdit('
                                + index
                                + ')">修改</a></span>';
                        }
                        return e + f;
                    }
                }
            ]],
            pagination: true,
            onLoadSuccess: function(data){

            },
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: true,
            toolbar: '#caseToolbar',
            onDblClickRow:function(row,index){
                linkCaseSave();
            }
        });
        var caseP = $('#link_case_datagrid').datagrid('getPager');
        $(caseP).pagination({
            onBeforeRefresh: function () {
                $('#link_case_datagrid').datagrid('load', {
                    trefresh: new Date().getTime()
                });
                return false;
            }
        });
        $("#org_cmb").combobox({
            url : '../../common/combobox/listAllOrganizationNameCombobox.do',
            valueField : 'id',
            textField : 'value',
        });
    }
    //案件新增
    function caseAdd(){
        $("#add_case_dialog").show();
        $('#form_case_add').form('clear');
        $("#zbmjId").val("");
        loadAjlx('','');
        loadPersonalConfig();
        loadZbdw();
        $("#saveLinkCase").show();
        $("#editLinkCase").hide();
        $("#ajlx").combobox("clear");
        initXbmjTable();
        $('#xbmjTable').datagrid('loadData',{rows:[]}) ;
        xbmjData = {};
        xbmjS = "";
        xbmjjhs = "";
        xbmjxms = "";
        //$("#zbmjCertificateNo").val("请输入警号");
    }
    //加载案由
    function loadAb(natureValue,caseNatureId){
        var natureValue2 = $("#ajlx").combobox("getText");
        if(natureValue2 != null && natureValue2 != ''){
            if(natureValue2 != natureValue){
                natureValue = natureValue2;
            }
        }
        //加载犯罪类型
        $('#ab').combobox({
            url : '../../bacs/combobox/listcrimetypebynature.do?nature=' +encodeURI(natureValue,"UTF-8")+'&tresh='+new Date().getTime(),
            valueField : 'id',
            textField : 'value',
            onLoadSuccess:function(data){
                $('#ab').combobox('setValue',caseNatureId);
            }
        })
    }
    //保存新增案件
    function saveCase(){
        //保存修改案件
        if($("#linkCaseId").val() != null && $("#linkCaseId").val() != ""){
            saveEditCase();
            return;
        }
        if (!checkForm('#form_case_add')) {
            return;
        }
        if($("#zbmjCertificateNo").val() == "" ){
            $.messager.alert('提示', '主办民警警号不正确或者为空!');
            $("#zbmjCertificateNo").val("");
            return;
        }
        var address = "";
        if($('#province').combobox('getValue')!=null&&$('#province').combobox('getValue')!=""){
            address+=$('#province').combobox('getText');
        }
        if($('#city').combobox('getValue')!=null&&$('#city').combobox('getValue')!="") {
            var cityName=$('#city').combobox('getText');
            if(cityName.indexOf('北京')<0&&cityName.indexOf('天津')<0&&
                cityName.indexOf('上海')<0&&cityName.indexOf('重庆')<0){
                address+=cityName;
            }
        }
        if($('#district').combobox('getValue')!=null&&$('#district').combobox('getValue')!="") {
            address+=$('#district').combobox('getText');
        }
        if($('#street').combobox('getValue')!=null &&$('#street').combobox('getValue')!=""){
            address+=$('#street').combobox('getText');
        }
        address += $('#involvedAddress').val();
        var addressCode = $('#province').combobox('getValue') + "*" +$('#city').combobox('getValue') + "*"+ $('#district').combobox('getValue') + "*"+$('#street').combobox('getValue');
        var CaseForm = $('#form_case_add').serializeObject();
        CaseForm["involvedReasonText"]=$('#ab').combobox('getText');
        CaseForm["afdd"] = address;
        CaseForm["afddCode"] = addressCode;
        if(jzAjlyCode != 0){
            CaseForm["ajly"] = jzAjlyCode;
        } else {
            CaseForm["ajly"] = caseAjlyCode;
        }
        CaseForm["xbmjIds"] = xbmjS;
        CaseForm["abmc"] = $("#ab").combobox("getText");
        CaseForm["zbmjjh"] = $("#zbmjCertificateNo").val();
        CaseForm["zbdwmc"] = $("#zbdwId").combobox("getText");
        CaseForm["xbmjXm"] = xbmjxms;
        CaseForm["xbmjjh"] = xbmjjhs;
        CaseForm["zbmjxm"] = $("#zbmjName").val();
        CaseForm["ajmc"] = $("#jzAjxx_ajmc").val();
        CaseForm["ajbh"] = $("#jzAjxx_ajbh").val();
        CaseForm["ajzt"] = $("#jzAjxx_ajzt").val();

        var CaseFormJson = JSON.stringify(CaseForm);
        $.messager.progress({
            title : '请等待',
            msg : '添加/修改数据中...'
        });
        jQuery.ajax({
            type : 'POST',
            url : "../../common/case/addCase.do",
            data :{
                form:CaseFormJson
            },
            dataType : 'json',
            success : function(data) {
                $.messager.progress('close');
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
                $('#add_case_dialog').hide();
                $("#link_case_datagrid").datagrid("clearSelections");
                $("#link_case_datagrid").datagrid("reload",{trefresh:new Date().getTime(),ajmc: $("#caseName").textbox("getValue"),zbdwId:$("#org_cmb").combobox("getValue")});
                $("#link_case_datagrid").datagrid({
                    onLoadSuccess: function(returnData){
                        $("#link_case_datagrid").datagrid("selectRecord",data.callbackData);
                        //所有调用这个弹框的页面必须有一个function
                        // linkCaseSave();
                    }
                })
            },
            error : function(data) {
                $.messager.progress('close');
                $.messager.alert('Error', 'Unknown Error!' + data);
            }
        });
    }
    //保存修改案件
    function saveEditCase(){
        if (!checkForm('#form_case_add')) {
            return;
        }
        if($('#involvedAddress').val()=="") {
            $.messager.alert("警告","请填写案发地址");
            return;
        }
        if($("#zbmjCertificateNo").val() == "" ){
            $.messager.alert('提示', '主办民警警号不正确或者为空!');
            $("#zbmjCertificateNo").val("");
            return;
        }
        var address = "";
        if($('#province').combobox('getValue')!=null&&$('#province').combobox('getValue')!=""){
            address+=$('#province').combobox('getText');
        }
        if($('#city').combobox('getValue')!=null&&$('#city').combobox('getValue')!="") {
            var cityName=$('#city').combobox('getText');
            if(cityName.indexOf('北京')<0&&cityName.indexOf('天津')<0&&
                cityName.indexOf('上海')<0&&cityName.indexOf('重庆')<0){
                address+=cityName;
            }
        }
        if($('#district').combobox('getValue')!=null&&$('#district').combobox('getValue')!="") {
            address+=$('#district').combobox('getText');
        }
        if($('#street').combobox('getValue')!=null &&$('#street').combobox('getValue')!=""){
            address+=$('#street').combobox('getText');
        }
        address += $('#involvedAddress').val();
        var addressCode = $('#province').combobox('getValue') + "*" +$('#city').combobox('getValue') + "*"+ $('#district').combobox('getValue') + "*"+$('#street').combobox('getValue');
        var CaseForm = $('#form_case_add').serializeObject();
        CaseForm["involvedReasonText"]=$('#ab').combobox('getText');
        CaseForm["afdd"] = address;
        CaseForm["afddCode"] = addressCode;
        CaseForm["xbmjIds"] = xbmjS;
        CaseForm["abmc"] = $("#ab").combobox("getText");
        CaseForm["zbmjjh"] = $("#zbmjCertificateNo").val();
        CaseForm["zbdwmc"] = $("#zbdwId").combobox("getText");
        CaseForm["xbmjXm"] = xbmjxms;
        CaseForm["xbmjjh"] = xbmjjhs;
        CaseForm["zbmjxm"] = $("#zbmjName").val();
        var CaseFormJson = JSON.stringify(CaseForm);
        $.messager.progress({
            title : '请等待',
            msg : '添加/修改数据中...'
        });
        jQuery.ajax({
            type : 'POST',
            url : "../../common/case/editCase.do",
            data :{
                form:CaseFormJson
            },
            dataType : 'json',
            success : function(data) {
                $.messager.progress('close');
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
                $('#add_case_dialog').hide();
                $("#link_case_datagrid").datagrid("clearSelections");
                $("#link_case_datagrid").datagrid("reload",{trefresh:new Date().getTime(),ajmc: $("#caseName").textbox("getValue"),zbdwId:$("#org_cmb").combobox("getValue")});
            },
            error : function(data) {
                $.messager.progress('close');
                $.messager.alert('Error', 'Unknown Error!' + data);
            }
        });
    }
    //校验输入的警号
    function checkUserId(obj){
        if(obj.value == null || obj.value == ""){
            return;
        }
        var userNo = obj.value;
        if(userNo.indexOf('(') >= 0){
            userNo = userNo.substring(0,userNo.indexOf('('));
        }
        var zbmj = $("#zbmjCertificateNo").val();
        var userNoInfo = $('#userNoInfo').serializeObject();
        userNoInfo["userNo"] = userNo;
        userNoInfo['tresh'] = new Date().getTime();
        var jsonrtinfo = JSON.stringify(userNoInfo);
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            dataType : 'json',
            url : '../../bacs/order/searchUser.do',
            data : jsonrtinfo,
            success : function(data) {
                if (data != null && data.id != null) {
                    if(xbmjS != null && xbmjS != ''
                        && (xbmjS.indexOf(data.id+",") >= 0 || xbmjS.indexOf(","+ data.id) >= 0 || xbmjS == data.id )){
                        $.messager.alert('提示', '主办民警与协办民警不能为同一个人!');
                        obj.value ="";
                        $("#zbmjId").val("");
                        $("#zbmjName").val("");
                        return false;
                    }
                    console.log(data);
                    $("#zbdwId").combobox("setValue",data.organizationId);
                    $("#zbmjId").val(data.id);
                    $("#zbmjName").val(data.realName);
                    //obj.value = obj.value +'('+data.realName+')';
                    $("#zbmjCertificateNo").val(userNo+"("+data.realName+")");
                   return true;
                } else {
                    $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
                    $("#zbmjId").val("");
                    $("#zbmjName").val("");
                    return false;
                }
            },
            error : function(data) {
                $.messager.alert('错误', '警号不存在，请找系统管理员维护！');
                $("#zbmjId").val("");
                $("#zbmjName").val("");
                return false;
            }
        });
    }
    //加载个性化配置
    function loadPersonalConfig(){
        var arr;
        jQuery.ajax({
            type : 'get',
            async:false,
            dataType : 'json',
            url : '../../bacs/personalconfig/listConfigDetailByAreaAndName.do?name='+encodeURIComponent('案发地址绑定'),
            success : function(data){
                if(data!=null&&data.error==false&&data.callbackData!=null){

                    if(data.callbackData["regionids"]!="" && data.callbackData["regionids"] != null){
                        arr=data.callbackData["regionids"].split('*');
                    }
                }
                BindAddress(arr);
            }
        });
    }
    //加载省市县村三级联动
    function BindAddress(arr){
        var province = $('#province').combobox({
            url : '../../common/region/list.do?level=1&trefresh='+new Date().getTime(),
            valueField : 'code',
            textField : 'name',
            onChange : function(newValue, oldValue) {
                //城市
                jQuery.ajax({
                    type : 'get',
                    dataType : 'json',
                    url :'../../common/region/list.do?level=2&parentId='+newValue+"&trefresh="+new Date().getTime(),
                    success : function(data){
                        city.combobox("clear").combobox('loadData', data);
                        district.combobox("clear");
                        street.combobox("clear");
                    }
                });
            },
            onLoadSuccess :function(){
                if(arr!=null && arr.length>0 && arr[0] != ""){
                    $('#province').combobox('setValue',arr[0]);
                }else {
                    var provinceData = $("#province").combobox("getData");
                    if(provinceData != null && provinceData .length >0){
                        $('#province').combobox('setValue', '11');
                    } else{
                        $('#province').combobox('setText',"省");
                    }
                }
            }
        });
        var city = $('#city').combobox({
            valueField : 'code', //值字段
            textField : 'name', //显示的字段
            editable : true,
            onChange : function(newValue, oldValue) {
                console.log(newValue+"+++"+ oldValue);
                if (newValue != "" ) {
                    //区县
                    $.get('../../common/region/list.do', {
                        level:3,
                        trefresh:new Date().getTime(),
                        parentId : newValue
                    }, function(data) {
                        district.combobox("clear").combobox('loadData', data);
                        street.combobox("clear");
                    }, 'json');
                }
            },
            onLoadSuccess :function(){
                if(arr!=null && arr.length>1 && arr[1] != ""){
                    $('#city').combobox('setValue',arr[1]);
                } else{
                    var cityData = $("#city").combobox("getData");
                    if(cityData != null && cityData .length >0){
                        $('#city').combobox('setValue', '1101');
                    } else{
                        $('#city').combobox('setText',"城市");
                    }
                }
            }
        });
        var district = $('#district').combobox({
            valueField : 'code', //值字段
            textField : 'name', //显示的字段
            editable : true,
            onChange : function(newValue, oldValue) {
                if (newValue != "") {
                    $.get('../../common/region/list.do', {
                        level:4,
                        trefresh:new Date().getTime(),
                        parentId : newValue
                    }, function(data) {
                        street.combobox("clear").combobox('loadData', data);
                    }, 'json');
                }
            },
            onLoadSuccess :function(){
                if(arr!=null && arr.length>2 && arr[2] != ""){
                    $('#district').combobox('setValue',arr[2]);
                }else{
                    var districtData = $("#district").combobox("getData");
                    if(districtData != null && districtData .length >0){
                        //海淀区
                        $('#district').combobox('setValue', '110108');
                    } else{
                        $('#district').combobox('setText',"区县");
                    }
                }
            }
        });
        var street = $('#street').combobox({
            valueField : 'code', //值字段
            textField : 'name', //显示的字段
            editable : true,
            onLoadSuccess :function(){
                if(arr!=null && arr.length>3 && arr[3] != ""){
                    $('#street').combobox('setValue',arr[3]);
                } else{
                    var streetData = $("#street").combobox("getData");
                    if(streetData != null && streetData .length >0){
                        $('#street').combobox('setValue', streetData[0].code);
                    } else{
                        $('#street').combobox('setText',"街道/乡镇");
                    }
                }
            }
        });
    }
    //加载主办单位
    function loadZbdw(zbdwId){
        $("#zbdwId").combobox({
            url : '../../common/combobox/listAllOrganizationNameCombobox.do',
            valueField : 'id',
            textField : 'value',
            onLoadSuccess:function(){
                if(zbdwId != null){
                    $("#zbdwId").combobox("setValue",zbdwId);
                }
            }
        });
    }
    //查询案件
    function doSearchLinkCase(){
        $("#link_case_datagrid").datagrid("reload",{trefresh:new Date().getTime(),ajmc: $("#caseName").textbox("getValue"),zbdwId:$("#org_cmb").combobox("getValue")});
    }
    //清除查询条件
    function doClearLinkCase(){
        $("#caseName").textbox("setValue",'');
        $("#org_cmb").combobox("setValue","");
    }
    //删除案件
    function linkCaseRemove(index){
        var rowData = $('#link_case_datagrid').datagrid('getRows')[index];
        var value = rowData.id;
        $.messager.confirm('Confirm', '确定删除?', function(r) {
            if (r) {
                $.messager.confirm({
                    width: 380,
                    height: 160,
                    title: '警告',
                    msg: '案件关联数据较多，确定删除?',
                    fn: function (m) {
                        if(m) {
                            $.messager.progress({
                                title: '请等待',
                                msg: '删除数据中...'
                            });
                            jQuery.ajax({
                                type: 'POST',
                                url: '../../common/case/removeCase.do',
                                data: {
                                    "caseId": value,
                                },
                                dataType: 'json',
                                success: function (data) {
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.content,
                                        timeout: 3000
                                    });
                                    $.messager.progress('close');
                                    doSearchLinkCase();
                                },
                                error: function (data) {
                                    $.messager.progress('close');
                                    $.messager.alert('错误', '未知错误!');
                                }
                            });
                        }
                    }
                 })
            }
        })
    }
    //打开修改弹框
    function linkCaseEdit(index){
        // showDialog('#add_case_dialog','编辑案件');
        $("#add_case_dialog").show();
        $("#saveLinkCase").hide();
        $("#editLinkCase").show();
        var rowData = $('#link_case_datagrid').datagrid('getRows')[index];
        $('#form_case_add').form('clear');
        var afdd = rowData.afdd;
        var arr;
        if(rowData.afddCode != null && rowData.afddCode != ""){
            arr = rowData.afddCode.split('*');
        }
        BindAddress(arr);
        var afdd = rowData.afdd;
        var involvedAddress = "";
        var flagIndex = -1;
        $('#form_case_add').form("load",rowData);
        if(arr != null && arr.length >0){
            for(var i=0;i<arr.length;i++){
                if(arr[i] == null || arr[i] == ''){
                    flagIndex = i;
                    break;
                }
            }
            if(flagIndex == 0){
                $("#involvedAddress").val(afdd);
            }else {
                if(flagIndex == -1 ){
                    flagIndex = 4;
                }
                jQuery.ajax({
                    type : 'POST',
                    contentType : 'application/json',
                    dataType : 'json',
                    url : '../../common/region/selectByCode.do?code='+arr[flagIndex -1],
                    success : function(data) {
                        involvedAddress =afdd.substring(afdd.indexOf(data.name) + data.name.length ,afdd.length);
                        $("#involvedAddress").val(involvedAddress);
                    },
                    error : function(data) {
                        $.messager.alert('错误', '数据错误');
                    }
                });
            }
        } else {
            $("#involvedAddress").val(afdd);
        }
        loadZbdw(rowData.zbdwId);
        loadAjlx(rowData.ajlx,rowData.ab);
        xbmjData = {};
        xbmjS = rowData.xbmjIds;
        initXbmjTable();
        jQuery.ajax({
            type : 'POST',
            url : "../../common/case/listXbPolice.do",
            data :{
                xbmjIds:xbmjS,
            },
            dataType : 'json',
            success : function(data) {
                $('#xbmjTable').datagrid('loadData',data);
                xbmjData = data;
            },
            error : function(data) {
                $.messager.alert('Error', 'Unknown Error!' + data);
                $('#xbmjTable').datagrid('loadData',xbmjData);
            }
        });
    }

    function initAddXbmjTable(){
        $('#addXbmjTable').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '80%',
            height: '80%',
            fitColumns: true,
            striped: true,
            nowrap: false,
            collapsible: false,
            loadMsg: '加载中.....',
            method: 'get',
            url: '../../common/case/listPolice.do',
            queryParams: {
                trefresh: new Date().getTime()
            },
            sortName: 'id',
            sortOrder: 'id',
            remoteSort: false,
            showFooter:false,
            idField: 'id',
            singleSelect: false,
            frozenColumns: [[
                {field:'ck',checkbox:true},
                {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
            ]],
            columns: [[
                {field: 'realName', title: '姓名', width: 10},
                {field: 'certificateNo', title: '警号', width: 10},
                {field: 'organizationName', title: '单位', width: 10 }
            ]],
            pagination: true,
            pageList: [10, 20, 30, 40, 50, 100],
            toolbar:"#addXbmjToolbar",
            onLoadSuccess: function (data) {
                $("#addXbmjTable").datagrid('unselectAll');
                if(xbmjData != ''){
                    for(var i = 0;i< xbmjData.length;i++){
                        var index = $("#addXbmjTable").datagrid("getRowIndex",xbmjData[i].id);
                        if(index != null && index != -1){
                            $("#addXbmjTable").datagrid('selectRow',index);
                        }
                    }
                }
            },
            onSelect:function(rowIndex, rowData){
                var zbmjId = $("#zbmjId").val();
                if(zbmjId != null && zbmjId != ''){
                    if(rowData.id == zbmjId){
                        $.messager.alert('提示',"主办民警与协办民警不能为同一个人");
                        $("#addXbmjTable").datagrid('unselectRow',rowIndex);
                    }
                }
            }

        })
    }
    //根据用户Id取消协办民警选中
    function cancleXbmjSelectById(id){
        var index = $("#addXbmjTable").datagrid("getRowIndex",id);
        if(index != null && index != -1){
            $("#addXbmjTable").datagrid('unselectRow',index);
        }
    }
    function initXbmjTable(){
        $('#xbmjTable').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '80%',
            height: '80%',
            fitColumns: true,
            striped: true,
            nowrap: false,
            collapsible: false,
            loadMsg: '加载中.....',
            sortName: 'id',
            sortOrder: 'id',
            remoteSort: false,
            showFooter:false,
            idField: 'id',
            singleSelect: false,
            frozenColumns: [[
                {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
            ]],
            columns: [[
                {field: 'realName', title: '姓名', width: 10},
                {field: 'certificateNo', title: '警号', width: 10},
                {field: 'organizationName', title: '单位', width: 10 },
                {
                    field: 'id',
                    title: '操作',
                    width: 10,
                    align: 'center',
                    formatter: function (value, row, index) {
                           return '<span class="spanRow"><a href="#" class="griddel" onclick="xbmjDelete('+ index+ ')">删除</a></span>';
                    }
                }
            ]],
            rownumbers: true
        });
    }

    function loadAllOrg(){
        $("#xb_org_cmb").combobox({
            url : '../combobox/listAllOrganizationNameComboboxWithNo.do',
            valueField : 'id',
            textField : 'value',
        });
    }
    function addXbmj(){
        $("#addXbmjDialog").show();
        initAddXbmjTable();
        loadAllOrg();
    }

    function doSearchXbmj(){
        $("#addXbmjTable").datagrid("reload",{
            real_name_t : $('#xb_name').val(),
            certificate_no_t : $('#xb_certificateNo').val(),
            organization_name_t:$('#xb_org_cmb').combobox("getText"),
            trefresh:new Date().getTime()
        });
    }
    function doClearXbmj(){
       $('#xb_name').val("");
       $('#xb_certificateNo').val("");
       $('#xb_org_cmb').combobox("setValue","");
    }
    //保存协办民警数据
    var xbmjData = {};
    var xbmjS = "";
    var xbmjjhs = "";
    var xbmjxms = "";
    function addXbmjSave(){
        xbmjData = $("#addXbmjTable").datagrid("getSelections");
        $("#xbmjTable").datagrid({
            data:xbmjData
        });
        $("#addXbmjDialog").hide();
        if(xbmjData != null){
            xbmjS = ",";
            xbmjjhs = ",";
            xbmjxms = ",";
            for(var i = 0;i< xbmjData.length;i++){
                    xbmjS += xbmjData[i].id +",";
                    xbmjjhs += xbmjData[i].certificateNo +",";
                    xbmjxms += xbmjData[i].realName +",";
            }
        }
    }

    function xbmjDelete(index){
        var rowData = $("#xbmjTable").datagrid("getRows")[index];
        $("#xbmjTable").datagrid("deleteRow",index);
        cancleXbmjSelectById(rowData.id);
        if($("#xbmjTable").datagrid("getData") != null){
            xbmjData = $("#xbmjTable").datagrid("getData").rows;
            if(xbmjData != null){
                xbmjS = ",";
                xbmjjhs = ",";
                xbmjxms = ",";
                for(var i = 0;i< xbmjData.length;i++){
                    xbmjS += xbmjData[i].id +",";
                    xbmjjhs += xbmjData[i].certificateNo +",";
                    xbmjxms += xbmjData[i].realName +",";

                }
            }
        }
    }

    function loadAjlx(ajlx,ab){
        $('#ajlx').combobox({
            url:contextPath+"/zhfz/common/code/listCodeByType.do?type=AJLX&tresh="+new Date().getTime(),
            valueField:'codeKey',
            textField:'codeValue',
            editable: false,
            filter: function(q, row){
                var opts = $(this).combobox('options');
                //return row[opts.textField].indexOf(q) == 0;
                return row.codeValue.indexOf(q)>-1;//将从头位置匹配改为任意匹配
            },
            onLoadSuccess : function() {
                $('#ajlx').combobox('setValue', ajlx);
                if(ab != null && ab != ''){
                    loadAb(ajlx,ab);
                }
            },
            onChange:function(newValue,oldValue){
                loadAb(newValue,"");
            }
        });
    }
</script>