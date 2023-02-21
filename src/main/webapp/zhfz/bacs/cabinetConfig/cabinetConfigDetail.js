$(function() {
	$('#cabinet_config_detail_datagrid').datagrid({
		title : '储物柜配置详情',
		iconCls : 'icon-datagrid',
		region : 'center',
		height : '100%',
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		loadMsg : 'Loading...',
		method : 'post',
		url : '../cabinetConfigDetail/list.do',
		queryParams : { configId : '0'},
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
			{ field : 'showNo', title : '显示编号', width : 120 },
			{ field : 'openKey', title : '命令编号', width : 120 },
			{ field : 'gg', title : '位置', width : 120,
				formatter : function(value, row) {
					return row.row+"行"+row.column+"列";
				}
			},
			{ field : 'type', title : '柜门类型', width : 120,
				formatter : function(value) {
					if(value==2){
						return "大";
					}else if(value==1){
						return "小";
					}else{
						return "错误类型";
					}
				}
			},
			{field:'id',title:'操作',width:150,
				formatter:function(value,rec,index){
					var e = '';
					if(isGridButton("cabinetDetialEdit")){
						e = '<span class="spanRow"><a href="#" class="gridedit" onclick="cabinetDetialEdit('+index+')">修改</a></span>';
					}
					return e ;
				}}
		] ],
		pagination : true,
		pageList : [ 10, 20, 30, 40, 50, 100 ],
		rownumbers : true,
		onSelect : function(rowIndex, rowData) {
		},
		onLoadSuccess : function(data) {

		}
	});
	var p3 = $('#cabinet_config_detail_datagrid').datagrid('getPager');
	$(p3).pagination({
		onBeforeRefresh : function() {
			// alert('before refresh');
		}
	});
});
function cabinetDetialEdit(index){
	var row = $('#cabinet_config_detail_datagrid').datagrid('getRows')[index];
	if (row){
		showDialog('#cabinetConfig_detail_dialog','编辑部门');
		$('#cabinetConfig_detail_form').form('clear');
	    $('#cabinetConfig_detail_form').form('load',row);
	    $('#type1').combobox('setValue',row.type);
	    $('#id1').val(row.id);
	    $('#id2').val(row.configId);
	    url = '../cabinetConfigDetail/editDetail.do';
	} else{
		$.messager.alert('提示', '请选择一条数据!'); 
	} 
}
function saveCabinetDetailConfig(){
	if(!checkForm('#cabinetConfigDetail_form')){
		return;
	}
	var orgForm = $('#cabinetConfigDetail_form'); 
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
			$('#cabinetConfig_detail_dialog').dialog('close');
			$('#cabinet_config_detail_datagrid').datagrid('reload',
					{
						configId: $('#id2').val(),
				       trefresh:new Date().getTime()
				    });
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});  
}
