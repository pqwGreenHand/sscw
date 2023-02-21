var AREAID = "";
var orderRequestDataId = "";
var refreshOrderId = "";
var jzPersonJson = "";
var flag = false;
var xtstatus = false;
var ids = "";
//保存当前浏览器信息
var browser;
// 数据展现层
$(function () {
    caseAjlyCode = "1";
    onloadPerson();
    loadSpecialIdentity();
    queryXtStatus();
    $('#jzInfo_day').next('.combo').find('input').blur(function () {
        var r = /^\+?[1-9][0-9]*$/;
        var str = $('#jzInfo_day').combobox('getValue');
        if (!r.test(str)) {
            $.messager.alert('提示', '请输入大于0的整数!');
        }
    });
    browser = myBrowser();
});

function queryXtStatus() {
    //查询xt_status,是否停止办案 如果是的话 页面弹框提示1停止2启用
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'queryXtStatus.do',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            if (data == '1') {
                xtstatus = true
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '停止办案查询失败!');
        }
    });
}

//开始预约点击下一步
function orderRequestAddNext(next) {
    //隐藏添加
    $('#dialog_add_suspectinfo').hide();
    var id = $('#id').val();
    var value1 = $('#dataInUserNo').val();
    var value2 = $('#maleCount').val() == "" ? 0 : $('#maleCount').val();
    var value3 = $('#femaleCount').val() == "" ? 0 : $('#femaleCount').val();
    var value4 = $('#juvenilesCount').val() == "" ? 0 : $('#juvenilesCount').val();
    var value5 = $('#specialCount').val() == "" ? 0 : $('#specialCount').val();
    if (!checkPositiveInteger(value2)) {
        $.messager.alert('提示', '男性数量请输入大于等于0的整数!');
        return;
    }
    if (!checkPositiveInteger(value3)) {
        $.messager.alert('提示', '女性数量请输入大于等于0的整数!');
        return;
    }
    if (!checkPositiveInteger(value4)) {
        $.messager.alert('提示', '未成年人数量请输入大于等于0的整数!');
        return;
    }
    if (!checkPositiveInteger(value5)) {
        $.messager.alert('提示', '传染病人数量请输入大于等于0的整数!');
        return;
    }
    var value9 = $('#caseId').val();
    var count = parseInt(value2) + parseInt(value3) + parseInt(value4) + parseInt(value5);
    if (count == 0) {
        $.messager.alert('提示', '嫌疑人人数不得小于一人!');
        return;
    }
    if ($("#orderarea").val() == null || $("#orderarea").val() == "") {
        $.messager.alert('提示', '请选择办案场所!');
        return;
    }
    var countStr = value2 + "@" + value3 + "@" + value4 + "@" + value5 + "@" + $("#orderarea").val();
    if (value1 != null && value1 != "" && count > 0) {
        if (count > 0) {
            $('#person_name').val();
            $('#certificateNo').val();
            $('#person_certificate_type').combobox(
                {
                    url: '../../common/combobox/certificateTypes.do',
                    valueField: 'id',
                    textField: 'value',
                    onLoadSuccess: function (data) {
                        $('#person_certificate_type').combobox('setValue', data[0].id);
                    },
                    onChange: function (n, o) {
                        setWuMingShi();
                    }
                });
            // 判断是否保存 调用后台check方法
            jQuery.ajax({
                type: 'POST',
                url: 'checkPersonCount.do',
                data: {
                    countStr: countStr
                },
                dataType: 'json',
                success: function (data) {
                    // $.messager.alert('提示',data[0]);
                    if ("success" == data[0]) {
                        //验证警号start--------------------------------------start
                        var userNo = $('#dataInUserNo').val();
                        var userNoInfo = $('#userNoInfo').serializeObject();
                        userNoInfo["userNo"] = userNo;
                        var jsonrtinfo = JSON.stringify(userNoInfo);
                        jQuery.ajax({
                            type: 'POST',
                            contentType: 'application/json',
                            dataType: 'json',
                            url: 'searchUser.do',
                            data: jsonrtinfo,
                            success: function (data) {
                                if (data != null && data.id != null) {
                                    $("#orderUserId").val(data.id);
                                    $('#orderUserName').val(data.realName);
                                    hiddendiv("#jibenxinxi");

                                    showdiv("#suspectinfo");
                                    $("#step2").addClass("now");
                                    $("#step1").addClass("active");
                                    $("#step1").removeClass("now");
                                    saveOrderRequest();
                                    loadperson(id);

                                } else {
                                    $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');

                                }
                            },
                            error: function (data) {
                                $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

                            }
                        });
                    } else {
                        $.messager.confirm('提示', data[0] + '您确定要继续？', function (r) {
                            if (r) {
                                //验证警号start--------------------------------------start
                                var userNo = $('#dataInUserNo').val();
                                var userNoInfo = $('#userNoInfo').serializeObject();
                                userNoInfo["userNo"] = userNo;
                                var jsonrtinfo = JSON.stringify(userNoInfo);
                                jQuery.ajax({
                                    type: 'POST',
                                    contentType: 'application/json',
                                    dataType: 'json',
                                    url: 'searchUser.do',
                                    data: jsonrtinfo,
                                    success: function (data) {
                                        if (data != null && data.id != null) {
                                            $("#orderUserId").val(data.id);
                                            $('#orderUserName').val(data.realName);
                                            hiddendiv("#jibenxinxi");
                                            showdiv("#suspectinfo");
                                            $("#step2").addClass("now");
                                            $("#step1").addClass("active");
                                            $("#step1").removeClass("now");
                                            saveOrderRequest();
                                            loadperson(id);
                                        } else {
                                            $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
                                            $('#dataInUserNo1').focus();
                                        }
                                    },
                                    error: function (data) {
                                        $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

                                    }
                                });
                            }
                        });
                    }
                },
                error: function (data) {
                    $.messager.progress('close');
                    $.messager.alert('错误', '保存失败（checkPersonCount）!');
                }
            });
        } else {
            $.messager.alert('提示', '嫌疑人人数不得小于一人!');
        }

    } else {
        $.messager.alert('提示', '请补全信息!');
    }
}


// 预约信息删除start
function orderRequestRemove(index) {
    var rowData = $('#datagrid_orderrequest').datagrid('getRows')[index];
    var value = rowData.id;
    $.messager.confirm('Confirm', '确定删除?',
        function (r) {
            if (r) {
                $.messager.progress({
                    title: '请等待',
                    msg: '删除数据中...'
                });
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'removeOrderRequest.do?orderRequestId=' + value,
                    dataType: 'json',
                    success: function (data) {
                        $.messager.progress('close');
                        $.messager.show({
                            title: '提示',
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
                        personRefrsh()
                    },
                    error: function (data) {
                        $.messager.progress('close');
                        $.messager.alert('Error', '未知错误!');
                    }
                });

            }
        });
}


// 保存延期
function saveDelayRequest() {
    $('#form_orderrequest').form('clear');
    var OrderRequestForm = $('#form_orderrequest_delay');
    $('#delayStatus').val(1);// 延期预约，status变为1
    var OrderRequestFormJson = JSON.stringify(OrderRequestForm
        .serializeObject());
    if ($("#delayTime").combobox("getValue") == null) {
        $.messager.alert("请选择延期时间");
    }
    $.messager.progress({
        title: '请等待',
        msg: '正在保存数据...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: OrderRequestFormJson,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $.messager.show({
                title: '提示',
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
            $('#delay_orderrequest').dialog('close');
            $.messager.progress('close');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '保存失败（edit）!');
        }
    });
}

// 预约信息保存start
function saveOrderRequest() {
    if (!checkForm('#form_orderrequest')) {
        $.messager.alert('提示', '请补全信息!');
        return;
    }

    var OrderRequestForm = $('#form_orderrequest').serializeObject();
    var OrderRequestFormJson = JSON.stringify(OrderRequestForm);
    var personList = $("#form_orderrequest_personList").datagrid('getData');
    console.log(personList);
    var personsJson = JSON.stringify(personList);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: 'addOrderRequest.do',
        data: {form: OrderRequestFormJson, persons: personsJson},
        dataType: 'json',
        success: function (data) {
            JzCaseInfo = null;
            jzPersonJson = null;
            v = data.title.split("@");
            $('#orderNo').val(v[1]);
            $('#id').val(v[0]);
            $('#addIdValue').val(v[0]);
            //2016-1-12 添加刷新
            refreshOrderId = v[0];
            $('#form_orderrequest_personList').datagrid('load', {
                orderRequestId: refreshOrderId,
                trefresh: new Date().getTime()
            });
            $('#datagrid_orderstatus_person').datagrid('load', {
                orderRequestId: refreshOrderId,
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
            $('#person_name').val();
            $('#person_certificate_no').val();
            $('#order_data_dialog').hide();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '保存失败!' + data.content);
        }
    });
}

// 预约信息保存end

// 预约状态清除end
// 验证警察编号是否存在searchInUser1
function finduser(tagName) {
    var userNo = $('#dataInUserNo').val();
    if (userNo == null || userNo.length == 0) {
        return;
    } else {
        if (userNo.indexOf('(') >= 0) {
            userNo = userNo.substring(0, userNo.indexOf('('));
        }
    }
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"] = userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: 'searchUser.do',
        data: jsonrtinfo,
        success: function (data) {
            if (data != null && data.id != null) {
                $('#' + tagName).val(userNo + "(" + data.realName + ")");
                $("#orderUserId").val(data.id);
                $('#orderUserName').val(data.realName);
            } else {
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
            }
        },
        error: function (data) {
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

        }
    });
}

//校验手机号 用中文、隔开 只要校验位数
function checkMobile(mobileStr) {
    //13521520973、18911578319
    if (mobileStr.length != 23 && mobileStr.length != 11) {
        $.messager.alert('提示', '手机号码填写不正确,手机号码之间用中文、隔开！');
    }

}


//xiugai
function finduseredit(tagName) {
    var userNo = $('#editdataInUserNo').val();
    if (userNo == null || userNo.length == 0) {
        return;
    } else {
        if (userNo.indexOf('(') >= 0) {
            userNo = userNo.substring(0, userNo.indexOf('('));
        }
    }
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"] = userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: 'searchUser.do',
        data: jsonrtinfo,
        success: function (data) {
            if (data != null && data.id != null) {
                $('#' + tagName).val(userNo + "(" + data.realName + ")");
                $("#editorderUserId").val(data.id);
                $('#editorderUserName').val(data.realName);
            } else {
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
            }
        },
        error: function (data) {
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

        }
    });
}

//添加新嫌疑人信息
function saveAddSuspectinfo(formName) {

    if ($("#person_certificate_type").combobox("getValue") == 111) {
        var certificateNo = $("#person_certificate_no").val();
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


    if (!checkForm(formName)) {
        $.messager.alert('错误', '信息填写不正确！');
        return;
    }
    //是否未成年
    var isJuvenile =$("input[name='isJuvenile']:checked").val();
    //是否孕妇
    var isGravida =$("input[name='isGravida']:checked").val();
    //怀孕月份
    var gravidaMonth = $('#gravidaMonth').val();
    if (gravidaMonth == null || gravidaMonth == "") {
        gravidaMonth = 0;
    }
    //是否有怀孕证明
    var isGravidaProve =$("input[name='isGravidaProve']:checked").val();
    //是否有其它疾病
    var isDisease =$("input[name='isDisease']:checked").val();
    //是否有疾病证明
    var isDiseaseProve =$("input[name='isDiseaseProve']:checked").val();
    var jzyms = $('#jzyms').val();
    if (jzyms == null || jzyms == "") {
        jzyms = 0;
    }
    //健康
    var jkb =$("input[name='jkb']:checked").val();
    //健康
    // var sfcrgfxdq =$("input[name='sfcrgfxdq']:checked").val();
    var sfcrgfxdq =$('#sfcrgfxdq').val();

    var xyrtw = $('#xyrtw').val();
    var zjdz = $('#zjdz').val().replace("%20","");
    var zhdz = $('#zhdz').val();

    var sfsjyqldaj =$("input[name='sfsjyqldaj']:checked").val();
    // var j3gysfyjjcgqk =$("input[name='j3gysfyjjcgqk']:checked").val();
    var j3gysfyjjcgqk =$('#j3gysfyjjcgqk').val();

    var sfymjjcs =$("input[name='sfymjjcs']:checked").val();
    var zjfjrq = $('#zjfjrq').datetimebox('getValue');


    var name = $('#person_name').val();
    var sex = $('#person_sex').combobox('getValue');
    //人员类型
    var personInfo = $('#person_personInfo').combobox('getValue');
    var specialIdentity = $('#special_identity').combobox('getValue');
    var personCountry = $('#person_country').combobox('getValue');
    var personNation = $('#person_nation').combobox('getValue');
    var person_certificate_type = $('#person_certificate_type').combobox('getValue');
    var person_certificate_no = $('#person_certificate_no').val();

    var ajbh = $('#ajbh').val();
    var rybh = $('#rybh').val();
    var ajmc = $('#ajmc').val();

    if (name != null && name != "" && sex != null && sex != ""
        && person_certificate_type != null && person_certificate_type != ""
        && person_certificate_no != null && person_certificate_no != "") {
        if (person_certificate_type == 111) {
            //var idErrorMessage = checkId(person_certificate_no);
            var idErrorMessage = true;
            if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {
                $('#form_orderrequest_personList').datagrid('insertRow', {
                    row: {
                        personType: personInfo,
                        isJuvenile: isJuvenile,
                        jzyms: jzyms,
                        sfcrgfxdq: sfcrgfxdq,
                        jkb: jkb,
                        xyrtw: xyrtw,
                        zjdz: zjdz,
                        zhdz: zhdz,
                        sfsjyqldaj:sfsjyqldaj,
                        j3gysfyjjcgqk:j3gysfyjjcgqk,
                        sfymjjcs:sfymjjcs,
                        zjfjrq:zjfjrq,
                        isGravidaProve: isGravidaProve,
                        isGravida: isGravida,
                        gravidaMonth: gravidaMonth,
                        personCountry: personCountry,
                        personNation: personNation,
                        isDisease: isDisease,
                        isDiseaseProve: isDiseaseProve,
                        other: specialIdentity,
                        certificateTypeId: person_certificate_type,
                        name: name,
                        sex: sex,
                        certificateNo: person_certificate_no,
                        ajbh: ajbh,
                        rybh: rybh,
                        ajmc: ajmc
                    }
                });
                $("#dialog_add_suspectinfo").dialog('close');
            } else {
                $.messager.alert('提醒', idErrorMessage);
            }
        }
        else {
            $('#form_orderrequest_personList').datagrid('insertRow', {
                row: {
                    personType: personInfo,
                    jzyms: jzyms,
                    sfcrgfxdq: sfcrgfxdq,
                    jkb: jkb,
                    xyrtw: xyrtw,
                    zjdz: zjdz,
                    zhdz: zhdz,
                    sfsjyqldaj:sfsjyqldaj,
                    j3gysfyjjcgqk:j3gysfyjjcgqk,
                    sfymjjcs:sfymjjcs,
                    zjfjrq:zjfjrq,
                    isJuvenile: isJuvenile,
                    isGravidaProve: isGravidaProve,
                    isGravida: isGravida,
                    gravidaMonth: gravidaMonth,
                    isDisease: isDisease,
                    isDiseaseProve: isDiseaseProve,
                    personCountry: personCountry,
                    personNation: personNation,
                    other: specialIdentity,
                    certificateTypeId: person_certificate_type,
                    name: name,
                    sex: sex,
                    certificateNo: person_certificate_no,
                    ajbh: ajbh,
                    rybh: rybh,
                    ajmc: ajmc
                }
            });
            $("#dialog_add_suspectinfo").dialog('close');
        }
    } else {
        $.messager.alert('提醒', '信息填写不完整');
    }
}

//添加新嫌疑人信息
function saveEditSuspectinfo(formName) {

    if ($("#edit_certificateTypeId").combobox("getValue") == 111) {
        var certificateNo = $("#edit_certificateNo").val();
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


    if (!checkForm(formName)) {
        $.messager.alert('错误', '信息填写不正确！');
        return;
    }
    //是否未成年
    var isJuvenile = $("input[name='edit_eisJuvenile']:checked").val();
    //是否孕妇
    var isGravida = $("input[name='edit_eisGravida']:checked").val();
    //怀孕月份
    var gravidaMonth = $('#edit_egravidaMonth').val();
    if (gravidaMonth == null || gravidaMonth == "") {
        gravidaMonth = 0;
    }
    //是否有怀孕证明
    var isGravidaProve = $("input[name='edit_eisGravidaProve']:checked").val();
    //是否有其它疾病
    var isDisease =  $("input[name='edit_eisDisease']:checked").val();
    //是否有疾病证明
    var isDiseaseProve = $("input[name='edit_eisDiseaseProve']:checked").val();
    var name = $('#edit_name').val();
    var sex = $('#edit_sex').combobox('getValue');
    //人员类型
    var personInfo = $('#edit_personInfo').combobox('getValue');
    var specialIdentity = $('#edit_specialIdentity').combobox('getValue');
    var personCountry = $('#edit_country').combobox('getValue');
    var personNation = $('#edit_nation').combobox('getValue');
    var person_certificate_type = $('#edit_certificateTypeId').combobox('getValue');
    var person_certificate_no = $('#edit_certificateNo').val();

    var jzyms = $('#editjzyms').val();
    if (jzyms == null || jzyms == "") {
        jzyms = 0;
    }
    //健康
    var jkb =  $("input[name='editjkb']:checked").val();

    //健康
    var sfcrgfxdq =$("#editsfcrgfxdq").val();

    var xyrtw = $('#editxyrtw').val();
    var zjdz = $('#editzjdz').val();
    var zhdz = $('#editzhdz').val();
    var sfsjyqldaj =$("input[name='editsfsjyqldaj']:checked").val();
    var j3gysfyjjcgqk = $("editj3gysfyjjcgqk").val();
    var sfymjjcs = $("input[name='editsfymjjcs']:checked").val();
    var zjfjrq = $('#editzjfjrq').datetimebox('getValue');
    if ($("#orderPersonEditIndex").val() != '') {
        $('#dialog_orderrequest_person').dialog('close');
        $.messager.show({
            title: "提示",
            msg: "修改成功",
            timeout: 3000
        });

        $('#form_orderrequest_personList').datagrid('updateRow', {
            index: $("#orderPersonEditIndex").val(),
            row: {
                personType: personInfo,
                isJuvenile: isJuvenile,
                isGravidaProve: isGravidaProve,
                isGravida: isGravida,
                gravidaMonth: gravidaMonth,
                personCountry: personCountry,
                personNation: personNation,
                isDisease: isDisease,
                isDiseaseProve: isDiseaseProve,
                other: specialIdentity,
                certificateTypeId: person_certificate_type,
                name: name,
                sex: sex,
                jzyms: jzyms,
                sfcrgfxdq: sfcrgfxdq,
                jkb: jkb,
                xyrtw: xyrtw,
                zjdz: zjdz,
                zhdz: zhdz,
                sfsjyqldaj:sfsjyqldaj,
                j3gysfyjjcgqk:j3gysfyjjcgqk,
                sfymjjcs:sfymjjcs,
                zjfjrq:zjfjrq,
                certificateNo: person_certificate_no
            }
        });
    }
    else if (name != null && name != "" && sex != null && sex != ""
        && person_certificate_type != null && person_certificate_type != ""
        && person_certificate_no != null && person_certificate_no != "") {
        if (person_certificate_type == 111) {
            var idErrorMessage = checkId(person_certificate_no);
            if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {
                $('#form_orderrequest_personList').datagrid('updateRow', {
                    index: $("#orderPersonEditIndex").val(),
                    row: {
                        personType: personInfo,
                        isJuvenile: isJuvenile,
                        isGravidaProve: isGravidaProve,
                        isGravida: isGravida,
                        gravidaMonth: gravidaMonth,
                        personCountry: personCountry,
                        personNation: personNation,
                        isDisease: isDisease,
                        isDiseaseProve: isDiseaseProve,
                        other: specialIdentity,
                        certificateTypeId: person_certificate_type,
                        name: name,
                        sex: sex,
                        jzyms: jzyms,
                        sfcrgfxdq: sfcrgfxdq,
                        jkb: jkb,
                        xyrtw: xyrtw,
                        zjdz: zjdz,
                        zhdz: zhdz,
                        sfsjyqldaj:sfsjyqldaj,
                        j3gysfyjjcgqk:j3gysfyjjcgqk,
                        sfymjjcs:sfymjjcs,
                        zjfjrq:zjfjrq,
                        certificateNo: person_certificate_no
                    }
                });
                $('#dialog_orderrequest_person').dialog('close')
            } else {
                $.messager.alert('提醒', idErrorMessage);
            }
        }
        else {
            $('#form_orderrequest_personList').datagrid('updateRow', {
                index: $("#orderPersonEditIndex").val(),
                row: {
                    personType: personInfo,
                    isJuvenile: isJuvenile,
                    isGravidaProve: isGravidaProve,
                    isGravida: isGravida,
                    gravidaMonth: gravidaMonth,
                    isDisease: isDisease,
                    isDiseaseProve: isDiseaseProve,
                    personCountry: personCountry,
                    personNation: personNation,
                    other: specialIdentity,
                    certificateTypeId: person_certificate_type,
                    name: name,
                    sex: sex,
                    certificateNo: person_certificate_no
                }
            });
            $('#dialog_orderrequest_person').dialog('close')
        }
    } else {
        $.messager.alert('提醒', '信息填写不完整');
    }
}

function saveOrderRequestPerson1() {
    if (!checkForm('#form_orderrequest_person')) {
        return;
    }
    var certificateTypeId = $('#certificateTypeId').combobox('getValue');
    var person_certificate_no = $("#certificateNo").val();
    var idErrorMessage = "";
    if (certificateTypeId == 111) {
        idErrorMessage = checkId(person_certificate_no);
    }
    if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {
        var OrderRequestPersonForm = $('#form_orderrequest_person');
        //是否未成年
        var isJuvenileObj = document.getElementsByName("eisJuvenile");
        var isJuvenile = 0;
        if (isJuvenileObj.length > 0) {
            if (isJuvenileObj[0].checked == true) {
                isJuvenile = isJuvenileObj[0].value;
            }
        }
        //是否孕妇
        var isGravidaObj = document.getElementsByName("eisGravida");
        var isGravida = 0;
        if (isGravidaObj.length > 0) {
            if (isGravidaObj[0].checked == true) {
                isGravida = isGravidaObj[0].value;
            }
        }
        //怀孕月份
        var gravidaMonth = 0;
        if (gravidaMonth == null || gravidaMonth == "") {
            gravidaMonth = 0;
        } else {
            gravidaMonth = $('#egravidaMonth').val();
        }
        //是否有怀孕证明
        var isGravidaProveObj = document.getElementsByName("eisGravidaProve");
        var isGravidaProve = 0;
        if (isGravidaProveObj.length > 0) {
            if (isGravidaProveObj[0].checked == true) {
                isGravidaProve = isGravidaProveObj[0].value;
            }
        }
        //是否有其它疾病
        var isDiseaseObj = document.getElementsByName("eisDisease");
        var isDisease = 0;
        if (isDiseaseObj.length > 0) {
            if (isDiseaseObj[0].checked == true) {
                isDisease = isDiseaseObj[0].value;
            }
        }
        //是否有疾病证明
        var isDiseaseProveObj = document.getElementsByName("eisDiseaseProve");
        var isDiseaseProve = 0;
        if (isDiseaseProveObj.length > 0) {
            if (isDiseaseProveObj[0].checked == true) {
                isDiseaseProve = isDiseaseProveObj[0].value;
            }
        }
        OrderRequestPersonForm["isJuvenile"] = isJuvenile;
        OrderRequestPersonForm["other"] = $('#specialIdentity').combobox('getValue');
        OrderRequestPersonForm["isGravida"] = isGravida;
        OrderRequestPersonForm["gravidaMonth"] = gravidaMonth;
        OrderRequestPersonForm["isGravidaProve"] = isGravidaProve;
        OrderRequestPersonForm["isDisease"] = isDisease;
        OrderRequestPersonForm["isDiseaseProve"] = isDiseaseProve;
        OrderRequestPersonForm["country"] = $('#country').combobox('getValue');
        OrderRequestPersonForm["nation"] = $('#nation').combobox('getValue');

        var OrderRequestPersonFormJson = JSON.stringify(OrderRequestPersonForm
            .serializeObject());
        $.messager.progress({
            title: '请等待',
            msg: '添加/修改数据中...'
        });
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: url,
            data: OrderRequestPersonFormJson,
            dataType: 'json',
            success: function (data) {
                $.messager.progress('close');
                $('#person_name').val();
                $('#person_certificate_no').val();
                $.messager.show({
                    title: '提示',
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
                $('#dialog_orderrequest_person').dialog('close');
                //              $('#datagrid_orderstatus_person').datagrid('reload',{trefresh:new Date().getTime()} );// reload
                var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
                if (rowData != null) {
                    $('#datagrid_orderstatus_person').datagrid('load', {
                        orderRequestId: rowData.id,
                        trefresh: new Date().getTime()
                    });

                    $('#dialog_orderrequest_person').dialog('close');
                    loadperson(rowData.id);
                }

                $('#dialog_orderrequest_person').dialog('close');
                // location.reload();
                // $('#datagrid').datagrid('loadData',data);// reload the data
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert('错误', '保存失败!' + data.content);
            }
        });

    } else {
        $.messager.alert('提醒', idErrorMessage);
    }


}

function queryUserNo(id) {
    $.ajax({
        type: 'POST',
        url: 'queryUserNoById.do',
        dataType: 'json',
        data: {
            pid: id
        },
        success: function (data) {
            $('#dataInUserNo').val(data.certificateNo);
            $('#editdataInUserNo').val(data.certificateNo);
        },
        error: function () {
            $.messager.alert('错误', '查询失败（queryUserNoById）!');
        }
    });
}

// person 删除 start
function orderRequestPersonOtherRemove(orderPersonId, personId) {
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
                    "orderRequestId": $('#orderRequestId').val(),
                    "orderPersonId": orderPersonId,
                    "personId": personId
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
                    $.messager.alert('错误', '删除失败（removeOrderRequestPerson）!' + data);
                }
            });

        }
    });
}

//person 删除 end

function orderRequestReach(index) {
    var rowData = $('#datagrid_orderrequest').datagrid('getRows')[index];
    var orderRequestId = rowData.id;
    $.messager.confirm('提示', '状态更改为已到达?', function (r) {
        if (r) {
            $.messager.progress({
                title: '请等待',
                msg: '更改状态中...'
            });
            jQuery.ajax({
                type: 'POST',
                url: 'orderReach.do',
                dataType: 'json',
                data: {orderRequestId: orderRequestId},
                success: function (data) {
                    $.messager.show({
                        title: '提示',
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
                },
                error: function (data) {
                    $.messager.progress('close');
                    $.messager.alert('错误', '更改失败（orderReach）!');
                }
            });

        }
    });
}

// 关联嫌疑人信息
function initPersonInfo(ajbh) {
    $('#link_jzAjxx_person').combogrid({
        panelWidth: 450,
        mode: 'remote',
        url: contextPath + '/zhfz/bacs/order/listJzPerson.do',
        idField: 'id',
        textField: 'xm',
        queryParams: {
            ajbh: ajbh
        },
        frozenColumns: [[{
            title: 'id',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[
            {field: 'xm', title: '姓名', width: 120},
            {
                field: 'xb', title: '性别', width: 80,
                formatter: function (value, rec) {
                    if (value == 1) {
                        return "男";
                    }
                    if (value == 2) {
                        return "女";
                    }
                    if (value == 0) {
                        return "未知的性别";
                    }
                    if (value == 9) {
                        return "未说明的性别";
                    }
                    return "";
                }
            },
            {field: 'zjhm', title: '证件号码', width: 250}
        ]],
        onLoadSuccess: function (data) {
            //$('#caseId').combogrid('setValue',-1);
        },
        pagination: false,
        pageSize: 5,
        pageList: [5, 10],
        rownumbers: false,
        onChange: function (newValue, oldValue) {
            var cg = $('#link_jzAjxx_person').combogrid('grid');   // 获取数据表格对象
            var row = cg.datagrid('getSelected');   // 获取选择的行
            $('#xm').val(row.xm);
            $('#xb').val(row.xb);
            $('#zjhm').val(row.zjhm);
            $('#rybh').val(row.rybh);
        }
    });
}

function enterKeyEvents2() {
    var e = e || window.event;
    if (e.keyCode == 13) {
        var zjhm = $('#person_certificate_no').val();
        jingzongByzjhm(zjhm);
    }
}

function checkCertificateNo() {
    var certificateNo = $('#person_certificate_no').val();
    var person_certificate_type = $('#person_certificate_type').val();
    if (person_certificate_type == 111) {//身份证
        var idErrorMessage = checkId(certificateNo)
        if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {

        } else {
            $.messager.alert('提醒', idErrorMessage);
        }
    }

    if (18 == certificateNo.length) {
        // 判断性别
        var num = certificateNo.charAt(16);
        if (num % 2 == 0) {
            $('#person_sex').combobox('setValue', 2);
        } else {
            $('#person_sex').combobox('setValue', 1);
        }
        // 判断年龄
        var birthYear = certificateNo.substring(6, 10);
        var nowYear = new Date().getFullYear();
        if (nowYear - birthYear >= 18) {
            //alert("已成年" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '成年人');
        } else if (nowYear - birthYear >= 60) {
            //alert("老年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '老年人');
        } else if (nowYear - birthYear < 18) {
            //alert("未成年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", true);
            $('#person_personInfo').combobox('setValue', '未成年人');
        }
    } else if (15 == certificateNo.length) {
        // 判断性别
        var num = certificateNo.charAt(14);
        if (num % 2 == 0) {
            $('#person_sex').combobox('setValue', 2);
        } else {
            $('#person_sex').combobox('setValue', 1);
        }
        // 判断年龄
        var birthYear = certificateNo.charAt(6) + certificateNo.charAt(7);//年份
        if (parseInt(birthYear) < 14) {
            birthYear = '20' + birthYear;//年份
        }
        else {
            birthYear = '19' + birthYear;//年份
        }
        var nowYear = new Date().getFullYear();
        if (nowYear - birthYear >= 18) {
            //alert("已成年" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '成年人');
        } else if (nowYear - birthYear >= 60) {
            //alert("老年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '老年人');
        } else if (nowYear - birthYear < 18) {
            //alert("未成年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", true);
            $('#person_personInfo').combobox('setValue', '未成年人');
        }
    }


    /*var  zjhm =$('#person_certificate_no').val();
    jingzongByzjhm(zjhm);*/

    /* var e = e || window.event;
     if (e.keyCode == 13) {
         var  zjhm =$('#person_certificate_no').val();
         jingzongByzjhm(zjhm);
     }*/
}


function enterKeyEvents() {
    var e = e || window.event;
    if (e.keyCode == 13) {
        var zjhm = $('#edit_certificateNo').val();
        jingzongByzjhm(zjhm);
    }
}

function jingzongByzjhm(zjhm) {

    var caseName = "";
    var caseLb = $('#jzajlb').combobox('getValue');
    //var zjhm = $('#dataCertificateNo').val();
    var zjhm = zjhm;
    loadJZPersonByZJHM(zjhm);
    showDialog('#jz_orderrequest', '执法办案平台');
    $('#jzInfo').datagrid({
//		title:'人身安全检查',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: contextPath + '/zhfz/bacs/serial/queryJzAjxxByzjhm.do?zjhm=' + zjhm,
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        columns: [[
            {field: 'AJMC', title: '案件名称', width: 180},
            {field: 'AJBH', title: '案件编号', width: 120},
            {field: 'FXSJ', title: '案发时间', width: 60},
            {field: 'FADDXZ', title: '案发地址', width: 140}
        ]],
        pagination: true,
        pageList: [10, 20, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar_jzInfo',
        onSelect: function (rowIndex, rowData) {
            if (rowData.AJBH != null) {
                loadJZPerson(rowData.AJBH);
            }

        }
    });
}

function loadJZPersonByZJHM(zjhm) {

    $('#jzPerson')
        .datagrid(
            {
                title: '嫌疑人信息',
                iconCls: 'icon-datagrid',
                region: 'east',
                width: '100%',
                height: '100%',
                fitColumns: true,
                nowrap: false,
                striped: true,
                collapsible: false,
                loadMsg: 'Loading...',
                method: 'get',
                remoteSort: false,
                url: contextPath + '/zhfz/bacs/serial/queryJzPersonXs.do',
                // idField:'code',
                queryParams: {
                    zjhm: zjhm,
                    trefresh: new Date().getTime()
                },
                pagination: false,
                // pageList: [5, 10, 15, 20, 30, 50],
                rownumbers: true,
                singleSelect: false,
                frozenColumns: [[
                    {field: 'id', checkbox: true},
                    {
                        title: 'id',
                        field: 'id',
                        width: 10,
                        sortable: true,
                        hidden: true
                    }]],
                columns: [[
                    {field: 'RYXM', title: '姓名', width: 20},
                    {
                        field: 'XB', title: '性别', width: 20,
                        formatter: function (value, rec) {
                            if (1 == value) {
                                return "男";
                            } else if (2 == value) {
                                return "女";
                            } else {
                                return '未知';
                            }
                        }
                    },
                    {field: 'ZJHM', title: '证件编号', width: 50},
                    {field: 'XZDXZ', title: '暂住地址', width: 50}
                ]],
                rownumbers: true

            });
}

function enterKeyEvents3() {
    var e = e || window.event;
    if (e.keyCode == 13) {
        var zjhm = $('#addperson_certificate_no').val();
        jingzongByzjhm(zjhm);
    }
}

function checkCertificateNo2() {

    var certificateNo = $('#addperson_certificate_no').val();
    var person_certificate_type = $('#addperson_certificate_type').val();
    if (person_certificate_type == 111) {//身份证
        var idErrorMessage = checkId(certificateNo)
        if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {

        } else {
            $.messager.alert('提醒', idErrorMessage);
        }
    }
    if (18 == certificateNo.length) {
        // 判断性别
        var num = certificateNo.charAt(16);
        if (num % 2 == 0) {
            $('#addperson_sex').combobox('setValue', 2);
        } else {
            $('#addperson_sex').combobox('setValue', 1);
        }
        // 判断年龄
        var birthYear = certificateNo.substring(6, 10);
        var nowYear = new Date().getFullYear();
        if (nowYear - birthYear >= 18) {
            //alert("已成年" + birthYear + nowYear);
            $("#addisJuvenile").prop("checked", false);
            $('#addperson_personInfo').combobox('setValue', '成年人');
        } else if (nowYear - birthYear >= 60) {
            //alert("老年人" + birthYear + nowYear);
            $("#addisJuvenile").prop("checked", false);
            $('#addperson_personInfo').combobox('setValue', '老年人');
        } else if (nowYear - birthYear < 18) {
            //alert("未成年人" + birthYear + nowYear);
            $("#addisJuvenile").prop("checked", true);
            $('#addperson_personInfo').combobox('setValue', '未成年人');
        }
    } else if (15 == certificateNo.length) {
        // 判断性别
        var num = certificateNo.charAt(14);
        if (num % 2 == 0) {
            $('#addperson_sex').combobox('setValue', 2);
        } else {
            $('#addperson_sex').combobox('setValue', 1);
        }
        // 判断年龄
        var birthYear = certificateNo.charAt(6) + certificateNo.charAt(7);//年份
        if (parseInt(birthYear) < 14) {
            birthYear = '20' + birthYear;//年份
        }
        else {
            birthYear = '19' + birthYear;//年份
        }
        var nowYear = new Date().getFullYear();
        if (nowYear - birthYear >= 18) {
            //alert("已成年" + birthYear + nowYear);
            $("#addisJuvenile").prop("checked", false);
            $('#addperson_personInfo').combobox('setValue', '成年人');
        } else if (nowYear - birthYear >= 60) {
            //alert("老年人" + birthYear + nowYear);
            $("#addisJuvenile").prop("checked", false);
            $('#addperson_personInfo').combobox('setValue', '老年人');
        } else if (nowYear - birthYear < 18) {
            //alert("未成年人" + birthYear + nowYear);
            $("#addisJuvenile").prop("checked", true);
            $('#addperson_personInfo').combobox('setValue', '未成年人');
        }
    }
}

function jingzong() {
    var caseLb = $('#jzajlb').combobox('getValue');
    showDialog('#jz_orderrequest', '执法办案平台');
    $('#jzInfo').datagrid({
//		title:'人身安全检查',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: '../../bacs/serial/queryJzAjxx.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        columns: [[
            {field: 'AJMC', title: '案件名称', width: 180},
            {field: 'AJBH', title: '案件编号', width: 120},
            {field: 'FXSJ', title: '案发时间', width: 60},
            {field: 'FADDXZ', title: '案发地址', width: 140}
        ]],
        pagination: true,
        pageList: [10, 20, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar_jzInfo',
        onSelect: function (rowIndex, rowData) {
            if (rowData.AJBH != null) {
                loadJZPerson(rowData.AJBH);
            }
        }
    });
}

function loadJZPerson(ajbh) {
    $('#jzPerson')
        .datagrid({
            title: '嫌疑人信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: '../../bacs/serial/queryJzPersonXs.do',
            queryParams: {
                caseNo: ajbh,
                trefresh: new Date().getTime()
            },
            pagination: false,
            rownumbers: true,
            singleSelect: false,
            frozenColumns: [[
                {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'RYXM', title: '姓名', width: 20},
                {
                    field: 'XB', title: '性别', width: 20,
                    formatter: function (value, rec) {
                        if (1 == value) {
                            return "男";
                        } else if (2 == value) {
                            return "女";
                        } else {
                            return '未知';
                        }
                    }
                },
                {field: 'ZJHM', title: '证件编号', width: 50},
                {field: 'XZDXZ', title: '暂住地址', width: 50}
            ]],
            rownumbers: true
        });
}

function saveZJInfo() {
    var jzInfo1 = $('#jzInfo').datagrid('getSelected');
    var jzInfo = $('#jzPerson').datagrid('getSelected');
    if (jzInfo != null) {
        $('#person_name').val(jzInfo.RYXM);
        $('#addperson_name').val(jzInfo.RYXM);
        if (jzInfo.XB == "1") {
            $('#person_sex').combobox('setValue', 1);
        } else if (jzInfo.XB == "2") {
            $('#person_sex').combobox('setValue', 2);
        }

        if (jzInfo.XB == "1") {
            $('#addperson_sex').combobox('setValue', 1);
        } else if (jzInfo.XB == "2") {
            $('#addperson_sex').combobox('setValue', 2);
        }

        $('#person_certificate_type').combobox('setValue', 111);
        $('#person_certificate_no').val(jzInfo.ZJHM);

        $('#addperson_certificate_type').combobox('setValue', 111);
        $('#addperson_certificate_no').val(jzInfo.ZJHM);

        $('#ajbh').val(jzInfo1.AJBH);
        $('#rybh').val(jzInfo.RYBH);
        $('#ajmc').val(jzInfo1.AJMC);

        $('#ajbh1').val(jzInfo1.AJBH);
        $('#rybh1').val(jzInfo.RYBH);
        $('#ajmc1').val(jzInfo1.AJMC);

        //$("input[name='dataSex'][value='" + (jzInfo.XBID==1)?0:1 + "']").prop("checked", "checked");

        $('#selectJzLable').html('<font style="color: green">已关联执法办案平台数据，案件名称为(' + jzInfo1.AJMC + ')。</font>');

        $('#selectXqJzLable').html('<font style="color: green">已关联执法办案平台数据，案件名称为(' + jzInfo1.AJMC + ')。</font>');
        $.messager.show({
            title: '提示',
            msg: "关联警综平台数据成功",
            timeout: 3000
        });
        $('#jz_orderrequest').dialog('close');
    }
    else {
        $.messager.show({
            title: '提示',
            msg: "该案件没有嫌疑人数据",
            timeout: 3000
        });
        $('#jz_orderrequest').dialog('close');
    }
}

function SearchJZInfo() {

    var caseLb = $('#jzajlb').combobox('getValue');
    $('#jzInfo').datagrid('load', {caseName: $('#jzInfo_name').val(), caseLb: caseLb, trefresh: new Date().getTime()});
}

function doClearJZInfo() {
    $('#jzInfo_name').val('');
    $('#jzInfo').datagrid('clearSelections');
    $('#jzPerson').datagrid('loadData', {total: 0, rows: []});
    jzPersonJson = "";
}
