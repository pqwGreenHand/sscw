$(function() {
	$('#areaId').combobox({   
		url:'../combobox/getAllInterrogateAreaName.do',
		valueField:'id',   
		textField:'value' 
	});
	$('#s_type').combobox('setValue',""); 
	$('#cabinet_config_datagrid').datagrid({
		title : '储物柜配置',
		iconCls : 'icon-datagrid',
		region : 'center',
		height : '100%',
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		loadMsg : 'Loading...',
		method : 'post',
		url : 'list.do',
		queryParams : { id : '0'},
		sortName : 'id',
		sortOrder : 'desc',
		remoteSort : false,
		idField : 'id',
		singleSelect : true,
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 80,
			sortable : true,
			hidden : true
		} ] ],
		columns : [ [
			{ field : 'name', title : '名称', width : 120 },
			{ field : 'type', title : '类型', width : 120,
				formatter : function(value) {
					if(value==1){
						return "随身储物柜";
					}else if(value==2){
						return "涉案储物柜";
					}else if(value==3){
					    return "民警储物柜";
					}else{
						return "错误类型";
					}
				}
			},
			{ field : 'gg', title : '规格', width : 120,
				formatter : function(value, row) {
					return row.rowCount+"行"+row.columnCount+"列";
				}
			},
			{ field : 'ip', title : 'ip', width : 120 },
			{ field : 'port', title : '端口', width : 80 },
			{ field : 'group', title : '分组名称', width : 120 },
			{ field : 'openIp', title : '开柜ip', width : 120 },
			{ field : 'areaName', title : '所属办案场所', width : 120 },
			{field:'id',title:'操作',width:150,
				formatter:function(value,rec,index){
					var e = '';
					var d = '';
					if(isGridButton("cabinetEdit")){
						e = '<span class="spanRow"><a href="#" class="gridedit" onclick="cabinetEdit('+index+')">修改</a></span>';
					}
					if(isGridButton("cabinetRemove")){
						d = '<span class="spanRow"><a href="#" class="griddel" onclick="cabinetRemove('+value+')">删除</a></span>';
					}
					return e + d;
				}}
		] ],
		pagination : true,
		pageList : [ 10, 20, 30, 40, 50, 100 ],
		rownumbers : true,
		toolbar:'#cabinetConfigToolbar',
		onSelect : function(rowIndex, rowData) {
			$('#cabinet_config_detail_datagrid').datagrid('load', {
				configId : rowData.id,
				tfresh : new Date().getTime()
			});
		},
		onLoadSuccess : function(data) {

		}
	});
	var p3 = $('#cabinet_config_datagrid').datagrid('getPager');
	$(p3).pagination({
		onBeforeRefresh : function() {
			// alert('before refresh');
		}
	});

	$('#fudong').remove();
});

function cabinetConfigAdd(){
	showDialog('#cabinetConfig_dialog','新增柜子'); 
	$('#cabinetConfig_form').form('clear');  
	url = 'add.do';
}

function saveCabinetConfig(){
	if(!checkForm('#cabinetConfig_form')){
		return;
	}
	var orgForm = $('#cabinetConfig_form'); 
	var cabinetinfo = JSON.stringify(orgForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'数据处理中...'
	    });
	jQuery.ajax({
		type : 'Post',
		contentType : 'application/json',
		url : url,
		data :cabinetinfo,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.alert(data.title, data.content); 
			$('#cabinetConfig_dialog').dialog('close');
			$('#cabinet_config_datagrid').datagrid('reload',
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
function cabinetEdit(index){
	$('#divml').css('display','none');
	var row = $('#cabinet_config_datagrid').datagrid('getRows')[index];
	if (row){
		showDialog('#cabinetConfig_dialog','编辑柜子');
		$('#cabinetConfig_form').form('clear');
	    $('#cabinetConfig_form').form('load',row);
	    $('#type2').combobox('setValue',row.type);
	    console.log(row);
	    url = 'edit.do';
	} else{
		$.messager.alert('提示', '请选择一条数据!'); 
	} 
}
function cabinetRemove(value){
    $.messager.confirm('确认','是否要删除数据?',function(r){  
        if (r){
        	$.messager.progress({
        	   	 title:'请等待',
        	   	 msg:'删除数据中...'
        	});
        	jQuery.ajax({
        		async : false,
    			type : 'POST',
    			contentType : 'application/json',
    			url : 'remove.do',
    			data : '{"id":'+value+'}',
    			dataType : 'json',
    			success : function(data){
    				$.messager.alert(data.title, data.content); 
    				$('#cabinet_config_datagrid').datagrid('load', {
    					trefresh:new Date().getTime()
    				});
    				$('#cabinet_config_detail_datagrid').datagrid('load', {
    					trefresh:new Date().getTime()
    				});
    				$.messager.progress('close');
    			},
    			error : function(data){
    				$.messager.alert('错误', '未知错误!');
                    $.messager.progress('close');
    			}
    		});  
        	
        }  
    }); 
}

function cabinetSearch() {
	$('#cabinet_config_datagrid').datagrid('load', {
		name : $('#s_name').val(),
		type : $('#s_type').combobox('getValue'),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_name').textbox('setValue',''),
	$('#s_type').combobox('clear'); 
}