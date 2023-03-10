var datagridPerson;
var dialog;
var tempId = null;
var status = null;
var flag = 0;
$(function () {
    $("#cxShow").show();
    $("#qcShow").show();
    datagridPerson = $('#dg').datagrid({
        method: "get",
        url: '/sscw/zhfz/bacs/belong/listBelongTemp.do',
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
        toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'xm', title: '嫌疑人', width: 60
            },
            {field: 'sfzh', title: '证件号码', width: 130},
           /* {
                field: 'ajbh', title: '案件编号', width: 130, formatter: function (value, rec) {
                    if (value == null || value == '') {
                        return '无';
                    } else {
                        return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                    }
                }
            },*/
            {
                field: 'ajmc', title: '案件名称', width: 150, formatter: function (value, rec) {
                    if (value == null || value == '') {
                        return '无';
                    } else {
                        return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                    }
                }
            },
            {field: 'yjdwName', title: '移交单位', width: 120},
            {field: 'jsdwName', title: '待接收单位', width: 120},
            {
                field: 'createdTime', title: '移交时间', width: 120,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {
                field: 'status', title: '状态', width: 60, formatter: function (value, row, index) {
                    if (value == 0 || value == null) {
                        return '<font color="red">待接收</font>';
                    } else if (value == 1) {
                        return '<font color="black">已接收</font>';
                    } else if (value == 2) {
                        return '<font color="red">已拒绝</font>';
                    }
                }
            },
            /* {
                 field: 'operate', title: '操作', width: 120,
                 formatter: function (value, row, index) {
                     var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                     var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                     return e + '  ' + d;
                 }
             }*/
        ]],
        onLoadSuccess: function (data) {
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
            tempId = row.id;
            status = row.status;
            lawdocDetailgridLoad(row);
            if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                $('#btn-delete').hide();
            } else {
//                    $('#btn-edit').show();
                $('#btn-delete').show();
            }
        },
        queryParams: {}
    });

    $('#lawdocDetailgrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'east',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '数据加载中...',
        method: 'get',
        remoteSort: false,
        queryParams: {tempId: -99},
        url: '/sscw/zhfz/bacs/belong/listBelongTempDetail.do',
        singleSelect: true,
        columns: [[
            {field: 'name', title: '物品名称', width: 100},
            {field: 'detailCount', title: '数量', width: 50},
            {field: 'unit', title: '单位', width: 50},
            {field: 'description', title: '特征', width: 100},
        ]],
        pagination: true,
        pageSize: 15,
        pageList: [15, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#lawdocDetailToolbar'
    });
    loadArea();
});

function lawdocDetailgridLoad(rowData) {
    $('#lawdocDetailgrid').datagrid('load', {
        tempId: rowData.id,
        trefresh: new Date().getTime()
    });
}

//加载办案场所
function loadArea() {
    $('#areaId').combobox({
        url:  '/sscw/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(data != null && data.length > 0){
                $('#s_areaId').combobox('setValue',data[0].id);
            }*/
        },
        onChange:function (data) {
            var lockerId_ulr = "/sscw/zhfz/bacs/belong/listBelongLockerBox.do?areaId=" + data + "&timeSign=" + getTimeSign();
            $('#lockerId').combobox({
                url: lockerId_ulr,
                valueField: 'id',
                textField: 'value'
            });
        }
    });

}
//一键接收
function yjAccept() {
    if(status==1&&status!=null){
        U.msg('物品已接收，请勿重复接收!');
        return
    }else if (status==2&&status!=null){
        U.msg('物品已拒绝接收!');
        return
    }
    if (tempId == null) {
        U.msg('请先选择待接收的物品!');
        return
    }else{
        var rowData = $('#dg').datagrid('getSelected');
        var jsdwId=rowData.jsdwId;
        $.get("/sscw/common/getSessionInfo.do", function(data){
            var sessionObj = eval('('+data+')');
            if(sessionObj.currentOrg.orgCode!=jsdwId){
                U.msg('请选择用正确的接收单位登录，进行物品接收!');
                return
            }else{
                dialog = $("#gm_dialog").dialog({
                    title: '选择柜门信息',
                    width: 440,
                    height: 360,
                    maximizable: false,
                    modal: true,
                    buttons: [{
                        text: '确认',
                        iconCls: 'icon-ok',
                        handler: function () {
                            var isValid = $("#gm_form").form('validate');
                            if (isValid) {
                                $.messager.progress({
                                    title: '请等待',
                                    msg: '添加/修改数据中...'
                                });
                                var entForm = $('#gm_form');
                                var lawdocInfo = entForm.serializeObject();
                                lawdocInfo["id"]=tempId;
                                var enterpriseinfo = JSON.stringify(lawdocInfo);
                                jQuery.ajax({
                                    type: 'POST',
                                    contentType: 'application/json',
                                    url: '/sscw/zhfz/bacs/belong/saveYjBelong.do',
                                    dataType: 'json',
                                    data: enterpriseinfo,
                                    success: function (data) {
                                        self.parent.ztab.open("随身物品接收", "../newpage/sswp/sswpin.jsp?ssareaid="+sessionObj.currentArea.id+"&personId="+data.content);
                                        U.msg("随身物品接收成功");
                                        dialog.dialog('close');
                                        $('#dg').datagrid('reload', {trefresh: new Date().getTime()});
                                        $.messager.progress('close');
                                    },
                                    error: function (data) {
                                        $.messager.progress('close');
                                        $.messager.alert('错误', '失败（updateBelongTempStatusById）!');
                                    }
                                });
                            }
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            dialog.dialog('close');
                        }
                    }]
                });
            }
        });

    }
}


//一键拒绝
function yjRefuse() {
    if(status==1&&status!=null){
        U.msg('物品已接收，请勿重复接收!');
        return
    }else if (status==2&&status!=null){
        U.msg('物品已接收，物品已拒绝接收!');
        return
    }
    //获取登录人的信息，判断是否是接收单位的人
    if (tempId == null) {
        U.msg('请先选择待接收的物品!');
        return
    }
    var rowData = $('#dg').datagrid('getSelected');
    var jsdwId=rowData.jsdwId;
    $.get("/zhfz/common/getSessionInfo.do", function(data){
        var sessionObj = eval('('+data+')');
        if(sessionObj.currentOrg.orgCode!=jsdwId){
          U.msg('请选择用正确的接收单位登录，进行物品接收!');
            return
        }else{
            $.messager.confirm('拒绝接收确认', '是否拒绝接收此数据？', function (r) {
                if (r) {
                    $('#id').val(tempId);
                    dialog = $("#bty_dialog").dialog({
                        title: '拒绝意见',
                        width: 440,
                        height: 360,
                        maximizable: false,
                        modal: true,
                        buttons: [{
                            text: '确认',
                            iconCls: 'icon-ok',
                            handler: function () {
                                var isValid = $("#bty_form").form('validate');
                                if (isValid) {
                                    $.messager.progress({
                                        title: '请等待',
                                        msg: '添加/修改数据中...'
                                    });
                                    var entForm = $('#bty_form');
                                    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
                                    jQuery.ajax({
                                        type: 'POST',
                                        contentType: 'application/json',
                                        url: '/sscw/zhfz/bacs/belong/updateBelongTempStatusById.do',
                                        dataType: 'json',
                                        data: enterpriseinfo,
                                        success: function (data) {
                                            U.msg( data.content);
                                            dialog.dialog('close');
                                            $('#dg').datagrid('reload', {trefresh: new Date().getTime()});
                                            $.messager.progress('close');
                                        },
                                        error: function (data) {
                                            $.messager.progress('close');
                                            $.messager.alert('错误', '失败（updateBelongTempStatusById）!');
                                        }
                                    });
                                }
                            }
                        }, {
                            text: '取消',
                            iconCls: 'icon-cancel',
                            handler: function () {
                                dialog.dialog('close');
                            }
                        }]
                    });
                }
            });
        }
    });

}

//查看照片
function searchImage() {
    $("#showPic_dialog").html("");
    if (tempId == null) {
        $.messager.alert('提示', '请先选择待接收的物品!');
        return
    }
    $.messager.progress({
        title: '请等待',
        msg: '加载数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: '/sscw/zhfz/bacs/belong/getImagesTemp.do',
        data: {tempId: tempId},
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            for (var i = 0; i < data.length; i++) {
                var url=encodeURI(data[i].url);
                $("#showPic_dialog").append('<img width="350" src="' + url + '" /><br/><br/>');
            }
            dialog = $("#showPic_dialog").dialog({
                title: '移交照片',
                width: 640,
                height: 450,
                maximizable: false,
                modal: true,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog('close');
                    }
                }]
            });

        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', '未知错误!');
        }
    });
}

function queryUsers() {
    $('#dg').datagrid('load', {
            xm: $('#xm').textbox("getValue"),
            sfzh: $('#sfzh').textbox("getValue")
        }
    );
}

function clearSearch() {
    $('#xm').textbox('setValue', '');
    $('#sfzh').textbox('setValue', '');
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
                        url: "/sscw/zhfz/bacs/person/updatePerson.do",
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
                url: '/sscw/zhfz/bacs/person/deletePerson.do',
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


