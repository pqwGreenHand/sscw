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
        <input id="ajmc1" class="easyui-textbox" data-options="label:'案件名称'" style="width:250px;"/>
        <input id="ajbh1" class="easyui-textbox" data-options="label:'案件编号'" style="width:250px;"/>
        <a href="javascript:void(0)" onclick="queryAjxx()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
        <a href="javascript:void(0)" onclick="clearAjxx()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;">清&nbsp;除</a>
    </div>

    <div data-options="region:'center',border:false" style="height:100%">
        <table id="dgajxx" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<div id="dlg"></div>
<script>
    var datagridajxx;
    $(function () {
        datagridajxx = $('#dgajxx').datagrid({
            method: "get",
            <%--url: '${ctx}/sys/user/page',--%>
            url:  '/sscw/zhfz/bacs/belong/queryCase.do',
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
                                $('#dgajxx').datagrid('checkRow', index);
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

    function queryAjxx() {
        $(datagridajxx).datagrid('load', {
              ajmc: $('#ajmc1').textbox("getValue"),
              ajbh: $('#ajbh1').textbox("getValue")
            }
        );
    }

    function clearAjxx(){
        $('#ajmc1').textbox('setValue', '');
        $('#ajbh1').textbox('setValue', '');
    }

</script>
</body>
</html>