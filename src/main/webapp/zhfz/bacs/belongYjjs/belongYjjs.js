var tempId = null;
var status = null;
var flag = 0;
$(function () {
    loadArea();
    $('#lawdocgrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        singleSelect: true,
        collapsible: false,
        loadMsg: '数据加载中...',
        method: 'get',
        url: contextPath + '/zhfz/bacs/belong/listBelongTemp.do',
        sortName: 'id',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 10, sortable: true, hidden: true}
        ]],
        columns: [[
            {
                field: 'xm', title: '嫌疑人', width: 60
            },
            {field: 'sfzh', title: '证件号码', width: 130},
            {field: 'ajbh', title: '案件编号', width: 130},
            {field: 'ajmc', title: '案件名称', width: 150},
            {field: 'yjdwName', title: '移交单位', width: 120},
            {field: 'jsdwName', title: '待接收单位', width: 120},
            {
                field: 'status', title: '状态', width: 60, formatter: function (value, row, index) {
                    if (value == 0 ||value == null) {
                        return '<font color="yellow">已移交</font>';
                    } else if (value == 1) {
                        return '<font color="gred">已接收</font>';
                    } else if (value == 2) {
                        return '<font color="red">已拒绝</font>';
                    }
                }
            }
        ]],
        pagination: true,
        pageSize: 20,
        pageList: [20, 30, 40, 50, 100],
        rownumbers: true,
        //行选择方法，进行条件触发
        onSelect: function (rowIndex, rowData) {
            tempId = rowData.id;
            status=rowData.status;
            lawdocDetailgridLoad(rowData);
        },
        toolbar: '#lawdocToolbar'
    })


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
        url: contextPath + '/zhfz/bacs/belong/listBelongTempDetail.do',
        singleSelect: true,
        columns: [[
            {field: 'name', title: '物品名称', width: 100},
            {field: 'detailCount', title: '数量', width: 50},
            {field: 'unit', title: '单位', width: 50},
            {field: 'description', title: '特征', width: 100},
            // {
            //     field: 'id', title: '操作', width: 300,
            //     formatter: function (value, row, index) {
            //         var e = '';
            //         var d = '';
            //         var p = '';
            //         var q = '';
            //         if (isGridButton("download")) {
            //             d = '<span class="spanRow"><a href="#" class="griddownload" onclick="downloadfile(' + index + ')">下载</a></span>';
            //         }
            //         q = '<span class="spanRow"><a href="#" class="griddownload" onclick="downloadfilePdf(' + index + ')">PDF下载</a></span>';
            //
            //         return d ;
            //     }
            // }
        ]],
        pagination: true,
        pageSize: 20,
        pageList: [20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#lawdocDetailToolbar'
    });
})


function lawdocDetailgridLoad(rowData) {
    $('#lawdocDetailgrid').datagrid('load', {
        tempId: rowData.id,
        trefresh: new Date().getTime()
    });
}

// 嫌疑人查询
function lawdocSearch() {
    var pName = $('#xm').val();
    var cNo = $('#sfzh').val();
    $('#lawdocgrid').datagrid('load', {
        xm: pName,
        sfzh: cNo,
        // areaId: $('#s_areaId').combobox("getValue"),
        trefresh: new Date().getTime()
    })
}

// 嫌疑人查询清除
function doClear() {
    $('#xm').textbox('setValue', '');
    $('#sfzh').textbox('setValue', '');
    // $("#s_areaId").combobox("setValue", "");
}

//加载办案场所
function loadArea() {
    $('#areaId').combobox({
        url: contextPath + '/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(data != null && data.length > 0){
                $('#s_areaId').combobox('setValue',data[0].id);
            }*/
        },
        onChange:function (data) {
            var lockerId_ulr = contextPath+"/zhfz/bacs/belong/listBelongLockerBox.do?areaId=" + data + "&timeSign=" + getTimeSign();
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
        $.messager.alert('提示', '物品已接收，请勿重复接收!');
        return
    }else if (status==2&&status!=null){
        $.messager.alert('提示', '物品已拒绝接收!');
        return
    }
    if (tempId == null) {
        $.messager.alert('提示', '请先选择待接收的物品!');
        return
    }else{
        var rowData = $('#lawdocgrid').datagrid('getSelected');
        var jsdwId=rowData.jsdwId;
        $.get(contextPath+"/common/getSessionInfo.do", function(data){
            var sessionObj = eval('('+data+')');
            console.log(sessionObj)
            if(sessionObj.currentOrg.orgCode!=jsdwId){
                $.messager.alert('提示', '请选择用正确的接收单位登录，进行物品接收!');
                return
            }else{
                showDialog('#gm_dialog', '选择柜门信息');
            }
        });

    }
}

function saveYj(){
    var entForm = $('#gm_form');
    var lawdocInfo = entForm.serializeObject();
    lawdocInfo["id"]=tempId;
    var enterpriseinfo = JSON.stringify(lawdocInfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath+'/zhfz/bacs/belong/saveYjBelong.do',
        dataType: 'json',
        data: enterpriseinfo,
        success: function (data) {
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            lawdocSearch()
        },
        error: function (data) {
            $.messager.alert('错误', '失败（updateBelongTempStatusById）!');
        }
    });
}

//一键拒绝
function yjRefuse() {
    if(status==1&&status!=null){
        $.messager.alert('提示', '物品已接收，请勿重复接收!');
        return
    }else if (status==2&&status!=null){
        $.messager.alert('提示', '物品已拒绝接收!');
        return
    }
    //获取登录人的信息，判断是否是接收单位的人
    if (tempId == null) {
        $.messager.alert('提示', '请先选择待接收的物品!');
        return
    }
    var rowData = $('#lawdocgrid').datagrid('getSelected');
    var jsdwId=rowData.jsdwId;
    $.get(contextPath+"/common/getSessionInfo.do", function(data){
        var sessionObj = eval('('+data+')');
        if(sessionObj.currentOrg.orgCode!=jsdwId){
            $.messager.alert('提示', '请选择用正确的接收单位登录，进行物品接收!');
            return
        }else{
            $.messager.confirm('拒绝接收确认', '是否拒绝接收此数据？', function (r) {
                if (r) {
                    $('#id').val(tempId);
                    showDialog('#bty_dialog', '拒绝意见');
                }

            });
        }
    });

}

function jjYjyj() {
    var entForm = $('#bty_form');
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath+'/zhfz/bacs/belong/updateBelongTempStatusById.do',
        dataType: 'json',
        data: enterpriseinfo,
        success: function (data) {
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
        },
        error: function (data) {
            $.messager.alert('错误', '失败（updateBelongTempStatusById）!');
        }
    });
}
//一键拒绝
function searchImage() {
    $("#showPic_dialog").html("");
    if (tempId == null) {
        $.messager.alert('提示', '请先选择待接收的物品!');
        return
    }
    var rowData = $('#lawdocgrid').datagrid('getSelected');
    var belongingsId=rowData.belongId;
    $.messager.progress({
        title: '请等待',
        msg: '加载数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: contextPath+'/zhfz/bacs/belong/getImagesTemp.do',
        data: {tempId: tempId},
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            for (var i = 0; i < data.length; i++) {
                // var url = contextPath+'/zhfz/bacs/iriscollection/imageshow.do?path='+ encodeURI(data[i].url)+"&timeSign="+getTimeSign();
                var url=encodeURI(data[i].url);
                $("#showPic_dialog").append('<img width="450" src="' + url + '" /><br/><br/>');
            }
            showDialog('#showPic_dialog', '照片');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('Error', '未知错误!');
        }
    });
}

function saveYjkg() {
    var ss=$('#lockerId').combobox('getValue');
    jQuery.ajax({
        type: 'GET',
        url: contextPath+'/zhfz/bacs/belong/belongsavebox.do?lockid=' + ss,
        dataType: 'json',
        success: function (data) {
            if (data && data.error) {
                $.messager.alert('错误', '开柜失败！' + data.content);
            }
        },
        error: function (data) {
            $.messager.alert('错误', '开柜失败（belongsavebox）!');
        }
    });
}