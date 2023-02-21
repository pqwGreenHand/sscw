$(function(){
     hiddendiv($('#batchAdd')); 
	$('#personalConfiggrid').datagrid({
		//title:'个性化配置',
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
		url: 'listPersonalConfig.do',
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
			{field:'type',title:'配置名称',width:80},
			{field:'desc',title:'配置描述',width:80},
			{field:'areaName',title:'办案场所名称',width:80},
			{
				field:'isEnable',
				title:'是否有效',
				width:80,
				formatter : function(value){
					var str = "";
					if(value==0)
						str = "无效";
					if(value==1)
						str = "有效";
					return str;
				}
			},
			{field:'id',title:'操作',width:80,
				formatter:function(value,row,index){
					var e='';
					var d='';
						if(isGridButton("personalConfigEdit")){
							e ='<span class="spanRow"><a href="#" class="gridedit" onclick="personalConfigEdit('+index+')">修改</a></span>';
						}
						if(isGridButton("personalconfigReomve")){
							 d ='<span class="spanRow"><a href="#" class="griddel" onclick="personalconfigReomve('+value+')">删除</a></span>';
						}
	        		return e+d;
				}
			}
		]],
		pagination:true,
		pageSize:15,
		pageList: [15,30,40,50,100],
		rownumbers:true,
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			personalConfigDetailgridLoad(rowData);
		},
		toolbar:'#personalConfigToolbar'

		
	});
	$('#AreaId,#a_AreaId').combobox({
	    url:'../combobox/comboArea.do',
	    valueField:'id',   
	    textField:'name'
	});
	var p = $('#personalConfiggrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			$('#personalConfiggrid').datagrid('load', {
				type : $('#s_personalConfig_type').val(),
				trefresh:new Date().getTime()
			});
			return false;
		}
	});
	

	$('#personalConfigDetailgrid').datagrid({
		//title:'配置详细信息',
		iconCls:'icon-datagrid',
		fitColumns:true,
		width:'100%',
		height:'100%',
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		remoteSort: false,
		queryParams:"{'enpId':'-99','type':'0'}",
		url: 'listPersonalConfigDetail.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:0},
			{field:'paramKey',title:'参数名',width:80},
			{field:'paramValue',title:'参数值',width:80},
			{field:'desc',title:'描述',width:80},
			{field:'id',title:'操作',width:110,
				formatter:function(value,row,index){
					  var e='';
						var d='';
							if(isGridButton("personalConfigDetailEdit")){
								e ='<span class="spanRow"><a href="#" class="gridedit" onclick="personalConfigDetailEdit('+index+')">修改</a></span>';
				        	}
							if(isGridButton("personalConfigDetailRemove")){
								d ='<span class="spanRow"><a href="#" class="griddel" onclick="personalConfigDetailRemove('+value+')">删除</a></span>';
				        	}
	        		  return e+d;
				}
			}
		]],
		pagination:true,
		pageSize:15,
		pageList: [15,20,30,40,50,100],
		rownumbers:true,
		toolbar:personalConfigDetailToolbar
	});
	$('#fudong').remove();
});

function personalConfigDetailgridLoad(rowData) {
	$('#personalConfigName1').attr('value','');
	$('#personalConfigDetailgrid').datagrid('load',{personalConfigId:rowData.id, paramKey : $('#s_personalConfigDetail_key').val(),
		paramValue : $('#s_personalConfigDetail_value').val(), type:0,trefresh:new Date().getTime()});
}

function savepersonalConfig(){
	if(!checkForm('#personalConfig_form')){
		return;
	}
	var p3=$("#AreaId").combobox('getValue');
	if(p3==''){
		$.messager.alert("提示", "请选择办案场所！！！！！");
		return;
	}
	var entForm = $('#personalConfig_form');
	if($("#isEnable").is(':checked')){
		$("#isEnable").attr("value",1);
	}else{
		$("#isEnable").attr("value",0);
	}
	var enterpriseinfo = JSON.stringify(entForm.serializeObject());
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
			$.messager.alert("提示", "添加成功！");
			$('#personalConfiggrid').datagrid('load', {
				name : $('#s_personalConfig_type').val(),
				trefresh:new Date().getTime()
			});
			$('#personalConfig_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('提示', '未知错误!'+data);
		}
	});  
}

function savepersonalConfigDetail(){
	if(!checkForm('#personal_config_detail_form')){
		return;
	}
	
	var orgForm = $('#personal_config_detail_form'); 
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
			$.messager.alert("提示", data.content);
			var row = $('#personalConfiggrid').datagrid('getSelected');  
			if (row){
				$('#personalConfigDetailgrid').datagrid('load', {
					personalConfigId:row.id,
					type:0,
					paramKey : $('#s_personalConfigDetail_key').val(),
					paramValue : $('#s_personalConfigDetail_value').val(),
					trefresh:new Date().getTime()
				});
			}
			$('#personal_config_detail_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('提示', '未知错误!'+data);
		}
	});  
}

function personalConfigAdd(){
	showDialog('#personalConfig_dialog','新增个性化配置'); 
	$('#personalConfig_form').form('clear');  
	url = 'addPersonalConfig.do';
}

function personalConfigEdit(index){
	var row = $('#personalConfiggrid').datagrid('getRows')[index];
	if (row){
		showDialog('#personalConfig_dialog','编辑个性化配置');
	    $('#personalConfig_form').form('load',row);
	    $('#AreaId').combobox('select',row.areaId);
	    $('#type').prop('value',row.type);
		$('#desc').prop('value',row.desc);
	    $('#id').prop('value',row.id);
		if(row.isEnable == '1'){
			$("#isEnable").prop("checked",true);
		}else{
			$("#isEnable").prop("checked",false);
		}
	    url = 'editPersonalConfig.do?id='+row.id;  
	} else{
		$.messager.alert('提示', '请先选择一行配置信息!'); 
	} 
}

function personalconfigReomve(value){
	$.messager.confirm('提示','是否确定删除此数据？',function(r){ 
		if (r){
		$.messager.progress({
		   	 title:'请等待',
		   	 msg:'删除数据中...'
		    });
			jQuery.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : 'removePersonalConfig.do',
				data : '{"id":'+value+'}',
				dataType : 'json',
				success : function(data){
					$.messager.alert("提示", "删除成功！");
					var row = $('#personalConfiggrid').datagrid('getSelected');  
					if (row){
						$('#personalConfigDetailgrid').datagrid('load', {
							personalConfigId:row.id,
							type:0,
							paramKey : $('#s_personalConfigDetail_key').val(),
							paramValue : $('#s_personalConfigDetail_value').val(),
							trefresh:new Date().getTime()
						});
					}
					$('#personalConfiggrid').datagrid('load', {
						name : $('#s_personalConfig_type').val(),
						trefresh:new Date().getTime()
					});
					$.messager.progress('close');
				},
				error : function(data){
					$.messager.progress('close');
					$.messager.alert('提示', '未知错误!');
				}
			});  
	    }});
}
function personalConfigSearch() {
	$('#personalConfiggrid').datagrid('load', {
		type : $('#s_personalConfig_type').val(),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_personalConfig_type').prop('value', '');
	$('#s_personalConfigDetail_key').prop('value', '');
	$('#s_personalConfigDetail_value').prop('value', '');
	$('#s_personalConfigDetail_key_inner').prop('value', '');
    $('#s_personalConfigDetail_value_inner').prop('value', '');
}

function personalConfigDetailAdd(type){
	var erow = $('#personalConfiggrid').datagrid('getSelected'); 
	if (erow){
		showDialog('#personal_config_detail_dialog','新增配置详细信息');
		$('#personalConfigId').attr('value',erow.id);
		$('#paramKey').prop('value','');
	    $('#paramValue').prop('value','');
	    $('#paramShow').prop('value','');
		$('#personal_config_detail_desc').prop('value','');
		$('#personalConfigName1').attr('value',erow.type);
		url = 'addPersonalConfigDetail.do';
		
	}else{
		$.messager.alert('提示', '请先选择一个配置信息!'); 
	}
}

function personalConfigDetailEdit(index){
    var row = $('#personalConfigDetailgrid').datagrid('getRows')[index];

	
	var erow = $('#personalConfiggrid').datagrid('getSelected'); 
	if (row){
		showDialog('#personal_config_detail_dialog','编辑配置详细信息');
	    $('#paramKey').prop('value',row.paramKey);
	    $('#paramValue').prop('value',row.paramValue);
	    $('#personalConfigId').prop('value',row.personalConfigId);
	    $('#personal_config_detai_id').prop('value',row.id);
	    $('#personalConfigName1').prop('value',erow.type);
		$('#personal_config_detail_desc').prop('value',row.desc);

	    url = 'editPersonalConfigDetail.do';  
	} else{
		$.messager.alert('提示', '请先选择一行!'); 
	} 
}
function personalConfigDetailRemove(value){
        $.messager.confirm('提示','确定删除此条数据?',function(r){  
            if (r){
            	jQuery.ajax({
        			type : 'POST',
        			contentType : 'application/json',
        			url : 'removePersonalConfigDetail.do',
        			data : '{"id":'+value+'}',
        			dataType : 'json',
        			success : function(data){
        				$.messager.alert("提示", "删除成功!");
        				var row = $('#personalConfiggrid').datagrid('getSelected');  
        				if (row){
        					$('#personalConfigDetailgrid').datagrid('load', {
        						personalConfigId:row.id,
        						type:0,
        						paramKey : $('#s_personalConfigDetail_key').val(),
        						paramValue : $('#s_personalConfigDetail_value').val(),
        						trefresh:new Date().getTime()
        					});
        				}
        			},
        			error : function(data){
        				$.messager.alert('提示', '未知错误!');
        			}
        		});  
            	
            }  
        });  
}

function personalConfigDetailSearch() {
	var row = $('#personalConfiggrid').datagrid('getSelected');  
	
	if (row){
		$('#personalConfigDetailgrid').datagrid('load', {
			personalConfigId:row.id,
			type:0,
			paramKey : $('#s_personalConfigDetail_key').val(),
			paramValue : $('#s_personalConfigDetail_value').val(),
			trefresh:new Date().getTime()
		});
	}
	
}
function areaInitConfigSave()
{
    var entForm = $('#areaInitConfig_form');
    
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
    $.messager.progress({
         title:'请等待',
         msg:'添加/修改数据中...'
        });
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : '../personalconfig/initArea.do',
        data : enterpriseinfo,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.alert("提示", data.content); 
            $('#personalConfiggrid').datagrid('load');
            $('#areaInitConfig_dialog').dialog('close');
        },
        error : function(data){
            $.messager.progress('close');
            
            $.messager.alert('提示', data.content);
        }
    });  
}
function areaInit()
{
    showDialog('#areaInitConfig_dialog','区域初始化'); 
    $('#areaInitConfig_form').form('clear');   
}

function batchAddConfigSave()
{
    var erow = $('#personalConfiggrid').datagrid('getSelected'); 
    if(erow==null)
    {
        $.messager.alert('提示', '请先选择一行!'); 
        return;
    }
    $.messager.progress({
         title:'请等待',
         msg:'添加/修改数据中...'
     });
   
     var start =$('#start').val();
     var end =$('#end').val();
     var data= JSON.stringify({configId:erow.id,start:start,end:end});
     
      jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : '../personalconfig/batchAdd.do',
        data : data,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.alert("提示", data.content); 

            $('#batchAdd_dialog').dialog('close');
			$('#personalConfigDetailgrid').datagrid('load', {
				personalConfigId:erow.id,
				type:0,
				trefresh:new Date().getTime()
			});
        },
        error : function(data){
            $.messager.progress('close');
            
            $.messager.alert('提示', data.content);
        }
    });
}

