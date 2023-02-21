 

$(function(){
	$('#datagrid').datagrid({
		iconCls : 'icon-datagrid',
		region : 'center',
		width : '100%',
		height : '100%',
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		loadMsg : '加载中...',
		method : 'get',
		url : '../../export/accountList.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName : 'serialId',
		sortOrder : 'desc',
		remoteSort : false,
		idField : 'serialId',
		singleSelect : true,
		frozenColumns : [ [

		{
			title : 'ID',
			field : 'serialId',
			width : 20,
			sortable : true,
			hidden : true
		} ] ],
		columns : [ [
		 {
			field : 'personName',
			title : '姓名',
			width : 100,
			align : 'center'
		}, {
			field : 'personSex',
			title : '性别',
			width : 50,
			align : 'center',
			formatter : function(value) {
				if (value == '1') {
					return '男'
				} else if (value == '2') {
					return '女'
				}
			}
		}, {
			field : 'personCertificateNo',
			title : '证件号码',
			width : 150,
			align : 'center'
		}, {
			field : 'inTime',
			title : '核查时间',
			width : 100,
			align : 'center',
			formatter : function(value) {
				return "无"
			}
		},{
			field : 'caseProperties',
			title : '主案别',
			width : 100,
			align : 'center'
		},{
			field : 'caseType',
			title : '案件性质',
			width : 100,
			align : 'center',
			formatter : function(value) {
				if (value == '2') {
					return '刑事'
				} else if (value == '1') {
					return '行政'
				}
			}
		}, {
			field : '查询系统',
			title : '查询系统',
			width : 100,
			formatter : function(value, rec) {
				return "智能检索、核录";
			}
		}, {
			field : 'confirmResult',
			title : '最终案件定性',
			width : 100
		}, {
			field : '核查结果',
			title : '核查结果',
			width : 80,
			formatter : function(value, rec) {
				return "无";
			}
		},{
			field : 'workSpace',
			title : '送案单位',
			width : 150,
			align : 'center'
		},{
			field : 'outTime',
			title : '出区时间',
			width : 150,
			formatter : function(value, rec) {
				if (value != null && value != '') {
					return valueToDate(value);
				} else {
					return "";
				}
			}
		},{
			field : 'sfsyjd',
			title : '出区方式',
			width : 100,
			align : 'center',
			formatter : function(value, rec){
				if(value==1){
					return "押解队";
				}else{
					return "释放";
				}
			}
		},{
			field : '备注',
			title : '备注',
			width : 100,
			align : 'center'
		}
		/*, {
			field : '总人数',
			title : '总人数',
			width : 100,
			align : 'center'
		},{
			field : '男/女',
			title : '男/女',
			width : 100,
			align : 'center'
		}*/
		] ],
		pagination : true,
		pageList : [ 10, 20, 30, 40, 50, 100 ],
		rownumbers : true,
		toolbar : '#exportToolbar'
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
		}
	});
	
	$('#fudong').remove();
});

function doSearch() {
	$('#datagrid').datagrid('load', {
		startTime : $('#startTime1').datebox('getValue'),
		endTime : $('#endTime1').datebox('getValue'),
		trefresh:new Date().getTime()

	});

}

function doClear() {
	$("#startTime1").datetimebox('clear');
	$("#endTime1").datetimebox('clear');
}
function saveData() { 
	var startTime = $('#startTime1').datebox('getValue');
	var endTime = $('#endTime1').datebox('getValue'); 
	$.messager.progress({
		title : '请等待',
		msg : '下载中...'
	});
	
	 
	$('#startTime').val(startTime);
	$('#endTime').val(endTime); 
	// 2请求后台
	document.getElementById("exportForm").submit();
	$.messager.progress('close');
	// 3打印表格

}  