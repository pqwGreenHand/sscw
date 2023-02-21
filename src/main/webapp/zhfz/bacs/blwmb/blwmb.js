

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
		sortName: 'sortNo',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'name',title:'笔录word模板名',width:80},
		    {field:'content',title:'word内容',width:300},
			{field:'sortNo',title:'访问热度',width:60},
            {field:'type',title:'模板类型',width:60,formatter:function (value,row,index) {
                    return ['公用模板','个人模板'][value];
                }},
            {field:'userName',title:'所属用户',width:60,formatter:function (value,row,index) {
                    return value || '--/--';
                }},
			{field:'createdTime',title:'创建时间',width:80,formatter:function (value,row,index) {
					return formatter(new Date(value));
                }},
			{field:'id',title:'操作',width:150,
		    	formatter:function(value,row,index){
		    		var e='',d='',u,l;
					// if(isGridButton("recordTemplateLook")){
					// 	l ='<span class="spanRow"><a href="#" class="gridlook" onclick="templateLook('+value+')">预览</a></span>';
					// }
                    if(isGridButton("recordTemplateDownload")){
                        u ='<span class="spanRow"><a href="load.do?id='+value+'" target="_blank" class="gridedit">下载</a></span>';
                    }
                    if(isGridButton("recordTemplateEdit")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="templateEdit('+index+')">编辑</a></span>';
                    }
					if(isGridButton("recordTemplateRemove")){
						d ='<span class="spanRow"><a href="#" class="griddel" onclick="templateRemove('+value+')">删除</a></span>';
					}
		    		return e+u+d;
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
				name : $('#q_name').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
	$('#fudong').remove();
});

function saveEnterprise(){
	console.log('rightsTemplateAdd:submit')
	if(!checkForm('#certificate_form')){
		return;
	}
    var jsonForm= new FormData();;
    jsonForm.append('id',$("#id").val());
    jsonForm.append('sortNo',$("#sort-no").val());
    var file = $("#word-file")[0].files[0];
    if(file)
    	jsonForm.append('wordFile',file);
    jsonForm.append('type',$("#save-type").combobox('getValue') || 0);

	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		url : url,
		data : jsonForm,
		dataType : 'json',
        cache:false,
        traditional: true,
        contentType: false,
        processData: false,
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

function templateAdd(){
	showDialog('#certificate_dialog','新增笔录模板');
	$('#certificate_form').form('clear');  
	url = 'add.do';
}
function templateEdit(index){
    showDialog('#certificate_dialog','编辑笔录模板');
    var row = $('#certificategrid').datagrid('getRows')[index];
    $('#id').val(row.id);
    $('#save-type').combobox('select', row.type);
    $('#sort-no').val(row.sortNo);
    $('#word-name').val(row.name);
    url = 'edit.do';
}
function templateLook(value){
    readDoc(value);
}

function templateRemove(value){
	$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
        if (r){
		$.messager.progress({
	   	 title:'请等待',
	   	 msg:'删除数据中...'
	    });
		jQuery.ajax({
			type : 'POST',
			url : 'remove.do',
			data : {id:value},
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
	var type = $('#query-type').combobox('getValue');
	$('#certificategrid').datagrid('load', {
        name : $('#q_name').val(),
		type : type == 0 ? '' : type,
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#left-search').form('clear');
	$('#query-type').combobox('setValue', '');
}

function doSelectWord() {
	$('#word-file').click();
}

function doWordChange(_this) {
	$('#word-name').val($(_this).val());
}