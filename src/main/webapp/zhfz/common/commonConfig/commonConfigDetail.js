$(function(){	
	$('#commonConfigDetailgrid').datagrid({
		//title:'配置详细信息',
		iconCls:'icon-datagrid',
		fitColumns:true,
		nowrap: false,
		width:'100%',
		height:'100%',
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		//sortName: 'code',
		//sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams:{enpId:'-99',trefresh:new Date().getTime()},
		url: 'listCommonConfigDetail.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:60},
			{field:'paramKey',title:'参数名',width:120},
			{field:'paramValue',title:'参数值',width:120},
			{field:'desc',title:'描述',width:100},
			{field:'id',title:'操作',width:120,
				formatter:function(value,row,index){					
					 var e='';
						var d='';
							if(isGridButton("commonConfigDetailEdit")){
								e ='<span class="spanRow"><a href="#" class="gridedit" onclick="commonConfigDetailEdit('+index+')">修改</a></span>';
				        	}
							if(isGridButton("commonConfigDetailRemove")){
								d ='<span class="spanRow"><a href="#" class="griddel" onclick="commonConfigDetailRemove('+value+')">删除</a></span>';
				        	}
	        		 return e+d;
				}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//			checkRoles();
		} ,
		toolbar:'#commonConfigDetailToolbar'
	});
	
	
	var p1 = $('#commonConfigDetailgrid').datagrid('getPager');
	$(p1).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			var row = $('#commonConfiggrid').datagrid('getSelected');
			$('#commonConfigDetailgrid').datagrid('reload',{
				commonConfigId:row.id,
				paramKey : $('#s_commonConfigDetail_key').val(),
				paramValue : $('#s_commonConfigDetail_value').val(),
				desc : $('#s_desc').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
	$('#fudong').remove();
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

function saveCommonConfigDetail(){
	if(!checkForm('#common_config_detail_form')){
		return;
	}
	var orgForm = $('#common_config_detail_form'); 
	var organizationinfo = JSON.stringify(orgForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : organizationinfo,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			if(url=="addCommonConfigDetail.do"){
				$.messager.alert("提示", "配置详细信息添加成功！"); 
			}else{
				$.messager.alert("提示", "配置详细信息修改成功！"); 
			}
//			$('#commonConfigDetailgrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
			var row = $('#commonConfiggrid').datagrid('getSelected');
			$('#commonConfigDetailgrid').datagrid('reload',{
				commonConfigId:row.id,
				paramKey : $('#s_commonConfigDetail_key').val(),
				paramValue : $('#s_commonConfigDetail_value').val(),
				//desc : $('#s_desc').val(),
				trefresh:new Date().getTime()});
			$('#common_config_detail_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('提示', '未知错误!');
		}
	});  
}


function commonConfigDetailAdd(){
	var erow = $('#commonConfiggrid').datagrid('getSelected'); 
	if (erow){
		showDialog('#common_config_detail_dialog','新增配置详细信息'); 
		$('#commonConfigId').attr('value',erow.id);
		$('#paramKey').prop('value','');
	    $('#paramValue').prop('value','');
	    $('#desc2').prop('value','');
		$('#commConfigName1').attr('value',erow.type);
		url = 'addCommonConfigDetail.do';
	}else{
		$.messager.alert('提示', '请先选择一个配置!'); 
	}
}
function commonConfigDetailEdit(index){
	var row = $('#commonConfigDetailgrid').datagrid('getRows')[index];  
		showDialog('#common_config_detail_dialog','编辑配置详细信息');
		var erow = $('#commonConfiggrid').datagrid('getSelected'); 
		//$('#common_config_detail_form').form('clear');
	    $('#paramKey').prop('value',row.paramKey);
	    $('#paramValue').prop('value',row.paramValue);
	    $('#desc2').prop('value',row.desc);
	    $('#commonConfigId').prop('value',row.commonConfigId);
	    $('#id').prop('value',row.id);
	    $('#commConfigName1').attr('value',erow.type);
	    url = 'editCommonConfigDetail.do';  
}

function commonConfigDetailRemove(value){
        $.messager.confirm('提示','确定删除此条配置详情？',function(r){  
            if (r){
            	jQuery.ajax({
        			type : 'POST',
        			contentType : 'application/json',
        			url : 'removeCommonConfigDetail.do',
        			data : '{"id":'+value+'}',
        			dataType : 'json',
        			success : function(data){
        				$.messager.alert("提示", "配置详情删除成功！"); 
//        				$('#commonConfigDetailgrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
        				var row = $('#commonConfiggrid').datagrid('getSelected');
        				$('#commonConfigDetailgrid').datagrid('reload',{
        					commonConfigId:row.id,
        					paramKey : $('#s_commonConfigDetail_key').val(),
        					paramValue : $('#s_commonConfigDetail_value').val(),
        					//desc : $('#s_desc').val(),
        					trefresh:new Date().getTime()});
        			},
        			error : function(data){
        				//exception in java
        				$.messager.alert('提示', '未知错误!');
        			}
        		});  
            	
            }  
        });  
}
function commonConfigDetailSearch() {
	var row = $('#commonConfiggrid').datagrid('getSelected');  
	
	if (row){
		$('#commonConfigDetailgrid').datagrid('load', {
			commonConfigId:row.id,
			paramKey : $('#s_commonConfigDetail_key').val(),
			paramValue : $('#s_commonConfigDetail_value').val(),
			//desc : $('#s_desc').val(),
			trefresh:new Date().getTime()		
		});
	} 
}

function doClear() {
	$('#s_commonConfigDetail_key').prop('value', '');
	$('#s_commonConfigDetail_value').prop('value', '');
	//$('#s_desc').prop('value', '');
}