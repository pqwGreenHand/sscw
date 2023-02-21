$(function(){
	$('#datagrid').datagrid({
		//title:'人员数据',
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: 'list.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'createdTime',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'name',title:'姓名',width:80},
            {field:'sexName',title:'性别',width:50},
            {field:'birth',title:'出生日期',width:80,
                formatter:function(value,rec){
                    return valueToDate(value,"yyyy-MM-dd");
                }
			},
			{field:'certificateTypeName',title:'证件类型',width:100},
			{field:'certificateNo',title:'证件号码',width:170},
            {field:'areaName',title:'办案场所',width:140},
            {field:'countryName',title:'国籍',width:100},
            {field:'nationName',title:'民族',width:100},
			{field:'createdTime',title:'创建时间',width:150,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},
			{field:'updatedTime',title:'修改时间',width:150,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},
			{
				field : '操作',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					    var e='';
						var d='';
							if(isGridButton("personEdit")){
								 e ='<span class="spanRow"><a href="#" class="gridedit" onclick="personEdit('+index+')">修改</a></span>';
				        		 }
							if(isGridButton("personRemove")){
								d ='<span class="spanRow"><a href="#" class="griddel" onclick="personRemove('+index+')">删除</a></span>';
				        		 }
	        		 return e + d;
				}
			}
		]],
		pagination:true,
		pageSize:20,
		pageList: [20,30,40,50,100],
		rownumbers:true,
		//行选择方法，进行条件触发
		onClickRow: function(rowIndex, rowData){

		},
		toolbar:'#toolbar'
		
	});

	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()} );
			return false;
		}
	});
    load();
	$('#fudong').remove();
});

function load() {
    $('#s_area_id').combobox({
        url: '../combobox/getAllInterrogateAreaName.do',
        valueField: 'id',
        textField: 'value',
        onChange: function (val) {
        }
    });
}

function personAdd(){
	$('#personform').form('clear');
	showDialog('#person','添加人员');
	url = 'insert.do';  
}

function personEdit(index){
	$('#personform').form('clear');
	var rowData = $('#datagrid').datagrid('getRows')[index];
	//console.info("=======birth1============"+rowData.birth);
	$('#personform').form('load',
        {
        id:rowData.id,
        name:rowData.name,
        sex:rowData.sex,
        certificateTypeId:rowData.certificateTypeId,
        certificateNo:rowData.certificateNo,
        oldName:rowData.oldName,
        nickname:rowData.nickname,
        birth:valueToDate(rowData.birth).substring(0,10),
        education:rowData.education,
        politic:rowData.politic,
        officer:rowData.officer,
        country:rowData.country,
        nation:rowData.nation,
        censusRegister:rowData.censusRegister,
        address:rowData.address,
        job:rowData.job,
        jobTitle:rowData.jobTitle,
        mobile:rowData.mobile,
        telephone:rowData.telephone,
        email:rowData.email,
        qq:rowData.qq,
        weixin:rowData.weixin,
        weibo:rowData.weibo,
        internetInfo:rowData.internetInfo,
        personNo:rowData.personNo,
		areaId:rowData.areaId
	});

	
	var personform = $('#personform').serializeObject();

    $('#sex').combobox({
        url:'../../common/combobox/listCode.do?type=XBID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#sex').combobox('setValue', personform["sex"]);
        }
    });

	$('#certificateTypeId').combobox({   
	    url:'../../common/combobox/listCode.do?type=ZJZLID',
	    valueField:'id',   
	    textField:'value' ,
	    onLoadSuccess: function(data){    
            $('#certificateTypeId').combobox('setValue', personform["certificateTypeId"]);    
        }
	});


    $('#education').combobox({
        url:'../../common/combobox/listCode.do?type=WHCDID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#education').combobox('setValue', personform["education"]);
        }
    });

    $('#certificateTypeId').combobox({
        url:'../../common/combobox/listCode.do?type=ZJZLID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#certificateTypeId').combobox('setValue', personform["certificateTypeId"]);
        }
    });


    $('#politic').combobox({
        url:'../../common/combobox/listCode.do?type=ZZMMID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#politic').combobox('setValue', personform["politic"]);
        }
    });

    $('#officer').combobox({
        url:'../../common/combobox/listCode.do?type=TSSFID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#officer').combobox('setValue', personform["officer"]);
        }
    });

    $('#country').combobox({
        url:'../../common/combobox/listCode.do?type=GJID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#country').combobox('setValue', personform["country"]);
        }
    });

    $('#nation').combobox({
        url:'../../common/combobox/listCode.do?type=MZID',
        valueField:'id',
        textField:'value' ,
        onLoadSuccess: function(data){
            $('#nation').combobox('setValue', personform["nation"]);
        }
    });

    $('#areaId').combobox({
        url: '../combobox/getAllInterrogateAreaName.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            $('#areaId').combobox('setValue', personform["areaId"]);
        }
    });
		showDialog('#person','编辑人员');
	    url = 'update.do';  
}

function personSave(){

	var elemPersoninfo = $('#personform');
	var personInfo = elemPersoninfo.serializeObject();
    personInfo["sex"]=$("#sex").combobox("getValue");
    personInfo["certificateTypeId"]=$("#certificateTypeId").combobox("getValue");
    personInfo["education"]=$("#education").combobox("getValue");
    personInfo["politic"]=$("#politic").combobox("getValue");
    personInfo["officer"]=$("#officer").combobox("getValue");
    personInfo["country"]=$("#country").combobox("getValue");
    personInfo["nation"]=$("#nation").combobox("getValue");

    var name=$("#name").val();
    if(name==""){
        $.messager.alert('提示', '请输入姓名');
        return;
    }

    if(personInfo["certificateTypeId"]==""){
        $.messager.alert('提示', '请选择证件类型');
        return;
    }else{
    	if(personInfo["certificateTypeId"]==111 && checkId(personInfo["certificateNo"])!=true){
            $.messager.alert('提示', '您输入的身份证号码不合法');
            return;
		}
	}

    if(personInfo["certificateNo"]==""){
        $.messager.alert('提示', '请输入证件号码');
        return;
    }

    if(personInfo["sex"]==""){
        $.messager.alert('提示', '请选择性别');
        return;
    }

	var jsonrtinfo = JSON.stringify(personInfo);
	//console.info("personform="+jsonrtinfo);

    $.messager.progress({
        title:'请等待',
        msg:'保存数据中...'
    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : 'json',
		url : url ,
		data : jsonrtinfo,
		success : function(data){
			$('#person').dialog('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
            $('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
			$.messager.progress('close');
		},
		error : function(data){
			$.messager.alert('错误', data.content);
			$.messager.progress('close');
		}
	});
}

function personRemove(index){
	$('#personform').form('clear');
	var row = $('#datagrid').datagrid('getRows')[index];
	//var row = $('#datagrid').datagrid('getSelected');
	$('#personform').form('load',{
		id:row.id
	});
	var elemPersoninfo = $('#personform');
	var personInfo = elemPersoninfo.serializeObject();
	var jsonrtinfo = JSON.stringify(personInfo);
	$.messager.confirm('删除确认','是否确定删除该条数据？',function(r){  
        if (r){
		$.messager.progress({
	   	 title:'请等待',
	   	 msg:'删除中...'
	    });
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : 'delete.do',
			data : jsonrtinfo,
			dataType : 'json',
			success : function(data){
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
				$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
				$.messager.progress('close');
			},
			error : function(data){
				$.messager.progress('close');
				$.messager.alert('错误', data.content);
			}
		});
        }});
}



function doSearch() {
	$('#datagrid').datagrid('load', {
		name : $('#p_name').textbox('getValue'),
		certificateNo : $('#p_certificateNo').textbox('getValue'),
		start_date : $('#p_start_date').datebox('getValue'),
		end_date : $('#p_end_date').datebox('getValue'),
        areaId : $('#s_area_id').combobox('getValue'),
		trefresh:new Date().getTime()
	});

}


function doClear() {
	$('#p_name').textbox('setValue',''),
	$('#p_certificateNo').textbox('setValue',''),
	$('#p_start_date').datebox('setValue','');
	$('#p_end_date').datebox('setValue','');
    $('#s_area_id').combobox('setValue','');
}



