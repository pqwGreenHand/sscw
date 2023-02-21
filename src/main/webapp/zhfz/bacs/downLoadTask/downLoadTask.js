var serialSelect= "";
$(function () {
    loadCaseANdPerson({});
    loadArea();
    loadDownLoadtask(-1);
    $('#fudong').remove();
})
//加载案件树
function loadCaseANdPerson(searchData){
    $.messager.progress({
        title:'请等待',
        msg:'加载数据中...'
    });
    jQuery.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'getCaseAndPersonTree.do',
        data : searchData,
        dataType : "json",
        success : function(data){
            $.messager.progress('close');
            //加载权限数据
            $('#caseAndPersonTree').tree({
                data:data,
                checkbox: false,
                cascadeCheck: false,
                onClick: function (node) {
                    //1为子节点，0为父节点
                    if(node.type == 1){
                        serialSelect = node.id;
                        loadDownLoadtask(node.id);
                    }
                }
            });
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '数据错误!'+data);
        }
    });
}
//加载下载任务
function loadDownLoadtask(serialId){
    $('#downLoadTaskDatagrid').datagrid({
        //title:'手环',
        iconCls:'icon-datagrid',
        region: 'center',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '加载中...',
        method: 'get',
        url: 'list.do',
        queryParams : {
            trefresh:new Date().getTime(),
            serialId:serialId
        },
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'taskNo',title:'任务编号',width:300,align:'center'},
            {field:'taskType',title:'任务类型',width:300,align:'center',formatter:function(value){
                    //任务类型，1 审讯视频、2 轨迹视频,3 二期平台审讯视频,4 远程辨认视频
                    if(value=='1'){
                        return '审讯视频'
                    }
                    else if(value=='2'){
                        return '轨迹视频'
                    }
                    else if(value=='3'){
                        return '二期平台审讯视频'
                    }else if(value=='4'){
                        return '远程辨认视频'
                    }
                }
            },
            {field:'icNo',title:'IC卡编号',width:150,align:'center'},
            {field:'status',title:'下载状态',width:150,align:'center',formatter:function(value){
                    //状态，0初始化，1下载中，2已完成，3已失败
                    if(value=='0'){
                        return '初始化'
                    }
                    else if(value=='1'){
                        return '下载中'
                    }
                    else if(value=='2'){
                        return '已完成'
                    }else if(value=='3'){
                        return '已失败'
                    }
                }},
            {field:'count',title:'失败次数',width:150,align:'center'},
            {field:'startTime',title:'下载开始时间',width:300,align:'center'},
            {field:'endTime',title:'下载结束时间',width:300,align:'center'},
            {
                field: 'id',
                title: '操作',
                width: 300,
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '';
                    if (isGridButton("reDownLoad")) {
                        if (row.status == 3) {
                            e = '<span class="spanRow"><a href="#" class="gridedit" onclick="reDownLoad(' + row.id + ')">重新下载</a></span>';
                        }
                    }
                    return e;
                }
            }
        ]],
        pagination:true,
        pageSize:20,
        pageList: [20,30,40,50,100],
        rownumbers:true,
    });
    var p = $('#downLoadTaskDatagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(pageNumber, pageSize){
        }
    });
}
//重新下载
function reDownLoad(id){
    $.messager.progress({
        title:'请等待',
        msg:'修改数据中...'
    });
    jQuery.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'reDownLoad.do',
        data : {
            trefresh:new Date().getTime(),
            downLoadTaskId:id
        },
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
            $('#downLoadTaskDatagrid').datagrid("reload",{trefresh:new Date().getTime(),serialId:serialSelect});

        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '数据错误!'+data);
        }
    });
}
//查询
function doSearch(){
    var serachData = {};
    serachData["ajmc"] = $("#s_case_name").val();
    serachData["personName"] = $("#s_person_name").val();
    serachData["sDate"] = $("#start_date").datetimebox("getValue");
    serachData["eDate"] = $("#end_date").datetimebox("getValue");
    serachData["areaId"] = $("#s_area_id").combobox("getValue");
    loadCaseANdPerson(serachData);
}
//加载办案场所下拉框
function loadArea(){
    $('#s_area_id').combobox({
        url: '../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            if(data != null && data.length > 0){
                $('#s_area_id').combobox('setValue',data[0].id);
            }
        }
    });
}

//清除查询条件
function doClear(){
    $("#s_case_name").val("");
    $("#s_person_name").val("");
    $("#s_area_id").combobox("setValue","");
    $("#start_date").datetimebox("setValue","");
    $("#end_date").datetimebox("setValue","");
}