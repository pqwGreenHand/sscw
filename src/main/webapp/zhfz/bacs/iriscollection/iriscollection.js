$(function () {
    initArea();
    $('#iris_datagrid').datagrid({
        iconCls:'icon-datagrid',
        region:'center',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap:false,
        striped:true,
        collapsible:false,
        loadMsg:'加载中.....',
        method:'get',
        url:'list.do',
        queryParams:{
            trefresh:new Date().getDate()
        },
        sortName:'id',
        sortOrder:'desc',
        remoteSort:false,
        idField:'id',
        singleSelect:true,

        frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'leftEye',title:'左眼数据',width:120},
            {field:'rightEye',title:'右眼数据',width:120},
            {field:'leftImg',title:'左眼图片',width:150,
            formatter:function (value,row,index) {
                var e = fileUtils.url("ba","hm",row.uuid,row.areaId,row.leftImg);
               w ='<div style="margin-top: 3px "  onmousemove="this.style.cursor=\'pointer\'">'
                   + '<img src="'+e+'"  onmouseover="onBigImage(event,this)" onmouseout="mvBigImage()" />'
                   '</div>';
                return w;
                }

            },
            {field:'rightImg',title:'右眼图片',width:150,
                formatter:function (value,row,index) {
                    var e = fileUtils.url("ba","hm",row.uuid,row.areaId,row.rightImg);
                    var w ='<div style="margin-top: 3px "  onmousemove="this.style.cursor=\'pointer\'">'
                        + '<img src="'+e+'"  onmouseover="onBigImage(event,this)" onmouseout="mvBigImage()" />'
                    '</div>';
                    return w;
                }
            },
            {field:'areaName',title:'办案场所',width:120},
            {field:'ajmc',title:'案件名称',width:120},
            {field:'personName',title:'嫌疑人姓名',width:120},
            {field:'userName',title:'采集人姓名',width:120},
            {field:'id',title:'操作',width:100,
                formatter:function (value,row,index) {
                    var e,d;
                    if(isGridButton("LeftDownload")){
                        e = '<span class="spanRow" style="margin-top: 15px"><span class="griddownload"   onclick="fileUtils.load(\'ba\',\'hm\','+ row.uuid +','+row.areaId+',\''+row.leftImg+'\')" name="nobutton">左眼图片下载</span></span>';
                    }
                    if(isGridButton("rightDownload")){
                        d = '<span class="spanRow"><span class="griddownload" onclick="fileUtils.load(\'ba\',\'hm\','+ row.uuid +','+row.areaId+',\''+row.rightImg+'\')" name="nobutton">右眼图片下载</span></span>';
                    }
                    return e+d;
                }
            }
        ]],

        onLoadSuccess: function(data){
            var panel = $(this).datagrid('getPanel');
            var tr = panel.find('div.datagrid-body tr');
            tr.each(function(){
                var td = $(this).children('td');
                td.children("div").css({
                    "height": "75px",
                    "line-height":"75px"
                });
            });
        },
        pagination:true,
        pageList: [10,20,30,40,50,100],
        toolbar:'#toolbar',
        rownumbers: true,
    });

    $('#fudong').remove();


});


// 查询
function doSearch(){
    $('#iris_datagrid').datagrid('load', {
        personName : $('#personName').val(),
        userName : $('#userName').val(),
        ajmc : $('#ajmc').val(),
        interrogatearea : $('#interrogatearea').val(),
        trefresh:new Date().getTime()
    });
}

// 清空
function doClear(){
    $('#personName').prop('value','');
    $('#userName').prop('value','');
    $('#ajmc').prop('value','');
    $("#interrogatearea").combobox("setValue","");
}

//加载办案场所
function initArea(){
    $.ajax({
        url : contextPath + '/zhfz/bacs/order/comboArea.do',
        dataType : 'json',
        async : false,
        success : function (data) {
            $('#interrogatearea').combobox({
                data: data,
                valueField: 'id',
                textField: 'name',
                width : 160
            });
            if(data != null && data.length > 0){
                $('#interrogatearea').combobox('setValue',data[0].id);
            }
        }
    });
}


