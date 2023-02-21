$(function(){
     $('#datagrid').datagrid({
         iconCls:'icon-datagrid',
         region: 'center',
         width:'100%',
         height:'100%',
         fitColumns:true,
         nowrap: false,
         striped: true,
         collapsible:false,
         loadMsg: '加载数据中...',
         method: 'get',
         url: '../receptionentrance/receptionHistoryList.do',
         queryParams : {
             trefresh:new Date().getTime()
         },
         sortName: 'id',
         sortOrder: 'desc',
         remoteSort: false,
         idField:'id',
         singleSelect:true,
         frozenColumns:[[
             {field:'ck',checkbox:true},
             {title:'ID',field:'id',width:80,sortable:true,hidden:true},
             {title:'案件id',field:'bCaseId',width:80,sortable:true,hidden:true}
         ]],
         columns:[[
             {field:'name',title:'姓名',width:15,
                 formatter:function(value,rec){
                     return '<font color="yellow">'+value+'</font>';
                 }},
             {field:'department',title:'访客单位',width:20},
             {field:'certificateNo',title:'证件号',width:40},
             {field:'phone',title:'手机号',width:40},
             {field:'reason',title:'事由',width:15,},
             {field:'receiver',title:'经办人',width:15,},
             {field:'areaName',title:'办案场所',width:25},
             {field:'card',title:'卡号',width:40},
             {field:'inTime',title:'入区时间',width:40,
                 formatter:function(value,rec){
                     return valueToDate(value);
                 }
             },
             {field:'outTime',title:'出区时间',width:40,
                 formatter:function(value,rec){
                   if(rec.outTime == null || rec.outTime == ""){
                       return '<font color="yellow">'+'未出区'+'</font>';
                   }
                     return valueToDate(value);
                 }
             },
             {
                 field : '操作',
                 title : '操作',
                 width : 20,
                 formatter : function(value, row, index) {

                     if (row.outTime == null || row.outTime == ""){
                         return '<span class="spanRow"><a href="#" class="gridout" onclick="exitList('+row.id+')">出区</a></span>';
                     }
                 }
             }
         ]],
         pagination:true,
         pageList: [10,20,30,40,50,100],
         rownumbers:true,
         toolbar:'#toolbar'
     });
    var p = $('#datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(){
            $('#datagrid').datagrid('load', {
                trefresh:new Date().getTime()
            });
        }
    });
  $('#fudong').remove();

});


function  exitList(id) {
    jQuery.ajax({
        type : 'POST',
        async : false,
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/receptionentrance/updateOutTime.do',
        data : {'receptionEntranceId':id},
        success : function(data){
            initEntranceData();
            $('#datagrid').datagrid('load',{
                trefresh:new Date().getTime()
            })
            $.messager.show({
                title:data.title,
                msg:data.content,
                timeout:3000
            });
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

function doSearch(){
    $('#datagrid').datagrid('load',{
        name:$('#s_name').val(),
        certificateNo: $('#s_certificateNo').val(),
        startTime:$('#s_start_date').datebox('getValue'),
        endTime:$('#s_end_date').datebox('getValue'),
        trefresh:new Date().getTime()
    })
}

function doClear() {
    $('#s_name').val("");
    $('#s_certificateNo').val("");
    $('#s_start_date').datebox('setValue',"");
    $('#s_end_date').datebox('setValue',"");
}