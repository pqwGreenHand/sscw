var flag = false;
var caseId=0;
$(function(){
	
	$('#treegrid').treegrid({
/*				title:'电子档案',*/
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
			trefresh:new Date().getTime()
		},
		idField:'id',
		treeField:'name',
		singleSelect:true,
		columns:[[
		    {field:'name',title:'名称',width:300},
		    {field:'id',title:'操作',width:100,
		    	formatter:function(value,row,index){
		    		var k = '';
		    		var r = '';
		    		var w='';
		    		var z='';
		    		var z1='';
                    z = '<span class="spanRow2"><a href="#" class="gridupload" onclick="filesUpload(\''+row.id+'\')">文件上传</a></span>';
                    if(isGridButton("receiveDisk") && value.indexOf("_")<0){
		    			// z1 = '<span class="spanRow2"><a href="#" class="gridupload" onclick="runBurnExe('+value+',\''+row.loginName+'\')">资料刻录</a></span>';
		    		}
		    		return k  + w + z + z1;
				}},
			{field:'areaId',hidden:true}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
//				rownumbers:true,
		onLoadSuccess:function(row,data){
			$('#treegrid').treegrid('collapseAll');
			if(data.rows.length==0){
				$('.datagrid .datagrid-pager').css('display','none');
			}else{
				$('.datagrid .datagrid-pager').css('display','block');
			}
		},
		onClickRow: function(rowData){
		   var id = rowData.id.split('_')[0];
		   var pId = rowData.id.split('_')[1];
            showImage(id,pId)
            showVideo(id,pId)
            showQt(id,pId)
//		   showGrid1(id);
//		   showGrid2(id);
//		   showGrid3(id);
//		   showGrid4(id);
//         showGrid5(id);
		},
		toolbar:'#toolbar',
		onSelect:function(row){
//			showVideo(row.id.split('_')[0],row.id.split('_')[1]);
 			showRecord(row.id.split('_')[0],row.id.split('_')[1]);
			showRecordVideo(row.id.split('_')[0],row.id.split('_')[1]);
		}
	});
	 
	var p = $('#treegrid').treegrid('getPager');
		$(p).pagination({
			onBeforeRefresh:function(){
				
				$('#treegrid').treegrid('reload',{
					caseName : $('#s_case_name').val(),
					trefresh : new Date().getTime()
					} );
			    return false;
			}
	});
	$('#fudong').remove();
});

function setFlag(){
	flag = true;
}

function receiveDisk(row){
	var id = row.id.split('_')[0];
	var areaId = row.areaId;
	if (id){
		showDialog('#dvd_dialog','光盘领取');
	    $('#dvd_form').form('clear');
	    $('#lawCaseId').val(id);
	    $('#interrogateAreaId').val(areaId);
	} else{
		$.messager.alert('提示', '请选择一条记录!'); 
	}
	 
	flag=false;
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
			var id = rowData.id.split('_')[0];
			$('#diskgird').datagrid('reload',{lawCaseId:id,trefresh:new Date().getTime()});
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!'+data);
		}
	});  
}

//轨迹
function showVideo1(caseId,personId){
	$('#videotree').treegrid({
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:true,
		loadMsg: 'Loading...',
		method: 'get',
		idField:'id',
		treeField:'text',
		url:"listVideoTree.do",
		singleSelect:true,
		queryParams : {caseId:caseId,personId:personId},
		columns:[[
				    {field:'text',title:'标题',width:400},
				    {field:'realFilePath',title:'realFilePath',hidden:true},
				    {field:'id',title:'操作',width:100,
				    	formatter:function(value,row,index){
				    		//alert("row.childrex:"+"value"+value+" ---- "+row.children);
				    		var d = '';
				    		if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
				    			if(row.realFilePath!=null){
				    				d = '<span class="spanRow"><a href="#" class="griddownload" onclick="downfile(\'videotree\')">下载</a></span>';
				    			}
				    		}
				    		return d;
					}}
				]]
	});
	
}

//笔录
function showRecord(caseId,personId){
	$('#recordtree').treegrid({
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:true,
		loadMsg: 'Loading...',
		method: 'get',
		idField:'id',
		treeField:'name',
		url:"listRecordTree.do",
		singleSelect:true,
		queryParams : {caseId:caseId,personId:personId,trefresh:new Date().getTime()},
		columns:[[
				    {field:'text',title:'标题',width:250},
				    {field:'realFilePath',title:'realFilePath',hidden:true},
				    {field:'id',title:'操作',width:100,
				    	formatter:function(value,row,index){
				    		var r = '';
				    		var v = '';
				    		//debugger;
				    		 if(isGridButton("downloadfile")){ //recordDownload 注意还没有加权限
				    			if(row.children.length==0 && row.realFilePath!=null){
				    				r = '<span class="spanRow"><a href="#" class="griddownload" onclick="downfile(\'recordtree\')">下载</a></span>';
				    				/*if(row.text.indexOf("txt")>0){
				    					var recordId = row.id.split("_")[1];
					    				v = '<span class="spanRow"><a href="#" class="gridseeinfo" onclick="javascript:window.open(\'recordextractshow.do?recordId='+recordId+'\',\'_blank\');">预览</a></span>';
					    			}*/
				    			}
				    		 }
				    		return r + v;
						}}
				]]
	});
	
}
//询/讯问视频
function showRecordVideo(caseId,personId){
	$('#recordVideotree').treegrid({
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:true,
		loadMsg: 'Loading...',
		method: 'get',
		idField:'id',
		treeField:'text',
		url:"listRecordVideo.do",
		singleSelect:true,
		queryParams : {caseId:caseId,personId:personId,trefresh:new Date().getTime()},
		columns:[[
			{field:'text',title:'标题',width:250},
			{field:'realFilePath',title:'realFilePath',hidden:true},
			{field:'id',title:'操作',width:100,
				formatter:function(value,row,index){
					var r = '';
				//	debugger;
				 	if(isGridButton("downloadfile")){ //recordDownload 注意还没有加权限
						if(row.children.length==0 && row.realFilePath!=null){
							r = '<span class="spanRow"><a href="#" class="griddownload" onclick="downfile(\'recordVideotree\')">下载</a></span>';
						}
				 	}
					return r;
				}}
		]]
	});

}

function showImage(id,pid){
	$('#imageDatagrid').datagrid({
		iconCls:'icon-datagrid',
		region:'center',
		fitColumns:true,
		width:'100%',
		height:'100%',
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		//sortName: 'code',
		//sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams: {lawCaseId:id,personId:pid,caseDataType:"1",trefresh:new Date().getTime()},
		url: '../clue/listCaseDataClue.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:0},
			{field:'fileName',title:'文件名称',width:100},
			{field:'policeName',title:'上传民警',width:50},
			{field:'uploadTime',title:'上传时间',width:80,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},{field:'id',title:'操作',width:50,
		    	formatter:function(value,row,index){
		    		var d = '';
		    		if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
		    			d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
		    		}
		    		return d;
		    	}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//					checkRoles();
		} 
		
	});
}

function showVideo(id,pid){
    $('#videoDatagrid').datagrid({
        iconCls:'icon-datagrid',
        region:'center',
        fitColumns:true,
        width:'100%',
        height:'100%',
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '数据加载中...',
        method: 'get',
        //sortName: 'code',
        //sortOrder: 'desc',
        remoteSort: false,
        //idField:'code',
        queryParams: {lawCaseId:id,personId:pid,caseDataType:"2",trefresh:new Date().getTime()},
        url: '../clue/listCaseDataClue.do',
        singleSelect:true,
        columns:[[
            {field:'checked',checkbox:true,width:0},
            {field:'fileName',title:'文件名称',width:100},
            {field:'policeName',title:'上传民警',width:50},
            {field:'uploadTime',title:'上传时间',width:80,
                formatter:function(value,rec){
                    return valueToDate(value);
                }
            },{field:'id',title:'操作',width:50,
                formatter:function(value,row,index){
                    var d = '';
                    if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
                        d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
                    }
                    return d;
                }
            }
        ]],
        pagination:true,
        pageList: [10,20,30,40,50,100],
        rownumbers:true,
        //当数据加载成功时触发
        onLoadSuccess:function(){
            //该死的onload的未知bug,延迟0.1秒就行了
            //setTimeout(function(){checkRoles();},100);
//					checkRoles();
        }

    });
}

function showQt(id,pid){
    $('#qtDatagrid').datagrid({
        iconCls:'icon-datagrid',
        region:'center',
        fitColumns:true,
        width:'100%',
        height:'100%',
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '数据加载中...',
        method: 'get',
        //sortName: 'code',
        //sortOrder: 'desc',
        remoteSort: false,
        //idField:'code',
        queryParams: {lawCaseId:id,personId:pid,caseDataType:"3",trefresh:new Date().getTime()},
        url: '../clue/listCaseDataClue.do',
        singleSelect:true,
        columns:[[
            {field:'checked',checkbox:true,width:0},
            {field:'fileName',title:'文件名称',width:100},
            {field:'policeName',title:'上传民警',width:50},
            {field:'uploadTime',title:'上传时间',width:80,
                formatter:function(value,rec){
                    return valueToDate(value);
                }
            },{field:'id',title:'操作',width:50,
                formatter:function(value,row,index){
                    var d = '';
                    if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
                        d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
                    }
                    return d;
                }
            }
        ]],
        pagination:true,
        pageList: [10,20,30,40,50,100],
        rownumbers:true,
        //当数据加载成功时触发
        onLoadSuccess:function(){
            //该死的onload的未知bug,延迟0.1秒就行了
            //setTimeout(function(){checkRoles();},100);
//					checkRoles();
        }

    });

}

function showGrid2(id){
	$('#interrogateCaseDetailgrid2').datagrid({
		//title:'处警资料',
		iconCls:'icon-datagrid',
		region:'center',
		fitColumns:true,
		width:'100%',
		height:'100%',
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		//sortName: 'code',
		//sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams: {lawCaseId:id,caseDataType:"口供材料",trefresh:new Date().getTime()},
		url: '../clue/listCaseDataClue.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:0},
			{field:'fileName',title:'文件名称',width:100},
			{field:'policeName',title:'上传民警',width:50},
			{field:'uploadTime',title:'上传时间',width:80,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},{field:'id',title:'操作',width:50,
		    	formatter:function(value,row,index){
		    		var d = '';
		    		if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
		    			d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
		    		}
		    		return d;
		    	}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//					checkRoles();
		} 
		
	});
	
	var caseData2 = $('#interrogateCaseDetailgrid2').datagrid('getPager');
	$(caseData2).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#interrogateCaseDetailgrid2').datagrid('reload',{
				lawCaseId : id,
				caseDataType:"口供材料",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
}


function showGrid3(id){
	$('#interrogateCaseDetailgrid3').datagrid({
		//title:'处警资料',
		iconCls:'icon-datagrid',
		region:'center',
		fitColumns:true,
		width:'100%',
		height:'100%',
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		//sortName: 'code',
		//sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams: {lawCaseId:id,caseDataType:"证据材料",trefresh:new Date().getTime()},
		url: '../clue/listCaseDataClue.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:0},
			{field:'fileName',title:'文件名称',width:100},
			{field:'policeName',title:'上传民警',width:50},
			{field:'uploadTime',title:'上传时间',width:80,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},{field:'id',title:'操作',width:50,
		    	formatter:function(value,row,index){
		    		var d = '';
		    		if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
		    			d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
		    		}
		    		return d;
		    	}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//					checkRoles();
		} 
		
	});
	
	var caseData3 = $('#interrogateCaseDetailgrid3').datagrid('getPager');
	$(caseData3).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#interrogateCaseDetailgrid3').datagrid('reload',{
				lawCaseId : id,
				caseDataType:"证据材料",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
	
}


function showGrid4(id){
	$('#interrogateCaseDetailgrid4').datagrid({
		//title:'处警资料',
		iconCls:'icon-datagrid',
		region:'center',
		fitColumns:true,
		width:'100%',
		height:'100%',
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		//sortName: 'code',
		//sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams: {lawCaseId:id,caseDataType:"视频材料",trefresh:new Date().getTime()},
		url: '../clue/listCaseDataClue.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:0},
			{field:'fileName',title:'文件名称',width:100},
			{field:'policeName',title:'上传民警',width:50},
			{field:'uploadTime',title:'上传时间',width:80,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},{field:'id',title:'操作',width:50,
		    	formatter:function(value,row,index){
		    		var d = '';
		    		if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
		    			d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
		    		}
		    		return d;
		    	}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//					checkRoles();
		} 
		
	});
	
	var caseData4 = $('#interrogateCaseDetailgrid4').datagrid('getPager');
	$(caseData4).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#interrogateCaseDetailgrid4').datagrid('reload',{
				lawCaseId : id,
				caseDataType:"视频材料",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
}


function showGrid5(id){
	$('#interrogateCaseDetailgrid5').datagrid({
		//title:'处警资料',
		iconCls:'icon-datagrid',
		region:'center',
		fitColumns:true,
		width:'100%',
		height:'100%',
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '数据加载中...',
		method: 'get',
		//sortName: 'code',
		//sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams: {lawCaseId:id,caseDataType:"其它材料",trefresh:new Date().getTime()},
		url: '../clue/listCaseDataClue.do',
		singleSelect:true,
		columns:[[
		    {field:'checked',checkbox:true,width:0},
			{field:'fileName',title:'文件名称',width:100},
			{field:'policeName',title:'上传民警',width:50},
			{field:'uploadTime',title:'上传时间',width:80,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},{field:'id',title:'操作',width:50,
		    	formatter:function(value,row,index){
		    		var d = '';
		    		if(isGridButton("downloadfile")){ //videoDownload 注意还没有加权限
		    			d = '<span class="spanRow"><a  href="../clue/download.do?id='+value+'" class="griddownload">下载</a></span>';
		    		}
		    		return d;
		    	}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//					checkRoles();
		} 
		
	});
	
	var caseData5 = $('#interrogateCaseDetailgrid5').datagrid('getPager');
	$(caseData5).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#interrogateCaseDetailgrid5').datagrid('reload',{
				lawCaseId : id,
				caseDataType:"其它材料",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
}

function downfile(tableid){
	var row = $('#treegrid').datagrid('getSelected'); 
	 var rowcode = $('#'+tableid).treegrid('getSelected'); 
	 if(row==null){
		 $.messager.alert('提示', '请选择一条记录!'); 
		 return;
	 }
	 if(rowcode==null){
		 $.messager.alert('提示', '请选择一条记录!'); 
		 return;
	 }
	 var name='';
	 var realFilePath=rowcode.realFilePath;
	 //alert("realFilePath"+realFilePath);
	 //alert("encodeURIComponent(realFilePath):"+encodeURIComponent(realFilePath));
	 if(tableid=='videotree'){
		 name=rowcode.text;
	 }
	 if(tableid=='recordtree' || tableid=='recordVideotree'){
		 name=rowcode.text;
		 tableid='recordtree';
	 } 
	 var coreid=row.id.split("_");
	// alert("downloadfile.do?name="+name+"&type="+tableid+"&interrogate_area_id="+coreid[0]);
	 window.location="downloadfile.do?realFilePath="+encodeURIComponent(realFilePath)+"&name="+encodeURIComponent(name)+"&type="+tableid+"&interrogate_case_id="+coreid[0];
}

function openrecord(caseId,personId){
	 window.showModalDialog("record.jsp?caseId="+caseId+'&personId='+personId, 'a', "dialogWidth =515px;dialogHeight = 405px;help=0 ");
}

function doSearch() {
	$('#treegrid').treegrid('load', {
		caseName : $('#s_case_name').val(),
		personName : $('#s_person_name').val(),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_case_name').val('');
}


function getDatetime(data){
    var dt = new Date(data);
	    return (dt.getFullYear()+'-'+(dt.getMonth()+1)+'-'+dt.getDate()+' '+dt.getHours()+':'+dt.getMinutes()+':'+dt.getSeconds()).replace(/([\-\: ])(\d{1})(?!\d)/g,'$10$2');
}

function barcodeprint(caseId){
	//var row = $('#infocollection_datagrid').datagrid('getRows')[index];
	//办案中心名称
	var an = '办案中心名称';//row.areaName;
	//案由
	var cn ='案由'; //row.caseName;
	//条码值
	var co ='条码值';//row.serialNo;
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'getCaseByID.do?caseId='+caseId,
		dataType : 'json',
		success : function(data){
			if(data){
				an=data.interrogateAreaName;
				cn=data.caseName;
				co=data.caseNo;
				//alert("办案中心名称= "+an+"案由= "+cn+"条码值= "+co);
				jQuery.ajax({
					type : 'POST',
					contentType : 'application/json',
					url : '../commonconfig/getbarcodeprinterconfig.do',
					dataType : 'json',
					success : function(data){
						if(data.callbackData!=null){
							//打印机名称
							var printerName ="ZDesigner GT800 (EPL)";
							//端口
							var printerPort ="USB001)";
							printerName =data.callbackData.barCodePrinterName;
							printerPort =data.callbackData.barCodePrinterPort;
							//alert("printerName= "+printerName+"printerPort= "+printerPort);
							print_epl.Open_Port(printerPort);
							print_epl.Begin_Job("2", "12", "False", "B");
							print_epl.Print_Winfont(200, 15, "办案中心:"+an, "宋体", 20, 14, "True", "False", "False", "False", "False");
							//print_epl.Print_Winfont(200, 45, "案由:"+cn, "宋体", 20, 12, "True", "False", "False", "False", "False");
							print_epl.Print_BarCode(200, 80, co, "1", "60", "2", "6", "B", "0");
							var caseInfo=cn;
							if(caseInfo.length>50){
								caseInfo=caseInfo.substr(0,50)
							}
							if(caseInfo.length>0 && caseInfo.length<=25){
								print_epl.Print_Winfont(200, 170, caseInfo, "宋体", 20, 8, "True", "False", "False", "False", "False");
							}else if(caseInfo.length>25 && caseInfo.length<=50){
								caseInfo1 = caseInfo.substr(0,25);
								caseInfo2 = caseInfo.substr(25);
								print_epl.Print_Winfont(200, 170, caseInfo1, "宋体", 20, 8, "True", "False", "False", "False", "False"); 
								print_epl.Print_Winfont(200, 200, caseInfo2, "宋体", 20, 8, "True", "False", "False", "False", "False"); 
							}
							
							print_epl.End_Job();
							print_epl.Close_Port();
							print_epl.Printing_USBPORT(printerName);
						}
					},
					error : function(data){
						$.messager.alert(data.title, data.content);
					}
				}); 
			}
		},
		error : function(data){
			$.messager.alert(data.title, data.content);
			return;
		}
	});
	
	
}

//资料上传
function filesUpload(value){
    loadPerson(value.split("_")[1],value.split("_")[0]);
    caseId=value.split("_")[0];
    showDialog('#uploadonlineinfo', '文件上传');
	// window.showModalDialog('upload.jsp?lawCaseId='+caseid, window, "dialogWidth:800px;dialogHeight:450px;resizable:no;");
}

function loadPerson(personId,caseid) {
    $('#person').combobox({
        url:'../combobox/listPerson.do?caseId=' + caseid,
        valueField:'id',
        textField:'value',
        onLoadSuccess: function (data) {
        	if(personId!=null){
                $("#person").combobox("setValue",personId)
			}
        },
        onChange:function (newValue,oldValue) {
        }
    });
}

function uploadonline() {
    // var cg = $('#interrogateSerialId').combogrid('grid'); // 获取数据表格对象
    // var row = cg.datagrid('getSelected');
    // if (row == null) {
    //     $.messager.alert('提示', '请先选择专属编号!');
    // } else {
    var fileType=$("#fileType").combobox("getValue");
    var personId= $("#person").combobox("getValue");
        var add=contextPath+"/zhfz/bacs/archives/onlineupload.do?personId="+personId+"&fileType="+fileType+"&caseId="+caseId;
        $('#fam').form('submit',{
            url: add,
            onSubmit: function(){

            },
            success: function(result){
                if (result=="success"){
                    $('#uploadonlineinfo').dialog('close');
                    $.messager.show({
                        title: 'Success',
                        msg: '上传成功'
                    });
                } else {
                    $.messager.show({
                        title: 'Error',
                        msg: '文件不能为空，请重新选择文件！'
                    });
                }
            }
        });
    // }
}


function runBurnExe(a,b){
	var path ='D:\\光盘刻录打印系统\\Burn.exe';
	//alert(a+"-----"+b);
	if(a!=null && a!="" && b!=null && b!=""){
		path=path+" "+b+" "+a;
		//alert(path); 
	}
	try{
		  var wsh=new ActiveXObject("WScript.Shell");
		  wsh.Run(path);
		 }catch(e){
		  $.messager.alert("提示", "未正确安装客户端程序！"+e);
		 }
}