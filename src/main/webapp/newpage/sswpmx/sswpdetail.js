var tempId = null;
var flag = 0;
var jzAjlyCode = 0;
var caseAjlyCode = 1;
var orderRequestDataId = "";
var personUrl = "";
var sfaj = "";

var datagridPerson;
var dialog;
$(function () {
    loadSexSearch();
    datagridPerson = $('#dg').datagrid({
        method: "get",
        url: '/zhfz/zhfz/common/user/getUsersInfo.do',
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
            {field: 'loginName', title: '登录名', width: 150},
            {field: 'realName', title: '真实姓名', width: 150},
            {field: 'jobTitle', title: '职务', width: 150},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {field: 'organizationName', title: '单位名称', width: 150},
            {field: 'mobile', title: '手机号码', width: 150},
            {
                field: 'operate', title: '操作', width: 120,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
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

function queryUsers() {
    $(datagridPerson).datagrid('load', {
            real_name_t: $('#s_real_name').textbox('getValue'),
            certificate_no_t: $('#s_certificate_no').textbox('getValue'),
        }
    );
}

function clearSearch() {
    $('#s_certificate_no').textbox('setValue', '');
    $('#s_real_name').textbox('setValue', '');
}

function loadSexSearch() {
    codeCombo('gender', 'XBID', '');
}

function codeCombo(id, type, selectedId) {
    $('#' + id).combobox({
        url: "/zhfz/zhfz/common/code/listCodeByType.do?type=" + type,
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
                        url: "/zhfz/zhfz/bacs/person/insertPerson.do",
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
    if (id == null) {
        var row = datagridPerson.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择修改的一行数据');
            return
        } else {
            id = row.id;
        }
    }

    dialog = $("#dlg").dialog({
        title: '修改人员',
        width: 540,
        height: 460,
        href: 'personedit.jsp',
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
                    var personsJson = JSON.stringify(personList);
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: "/zhfz/zhfz/bacs/person/updatePerson.do",
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
                url: '/zhfz/zhfz/common/user/delete.do',
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


