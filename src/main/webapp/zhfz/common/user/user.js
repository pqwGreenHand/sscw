$(function(){


	$('#user_datagrid').datagrid({
		title:'用户',
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '正在加载...',
		method: 'get',
		url: 'getUsersInfo.do',
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
			{field:'loginName',title:'登录名',width:150},
			{field:'realName',title:'真实姓名',width:150},
			{field:'jobTitle',title:'职务',width:150},
			{field:'certificateNo',title:'证件号码',width:150},
			{field:'organizationName',title:'单位名称',width:150},
			{field:'mobile',title:'手机号码',width:150},
			{field:'isActive',title:'是否激活',width:70,
				formatter:function(value,rec){
					return formatTF(value);
				}
			},
			{field:'操作',title:'操作',width:200,
				formatter : function(value, row, index) {
					var e ='';
					var d ='';
					if(isGridButton("editUser")){
						 e = '<span class="spanRow"><a href="javascript:void(0)" class="gridedit" onclick="editUser('
								+ index + ')">修改</a> </span>';
					}
					if(isGridButton("deleteUser")){
						 d = '<span class="spanRow"><a href="javascript:void(0)" class="griddel" onclick="deleteUser('
								+ index + ')")">删除</a></span>';
					}
					return e + d;
					}
			}
		]],
		pagination:true,
		pageSize:15,
		pageList: [15,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#toolbar',
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			rolegridData(rowData);
		}
		
	});
	var p = $('#user_datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			doSearch();
			return false;
		}
	});
	$('#role_id').combobox({   
		url:'../../common/combobox/getRoles.do',
		valueField:'id',   
		textField:'value' 
	});
	$('#s_certificate_type_id').combobox({   
	    url:'../../common/combobox/certificateTypes.do',   
	    valueField:'id',   
	    textField:'value',
	});
	$('#fudong').remove();
});
function rolegridData(rowData){
	var rowsData = $('#role_datagrid').datagrid('getData');
	
	$.each(rowsData.rows, function(index, value){
		if(rowData.roleId==value.id){
			$('#role_datagrid').datagrid("checkRow", index);
		}
			
	});
}


function addUser(){
	$('#certificateTypeId').combobox({   
	    url:'../../common/combobox/certificateTypes.do',   
	    valueField:'id',   
	    textField:'value',
	    onLoadSuccess: function(data){
            $('#certificateTypeId').combobox('setValue', 191);
            //$('#certificateTypeId').combobox('setValue', data[0].id);// 191 警官证
        }
	});
	
	$('#roleId').combobox({   
		url:'../../common/combobox/getRoles.do',
		valueField:'id',   
		textField:'value' 
	});
	$('#organizationId').combobox({   
		url:'../../common/combobox/listAllOrganizationNameCombobox.do',
		valueField:'id',   
		textField:'value' 
	});
	showDialog('#user_dialog','新增用户'); 
	$('#user_form').form('clear'); 
	$('#sex').combobox('setValue',1);
	selectCheck("#isActive",1);
	url = 'add.do';
}

function editUser(index){
	$('#certificateTypeId').combobox({   
	    url:'../../common/combobox/certificateTypes.do',   
	    valueField:'id',   
	    textField:'value',
	    onLoadSuccess: function(data){
            $('#certificateTypeId').combobox('setValue', 191);
            //$('#certificateTypeId').combobox('setValue', data[0].id);// 191 警官证
        }
	});
	$('#roleId').combobox({   
		url:'../../common/combobox/getRoles.do',
		valueField:'id',   
		textField:'value' 
	});
	//下拉选择部门--w.xb
	$('#organizationId').combobox({
		url:'../../common/combobox/listAllOrganizationNameCombobox.do',  
		valueField:'id',   
		textField:'value' 
	});
	var row = $('#user_datagrid').datagrid('getRows')[index];  
	if (row){
		showDialog('#user_dialog','编辑用户信息');
	    $('#user_form').form('clear');
	    $('#certificateTypeId').combobox('setValue',row.certificateTypeId);
	    $('#user_form').form('load',row);
	    $('#certificateTypeId').combobox('setValue',row.certificateTypeId);
	    $('#organizationId').combobox('setValue',row.organizationId);
	    $('#roleId').combobox('setValue',row.roleId);
	    $('#id').val(row.id);
	    $('#isActive').val(row.isActive);
	    url = 'edit.do?id='+row.id;  
	} else{
		$.messager.alert('提示', '请选择一行!'); 
	} 
}

function deleteUser(index){
	
	var row = $('#user_datagrid').datagrid('getRows')[index]; 
    if (row){  
        $.messager.confirm('提示','是否删除数据?',function(r){  
        	$.messager.progress({
       	   	 title:'请等待',
       	   	 msg:'删除数据中...'
       	    });
        	
            if (r){
            	jQuery.ajax({
        			type : 'POST',
        			contentType : 'application/json',
        			url : 'delete.do',
        			data : '{"id":'+row.id+'}',
        			dataType : 'json',
        			success : function(data){
//        				$('#user_datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
        				doSearch();
        				$.messager.progress('close');
                        $.messager.show({
                            title:'提示',
                            msg:data.content,
                            timeout:3000
                        });
        			},
        			error : function(data){
        				$.messager.progress('close');
        				$.messager.alert('错误', '未知错误!');
        			}
        		});  
            	
            }  else {
            	$.messager.progress('close');
            }
        });  
    } else{
		$.messager.alert('提示', '请选择一行!'); 
	}
	
}

function doSearch() {
	$('#user_datagrid').datagrid('load', {
		login_name_t : $('#s_login_name').textbox('getValue'),
		real_name_t : $('#s_real_name').textbox('getValue'),
		certificate_type_id_t : $('#s_certificate_type_id').combobox('getValue'),
		role : $('#role_id').combobox('getValue'),
		certificate_no_t : $('#s_certificate_no').textbox('getValue'),
		mobile_t : $('#s_mobile').textbox('getValue'),
		organization_name_t:$('#s_organization_name').textbox('getValue'),
		organization_type_t:$('#s_organization_type').val(),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_certificate_type_id').combobox('clear');   
	$('#role_id').combobox('clear');   
	$('#s_login_name').textbox('setValue','');
	$('#s_real_name').textbox('setValue','');
	$('#s_job_title').textbox('setValue','');
//	$('#s_certificate_type_id').prop('value','');
	$('#s_certificate_no').textbox('setValue','');
	$('#s_mobile').textbox('setValue','');
	$('#s_start_date').datebox('setValue','');
	$('#s_end_date').datebox('setValue','');
	$('#s_organization_name').textbox('setValue','');
	$('#s_organization_type').prop('value','');
	
}

function saveData(){
	var sex = $('#sex').combobox('getValue');
	if($("#loginName").val()!=null&&$("#loginName").val()!=''&&
			$("#realName").val()!=null&&$("#realName").val()!=''&&
			$("#password").val()!=null&&$("#password").val()!=''&&
			sex!=null&&sex!=''&&
			$("#jobTitle").val()!=null&&$("#jobTitle").val()!=''&&
			$("#organizationId").combobox('getValue')!=null&&$("#organizationId").combobox('getValue')!=''&&
			$("#certificateTypeId").combobox('getValue')!=null&&$("#certificateTypeId").combobox('getValue')!=''&&
			$("#certificateNo").val()!=null&&$("#certificateNo").val()!=''&&
		    $("#type").combobox('getValue')!=null&&$("#type").combobox('getValue')!=''
				){
		var url="";
		var id =  $('#id').val();
		if(id!=null && id.length>0){
			url=""; 
			url ="edit.do";
			
			$.messager.progress({
			   	 title:'请等待',
			   	 msg:'修改用户中...'
			    });
		}	else
		{	url="";
			 url = "add.do";
			 $.messager.progress({
			   	 title:'请等待',
			   	 msg:'添加用户中...'
			    });
		}
		var elemUserinfo = $('#user_form');
		var userinfo = elemUserinfo.serializeObject();
		if ($("#isActive").get(0).checked) {
			userinfo["isActive"] = 1;
			}else{
			userinfo["isActive"] = 0;
			}
		var jsonuserinfo = JSON.stringify(userinfo);
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : url,
			data : jsonuserinfo,
			dataType : 'json',
			success : function(data){
				$('#user_datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
				refrshAuthority();
                $.messager.progress('close');
                $('#user_dialog').dialog('close');
                if(url == 'edit.do'){
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000
                    });
				}else{
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000
                    });
                    $("#loginName").prop('value','');
                    $("#realName").prop('value','');
                    $("#jobTitle").prop('value','');
                    $("#certificateNo").prop('value','');
                    $("#loginName").prop('value','');
                    $("#mobile").prop('value','');
                    $("#telephone").prop('value','');
                    $("#email").prop('value','');
                    $("#address").prop('value','');
                    $("#type").combobox('clear');
                    $("#description").prop('value','');
				}

			},
			error : function(data){
				$('#user_dialog').dialog('close');
				$.messager.alert('错误', '修改失败（edit）!');
			}
		}); 
		
	}else{
		$.messager.alert('警告', '信息填写不完整!');
	}
}

function refrshAuthority() {
    $.ajax({
        //async: false,
        type: 'POST',
        url: '../authority/refrshAuthority.do',
        dataType: 'json',
        success: function (data) {
        	 $.messager.show({
                 title:'提示',
                 msg:"刷新权限成功",
                 timeout:3000
             });
        },
        error: function () {
            $.messager.alert('警告', '刷新失败（refrshAuthority）!');
        }
    })
}