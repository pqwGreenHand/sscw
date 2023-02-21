 var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: '清空',
        handler: function (target) {//target对象就是当前的inupt对象，不需要写死id
            $(target).datebox('setValue', '').datebox("hidePanel");
        }
    });
    $.extend($.fn.datebox.defaults, {
        buttons: buttons
    }); 
$(function(){
	$('#datagrid').datagrid({
		title:'操作日志',
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: 'Loading...',
		method: 'get',
		url: 'list.do',
		sortName: 'operTime',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		queryParams : {
			trefresh:new Date().getTime()
		},
		singleSelect:true,
		frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
			{field:'type',title:'操作类型',width:80},
			{field:'systemName',title:'系统名称',width:80},
			{field:'content',title:'操作内容',width:400},
			{field:'user',title:'操作人',width:100},
			{field:'operTime',title:'操作时间',width:150,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},
			{field:'isSuccess',title:'是否成功',width:50,
				formatter:function(value,rec){
					if(value==0){
						return '<font color="red">否</font>';
					}
					return '<font color="green">是</font>';
				}
			}
			/*,{field:'id',title:'操作',width:100,
				formatter:function(field, rec, index){
					var a='';
						if(isGridButton("operlogRemove")){
							a ='<a href="#" class="griddel" onclick="operlogRemove('+field+')">删除</a>';
						}
					return a;
				}
			}*/
			
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#toolbar'
		
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			$('#datagrid').datagrid('load', {
				user_t : $('#s_user').val(),
				content_t : $('#s_content').val(),
				start_date_d : $('#s_start_date').datebox('getValue'),
				end_date_d : $('#s_end_date').datebox('getValue'),
				trefresh:new Date().getTime()
			});
			return false;
		}
	});
	$('#fudong').remove();
});

function operlogEdit(index){
	$.messager.alert('indexRow', $('#datagrid').datagrid('selectRow',index));
}

function operlogRemove(value){
	$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
        if (r){
			$.messager.progress({
			 title:'请等待',
			 msg:'删除数据中...'
			});
			jQuery.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : 'remove.do',
				data : '{"id":'+value+'}',
				dataType : 'json',
				success : function(data){
					$.messager.alert(data.title, data.content); 
//					$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
					$('#datagrid').datagrid('load', {
						user_t : $('#s_user').val(),
						content_t : $('#s_content').val(),
						start_date_d : $('#s_start_date').datebox('getValue'),
						end_date_d : $('#s_end_date').datebox('getValue'),
						trefresh:new Date().getTime()
					});
					$.messager.progress('close');
				},
				error : function(data){
					//exception in java
					$.messager.progress('close');
					$.messager.alert('Error', '未知错误!');
				}
			});  
        }
	});  
}

function doSearch() {
	$('#datagrid').datagrid('load', {
		system_name_t : $('#s_name').val(),
		user_t : $('#s_user').val(),
		content_t : $('#s_content').val(),
		start_date_d : $('#s_start_date').datebox('getValue'),
		end_date_d : $('#s_end_date').datebox('getValue'),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_name').prop('value','');
	$('#s_user').prop('value','');
	$('#s_content').prop('value','');
	$('#s_start_date').datebox('setValue','');
	$('#s_end_date').datebox('setValue','');
}
