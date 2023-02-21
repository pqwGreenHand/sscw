$(function(){
	$('#datagrid').datagrid({
		title:'天地入区操作日志',
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
		url: 'listTd.do',
		idField:'id',
		queryParams : {
			trefresh:new Date().getTime()
		},
		singleSelect:false,
		frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'uuid',title:'嫌疑人uuid',width:150},
            {field:'name',title:'嫌疑人姓名',width:80},
            {field:'personId',title:'嫌疑人id',width:80},
            {field:'cuffNo',title:'手环编号',width:80},
            {field:'ajmc',title:'案件名称',width:150},
            {field:'ajbh',title:'案件编号',width:200},
            {field:'rybh',title:'人员编号',width:200},
            {field:'inTime',title:'入区时间',width:100,
                formatter:function(value,rec){
                    return valueToDate(value);
                }
            },
            {field:'outTime',title:'出区时间',width:150,
                formatter:function(value,rec){
                    return valueToDate(value);
                }
            },
            {field:'sfcl',title:'是否处理',width:150,
                formatter:function(value,rec){
                    if(value==0){
                        return "<font color='red'>未处理</font>";
					}else{
                        return "已处理";
					}
                }
            },
            {field:'content',title:'失败原因',width:200},
            {field:'params',title:'传参',width:200}

            /*,{field:'id',title:'操作',width:100,
                formatter:function(field, rec, index){
                    var a='';
                        if(isGridButton("operlogRemove")){
                            a ='<a href="#" class="griddel" onclick="operlogRemove('+field+')">删除</a>';
                        }
                    return a;
                }
            }*/
			
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#toolbar'
		
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			$('#datagrid').datagrid('load', {
                sfcl:$('#sfcl').combobox('getValue'),
                system_name_t : $('#s_name').val(),
                start_date_d : $('#s_start_date').datebox('getValue'),
                end_date_d : $('#s_end_date').datebox('getValue'),
				trefresh:new Date().getTime()
			});
			return false;
		}
	});
	$('#fudong').remove();
});

function plcl(){
    var items = $('#datagrid').datagrid('getSelections');
    // var ids = [];
    var ids="";
    console.log(items);
    if(items.length>0){
        $(items).each(function(){
            // ids.push(this.id);
            ids+=this.id+",";
        });
    }else{
        $.messager.alert('提示', "至少选择一个嫌疑人进行处理！");
        return false;
    }
    console.log(ids.substring(0,ids.length-1));
    jQuery.ajax({
        type : 'GET',
		async:true,
        contentType : 'application/json',
        dataType : 'json',
        url : 'batchClByIds.do' ,
        data : {
            ids : ids.substring(0,ids.length-1)
        },
        success : function(data){

            $.messager.alert(data.title, data.content);
            $('#datagrid').datagrid('load', {
                sfcl:$('#sfcl').combobox('getValue'),
                system_name_t : $('#s_name').val(),
                start_date_d : $('#s_start_date').datebox('getValue'),
                end_date_d : $('#s_end_date').datebox('getValue'),
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

function operlogEdit(index){
	$.messager.alert('indexRow', $('#datagrid').datagrid('selectRow',index));
}

function operlogRemove(value){
	$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
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
					$.messager.alert(data.title, data.content); 
					$('#datagrid').datagrid('load', {
                        system_name_t : $('#s_name').val(),
                        start_date_d : $('#s_start_date').datebox('getValue'),
                        end_date_d : $('#s_end_date').datebox('getValue'),
						trefresh:new Date().getTime()
					});
					$.messager.progress('close');
				},
				error : function(data){
					//exception in java
					$.messager.progress('close');
					$.messager.alert('Error', '未知错误!');
				}
			});  
        }
	});  
}

function doSearch() {
	$('#datagrid').datagrid('load', {
        sfcl:$('#sfcl').combobox('getValue'),
		system_name_t : $('#s_name').val(),
		start_date_d : $('#s_start_date').datebox('getValue'),
        end_date_d : $('#s_end_date').datebox('getValue'),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_name').prop('value','');
	$('#sfcl').combobox('setValue','');
	$('#s_start_date').datebox('setValue','');
	$('#s_end_date').datebox('setValue','');
}
