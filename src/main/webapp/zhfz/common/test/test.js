var typeData=[];
$(function(){
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
		sortName: 'type',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'type',title:'字典类型编码',width:80},
		    {field:'codeKey',title:'字典键',width:80},
			{field:'codeValue',title:'字典值',width:80},
			{field:'keyDesc',title:'描述',width:80},
			{field:'id',title:'操作',width:150,
		    	formatter:function(value,row,index){
		    		var e='';
		    		var d='';
			    		if(isGridButton("codeEdit")){
			    			e ='<span class="spanRow"><a href="#" class="gridedit" onclick="codeEdit('+index+')">修改</a></span>';
			    		}
			    		if(isGridButton("codeRemove")){
			    			d ='<span class="spanRow"><a href="#" class="griddel" onclick="codeRemove('+value+')">删除</a></span>';
			    		}  
		    		return e+d;
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
				type : $('#s_certificate_name').val(),
				codeKey:$('#s_certificate_val').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
	$('#fudong').remove();
});

function saveEnterprise(){
	if(!checkForm('#certificate_form')){
		return;
	}
    var jsonForm={};
    jsonForm["id"]=$("#id").val();
    jsonForm["codeKey"]=$("#codeKey").val();
    jsonForm["codeValue"]=$("#codeValue").val();
	jsonForm["keyDesc"]=$("#keyDesc").val();
	jsonForm["type"]=$("#type").val();
	var enterpriseinfo = JSON.stringify(jsonForm);
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
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
			$('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
			$('#certificate_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('错误', '未知错误!');
		}
	});  
}

function codeAdd(){
	showDialog('#certificate_dialog','新增字典数据');
	$('#certificate_form').form('clear');  
	url = 'addCodeEntity.do';
}

function codeEdit(index){
	 var row = $('#certificategrid').datagrid('getRows')[index];
		showDialog('#certificate_dialog','编辑字典数据');
	    $('#certificate_form').form('clear');
	    $('#certificate_form').form('load',row);
	    url = 'updateCodeEntity.do?id='+row.id;  
}

function codeRemove(value){
	$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
        if (r){
		$.messager.progress({
	   	 title:'请等待',
	   	 msg:'删除数据中...'
	    });
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : 'removeCodeEntity.do?id='+value,
			dataType : 'json',
			success : function(data){
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
				$('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
				$.messager.progress('close');
			},
			error : function(data){
				//exception in java
				$.messager.progress('close');
				$.messager.alert('错误', '未知错误!');
			}
		});
        }});
	}

function doSearch() {
	$('#certificategrid').datagrid('load', {
        type : $('#s_type').val(),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_type').prop('value', '');
	$('#s_name').prop('value', '');
}
