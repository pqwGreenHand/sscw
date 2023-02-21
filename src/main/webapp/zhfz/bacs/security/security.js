var flag = 0;
// 预约信息添加start
$(function(){
    //初始化
    intlize();
    initArea();
    // 预约信息
    $('#security_datagrid').datagrid({
        iconCls : 'icon-datagrid',
        region : 'center',
        width : '100%',
        height : '80%',
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        loadMsg : '加载中...',
        method : 'get',
        url : 'list.do',
        queryParams : {
            trefresh:new Date().getTime()
        },
        remoteSort : false,
        idField : 'id',
        singleSelect : true,
        frozenColumns : [ [ {
            title : 'id',
            field : 'id',
            width : 80,
            sortable : true,
            hidden : true
        } ] ],
        columns : [ [
            {
                field : 'personName',
                title:'嫌疑人',
                align : 'center',
                width:40,
                formatter : function(value,rec) {
                    if(rec.sex==1){
                        return value+"(男)";
                    }else if(rec.sex==2){
                        return value+"(女)";
                    }else{
                        return value+"(未知性别)"
                    }
                }
            },
            {
                field : 'certificateNo',
                title : '证件号码',
                align : 'center',
                width : 90,
            },
            {
                field : 'checkedEndTime',
                title : '安检时间',
                align : 'center',
                width : 100,
                formatter : function(value) {
                    return valueToDate(value);
                }
            },
            {
                field : 'count',
                title : '安检次数',
                align : 'center',
                width : 50,
                formatter : function(value,row,index) {
                    if(row.id != null){
                        return value+"次";
                    }
                }
            },
            {
                field : 'checkUserName',
                title : '安检民警',
                align : 'center',
                width : 50
            },
            {
                field : 'ajlx',
                title : '案件类型',
                align : 'center',
                width : 40,
                formatter : function(value) {
                    if(value == 0){
                        return "刑事";
                    }else if(value==1){
                        return "行政";
                    }else if(value==2){
                        return "刑事";
                    }else{
                        return "其他";
                    }
                }
            },
            {
                field : 'caseNature',
                title : '案件性质',
                align : 'center',
                width : 80
            },
            {field:'zhuangtai',title:'是否安检',width:70 ,height:100,
                formatter:function(value,row,index){
                    if(row.id != null && row.id != 0){
                        return '已安检';
                    }else{
                        return '<span style="color: red">未安检</span>';
                    }
                }
            },
            {
                field: 'id',
                title: '操作',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '', g = "", h = '', i = '', l = '', m = '';
                    var u = '';
                    var cx = '';
                    if (row.id != null && row.id != 0) {
                        if (isGridButton("securityRepeat")) {
                            e = '<span class="spanRow"><a href="#" class="gridedit"  onclick="securityRepeat(' + index + ')">复检</a></span>';
                        }
                        if (isGridButton("securityPrint")) {
                            d = '<span class="spanRow"><a href="#" class="gridmsprint" style="color:aquamarine; "  onclick="orderRequestDelay('
                                + index
                                + ')">原始版打印</a></span>';
                        }
                        if (isGridButton("securitySignPrint")) {
                            i = '<span class="spanRow"><a href="#" class="griddownload" style="color:aquamarine; "  onclick="PdfDownLoad('
                                + index
                                + ')">签字版下载</a></span>';
                            h = '<span class="spanRow"><a href="#" class="gridmsprint" style="color:aquamarine; "  onclick="signPrint('
                                + index
                                + ')">签字版打印</a></span>';
                        }
                        if (isGridButton("securitySign")) {
                            g = '<span class="spanRow"><a href="#" class="gridedit" style="color:aquamarine; "  onclick="securitySign('
                                + index
                                + ')">签字</a></span>';
                            cx = '<span class="spanRow"><a href="#" class="gridedit" style="color:aquamarine; "  onclick="cxsecuritySign('
                                + index
                                + ')">重新签字</a></span>';
                        }
                        if (isGridButton("securityUpdate")) {
                            u = '<span class="spanRow"><a href="#" class="gridedit" style="color:aquamarine; "  onclick="securityUpdate('
                                + index
                                + ')">修改</a></span>';
                        }
                        if (isGridButton("securityUpdate")) {
                            m = '<span class="spanRow"><a href="#" class="gridedit" style="color:aquamarine; "  onclick="history('
                                + index
                                + ')">历史</a></span>';
                        }
                        } else {
                            l = '<span class="spanRow"><a href="#" class="gridedit" style="color:aquamarine; "  onclick="securityAdd()">安检</a></span>';
                        }

                        return e + d + h + i + g + l + m + u+cx;
                    }
                }

          ] ],
        pagination : true,
        pageList : [ 10, 20, 30, 40, 50, 100 ],
        rownumbers : true,
        // 行选择方法，进行条件触发
        toolbar:'#toolbar',
        onSelect : function(rowIndex, rowData) {
            console.log(rowData)
            reloadMarkersPic(rowData.serialUUID,rowData.areaId,rowData.count);
        },
        onDblClickRow : function(index) {
            var currentRow = $("#security_datagrid").datagrid("getSelected");
            $('#securityDetail').html(xqHtml(currentRow));
            showDialog('#securityDetail', '人身安检详情');
        }
    });
})

// 人身安检修改
function securityUpdate(index) {
    flag =1;
    $("#security_data_dialog").show();
    var row = $('#security_datagrid').datagrid("getRows")[index];
    $('#serialNo').combogrid('setValue', row.personName);
    $('#checkUserNo').val(row.checkUserNo);
    $('#medicalHistory').val(row.medicalHistory);
    $('#physicalDescription').val(row.physicalDescription);
    $('#dangerous').val(row.dangerous);
    $('#injuryDescription').val(row.injuryDescription);
    $('#otherDescription').val(row.otherDescription);

    if(row.hasInjury == 1){
        $("input[name='hasInjury']").prop("checked",true);
    }else {
        $("input[name='hasInjury']").prop("checked",false);
    }
    if(row.hasWine == 1){
        $("input[name='hasWine']").prop("checked",true);
    }else{
        $("input[name='hasWine']").prop("checked",false);
    }
    if(row.hasPhoto == 1){
        $("input[name='hasPhoto']").prop("checked",true);
    }else{
        $("input[name='hasPhoto']").prop("checked",false);
    }

    /*console.log(row);
    securityId*/
    $('#securityId').val(row.id);
    url:'update.do';

}


function history(index){
    showDialog('#checkHistory', '人身安检历史记录');
    var row = $("#security_datagrid").datagrid("getRows")[index];
    loadHistoryDialog(row.serialId);
}
function loadHistoryDialog(serialId){
    $("#security_history_datagrid").datagrid({
        iconCls : 'icon-datagrid',
        region : 'center',
        width : '100%',
        height : '80%',
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        loadMsg : '加载中...',
        method : 'get',
        url : 'listHistory.do',
        queryParams : {
            serialId:serialId,
            trefresh:new Date().getTime()
        },
        remoteSort : false,
        idField : 'id',
        singleSelect : true,
        frozenColumns : [ [ {
            title : 'id',
            field : 'id',
            width : 80,
            sortable : true,
            hidden : true
        } ] ],
        columns : [ [
            {
                field : 'personName',
                title:'嫌疑人',
                align : 'center',
                width:40,
                formatter : function(value,rec) {
                    if(rec.sex==1){
                        return value+"(男)";
                    }else if(rec.sex==2){
                        return value+"(女)";
                    }else{
                        return value+"(未知性别)"
                    }
                }
            },
            {
                field : 'certificateNo',
                title : '证件号码',
                align : 'center',
                width : 90,
            },
            {
                field : 'checkedEndTime',
                title : '安检时间',
                align : 'center',
                width : 100,
                formatter : function(value) {
                    return valueToDate(value);
                }
            },
            {
                field : 'count',
                title : '安检次数',
                align : 'center',
                width : 50,
                formatter : function(value,row,index) {
                    if(row.id != null){
                        return '第'+value+"次";
                    }
                }
            },
            {
                field : 'checkUserName',
                title : '安检民警',
                align : 'center',
                width : 50
            },
            {
                field : 'ajlx',
                title : '案件类型',
                align : 'center',
                width : 40,
                formatter : function(value) {
                    if(value == 0){
                        return "刑事";
                    }else if(value==1){
                        return "行政";
                    }else if(value==2){
                        return "警情";
                    }else{
                        return "其他";
                    }
                }
            },
            {
                field : 'caseNature',
                title : '案件性质',
                align : 'center',
                width : 80
            }
        ]],
        onLoadSuccess:function(data){
            console.log(data);
        },
        rownumbers : true,
        onDblClickRow : function(index) {
            var currentRow = $("#security_history_datagrid").datagrid("getSelected");
            $('#securityDetail').html(xqHtml(currentRow));
            showDialog('#securityDetail', '人身安检详情');
        }
    });
}
function securityAdd() {
    toInitialize();
    //专属编号
    $('#serialNo').combogrid({
        panelWidth: 360,
        //   mode: 'remote',
        url: '../../common/combogrid/getSuspectSerialNo.do?type=security',
        idField: 'serialNo',
        textField: 'name',
        columns: [[
            {field: 'serialNo', title: '入区编号', width: 135},
            {field: 'name', title: '姓名', width: 60},
            {field: 'certificateNo', title: '证件号码', width: 150}
        ]],
        onChange: function () {
                        var cg = $('#serialNo').combogrid('grid');	// 获取数据表格对象
                        var row = cg.datagrid('getSelected');	// 获取选择的行
                      if(row!=null){
                          $('#s_lawCaseId').val(row.caseId);
                          $('#involvedPeople').val(row.name);
                          $('#cserialNo').val(row.serialNo);
                          //$('#checkUserNo').val(row.inUserNo1);
                          //$('#checkUserId').val(row.inUserId1);
                          $('#involvedPeople').val(row.name);
                          $('#involvedDate').datetimebox('setValue',row.afsj);
                          $('#involvedReason').combobox('setValue', row.ab);
                          $('#reason').val(row.ab)
                          $('#involvedAddress').val(row.afdd);
                          $('#caseId').val(row.caseId);
                          $('#uuid').val(row.uuid);
                          $('#areaId').val(row.areaId);
                      }
                }
            });
    $('#serialNo').combogrid('readonly',false)
    document.getElementById('hasPhoto').checked = true;
    $.get("../../../common/getSessionInfo.do", function(data){
        var sessionObj = eval('('+data+')');
        var user = sessionObj.user;
        $("#checkUserNo").val(user.certificateNo +"("+ user.realName+')');
        $("#checkUserId").val(user.id);
    });
    $("#cuffNumber").focus();
}

//点击下一步
function securityNext(index) {
    $("#step2").addClass("now");
    $("#step1").addClass("active");
    $("#step1").removeClass("now");
    showdiv("#security_info_dialog");
    hiddendiv("#jibenxinxi");
}

//加载犯罪类型下拉框
function loadInvolvedReason(involvedReasonValue) {
    //清空犯罪类型
    $('#involvedReason').combobox('clear');
    var natureValue = '';
    // 加载犯罪类型
    $('#involvedReason').combobox({
        url: '../combobox/listcrimetypebynature.do?nature=' + encodeURI(natureValue, "UTF-8"),
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#involvedReason').combobox('select', '');
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == involvedReasonValue) {
                    $('#involvedReason').combobox('select', involvedReasonValue);
                }
            }
        }
    });
}

//初始化各种表单
function toInitialize(){
    $('#reason').val("")
    $('#checkedStartTime').val(new Date().getTime());
    $('#uuid').val("");
    $('#checkUserNo').val("");
    $('#checkUserId').val("");
    $("#bodyTag").val("0")
    $("#bodyPhoto").val("0")
    $("#security_data_dialog").show();
    hiddendiv("#jibenxinxi");
    showdiv("#security_info_dialog");
    //初始化案由下拉框
    loadInvolvedReason();
    $("#step2").addClass("now");
    $("#step2").addClass("active");
    $('#caseinfo').form('clear');
    $('#securityinfo').form('clear');
    document.getElementById("common").checked = "checked";
    $("#firstDiv").show();
    $("#repeatDiv").hide();
    $('#count').val("1");
    //document.getElementById("tbbjm").innerHTML = "双击图像以进行体表标记↗"
    //document.getElementById("tbbjm").style.color = "red";
}
//保存安检信息确认完成
function saveInfo(index) {
    // 修改人身检查
    if(flag == 1){
        var checkUserNo = $('#checkUserNo').val();

        var medicalHistory = $('#medicalHistory').val();

        var physicalDescription = $('#physicalDescription').val();

        var dangerous = $('#dangerous').val();

        var injuryDescription = $('#injuryDescription').val();

        var otherDescription = $('#otherDescription').val();

        var securityId = $('#securityId').val();

        var data = {"securityId":securityId,"checkUserNo":checkUserNo,"medicalHistory":medicalHistory,
            "physicalDescription":physicalDescription,"dangerous":dangerous,
            "injuryDescription":injuryDescription,"otherDescription":otherDescription}

        if ($("#hasInjury").is(':checked')) {
            data["hasInjury"] = 1;
        }else{
            data["hasInjury"] = '';
        }
        if ($("#hasWine").is(':checked')) {
            data["hasWine"] = 1;
        }else{
            data["hasWine"] = '';
        }
        if ($("#hasPhoto").is(':checked')) {
            data["hasPhoto"] = 1;
        }else{
            data["hasPhoto"] = '';
        }

        var jsonrtinfo = JSON.stringify(data);

        $.messager.progress({
            title: '请等待',
            msg: '修改数据中...'
        });

        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'update.do',
            data: jsonrtinfo,
            dataType: 'json',
            success: function (data) {
                $.messager.progress('close');
                $('#security_data_dialog').hide();
                $.messager.alert(data.title, data.content);

                $('#security_datagrid').datagrid('load',{
                    queryParams: {
                        trefresh:new Date().getTime()
                    }
                });
                console.log(data);
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert('错误', '修改数据失败!');
            }

        })
        return;
    }


    if ($("#checkUserId").val() != null && $("#checkUserId").val() != '') {
        var cg = $('#serialNo').combogrid('grid');	// 获取数据表格对象
        var row = cg.datagrid('getSelected');	// 获取选择的行
        var serialNo = row.serialNo;
        if ($("input[name='type']:checked").val() != null && $("input[name='type']:checked").val() != '' && serialNo != null && serialNo != '' && $("#checkUserNo").val() != null && $("#checkUserNo").val() != '' && $("#checkUserNo").val() != '请输入警号') {
            if(true){
                if (true){
//保存案件信息
                    var caseinfo = $('#caseinfo').serializeObject();
                    //保存安检信息
                    var securityInfo = $('#securityinfo').serializeObject();
                    if (securityInfo["physicalDescription"] == null || securityInfo["physicalDescription"] == '') {
                        securityInfo["physical"] = 0;
                    } else {
                        securityInfo["physical"] = 1;
                    }
                    if (securityInfo["injuryDescription"] == null || securityInfo["injuryDescription"] == '') {
                        securityInfo["injury"] = 0;
                    } else {
                        securityInfo["injury"] = 1;
                    }
                    var security = $('#securityinfo').serializeObject();
                    var a = caseinfo["involvedDate"].split(/[^0-9]/);
                    var d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
                    security["checkUserId"] = $("#checkUserId").val();
                    security["involvedDate"] = d.getTime();
                    security["involvedAddress"] = caseinfo["involvedAddress"];
                    security["involvedPeople"] = caseinfo["involvedPeople"];
                    security["involvedReason"] = $('#reason').val();

                    security["involvedReasonText"] = $('#involvedReason').combobox('getText');
                    security["againReason"] = caseinfo["againReason"];
                    //security["lawCaseId"] = $("#caseId").val();
                    security["serialNo"] = serialNo;
                    security["checkUserNo"] = securityInfo["checkUserNo"];
                    security["checkedEndTime"] = new Date().getTime();
                    security["checkedStartTime"] = $("#checkedStartTime").val();
                    security["type"] = $("input[name='type']:checked").val();
                    security["medicalHistory"] = securityInfo["medicalHistory"];
                    security["physical"] = securityInfo["physical"];
                    security["physicalDescription"] = securityInfo["physicalDescription"];
                    security["injury"] = securityInfo["injury"];
                    security["injuryDescription"] = securityInfo["injuryDescription"];
                    security["dangerous"] = securityInfo["dangerous"];
                    security["otherDescription"] = securityInfo["otherDescription"];
                    security["uuid"]=$("#uuid").val();
                    security["count"]=$("#count").val();
                    security["areaId"]=$("#areaId").val();
                    if ($("#hasInjury").is(':checked')) {
                        security["hasInjury"] = 1;
                    }
                    if ($("#hasWine").is(':checked')) {
                        security["hasWine"] = 1;
                    }
                    if ($("#hasPhoto").is(':checked')) {
                        security["hasPhoto"] = 1;
                    }
                    var jsonrtinfo = JSON.stringify(security);
                    $.messager.progress({
                        title: '请等待',
                        msg: '保存数据中...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'insert.do?maker=cj',
                        data: jsonrtinfo,
                        dataType: 'json',
                        success: function (data) {
                            $('#uuid').val("");
                            $('#reason').val("")
                            $.messager.progress('close');
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            $('#security_data_dialog').hide()
                            $('#serialNo').combogrid('grid').datagrid('reload');
                            $('#caseinfo').form('clear');
                            $('#securityinfo').form('clear');
                            $('#security_datagrid').datagrid('reload');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '未知错误!');
                        }
                    });
                }else {
                    return;
                }
            }
            if(true){
                if (true){
//保存案件信息
                    var caseinfo = $('#caseinfo').serializeObject();
                    //保存安检信息
                    var securityInfo = $('#securityinfo').serializeObject();
                    if (securityInfo["physicalDescription"] == null || securityInfo["physicalDescription"] == '') {
                        securityInfo["physical"] = 0;
                    } else {
                        securityInfo["physical"] = 1;
                    }
                    if (securityInfo["injuryDescription"] == null || securityInfo["injuryDescription"] == '') {
                        securityInfo["injury"] = 0;
                    } else {
                        securityInfo["injury"] = 1;
                    }
                    var security = $('#securityinfo').serializeObject();
                    var a = caseinfo["involvedDate"].split(/[^0-9]/);
                    var d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
                    security["involvedDate"] = d.getTime();
                    security["involvedAddress"] = caseinfo["involvedAddress"];
                    security["involvedPeople"] = caseinfo["involvedPeople"];
                    security["involvedReason"] = $('#reason').val();

                    security["involvedReasonText"] = $('#involvedReason').combobox('getText');
                    security["againReason"] = caseinfo["againReason"];
                    security["lawCaseId"] = $("#caseId").val();
                    security["serialNo"] = serialNo;
                    security["checkUserNo"] = securityInfo["checkUserNo"];
                    security["checkedEndTime"] = new Date().getTime();
                    security["checkedStartTime"] = $("#checkedStartTime").val();
                    security["type"] = $("input[name='type']:checked").val();
                    security["medicalHistory"] = securityInfo["medicalHistory"];
                    security["physical"] = securityInfo["physical"];
                    security["physicalDescription"] = securityInfo["physicalDescription"];
                    security["injury"] = securityInfo["injury"];
                    security["injuryDescription"] = securityInfo["injuryDescription"];
                    security["dangerous"] = securityInfo["dangerous"];
                    security["otherDescription"] = securityInfo["otherDescription"];
                    security["uuid"]=$("#uuid").val();
                    security["count"]=$("#count").val();
                    security["areaId"]=$("#areaId").val();
                    if ($("#hasInjury").is(':checked')) {
                        security["hasInjury"] = 1;
                    }
                    if ($("#hasWine").is(':checked')) {
                        security["hasWine"] = 1;
                    }
                    if ($("#hasPhoto").is(':checked')) {
                        security["hasPhoto"] = 1;
                    }
                    var jsonrtinfo = JSON.stringify(security);
                    $.messager.progress({
                        title: '请等待',
                        msg: '保存数据中...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'insert.do?maker=cj',
                        data: jsonrtinfo,
                        dataType: 'json',
                        success: function (data) {
                            $('#uuid').val("");
                            $('#reason').val("")
                            $.messager.progress('close');
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            $('#security_data_dialog').hide();
                            $('#serialNo').combogrid('grid').datagrid('reload');
                            $('#caseinfo').form('clear');
                            $('#securityinfo').form('clear');
                            doSearchAj();
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '未知错误!');
                        }
                    });
                }else {
                    return;
                }
            }
            if(true){
                if (true){
                    var caseinfo = $('#caseinfo').serializeObject();
                    //保存安检信息
                    var securityInfo = $('#securityinfo').serializeObject();
                    if (securityInfo["physicalDescription"] == null || securityInfo["physicalDescription"] == '') {
                        securityInfo["physical"] = 0;
                    } else {
                        securityInfo["physical"] = 1;
                    }
                    if (securityInfo["injuryDescription"] == null || securityInfo["injuryDescription"] == '') {
                        securityInfo["injury"] = 0;
                    } else {
                        securityInfo["injury"] = 1;
                    }
                    var security = $('#securityinfo').serializeObject();
                    var a = caseinfo["involvedDate"].split(/[^0-9]/);
                    var d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
                    security["involvedDate"] = d.getTime();
                    security["involvedAddress"] = caseinfo["involvedAddress"];
                    security["involvedPeople"] = caseinfo["involvedPeople"];
                    security["involvedReason"] = $('#reason').val();

                    security["involvedReasonText"] = $('#involvedReason').combobox('getText');
                    security["againReason"] = caseinfo["againReason"];
                    security["lawCaseId"] = $("#caseId").val();
                    security["serialNo"] = serialNo;
                    security["checkUserNo"] = securityInfo["checkUserNo"];
                    security["checkedEndTime"] = new Date().getTime();
                    security["checkedStartTime"] = $("#checkedStartTime").val();
                    security["type"] = $("input[name='type']:checked").val();
                    security["medicalHistory"] = securityInfo["medicalHistory"];
                    security["physical"] = securityInfo["physical"];
                    security["physicalDescription"] = securityInfo["physicalDescription"];
                    security["injury"] = securityInfo["injury"];
                    security["injuryDescription"] = securityInfo["injuryDescription"];
                    security["dangerous"] = securityInfo["dangerous"];
                    security["otherDescription"] = securityInfo["otherDescription"];
                    security["uuid"]=$("#uuid").val();
                    security["count"]=$("#count").val();
                    security["areaId"]=$("#areaId").val();
                    if ($("#hasInjury").is(':checked')) {
                        security["hasInjury"] = 1;
                    }
                    if ($("#hasWine").is(':checked')) {
                        security["hasWine"] = 1;
                    }
                    if ($("#hasPhoto").is(':checked')) {
                        security["hasPhoto"] = 1;
                    }
                    var jsonrtinfo = JSON.stringify(security);
                    $.messager.progress({
                        title: '请等待',
                        msg: '保存数据中...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'insert.do?maker=cj',
                        data: jsonrtinfo,
                        dataType: 'json',
                        success: function (data) {
                            $('#uuid').val("");
                            $('#reason').val("")
                            $.messager.progress('close');
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            $('#security_data_dialog').hide()
                            $('#serialNo').combogrid('grid').datagrid('reload');
                            $('#caseinfo').form('clear');
                            $('#securityinfo').form('clear');
                            $('#security_datagrid').datagrid('reload');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '未知错误!');
                        }
                    });
                }else {
                    return;
                }
            }
            if(true){
                    var caseinfo = $('#caseinfo').serializeObject();
                    //保存安检信息
                    var securityInfo = $('#securityinfo').serializeObject();
                    if (securityInfo["physicalDescription"] == null || securityInfo["physicalDescription"] == '') {
                        securityInfo["physical"] = 0;
                    } else {
                        securityInfo["physical"] = 1;
                    }
                    if (securityInfo["injuryDescription"] == null || securityInfo["injuryDescription"] == '') {
                        securityInfo["injury"] = 0;
                    } else {
                        securityInfo["injury"] = 1;
                    }
                    var security = $('#securityinfo').serializeObject();
                    var a = caseinfo["involvedDate"].split(/[^0-9]/);
                    var d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
                    security["involvedDate"] = d.getTime();
                    security["involvedAddress"] = caseinfo["involvedAddress"];
                    security["involvedPeople"] = caseinfo["involvedPeople"];
                    security["involvedReason"] = $('#reason').val();

                    security["involvedReasonText"] = $('#involvedReason').combobox('getText');
                    security["againReason"] = caseinfo["againReason"];
                    security["lawCaseId"] = $("#caseId").val();
                    security["serialNo"] = serialNo;
                    security["checkUserNo"] = securityInfo["checkUserNo"];
                    security["checkedEndTime"] = new Date().getTime();
                    security["checkedStartTime"] = $("#checkedStartTime").val();
                    security["type"] = $("input[name='type']:checked").val();
                    security["medicalHistory"] = securityInfo["medicalHistory"];
                    security["physical"] = securityInfo["physical"];
                    security["physicalDescription"] = securityInfo["physicalDescription"];
                    security["injury"] = securityInfo["injury"];
                    security["injuryDescription"] = securityInfo["injuryDescription"];
                    security["dangerous"] = securityInfo["dangerous"];
                    security["otherDescription"] = securityInfo["otherDescription"];
                    security["uuid"]=$("#uuid").val();
                    security["count"]=$("#count").val();
                    security["areaId"]=$("#areaId").val();
                    if ($("#hasInjury").is(':checked')) {
                        security["hasInjury"] = 1;
                    }
                    if ($("#hasWine").is(':checked')) {
                        security["hasWine"] = 1;
                    }
                    if ($("#hasPhoto").is(':checked')) {
                        security["hasPhoto"] = 1;
                    }
                    var jsonrtinfo = JSON.stringify(security);
                    $.messager.progress({
                        title: '请等待',
                        msg: '保存数据中...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'insert.do?maker=cj',
                        data: jsonrtinfo,
                        dataType: 'json',
                        success: function (data) {
                            $('#uuid').val("");
                            $('#reason').val("")
                            $.messager.progress('close');
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            $('#security_data_dialog').hide()
                            $('#serialNo').combogrid('grid').datagrid('reload');
                            $('#caseinfo').form('clear');
                            $('#securityinfo').form('clear');
                            $('#security_datagrid').datagrid('reload');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '未知错误!');
                        }
                    });
            }
        } else {
            $.messager.alert('警告', '信息填写不完整!');
        }
    } else {
        $.messager.alert('警告', '安检员警号不正确!');
    }
}

//定格画面
function photoScan() {
    isPhoto = 1;
    _register();
    webcam.capture();
    changeFilter();
    void (0);
}
function loadPersonImg(){
    var uuid = $("#uuid").val();
    var areaId = $("#areaId").val();
    var count = $("#count").val();
    if ($("#checkUserId").val() != null && $("#checkUserId").val() != '') {
        var ww = window.screen.width/2 + 300;
        var hh = 700;
        window.open("../security/personfont/personfont.jsp?r="+Math.random()+"&uuid="
            +uuid+"&areaId="+areaId+"&count="+count, "","toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no,width="+ww+",height="+hh);
    }else {
        $.messager.alert('警告', '请先选择安检民警!');
    }
}
function uploadImg(){
    var cg = $('#serialNo').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');	// 获取选择的行
    var count = $("#count").val()
    var uuid = $("#uuid").val();
    var areaId = $("#areaId").val();
    var m =  $("#serialId").val()
    var s="0";
    if(row != null){
        if(m==0){
            s =  row.id
        }else{
            s = m;
        }
        var h =  $("#serialNoo").val()
        var k="0";
        if(h==0){
            k =  row.serialNo
        }else{
            k = h;
        }
    }
    if ($("#checkUserId").val() != null && $("#checkUserId").val() != '') {
        var canvas = document.getElementById("canvas");
        var str = canvas.toDataURL("image/png");
        var len = str.length;
        var _blob = dataURLtoBlob(str);
        var formData = new FormData();
        formData.append("file",_blob,'a.png');
        formData.append("serialId",s);
        jQuery.ajax({
            type: 'POST',
            url: contextPath + '/zhfz/bacs/security/ingetpicture.do?uuid='+uuid+"&areaId="+areaId+"&serialNo="+k+"&count="+count,
            data: formData,
            contentType: false, // 注意这里应设为false
            processData: false,
            cache: false,
            dataType: 'json',
            success: function (data) {
                $("#bodyPhoto").val("1")
                $.messager.progress('close');
                isPhoto = 0;
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert(data.title, data.content);
            }
        });
    }else{
        $.messager.alert('警告', '请先选择安检民警!');
    }
}
function intlize() {
    $("#bodyTag").val("0")
    $("#bodyPhoto").val("0")
    $('#reason').val("")
}

function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], { type: mime });
}

//用于生成uuid
function getUuidNumber() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function getUuid() {
    return (getUuidNumber()+getUuidNumber()+"-"+getUuidNumber()+"-"+getUuidNumber()+"-"+getUuidNumber()+"-"+getUuidNumber()+getUuidNumber()+getUuidNumber());
}
//行点击事件重新加载体表标记照片
function reloadMarkersPic(serialUUID,areaId,count){
  /*  $("#sliderPhoto").append("<li><img src='../security/image/person2.png' style='height: 267px;width: 400px'></li>")*/
    /*document.getElementById('yyy').contentWindow.location.reload(true);*/
    /*alert(document.getElementById('yyy').contentWindow.document.getElementById('test').value)*/
    /* document.getElementById('yyy').contentWindow.document.getElementById('sliderPhoto').append('<li><img src="../security/image/person1.png"></li>')*/
    /*document.getElementById('ttt').contentWindow.testMarkes(serialUUID);*/
    // document.getElementById('ttt').contentWindow.testMakers(serialUUID,areaId,count);
    document.getElementById('yyy').contentWindow.testPhotos(serialUUID,areaId,count);
   /* $("#sliderPhoto").append("<div class='swiper-slide'><img src='../security/image/person2.png' style='height: 267px;width: 400px'></div>")*/
}

//复检
function securityRepeat (index){
    toInitialize();
    $("#firstDiv").hide();
    $("#repeatDiv").show();
    $('#serialNo').combogrid('reset')
    $('#serialNo').combogrid('clear')
    var rowData = $('#security_datagrid').datagrid('getRows')[index];
    //专属编号
    $('#serialNo').combogrid('setValues', [rowData.personName]);
    $('#serialNo').combogrid('readonly',true)
    $('#involvedPeople').val(rowData.personName);
    $('#cserialNo').val(rowData.serialNo);
    $('#checkUserNo').val(rowData.checkUserNo);
    $('#checkUserId').val(rowData.checkUserId);
    $('#involvedDate').datetimebox('setValue',valueToDate(rowData.afsj));
    $('#involvedReason').combobox('setValue', rowData.ab);
    $('#reason').val(rowData.ab)
    $('#involvedAddress').val(rowData.afdd);
    $('#caseId').val(rowData.caseId);
    $('#uuid').val(rowData.uuid);
    $('#areaId').val(rowData.areaId);
    $('#serialId').val(rowData.serialId);
    $('#serialNoo').val(rowData.serialNo);
    document.getElementById('hasPhoto').checked = true;
    jQuery.ajax({
        type: 'POST',
        url: 'selectRepetCount.do?serialId='+rowData.serialId,
        success: function (data) {
            $('#count').val(data);
        }
    })
}

function orderRequestDelay(index) {
    var row = $('#security_datagrid').datagrid('getRows')[index];
   // fileUtils.load('ba','AJ',row.serialUUID,row.areaId,row.uuid+'.doc');

    $.messager.progress({
            title: '请等待',
            msg: '下载中...'
        });

        fileUtils.read("/zhfz/lawdocProcess/download.do?number=11&name=违法犯罪嫌疑人人身安全检查情况登记表&serialUUID="+row.serialUUID+"&serialNo="+row.serialNo+"&serialId="+row.serialId
                    +"&count="+row.count);

        $.messager.progress('close');
}

//保存复检结果
function saveInfoRepeat(index) {
    if ($("#checkUserId").val() != null && $("#checkUserId").val() != '') {
  /*      var cg = $('#serialNo').combogrid('grid');	// 获取数据表格对象
        var row = cg.datagrid('getSelected');	// 获取选择的行*/
        var serialNo = $('#cserialNo').val();
        if ($("input[name='type']:checked").val() != null && $("input[name='type']:checked").val() != '' && serialNo != null && serialNo != '' && $("#checkUserNo").val() != null && $("#checkUserNo").val() != '' && $("#checkUserNo").val() != '请输入警号') {
            if (true) {
                if (true) {
//保存案件信息
                    var caseinfo = $('#caseinfo').serializeObject();
                    //保存安检信息
                    var securityInfo = $('#securityinfo').serializeObject();
                    if (securityInfo["physicalDescription"] == null || securityInfo["physicalDescription"] == '') {
                        securityInfo["physical"] = 0;
                    } else {
                        securityInfo["physical"] = 1;
                    }
                    if (securityInfo["injuryDescription"] == null || securityInfo["injuryDescription"] == '') {
                        securityInfo["injury"] = 0;
                    } else {
                        securityInfo["injury"] = 1;
                    }
                    var security = $('#securityinfo').serializeObject();
                    var a = caseinfo["involvedDate"].split(/[^0-9]/);
                    var d = new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
                    security["involvedDate"] = d.getTime();
                    security["involvedAddress"] = caseinfo["involvedAddress"];
                    security["involvedPeople"] = caseinfo["involvedPeople"];
                    security["involvedReason"] = $('#reason').val();

                    security["involvedReasonText"] = $('#involvedReason').combobox('getText');
                    security["againReason"] = caseinfo["againReason"];
                    security["lawCaseId"] = $("#caseId").val();
                    security["serialNo"] = serialNo;
                    security["checkUserNo"] = securityInfo["checkUserNo"];
                    security["checkedEndTime"] = new Date().getTime();
                    security["checkedStartTime"] = $("#checkedStartTime").val();
                    security["type"] = $("input[name='type']:checked").val();
                    security["medicalHistory"] = securityInfo["medicalHistory"];
                    security["physical"] = securityInfo["physical"];
                    security["physicalDescription"] = securityInfo["physicalDescription"];
                    security["injury"] = securityInfo["injury"];
                    security["injuryDescription"] = securityInfo["injuryDescription"];
                    security["dangerous"] = securityInfo["dangerous"];
                    security["otherDescription"] = securityInfo["otherDescription"];
                    security["uuid"] = $("#uuid").val();
                    security["count"] = $("#count").val();
                    security["areaId"] = $("#areaId").val();
                    if ($("#hasInjury").is(':checked')) {
                        security["hasInjury"] = 1;
                    }
                    if ($("#hasWine").is(':checked')) {
                        security["hasWine"] = 1;
                    }
                    if ($("#hasPhoto").is(':checked')) {
                        security["hasPhoto"] = 1;
                    }
                    var jsonrtinfo = JSON.stringify(security);
                    $.messager.progress({
                        title: '请等待',
                        msg: '保存数据中...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'insert.do?maker=fj',
                        data: jsonrtinfo,
                        dataType: 'json',
                        success: function (data) {
                            $('#uuid').val("");
                            $('#reason').val("")
                            $.messager.progress('close');
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            $('#security_data_dialog').hide()
                            $('#serialNo').combogrid('grid').datagrid('reload');
                            $('#caseinfo').form('clear');
                            $('#securityinfo').form('clear');
                            $('#security_datagrid').datagrid('reload');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '未知错误!');
                        }
                    });
                } else {
                    return;
                }
            }
        } else {
            $.messager.alert('警告', '信息填写不完整!');
        }
    } else {
        $.messager.alert('警告', '安检员警号不正确!');
    }
}

function doSearchAj() {
    $('#security_datagrid').datagrid('load', {
        ryxm : $('#ryxm').val(),
        zjhm : $('#zjhm').val(),
        ajmc : $('#ajmc').val(),
        interrogatearea : $('#interrogatearea').val(),
        trefresh:new Date().getTime()
    });
}

function doClearAj() {
    $('#ryxm').prop('value','');
    $('#zjhm').prop('value','');
    $('#ajmc').prop('value','');
    $("#interrogatearea").combobox("setValue","");
}

function testfun(){
    $.messager.show({
        title:"提示",
        msg:"体表标注已完成",
        timeout:3000
    });
    document.getElementById("tbbjm").innerHTML = "已经完成体表标记√"
    document.getElementById("tbbjm").style.color = "white";

}

function finduser() {
    var userNo = $('#checkUserNo').val();
    if(userNo==null||userNo.length==0){
        return;
    }
    $('#userNo').val(userNo)
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"] = userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : '../order/searchUser.do',
        data : jsonrtinfo,
        success : function(data) {
            if (data != null && data.id != null) {
                $("#checkUserId").val(data.id);
            } else {
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

        }
    });
}

//键盘监听事件
function enterKeyEventSecurity(){
    var e = e || window.event;
    if (e.keyCode == 13) {
        checkRing($("#cuffNumber").val());
    }
}

//手环校验
function checkRing(icNo){
    //debugger;
    var checkData = checkRingNo(icNo,0);
    if(checkData.isError){
        $.messager.alert("提示","手环不存在");
        $('#uuid').val("");
        $('#reason').val("")
        $('#serialNo').combogrid('grid').datagrid('reload');
        $('#caseinfo').form('clear');
        $('#securityinfo').form('clear');
        return;
    }else{
            if (checkData.callbackData.cuffNo) {
                var cuff = $('#cuffInfo').serializeObject();
                cuff["cuffNo"] = checkData.callbackData.cuffNo;
                cuff["type"] = 0;
                var jsonrtinfo = JSON.stringify(cuff);
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: '../serial/getSerialByCuffNo.do',
                    data: jsonrtinfo,
                    dataType: 'json',
                    success: function (data) {
                        if (data.callbackData) {
                            var status = data.callbackData.status;
                            var interrogateCaseId = data.callbackData.caseId;
                            $('#s_lawCaseId').val(data.callbackData.caseId);
                            if (status != null && status == 0) {
                                var serialNo = data.callbackData.serialNo;
                                if (serialNo != null && serialNo != "") {
                                    $('#cuffNumber').val(data.callbackData.cuffNo);
                                    $('#serialNo').combogrid('setValue', serialNo);
                                    $('#cserialNo').val(serialNo);
                                    $('#cuffNumber').val('');
                                } else {
                                    $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                                    $('#cuffNumber').val('');
                                }
                            } else if (status != null && status > 0) {
                                if (interrogateCaseId != null) {
                                    $.messager.confirm('提醒', '该嫌疑人已经安检过,确认要再次安检吗!',function(r){
                                        if(r){
                                            var serialNo = data.callbackData.serialNo;
                                            if (serialNo != null && serialNo != "") {
                                                //查询出该条信息
                                                jQuery.ajax({
                                                    type: 'POST',
                                                    url: 'selectRepeatPerson.do?serialNo='+serialNo,
                                                    dataType:'json',
                                                    success: function (data) {
                                                        toInitialize();
                                                        $("#firstDiv").hide();
                                                        $("#repeatDiv").show();
                                                        $('#serialNo').combogrid('reset')
                                                        $('#serialNo').combogrid('clear')
                                                        $('#serialNo').combogrid('setValues', data.personName);
                                                        $('#serialNo').combogrid('readonly',true)
                                                        $('#involvedPeople').val(data.personName);
                                                        $('#cserialNo').val(data.serialNo);
                                                        $('#checkUserNo').val(data.checkUserNo);
                                                        $('#checkUserId').val(data.checkUserId);
                                                        $('#involvedDate').datetimebox('setValue',valueToDate(data.afsj));
                                                        $('#involvedReason').combobox('setValue', data.ab);
                                                        $('#reason').val(data.ab)
                                                        $('#involvedAddress').val(data.afdd);
                                                        $('#caseId').val(data.caseId);
                                                        $('#uuid').val(data.uuid);
                                                        $('#areaId').val(data.areaId);
                                                        $('#serialId').val(data.serialId);
                                                        $('#serialNoo').val(data.serialNo);
                                                        $("#cuffNumber").val(checkData.callbackData.cuffNo);
                                                        $('#count').val(Number(data.count)+Number(1));
                                                        $('#cuffNumber').val('');
                                                    }
                                                })
                                            } else {
                                                $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                                                $('#cuffNumber').val('');
                                            }
                                        }
                                    });
                                } else {
                                    var serialNo = data.callbackData.serialNo;
                                    if (serialNo != null && serialNo != "") {
                                        $('#cuffNumber').val(data.callbackData.cuffNo);
                                        $('#serialNo').combogrid('setValue', serialNo);
                                        $('#cserialNo').val(serialNo);
                                    }
                                }
                            } else {
                                $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                                $('#cuffNumber').val('');
                            }
                        } else {
                            $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                            $('#cuffNumber').val('');
                        }
                    },
                    error: function (data) {
                        $.messager.alert('错误', '未知错误!');
                        $('#cuffNumber').val('');
                    }
                });
            } else {
                $.messager.alert('警告', '读卡失败!');
                $('#cuffNumber').val('');
            }
    }
}

//加载办案场所
function initArea(){
    $.ajax({
        url : contextPath + '/zhfz/bacs/order/comboArea.do',
        dataType : 'json',
        async : false,
        success : function (data) {
            $('#interrogatearea').combobox({
                data: data,
                valueField: 'id',
                textField: 'name',
                width : 160
            });
            if(data != null && data.length > 0){
                $('#interrogatearea').combobox('setValue',data[0].id);
            }
        }
    });
}
//PDF打印
function signPrint(index){
    var row = $('#security_datagrid').datagrid('getRows')[index];
    printChoose(row.serialId,row.serialUUID,row.areaId,3);
}

//签字
function securitySign(index) {
    var row = $('#security_datagrid').datagrid('getRows')[index];
    var url = "../../lawdocProcess/downloadBase64.do?number=11&name=违法犯罪嫌疑人人身安全检查情况登记表&serialUUID="+row.serialUUID+"&serialNo="+row.serialNo+"&serialId="+row.serialId
        +"&count="+row.count
    SignType = 3;
    startSign(url,row.serialId,3);
}
//签字
function cxsecuritySign(index) {
    var row = $('#security_datagrid').datagrid('getRows')[index];
    var url = "../../lawdocProcess/downloadBase64.do?number=11&name=违法犯罪嫌疑人人身安全检查情况登记表&serialUUID="+row.serialUUID+"&serialNo="+row.serialNo+"&serialId="+row.serialId
        +"&count="+row.count
    SignType = 3;
    cxstartSign(url,row.serialId,3);
}

//pdf下载
function PdfDownLoad(index){
    var row = $('#security_datagrid').datagrid('getRows')[index];
    downLoadPdf(row.serialId, row.uuid, row.areaId, 3);
}

function xqHtml(currentRow){
    var xqHtmlBody = ' <table  id="tt" class="colinfo_table">'
        + ' <tr>'
        + ' 	<td class="detailLable">姓名</td>'
        + ' <td>'
        + currentRow.personName
        + '</td>'
        + ' 	<td class="detailLable">性别</td>'
        + ' 	<td>'
        + (currentRow.sex==1?'男':'女')
        + '</td>'
        + ' 	<td class="detailLable">入区编号</td>'
        + ' 	<td colspan="2">'
        + currentRow.serialNo
        + '</td>'
        + ' <tr>'
        + ' 	<td class="detailLable">安检次数</td>'
        + ' 	<td>'
        + '第' + currentRow.count + '次'
        + '</td>'
        + ' 	<td class="detailLable">证件类型</td>'
        + ' 	<td>'
        + currentRow.certificateTypeName
        + '</td>	'
        + ' 	<td class="detailLable">证件号码</td>'
        + ' 	<td>'
        + currentRow.certificateNo
        + '</td>	'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">安检单位</td>'
        + ' 	<td>'
        + currentRow.areaName
        + '</td>'
        + ' 	<td class="detailLable">安检民警</td>'
        + ' 	<td>'
        + currentRow.checkUserName + '('+ currentRow.checkUserNo +')'
        + '</td>'
        + '     <td class="detailLable">案件名称</td>'
        + '     <td>'
        + currentRow.caseName
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">自述症状(既往病史)</td>'
        + ' 	<td>'
        + currentRow.medicalHistory
        + '</td>'
        + ' 	<td class="detailLable">特殊体貌特征</td>'
        + ' 	<td >'
        + currentRow.physicalDescription
        + '</td>'
        + ' 	<td class="detailLable">携带危险品情况</td>'
        + ' 	<td>'
        + currentRow.dangerous
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">检查情况</td>'
        + ' 	<td>'
        + currentRow.injuryDescription
        + '</td>'
        + ' 	<td class="detailLable">其他说明情况</td>'
        + ' 	<td>'
        + currentRow.otherDescription
        + '</td>'
        + ' 	<td class="detailLable">体表是否有伤情</td>'
        + ' 	<td>'
        + (currentRow.hasInjury==0?'否':'有')
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">是否饮酒</td>'
        + ' 	<td>'
        + (currentRow.hasWine==0?'否':'有')
        + '</td>'
        + ' 	<td class="detailLable"></td>'
        + ' 	<td>'
        + '</td>'
        + '     <td class="detailLable"></td>'
        + '     <td class="detailLable"></td>' + ' </tr>' +
        ' </table> ';
    return xqHtmlBody;
}