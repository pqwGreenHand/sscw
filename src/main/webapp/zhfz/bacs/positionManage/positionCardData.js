var flag = false;
$(function(){
	$('#treegrid').datagrid({
		//title:'定位人员',
		iconCls:'icon-datagrid',
		region: 'center',
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '加载中...',
		method: 'get',
		url: 'listPolices.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		
		columns:[[
		    {title:'ID',field:'id',width:80,sortable:true,hidden:true},
			{field:'name',title:'人员名称',width:100,align:'center'},
			{field:'type',title:'人员类型',width:70,align:'center',
				formatter:function(value) {
						return '<font>民警</font>';
				}
			},
			{field:'cuff_no',title:'手环/卡片编号',width:100,align:'center'},
			{field:'certificate_type',title:'证件类型',width:80,align:'center'},
			{field:'certificate_no',title:'证件号码',width:200,align:'center'},
			{field:'case_name',title:'所属案件名称',width:850,align:'center'}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		onClickRow: function(index,row){
			//alert(rowData);
			
			showPositionData(row);
			showPositionAlarm(row);
			showPositionEntrance(row);
		},
		rownumbers:true,
		toolbar:'#toolbar'
	});

	var p = $('#treegrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(pageNumber, pageSize){
			doSearch();
		    return false;
		}
	});

	$('#fudong').remove();
});

function doSearch() {

	$('#treegrid').datagrid('load', {
		name:$('#s_name').textbox('getValue'),
		cuff_no:$('#s_cuff_no').textbox('getValue'),
		case_name:$('#s_case_name').textbox('getValue'),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_name').textbox('setValue','');
	$('#s_cuff_no').textbox('setValue','');
	$('#s_case_name').textbox('setValue','');
}

function getDatetime(data){
    var dt = new Date(data);
	    return (dt.getFullYear()+'-'+(dt.getMonth()+1)+'-'+dt.getDate()+' '+dt.getHours()+':'+dt.getMinutes()+':'+dt.getSeconds()).replace(/([\-\: ])(\d{1})(?!\d)/g,'$10$2');
}


//定位轨迹
function showPositionData(row){
	console.log(row);
	$('#positionData').datagrid({
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
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		queryParams: {cuffId:row.cuff_no,caseDataType:"定位轨迹",trefresh:new Date().getTime(),startTime:getDatetime(row.in_time),endTime:getDatetime(row.out_time)},
		url: 'listPositionData.do',
		singleSelect:true,
		frozenColumns:[[         
		                {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		    		]],
		columns:[[
		    
		    {field:'cuffId',title:'手环编号',width:200,align:'center'},
		    {field:'deviceId',title:'设备编号',width:300,align:'center'},
			{field:'sendTime',title:'发送时间',width:300,align:'center',formatter:function(value){
				return value?formatDateTimeText(new Date(value)):'';
			}}
			
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			
		} 
		
	});
	
	var casePositionData = $('#positionData').datagrid('getPager');
	$(casePositionData).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#positionData').datagrid('reload',{
				serialId:row.id,
				cuffId:row.cuff_no,
				caseDataType:"定位轨迹",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
}

//定位定位告警
function showPositionAlarm(row){
	$('#positionAlarm').datagrid({
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
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		queryParams: {cuffId:row.cuff_no,caseDataType:"告警",trefresh:new Date().getTime(),startTime:getDatetime(row.in_time),endTime:getDatetime(row.out_time)},
		url: 'listAlarm.do',
		singleSelect:true,
		frozenColumns:[[         
		                {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		    		]],
		columns:[[
		    
		    {field:'cuffId',title:'手环编号',width:200,align:'center'},
		    {field:'deviceId',title:'设备编号',width:300,align:'center'},
		    {field:'alarmType',title:'告警类型',width:100,align:'center',
				formatter:function(value) {
					if (value == '0') {
						return '拆卸警报';
					} else if (value == '1') {
						return '电压警报';
					}
				}
			},
			{field:'alarmTime',title:'告警时间',width:300,align:'center',formatter:function(value){
				return value?formatDateTimeText(new Date(value)):'';
			}}
			
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
		} 
		
	});
	
	var casePositionAlarm = $('#positionAlarm').datagrid('getPager');
	$(casePositionAlarm).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#positionAlarm').datagrid('reload',{
				serialId:row.id,
				cuffId:row.cuff_no,
				caseDataType:"定位告警",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
}

//定位进出
function showPositionEntrance(row){
	$('#positionEntrance').datagrid({
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
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		queryParams: {cuffId:row.cuff_no,caseDataType:"定位进出",trefresh:new Date().getTime(),startTime:getDatetime(row.in_time),endTime:getDatetime(row.out_time)},
		url: 'listPositionEntrance.do',
		singleSelect:true,
		frozenColumns:[[         
		                {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		    		]],
		columns:[[
		    
		    {field:'cuffId',title:'手环编号',width:200,align:'center'},
		    {field:'deviceId',title:'设备编号',width:300,align:'center'},
		    {field:'actionType',title:'进/出',width:100,align:'center',
				formatter:function(value) {
					if (value == '0') {
						return '进';
					} else if (value == '1') {
						return '出';
					}
				}
			},
			{field:'actionTime',title:'进出时间',width:300,align:'center',formatter:function(value){
				return value?formatDateTimeText(new Date(value)):'';
			}}
			
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//当数据加载成功时触发 
		onLoadSuccess:function(){
			//alert(11);
			//该死的onload的未知bug,延迟0.1秒就行了
			//setTimeout(function(){checkRoles();},100);
//					checkRoles();
		} 
		
	});
	
	var casePositionEntrance = $('#positionEntrance').datagrid('getPager');
	$(positionEntrance).pagination({
		onBeforeRefresh:function(){
			var rowData = $('#treegrid').datagrid('getSelected');  
			var id = rowData.id.split('_')[0];
			$('#positionEntrance').datagrid('reload',{
				serialId:row.id,
				cuffId:row.cuff_no,
				caseDataType:"定位告警",
				trefresh : new Date().getTime()
			})
			return false;
		}
	});
}