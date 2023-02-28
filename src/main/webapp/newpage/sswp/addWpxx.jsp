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
    <title>添加物品</title>
    <%@ include file="../common.jsp" %>
    <%--<script type="text/javascript" src="ajxx.js"></script>--%>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="height:50%;padding: 10px 5px;">
        <form id="formwpxx">
            <input type="hidden" id="id" name="id"/>
            <input type="hidden" id="caseId" name="caseId">
            <input type="hidden" id="serialId" name="serialId">
            <input type="hidden" id="areaId" name="areaId">
            <table style="margin: 0 auto; padding: 10px;width: 100%">
                <tr>
                    <td>选择嫌疑人:</td>
                    <td><a href="javascript:void(0)" onclick="selectPerson()" class="easyui-menubutton  button-line-blue"
                           data-options="iconCls:'icon-search'">高级查询</a>
                    </td>
                    <%--<td><a href="javascript:void(0)" class="easyui-menubutton  button-line-blue"
                           data-options="menu:'#mm',iconCls:'icon-search'">高级查询</a>
                        <div id="mm" style="width:150px;">
                            <div onclick="selectPerson()">本系统人员</div>
                            <div onclick="selectZfbaPerson()">市局平台人员</div>
                        </div>
                    </td>--%>
                    <td>嫌疑人姓名:</td>
                    <td><input id="personName" name="personName" class="easyui-textbox" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>储物柜号:</td>
                    <td><input id="lockerId" name="lockerId" class="easyui-combobox" data-options="required:true"/>
                    </td>
                    <td>物品名称:</td>
                    <td><input id="name" name="name" class="easyui-combobox"
                               data-options="url: 'json/name_data.json',valueField: 'id',textField: 'text',required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>数量:</td>
                    <td><input id="detailCount" name="detailCount" class="easyui-textbox" data-options="required:true"/>
                    </td>
                    <td>单位:</td>
                    <td><input id="unit" name="unit" class="easyui-combobox"
                               data-options="url: 'json/unit.json',valueField: 'id',textField: 'text',required:true"/>
                    </td>
                </tr>

                <tr>
                    <td>特征:</td>
                    <td><input id="description" name="description" class="easyui-textbox" data-options="required:true"/>
                    </td>
                    <td>暂存方式:</td>
                    <td><input id="saveMethod" name="saveMethod" class="easyui-combobox"
                               data-options="url: 'json/saveMethod.json',valueField: 'id',textField: 'text',required:true"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: center;padding-top: 40px">
                        <a href="javascript:void(0)" onclick="addWpxx()" style="width: 200px;;"
                           data-options="iconCls:'icon-add'" class="easyui-linkbutton button-line-blue"
                           style="width: 70px;margin-left: 10px;">添加物品</a>
                    </td>
                </tr>

            </table>
        </form>
    </div>

    <div data-options="region:'center',border:false" style="height:50%">
        <table id="belonggrid" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<div id="dlgxyr"></div>
<script>
    var dialogXyr;
    $(function () {
        loadLocked();
        $('#belonggrid').datagrid({
            method: "get",
            url: '/zhfz/zhfz/bacs/belong/listAllBelongdet2.do',
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
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'personName', title: '嫌疑人名称', width: 60},
                {
                    field: 'lockerId', title: '储物柜编号', width: 180,
                    formatter: function (field, rec, index) {
                        return "<div title='" + rec.cabinetGroup + " " + rec.cabinetNo + "号柜" + "' class='textEllipsis'>" + rec.cabinetGroup + " " + rec.cabinetNo + "号柜" + "</div>";
                    }
                },
                {field: 'name', title: '物品名称', width: 50},
                {field: 'detailCount', title: '数量', width: 20},
                {field: 'unit', title: '单位', width: 40},
                {field: 'saveMethod', title: '保管措施', width: 60},
                {field: 'description', title: '特征', width: 140},
                {
                    field: 'operate', title: '操作', width: 60,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='removebelong(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        return d;
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
                                $('#belonggrid').datagrid('checkRow', index);
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

    function loadLocked() {
        var lockerId_ulr = "/zhfz/zhfz/bacs/belong/listBelongLockerBox.do?areaId=" + $('#ssareaid').val() + "&timeSign=" + getTimeSign();
        $('#lockerId').combobox({
            url: lockerId_ulr,
            valueField: 'id',
            textField: 'value'
        });
    }

    function removebelong(id) {
        if (id == null) {
            var row = $('#belonggrid').datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择删除的一行数据');
                return
            } else {
                id = row.id;
            }
        }
        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                var rowData = $('#belonggrid').datagrid('getSelected');
                var belongingsId = rowData.belongingsId;
                var detailId = rowData.id;
                var personId = rowData.serialId;
                $.messager.progress({
                    title: '请等待',
                    msg: '删除中...'
                });
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: '/zhfz/zhfz/bacs/belong/removeBelongdet.do?belongingsId=' + belongingsId + "&detailId=" + detailId,
                    dataType: 'json',
                    success: function (data) {
                        U.msg(data.content);
                        $.messager.progress('close');
                        $('#detid').datagrid('reload', {
                            enpId: personId,
                            areaId: ssareaid,
                            trefresh: new Date().getTime()
                        });
                        $('#belonggrid').datagrid('load', {
                            enpId: personId,
                            areaId: ssareaid, trefresh: new Date().getTime()
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


    function selectPerson() {
        dialogXyr = $("#dlgxyr").dialog({
            title: '选择嫌疑人',
            width: 1200,
            height: 550,
            href: 'person.jsp',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var personInfo = datagridPerson.datagrid('getSelected');
                    if (personInfo != null) {
                        $('#personName').textbox("setValue", personInfo.name);
                        $('#serialId').val(personInfo.id);
                        $('#caseId').val(personInfo.caseId);
                        $('#areaId').val(ssareaid);
                        $('#lockerId').combobox('reload');
                        $('#detid').datagrid('load', {
                            enpId: personInfo.id, areaId: ssareaid, trefresh: new Date().getTime()
                        });
                        $('#belonggrid').datagrid('load', {
                            enpId: personInfo.id, areaId: ssareaid, trefresh: new Date().getTime()
                        });
                        U.msg("关联人员数据成功");
                        dialogXyr.dialog('close');
                    }
                    else {
                        U.msg("请先选择嫌疑人数据");
                    }
                }
            }, {
                text: '取消', iconCls: 'icon-cancel',
                handler: function () {
                    dialogXyr.dialog('close');
                }
            }]
        });
    }

    function addWpxx() {
        var isValid = $("#formwpxx").form('validate');
        if (isValid) {
            $.messager.progress({
                title: '请等待',
                msg: '添加/修改数据中...'
            });
            var wpxxList = $("#formwpxx").serializeObject();
            var wpxxJson = JSON.stringify(wpxxList);
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: "/zhfz/zhfz/bacs/belong/belongsave.do",
                data: wpxxJson,
                dataType: 'json',
                success: function (data) {
                    U.msg(data.content);
                    $('#detid').datagrid('reload', {
                        enpId: data.callbackData,
                        areaId: ssareaid,
                        trefresh: new Date().getTime()
                    });
                    $('#serialIdQuery').combogrid('setValue', data.callbackData);
                    $('#belonggrid').datagrid('load', {
                        enpId: data.callbackData, areaId: ssareaid, trefresh: new Date().getTime()
                    });
                    $.messager.progress('close');
                    $('#unit').combobox('clear');
                    $('#detailCount').textbox('setValue', '');
                    $('#description').textbox('setValue', '');
                },
                error: function (data) {
                    $.messager.progress('close');
                    U.msg(data.content);
                }
            });
        }
    }
</script>
</body>
</html>