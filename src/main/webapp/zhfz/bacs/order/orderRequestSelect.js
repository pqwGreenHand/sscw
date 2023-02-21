$(function () {
    // 预约信息
    $('#datagrid_orderrequest').datagrid({
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
        url: 'orderRequestlist.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[{
            title: 'id',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[
            {
                field: 'caseNatureId',
                hidden: true
            },
            {
                field: 'orderNo',
                title: '预约编号',
                width: 75
            },
            {
                field: 'personName',
                title: '预约民警',
                width: 40
            },
            {
                field: 'organizationName',
                title: '办案单位',
                width: 60
            },
            {
                field: 'userMobile',
                title: '联系电话',
                width: 60
            },
            {
                field: 'status',
                title: '状态',
                width: 30,
                formatter: function (value) {
                    if (value == '0') {
                        return '<font color="#00FF00">已预约</font>';
                    } else if (value == '1') {
                        return '<font color="#1E90FF">已推迟</font>';
                    } else if (value == '2') {
                        return '<font color="#FFFF00">已到达</font>';
                    } else if (value == '3') {
                        return '<font color="#A9A9A9">已取消</font>';
                    } else if (value == '4') {
                        return '<font color="gray">已过期</font>';
                    } else if (value == '5') {
                        //  return '<font color="#00FF00">部分已到达</font>';
                        return '<font color="#FFFF00">已到达</font>';
                    } else if (value == '6') {
                        //  return '<font color="#00FF00">部分已到达</font>';
                        return '<font color="#FFFF00">审核未通过</font>';
                    }
                }
            },
            {
                field: 'sumPerson',
                title: '总人数',
                width: 40
            },
            {
                field: 'maleCount',
                title: '男',
                width: 20
            },
            {
                field: 'femaleCount',
                title: '女',
                width: 20
            },
            {
                field: 'planTime',
                title: '预计到达时间',
                width: 80,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {
                field: 'description',
                title: '简要案情',
                width: 80,
                hidden: true
            },
            {
                field: 'ajlx',
                title: '案件类型',
                width: 50,
                formatter: function (value) {
                    if (value == '1') {
                        return '行政';
                    } else if (value == '2') {
                        return '刑事';
                    }
                }
            },
            {
                field: 'caseNature',
                title: '案由',
                width: 80
            },
            {
                field: 'areaName',
                title: '办案场所',
                width: 60
            },
            {
                field: 'id',
                title: '操作',
                width: 130,
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '';
                    var c = '';
                    var d = '';
                    var m = '';
                    var s = '';
                    var dl = '';
                    if (isGridButton("orderRequestEdit")) {
                        e = '<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="orderRequestEdit(' + index + ')">修改</a></span>';
                    }
                    if (isGridButton("orderRequestCancel")) {
                        c = '<span class="spanRow"><a href="#" class="gridcancel" style="color:aquamarine" name="nobutton"  onclick="orderRequestCancel('
                            + index
                            + ')">取消</a></span>';
                    }
                    if (isGridButton("orderRequestDelay")) {
                        d = '<span class="spanRow"><a href="#" class="griddelay" style="color:aquamarine; " name="nobutton" onclick="orderRequestDelay('
                            + index
                            + ')">延期</a></span>';
                    }
                    if (isGridButton("orderRequestReach")) {
                        m = '<span class="spanRow"><a href="#" class="gridarrive" style="color:aquamarine" name="nobutton"  onclick="orderRequestReach('
                            + index
                            + ')">到达</a></span>';
                    }
                    if (isGridButton("orderRequestCancel")) {
                        dl = '<span class="spanRow"><a href="#" class="griddel" style="color:aquamarine" name="nobutton"  onclick="orderRequestRemove('
                            + index
                            + ')">删除</a></span>';
                    }
                    if (row.status == '3') {
                        return null;
                    }
                    // if (row.status =='0' ||row.status =='1') {
                    //     if (row.type == '2'&& row.shzt == '0') {
                    //         s = '<span class="spanRow"><a href="#" class="gridarrive" style="color:aquamarine" name="nobutton"  onclick="orderSh('
                    //             + index
                    //             + ')">审核</a></span>';
                    //     }
                    // }
                    if (row.status == '2') {
                        return e;
                    }
                    if (row.status == '4') {
                        //当前时间 - createdTime < = 24 显示
                        var creatTime = '';
                        creatTime = getDatetime(row.createdTime);
                        var creatTime1 = creatTime.replace(/-/g, '/');
                        var how = new Date(creatTime1);
                        var today = new Date();
                        var hour = parseInt((today.getTime() - how.getTime()) / 1000 / 60 / 60);
                        //alert(hour);
                        if (hour <= 24) {
                            return d;
                        } else {
                            return null;
                        }
                    }
                    return e + d + c + m + s + dl;
                }
            }]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        onLoadSuccess: function (data) {
        },
        // 行选择方法，进行条件触发
        onSelect: function (rowIndex, rowData) {
            orderRequestDataId = rowData.id;
            AREAID = rowData.areaId;
            orderstatusreLoad(rowData);
            orderrquestperosnreLoad(rowData);
            if (rowData.status == '3') {
                $('#addPerson').linkbutton('disable');
            } else {
                $('#addPerson').linkbutton('enable');
            }
            if (rowData.type == 2 && rowData.status != '3') {
                $('#addPerson1').linkbutton('enable');
            } else {
                $('#addPerson1').linkbutton('disable');
            }
        },
        toolbar: '#Toolbar_orderrequest'
    });
    var p = $('#datagrid_orderrequest').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            var areaId = $('#interrogatearea').combobox('getValue');
            $('#datagrid_orderrequest').datagrid('load', {
                userName: $('#o_user_name').val(),
                orderNo: $('#o_order_no').val(),
                personName: $('#o_person_name').val(),
                areaId: areaId,
                trefresh: new Date().getTime()
            });
            return false;
        }
    });
    $('#fudong').remove();
})

//获取右侧的personList
function onloadPerson() {
    var requestId = null;
    var title = "<div id='dataGridTitle'>" +
        "<span style='float: left'>嫌疑人详情</span>" +
        "<span style='float: right;margin-right: 20px'>" +
        "<a href='#' style='padding: 0px;margin-left: 5px;border: 0px' class='easyui-linkbutton' name='noButton' id='addPerson1' iconCls='icon-add' disabled='true' onclick='yjOrderSh()'>一键审核</a>" +
        "<a href='#' style='padding: 0px;margin-left: 5px;border: 0px' class='easyui-linkbutton' name='noButton' id='addPerson' iconCls='icon-add' disabled='true' onclick='addSuspection()'>添加</a>" +
        "<a href='#' style='padding: 0px;margin-left: 15px;border: 0px' class='easyui-linkbutton' name='noButton' id='personRefresh' iconCls='icon-reload' plain='true' onclick='personRefrsh()'>刷新</a>" +
        "</span></div>";
    $('#datagrid_orderstatus_person').datagrid({
        title: title,
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: 'Loading...',
        method: 'get',
        remoteSort: false,
        queryParams: {
            orderRequestId: requestId
        },
        url: 'OrderStatusPersonlist.do',
        singleSelect: true,
        frozenColumns: [[
            {
                title: 'id',
                field: 'id',
                width: 10,
                sortable: true,
                hidden: true
            }]],
        columns: [[
            {
                field: 'name',
                title: '姓名',
                width: 25
            },
            {
                field: 'certificateNo',
                title: '证件编号',
                width: 45
            },
            {
                field: 'id',
                title: '操作',
                width: 35,
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '';
                    var s = '';
                    if (isGridButton("orderRequestPersonRemove")) {
                        e = '<span class="spanRow"><a href="#" class="griddel" id="orderRequestPersonRemove" onclick="orderRequestPersonRemove('
                            + index
                            + ')">删除</a></span>';
                    }
                    var f = '';
                    if (isGridButton("orderRequestPersonEdit")) {
                        f = '<span class="spanRow"><a href="#" class="gridedit" id="orderRequestPersonEdit" onclick="orderRequestPersonEdit('
                            + index
                            + ')">修改</a></span>';
                    }
                    if (row.type == '2' && row.shzt == '0') {
                        s = '<span class="spanRow"><a href="#" class="gridarrive" style="color:aquamarine" name="nobutton"  onclick="orderSh('
                            + index
                            + ')">审核</a></span>';
                    }
                    if (row.type == '2' && row.shzt != '0') {
                        s = '<span class="spanRow"><a href="#" class="gridarrive" style="color: #dae3e0" name="nobutton"  onclick="orderYsh('
                            + index
                            + ')">已审核</a></span>';
                    }
                    return f + e + s;
                }
            }]],
        pagination: false,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        // 当数据加载成功时触发
        onLoadSuccess: function () {
            $.parser.parse($("#dataGridTitle").parent());
        },
        onDblClickRow: function (index) {
            var rowData = $('#datagrid_orderstatus_person')
                .datagrid('getRows')[index];
            showDialog('#dialog_orderrequest_person_detail',
                '人员详细信息');
            $('#form_orderrequest_person_detai').form('clear');
            // $('#form_orderrequest').form('load',rowData);
            $('#contentId_person').val(rowData.id);
            $('#requestId_person').val(rowData.orderRequestId);
            $('#name_person').val(rowData.name);
            var certificateTypeId = rowData.certificateTypeId;
            $('#certificateTypeId_person').combobox({
                url: '../combobox/certificateTypes.do',
                valueField: 'id',
                textField: 'value',
                onLoadSuccess: function (data) {
                    $('#certificateTypeId_person').combobox('setValue', certificateTypeId);
                }
            });
            $('#certificateNo_person').val(
                rowData.certificateNo);
            var sex = rowData.sex;
            $('#sex_person').combobox('select', sex);
            $('#descriptionperson_person').val(
                rowData.description);
            if (rowData.isJuvenile == 1) {
                $("input[name='xqisJuvenile'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqisJuvenile'][value='0']").prop("checked", true);
            }
            if (rowData.isGuardian == 1) {
                $("input[name='xqisGuardian'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqisGuardian'][value='0']").prop("checked", true);
            }
            if (rowData.isGravida == 1) {
                $("input[name='xqisGravida'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqisGravida'][value='0']").prop("checked", true);
            }
            $('#xqgravidaMonth').val(rowData.gravidaMonth);
            if (rowData.isGravidaProve == 1) {
                $("input[name='xqisGravidaProve'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqisGravidaProve'][value='0']").prop("checked", true);
            }
            if (rowData.isDisease == 1) {
                $("input[name='xqisDisease'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqisDisease'][value='0']").prop("checked", true);
            }
            if (rowData.isDiseaseProve == 1) {
                $("input[name='xqisDiseaseProve'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqisDiseaseProve'][value='0']").prop("checked", true);
            }
            if (rowData.jkb == 1) {
                $("input[name='xqjkb'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqjkb'][value='0']").prop("checked", true);
            }
            if (rowData.sfcrgfxdq != "无" && rowData.sfcrgfxdq !=null) {
                $("input[name='xqsfcrgfxdq'][value='1']").prop("checked", true);
                $("#xqsfcrgfxdq").val(rowData.sfcrgfxdq );
            }else{
                $("input[name='xqsfcrgfxdq'][value='0']").prop("checked", true);
                $("#xqsfcrgfxdq").val(rowData.sfcrgfxdq );
            }
            $('#xqjzyms').val(rowData.jzyms);
            $('#xqxyrtw').val(rowData.xyrtw);
            if (rowData.zjdz != null) {
                $('#xqzjdz').val(rowData.zjdz.replace("%20", ""));
            }
            $('#xqzhdz').val(rowData.zhdz);
            $('#xqzjfjrq').datetimebox('setValue', valueToDate(rowData.zjfjrq));

            if ((rowData.other != "" && rowData.other != null) || (rowData.mgsf != null&&rowData.mgsf != '无')) {
                $("input[name='xqsfmgsf'][value='1']").prop("checked", true);
                if (rowData.other != "" && rowData.other != null) {
                    $('#xq_specialIdentity').val(rowData.other);
                } else {
                    $('#xq_specialIdentity').val(rowData.mgsf);
                }
            }else{
                $("input[name='xqsfmgsf'][value='0']").prop("checked", true);
            }

            if (rowData.sfsjyqldaj == 1) {
                $("input[name='xqsfsjyqldaj'][value='1']").prop("checked", true);
            }else{
                $("input[name='xqsfsjyqldaj'][value='0']").prop("checked", true);
            }
            if (rowData.j3gysfyjjcgqk != "无" && rowData.j3gysfyjjcgqk !=null) {
                $('#xqj3gysfyjjcgqk').val(rowData.j3gysfyjjcgqk);
                $("input[name='xqj3gysfyjjcgqk'][value='1']").prop("checked", true)
            }else{
                $("input[name='xqj3gysfyjjcgqk'][value='0']").prop("checked", true)
                $('#xqj3gysfyjjcgqk').val(rowData.j3gysfyjjcgqk);
            }
            if (rowData.sfymjjcs == 1) {
                $("input[name='xqsfymjjcs'][value='1']").prop("checked", true)
            }else{
                $("input[name='xqsfymjjcs'][value='0']").prop("checked", true)
            }
            if (rowData.shzt == 0) {
                //0未审核1已同意2未同意
                $('#shzt').val("未审核");
            } else if (rowData.shzt == 1) {
                $('#shzt').val("审核已同意");
            } else if (rowData.shzt == 2) {
                $('#shzt').val("审核未通过");
            }
            $('#shjg').val(rowData.shjg);
        }
    });
}

function orderYsh() {
    $.messager.alert('提示', '此嫌疑人已审核!');
}

function yjOrderSh() {

    showDialog('#shlist_dialog', '预约审核列表');
    $('#datagrid_orderstatus_person1').datagrid({
        // title : "预约审核列表",
        iconCls: 'icon-datagrid',
        // region : 'center',
        width: '100%',
        height: '90%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: 'Loading...',
        method: 'get',
        remoteSort: false,
        queryParams: {
            orderRequestId: orderRequestDataId
        },
        url: 'OrderStatusPersonlist.do',
        singleSelect: false,
        frozenColumns: [[{field: 'ck', checkbox: true},
            {
                title: 'id',
                field: 'id',
                width: 10,
                sortable: true,
                hidden: true
            }]],
        // frozenColumns: [[
        //     {field: 'ck', checkbox: true},
        //     {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        // ]],
        columns: [[
            {
                field: 'name',
                title: '姓名',
                width: 25
            },
            {
                field: 'certificateNo',
                title: '证件编号',
                width: 45
            },
            {
                field: 'shzt',
                title: '审核状态',
                width: 45,
                formatter: function (value) {

                    if (value == '1') {
                        return '审核已同意';
                    } else if (value == '2') {
                        return '审核未通过';
                    } else if (value == '0') {
                        return '未审核';
                    }
                }
            },
            {
                field: 'shjg',
                title: '审核结果',
                width: 45
            },
            {
                field: 'id',
                title: '操作',
                width: 35,
                align: 'center',
                formatter: function (value, row, index) {
                    var s = '';

                    if (row.type == '2' && row.shzt == '0') {
                        s = '<span class="spanRow"><a href="#" class="gridarrive" style="color:aquamarine" name="nobutton"  onclick="orderSh('
                            + index
                            + ')">审核</a></span>';
                    }
                    if (row.type == '2' && row.shzt != '0') {
                        s = '<span class="spanRow"><a href="#" class="gridarrive" style="color: #dae3e0" name="nobutton"  onclick="orderYsh('
                            + index
                            + ')">审核</a></span>';
                    }
                    return s;
                }
            }]],
        pagination: false,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        // 当数据加载成功时触发
        onLoadSuccess: function () {
        },
    });
}

function yjSaveSh() {
    var rows = $('#datagrid_orderstatus_person1').datagrid('getSelections');
    console.log(rows)
    var params = [];
    $.each(rows, function (index, item) {
        params.push(item.id + '@' + item.orderRequestId + '@' + item.orderUserId);// id@order_id
    });
    ids = params.join(",");
    showDialog('#sh_dialog', '手机预约审核');
}

function orderSh(index) {
    var rowData = $('#datagrid_orderstatus_person').datagrid('getRows')[index];
    $('#orderPersonId').val(rowData.id);
    $('#orderId').val(rowData.orderRequestId);
    $('#orderUserId1').val(rowData.orderUserId);
    showDialog('#sh_dialog', '手机预约审核');
}

function saveOrdderSh() {
    if (ids != null && ids != "") {
        $.messager.confirm('Confirm', '确定审核，审核后将无法修改！', function (r) {
            if (r) {
                if ($("input[name='shzt']:checked").val() == 2) {
                    //不同意，弹框，填写不同意原因
                    $('#btyshjg').textbox('setValue', "");
                    showDialog('#shjg_dialog', '不同意审核理由');
                } else {
                    //更新shzt状态
                    $.messager.progress({
                        title: '请等待',
                        msg: '正在保存数据...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'orderYjSh.do?ids=' + ids + "&shzt=" + $("input[name='shzt']:checked").val() + "&shjg=" + $('#shjg').val(),
                        dataType: 'json',
                        success: function (data) {
                            console.log(data);
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
                            if (rowData) {
                                $('#datagrid_orderstatus_person1').datagrid('load', {
                                    orderRequestId: rowData.id,
                                    trefresh: new Date().getTime()
                                });
                            }
                            $('#sh_dialog').dialog('close');
                            $.messager.progress('close');
                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '审核失败!');
                        }
                    });
                }

            }
        });
    } else {
        $.messager.confirm('Confirm', '确定审核，审核后将无法修改！', function (r) {
            if (r) {
                if ($("input[name='shzt']:checked").val() == 2) {
                    //不同意，弹框，填写不同意原因
                    $('#btyshjg').textbox('setValue', "");
                    showDialog('#shjg_dialog', '不同意审核理由');
                } else {
                    //更新shzt状态
                    $.messager.progress({
                        title: '请等待',
                        msg: '正在保存数据...'
                    });
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'orderSh.do?orderPersonId=' + $('#orderPersonId').val() + "&shzt=" + $("input[name='shzt']:checked").val() + "&orderUserId=" + $('#orderUserId1').val() + "&shjg=" + $('#shjg').val() + "&orderRequestId=" + $('#orderId').val(),
                        dataType: 'json',
                        success: function (data) {
                            console.log(data);
                            $('#sh_dialog').dialog('close');
                            $.messager.progress('close');
                            $.messager.show({
                                title: '提示',
                                msg: data.content,
                                timeout: 3000
                            });
                            var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
                            if (rowData) {
                                $('#datagrid_orderstatus_person').datagrid('load', {
                                    orderRequestId: rowData.id,
                                    trefresh: new Date().getTime()
                                });
                            }
                            var rowData1 = $('#datagrid_orderstatus_person1').datagrid('getSelected');
                            if (rowData1) {
                                $('#datagrid_orderstatus_person1').datagrid('load', {
                                    orderRequestId: rowData1.id,
                                    trefresh: new Date().getTime()
                                });
                            }
                            loadperson(rowData.id);

                        },
                        error: function (data) {
                            $.messager.progress('close');
                            $.messager.alert('错误', '审核失败!');
                        }
                    });
                }

            }
        });
    }

}

function saveBtyOrdderSh() {
    if (ids != null && ids != "") {
        $.messager.progress({
            title: '请等待',
            msg: '正在保存数据...'
        });
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'orderYjSh.do?ids=' + ids + "&shzt=" + $("input[name='shzt']:checked").val() + "&shjg=" + $('#btyshjg').val(),
            dataType: 'json',
            success: function (data) {
                console.log(data);
                $.messager.show({
                    title: '提示',
                    msg: data.content,
                    timeout: 3000
                });
                var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
                if (rowData) {
                    $('#datagrid_orderstatus_person1').datagrid('load', {
                        orderRequestId: rowData.id,
                        trefresh: new Date().getTime()
                    });
                }
                $('#sh_dialog').dialog('close');
                $('#shjg_dialog').dialog('close');
                $.messager.progress('close');
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert('错误', '审核失败!');
            }
        });
    } else {
        $.messager.progress({
            title: '请等待',
            msg: '正在保存数据...'
        });
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'orderSh.do?orderPersonId=' + $('#orderPersonId').val() + "&shzt=" + $("input[name='shzt']:checked").val() + "&orderUserId=" + $('#orderUserId1').val() + "&shjg=" + $('#btyshjg').val() + "&orderRequestId=" + $('#orderId').val(),
            dataType: 'json',
            success: function (data) {
                console.log(data);
                $.messager.show({
                    title: '提示',
                    msg: data.content,
                    timeout: 3000
                });
                var rowData = $('#datagrid_orderrequest').datagrid('getSelected');
                if (rowData) {
                    $('#datagrid_orderstatus_person').datagrid('load', {
                        orderRequestId: rowData.id,
                        trefresh: new Date().getTime()
                    });
                }
                loadperson(rowData.id);
                $('#sh_dialog').dialog('close');
                $('#shjg_dialog').dialog('close');
                $.messager.progress('close');
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert('错误', '审核失败!');
            }
        });
    }

}

//弹框界面的刷新
function secondPersonRefesh() {
    var id = $('#orderRequestId').val();
    loadperson(id);
}

//获取弹框界面的personList
function loadperson(id) {
    var requestId = id;
    $('#form_orderrequest_personList').datagrid(
        {
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            // idField:'code',
            queryParams: {
                orderRequestId: requestId,
                trefresh: new Date().getTime()
            },
            url: 'OrderStatusPersonlist.do',
            singleSelect: true,
            frozenColumns: [[
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'personType', hidden: true},
                {field: 'isJuvenile', hidden: true},
                {field: 'isGravida', hidden: true},
                {field: 'isGravidaProve', hidden: true},
                {field: 'gravidaMonth', hidden: true},
                {field: 'isDisease', hidden: true},
                {field: 'isDiseaseProve', hidden: true},
                {field: 'isGuardian', hidden: true},
                {field: 'other', hidden: true},
                {field: 'personNation', hidden: true},
                {field: 'personCountry', hidden: true},
                {field: 'jkb', hidden: true},
                {field: 'sfcrgfxdq', hidden: true},
                {field: 'jzyms', hidden: true},
                {field: 'xyrtw', hidden: true},
                {field: 'zjdz', hidden: true},
                {field: 'zhdz', hidden: true},
                {field: 'sfsjyqldaj', hidden: true},
                {field: 'zjfjrq', hidden: true},
                {field: 'j3gysfyjjcgqk', hidden: true},
                {field: 'sfymjjcs', hidden: true},
                {field: 'personCountry', hidden: true},
                {field: 'certificateTypeId', hidden: true},
                {
                    field: 'name',
                    title: '姓名',
                    width: 30

                },
                {
                    field: 'sex',
                    title: '性别',
                    formatter: function (value, rec) {
                        if (value == 1) {
                            return "男";
                        }
                        if (value == 2) {
                            return "女";
                        }
                        if (value == 0) {
                            return "未知的性别";
                        }
                        if (value == 9) {
                            return "未说明的性别";
                        }
                        return "";
                    },
                    width: 20

                },
                {
                    field: 'certificateNo',
                    title: '证件编号',
                    width: 50

                },
                {
                    field: 'id',
                    title: '操作',
                    width: 50,
                    align: 'left',
                    formatter: function (value, row, index) {
                        return '<span class="spanRow"><a href="#" class="gridedit" onclick="orderRequestPersonOtherEdit('
                            + row.id
                            + ','
                            + index
                            + ')">修改</a></span><span class="spanRow"><a href="#" class="griddel" onclick="orderRequestPersonCancel('
                            + index + ')">删除</a></span>';
                    }
                }]],
            pagination: false,
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: false,
            // 当数据加载成功时触发
            onLoadSuccess: function () {

            }
        });
}
