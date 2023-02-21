var datagridDit
var dialog;
var ssareaid;
$(function () {
    ssareaid = $('#ssareaid').val();
    loadPerson();
    $("#outshow").show();
    datagridDit = $('#detid').datagrid({
        method: "get",
        // url:  '/zhfz/zhfz/bacs/belong/queryCase.do',
        url: '/zhfz/zhfz/bacs/belong/listAllBelongdetByLockerId.do',
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
        frozenColumns: [[
            {title: 'id', field: 'id', width: 80, sortable: true, hidden: true},
            {field: 'belongingsId', title: '物品暂存ID', hidden: true},
            {field: 'serialId', title: 'serialId', hidden: true}
        ]],
        columns: [[
            {field: 'personName', title: '嫌疑人姓名', width: 40},
            {field: 'lockerIds', title: '储物柜编号', width: 140, hidden: true},
            {
                field: 'lockerId', title: '储物柜编号', width: 150,
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
                        return '<font color="red">未领取</font>'
                    }
                }
            },
            {
                field: 'operate', title: '操作', width: 60,
                formatter: function (value, row, index) {
                    var e = "<a onclick='boxopens(" + index + ")' class='button-edit button-blue'>领取</a>";
                    return e;
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
        queryParams: {enpId: -99, trefresh: new Date().getTime()},
    });

    $('#jsdwId').combobox({
        url: '/zhfz/zhfz/bacs/combobox/listAllOrganizationCode.do',
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
                    $('#getPerson').textbox('setValue', rows[0].personName);
                }
            }else{
                $('#getPerson').textbox('setValue', "");
                $('#jsdwId').combobox('setValues', '');
            }
            if (value == 5) {
                $('#jsdwShow').show()
            } else {
                $('#jsdwShow').hide()
            }
        }
    });
});

function loadPerson() {
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '/zhfz/zhfz/common/combogrid/getPersonBelong.do?areaId=' + ssareaid,
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
                    enpId: row.id,
                    belongsId: row.belongingsId,
                    trefresh: new Date().getTime()
                });
            } else {
                $('#detid').datagrid('load', {
                    enpId: data,
                    trefresh: new Date().getTime()
                });
            }

            /*if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#personName').val(row.name);
                $('#serialId').val(row.id);
                $('#caseId').val(row.caseId);
                $('#areaId').val(ssareaid);
            }*/
        }
    });
}


//刷新
//嫌疑人下拉框
function shuaxin() {
    //嫌疑人下拉框,查询所有已经存物的嫌疑人
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '/zhfz/zhfz/common/combogrid/getPersonBelong.do?areaId=' + ssareaid,
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
            }
        }
    });
    $('#detid').datagrid('load', {
        enpId: 0, trefresh: new Date().getTime()
    });
}
//单个领取
function boxopens(index){
    var rowData = $('#detid').datagrid('getRows')[index];
    $('#openbill_form').form('clear');
    $('#getWay').combobox('setValues', '');
    $('#jsdwId').combobox('setValues', '');
    $('#belongingsId').val(rowData.belongingsId);
    $('#lockerId').val(rowData.lockerId);
    url = '/zhfz/zhfz/bacs/belong/editBoxopenouts.do?id=' + rowData.id;
    dialog = $("#lqwpxx").dialog({
        title: '领取物品',
        width: 500,
        height: 350,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#openbill_form").form('validate');
                if (isValid) {
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
                            U.msg(data.content);
                            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
                            var row = cg.datagrid('getSelected');
                            if (row && row.id) {
                                $('#detid').datagrid('reload', {enpId: row.id, belongsId:row.belongingsId, trefresh: new Date().getTime()});
                            } else {
                                $('#detid').datagrid('reload', {trefresh: new Date().getTime()});
                            }
                            dialog.dialog('close');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            U.msg('保存失败!' + data.content);
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
//  全部提取
function boxopen() {
    var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (row != null && row != '') {
        //查看是否有未提取的记录
        var rows = $('#detid').datagrid('getRows');
        if (rows == null || rows.length == 0) {
            U.msg("没有存柜记录");
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
            U.msg("物品已全部领取");
            return false;
        }
        $('#jsdwId').combobox('setValues', '');
        $('#getWay').combobox('setValues', '');
        url = '/zhfz/zhfz/bacs/belong/editBoxopenout.do?id=' + row.id;
        dialog = $("#lqwpxx").dialog({
            title: '领取物品',
            width: 500,
            height: 350,
            // href: 'addWpxx.jsp',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#openbill_form").form('validate');
                    if (isValid) {
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
                                U.msg(data.content);
                                if (row && row.id) {
                                    $('#detid').datagrid('reload', {enpId: row.id, belongsId:row.belongingsId, trefresh: new Date().getTime()});
                                } else {
                                    $('#detid').datagrid('reload', {trefresh: new Date().getTime()});
                                }
                                dialog.dialog('close');
                            },
                            error: function (data) {
                                $.messager.progress('close');
                                U.msg('保存失败!' + data.content);
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

    } else {
        U.msg("请选择嫌疑人");
    }
}

//台账下载
function securityDownLoad() {
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
        url: "/zhfz/zhfz/bacs/belong/getImages.do",
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
                        url: "/zhfz/zhfz/bacs/belong/editBelongdet.do",
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
                url: '/zhfz/zhfz/bacs/belong/removeBelongdet.do?belongingsId=' + belongingsId + "&detailId=" + detailId,
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


