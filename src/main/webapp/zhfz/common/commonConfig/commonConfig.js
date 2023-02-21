$(function(){
	$('#commonConfiggrid').datagrid({
		//title:'通用配置',
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		url: 'listCommonConfig.do',
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
			{field:'type',title:'配置类型',width:80},
			{field:'desc',title:'配置描述',width:80},
			{field:'isEnable',title:'是否开启',width:80,
				formatter:function(value,row,index){
					var a='';
						if(value == 0){
							a ='否';
						}
						if(value == 1){
							a ='是';
						}
					return a;
				}
			},
			{field:'commonConfigId',title:'',width:0},
			{field:'id',title:'操作',width:80,
				formatter:function(value,row,index){
					//return '<a href="#" class="griddel" onclick="commonConfigRemove('+value+')">删除</a><a href="#" class="gridedit" onclick="certificateEdit('+index+')">修改</a>';
					//return '<a href="#" class="btn default btn-xs purple"><i class="icon-edit"></i> Edit</a>';
					var e='';
					var d='';
						if(isGridButton("commonConfigEdit")){
							e ='<span class="spanRow"><a href="#" class="gridedit" onclick="commonConfigEdit('+index+')">修改</a></span>';
						}
						if(isGridButton("commonConfigRemove")){
							d ='<span class="spanRow"><a href="#" class="griddel" onclick="commonConfigRemove('+value+')">删除</a></span>';
						}
					return e+d;
				}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			commonConfigDetailgridLoad(rowData);
		},
		toolbar:'#commonConfigToolbar'		
	});
	var p = $('#commonConfiggrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			$('#commonConfiggrid').datagrid('load',{
				name : $('#s_commonConfig_name').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
});




function commonConfigDetailgridLoad(rowData)
{
	$('#commConfigName1').attr('value','');
	$('#commonConfigDetailgrid').datagrid('load',{commonConfigId:rowData.id,trefresh:new Date().getTime()});
}

function creatParam(name,value)
{
	var param={
			name: name,
			value: value
		};
	return param;
}

function saveCommonConfig(){
	if(!checkForm('#commonConfig_form')){
		return;
	}
	var val = $('input:radio[name="isEnable"]:checked').val();
	if(val == undefined){
		$.messager.alert("提示", "是否启用未选中！");
		return;
	}
	var entForm = $('#commonConfig_form');
	var enterpriseinfo = JSON.stringify(entForm.serializeObject());
	if(enterpriseinfo.isEnable == ''){
		enterpriseinfo.isEnable = 1;
	}
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : enterpriseinfo,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			
			if(url=='addCommonConfig.do'){
				$.messager.alert("提示", "通用配置添加成功！"); 
			}else{
				$.messager.alert("提示", "通用配置修改成功！"); 
			}
//			$('#commonConfiggrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
			$('#commonConfiggrid').datagrid('load',{
				type : $('#type').val(),
				desc : $('#desc').val(),
				isEnable : $('#isEnable').val(),
				trefresh:new Date().getTime()});
			$('#commonConfig_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('提示', '未知错误!'+data);
		}
	});  
}

function commonConfigAdd(){
	showDialog('#commonConfig_dialog','新增通用配置'); 
	$('#isEnable1').attr("checked","checked");
	$('#commonConfig_form').form('clear');  
	url = 'addCommonConfig.do';
}

function commonConfigEdit(index){
	 var row = $('#commonConfiggrid').datagrid('getRows')[index];
		showDialog('#commonConfig_dialog','编辑通用配置');
	    $('#commonConfig_form').form('clear');
	    $('#commonConfig_form').form('load',row);
	    url = 'editCommonConfig.do?id='+row.id;  
}

function commonConfigRemove(value){
	$.messager.confirm('提示','是否确定删除此通用配置？',function(r){ 
		if (r){
		$.messager.progress({
		   	 title:'请等待',
		   	 msg:'删除数据中...'
		    });
			jQuery.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : 'removeCommonConfig.do',
				data : '{"id":'+value+'}',
				dataType : 'json',
				success : function(data){
					$.messager.alert("提示", "通用配置删除成功！"); 
//					$('#commonConfigDetailgrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
					var row = $('#commonConfiggrid').datagrid('getSelected');
					$('#commonConfigDetailgrid').datagrid('reload',{
						commonConfigId:row.id,
						paramKey : $('#s_commonConfigDetail_key').val(),
						paramValue : $('#s_commonConfigDetail_value').val(),
						desc : $('#s_desc').val(),
						trefresh:new Date().getTime()});
//					$('#commonConfiggrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
					$('#commonConfiggrid').datagrid('load',{
						name : $('#s_commonConfig_name').val(),
						trefresh:new Date().getTime()});
					$.messager.progress('close');
				},
				error : function(data){
					//exception in java
					$.messager.progress('close');
					$.messager.alert('提示', '未知错误!');
				}
			});  
	    }});
}

function commonConfigSearch() {
	$('#commonConfiggrid').datagrid('load', {
		type : $('#type').val(),
		desc : $('#desc').val(),
		isEnable : $('#isEnable').val(),
		trefresh:new Date().getTime()
	});
}

function doClear1(){
	$('#type').prop('value', '');
	$('#desc').prop('value', '');
	$('#isEnable').combobox('clear');
}