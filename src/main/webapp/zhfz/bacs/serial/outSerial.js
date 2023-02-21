var outSerialPhotoBase64;
var outSerialId = 0;

function exitBegin() {

    $('#selectJzLableCq').html('<font style="color: red">未关联执法办案平台数据！</font></font>');
    $("#glrybh").val("");
    $('#rybh').val("");
    $('#cqrybh').val("");

    $("#glajbh").val("");
    $('#ajbh').val("");
    $('#cqajbh').val("");
    $("#wxcajTr").hide();
    $("#jzTr").hide();
    $("#glTr").hide();
    $("#ryTr").hide();
    $("#ajTr").hide();
    glyy = true;

    outSerialId = 0;
    phototype = 'exitBegin';
    $("#outPopup").show();
    $("#inSerialBox").width("1000");
    showNoHideStepDiv("stepOutDiv1", "outStep1", 1);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
    $("#dataType").combobox({
        onChange: function (value) {
            getFirstReason();
        }
    });
    $('#serialNo').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getSuspectSerialNo.do',
        idField: 'serialNo',
        textField: 'name',
        columns: [[{
            field: 'serialNo',
            title: '入区编号',
            width: 135
        }, {
            field: 'name',
            title: '姓名',
            width: 60
        }, {
            field: 'certificateNo',
            title: '证件号码',
            width: 150
        }]],
        onChange: function (newValue, oldValue) {
            var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
            var row = cg.datagrid('getSelected'); // 获取选择的行
            $('#eNo').val(row.certificateNo);
            jzyyinfo(row);
            if (row == null) {
                $('#serialNo').combogrid('setValue', "");
            }
            else {
                $('#cuffNumber').val(row.cuffNo);
                $("#outSendUserName").val(row.sendUserName);
                $("#outSendUserId").val(row.sendUserId);
                $("#outAjmc").val(row.ajmc);
                var e = fileUtils.url("ba", fileTypeEntity.FILETYPRRQ, row.uuid, row.areaId, row.inPhotoName);
                $("#inphoto").attr("src", e);
                outSerialId = row.id;

                if (row.sfxxcj == null || row.sfxxcj == 0) {
                    $.messager.alert("提示", "嫌疑人未信息采集");
                }

                initRybh(row.certificateNo);
            }
        }
    });
    $('#dataOutUserId2').combogrid({
        panelWidth: 210,
        panelHeight: 200,
        mode: 'remote',
        url: '../policeEntrance/getPoliceEnteance.do',
        idField: 'policeId',
        textField: 'name',
        queryParams: {
            trefresh: new Date().getTime()
        },
        columns: [[{
            field: 'certificateNo',
            title: '警号',
            width: 60
        }, {
            field: 'name',
            title: '姓名',
            width: 150
        }]]
    });

    $("#dataType").combobox('setValue', "");
    $("#direction").combobox('setValue', '');
    $("#confirm_result").combobox('setValue', '');
    $("#outPhoto").attr("src", 'image/default.jpg');
    $('#outPersonNo').combogrid('setValue', "");
    //$(":radio[name='sfsyjd'][value='0']").prop("checked", "checked");//是否送押解队
}

function initRybh(zjhm) {
    $('#outPersonNo').combogrid({
        panelWidth: 500,
        panelHeight: 200,
        mode: 'remote',
        url: '../../common/case/queryRybhByZjhm.do',
        idField: 'rybh',
        textField: 'rybh',
        queryParams: {
            trefresh: new Date().getTime(),
            zjhm: zjhm
        },
        columns: [[{
            field: 'ajmc',
            title: '案件名称',
            width: 200
        }, {
            field: 'xm',
            title: '姓名',
            width: 100
        },
            {
                field: 'zjhm',
                title: '证件号码',
                width: 200
            }

        ]]
    });

}

//键盘监听事件
function enterKeyEvent2() {
    var e = e || window.event;
    if (e.keyCode == 13) {
        checkRing2($("#cuffNumber").val());
    }
}

//手环校验
function checkRing2(icNo) {
    var checkData = checkRingNo(icNo, 0);
    if (checkData.isError) {
        // $.messager.alert("提示",checkData.callbackData);
        $.messager.show({
            title: "提示",
            msg: checkData.content,
            timeout: 5000
        });
        $("#cuffNumber").val("");
        return;
    } else {
        if (checkData.callbackData.status == 0 || checkData.callbackData.status == 3) {
            $.messager.alert("手环未绑定或者已临时出区");
            $("#cuffNumber").val("");
            return;
        }
        if (checkData.callbackData.status == 1) {
            $("#cuffNumber").val("");
            $("#cuffNumber").val(checkData.callbackData.cuffNo);
            chec(checkData.callbackData.cuffNo);
        }
    }
}

function chec(number) {
    var cuff = {};
    cuff["cuffNo"] = number;
    cuff["type"] = 0;
    var jsonrtinfo = JSON.stringify(cuff);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath + '/zhfz/bacs/serial/getSerialByCuffNo.do',
        data: jsonrtinfo,
        dataType: 'json',
        success: function (data) {
            var serialNo = data.callbackData ? data.callbackData.serialNo : null;
            if (serialNo != null && serialNo != "") {
                outSerialId = data.callbackData.id;
                $('#serialNo').combogrid('setValue', serialNo);
                $("#cuffNumber").val(number);
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
}

function closeOutMpopup() {
    $('#typeInfo').form('clear');
    $('#cuffInfo').form('clear');
    $('#photoInfo').form('clear');
    $("#dataOutReason").val("");
    showNoHideStepDiv("stepOutDiv1", "outStep1", 1);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
    $("#outPopup").hide();
    if (preViewBase64 != null && preViewBase64 != '') {
        closePreview(1);
    }
    // window.location.reload();
}

function outBeforePhoto() {
    showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 1);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
}

var firstDirection = [[{
    lable: "",
    value: "请选择去向"
}, {
    lable: "1",
    value: "送拘留所执行行政拘留"
}, {
    lable: "2",
    value: "送看守所执行刑事拘留"
},
    {
        lable: "12",
        value: "直接取保候审"
    }, /*{
    lable: "13",
    value: "行政拘留停止执行"
},*/{
        lable: "14",
        value: "排除嫌疑"
    }, {
        lable: "6",
        value: "解除传唤"
    }, {
        lable: "3",
        value: "行政拘留不执行"
    }, {
        lable: "4",
        value: "未达到法定年龄不予以处理"
    }, {
        lable: "5",
        value: "行政拘留暂缓/暂停执行"
    }, {
        lable: "7",
        value: "符合调解相关规定调解后离开"
    }, {
        lable: "8",
        value: "送收容所执行收容教育"
    }, {
        lable: "9",
        value: "送戒毒所执行强制戒毒"
    }, {
        lable: "10",
        value: "刑事拘留转取保候审后离开"
    }, {
        lable: "11",
        value: "刑事拘留转监视居住后离开"
    }, {
        lable: "15",
        value: "其他"
    }], [{
    lable: "",
    value: "请选择去向"
},
    {
        lable: "4",
        value: "外出就医"
    },
    {
        lable: "2",
        value: "辨认现场"
    },
    {
        lable: "7",
        value: "搜查"
    },
    {
        lable: "3",
        value: "起赃"
    }, {
        lable: "1",
        value: "验伤"
    },
    {
        lable: "8",
        value: "市局验毒"
    }, {
        lable: "5",
        value: "伤情鉴定"
    }, {
        lable: "6",
        value: "其它"
    }], [{
    lable: "",
    value: "请选择去向"
}, {
    lable: "1",
    value: "突发疾病"
}, {
    lable: "2",
    value: "其它"
}], [{
    lable: "",
    value: "请选择去向"
}, {
    lable: "1",
    value: "其它"
}]];

function getFirstReason() {
    // 获得类型下拉框的对象
    var dataType = $("#dataType").combobox("getValue");
    // 获得去向下拉框的对象
    var direction = document.getElementById("direction");
    // 得到对应类型的去向数组
    if (dataType != null && dataType != "") {
        var dataTypeDirection = firstDirection[dataType];
        // 将去向数组中的值填充到去向下拉框中
        $('#direction').combobox({
            valueField: 'lable',
            textField: 'value',
            data: dataTypeDirection,
            onSelect: function (data) {
                if (data.value == "送拘留所执行行政拘留") {
                    $('#confirm_result').combobox('setValue', '治拘');
                } else if (data.value == "送看守所执行刑事拘留") {
                    $('#confirm_result').combobox('setValue', '刑拘');
                } else if (data.value == "排除嫌疑") {
                    $('#confirm_result').combobox('setValue', '排除');
                } else if (data.value == "未达到法定年龄不予以处理") {
                    $('#confirm_result').combobox('setValue', '不予处理');
                } else if (data.value == "行政拘留暂缓执行") {
                    $('#confirm_result').combobox('setValue', '治拘');
                } else if (data.value == "解除传唤") {
                    $('#confirm_result').combobox('setValue', '解除传唤出区');
                } else if (data.value == "符合调解相关规定调解后离开") {
                    $('#confirm_result').combobox('setValue', '不予以处罚');
                } else if (data.value == "移交其他分局") {
                    $('#confirm_result').combobox('setValue', '移交');
                } else if (data.value == "送戒毒所执行强制戒毒") {
                    $('#confirm_result').combobox('setValue', '强制戒毒');
                } else if (data.value == "刑事拘留转取保候审后离开") {
                    $('#confirm_result').combobox('setValue', '取保候审');
                } else if (data.value == "刑事拘留转监视居住后离开") {
                    $('#confirm_result').combobox('setValue', '监视居住');
                } else if (data.value == "直接取保候审") {
                    $('#confirm_result').combobox('setValue', '直保');
                } else if (data.value == "行政拘留不执行") {
                    $('#confirm_result').combobox('setValue', '治拘');
                } else if (data.value == "行政拘留暂缓/暂停执行") {
                    $('#confirm_result').combobox('setValue', '治拘');
                } else if (data.value == "送收容所执行收容教育") {
                    $('#confirm_result').combobox('setValue', '教育');
                } else {
                    $('#confirm_result').combobox('setValue', '选择裁决结果');
                }
            }
        });
        $('#direction').combobox('setValue', '');
    } else {
        var dataTypeDirection = [{lable: "", value: "请选择去向"}];
        $("#direction").combobox({
            valueField: 'lable',
            textField: 'value',
            data: dataTypeDirection
        });
    }
}

function typeNext(next) {
    //var dataType = $('#dataType').combobox('getValue');

    var direction = $('#direction').combobox('getText');  // 去向

    var confirm_result = $('#confirm_result').combobox('getValue'); // 裁决

    var dataOutReason = $('#dataOutReason').val(); // 出区原因

    if (direction == "其他" || confirm_result == "其他") {
        if (dataOutReason == '') {
            $.messager.alert("提示", "请填写出区原因");
            return;
        }
    }

    var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    if (!row || !row.serialNo) {
        $.messager.alert("提示", "请选择嫌疑人");
    }
    var serialNo = row.serialNo;
    if (serialNo != null && serialNo != '') {
        var entranceData = $('#entranceData').serializeObject();
        entranceData["serialNo"] = row.serialNo;
        var personstatusjson = JSON.stringify(entranceData);
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            url: 'querypersonstatus.do',
            data: personstatusjson,
            success: function (data) {
                /**
                 * 查询嫌疑人在办案中心的状态 0： 1： 审讯中 2: 看押中 3: 随身物品未取
                 */
                var result = data.callbackData;
                if (result == 3) {
                    var dataType = $("#dataType").combobox("getValue");
                    if (dataType == 1) {
                        $.messager.confirm('提醒', '该出区人员还未领取随身物品，确定继续出区?',
                            function (r) {
                                if (r) {
                                    var dataType = $('#direction').combobox("getValue");
                                    var direction = $('#direction').combobox('getText');
                                    var confirm_result = $('#confirm_result').combobox('getText');
                                    if (direction == "选择去向" || direction == "请选择去向") {
                                        $.messager.alert('警告', '请选择或填写去向!');
                                        return;
                                    }
                                    if (dataType != null && dataType != '' && direction != null
                                        && direction != '') {
                                        showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
                                        showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
                                        showNoHideStepDiv("stepOutDiv3", "outStep3", 1);
                                        showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                                        outStartImgGet();
                                    } else {
                                        $.messager.alert('警告', '信息填写不完整!');
                                    }
                                } else {
                                    closeOutMpopup();
                                }
                            });
                    } else {
                        $.messager.alert('警告', "抱歉," + row.name + "有物品未领取！");
//	                            closeOutMpopup();
                    }
                } else {
                    var dataType = $('#dataType').combobox("getValue");
                    if (dataType == 1) {
                        var direction = $('#direction').combobox('getText');
                        var confirm_result = $('#confirm_result').combobox('getText');
                        if (direction == "选择去向" || direction == "请选择去向") {
                            $.messager.alert('警告', '请选择或填写去向!');
                            return;
                        }
                        if (dataType != null && dataType != '' && direction != null
                            && direction != '') {
                            showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
                            showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
                            showNoHideStepDiv("stepOutDiv3", "outStep3", 1);
                            showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                            outStartImgGet();
                        } else {
                            $.messager.alert('警告', '信息填写不完整!');
                        }
                    } else {
                        var dataType = $('#dataType').combobox("getValue");
                        var direction = $('#direction').combobox('getText');
                        var confirm_result = $('#confirm_result').combobox('getText');
                        if (direction == "选择去向" || direction == "请选择去向" || confirm_result == "选择裁决结果") {
                            $.messager.alert('警告', '请选择或填写去向和裁决结果!');
                            return;
                        }
                        if (dataType != null && dataType != '' && direction != null
                            && direction != '' && confirm_result != null
                            && confirm_result != '') {
                            showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
                            showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
                            showNoHideStepDiv("stepOutDiv3", "outStep3", 1);
                            showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                            outStartImgGet();
                        } else {
                            $.messager.alert('警告', '信息填写不完整!');
                        }
                    }


                }
            }
        });
    } else {
        $.messager.alert('警告', '信息填写不完整!');
    }

}

//出区启动预览
function outStartImgGet() {
    //启动预览
    GWQ_StartPreview(1);
    // GWQ_StartPreviewShow(0);
}

function outGetImgBase64() {
    var base64 = getPreview();
    if (base64 != null) {
        outSerialPhotoBase64 = base64;
        $("#outPhoto").attr("src", "data:image/jpeg;base64," + outSerialPhotoBase64)
    } else {
        $.messager.alert("提示", "预览数据获取失败，请检查设备是否连接正常");
    }
}

function outBeforeCuff() {
    showNoHideStepDiv("stepOutDiv1", "outStep1", 1);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
}

function cuffNext() {
    var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    if (!row || !row.serialNo) {
        $.messager.alert("提示", "请选择嫌疑人");
        return false;
    }
    var sfsyjd = $("input[name='sfsyjd']:checked").val();
    if (!sfsyjd) {
        $.messager.alert("提示", "请选择是否送押解队");
        return false;
    }

    var data = {"serialId": row.id};
    var jsonData = JSON.stringify(data);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: 'querystatus.do',
        data: jsonData,
        success: function (data) {

            if (data.sta == 0 || data.wta == 0) {
                $.messager.alert('提示', "请前往候问室提讯出区！");
                closeOutMpopup();
                isPhoto = 0;
                return;
            }

        }
    });
    var serialNo = row.serialNo;
    if (serialNo != null && serialNo != '') {
        var entranceData = $('#entranceData').serializeObject();
        entranceData["serialNo"] = row.serialNo;
        var personstatusjson = JSON.stringify(entranceData);
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            url: 'querypersonstatus.do',
            data: personstatusjson,
            success: function (data) {
                /**
                 * 查询嫌疑人在办案中心的状态 0： 1： 审讯中 2: 看押中 3: 随身物品未取
                 */
                var result = data.callbackData;
                if (result == 0) {
                    showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
                    showNoHideStepDiv("stepOutDiv2", "outStep2", 1);
                    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
                    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                    outSerialPhotoBase64 = "";
                } else if (result == 1) {
                    // $.messager.alert('警告', "抱歉," + row.name+ "处于被审讯状态，不能出区！");
                    $.messager.alert('警告', '抱歉,' + row.name + '处于被审讯状态，不能出区！', 'info', function () {
                        closeOutMpopup();
                        isPhoto = 0;
                    });

                } else if (result == 2) {
                    // $.messager.alert('警告', "抱歉," + row.name + "处于被看押状态，不能出区！");
                    $.messager.alert('警告', '抱歉,' + row.name + '处于被看押状态，不能出区！', 'info', function () {
                        closeOutMpopup();
                        isPhoto = 0;
                    });
                } else if (result == 3) {
                    var dataType = $("#dataType").combobox("getValue");
                    if (dataType == 1) {
                        $.messager.confirm('提醒', '该出区人员还未领取随身物品，确定继续出区?',
                            function (r) {
                                if (r) {
                                    showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
                                    showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
                                    showNoHideStepDiv("stepOutDiv3", "outStep3", 1);
                                    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                                    outSerialPhotoBase64 = "";
                                } else {
                                    closeOutMpopup();
                                    isPhoto = 0;
                                }
                            });
                    } else {
                        showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
                        showNoHideStepDiv("stepOutDiv2", "outStep2", 1);
                        showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
                        showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                        outSerialPhotoBase64 = "";
//                            $.messager.alert('警告', "抱歉," + row.name+ "有物品未领取！");
//                            closeOutMpopup();
//                            isPhoto = 0;
                    }
                }
            }
        });
    } else {
        $.messager.alert('警告', '信息填写不完整!');
    }
}

function glyySave() {
    if ($("#glyy").val() == null || $("#glyy").val() == "") {
        $.messager.alert("提示", "必须填写未关联原因!");
        return;
    }
    //$("#yy").val($("#glyy").val())
    $('#glyy_dialog').dialog('close');
    cuffNext(2);
}

//人脸比对下一步
function photoNext() {
    $.messager.confirm('提醒', '此处“下一步”将进行数据插入，不能进行修改，是否确定提交数据？',
        function (r) {
            if (r) {
                // 获取类型信息form的值
                var typeInfo = $('#typeInfo').serializeObject();
                var type = typeInfo["dataType"];
                var outReason = '';
                if (typeInfo["dataOutReason"] != null && typeInfo["dataOutReason"] != '') {
                    outReason = $('#direction').combobox('getText') + "，" + typeInfo["dataOutReason"];
                } else {
                    outReason = $('#direction').combobox('getText');
                }
                var outSendUserid = $('#outSendUserid').val();
                var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
                var row = cg.datagrid('getSelected'); // 获取选择的行
                var serialNo = row.serialNo;
                // 获取照片form的值
                var photoInfo = $('#photoInfo').serializeObject();
                var entranceData = $('#entranceData').serializeObject();
                entranceData["serialNo"] = serialNo;
                entranceData["outType"] = type;
                entranceData["outReason"] = outReason;
                entranceData["sendUserid"] = outSendUserid;
                entranceData["areaId"] = row.areaId;
                entranceData["birth"] = $('#birth').val();
                entranceData["nation"] = $('#nation').val();
                entranceData["country"] = $('#country').val();
                entranceData["confirmResult"] = $('#confirm_result').combobox(
                    'getText');
                //rybh

                entranceData["ajbh"] = $('#cqajbh').val();
                entranceData["rybh"] = $('#cqrybh').val();
                //entranceData["yy"] = $('#glyy').val();
                entranceData["yy"] = $('#wglyy').combobox('getText');

                if ($("#cqajbh").val() != null && $("#cqrybh").val() != null) {
                    if(sfajbh == $("#cqajbh").val() && sfrybh == $("#cqrybh").val()){

                    }else if (sfzfglAjbh == $("#cqajbh").val() && sfzfglRybh == $("#cqrybh").val()) {
                        entranceData["sfzdgl"] = 1;
                    }else {
                        entranceData["sfzdgl"] = 2;
                    }
                } else {
                    entranceData["sfzdgl"] = 0;
                }


                entranceData["personNo"] = $('#outPersonNo').val();
                var sfsyjd = $("input[name='sfsyjd']:checked").val();//是否送押解队
                entranceData["sfsyjd"] = sfsyjd;
                var jsonrtinfo = JSON.stringify(entranceData);
                $.messager.confirm('提醒', '请确认两张图片是否为同一个人？',
                    function (r) {
                        if (r) {
                            exitEdit(jsonrtinfo);
                        }
                    })
            }
        })

    /*if(outSerialPhotoBase64 != '' && outSerialPhotoBase64 != null) {
         $.messager.confirm('提醒', '此处“下一步”将进行数据插入，不能进行修改，是否确定提交数据？',
             function (r) {
                 if (r) {
                     // 获取类型信息form的值
                     var typeInfo = $('#typeInfo').serializeObject();
                     var type = typeInfo["dataType"];
                     var outReason = '';
                     if (typeInfo["dataOutReason"] != null && typeInfo["dataOutReason"] != '') {
                         outReason = $('#direction').combobox('getText') + "，" + typeInfo["dataOutReason"];
                     } else {
                         outReason = $('#direction').combobox('getText');
                     }
                     var outSendUserid = $('#outSendUserid').val();
                     var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
                     var row = cg.datagrid('getSelected'); // 获取选择的行
                     var serialNo = row.serialNo;
                     // 获取照片form的值
                     var photoInfo = $('#photoInfo').serializeObject();
                     var entranceData = $('#entranceData').serializeObject();
                     entranceData["serialNo"] = serialNo;
                     entranceData["outType"] = type;
                     entranceData["outReason"] = outReason;
                     entranceData["sendUserid"] = outSendUserid;
                     entranceData["areaId"] = row.areaId;
                     entranceData["birth"] = $('#birth').val();
                     entranceData["nation"] = $('#nation').val();
                     entranceData["country"] = $('#country').val();
                     entranceData["confirmResult"] = $('#confirm_result').combobox(
                         'getText');
                     //rybh

                     entranceData["ajbh"] = $('#cqajbh').val();
                     entranceData["rybh"] = $('#cqrybh').val();
                     //entranceData["yy"] = $('#glyy').val();
                     entranceData["yy"] = $('#wglyy').combobox('getText');

                     entranceData["personNo"] = $('#outPersonNo').val();
                     var sfsyjd = $("input[name='sfsyjd']:checked").val();//是否送押解队
                     entranceData["sfsyjd"] = sfsyjd;
                     var jsonrtinfo = JSON.stringify(entranceData);
                     $.messager.confirm('提醒', '请确认两张图片是否为同一个人？',
                         function(r){
                             if(r){
                                 exitEdit(jsonrtinfo);
                             }
                         })
                 }
             })
     } else{
         $.messager.alert('提示', '请在页面上点击拍照，并在设备点击确认拍照，提交照片!');
     }*/
}

function glyyNext(next) {

    $("#glyy").val("");
    var yy = $("#wglyy").combobox("getText");
    if (!glyy) {
        if ($("#cqajbh").val() != null && $("#cqajbh").val() != "" && $("#cqrybh").val() != null && $("#cqrybh").val() != "") {

            if ($("#cqajbh").val().length == 0 || $("#cqrybh").val().length == 0) {
                $.messager.alert("提示", "所输内容不能为空");
                return;
            }
            if ($("#cqajbh").val().length != 23 || $("#cqrybh").val().length != 23) {
                $.messager.alert("提示", "请输入正确的案件或人员编号");
                return;
            }
            var s = /^[0-9]*$/;
            var s2 = /^A\w*$/;
            var s3 = /^R\w*$/;
            if (!$("#cqajbh").val().match(s2)) {
                $.messager.alert("提示", "所输入的案件编号首字母必须大写A！");
                return;
            }
            if (!$("#cqrybh").val().match(s3)) {
                $.messager.alert("提示", "所输入的人员编号首字母必须大写R！");
                return;
            }

            if (!$("#cqajbh").val().substring(1, $("#cqajbh").val().length).match(s) ||
                !$("#cqrybh").val().substring(1, $("#cqrybh").val().length).match(s)) {
                $.messager.alert("提示", "所输入的人员或案件编号除首字母外均为数字！");
                return;
            }
            cuffNext();
        } else if (yy == '' || yy == null) {
            /// showDialog('#glyy_dialog', '未关联执法办案平台原因');
            $.messager.alert("提示", "请输入未关联执法办案平台原因！");
        } else {
            cuffNext();
        }
    } else {
        cuffNext();
    }
}

function exitEdit(jsonrtinfo) {
    // 修改数据
    $.messager.progress({
        title: '请等待',
        msg: '保存数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'suspectexit.do',
        data: jsonrtinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
            showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
            showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
            showNoHideStepDiv("stepOutDiv4", "outStep4", 1);
            //关闭预览
            closePreview(1);
            if ($('#datagrid')) {
                var es = $('#e_status').combobox('getValue');
                var areaId = $('#interrogatearea').combobox('getValue');
                $('#datagrid').datagrid('load', {
                    name: $('#e_person').val(),
                    certificateNo: $('#e_certificateNo').val(),
                    status: es,
                    areaId: areaId,
                    start_date: $('#e_start_date').datebox('getValue'),
                    end_date: $('#e_end_date').datebox('getValue'),
                    trefresh: new Date().getTime()
                })
            } else {
                initSerialData();
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
    $('#imageForm').form('clear');
}

//出区台账打印
function outPrintSign() {
    if ($('#datagrid')) {
        var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
        var row = cg.datagrid('getSelected'); // 获取选择的行
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord', row.id);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if (row != null) {
            printChoose(row.id, row.uuid, row.areaId, 2);
        } else {
            $.messager.alert('提示', "未选中数据，请刷新页面！");
        }
    } else {
        $.messager.alert('提示', "嫌疑人未出区，无法打印出区台账！");
        return;
    }

}

// 原始版出区台账打印
function puTongOutPrintSign() {
    var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    if (row != null) {
        $.messager.progress({
            title: '请等待',
            msg: '打印预览中...'
        });
        fileUtils.read("/zhfz/lawdocProcess/download.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialUUID=" + row.uuid + "&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id);
        $.messager.progress('close');
    } else {
        $.messager.alert('提示', "未选中数据，请刷新页面！");
    }
}

//出区签字
function cruquSign() {
    var serialId = outSerialId;
    if (serialId == null || serialId == '') {
        $.messager.alert('提示', 'serialId为空，请在外侧打印');
        return;
    } else {
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord', serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if (row != null) {
            SignType = '2';
            var url = "../../lawdocProcess/downloadBase64.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialNo=" + row.serialNo;
            startSign(url, serialId, SignType);
        } else {
            $.messager.alert('提示', "未选中数据，请刷新页面！");
            return;
        }
    }
}

//出区拍照反馈调用方法
function getOutSerialPhoto(ErrorCode, imgStr) {
    if (ErrorCode == 0) {
        $.messager.progress('close');
        outSerialPhotoBase64 = imgStr;
        $("#outPhoto").attr("src", 'data:image/png;base64,' + imgStr);
    }
    else if (ErrorCode == -9) {
        $.messager.progress('close');
        $.messager.alert('取消', '设备已取消');
    }
    else {
        $.messager.progress('close');
        $.messager.alert('失败', '返回错误码为' + ErrorCode);
    }
}


//启动入区与出区照片比对
function startInAndOutPhotoDistinguish(inPhoto, outPhoto) {
    var browsern = browser["browser"];
    if (!(browsern == "Microsoft IE")) {
        $.messager.alert('提示', '请使用IE浏览器');
        return;
    }
    var ret = GWQ.DoGWQ_ImageCompare(inPhoto, outPhoto);
    if (ret != 0) {
        $.messager.alert('失败', '返回错误码为' + ret);
    }
}

//照片比对回调函数
function afterPhotoDistinguish(ErrorCode, JsonData) {
    if (ErrorCode == 0) {
        console.log(JsonData);
    }
    else if (ErrorCode == -9) {
        $.messager.alert('取消', '设备已取消');
    }
    else {
        $.messager.alert('失败', '返回错误码为' + ErrorCode);
    }
}