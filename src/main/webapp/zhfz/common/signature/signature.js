$(function(){
    // loadSignatureDatagrid();
    // loadArea();
    $('#fudong').remove();
    upload_swfupload_load();
})
//加载签章列表
function loadSignatureDatagrid(){
    $('#signatureDatagrid').datagrid({
        iconCls:'icon-datagrid',
        region: 'center',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '加载中.....',
        method: 'get',
        url: '../signfile/list.do',
        queryParams : {
            trefresh:new Date().getTime()
        },
        remoteSort: false,
        singleSelect:true,
        columns:[[
            {field:'name',title:'办案场所',width:80},
            {field:'fileName',title:'签章名称',width:80},
            {field:"id",title:'操作',width:80,
                formatter:function(value,row,index){
                    var e='',d='',f='',g='';
                    if(row.id != null && row.fileName != null &&  row.fileName != ''){
                        if(isGridButton("signDownload")){
                            e ='<span class="spanRow"><a href="#" class="gridedit" onclick="edit('+index+')">下载</a></span>';
                        }
                        if(isGridButton("signUpdate")){
                            g ='<span class="spanRow"><a href="#" class="gridedit" onclick="edit('+index+')">重新上传</a></span>';
                        }
                        if(isGridButton("signDelte")){
                            d ='<span class="spanRow"><a href="#" class="griddel" onclick="remove('+value+')">删除</a></span>';
                        }
                    }
                    if(isGridButton("signAdd")){
                        f ='<span class="spanRow"><a href="#" class="griddel" onclick="startUpload()">上传</a></span>';
                    }
                    return e + d + f + g;
                }}
        ]],
        rownumbers:true,
        toolbar:'#certificateToolbar'
    });
    var p = $('#signatureDatagrid').datagrid('getPager');

    $(p).pagination({
        onBeforeRefresh:function(){
            $('#signatureDatagrid').datagrid('reload',{
                trefresh:new Date().getTime()});
            return false;
        }
    });
}
function startUpload(){
    upload_swfupload.selectFiles();
}
function loadArea(areaId){
    $('#s_area').combobox({
        url:'../combobox/listArea.do',
        valueField:'id',
        textField:'value',
        onLoadSuccess:function(){
            if(areaId != null && areaId != ""){
                $("#s_area").combobox("setValue",areaId);
            }
        }
    })
}

function doSearch() {
    $('#signatureDatagrid').datagrid('reload', {
        trefresh: new Date().getTime(),
        serialId: $("#s_area").combobox("getValue")
    });
}

var fileName = new Array();
var fileIndex =0;
var upload_swfupload = {SWFObj:new Object()};
function upload_swfupload_load(){
    var loadSettings = {
        //提交路径
        upload_url : "upload.do",
        //向后台传递额外的参数
        post_params : {
            savePath: "/upload/2015-08-23/",
            urinalysisId:$("#id").val(),
            small : false,
            sw:120,
            sh:120,
            width : 800,
            height: 800,
            inputResult : window.location.href
        },
        file_size_limit : "1024000",
        file_types_description : "图片",
        file_types : "*.jpg;*.jpeg;*.gif;*.png;",
        file_upload_limit : "0",
        file_queue_limit : "0",
        prevent_swf_caching :true,
        // 按钮的处理
        button_image_url : "../../../swfUpload/images/background_c.png",
        button_placeholder_id : "spanButtonPlaceholder1_swfupload",
        button_width : 125,
        button_height : 50,
        upload_success_handler:myUploadSuccess,
        flash_url : "../../../swfUpload/new/swfupload.swf",

        custom_settings : {
            file_post_name : "filedata",
            cancelButtonId : "btnCancel1_swfupload",
            fileSiteName : "",
            fileTypeName : ""
        }
    }
    SWFLoad(upload_swfupload,loadSettings);
}
function myUploadSuccess(fileObj,server_data) {
    fileName[fileIndex]=server_data;
    fileIndex = fileIndex + 1;
    uploadSuccess(fileObj,server_data,"/ictpm",this);
    $.messager.show({
        title:"提示",
        msg:"上传成功",
        timeout:3000
    });
}