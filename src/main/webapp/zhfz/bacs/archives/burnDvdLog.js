var flag = false;
var obj = null;
$(function(){
	//办案场所选择
	$('#interrogatearea').combobox({
		url : contextPath + '/zhfz/bacs/order/comboArea.do',
		valueField : 'id',
		textField : 'value',
		onLoadSuccess : function(data) {
			//$('#interrogatearea').combobox('setValue', data[0].id);
			$('#burngrid').datagrid({
						title:'光盘刻录记录',
						iconCls : 'icon-datagrid',
						region : 'center',
						width : '100%',
						height : '100%',
						fitColumns : true,
						nowrap : false,
						striped : true,
						collapsible : false,
						loadMsg : '加载中.....',
						method : 'get',
						queryParams : {
							trefresh:new Date().getTime(),
							areaId:$('#interrogatearea').combobox('getValue')
						},
						//url : 'listBurn.do',
						sortName : 'id',
						sortOrder : 'desc',
						remoteSort : false,
						idField : 'id',
						singleSelect : true,
						frozenColumns : [ [ {
							title : 'id',
							field : 'id',
							width : 80,
							sortable : true,
							hidden : true
						} ] ],
						columns : [ [
								{
									field : 'ajmc',
									title : '案件名称',
									width : 30,
									hidden : true
								},
								{
									field : 'personName',
									title : '嫌疑人',
									width : 30
								},
								{
									field : 'userName',
									title : '刻录人',
									width : 30
								},

								{
									field : 'burnTime',
									title : '刻录时间',
									width : 30,
									 formatter : function(value) {
						                    return valueToDate(value);
									}
								},
								{
									field : 'areaName',
									title : '办案场所',
									width : 40,
									hidden : true
								}] ],
						pagination : false,
                		pageSize:10,
						pageList : [ 10, 20, 30, 40, 50, 100 ],
						rownumbers : true,
						onLoadSuccess:function(){

						} 
					});
			
			//dvd领取记录
			$('#dvdLogTable').datagrid({
				title:'光盘领取记录',
				iconCls : 'icon-datagrid',
				region : 'center',
				width : '100%',
				height : '100%',
				fitColumns : true,
				nowrap : false,
				striped : true,
				collapsible : false,
				loadMsg : 'Loading...',
				method : 'post',
				//url: 'listByAll.do',
				queryParams : {
					trefresh:new Date().getTime(),
					areaId:$('#interrogatearea').combobox('getValue')
				},
				remoteSort: false,
				idField:'id',
				singleSelect:true,
				frozenColumns:[[
		            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
				]],
				columns:[[
					{field:'policeName',title:'领取人',width:30},
					{field:'ajmc',title:'案件名称',width:30,hidden : true},
					{field:'areaName',title:'办案中心名字',width:30,hidden : true},
					{field:'registerUserName',title:'记录人',width:30},
					{field:'personName',title:'嫌疑人',width:30},
					{field:'receiveTime',title:'记录时间',width:30,
						 formatter : function(value) {
			                    return valueToDate(value);
						}
					}
				]],
				pagination:false,
				pageSize:10,
				pageList: [10,20,30,50,100],
				rownumbers:true,
				toolbar:'#toolbarDvd',
				onSelect: function(rowIndex, rowData){
					
				}				
			});
			
			$('#treegrid').treegrid({
				iconCls:'icon-datagrid',
				region: 'center',  
				width:'100%',
				height:'100%',
				fitColumns:true,
				nowrap: false,
				striped: true,
				collapsible:true,
				loadMsg: 'Loading...',
				method: 'get',
				url: 'listTree.do',
				queryParams : {
					trefresh:new Date().getTime(),
					areaId:$('#interrogatearea').combobox('getValue')
				},
				idField:'id',
				treeField:'name',
				singleSelect:true,
				columns:[[
				    {field:'name',title:'名称',width:300},				    
					{field:'areaId',hidden:true}
				]],
				pagination:true,
				pageList: [10,20,30,40,50,100],
				onLoadSuccess:function(row,data){
					$('#treegrid').treegrid('collapseAll');
					if(data.rows.length==0){
						$('.datagrid .datagrid-pager').css('display','none');
					}else{
						$('.datagrid .datagrid-pager').css('display','block');
					}
				},
//				onClickRow: function(rowData){
//				   showBurnGrid(rowData.caseId);
//				   showDvdGrid(rowData.caseId);				   
//				},
				toolbar:'#toolbar',
				onSelect:function(row){
					obj = row;
					// 获取当前选中的节点
					var checkedNode = $("#treegrid").treegrid("getChecked");
					// 获取当前节点的父节点
					var childrenNode = $("#treegrid").treegrid("getChildren",checkedNode[0].id);
					//childrenNode.length为0，该节点为子节点；否则为父节点 
					if(childrenNode.length == 0){
						// row.id为嫌疑人id
						flag = true;
						showBurnGrid(row.id.split('_')[1],row.caseId);
					    showDvdGrid(row.id.split('_')[1],row.caseId);
					}																			
				}
			});
		},
	});		 
	var p = $('#treegrid').treegrid('getPager');
		$(p).pagination({
			onBeforeRefresh:function(){				
				$('#treegrid').treegrid('reload',{
					ajmc : $('#s_case_name').val(),
					trefresh : new Date().getTime()
					} );
			    return false;
			}
	});
	$('#fudong').remove();
});

function showBurnGrid(id,caseId){
	var data;
	// flag为true，点击的节点为嫌疑人；false为案件	
	if(flag){
		data = {
				caseId : caseId,
				personId : id,
				areaId:$('#interrogatearea').combobox('getValue'),
				trefresh : new Date().getTime()
			}
	}
//	else{
//		data = {
//				caseId : id,
//				areaId:$('#interrogatearea').combobox('getValue'),
//				trefresh : new Date().getTime()
//			}
//	}
	$('#burngrid').datagrid({
			iconCls : 'icon-datagrid',
			region : 'center',
			width : '100%',
			height : '100%',
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : false,
			loadMsg : '加载中.....',
			method : 'get',
			queryParams : data,
			url : 'listBurn.do',
			sortName : 'id',
			sortOrder : 'desc',
			remoteSort : false,
			idField : 'id',
			singleSelect : true,
			frozenColumns : [ [ {
				field : 'ck',
			}, {
				title : 'id',
				field : 'id',
				width : 80,
				sortable : true,
				hidden : true
			} ] ],
			columns : [ [
					{
						field : 'ajmc',
						title : '案件名称',
						width : 30,
						hidden : true
					},
					{
						field : 'personName',
						title : '嫌疑人',
						width : 30
					},
					{
						field : 'userName',
						title : '刻录人',
						width : 30
					},

					{
						field : 'burnTime',
						title : '刻录时间',
						width : 30,
						 formatter : function(value) {
			                    return valueToDate(value);
						}
					},
					{
						field : 'areaName',
						title : '办案场所',
						width : 40,
						hidden : true
					}] ],
			pagination : false,
            pageSize:10,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			rownumbers : true,
			onLoadSuccess:function(){
			
			} 
		});
}

function showDvdGrid(id,caseId){
	var data;
	// flag为true，点击的节点为嫌疑人；false为点击的节点为案件	
	if(flag){
		data = {
				caseId : caseId,
				personId : id,
				areaId:$('#interrogatearea').combobox('getValue'),
				trefresh : new Date().getTime()
			}
	}
//	else{
//		data = {
//				caseId : id,
//				areaId:$('#interrogatearea').combobox('getValue'),
//				trefresh : new Date().getTime()
//			}
//	}
	//dvd领取记录
	$('#dvdLogTable').datagrid({
		iconCls : 'icon-datagrid',
		region : 'center',
		width : '100%',
		height : '100%',
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		loadMsg : 'Loading...',
		method : 'post',
		url: 'listByAll.do',
		queryParams : data,
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            //{field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
			{field:'receiveUser',title:'领取人',width:30},
			{field:'ajmc',title:'案件名称',width:30,hidden:true},
			{field:'areaName',title:'办案中心名字',width:30,hidden:true},
			{field:'registerUserName',title:'记录人',width:30},
			{field:'personName',title:'嫌疑人',width:30},
			{field:'receiveTime',title:'记录时间',width:30,
				 formatter : function(value) {
	                    return valueToDate(value);
				}
			},
		]],
		pagination:false,
		pageSize:10,
		pageList: [10,20,30,50,100],
		rownumbers:true,
		toolbar:'#toolbarDvd',
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			rolegridData(rowData);
		}		
	});	
	flag = false;
}

//添加领取信息
function dvdLogAdd(){
	if(obj!=null&&obj!=""){
		var personId = obj.id.split('_')[1];
		var areaId = obj.areaId;
		var caseId = obj.caseId;
		if(personId!=null&&personId!=""){
				showDialog('#dvd_dialog','光盘领取');
			    $('#dvd_form').form('clear');
			    $('#caseId').val(caseId);
			    $('#personId').val(personId);
			    $('#areaId').val(areaId);
			}else{
				$.messager.alert('提示', '请选择嫌疑人!'); 
			}
	}else{
		$.messager.alert('提示', '请选择嫌疑人!'); 
	}
}

function saveDvd(){
	if(!checkForm('#dvd_form')){
		return;
	}
	var orgForm = $('#dvd_form'); 
	var info = JSON.stringify(orgForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'数据处理中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'insertDvdLog.do',
		data : info,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.alert(data.title, data.content); 
			$('#dvd_dialog').dialog('close');
			var rowData = $('#treegrid').datagrid('getSelected');  
			$('#dvdLogTable').datagrid('reload',{personId:rowData.id.split('_')[1],caseId:rowData.caseId,trefresh:new Date().getTime()});
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!'+data);
		}
	});  
}

function doSearch() {
	$('#treegrid').treegrid('load', {
		ajmc : $('#s_case_name').val(),
		trefresh:new Date().getTime(),
		areaId:$('#interrogatearea').combobox('getValue')
	});
}

function doClear() {
	$('#s_case_name').val('');
    $("#interrogatearea").combobox("setValue","");
}


function getDatetime(data){
    var dt = new Date(data);
	    return (dt.getFullYear()+'-'+(dt.getMonth()+1)+'-'+dt.getDate()+' '+dt.getHours()+':'+dt.getMinutes()+':'+dt.getSeconds()).replace(/([\-\: ])(\d{1})(?!\d)/g,'$10$2');
}
