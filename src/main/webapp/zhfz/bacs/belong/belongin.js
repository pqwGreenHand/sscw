var areaId;
$(function () {
    ssareaid = $('#ssareaid').val();
    $.messager.progress({
        title: '请等待',
        msg: '正在加载控件...'
    });

    // var printEplObject = '<object id="print_epl" style="display: none; visibility: hidden"	width="51" height="21" codebase="EPL.ocx#version=1,0,0,0"classid="CLSID:64CBDF17-E597-4309-A586-4BB8051452AB" visible="false"></object>';
    // var cuffInfoObject = '<object classid="CLSID:05782014-9FF7-468C-BE96-8EDC73084202" id="IcCardReader" codebase="IcCardReader.ocx#version=1,0,0,0" viewastext width="0" height="0" ></object>';
    // var objects = cuffInfoObject + printEplObject;
    // document.getElementById("objects").innerHTML = objects;
    var printObject = '<object id="PSKPrn"  classid="clsid:81C07687-3353-4ABA-B108-94BCE81E5CBA"  codebase="/PSKPrn.ocx#version=2,0,0,1"  width="0" height="0"></object>';
    var objects = printObject;
    document.getElementById("objects").innerHTML = objects;
    $.messager.progress('close');

    $('#wutm').textbox("textbox").keydown(function (event) {
        if (event.keyCode == 13) {
            querWpByUUID()
        }
    });

    $.get("../../../common/getSessionInfo.do", function (data) {
        var sessionObj = eval('(' + data + ')');
        areaId = sessionObj.currentArea.id
    });

    // $(document).keydown(function (event) {
    //     if (event.keyCode == 13) {
    //         var data = checkRingNo($(event.target).val(), 0);
    //         if (data.callbackData) {
    //             readRingNo(data.callbackData.cuffNo);
    //         } else {
    //             $.messager.show({
    //                 title: '提示',
    //                 msg: "无效的手环！！",
    //                 timeout: 3000
    //             });
    //             $(event.target).val('');
    //         }
    //     }
    // });
    $('#names1').combobox({
        onChange: function (n, o) {
            var str = "";
            if (n == "手机") {
                str = "部";
            } else if (n == "人民币") {
                str = "张";
            } else if (n == "银行卡") {
                str = "张";
            } else if (n == "钥匙") {
                str = "串";
            } else if (n == "身份证") {
                str = "张";
            } else if (n == "钱包") {
                str = "个";
            } else if (n == "打火机") {
                str = "个";
            } else if (n == "香烟") {
                str = "盒";
            } else if (n == "手表") {
                str = "个";
            } else if (n == "项链") {
                str = "串";
            } else if (n == "戒指") {
                str = "个";
            } else if (n == "耳环") {
                str = "个";
            } else {
                str = "";
            }
            $('#unit').textbox('setValue', str);
        }
    });
});
$(function () {
    //嫌疑人随身储物
    $('#detid').datagrid({
        title: '存储随身物品',
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
        queryParams: {trefresh: new Date().getTime()},
        url: 'listAllBelongdet2.do',
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {title: 'id', field: 'detail_id', width: 80, sortable: true, hidden: true},
            {title: 'belongingsId', field: 'belongingsId', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'personName', title: '嫌疑人名称', width: 30},
            {
                field: 'lockerId', title: '储物柜编号', width: 60,
                formatter: function (field, rec, index) {
                    return rec.cabinetGroup + " " + rec.cabinetNo + "号柜";
                }
            },
            {field: 'name', title: '物品名称', width: 50},
            {field: 'detailCount', title: '数量', width: 20},
            {field: 'unit', title: '单位', width: 40},
            {field: 'saveMethod', title: '保管措施', width: 80},
            {field: 'description', title: '特征', width: 140},
            {
                field: 'id', title: '操作', width: 80,
                formatter: function (field, rec, index) {
                    if (isGridButton("sdetEdit")) {
                        var f = '<span class="spanRow"><a id="sdetEdit" href="#" class="gridedit" onclick="detEdit(' + index + ')">修改</a></span>';
                    } else {
                        var f = '';
                    }
                    if (isGridButton("sdetRemove")) {
                        var e = '<span class="spanRow"><a id="sdetRemove" href="#" class="griddel" onclick="detRemove(' + index + ')">删除</a></span>';
                    } else {
                        var e = '';
                    }
                    return f + e;
                }
            }
        ]],
        rownumbers: true,
        toolbar: '#areaToolbar',
        onLoadSuccess: function (data) {
            if (null != data && data.rows.length > 0) {
                var lockerId = data.rows[0].lockerId;
                $('#lockerId').combobox('setValue', lockerId);
            } else {
                var s = $('#id1').val();
                if (s == null || s == "") {
                    $('#lockerId').combobox('setValue', '');
                }
            }
        }
    });
    var p = $('#detid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            $('#detid').datagrid('reload', {
                enpId: row.id,
                areaId: ssareaid,
                trefresh: new Date().getTime()
            });
            return false;
        }
    });
    var lockerId_ = getUrlParam('s_lockerId');
    var lockerId_ulr = "listBelongLockerBox.do?areaId=" + $('#ssareaid').val() + "&timeSign=" + getTimeSign();
    $('#lockerId').combobox({
        url: lockerId_ulr,
        valueField: 'id',
        textField: 'value'
    });
    if (lockerId_ != null) {
        $('#lockerId').combobox('setValue', lockerId_);
    }
    $('#jslockerId').combobox({
        url: lockerId_ulr,
        valueField: 'id',
        textField: 'value'
    });

    //嫌疑人下拉框,查询所有已经存物的嫌疑人
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getPersonBelong.do?areaId=' + ssareaid,
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'name', title: '姓名', width: 100},
            {field: 'certificateNo', title: '证件号码', width: 260}
            // {field: 'areaId', title: '办案区域id', hidden: true},
            // {field: 'caseId', title: '案件id', hidden: true},
            // {field: 'personId', title: '人员id', hidden: true}
        ]],
        onChange: function (data, date1) {
            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#personName').val(row.name);
                $('#serialId').val(row.id);
                $('#caseId').val(row.caseId);
                $('#areaId').val(ssareaid);
            } else {
                $('#personName').val("");
                $('#serialId').val("");
                $('#caseId').val("");
                $('#areaId').val("");
            }
        }
    });

    // //嫌疑人下拉框弹框
    // $('#serialId').combogrid({
    //     panelWidth: 360,
    //     mode: 'remote',
    //     url: '../../common/combogrid/getSuspectSerialNo.do?type=belongin',
    //     idField: 'id',
    //     textField: 'name',
    //     columns: [[
    //         {field: 'name', title: '姓名', width: 60},
    //         {field: 'certificateNo', title: '证件号码', width: 150},
    //         {field: 'policeName1', title: '主办民警', width: 70},
    //         {field: 'policeName2', title: '协办民警', width: 70},
    //         {field: 'serialNo', title: '入区编号', width: 135},
    //         {field: 'areaId', title: '办案区域id', hidden: true},
    //         {field: 'caseId', title: '案件id', hidden: true},
    //         {field: 'personId', title: '人员id', hidden: true}
    //     ]],
    //     onLoadSeccuss: function (data) {
    //         var serialIdQuery = $('#serialIdQuery').combogrid('getValue');
    //         if (serialIdQuery != null && serialIdQuery != '') {
    //             $("#serialId").combogrid('setValue', serialIdQuery);
    //         }
    //     },
    //     onChange: function (data, date1) {
    //         $('#lockerId').combobox('reload');
    //         var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    //         var row = cg.datagrid('getSelected');
    //         if (row != null) {
    //             $('#detid').datagrid('load', {
    //                 enpId: row.id, trefresh: new Date().getTime()
    //             });
    //             $('#serialIdQuery').combogrid('setValue', row.id);
    //             // 根据嫌疑人身份证号查询是否有移交的物品
    //             queryDetail(row.certificateNo);
    //         }
    //     }
    // });


    $('#jsserialId').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getSuspectSerialNo.do?type=belongin',
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'name', title: '姓名', width: 60},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {field: 'policeName1', title: '主办民警', width: 70},
            {field: 'policeName2', title: '协办民警', width: 70},
            {field: 'serialNo', title: '入区编号', width: 135},
            {field: 'areaId', title: '办案区域id', hidden: true},
            {field: 'caseId', title: '案件id', hidden: true},
            {field: 'personId', title: '人员id', hidden: true}
        ]],
        onLoadSeccuss: function (data) {
            var serialIdQuery = $('#serialIdQuery').combogrid('getValue');
            if (serialIdQuery != null && serialIdQuery != '') {
                $("#jsserialId").combogrid('setValue', serialIdQuery);
            }
        },
        onChange: function (data, date1) {
            $('#jslockerId').combobox('reload');
            var cg = $('#jsserialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#serialIdQuery').combogrid('setValue', row.id);
            }
        },
        onSelect: function (data, date1) {
            $('#jslockerId').combobox('reload');
            var cg = $('#jsserialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#serialIdQuery').combogrid('setValue', row.id);
            }
        }
    });

});


function queryDetail(sno) {
    $('#qx_form').form('clear');
    jQuery.ajax({
        type: 'GET',
        url: 'queryBelongTempBySfzh.do?sfzh=' + sno,
        dataType: 'json',
        success: function (data) {
            if (data != null && data.length > 0) {
                $.messager.confirm('接收确认', '是否确定接收此数据？', function (r) {
                    if (r) {
                        jQuery.ajax({
                            type: 'GET',
                            url: 'queryBelongDtailById.do?tempId=' + data[0].id,
                            dataType: 'json',
                            success: function (res) {
                                if (res != null && res.length > 0) {
                                    console.log(res)
                                    for (var i = 0; i < res.length; i++) {
                                        $('#belonggrid').datagrid('appendRow', {
                                            name: res[i].name,
                                            description: res[i].description,
                                            detailCount: res[i].detailCount,
                                            unit: res[i].unit,
                                            wpUuid: res[i].wpUuid,
                                            tempId: res[i].tempId
                                        });
                                    }

                                }
                            },
                            error: function (data) {
                                $.messager.alert('错误', '失败（queryBelongDtailById）!');
                            }
                        });
                    } else {
                        showDialog('#qx_dialog', '编辑取消移交意见');
                        $('#wpid').val(data[0].id);
                    }

                });
            }
        },
        error: function (data) {
            $.messager.alert('错误', '移交失败（queryBelongTempBySfzh）!');
        }
    });
}

//编辑取消移交意见
function saveYjyj() {
    if (!checkForm('#qx_form')) {
        return;
    }
    var entForm = $('#qx_form');
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'updateTempYjyj.do',
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            // $.messager.alert(data.title, data.content);
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#qx_dialog').dialog('close');
            $("#inPopup").hide();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', '未知错误!' + data.content);
        }
    });
}

function querWpByUUID() {
    var wpUUID = $('#wutm').textbox('getValue');
    jQuery.ajax({
        type: 'GET',
        url: 'queryBelongDtailByUUID.do?wpUuid=' + wpUUID,
        dataType: 'json',
        success: function (res) {
            console.log(res)
            if (res != null && res.length > 0) {
                $('#belonggrid').datagrid('appendRow', {
                    name: res[0].name,
                    description: res[0].description,
                    detailCount: res[0].detailCount,
                    unit: res[0].unit,
                    wpUuid: res[0].wpUuid
                });
                $('#wutm').textbox('setValue', "");
            } else {
                $.messager.alert('提示', '无对应物品，请重新扫描！');
            }
        },
        error: function (data) {
            $.messager.alert('错误', '失败（queryBelongDtailByUUID）!');
        }
    });

}

//新增保存
function belongsave() {
    // var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    // var row = cg.datagrid('getSelected');
    // if (!row) {
    //     $.messager.alert('提示', '请先添加物品信息!');
    //     return;
    // }
    // $('#caseId').val(row.caseId);
    // $('#areaId').val(row.areaId);
    var data = $('#detinfo_form').serializeObject();
    data['serialId'] = $('#serialId').val();
    var lockerNo = $('#lockerId').combobox('getText');
    if (!lockerNo) {
        $.messager.alert('提示', '请选择储物柜编号!');
        return;
    }
    data['cabinetNoStr'] = lockerNo.indexOf("[") > 0 ? lockerNo.slice(0, lockerNo.indexOf("[")) : lockerNo;
    var rows = $("#belonggrid").datagrid("getRows");
    var a = {};
    a['list'] = rows;
    a['data'] = data;
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'belongsave.do',
        data: JSON.stringify(a),
        dataType: 'json',
        success: function (data) {
            if (data.error) {
                $.messager.alert('提示', data.content);
            } else {
                $.messager.progress({
                    title: '请等待',
                    msg: '添加存物信息中...'
                });
            }
            $.messager.progress('close');
            if (data.callbackData) {
                $('#serialId').val(data.callbackData)
                shuaxin();
                $('#serialIdQuery').combogrid('setValue', data.callbackData);
                $('#detid').datagrid('reload', {
                    enpId: data.callbackData,
                    areaId: ssareaid,
                    trefresh: new Date().getTime()
                });
            }
            $('#names1').prop('value', '');
            $('#detailCount').prop('value', '');
            $('#description').textbox('setValue', '');
            $("#belonggrid").datagrid('loadData', {total: 0, rows: []})
            $("#inPopup").hide();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '保存失败（belongsave）!');
        }
    });
}

//编辑随身物品详情
function detEdit(index) {
    if (isGridButton("sdetEdit")) {
        var rowData = $('#detid').datagrid('getRows')[index];
        showDialog('#det_dialog', '编辑随身物品详情');
        $('#det_form').form('clear');
        $('#det_form').form('load', rowData);
        url = 'editBelongdet.do?id=' + rowData.id;
    } else {
        $.messager.alert('提示', '权限不足，请更换账号或联系管理员!');
    }
}

//查看图片
function showImages() {
    $("#showPic_dialog").html("");
    var rowData = $('#detid').datagrid('getRows');
    if (!rowData || rowData.length == 0) {
        $.messager.alert('提示', '请选择嫌疑人！');
        return;
    }
    var belongingsId = rowData[0].belongingsId;
    $.messager.progress({
        title: '请等待',
        msg: '加载数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: 'getImages.do',
        data: {belongingsId: belongingsId},
        dataType: 'json',
        success: function (data) {
            console.log(data)
            $.messager.progress('close');
            for (var i = 0; i < data.length; i++) {
                // var url = fileUtils.url('ba', 'WP', data[i].id, data[i].areaId, data[i].url);
                // var url = contextPath + '/zhfz/bacs/iriscollection/imageshow.do?path=' + encodeURI(data[i].url) + "&timeSign=" + getTimeSign();
                var url = encodeURI(data[i]);
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

//编辑随身物品详情保存
function savedet() {
    if (!checkForm('#det_form')) {
        return;
    }
    var entForm = $('#det_form');
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            // $.messager.alert(data.title, data.content);
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            $('#detid').datagrid('reload', {enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()});
            $('#det_dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', '未知错误!' + data.content);
        }
    });
}

//删除随身物品详情
function detRemove(index) {
    if (isGridButton("sdetRemove")) {
        $.messager.confirm('删除确认', '是否确定删除此数据？', function (r) {
            var rowData = $('#detid').datagrid('getRows')[index];
            var belongingsId = rowData.belongingsId;
            var detailId = rowData.id;
            if (r) {
                jQuery.ajax({
                    type: 'POST',
                    url: 'removeBelongdet.do?timeSign=' + getTimeSign(),
                    data: {belongingsId: belongingsId, detailId: detailId},
                    dataType: 'json',
                    success: function (data) {
                        $.messager.alert(data.title, data.content);
                        var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
                        var row = cg.datagrid('getSelected');
                        $('#detid').datagrid('reload', {
                            enpId: row.id,
                            areaId: ssareaid,
                            trefresh: new Date().getTime()
                        });// reload the data
                        $.messager.progress('close');
                    },
                    error: function (data) {
                        $.messager.alert('错误', '删除失败（removeBelongdet）!');
                    }
                });

            }
        });
    } else {
        $.messager.alert('提示', '权限不足，请更换账号或联系管理员!');
    }
}

function areaSearch() {
    $('#belongid').datagrid('load', {
        lockerId_t: $('#s_lockerId').val(),
        trefresh: new Date().getTime()
    });
}

function areadoClear() {
    $('#names1').prop('value', '');
    $('#detailCount').prop('value', '');
    $('#description').textbox('setValue', '');
}

//台账预览
function securityPrint(index) {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
        return;
    }
    var enterpriseinfo = {
        "id": null,
        "serialId": row.id
    };
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"] = 9;
    lawdocInfo["name"] = "随身物品登记";
    lawdocInfo["type"] = 1;
    lawdocInfo["userId"] = 0;
    lawdocInfo["dataId"] = row.id;
    lawdocInfo["serialNo"] = row.serialNo;
    lawdocInfo["serialId"] = row.id;
    $('#number').val(9);
    $('#name').val('随身物品登记');
    $('#type').val(1);
    $('#userId').val(0);
    $('#dataId').val(row.id);
    $('#serialNo').val(row.serialNo);
    $('#serialId').val(row.id);
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    // document.getElementById("lawdocInfo_belonginfo").submit();// ********
    fileUtils.read("/zhfz/lawdocProcess/download.do?number=9&name=随身物品登记&type=1&userId=0&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id);
    $.messager.progress('close');
}

//台账下载
function securityDownLoad(index) {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    var belongId = null;
    var personId = null;
    var rowdata = $('#detid').datagrid('getRows')[0];
    if (row == null && rowdata == null) {
        $.messager.alert('提示', '请选择嫌疑人!');
        return;
    } else {
        if (row != null) {
            belongId = row.belongingsId
            personId = row.id
        } else {
            belongId = rowdata.belongingsId
            personId = rowdata.serialId
        }
    }
    $.messager.progress({
        title: '请等待',
        msg: '打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"] = 9;
    lawdocInfo["name"] = "随身物品登记";
    lawdocInfo["type"] = 1;
    lawdocInfo["userId"] = personId;
    lawdocInfo["dataId"] = belongId;
    $('#number').val(9);
    $('#name').val('随身物品登记');
    $('#type').val(1);
    $('#userId').val(personId);
    $('#dataId').val(belongId);
    // var lawdocInfojson = JSON.stringify(lawdocInfo);
    document.getElementById("lawdocInfo_belonginfo").submit();// ********
    // fileUtils.read("/zhfz/lawdocProcess/download.do?number=9&name=随身物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
    $.messager.progress('close');
}

//打开拍照
function showphotowid() {
    var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (row == null) {
        $.messager.alert('提示', '请选择嫌疑人!');
    } else {
        var shu = $('#detid').datagrid('getRows').length;
        if (shu == 0) {
            $.messager.alert('提示', '请先存物!');
        } else {
            var height = 500;
            var width = 900;
            var screenParam = "left=0,top=0,scrollbars,resizable=yes,toolbar=no',height=" + height + ",width=" + width;
            var winOpen = window.open("about:blank", "", screenParam);
            if (areaId > 49 ||areaId==1 ||areaId==45) {
                winOpen.location = "../common/photo.jsp?type=1&serialid=" + row.id + "&serialNo=" + row.belongingsId;//type=1 随身拍照
            } else {
                winOpen.location = "../common/demo/photo.jsp?type=1&serialid=" + row.id + "&serialNo=" + row.belongingsId;//type=1 随身拍照
            }
        }
    }
}

//开柜
function belongsavebox() {
    /* var cuffNumber=$('#cuffNumber').textbox('getValue');
     var ss=$('#lockerId').combobox('getValue');
     if(ss==''||ss==null){
         $.messager.alert('提示', '请扫描手环或选择嫌疑人！');
         return false;
     }*/
    //取第一条
    var ss = "";
    var row = $('#detid').datagrid('getRows')[0];
    if (row) {
        ss = row.lockerId;
    }
    jQuery.ajax({
        type: 'GET',
        url: 'belongsavebox.do?lockid=' + ss,
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

//手环扫描
function readRingNo(number) {
    if (number) {
        var cuff = $('#cuffInfo').serializeObject();
        cuff["cuffNo"] = number;
        cuff["type"] = 0;
        var jsonrtinfo = JSON.stringify(cuff);
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: '../serial/getSerialByCuffNo.do',
            data: jsonrtinfo,
            dataType: 'json',
            success: function (data) {
                var id = data.callbackData ? data.callbackData.id : null;
                if (id != null && id != "") {
                    $('#cuffNumber').textbox('setValue', data.callbackData.cuffNo);
                    $('#serialId').combogrid('setValue', id);
                    $('#cuffNumberQuery').textbox('setValue', data.callbackData.cuffNo);
                    $('#serialIdQuery').combogrid('setValue', id);
                    $.ajax({
                        async: false,
                        type: 'POST',
                        url: 'getCurrentStatus.do',
                        dataType: 'json',
                        data: {
                            serialId: id
                        },
                        success: function (dataresult) {
                            if (dataresult.content == "已安检") {
                                $('#cuffNumberQuery').textbox('setValue', '');
                            }
                            else {
                                if (dataresult.content == "未安检") {
                                    $.messager.alert('警告', '请先录入嫌疑人的人身安全检查信息!');
                                    $('#cuffNumber').textbox('setValue', '');
                                    $('#cuffNumberQuery').textbox('setValue', '');
                                }
                            }
                        },
                        error: function () {
                            $.messager.alert('警告', '请先录入嫌疑人的人身安全检查信息!');
                            $('#cuffNumber').textbox('setValue', '');
                            $('#cuffNumberQuery').textbox('setValue', '');
                        }
                    });
                } else {
                    $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                    $('#cuffNumber').textbox('setValue', '');
                    $('#cuffNumberQuery').textbox('setValue', '');
                }
            },
            error: function (data) {
                $.messager.alert('错误', '未知错误!');
                $('#cuffNumber').textbox('setValue', '');
                $('#cuffNumberQuery').textbox('setValue', '');
            }
        });
    } else {
        $.messager.alert('提示', '读卡失败!');
        $('#cuffNumber').textbox('setValue', '');
        $('#cuffNumberQuery').textbox('setValue', '');
    }
}

function formClear() {
    $('#cuffNumber').textbox('setValue', '');
    $('#serialId').combogrid('setValue', '');
    $('#names1').combobox('setValue', '');
    $('#detailCount').textbox('setValue', '');
    $('#unit').combobox('setValue', '');
    $('#description').textbox('setValue', '');
    $('#belonggrid').datagrid('loadData', {total: 0, rows: []});

}

function belongJsAddList() {
    $("#jsPopup").show();
    //$('#detinfo_form').form('clear');
    $('#jsdetinfo_form').form('clear');
    // $('#belongDetail_form').form('clear');
    // $("#belongDetail_form").html("");
    $('#djswp').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        title: "待接收物品",
        nowrap: false,
        url: 'listAllBelongdet2.do',
        striped: true,
        collapsible: false,
        loadMsg: 'Loading...',
        method: 'get',
        remoteSort: false,
        queryParams: {
            enpId: "29347",
            trefresh: new Date().getTime()
        },
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
                title: '物品名称',
                width: '15%'
            },
            {field: 'detailCount', title: '数量', width: '10%'},
            {field: 'unit', title: '单位', width: '10%'},
            {
                field: 'description',
                title: '特征',
                width: '45%'
            }]],
        pagination: false,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        // 当数据加载成功时触发
        onLoadSuccess: function () {
        },
        onSelect: function (index, row) {
            // console.log(row)
            // queryDetail(row.id);
        }
    });
    $('#jsbelonggrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        title: "物品详情",
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: 'Loading...',
        method: 'get',
        remoteSort: false,
        queryParams: {
            trefresh: new Date().getTime()
        },
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
                title: '物品名称',
                width: '15%'
            },
            {field: 'detailCount', title: '数量', width: '10%'},
            {field: 'unit', title: '单位', width: '10%'},
            {
                field: 'description',
                title: '特征',
                width: '45%'
            },
            {
                field: 'id',
                title: '操作',
                width: '20%',
                align: 'left',
                formatter: function (value, row, index) {
                    return '<span class="spanRow"><a href="#" class="griddel" onclick="deleteRow(this)">删除</a></span>';
                }
            }]],
        pagination: false,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        // 当数据加载成功时触发
        onLoadSuccess: function () {
        }
    });
    formClear();
    $('#jscuffNumber').textbox('textbox').focus();
}

function selectPerson(){
    showDialog('#personDialog', '人员选择');
    $('#personInfo')
        .datagrid({
            title: '人员信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: 'queryPerson.do',
            queryParams: {
                trefresh: new Date().getTime()
            },
            singleSelect: true,
            frozenColumns: [[
                // {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'name', title: '姓名', width: 20},
                {
                    field: 'sex', title: '性别', width: 20,
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
                {field: 'certificateTypeName', title: '证件类型', width: 30},
                {field: 'certificateNo', title: '证件号码', width: 50}
            ]],
            pagination: true,
            pageList: [10, 20, 40, 50, 100],
            rownumbers: true,
        });
}



function savePersonInfo() {
    var personInfo = $('#personInfo').datagrid('getSelected');
    if (personInfo != null) {
        $('#personName').val(personInfo.name);
        // $('#serialIdQuery').combogrid('setValue',personInfo.id);
        $('#serialId').val(personInfo.id);
        $('#caseId').val(personInfo.caseId);
        $('#areaId').val(ssareaid);
        $('#lockerId').combobox('reload');
        $('#detid').datagrid('load', {
            enpId: personInfo.id, areaId: ssareaid, trefresh: new Date().getTime()
        });
        $.messager.show({
            title: '提示',
            msg: "关联人员数据成功",
            timeout: 3000
        });
        $('#personDialog').dialog('close');
    }
    else {
        $.messager.show({
            title: '提示',
            msg: "请先选择嫌疑人数据",
            timeout: 3000
        });
    }
}

function doSearchPersonInfo() {
    var name = $('#xm').val();
    var certificateNo = $('#certificateNo').val();
    $('#personInfo').datagrid('load', {name: name, certificateNo: certificateNo, trefresh: new Date().getTime()});
}
function doClearPersonInfo() {
    $('#xm').val("");
    $('#certificateNo').val("");
}

//选择嫌疑人弹框
function selectAjxxAndPerson() {
    showDialog('#ajxxAndPersonDialog', '案件人员选择');
    loadPerson("-999999")
    $('#jzInfo').datagrid({
//		title:'人身安全检查',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: 'queryCase.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        columns: [[
            {field: 'ajmc', title: '案件名称', width: 180},
            {field: 'ajbh', title: '案件编号', width: 120},
            {
                field: 'ajlx', title: '案件类型', width: 60,
                formatter: function (value, rec) {
                    if (value == 1) {
                        return "行政";
                    }
                    if (value == 2) {
                        return "刑事";
                    }
                    return "";
                }
            },
            {field: 'abmc', title: '案别', width: 60},
            {field: 'zbdwmc', title: '主办单位', width: 120},
            {field: 'zbmjName', title: '主办民警', width: 120},
            {
                field: 'afsj', title: '案发时间', width: 60,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {field: 'afdd', title: '案发地址', width: 140}
        ]],
        pagination: true,
        pageList: [10, 20, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar_jzInfo',
        onSelect: function (rowIndex, rowData) {
            loadPerson(rowData.ajbh);
        }
    });
}

function selectZfbaAjxxAndPerson() {
    showDialog('#ajxxAndPersonDialogZfba', '案件人员选择');
    loadZfbaCase();
    loadZfbaPerson("-999999");
    loadZfbaBelong("-999999");
}

function loadZfbaCase() {
    $('#zfbaAjxxTable').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: 'queryZfbaCase.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        columns: [[
            {field: 'ajmc', title: '案件名称', width: 180},
            {field: 'ajbh', title: '案件编号', width: 120},
            {
                field: 'afsj', title: '案发时间', width: 60,
                formatter: function (value, rec) {
                    return value;
                }
            },
            {field: 'afdd', title: '案发地址', width: 140}
        ]],
        pagination: true,
        pageList: [10, 20, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar_zfba',
        onSelect: function (rowIndex, rowData) {
            loadZfbaPerson(rowData.ajbh);
            loadZfbaBelong(rowData.ajbh);
        }
    });
}

function loadZfbaPerson(ajbh) {
    $('#zfbaRyxxTable')
        .datagrid({
            title: '嫌疑人信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: 'queryZfbaPerson.do',
            queryParams: {
                ajbh: ajbh,
                trefresh: new Date().getTime()
            },
            singleSelect: true,
            frozenColumns: [[
                // {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'name', title: '姓名', width: 20},
                {
                    field: 'sex', title: '性别', width: 20,
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
                {field: 'certificateTypeName', title: '证件类型', width: 30},
                {field: 'certificateNo', title: '证件号码', width: 50}
            ]],
            pagination: false,
            pageList: [10, 20, 40, 50, 100],
            rownumbers: true,
        });
}

function loadZfbaBelong(ajbh) {
    $('#zfbaWpxxTable')
        .datagrid({
            title: '物品信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: 'queryZfbaBelong.do',
            queryParams: {
                ajbh: ajbh,
                trefresh: new Date().getTime()
            },
            singleSelect: false,
            frozenColumns: [[
                // {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'name', title: '物品名称', width: 30},
                {field: 'detail_count', title: '物品数量', width: 20},
                {field: 'description', title: '描述', width: 50}
            ]],
            pagination: false,
            pageList: [10, 20, 40, 50, 100],
            rownumbers: true
        });
}

function selectBazxAjxxAndPerson() {
    showDialog('#ajxxAndPersonDialogBazx', '案件人员选择');
    loadBazxCase();
    loadBazxPerson("-999999");
    loadBazxBelong("-999999");
}

function loadBazxCase() {
    $('#bazxAjxxTable').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: 'queryBazxCase.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        columns: [[
            {field: 'ajmc', title: '案件名称', width: 180},
            {field: 'ajbh', title: '案件编号', width: 120},
            {
                field: 'ajlx', title: '案件类型', width: 60,
                formatter: function (value, rec) {
                    if (value == 1) {
                        return "行政";
                    }
                    if (value == 2) {
                        return "刑事";
                    }
                    return "";
                }
            },
            {field: 'abmc', title: '案别', width: 60},
            {
                field: 'afsj', title: '案发时间', width: 60,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {field: 'afdd', title: '案发地址', width: 140}
        ]],
        pagination: true,
        pageList: [10, 20, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar_bazx',
        onSelect: function (rowIndex, rowData) {
            loadBazxPerson(rowData.ajbh);
        }
    });
}

function loadBazxPerson(ajbh) {
    $('#bazxRyxxTable')
        .datagrid({
            title: '嫌疑人信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: 'queryBazxPerson.do',
            queryParams: {
                ajbh: ajbh,
                trefresh: new Date().getTime()
            },
            singleSelect: true,
            frozenColumns: [[
                // {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'name', title: '姓名', width: 20},
                {
                    field: 'sex', title: '性别', width: 20,
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
                {field: 'certificateTypeName', title: '证件类型', width: 30},
                {field: 'certificateNo', title: '证件号码', width: 50}
            ]],
            pagination: false,
            pageList: [10, 20, 40, 50, 100],
            rownumbers: true,
            onSelect: function (rowIndex, rowData) {
                loadBazxBelong(rowData.id);
            }
        });
}

function loadBazxBelong(serialId) {
    $('#bazxWpxxTable')
        .datagrid({
            title: '物品信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: 'queryBazxBelong.do',
            queryParams: {
                serialId: serialId,
                trefresh: new Date().getTime()
            },
            singleSelect: false,
            frozenColumns: [[
                // {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'name', title: '物品名称', width: 20},
                {field: 'detail_count', title: '物品数量', width: 20},
                {field: 'unit', title: '物品单位', width: 20},
                {field: 'description', title: '描述', width: 50}
            ]],
            pagination: false,
            pageList: [10, 20, 40, 50, 100],
            rownumbers: true
        });
}

function loadPerson(ajbh) {
    $('#jzPerson')
        .datagrid({
            title: '嫌疑人信息',
            iconCls: 'icon-datagrid',
            region: 'east',
            width: '100%',
            height: '100%',
            fitColumns: true,
            nowrap: false,
            striped: true,
            collapsible: false,
            loadMsg: 'Loading...',
            method: 'get',
            remoteSort: false,
            url: 'queryPerson.do',
            queryParams: {
                ajbh: ajbh,
                trefresh: new Date().getTime()
            },
            singleSelect: true,
            frozenColumns: [[
                // {field: 'id', checkbox: true},
                {
                    title: 'id',
                    field: 'id',
                    width: 10,
                    sortable: true,
                    hidden: true
                }]],
            columns: [[
                {field: 'name', title: '姓名', width: 20},
                {
                    field: 'sex', title: '性别', width: 20,
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
                {field: 'certificateTypeName', title: '证件类型', width: 30},
                {field: 'certificateNo', title: '证件号码', width: 50}
            ]],
            pagination: true,
            pageList: [10, 20, 40, 50, 100],
            rownumbers: true,
        });
}

function doSearchInfo() {
    var ajmc = $('#ajmc').val();
    var ajbh = $('#ajbh').val();
    $('#jzInfo').datagrid('load', {ajmc: ajmc, ajbh: ajbh, trefresh: new Date().getTime()});
    loadPerson("-999999")
}

function doSearchBazxInfo() {
    var ajmc = $('#ajmcBazx').val();
    var ajbh = $('#ajbhBazx').val();
    $('#bazxAjxxTable').datagrid('load', {ajmc: ajmc, ajbh: ajbh, trefresh: new Date().getTime()});
    loadBazxPerson("-999999");
    loadBazxBelong("-999999");
}

function doSearchZfbaInfo() {
    var ajmc = $('#ajmcZfba').val();
    var ajbh = $('#ajbhZfba').val();
    $('#zfbaAjxxTable').datagrid('load', {ajmc: ajmc, ajbh: ajbh, trefresh: new Date().getTime()});
    loadZfbaPerson("-999999");
    loadZfbaBelong("-999999");
}

function doClearInfo() {
    $('#ajmc').val("");
    $('#ajbh').val("");
}

function doClearBazxInfo() {
    $('#ajmcBazx').val("");
    $('#ajbhBazx').val("");
}

function doClearZfbaInfo() {
    $('#ajmcZfba').val("");
    $('#ajbhZfba').val("");
}

function saveInfo() {
    var personInfo = $('#jzPerson').datagrid('getSelected');
    if (personInfo != null) {
        $('#personName').val(personInfo.name);
        // $('#serialIdQuery').combogrid('setValue',personInfo.id);
        $('#serialId').val(personInfo.id);
        $('#caseId').val(personInfo.caseId);
        $('#areaId').val(ssareaid);
        $('#lockerId').combobox('reload');
        $('#detid').datagrid('load', {
            enpId: personInfo.id, areaId: ssareaid, trefresh: new Date().getTime()
        });
        $.messager.show({
            title: '提示',
            msg: "关联执法办案平台数据成功",
            timeout: 3000
        });
        $('#ajxxAndPersonDialog').dialog('close');
    }
    else {
        $.messager.show({
            title: '提示',
            msg: "请先选择嫌疑人数据",
            timeout: 3000
        });
    }
}

function saveInfoBazx() {
    $('#xzType').val('bazx');
    $('#areaId').val(ssareaid);
    var ajxx = $('#bazxAjxxTable').datagrid('getSelected');
    var yyxx = $('#bazxRyxxTable').datagrid('getSelected');
    var wpxxArr = $('#bazxWpxxTable').datagrid('getSelections');
    if (ajxx) {
        $('#bazxCaseId').val(ajxx.id);
    }
    if (yyxx) {
        $('#bazxSerialId').val(yyxx.id);
        $('#personName').val(yyxx.name);
    }
    if (wpxxArr) {
        for (var i = 0; i < wpxxArr.length; i++) {
            var wpxx = wpxxArr[i]
            $('#belonggrid').datagrid('appendRow', {
                name: wpxx.name,
                description: wpxx.description,
                detailCount: wpxx.detail_count,
                unit: wpxx.unit
            });
        }
    }
    $('#ajxxAndPersonDialogBazx').dialog('close');
}

function saveInfoZfba() {
    $('#xzType').val('zfba');
    $('#areaId').val(ssareaid);
    var ajxx = $('#zfbaAjxxTable').datagrid('getSelected');
    var yyxx = $('#zfbaRyxxTable').datagrid('getSelected');
    var wpxxArr = $('#zfbaWpxxTable').datagrid('getSelections');
    if (ajxx) {
        $('#zfbaAjId').val(ajxx.id);
    }
    if (yyxx) {
        $('#zfbaYyId').val(yyxx.id);
        $('#personName').val(yyxx.name);
    } else {
        $.messager.alert('提示', '请选择人员信息!');
        return;
    }
    if (wpxxArr) {
        for (var i = 0; i < wpxxArr.length; i++) {
            var wpxx = wpxxArr[i]
            $('#belonggrid').datagrid('appendRow', {
                name: wpxx.name,
                description: wpxx.description,
                detailCount: wpxx.detail_count,
                unit: wpxx.unit
            });
        }
    }
    $('#ajxxAndPersonDialogZfba').dialog('close');
}

//打开新增弹框
function belongAddList() {

    $("#inPopup").show();

    //$('#detinfo_form').form('clear');
    $('#qx_form').form('clear');
    $('#belong_form').form('clear');
    $('#belongDetail_form').form('clear');
    $("#belongDetail_form").html("");
    $('#belonggrid').datagrid({
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
            trefresh: new Date().getTime()
        },
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
                title: '物品名称',
                width: '15%'
            },
            {field: 'detailCount', title: '数量', width: '10%'},
            {field: 'unit', title: '单位', width: '10%'},
            {
                field: 'description',
                title: '特征',
                width: '45%'
            },
            {
                field: 'id',
                title: '操作',
                width: '20%',
                align: 'left',
                formatter: function (value, row, index) {
                    return '<span class="spanRow"><a href="#" class="griddel" onclick="deleteRow(this)">删除</a></span>';
                }
            }]],
        pagination: false,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        // 当数据加载成功时触发
        onLoadSuccess: function () {
        }
    });
    formClear();
    $('#cuffNumber').textbox('textbox').focus();
}

//关闭弹出框
function closeMpopup() {
    $(".m-popup").hide();
}

//随身物品增加
function belongAdd() {
    if ($("#names1").val() == null || $("#names1").val() == "" || $("#detailCount").val() == null || $("#detailCount").val() == "" || $("#unit").val() == null || $("#unit").val() == "") {
        $.messager.alert('提示', '信息填写不完整!');
        return;
    }
    var data = $('#detinfo_form').serializeObject();
// 动态给table添加一行数据
    $('#belonggrid').datagrid('appendRow', {
        name: data.name,
        description: data.description,
        detailCount: data.detailCount,
        unit: data.unit
    });
    $('#unit').combobox('clear');
    $('#detailCount').textbox('setValue', '');
    $('#description').textbox('setValue', '');
    $('#names1').combobox('clear');
}

// 删除存物信息
function deleteRow(target) {
    // $('#belonggrid').datagrid('deleteRow',index);
    $('#belonggrid').datagrid('deleteRow', getRowIndex(target));
}

function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

//PDF打印
function signPrint(index) {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
    } else {
        printChoose(row.id, row.uuid, row.areaId, 5);
    }
}

//签字
function securitySign() {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
    } else {
        var url = "../../lawdocProcess/downloadBase64.do?number=9&name=随身物品登记&type=1&userId=0&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id;
        SignType = 5;
        startSign(url, row.id, 5);
    }
}

//重新签字
function cxsecuritySign() {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
    } else {
        var url = "../../lawdocProcess/downloadBase64.do?number=9&name=随身物品登记&type=1&userId=0&dataId=" + row.id + "&serialNo=" + row.serialNo + "&serialId=" + row.id;
        SignType = 5;
        cxstartSign(url, row.id, 5);
    }
}

//pdf下载
function PdfDownLoad() {
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if (!row || !row.id) {
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
    } else {
        downLoadPdf(row.id, row.uuid, row.areaId, 5);
    }
}

function chooseWp(value) {
    $('#unit').textbox('setValue', '12112');
}

//打印条形码
function printcod() {
    if (window.print) {
        //办案中心名称
        var an = null;
        //嫌疑人姓名
        var pn = null;
        //案由
        var cn = null;
        //条码值
        var co = null;
        //身份证号
        var ceNo = null;
        //物品名称
        var wName = null;
        //物品数量
        var count = null;

        var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
        var row = cg.datagrid('getSelected');
        if (row == null) {
            $.messager.alert('提醒', '请选择嫌疑人!');
            return;
        }
        var enterpriseinfo = {"serialId": row.id, belongingsId: row.belongingsId};
        var json_data = JSON.stringify(enterpriseinfo);
        $.messager.confirm('打印确认', '是否确定打印条码？', function (r) {
            if (r) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'addBelongcodNew.do',
                    data: json_data,
                    dataType: 'json',
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data != null && data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                an = data[i].aname;
                                wName = data[i].wname;
                                pn = data[i].pname;
                                co = data[i].wpUuid;
                                count = data[i].detailCount
                                // alert("wName= "+wName+"co= "+co+count);
                                OpenPrinter();
                                // PSKPrn.PTKDrawTextEx(200, 10, 0, 97, 1, 1, 78, "物品名称:" + wName, false);
                                // PSKPrn.PTKDrawTextEx(200, 50, 0, 97, 1, 1, 78, "数量：" + count, false);
                                // PSKPrn.PTKDrawBar2DQR(200, 80, 180, 180, 0, 7, 2, 0, 0, co);// QR码
                                PSKPrn.PTKDrawTextEx(150, 10, 0, 97, 1, 1, 78, "物品名称:" + wName, false);
                                PSKPrn.PTKDrawTextEx(150, 50, 0, 97, 1, 1, 78, "数量：" + count, false);
                                PSKPrn.PTKDrawBarcodeEx(90, 150, 0, "1A", 2, 5, 60, 66, "\"" + co + "\"C0", true);
                                PSKPrn.PTKPrintLabel(1, 1);
                                ClosePrinter();
                            }

                            $.messager.progress('close');
                        }
                    },
                    error: function (data) {
                        $.messager.alert('错误', '打印条码错误!');
                    }
                });
            }
        });
    } else {
        alert("本地没有打印机");
        var iWidth = 800; //弹出窗口的宽度;
        var iHeight = 600; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('belongWordTemplate.doc.html', '条码打印机安装、操作手册', "height=" + iHeight + ", width=" + iWidth + ", top=" + iTop + ", left=" + iLeft);
    }
}

function OpenPrinter() {
    PSKPrn.OpenPort("POSTEK C200+");
    PSKPrn.PTKClearBuffer();
    PSKPrn.PTKSetPrintSpeed(4);
    PSKPrn.PTKSetDarkness(10);
    PSKPrn.PTKSetLabelHeight(320, 16, 0, 0);
    PSKPrn.PTKSetLabelWidth(620);
}

function ClosePrinter() {
    PSKPrn.ClosePort();
}

//打印条形码
function printcodold() {
    if (window.print) {
        //办案中心名称
        var an = null;
        //嫌疑人姓名
        var pn = null;
        //案由
        var cn = null;
        //条码值
        var co = null;
        //身份证号
        var ceNo = null;

        var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
        var row = cg.datagrid('getSelected');
        if (row == null) {
            $.messager.alert('提醒', '请选择嫌疑人!');
            return;
        }
        var enterpriseinfo = {"serialId": row.id, belongingsId: row.belongingsId};
        var json_data = JSON.stringify(enterpriseinfo);
        $.messager.confirm('打印确认', '是否确定打印条码？', function (r) {
            if (r) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'selectBelonginfo.do',
                    data: json_data,
                    dataType: 'json',
                    cache: false,
                    async: false,
                    success: function (data) {
                        if (data != null) {
                            an = data.aname;
                            cn = data.cname;
                            pn = data.pname;
                            co = data.burcode;
                            ceNo = data.certificateNo
                            //打印机名称
                            var printerName = "ZDesigner GT800 (EPL)";
                            //端口
                            var printerPort = "USB001)";
                            print_epl.Open_Port(printerPort);
                            print_epl.Begin_Job("2", "12", "False", "B");
                            print_epl.Print_Winfont(200, 15, "嫌疑人姓名:" + pn, "宋体", 20, 12, "True", "False", "False", "False", "False");
                            print_epl.Print_Winfont(200, 45, "身份证号:" + ceNo, "宋体", 20, 12, "True", "False", "False", "False", "False");
                            print_epl.Print_BarCode(200, 80, co, "1", "60", "2", "6", "B", "0");
                            var caseInfo = cn;
                            if (caseInfo.length > 50) {
                                caseInfo = caseInfo.substr(0, 50)
                            }
                            if (caseInfo.length > 0 && caseInfo.length <= 25) {
                                print_epl.Print_Winfont(200, 170, caseInfo, "宋体", 20, 8, "True", "False", "False", "False", "False");
                            } else if (caseInfo.length > 25 && caseInfo.length <= 50) {
                                caseInfo1 = caseInfo.substr(0, 25);
                                caseInfo2 = caseInfo.substr(25);
                                print_epl.Print_Winfont(200, 170, caseInfo1, "宋体", 20, 8, "True", "False", "False", "False", "False");
                                print_epl.Print_Winfont(200, 200, caseInfo2, "宋体", 20, 8, "True", "False", "False", "False", "False");
                            }
                            print_epl.End_Job();
                            print_epl.Close_Port();
                            print_epl.Printing_USBPORT(printerName);
                            $.messager.progress('close');
                        }
                    },
                    error: function (data) {
                        $.messager.alert('错误', '打印条码错误!');
                    }
                });
            }
        });
    } else {
        alert("本地没有打印机");
        var iWidth = 800; //弹出窗口的宽度;
        var iHeight = 600; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('belongWordTemplate.doc.html', '条码打印机安装、操作手册', "height=" + iHeight + ", width=" + iWidth + ", top=" + iTop + ", left=" + iLeft);
    }
}

//刷新
//嫌疑人下拉框
function shuaxin() {
    //嫌疑人下拉框,查询所有已经存物的嫌疑人
    $('#personName').val("");
    $('#serialId').val("");
    $('#caseId').val("");
    $('#areaId').val("");
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getPersonBelong.do?areaId=' + ssareaid,
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'id', title: '姓名', width: 100,hidden: true},
            {field: 'name', title: '姓名', width: 100},
            {field: 'certificateNo', title: '证件号码', width: 260}
            // {field: 'areaId', title: '办案区域id', hidden: true},
            // {field: 'caseId', title: '案件id', hidden: true},
            // {field: 'personId', title: '人员id', hidden: true}
        ]],
        onChange: function (data, date1) {
            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');

            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, areaId: ssareaid, trefresh: new Date().getTime()
                });
                $('#personName').val(row.name);
                $('#serialId').val(row.id);
                $('#caseId').val(row.caseId);
                $('#areaId').val(ssareaid);
            }
        }
    });
    $('#detid').datagrid('load', {
        enpId: 0, trefresh: new Date().getTime()
    });
}

