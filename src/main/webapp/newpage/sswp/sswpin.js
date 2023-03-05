var datagridDit
var dialog;
var ssareaid;
var personId;
$(function () {
    ssareaid = $('#ssareaid').val();
    personId = $('#personId').val();
    loadPerson();
    $("#inshow").show();
    datagridDit = $('#detid').datagrid({
        method: "get",
        // url:  '/sscw/zhfz/bacs/belong/queryCase.do',
        url: '/sscw/zhfz/bacs/belong/listAllBelongdet2.do',
        fit: true,
        fitColumns: true,
        border: true,
        idField: 'id',
        striped: true,
        pagination: false,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 30, 50, 100],
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true,
        toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'id', hidden: true},
            {field: 'personName', title: '嫌疑人名称', width: 50},
            {
                field: 'lockerId', title: '储物柜编号', width: 140,

                formatter: function (field, rec, index) {
                    return rec.cabinetGroup + " " + rec.cabinetNo + "号柜";
                }
            },
            {field: 'name', title: '物品名称', width: 50},
            {field: 'detailCount', title: '数量', width: 20},
            {field: 'unit', title: '单位', width: 40},
            {field: 'saveMethod', title: '保管措施', width: 80},
            {field: 'description', title: '特征', width: 140},
            {
                field: 'operate', title: '操作', width: 120,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + index + ")' class='button-edit button-blue'>编辑</a>";
                    return e + '  ' + d;
                }
            }
        ]],
        onLoadSuccess: function (data) {
            $('.button-delete').linkbutton({});
            $('.button-edit').linkbutton({});

            if (data) {
                $.each(data.rows,
                    function (index, item) {
                        if (item.checked) {
                            $('#dg').datagrid('checkRow', index);
                        }
                    });
            }
        },
        onSelect: function (index, row) {
            if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                $('#btn-delete').hide();
            } else {
//                    $('#btn-edit').show();
                $('#btn-delete').show();
            }
        },
        queryParams: {
            username: $('#username').val(),
            mobile: $('#mobile').val(),
            gender: $('#gender').val()
        }
    });
});

function loadPerson() {
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '/sscw/zhfz/common/combogrid/getPersonBelong.do?areaId=' + ssareaid,
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'id', title: '姓名', width: 100, hidden: true},
            {field: 'name', title: '姓名', width: 100},
            {field: 'certificateNo', title: '证件号码', width: 260}
        ]],
        onChange: function (data, date1) {
            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');

            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#personName').val(row.name);
                $('#serialId').val(row.id);
                $('#caseId').val(row.caseId);
                $('#areaId').val(ssareaid);
            }
        },
        onLoadSuccess: function (data) {
            if (personId != null && personId != "null") {
                $('#serialIdQuery').combogrid('setValue', personId);
            }
        }
    });
}


//刷新
//嫌疑人下拉框
function shuaxin() {
    //嫌疑人下拉框,查询所有已经存物的嫌疑人
    $('#personName').val("");
    $('#serialId').val("");
    $('#caseId').val("");
    $('#areaId').val("");
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '/sscw/zhfz/common/combogrid/getPersonBelong.do?areaId=' + ssareaid,
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'id', title: '姓名', width: 100, hidden: true},
            {field: 'name', title: '姓名', width: 100},
            {field: 'certificateNo', title: '证件号码', width: 260}
        ]],
        onChange: function (data, date1) {
            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#personName').val(row.name);
                $('#serialId').val(row.id);
                $('#caseId').val(row.caseId);
                $('#areaId').val(ssareaid);
            }
        }
    });
    $('#detid').datagrid('load', {
        enpId: 0, trefresh: new Date().getTime()
    });
}


function add() {
    dialog = $("#addwpxx").dialog({
        title: '添加物品',
        width: 1000,
        height: 650,
        href: 'addWpxx.jsp',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                dialog.dialog('close');

            }
        }, {
            text: '取消', iconCls: 'icon-cancel',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });
}

function showphotowid() {
    var cg = $('#interrogateSerialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    //上传图片开始
    var params = {
        "ServiceUrl": "http://14.196.97.86:8083/zhfz-common-service/FileService/ba",
        "Uuid": "",//uuid为空是调用回调函数，不为空时调用文件上传服务
        "Type": "CW",
        "UserId": 0,
        "FunctionId": row.belongingsId,
        "CallbackUrl": "http://127.0.0.1:8081/zhfz/restful/insertPhotoWS/insertPhoto",
        "SaRecordId": row.belongingsId
    };

    var param = JSON.stringify(params);//需要变成字符串格式
    jQuery.ajax({
        type: "post",
        dataType: "json",
        // headers:{'Content-Type':'application/x-www-form-urlencoded'},
        url: "http://127.0.0.1:22000/CameraCapture",
        contentType: "application/json;charset=UTF-8",//指定消息请求类型
        data: param,
        success: function (result) {
            console.dir(result);//在控制台打印结果
            $.messager.progress('close');
            window.close();
        }
    });
}

//台账下载
function securityDownLoad(index) {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    var belongId = null;
    var personId = null;
    var rowdata = $('#detid').datagrid('getRows')[0];
    if (row == null && rowdata == null) {
        $.messager.alert('提示', '请选择嫌疑人!');
        return;
    } else {
        if (row != null) {
            belongId = row.belongingsId
            personId = row.id
        } else {
            belongId = rowdata.belongingsId
            personId = rowdata.serialId
        }
    }
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"] = 9;
    lawdocInfo["name"] = "随身物品登记";
    lawdocInfo["type"] = 1;
    lawdocInfo["userId"] = personId;
    lawdocInfo["dataId"] = belongId;
    $('#number').val(9);
    $('#name').val('随身物品登记');
    $('#type').val(1);
    $('#userId').val(personId);
    $('#dataId').val(belongId);
    document.getElementById("lawdocInfo_belonginfo").submit();// ********
    $.messager.progress('close');
}

//查看图片
function showImages() {
    $("#wpImage").html("");
    var rowData = $('#detid').datagrid('getRows');
    if (!rowData || rowData.length == 0) {
        $.messager.alert('提示', '请选择嫌疑人！');
        return;
    }
    var belongingsId = rowData[0].belongingsId;
    $.messager.progress({
        title: '请等待',
        msg: '加载数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: "/sscw/zhfz/bacs/belong/getImages.do",
        data: {belongingsId: belongingsId},
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            for (var i = 0; i < data.length; i++) {
                var url = encodeURI(data[i]);
                $("#wpImage").append('<img width="350" src="' + url + '" /><br/><br/>');
            }
            // showDialog('#showPic_dialog', '照片');
            dialog = $("#wpImage").dialog({
                title: '照片',
                width: 640,
                height: 450,
                maximizable: false,
                modal: true,
                buttons: [{
                    text: '关闭',
                    handler: function () {
                        dialog.dialog('close');
                    }
                }]
            });


        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            U.msg(data.content);
        }
    });
}

function printcod(){
    var enterpriseinfo = {"serialId": 1, belongingsId: 2};
    var json_data = JSON.stringify(enterpriseinfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '/sscw/zhfz/bacs/belong/addBelongcodNew.do',
        data: json_data,
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    an = data[i].aname;
                    wName = data[i].wname;
                    pn = data[i].pname;
                    co = data[i].wpUuid;
                    count = data[i].detailCount
                    // alert("wName= "+wName+"co= "+co+count);
                    OpenPrinter();
                    // PSKPrn.PTKDrawTextEx(200, 10, 0, 97, 1, 1, 78, "物品名称:" + wName, false);
                    // PSKPrn.PTKDrawTextEx(200, 50, 0, 97, 1, 1, 78, "数量：" + count, false);
                    // PSKPrn.PTKDrawBar2DQR(200, 80, 180, 180, 0, 7, 2, 0, 0, co);// QR码
                    PSKPrn.PTKDrawTextEx(150, 10, 0, 97, 1, 1, 78, "物品名称:" + wName, false);
                    PSKPrn.PTKDrawTextEx(150, 50, 0, 97, 1, 1, 78, "数量：" + count, false);
                    PSKPrn.PTKDrawBarcodeEx(90, 150, 0, "1A", 2, 5, 60, 66, "\"" + co + "\"C0", true);
                    PSKPrn.PTKPrintLabel(1, 1);
                    ClosePrinter();
                }

                $.messager.progress('close');
            }
        },
        error: function (data) {
            $.messager.alert('错误', '打印条码错误!');
        }
    });
}

function edit(index) {
    var rows = $('#detid').datagrid('getRows')[index];
    $('#form').form('load', rows);
    dialog = $("#dlgEdit").dialog({
        title: '修改物品',
        width: 440,
        height: 360,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    $.messager.progress({
                        title: '请等待',
                        msg: '添加/修改数据中...'
                    });
                    var edtList = $("#form").serializeObject();
                    edtList['id'] = rows.id;
                    var edtJson = JSON.stringify(edtList);
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: "/sscw/zhfz/bacs/belong/editBelongdet.do",
                        data: edtJson,
                        dataType: 'json',
                        success: function (data) {
                            U.msg(data.content);
                            dialog.dialog('close');
                            $.messager.progress('close');
                            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
                            var row = cg.datagrid('getSelected');
                            $('#detid').datagrid('reload', {
                                enpId: row.id,
                                areaId: ssareaid,
                                trefresh: new Date().getTime()
                            });
                            $('#det_dialog').dialog('close');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            U.msg(data.content);
                        }
                    });
                }
            }
        }, {
            text: '取消', iconCls: 'icon-cancel',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });

}

function remove(id) {
    if (id == null) {
        var row = datagridDit.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择删除的一行数据');
            return
        } else {
            id = row.id;
        }
    }
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            var rowData = datagridDit.datagrid('getSelected');
            var belongingsId = rowData.belongingsId;
            var detailId = rowData.id;
            alert(detailId)
            alert(belongingsId)
            $.messager.progress({
                title: '请等待',
                msg: '删除中...'
            });
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: '/sscw/zhfz/bacs/belong/removeBelongdet.do?belongingsId=' + belongingsId + "&detailId=" + detailId,
                // data: {belongingsId: belongingsId, detailId: detailId},
                dataType: 'json',
                success: function (data) {
                    U.msg(data.content);
                    $.messager.progress('close');
                    var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
                    var row = cg.datagrid('getSelected');
                    $('#detid').datagrid('reload', {
                        enpId: row.id,
                        areaId: ssareaid,
                        trefresh: new Date().getTime()
                    });
                },
                error: function (data) {
                    U.msg(data.content);
                    $.messager.progress('close');
                }
            });
        }
    });
}


