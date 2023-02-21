$(function(){
	$('#datagrid').datagrid({
		title:'功能室分组数据',
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
		url: 'grouplist.do',
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
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
		    
		    {field:'name',title:'分组名称',width:80},
		    {field:'interrogateAreaName',title:'所属办案场所',width:80},
		    {
				field : '操作',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					 var e ='';
	        		 var d ='';
        		 	if(isGridButton("roomGroupEdit")){
						e ='<span class="spanRow"><a href="#" class="gridedit" onclick="roomGroupEdit('+index+')">修改</a></span>';
					}
					if(isGridButton("roomGroupRemove")){
						d ='<span class="spanRow"><a href="#" class="griddel" onclick="roomGroupRemove('+index+')">删除</a></span>';
					}
	        		 return e + d;
				}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		/*//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			organizationgridLoad(rowData);
		},*/
		toolbar:'#toolbar'
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
//			$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()} );
			$('#datagrid').datagrid('load', {
				name : $('#s_room_group_name').val(),
				interrogateAreaName : $('#s_interrogate_area_name').val(),
				trefresh:new Date().getTime()
			});
			return false;
		}
	});
	//TODO hideButton('btnadd');
	//hideButton('btnedit');
	//hideButton('btnremove');
	//doCall();
	
	//hideButton('enterprisebtnadd');
	//hideButton('enterprisebtnedit');
	//hideButton('organizationbtnadd');
	//hideButton('organizationbtnedit');
	//doCall();
	$('#fudong').remove();
});

function roomGroupAdd(){
	showDialog('#data_dialog','新增功能室分组'); 
	$('#room_group_form').form('clear');  
	$('#interrogateAreaId').combobox({   
		url:'../combobox/getAllInterrogateAreaName.do',   
		valueField:'id',   
		textField:'value'  ,
	    onLoadSuccess: function(data){    
//            $('#interrogateAreaId').combobox('setValue', data[0].id);    
        } 
	});
	url = 'groupadd.do';
}

function roomGroupEdit(index){
	 var row = $('#datagrid').datagrid('getRows')[index];
	if (row){
		showDialog('#data_dialog','编辑功能室分组');
	    $('#room_group_form').form('clear');
	    $('#room_group_form').form('load',row);
	    $('#interrogateAreaId').combobox({   
			url:'../combobox/getAllInterrogateAreaName.do',   
			valueField:'id',   
			textField:'value',
		    onLoadSuccess: function(data){    
	            $('#interrogateAreaId').combobox('setValue', row.interrogateAreaName);    
	        } 
		});
	    url = 'groupedit.do';  
	} else{
		$.messager.alert('Message', 'Please select a row first!'); 
	} 
}

function saveRoomGroup(){
	if(!checkForm('#room_group_form')){
		return;
	}
	var roomGroupForm = $('#room_group_form');
	var jsonrtinfo = JSON.stringify(roomGroupForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'保存数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : jsonrtinfo,
		dataType : 'json',
		success : function(data){
			$.messager.alert(data.title, data.content);
			$('#data_dialog').dialog('close');
//			$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
			$('#datagrid').datagrid('load', {
				name : $('#s_room_group_name').val(),
				interrogateAreaName : $('#s_interrogate_area_name').val(),
				trefresh:new Date().getTime()
			});
			$.messager.progress('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});  
}

function roomGroupRemove(index){
	var row = $('#datagrid').datagrid('getRows')[index];
	$('#room_group_form').form('load',row);
	var roomGroupForm = $('#room_group_form');
	var jsonrtinfo = JSON.stringify(roomGroupForm.serializeObject());
	$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
        if (r){
		$.messager.progress({
	   	 title:'请等待',
	   	 msg:'删除数据中...'
	    });
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : 'groupdelete.do',
			data : jsonrtinfo,
			dataType : 'json',
			success : function(data){
				$.messager.alert(data.title, data.content); 
//				$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
				$('#datagrid').datagrid('load', {
					name : $('#s_room_group_name').val(),
					interrogateAreaName : $('#s_interrogate_area_name').val(),
					trefresh:new Date().getTime()
				});
				$.messager.progress('close');
			},
			error : function(data){
				$.messager.progress('close');
				$.messager.alert('错误', '未知错误!');
			}
		});
        }});
	}
function doSearch() {
	$('#datagrid').datagrid('load', {
		name : $('#s_room_group_name').val(),
		interrogateAreaName : $('#s_interrogate_area_name').val(),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_room_group_name').val('');
	$('#s_interrogate_area_name').val('');
}
