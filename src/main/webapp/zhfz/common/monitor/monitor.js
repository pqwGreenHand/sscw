$(function(){
	$('#datagrid').datagrid({
//		title:'监控',
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: true,
		striped: true,
		collapsible:false,
		loadMsg: 'Loading...',
		method: 'get',
		url: 'monitor.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
			{field:'hostname',title:'名称',width:100},
			{field:'ip',title:'IP',width:100},
			{field:'appname',title:'应用',width:100},
			{field:'status',title:'状态',width:50,
				formatter:function(value,rec){
					if(value==1){
						return '<font color="green">在线</font>';
					}else{
						return '<font color="red">不在线</font>';
					}
				}
			},
			{field:'updatedTime',title:'检查时间',width:80,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},
			{field:'id',title:'操作',width:120,
				formatter:function(field, rec, index){
					var a ='<span class="spanRow"><a href="#" class="gridcog" onclick="checkajax('+field+','+index+')">检查</a></span>';
                    var e = '<span class="spanRow"><a href="javascript:void(0)" class="gridedit" onclick="editMonitor('+ index + ')">修改</a></span> ';
                    var d = '<span class="spanRow"><a href="javascript:void(0)" class="griddel" onclick="deleteMonitor('+ index + ')")">删除</a></span>';
                    var f;
                    if (rec.enabled == 0){
                        f = '<span class="spanRow"><a href="javascript:void(0)" class="gridedit" onclick="updMonitor('+ index + ')")">启用</a></span>';
                    }else {
                        f = '<span class="spanRow"><a href="javascript:void(0)" class="gridedit" onclick="updMonitor('+ index + ')")">禁用</a></span>';
                    }
					return a+e+d+f;
				}
			}
		]],
		rownumbers:true,
		toolbar:'#toolbar'
		
	});
	
})
	
function checkajax(id,index){
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'数据加载中...'
	});
	$.post("checkajax.do", {id:id}, 
			function (text, stat){
		       if(text){
		    	   var data=JSON.parse(text);
		    	   $('#datagrid').datagrid('updateRow',{
		    			index: index,
		    			row: {
		    				status: data.status,
		    				updatedTime: data.updateTime
		    			}
		    		});
		       }
		       $.messager.progress('close');
	}).error(
		function(){$.messager.alert('提示', '未知错误!'); $.messager.progress('close');}	
	);
	
}

function doAllCheck()
{
	$('#datagrid').datagrid('load',{trefresh:new Date().getTime()});
}


function deleteMonitor(index){
    $.messager.progress({
        title:'请等待',
        msg:'删除数据中...'
    });
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){
        $.messager.confirm('提示','是否删除数据?',function(r){
            if (r){
                jQuery.ajax({
                    type : 'POST',
                    contentType : 'application/json',
                    url : 'delete.do',
                    data : '{"id":'+row.id+'}',
                    dataType : 'json',
                    success : function(data){
       				$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
                        $.messager.progress('close');
                        $.messager.show({
                            title:'提示',
                            msg:data.content,
                            timeout:3000
                        });
                    },
                    error : function(data){
                        $.messager.progress('close');
                        $.messager.alert('错误', '未知错误!');
                    }
                });
            }  else {
                $.messager.progress('close');
            }
        });
    } else{
        $.messager.alert('提示', '请选择一行!');
    }

}

function saveData(){
    if(!checkForm('#monitor_form')){
        return;
    }
    var orgForm = $('#monitor_form');
    var monitor_form = JSON.stringify(orgForm.serializeObject());
    $.messager.progress({
        title:'请等待',
        msg:'数据处理中...'
    });
    jQuery.ajax({
        type : 'Post',
        contentType : 'application/json',
        url : url,
        data :monitor_form,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
            $('#monitor_dialog').dialog('close');
            $('#datagrid').datagrid('reload',
                {
                    trefresh:new Date().getTime()
                });
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function editMonitor(index){
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){
        showDialog('#monitor_dialog','编辑信息');
        $('#monitor_form').form('clear');
        $('#monitor_form').form('load',row);
        $('#id').val(row.id);
        url = 'edit.do?id='+row.id;
    } else{
        $.messager.alert('提示', '请选择一行!');
    }
}

function addMonitor(){
    showDialog('#monitor_dialog','新增系统监控');
    $('#monitor_form').form('clear');
    url = 'add.do';
}

function updMonitor(index){
    var row = $('#datagrid').datagrid('getRows')[index];
    var updurl = 'edit.do?id='+row.id;
    jQuery.ajax({
        type : 'Post',
        contentType : 'application/json',
        url : updurl,
        data :'{"enabled":'+row.enabled+',"id":'+row.id+'}',
        dataType : 'json',
        success : function(data){
            $('#datagrid').datagrid('reload',
                {
                    trefresh:new Date().getTime()
                });
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}