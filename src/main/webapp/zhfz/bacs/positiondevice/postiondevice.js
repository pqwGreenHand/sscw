$(function () {
    // $('#room').combobox({
    //     textField : 'value',
    //     url: '../../bacs/combobox/listAllRoomNameCombobox.do',
    //     sortName: 'id',
    //
    //
    //     /*onSelect: function(rowIndex, rowData){
    //         var grid = $('#room').combogrid('grid');
    //         var row = grid.datagrid('getSelected');
    //     },
    //     */
    //
    // });
    $('#datagrid').datagrid({
        //title:'定位设备',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载中...',
        method: 'get',
        url: 'list.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'id',
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[

            {title: 'ID', field: 'id', width: 80, sortable: true}
        ]],
        columns: [[
            {field: 'deviceNo', title: '设备编号', width: 300, align: 'center'},
            {
                field: 'deviceType',
                title: '设备类型',
                width: 300,
                formatter: function (value) {
                    var str = "";
                    if (value == 1)
                        str = "主基站930";
                    if (value == 2)
                        str = "激活器";
                    if (value == 3)
                        str = "从基站931";
                    return str;

                }
            },
            {field: 'mode', title: '定位模式', width: 300, align: 'center'},
            {field: 'groupName', title: '组名称', width: 100, align: 'center'},
            {field: 'groupNo', title: '组内序号', width: 100, align: 'center'},
            {field: 'ip', title: '设备IP', width: 300, align: 'center'},
            {field: 'areaName', title: '办案场所', width: 300, align: 'center'},
            {field: 'roomName', title: '功能室', width: 300, align: 'center'},
            {
                field: 'operation',
                title: '操作',
                width: 300,
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '';
                    if (isGridButton("deviceedit")) {
                        e = '<span class="spanRow"><a href="#" class="gridedit" onclick="deviceedit(' + index + ')">修改</a></span>';
                    }
                    if (isGridButton("devicedelete")) {
                        d = '<span class="spanRow"><a href="#" class="griddel" onclick="devicedelete(' + row.id + ')">删除</a></span>';
                    }
                    return e + d;
                }
            }
        ]],
        pagination: true,
        pageSize: 15,
        pageList: [15, 30, 40, 50, 100],
        rownumbers: true,

        toolbar: '#deviceToolbar'

    });
    var p = $('#datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function (pageNumber, pageSize) {
            doSearch();
            return false;
        }
    });

    $('#areaId').combobox({
        url: '../combobox/getAllInterrogateAreaName.do',
        valueField: 'id',
        textField: 'value',
        onChange: function (val) {
            $('#roomId').combobox('clear');
            $('#roomId').combobox({
                url: '../../bacs/combobox/listAllRoomNameCombobox.do?areaId=' + val,
                valueField: 'id',
                textField: 'value'
            });
        }
    });

    $('#s_device_interrogate_area_id').combobox({
        url: '../combobox/getAllInterrogateAreaName.do',
        valueField: 'id',
        textField: 'value',
        onChange: function (val) {
            doSearchRoom(val);
        }
    });

    $('#fudong').remove();


});


function devicedelete(index) {
    //var rowData = $('#datagrid').datagrid('getRows')[index];
    var value = index;
    $.messager.confirm('Confirm', '确定删除?', function (r) {
        if (r) {
            $.messager.progress({
                title: '请等待',
                msg: '删除数据中...'
            });
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: 'remove.do',
                data: '{"id":' + value + '}',
                dataType: 'json',
                success: function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.content,
                        timeout: 3000
                    });
                    doSearch();
                    $.messager.progress('close');
                },
                error: function (data) {
                    $.messager.progress('close');
                    $.messager.alert('错误', '未知错误!');
                }
            });

        }
    });
}
//关联查询办案场所与功能室
function doSearchRoom(value) {
    $('#room').combobox("clear");
    $('#room').combobox({
        valueField: 'id',
        textField: 'value',
        url: '../combobox/listAllRoomNameCombobox.do?areaId='+value,
        onChange: function (val) {
            doSearch();
        }
    });
}


function doSearch() {
    var ss = $('#device_no').textbox('getValue');
    var se = $('#s_device_interrogate_area_id').combobox('getValue');
    var room = $('#room').combobox('getValue');
    $('#datagrid').datagrid('load', {
        deviceNo: ss,
        areaId: se,
        room: room,
        trefresh: new Date().getTime()
    });
}


function doClear() {
    $('#device_no').textbox('setValue', '');
    $('#s_device_interrogate_area_id').combobox('setValues', '');
    $('#room').combobox('setValues', '');
}


function deviceadd() {
    showDialog('#data_dialog', '添加定位设备');
    $('#data_form').form('clear');
    url = '../../bacs/positiondevice/add.do';
}


function deviceedit(index) {
    var rowData = $('#datagrid').datagrid('getRows')[index];

    showDialog('#data_dialog', '编辑定位设备');
    $('#data_form').form('clear');
    $('#data_form').form('load', rowData);
    url = '../../bacs/positiondevice/edit.do?id=' + rowData.id;

}

function saveData() {
    if (!checkForm('#data_form')) {
        return;
    }
    if ($("#deviceNo").val() == null || $("#deviceNo").val() == '' ||
        $("#deviceType").combobox('getValue') == null || $("#deviceType").combobox('getValue') == '' ||
        $("#mode").combobox('getValue') == null || $("#mode").combobox('getValue') == '' ||
        $("#areaId").combobox('getValue') == null || $("#areaId").combobox('getValue') == '' ||
        $("#roomId").combobox('getValue') == null || $("#roomId").combobox('getValue') == ''
    ) {
        $.messager.alert('警告', '信息填写不完整!');
        return;
    }

    var positionDeviceForm = $('#data_form');
    var positionDeviceFormJson = JSON.stringify(positionDeviceForm.serializeObject());
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: positionDeviceFormJson,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');

            $('#data_dialog').dialog('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
            doSearch();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!' + data);
        }
    });
}


