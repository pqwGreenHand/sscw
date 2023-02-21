var ssareaid;
$(function(){
	//储物柜编号初始化
	ssareaid = $('#ssareaid').val();
	if(ssareaid == null||ssareaid =="null"){
		hiddenBack();
	}
$('#lockerIdSeaerch').combobox({
	editable:false,  
	url:'listunUsedbox.do?areaid='+ssareaid,
	valueField:'id',
	textField:'value',
	formatter:function(row){
    	return row.value+'['+(row.status==0?'满':'空')+']';
    }
  });
	
	$('#belongid').datagrid({
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
		url: 'listAllBelong.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'id',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
			// {field:'serialNo',title:'专属编号',width:80},
			{field:'personname',title:'嫌疑人',width:40,
				formatter : function(value, row, index){
					return "<font>"+value+"</font>";
				}
			},
			{field:'lockerId',title:'储物柜编号',width:80,
				formatter:function(field, rec, index){
					return rec.cabinetGroup+" "+rec.cabinetNo+"号柜";
				}},
			{field:'isGet',title:'是否已领取',width:30,
				formatter:function(value,rec){
					if('true'==value ||"1"==value){
						return '已领取';
					}
					else if("0"==value){
						return '未领取';
					}
				}},
			{field:'areaName',title:'办案场所',width:60},
	        {field:'id',title:'操作',width:100,
					formatter:function(field, rec, index){
					   var d = '';
					   var o = '';
					   var i = '';
					   var a = '';
					   var cwsp = '';
					   var qwsp = '';
					   var crUrl = '';
					   var lqUrl = '';
					   if(isGridButton("sbelongRemove")){
						   d = '<span class="spanRow"><a href="#" class="griddel" onclick="belongRemove('+field+')">删除</a></span>';
					   }
					   if(isGridButton("sbelongopen")){
						   o = '<span class="spanRow"><a href="#" class="gridopenbox" onclick="boxopen('+index+')">领取</a></span>';
					   }
					   i = '<span class="spanRow"><a href="#" class="gridpicture" onclick="showImages('+field+')">照片</a></span>';
						if(rec.cwurl !=null || rec.qwurl!=null){
							if (rec.cwurl != null) {
								cwsp = '<span class="spanRow2"><a href="#" class="gridpreview" onclick="cwVideoPlayback(' + index + ')">存物视频</a></span>';
							}
							if ('true' == rec.isGet || "1" == rec.isGet) {
								if (rec.cwurl != null) {
									qwsp = '<span class="spanRow2"><a href="#" class="gridpreview" onclick="qwVideoPlayback(' + index + ')">取物视频</a></span>';
								}
							}
						}else{
							a = '<span class="spanRow2" style="width: 100px"><a href="#" class="gridanswer" onclick="videoList('+rec.id+', \''+rec.serialNo+'\', 2)">视频列表</a></span>';
						}
					   return d + o + i + a + cwsp + qwsp + crUrl + lqUrl;
					}
				}
				
			]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
			organizationgridLoad(rowData);
		},
		toolbar:'#areaToolbar'
	});
	var p = $('#belongid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			$('#belongid').datagrid('reload',
					{
				       lockerId_t : $('#lockerIdSeaerch').combobox('getValue'),
				       name : $('#s_person').val(),
					   start_date : $('#d_start_date').datebox('getValue'),
					   end_date : $('#d_end_date').datebox('getValue'),
				       trefresh:new Date().getTime()
				    }
			);
			return false;
		}
	});
	//办案中心下拉框
	$('#area').combobox({
		url : '../combobox/comboArea.do',
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if(ssareaid==null||ssareaid=="null"){
				$('#area').combobox('setValue', data[0].id);
				ssareaid = data[0].id;
			}else{
				for(var i = 0;i<data.length;i++){
					if(ssareaid==data[i].id){
						$('#area').combobox('setValue', data[i].id);
					}
				}
			}
		},
		onSelect : function(rowIndex, rowData) {
			ssareaid = rowIndex.id;
			$('#lockerIdSeaerch').combobox({
				editable:false,
				url:'listunUsedbox.do?areaid='+ssareaid,
				valueField:'id',
				textField:'value',
				formatter:function(row){
					return row.value+'['+(row.status==0?'满':'空')+']';
				}
			});
		}
	});

	$('#detid').datagrid({
		title:'状态去向',
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
		sortOrder: 'desc',
		remoteSort: false,
		//idField:'code',
		queryParams:{'enpId':'-99','trefresh':new Date().getTime()},
		url: 'listAllBelongdet.do',
		singleSelect:true,
		frozenColumns:[[
		                {title:'id',field:'id',width:80,sortable:true,hidden:true},
		                {title:'belongingsId',field:'belongingsId',width:80,sortable:true,hidden:true}
		    		]],
		columns:[[
			{field:'name',title:'名称',width:70},
			{field:'detailCount',title:'数量',width:30,},
			{field:'unit',title:'单位',width:40},
			{field:'isGet',title:'是否已领取',width:50,
				formatter:function(value,rec){
				if('true'==value ||"1"==value){
					return '已领取';
				}
				else if("0"==value){
					return '未领取';
				}
			}},
			{field:'getWay',title:'领取方式',width:60,
				formatter:function(value,rec){
				if('true'==value ||"1"==value){
					return '本人领取';
				}
				else if("0"==value){
					return '未领取';
				}
				else if("2"==value){
					return '委托他人代为领取';
				}
				else if("3"==value){
					return '本人收到扣押物品清单';
				}
                else if("4"==value){
                    return '转涉案财物';
                }
                else if("5"==value){
                    return '移交';
                }
			}},
			{field:'getPerson',title:'领取人',width:50},
			{field:'getTime',title:'领取时间',width:80,
				formatter:function(value,rec){
				return valueToDate(value);
			}
			},
			{field:'saveMethod',title:'保管措施',width:50},
			{field:'description',title:'特征',width:100},
	        {field:'id',title:'操作',width:100,
					formatter:function(field, rec, index){
					    var d = '';
					    var u = '';
						if(isGridButton("sdetRemove")){
							d = '<span class="spanRow"><a href="#" class="griddel" onclick="detRemove('+index+')">删除</a></span>';
						}
                        if(isGridButton("sdetEdit")){
							u = '<span class="spanRow"><a href="#" class="gridedit" onclick="detEdit('+index+')">修改</a></span>';
						}
                        return d + u;
					}
				}
				
			]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		
		toolbar:''
	});

	var p1 = $('#detid').datagrid('getPager');
	$(p1).pagination({
		onBeforeRefresh:function(){
			var row = $('#belongid').datagrid('getSelected');
			$('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
			return false;
		}
	});
	
	$('#codid').datagrid({
		title:'随身物品开柜记录',
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
		/*sortName: 'id',
		sortOrder: 'desc',*/
		remoteSort: false,
		//idField:'code',
		queryParams:{'enpId':'-99',trefresh:new Date().getTime()},
		url: 'openBoxLog.do',
		singleSelect:true,
		frozenColumns:[[
		                /*{field:'ck',checkbox:true},*/
		                {title:'id',field:'id',width:80,sortable:true,hidden:true},
		    		]],
		columns:[[
			{field:'showNo',title:'柜号',width:60},
			{field:'opener',title:'开柜人',width:150},
			{field:'createdTime',title:'开柜时间',width:150,
	      	  formatter:function(value,rec){
					return valueToDate(value);
				}
	        },
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		
		toolbar:''
	});
	
	var p2 = $('#codid').datagrid('getPager');
	$(p2).pagination({
		onBeforeRefresh:function(){
			var row = $('#belongid').datagrid('getSelected');
			$('#codid').datagrid('load',{lockerId:"随身柜-"+rowData.cabinetNo,trefresh:new Date().getTime()});
			return false;
		}
	});
	
	$('#fudong').remove();
});


function videoList(id){
	$("#uploadonlineinfoBelongId").val(id);
	$('#videoList').datagrid({
		title:'随身物品视频列表',
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
		/*sortName: 'id',
        sortOrder: 'desc',*/
		remoteSort: false,
		//idField:'code',
		queryParams:{'belongId':id,trefresh:new Date().getTime()},
		url: 'listBelongVideo.do',
		singleSelect:true,
		frozenColumns:[[
			/*{field:'ck',checkbox:true},*/
			{title:'id',field:'id',width:80,sortable:true,hidden:true},
		]],
		columns:[[
			{field:'createdUserName',title:'上传人员',width:60},
			{field:'createdTime',title:'上传时间',width:150,
				formatter:function(value,rec){
					return valueToDate(value);
				}
			},
			{field:'type',title:'视频类型',width:150,
				formatter:function(value,rec){
					if(value==1){
						return  "存入视频"
					}
					if(value==2){
						return  "领取视频"
					}
				}
			},{field:'id',title:'操作',width:130,
				formatter:function(field, rec, index){
					var a = '';
					var b = '';
					a = '<span class="spanRow2" style="width: 100px"><a href="#" class="gridpreview" onclick="playback('+index+')">播放</a></span>';
					b = '<span class="spanRow"><a href="#" class="griddel" onclick="belongVideoRemove('+field+')">删除</a></span>';
					return a + b;
				}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#video_list_dialog_toolbar'
	});

	showDialog('#video_list_dialog','视频列表');
}

function playback(index){
	var rowData = $('#videoList').datagrid('getRows')[index];
	// window.open(rowData.crUrl, '_blank');
	var path=rowData.url;
	var spbf ='  <video id="myVideo" width="100%" height="100%" autoplay="autoplay" preload="auto" controls><source src="'+path+'"  type="video/mp4"></video>';
	$('#spbf').html(spbf);
	showDialog('#video_dialog', '视频播放');
}

function upload(type){
	$("#uploadonlineinfoType").val(type);
	showDialog('#uploadonlineinfo','文件上传');
}

function uploadonline() {
	var url="onlineupload.do";
	$('#fam').form('submit',{
		url: url,
		onSubmit: function(){

		},
		success: function(result){
			if (result=="success"){
				$('#uploadonlineinfo').dialog('close');
				$.messager.show({
					title: 'Success',
					msg: '上传成功'
				});
				$('#videoList').datagrid('reload');
			} else {
				$.messager.show({
					title: 'Error',
					msg: '上传异常！'
				});
			}
		}
	});

}

function belongVideoRemove(value){
	$.messager.confirm('删除确认','是否确定删除此数据?',function(r){
		if (r){
			jQuery.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : 'removeBelongVideo.do',
				data : '{"id":'+value+'}',
				dataType : 'json',
				success : function(data){
					$.messager.alert(data.title, data.content);
					$('#videoList').datagrid('reload');
				},
				error : function(data){
					//exception in java
					$.messager.alert('Error', '未知错误!');
				}
			});

		}
	});
}

function cwVideoPlayback(index) {
	var rowData = $('#belongid').datagrid('getRows')[index];
	window.open(rowData.cwurl, '_blank');
	// $("#iframe").attr("src",rowData.cwurl)
	// showDialog('#videoPlay_dialog', '存物视频播放');
}

function qwVideoPlayback(index) {
	var rowData = $('#belongid').datagrid('getRows')[index];
	window.open(rowData.qwurl, '_blank');
	// $("#iframe").attr("src",rowData.cwurl)
	// showDialog('#videoPlay_dialog', '存物视频播放');
}

function crVideoPlayback(index){
	var rowData = $('#belongid').datagrid('getRows')[index];
	// window.open(rowData.crUrl, '_blank');
	var path=rowData.crUrl;
	var spbf ='  <video id="myVideo" width="100%" height="100%" autoplay="autoplay" preload="auto" controls><source src="'+path+'"  type="video/mp4"></video>';
	$('#spbf').html(spbf);
	showDialog('#video_dialog', '视频播放');
}

function lqVideoPlayback(index){
	var rowData = $('#belongid').datagrid('getRows')[index];
	// window.open(rowData.lqUrl, '_blank');
	var path=rowData.lqUrl;
	var spbf ='  <video id="myVideo" width="100%" height="100%" autoplay="autoplay" preload="auto" controls><source src="'+path+'"  type="video/mp4"></video>';
	$('#spbf').html(spbf);
	showDialog('#video_dialog', '视频播放');
}

function closeVideo() {
	var myVideo = document.getElementById("myVideo");
	myVideo.pause();
	$('#video').remove();
	$('#video_dialog').dialog('close');
}

//选中触发刷新
function organizationgridLoad(rowData)
{
	$('#detid').datagrid('load',{enpId:rowData.id,trefresh:new Date().getTime()});
    $('#codid').datagrid('load',{lockerId:rowData.lockerId,trefresh:new Date().getTime()})
}

function creatParam(name,value)
{
	var param={
			name: name,
			value: value
		};
	return param;
}
//查看图片
function showImages(belongingsId){
	$("#showPic_dialog").html("");
    //alert("belongingsId="+belongingsId);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'加载数据中...'
	});
	jQuery.ajax({
		type : 'POST',
		url : 'getImages.do',
		data : {belongingsId:belongingsId},
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			for(var i=0;i<data.length;i++){
				// var url = fileUtils.url('ba','WP',data[i].uuid,data[i].areaId,data[i].url);
                // var url = contextPath+'/zhfz/bacs/iriscollection/imageshow.do?path='+ encodeURI(data[i].url)+"&timeSign="+getTimeSign();
                var url = encodeURI(data[i]);
				$("#showPic_dialog").append('<img width="450" src="'+url+'" /><br/><br/>');
			}
			showDialog('#showPic_dialog','照片');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('Error', '未知错误!');
		}
	});  
}

function belongExport(){
	$('#exportForm').form('clear');
	showDialog('#export_dialog','导出');

}
function exportSave(){
   var startTime=$('#begin_date').datebox('getValue');
	var endTime=$('#end_date').datebox('getValue');
if(startTime==null||startTime==""||endTime==null||endTime==""){
	$.messager.alert('提示', '请选择保管时间!');
	return;
}
	$("#number").val("55");
	$("#areaId").val($('#ssareaid').val());
	$("#startTime").val(startTime);
	$("#endTime").val(endTime);

	$.messager.progress({
		title:'请等待',
		msg:'下载中...'
	});
	document.getElementById("exportForm").submit();
	$.messager.progress('close');
	$('#export_dialog').dialog('close');
}
//---------------------------------------------------------------------------------------------
//点击开柜填写开柜单
function boxopen(index){
	if(isGridButton("sbelongopen")){
		var rowData = $('#belongid').datagrid('getRows')[index];
		if(rowData.isGet!='0'){
			$.messager.alert('提醒', '该柜已领取'); 
			return false;
		}
		showDialog('#openbill_dialog','填写开柜单');
		//柜子列表
		var ssareaid = $('#ssareaid').val();
		$('#lockerId').combobox({     
			editable:false,
			url:'../combobox/listunUsedbox.do?areaid='+ssareaid,
			valueField:'id',
			textField:'value',
			groupField:'groupName',
			panelHeight:'auto',
			formatter:function(row){
		    	return row.value+'['+(row.status==0?'满':'空')+']';
		    }
		  });
		$('#openbill_form').form('clear');
		$('#openbill_form').form('load',rowData);
		$('#getWay').combobox('setValues', '');
		
		url = 'editBoxopen.do?id='+rowData.id;  
	}else{
		alert("权限不足");
	}
}

//开柜单保存
function saveOpeninfo(){
	if(!checkForm('#openbill_form')){
		return;
	}
	var entForm = $('#openbill_form');
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
			$.messager.alert(data.title, data.content);
	    	var row = $('#belongid').datagrid('getSelected');  
//			$('#belongid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
	    	$('#belongid').datagrid('reload',{lockerId_t : $('#lockerIdSeaerch').combobox('getValue'),trefresh:new Date().getTime(),
	    name:$('#s_person').val(),start_date:$('#d_start_date').datebox('getValue'),end_date:$('#d_end_date').datebox('getValue')
	    	});
			$('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
			$('#codid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
			$('#openbill_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('Error', '未知错误!'+data.content);
		}
	});  
}

//---------------------------------------------------------------------------------------------

//编辑随身物品详情
function detEdit(index){
	if(isGridButton("sdetEdit")){
		var rowData = $('#detid').datagrid('getRows')[index];
		showDialog('#det_dialog','编辑随身物品详情');
		$('#det_form').form('clear');
		$('#det_form').form('load',rowData);
		
		url = 'editBelongdet.do?id='+rowData.id;  
	}else{
		alert("权限不足");
	}
	
}

//编辑随身物品详情保存
function savedet(){
	if(!checkForm('#det_form')){
		return;
	}
	var entForm = $('#det_form');
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
			// $.messager.alert(data.title, data.content);
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
			var row = $('#belongid').datagrid('getSelected');  
			$('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
			$('#det_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('Error', '未知错误!'+data.content);
		}
	});  
}


//删除随身物品 
function belongRemove(value){
	if(isGridButton("sbelongRemove")){
		$.messager.confirm('删除确认','是否确定删除此数据?',function(r){  
			if (r){
				jQuery.ajax({
					type : 'POST',
					contentType : 'application/json',
					url : 'removeBelong.do',
					data : '{"id":'+value+'}',
					dataType : 'json',
					success : function(data){
						$.messager.alert(data.title, data.content); 
						var row = $('#belongid').datagrid('getSelected');  
//						$('#belongid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
						$('#belongid').datagrid('reload',{lockerId_t : $('#lockerIdSeaerch').combobox('getValue'),trefresh:new Date().getTime(),
		name:$('#s_person').val(),start_date:$('#d_start_date').datebox('getValue'),end_date:$('#d_end_date').datebox('getValue')		
						});
						$('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
						$.messager.progress('close');
					},
					error : function(data){
						//exception in java
						$.messager.alert('Error', '未知错误!');
					}
				});  
				
			}  
		});  
	}else{
		alert("权限不足");
	}
   
}


//删除随身物品详情

function detRemove(index){
	if(isGridButton("sdetRemove")){
		$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
			if (r){
				var rowData = $('#detid').datagrid('getRows')[index];
				var belongingsId = rowData.belongingsId;
			    var detailId=rowData.id;
				jQuery.ajax({
					type : 'POST',
					url : 'removeBelongdet.do',
					data : {belongingsId:belongingsId,detailId:detailId},
					dataType : 'json',
					success : function(data){
						$.messager.alert(data.title, data.content); 
						var row = $('#belongid').datagrid('getSelected');  
						$('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});// reload the data
//						$('#belongid').datagrid('reload',{trefresh:new Date().getTime()});
						$('#belongid').datagrid('reload',{lockerId_t : $('#lockerIdSeaerch').combobox('getValue'),trefresh:new Date().getTime(),
		name:$('#s_person').val(),start_date:('#d_start_date').datebox('getValue'),end_date:$('#d_end_date').datebox('getValue')			
						});
						$.messager.progress('close');
					},
					error : function(data){
						//exception in java
						$.messager.alert('Error', '未知错误!');
					}
				});  
				
			}  
		});  
	}else{
		alert("权限不足");
	}
   
}

//打印条形码
function printcod(value){
	if(isGridButton("saddBelongcod")){
		if(window.print){
			//办案中心名称
			var an =null;
			//嫌疑人姓名
			var pn =null;
			//案由
			var cn =null;
			//条码值
			var co =null;
			//打印机名称
			var printerName =null;
//	var printerName ="ZDesigner GT800 (EPL) (副本 1)";
			//端口
//	var printerPort ="USB001)";
			var printerPort =null;
			$.messager.confirm('打印确认','是否确定打印条码？',function(r){  
				if (r){
					jQuery.ajax({
						type : 'POST',
						contentType : 'application/json',
						url : 'addBelongcod.do',
						data : '{"id":'+value+'}',
						dataType : 'json',
						cache: false,
						async: false,
						success : function(data){
							if(data!=null){
								an=data.aname;
								cn=data.cname;
								pn=data.pname;
								co=data.burcode;
								if(data.printerName!=null&&data.printerName!=''&&data.printerPort!=null&&data.printerPort!=''){
									printerName =data.printerName;
									printerPort =data.printerPort;
								}else{
									$.messager.alert('Error', '没有配置打印机!');
								}
								console.info("1printerName="+data.printerName+",printerPort="+data.printerName);
								var row = $('#belongid').datagrid('getSelected');  
								$('#codid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});// reload the data
								$.messager.progress('close');
							}
						},
						error : function(data){
							//exception in java
							$.messager.alert('Error', '未知错误!');
						}
					});  
				}
				//---------todo打印方法  
				
				console.info("2printerName="+printerName+",printerPort="+printerPort);
				print_epl.Open_Port(printerPort);
//        print_epl.Open_Port("USB001)");
				print_epl.Begin_Job("2", "12", "False", "B");
				print_epl.Print_Winfont(200, 15, "办案中心名称:"+an, "宋体", 20, 12, "True", "False", "False", "False", "False");
				print_epl.Print_Winfont(200, 45, "嫌疑人姓名:"+pn, "宋体", 20, 12, "True", "False", "False", "False", "False");
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
//     	print_epl.Printing_USBPORT("ZDesigner GT800 (EPL) (副本 1)");  //prot 打印机名称 从 控制面板  ”设备和打印机“ 拷贝即可   
				//-------------- 
			});   
		}else{
			alert("本地没有打印机");
			var iWidth=800; //弹出窗口的宽度;
			var iHeight=600; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open('belongWordTemplate.doc.html','条码打印机安装、操作手册',"height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
		}
	}else{
		alert("权限不足");
	}
}
	    
	    
function doSearch() {
	$('#detid').datagrid('loadData', { total: 0, rows: [] });
	$('#codid').datagrid('loadData', { total: 0, rows: [] });
	$('#belongid').datagrid('load', {
		lockerId_t : $('#lockerIdSeaerch').combobox('getValue'),
		name : $('#s_person').val(),
		start_date : $('#d_start_date').datebox('getValue'),
		end_date : $('#d_end_date').datebox('getValue'),
		areaId : ssareaid,
		trefresh:new Date().getTime()
	});
	
}

function doClear() {
	$('#lockerIdSeaerch').combobox('setValue', '');
	$('#s_person').val('');
	$('#d_start_date').datebox('setValue','');
	$('#d_end_date').datebox('setValue','');
	$('#area').combobox('setValues', '');
}

function printConfig(){
	showDialog('#print_dialog','条码打印机配置');
    $('#print_dialog').form('clear');
    //名称
    var printerName ="ZDesigner GT800 (EPL)";
	//端口
	var printerPort ="USB001)";
    $('#printerName').textbox("setValue",printerName);
	$('#printerPort').textbox("setValue",printerPort);
    url = '../barcode/printerconfig.do';
}

function savePrintConfig(){
	if($("#printerName").val()!=null&&$("#printerName").val()!=''&&$("#printerPort").val()!=null&&$("#printerPort").val()!=''){
		var jsonrtinfo = JSON.stringify($('#print_form').serializeObject());
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			dataType : 'json',
			url : url,
			data : jsonrtinfo,
			success : function(data){
				$.messager.alert(data.title, data.content); 
				$('#print_dialog').dialog('close');
			},
			error : function(data){
				//exception in java
				$.messager.alert('Error', '未知错误!');
			}	
		});  
	}
}

function printHelp(){
	var iWidth=800; //弹出窗口的宽度;
	var iHeight=600; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	window.open('belongWordTemplate.doc.html','条码打印机安装、操作手册',"height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=yes, status=no"); 
}

function hiddenBack() {
	$("#enterprisebtn").hide()
}