//打开关联案件弹框
function linkCase() {
    $("#link_case_dialog").show();
    loadLinkCaseList();
    if ($('#link_case_datagrid')) {
        $('#link_case_datagrid').datagrid({
            onLoadSuccess: function (data) {
                if ($("#caseId")) {
                    var caseId = $("#caseId").val();
                    if (caseId != null && caseId != '' && data != null && data.rows != null && data.rows.length != 0) {
                        for (var i = 0; i < data.rows.length; i++) {
                            if (caseId == data.rows[i].id) {
                                $('#link_case_datagrid').datagrid("selectRow", i);
                            }
                        }
                    }
                }
            }
        });
    }
}

//关联案件保存
function linkCaseSave() {
    var erow = $('#link_case_datagrid').datagrid('getSelected');
    if (erow == null) {
        $.messager.alert('提示', '请先选择一行!');
        return;
    } else {
        if (erow.ajly == 101) {
            initPersonInfo(erow.ajbh);
        }
        $("#jbaq").val(erow.ajmc);
        $("#lawCaseName").val(erow.ajmc);
        $("#caseId").val(erow.id);
        $("#lawCaseId").val(erow.id);
        $("#link_case_ajmc").val("已联案件：" + erow.ajmc);
        // $('#link_case_dialog').dialog('close');
        $("#link_case_dialog").hide();
    }
}

function personRefrsh() {
    if (orderRequestDataId != null && orderRequestDataId != "") {
        $('#datagrid_orderstatus_person').datagrid('load', {
            orderRequestId: orderRequestDataId,
            //trefresh:new Date().getTime()
        });
    } else {
        $.messager.alert('提示', '请先选择左侧预约记录!');
    }
}

//person 修改 start
function orderRequestPersonOtherEdit(id, index) {
    var rowData = $('#form_orderrequest_personList').datagrid('getRows')[index];
    showDialog('#dialog_orderrequest_person', '编辑人员信息');
    $('#editjzyms').val("");
    $('#editxyrtw').val("");
    $('#editzhdz').val("");
    $('#editzjdz').val("");
    $('#editzjfjrq').datetimebox('setValue', "");
    $("#orderPersonEditIndex").val(index);
    $('#edit_name').val(rowData.name);
    $('#edit_certificateNo').val(rowData.certificateNo);
    var sex = rowData.sex;
    $('#edit_sex').combobox('select', sex);
    var zjzl = rowData.certificateTypeId;
    $('#edit_certificateTypeId').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#edit_certificateTypeId').combobox('setValue', zjzl);
        }
    });

    $('#edit_personInfo').combobox('setValue', rowData.personType);
    $('#edit_specialIdentity').combobox('setValue', rowData.other);
    if (rowData.personCountry != null && rowData.personCountry != 0) {
        codeCombo('edit_country', 'GJID', rowData.personCountry);
    }
    if (rowData.personNation != null && rowData.personNation != 0) {
        loadNation(rowData.personNation);
    }
    if (rowData.isJuvenile == 1) {
        $("input[name='edit_eisJuvenile'][value='1']").prop("checked", true);
    } else {
        $("input[name='edit_eisJuvenile'][value='0']").prop("checked", true);
    }
    if (rowData.isGravida == 1) {
        $("input[name='edit_eisGravida'][value='1']").prop("checked", true);
    } else {
        $("input[name='edit_eisGravida'][value='0']").prop("checked", true);
    }
    $('#edit_egravidaMonth').val(rowData.gravidaMonth);
    if (rowData.isGravidaProve == 1) {
        $("input[name='edit_eisGravidaProve'][value='1']").prop("checked", true);
    } else {
        $("input[name='edit_eisGravidaProve'][value='0']").prop("checked", true);
    }
    if (rowData.isDisease == 1) {
        $("input[name='edit_eisDisease'][value='1']").prop("checked", true);
    } else {
        $("input[name='edit_eisDisease'][value='0']").prop("checked", true);
    }
    if (rowData.isDiseaseProve == 1) {
        $("input[name='edit_eisDiseaseProve'][value='1']").prop("checked", true);
    } else {
        $("input[name='edit_eisDiseaseProve'][value='0']").prop("checked", true);
    }
    if (rowData.jkb == 1) {
        $("input[name='jkb'][value='1']").prop("checked", true);
    } else {
        $("input[name='jkb'][value='0']").prop("checked", true);
    }
    console.log(rowData)
    alert(rowData.sfcrgfxdq)
    if (rowData.sfcrgfxdq !="无"&&rowData.sfcrgfxdq !=null) {
        $("#editsfcrgfxdq").val(rowData.sfcrgfxdq);
        $("input[name='sfcrgfxdq1'][value='1']").prop("checked", true);
    } else {
        $("#editsfcrgfxdq").val(rowData.sfcrgfxdq);
        $("input[name='sfcrgfxdq1'][value='0']").prop("checked", true);
    }
    if (rowData.sfsjyqldaj == 1) {
        $("input[name='sfsjyqldaj'][value='1']").prop("checked", true);
    } else {
        $("input[name='sfsjyqldaj'][value='0']").prop("checked", true);
    }
    if (rowData.sfymjjcs == 1) {
        $("input[name='sfymjjcs'][value='1']").prop("checked", true);
    } else {
        $("input[name='sfymjjcs'][value='0']").prop("checked", true);
    }

    if (rowData.j3gysfyjjcgqk !='无'&&rowData.j3gysfyjjcgqk !=null) {
        $("#editj3gysfyjjcgqk").val(rowData.j3gysfyjjcgqk);
        $("input[name='j3gysfyjjcgqk1'][value='1']").prop("checked", true);
    } else {
        $("#editj3gysfyjjcgqk").val(rowData.j3gysfyjjcgqk);
        $("input[name='j3gysfyjjcgqk1'][value='0']").prop("checked", true);
    }
    if (rowData.sfymjjcs == 1) {
        $("input[name='sfymjjcs'][value='1']").prop("checked", true);
    } else {
        $("input[name='sfymjjcs'][value='0']").prop("checked", true);
    }
    $('#editjzyms').val(rowData.jzyms);
    $('#editxyrtw').val(rowData.xyrtw);
    $('#editzhdz').val(rowData.zhdz);
    $('#editzjdz').val(rowData.zjdz.replace("%20", ""));
    $('#editzjfjrq').datetimebox('setValue', valueToDate(rowData.zjfjrq));

//    $("#form_orderrequest_personList").datagrid("deleteRow", index);
//    
//    url = 'editPerson.do';
}

//person修改 end
function orderRequestAddUp(last) {
    showdiv("#jibenxinxi");
    hiddendiv("#suspectinfo");
    $("#step1").addClass("now");
    $("#step1").removeClass("active");
    $("#step2").removeClass("now");
    $("#step2").removeClass("active");
}

function fresh() {
    $.messager.progress({
        title: '请等待',
        loadMsg: '加载中...',
    });
    var areaId = $('#interrogatearea').combobox('getValue');
    if (areaId != null && areaId != '') {
        loadRooms(areaId);
    }
    location.reload();
    $.messager.progress('close');
}

function orderRequestPersonCancel(index) {
    $.messager.confirm('Confirm', '确定要删除该嫌疑人?', function (r) {
        if (r) {
            $("#form_orderrequest_personList").datagrid("deleteRow", index);
        }
    });
}

function onclickRadio(a, b) {
    var sf = $("input[name=" + b + "]:checked").val();
    if (sf == 0) {
        $('#' + a + '').val("无");
    }else{
        $('#' + a + '').val("");
    }
}

function saveAddperson() {
    if ($("#addperson_certificate_type").combobox("getValue") == 111) {
        var certificateNo = $("#addperson_certificate_no").val();
        var regex = /.*([a-z])+.*/;
        //alert(regex.test(certificateNo));
        if (regex.test(certificateNo)) {
            $.messager.alert('提醒', '请将您输入的身份证号中的小写字母改为大写！');
            return;
        }

        var reg1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (reg1.test(certificateNo) === false) {
            $.messager.alert('提醒', '请输入合法的身份证号码！');
            return;
        }
    }


    if (!checkForm('#addpersoninfoform')) {
        $.messager.alert('提示', '请补全信息!');
        return;
    }
    var certificateNo = $('#addperson_certificate_no').val();
    var person_certificate_type = $('#addperson_certificate_type').val();
    if (person_certificate_type == 111) {//身份证
        var idErrorMessage = checkId(certificateNo)
        if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {

        } else {
            $.messager.alert('提醒', idErrorMessage);
            return;
        }
    }
    var personList = $("#addpersoninfoform").serializeObject();
    personList["orderRequestId"] = orderRequestDataId;
    personList["areaId"] = AREAID;

    personList["ajbh"] = $("#ajbh1").val();
    personList["rybh"] = $("#rybh1").val();
    personList["ajmc"] = $("#ajmc1").val();

    var personsJson = JSON.stringify(personList);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
//		url : 'addPerson.do',
        url: url,
        data: personsJson,
        dataType: 'json',
        success: function (data) {
            //2016-1-12 添加刷新
            $('#dialog_add_person').dialog('close');
//			$.messager.alert('成功',  data.content);
            $.messager.show({
                title: "提示",
                msg: data.content,
                timeout: 3000
            });
            $('#form_orderrequest_personList').datagrid('load', {
                orderRequestId: orderRequestDataId,
                trefresh: new Date().getTime()
            });
            $('#datagrid_orderstatus_person').datagrid('load', {
                orderRequestId: orderRequestDataId,
                trefresh: new Date().getTime()
            });
            $.messager.progress('close');
            var areaId = $('#interrogatearea').combobox('getValue');
            $('#datagrid_orderrequest').datagrid('load', {
                userName: $('#o_user_name').val(),
                orderNo: $('#o_order_no').val(),
                personName: $('#o_person_name').val(),
                areaId: areaId,
                trefresh: new Date().getTime()
            });
            $('#addperson_name').val();
            $('#addperson_certificate_no').val();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '保存失败!' + data.content);
        }
    });
}

// 添加人员
function addSuspection() {
    if ($("#orderRequestId").val() == null) {
        var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
        if (rowData == null) {
            $.messager.alert('提示', '请选择一条左侧预约信息!');
            return;
        }
        AREAID = rowData.areaId;
    }

    $('#selectXqJzLable').html('<font style="color: red">未关联执法办案平台数据！</font>');

    showDialog('#dialog_add_person', '嫌疑人信息');
    $('#dialog_add_person').form('clear');
    loadSexadd();
    codeCombo('addperson_country', 'GJID', 156);
    loadNation();
    loadSpecialIdentity();
    //加载人员类型
    $('#addperson_personInfo').combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        }, {
            label: '老年人',
            value: '老年人'
        }, {
            label: '特殊疾病',
            value: '特殊疾病'
        }, {
            label: '孕妇',
            value: '孕妇'
        }, {
            label: '残疾人',
            value: '残疾人'
        }, {
            label: '成年人',
            value: '成年人',
            "selected": true
        }],
        valueField: 'value',
        textField: 'label',
        onChange: function (value) {
            var juvenile = document.getElementsByName("isJuvenile");//未成年人
            var isGravida = document.getElementsByName("isGravida");//孕妇
            if (value == '未成年人') {
                juvenile[0].checked = true;
                isGravida[0].checked = false;
            } else if (value == '孕妇') {
                isGravida[0].checked = true;
                juvenile[0].checked = false;
            } else {
                juvenile[0].checked = false;
                isGravida[0].checked = false;
            }
        }

    });
    $('#addperson_certificate_type').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        required: true,
        onSelect: function (rec) {
            if (rec.id == 7) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    url: "../combobox/listWMSCount.do",
                    success: function (data) {
                        var count = data + 1;
                        $('#addperson_name').val("无名氏" + count);
                        $("#addperson_certificate_no").val("XXXXX.....XXXXX" + count);
                    },
                    error: function (data) {
                        $.messager.alert('错误', '未知错误!');
                        $.messager.progress('close');
                    }
                });
            }
        }, onLoadSuccess: function (data) {
            $('#addperson_certificate_type').combobox('setValue', data[0].id);
        }, onChange: function (n, o) {
            setWuMingShi();
        }
    });
    $('#addperson_country').combobox('setValue', 156);
    $('#addperson_nation').combobox('setValue', 1);
    $('#addperson_name').focus();
    $('#addperson_name').select();
    url = 'addPerson.do';
}


//添加人员
function addPerson() {
    $("#personEditIndex").val("");
    if ($("#orderRequestId").val() == null) {
        var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
        if (rowData == null) {
            $.messager.alert('提示', '请选择一条左侧预约信息!');
            return;
        }
        AREAID = rowData.areaId;
    }
    $('#selectJzLable').html('<font style="color: red">未关联警综平台数据！</font>');
    showDialog('#dialog_add_suspectinfo', '嫌疑人信息');
    $('#addsuspectinfoform').form('clear');
    loadSex();
    codeCombo('person_country', 'GJID', 156);
    loadNation();
    loadSpecialIdentity();
    //加载人员类型
    $('#person_personInfo').combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        }, {
            label: '老年人',
            value: '老年人'
        }, {
            label: '特殊疾病',
            value: '特殊疾病'
        }, {
            label: '孕妇',
            value: '孕妇'
        }, {
            label: '残疾人',
            value: '残疾人'
        }, {
            label: '成年人',
            value: '成年人',
            "selected": true
        }],
        valueField: 'value',
        textField: 'label',
        onChange: function (value) {
            // var juvenile = document.getElementsByName("isJuvenile");//未成年人
            // var isGravida = document.getElementsByName("isGravida");//孕妇

            if (value == '未成年人') {
                $("input[name='isJuvenile'][value='1']").prop("checked", true);
                $("input[name='isGravida'][value='0']").prop("checked", true);
                // juvenile[0].checked = true;
                // isGravida[0].checked = false;
            } else if (value == '孕妇') {
                $("input[name='isJuvenile'][value='0']").prop("checked", true);
                $("input[name='isGravida'][value='1']").prop("checked", true);
            }
        }

    });
    $('#person_certificate_type').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        required: true,
        onSelect: function (rec) {
            if (rec.id == 7) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    url: "../combobox/listWMSCount.do",
                    success: function (data) {
                        var count = data + 1;
                        $('#person_name').val("无名氏" + count);
                        $("#person_certificate_no").val("XXXXX.....XXXXX" + count);
                    },
                    error: function (data) {
                        $.messager.alert('错误', '未知错误!');
                        $.messager.progress('close');
                    }
                });
            }
        }, onLoadSuccess: function (data) {
            $('#person_certificate_type').combobox('setValue', data[0].id);
        }, onChange: function (n, o) {
            setWuMingShi();
        }
    });
    $('#person_country').combobox('setValue', 156);
    $('#person_nation').combobox('setValue', 1);
    $('#person_name').focus();
    $('#person_name').select();
}

//重新加载personList
function orderrquestperosnreLoad(rowData) {
    $('#datagrid_orderstatus_person').datagrid('load', {
        orderRequestId: rowData.id,
        trefresh: new Date().getTime()
    });
}

function doexportOrderRequest() {
    var areaId = $('#interrogatearea').combobox('getValue');
    $('#areaId_exp').val(areaId);
    $('#userName_exp').val($('#o_user_name').val());
    $('#orderNo_exp').val($('#o_order_no').val());
    $('#personName_exp').val($('#o_person_name').val());
    document.getElementById("exportForm").submit();

}

//查询
function SearchOrderRequest() {
    var areaId = $('#interrogatearea').combobox('getValue');
    $('#datagrid_orderrequest').datagrid('load', {
        userName: $('#o_user_name').val(),
        orderNo: $('#o_order_no').val(),
        personName: $('#o_person_name').val(),
        areaId: areaId,
        trefresh: new Date().getTime()
    });
    //清除右侧数据
    $('#datagrid_orderstatus_person').datagrid('load', {
        orderRequestId: -1
    });
}

//清除查询条件
function doClearOrderRequest() {
    $('#o_user_name').prop('value', '');
    $('#o_person_name').prop('value', '');
    $('#o_order_no').prop('value', '');
    $('#interrogatearea').combobox('setValue', '');
}

// 预约信息添加start
function orderRequestAdd() {
    if (xtstatus) {
        $.messager.alert('提醒', '中心系统已停止办案');
        return;
    }
    AREAID = "";
    $('#planTime').combo({
        disabled: false
    });
    $("#jz").attr('disabled', false);
    $("#order_data_dialog").show();
    showdiv("#jibenxinxi");
    $('#planTime').combo({
        disabled: false
    });
    $('#form_orderrequest').form('clear');
    if ($('#dataInUserNo').val() == '') {
        $('#dataInUserNo').val('请输入警号');
    }
    $('#0').combogrid("reload");
    $('#type').combobox('setValue', 0);
    $('#planTime').combobox('setValue', 2);
    $('#maleCount').val(0);
    $('#femaleCount').val(0);
    $('#juvenilesCount').val(0);
    $('#specialCount').val(0);
    $('#orderRequestId').val("");
    $("#link_case_ajmc").val("未关联案件");
    $('#dataInUserNo').focus();
    $('#selectJzLable').html('<font style="color: red">未关联警综平台数据！</font>');
    $('#jzCaseNumber').val('');
    hiddendiv("#suspectinfo");
    $("#step1").addClass("now");
    $("#step1").removeClass("active");
    $("#step2").removeClass("now");
    loadOrderArea($("#sysAreaId").val());
    loadperson();
    initAjlxNew();
}

// 预约信息添加end
//预约结束
function endOrder() {
    refreshOrderId = "";
    $("#person_name").val("");
    $("#person_certificate_no").val("");
    showdiv("#jibenxinxi");
    hiddendiv("#suspectinfo");
    $('#form_orderrequest').form('clear');
    $('#out_two').removeClass("active");
    $('#order_data_dialog').hide();
}

// 预约信息取消start
function orderRequestCancel(index) {
    var rowData = $('#datagrid_orderrequest').datagrid('getRows')[index];
    var status = rowData.status;
    var currentStatus = '';
    jQuery.ajax({
        type: 'POST',
        url: 'queryStatus.do',
        data: {orderRequestId: rowData.id},
        dataType: 'json',
        success: function (data) {
            currentStatus = data.status;
            if (currentStatus != null && status == currentStatus) {
                $('#orderRequestId').val(rowData.id);
                $('#status').val(3);// 取消预约，status变为3
                var OrderRequestForm = $('#form_orderrequest');
                var OrderRequestFormJson = JSON.stringify(OrderRequestForm
                    .serializeObject());
                $.messager.confirm('提示', '确定取消预约?', function (r) {
                    if (r) {
                        $.messager.progress({
                            title: '请等待',
                            msg: '取消预约中...'
                        });
                        jQuery.ajax({
                            type: 'POST',
                            contentType: 'application/json',
                            url: 'changestatus.do',
                            data: OrderRequestFormJson,
                            dataType: 'json',
                            success: function (data) {
                                $.messager.show({
                                    title: data.title,
                                    msg: data.content,
                                    timeout: 3000
                                });
                                var areaId = $('#interrogatearea').combobox('getValue');
                                $('#datagrid_orderrequest').datagrid('load', {
                                    userName: $('#o_user_name').val(),
                                    orderNo: $('#o_order_no').val(),
                                    personName: $('#o_person_name').val(),
                                    areaId: areaId,
                                    trefresh: new Date().getTime()
                                });
                                $('#datagrid_orderstatus').datagrid('reload', {trefresh: new Date().getTime()});// reload
                                $.messager.progress('close');
                                $('#addPerson').linkbutton('disable');
                            },
                            error: function (data) {
                                $.messager.progress('close');
                                $.messager.alert('错误', '未知错误!');
                            }
                        });

                    }
                });
            }
            else {
                alert("请刷新");
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

// 预约信息取消end

// 延期
function orderRequestDelay(index) {
    var rowData = $('#datagrid_orderrequest').datagrid('getRows')[index];
    var status = rowData.status;
    var currentStatus = '';
    jQuery.ajax({
        type: 'POST',
        url: 'queryStatus.do',
        data: {orderRequestId: rowData.id},
        dataType: 'json',
        success: function (data) {
            currentStatus = data.status;
            if (currentStatus != null && status == currentStatus) {
                showDialog('#delay_orderrequest', '预约延期');
                $('#form_orderrequest_delay').form('clear');
                $('#delayId').val(rowData.id);
                $('#planTimeDelay').val(rowData.planTime);
                url = 'delay.do';
            }
            else {
                alert("请刷新");
            }

        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

// 预约信息修改start
function orderRequestEdit(index) {
    $('#jzCaseNumber').val('');
    $('#selectJzLable').html('');
    jzPersonJson = "";
    var rowData = $('#datagrid_orderrequest').datagrid('getRows')[index];
    console.log(rowData);
    var status = rowData.status;
    var currentStatus = '';
    jQuery.ajax({
        type: 'POST',
        url: 'queryStatus.do',
        data: {orderRequestId: rowData.id},
        dataType: 'json',
        success: function (data) {
            currentStatus = data.status;
            if (currentStatus != null && status == currentStatus) {
                if (data.jzCaseNumber != null && data.jzCaseNumber != "") {
                    if (data.caseName != null) {
                        $('#selectJzLable').html('<font style="color: green">已关联警综平台数据，案件名称为(' + data.caseName + ')。</font>');
                    } else if (data.jzCaseNumber != null) {
                        $('#selectJzLable').html('<font style="color: green">已关联警综平台数据，案件编号为(' + data.jzCaseNumber + ')。</font>');
                    } else {
                        $('#selectJzLable').html('<font style="color: green">已关联警综平台数据。</font>');
                    }
                    $('#jzCaseNumber').val(data.jzCaseNumber);
                } else {
                    $('#selectJzLable').html('<font style="color: red">未关联警综平台数据！</font>');
                }
                if (currentStatus == 2) {
                    flag = true;
                } else {
                    flag = false;
                }
                $("#jz").attr('disabled', flag);
                showDialog('#editorder_data_dialog', '修改预约信息');
//                showdiv("#jibenxinxi");
//                hiddendiv("#suspectinfo");
                // 证件

                $('#editform_orderrequest').form('clear');

                $('#editorderNo').val(rowData.orderNo);
                $('#editorderRequestId').val(rowData.id);
                $('#addIdValue').val(rowData.id);
                //电话
                $('#edituserMobile').val(rowData.userMobile);
                $('#edittype').combobox('setValue', rowData.type);
                $('#editplanTime').combobox({
                    disabled: true
                });
                var planTime = (rowData.planTime - rowData.createdTime) / 1000 / 60;//分钟
                var planTimeSelected = '2';
                if (planTime < 30) {
                    planTimeSelected = '1';
                } else if (planTime >= 30 && planTime < 60) {
                    planTimeSelected = '2';
                } else if (planTime >= 60 && planTime < 90) {
                    planTimeSelected = '3';
                } else {
                    planTimeSelected = '4';
                }

                $('#editplanTime').combobox('setValue', planTimeSelected);
                var user = rowData.orderUserId;
                $('#editorderUserId').val(user);
                queryUserNo(user);
                $('#editdataInUserNo').focus();
                loadOrderArea(rowData.areaId);
                $('#editinterpreter').combobox('setValue', rowData.interpreter);
                $('#editstatus').val(currentStatus);
//                $('#editjbaq').text(rowData.description);
                $('#editjbaq').textbox('setValue', rowData.description);
//                if(rowData.jbaq != null ){
//                    $("#link_case_ajmc").val("已关联案件："+rowData.jbaq);
//                }else{
//                    $("#link_case_ajmc").val("未关联案件");
//                }
                $('#editajlx').combobox('setValue', rowData.ajlx);
                $('#editab').combobox('setValue', rowData.ab);
            }
            else {
                $.messager.alert('错误', "请刷新");
            }
            $("#step1").addClass("now");
            $("#step1").removeClass("active");
            $("#step2").removeClass("now");
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
    loadOrderArea();
    initAjlxEdit();
}

// 预约信息修改end

//预约信息保存start
function editOrder() {
    if (!checkForm('#editform_orderrequest')) {
        $.messager.alert('提示', '请补全信息!');
        return;
    }

    var OrderRequestForm = $('#editform_orderrequest').serializeObject();
    var OrderRequestFormJson = JSON.stringify(OrderRequestForm);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: 'edit.do',
        data: {form: OrderRequestFormJson},
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: "提示",
                msg: data.content,
                timeout: 3000
            });
            $('#editorder_data_dialog').dialog('close');
//			showDialog('#editorder_data_dialog', '修改预约信息');
            $('#datagrid_orderrequest').datagrid('load', {
                trefresh: new Date().getTime()
            });
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '保存失败!' + data.content);
        }
    });
}

// 预约状态清除start
function doClearOrderStatus() {
    $('#s_ordertatus_status').prop('value', '');
}

// person 添加 start
function orderRequestPersonRadd(index) {
    var rowData = $('#datagrid_orderrequest').datagrid('getRows')[index];
    var value = rowData.id;
    showDialog('#dialog_orderrequest_person', '新增预约人员信息');
    $('#contentId').val(value);
    $('#form_orderrequest_person').form('clear');
    url = 'addOrderRequestPerson.do?orderId=' + value;

}

// person 添加 end

// person 修改 start
function orderRequestPersonEdit(index) {
    $('#addpersoninfoform').form('clear');
    loadSpecialIdentity();
    loadNation();
    loadSexadd();
    codeCombo('addperson_country', 'GJID', 156);
    var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
    $('#datagrid_orderstatus_person').datagrid('load', {orderRequestId: rowData.id, trefresh: new Date().getTime()});
    var rowData1 = $('#datagrid_orderstatus_person').datagrid('getRows')[index];
    console.log(rowData1)
    if (rowData1.ajbh != null && rowData1.rybh != null) {
        $('#selectXqJzLable').html('<font style="color: green">已关联执法办案平台数据，案件名称为(' + rowData1.ajmc + ')。</font>');
    } else {
        $('#selectXqJzLable').html('<font style="color: red">未关联执法办案平台数据！</font>');
    }

    showDialog('#dialog_add_person', '编辑人员信息');
    $('#addorderPersonId').val(rowData1.id);
    $('#addpersonId').val(rowData1.personId);
    $('#addperson_name').val(rowData1.name);
    $('#addareaId').val(AREAID);
    var zjzl = rowData1.certificateTypeId;
    $('#addperson_certificate_type').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#addperson_certificate_type').combobox('setValue', zjzl);
        }
    });
    //加载人员类型
    loadPersonInfoEdit('#addperson_personInfo', rowData1.personType);

    $('#addspecial_identity').combobox('setValue', rowData1.other);
    if (rowData1.country != null && rowData1.country != 0) {
        codeCombo('addperson_country', 'GJID', rowData1.country);
    }
    if (rowData1.nation != null && rowData1.nation != 0) {
        $('#addperson_nation').combobox('setValue', rowData1.nation);
    }
    $('#addperson_certificate_type').combobox('select', rowData1.certificateTypeId);
    $('#addperson_certificate_no').val(rowData1.certificateNo);
    var sex = rowData1.sex;
    $('#addperson_sex').combobox('select', sex);

    if (rowData1.isJuvenile == 1) {
        $("input[name='isJuvenile'][value='1']").prop("checked", true);
    } else {
        $("input[name='isJuvenile'][value='0']").prop("checked", true);
    }
    if (rowData1.isGravida == 1) {
        $("input[name='isGravida'][value='1']").prop("checked", true);
    } else {
        $("input[name='isGravida'][value='0']").prop("checked", true);
    }
    $('#addgravidaMonth').val(rowData1.gravidaMonth);

    if (rowData1.isDiseaseProve == 1) {
        $("input[name='isGravidaProve'][value='1']").prop("checked", true);
    } else {
        $("input[name='isGravidaProve'][value='0']").prop("checked", true);
    }
    if (rowData1.isDisease == 1) {
        $("input[name='isDisease'][value='1']").prop("checked", true);
    } else {
        $("input[name='isDisease'][value='0']").prop("checked", true);
    }
    if (rowData1.jkb == 1) {
        $("input[name='jkb'][value='1']").prop("checked", true);
    } else {
        $("input[name='jkb'][value='0']").prop("checked", true);
    }
    if (rowData1.sfcrgfxdq != "无" && rowData1.sfcrgfxdq !=null ) {
        $("#addsfcrgfxdq").val(rowData1.sfcrgfxdq );
        $("input[name='sfcrgfxdq1'][value='1']").prop("checked", true);
    } else {
        $("input[name='sfcrgfxdq1'][value='0']").prop("checked", true);
        $("#addsfcrgfxdq").val(rowData1.sfcrgfxdq );
    }

    $('#addjzyms').val(rowData1.jzyms);
    $('#addxyrtw').val(rowData1.xyrtw);
    if (rowData1.zjdz != null) {
        $('#addzjdz').val(rowData1.zjdz.replace("%20", ""));
    }
    $('#addzhdz').val(rowData1.zhdz);
    $('#addzjfjrq').datetimebox('setValue', valueToDate(rowData1.zjfjrq));
    if (rowData1.sfsjyqldaj == 1) {
        $("input[name='sfsjyqldaj'][value='1']").prop("checked", true);
    } else {
        $("input[name='sfsjyqldaj'][value='0']").prop("checked", true);
    }

    if (rowData1.j3gysfyjjcgqk != '无'&&rowData1.j3gysfyjjcgqk !=null) {
        $("#addj3gysfyjjcgqk").val(rowData1.j3gysfyjjcgqk );
        $("input[name='j3gysfyjjcgqk1'][value='1']").prop("checked", true);
    } else {
        $("input[name='j3gysfyjjcgqk1'][value='0']").prop("checked", true);
        $("#addj3gysfyjjcgqk").val(rowData1.j3gysfyjjcgqk );
    }
    if (rowData1.sfymjjcs == 1) {
        $("input[name='sfymjjcs'][value='1']").prop("checked", true);
    } else {
        $("input[name='sfymjjcs'][value='0']").prop("checked", true);
    }

    url = 'editPersonRight.do';

}

//function saveEditSuspectinfo(){
//	alert(111);
//	if (!checkForm('#form_orderrequest_person')) {
//	 	$.messager.alert('提示', '请补全信息!');
//	 	return;
//	}
//
//	var personList = $("#form_orderrequest_person").serializeObject();;
//	personList["orderRequestId"]=orderRequestDataId;
//	personList["areaId"]=AREAID;
//	var personsJson = JSON.stringify(personList);
//
//	$.messager.progress({
//		title : '请等待',
//		msg : '添加/修改数据中...'
//	});
//	jQuery.ajax({
//		type : 'POST',
//	     contentType : 'application/json',
//		url : url,
//		 data : personsJson,
//		dataType : 'json',
//		success : function(data) {
//			//2016-1-12 添加刷新
//			$('#dialog_orderrequest_person').dialog('close');
//			$.messager.alert('提示',  data.content);
//			$.messager.progress('close');
//		},
//		error : function(data) {
//			$.messager.alert('错误', '保存失败!' + data.content);
//		}
//	});
//}
// person修改 end

// person 删除 start
function orderRequestPersonRemove(index) {
    var rowData = $('#datagrid_orderstatus_person').datagrid('getRows')[index];
    var value = rowData.id;
    var orderData = $('#datagrid_orderrequest').datagrid('getSelected');
    var orderRequestId = orderData.id;
    $.messager.confirm('Confirm', '确定删除?', function (r) {
        if (r) {
            $.messager.progress({
                title: '请等待',
                msg: '删除数据中...'
            });
            jQuery.ajax({
                type: 'POST',
                url: 'removeOrderRequestPerson.do',
                data: {
                    "orderPersonId": value,
                    "personId": rowData.personId,
                    "orderRequestId": orderRequestId
                },
                dataType: 'json',
                success: function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.content,
                        timeout: 3000
                    });
                    $.messager.progress('close');
                    var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
                    if (rowData) {
                        $('#datagrid_orderstatus_person').datagrid('load', {
                            orderRequestId: rowData.id,
                            trefresh: new Date().getTime()
                        });
                    }
                    loadperson(rowData.id);
                },
                error: function (data) {
                    $.messager.progress('close');
                    $.messager.alert('错误', '未知错误!');
                }
            });

        }
    });
}

// person 删除 end


function CurentTime() {
    var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();
    var clock = year + "-";
    if (month < 10)
        clock += "0";
    clock += month + "-";
    if (day < 10)
        clock += "0";
    clock += day + " ";
    if (hh < 10)
        clock += "0";
    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm;
    clock += ":";
    if (ss < 10) clock += '0';
    clock += ss;
    return (clock);
}

function getDatetime(data) {
    var dt = new Date(data);
    return (dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate()
        + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt
            .getSeconds()).replace(/([\-\: ])(\d{1})(?!\d)/g, '$10$2');
}

function orderstatusreLoad(rowData) {
    $('#datagrid_orderstatus').datagrid('load', {
        orderRequestId: rowData.id,
        trefresh: new Date().getTime()
    });
}

//读取身份证信息
function readCard() {
    var browsern = browser["browser"];
    if (!(browsern == "Microsoft IE")) {
        $.messager.alert('提示', '请使用IE浏览器');
        return;
    }
    var ret = GWQ.DoGWQ_ReadID();
    if (ret == 0) {
        $.messager.alert('提示', "启动成功，请在设备上读取身份证信息");
    }
    else {
        $.messager.alert("失败，请检查设备是否打开并启动APP");
    }
}

//保存读取身份证返回的信息
function saveReadCardData(ErrorCode, JsonData) {
    console.log(JsonData);
    if (ErrorCode == 0) {
        var obj = JSON.parse(JsonData);
        if (obj.name != null && obj.name != '') {
            $("#person_name").val(obj.name.trim());
        }
        var pSex;
        if (obj.sex == '男') {
            pSex = '1';
        } else if (obj.sex == '女') {
            pSex = '2';
        }
        $("#person_sex").combobox("setValue", pSex);
        $("#person_certificate_no").val(obj.id_num);
        $("#person_certificate_type").combobox("setValue", 111);
        var nationData = $("#person_nation").combobox("getData");
        if (nationData != null) {
            $.each(nationData, function (name, value) {
                if (value.codeValue != null && value.codeValue != "" && value.codeValue == obj.nation) {
                    $("#person_nation").combobox("setValue", value.codeKey);
                }
            });
        }
    } else if (ErrorCode == -9) {
        $.messager.alert("取消");
    } else {
        $.messager.alert("失败，返回错误码为" + ErrorCode);
    }
}

// 加载案件类型
function initAjlxNew() {
    $('#ajlxNew').combobox({
        url: contextPath + "/zhfz/common/code/listCodeByType.do?type=AJLX&tresh=" + new Date().getTime(),
        valueField: 'codeKey',
        textField: 'codeValue',
        onChange: function (newValue, oldValue) {
            var natureValue = $("#ajlxNew").combobox("getText");
            //加载犯罪类型
            $('#abNew').combobox({
                url: '../../bacs/combobox/listcrimetypebynature.do?nature=' + encodeURI(natureValue, "UTF-8") + '&tresh=' + new Date().getTime(),
                valueField: 'id',
                textField: 'value',
                onLoadSuccess: function (data) {

                }
            });
        }
    });
}


//加载案件类型修改
function initAjlxEdit() {
    $('#editajlx').combobox({
        url: contextPath + "/zhfz/common/code/listCodeByType.do?type=AJLX&tresh=" + new Date().getTime(),
        valueField: 'codeKey',
        textField: 'codeValue',
        onChange: function (newValue, oldValue) {
            var natureValue = $("#ajlxNew").combobox("getText");
            //加载犯罪类型
            $('#editab').combobox({
                url: '../../bacs/combobox/listcrimetypebynature.do?nature=' + encodeURI(natureValue, "UTF-8") + '&tresh=' + new Date().getTime(),
                valueField: 'id',
                textField: 'value',
                onLoadSuccess: function (data) {

                }
            });
        }
    });
}
