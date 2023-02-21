var url;
function init(){
    $('#save-serial').combogrid({
        url:'../combobox/listSerial.do',
        panelWidth:223,
        idField:'id',
        textField:'value',
        columns:[[
            {field:'value',title:'嫌疑人姓名',width:100},
            {field:'groupName',title:'案件编号',width:120},
        ]],
        onSelect: function (record) {
        	console.log(111);
            var data = $('#save-serial').combogrid('grid').datagrid('getSelected');
            $('#save-police-ask,#save-police,#save-police-record').combobox('reload','../combobox/listSerialPolice.do?caseId=' + (parseParam(data.id)['caseId'] || 0));
        }
    });
    // $('#query-person-name').combobox({
    //     url:'../combobox/listSerial.do',
    //     valueField:'id',
    //     textField:'value',
    // });
    //办案中心下拉框
       $('#area').combobox({
        url : '../combobox/comboArea.do',
        valueField : 'id',
        textField : 'name',
        onLoadSuccess : function(data) {
        	for(var i = 0;i<data.length;i++){
        		if(ssareaid==data[i].id){
        			$('#area').combobox('setValue', data[i].name);
        		}
        	}
        }
       });

}


$(function(){
    init();
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
		sortName: 'id',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'caseNo',title:'案件编号',width:80},
		    // {field:'registerUserName',title:'注册人姓名',width:80},
		    {field:'policeName',title:'押送民警',width:80},
			{field:'personName',title:'救助人姓名',width:80},
			{field:'areaName',title:'办案场所',width:80},
			{field:'doctor',title:'救治医生',width:80},
			{field:'item',title:'救助事项',width:120},
			{field:'description',title:'描述',width:200},
            {field:'rescueTime',title:'救助时间',width:80,formatter:function (value,row,index) {
                    return formatter(new Date(value),'yyyy-MM-dd hh:mm:ss');
                }},
			{field:'createdTime',title:'创建时间',width:80,formatter:function (value,row,index) {
					return formatter(value,'yyyy-MM-dd hh:mm:ss');
                }},
			// {field:'updatedTime',title:'更新时间',width:80,formatter:function (value,row,index) {
			// 		return formatter(new Date(value),'yyyy-MM-dd hh:mm:ss');
            //     }},
			{field:'id',title:'操作',width:150,
		    	formatter:function(value,row,index){
		    		var e='',d='';
                    if(isGridButton("rescueEdit")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="edit('+index+')">编辑</a></span>';
                    }
					if(isGridButton("rescueRemove")){
						d ='<span class="spanRow"><a href="#" class="griddel" onclick="remove('+value+')">删除</a></span>';
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
				name : $('#q_name').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
	$('#fudong').remove();
});

function saveEnterprise(){
	if(!checkForm('#save-form')){
		return;
	}
    var jsonForm = {};
	jsonForm = $('#save-form').serializeObject(jsonForm);
    jsonForm = parseParam(jsonForm.serial,jsonForm);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	});
    jQuery.ajax({
        type: 'POST',
        url: url,
        data: jsonForm,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#certificate_dialog').hide();
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function add(){

    $('#certificate_dialog').show();
	url = 'add.do';
	$('#save-form').form('clear');
	$('#rescue-time').datetimebox('setValue',new Date().getTime());
}
function edit(index){
	url = 'edit.do';

    $('#certificate_dialog').show();
    var row = $('#certificategrid').datagrid('getRows')[index];
	$('#save-police').combobox('reload','../combobox/listSerialPolice.do?caseId=' + row.caseId);
	var serial = 'serialId:{serialId},personId:{personId},areaId:{areaId},caseId:{caseId}'.format(row);
	$('#save-serial').combogrid('setValue',serial);
    $('#policeNo').val(row.policeNo);

    $('#save-form').form('load',row);
}


function remove(value){
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
    var param = {trefresh:new Date().getTime(),
                name:$('#query-person-name').val(),
                 areaId : $('#area').combobox('getValue'),
                };
	$('#certificategrid').datagrid('load', param);
}

function doClear() {
	$('#search').form('clear');
}
function finduser1(index) {
    var userNo ;
    if(index==0){
        userNo= $('#policeNo').val();
    }else if(index==1){
        userNo= $('#e_policeNo').val();
    }else if(index==2){
        userNo= $('#ep_policeNo').val();
    }else if(index==3){
        userNo= $('#add_policeNo').val();
    }
    if(!userNo){
        return;
    }
    var userNoInfo = {"userNo":userNo};
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/order/searchUser.do',
        data : jsonrtinfo,
        success : function(data) {
            if(data != null && data.id != null) {
                if(index==0){
                    $("#policeId").val(data.id);
                }else if(index==1){
                    $('#e_policeId').val(data.id);
                }else if(index==2){
                    $('#ep_policeId').val(data.id);
                }else if(index==3){
                    $('#add_policeId').val(data.id);
                }
            }else{
                $.messager.alert('错误', userNo + '该警号不存在!');
                if(index==0){
                    $('#policeNo').val('');
                }else if(index==1){
                    $('#e_policeNo').val('');
                }else if(index==2){
                    $('#ep_policeNo').val('');
                }else if(index==3){
                    $('#add_policeNo').val('');
                }
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在！');
            if(index==0){
                $('#policeNo').val('');
            }else if(index==1){
                $('#e_policeNo').val('');
            }else if(index==2){
                $('#ep_policeNo').val('');
            }else if(index==3){
                $('#add_policeNo').val('');
            }
        }
    });
}