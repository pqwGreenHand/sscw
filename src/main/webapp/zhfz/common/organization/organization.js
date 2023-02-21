$(function(){
	$('#datagrid').datagrid({
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
            {title:'ID',field:'id',width:80,sortable:false,hidden:true}
		]],
		columns:[[
		    {field:'name',title:'部门名称',width:100},
			{field:'type',title:'部门类型',width:100,
		    	formatter:function(value){
		    		if(value == '0'){
		    			return '后台管理';
		    		}
		    		if(value == '1'){
		    			return '办案中心';
		    		}
		    		if(value == '2'){
		    			return '办案部门';
		    		}
		    		if(value == '3'){
		    			return '法制部门';
		    		}
		    		if(value == '4'){
		    			return '合成作战';
		    		}
		    		if(value == '5'){
		    			return '999体检';
		    		}
		    		if(value == '6'){
		    			return '看守所';
		    		}
		    		if(value == '7'){
		    			return '拘留所';
		    		}
		    		return value;
		    	}},
			{field:'address',title:'部门地址',width:120},
			{field:'proName',title:'上级部门',width:120},
			{field:'telephone',title:'电话',width:70},
			{field:'postcode',title:'邮编',width:80},
			{field:'id',title:'操作',width:80,
				formatter:function(value,rec,index){
					var e = '';
					var u = '';
					var d = '';
					if(isGridButton("organizationEdit")){
						e = '<span class="spanRow"><a href="#" class="gridedit" onclick="organizationEdit('+index+')">修改</a></span>';
					}
//					if(isGridButton("updateRela")){
//						u = '<span class="spanRow2"><a href="#" class="gridupper" onclick="updateRela('+value+',\'parent\')">上级部门</a></span>';
//					}
					if(isGridButton("organizationRemove")){
						d = '<span class="spanRow"><a href="#" class="griddel" onclick="organizationRemove('+value+')">删除</a></span>';
					}
					return e + u + d;
				}}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#organizationToolbar',
		onSelect: function(rowIndex, rowData){
			relationload(rowData);
		},
		onLoadSuccess:function(data){
			if($('#s_type').combobox('getValue')==''){
				$('#s_type').combobox('clear');
			}
		}
	});

	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
//			$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()} );
			$('#datagrid').datagrid('load', {
				organization_t : $('#s_organization').val(),
				type_t : $('#s_type').combobox('getValue'),
				trefresh:new Date().getTime()
			});
			return false;
		}
	});
	
	$('#cdatagird').datagrid({
		title:'下级部门',
		iconCls:'icon-datagrid',
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: 'Loading...',
		method: 'get',
		url: 'listChildOrg.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
		                {title:'ID',field:'id',width:80,sortable:false,hidden:true}
		    		]],
		columns:[[
		    {field:'name',title:'部门名称',width:100},
			{field:'type',title:'部门类型',width:50,
		    	formatter:function(value){
		    		if(value == '0'){
		    			return '后台管理';
		    		}
		    		if(value == '1'){
		    			return '办案中心';
		    		}
		    		if(value == '2'){
		    			return '办案部门';
		    		}
		    		if(value == '3'){
		    			return '法制部门';
		    		}
		    		if(value == '4'){
		    			return '合成作战';
		    		}
		    		if(value == '5'){
		    			return '999体检';
		    		}
		    		if(value == '6'){
		    			return '看守所';
		    		}
		    		if(value == '7'){
		    			return '拘留所';
		    		}
		    		return value;
		    	}},
			{field:'telephone',title:'电话',width:100}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true
	});
	
	$('#fudong').remove();
});

function organizationAdd(){
	$('#UpOrgSelect').css('display','block');
	$('#parentId').combobox({   
	    url: contextPath + '/zhfz/common/combobox/listAllOrganizationNameCombobox.do',
	    valueField:'id',   
	    textField:'value',
        required: true,
        filter: function(q, row){
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        }
	});
	showDialog('#organization_dialog','新增部门'); 
	$('#organization_form').form('clear');  
	url = 'add.do';
}

function organizationEdit(index){
	$('#UpOrgSelect').css('display','block');
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row){
		showDialog('#organization_dialog','编辑部门');
		$('#organization_form').form('clear');
		$('#parentId').combobox({   
			url:'upOrgCombo.do',   
			valueField:'id',   
			textField:'name',
			required: true,
			filter: function(q, row){
				$('#parentId').combobox('setValue',row.id);
			}
		});
	    $('#organization_form').form('load',row);
		$('#type').combobox('setValue',row.type);
	    console.log(row);
	    if(row.name=="系统管理"){
	    	$('#UpOrgSelect').css('display','none');
	    	$('#parentId').combobox('setValue',0);
	    }
	    url = 'edit.do';
	} else{
		$.messager.alert('提示', '请选择一条数据!'); 
	} 
}

function organizationgSearch() {
	var type = $('#s_type').combobox('getValue');
	if(type == '9'){
		type = '';
	}
	$('#datagrid').datagrid('load', {
		organization_t : $('#s_organization').val(),
		type_t : type,
		trefresh:new Date().getTime()
	});
}

function saveOrganization(){
	if(!checkForm('#organization_form')){
		return;
	}
	var orgForm = $('#organization_form'); 
	var organizationinfo = JSON.stringify(orgForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'数据处理中...'
	    });
	jQuery.ajax({
		type : 'Post',
		contentType : 'application/json',
		url : url,
		data :organizationinfo,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.alert(data.title, data.content); 
			$('#organization_dialog').dialog('close');
			$('#datagrid').datagrid('reload',
					{
				       organization_t : $('#s_organization').textbox('getValue'),
				       type_t : $('#s_type').combobox('getValue'),
				       trefresh:new Date().getTime()
				    });
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('错误', '未知错误!');
		}
	});  
}

function organizationRemove(value){
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
        				$('#datagrid').datagrid('load', {
        					organization_t : $('#s_organization').textbox('getValue'),
        					type_t : $('#s_type').combobox('getValue'),
        					trefresh:new Date().getTime()
        				});
        				$.messager.progress('close');
        				var rowData = $('#datagrid').datagrid('getSelected'); 
        				$('#pdatagrid').datagrid('load',{id:rowData.id,trefresh:new Date().getTime()});
        		    	$('#cdatagird').datagrid('load',{id:rowData.id,trefresh:new Date().getTime()});
        			},
        			error : function(data){
        				$.messager.alert('错误', '未知错误!');
                        $.messager.progress('close');
        			}
        		});  
            	
            }  
        }); 
}

function doClear() {
	$('#s_organization').textbox('setValue',''),
	$('#s_type').combobox('setValue','9');
}

function relationload(rowData){
	$('#cdatagird').datagrid('load',{id:rowData.id,trefresh:new Date().getTime()});
}
function userImport(){
	showDialog('#import_dialog','民警导入'); 
}

function doImport(){  
	 $.ajaxFileUpload
     (
         {
             url: 'userImportByXls.do', //用于文件上传的服务器端请求地址
             secureuri: false, //是否需要安全协议，一般设置为false
             fileElementId: 'file', //文件上传域的ID
             async:false,
             dataType: 'json', //返回值类型 一般设置为json
             success: function (data, status)  //服务器成功响应处理函数
             {
            	 refrshAuthority();
            	 organizationgSearch();
                 console.info(data);
             },
             error: function (data, status, e)//服务器响应失败处理函数
             {
            	 //其实成功也走这段
            		$.messager.alert('提示', '部门用户导入成功!');
            	 console.info(data);
            	 console.info(e);
             }
         }
     )
    organizationgSearch();
	 refrshAuthority();
 	$('#import_dialog').dialog('close');
} 
function refrshAuthority() {
    $.ajax({
        //async: false,
        type: 'POST',
        url: '../authority/refrshAuthority.do',
        dataType: 'json',
        success: function (data) {

        },
        error: function () {
            $.messager.alert('警告', '未知错误!');
        }
    })
}
