var url,_sysType = {aj :'案件', ba :'办案', sa :'涉案', jg :'卷管',br:'辨认图片库',cj:'插件',qz:'签章'};

function init() {
	$('#save-sys-id').combobox({
		url:'../combobox/listArea.do',
        valueField:'id',
        textField:'value'
	})
	var list = [];
	$.each(_sysType,function (k,v) {
		var obj = {id:k,value:v};
		list.push(obj);
    });
	$('#query-sys-type').combobox({
		data:list,
        valueField:'id',
		textField:'value',
		// onSelect : function (data) {
		//
		// 	if(data.id == 'aj' || data.id == 'br'){
		// 		$('#query-sys-id').combobox('disable');
		// 	}else {
        //         $('#query-sys-id').combobox('enable');
        //     }
        // }
	});
	$('#save-sys-type').combobox({
		data:list,
        valueField:'id',
		textField:'value',
		onSelect : function (data) {
			if(data.id == 'sa'){
				console.log(11121)
				$('#save-sys-id').combobox('reload','../combobox/listWare.do');
			}else{
				console.log(22212)
				$('#save-sys-id').combobox('reload','../combobox/listArea.do');
			}
			if(data.id == 'aj' || data.id == 'br'|| data.id == 'cj'){
				$('.save-sys-id').hide();
				$('#save-sys-id').combobox('setValue','');
			}else{
                $('.save-sys-id').show();
            }
            setTimeout(reloadResultUrl,100);

        }
	});

}

$(function(){
    init();
	$('#certificategrid').datagrid({
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
		url: 'list.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
		    {field:'sysType',title:'系统类型',width:80,formatter:function(value){
		    	return _sysType[value];
				}},
			{field:'serviceUrl',title:'文件服务地址',width:300},
			{field:'fileSavePath',title:'文件保存地址',width:200},
			{field:'areaName',title:'办案场所',width:80},
			{field:'desc',title:'描述',width:80},
			{field:'isEnable',title:'是否可用',width:80,formatter:function (value) {
					return ['否','是'][value];
                }},
			{field:'id',title:'操作',width:150,
		    	formatter:function(value,row,index){
		    		var e='',d='',v='';
                    if(isGridButton("fscEdit")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="edit('+index+')">编辑</a></span>';
                        if(row.isEnable == 1)
                            v ='<span class="spanRow"><a href="#" class="gridedit" onclick="enable('+value+',0)">禁用</a></span>';
                        else
                            v ='<span class="spanRow"><a href="#" class="gridedit" onclick="enable('+value+',1)">启用</a></span>';
                    }
					if(isGridButton("fscRemove")){
						d ='<span class="spanRow"><a href="#" class="griddel" onclick="remove('+value+')">删除</a></span>';
					}
		    		return e + d + v;
		    	}}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#certificateToolbar'
	});
	var p = $('#certificategrid').datagrid('getPager');

	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			$('#certificategrid').datagrid('reload',{
				name : $('#q_name').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
	$('#fudong').remove();
});


function saveEnterprise(){
	if(!checkForm('#save-form')){
		return;
	}
    var jsonForm = {};
	jsonForm = $('#save-form').serializeObject(jsonForm);
	objToUrl(jsonForm);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
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
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#certificate_dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function add(){
	// console.log(fileUtils.url('ba','ba',111,1,'111.png'));
	showDialog('#certificate_dialog','新增文件系统配置');
	url = 'add.do';
	$('#save-form').form('clear').form('load',{ip:'127.0.0.1',port:8080,url:'/zhfz-common-service/FileService',fileSavePath:'D:/data'});
	$('#result-url').html('');
}
function edit(index){
	// fileUtils.load('ba','ba',111,1,'111.png');
	url = 'edit.do';
    showDialog('#certificate_dialog','编辑文件系统配置');
    var row = $('#certificategrid').datagrid('getRows')[index];
    urlToObj(row);
    $('#save-form').form('load',row);
}
function enable(id,enable) {
    $.messager.progress({
        title:'请等待',
        msg:'禁用/启用数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: 'updEnable.do',
        data: {id:id,enable:enable},
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#certificate-dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}


function remove(value) {
    $.messager.confirm('删除确认', '是否确定删除此数据？', function (r) {
        if (r) {
            $.messager.progress({
                title: '请等待',
                msg: '删除数据中...'
            });
            jQuery.ajax({
                type: 'POST',
                url: 'remove.do',
                data: {id: value},
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

function doSearch() {
    var param = {trefresh:new Date().getTime()};
    param = $('#search').serializeObject(param);
	$('#certificategrid').datagrid('load', param);
}

function doClear() {
	$('#search').form('clear');
}
function urlToObj(row) {
	var url =  row.serviceUrl.substring(7);
	var len = url.indexOf('/');
	var s = url.substring(0,len);
	var arr = s.split(':');
	row['ip'] = arr[0];
	row['port'] = arr[1];
	row['url'] = url.substring(len,url.lastIndexOf('/'));
}
function objToUrl(row) {
	var url = 'http://{ip}:{port}{url}/{sysType}';
	row.serviceUrl = url.format(row);
	if(row.sysType == 'aj' || row.sysType == 'br'|| row.sysType == 'cj')
		row.sysId = 0;
}

function keyUp() {
	reloadResultUrl();
}

function reloadResultUrl(obj) {
    var obj = obj || $('#save-form').serializeObject()
        ,url = 'http://{ip}:{port}{url}/{sysType}';
    $('#result-url').html(url.format(obj));
}

