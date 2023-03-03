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
    <title>人员管理</title>
    <%@ include file="../common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="ajmc" class="easyui-textbox" data-options="label:'案件名称'" style="width:220px;"/>
        <input id="ajbh" class="easyui-textbox" data-options="label:'案件编号'" style="width:220px;"/>
        <input id="xm" class="easyui-textbox" data-options="label:'姓名'" style="width:150px;"/>
        <input id="zjhm" class="easyui-textbox" data-options="label:'证件号码'" style="width:220px;"/>
        <select id="gender" class="easyui-combobox" panelHeight="auto" name="sex" label='性别' style="width:180px">
        </select>
        <a href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
        <a href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;">清&nbsp;除</a>
    </div>

    <div data-options="region:'center',border:false" style="height:100%">
        <table id="dgperson" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<script>
    var datagridPerson;
    $(function () {
        datagridPerson = $('#dgperson').datagrid({
            method: "get",
            // url:  '/sscw/zhfz/bacs/belong/queryCase.do',
            url: '/sscw/zhfz/bacs/belong/queryPerson.do',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: true,
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
                /*{
                    field: 'operate', title: '操作', width: 120,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        return e + '  ' + d;
                    }
                }*/
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

</script>
<style scoped>
    #form tr {
        line-height: 35px;
    }

    #form tr input,select{
        width:220px;
    }
</style>
</body>
</html>