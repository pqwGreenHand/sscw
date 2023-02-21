$(function(){
	$('#datagrid').datagrid({
		//title:'手环',
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '加载中...',
		method: 'get',
		url: 'list.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
			{field:'cuffNo',title:'手环编号',width:300,align:'center'},
			{field:'icNo',title:'IC卡编号',width:150,align:'center'},
			{field:'type',title:'手环类型',width:100,align:'center',formatter:function(value){
				if(value=='0'){
					return '手环'
				}else if(value=='1'){
					return '卡片'
				}
			}},
			{field:'status',title:'手环状态',width:100,align:'center',formatter:function(value){
				if(value=='0'){
					return '空闲'
				} 
				else if(value=='1'){
					return '<font color=\"green\">已绑定</font>'
				} 
				else if(value=='2'){
					return '<font color=\"red\">已损坏</font>'
				} else{
					return '无状态'
				}
			}},
			{field:'interrogateAreaName',title:'办案场所',width:300,align:'center'},
			{
				field : 'id',
				title : '操作',
				width : 300,
				align : 'center',
				formatter : function(value, row, index) {
					var e='';
					var d='';
						if(isGridButton("cuffedit")){
							e ='<span class="spanRow"><a href="#" class="gridedit" onclick="cuffedit('+index+')">修改</a></span>';
						}
						if(isGridButton("cuffdelete")){
							d ='<span class="spanRow"><a href="#" class="griddel" onclick="cuffdelete('+index+')">删除</a></span>';
						}
					return e+d;
				}
			}
		]],
		pagination:true,
		pageSize:20,
		pageList: [20,30,40,50,100],
		rownumbers:true,
		
		toolbar:'#cuffToolbar'
		
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(pageNumber, pageSize){
			doSearch();
			return false;
		}
	});

	$('#interrogateAreaId').combobox({
	    url:'../combobox/getAllInterrogateAreaName.do',
	    valueField:'id',   
	    textField:'value'
	});
	
	$('#s_cuff_interrogate_area_id').combobox({   
	    url:'../combobox/getAllInterrogateAreaName.do',
	    valueField:'id',   
	    textField:'value'  
	});
	
	$('#fudong').remove();
});


function cuffdelete(index){
	var rowData = $('#datagrid').datagrid('getRows')[index];
    var value=rowData.id;  
	$.messager.confirm('Confirm','确定删除?',function(r){  
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
                        $.messager.progress('close');
                        $.messager.show({
                            title:'提示',
                            msg:data.content,
                            timeout:3000
                        });
        				doSearch();

        			},
        			error : function(data){
        				$.messager.progress('close');
        				$.messager.alert('错误', '数据错误!'+data);
        			}
        		});  
            	
            }  
        });  
}
//手环导入
function cuffImport(){
    showDialog('#import_dialog','手环导入');
}
function doImport(){
//	var import_form=$('#import_form');
//	import_form.attr('action', contextPath+ "/interrogate/user/userImportByXls.do");
//
//	import_form.submit();

    $.ajaxFileUpload
    (
        {
            url: contextPath+ "/zhfz/bacs/cuff/cuffImportByXls.do", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'file', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data, status)  //服务器成功响应处理函数
            {
                $('#import_dialog').dialog('close');
                refrshAuthority();

            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                //其实成功也走这段
                $('#import_dialog').dialog('close');
                $("#datagrid").datagrid("reload");
                refrshAuthority();
                console.info(data);
                console.info(e);
            }

        }
    )
}

function refrshAuthority() {
    $.ajax({
        //async: false,
        type: 'POST',
        url: '/zhfz/zhfz/common/authority/refrshAuthority.do',
        dataType: 'json',
        success: function (data) {

        },
        error: function () {
            $.messager.alert('警告', '返回错误!');
        }
    })
}

function doSearch() {
	var ss=$('#s_cuff_stuts').combobox('getValue');
	var st=$('#s_cuff_type').combobox('getValue');
	var se=$('#s_cuff_interrogate_area_id').combobox('getValue'); 
	$('#datagrid').datagrid('load', {
		cuffNo : $('#s_cuff_no').val(),
		status : ss,
		type : st,
		interrogateAreaId : se,
		trefresh:new Date().getTime()
	});

}


function doClear() {
	$('#s_cuff_no').prop('value','');
	$('#s_cuff_stuts').combobox('setValues', '');
	$('#s_cuff_type').combobox('setValues', '');
	$('#s_cuff_interrogate_area_id').combobox('setValues', '');
}


function cuffadd(){
	showDialog('#data_dialog','新增手环'); 
	$('#data_form').form('clear');  
	url = 'add.do';
}


function cuffedit(index){
	var rowData = $('#datagrid').datagrid('getRows')[index]; 
	
	showDialog('#data_dialog','编辑手环');
	    $('#data_form').form('clear');
	    $('#data_form').form('load',rowData);
	    url = 'edit.do?id='+rowData.id;  
	
}

function saveData(){
	if(!checkForm('#data_form')){
		return;
	}
	var CuffForm = $('#data_form');
	var cuffFormJson = JSON.stringify(CuffForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : cuffFormJson,
		dataType : 'json',
		success : function(data){
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
			doSearch();
            var str2=data.content;
			if(str2=="修改成功!"){
               $('#data_dialog').dialog('close');
			}

		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '数据错误!'+data);
		}
	});  
}

function refrshCuff(){
	$.messager.progress({
		title:'请等待',
		msg:'刷新手环编号与IC卡编号关联关系中...'
	});
	$.ajax({
		type : 'POST',
		url :'refrshCuff.do',
		dataType : 'json',
		success : function(data) {
			cuffMap = data.callbackData;
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
		},
		error : function() {
			$.messager.alert('警告', '服务器内部错误!');
		}
	})
}


