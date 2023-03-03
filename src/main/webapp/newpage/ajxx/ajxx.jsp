<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>案件管理</title>
    <%@ include file="../common.jsp" %>
    <%--<script type="text/javascript" src="ajxx.js"></script>--%>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="ajmc" class="easyui-textbox" data-options="label:'案件名称'" style="width:250px;"/>
        <input id="ajbh" class="easyui-textbox" data-options="label:'案件编号'" style="width:250px;"/>
        <a href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
        <a href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;">清&nbsp;除</a>

        &nbsp;&nbsp;
        <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton button-line-blue" iconCls="icon-add"
           plain="true">添加</a>
        &nbsp;&nbsp;
        <a href="javascript:void(0)" onclick="sysZfbaData()" class="easyui-linkbutton button-line-brown"
           iconCls="icon-user-config"
           plain="true">同步执法办案平台数据</a>
    </div>

    <div data-options="region:'center',border:false" style="height:100%">
        <table id="dg" style="width:100%;height:100%;">
        </table>
        <div id="tb" style="padding:2px 5px;">
            <%-- <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton" iconCls="icon-add"
                plain="true">添加</a>
             <a id="btn-edit" href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
                plain="true">编辑</a>
             <a id="btn-delete" href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
                iconCls="icon-remove"
                plain="true">删除</a>--%>
            <%--<a href="javascript:void(0)" onclick="getRoles()" class="easyui-linkbutton" iconCls="icon-user-config"
               plain="true">角色设置</a>
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true">剪切</a>--%>

        </div>
    </div>
</div>
<div id="dlg"></div>
<script>
    var datagrid;
    var dialog;
    $(function () {
        datagrid = $('#dg').datagrid({
            method: "get",
            <%--url: '${ctx}/sys/user/page',--%>
            url: '/zhfz/zhfz/bacs/belong/queryCase.do',
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
                {field: 'ajbh', title: '案件编号', width: 130},
                {
                    field: 'ajlx', title: '案件类型', width: 50,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "行政";
                        }
                        if (value == 2) {
                            return "刑事";
                        }
                        return "无";
                    }
                },
                {
                    field: 'abmc', title: '案别', width: 70, formatter: function (value, rec) {
                        if (value == null || value == '') {
                            return '无';
                        } else {
                            return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                        }
                    }
                },
                {field: 'zbmjxm', title: '主办民警', width: 40},
                {
                    field: 'zbdwmc', title: '主办单位', width: 70,
                    formatter: function (value, rec) {
                        if (value == null || value == '') {
                            return '无';
                        } else {
                            return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                        }
                    }
                },
                {
                    field: 'afsj', title: '案发时间', width: 120,
                    formatter: function (value, rec) {
                        return valueToDate(value);
                    }
                },
                {
                    field: 'afdd', title: '案发地址', width: 120,
                    formatter: function (value, rec) {
                        if (value == null || value == '') {
                            return '无';
                        } else {
                            return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                        }
                    }
                },
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
        $(datagrid).datagrid('load', {
                ajmc: $('#ajmc').textbox("getValue"),
                ajbh: $('#ajbh').textbox("getValue")
            }
        );
    }

    function clearSearch() {
        $('#ajmc').textbox('setValue', '');
        $('#ajbh').textbox('setValue', '');
    }

    function sysZfbaData() {
        dialog = $("#dlg").dialog({
            title: '同步数据',
            width: 1200,
            height: 700,
            href: '${ctx}/newpage/ajxx/zfbadata.jsp',
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

    function add() {
        dialog = $("#dlg").dialog({
            title: '添加案件',
            width: 940,
            height: 460,
            href: '${ctx}/newpage/ajxx/add.jsp',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        var address = "";
                        if ($('#province').combobox('getValue') != null && $('#province').combobox('getValue') != "") {
                            address += $('#province').combobox('getText');
                        }
                        if ($('#city').combobox('getValue') != null && $('#city').combobox('getValue') != "") {
                            var cityName = $('#city').combobox('getText');
                            if (cityName.indexOf('北京') < 0 && cityName.indexOf('天津') < 0 &&
                                cityName.indexOf('上海') < 0 && cityName.indexOf('重庆') < 0) {
                                address += cityName;
                            }
                        }
                        if ($('#district').combobox('getValue') != null && $('#district').combobox('getValue') != "") {
                            address += $('#district').combobox('getText');
                        }
                        if ($('#street').combobox('getValue') != null && $('#street').combobox('getValue') != "") {
                            address += $('#street').combobox('getText');
                        }
                        address += $('#involvedAddress').textbox("getValue");
                        var addressCode = $('#province').combobox('getValue') + "*" + $('#city').combobox('getValue') + "*" + $('#district').combobox('getValue') + "*" + $('#street').combobox('getValue');
                        var CaseForm = $('#form').serializeObject();
                        CaseForm["abmc"] = $('#ab').combobox('getText');
                        CaseForm["afdd"] = address;
                        CaseForm["afddCode"] = addressCode;
                        CaseForm["abmc"] = $("#ab").combobox("getText");
                        CaseForm["zbdwmc"] = $("#zbdwId").combobox("getText");
                        CaseForm["zbmjxm"] = $("#zbmjName").val();
                        CaseForm["ajbh"] = $("#ajbh").textbox("getValue");

                        var CaseFormJson = JSON.stringify(CaseForm);
                        $.messager.progress({
                            title: '请等待',
                            msg: '添加/修改数据中...'
                        });
                        jQuery.ajax({
                            type: 'POST',
                            url: "/zhfz/zhfz/common/case/addCase.do",
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
            var row = datagrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择修改的一行数据');
                return
            } else {
                id = row.id;
            }
        }
        dialog = $("#dlg").dialog({
            title: '修改案件信息',
            width: 940,
            height: 460,
            href: 'edit.jsp',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#formedit").form('validate');
                    if (isValid) {
                        var address = $('#afdd').textbox("getValue");
                        var CaseForm = $('#formedit').serializeObject();
                        CaseForm["id"] = id;
                        CaseForm["abmc"] = $('#ab').combobox('getText');
                        CaseForm["afdd"] = address;
                        CaseForm["zbdwmc"] = $("#zbdwId").combobox("getText");
                        CaseForm["zbmjxm"] = $("#zbmjName").val();
                        CaseForm["ajbh"] = $("#editajbh").textbox("getValue");
                        var CaseFormJson = JSON.stringify(CaseForm);
                        $.messager.progress({
                            title: '请等待',
                            msg: '添加/修改数据中...'
                        });
                        jQuery.ajax({
                            type: 'POST',
                            url: "/zhfz/zhfz/common/case/editCase.do",
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
            var row = datagrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择删除的一行数据');
                return
            } else {
                id = row.id;
            }
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                jQuery.ajax({
                    type: 'POST',
                    url: '/zhfz/zhfz/common/case/removeCase.do',
                    data: {
                        "caseId": id,
                    },
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

</script>
</body>
</html>