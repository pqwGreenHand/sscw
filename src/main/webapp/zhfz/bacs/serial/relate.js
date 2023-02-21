//嫌疑人出入区主页面关联按钮
function relateButton(serialId){
    $("#relateDialog").show();
    var serialRow = currenMap[serialId]
    loadRelateDatagrid(serialRow.certificateNo);
}
//入区历史界面关联按钮
function relateButton2(index){
    $("#relateDialog").show();
    var row = $('#datagrid').datagrid('getRows')[index];
    var certificateNo = row.certificateNo;
    loadRelateDatagrid(certificateNo);
}

function loadRelateDatagrid(certificateNo){
    $('#relateDatagrid').datagrid({
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
        url: 'suspectlist.do',
        queryParams: {
            certificateNo: certificateNo
        },
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }, {
            title: 'ID',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[{
            field: 'inTime',
            title: '入区时间',
            width: 70,
            formatter: function (value, rec) {
                return valueToDate(value);
            }
        }, {
            field: 'name',
            title: '嫌疑人',
            width: 40,
        }, {
            field: 'inUserRealName1',
            title: '民警',
            width: 40
        }, {
            field: 'organization',
            title: '办案单位',
            width: 60
        }, {
            field: 'outReason',
            title: '出区去向',
            width: 70
        }, {
            field: 'outTime',
            title: '出区时间',
            width: 70,
            formatter: function (value, rec) {
                if (value != null && value != '') {
                    return valueToDate(value);
                } else {
                    return "";
                }
            }
        }, {
            field: 'caseType',
            title: '案件性质',
            width: 40,
            formatter: function (value, rec) {
                if (value == 0) {
                    return '刑事';
                } else if (value == 1) {
                    return '行政';
                } else {
                    return '无';
                }
            }
        }, {
            field: 'caseNature',
            title: '案件类型',
            width: 80,
            formatter: function (value, rec) {
                if (value == null || value == "") {
                    return '无';
                } else {
                    return formatterColunmVal(value, 8);
                }
            }
        }, {
            field: 'confirmResult',
            title: '裁决结果',
            width: 50,
            formatter: function (value, rec) {
                if (value != null && value != '') {
                    return value;
                } else {
                    return '<font color="#ff2a00">未裁决</font>';
                }
            }
        }]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        onClickRow: function () {
            var currentRow = $("#relateDatagrid").datagrid("getSelected");
            console.log(currentRow);
            if (currentRow.caseId) {
                loadSameCasePersonDatagrid(currentRow.caseId,certificateNo);
                loadElsePersonCaseDatagrid(currentRow.caseId);
            } else {
                loadSameCasePersonDatagrid(-1,certificateNo);
                loadElsePersonCaseDatagrid(-1);
            }
        }
    });
    var p = $('#relateDatagrid').datagrid('getPager');
    $(p).pagination({});
}
//加载同案人员
function loadSameCasePersonDatagrid(caseId,certificateNo){
    $('#sameCasePersonDatagrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '90%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        checkbox: false,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: 'suspectlist.do',
        queryParams: {
            caseId: caseId
        },
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }, {
            title: 'ID',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[{
            field: 'inTime',
            title: '入区时间',
            width: 70,
            formatter: function (value, rec) {
                return valueToDate(value);
            }
        }, {
            field: 'name',
            title: '嫌疑人',
            width: 40,
            formatter: function (value, rec) {
                return  value;
            }
        }, {
            field: 'sex',
            title: '性别',
            width: 20,
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
            }
        }, {
            field: 'certificateNo',
            title: '证件号码',
            width: 120
        }, {
            field: 'outReason',
            title: '出区去向',
            width: 70
        }, {
            field: 'outTime',
            title: '出区时间',
            width: 70,
            formatter: function (value, rec) {
                if (value != null && value != '') {
                    return valueToDate(value);
                } else {
                    return "";
                }
            }
        }, {
            field: 'confirmResult',
            title: '裁决结果',
            width: 50,
            formatter: function (value, rec) {
                if (value != null && value != '') {
                    return value;
                } else {
                    return '<font color="#ff2a00">未裁决</font>';
                }
            }
        }

        ]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        loadFilter: function (data) {
            for (var i = 0; i < data.rows.length; i++) {
                var itm = data.rows[i];
                if (itm.certificateNo == certificateNo) {
                    data.rows.splice(i, 1);
                    break;
                }
            }
            return data;
        }
    });
    var p = $('#sameCasePersonDatagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
        }
    });
}
//加载事主/证人
function loadElsePersonCaseDatagrid(caseId) {
    $('#elsePersonCaseDatagrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '90%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        checkbox: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: 'otherlist.do',
        queryParams: {
            caseId: caseId
        },
        sortName: 'inTime',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }, {
            title: 'ID',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[{
            field: 'name',
            title: '姓名',
            width: 40,
        }, {
            field: 'sex',
            title: '性别',
            width: 25,
            formatter: function (value, rec) {
                if (value == 0) {
                    return "未知的性别";
                }
                if (value == 1) {
                    return "男";
                }
                if (value == 2) {
                    return "女";
                }
                if (value == 9) {
                    return "未说明的性别";
                }
                return "";
            }
        }, {
            field: 'type',
            title: '类型',
            width: 25,
            formatter: function (value, rec) {
                if (value == 0) {
                    return "嫌疑人";
                } else if (value == 1) {
                    return "事主";
                } else if (value == 2) {
                    return "证人";
                } else if (value == 3) {
                    return "见证人";
                } else if (value == 4) {
                    return "被侵害人";
                } else if (value == 5) {
                    return "报案人";
                } else if (value == 6) {
                    return "其它";
                }

            }
        }, {
            field: 'certificateNo',
            title: '证件号码',
            width: 95
        }, {
            field: 'inTime',
            title: '入区时间',
            width: 100,
            formatter: function (value, rec) {
                return valueToDate(value);
            }
        }, {
            field: 'outTime',
            title: '出区时间',
            width: 100,
            formatter: function (value, rec) {
                if (value != null && value != '') {
                    return valueToDate(value);
                } else {
                    return "";
                }

            }
        }]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
    });
    var p = $('#elsePersonCaseDatagrid').datagrid('getPager');
    $(p).pagination({});
}