var userid = '';
var belongId = '';
var caseNo = '';
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
        url: 'listPerson.do',
        sortName: 'id',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {
                field: 'areaName', title: '办案场所', width: 120,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {
                field: 'personName', title: '嫌疑人', width: 40,
                formatter: function (value, row, index) {
                    return value;
                }
            },
            {field: 'certificateNo', title: '证件号码', width: 100},
            {field: 'ajbh', title: '案件编号', width: 120},
            {field: 'ajmc', title: '案件名称', width: 120},
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
                field: 'abmc', title: '案件性质', width: 90,
                formatter: function (value, rec) {
                    if (value == null || value == "") {
                        return '无';
                    } else {
                        return formatterColunmVal(value, 18);
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
            userid = rowData.personId;
            caseNo = rowData.caseNo;
            belongId = rowData.belongId;
            lawdocDetailgridLoad(rowData);
            flag = rowIndex;
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
        queryParams: "{'enpId':'-99','trefresh':" + new Date().getTime() + "}",
        url: 'listlawdoc.do',
        singleSelect: true,
        columns: [[
            {field: 'name', title: '文书名称', width: 350},
            {
                field: 'id', title: '操作', width: 300,
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '';
                    var p = '';
                    var q = '';
                    /*if(isGridButton("preview") && index < 16){
                        e ='<span class="spanRow"><a href="#" class="gridseeinfo" onclick="preview('+index+')">预览</a></span>';
                    }*/
                    if (isGridButton("download")) {
                        d = '<span class="spanRow"><a href="#" class="griddownload" onclick="downloadfile(' + index + ')">下载</a></span>';
                    }
                    /* if(isGridButton("docPrint")){
                         p ='<span class="spanRow"><a href="#" class="gridprint" onclick="docPrint('+index+')">打印</a></span>';
                     }
                     if(true&&($.inArray(row.number, [12,1,2,3,4,5,6,7,8,9,10,11,16,46,13])>-1)){
                         q ='<span class="spanRow"><a href="#" class="gridprint" onclick="record('+index+')">录入</a></span>';
                     }*/
                    q = '<span class="spanRow"><a href="#" class="griddownload" onclick="downloadfilePdf(' + index + ')">PDF下载</a></span>';

                    return d ;
                }
            }
        ]],
        rownumbers: true,
        toolbar: '#lawdocDetailToolbar'
    });
})

// PDF下载
function downloadfilePdf(index) {
    var row = $('#lawdocgrid').datagrid('getRows')[flag];
    var fileType = 0;
    var pdfDoc = $('#lawdocDetailgrid').datagrid('getRows')[index];

    if (pdfDoc.number == 14) {
        // 入区PDF台账
        fileType = 0;
    } else if (pdfDoc.number == 11) {
        // 人身安检
        fileType = 3;
    } else if (pdfDoc.number == 9) {
        // 随身物品登记
        fileType = 5
    } else if (pdfDoc.number == 2) {
        //出区
        fileType = 2
    } else if (pdfDoc.number == 99) {
        // 临时出区
        fileType = 99
    } else {
        $.messager.alert("提示", "该台账无签字版");
        return;
    }
    printChoose(row.id, row.uuid, row.areaId, fileType);
}

function printChoose(serialId, uuid, areaId, fileType) {

    $.messager.progress({
        title: '请等待',
        msg: '下载中...'
    });
    jQuery.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: '../../common/signfile/getNewPdf.do',
        data: {
            serialId: serialId,
            fileType: fileType,
            trefresh: new Date().getTime()
        },
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            if (data.error) {
                $.messager.alert("提示", data.content);
                return;
            } else {
                var row = data.callbackData.entity;
                if (row) {
                    // var pdfU = fileUtils.url('ba',fileTypeEntity.FILETYPRQZ,uuid,areaId,row.fileName);
                    // fileUtils.read(pdfU,"pdf");
                    fileUtils.load('ba', fileTypeEntity.FILETYPRQZ, uuid, areaId, row.fileName);
                }
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
    $.messager.progress('close');
}

function lawdocDetailgridLoad(rowData) {
    $('#lawdocDetailgrid').datagrid('load', {
        lawdocId: rowData.id,
        userId: userid,
        trefresh: new Date().getTime()
    });
}

// 嫌疑人查询
function lawdocSearch() {
    var pName = $('#s_lawdoc_personname').val();
    var cNo = $('#s_lawdoc_certifacateno').val();
    $('#lawdocgrid').datagrid('load', {
        personName: pName,
        certificateNo: cNo,
        areaId: $('#s_areaId').combobox("getValue"),
        trefresh: new Date().getTime()
    })
}

// 嫌疑人查询清除
function doClear() {
    $('#s_lawdoc_personname').textbox('setValue', '');
    $('#s_lawdoc_certifacateno').textbox('setValue', '');
    $("#s_areaId").combobox("setValue", "");
}


// 文书预览
function preview(index) {

    if (index < 16) {
        if (serialId != null && serialId != "") {
            $.messager.progress({
                title: '请等待',
                msg: '预览中...'
            });
            jQuery.ajax({
                type: 'POST',
                dataType: 'json',
                url: '../../bacs/checkbody/selectid.do',
                data: {serialId: serialId},
                success: function (rowData) {
                    console.log(rowData);
                    var number = 999;
                    var name = "违法人员饮食和休息登记表";
                    var userId = rowData.personId;
                    //alert("======userid====="+userId);
                    var type = 1;
                    var checkbodyId = rowData.id;
                    //测试---------------------------------
                    $('#number1').val(999);
                    $('#name').val("违法人员饮食和休息登记表");
                    $('#type').val(1);
                    $('#userId1').val(userId);
                    $('#checkbodyId').val(checkbodyId);
                    var checkbodylawdocInfo = $('#lawdocInfo_checkbody').serializeObject();
                    var checkbodylawdocInfojson = JSON.stringify(checkbodylawdocInfo);
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json',
                        url: '../../bacs/checkbody/preview.do',
                        data: checkbodylawdocInfojson,
                        success: function (data) {
                            var winWidth = 0;
                            if (window.innerWidth)
                                winWidth = window.innerWidth;
                            else if ((document.body) && (document.body.clientWidth))
                                winWidth = document.body.clientWidth;
                            window.open("inframe1.jsp?content=" + data.content + "&number=" + number + "&name=" + name + "&type=" + type + "&userId=" + userId + "&checkbodyId=" + checkbodyId, 'newwindow', 'height=' + 500 + ', width=' + (window.screen.availWidth) + ', top=0,left=' + (winWidth - 1100) / 2 + ', toolbar=yes, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
                            $.messager.progress('close');
                        },
                        error: function (data) {

                        }
                    });
                },
                error: function (data) {
                    $.messager.progress('close');
                    $.messager.alert("error", "该嫌疑人还未做体检");

                }
            })
        }
    }
}


//文书下载
function downloadfile(index) {
    var rowData = $('#lawdocDetailgrid').datagrid('getRows')[index];
    console.log(rowData);
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
    // $('#serialNo').val(serialNo);
    // $('#serialId').val(serialId);
    //$('#caseNo').val(caseNo);

    /* if(index == 43 ){
          if(caseNo == null || caseNo == ""){

          }else {
              document.getElementById("exportForm").submit();
              $.messager.progress('close');
          }
     }else{*/

    if (userId != null && (userId != "" || index == 24 || index == 25 || index == 26 || index == 27 || index == 29 || index == 30 || index == 32 || index == 33 || index == 40 || index == 41 || index == 43 || index == 44)) {
        $.messager.progress({
            title: '请等待',
            msg: '下载中...'
        });
        document.getElementById("lawdocInfo").submit();
        $.messager.progress('close');
    } else {
        $.messager.alert('提示', '请先选择左侧相应案件!');
        $.messager.progress('close');
    }
    //  }
}

//寄押决定书、寄押审批表
function doc3435(index) {
    zeroModal.show({
        unique: 'doc3435',
        title: '提解审批表、通知书',
        iframe: true,
        url: 'docinput/doc3435.jsp',
        width: '400px',
        height: '80%',
        forbidBodyScroll: true,
        buttons: [{
            className: 'btn btn-success btn-sm',
            name: '确定',
            fn: function (opt) {
                var param = $("iframe").contents().find("#doc2526Form").serializeObject();
                if (param.xm == "" || param.xm == null) {
                    swal({
                        title: '请填写被提解人姓名',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.nl == "" || param.nl == null) {
                    swal({
                        title: '请填写被提解人年龄',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.zz == "" || param.zz == null) {
                    swal({
                        title: '请填写被提解人住址',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.ay == "" || param.ay == null) {
                    swal({
                        title: '请填写案由',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.jycs == "" || param.jycs == null) {
                    swal({
                        title: '请填写寄押处所',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.jysj == "" || param.jysj == null) {
                    swal({
                        title: '请填写寄押执行时间',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.tjdw == "" || param.tjdw == null) {
                    swal({
                        title: '请填写提解单位',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.tjr == "" || param.tjr == null) {
                    swal({
                        title: '请填写提解人',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.xzdw == "" || param.xzdw == null) {
                    swal({
                        title: '请填写协助单位',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.xzr == "" || param.xzr == null) {
                    swal({
                        title: '请填写协助人',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.pzr == "" || param.pzr == null) {
                    swal({
                        title: '请填写批准人',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.pzsj == "" || param.pzsj == null) {
                    swal({
                        title: '请填写批准时间',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.tfr == "" || param.tfr == null) {
                    swal({
                        title: '请填写填发人',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                if (param.tfsj == "" || param.tfsj == null) {
                    swal({
                        title: '请填写填发时间',
                        timer: 1000,
                        showConfirmButton: false,
                        type: "error",
                        animation: "slide-from-top"
                    });
                    return false;
                }
                param.tjdw = $("iframe").contents().find("#tjdw option:selected").text();
                param.xzdw = $("iframe").contents().find("#xzdw option:selected").text();
                console.log(param);
                $.ajax({
                    url: "../bacs/lawdocProcess/downloadparam.do",
                    type: "post",
                    dataType: "json",
                    data: param,
                    success: function () {
                        if (index == 35) {
                            $('#number').val('38');
                            document.getElementById("lawdocInfo").submit();
                            swal({
                                title: "下载",
                                text: "已下载提解通知书，需要下载提解审批表吗？",
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                animation: "slide-from-top",
                                confirmButtonText: "下载",
                                cancelButtonText: "取消",
                                closeOnConfirm: true
                            }, function () {
                                $('#number').val('37');
                                document.getElementById("lawdocInfo").submit();
                            });
                        }
                        if (index == 34) {
                            $('#number').val('37');
                            document.getElementById("lawdocInfo").submit();
                            swal({
                                title: "下载",
                                text: "已下载提解审批表，需要下载提解通知书吗？",
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                animation: "slide-from-top",
                                confirmButtonText: "下载",
                                cancelButtonText: "取消",
                                closeOnConfirm: true
                            }, function () {
                                $('#number').val('38');
                                document.getElementById("lawdocInfo").submit();
                            });
                        }
                    }
                })
            }
        }, {
            className: 'btn btn-danger btn-sm',
            name: '取消',
            fn: function (opt) {

            }
        }]
    });
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