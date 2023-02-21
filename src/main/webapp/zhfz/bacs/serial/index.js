var currenPage = 1;//当前第几页
var pageSize = 999;//每页数量
var totalPage = 0;//数据总数量
var currenMap = {};//当前集合的map集合  key：serailid  value：serail对象
var searchMap = {};//查询条件的集合
//保存调用摄像头的功能，用于对不同的地方调用摄像头做出不同的反馈
var phototype="";
//保存当前浏览器信息
var browser;
$(function(){
    caseAjlyCode = 1;
    init();
    initSerialData();
    loadArea();
    initStatus();
    //禁止ajax的缓存
    $.ajaxSetup({ cache: false });
    //解决IE下只读INPUT键入BACKSPACE 后退问题
    $("input[readOnly]").keydown(function(e) {
        e.preventDefault();
    });
    browser = myBrowser();
});

function initStatus(){
    $('#serial_status').combobox({
        valueField: 'value',
        textField: 'label',
        data: [{
            label: '候问中',
            value: '3'
        },{
            label: '审讯中',
            value: '6'
        },{
            label: '临时出区',
            value: '10'
        }]
    });
}
var rowData;
function initSerialData(){
    var data = {};
    data['page'] = currenPage;
    data['rows'] = pageSize;
    $.each(searchMap, function(name, value) {
        if(value != null && value != ""){
            data[name] = value;
        }
    });
    data['status'] = 11;
    data["trefresh"] = new Date().getTime();
    $.ajax({
        contentType : 'application/json',
        url : "list.do",
        data : data,
        dataType : 'json',
        success : function(data){
            rowData = data;
            $("#serialItems").html('');
            currenMap = {};
            var rows = data.rows;
            var count = data.total;
            var serialItemsHtml = '';
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                var caozuo = '<div>';
                var genduo = "";
                var browsern = browser["browser"];
                if(browsern == "Microsoft IE" ){
                    genduo += '<div class="m-btn-1 blue open-btn" style="display:inline-block;height:25px;line-height: 25px;border-top-width: 3px;border-bottom-width: 0px;"><span style="font-size: 13px;font-family: "'+"微软雅黑"+'">更多</span><div><ul style="height: 70px;">';
                }else{
                    genduo += '<div class="m-btn-1 blue open-btn" style="display:inline-block;height:26px;line-height: 26px;"><span style="font-size: 13px;font-family: "'+"微软雅黑"+'">更多</span><div><ul style="height: 70px;">';
                }
                var cazuoflag = 0;
                if (row.status < 11) {
                    if (isGridButton("personEntranceEdit")) {
                        caozuo += '<button class="m-btn-1 blue" onclick="SignTaizhang('+row.id+')">签字</button>';
                        cazuoflag = cazuoflag +1;
                    }
                    if (isGridButton("exitBegin2")) {
                        caozuo += '<button class="m-btn-1 blue" onclick="exitBegin2('+row.id+')">出区</button>';
                        cazuoflag = cazuoflag +1;
                    }
                    if (isGridButton("printChoose")) {
                        caozuo +='<button class="m-btn-1 blue" onclick="PringTaizhang('+row.id+",'"+row.uuid+"',"+row.areaId+')">打印</button>';
                        cazuoflag = cazuoflag +1;
                    }
                    if (isGridButton("photoEdit")) {
                        caozuo += '<button class="m-btn-1 blue" onclick="photoEdit('+row.id+')">照片</button>';
                        cazuoflag = cazuoflag +1;
                    }
                    if (row.status == 10) {
                        if (isGridButton("tempBack")) {
                            if(cazuoflag > 4){
                                genduo += '<li class="m-btn-1 blue" onclick="tempBack('+i+')">返回</li>';
                            }else {
                                caozuo += '<button class="m-btn-1 blue" onclick="tempBack('+i+')">返回</button>';
                                cazuoflag = cazuoflag +1;
                            }
                        }
                    }
                    if(cazuoflag > 4){
                        genduo += '<li onclick="showDetail1('+row.id+')" style="text-align: center"  class="m-btn-1 blue">详情</li>';
                    }else{
                        caozuo += '<button onclick="showDetail1('+row.id+')" class="m-btn-1 blue">详情</button>';
                        cazuoflag = cazuoflag +1;
                    }
                    if (row.confirmTime == null || row.confirmTime == '') {
                        if (isGridButton("confirmButton")) {
                            if(cazuoflag > 4){
                                genduo += '<li class="m-btn-1 blue" style="text-align: center" onclick="confirmButton('+row.id+')">裁决</li>';
                            }else {
                                caozuo += '<button class="m-btn-1 blue" onclick="confirmButton('+row.id+')">裁决</button>';
                                cazuoflag = cazuoflag + 1;
                            }
                        }
                    }
                    if (isGridButton("personEntranceEdit")) {
                        if(cazuoflag > 4){
                            genduo += '<li class="m-btn-1 blue" style="text-align: center" onclick="entranceEdit('+row.id+')">修改</li>';
                        }else {
                            caozuo += '<button class="m-btn-1 blue" onclick="entranceEdit('+row.id+')">修改</button>';
                            cazuoflag = cazuoflag + 1;
                        }
                    }
                    // 关联以前的入区情况
                    if (isGridButton("guanlianButton")) {
                        if(cazuoflag > 4){
                            genduo += '<li class="m-btn-1 blue" style="text-align: center" onclick={relateButton("'+row.id+'");event.stopPropagation();}>关联</li>';
                        }else {
                            caozuo += '<button class="m-btn-1 blue" onclick=relateButton("'+row.id+'")>关联</button>';
                            cazuoflag = cazuoflag +1;
                        }
                    }
                }
                var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoId);
                serialItemsHtml += '<div class="item"><div class="item-in m-box"><div class="left">' +
                    '<img src="'+e+'" onerror=this.src='+'"image/noPhoto.jpg"'+'></div><div class="right">' +
                    '<p><i>姓&emsp;&emsp;名：</i><span>'+row.name+'（&nbsp;'+row.certificateNo+'&nbsp;）'+'</span></p><p><i>入区时间：</i>' +
                    '<span>'+formatDateTimeText(new Date(row.inTime))+'</span></p>' +
                    '<p><i>案&emsp;&emsp;由：</i><span>'+formatterColunmVal(row.caseNature, 16)+'</span></p><p><i>办案单位：</i><span>'+formatterColunmVal(row.organization, 16)+'</span></p>';
                if(cazuoflag > 4 ){
                    serialItemsHtml += caozuo + genduo + '</ul></div></div></div></div></div></div>';
                }else{
                    serialItemsHtml += caozuo +'</div></div></div></div>';
                }
                currenMap[row.id] = row;
            }
            $("#serialItems").html(serialItemsHtml);
            totalPage = (parseInt((count-1)/pageSize)+1);
            $("#pageLable").html(currenPage + "/" + totalPage);
            $("#totalSpan").html('当前显示'+((currenPage-1)*pageSize+1)+'-'+(currenPage*pageSize)+'条，共'+count+'条');
        },
        error : function(){
            $.messager.progress('close');
            $.messager.alert('错误', '系统错误!');
        }
    });
}

function showDetail1(id){

    $('#serailDetail').html(xqHtml(currenMap[id]));
    showDialog('#serailDetail', '入出区详情');
}

function toPage(type){
    switch(type){
        case 'first' : currenPage =1; initSerialData(); break;
        case 'last'  : if(currenPage!=1) currenPage--; initSerialData(); break;
        case 'next'  : if(currenPage!=totalPage) currenPage++; initSerialData(); break;
        case 'end' : currenPage =totalPage; initSerialData(); break;
    }
}

function init(){
    $(".m-head .user").hover(function(){
        $(this).addClass("open");
    },function(){
        $(this).removeClass("open");
    });
    $(".m-head .user").on("mousemove","*",function(){
        $(this).parents(".user").addClass("open");
    });
    $(".page").on("mouseenter",".open-btn",function(){
        $(this).addClass("active");
    });
    $(".page").on("mouseleave",".open-btn",function(){
        $(this).removeClass("active");
    });
}
//查询
function doSearch(){
    currenPage = 1;
    pageSize = 999;
    var areaId = $("#area_cmb_id").combobox("getValue");
    var status = $("#serial_status").combobox("getValue");
    var name = $('#person_name').val();
    var certificateNo =$('#certificate_no').val();
    var start_date =$('#start_date').datebox('getValue');
    var end_date = $('#end_date').datebox('getValue');
    if(areaId != null){
        searchMap["areaId"] = areaId;
    }else{
        searchMap["areaId"] = "";
    }
    if(name != null){
        searchMap["name"] = name;
    }else{
        searchMap["name"] = "";
    }
    if(certificateNo != null){
        searchMap["certificateNo"] = certificateNo;
    }else{
        searchMap["certificateNo"] = "";
    }
    if(start_date != null){
        searchMap["start_date"] = start_date;
    }else{
        searchMap["start_date"] = "";
    }
    if(end_date != null){
        searchMap["end_date"] = end_date;
    }else{
        searchMap["end_date"] = "";
    }
    if(status != null){
        searchMap["q_status"] = status;
    }else{
        searchMap["q_status"] = "";
    }
    initSerialData();
}


// 临时出区返回
function tempBack(index) {
    var row = rowData.rows[index];
    showDialog('#tempBack','临时出区返回信息');
    $('#tempBackForm').form('load',{
        tbid:row.id,
        tbSerialNo:row.serialNo,
        tbname:row.name,
        tbcertificateNo:row.certificateNo,
        tbcuffNo:row.cuffNo
    });

    if(row.sex==1){
        document.getElementById("tbmale").checked = "checked";
    }else{
        document.getElementById("tbfemale").checked = "checked";
    }
    $('#tbcertificateTypeId').combobox({
        url:'../combobox/certificateTypes.do',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#tbcertificateTypeId').combobox('setValue', row.certificateTypeId);
        }
    });
}

// 临时出区返回保存
function sureBack() {
    var tempBackForm = $('#tempBackForm').serializeObject();
    var entranceData = $('#entranceData').serializeObject();
    entranceData["id"]=tempBackForm["tbid"];
    var jsonrtinfo = JSON.stringify(entranceData);
    $.messager.progress({
        title:'请等待',
        msg:'修改数据中...'
    });
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : 'tempback.do' ,
        data : jsonrtinfo,
        success : function(data){
            $.messager.alert(data.title, data.content);
            initSerialData();
            $('#tempBack').dialog('close');
            $.messager.progress('close');
            $('#tempBackForm').form("clear");
            $('#entranceData').form("clear");
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

//打开裁决框
function confirmButton(serialId){
    $('#choose_form').form("clear");
    $("#confirm_serial_id").val(serialId);
    showDialog('#showConfirmDg', '裁决结果');
}
//保存裁决
function doConformResult(){
    var confirmResult = $('#confirm_result_dg').combobox("getValue");
    if (confirmResult == null || confirmResult == '') {
        $('#showConfirmDg').dialog('close');
        return;
    }
    var serialId = $("#confirm_serial_id").val();
    var entranceInfo = $('#entranceData').serializeObject();
    entranceInfo["id"] = serialId;
    entranceInfo["confirmResult"] = confirmResult;
    var jsonrtinfo = JSON.stringify(entranceInfo);
    $.messager.progress({
        title: '请等待',
        msg: '裁决中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'confirm.do',
        data: jsonrtinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.alert(data.title, data.content);
            initSerialData();
            $.messager.progress('close');
            $('#showConfirmDg').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

//清除查询条件
function doClear(){
    $("#area_cmb_id").combobox("setValue","");
    $("#serial_status").combobox("setValue","");
    $('#person_name').val("");
    $('#certificate_no').val("");
    $("#status").combobox("setValue","");
    $('#start_date').datebox('setValue',"");
    $('#end_date').datebox('setValue',"");
}
//base64转换为Blob对象
function b64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;
    var byteCharacters = atob(b64Data);
    var byteArrays = [];
    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        var slice = byteCharacters.slice(offset, offset + sliceSize);
        var byteNumbers = new Array(slice.length);
        for (var i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
        }
        var byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
    }
    var blob = new Blob(byteArrays, { type: contentType });
    return blob;
}
//快速出区
function exitBegin2(serialId){
    var row = currenMap[serialId];
    if (row.status == 11) {
        $.messager.alert('警告', "抱歉," + row.name + "已经出区！");
    } else {
        phototype = 'exitBegin';
        $("#outPopup").show();
        showNoHideStepDiv("stepOutDiv1", "outStep1", 1);
        showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
        showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
        showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
        $("#dataType").combobox({
            onChange:function(value){
                getFirstReason();
            }
        });
        $("#dataType").combobox('setValue', "");
        $("#direction").combobox('setValue', '');
        $("#confirm_result").combobox('setValue', '');
        $("#outPhoto").attr("src","image/default.jpg");
        var serialNo = row.serialNo;
        $('#serialNo').combogrid({
            panelWidth: 360,
            mode: 'remote',
            url: '../../common/combogrid/getSuspectSerialNo.do',
            idField: 'serialNo',
            textField: 'name',
            columns: [[{
                field: 'serialNo',
                title: '入区编号',
                width: 135
            }, {
                field: 'name',
                title: '姓名',
                width: 60
            }, {
                field: 'certificateNo',
                title: '证件号码',
                width: 150
            }]],
            onChange: function (newValue, oldValue) {
                var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
                var row = cg.datagrid('getSelected'); // 获取选择的行
                if (row == null) {
                    $('#serialNo').combogrid('setValue',"");
                }
                else {
                    $('#cuffNumber').val(row.cuffNo);
                    $("#outSendUserName").val(row.sendUserName);
                    $("#outSendUserId").val(row.sendUserId);
                    $("#outAjmc").val(row.ajmc);
                    var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoName);
                    $("#inphoto").attr("src",e);
                    console.log(row);
                }
            },
            onLoadSuccess: function (data) {
                $('#serialNo').combogrid('setValue', serialNo);
                $('#serialId').val(row.id);
            }
        });
    }
}
//获取项目根路径
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
//加载办案场所
function loadArea(){
    $('#area_cmb_id').combobox({
        url: '../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            if(data != null && data.length > 0){
                $('#area_cmb_id').combobox('setValue',data[0].id);
            }
        }
    });
}