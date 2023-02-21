var outSerialPhotoBase64;
function exitBegin() {
    phototype = 'exitBegin';
    $("#inPopup").show();
    $("#inSerialBox").width("1000");
    $("#inSerialHD").hide();
    $("#outSerialHD").show();
    showNoHideStepDiv("stepDiv1", "step1", 0);
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv3", "step3", 0);
    showNoHideStepDiv("stepOutDiv1", "step2", 1);
    showNoHideStepDiv("stepOutDiv2", "step1", 0);
    $("#dialogTitle").html("出区流程");
    $("#dataType").combobox({
        onChange:function(value){
            getFirstReason();
        }
    });
    $("#dataType").combobox('setValue', "");
    $("#direction").combobox('setValue', '');
    $("#confirm_result").combobox('setValue', '');

}


function closeOutMpopup(){
    $('#typeInfo').form('clear');
    $('#cuffInfo').form('clear');
    $('#photoInfo').form('clear');
    $("#dataOutReason").val("");
    showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
    showNoHideStepDiv("stepDiv1", "outStep1", 1);
    showNoHideStepDiv("stepDiv2", "outStep2", 0);
    showNoHideStepDiv("stepDiv3", "outStep3", 0);
    $("#inSerialHD").show();
    $("#outSerialHD").hide();
    $("#outPopup").hide();
}
function outBeforePhoto(){
    showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 1);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
    //关闭预览
    closePreview(1);
}
var firstDirection = [[{
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
            data: dataTypeDirection
        });
        $('#direction').combobox('setValue', '');
    } else {
        var dataTypeDirection = [{  lable: "",value: "请选择去向"}];
        $("#direction").combobox({
            valueField: 'lable',
            textField: 'value',
            data: dataTypeDirection
        });
    }
}

function typeNext(next) {
    var dataType = $('#direction').combobox("getValue");
    var direction = $('#direction').combobox('getText');
    var confirm_result = $('#confirm_result').combobox('getText');
    if (direction == "选择去向" ||direction == "请选择去向"  || confirm_result == "选择裁决结果") {
        $.messager.alert('警告', '请选择或填写去向和裁决结果!');
        return;
    }
    if (dataType != null && dataType != '' && direction != null
        && direction != '' && confirm_result != null
        && confirm_result != '') {
        showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
        showNoHideStepDiv("stepOutDiv2", "outStep2", 1);
        showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
        showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
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
                if (row == null) {
                    $('#serialNo').combogrid('setValue',"");
                }
                else {
                    $('#cuffNumber').val(row.cuffNo);
                    $("#outSendUserName").val(row.sendUserName);
                    $("#outSendUserId").val(row.sendUserId);
                    $("#outAjmc").val(row.ajmc);
                    var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoName);
                    $("#inphoto").attr("src",e);
                }
            }
        });
        $('#dataOutUserId2').combogrid({
            panelWidth: 210,
            panelHeight:200,
            mode: 'remote',
            url: '../policeEntrance/getPoliceEnteance.do',
            idField: 'policeId',
            textField: 'name',
            queryParams : {
                trefresh:new Date().getTime()
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

    } else {
        $.messager.alert('警告', '信息填写不完整!');
    }
}
function outBeforeCuff(){
    showNoHideStepDiv("stepOutDiv1", "outStep1", 1);
    showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
    showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
    showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
}
function cuffNext() {
    var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    var serialNo = row.serialNo;
    if (serialNo != null && serialNo != '' ) {
        var entranceData = $('#entranceData').serializeObject();
        entranceData["serialNo"] = row.serialNo;
        var personstatusjson = JSON.stringify(entranceData);
        jQuery .ajax({
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
                        showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
                        showNoHideStepDiv("stepDiv2", "outStep3", 1);
                        showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
                        $("#cuffDialog").hide();
                        $("#editPhotoTD").show();
                        $("#inserialButtons").hide();
                        $("#editPhotoButtons").hide();
                        $("#outSerialButtons").show();
                        $("#editPhotoTR").show();
                        var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoId);
                        $("#oldphoto").attr("src",e);
                        initSwf();
                    } else if (result == 1) {
                        $.messager.alert('警告', "抱歉," + row.name+ "处于被审讯状态，不能出区！");
                        closeOutMpopup();
                        isPhoto = 0;
                    } else if (result == 2) {
                        $.messager.alert('警告', "抱歉," + row.name + "处于被看押状态，不能出区！");
                        closeOutMpopup();
                        isPhoto = 0;
                    } else if (result == 3) {
                        var dataType = $("#dataType").combobox("getValue");
                        if (dataType == 1) {
                            $.messager .confirm( '提醒','该出区人员还未领取随身物品，确定继续出区?',
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
                            $.messager.alert('警告', "抱歉," + row.name+ "有物品未领取！");
                            closeOutMpopup();
                            isPhoto = 0;
                        }
                    }
                }
            });
    } else {
        $.messager.alert('警告', '信息填写不完整!');
    }
}
//人脸比对下一步
function photoNext() {
    if(outSerialPhotoBase64 != '' && outSerialPhotoBase64 != null) {
        var inPhotoUrl = $("inPhoto").attr("src");
        if(inPhotoUrl != "image/noPhoto.jpg"){
            startInAndOutPhotoDistinguish(inPhotoUrl);
            return;
        } else{
            $.messager.alert('提示', '入区照片不存在，请先添加入区照片!');
            return;
        }

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
                    var jsonrtinfo = JSON.stringify(entranceData);
                    // $.messager.progress({
                    //     title: '请等待',
                    //     msg: '人像比对中...'
                    // });
                    // // 上传图片开始
                    // var imageForm = $('#imageForm').serializeObject();
                    // imageForm["imageData"] = str;
                    // var imageinfo = JSON.stringify(imageForm);
                    // jQuery.ajax({
                    //     async: false,
                    //     type: 'POST',
                    //     contentType: 'application/json',
                    //     url: 'outgetpicture.do',
                    //     data: imageinfo,
                    //     dataType: 'json',
                    //     success: function (data) {
                    //         $.messager.progress('close');
                    //         var result = data.callbackData;
                    if (confirm('请确认两张照片是否为同一个人，确定允许出区？')) {
                        exitEdit(jsonrtinfo);
                    }
                    //     },
                    //     error: function (data) {
                    //         $.messager.alert("提示", "未知错误！");
                    //         $.messager.progress('close');
                    //     }
                    // });
                }
            })
    } else{
        $.messager.alert('提示', '请在页面上点击拍照，并在设备点击确认拍照，提交照片!');
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
            var es = $('#e_status').combobox('getValue');
            $('#datagrid').datagrid('load', {
                name: $('#e_person').textbox('getValue'),
                certificateNo: $('#e_certificateNo').textbox('getValue'),
                status: es,
                start_date: $('#e_start_date').datebox('getValue'),
                end_date: $('#e_end_date').datebox('getValue'),
                trefresh: new Date().getTime()
            });
            showNoHideStepDiv("stepOutDiv1", "outStep1", 0);
            showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
            showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
            showNoHideStepDiv("stepOutDiv4", "outStep4", 1);
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
    $('#imageForm').form('clear');
}
//出区拍照反馈调用方法
function getOutSerialPhoto(ErrorCode,imgStr){
    if(ErrorCode==0)
    {
        outSerialPhotoBase64 = imgStr;
        $("#outPhoto").attr("src",'data:image/png;base64,'+imgStr);
    }
    else if(ErrorCode==-9)
    {
        $.messager.alert('取消','设备已取消');
    }
    else
    {
        $.messager.alert('失败','返回错误码为'+ErrorCode);
    }
}