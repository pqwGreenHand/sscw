$(function(){

    $('#role_datagrid').datagrid({
        title:'角色',
        iconCls:'icon-datagrid',
        region: 'east',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '正在加载...',
        method: 'get',
        rownumbers :false,
        url: 'getRoles.do',
        remoteSort: false,
        singleSelect: true ,
        columns:[[
            {field:'checked',checkbox:true,width:50},
            {field:'name',title:'角色',width:200}
        ]],
        toolbar:'#roletoolbar',
        onClickRow: function(rowIndex, rowData){
        }
    });
	$('#fudong').remove();
});

function checkRoles(){
	var rows=$('#role_datagrid').datagrid('getRows');
	$('#role_datagrid').datagrid('clearSelections');
	$('#role_datagrid').datagrid('uncheckAll');
	for(var i=0;i<rows.length;i++)
	{
		if(rows[i].isSelect)
		{
			$('#role_datagrid').datagrid('checkRow',i);//根据id选中行 
		}
	}
}

function saveUserRole(){
	var rows = $('#role_datagrid').datagrid('getChecked');
	console.log(rows);
	var row = $('#user_datagrid').datagrid('getSelected');  
    if (row){  
        $.messager.confirm('提示','确定保存?',function(r){
            if (r){
            	jQuery.ajax({
        			type : 'POST',
        			contentType : 'application/json',
        			url : 'updateUserRoles.do',
        			data : '{"userId":'+row.id+',"roleId":"'+rows[0].id+'"}',
        			dataType : 'json',
        			success : function(data){
        				refrshAuthority();
                        $.messager.show({
                            title:'提示',
                            msg:data.content,
                            timeout:3000
                        });
        				doSearch();
        			},
        			error : function(data){
        				$.messager.alert('Error', '保存角色失败（updateUserRoles）!');
        			}
        		});  
            	
            }  
        });  
    } else{
		$.messager.alert('提示', '先选择一行!');
	} 
}

function roleCancle(){
	$('#role_datagrid').datagrid('clearChecked');
}


