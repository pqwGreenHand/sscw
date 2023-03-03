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
    <title>电子档案</title>
    <%@ include file="../common.jsp" %>
    <%--<script type="text/javascript" src="ajxx.js"></script>--%>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="s_case_name" name="s_case_name"  class="easyui-textbox" data-options="label:'案件名称'" style="width:250px;"/>
        <input id="s_person_name" name="s_person_name" class="easyui-textbox" data-options="label:'姓名'" style="width:250px;"/>
        <a id="cxShow" href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;display: none">查&nbsp;询</a>
        <a id="qcShow" href="javascript:void(0)" onclick="clearSearch()" class="easyui-linkbutton button-line-red"
           style="width: 70px;margin-left: 10px;display: none">清&nbsp;除</a>
    </div>

    <div data-options="region:'center',border:false" style="width: 70%;height:60%">
        <table id="treegrid" style="width:100%;height:100%;">
        </table>
    </div>

    <div data-options="region:'south',split:true" class="easyui-tabs" pill="true" style="height: 40%">
        <div title="图片材料" style="padding:5px">
            <table id="imageDatagrid"></table>
        </div>
        <div title="视频材料" style="padding:5px">
            <table id="videoDatagrid"></table>
        </div>
        <div title="其它材料" style="padding:5px">
            <table id="qtDatagrid"></table>
        </div>
    </div>

</div>
<div id="dlg"></div>

<div id="uploadonlineinfo" style="display: none">
    <form id="fam" method="post" enctype="multipart/form-data">
        <table style="margin: 0 auto; padding: 10px;width: 90%">
            <tr>
                <td>文件类型:</td>
                <td><select id="fileType" name="fileType" class="easyui-combobox" required="true"
                            style="margin: -2px; width: 190px; "
                            data-options="url: 'type_data.json',valueField: 'id',textField: 'text',validType:'maxLength[128]',invalidMessage:'最长128字节'">
                </select></td>
            </tr>
            <tr>
                <td>人员信息:</td>
                <td><select style="width:190px" editable="true" id="person" required="true"
                            name="type" class="easyui-combobox">
                </select></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input class="easyui-filebox" name="onlinefilename" data-options="prompt:'文件上传'"
                           buttonText="选择文件"
                           style="width:95%"></input>
                    <span style="color:#F00"> * </span>

                </td>
            </tr>
        </table>
    </form>
</div>


<script>
    var datagrid;
    $(function () {
        $("#cxShow").show();
        $("#qcShow").show();
        $('#treegrid').treegrid({
            /*				title:'电子档案',*/
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: true,
            loadMsg: 'Loading...',
            method: 'get',
            url: '/sscw/zhfz/bacs/dzRecord/listTree.do',
            queryParams: {
                trefresh: new Date().getTime()
            },
            idField: 'id',
            treeField: 'name',
            singleSelect: true,
            columns: [[
                {field: 'name', title: '名称', width: 300},
                {
                    field: 'id', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var z = '';
                        z = '<a href="#" class="button-edit button-blue" onclick="filesUpload(\'' + row.id + '\')">文件上传</a>';
                        return z;
                    }
                },
                {field: 'areaId', hidden: true}
            ]],
            pagination: true,
            pageList: [10, 20, 30, 40, 50, 100],
//				rownumbers:true,
            onLoadSuccess: function (row, data) {
                $('#treegrid').treegrid('collapseAll');
                if (data.rows.length == 0) {
                    $('.datagrid .datagrid-pager').css('display', 'none');
                } else {
                    $('.datagrid .datagrid-pager').css('display', 'block');
                }
            },
            onClickRow: function (rowData) {
                var id = rowData.id.split('_')[0];
                var pId = rowData.id.split('_')[1];
                showImage(id, pId)
                showVideo(id, pId)
                showQt(id, pId)
            },
            toolbar: '#toolbar',
            onSelect: function (row) {
            }
        });

        var p = $('#treegrid').treegrid('getPager');
        $(p).pagination({
            onBeforeRefresh: function () {

                $('#treegrid').treegrid('reload', {
                    // caseName : $('#s_case_name').val(),
                    trefresh: new Date().getTime()
                });
                return false;
            }
        });
    });

    var caseId;
    var dialog;

    //资料上传
    function filesUpload(value) {
        loadPerson(value.split("_")[1], value.split("_")[0]);
        caseId = value.split("_")[0];
        dialog = $("#uploadonlineinfo").dialog({
            title: '文件上传',
            width: 440,
            height: 360,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#fam").form('validate');
                    if (isValid) {

                        var fileType = $("#fileType").combobox("getValue");
                        var personId = $("#person").combobox("getValue");
                        var add =  "/sscw/zhfz/bacs/archives/onlineupload.do?personId=" + personId + "&fileType=" + fileType + "&caseId=" + caseId;
                        $('#fam').form('submit', {
                            url: add,
                            onSubmit: function () {

                            },
                            success: function (result) {
                                if (result == "success") {
                                    $('#uploadonlineinfo').dialog('close');
                                    $.messager.show({
                                        title: 'Success',
                                        msg: '上传成功'
                                    });
                                } else {
                                    $.messager.show({
                                        title: 'Error',
                                        msg: '文件不能为空，请重新选择文件！'
                                    });
                                }
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

    function loadPerson(personId, caseid) {
        $('#person').combobox({
            url: '/sscw/zhfz/bacs/combobox/listPerson.do?caseId=' + caseid,
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function (data) {
                if (personId != null) {
                    $("#person").combobox("setValue", personId)
                }
            },
            onChange: function (newValue, oldValue) {
            }
        });
    }



    function queryUsers() {
        $('#treegrid').treegrid('load', {
            ajmc: $('#s_case_name').textbox("getValue"),
            personName: $('#s_person_name').textbox("getValue")
            }
        );
    }

    function clearSearch() {
        $('#s_case_name').textbox('setValue', '');
        $('#s_person_name').textbox('setValue', '');
    }

    function showImage(id, pid) {
        $('#imageDatagrid').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            fitColumns: true,
            width: '100%',
            height: '100%',
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: '数据加载中...',
            method: 'get',
            //sortName: 'code',
            //sortOrder: 'desc',
            remoteSort: false,
            //idField:'code',
            queryParams: {lawCaseId: id, personId: pid, caseDataType: "1", trefresh: new Date().getTime()},
            url: '/sscw/zhfz/bacs/clue/listCaseDataClue.do',
            singleSelect: true,
            columns: [[
                {field: 'checked', checkbox: true, width: 0},
                {field: 'fileName', title: '文件名称', width: 100},
                {field: 'policeName', title: '上传民警', width: 50},
                {
                    field: 'uploadTime', title: '上传时间', width: 80,
                    formatter: function (value, rec) {
                        return valueToDate(value);
                    }
                }, {
                    field: 'id', title: '操作', width: 50,
                    formatter: function (value, row, index) {
                        var d = '';
                        d = '<a  href="/sscw/zhfz/bacs/clue/download.do?id=' + value + '" class="button-delete button-red" >下载</a>';
                        return d;
                    }
                }
            ]],
            pagination: true,
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: true,
            //当数据加载成功时触发
            onLoadSuccess: function () {
                //该死的onload的未知bug,延迟0.1秒就行了
                //setTimeout(function(){checkRoles();},100);
//					checkRoles();
            }

        });
    }

    function showVideo(id, pid) {
        $('#videoDatagrid').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            fitColumns: true,
            width: '100%',
            height: '100%',
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: '数据加载中...',
            method: 'get',
            //sortName: 'code',
            //sortOrder: 'desc',
            remoteSort: false,
            //idField:'code',
            queryParams: {lawCaseId: id, personId: pid, caseDataType: "2", trefresh: new Date().getTime()},
            url: '/sscw/zhfz/bacs/clue/listCaseDataClue.do',
            singleSelect: true,
            columns: [[
                {field: 'checked', checkbox: true, width: 0},
                {field: 'fileName', title: '文件名称', width: 100},
                {field: 'policeName', title: '上传民警', width: 50},
                {
                    field: 'uploadTime', title: '上传时间', width: 80,
                    formatter: function (value, rec) {
                        return valueToDate(value);
                    }
                }, {
                    field: 'id', title: '操作', width: 50,
                    formatter: function (value, row, index) {
                        var d = '';
                        d = '<a  href="/sscw/zhfz/bacs/clue/download.do?id=' + value + '" class="button-delete button-red">下载</a>';
                        return d;
                    }
                }
            ]],
            pagination: true,
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: true,
            //当数据加载成功时触发
            onLoadSuccess: function () {
                //该死的onload的未知bug,延迟0.1秒就行了
                //setTimeout(function(){checkRoles();},100);
//					checkRoles();
            }

        });
    }

    function showQt(id, pid) {
        $('#qtDatagrid').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            fitColumns: true,
            width: '100%',
            height: '100%',
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: '数据加载中...',
            method: 'get',
            //sortName: 'code',
            //sortOrder: 'desc',
            remoteSort: false,
            //idField:'code',
            queryParams: {lawCaseId: id, personId: pid, caseDataType: "3", trefresh: new Date().getTime()},
            url: '/sscw/zhfz/bacs/clue/listCaseDataClue.do',
            singleSelect: true,
            columns: [[
                {field: 'checked', checkbox: true, width: 0},
                {field: 'fileName', title: '文件名称', width: 100},
                {field: 'policeName', title: '上传民警', width: 50},
                {
                    field: 'uploadTime', title: '上传时间', width: 80,
                    formatter: function (value, rec) {
                        return valueToDate(value);
                    }
                }, {
                    field: 'id', title: '操作', width: 50,
                    formatter: function (value, row, index) {
                        var d = '';
                        d = '<a  href="/sscw/zhfz/bacs/clue/download.do?id=' + value + '" class="button-delete button-red">下载</a>';
                        return d;
                    }
                }
            ]],
            pagination: true,
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: true,
            //当数据加载成功时触发
            onLoadSuccess: function () {
                //该死的onload的未知bug,延迟0.1秒就行了
                //setTimeout(function(){checkRoles();},100);
//					checkRoles();
            }

        });

    }

</script>
</body>
</html>