var editType = 0;
function entranceEdit(serialId) {
    editType=1;
    $("#entranceEditDialog").show();
    loadEntranceEditProcedure();// 加载入区手续下拉框
    loadCertificateType();
    var rowData =currenMap[serialId];
    $('#ecertificateTypeId').combobox(
        {
            url : '../../common/combobox/certificateTypes.do',
            valueField : 'id',
            textField : 'value',
            onLoadSuccess : function(data) {
                $('#ecertificateTypeId').combobox('setValue',rowData.certificateTypeId);
            }
        });
    var direction = "";
    var editReason = "";
    if(rowData.outReason != null && rowData.outReason.indexOf(",") >= 0){
        direction = rowData.outReason.substring(0,rowData.outReason.indexOf(","));
        editReason = rowData.outReason.substring(rowData.outReason.indexOf(","),rowData.outReason.length);
    }
    $('#entranceEdit').form('load', {
        eid: rowData.id,
        ename: rowData.name,
        esex:rowData.sex,
        epersonInfo:rowData.personInfo,
        epersonId: rowData.personId,
        ecertificateNo: rowData.certificateNo,
        entranceProcedureCmb: rowData.entranceProcedure,
        writtenTimeCmb: valueToDate(rowData.writtenTime),
        editAjmc:rowData.interrogateCaseName,
        einUserNo1:rowData.sendUserNo,
        einUserId1:rowData.sendUserId,
        ecaseId:rowData.caseId
    });
    $("#editAjmc").val(rowData.interrogateCaseName);
}
//嫌疑人历史弹框界面修改
function entranceEdit2(index){

    editType=2;
    $("#entranceEditDialog").show();
    var row = $('#datagrid').datagrid('getRows')[index];

    $('#oldAjbh').val(row.caseNo);
    $('#oldRybh').val(row.rybh);
    $('#oldZjhm').val(row.certificateNo);

    $("#rssj").val(row.inTime);

    $('#editAjbh').val(row.caseNo);
    $('#editRybh').val(row.rybh);

    if(row != null){
        loadEntranceEditProcedure();// 加载入区手续下拉框
        loadCertificateType();
        $('#ecertificateTypeId').combobox(
            {
                url : '../../common/combobox/certificateTypes.do',
                valueField : 'id',
                textField : 'value',
                onLoadSuccess : function(data) {
                    $('#ecertificateTypeId').combobox('setValue',row.certificateTypeId);
                }
            });
        var direction = "";
        var editReason = "";
        if(row.outReason != null && row.outReason.indexOf(",") >= 0){
            direction = row.outReason.substring(0,row.outReason.indexOf(","));
            editReason = row.outReason.substring(row.outReason.indexOf(","),row.outReason.length);
        }
       /* $('#entranceEdit').form('load', {
            eid: row.id,
            ename: row.name,
            esex:row.sex,
            epersonInfo:row.personInfo,
            epersonId: row.personId,
            ecertificateNo: row.certificateNo,
            entranceProcedureCmb: row.entranceProcedure,
            writtenTimeCmb: row.writtenTime,
            editAjmc:row.interrogateCaseName,
            einUserNo1:row.sendUserNo,
            einUserId1:row.sendUserId,
            ecaseId:row.caseId
        });*/

        $('#eid').val(row.id);
        $('#ename').val(row.name);
        $('#esex').combobox('setValue', row.sex);
        $('#epersonInfo').combobox('setValue', row.personInfo);
        $('#entranceProcedureCmb').combobox('setValue', row.entranceProcedure);
        $('#epersonId').val(row.personId);

        $('#editAjbh').val(row.caseNo);
        $('#editRybh').val(row.rybh);
        //console.log($('#epersonId').val());
        $('#ecertificateNo').val(row.certificateNo);

       // $('#writtenTimeCmb').val(row.writtenTime);
        $('#writtenTimeCmb').datetimebox('setValue', valueToDate(row.writtenTime));

        $('#einUserNo1').val(row.sendUserNo);
        $('#einUserId1').val(row.sendUserId);
        $('#ecaseId').val(row.caseId);

        $('#orderId').val(row.orderId);
        $("#editAjmc").val(row.interrogateCaseName);
        $(":radio[name='sfxxcj'][value='" + row.sfxxcj + "']").prop("checked", "checked");
        $(":radio[name='sfsyjdEdit'][value='" + row.sfsyjd + "']").prop("checked", "checked");
        
        //正常出区去向和裁决结果
        var dataTypeDirection = firstDirection[0];
        // 将去向数组中的值填充到去向下拉框中
        $('#outReasonEdit').combobox({
            valueField: 'value',
            textField: 'value',
            data: dataTypeDirection
        });
        $('#outReasonEdit').combobox('setValue', row.outReason);
        
        $('#confirmResultEdit').combobox('setValue', row.confirmResult);
        
        
        
    }else{
        $.message.alert("数据错误，rowData为null，请刷新页面");
    }
}

// 加载入区手续
function loadEntranceEditProcedure() {
    $('#entranceProcedureCmb').combobox({
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
}
var entranceEditDirection = [[{
    lable: "",
    value: "请选择去向"
},{
    lable: "1",
    value: "送拘留所执行行政拘留"
}, {
    lable: "2",
    value: "送看守所执行刑事拘留"
}, {
    lable: "3",
    value: "行政拘留不执行"
}, {
    lable: "4",
    value: "未达到法定年龄不予以处理"
}, {
    lable: "5",
    value: "行政拘留暂停执行"
}, {
    lable: "6",
    value: "排除嫌疑离开"
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
    lable: "12",
    value: "直接取保候审"
}], [{
    lable: "",
    value: "请选择去向"
},{
    lable: "1",
    value: "验伤"
}, {
    lable: "2",
    value: "辨认现场或尸体"
}, {
    lable: "3",
    value: "起赃"
}, {
    lable: "4",
    value: "外出就医"
}, {
    lable: "5",
    value: "伤情鉴定"
}, {
    lable: "6",
    value: "其它"
}], [{
    lable: "",
    value: "请选择去向"
},{
    lable: "1",
    value: "突发疾病"
}, {
    lable: "2",
    value: "其它"
}], [{
    lable: "",
    value: "请选择去向"
},{
    lable: "1",
    value: "其它"
}]];

function getEditReason() {
    // 获得类型下拉框的对象
    var dataType = $("#edataType").combobox("getValue");
    // 获得去向下拉框的对象
    var direction = document.getElementById("edirection");
    // 得到对应类型的去向数组
    if (dataType != null && dataType != "") {
        var dataTypeDirection = entranceEditDirection[dataType];
        // 将去向数组中的值填充到去向下拉框中
        $('#edirection').combobox({
            valueField: 'lable',
            textField: 'value',
            data: dataTypeDirection
        });
        $('#edirection').combobox('setValue', '');
    } else {
        var dataTypeDirection = [{  lable: "",value: "请选择去向"}];
        $("#edirection").combobox({
            valueField: 'lable',
            textField: 'value',
            data: dataTypeDirection
        });
    }
}
//保存修改
function entranceSave() {
    var certificateNo=$("#ecertificateNo").val();

    if ($("#ecertificateTypeId").combobox("getValue") == 111){
        var regex=/.*([a-z])+.*/;
        if(regex.test(certificateNo)){
            $.messager.alert('提醒', '请将您输入的身份证号中的小写字母改为大写！');
            return;
        }

        var reg1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if(reg1.test(certificateNo) === false)
        {
            $.messager.alert('提醒', '请输入合法的身份证号码！');
            return;
        }
    }

    if ($('#ecertificateTypeId').combobox('getValue') != null
        && $('#ecertificateTypeId').combobox('getValue') != ''
        && $('#ecertificateTypeId').combobox('getValue') == 111) {
        // 身份证验证
        var ecertificateNo = $('#ecertificateNo').val();
        if (ecertificateNo != null && ecertificateNo != "") {
            if (getAge(ecertificateNo) < 18) {
                $.messager.alert('提醒', '该嫌疑人为未成年！');
            }
            $('#birth').val(getBirth(ecertificateNo));

            if($("#oldAjbh").val() != $("#editAjbh").val() || $("#oldRybh").val() != $("#editRybh").val()){
                // 调用回传程序更新接口
                updateOrcal();
            }else{
                editSaveInfo();
            }
            /*if (checkId(ecertificateNo) == true) {
                if (getAge(ecertificateNo) < 18) {
                    $.messager.alert('提醒', '该嫌疑人为未成年！');
                }
                $('#birth').val(getBirth(ecertificateNo));
                editSaveInfo();
            } else {
                $.messager.alert('提醒', '您输入身份证不合法！');
                $('#dataCertificateNo').val("");
            }*/

        } else {
            $.messager.alert('提醒', '请输入合法的身份证号码！');
        }
    } else {

        if($("#oldAjbh").val() != $("#editAjbh").val() || $("#oldRybh").val() != $("#editRybh").val()){
            // 调用回传程序更新接口
            updateOrcal();
            //手动关联
            $("#sfzdgl").val(2)
        }else{
            editSaveInfo();
        }
    }
}

function updateOrcal() {
    var rssj = $("#rssj").val();
    var ajbh = $("#editAjbh").val();
    var rybh = $("#editRybh").val();
    var zjhm = $("#ecertificateNo").val();
    var json = {"rssj":rssj,"ajbh":ajbh,"rybh":rybh,"zjhm":zjhm};
    var jsonrtinfo = JSON.stringify(json);
    jQuery.ajax({
        type : 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: 'http://14.112.65.174:8080/interrogate/restful/update/updateAjRyBh',
        data: jsonrtinfo,
        success: function (data) {
            editSaveInfo();
        },
        error: function (data) {
            $.messager.alert('错误', '修改失败!');
        }
    });
}

// 验证警察编号是否存在searchInUser1
function findUserEdit() {
    var userNo = $('#einUserNo1').val();
    if (userNo == null || userNo.length == 0) {
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
                $("#einUserId1").val(data.id);
            } else {
                $("#einUserNo1").val("");
                $("#einUserId1").val("");
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
            }
        },
        error: function (data) {
            $("#einUserNo1").val("");
            $("#einUserId1").val("");
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');

        }
    });
}
function editSaveInfo() {
    var entranceForm = $('#entranceEdit').serializeObject();
    var entranceData = $('#entranceData').serializeObject();
    //entranceData["id"] = entranceForm["eid"];//入区id
    entranceData["id"] = $('#eid').val();//入区id
    //entranceData["personId"] = entranceForm["epersonId"];//人员id
    entranceData["personId"] = $('#epersonId').val();//人员id
    //entranceData["name"] = entranceForm["ename"];//姓名
    entranceData["name"] = $('#ename').val();//姓名
    entranceData["sex"] = $("#esex").combobox('getValue');//性别
    entranceData["certificateTypeId"] = $('#ecertificateTypeId').combobox('getValue');//证件类型
   // entranceData["certificateNo"] = entranceForm["ecertificateNo"];//证件号码
    entranceData["certificateNo"] = $('#ecertificateNo').val();//证件号码
    //entranceData["personInfo"] = entranceForm["epersonInfo"];//人员类型
    entranceData["personInfo"] = $('#epersonInfo').combobox('getValue');//人员类型
    //entranceData["entranceProcedure"] = entranceForm["entranceProcedureCmb"];// 入区手续
    entranceData["entranceProcedure"] = $('#entranceProcedureCmb').combobox('getValue');// 入区手续
   // entranceData["writtenTime"] = entranceForm["writtenTimeCmb"];// 手续开具时间
    entranceData["writtenTime"] = $('#writtenTimeCmb').datetimebox('getValue');;// 手续开具时间

    //entranceData["sendUserId"] = entranceForm["einUserId1"];// 送押民警
    entranceData["sendUserId"] = $('#einUserId1').val();// 送押民警
    entranceData["orderRequestId"] = $('#orderId').val();// 预约信息
    var sfxxcj = $("input[name='sfxxcj']:checked").val();//是否信息采集
    entranceData["sfxxcj"] = sfxxcj;
    
    var sfsyjd = $("input[name='sfsyjdEdit']:checked").val();//是否送押解队
    entranceData["sfsyjd"] = sfsyjd;
    
    entranceData["outReason"] = $('#outReasonEdit').combobox('getValue');//正常出区去向
    entranceData["confirmResult"] = $('#confirmResultEdit').combobox('getValue');//裁决结果
    if ($('#oldAjbh').val() !== $('#editAjbh').val() || $('#oldRybh').val() !== $('#editRybh').val() ||
        $('#oldZjhm').val() !== $('#ecertificateNo').val()){
        entranceData["flag"] ='1';
    }else{
        entranceData["flag"] ='0';
    }
    entranceData["ajbh"] = $('#editAjbh').val();
    entranceData["rybh"] = $('#editRybh').val();
    entranceData["sfzdgl"] = $('#sfzdgl').val();// 是否自动关联案件人员编号
    entranceData["rssj"] = $("#rssj").val();


    var jsonrtinfo = JSON.stringify(entranceData);
    $.messager.progress({
        title: '请等待',
        msg: '修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: 'suspectupdate.do',
        data: jsonrtinfo,
        success: function (data) {
            //$.messager.alert(data.title, data.content);
            $.messager.show({
                id:'提示',
                title:data.title,
                msg:data.content,
                timeout:3000,
                showType:'slide'
            });
            if(editType == 1){
                initSerialData();
            }else{
                doSearch();
            }
            $('#entranceEditDialog').hide();
            $.messager.progress('close');
            $('#entranceForm').form('clear');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

/*时间转换*/
function valueToDate(value, formatStr) {
    if(!formatStr){
        formatStr = 'yyyy-MM-dd hh:mm:ss';
    }
    if(value==null || value==0) {
        return "";
    }else{
        var d = new Date(value);
        return d.format(formatStr);
    }
}
