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
    <title>文书管理</title>
    <%@ include file="../common.jsp" %>
    <%--<script type="text/javascript" src="ajxx.js"></script>--%>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="personName" class="easyui-textbox" data-options="label:'姓名'" style="width:250px;"/>
        <input id="certificateNo" class="easyui-textbox" data-options="label:'证件号码'" style="width:250px;"/>
        <a id="cxShow" href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;display: none">查&nbsp;询</a>
        <a id="qcShow" href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;display: none">清&nbsp;除</a>
    </div>

    <div data-options="region:'center',border:false" style="width: 70%;height:100%">
        <table id="dg" style="width:100%;height:100%;">
        </table>
    </div>

    <div data-options="region:'east',split:true" style="width: 30%;">
        <table id="lawdocDetailgrid" style="width:100%;height:100%;">
        </table>
    </div>

</div>
<div>
    <form id="lawdocInfo" class="form-style" method="post" action="/zhfz/zhfz/lawdocProcess/download.do"
          accept-charset="UTF-8'">
        <input type="hidden" id="number" name="number"/>
        <input type="hidden" id="userId" name="userId"/>
        <input type="hidden" id="serialNo" name="serialNo"/>
        <input type="hidden" id="serialId" name="serialId"/>
        <input type="hidden" id="dataId" name="dataId"/>
        <input type="hidden" id="type" name="type"/>
        <input type="hidden" id="name" name="name"/>
    </form>

</div>
<div id="dlg"></div>
<script>
    var datagrid;
    var datagriddetail;
    var userid = '';
    var belongId = '';
    var caseNo = '';
    $(function () {
        $("#cxShow").show();
        $("#qcShow").show();
        datagrid = $('#dg').datagrid({
            method: "get",
            <%--url: '${ctx}/sys/user/page',--%>
            url: '/zhfz/zhfz/bacs/lawdoc/listPerson.do',
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
                    field: 'personName', title: '嫌疑人', width: 40,
                    formatter: function (value, row, index) {
                        return value;
                    }
                },
                {field: 'certificateNo', title: '证件号码', width: 120},
                {
                    field: 'createdTime', title: '存物时间', width: 120,
                    formatter: function (value, rec) {
                        return valueToDate(value);
                    }
                },
                /*{field: 'ajbh', title: '案件编号', width: 120},
                {field: 'ajmc', title: '案件名称', width: 120},*/
                {
                    field: 'ajlx',
                    title: '案件类型',
                    width: 40,
                    formatter: function (value) {
                        var str = "";
                        if (value == 0)
                            str = "刑事";
                        if (value == 1)
                            str = "行政";
                        return str;
                    }
                },
                {
                    field: 'abmc', title: '案件性质', width: 70,
                    formatter: function (value, rec) {
                        if (value == null || value == "") {
                            return '无';
                        } else {
                            return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                        }
                    }
                },
                {
                    field: 'areaName', title: '办案场所', width: 140,
                    formatter: function (value, row, index) {
                        if (value == null || value == "") {
                            return '无';
                        } else {
                            return "<div title='" + value + "' class='textEllipsis'>" + value + "</div>";
                        }
                    }
                },
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
                userid = row.personId;
                caseNo = row.caseNo;
                belongId = row.belongId;
                lawdocDetailgridLoad(row);
            }
        });

        datagriddetail = $('#lawdocDetailgrid').datagrid({
            method: "get",
            queryParams: "{'enpId':'-99','trefresh':" + new Date().getTime() + "}",
            url: '/zhfz/zhfz/bacs/lawdoc/listlawdoc.do',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#tb',
            columns: [[
                {field: 'name', title: '文书名称', width: 350},
                {
                    field: 'operate', title: '操作', width: 120,
                    formatter: function (value, row, index) {
                        var e = "<a onclick='downloadLow(" + index + ")' class='button-edit button-blue'>下载</a>";
                        return e;
                    }
                }
            ]],
            onLoadSuccess: function (data) {
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

            }
        });
    });


    function lawdocDetailgridLoad(rowData) {
        $('#lawdocDetailgrid').datagrid('load', {
            lawdocId: rowData.id,
            userId: userid,
            trefresh: new Date().getTime()
        });
    }

    function queryUsers() {
        $(datagrid).datagrid('load', {
                certificateNo: $('#certificateNo').textbox("getValue"),
                personName: $('#personName').textbox("getValue")
            }
        );
    }

    function clearSearch() {
        $('#personName').textbox('setValue', '');
        $('#certificateNo').textbox('setValue', '');
    }


    //文书下载
    function downloadLow(index) {
        var rowData = $('#lawdocDetailgrid').datagrid('getRows')[index];
        var number = rowData.number;
        var name = rowData.name;
        var type = rowData.type;
        var userId = rowData.userId;
        var dataId = belongId;
        $('#number').val(number);
        $('#name').val(name);
        $('#type').val(type);
        $('#userId').val(userId);
        $('#dataId').val(dataId);
        var row = datagrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择左侧相应案件');
        } else {
            $.messager.progress({
                title: '请等待',
                msg: '下载中...'
            });
            document.getElementById("lawdocInfo").submit();
            $.messager.progress('close');
        }
    }

</script>
</body>
</html>