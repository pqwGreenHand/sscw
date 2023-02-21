var tempId = null;
var flag = 0;
var jzAjlyCode = 0;
var caseAjlyCode = 1;
var orderRequestDataId = "";
var personUrl = "";
var sfaj = "";
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
        url: contextPath + '/zhfz/bacs/belong/queryCase.do',
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
            {field: 'ajmc', title: '案件名称', width: 150},
            {field: 'ajbh', title: '案件编号', width: 100},
            {
                field: 'ajlx', title: '案件类型', width: 40,
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
            {field: 'abmc', title: '案别', width: 40},
            {field: 'zbdwName', title: '办案场所', width: 60},
            {
                field: 'id',
                title: '操作',
                width: 60,
                align: 'center',
                formatter: function (value, row, index) {
                    var e = "";
                    var f = "";
                    if (isGridButton("linkCaseRemove")) {
                        e = '<span class="spanRow"><a href="#" class="griddel" id="orderRequestPersonRemove" onclick="linkCaseRemove('
                            + index
                            + ')">删除</a></span>';
                    }
                    if (isGridButton("linkCaseEdit")) {
                        f = '<span class="spanRow"><a href="#" class="gridedit" id="orderRequestPersonEdit" onclick="linkCaseEdit('
                            + index
                            + ')">修改</a></span>';
                    }
                    return e + f;
                }
            }
        ]],
        pagination: true,
        pageSize: 20,
        pageList: [20, 30, 40, 50, 100],
        rownumbers: true,
        //行选择方法，进行条件触发
        onSelect: function (rowIndex, rowData) {
            lawdocDetailgridLoad(rowData);
        }, onLoadSuccess: function (data) {
            if (data) {
                // lawdocDetailgridLoad(data.rows[0]);
            }
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
        queryParams: {},
        url: contextPath + '/zhfz/bacs/belong/queryPerson.do',
        singleSelect: true,
        columns: [[
            {field: 'name', title: '姓名', width: 70},
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
            {field: 'certificateTypeName', title: '证件类型', width: 80},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {
                field : '操作',
                title : '操作',
                width : 150,
                align : 'center',
                formatter : function(value, row, index) {
                    var e='';
                    var d='';
                    if(isGridButton("personEditSscw")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="personEditSscw('+index+')">修改</a></span>';
                    }
                    if(isGridButton("personRemoveSscw")){
                        d ='<span class="spanRow"><a href="#" class="griddel" onclick="personRemoveSscw('+index+')">删除</a></span>';
                    }
                    return e + d;
                }
            }
        ]],
        pagination: true,
        pageSize: 20,
        pageList: [20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#lawdocDetailToolbar'
    });
})
function personEditSscw(index){
    $("#addsuspectinfoform").show();
    //$('#addsuspectinfoform').form('clear');
    var rowData1 = $('#lawdocgrid').datagrid('getSelected');
    var rowData = $('#lawdocDetailgrid').datagrid('getRows')[index];
    //console.info("=======birth1============"+rowData.birth);
    loadSex();
    codeCombo('person_country', 'GJID', '');
    loadSpecialIdentity();

    $('#addsuspectinfoform').form('load',
        {
            personId:rowData.id,
            person_name:rowData.name,
            person_sex:rowData.sex,
            person_certificate_type:rowData.certificateTypeId,
            person_certificate_no:rowData.certificateNo,
            person_personInfo:rowData.oldName,
            person_country:rowData.country,
            person_nation:rowData.nation,
            addareaId:rowData.areaId
        });

    loadNation(rowData.nation);
    var personform = $('#addsuspectinfoform').serializeObject();
    $('#person_certificate_type').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        required: true,
        onSelect: function (rec) {
            if (rec.id == 7) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    url: "../combobox/listWMSCount.do",
                    success: function (data) {
                        var count = data + 1;
                        $('#person_name').val("无名氏" + count);
                        $("#person_certificate_no").val("XXXXX.....XXXXX" + count);
                    },
                    error: function (data) {
                        $.messager.alert('错误', '未知错误!');
                        $.messager.progress('close');
                    }
                });
            }
        }, onLoadSuccess: function (data) {
            $('#person_certificate_type').combobox('setValue', rowData.certificateTypeId);
        }, onChange: function (n, o) {
            setWuMingShi();
        }
    });

    $('#sex').combobox({
        url:'../../common/combobox/listCode.do?type=XBID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#sex').combobox('setValue', personform["person_sex"]);
        }
    });

    $('#certificateTypeId').combobox({
        url:'../../common/combobox/listCode.do?type=ZJZLID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#certificateTypeId').combobox('setValue', personform["certificateTypeId"]);
        }
    });


    $('#education').combobox({
        url:'../../common/combobox/listCode.do?type=WHCDID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#education').combobox('setValue', personform["education"]);
        }
    });

    $('#certificateTypeId').combobox({
        url:'../../common/combobox/listCode.do?type=ZJZLID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#certificateTypeId').combobox('setValue', personform["certificateTypeId"]);
        }
    });


    $('#politic').combobox({
        url:'../../common/combobox/listCode.do?type=ZZMMID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#politic').combobox('setValue', personform["politic"]);
        }
    });

    $('#officer').combobox({
        url:'../../common/combobox/listCode.do?type=TSSFID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#officer').combobox('setValue', personform["officer"]);
        }
    });

    /*$('#country').combobox({
        url:'../../common/combobox/listCode.do?type=GJID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#country').combobox('setValue', personform["country"]);
        }
    });*/

    $('#nation').combobox({
        url:'../../common/combobox/listCode.do?type=MZID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#nation').combobox('setValue', personform["nation"]);
        }
    });

    $('#areaId').combobox({
        url: '../combobox/getAllInterrogateAreaName.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#areaId').combobox('setValue', personform["areaId"]);
        }
    });
    showDialog('#dialog_add_suspectinfo', '嫌疑人信息');
    /*showDialog('#addsuspectinfoform','编辑人员');*/
    personUrl = contextPath + '/zhfz/bacs/person/updatePerson.do';
    //url = 'update.do';
}
function personRemoveSscw(index){
    $('#addsuspectinfoform').form('clear');
    var row = $('#lawdocDetailgrid').datagrid('getRows')[index];
    //var row = $('#datagrid').datagrid('getSelected');
    $('#addsuspectinfoform').form('load',{
        id:row.id
    });
    var rowData = $('#lawdocDetailgrid').datagrid('getRows')[index];
    //var personInfo = elemPersoninfo.serializeObject();
    var jsonrtinfo = JSON.stringify(rowData);
    $.messager.confirm('删除确认','是否确定删除该条数据？',function(r){
        if (r){
            $.messager.progress({
                title:'请等待',
                msg:'删除中...'
            });
            jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                url: contextPath + '/zhfz/bacs/person/deletePerson.do',
                data : jsonrtinfo,
                dataType : 'json',
                success : function(data){
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000

                    });
                    lawdocSearch();
                    //$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
                    $.messager.progress('close');
                },
                error : function(data){
                    $.messager.progress('close');
                    $.messager.alert('错误', data.content);
                }
            });
        }});
}
//删除案件
function linkCaseRemove(index) {
    var rowData = $('#lawdocgrid').datagrid('getRows')[index];
    var value = rowData.id;
    $.messager.confirm('Confirm', '确定删除?', function (r) {
        if (r) {
            $.messager.confirm({
                width: 380,
                height: 160,
                title: '警告',
                msg: '案件关联数据较多，确定删除?',
                fn: function (m) {
                    if (m) {
                        $.messager.progress({
                            title: '请等待',
                            msg: '删除数据中...'
                        });
                        jQuery.ajax({
                            type: 'POST',
                            url: '../../common/case/removeCase.do',
                            data: {
                                "caseId": value,
                            },
                            dataType: 'json',
                            success: function (data) {
                                $.messager.show({
                                    title: '提示',
                                    msg: data.content,
                                    timeout: 3000
                                });
                                $.messager.progress('close');
                                lawdocSearch();
                            },
                            error: function (data) {
                                $.messager.progress('close');
                                $.messager.alert('错误', '未知错误!');
                            }
                        });
                    }
                }
            })
        }
    })
}

//打开修改弹框
function linkCaseEdit(index) {
    // showDialog('#add_case_dialog','编辑案件');
    $("#add_case_dialog").show();
    $("#saveLinkCase").hide();
    $("#editLinkCase").show();
    var rowData = $('#lawdocgrid').datagrid('getRows')[index];
    $('#form_case_add').form('clear');
    var afdd = rowData.afdd;
    var arr;
    if (rowData.afddCode != null && rowData.afddCode != "") {
        arr = rowData.afddCode.split('*');
    }
    BindAddress(arr);
    var afdd = rowData.afdd;
    var involvedAddress = "";
    var flagIndex = -1;
    $('#form_case_add').form("load", rowData);
    if (arr != null && arr.length > 0) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == null || arr[i] == '') {
                flagIndex = i;
                break;
            }
        }
        if (flagIndex == 0) {
            $("#involvedAddress").val(afdd);
        } else {
            if (flagIndex == -1) {
                flagIndex = 4;
            }
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                url: '../../common/region/selectByCode.do?code=' + arr[flagIndex - 1],
                success: function (data) {
                    involvedAddress = afdd.substring(afdd.indexOf(data.name) + data.name.length, afdd.length);
                    $("#involvedAddress").val(involvedAddress);
                },
                error: function (data) {
                    $.messager.alert('错误', '数据错误');
                }
            });
        }
    } else {
        $("#involvedAddress").val(afdd);
    }
    loadZbdw(rowData.zbdwId);
    loadAjlx(rowData.ajlx, rowData.ab);
    xbmjData = {};
    xbmjS = rowData.xbmjIds;
    initXbmjTable();
    jQuery.ajax({
        type: 'POST',
        url: "../../common/case/listXbPolice.do",
        data: {
            xbmjIds: xbmjS,
        },
        dataType: 'json',
        success: function (data) {
            $('#xbmjTable').datagrid('loadData', data);
            xbmjData = data;
        },
        error: function (data) {
            $.messager.alert('Error', 'Unknown Error!' + data);
            $('#xbmjTable').datagrid('loadData', xbmjData);
        }
    });
}

//加载主办单位
function loadZbdw(zbdwId) {
    $("#zbdwId").combobox({
        url: '../../common/combobox/listAllOrganizationNameCombobox.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function () {
            if (zbdwId != null) {
                $("#zbdwId").combobox("setValue", zbdwId);
            }
        }
    });
}

function loadAjlx(ajlx, ab) {
    $('#ajlx').combobox({
        url: contextPath + "/zhfz/common/code/listCodeByType.do?type=AJLX&tresh=" + new Date().getTime(),
        valueField: 'codeKey',
        textField: 'codeValue',
        editable: false,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            //return row[opts.textField].indexOf(q) == 0;
            return row.codeValue.indexOf(q) > -1;//将从头位置匹配改为任意匹配
        },
        onLoadSuccess: function () {
            $('#ajlx').combobox('setValue', ajlx);
            if (ab != null && ab != '') {
                loadAb(ajlx, ab);
            }
        },
        onChange: function (newValue, oldValue) {
            loadAb(newValue, "");
        }
    });
}

//查询嫌疑人
function lawdocPersonSearch() {
    $("#lawdocDetailgrid").datagrid("reload", {
        trefresh: new Date().getTime(),
        name: $("#name").textbox("getValue"),
        certificateNo: $("#certificateNo").textbox("getValue")
    });
}
function doPersonClear() {
    $('#name').textbox('setValue', '');
    $('#certificateNo').textbox('setValue', '');
}

//查询案件
function lawdocCaseSearch() {
    $("#lawdocgrid").datagrid("reload", {
        trefresh: new Date().getTime(),
        ajmc: $("#ajmc").textbox("getValue"),
        ajbh: $("#ajbh").textbox("getValue")
    });
}
// 案件查询清除
function doCaseClear() {
    $('#ajmc').textbox('setValue', '');
    $('#ajbh').textbox('setValue', '');
    $("#s_areaId").combobox("setValue", "");
}


//案件新增
function caseAdd1() {
    $("#add_case_dialog").show();
    $('#form_case_add').form('clear');
    $("#zbmjId").val("");
    loadAjlx1('', '');
    loadPersonalConfig1();
    loadZbdw1();
    $("#saveLinkCase").show();
    $("#editLinkCase").hide();
    $("#ajlx").combobox("clear");
    initXbmjTable1();
    $('#xbmjTable').datagrid('loadData', {rows: []});
    xbmjData = {};
    xbmjS = "";
    xbmjjhs = "";
    xbmjxms = "";
    //$("#zbmjCertificateNo").val("请输入警号");
}

function lawdocDetailgridLoad(rowData) {
    $('#lawdocDetailgrid').datagrid('load', {
        ajbh: rowData.ajbh,
        trefresh: new Date().getTime()
    });
}

function addXbmj() {
    $("#addXbmjDialog").show();
    loadAllOrg();
}

function loadAjlx1(ajlx, ab) {
    $('#ajlx').combobox({
        url: contextPath + "/zhfz/common/code/listCodeByType.do?type=AJLX&tresh=" + new Date().getTime(),
        valueField: 'codeKey',
        textField: 'codeValue',
        editable: false,
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            //return row[opts.textField].indexOf(q) == 0;
            return row.codeValue.indexOf(q) > -1;//将从头位置匹配改为任意匹配
        },
        onLoadSuccess: function () {
            $('#ajlx').combobox('setValue', ajlx);
            if (ab != null && ab != '') {
                loadAb(ajlx, ab);
            }
        },
        onChange: function (newValue, oldValue) {
            loadAb(newValue, "");
        }
    });
}

//校验输入的警号
function checkUserId(obj) {
    if (obj.value == null || obj.value == "") {
        return;
    }
    var userNo = obj.value;
    if (userNo.indexOf('(') >= 0) {
        userNo = userNo.substring(0, userNo.indexOf('('));
    }
    var zbmj = $("#zbmjCertificateNo").val();
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"] = userNo;
    userNoInfo['tresh'] = new Date().getTime();
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        url: '../../bacs/order/searchUser.do',
        data: jsonrtinfo,
        success: function (data) {
            if (data != null && data.id != null) {
                if (xbmjS != null && xbmjS != ''
                    && (xbmjS.indexOf(data.id + ",") >= 0 || xbmjS.indexOf("," + data.id) >= 0 || xbmjS == data.id)) {
                    $.messager.alert('提示', '主办民警与协办民警不能为同一个人!');
                    obj.value = "";
                    $("#zbmjId").val("");
                    $("#zbmjName").val("");
                    return false;
                }
                console.log(data);
                $("#zbdwId").combobox("setValue", data.organizationId);
                $("#zbmjId").val(data.id);
                $("#zbmjName").val(data.realName);
                //obj.value = obj.value +'('+data.realName+')';
                $("#zbmjCertificateNo").val(userNo + "(" + data.realName + ")");
                return true;
            } else {
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
                $("#zbmjId").val("");
                $("#zbmjName").val("");
                return false;
            }
        },
        error: function (data) {
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');
            $("#zbmjId").val("");
            $("#zbmjName").val("");
            return false;
        }
    });
}

//加载个性化配置
function loadPersonalConfig1() {
    var arr;
    jQuery.ajax({
        type: 'get',
        async: false,
        dataType: 'json',
        url: '../../bacs/personalconfig/listConfigDetailByAreaAndName.do?name=' + encodeURIComponent('案发地址绑定'),
        success: function (data) {
            if (data != null && data.error == false && data.callbackData != null) {

                if (data.callbackData["regionids"] != "" && data.callbackData["regionids"] != null) {
                    arr = data.callbackData["regionids"].split('*');
                }
            }
            BindAddress(arr);
        }
    });
}

function initAddXbmjTable() {
    $('#addXbmjTable').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '80%',
        height: '80%',
        fitColumns: true,
        striped: true,
        nowrap: false,
        collapsible: false,
        loadMsg: '加载中.....',
        method: 'get',
        url: '../../common/case/listPolice.do',
        queryParams: {
            organization_name_t: $('#xb_org_cmb').combobox("getText"),
            trefresh: new Date().getTime()
        },
        sortName: 'id',
        sortOrder: 'id',
        remoteSort: false,
        showFooter: false,
        idField: 'id',
        singleSelect: false,
        frozenColumns: [[
            {field: 'ck', checkbox: true},
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'realName', title: '姓名', width: 10},
            {field: 'certificateNo', title: '警号', width: 10},
            {field: 'organizationName', title: '单位', width: 10}
        ]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        toolbar: "#addXbmjToolbar",
        onLoadSuccess: function (data) {
            $("#addXbmjTable").datagrid('unselectAll');
            if (xbmjData != '') {
                for (var i = 0; i < xbmjData.length; i++) {
                    var index = $("#addXbmjTable").datagrid("getRowIndex", xbmjData[i].id);
                    if (index != null && index != -1) {
                        $("#addXbmjTable").datagrid('selectRow', index);
                    }
                }
            }
        },
        onSelect: function (rowIndex, rowData) {
            var zbmjId = $("#zbmjId").val();
            if (zbmjId != null && zbmjId != '') {
                if (rowData.id == zbmjId) {
                    $.messager.alert('提示', "主办民警与协办民警不能为同一个人");
                    $("#addXbmjTable").datagrid('unselectRow', rowIndex);
                }
            }
        }

    })
}

//根据用户Id取消协办民警选中
function cancleXbmjSelectById(id) {
    var index = $("#addXbmjTable").datagrid("getRowIndex", id);
    if (index != null && index != -1) {
        $("#addXbmjTable").datagrid('unselectRow', index);
    }
}

function initXbmjTable() {
    $('#xbmjTable').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '80%',
        height: '80%',
        fitColumns: true,
        striped: true,
        nowrap: false,
        collapsible: false,
        loadMsg: '加载中.....',
        sortName: 'id',
        sortOrder: 'id',
        remoteSort: false,
        showFooter: false,
        idField: 'id',
        singleSelect: false,
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'realName', title: '姓名', width: 10},
            {field: 'certificateNo', title: '警号', width: 10},
            {field: 'organizationName', title: '单位', width: 10},
            {
                field: 'id',
                title: '操作',
                width: 10,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<span class="spanRow"><a href="#" class="griddel" onclick="xbmjDelete(' + index + ')">删除</a></span>';
                }
            }
        ]],
        rownumbers: true
    });
}

function loadAllOrg() {
    $("#xb_org_cmb").combobox({
        url: '../combobox/listAllOrganizationNameComboboxWithNo.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function () {
            $.get("../../../common/getSessionInfo.do", function (data) {
                var sessionObj = eval('(' + data + ')');
                console.log(sessionObj)
                $("#xb_org_cmb").combobox("setValue", sessionObj.currentOrg.id);
                initAddXbmjTable();
            });
        }
    });
}

//加载主办单位
function loadZbdw1(zbdwId) {
    $("#zbdwId").combobox({
        url: '../../common/combobox/listAllOrganizationNameCombobox.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function () {
            if (zbdwId != null) {
                $("#zbdwId").combobox("setValue", zbdwId);
            }
        }
    });
}

function initXbmjTable1() {
    $('#xbmjTable').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '80%',
        height: '80%',
        fitColumns: true,
        striped: true,
        nowrap: false,
        collapsible: false,
        loadMsg: '加载中.....',
        sortName: 'id',
        sortOrder: 'id',
        remoteSort: false,
        showFooter: false,
        idField: 'id',
        singleSelect: false,
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'realName', title: '姓名', width: 10},
            {field: 'certificateNo', title: '警号', width: 10},
            {field: 'organizationName', title: '单位', width: 10},
            {
                field: 'id',
                title: '操作',
                width: 10,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<span class="spanRow"><a href="#" class="griddel" onclick="xbmjDelete(' + index + ')">删除</a></span>';
                }
            }
        ]],
        rownumbers: true
    });
}

//加载省市县村三级联动
function BindAddress(arr) {
    var province = $('#province').combobox({
        url: '../../common/region/list.do?level=1&trefresh=' + new Date().getTime(),
        valueField: 'code',
        textField: 'name',
        onChange: function (newValue, oldValue) {
            //城市
            jQuery.ajax({
                type: 'get',
                dataType: 'json',
                url: '../../common/region/list.do?level=2&parentId=' + newValue + "&trefresh=" + new Date().getTime(),
                success: function (data) {
                    city.combobox("clear").combobox('loadData', data);
                    district.combobox("clear");
                    street.combobox("clear");
                }
            });
        },
        onLoadSuccess: function () {
            if (arr != null && arr.length > 0 && arr[0] != "") {
                $('#province').combobox('setValue', arr[0]);
            } else {
                var provinceData = $("#province").combobox("getData");
                if (provinceData != null && provinceData.length > 0) {
                    $('#province').combobox('setValue', '11');
                } else {
                    $('#province').combobox('setText', "省");
                }
            }
        }
    });
    var city = $('#city').combobox({
        valueField: 'code', //值字段
        textField: 'name', //显示的字段
        editable: true,
        onChange: function (newValue, oldValue) {
            console.log(newValue + "+++" + oldValue);
            if (newValue != "") {
                //区县
                $.get('../../common/region/list.do', {
                    level: 3,
                    trefresh: new Date().getTime(),
                    parentId: newValue
                }, function (data) {
                    district.combobox("clear").combobox('loadData', data);
                    street.combobox("clear");
                }, 'json');
            }
        },
        onLoadSuccess: function () {
            if (arr != null && arr.length > 1 && arr[1] != "") {
                $('#city').combobox('setValue', arr[1]);
            } else {
                var cityData = $("#city").combobox("getData");
                if (cityData != null && cityData.length > 0) {
                    $('#city').combobox('setValue', '1101');
                } else {
                    $('#city').combobox('setText', "城市");
                }
            }
        }
    });
    var district = $('#district').combobox({
        valueField: 'code', //值字段
        textField: 'name', //显示的字段
        editable: true,
        onChange: function (newValue, oldValue) {
            if (newValue != "") {
                $.get('../../common/region/list.do', {
                    level: 4,
                    trefresh: new Date().getTime(),
                    parentId: newValue
                }, function (data) {
                    street.combobox("clear").combobox('loadData', data);
                }, 'json');
            }
        },
        onLoadSuccess: function () {
            if (arr != null && arr.length > 2 && arr[2] != "") {
                $('#district').combobox('setValue', arr[2]);
            } else {
                var districtData = $("#district").combobox("getData");
                if (districtData != null && districtData.length > 0) {
                    //海淀区
                    $('#district').combobox('setValue', '110109');
                } else {
                    $('#district').combobox('setText', "区县");
                }
            }
        }
    });
    var street = $('#street').combobox({
        valueField: 'code', //值字段
        textField: 'name', //显示的字段
        editable: true,
        onLoadSuccess: function () {
            if (arr != null && arr.length > 3 && arr[3] != "") {
                $('#street').combobox('setValue', arr[3]);
            } else {
                var streetData = $("#street").combobox("getData");
                if (streetData != null && streetData.length > 0) {
                    $('#street').combobox('setValue', streetData[0].code);
                } else {
                    $('#street').combobox('setText', "街道/乡镇");
                }
            }
        }
    });
}


function doSearchXbmj() {
    $("#addXbmjTable").datagrid("reload", {
        real_name_t: $('#xb_name').val(),
        certificate_no_t: $('#xb_certificateNo').val(),
        organization_name_t: $('#xb_org_cmb').combobox("getText"),
        trefresh: new Date().getTime()
    });
}

function doClearXbmj() {
    $('#xb_name').val("");
    $('#xb_certificateNo').val("");
    $('#xb_org_cmb').combobox("setValue", "");
}

//保存协办民警数据
var xbmjData = {};
var xbmjS = "";
var xbmjjhs = "";
var xbmjxms = "";

function addXbmjSave() {
    xbmjData = $("#addXbmjTable").datagrid("getSelections");
    $("#xbmjTable").datagrid({
        data: xbmjData
    });
    $("#addXbmjDialog").hide();
    if (xbmjData != null) {
        xbmjS = ",";
        xbmjjhs = ",";
        xbmjxms = ",";
        for (var i = 0; i < xbmjData.length; i++) {
            xbmjS += xbmjData[i].id + ",";
            xbmjjhs += xbmjData[i].certificateNo + ",";
            xbmjxms += xbmjData[i].realName + ",";
        }
    }
}

function xbmjDelete(index) {
    var rowData = $("#xbmjTable").datagrid("getRows")[index];
    $("#xbmjTable").datagrid("deleteRow", index);
    cancleXbmjSelectById(rowData.id);
    if ($("#xbmjTable").datagrid("getData") != null) {
        xbmjData = $("#xbmjTable").datagrid("getData").rows;
        if (xbmjData != null) {
            xbmjS = ",";
            xbmjjhs = ",";
            xbmjxms = ",";
            for (var i = 0; i < xbmjData.length; i++) {
                xbmjS += xbmjData[i].id + ",";
                xbmjjhs += xbmjData[i].certificateNo + ",";
                xbmjxms += xbmjData[i].realName + ",";

            }
        }
    }
}

//加载案由
function loadAb(natureValue, caseNatureId) {
    var natureValue2 = $("#ajlx").combobox("getText");
    if (natureValue2 != null && natureValue2 != '') {
        if (natureValue2 != natureValue) {
            natureValue = natureValue2;
        }
    }
    //加载犯罪类型
    $('#ab').combobox({
        url: '../../bacs/combobox/listcrimetypebynature.do?nature=' + encodeURI(natureValue, "UTF-8") + '&tresh=' + new Date().getTime(),
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#ab').combobox('setValue', caseNatureId);
        }
    })
}

//保存新增案件
function saveCase() {
    //保存修改案件
    if ($("#linkCaseId").val() != null && $("#linkCaseId").val() != "") {
        saveEditCase();
        return;
    }
    if (!checkForm('#form_case_add')) {
        return;
    }
    if ($("#zbmjCertificateNo").val() == "") {
        $.messager.alert('提示', '主办民警警号不正确或者为空!');
        $("#zbmjCertificateNo").val("");
        return;
    }
    var address = "";
    if ($('#province').combobox('getValue') != null && $('#province').combobox('getValue') != "") {
        address += $('#province').combobox('getText');
    }
    if ($('#city').combobox('getValue') != null && $('#city').combobox('getValue') != "") {
        var cityName = $('#city').combobox('getText');
        if (cityName.indexOf('北京') < 0 && cityName.indexOf('天津') < 0 &&
            cityName.indexOf('上海') < 0 && cityName.indexOf('重庆') < 0) {
            address += cityName;
        }
    }
    if ($('#district').combobox('getValue') != null && $('#district').combobox('getValue') != "") {
        address += $('#district').combobox('getText');
    }
    if ($('#street').combobox('getValue') != null && $('#street').combobox('getValue') != "") {
        address += $('#street').combobox('getText');
    }
    address += $('#involvedAddress').val();
    var addressCode = $('#province').combobox('getValue') + "*" + $('#city').combobox('getValue') + "*" + $('#district').combobox('getValue') + "*" + $('#street').combobox('getValue');
    var CaseForm = $('#form_case_add').serializeObject();
    CaseForm["involvedReasonText"] = $('#ab').combobox('getText');
    CaseForm["afdd"] = address;
    CaseForm["afddCode"] = addressCode;
    /*if(jzAjlyCode != 0){
        CaseForm["ajly"] = jzAjlyCode;
    } else {
        CaseForm["ajly"] = caseAjlyCode;
    }*/
    CaseForm["ajly"] = caseAjlyCode;
    CaseForm["xbmjIds"] = xbmjS;
    CaseForm["abmc"] = $("#ab").combobox("getText");
    CaseForm["zbmjjh"] = $("#zbmjCertificateNo").val();
    CaseForm["zbdwmc"] = $("#zbdwId").combobox("getText");
    CaseForm["xbmjXm"] = xbmjxms;
    CaseForm["xbmjjh"] = xbmjjhs;
    CaseForm["zbmjxm"] = $("#zbmjName").val();
    CaseForm["ajmc"] = $("#jzAjxx_ajmc").val();
    CaseForm["ajbh"] = $("#jzAjxx_ajbh").val();
    CaseForm["ajzt"] = $("#jzAjxx_ajzt").val();

    var CaseFormJson = JSON.stringify(CaseForm);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: "../../common/case/addCase.do",
        data: {
            form: CaseFormJson
        },
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#add_case_dialog').hide();
            $("#lawdocDetailgrid").datagrid("clearSelections");
            $("#lawdocgrid").datagrid("reload", {
                trefresh: new Date().getTime(),
                ajmc: $("#caseName").textbox("getValue"),
                zbdwId: $("#org_cmb").combobox("getValue")
            });
            $("#lawdocgrid").datagrid({
                onLoadSuccess: function (returnData) {
                    $("#lawdocgrid").datagrid("selectRecord", data.callbackData);
                    //所有调用这个弹框的页面必须有一个function
                    // linkCaseSave();
                    //lawdocSearch();
                }
            })
            lawdocSearch();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', 'Unknown Error!' + data);
        }
    });
}

//保存修改案件
function saveEditCase() {
    if (!checkForm('#form_case_add')) {
        return;
    }
    if ($('#involvedAddress').val() == "") {
        $.messager.alert("警告", "请填写案发地址");
        return;
    }
    if ($("#zbmjCertificateNo").val() == "") {
        $.messager.alert('提示', '主办民警警号不正确或者为空!');
        $("#zbmjCertificateNo").val("");
        return;
    }
    var address = "";
    if ($('#province').combobox('getValue') != null && $('#province').combobox('getValue') != "") {
        address += $('#province').combobox('getText');
    }
    if ($('#city').combobox('getValue') != null && $('#city').combobox('getValue') != "") {
        var cityName = $('#city').combobox('getText');
        if (cityName.indexOf('北京') < 0 && cityName.indexOf('天津') < 0 &&
            cityName.indexOf('上海') < 0 && cityName.indexOf('重庆') < 0) {
            address += cityName;
        }
    }
    if ($('#district').combobox('getValue') != null && $('#district').combobox('getValue') != "") {
        address += $('#district').combobox('getText');
    }
    if ($('#street').combobox('getValue') != null && $('#street').combobox('getValue') != "") {
        address += $('#street').combobox('getText');
    }
    address += $('#involvedAddress').val();
    var addressCode = $('#province').combobox('getValue') + "*" + $('#city').combobox('getValue') + "*" + $('#district').combobox('getValue') + "*" + $('#street').combobox('getValue');
    var CaseForm = $('#form_case_add').serializeObject();
    CaseForm["involvedReasonText"] = $('#ab').combobox('getText');
    CaseForm["afdd"] = address;
    CaseForm["afddCode"] = addressCode;
    CaseForm["xbmjIds"] = xbmjS;
    CaseForm["abmc"] = $("#ab").combobox("getText");
    CaseForm["zbmjjh"] = $("#zbmjCertificateNo").val();
    CaseForm["zbdwmc"] = $("#zbdwId").combobox("getText");
    CaseForm["xbmjXm"] = xbmjxms;
    CaseForm["xbmjjh"] = xbmjjhs;
    CaseForm["zbmjxm"] = $("#zbmjName").val();
    var CaseFormJson = JSON.stringify(CaseForm);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: "../../common/case/editCase.do",
        data: {
            form: CaseFormJson
        },
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#add_case_dialog').hide();
            $("#lawdocDetailgrid").datagrid("clearSelections");
            $("#lawdocDetailgrid").datagrid("reload", {
                trefresh: new Date().getTime(),
                ajmc: $("#caseName").textbox("getValue"),
                zbdwId: $("#org_cmb").combobox("getValue")
            });
            lawdocSearch();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', 'Unknown Error!' + data);
        }
    });
}

//无案件添加人员
function addPersonNoCase(id){
    sfaj=id;
    $("#personEditIndex").val("");
    //$('#selectJzLable').html('<font style="color: red">未关联警综平台数据！</font>');
    showDialog('#dialog_add_suspectinfo', '嫌疑人信息');
    $('#addsuspectinfoform').form('clear');
    loadSex();
    codeCombo('person_country', 'GJID', 156);
    loadNation();
    loadSpecialIdentity();
    //加载人员类型
    $('#person_personInfo').combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        }, {
            label: '老年人',
            value: '老年人'
        }, {
            label: '特殊疾病',
            value: '特殊疾病'
        }, {
            label: '孕妇',
            value: '孕妇'
        }, {
            label: '残疾人',
            value: '残疾人'
        }, {
            label: '成年人',
            value: '成年人',
            "selected": true
        }],
        valueField: 'value',
        textField: 'label',
        onChange: function (value) {
            // var juvenile = document.getElementsByName("isJuvenile");//未成年人
            // var isGravida = document.getElementsByName("isGravida");//孕妇

            if (value == '未成年人') {
                $("input[name='isJuvenile'][value='1']").prop("checked", true);
                $("input[name='isGravida'][value='0']").prop("checked", true);
                // juvenile[0].checked = true;
                // isGravida[0].checked = false;
            } else if (value == '孕妇') {
                $("input[name='isJuvenile'][value='0']").prop("checked", true);
                $("input[name='isGravida'][value='1']").prop("checked", true);
            }
        }

    });
    $('#person_certificate_type').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        required: true,
        onSelect: function (rec) {
            if (rec.id == 7) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    url: "../combobox/listWMSCount.do",
                    success: function (data) {
                        var count = data + 1;
                        $('#person_name').val("无名氏" + count);
                        $("#person_certificate_no").val("XXXXX.....XXXXX" + count);
                    },
                    error: function (data) {
                        $.messager.alert('错误', '未知错误!');
                        $.messager.progress('close');
                    }
                });
            }
        }, onLoadSuccess: function (data) {
            $('#person_certificate_type').combobox('setValue', data[0].id);
        }, onChange: function (n, o) {
            setWuMingShi();
        }
    });
    $('#person_country').combobox('setValue', 156);
    $('#person_nation').combobox('setValue', 1);
    $('#person_name').focus();
    $('#person_name').select();
    personUrl = contextPath + '/zhfz/bacs/person/insertPerson.do';
}

//添加人员
function addPerson(id) {
    sfaj=id;
    $("#personEditIndex").val("");
    var rowData = $('#lawdocgrid').datagrid('getSelected');
    if (rowData == null) {
        $.messager.alert('提示', '请选择一条左侧案件信息!');
        return;
    }
    //$('#selectJzLable').html('<font style="color: red">未关联警综平台数据！</font>');
    showDialog('#dialog_add_suspectinfo', '嫌疑人信息');
    $('#addsuspectinfoform').form('clear');
    loadSex();
    codeCombo('person_country', 'GJID', 156);
    loadNation();
    loadSpecialIdentity();
    //加载人员类型
    $('#person_personInfo').combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        }, {
            label: '老年人',
            value: '老年人'
        }, {
            label: '特殊疾病',
            value: '特殊疾病'
        }, {
            label: '孕妇',
            value: '孕妇'
        }, {
            label: '残疾人',
            value: '残疾人'
        }, {
            label: '成年人',
            value: '成年人',
            "selected": true
        }],
        valueField: 'value',
        textField: 'label',
        onChange: function (value) {
            // var juvenile = document.getElementsByName("isJuvenile");//未成年人
            // var isGravida = document.getElementsByName("isGravida");//孕妇

            if (value == '未成年人') {
                $("input[name='isJuvenile'][value='1']").prop("checked", true);
                $("input[name='isGravida'][value='0']").prop("checked", true);
                // juvenile[0].checked = true;
                // isGravida[0].checked = false;
            } else if (value == '孕妇') {
                $("input[name='isJuvenile'][value='0']").prop("checked", true);
                $("input[name='isGravida'][value='1']").prop("checked", true);
            }
        }

    });
    $('#person_certificate_type').combobox({
        url: '../combobox/certificateTypes.do',
        valueField: 'id',
        textField: 'value',
        required: true,
        onSelect: function (rec) {
            if (rec.id == 7) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    url: "../combobox/listWMSCount.do",
                    success: function (data) {
                        var count = data + 1;
                        $('#person_name').val("无名氏" + count);
                        $("#person_certificate_no").val("XXXXX.....XXXXX" + count);
                    },
                    error: function (data) {
                        $.messager.alert('错误', '未知错误!');
                        $.messager.progress('close');
                    }
                });
            }
        }, onLoadSuccess: function (data) {
            $('#person_certificate_type').combobox('setValue', data[0].id);
        }, onChange: function (n, o) {
            setWuMingShi();
        }
    });
    $('#person_country').combobox('setValue', 156);
    $('#person_nation').combobox('setValue', 1);
    $('#person_name').focus();
    $('#person_name').select();
    personUrl = contextPath + '/zhfz/bacs/person/insertPerson.do';
}
// 嫌疑人查询
function lawdocSearch() {
    var pName = $('#name').val();
    var cNo = $('#certificateNo').val();
    var rowData1 = $('#lawdocgrid').datagrid('getSelected');
    if(rowData1!=null){
        if(sfaj==2){
            $('#lawdocDetailgrid').datagrid('load', {
                name: pName,
                certificateNo: cNo,
                trefresh: new Date().getTime()
            })
        }else{
            $('#lawdocDetailgrid').datagrid('load', {
                ajbh:rowData1.ajbh,
                name: pName,
                certificateNo: cNo,
                trefresh: new Date().getTime()
            })
        }

    }else{
        $('#lawdocDetailgrid').datagrid('load', {
            name: pName,
            certificateNo: cNo,
            trefresh: new Date().getTime()
        })
    }

}
function enterKeyEvents2() {
    var e = e || window.event;
    if (e.keyCode == 13) {
        var zjhm = $('#person_certificate_no').val();
        jingzongByzjhm(zjhm);
    }
}
// 嫌疑人查询清除
function doClear() {
    $('#s_lawdoc_personname').textbox('setValue', '');
    $('#s_lawdoc_certifacateno').textbox('setValue', '');
    $("#s_areaId").combobox("setValue", "");
}

//加载办案场所
function loadArea() {
    $('#s_areaId').combobox({
        url: contextPath + '/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(data != null && data.length > 0){
                $('#s_areaId').combobox('setValue',data[0].id);
            }*/
        }
    });
}
function checkCertificateNo() {
    var certificateNo = $('#person_certificate_no').val();
    var person_certificate_type = $('#person_certificate_type').val();
    if (person_certificate_type == 111) {//身份证
        var idErrorMessage = checkId(certificateNo)
        if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {

        } else {
            $.messager.alert('提醒', idErrorMessage);
        }
    }

    if (18 == certificateNo.length) {
        // 判断性别
        var num = certificateNo.charAt(16);
        if (num % 2 == 0) {
            $('#person_sex').combobox('setValue', 2);
        } else {
            $('#person_sex').combobox('setValue', 1);
        }
        // 判断年龄
        var birthYear = certificateNo.substring(6, 10);
        var nowYear = new Date().getFullYear();
        if (nowYear - birthYear >= 18) {
            //alert("已成年" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '成年人');
        } else if (nowYear - birthYear >= 60) {
            //alert("老年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '老年人');
        } else if (nowYear - birthYear < 18) {
            //alert("未成年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", true);
            $('#person_personInfo').combobox('setValue', '未成年人');
        }
    } else if (15 == certificateNo.length) {
        // 判断性别
        var num = certificateNo.charAt(14);
        if (num % 2 == 0) {
            $('#person_sex').combobox('setValue', 2);
        } else {
            $('#person_sex').combobox('setValue', 1);
        }
        // 判断年龄
        var birthYear = certificateNo.charAt(6) + certificateNo.charAt(7);//年份
        if (parseInt(birthYear) < 14) {
            birthYear = '20' + birthYear;//年份
        }
        else {
            birthYear = '19' + birthYear;//年份
        }
        var nowYear = new Date().getFullYear();
        if (nowYear - birthYear >= 18) {
            //alert("已成年" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '成年人');
        } else if (nowYear - birthYear >= 60) {
            //alert("老年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", false);
            $('#person_personInfo').combobox('setValue', '老年人');
        } else if (nowYear - birthYear < 18) {
            //alert("未成年人" + birthYear + nowYear);
            $("#isJuvenile").prop("checked", true);
            $('#person_personInfo').combobox('setValue', '未成年人');
        }
    }


    /*var  zjhm =$('#person_certificate_no').val();
    jingzongByzjhm(zjhm);*/

    /* var e = e || window.event;
     if (e.keyCode == 13) {
         var  zjhm =$('#person_certificate_no').val();
         jingzongByzjhm(zjhm);
     }*/
}
//一键接收
function yjAccept() {
    if (tempId == null) {
        $.messager.alert('提示', '请先选择待接收的物品!');
        return
    } else {

    }
}

//一键拒绝
function yjRefuse() {
    //获取登录人的信息，判断是否是接收单位的人
    if (tempId == null) {
        $.messager.alert('提示', '请先选择待接收的物品!');
        return
    }
    var rowData = $('#lawdocgrid').datagrid('getSelected');
    console.log(rowData)
    var jsdwId = rowData.jsdwId;
    $.get(contextPath + "/common/getSessionInfo.do", function (data) {
        var sessionObj = eval('(' + data + ')');
        console.log(sessionObj)
        if (sessionObj.currentOrg.orgCode != jsdwId) {
            $.messager.alert('提示', '请选择用正确的接收单位登录，进行物品接收!');
            return
        } else {
            $.messager.confirm('拒绝接收确认', '是否拒绝接收此数据？', function (r) {
                if (r) {
                    // lawdocgrid 更新物品状态，已拒绝
                    jQuery.ajax({
                        type: 'GET',
                        url: 'updateBelongTempStatusById.do?tempId=' + data[0].id,
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
                }

            });
        }
    });

}

function loadSex() {
    codeCombo('person_sex','XBID','');
}
function loadSexadd() {
    codeCombo('addperson_sex','XBID','');
}
function loadSexEdit() {
    codeCombo('edit_sex','XBID','');
}
//加载特殊身份
function loadSpecialIdentity(){
    $('#special_identity,#specialIdentity,#edit_specialIdentity,#addspecial_identity,#person_specialIdentity').combobox({
        data: [{
            label: '记者',
            value: '记者'
        },{
            label: '律师',
            value: '律师'
        },{
            label: '社会公众人物或网络人',
            value: '社会公众人物或网络人'
        },{
            label: '县以上人大代表',
            value: '县以上人大代表'
        },{
            label: '区县以上政协委员',
            value: '区县以上政协委员'
        },{
            label: '民主党派区县以上组织负责人',
            value: '民主党派区县以上组织负责人'
        },{
            label: '宗教界神职人员',
            value: '宗教界神职人员'
        },{
            label: '文化艺术、体育界知名人士',
            value: '文化艺术、体育界知名人士'
        },{
            label: '副局级以上干部',
            value: '副局级以上干部'
        },{
            label: '省军级以上干部的直系亲属',
            value: '省军级以上干部的直系亲属'
        }
            ,{
                label: '其他',
                value: '其他'
            }],
        valueField:'value',
        textField:'label'
    });
}
//加载人员类型
function loadPersonInfo(id, value)
{
    //加载人员类型
    var personInfo = value;
    $(id).combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        },{
            label: '老年人',
            value: '老年人'
        },{
            label: '特殊疾病',
            value: '特殊疾病'
        },{
            label: '孕妇',
            value: '孕妇'
        },{
            label: '残疾人',
            value: '残疾人'
        },{
            label: '成年人',
            value: '成年人'
        }],
        valueField:'value',
        textField:'label',
        onLoadSuccess: function(data){
            $(id).combobox('setValue', personInfo);
        },
        onChange:function(value)
        {
            var eisJuvenile=document.getElementsByName("eisJuvenile");//未成年人
            var eisGravida=document.getElementsByName("eisGravida");//孕妇

            if(value=='未成年人')
            {
                eisJuvenile[0].checked = true;
                eisGravida[0].checked = false;
            } else if (value=='孕妇')
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = true;
            }else
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = false;
            }
        }
    });
}
function saveAddSuspectinfo() {
    var rowData1 = $('#lawdocgrid').datagrid('getSelected');
    if (!checkForm('#dialog_add_suspectinfo')) {
        $.messager.alert('提示', '请补全信息!');
        return;
    }
    var certificateNo = $('#person_certificate_no').val();
    var person_certificate_type = $('#person_personInfo').val();
    if (person_certificate_type == 111) {//身份证
        var idErrorMessage = checkId(certificateNo)
        if (idErrorMessage == null || idErrorMessage == "" || idErrorMessage == true) {

        } else {
            $.messager.alert('提醒', idErrorMessage);
            return;
        }
    }
    var personList = $("#addsuspectinfoform").serializeObject();
    //personList["orderRequestId"] = orderRequestDataId;
    //personList["areaId"] = rowData1.areaId;
    // personList["rybh"] = rowData1.ajbh;
    // personList["ajbh"] = rowData1.ajbh;
    // personList["ajmc"] = rowData1.ajmc;
    personList["certificateNo"] = $("#person_certificate_no").val();
    personList["certificateTypeName"] = $('#person_personInfo').val();
    personList["name"] = $('#person_name').val();
    personList["nation"] = $('#person_nation').val();
    personList["country"] = $('#person_country').val();
    personList["certificateTypeId"] = $('#person_certificate_type').val();
    personList["sex"] = $('#person_sex').val();
    if(sfaj==1){
        personList["caseId"] = rowData1.id;
    }
    var personsJson = JSON.stringify(personList);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        //url : 'addPerson.do',
        //url: url,
        url: personUrl,
        data: personsJson,
        dataType: 'json',
        success: function (data) {
            lawdocSearch();
            $('#dialog_add_suspectinfo').dialog('close');
            $.messager.show({
                title: "提示",
                msg: data.content,
                timeout: 3000
            });
            $.messager.progress('close');
            $('#addperson_certificate_no').val();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '保存!' + data.content);
        }
    });
}
function setWuMingShi(){
    var person_certificate_type=$('#person_certificate_type').combobox('getValue');
    if(person_certificate_type==7){
        $('#person_name').val('无名氏');
        $('#person_certificate_no').val('XXXXX.....XXXXX');
    }
}
//加载人员类型
function loadPersonInfoEdit(id, value)
{
    //加载人员类型
    var personInfo = value;
    $(id).combobox({
        data: [{
            label: '未成年人',
            value: '未成年人'
        },{
            label: '老年人',
            value: '老年人'
        },{
            label: '特殊疾病',
            value: '特殊疾病'
        },{
            label: '孕妇',
            value: '孕妇'
        },{
            label: '残疾人',
            value: '残疾人'
        },{
            label: '成年人',
            value: '成年人'
        }],
        valueField:'value',
        textField:'label',
        onLoadSuccess: function(data){
            $(id).combobox('setValue', personInfo);
        },
        onChange:function(value)
        {
            var eisJuvenile=document.getElementsByName("edit_eisJuvenile");//未成年人
            var eisGravida=document.getElementsByName("edit_eisGravida");//孕妇

            if(value=='未成年人')
            {
                eisJuvenile[0].checked = true;
                eisGravida[0].checked = false;
            } else if (value=='孕妇')
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = true;
            }else
            {
                eisJuvenile[0].checked = false;
                eisGravida[0].checked = false;
            }
        }
    });
}
function onclickRadio(a, b) {
    var sf = $("input[name=" + b + "]:checked").val();
    if (sf == 0) {
        $('#' + a + '').val("无");
    }else{
        $('#' + a + '').val("");
    }
}
function loadNation(nation){
    nation = nation?nation:1;
    jQuery.ajax({
        type: 'get',
        async: true,
        dataType: 'json',
        url:contextPath+"/zhfz/common/code/listCodeByType.do?type=MZID",
        success: function (data) {
            nationlist=data;
            if(data!=null){
                $('#person_nation').combobox({
                    valueField:'codeKey',
                    textField:'codeValue',
                    onLoadSuccess : function() {
                        $('#person_nation').combobox('setValue',nation);
                    }
                });
                $('#edit_nation').combobox({
                    valueField:'codeKey',
                    textField:'codeValue',
                    onLoadSuccess : function() {
                        $('#edit_nation').combobox('setValue',nation);
                    }
                });
                $('#addperson_nation').combobox({
                    valueField:'codeKey',
                    textField:'codeValue',
                    onLoadSuccess : function() {
                        $('#addperson_nation').combobox('setValue',nation);
                    }
                });
                $('#person_nation').combobox( 'loadData',data);
                $('#edit_nation').combobox( 'loadData',data);
                $('#addperson_nation').combobox( 'loadData',data);
            }
        },
        error:function(data){
            console.log(data);
        }
    });
}
//加载添加预约信息的办案场所下拉框
function loadOrderArea(areaId){
    $('#orderarea').combobox({
        url: 'comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(areaId == null || areaId == "" || areaId == 0){
                $('#orderarea').combobox('setText',"请选择办案场所");
            } else{
                $('#orderarea').combobox("setValue",areaId);
            }*/
            $('#orderarea').combobox("setValue",data[0].id);
        },
        onChange: function (newValue, oldValue) {
        }
    });
    $('#editorderarea').combobox({
        url: 'comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            if(areaId == null || areaId == "" || areaId == 0){
                $('#editorderarea').combobox('setText',"请选择办案场所");
            } else{
                $('#editorderarea').combobox("setValue",areaId);
            }
        },
        onChange: function (newValue, oldValue) {
        }
    });
}
