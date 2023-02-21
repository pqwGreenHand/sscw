$(function(){
	$('#areaid').datagrid({
		title:'办案场所',
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
		url: 'listAllArea.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
          /*  {field:'ck',checkbox:true},*/
            {title:'id',field:'id',width:80,sortable:true,hidden:true},
		]],
		columns:[[
			{field:'name',title:'名称',width:80},
			{field:'type',title:'类型',width:30,
				formatter:function(value,rec){
					if('true'==value ||"1"==value){
						return '办案区'
					}
					else if("0"==value){
						return '办案中心'
					}
				}},
		/*	{field:'description',title:'描述',width:80},*/
			{field:'organizationName',title:'所在公安机关',width:90},
			/*{field:'address',title:'地址',width:80},
			{field:'telephone',title:'单位电话',width:80},
			{field:'postcode',title:'单位邮编',width:80},*/
			{field:'id',title:'操作',width:50,
				formatter:function(field, rec, index){
					var e='';
					var d='';
					if(isGridButton("areaRemove")){
						e='<span class="spanRow"><a href="#" class="griddel" onclick="areaRemove('+field+')">删除</a></span>'
					}
					if(isGridButton("areaEdit")){
						d='<span class="spanRow"><a href="#" class="gridedit" onclick="areaEdit('+index+')">修改</a></span>'
					}
					return e +d;
				}
			}
		]],
		pagination:true,
		pageSize: 15,
		pageList: [15,20,30,40,50,100],
		rownumbers:true,
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			organizationgridLoad(rowData);
		},
		toolbar:'#areaToolbar'

		
	});
	var p = $('#areaid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			var ss=$('#s_type').combobox('getValue');
			$('#areaid').datagrid('reload',{
				name_t : $('#s_name').val(),
				type_t : ss,
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
	
	$('#organizationId').combobox({   
	    url:'../combobox/listAllOrganizationNameCombobox.do',
	    valueField:'id',   
	    textField:'value' 
	});
	$('#roomid').datagrid({
		title:'办案场所内的功能室',
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
		sortName: 'id',
		sortOrder: 'asc',
		remoteSort: false,
		//idField:'code',
		queryParams:{'enpId':'-99','trefresh':new Date().getTime()},
		url: 'listRoom.do',
		singleSelect:true,
		frozenColumns:[[
		                /*{field:'ck',checkbox:true},*/
		                {title:'id',field:'id',width:50,sortable:true,hidden:true},
		                {title:'sid',field:'sid',width:80,sortable:true,hidden:true}
		    		]],
		columns:[[
			{field:'name',title:'名称',width:70},
			{field:'ips',title:'ip地址',width:100},
			{field:'type',title:'类型',width:40,},
			{field:'isActive',title:'是否启用',width:40,
				formatter:function(value,row,index){
					if(row.isActive!=1){
						return '<span style="color: red">否</span>';
					}else{
						return '<span style="color: yellow" >是</span>';
					}
				}
			},
			{field:'operation',title:'操作',width:90,
				formatter:function(field, rec, index){
					var e='';
					var d=''; 
					if(isGridButton("roomRemove")){
						e='<span class="spanRow"><a href="#" class="griddel" onclick="roomRemove('+rec.id+')">删除</a></span>'
					}
					if(isGridButton("roomEdit")){
						d='<span class="spanRow"><a href="#" class="gridedit" onclick="roomEdit('+index+')">修改</a></span>'
					}
					var f='<span class="spanRow"><a href="#" class="griddetail" onclick="lookDetail('+index+')">详情</a></span>';
					
					var g='<span class="spanRow"><a href="#" class="griddetail" onclick="lookLed('+rec.id+')">led</a></span>';
					
					
					
					return e + d+f+g;
				}
			}
		]],
		pagination:true,
		pageSize: 15,
		pageList: [15,30,40,50,100],
		rownumbers:true,
		
		toolbar:'#roomToolbar'
	});

	var p1 = $('#roomid').datagrid('getPager');
	$(p1).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			var row = $('#areaid').datagrid('getSelected');  
			if (row){
				$('#roomid').datagrid('load', {
					enpId:row.id,
					name_t : $('#r_name').val(),
					trefresh : new Date().getTime()
				});
			}
			return false;
		}
	});
	

	//TODO hideButton('btnadd');
	//hideButton('btnedit');
	//hideButton('btnremove');
	//doCall();
	
	/*hideButton('enterprisebtnadd');
	hideButton('enterprisebtnedit');
	hideButton('organizationbtnadd');
	hideButton('organizationbtnedit');
	doCall();*/
	
	$('#fudong').remove();

});



function organizationgridLoad(rowData)
{
	$('#roomid').datagrid('load',{enpId:rowData.id,trefresh:new Date().getTime()});
}

function creatParam(name,value)
{
	var param={
			name: name,
			value: value
		};
	return param;
}

function saveArea(){
	if(!checkForm('#Area_form')){
		return;
	}
	var p3=$("#organizationId").combobox('getValue');
	if(p3==''){
		$.messager.alert("提醒", "请选择所在公安！"); 
		return;
	}
	var entForm = $('#Area_form');
	var enterpriseinfo = JSON.stringify(entForm.serializeObject());
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
//			$('#areaid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
			var ss=$('#s_type').combobox('getValue');
			$('#areaid').datagrid('reload',{
				name_t : $('#s_name').val(),
				type_t : ss,
				trefresh : new Date().getTime()
			})
			$('#organization_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('Error', '数据错误!'+data.content);
		}
	});  
}



function saveRoom(){
	if(!checkForm('#Room_form')){
		return;
	}
	var orgForm = $('#Room_form'); 
	var organizationinfo = JSON.stringify(orgForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : organizationinfo,
		dataType : 'json',
		success : function(data){
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
			var row = $('#areaid').datagrid('getSelected');  
			if (row){
				$('#roomid').datagrid('load', {
					enpId:row.id,
					name_t : $('#r_name').val(),
					trefresh : new Date().getTime()
				});
			}
			$('#enterprise_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('Error', '数据错误!'+data);
		}
	});  
}

function areaAdd(){
	
	showDialog('#organization_dialog','新增办案场所'); 
	$('#Area_form').form('clear');  
	url = 'addArea.do';
	
	
	
}

function areaEdit(index){
	 var rowData = $('#areaid').datagrid('getRows')[index];
		showDialog('#organization_dialog','编辑办案场所');
	    $('#Area_form').form('clear');
	    $('#Area_form').form('load',rowData);
	  //所在公安机关
	   $('#organizationId').combobox('setValue',rowData.organizationId);
	    url = 'editArea.do?id='+rowData.id; 
}

function areaRemove(value){
        $.messager.confirm('删除确认','是否确定删除此数据？',function(r){
        	 if (r){
     			$.messager.progress({
     			 title:'请等待',
     			 msg:'删除数据中...'
     			});
            	jQuery.ajax({
        			type : 'POST',
        			contentType : 'application/json',
        			url : 'removeArea.do',
        			data : '{"id":'+value+'}',
        			dataType : 'json',
        			success : function(data){
                        $.messager.progress('close');
                        $.messager.show({
                            title:'提示',
                            msg:data.content,
                            timeout:3000
                        });
//        				$('#areaid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
        				var ss=$('#s_type').combobox('getValue');
        				$('#areaid').datagrid('reload',{
        					name_t : $('#s_name').val(),
        					type_t : ss,
        					trefresh : new Date().getTime()
        				});
        				 $("#roomid").datagrid("reload");
        				$.messager.progress('close');
        			},
        			error : function(data){
        				//exception in java
        				$.messager.alert('Error', '数据错误!');
        			}
        		});  
            	
            }  
        });  
   
}



function areaSearch() {
	var ss=$('#s_type').combobox('getValue');
	$('#areaid').datagrid('load', {
		name_t : $('#s_name').val(),
		type_t : ss,
		trefresh:new Date().getTime()

	});
	
}

function areadoClear() {
	$('#s_name').prop('value', '');
	$('#r_name').prop('value', '');
	$('#s_type').combobox('setValues', '');
}




function roomAdd(){
	var erow = $('#areaid').datagrid('getSelected'); 
	if (erow){
		showDialog('#enterprise_dialog','新增功能室'); 
		$('#Room_form').form('clear');  
		url = 'addRoom.do';
		$("#interrogateAreaId").val(erow.id);
	}else{
		$.messager.alert('Message', '请先选择一个办案场所!'); 
	}
	//从字典表中选择room类型
	$('#roomTypeId').combobox({ 
	    editable:false,  
	    url:'../combobox/roomtype.do',
	    valueField:'id',   
	    textField:'value'  
	});
	//room所属办案区域
	$('#roomGroupId').combobox({
		 editable:false,  
		url:'../combobox/listAllRoomGroupNameCombobox.do?id='+erow.id,
		valueField:'id',   
		textField:'value' 
	});
}

function roomEdit(index){
	 var rowData = $('#roomid').datagrid('getRows')[index];
	 var erow = $('#areaid').datagrid('getSelected'); 
	showDialog('#enterprise_dialog','编辑办案场所');
    $('#Room_form').form('clear');
    $('#Room_form').form('load',rowData);
    url = 'editRoom.do?id='+rowData.id;  
    $('#roomTypeId').combobox({   
	    url:'roomtype.do',   
	    valueField:'id',   
	    textField:'value'  
	});
  //room所属办案区域
	$('#roomGroupId').combobox({
		 editable:false,  
		url:'../combobox/listAllRoomGroupNameCombobox.do?id='+erow.id,
		valueField:'id',   
		textField:'value' 
	});
   $('#roomTypeId').combobox('setValue',rowData.sid);
   $('#roomGroupId').combobox('setValue',rowData.roomGroupId);
}

function roomRemove(value){
        $.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
        	 if (r){
     			$.messager.progress({
     			 title:'请等待',
     			 msg:'删除数据中...'
     			});
            	jQuery.ajax({
        			type : 'POST',
        			contentType : 'application/json',
        			url : 'removeRoom.do',
        			data : '{"id":'+value+'}',
        			dataType : 'json',
        			success : function(data){
                        $.messager.progress('close');
                        $.messager.show({
                            title:'提示',
                            msg:data.content,
                            timeout:3000
                        });
        				var row = $('#areaid').datagrid('getSelected');  
        				if (row){
        					$('#roomid').datagrid('load', {
        						enpId:row.id,
        						name_t : $('#r_name').val(),
        						trefresh : new Date().getTime()
        					});
        				}
        				$.messager.progress('close');
        			},
        			error : function(data){
        				//exception in java
        				$.messager.alert('Error', '数据错误!');
        			}
        		});  
            	
            }  
        });  
   
}

function roomBatchAdd(){
	var erow = $('#areaid').datagrid('getSelected'); 
	if (erow){
		showDialog('#batchAddRoom_dialog','新增功能室'); 
		$('#batchAddRoom_form').form('clear');  
		url = 'batchAddRoom.do';

		batchInit();
	}else{
		$.messager.alert('Message', '请先选择一个办案场所!'); 
		return;
	}
//	//从字典表中选择room类型
//	$('#roomTypeId2').combobox({ 
//	    editable:false,  
//	    url:'../combobox/roomtype.do',   
//	    valueField:'id',   
//	    textField:'value'  
//	});
//	//room所属办案区域

	$('#roomGroupId2').combobox({
		editable:false,
		url:contextPath+'../combobox/listAllRoomGroupNameCombobox.do?id='+erow.id,
		valueField:'id',
		textField:'value'
	});
}



/**
 * 判断是否是IE
 * @returns boolean
 */
function isIE() {
    if (!!window.ActiveXobject || "ActiveXObject" in window) {
        return true;
    } else {
        return false;
    }
}
/**
 * 判断是否是IE11
 * @returns boolean
 */
function isIE11(){
    if((/Trident\/7\./).test(navigator.userAgent)) {
        return true;
    } else {
        return false;
    }
}


function batchInit(){
	//$("#batchAddRoom_box div:.group_item").remove();
	var items=$("#batchAddRoom_box").children("div");
	for(var i=0;i<items.length;i++)
	{
		if(items[i].id!='group_item'){
            if (isIE() || isIE11()) {
                items[i].removeNode(true);//IE仅支持removeNode()方法，其他浏览器可用remove()
			}else {
                items[i].remove();
			}

//			items[i].empty();
		}
	}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : contextPath+'/zhfz/bacs/combobox/roomtype.do',
		dataType : 'json',
		success : function(data){
			for(var i=0;i<data.length;i++)
			{
				var item="<div  class='fitem' style='mamargin-left: auto; margin-right: auto;'><span style='display:inline-block;width:90px;'>"+data[i].value+"：</span> "+
						"<input id='"+data[i].id+"' name='"+data[i].id+"' class='easyui-validatebox'  required='true' value='0' >" +
						"</div>";
				$("#batchAddRoom_box").append(item);
			}
			
			
		},
		error : function(data){
			$.messager.alert('Error', '数据错误!');
		}
	});  
}



function saveBatchRoom(){
	var erow = $('#areaid').datagrid('getSelected'); 
	if (erow){
	
	}else{
		$.messager.alert('Message', '请先选择一个办案场所!'); 
		return;
	}
	
	
	
	var map=$("#batchAddRoom_form").serializeObject();
	map["areaId"]=erow.id;
	console.info(map);

	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'saveBatchRoom.do',
		data : JSON.stringify(map),
		dataType : 'json',
		success : function(data){
            $("#roomid").datagrid("reload");
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });

            $('#batchAddRoom_dialog').dialog('close');
		},
		error : function(data){
			//exception in java
			$.messager.alert('Error', '返回错误!');
		}
	});  
}

function roomSearch() {
	var row = $('#areaid').datagrid('getSelected');  
	if (row){
		$('#roomid').datagrid('load', {
			enpId:row.id,
			name_t : $('#r_name').val(),
			trefresh : new Date().getTime()
		});
	}else{
		$.messager.alert('Message', '请选择办案场所！'); 
	} 
	
}
function lookDetail(index){
	  $('#roomTypeId1').combobox('setValue','');
	   $('#roomGroupId1').combobox('setValue','');
	   $('#isActive1').combobox('setValue','');
	   $('#name1').val('');
	   $('#description1').val('');
	   $('#ips1').val('');
	   $('#volume1').val('');
	   $('#ledAddress1').val('');
    $('#triggerNo1').val('');
    $('#axis1').val('');
	 var rowData = $('#roomid').datagrid('getRows')[index];
	 var erow = $('#areaid').datagrid('getSelected'); 
	showDialog('#detail_dialog1','功能室详情');
    $('#roomTypeId1').combobox({   
	    url:'roomtype.do',   
	    valueField:'id',   
	    textField:'value'  
	});
  //room所属办案区域
	$('#roomGroupId1').combobox({   
		 editable:false,  
		url:'../combobox/listAllRoomGroupNameCombobox.do?id='+erow.id,
		valueField:'id',   
		textField:'value' 
	});
   $('#roomTypeId1').combobox('setValue',rowData.sid);

   $('#roomGroupId1').combobox('setValue',rowData.roomGroupId);
   $('#isActive1').combobox('setValue',rowData.isActive);
   $('#name1').val(rowData.name);
   $('#description1').val(rowData.description);
   $('#ips1').val(rowData.ips);
   $('#volume1').val(rowData.volume);
   $('#ledAddress1').val(rowData.ledAddress);
    $('#triggerNo1').val(rowData.triggerNo);
	$('#axis1').textbox("setValue", rowData.axis);
}

//查看led
function lookLed(roomId){  
	showDialog('#led_dialog','led设置'); 
	 var erow = $('#areaid').datagrid('getSelected');  
	 var areaId = erow.id
	$.messager.progress({
		 title:'请等待',
		 msg:'查询数据中...'
		});
   	jQuery.ajax({
   	  contentType : 'application/json',
      url : "../led/LedByRoomId.do?roomId="+roomId,
	  success : function(data){
		  if(data){
		   data   = eval("("+data+")");    
	       $("#ledId").val(data.id);
	       $("#ip").val(data.ip);
	       $("#port").val(data.port);
		  }else{
			  $("#ledId").val('');
		  } 
		  $("#ledAreaId").val(areaId);
		  $("#ledRoomId").val(roomId);
       $.messager.progress('close');
	},
	error : function(data){ 
		$.messager.alert('Error', '数据错误!');
		$.messager.progress('close');
	}
});  
   	
}

function ledSaveAll(){
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : '../led/saveLedAll.do',
        dataType : 'json',
        success : function(data){
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
        },
        error : function(data){
            //exception in java
            $.messager.alert('Error', '返回错误!');
        }
    });
}

//保存led
function saveLed(){
	var ip = $('#ip').val();
	var port = $('#port').val();
	var lednr = $('#lednr').val(); 
	var map=$("#ledForm").serializeObject();
//	map["ip"]=ip;
//	map["port"]=port;
	console.info(map);

	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../led/saveLed.do',
		data : JSON.stringify(map),
		dataType : 'json',
		success : function(data){
			$('#led_dialog').dialog('close');
			 $.messager.show({
	                title:'提示',
	                msg:data.content,
	                timeout:3000
	            });
		},
		error : function(data){
			//exception in java
			$.messager.alert('Error', '返回错误!');
		}
	});  
}
