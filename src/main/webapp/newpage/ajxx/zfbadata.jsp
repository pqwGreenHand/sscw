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
    <title>执法办案数据信息</title>
    <%@ include file="../common.jsp" %>
</head>

<body>
<div data-options="region:'north',border:false" style="padding: 10px 5px;">
    <input id="zfbaajmc" class="easyui-textbox" data-options="label:'案件名称'" style="width:250px;"/>
    <input id="zfbaajbh" class="easyui-textbox" data-options="label:'案件编号'" style="width:250px;"/>
    <a href="javascript:void(0)" onclick="queryZfbaAjxx()" class="easyui-linkbutton button-line-blue"
       style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
    <a href="javascript:void(0)" onclick="clearZfbaSearch()" class="easyui-linkbutton button-line-red"
       style="width: 70px;margin-left: 10px;">清&nbsp;除</a>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="height:50%">
        <table id="dgaj" style="width:100%;">
        </table>
    </div>
    <div data-options="region:'south',border:false" style="height:50%;">
        <table id="dgdetail" style="width:100%;">
        </table>
    </div>
</div>
<style scoped>
    #form tr {
        line-height: 35px;
    }

    #form tr input, select {
        width: 220px;
    }
</style>
<script>
    var datagridaj;
    var datagridperson;
    var dialog;
    $(function () {
        datagridaj = $('#dgaj').datagrid({
            method: "get",
            url: '/zhfz/zhfz/bacs/belong/listCaseZfba.do',
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
                {field: 'ZBR_SFZH', title: 'ZBR_SFZH', hidden: true},
                {field: 'JYAQ', title: 'JYAQ', hidden: true},
                {field: 'ZBDW_BH', title: 'ZBDW_BH', hidden: true},
                {
                    field: 'AJMC', title: '案件名称', width: 30,
                    formatter: function (value, row, index) {
                        return "<font>" + value + "</font>";
                    }
                }, {
                    field: 'AJBH', title: '案件编号', width: 50,
                    formatter: function (value, row, index) {
                        return "<font>" + value + "</font>";
                    }
                },
                {
                    field: 'AJLX', title: '案件性质', width: 20,
                    formatter: function (value, rec, index) {
                        if (value == '01') {
                            return '行政案件';
                        } else {
                            return "刑事案件";
                        }
                    }
                }, {
                    field: 'ZBR_XM', title: '主办民警', width: 20
                },
                {field: 'FADDXZ', title: '案发地址', width: 30},
                {field: 'FXSJ', title: '案发时间', width: 30}
            ]],
            onLoadSuccess: function (data) {
                if (data) {
                    $.each(data.rows,
                        function (index, item) {
                            if (item.checked) {
                                $('#dgaj').datagrid('checkRow', index);
                            }
                        });
                }
            },
            onSelect: function (index, row) {
                //行选择方法，进行条件触发
                detailPersongridLoad(row);
            }
        });

        datagridperson = $('#dgdetail').datagrid({
            method: "get",
            url: '/zhfz/zhfz/bacs/belong/listPersonZfba.do',
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
            queryParams: {'caseNo': '-99', 'trefresh': new Date().getTime()},
            // toolbar: '#tb',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'GJ', title: 'GJ', hidden: true},
                {field: 'AGE', title: 'AGE', hidden: true},
                {field: 'XB', title: 'XB', hidden: true},
                {field: 'CSRQ', title: 'CSRQ', hidden: true},
                {field: 'MZ', title: 'MZ', hidden: true},
                {field: 'RYBH', title: '人员编号', width: 50},
                {field: 'RYXM', title: '姓名', width: 30},
                {field: 'ZJHM', title: '证件号码', width: 40,},
                {field: 'HJDXZ', title: '户籍地', width: 40}
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
                if (row.isFixed == 1) {//固定的
                    $('#btn-delete').hide();
                } else {
                    $('#btn-delete').show();
                }
            }

        });
    });

    //选中触发刷新
    function detailPersongridLoad(rowData) {
        $('#dgdetail').datagrid('load', {caseNo: rowData.AJBH, trefresh: new Date().getTime()});
    }

    function queryZfbaAjxx() {
        $(datagridaj).datagrid('load', {
            caseName: $('#zfbaajmc').textbox("getValue"),
                ajbh: $('#zfbaajbh').textbox("getValue")
            }
        );
    }

    function clearZfbaSearch() {
        $('#zfbaajmc').textbox('setValue', '');
        $('#zfbaajbh').textbox('setValue', '');
    }

    //加载主办单位
    function loadZbdw(zbdwId) {
        $("#zbdwId").combobox({
            url: '/zhfz/zhfz/common/combobox/listAllOrganizationNameCombobox.do',
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function () {
                if (zbdwId != null) {
                    $("#zbdwId").combobox("setValue", zbdwId);
                }
            }
        });
    }

</script>
</body>
</html>