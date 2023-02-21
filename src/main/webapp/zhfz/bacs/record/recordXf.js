var url;

function init() {
    $('#save-serial').combogrid({
        url: '../combobox/listSerial.do',
        panelWidth: 223,
        idField: 'id',
        textField: 'value',
        columns: [[
            {field: 'value', title: '涉案人员', width: 100},
            {field: 'groupName', title: '案件编号', width: 120},
        ]],
        onSelect: function () {
            var data = $('#save-serial').combogrid('grid').datagrid('getSelected');
            $('#save-record-type-id').val(data.status);
            $('#save-police-ask,#save-police-record').combobox('reload', '../combobox/listSerialPolice.do?caseId=' + (parseParam(data.id)['caseId'] || 0));
        }
    });
    // $('#query-person-name').combobox({
    //     url:'../combobox/listSerial.do',
    //     valueField:'id',
    //     textField:'value',
    // });

}
//加载办案场所
function loadArea(){
    $('#interrogatearea').combobox({
        url: '../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('#interrogatearea').combobox('setValue', data[0].id);
        },
        onChange: function (newValue, oldValue) {
        }
    });
}

$(function () {
    initCaseType();
    initRecordType();
   // loadArea();//加载办案场所
    $('#certificategrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载中.....',
        method: 'get',
        url: 'list.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'startTime',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'personName', title: '嫌疑人', width: 80},
            {
                field: 'caseType', title: '案件类型', width: 80, formatter: function (value, row, index) {
                    return ['刑事', '行政', '刑事'][value];
                }
            },
            {field: 'caseDesc', title: '案件性质', width: 80},
            {field: 'policeAskName', title: '询问民警', width: 80},
            {field: 'organizationName', title: '办案部门', width: 140},
            {field: 'policeRecordName', title: '记录民警', width: 80},
            {field: 'roomName', title: '询问地点', width: 80},
            {
                field: 'count', title: '审讯次数', width: 80, formatter: function (value, row, index) {
                    return "第" + value + "次";
                }
            },
            {
                field: 'status', title: '状态', width: 80, formatter: function (value, row, index) {
                    var str = "";
                	if(value==1)
						str = "<font color='#00FF00'>审讯中</font>";
					if(value==3)
						str = "<font color='white'>已完成</font>";
					if(value==0)
						str = "<font color='#00EEEE'>初始化</font>";
                    return str;
                }
            },
            {
                field: 'startTime', title: '开始时间', width: 80, formatter: function (value, row, index) {
                    return formatter(value, 'yyyy-MM-dd hh:mm:ss');
                }
            },
            {
                field: 'endTime', title: '结束时间', width: 80, formatter: function (value, row, index) {
                    return formatter(new Date(value), 'yyyy-MM-dd hh:mm:ss');
                }
            },
            {field: 'areaName', title: '办案场所', width: 80},
            // {field:'updatedTime',title:'更新时间',width:80,formatter:function (value,row,index) {
            // 		return formatter(new Date(value),'yyyy-MM-dd hh:mm:ss');
            //     }},
            {
                field: 'id', title: '操作', width: 200,
                formatter: function (value, row, index) {
                    /*var e = '';
                    var d ='';
                    var f = '';
                    var g = '';
                    if (row.status == 0 || row.status == 1) {//初始化 or 审讯中
                        if (isGridButton("recordRemove")) {
                            d = '<span class="spanRow" style="width: 80px"><a href="#" class="griddel" onclick="remove(' + index + ')">强制结束</a></span>';
                        }
                        if (isGridButton("recordEdit")) {
                            e = '<span class="spanRow" style="width: 80px"><a href="#" class="gridedit" onclick="start(' + index + ')">继续审讯</a></span>';
                        }

                    } else if (row.status == 2) {//点播中
                        if (isGridButton("recordRemove")) {
                            d = '<span class="spanRow" style="width: 70px"><a href="#" class="griddel" onclick="remove(' + index + ')">强制结束</a></span>';
                        }
                    } else if (row.status == 3) {
                        if (isGridButton("recordLoad")) {
                            g = '<span class="spanRow"><a href="#" class="griddownload" onclick="load(' + index + ')">下载</a></span>';
                        }
                    }
                    return e + d + f + g;*/
                    var e='';
                    var d='';
                    var f='';
                 if(row.status == 1){
                        /*if(isGridButton("recordEditXf")){
                            e ='<span class="spanRow" ><a href="#" class="gridedit" onclick="recordEdit('+index+')">继续审讯</a></span>';
                        }*/
                        if(isGridButton("recordRemoveXf")){
                            d ='<span class="spanRow" ><a href="#" class="griddel" onclick="recordRemove('+index+')">终止审讯</a></span>';
                        }
                    } else if(row.status==2){
                        if(isGridButton("recordRemoveXf")){
                            d ='<span class="spanRow" ><a href="#" class="griddel" onclick="recordRemove('+index+')">终止审讯</a></span>';
                        }
                    }
                    if(row.status==3){
                    	if (isGridButton("recordLoadXf")) {
                        f ='<span class="spanRow" ><a href="#" class="gridseeinfo" onclick="recordInfo('+index+')">查看详情</a></span>';
                    	}
                    }
                    return e + d + f;
                }
            }
        ]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#certificateToolbar'
    });
    var p = $('#certificategrid').datagrid('getPager');

    $(p).pagination({
        onBeforeRefresh: function () {
            //alert('before refresh');
            $('#certificategrid').datagrid('reload', {
                name: $('#q_name').val(),
                trefresh: new Date().getTime()
            });
            return false;
        }
    });
    $('#fudong').remove();
});

function saveEnterprise() {
    if (!checkForm('#save-form')) {
        return;
    }
    var jsonForm = {};
    jsonForm = $('#save-form').serializeObject(jsonForm);
    jsonForm = parseParam(jsonForm.serial, jsonForm);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: url,
        data: jsonForm,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            if(!data.error){
                if(url.indexOf('add') > -1){
                    csStart(data.callbackData.id)
                }else{
                    csStart(jsonForm.id);
                }
                $('#certificategrid').datagrid('reload', {trefresh: new Date().getTime()});// reload the data
                $('#certificate_dialog').dialog('close');
            }

        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function add() {
    init();
    showDialog('#certificate_dialog', '新增智能笔录');
    url = 'add.do'
    $('#save-form').form('clear');
}

function edit(index) {
    url = 'edit.do';
    showDialog('#certificate_dialog', '编辑智能笔录');
    var row = $('#certificategrid').datagrid('getRows')[index];
    $('#save-police-ask,#save-police-record').combobox('reload', '../combobox/listSerialPolice.do?caseId=' + row.caseId);
    row['serial'] = 'serialId:{serialId},personId:{personId},areaId:{areaId},caseId:{caseId}'.format(row);
    $('#save-form').form('load', row);
}

function load(index) {
    var row = $('#certificategrid').datagrid('getRows')[index];
    fileUtils.load('ba','BL',row.serialUuid,row.areaId,row.uuid+'.doc');

}

function remove(index) {
    var row = $('#certificategrid').datagrid('getRows')[index];
    jQuery.ajax({
        type: 'POST',
        url: "checkRoomBeforeStopRecord.do",
        data: row,
        dataType: 'json',
        success: function (data) {
            if (data.error) {
                $.messager.alert(data.title, data.content + '结束询/讯问！');
            } else {
                $.messager.confirm('终止确认', '是否确定强制终止审讯？', function (r) {
                    if (r) {
                        $.messager.progress({
                            title: '请等待',
                            msg: '删除数据中...'
                        });

                        jQuery.ajax({
                            type: 'POST',
                            url: 'stop.do',
                            data: row,
                            dataType: 'json',
                            success: function (data) {
                                $.messager.show({
                                    title: '提示',
                                    msg: data.content,
                                    timeout: 3000
                                });
                                $('#certificategrid').datagrid('reload', {trefresh: new Date().getTime()});// reload the data
                                $.messager.progress('close');
                            },
                            error: function (data) {
                                //exception in java
                                $.messager.progress('close');
                                $.messager.alert('错误', '未知错误!');
                            }
                        });
                    }
                });
            }
        }
    });
}

function start(index) {
    var row = $('#certificategrid').datagrid('getRows')[index];
    jQuery.ajax({
        type: 'POST',
        url: "checkRoomBeforeStopRecord.do",
        data: row,
        dataType: 'json',
        success: function (data) {
            if (data.error) {
                $.messager.alert(data.title, data.content + '继续审讯！');
            } else {
                csStart(row.id);
            }
        }
    });
}

function csStart(id) {
    jQuery.ajax({
        type: 'POST',
        url: contextPath + "/zhfz/common/cshelper/getCsUrl.do",
        data: {type: 0, id: id},
        dataType: 'json',
        success: function (data) {
            if (data.error) {
                $.messager.alert(data.title, data.content);
            } else {
                try{
                    window.location = data.callbackData;
                }catch(e){
                    $.messager.alert("提示", "未正确安装客户端程序！"+e);
                }

            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}


function doSearch() {
    var param = $('#search').serializeObject({trefresh: new Date().getTime()});
    $('#certificategrid').datagrid('load', param);
}

function doClear() {
    $('#search').form('clear');
}

// 加载案件类型
function initCaseType() {
    //加载案件类型
    $('#caseType').combobox({
        data: [{
            label: '刑事',
            value: '1'
        },{
            label: '民事',
            value: '2'
        },{
            label: '行政',
            value: '3'
        },{
            label: '执行',
            value: '4'
        },{
            label: '赔偿',
            value: '5'
        },{
            label: '经济',
            value: '6'
        },{
            label: '知识产权',
            value: '7'
        },{
            label: '海事海商纠纷',
            value: '8'
        }],
        valueField:'value',
        textField:'label'
    });
}

function initRecordType() {
    //加载审讯类型数据
    $('#recordType').combobox({
        data: [{
            label: '询问',
            value: '1'
        },{
            label: '讯问',
            value: '2'
        },{
            label: '谈话',
            value: '3'
        },{
            label: '搜查',
            value: '4'
        },{
            label: '检查',
            value: '5'
        },{
            label: '提取',
            value: '6'
        },{
            label: '调查',
            value: '7'
        },{
            label: '辩认',
            value: '8'
        },{
            label: '侦查实验',
            value: '9'
        }],
        valueField:'value',
        textField:'label',
        onSelect: function(rec){
            selectRecordType(rec.value);
        }
    });
}

// 选择审讯类型联动出笔录类型和问答模板
function selectRecordType(recordType){
    return;
    $('#flageType').combobox("clear");
    $('#recordTemp').combobox("clear");
    var recordTempData;
    jQuery.ajax({
        async : false,
        url : "queryRecordData.do?recordType="+recordType,
        contentType : 'application/json',
        dataType : 'json',
        success : function(data){
            if(!data.error){
                recordTempData = data.callbackData.recordTempData.content;
            }
        }
    });
    var flageTypeData = [{"id":"1001001","name":"行政询问笔录"},{"id":"1001002","name":"行政辨认笔录"},{"id":"1001003","name":"行政检查笔录"},
                     {"id":"1001004","name":"行政勘验笔录"},{"id":"1001005","name":"行政调解笔录"},{"id":"1002001","name":"刑事询问笔录"},
                     {"id":"1002002","name":"刑事讯问笔录"},{"id":"1002003","name":"刑事辨认笔录"},{"id":"1002004","name":"现场辨认笔录"},
                     {"id":"1002005","name":"刑事检查笔录"},{"id":"1002006","name":"刑事扣押笔录"},{"id":"1002007","name":"刑事搜查笔录"}];
    $('#flageType').combobox({
        valueField:'id',
        textField:'name',
        data: flageTypeData
    });
    $('#recordTemp').combobox({
        valueField:'id',
        textField:'name'
    });
    $('#recordTemp').combobox("loadData", recordTempData);

}

// 是否笔录
function clickFlage(flage){
    if(flage.checked){
        $('#flageType').combo({disabled:false, required:true});
        $('#recordTemp').combo({disabled:false, required:true});
    }else{
        $('#flageType').combo({disabled:true, required:false});
        $('#recordTemp').combo({disabled:true, required:false});
    }
}

function esearchUser1(){
    var askNo=$('#askNo').val();
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"]=askNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : 'searchUser.do' ,
        data : jsonrtinfo,
        success : function(data){
            if(data.title=='错误'){
                $.messager.alert(data.title, data.content);
            }else{
//				$('#einUserId1').val(data.callbackData);

            }
        }
    });
}

function esearchUser2(){
    var recodeNo=$('#recodeNo').val();
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"]=recodeNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : 'searchUser.do' ,
        data : jsonrtinfo,
        success : function(data){
            if(data.title=='错误'){
                $.messager.alert(data.title, data.content);
            }else{
//				$('#einUserId2').val(data.callbackData);
            }
        }
    });
}

function searchUserNo(obj){
    var flag=false;
    var userNo = $(obj).val();
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"]=userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        async : false,
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : 'searchUser.do' ,
        data : jsonrtinfo,
        success : function(data){
            //alert("data==="+data);
            if(data.title!='错误'){
                flag=true;
            }
        }
    });
    return flag;
}

function saveRecord(){
    if(!checkForm('#recode_form')){
        return;
    }
    if(!searchUserNo($('#askNo'))){
        $.messager.alert('提醒','询问民警编号有误，不存在！');
        return;
    }
    if(!searchUserNo($('#recodeNo'))){
        $.messager.alert('提醒','记录民警编号有误，不存在！');
        return;
    }

    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');	// 获取选择的行
    var certNo=row.certificateNo;

    if(checkId(certNo)){
        if(getAge(certNo)<18){
            $.messager.alert('提醒','请参照未成年相关法律法规执行！');
        }
        //else{
        //	$.messager.alert('提醒','chengnianl ！');
        //}
    }
    //return;
    var recodeForm = $('#recode_form');
    var obj = recodeForm.serializeObject();

    obj["flageType"]=$('#flageType').combobox('getValue');
//    obj["recordTemp"]=$('#recordTemp').combobox('getValue');
    var jsonrtinfo = JSON.stringify(obj);
    //alert("js="+jsonrtinfo);
    $.messager.progress({
        title:'请等待',
        msg:'正在准备审讯数据中...'
    });
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        data : jsonrtinfo,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            doSearch();// reload the data
            $('#data_dialog').dialog('close');

            if(data.error){
                $.messager.alert(data.title, data.content);
            }else{
                var url = data.callbackData;
                //openWindowMax(url);
                window.location.href=decodeURI(url);
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

function recordInfo(index){
    var row = $('#certificategrid').datagrid('getRows')[index];
    jQuery.ajax({
        contentType : 'application/json',
        url : "toRecordInfo.do?recordId="+row.uuid+"&areaId="+row.areaId,
        dataType : 'json',
        success : function(data){
            if(data.error){
                $.messager.alert(data.title, data.content);
            }else{
                var url = data.callbackData;
                openWindowMax(url);
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

function recordEdit(index){
    var row = $('#certificategrid').datagrid('getRows')[index];
    jQuery.ajax({
        contentType : 'application/json',
        url : "toContiuneRecord.do",
        dataType : 'json',
        data:{"recordId" : row.uuid,'areaId' : row.areaId},
        success : function(data){
            if(data.error){
                $.messager.alert(data.title, data.content);
            }else{
                var url = data.callbackData;
                openWindowMax(url);
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

function recordRemove(index){
    var row = $('#certificategrid').datagrid('getRows')[index];
    $.messager.confirm('删除确认','是否确定强制终止本次审讯？',function(r){
        if (r){
            jQuery.ajax({
                contentType : 'application/json',
                url : "toStopRecord.do?recordId="+row.id+"&uuid="+row.uuid+"&serialId="+row.serialId+"&roomId="+row.roomId+"&areaId="+row.areaId,
                success : function(data){
                    if(data.error){
                        $.messager.alert(data.title, data.content);
                    }else{
                        var url = data.callbackData;
                        $.get(url);
                    }
                    doSearch();
                },
                error : function(data){
                    $.messager.progress('close');
                    $.messager.alert(data.title, data.content);
                }
            });
        }
    });
}



