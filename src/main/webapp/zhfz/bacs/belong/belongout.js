var lockId = null;
var pId = null;
var areaId;
$(function () {
    ssareaid = $('#ssareaid').val();
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            var data = checkRingNo($(event.target).val(), 0);
            if (data.callbackData) {
                readRingNo(data.callbackData.cuffNo);
            } else {
                $.messager.show({
                    title: '提示',
                    msg: "无效的手环！！",
                    timeout: 3000
                });
                $(event.target).val('');
            }
        }
    });
    $.get("../../../common/getSessionInfo.do", function(data){
        var sessionObj = eval('('+data+')');
        areaId=sessionObj.currentArea.id
    });
    lockId = getUrlParam("s_lockerId");
    $('#detid').datagrid({
        title: '随身物品详情',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: 'Loading...',
        method: 'get',
        queryParams: {enpId: -99, trefresh: new Date().getTime()},
        url: 'listAllBelongdetByLockerId.do',
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {title: 'id', field: 'id', width: 80, sortable: true, hidden: true},
            {field: 'belongingsId', title: '物品暂存ID', hidden: true},
            {field: 'serialId', title: 'serialId', hidden: true}
        ]],
        columns: [[
            {field: 'personName', title: '嫌疑人姓名', width: 40},
            {field: 'lockerIds', title: '储物柜编号', width: 40, hidden: true},
            {
                field: 'lockerId', title: '储物柜编号', width: 70,
                formatter: function (field, rec, index) {
                    return rec.cabinetGroup + " " + rec.cabinetNo + "号柜";
                }
            },
            {field: 'name', title: '物品名称', width: 40},
            {field: 'detailCount', title: '数量', width: 20,},
            {field: 'unit', title: '单位', width: 40},
            {field: 'saveMethod', title: '保管措施', width: 80},
            {field: 'description', title: '特征', width: 130},
            {
                field: 'isGet', title: '是否已领取', width: 40,
                formatter: function (value, rec) {
                    if ('true' == value || "1" == value) {
                        return '<font color="yellow">已领取</font>'
                    }
                    else if ("0" == value) {
                        return '<font color="gred">未领取</font>'
                    }
                }
            },
            /*{field:'getWay',title:'领取方式',width:30,
                formatter:function(value,rec){
                    if('true'==value ||"1"==value){
                        return '本人领取'
                    }
                    else if("0"==value){
                        return '未领取'
                    }
                    else if("2"==value){
                        return '委托他人代为领取'
                    }
                    else if("3"==value){
                        return '本人收到扣押物品清单'
                    }
                }},
            {field:'getPerson',title:'领取人',width:30},
            {field:'getTime',title:'领取时间',width:80,
                  formatter:function(value,rec){
                        return valueToDate(value);
                    }
              },*/
            {
                field: 'id', title: '操作', width: 40,
                formatter: function (value, row, index) {
                    if (isGridButton("boxopens")) {
                        var e = '<span class="spanRow"><a href="#" class="gridopenbox" onclick="boxopens(' + index + ')">领取</a></span>'
                        if (row.isGet == '0') {
                            return e;
                        }
                    }
                }
            }
        ]],
        rownumbers: true,
        toolbar: '#areaToolbar'
    });

    var p = $('#detid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            //alert('before refresh');
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            $('#detid').datagrid('reload',
                {
                    enpId: row.id,
                    belongsId:row.belongingsId,
                    trefresh: new Date().getTime()
                }
            );
            return false;
        }
    });

    if(lockId !=null && lockId!=''){
        jQuery.ajax({
            type : 'POST',
            url : 'getSerialIdByLockId.do',
            data : {lockId:lockId},
            dataType : 'json',
            success : function(data){
                console.log(data)
                pId=data;
                // $('#detid').datagrid('load', {
                //     enpId : data,
                //     trefresh:new Date().getTime()
                // });
            },
            error : function(data){
            }
        });
    }

    $('#serialId').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getPersonBelong.do?areaId='+ssareaid,
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'name', title: '姓名', width: 100},
            {field: 'certificateNo', title: '证件号码', width: 260}
            // {field:'policeName1',title:'主办民警',width:70},
            // {field:'policeName2',title:'协办民警',width:70},
            // {field:'serialNo',title:'入区编号',width:135},
            // {field:'areaId',title:'办案区域id',hidden:true},
            // {field:'lawCaseId',title:'案件id',hidden:true},
            // {field:'personId',title:'人员id',hidden:true}
        ]],
        onLoadSuccess :function(param){
            if(lockId !=null && lockId!=''){
                jQuery.ajax({
                    type : 'POST',
                    url : 'getSerialIdByLockId.do',
                    data : {lockId:lockId},
                    dataType : 'json',
                    success : function(data){
                        console.log(data)
                        pId=data;
                        $('#serialId').combogrid('setValue', data);
                        // $('#detid').datagrid('load', {
                        //     enpId : data,
                        //     trefresh:new Date().getTime()
                        // });
                    },
                    error : function(data){
                    }
                });
            }

        },
        onChange: function (data, date1) {
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if(row!=null){
                $('#detid').datagrid('load', {
                    enpId: row.id,
                    belongsId:row.belongingsId,
                    trefresh: new Date().getTime()
                });
            }else{
                $('#detid').datagrid('load', {
                    enpId: data,
                    trefresh: new Date().getTime()
                });
            }
        }
    });

    $.messager.progress({
        title: '请等待',
        msg: '正在加载控件...'
    });
    $.messager.progress('close');
    $('#jsdwId').combobox({
        url: contextPath + '/zhfz/bacs/combobox/listAllOrganizationCode.do',
        valueField: 'id',
        textField: 'value',
        onChange: function (value) {
        }
    });
    $('#getWay').combobox({
        onChange: function (value) {
            if (value == 1) {
                var rows = $('#detid').datagrid('getRows');
                if (rows != null && rows.length > 0) {
                    $('#getPerson').prop('value', rows[0].personName);
                }
            }
            if (value == 4) {
                $('#photoShow').show()
            } else {
                $('#photoShow').hide()
            }
            if (value == 5) {
                $('#jsdwShow').show()
            } else {
                $('#jsdwShow').hide()
            }
        }
    });
    $('#fudong').remove();
});

function areadoClear() {
    $('#serialId').combogrid('setValue', '请选择唯一编号');
}

//---------------------------------------------------------------------------------------------
//点击开柜填写开柜单    全部提取
function boxopen(index) {

    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');

    if (row != null && row != '') {

        //查看是否有未提取的记录
        var rows = $('#detid').datagrid('getRows');
        if (rows == null || rows.length == 0) {
            $.messager.alert('提示', '没有存柜记录!');
            return false;
        }
        var flag = false;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].isGet == '0') {
                flag = true;
                break;
            }
        }
        if (!flag) {
            $.messager.alert('提示', '物品已全部领取!');
            return false;
        }
        showDialog('#openbill_dialog', '填写开柜单');
        $('#openbill_form').form('clear');
        $('#jsdwId').combobox('setValues', '');
        $('#getWay').combobox('setValues', '');

        url = 'editBoxopenout.do?id=' + row.id;

    } else {
//		 $.messager.progress('close');
        $.messager.alert('提示', '请选择唯一编号!');
    }
}

//点击开柜填写开柜单(单个领取)
function boxopens(index) {
    var rowData = $('#detid').datagrid('getRows')[index];

    showDialog('#openbill_dialog', '填写开柜单');
    $('#openbill_form').form('clear');
    $('#getWay').combobox('setValues', '');
    $('#jsdwId').combobox('setValues', '');
    $('#belongingsId').prop('value', rowData.belongingsId);
    $('#lockerId').prop('value', rowData.lockerId);
    url = 'editBoxopenouts.do?id=' + rowData.id;
}

//查看图片
function showImages() {
    $("#showPic_dialog").html("");
    var rowData = $('#detid').datagrid('getRows');
    //alert("rowData"+rowData);
    //alert("rowData.rows.length"+rowData.length);
    if (!rowData || rowData.length == 0) {
        $.messager.alert('提示', '请扫描手环或者选择专属编号！');
        return;
    }
    var belongingsId = rowData[0].belongingsId;
    //alert("belongingsId="+belongingsId);
    $.messager.progress({
        title: '请等待',
        msg: '加载数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: 'getImages.do',
        data: {belongingsId: belongingsId},
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            for (var i = 0; i < data.length; i++) {
                var url = '../imageshow.do?path=' + encodeURI(data[i]);
                $("#showPic_dialog").append('<img width="450" src="' + url + '" /><br/><br/>');
            }
            showDialog('#showPic_dialog', '照片');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('Error', '未知错误!');
        }
    });
}

//打开拍照
function showphotowid() {
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (row != null) {
        var height = 500;
        var width = 900;
        var screenParam = "left=0,top=0,scrollbars,resizable=yes,toolbar=no',height=" + height + ",width=" + width;
        var winOpen = window.open("about:blank", "", screenParam);
        // winOpen.location = "../common/photo.jsp?type=1&serialid=" + row.id + "&serialNo=" + row.belongingsId;//type=1 随身拍照
        if(areaId>49 ||areaId==1 ||areaId==45){
            winOpen.location = "../common/photo.jsp?type=1&serialid=" + row.id + "&serialNo=" + row.belongingsId;//type=1 随身拍照
        }else{
            winOpen.location = "../common/demo/photo.jsp?type=1&serialid="+row.id+"&serialNo="+row.belongingsId;//type=1 随身拍照
        }

    }
}


// 开柜单保存
function saveOpeninfo() {
    if (!checkForm('#openbill_form')) {
        return;
    }

    var entForm = $('#openbill_form');
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
    $.messager.progress({
        title: '请等待',
        msg: '数据处理中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row && row.id) {
                $('#detid').datagrid('reload', {enpId: row.id, belongsId:row.belongingsId, trefresh: new Date().getTime()});
            } else {
                $('#detid').datagrid('reload', {trefresh: new Date().getTime()});
            }
            $('#openbill_dialog').dialog('close');

        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '保存失败!' + data.content);
        }
    });
}

//台账
function securityPrint(index) {
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
        return;
    }
    var enterpriseinfo = {"id": null, "serialId": row.id};
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"] = 9;
    lawdocInfo["name"] = "随身物品登记";
    lawdocInfo["type"] = 1;
    lawdocInfo["userId"] = 0;
    lawdocInfo["dataId"] = row.id;
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    $('#number').val(9);
    $('#name').val('随身物品登记');
    $('#type').val(1);
    $('#userId').val(0);
    $('#dataId').val(row.id);
    $('#serialNo').val(row.serialNo);
    $('#serialId').val(row.id);
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    // document.getElementById("lawdocInfo_belonginfo").submit();// ********
    fileUtils.read("/zhfz/lawdocProcess/download.do?number=9&name=随身物品登记&type=1&userId=0&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id);
    $.messager.progress('close');
}

function securityDownLoad(index) {
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
        return;
    }
    var enterpriseinfo = {"id": null, "serialId": row.id};
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"] = 9;
    lawdocInfo["name"] = "随身物品登记";
    lawdocInfo["type"] = 1;
    lawdocInfo["userId"] = row.belongingsId;
    lawdocInfo["dataId"] = row.id;
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    $('#number').val(9);
    $('#name').val('随身物品登记');
    $('#type').val(1);
    $('#userId').val(row.id);
    $('#dataId').val(row.belongingsId);
    $('#serialNo').val(row.serialNo);
    $('#serialId').val(row.id);
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    document.getElementById("lawdocInfo_belonginfo").submit();// ********
    //fileUtils.read("/zhfz/lawdocProcess/download.do?number=9&name=随身物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
    $.messager.progress('close');
}

function doSearch() {
    var id = $('#serialId').combogrid('getValue');
    if (id == null || id == '' || id == '请选择唯一编号') {
        $.messager.alert('', '请扫描手环或选择嫌疑人!');
        return false;
    }
    $('#detid').datagrid('reload', {enpId: id, trefresh: new Date().getTime()});
}

//---------------------------------------------------------------------------------------------
//取物开柜
function boxoutopen() {
    var cuffNumber = $('#cuffNumber').val();
    // if(cuffNumber==''||cuffNumber==null)
    // {
    //     $.messager.alert('提示', '请扫描嫌疑人手环或者民警卡片!');
    //     return false;
    // }
    var id = $('#serialId').combogrid('getValue');
    if (id == null || id == '' || id == '请选择唯一编号') {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
        return false;
    }
    open2();
}

//取物开柜
function open2() {
    var ids = $("#id1").val();
    if (!ids) {
        var a = $('#detid').datagrid('getRows')[0];
        if (a) {
            ids = a.lockerId;
        }
    }
    jQuery.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: 'boxoutopen.do?lockid=' + ids,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.alert('通知', data.content);
        },
        error: function (data) {
            $.messager.alert('错误', '开柜失败（boxoutopen）!');
        }
    });
}

//手环扫描
function readRingNo(number) {
    //选择唯一id
    $('#serialId').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../combogrid/getSuspectSerialNo.do?type=belongout',
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'serialNo', title: '入区编号', width: 135},
            {field: 'name', title: '姓名', width: 60},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {field: 'interrogateAreaId', title: '办案区域id', hidden: true},
            {field: 'lawCaseId', title: '案件id', hidden: true},
            {field: 'personId', title: '案件id', hidden: true}
        ]]
    });
    if (number) {
        var cuff = $('#cuffInfo').serializeObject();
        cuff["cuffNo"] = number;
        var jsonrtinfo = JSON.stringify(cuff);
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: '../serial/getSerialByCuffNo.do',
            data: jsonrtinfo,
            dataType: 'json',
            success: function (data) {
                var id = data.callbackData ? data.callbackData.id : null;
                if (id != null && id != "") {
                    $('#cuffNumber').textbox("setValue", data.callbackData.cuffNo);
                    $('#serialId').combogrid('setValue', id);
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
        alert("读卡失败！");
        $('#cuffNumber').val('');
    }
}

//PDF打印
function signPrint(index) {
    var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (row != null) {
        printChoose(row.id, row.uuid, row.areaId, 8);
    } else {
        $.messager.alert("提示", "请扫描手环或选择嫌疑人");
    }
}

//签字
function securitySign() {
    var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (row != null) {
        var url = "../../lawdocProcess/downloadBase64.do?number=9&name=随身物品登记&type=1&userId=0&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id;
        startSign(url, row.id);
    } else {
        $.messager.alert("提示", "请扫描手环或选择嫌疑人");
    }
}

//pdf下载
function PdfDownLoad() {
    var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (row != null) {
        downLoadPdf(row.id, row.uuid, row.areaId, 8);
    } else {
        $.messager.alert("提示", "请扫描手环或选择嫌疑人");
    }
}

function getWaySelect() {
    alert(111)
    console.log(111)
}