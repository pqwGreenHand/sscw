var datagridPerson;
var dialog;
$(function () {
    loadSexSearch();
    datagridPerson = $('#dg').datagrid({
        method: "get",
        url: '/sscw/zhfz/bacs/belong/listAllBelong.do',
        fit: true,
        fitColumns: true,
        border: true,
        idField: 'id',
        striped: true,
        pagination: true,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 15,
        pageList: [15, 20, 30, 50, 100],
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true,
        // toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'personname', title: '嫌疑人', width: 20,
                formatter: function (value, row, index) {
                    return "<font>" + value + "</font>";
                }
            }, {
                field: 'certificateNo', title: '身份证号', width: 50,
                formatter: function (value, row, index) {
                    return "<font>" + value + "</font>";
                }
            },
            {
                field: 'lockerId', title: '储物柜编号', width: 60,
                formatter: function (field, rec, index) {
                    return rec.cabinetGroup + " " + rec.cabinetNo + "号柜";
                }
            },
            {
                field: 'isGet', title: '是否已领取', width: 30,
                formatter: function (value, rec) {
                    if ('true' == value || "1" == value) {
                        return '<font color="green">已领取</font>';
                    } else if ("0" == value) {
                        return '未领取';
                    }
                }
            },
            {field: 'areaName', title: '办案单位', width: 50},
            {
                field: 'operate', title: '操作', width: 45,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>照片</a>";
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
            //行选择方法，进行条件触发
            detailgridLoad(row);
        }
    });

    datagridPerson = $('#dgdetail').datagrid({
        method: "get",
        url: '/sscw/zhfz/bacs/belong/listAllBelongdet.do',
        fit: true,
        fitColumns: true,
        border: true,
        idField: 'id',
        striped: true,
        pagination: true,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 15,
        pageList: [15, 20, 30, 50, 100],
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true,
        queryParams: {'enpId': '-99', 'trefresh': new Date().getTime()},
        // toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'id', hidden: true},
            {field: 'name', title: '名称', width: 70},
            {field: 'detailCount', title: '数量', width: 30,},
            {field: 'unit', title: '单位', width: 40},
            {
                field: 'isGet', title: '是否已领取', width: 50,
                formatter: function (value, rec) {
                    if ('true' == value || "1" == value) {
                        return '已领取';
                    } else if ("0" == value) {
                        return '未领取';
                    }
                }
            },
            {
                field: 'getWay', title: '领取方式', width: 60,
                formatter: function (value, rec) {
                    if ('true' == value || "1" == value) {
                        return '本人领取';
                    } else if ("0" == value) {
                        return '未领取';
                    } else if ("2" == value) {
                        return '委托他人代为领取';
                    } else if ("3" == value) {
                        return '本人收到扣押物品清单';
                    } else if ("4" == value) {
                        return '转涉案财物';
                    } else if ("5" == value) {
                        return '移交';
                    }
                }
            },
            {field: 'getPerson', title: '领取人', width: 50},
            {
                field: 'getTime', title: '领取时间', width: 80,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {field: 'saveMethod', title: '保管措施', width: 50},
            {field: 'description', title: '特征', width: 100},
            {
                field: 'operate', title: '操作', width: 120,
                formatter: function (value, row, index) {
                    var e = "<a onclick='queryGjWpxx(" + row.id+ ")' class='button-edit button-blue'>轨迹时间轴</a>";
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
        }

    });
});

function  queryGjWpxx(id){
    dialog = $("#dlg").dialog({
        title: '轨迹时间轴',
        width: 1100,
        height: 350,
        href: 'wpsjz.jsp?id='+id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '关闭', iconCls: 'icon-cancel',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });
}

//选中触发刷新
function detailgridLoad(rowData)
{
    $('#dgdetail').datagrid('load',{enpId:rowData.id,trefresh:new Date().getTime()});
}

function queryUsers() {
    $(datagridPerson).datagrid('load', {
            name: $('#name').textbox('getValue'),
            certificateNo: $('#certificate_no').textbox('getValue'),
        }
    );
}

function clearSearch() {
    $('#name').textbox('setValue', '');
    $('#certificate_no').textbox('setValue', '');
}

function loadSexSearch() {
    codeCombo('gender', 'XBID', '');
}

function codeCombo(id, type, selectedId) {
    $('#' + id).combobox({
        url: "/sscw/zhfz/common/code/listCodeByType.do?type=" + type,
        valueField: 'codeKey',
        textField: 'codeValue',
        editable: false,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row.codeValue.indexOf(q) > -1;//将从头位置匹配改为任意匹配
        },
        onLoadSuccess: function () {
            if (selectedId) {
                //延迟一下，对于某些浏览器能set成功
                setTimeout(function () {
                    $('#' + id).combobox('setValue', selectedId);
                }, 10);
            }

        }
    });
}

function add() {
    dialog = $("#dlg").dialog({
        title: '添加民警信息',
        width: 600,
        height: 550,
        href: 'personadd.jsp',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    if ($('#person_certificate_type').combobox('getValue') == 111) {
                        if (!U.validatorZjhm($("#person_certificate_no").textbox('getValue'))) {
                            U.msg('请输入正确的身份证号');
                            return;
                        }
                    }
                    $.messager.progress({
                        title: '请等待',
                        msg: '添加/修改数据中...'
                    });
                    var personList = $("#form").serializeObject();
                    personList["caseId"] = $("#caseId").val();
                    var personsJson = JSON.stringify(personList);
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: "/sscw/zhfz/bacs/person/insertPerson.do",
                        data: personsJson,
                        dataType: 'json',
                        success: function (data) {
                            U.msg(data.content);
                            dialog.dialog('close');
                            $.messager.progress('close');
                            queryUsers();

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

function edit(id) {
    $("#wpImage").html("");
    var belongingsId = id;
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

function remove(id) {
    if (id == null) {
        var row = datagridPerson.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择删除的一行数据');
            return
        } else {
            id = row.id;
        }
    }
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            var rowData = datagridPerson.datagrid('getSelected');
            var jsonrtinfo = JSON.stringify(rowData);
            $.messager.progress({
                title: '请等待',
                msg: '删除中...'
            });
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: '/sscw/zhfz/common/user/delete.do',
                data: jsonrtinfo,
                dataType: 'json',
                success: function (data) {
                    U.msg(data.content);
                    $.messager.progress('close');
                    queryUsers();
                },
                error: function (data) {
                    U.msg(data.content);
                    $.messager.progress('close');
                }
            });
        }
    });
}


