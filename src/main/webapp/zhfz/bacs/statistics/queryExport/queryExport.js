var ys;
$(function(){
	$.ajaxSettings.async = false;
	$.get("../../common/getCssName.do", function(data){
		ys=data;
	});
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
		url : '../../export/list.do',
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
			width : 80,
			sortable : true,
			hidden : true
		} ] ],
		columns : [ [
		// 类型0 嫌疑人 1事主 2证人 3其它
		{
			field : 'personType',
			title : '人员类型',
			width : 100,
			align : 'center',
			formatter : function(value) {
				if (value == '0') {
					return '<font color=\"red\">嫌疑人</font>'
				} else if (value == '1') {
					return '<font color=\"green\">事主</font>'
				} else if (value == '2') {
					return '证人'
				} else if (value == '3') {
					return '其它'
				}else if (value == '4') {
					return '翻译'
				}else if (value == '5') {
					return '办案辅警'
				}else if (value == '6') {
					return '临时入区民警'
				}else if (value == '7') {
					return '临时入区辅警'
				}else if (value == '8') {
					return '<font color=\"blue\">律师</font>'
				}else if (value == '9') {
					return '代理人'
				}
				
			}
		}, {
			field : 'personName',
			title : '姓名',
			width : 100,
			align : 'center'
		}, {
			field : 'personSex',
			title : '性别',
			width : 100,
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
			width : 200,
			align : 'center'
		}, {
			field : 'personAge',
			title : '是否成年',
			width : 80,
			align : 'center',
			formatter : function(value) {
				if(ys=='black'){
				if (value == '0') {
					return '<font color=\"red\">未成年</font>'
				} else if (value == '1') {
					return '<font color="#00FF00">成年</font>'
				}
				}else{
					if (value == '0') {
						return '<font color=\"red\">未成年</font>'
					} else if (value == '1') {
						return '<font color=\"green\">成年</font>'
					}
				}
			}
		},
		// 0刑事 1行政
		{
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
			field : 'caseProperties',
			title : '主案别',
			width : 200,
			align : 'center'
		}, {
			field : 'inTime',
			title : '入区时间',
			width : 200,
			formatter : function(value, rec) {
				return valueToDate(value);
			}
		}, {
			field : 'outTime',
			title : '出区时间',
			width : 200,
			formatter : function(value, rec) {
				if (value != null && value != '') {
					return valueToDate(value);
				} else {
					return "";
				}
			}
		},
		//confirmResult
		{
			field : 'confirmResult',
			title : '裁决结果',
			width : 200
		},
		{
			field : 'confirmTime',
			title : '裁决时间',
			width : 200,
			formatter : function(value, rec) {
				if (value != null && value != '') {
					return valueToDate(value);
				} else {
					return "";
				}
			}
		}, {
			field : 'outPlace',
			title : '出区去向',
			width : 200,
			align : 'center'
		}, {
			field : 'policeman',
			title : '办案民警',
			width : 200,
			align : 'center'
		}, {
			field : 'workSpace',
			title : '办案单位',
			width : 200,
			align : 'center'
		}
		,
		{
			field : 'crimeSection',
			title : '主案别',
			width : 200,
			align : 'center',
			hidden : true
		},
		{
			field : 'crimeTypeId',
			title : '罪名id',
			width : 200,
			align : 'center',
			hidden : true
		}, 
		 {
			field : 'writtenTime',
			title : '手续开具时间',
			width : 200,
			align : 'center',
			formatter : function(value, rec) {
				if (value != null && value != '') {
					return valueToDate(value);
				} else {
					return "";
				}
			}
		}
		 
		] ],
		pagination : true,
		pageList : [ 10, 20, 30, 40, 50, 100 ],
		rownumbers : true,
		toolbar : '#exportToolbar'
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
//			$('#datagrid').datagrid('reload',{trefresh:new Date().getTime()} );
			$('#datagrid').datagrid('load', {
				personName : $('#personName').val(),
				workSpace : $('#workSpace').val(),
				personType : $('#personType').combobox('getValue'),
				personSex : $("#personSex").combobox('getValue'),
				caseType : $('#caseType').combobox('getValue'),
				caseProperties : $('#caseProperties').val(),
				betweenTime : $('#betweenTime').datebox('getValue'),
				betweenTimeMax : $('#betweenTimeMax').datebox('getValue'),
				trefresh:new Date().getTime()

			});
			return false;
		}
	});
	
	$('#fudong').remove();
});

function doSearch() {
	$('#datagrid').datagrid('load', {
		personName : $('#personName').val(),
		workSpace : $('#workSpace').val(),
		personType : $('#personType').combobox('getValue'),
		personSex : $("#personSex").combobox('getValue'),
		caseType : $('#caseType').combobox('getValue'),
		caseProperties : $('#caseProperties').val(),
		betweenTime : $('#betweenTime').datebox('getValue'),
		betweenTimeMax : $('#betweenTimeMax').datebox('getValue'),
		subjectType : $('#subjectType').combobox('getValue'),
		subjectTime : $('#subjectTime').val(),
		trefresh:new Date().getTime()

	});

}

function doClear() {
	$('#workSpace').prop('value', '');
	$('#personName').prop('value', '');
	$('#personType').combobox('setValues', '');
	$('#personSex').combobox('setValues', '');
	$('#caseType').combobox('setValues', '');
	$('#caseProperties').val('');
	$("#betweenTime").datetimebox('clear');
	$("#betweenTimeMax").datetimebox('clear');
}
 

function exportData() {
	showDialog('#exportExcel_dialog', '选择列名');
}
 

function saveData() {
	// 0 弹出dialog勾选列
	var bb = "";
	var temp = "";
	var a = document.getElementsByName("checkTheme");

	for (var i = 0; i < a.length; i++) {
		if (a[i].checked) {
			temp = a[i].value;
			bb = bb + "," + temp;
		}
	}
	document.getElementById("tempString").value = bb.substring(1, bb.length);
	// 1抓取条件查询的值
	var personName = $('#personName').val();
	var personType = $('#personType').combobox('getValue');
	var personSex = $("#personSex").combobox('getValue');
	var caseType = $('#caseType').combobox('getValue');
	var caseProperties = $('#caseProperties').val();
	var workSpace = $('#workSpace').val();
	var betweenTime = $('#betweenTime').datebox('getValue');
	var betweenTimeMax = $('#betweenTimeMax').datebox('getValue');
    var subjectType =  $('#subjectType').val();
    var subjectTime =  $('#subjectTime').val();
    
	$.messager.progress({
		title : '请等待',
		msg : '下载中...'
	});
	
	$('#personName1').val(personName);
	$('#personType1').val(personType);
	$('#personSex1').val(personSex);
	$('#caseType1').val(caseType);
	$('#caseProperties1').val(caseProperties);
	$('#workSpace1').val(workSpace);
	$('#betweenTime1').val(betweenTime);
	$('#betweenTimeMax1').val(betweenTimeMax);
	$('#subjectType1').val(subjectType);
	$('#subjectTime1').val(subjectTime);
	
	// 2请求后台
	document.getElementById("exportForm").submit();
	$.messager.progress('close');
	// 3打印表格

}  