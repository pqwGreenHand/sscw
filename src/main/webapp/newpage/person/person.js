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
        // url:  '/zhfz/zhfz/bacs/belong/queryCase.do',
        url: '/zhfz/zhfz/bacs/belong/queryPerson.do',
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
                field: 'ajmc', title: '案件名称', width: 150, formatter: function (value, rec) {
                    if (value == null || value == '') {
                        return '无';
                    } else {
                        return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                    }
                }
            },
            {
                field: 'ajbh', title: '案件编号', width: 120, formatter: function (value, rec) {
                    if (value == null || value == '') {
                        return '无';
                    } else {
                        return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                    }
                }
            },
            {
                field: 'areaName', title: '所属单位', width: 140
            },
            {field: 'name', title: '嫌疑人姓名', width: 70},
            {
                field: 'sex', title: '性别', width: 40,
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
            {field: 'certificateTypeName', title: '证件类型', width: 70},
            {field: 'certificateNo', title: '证件号码', width: 120},
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
            ajmc: $('#ajmc').textbox("getValue"),
            ajbh: $('#ajbh').textbox("getValue"),
            name: $('#xm').textbox("getValue"),
            certificateNo: $('#zjhm').textbox("getValue"),
            set: $('#gender').combobox("getValue")
        }
    );
}

function clearSearch() {
    $('#ajmc').textbox('setValue', '');
    $('#ajbh').textbox('setValue', '');
    $('#xm').textbox('setValue', '');
    $('#zjhm').textbox('setValue', '');
    $("#gender").combobox("setValue", "");
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
        title: '添加嫌疑人',
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

function sysZfbaData() {
    dialog = $("#dlg").dialog({
        title: '同步数据',
        width: 1200,
        height: 700,
        href: '../../newpage/ajxx/zfbadata.jsp',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var rowAJ = datagridaj.datagrid('getSelected');
                var rowPerson = datagridperson.datagrid('getSelected');

                var CaseForm = $('#form').serializeObject();
                if(rowAJ!=null){
                    CaseForm["ajbh"] = rowAJ.AJBH;
                    CaseForm["ajmc"] = rowAJ.AJMC;
                    CaseForm["afdd"] = rowAJ.FADDXZ;
                    CaseForm["zbmjxm"] = rowAJ.ZBR_XM;
                    CaseForm["zbdwbh"] = rowAJ.ZBDW_BH;
                    CaseForm["afsj"] = rowAJ.FXSJ;
                    CaseForm["ajlx"] = rowAJ.AJLX;
                    CaseForm["ajly"] = 2;
                }

                //人员
                if(rowPerson!=null){
                    CaseForm["name"] = rowPerson.RYXM;
                    CaseForm["gj"] = rowPerson.GJ;
                    CaseForm["sex"] = rowPerson.XB;
                    CaseForm["age"] = rowPerson.AGE;
                    CaseForm["birth"] = rowPerson.CSRQ;
                    CaseForm["nation"] = rowPerson.MZ;
                    CaseForm["certificateNo"] = rowPerson.ZJHM;
                    CaseForm["censusRegister"] = rowPerson.HJDXZ;
                    CaseForm["rybh"] = rowPerson.RYBH;
                }

                var CaseFormJson = JSON.stringify(CaseForm);
                $.messager.progress({
                    title: '请等待',
                    msg: '添加/修改数据中...'
                });
                jQuery.ajax({
                    type: 'POST',
                    url: "/zhfz/zhfz/common/case/addPersonAndCase.do",
                    data: {
                        form: CaseFormJson
                    },
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
                url: '/zhfz/zhfz/bacs/person/deletePerson.do',
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


