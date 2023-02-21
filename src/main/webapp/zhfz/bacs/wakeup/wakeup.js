
$(function(){
    loadArea();
    bindClick();
});

var _jzw_save = {form:'',url:'',type:0,init:function (url,type) {
        _jzw_save.url = url;
        _jzw_save.type = type || 0;
        if(type == 1){
            _jzw_save.form = '#out-form';
        }else
            _jzw_save.form = '#in-form';

    }}; //type 0 add\edit,1out
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
            var data = $('#save-serial').combogrid('grid').datagrid('getSelected');
            $('#out-police,#in-police').combobox('reload','../combobox/listSerialPolice.do?caseId=' + (parseParam(data.id)['caseId'] || 0));
        }
    });
    // $('#query-person-name').combobox({
    //     url:'../combobox/listSerial.do',
    //     valueField:'id',
    //     textField:'value',
    // });

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
		    {field:'inUserName',title:'押送民警',width:80},
		    {field:'outUserName',title:'提取民警',width:80},
			{field:'personName',title:'人员姓名',width:80},
			{field:'areaName',title:'办案场所',width:120},
			{field:'outUserId',title:'状态',width:80,formatter:function (value) { return value ? '已提取' : '醒酒中'  }},
			{field:'inDescription',title:'描述',width:80},
            {field:'startTime',title:'开始时间',width:120,formatter:function (value,row,index) {
                    return formatter(new Date(value),'yyyy-MM-dd hh:mm:ss');
                }},
            {field:'endTime',title:'结束时间',width:120,formatter:function (value,row,index) {
                    return formatter(value,'yyyy-MM-dd hh:mm:ss');
                }},
			{field:'createdTime',title:'创建时间',width:120,formatter:function (value,row,index) {
					return formatter(value,'yyyy-MM-dd hh:mm:ss');
                }},
			// {field:'updatedTime',title:'更新时间',width:80,formatter:function (value,row,index) {
			// 		return formatter(new Date(value),'yyyy-MM-dd hh:mm:ss');
            //     }},
			{field:'id',title:'操作',width:150,
		    	formatter:function(value,row,index){
		    		var e='',d='',u='';
                    if(isGridButton("wakeupEdit") && !row.outUserId){
                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="edit('+index+',\'编辑醒酒记录\',0)">编辑</a></span>';
                        if(row.endTimeStr==null){
                            u ='<span class="spanRow"><a href="#" class="gridedit" onclick="edit('+index+',\'提取\',1)">提取</a></span>';
                        }

                    }
					if(isGridButton("wakeupRemove")){
						d ='<span class="spanRow"><a href="#" class="griddel" onclick="remove('+value+')">删除</a></span>';
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
	if(!validate.init(_jzw_save.form)){
	    return null;
    }
    var jsonForm = {};
    jsonForm = $(_jzw_save.form).serializeObject(jsonForm);
	if(!_jzw_save.type)
        jsonForm = parseParam(jsonForm.serial,jsonForm);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	});
    jQuery.ajax({
        type: 'POST',
        url: _jzw_save.url,
        data: jsonForm,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#certificate_dialog').hide();
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
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
	$('.save-form').hide();
    $('#cuffNumber-tr').show();
    $('#save-title').html('新增醒酒记录');
	_jzw_save.init('add.do');
	$(_jzw_save.form).show().form('clear');
	$('#start-time').datetimebox('setValue',new Date().getTime());
}
function edit(index,title,type){
    $('#save-title').html(title);
    _jzw_save.init('edit.do',type);
    $('#certificate_dialog').show();
    $('.save-form').hide();
    var row = $('#certificategrid').datagrid('getRows')[index];

    console.log()
    if(type==0){
        $('#in-police').combobox('reload','../combobox/listSerialPolice.do?caseId=' + row.caseId);
        row['serial'] = row.personName;
        $('#cuffNumber-tr').hide();
        $('#inUserId').val(row.policeNo);
        $('#cuffNumber').val(0);
    }else{
        $('#out-police').combobox('reload','../combobox/listSerialPolice.do?caseId=' + row.caseId);
		row.endTimeStr = formatter(new Date(),'yyyy-MM-dd hh:mm:ss');

    }
  		 $(_jzw_save.form).show().form('load',row);
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
    var param = {trefresh:new Date().getTime(),name:$('#query-person-name').val(),areaId : $('#s_areaId').combobox("getValue")};
	$('#certificategrid').datagrid('load', param);
}

function doClear() {
	$('#search').form('clear');
    $("#s_areaId").combobox("setValue","");
}



//绑定手环回车事件
function bindClick(){
    $('#cuffNumber').keydown(function(event){
        if(event.keyCode == 13){
            var data = checkRingNo($("#cuffNumber").val(),1);
            if(!(data.isError)){
                if(data.callbackData.cuffNo != null&&data.callbackData.cuffNo != ""){
                    if(data.callbackData.status == 1){
                        $("#cuffNumber").val(data.callbackData.cuffNo);
                        $("#cuffId").val(data.callbackData.id);
                    }else{
                        $.messager.alert(data.title, data.content);
                        $("#cuffNumber").val("");
                        $("#detainPoliceId").val("");
                    }
                }
            }else{
                $.messager.alert(data.title, data.content);
                $("#cuffNumber").val("");
                $("#detainPoliceId").val("");
            }
        }
    });
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
                    $("#inUserId").val(data.id);
                }else if(index==1){
                    $('#outUserId').val(data.id);
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

//加载办案场所
function loadArea(){
    $('#s_areaId').combobox({
        url: contextPath + '/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(data != null && data.length > 0){
                $('#s_areaId').combobox('setValue',data[0].id);
            }*/
        }
    });
}