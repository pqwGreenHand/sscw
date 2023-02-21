var baseSerialId = "";
function entrance() {
    codeCombo('dataSex','XBID','');
    baseSerialId = "";
    loadOrderArea();
    phototype = 'entrance';
    $("#inPopup").show();
    showNoHideStepDiv("stepDiv1", "step1", 1);
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv3", "step3", 0);
    showNoHideStepDiv("stepOutDiv1", "step2", 0);
    showNoHideStepDiv("stepOutDiv2", "step1", 0);
    $("#isBasicInfo").form('clear');
    $("#cuff").form('clear');
    $("#cuff").form('clear');
    loadEntranceProcedure('entranceProcedure');
    loadPersonInfo();
    loadCertificateType();
    $("#writtenTime").datetimebox('setValue',valueToDate(new Date()));
    $('#orderPeople').combogrid({
        panelWidth: 200,
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
            width: 50
        }, {
            field: 'certificateNo',
            title: '证件号码',
            width: 150
        }]],
        onChange: function (newValue, oldValue) {
            var oc = $('#orderPeople').combogrid('grid'); // 获取数据表格对象
            var ocrow = oc.datagrid('getSelected'); // 获取选择的行
            if (ocrow != null && ocrow.id != null
                && ocrow.id != 0) {
                console.log(ocrow.areaId);

                $('#dataName').val(ocrow.name);
                $("#ajmc").val(ocrow.ajmc);
                $("#link_case_ajmc").val("已关联案件："+ocrow.ajmc);
                $("#orderarea").combobox("setValue",ocrow.areaId);
                $("#caseId").val(ocrow.caseId);
                $('#dataSex').combobox('setValue', ocrow.sex);
                $('#dataCertificateTypeId').combobox( 'setValue', ocrow.certificateTypeId);
                $('#dataCertificateNo').val(ocrow.certificateNo);
                $('#dataPersonInfo').combobox('setValue',ocrow.personType);
                $("#personId").val(ocrow.id);
                $('#dataOrderId').combogrid('setValue',ocrow.orderRequestId);
                $('#mainPolice') .combogrid({
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
                            $('#mainPolice') .combogrid( 'setValue', data.rows[0].id);
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
                                        $('#dataInUserNo2') .val( data.callbackData.certificateNo);
                                        $('#dataInUserId2') .val( data.callbackData.policeId);
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
}
function saveStepDiv1Data(){
    if ($('#writtenTime').datebox('getValue') == null
        || $('#writtenTime').datebox('getValue') == "") {
        $.messager.alert('提醒', '请选择手续开具时间！');
        return;
    }
    if ($('#orderarea').combobox('getValue') == null
        || $('#orderarea').combobox('getValue') == "") {
        $.messager.alert('提醒', '请选择办案场所！');
        return;
    }
    if ($('#caseId').val() == null
        || $('#caseId').val() == "") {
        $.messager.alert('提醒', '请关联案件！');
        return;
    }
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
    }else{
        changeDiv();
    }
}

function backStepDiv1(){
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv1", "step1", 1);
    showNoHideStepDiv("stepDiv3", "step3", 0);
}

function saveStepDiv2Data(){
    var canvas = document.getElementById("canvas");
    var str=canvas.toDataURL("image/png");
    var len=str.length;
    if (len > 5000) {
        if ($("#dataCuffIdText").val() == "" && ($("#dataCuffId").combobox("getValue") == null || $("#dataCuffId").combobox("getValue") == "")) {
            $.messager.alert('提示', '请扫描手环!');
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
                isBasicInfo["certificateTypeId"] = isBasicInfo["dataCertificateTypeId"];
                isBasicInfo["sex"] = isBasicInfo["dataSex"];
                isBasicInfo["personInfo"] = isBasicInfo["dataPersonInfo"];
                isBasicInfo["name"] = isBasicInfo["dataName"];

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
                        $.messager.progress('close');
                        $.messager.progress({
                            title: '请等待',
                            msg: '入区已完成，正在进行图片上传，情稍等...'
                        });
                        //图片上传开始
                        var imageForm = $('#imageForm').serializeObject();
                        var formData = new FormData();
                        var serialId = data.callbackData.id;
                        var _blob = dataURLtoBlob(str);
                        formData.append("file", _blob, 'a.png');
                        formData.append("serialId", serialId);
                        formData.append("photoName", data.callbackData.inPhotoId);
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
                                if(data.isError){
                                    $.messager.alert(data.title, data.content+data.callbackData);
                                }else{
                                    $.messager.show({
                                        title:'提示',
                                        msg:data.content,
                                        timeout:3000
                                    });
                                    showNoHideStepDiv("stepDiv2", "step2", 0);
                                    showNoHideStepDiv("stepDiv2", "step2", 0);
                                    showNoHideStepDiv("stepDiv3", "step3", 1);
                                    initSerialData();
                                    signStart(serialId);
                                }
                            },
                            error : function(data){
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
    } else{
        $.messager.alert('提示', '请先点击允许再点击拍照！');
    }
}

//需要隐藏的步骤id；显示还是隐藏 type:0隐藏 1显示
function showNoHideStepDiv(stepDivId,stepId,type){
    var tempStep = $("#"+stepId);
    var tempStepDiv = $("#"+stepDivId);
    if(type==1){
        tempStepDiv.show();
        tempStep.addClass("now");
    }else{
        tempStepDiv.hide();
        tempStep.removeClass("now");
    }
}

function loadEntranceProcedure(id) {
    // 加载入区手续
    $('#'+id).combobox({
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
    $('#entranceProcedure').combobox('setValue', '口头传唤');
}
// 加载人员类型
function loadPersonInfo(){
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
}
// 证件
function loadCertificateType(){
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
            url: "../combobox/listWMSCount.do" ,
            success: function (data) {
                var count = data + 1;
                $('#orderPeople').combogrid('setValue', "无名氏" + count);
                $('#dataCertificateNo').val('XXXXX.....XXXXX'+count);
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
function changeDiv(next){
    var cg = $('#dataOrderId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    var dataOrderId = row.id;
    if (dataOrderId != null && dataOrderId != ''
        && $("#dataName").val() != null && $("#dataName").val() != ''
        && $("#dataSex").combobox('getValue') != ''
        && $('#dataCertificateTypeId').combobox('getValue') != null
        && $('#dataCertificateTypeId').combobox('getValue') != ''){
        showNoHideStepDiv("stepDiv1", "step1", 0);
        showNoHideStepDiv("stepDiv2", "step2", 1);
        initSwf();
        // 入区嫌疑人手环
        $('#dataCuffId').combobox({
            url: '../combobox/getallcuff.do?type=0',
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function (data) {
                if (data.length == 0) {
                    $.messager.alert('警告', '嫌疑人手环数量不足！');
                }
            }
        });
        // 绑定回车事件
        $('#dataCuffId').combobox("textbox").keydown(function (e) {
            enterKeyEvent();
        });
        // 手环
        if (document.getElementById("selte").checked) {
            $('#selte').change();
            $('#selte').prop('checked', false);
        }else{
            $('#dataCuffId').next(".combo").hide();
        }
    }
    else {
        $.messager.alert('警告', '信息填写不完整!');
    }
}
//键盘监听事件
function enterKeyEvent(){
    var e = e || window.event;
    if (e.keyCode == 13) {
        checkRing($("#dataCuffIdText").val());
    }
}

//手环校验
function checkRing(icNo){
    var checkData = checkRingNo(icNo,0);
    if(checkData.isError){
        $.messager.alert("提示",checkData.callbackData);
        return;
    }else{
        if(checkData.callbackData.status == 1 ||checkData.callbackData.status == 3){
            $.messager.alert("手环已绑定或者已临时出区");
            $("#dataCuffIdText").val("");
            return;
        }
        if(checkData.callbackData.status == 0 ){
            $("#dataCuffIdText").val(checkData.callbackData.cuffNo);
        }
    }
}



//打开关联案件弹框
function linkCase(){
    // showDialog('#link_case_dialog','关联案件');
    $("#link_case_dialog").show();
    loadLinkCaseList();
    if($('#link_case_datagrid')) {
        $('#link_case_datagrid').datagrid({
            onLoadSuccess: function (data) {
                if($("#caseId")){
                    var caseId = $("#caseId").val();
                    if (caseId != null && caseId != '' && data != null && data.rows != null && data.rows.length != 0) {
                        for (var i=0;i<data.rows.length;i++){
                            if(caseId == data.rows[i].id){
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
function linkCaseSave(){
    var erow = $('#link_case_datagrid').datagrid('getSelected');
    if(erow==null)
    {
        $.messager.alert('提示', '请先选择一行!');
        return;
    } else{
        $("#ajmc").val(erow.ajmc);
        $("#caseId").val(erow.id);
        $("#link_case_ajmc").val("已关联案件："+erow.ajmc);
        // $('#link_case_dialog').dialog('close');
        $("#link_case_dialog").hide();
    }
}

// 验证警察编号是否存在searchInUser1
function finduser() {
    var userNo = $('#sendUserNO').val();
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
                $("#sendUserId").val(data.id);
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

//加载添加入区信息的办案场所下拉框
function loadOrderArea(){
    $('#orderarea').combobox({
        url: '../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('#orderarea').combobox('setText',"请选择办案场所");
        },
        onChange: function (newValue, oldValue) {
        }
    });
}
//弹框打印台账
function printSign(){
    $.messager.progress({
        title: '请等待',
        msg: '下载中...'
    });
    jQuery.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: 'downloadWord.do',
        data: {
            exportSerialId:baseSerialId
        },
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            if(data.error){
                $.messager.alert("提示",data.content);
                return;
            }else {
                var row = data.callbackData;
                if(row){
                    var cRow = currenMap[baseSerialId];
                    fileUtils.load('ba',fileTypeEntity.FILETYPRRQ,cRow.uuid,cRow.areaId,row.fileName);
                }
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
    $.messager.progress('close');
}
/* 拍照  ************/
var pos = 0;
var ctx = null;
var cam = null;
var image = null;

var filter_on = false;
var filter_id = 0;
var isPhoto=0;
function photoScan(){
    isPhoto=1;
    _register();
    webcam.capture();
    changeFilter();
    void(0);
}
function changeFilter() {
    if (filter_on) {
        filter_id = (filter_id + 1) & 7;
    }
}
function toggleFilter(obj) {
    if (filter_on = !filter_on) {
        obj.parentNode.style.borderColor = "#c00";
    } else {
        obj.parentNode.style.borderColor = "#333";
    }
}
//入区摄像头初始化
function cameraInit(){
    //摄像头
    jQuery("#webcam").webcam({
        width: 240,
        height: 180,
        mode: "callback",
        swffile: "jscam_canvas_only.swf",
        onSave: function(data) {
            var col = data.split(";");
            var img = image;
            if (false == filter_on) {
                for(var i = 0; i < 240; i++) {
                    var tmp = parseInt(col[i]);
                    img.data[pos + 0] = (tmp >> 16) & 0xff;
                    img.data[pos + 1] = (tmp >> 8) & 0xff;
                    img.data[pos + 2] = tmp & 0xff;
                    img.data[pos + 3] = 0xff;
                    pos+= 4;
                }
            } else {
                var id = filter_id;
                var r,g,b;
                var r1 = Math.floor(Math.random() * 255);
                var r2 = Math.floor(Math.random() * 255);
                var r3 = Math.floor(Math.random() * 255);

                for(var i = 0; i < 240; i++) {
                    var tmp = parseInt(col[i]);

                    /* Copied some xcolor methods here to be faster than calling all methods inside of xcolor and to not serve complete library with every req */

                    if (id == 0) {
                        r = (tmp >> 16) & 0xff;
                        g = 0xff;
                        b = 0xff;
                    } else if (id == 1) {
                        r = 0xff;
                        g = (tmp >> 8) & 0xff;
                        b = 0xff;
                    } else if (id == 2) {
                        r = 0xff;
                        g = 0xff;
                        b = tmp & 0xff;
                    } else if (id == 3) {
                        r = 0xff ^ ((tmp >> 16) & 0xff);
                        g = 0xff ^ ((tmp >> 8) & 0xff);
                        b = 0xff ^ (tmp & 0xff);
                    } else if (id == 4) {

                        r = (tmp >> 16) & 0xff;
                        g = (tmp >> 8) & 0xff;
                        b = tmp & 0xff;
                        var v = Math.min(Math.floor(.35 + 13 * (r + g + b) / 60), 255);
                        r = v;
                        g = v;
                        b = v;
                    } else if (id == 5) {
                        r = (tmp >> 16) & 0xff;
                        g = (tmp >> 8) & 0xff;
                        b = tmp & 0xff;
                        if ((r+= 32) < 0) r = 0;
                        if ((g+= 32) < 0) g = 0;
                        if ((b+= 32) < 0) b = 0;
                    } else if (id == 6) {
                        r = (tmp >> 16) & 0xff;
                        g = (tmp >> 8) & 0xff;
                        b = tmp & 0xff;
                        if ((r-= 32) < 0) r = 0;
                        if ((g-= 32) < 0) g = 0;
                        if ((b-= 32) < 0) b = 0;
                    } else if (id == 7) {
                        r = (tmp >> 16) & 0xff;
                        g = (tmp >> 8) & 0xff;
                        b = tmp & 0xff;
                        r = Math.floor(r / 255 * r1);
                        g = Math.floor(g / 255 * r2);
                        b = Math.floor(b / 255 * r3);
                    }

                    img.data[pos + 0] = r;
                    img.data[pos + 1] = g;
                    img.data[pos + 2] = b;
                    img.data[pos + 3] = 0xff;
                    pos+= 4;
                }
            }

            if (pos >= 0x2A300) {
                ctx.putImageData(img, 0, 0);
                pos = 0;
            }
        },
        onCapture: function () {
                webcam.save();
                jQuery("#flash1").css("display", "block");
                jQuery("#flash1").fadeOut(100, function () {
                jQuery("#flash1").css("opacity", 1);
            });
        },
    });
}
function initSwf() {
    jQuery("body").append("<div id=\"flash\"></div>");
    var canvas = document.getElementById("canvas");
    if (canvas.getContext) {
        ctx = document.getElementById("canvas").getContext("2d");
        ctx.clearRect(0, 0, 240, 180);
        var img = new Image();
        img.src = "image/logo.gif";
        img.onload = function() {
            ctx.drawImage(img, 129, 89);
        }
        image = ctx.getImageData(0, 0, 240, 180);
    }
    jQuery("#flash").css({ height: "150px" });
}

function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], { type: mime });
}