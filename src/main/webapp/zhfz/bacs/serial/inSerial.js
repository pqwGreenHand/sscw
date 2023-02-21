var inSerialPhotoBase64;
var baseSerialId = "";
var qiankeData = {}, renkouData = {}, zaitaoData = {}, historyResultData = {};
var orderShzt = "";

function entrance() {

    $('#selectJzLable').html('<font style="color: red">未关联执法办案平台数据！</font></font>');

    codeCombo('dataSex', 'XBID', '1');
    baseSerialId = "";
    loadOrderArea();
    phototype = 'entrance';
    $("#inPopup").show();
    $("#isBasicInfo").form('clear');
    $("#cuff").form('clear');
    $("#cuff").form('clear');
    $(":radio[name='sfxxcjin'][value='0']").prop("checked", "checked");
    loadEntranceProcedure('entranceProcedure');
    loadPersonInfo();
    loadCertificateType();
    //$('#writtenTime').datetimebox('setValue', formatDateTime(new Date()));
    $('#orderPeople').combogrid({
        panelWidth: 270,
        mode: 'remote',
        url: ' ../../common/combogrid/getOrderContentForEntrance.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        idField: 'name',
        textField: 'name',
        columns: [[{
            field: 'name',
            title: '姓名',
            width: 100
        }, {
            field: 'certificateNo',
            title: '证件号码',
            width: 170
        }]],
        onChange: function (newValue, oldValue) {
            var oc = $('#orderPeople').combogrid('grid'); // 获取数据表格对象
            var ocrow = oc.datagrid('getSelected'); // 获取选择的行
            console.log(ocrow);
            orderShzt = true
            if (ocrow != null && ocrow.id != null
                && ocrow.id != 0) {
                if (ocrow.type =="2") {
                    if (ocrow.shzt == 2) {
                        orderShzt = false
                        $.messager.alert('提醒', '手机预约审核未通过，不能入区！');
                        return
                    } else if (ocrow.shzt == 0) {
                        orderShzt = false
                        $.messager.alert('提醒', '手机预约未审核，不能入区！');
                        return
                    }

                }

                if (ocrow.ajbh != null && ocrow.ajbh != "") {
                    // 绑定入区
                    $('#selectJzLable').html('<font style="color: green">已关联执法办案平台数据，案件名称为(' + ocrow.orderPersonAjmc + ')。</font>');
                    $('#rqAjbh').val(ocrow.ajbh);
                    $('#rqRybh').val(ocrow.rybh);

                    $("#yyAjbh").val(ocrow.ajbh);
                    $("#yyRybh").val(ocrow.rybh);

                } else {
                    $("#yyAjbh").val('');
                    $("#yyRybh").val('');
                    //调用警综
                    $('#selectJzLable').html('<font style="color: red">未关联执法办案平台数据！</font></font>');
                    // jingzongByzjhm(ocrow.certificateNo);
                }

                $('#dataName').val(ocrow.name);
                // $("#ajmc").val(ocrow.ajmc);
                if (ocrow.ajmc != null && ocrow.ajmc != '') {
                    $("#link_case_ajmc").val("已关联案件：" + ocrow.ajmc);
                }
                //送押民警默认预约民警
                $("#sendUserNO").val(ocrow.orderUserNo + "(" + ocrow.orderUserName + ")");
                $("#sendUserId").val(ocrow.orderUserId);
                //案件类型和案由
                $("#ajlxNews").combobox("setValue", ocrow.ajlx);
                $("#abNews").combobox("setValue", ocrow.ab);
                $("#orderarea").combobox("setValue", ocrow.areaId);
                $('#dataSex').combobox('setValue', ocrow.sex);
                $('#dataCertificateTypeId').combobox('setValue', ocrow.certificateTypeId);
                $('#dataCertificateNo').val(ocrow.certificateNo);
                //如果是身份证则从人口库你查询，改成正确的姓名
                if (ocrow.certificateTypeId == 111) {
                    jQuery.ajax({
                        type: 'GET',
                        contentType: 'application/json',
                        dataType: 'json',
                        url: "renKouByZjhm.do",
                        data: {zjhm: ocrow.certificateNo, personId: ocrow.id},
                        success: function (data) {
                            // $.messager.alert(data.title, data.content);
                        },
                        error: function (data) {
                            // $.messager.alert('错误', '未知错误!');
                        }
                    });
                }


                $('#dataPersonInfo').combobox('setValue', ocrow.personType);
                $("#personId").val(ocrow.id);
                $('#dataOrderId').combogrid('setValue', ocrow.orderRequestId);
                $('#mainPolice').combogrid({
                    panelWidth: 240,
                    mode: 'remote',
                    url: ' listMainPoliceForEntranceByOrderId.do?orderId='
                    + ocrow.orderRequestId,
                    idField: 'id',
                    textField: 'realName',
                    columns: [[
                        {
                            field: 'policeEntranceId',
                            title: '民警登记入区ID',
                            width: 50,
                            hidden: true
                        },
                        {
                            field: 'interrogateCaseId',
                            title: '案件ID',
                            width: 50,
                            hidden: true
                        },
                        {
                            field: 'realName',
                            title: '姓名',
                            width: 60
                        },
                        {
                            field: 'crimeTypeName',
                            title: '案件性质',
                            width: 100
                        },
                        {
                            field: 'involvedAddress',
                            title: '案发地址',
                            width: 80
                        }
                    ]],
                    onLoadSuccess: function (data) {
                        if (data != null) {
                            $('#mainPolice').combogrid('setValue', data.rows[0].id);
                            $('#dataInUserNo1').val(data.rows[0].certificateNo);
                            $('#dataInUserId1').val(data.rows[0].policeId);
                            jQuery.ajax({
                                type: 'POST',
                                contentType: 'application/json',
                                dataType: 'json',
                                url: 'listOtherPolice.do?policeEntranceId='
                                + data.rows[0].policeEntranceId,
                                success: function (data) {
                                    if (data.callbackData != null) {
                                        $('#dataInUserNo2').val(data.callbackData.certificateNo);
                                        $('#dataInUserId2').val(data.callbackData.policeId);
                                    }
                                }
                            });
                        }
                    },
                });
            }
        }
    });
    //加载预约信息列表
    $('#dataOrderId').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: ' ../../common/combogrid/getAllOrderInfo.do',
        idField: 'id',
        textField: 'name',
        columns: [[{
            field: 'orderNo',
            title: '预约编号',
            width: 160
        }, {
            field: 'name',
            title: '民警姓名',
            width: 80
        }, {
            field: 'certificateNo',
            title: '民警警号',
            width: 100
        }, {
            field: 'policeId',
            title: '预约民警ID',
            width: 50,
            hidden: true
        },]]
    });
    $("#sendUserNO").val("请输入警号");
    $("#dataCuffId").combobox('setValue', '');
    $("#dataCuffIdText").val('');
    $("#dataCuffIdText").show();
    $('#dataCuffId').next(".combo").hide();
    $("#inSerialPhoto").attr("sec", "image/default.jpg");
    initAjlxNew();
}

function saveStepDiv1Data() {
    if (!orderShzt) {
        $.messager.alert('提醒', '手机预约审核未通过，不能入区！');
        return
    }
    /*if ($('#writtenTime').datebox('getValue') == null
        || $('#writtenTime').datebox('getValue') == "") {
        $.messager.alert('提醒', '请选择手续开具时间！');
        return;
    }*/
    if ($("#dataCertificateTypeId").combobox('getValue') == 111) {
        var certificateNo = $("#dataCertificateNo").val();
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

    if ($('#orderarea').combobox('getValue') == null
        || $('#orderarea').combobox('getValue') == "") {
        $.messager.alert('提醒', '请选择办案场所！');
        return;
    }
    if ($('#sendUserId').val() == null
        || $('#sendUserId').val() == "") {
        $.messager.alert('提醒', '请输入押送民警！');
        return;
    }
    /*if ($('#caseId').val() == null
        || $('#caseId').val() == "") {
        $.messager.alert('提醒', '请关联案件！');
        return;
    }*/
    if ($('#orderPeople').combobox('getValue') == null
        || $('#orderPeople').combobox('getText').trim() == "") {
        $.messager.alert('提醒', '请选择或输入嫌疑人姓名！');
        return;
    }
    $('#dataName').val($("input[name=orderPeople]").val());
    if ($('#dataCertificateTypeId').combobox('getValue') != null
        && $('#dataCertificateTypeId').combobox('getValue') != ''
        && $('#dataCertificateTypeId').combobox('getValue') == 111) {
        // 身份证验证
        var dataCertificateNo = $('#dataCertificateNo').val();
        if (dataCertificateNo != null && dataCertificateNo != "") {
            if (checkId(dataCertificateNo) == true) {
                if (getAge(dataCertificateNo) < 18) {
                    $.messager.alert('提醒', '该嫌疑人为未成年！');
                }
                $('#birth').val(getBirth(dataCertificateNo));
                changeDiv();
            }
            else {
                $.messager.alert('提醒', checkId(dataCertificateNo));
                $('#dataCertificateNo').val("");
            }
        } else {
            $.messager.alert('提醒', '请输入合法的身份证号码！');
        }
    } else {
        changeDiv();
    }
}

function backStepDiv1() {
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv1", "step1", 1);
    showNoHideStepDiv("stepDiv3", "step3", 0);
    //关闭预览
    closePreview(1);
}

function saveStepDiv2Data() {
    if (true) {
        if ($("#dataCuffIdText").val() == "" || ($("#dataCuffId").combobox("getValue") == null || $("#dataCuffId").combobox("getValue") == "")) {
            $.messager.alert('提示', '请扫描手环!检查有问题数据！');
            return;
        }

        $.messager.confirm('提示', '此处“下一步”将进行数据插入，不能进行修改，是否确定提交数据？', function (r) {
            if (r) {
                // 获取基本信息form的值
                var isBasicInfo = $('#isBasicInfo').serializeObject();
                var cg = $('#dataOrderId').combogrid('grid'); // 获取数据表格对象
                var row = cg.datagrid('getSelected'); // 获取选择的行
                var dataOrderId = row.id;
                // 获取手环form的值
                var cuff = $('#cuff').serializeObject();
                var dataCuffId = $("#dataCuffId").combobox("getValue")// 嫌疑人手环
                var dataCuffNo = $("#dataCuffId").combo('getText');// 嫌疑人手环编号
                // 获取照片form的值
                isBasicInfo["caseId"] = $("#caseId").val();
                isBasicInfo["sex"] = $("#dataSex").combobox('getValue');
                isBasicInfo["orderRequestId"] = dataOrderId;
                isBasicInfo["cuffId"] = dataCuffId;
                isBasicInfo["cuffNo"] = dataCuffNo;
                isBasicInfo["certificateNo"] = isBasicInfo["dataCertificateNo"];
                var dataCertificateTypeId = $("#dataCertificateTypeId").combobox("getValue");
                isBasicInfo["certificateTypeId"] = dataCertificateTypeId;
                isBasicInfo["sex"] = isBasicInfo["dataSex"];
                isBasicInfo["personInfo"] = isBasicInfo["dataPersonInfo"];
                isBasicInfo["name"] = isBasicInfo["dataName"];
                var sendUserNo = $('#sendUserNO').val();
                isBasicInfo["sendUserNO"] = sendUserNo.split("(")[0];
                /*var pserNo = $("#link_jzAjxx_person").combobox("getValue");
                if(pserNo != null && pserNo != ''){
                    isBasicInfo['personNo'] = pserNo;
                }*/
                isBasicInfo["ajbh"] = $('#rqAjbh').val();
                isBasicInfo["rybh"] = $('#rqRybh').val();

                var sfxxcj = $("input[name='sfxxcjin']:checked").val();//是否信息采集
                isBasicInfo["sfxxcj"] = sfxxcj;
                if ($("#sendUserId").val() == null || $("#sendUserId").val() == "" || $("#sendUserId").val() == "请输入警号") {
                    isBasicInfo["sendUserId"] = "";
                }
                var jsonrtinfo = JSON.stringify(isBasicInfo);

                $.messager.progress({
                    title: '请等待',
                    msg: '保存数据中...'
                });
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'suspectinsert.do',
                    data: jsonrtinfo,
                    dataType: 'json',
                    success: function (data) {
                        if (data.callbackData == null) {
                            $.messager.progress('close');
                            $.messager.alert('错误', data.content);
                            return;
                        }
                        //图片上传开始
                        var imageForm = $('#imageForm').serializeObject();
                        var formData = new FormData();
                        var collRow = data.callbackData;
                        var blob = b64toBlob(inSerialPhotoBase64, 'image/png');
                        formData.append("file", blob, 'a.png');
                        formData.append("serialId", collRow.id);
                        formData.append("photoName", data.callbackData.inPhotoId);
                        baseSerialId = collRow.id;
                        jQuery.ajax({
                            type: 'POST',
                            url: "personIngetpicture.do",
                            data: formData,
                            contentType: false, // 注意这里应设为false
                            processData: false,
                            cache: false,
                            dataType: 'json',
                            success: function (data) {
                                $.messager.progress('close');
                                if (data.isError) {
                                    $.messager.alert(data.title, data.content);
                                    var resultData = data.callbackData;
                                    if (resultData != null) {
                                        console.log(resultData);
                                    }
                                } else {
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.content,
                                        timeout: 3000
                                    });
                                    //关闭预览
                                    closePreview(1);
                                    showNoHideStepDiv("stepDiv2", "step2", 0);
                                    showNoHideStepDiv("stepDiv2", "step2", 0);
                                    showNoHideStepDiv("stepDiv3", "step3", 1);
                                    $("#dialogSerialImg").attr("src", 'data:image/png;base64,' + inSerialPhotoBase64);
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
                                    // 人口库
                                    var resultData = data.callbackData;
                                    if (resultData != null) {
                                        console.log(resultData);
                                        var onevOneResult = resultData.onevOneResult.similarity;
                                        if (onevOneResult != null && onevOneResult != "") {
                                            onevOneResult = onevOneResult.substring(0, 4) + "%";
                                        } else {
                                            onevOneResult = 0;
                                        }
                                        $('#photoDistinguish').text(onevOneResult);
                                        var renKou = resultData.personWarehouse;
                                        if (renKou != null && renKou.person_id != null && renKou.person_id != "") {
                                            $("#renkou").removeClass('without');
                                            renkouData = renKou;
                                            $("#renkou").attr("onmouseover", "showDetail(event,'renkou')");
                                            $("#renkou").attr("onmouseout", "hideDetail()");
                                        } else {
                                            renkouData = {};
                                            $("#renkou").addClass('without');
                                            $("#renkou").attr("onmouseover", "");
                                            $("#renkou").attr("onmouseout", "");
                                        }
                                        var zaitao = resultData.largeWarehouse;
                                        if (zaitao != null && zaitao.person_id != null && zaitao.person_id != "") {
                                            zaitaoData = zaitao;
                                            $("#zaitao").removeClass('without');
                                            $("#zaitao").attr("onmouseover", "showDetail(event,'zaitao')");
                                            $("#zaitao").attr("onmouseout", "hideDetail()");
                                            $("#feizaitao").addClass('without');
                                        } else {
                                            zaitaoData = {};
                                            $("#feizaitao").removeClass('without');
                                            $("#zaitao").attr("onmouseover", "");
                                            $("#zaitao").attr("onmouseout", "");
                                            $("#zaitao").addClass('without');
                                        }
                                        var qianke = resultData.oldWarehouse;
                                        if (qianke != null && qianke.person_id != null & qianke.person_id != "") {
                                            qiankeData = qianke;
                                            $("#qianke").removeClass('without');
                                            $("#feiqianke").addClass('without');
                                            $("#qianke").attr("onmouseover", "showDetail(event,'qianke')");
                                            $("#qianke").attr("onmouseout", "hideDetail()");
                                        } else {
                                            qiankeData = {};
                                            $("#feiqianke").removeClass('without');
                                            $("#qianke").attr("onmouseover", "");
                                            $("#qianke").attr("onmouseout", "");
                                            $("#qianke").addClass('without');
                                        }
                                    }
                                    // 历史对比结果start
                                    var certificateNoResult = isBasicInfo["dataCertificateNo"];
                                    jQuery.ajax({
                                        type: 'POST',
                                        //contentType : 'application/json',
                                        dataType: 'json',
                                        async: true,
                                        url: contextPath + '/zhfz/bacs/serial/listHistory.do',
                                        data: {'certificateNo': certificateNoResult},
                                        success: function (data) {
                                            if (data != null && data.total > 0) {
                                                $("#historyResult").removeClass('without');
                                                historyResultData = data.rows;
                                                $("#historyResult").attr("onmouseover", "showHistoryResultDetail(event)");
                                                $("#historyResult").attr("onmouseout", "hideDetail()");
                                            } else {
                                                historyResultData = {};
                                                $("#historyResult").addClass('without');
                                                $("#historyResult").attr("onmouseover", "");
                                                $("#historyResult").attr("onmouseout", "");
                                            }
                                        },
                                        error: function (data) {
                                            $.messager.alert('提示', '历史对比结果错误！');
                                        }
                                    });
                                    // 历史对比结果end
                                }
                            },
                            error: function (data) {
                                $.messager.progress('close');
                                $.messager.alert('错误', '未知错误!');
                            }
                        });
                    },
                    error: function (data) {
                        $.messager.progress('close');
                        $.messager.alert('错误', '未知错误');
                    }
                });
            }
        })
    } else {
        $.messager.alert('提示', '请在页面上点击拍照或者人证对比，并在设备点击确认拍照，提交照片或者进行人证识别!');
    }
}

//详情展示
function showDetail(e, type) {
    var obj = {};
    if (type == 'qianke') {
        obj = qiankeData;
    }
    if (type == 'zaitao') {
        obj = zaitaoData;
    }
    if (type == 'renkou') {
        obj = renkouData;
    }
    if (type == 'historyResult') {
        obj = historyResultData;
    }
    var pos = getMousePos(e);
    var bigger = '<div id="tooltip" class="detailTip">' +
        '<table>' +
        '<tr> <td>姓名</td><td>' + obj.name + '</td></tr>' +
        '<tr> <td>身份证号</td><td>' + obj.person_id + '</td></tr>' +
        '<tr> <td>出生日期</td><td>' + obj.born_year + '</td></tr>' +
        '<tr> <td>地址</td><td>' + obj.address + '</td></tr>' +
        '<tr> <td>证件图片</td><td><img src=' + obj.face_image_url + '/></td></tr>' +
        '</table>' +
        '</div>';
    $("body").append(bigger);
    var zIndex = 99;
    if ($(obj).closest('.window').css('z-index')) {
        zIndex = $(obj).closest('.window').css('z-index') + 1;
    }
    $("#tooltip").css({
        left: pos.x,
        top: pos.y,
        "z-index": zIndex
    }).show(1000);
}

// 历史对比结果展示
function showHistoryResultDetail(e) {
    var obj = historyResultData;
    var pos = getMousePos(e);
    var bigger = '<div id="tooltip" class="detailTip"><table border="1">' +
        '<tr><td>次数</td><td>入区时间</td><td>出去原因</td><td>出区时间</td><td>案由</td><td>裁决结果</td></tr>';
    for (var i = 0; i < obj.length; i++) {
        var row = obj[i];
        bigger += '<tr><td>' + "第" + (obj.length - i) + "次入区" + '</td>' +
            '<td>' + valueToDate(row.inTime) + '</td>' +
            '<td>' + row.outReason + '</td>' +
            '<td>' + valueToDate(row.outTime) + '</td>' +
            '<td>' + row.caseNature + '</td>' +
            '<td>' + row.confirmResult + '</td></tr>';
    }
    bigger += '</table></div>';
    $("body").append(bigger);
    var zIndex = 99;
    if ($(obj).closest('.window').css('z-index')) {
        zIndex = $(obj).closest('.window').css('z-index') + 1;
    }
    $("#tooltip").css({
        left: pos.x,
        top: pos.y,
        "z-index": zIndex
    }).show(1000);
}

function hideDetail() {
    $("#tooltip").remove();
}

function ruquSign() {
    var serialId = baseSerialId;
    if (serialId == null || serialId == '') {
        $.messager.alert('提示', 'serialId为空，请在外侧打印');
        return;
    } else {
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord', serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if (row != null) {
            SignType = '0';
            var url = "../../lawdocProcess/downloadBase64.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id;
            startSign(url, serialId, SignType);
        } else {
            $.messager.alert('提示', "未选中数据，请刷新页面！");
            return;
        }
    }
}

//需要隐藏的步骤id；显示还是隐藏 type:0隐藏 1显示
function showNoHideStepDiv(stepDivId, stepId, type) {
    var tempStep = $("#" + stepId);
    var tempStepDiv = $("#" + stepDivId);
    if (type == 1) {
        tempStepDiv.show();
        tempStep.addClass("now");
    } else {
        tempStepDiv.hide();
        tempStep.removeClass("now");
    }
}

function closeMpopup() {
    $(".m-popup").hide();
    showNoHideStepDiv("stepDiv3", "step3", 0);
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv1", "step1", 1);
    if (preViewBase64 != null && preViewBase64 != '') {
        closePreview(1);
    }
    window.location.reload();
}


function jingzong() {
    var caseLb = $('#jzajlb').combobox('getValue');
    loadJZPerson(-99, -99);
    showDialog('#jz_orderrequest', '执法办案平台');
    $('#jzInfo').datagrid({
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
        url: 'queryJzAjxx.do',
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

        },
        onLoadSuccess: function (data) {
            console.log(data);
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

function loadEntranceProcedure(id) {
    // 加载入区手续
    $('#' + id).combobox({
        data: [{
            label: '口头传唤',
            value: '口头传唤'
        }, {
            label: '治安传唤',
            value: '治安传唤'
        }, {
            label: '刑事传唤',
            value: '刑事传唤'
        }, {
            label: '拘传',
            value: '拘传'
        }, {
            label: '留置盘问',
            value: '留置盘问'
        }, {
            label: '继续留置盘问',
            value: '继续留置盘问'
        }, {
            label: '刑事拘留',
            value: '刑事拘留'
        }, {
            label: '其他',
            value: '其他'
        }],
        valueField: 'value',
        textField: 'label'

    });
    $('#entranceProcedure').combobox('setValue', '');
}

// 加载人员类型
function loadPersonInfo() {
    $('#dataPersonInfo').combobox({
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
            value: '成年人'
        }],
        valueField: 'value',
        textField: 'label'

    });
    $('#dataPersonInfo').combobox('setValue', '成年人');
}

// 证件
function loadCertificateType() {
    $('#dataCertificateTypeId').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#dataCertificateTypeId').combobox('setValue', data[0].id);
        }
    });
}

//无名氏点击事件
function setWuMingShi() {
    if ($('#wumingshi').is(':checked')) {
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            url: "../combobox/listWMSCount.do",
            success: function (data) {
                var count = data + 1;
                $('#orderPeople').combogrid('setValue', "无名氏" + count);
                $('#dataCertificateNo').val('XXXXX.....XXXXX' + count);
                $("#dataOrderId").combogrid("setValue", -1);
            },
            error: function (data) {
                $.messager.alert('错误', '未知错误!');
                $.messager.progress('close');
            }
        });
        $('#dataName').val('无名氏');
        $('#dataCertificateTypeId').combobox('select', 7);
    } else {
        $('#dataName').val('');
        $('#dataCertificateTypeId').combobox('select', 111);
        $('#dataCertificateNo').val('');
        $('#orderPeople').combogrid('setValue', '');
        $("#dataOrderId").combogrid("setValue", "");
    }
}

var startpath = '<%=basePath%>';

//刷手环与手动选择切换事件
function cheack(obj, selectId) {
    $(selectId).combobox('setValue', '');
    $(selectId + "Text").val('');
    $(selectId + "Text").toggle();
    $(selectId).next(".combo").toggle();
}

//入区上一步与下一步切换
function changeDiv(next) {
    var cg = $('#dataOrderId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    var dataOrderId = row.id;
    if (dataOrderId != null && dataOrderId != ''
        && $("#dataName").val() != null && $("#dataName").val() != ''
        && $("#dataSex").combobox('getValue') != ''
        && $('#dataCertificateTypeId').combobox('getValue') != null
        && $('#dataCertificateTypeId').combobox('getValue') != '') {
        showNoHideStepDiv("stepDiv1", "step1", 0);
        showNoHideStepDiv("stepDiv2", "step2", 1);

        // 入区嫌疑人手环
        $('#dataCuffId').combobox({
            url: '../combobox/getallcuff.do?type=0',
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function (data) {
                if (data.length == 0) {
                    $.messager.alert('警告', '嫌疑人手环数量不足！');
                }
            },
            onSelect: function (rec) {
                $('#dataCuffIdText').val(rec.value);
            }
        });
        // startImgGet();
        //启动预览
        GWQ_StartPreview(1);
        //GWQ_StartPreviewShow(0);
        // 绑定回车事件
        $('#dataCuffId').combobox("textbox").keydown(function (e) {
            enterKeyEvent();
        });
        // 手环
        if (document.getElementById("selte").checked) {
            $('#selte').change();
            $('#selte').prop('checked', false);
        } else {
            $('#dataCuffId').next(".combo").hide();
        }
    }
    else {
        $.messager.alert('警告', '信息填写不完整!');
    }
}

//重新启动抓拍
function startImgGet() {
    GWQ_StartGetFrame();
    $("#inSerialPhotoTips").show();
    $("#inSerialPhotoTips2").hide();
}

//拍照
function getImgBase64() {
    var base64 = getPreview();
    if (base64 != null) {
        inSerialPhotoBase64 = base64;
        $("#inSerialPhoto").attr("src", "data:image/jpeg;base64," + inSerialPhotoBase64)
    } else {
        $.messager.alert("提示", "预览数据获取失败，请检查设备是否连接正常");
    }
}

//键盘监听事件
function enterKeyEvent() {
    var e = e || window.event;
    if (e.keyCode == 13) {
        checkRing($("#dataCuffIdText").val());
    }
}

//手环校验
function checkRing(icNo) {
    var checkData = checkRingNo(icNo, 0);
    if (checkData && checkData.isError) {
        //$.messager.alert("提示",checkData.callbackData);
        $.messager.show({
            title: "提示",
            msg: checkData.content,
            timeout: 5000
        });
        $("#dataCuffIdText").val("");
        $("#dataCuffId").combobox("setValue", '');
        return;
    } else {
        if (checkData.callbackData.status == 1 || checkData.callbackData.status == 3) {
            //$.messager.alert("提示","手环已绑定或者已临时出区");
            $.messager.show({
                title: "提示",
                msg: "手环已绑定或者已临时出区！",
                timeout: 5000
            });
            $("#dataCuffIdText").val("");
            $("#dataCuffId").combobox("setValue", '');
            return;
        }
        if (checkData.callbackData.status == 0) {
            $("#dataCuffIdText").val(checkData.callbackData.cuffNo);
            $("#dataCuffId").combobox("setValue", checkData.callbackData.id);
        }
    }
}

//打开关联案件弹框
function linkCase() {
    // showDialog('#link_case_dialog','关联案件');
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
        $("#ajmc").val(erow.ajmc);
        $("#caseId").val(erow.id);
        $("#link_case_ajmc").val("已关联案件：" + erow.ajmc);
        // $('#link_case_dialog').dialog('close');
        $("#link_case_dialog").hide();
        if (erow.ajly == 101) {
            $("#jz_person_inserial").show();
            initPersonInfo(erow.ajbh);
        } else {
            $("#jz_person_inserial").hide();
        }
    }
}

// 关联警综嫌疑人信息
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

// 验证警察编号是否存在searchInUser1
function finduser() {
    var userNo = $('#sendUserNO').val();
    if (userNo.indexOf('(') >= 0) {
        userNo = userNo.substring(0, userNo.indexOf('('));
    }
    if (userNo == null || userNo.length == 0) {
        $("#sendUserNO").val("请输入警号");
        return;
    }
    var userNoInfo = {};
    userNoInfo["userNo"] = userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: '../order/searchUser.do',
        data: jsonrtinfo,
        success: function (data) {
            if (data != null && data.id != null) {
                $("#sendUserId").val(data.id);
                $("#sendUserNO").val(data.certificateNo + "(" + data.realName + ")");
            } else {
                $("#sendUserNO").val("");
                $("#sendUserId").val("");
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
            }
        },
        error: function (data) {
            $("#sendUserNO").val("");
            $("#sendUserId").val("");
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

        }
    });
}

//入区拍照启动设备拍照,设备提交图片后的回调函数
function getInSerialPhoto(ErrorCode, imgStr) {
    if (ErrorCode == 0) {
        $.messager.progress('close');
        inSerialPhotoBase64 = imgStr;
        $("#inSerialPhoto").attr("src", 'data:image/png;base64,' + imgStr);
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

//入区人证识别启动设备拍照,设备提交图片后的回调函数
function getInSerialPersonPhotoDistinguish(ErrorCode, jsonData) {
    if (ErrorCode == 0) {
        var obj = JSON.parse(jsonData);
        //1-----人证人脸都成功  2-----人证成功人脸失败  3---人证失败 人脸成功 4---人证失败 人脸失败
        if (obj.passFlag == 2) {
            inSerialPhotoBase64 = obj.base64_Face;
            $("#inSerialPhoto").attr("src", 'data:image/png;base64,' + obj.base64_Face);
        } else if (obj.passFlag == 0 || obj.passFlag == 3 || obj.passFlag == 4) {
            $.messager.confirm('提示', '人证识别不通过，是否确认入区？', function (r) {
                if (r) {
                    inSerialPhotoBase64 = obj.base64_Face;
                    $("#inSerialPhoto").attr("src", 'data:image/png;base64,' + obj.base64_Face);
                } else {
                    inSerialPhotoBase64 = "";
                    $("#inSerialPhoto").attr("src", 'image/default.jpg');
                }
            })
        }

    }
    else if (ErrorCode == -9) {
        $.messager.alert('取消', '设备已取消');
    }
    else {
        $.messager.alert('失败', '返回错误码为' + ErrorCode);
    }
    //启动预览
    GWQ_StartPreview(1);
    //GWQ_StartPreviewShow(0);
}

//加载添加入区信息的办案场所下拉框
function loadOrderArea() {
    $('#orderarea').combobox({
        url: '../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            // $('#orderarea').combobox('setText',"请选择办案场所");
            if (data != null && data.length > 0) {
                $('#orderarea').combobox('setValue', data[0].id);
            }
        },
        onChange: function (newValue, oldValue) {
        }
    });
}

// 入区原始版打印
function puTongInPrint() {
//        var cg = $('#orderPeople').combogrid('grid'); // 获取数据表格对象
//        var row = cg.datagrid('getSelected'); // 获取选择的行
//        console.log(row);
//        if(row != null){
//            $.messager.progress({
//                title:'请等待',
//                msg:'打印预览中...'
//            });
////            fileUtils.read("/zhfz/lawdocProcess/download.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&serialUUID="+row.uuid+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);            $.messager.progress('close');
//            fileUtils.read("/zhfz/lawdocProcess/download.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&serialUUID="+row.uuid+"&serialNo="+row.serialNo+"&serialId="+row.id
//                    +"&count="+1);
//            $.messager.progress('close');
//        }else{
//            $.messager.alert('提示',"未选中数据，请刷新页面！");
//        }
    var row;
    if ($("#datagrid")) {
        if (baseSerialId == null || baseSerialId == '') {
            $.messager.alert('提示', 'serialId为空，请在外侧打印');
            return;
        } else {
            //强制让表格选中一行
            $('#datagrid').datagrid('selectRecord', baseSerialId);
            //获取选中行数据
            row = $('#datagrid').datagrid("getSelected");
        }
    } else {
        row = currenMap[baseSerialId];
    }
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    console.log(row);
    if (row != null) {
//	        printChoose(row.id,row.uuid,row.areaId,0);

        fileUtils.read("/zhfz/lawdocProcess/download.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&serialUUID=" + row.uuid + "&serialNo=" + row.serialNo + "&serialId=" + row.id
            + "&count=" + 1);
        $.messager.progress('close');
    } else {
        $.messager.alert('提示', 'row，请在外侧打印');
        return;
    }
}

//弹框打印台账
function printSign() {
    var row;
    if ($("#datagrid")) {
        if (baseSerialId == null || baseSerialId == '') {
            $.messager.alert('提示', 'serialId为空，请在外侧打印');
            return;
        } else {
            //强制让表格选中一行
            $('#datagrid').datagrid('selectRecord', baseSerialId);
            //获取选中行数据
            row = $('#datagrid').datagrid("getSelected");
        }
    } else {
        row = currenMap[baseSerialId];
    }
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    if (row != null) {
        printChoose(row.id, row.uuid, row.areaId, 0);
    } else {
        $.messager.alert('提示', 'row，请在外侧打印');
        return;
    }
}

function Base64() {
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}

// 加载案件类型
function initAjlxNew() {
    $('#ajlxNews').combobox({
        url: contextPath + "/zhfz/common/code/listCodeByType.do?type=AJLX&tresh=" + new Date().getTime(),
        valueField: 'codeKey',
        textField: 'codeValue',
        onChange: function (newValue, oldValue) {
            var natureValue = $("#ajlxNews").combobox("getText");
            //加载犯罪类型
            $('#abNews').combobox({
                url: '../../bacs/combobox/listcrimetypebynature.do?nature=' + encodeURI(natureValue, "UTF-8") + '&tresh=' + new Date().getTime(),
                valueField: 'id',
                textField: 'value',
                onLoadSuccess: function (data) {

                }
            });
        }
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
    if (ErrorCode == 0) {
        var obj = JSON.parse(JsonData);
        if (obj.name != null && obj.name != '') {
            $("#orderPeople").combogrid("setValue", obj.name.trim());
        }
        var pSex;
        if (obj.sex == '男') {
            pSex = '1';
        } else if (obj.sex == '女') {
            pSex = '2';
        }
        $("#dataSex").combobox("setValue", pSex);
        $("#dataCertificateNo").val(obj.id_num);
        $("#dataCertificateTypeId").combobox("setValue", 111);
        $("#dataOrderId").combogrid("setValue", -1);
//      var nationData = $("#person_nation").combobox("getData");
//      if(nationData != null){
//          $.each(nationData, function(name, value) {
//              if(value.codeValue != null && value.codeValue != "" && value.codeValue == obj.nation){
//                  $("#person_nation").combobox("setValue",value.codeKey);
//              }
//          });
//      }
    } else if (ErrorCode == -9) {
        $.messager.alert("取消");
    } else {
        $.messager.alert("失败，返回错误码为" + ErrorCode);
    }
}

//时间转化
function formatDateTime(date) {
    var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
    var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
    var hor = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();
    return date.getFullYear() + '-' + month + '-' + day + " " + hor + ":" + min + ":" + sec;
}
