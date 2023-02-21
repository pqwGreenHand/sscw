//保存调用摄像头的功能，用于对不同的地方调用摄像头做出不同的反馈
var phototype="";
//保存当前浏览器信息
var browser;
$(function() {
	caseAjlyCode = 1;
	loadInterrogatearea();
	browser = myBrowser();
	$('#datagrid').datagrid({
		// title:'嫌疑人入区',
		iconCls : 'icon-datagrid',
		region : 'center',
		width : '100%',
		height : '100%',
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		loadMsg : '加载数据中...',
		method : 'get',
		url : 'list.do',
		queryParams : {
			trefresh : new Date().getTime()
		},
		sortName : 'id',
		sortOrder : 'desc',
		remoteSort : false,
		idField : 'id',
		singleSelect : true,
		frozenColumns : [ [
			//{field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true},
		] ],
		columns : [ [
				{
					field : 'name',
					title : '嫌疑人',
					width : 40,
					formatter : function(value, rec) {
						return '<font color="yellow">'
								+ formatterColunmVal(value, 5)
								+ '</font>';
					}
				},
				{
					field : 'inTime',
					title : '入区时间',
					width : 70,
					formatter : function(value, rec) {
						return valueToDate(value);
					}
				},
				{
					field : 'sex',
					title : '性别',
					width : 30,
					formatter : function(value, rec) {
						if (value == 0) {
							return "未知的性别";
						}
						if (value == 1) {
							return "男";
						}
						if (value == 2) {
							return "女";
						}
						if (value == 9) {
							return "未说明的性别";
						}
						return "";
					}
				},
				{
					field : 'cuffNo',
					title : '手环编号',
					width : 95,
					hidden : true
				},
				{
					field : 'certificateNo',
					title : '证件号码',
					width : 100
				},
				{
					field : 'sendUserRealName',
					title : '民警',
					width : 30
				},
				{
					field : 'organization',
					title : '办案单位',
					width : 60
				},
				{
					field : 'sfxxcj',
					title : '是否信息采集',
					width : 70,
					formatter : function(value){
						if(value==0){
							return '<font color="red">未采集</font>';
						}else if(value==1){
							return '<font color="#0fea67">已采集</font>';
						}else if(value==2){
							return '<font color="#0fea67">所内采集</font>';
						}
						else if(value==3){
							return '<font color="#0fea67">办案中心内采集</font>';
						}
					}
				},
				{
					field : 'status',
					title : '状态',
					width : 55,
					formatter : function(value, rec) {
						// 0已入区 1已安检 2物品已暂存 3候问开始 4候问结束 5信息已采集
						// 6审讯开始 7审讯结束 8物品已领取 9临时出区返回 10临时出区
						// 11已出区
						if (value == 0) {
							return '<font color="green">已入区</font>';
						} else if (value == 1) {
							return '<font color="yellow">已安检</font>';
						} else if (value == 2) {
							return '<font color="orange">物品已暂存</font>';
						} else if (value == 3) {
							return '<font color="yellow">候问开始</font>';
						} else if (value == 4) {
							return '<font color="orange">候问结束</font>';
						} else if (value == 5) {
							return '<font color="yellow">信息已采集</font>';
						} else if (value == 6) {
							return '<font color="orange">审讯开始</font>';
						} else if (value == 7) {
							return '<font color="yellow">审讯结束</font>';
						} else if (value == 8) {
							return '<font color="orange">物品已领取</font>';
						} else if (value == 9) {
							return '<font color="gray">临时出区返回</font>';
						} else if (value == 10) {
							return '<font color="orange">临时出区</font>';
						} else if (value == 11) {
							return '<font color="gray">已出区</font>';
						}
					}
				},
				{
					field : 'outReason',
					title : '出区去向',
					width : 70,
					formatter : function(value) {
						return formatterColunmVal(value, 10);
					}
				},
				{
					field : 'outTime',
					title : '出区时间',
					width : 70,
					formatter : function(value, rec) {
						if (value != null && value != '') {
							return valueToDate(value);
						} else {
							return "";
						}
					}
				},
				{
					field : 'caseType',
					title : '案件性质',
					width : 80,
					formatter : function(value, rec) {
						if (value ==1) {
							return "行政";
						} else  if(value ==2){
							return "刑事";
						}
					}
				},
				{
					field : 'caseNature',
					title : '案由',
					width : 80,
					formatter : function(value, rec) {
						if (value == null || value == "") {
							return '无';
						} else {
							return value;
						}
					}
				},
				{
					field : 'confirmResult',
					title : '裁决结果',
					width : 45,
					formatter : function(value, rec) {
						if (value != null && value != '') {
							return value;
						} else {
							return '<font color="red">未裁决</font>';
						}
					}
				},
				{
					field : 'interrogateAreaName',
					title : '办案场所',
					width : 65
				},
			{
				field : 'caseNo',
				title : '是否关联',
				width : 65,
				formatter: function (value,row,index) {
					var ajbh = row.caseNo;
					var rybh = row.rybh;
					if(ajbh !=null && ajbh !="" && rybh !=null && rybh !=""){
						if(ajbh.startWith("A") && ajbh.length > 10 && rybh.startWith("R") && rybh.length > 10){
							return '<a href="#"  onclick="chakan('+ "'" + row.caseNo + "'" + ',' + "'" + row.rybh + "'" + ')"><font color="green">已关联</font></a>';
                        }else{
							return '<a href="#"  onclick="weiguanlian('+ "'" + row.id + "'" + ',' + "'" + row.certificateNo + "'" + ','+ index  + ')"><font color="red">未关联</font></a>';
						}
					}else{
						return '<a href="#"  onclick="weiguanlian('+ "'" + row.id + "'" + ',' + "'" + row.certificateNo + "'" + ','+ index  + ')"><font color="red">未关联</font></a>';
					}
				}
			},
				{
					field : '操作',
					title : '操作',
					width : 200,
					align : 'center',
					formatter : function(value, row, index) {
						var a = '';
						var e = '';
						var p = '';
						var o = '';
						var t = '';
						var c = '';
						var ep = '';
						var gl = '';
						var sg = '';
						var jz = '';
						var five = '';
						var sftjcl = '';
						var cq = '';
						if (row.status < 11) {
							if (isGridButton("entranceEdit")) {
								e = '<span class="spanRow"><a href="#" class="gridedit" onclick="entranceEdit2('
										+ index
										+ ')">修改</a></span>';
							}
							if (isGridButton("jzEdit")) {
								jz = '<span class="spanRow"><a href="#" class="gridconnect" onclick="jzEdit2('
									+ index
									+ ')">警综</a></span>';
							}
							if (isGridButton("photoEdit")) {
								ep = '<span class="spanRow"><a href="#" class="gridpicture" onclick="photoEdit2('
										+ index
										+ ')">照片</a></span>';
							}
							sg = '<span class="spanRow"><a href="#" class="gridedit" onclick="SignTaizhang('
								+ row.id
								+ ')">签字</a></span>';
                            cq = '<span class="spanRow"><a href="#" class="gridpicture" onclick="cxSignTaizhang('
                                + row.id
                                + ')">重新签字</a></span>';
							a = '<span class="spanRow"><a href="#" class="gridprint" onclick="PringTZ('
								+ row.id
								+ ')">原始版打印</a></span>';
							if (isGridButton("printChoose")) {
								p = '<span class="spanRow"><a href="#" class="gridprint" onclick="PringTaizhang('
										+ row.id
										+ ')">签字版打印</a></span>';
							}
							if (isGridButton("exitBegin2")) {
								o = '<span class="spanRow"><a href="#" class="gridout" onclick="exitBegin2('
										+ index
										+ ')">出区</a></span>';
							}
							if (row.status == 10) {
								if (isGridButton("tempBack")) {
									t = '<span class="spanRow"><a href="#" class="gridundo" onclick="tempBack('
											+ index
											+ ')">返回</a></span>';
								}
							}
							/*if (isGridButton("tempBack")) {
								t = '<span class="spanRow"><a href="#" class="gridundo" onclick="tempBack('
									+ index
									+ ')">返回</a></span>';
							}
*/
							if (row.confirmTime == null
									|| row.confirmTime == '') {
								if (isGridButton("confirmButton")) {
									c = '<span class="spanRow"><a href="#" class="gridhammer" onclick="confirmButton('
											+ index
											+ ')">裁决</a></span>';
								}
							}
							// 关联以前的入区情况
							gl = '<span class="spanRow"><a href="#" class="gridconnect" onclick="relateButton2('
									+ index
									+ ')">关联</a></span>';
							
							if(!row.sftjcl){
								if (isGridButton("sftjcl")) {
									sftjcl = '<span class="spanRow"><a href="#" class="gridhammer" onclick="tjclButton('
											+ index
											+ ')">提交材料</a></span>';
								}
							}
							
							return e + sg + a +  p + o + t + c + ep + gl + jz+sftjcl+cq;
						} else {
							if (isGridButton("entranceEdit")) {
								e = '<span class="spanRow"><a href="#" class="gridedit" onclick="entranceEdit2('
										+ index
										+ ')">修改</a></span>';
							}
							if (isGridButton("jzEdit")) {
								jz = '<span class="spanRow"><a href="#" class="gridconnect" onclick="jzEdit2('
									+ index
									+ ')">警综</a></span>';
							}
							a = '<span class="spanRow"><a href="#" class="gridprint" onclick="PringTZ('
								+ row.id
								+ ')">原始版打印</a></span>';
							if (isGridButton("printChoose")) {
								p = '<span class="spanRow"><a href="#" class="gridprint" onclick="PringTaizhang('
										+ row.id
										+ ')">签字版打印</a></span>';
							}
							if (row.confirmTime == null
									|| row.confirmTime == '') {
								if (isGridButton("confirmButton")) {
									c = '<span class="spanRow"><a href="#" class="gridhammer" onclick="confirmButton('
											+ index
											+ ')">裁决</a></span>';
								}
							}
							if (isGridButton("fivePrint")) {
								five =  '<span class="spanRow"><a href="#" class="gridprint" onclick="FivePringTaizhang('
									+ row.id
									+ ')">五合一台账</a></span>';
							}
							if(!row.sftjcl){
								if (isGridButton("sftjcl")) {
									sftjcl = '<span class="spanRow"><a href="#" class="gridhammer" onclick="tjclButton('
											+ index
											+ ')">提交材料</a></span>';
								}
							}
							return e + sg + a + p + c + jz+five+sftjcl;;
						}
					}
				} ] ],
		pagination : true,
		pageSize : 10,
		pageList : [ 10,20, 30, 40, 50, 100 ],
		rownumbers : true,
		toolbar : '#toolbar',
		onDblClickRow : function(index) {
			var currentRow = $('#datagrid').datagrid('getRows')[index];
			$('#serailDetail').html(xqHtml(currentRow));
			showDialog('#serailDetail', '入出区详情');
		}
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
			// $('#datagrid').datagrid('reload',{trefresh:new Date().getTime()}
			// );
			var es = $('#e_status').combobox('getValue');
			$('#datagrid').datagrid('load', {
				name : $('#e_person').val(),
				certificateNo : $('#e_certificateNo').val(),
				status : es,
				start_date : $('#e_start_date').datebox('getValue'),
				end_date : $('#e_end_date').datebox('getValue'),
				trefresh : new Date().getTime()
			});
			return false;
		}
	});

	$('#fudong').remove();
});
//重新签字台账签字
function  cxSignTaizhang(serialId) {
    $("#cxSignTaizhangDialog").show();
    $("#cxSignTaizhangDiv").show();
    $("#cxSignTaizhangSerialId").val(serialId);
    CurrFlag = typeof currenMap != "undefined" ? true : false;
}
function doSearch() {
	var es = $('#e_status').combobox('getValue');
	var caseType = $('#e_ajlx').combobox('getValue');
	var areaId = $('#interrogatearea').combobox('getValue');
	var sfsyjd = $('#e_sfsyjd').combobox('getValue');
	var glaj = $('#e_glaj').combobox('getValue');
	$('#datagrid').datagrid('load', {
		name : $('#e_person').val(),
		certificateNo : $('#e_certificateNo').val(),
		status : es,
		caseType:caseType,
		areaId : areaId,
		start_date : $('#e_start_date').datebox('getValue'),
		end_date : $('#e_end_date').datebox('getValue'),
		sfsyjd : sfsyjd,
		glaj : glaj,
		trefresh : new Date().getTime()
	});
}
function doSearchOutTimeRecord(){
    var hour = $('#hour').combobox('getValue');
    $('#timeoutRecodeDatagrid').datagrid('load', {
        name : $('#out_person').val(),
        hour:hour,
		startTime:$('#out_start_date').datebox('getValue'),
		endTime:$('#out_end_date').datebox('getValue'),
        trefresh : new Date().getTime()
    });
}
function doClearOutTimeRecord(){
    $('#out_person').val("")
    $('#out_end_date').datebox('setValue',""),
    $('#out_start_date').datebox('setValue',""),
    $('#hour').combobox('setValue',2);
}
function exportOutTimeRecord(){
    $.messager.progress({
        title : '请等待',
        msg : '下载中...'
    });
    $('#out_name').val($('#out_person').val());
    $('#out_hour').val($('#hour').combobox('getValue'));
    $('#out_start_time').val($('#out_start_date').datebox('getValue'));
    $('#out_end_time').val($('#out_end_date').datebox('getValue'));
    // 2请求后台
    document.getElementById("exportFormOutTime").submit();
    $.messager.progress('close');
}
function  chakan(ajbh,rybh){
	$('#jzxiangqing').html('');
	var jzxqHtmlBody = ' <table  id="tt" class="colinfo_table">'
		+ ' <tr>'
		+ ' 	<td style="background-color: #000088">执法办案平台案件编号</td>'
		+ ' <td>'
		+ ajbh
		+ '</td>'
		+ ' </tr>'
		+ ' <tr>'
		+ ' 	<td style="background-color: #000088">执法办案平台人员编号</td>'
		+ ' <td>'
		+ rybh
		+ '</td>'
		+ ' </tr>'
	' </table> ';
	$('#jzxiangqing').html(jzxqHtmlBody);
	showDialog('#jzxiangqing', '关联执法办案平台详情');
}


function enterKeyEvents() {
	var e = e || window.event;
	if (e.keyCode == 13) {
		var  zjhm =$('#dataCertificateNo').val();
		jingzongByzjhm(zjhm);
	}
}

function jingzongByzjhm(zjhm){
	var caseName =  "";
	var caseLb = $('#jzajlb').combobox('getValue');
	// var zjhm = $('#dataCertificateNo').val();
	var zjhm = zjhm;
	loadJZPersonByZJHM(zjhm);
	showDialog('#jz_orderrequest', '执法办案平台');
	$('#jzInfo').datagrid({
//		title:'人身安全检查',
		iconCls: 'icon-datagrid',
		region: 'center',
		width: '100%',
		height: '100%',
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: 'queryJzAjxxByzjhm.do?zjhm='+zjhm,
		queryParams: {
			trefresh: new Date().getTime()
		},
		sortOrder: 'desc',
		remoteSort: false,
		idField: 'id',
		singleSelect: true,
		columns: [[
			{field: 'AJMC', title: '案件名称', width: 180},
			{field: 'AJBH', title: '案件编号', width: 120},
			{field: 'FXSJ', title: '案发时间', width: 60,
				formatter:function(value,rec){
					return value;
				}
			},
			{field: 'FADDXZ', title: '案发地址', width: 140}
		]],
		pagination: true,
		pageList: [10, 20, 40, 50, 100],
		rownumbers: true,
		toolbar: '#toolbar_jzInfo',
		onSelect: function (rowIndex, rowData) {
			loadJZPerson(rowData.ajbh,caseLb);
		}
	});
}

//手动关联
function weiguanlian(id,sfzh,index){
	$('#selectJzLableGl').html('<font style="color: red">未关联执法办案平台数据！</font></font>');
	$("#glajbh").val("");
	$("#glrybh").val("");
	var rows = $("#datagrid").datagrid("getRows")[index];
	$("#sId").val(id);
	$("#sfz").val(sfzh);


	$("#wgyy").combobox('setText',rows.unRelationReason);

	showDialog('#sdgl_dialog', '手动关联案件信息');

}

function glSave() {
	if ($("#glajbh").val().length != 0 && $("#glrybh").val().length != 0 ){

		if ( $("#wgyy").combobox("getText") ==  "外省市单位案件" || $("#wgyy").combobox("getText") ==  "涉密案件"
		|| $("#wgyy").combobox("getText") ==  "2012年前逃犯"|| $("#wgyy").combobox("getText") ==  "教育释放" ){
			if($("#glajbh").val().length == 0 || $("#glrybh").val().length == 0){
				$.messager.alert("提示", "请输入案件或人员编号");
				return;
			}
			if($("#glajbh").val().length < 10 || $("#glrybh").val().length < 10){
				$.messager.alert("提示", "请输入正确的案件或人员编号");
				return;
			}

			var s = /^[0-9]*$/;
			var s2 = /^A\w*$/;
			var s3 = /^R\w*$/;
			if(!$("#glajbh").val().match(s2)){
				$.messager.alert("提示", "所输入的案件编号首字母必须大写A！");
				return;
			}
			if(!$("#glrybh").val().match(s3)){
				$.messager.alert("提示", "所输入的人员编号首字母必须大写R！");
				return;
			}
			if (!$("#glajbh").val().substring(1,$("#glajbh").val().length).match(s) ||
				!$("#glrybh").val().substring(1,$("#glrybh").val().length).match(s) ){
				$.messager.alert("提示", "所输入的人员或案件编号除首字母外均为数字！");
				return;
			}
		}
	}
//	var myReg = /^[a-zA-Z0-9_]{0,}$/;

	var entranceData = {};
	entranceData["ajbh"] = $("#glajbh").val();
	entranceData["rybh"] = $("#glrybh").val();
    // if(sfzfglAjbh==$("#glajbh").val() && sfzfglRybh==$("#glrybh").val()){
    //     entranceData["sfzdgl"] = 1;
    // }else{
        entranceData["sfzdgl"] = 2;
    // }
	entranceData["id"] = $("#sId").val();
	entranceData["hczt"] = 2;
	entranceData["wgl"] = $("#wgyy").combobox('getText');
	var jsonrtinfo = JSON.stringify(entranceData);
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : 'json',
		url : 'serialGlUpdate.do',
		data : jsonrtinfo,
		success : function(data) {
			$.messager.alert(data.title, data.content);
			$('#datagrid').datagrid('load', {
				trefresh : new Date().getTime()
			});
			$('#sdgl_dialog').dialog('close');
			$.messager.progress('close');
			$('#GlForm').form('clear');
		},
		error : function(data) {
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});
}

function jingzongByzjhmGl(){
	var zjhm = $('#sfz').val();
	loadJZPersonByZJHM(zjhm)
	showDialog('#jz_orderrequest', '执法办案平台');
	$('#jzInfo').datagrid({
		iconCls: 'icon-datagrid',
		region: 'center',
		width: '100%',
		height: '100%',
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: 'queryJzAjxxByzjhm.do?zjhm='+zjhm+"&type=0",
		queryParams: {
			trefresh: new Date().getTime()
		},
		sortOrder: 'desc',
		remoteSort: false,
		idField: 'id',
		singleSelect: true,
		columns: [[
			{field: 'AJMC', title: '案件名称', width: 180},
			{field: 'AJBH', title: '案件编号', width: 180},
			{field: 'FXSJ', title: '案发时间', width: 60},
			{field: 'FADDXZ', title: '案发地址', width: 140}
		]],
		pagination: true,
		pageList: [10, 20, 40, 50, 100],
		rownumbers: true,
		toolbar: '#toolbar_jzInfo',
		onSelect: function (rowIndex, rowData) {
            if(rowData.AJBH!=null){
                loadJZPerson(rowData.AJBH);
            }
		}
	});
}

function loadJZPersonByZJHM(zjhm) {
	$('#jzPerson')
		.datagrid(
			{
				title: '嫌疑人信息',
				iconCls: 'icon-datagrid',
				region: 'east',
				width: '100%',
				height: '100%',
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: false,
				loadMsg: 'Loading...',
				method: 'get',
				remoteSort: false,
				url: 'queryJzPersonXs.do',
				// idField:'code',
				queryParams: {
					zjhm: zjhm,
					trefresh: new Date().getTime()
				},
				pagination: false,
				// pageList: [5, 10, 15, 20, 30, 50],
				rownumbers: true,
				singleSelect: false,
				frozenColumns: [[
					{field: 'id', checkbox: true},
					{
						title: 'id',
						field: 'id',
						width: 10,
						sortable: true,
						hidden: true
					}]],
				columns: [[
					{field: 'RYXM', title: '姓名', width: 20},
					{
						field: 'XB', title: '性别', width: 20,
						formatter: function (value, rec) {
							if (1 == value) {
								return "男";
							} else if (2 == value) {
								return "女";
							} else {
								return '未知';
							}
						}
					},
					{field: 'ZJHM', title: '证件编号', width: 50},
					{field: 'XZDXZ', title: '暂住地址', width: 50}
				]],
				rownumbers: true

			});
}

var sfzfglAjbh;
var sfzfglRybh;
function  saveZJInfo(){
	$("#yy").val("");
	$("#glyy").val("");
	var jzInfo1=$('#jzInfo').datagrid('getSelected');
	var jzInfo=$('#jzPerson').datagrid('getSelected');
	if(jzInfo1 != null && jzInfo != null){
		$('#selectJzLable').html('<font style="color: green">已关联执法办案平台数据，案件名称为('+jzInfo1.AJMC+')。</font>');
		$('#selectJzLableCq').html('<font style="color: green">已关联执法办案平台数据，案件名称为('+jzInfo1.AJMC+')。</font>');
		$('#selectJzLableGl').html('<font style="color: green">已关联执法办案平台数据，案件名称为('+jzInfo1.AJMC+')。</font>');
	}else if(jzInfo1 == null && jzInfo != null){
		$('#selectJzLable').html('<font style="color: green">已关联执法办案平台数据。</font>');
		$('#selectJzLableCq').html('<font style="color: green">已关联执法办案平台数据。</font>');
		$('#selectJzLableGl').html('<font style="color: green">已关联执法办案平台数据。</font>');
	}
	if(jzInfo !=null){
		$('#orderContentPeople').combogrid('setValue',jzInfo.ryxm);
		$('#rqAjbh').val(jzInfo.AJBH);
		$('#rqRybh').val(jzInfo.RYBH);
		$('#cqrybh').val(jzInfo.RYBH);
		$('#cqajbh').val(jzInfo.AJBH);
		$('#glrybh').val(jzInfo.RYBH);
		$('#glajbh').val(jzInfo.AJBH);
        sfzfglAjbh=jzInfo.AJBH;
        sfzfglRybh=jzInfo.RYBH;
		if (jzInfo.xb=="1"){
			$('#dataSex').combobox('setValue', 1);
		}else if (jzInfo.xb=="2"){
			$('#dataSex').combobox('setValue', 2);
		}
		$('#dataCertificateNo').val(jzInfo.zjhm);
		$.messager.show({
			title:'提示',
			msg:"关联执法办案平台数据成功",
			timeout:3000
		});
		$('#jz_orderrequest').dialog('close');
	}
	else{
		$.messager.show({
			title:'提示',
			msg:"该案件没有嫌疑人数据",
			timeout:3000
		});
		$('#jz_orderrequest').dialog('close');
	}
//    $('#dataCertificateTypeId').combobox('setValue', jzInfo.ZJZLID);
}

function jingzongByzjhmCq(){
	var caseLb = $('#jzajlb').combobox('getValue');
	var zjhm= $('#eNo').val();
	loadJZPersonByZJHM(zjhm);
	showDialog('#jz_orderrequest', '执法办案平台');
	$('#jzInfo').datagrid({
//		title:'人身安全检查',
		iconCls: 'icon-datagrid',
		region: 'center',
		width: '100%',
		height: '100%',
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: 'queryJzAjxxByzjhm.do?zjhm='+zjhm+"&type=0",
		queryParams: {
			trefresh: new Date().getTime()
		},
		sortOrder: 'desc',
		remoteSort: false,
		idField: 'id',
		singleSelect: true,
		columns: [[
			{field: 'AJMC', title: '案件名称', width: 180},
			{field: 'AJBH', title: '案件编号', width: 120},
			{field: 'FXSJ', title: '案发时间', width: 60,
				formatter:function(value,rec){
					return value;
				}
			},
			{field: 'FADDXZ', title: '案发地址', width: 140}
		]],
		pagination: true,
		pageList: [10, 20, 40, 50, 100],
		rownumbers: true,
		toolbar: '#toolbar_jzInfo',
		onSelect: function (rowIndex, rowData) {
			loadJZPersonByZJHM(rowData.ajbh,caseLb,zjhm);
		}
	});
}

function  SearchJZInfo(){
	var caseName=$('#jzInfo_name').val();
	var caseLb = $('#jzajlb').combobox('getValue');
	$('#jzInfo').datagrid('load',{caseLb : caseLb,caseName:caseName,trefresh:new Date().getTime()} );
}
function  doClearJZInfo(){
	$('#jzInfo_name').val("");
	$('#jzajlb').combobox('setValue',"");
}

function doClear() {
	$('#e_person').val('');
	$('#e_certificateNo').val('');
	$('#e_status').combobox('setValues', '');
	$('#interrogatearea').combobox('setValues', '');
	$('#e_start_date').datebox('setValue', '');
	$('#e_end_date').datebox('setValue', '');

	$('#e_glaj').combobox('setValues', '');
}

//打开警综框
var sid = "";
function jzEdit2(index) {
	var rows = $("#datagrid").datagrid("getRows");
	var row = rows[index];
    sid = row.id;
	$('#jzRybh').val(row.rybh);
	//showDialog('#jzDialog', '警综信息');
	$("#jzDialog").show();
}

// 保存人员编号
function insertRybh(){
	var rybh = $('#jzRybh').val();
	var data = {'personNo':rybh,'id':sid};
	var jsonInfo = JSON.stringify(data);
	jQuery.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: 'insertRybh.do',
		data: jsonInfo,
		dataType: 'json',
		success: function (data) {
			$('#datagrid').datagrid('load', {
				trefresh: new Date().getTime()
			});
				$('#jzDialog').hide();
				$.messager.alert('提示', '关联成功!');

		},
		error: function (data) {
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});

}


//打开裁决框
function confirmButton(index){
	var row = $('#datagrid').datagrid('getRows')[index];
	$('#choose_form').form("clear");
	$("#confirm_serial_id").val(row.id);
	showDialog('#showConfirmDg', '裁决结果');
}
//保存裁决
function doConformResult(){
	var confirmResult = $('#confirm_result_dg').combobox("getValue");
	if (confirmResult == null || confirmResult == '') {
		$('#showConfirmDg').dialog('close');
		return;
	}
	var serialId = $("#confirm_serial_id").val();
	var entranceInfo = $('#entranceData').serializeObject();
	entranceInfo["id"] = serialId;
	entranceInfo["confirmResult"] = confirmResult;
	var jsonrtinfo = JSON.stringify(entranceInfo);
	$.messager.progress({
		title: '请等待',
		msg: '裁决中...'
	});
	jQuery.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: 'confirm.do',
		data: jsonrtinfo,
		dataType: 'json',
		success: function (data) {
			$.messager.alert(data.title, data.content);
			$.messager.progress('close');
			$('#showConfirmDg').dialog('close');
			$('#datagrid').datagrid('load', {
				areaId:$("#interrogatearea").combobox("getValue"),
				name: $('#e_person').val(),
				certificateNo: $('#e_certificateNo').val(),
				status: $('#e_status').combobox('getValue'),
				start_date: $('#e_start_date').datebox('getValue'),
				end_date: $('#e_end_date').datebox('getValue'),
				trefresh: new Date().getTime()
			});
		},
		error: function (data) {
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});
}
//加载办案场所
function loadInterrogatearea(){
	$('#interrogatearea').combobox({
		url: '../order/comboArea.do',
		valueField: 'id',
		textField: 'name',
		onLoadSuccess: function (data) {
			if(data != null && data.length > 0){
				$('#area_cmb_id').combobox('setValue',data[0].id);
			}
		}
	});
}
// 证件
function loadCertificateType(){
	$('#dataCertificateTypeId').combobox({
		url: '../combobox/certificateTypes.do',
		valueField: 'id',
		textField: 'value',
		onLoadSuccess: function (data) {
			$('#dataCertificateTypeId').combobox('setValue', data[0].id);
		}
	});
}

//base64转换为Blob对象
function b64toBlob(b64Data, contentType, sliceSize) {
	contentType = contentType || '';
	sliceSize = sliceSize || 512;
	var byteCharacters = atob(b64Data);
	var byteArrays = [];
	for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
		var slice = byteCharacters.slice(offset, offset + sliceSize);
		var byteNumbers = new Array(slice.length);
		for (var i = 0; i < slice.length; i++) {
			byteNumbers[i] = slice.charCodeAt(i);
		}
		var byteArray = new Uint8Array(byteNumbers);
		byteArrays.push(byteArray);
	}
	var blob = new Blob(byteArrays, { type: contentType });
	return blob;
}

// 临时出区返回
function tempBack(index) {
	var rowData = $('#datagrid').datagrid('getRows')[index];
	//var rowData = $('#datagrid').datagrid('getRowIndex')[index];

	showDialog('#tempBack','临时出区返回信息');

	$('#tempBackForm').form('load',{
		tbid:rowData.id,
		tbSerialNo:rowData.serialNo,
		tbname:rowData.name,
		tbcertificateNo:rowData.certificateNo,
		tbcuffNo:rowData.cuffNo
	});

	if(rowData.sex==1){
		document.getElementById("tbmale").checked = "checked";
	}else{
		document.getElementById("tbfemale").checked = "checked";
	}
	$('#tbcertificateTypeId').combobox({
		url:'../combobox/certificateTypes.do',
		valueField:'id',
		textField:'value' ,
		onLoadSuccess: function(data){
			$('#tbcertificateTypeId').combobox('setValue', rowData.certificateTypeId);
		}
	});
}


// 临时出区返回保存
function sureBack() {

	var tempBackForm = $('#tempBackForm').serializeObject();
	var entranceData = $('#entranceData').serializeObject();
	entranceData["id"]=tempBackForm["tbid"];
	var jsonrtinfo = JSON.stringify(entranceData);
	$.messager.progress({
		title:'请等待',
		msg:'修改数据中...'
	});
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : 'json',
		url : 'tempback.do' ,
		data : jsonrtinfo,
		success : function(data){

			console.log(data);
			$.messager.alert(data.title, data.content);
			var es=$('#e_status').combobox('getValue');
			$('#datagrid').datagrid('load', {
				name : $('#e_person').val(),
				certificateNo : $('#e_certificateNo').val(),
				status:es,
				start_date : $('#e_start_date').datebox('getValue'),
				end_date : $('#e_end_date').datebox('getValue'),
				trefresh:new Date().getTime()
			});
			$('#tempBack').dialog('close');
			$.messager.progress('close');
			$('#tempBackForm').form("clear");
			$('#entranceData').form("clear");
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});
}

//快速出区
function exitBegin2(index){
	/*$("#jzTr").hide();
	$("#ryTr").hide();
	$("#ajTr").hide();
	$("#wxcajTr").hide();
	$("#yy").val("");*/

	$('#selectJzLableCq').html('<font style="color: red">未关联执法办案平台数据！</font></font>');
	$("#glrybh").val("");
	$('#rybh').val("");
	$('#cqrybh').val("");

	$("#glajbh").val("");
	$('#ajbh').val("");
	$('#cqajbh').val("");
	$("#wxcajTr").hide();
	$("#jzTr").hide();
	$("#ryTr").hide();
	$("#ajTr").hide();
	glyy=true;
	//jingzongByzjhmCq();

	var row = $('#datagrid').datagrid('getRows')[index];
	$('#eNo').val(row.certificateNo);

	if (row.status == 11) {
		$.messager.alert('警告', "抱歉," + row.name + "已经出区！");
	} else {
		phototype = 'exitBegin';
		$("#outPopup").show();
		showNoHideStepDiv("stepOutDiv1", "outStep1", 1);
		showNoHideStepDiv("stepOutDiv2", "outStep2", 0);
		showNoHideStepDiv("stepOutDiv3", "outStep3", 0);
		showNoHideStepDiv("stepOutDiv4", "outStep4", 0);
		$("#dataType").combobox({
			onChange:function(value){
				getFirstReason();
			}
		});
		$("#dataType").combobox('setValue', "");
		$("#direction").combobox('setValue', '');
		$("#confirm_result").combobox('setValue', '');
		
		 $(":radio[name='sfsyjd'][value='0']").prop("checked", "checked");//是否送押解队
		 
		$("#outPhoto").attr("src","image/default.jpg");
		var serialNo = row.serialNo;
		$('#serialNo').combogrid({
			panelWidth: 360,
			mode: 'remote',
			url: '../../common/combogrid/getSuspectSerialNo.do',
			idField: 'serialNo',
			textField: 'name',
			columns: [[{
				field: 'serialNo',
				title: '入区编号',
				width: 135
			}, {
				field: 'name',
				title: '姓名',
				width: 60
			}, {
				field: 'certificateNo',
				title: '证件号码',
				width: 150
			}]],
			onChange: function (newValue, oldValue) {

				var cg = $('#serialNo').combogrid('grid'); // 获取数据表格对象
				var row = cg.datagrid('getSelected'); // 获取选择的行
				$('#eNo').val(row.certificateNo);
				jzyyinfo(row);
				if (row == null) {
					$('#serialNo').combogrid('setValue',"");
				}
				else {
					$('#cuffNumber').val(row.cuffNo);
					$("#outSendUserName").val(row.sendUserName);
					$("#outSendUserId").val(row.sendUserId);
					$("#outAjmc").val(row.ajmc);
					var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoName);
					$("#inphoto").attr("src",e);
					
					 $('#outPersonNo').combogrid('setValue',"");
		                initRybh(row.certificateNo);//加载人员编号
				}
			},
			onLoadSuccess: function (data) {
				$('#serialNo').combogrid('setValue', serialNo);
				$('#serialId').val(row.id);
                outSerialId = row.id;
                $('#outPersonNo').combogrid('setValue',"");
                initRybh(row.certificateNo);//加载人员编号
			}
		});
	}
}
var sfajbh;
var sfrybh;
function jzyyinfo(row){
	/*$("#jzTr").hide();
	$("#ryTr").hide();
	$("#ajTr").hide();*/
	/*glyy = true;*/
	$("#jzTr").show();
	$("#ryTr").show();
	$("#ajTr").show();
	$("#glTr").show();
	glyy = false;
	$("#cqajbh").val("");
	$("#cqrybh").val("");
	// 自动填充案件人员编号
	if(row && row.caseNo && row.rybh){
		$("#cqajbh").val(row.caseNo);
		$("#cqrybh").val(row.rybh);
        sfajbh=row.caseNo;
        sfrybh=row.rybh;
	} else {
		$("#cqajbh").val("");
		$("#cqrybh").val("");
		// 自动填充案件人员编号
		jzFill();
	}
}

function jzFill() {
	var data = {'zjhm' : $('#eNo').val()}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : 'json',
		url : 'jzFillByRybhAndAJbh.do' ,
		data : JSON.stringify(data),
		success : function(data){
			 console.log("dat=======");
			 console.log(data);
			 sfzfglAjbh=data.AJBH;
			sfzfglRybh=data.RYBH;
			 $("#cqajbh").val(data.AJBH);
			 $("#cqrybh").val(data.RYBH);
		}
	});
}


function confirmCheckBoxs(){

	var items = $('#datagrid').datagrid('getSelections');
	var ids = [];
	if(items.length>0){
	    $(items).each(function(){
	        ids.push(this.id);    
	    });
	}else{
		$.messager.alert('提示', "至少选择一个嫌疑人进行裁决！");
		return false;
	}
    $("#ids").val(ids.join(","));
	showDialog('#batchConfirm', '批量裁决');
}

//批量裁决
function batchConfirm(){
	$.messager.progress({
		title:'请等待',
		msg:'修改数据中...'
	});
	console.info($("#ids").val());
	console.info($("#batchConfirm_pl").combobox('getValue'));
	jQuery.ajax({
		type : 'GET',
		contentType : 'application/json',
		dataType : 'json',
		url : 'batchConfirmByIds.do' ,
		data : {
			ids : $("#ids").val(),
	        confirmResult : $("#batchConfirm_pl").combobox('getValue'),
			
		},
		success : function(data){ 
 
			$.messager.alert(data.title, data.content);
			var es=$('#e_status').combobox('getValue');
			$('#datagrid').datagrid('load', {
				name : $('#e_person').val(),
				certificateNo : $('#e_certificateNo').val(),
				status:es,
				start_date : $('#e_start_date').datebox('getValue'),
				end_date : $('#e_end_date').datebox('getValue'),
				trefresh:new Date().getTime()
			});
			$('#batchConfirm').dialog('close');
			$.messager.progress('close'); 
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});
	 
 
}

function tjclButton(index){
	var row = $('#datagrid').datagrid('getRows')[index];
	alert(row.id);
	jQuery.ajax({
		type : 'GET',
		contentType : 'application/json',
		dataType : 'json',
		url : 'updateSftjclByid.do' ,
		data : {
			id : row.id			
		},
		success : function(data){  
			$.messager.alert(data.title, data.content);
			var es=$('#e_status').combobox('getValue');
			$('#datagrid').datagrid('load', {
				name : $('#e_person').val(),
				certificateNo : $('#e_certificateNo').val(),
				status:es,
				start_date : $('#e_start_date').datebox('getValue'),
				end_date : $('#e_end_date').datebox('getValue'),
				trefresh:new Date().getTime()
			});
			$.messager.progress('close'); 
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});
	
}

function showHistoryTimeoutRecodeData(){
	$("#timeoutRecodeDialog").show();
	$('#timeoutRecodeDatagrid').datagrid({
		iconCls: 'icon-datagrid',
		region: 'center',
		width: '100%',
		height: '100%',
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: 'timeoutRecodelist.do',
		queryParams: {
			hour:2,
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField: 'id',
		singleSelect: true,
		frozenColumns: [[{
			field: 'ck',
			checkbox: true
		}, {
			title: 'ID',
			field: 'id',
			width: 80,
			sortable: true,
			hidden: true
		}]],
		columns: [[{
			field: 'name',
			title: '嫌疑人',
			width: 40,
		}, {
			field: 'inUserRealName1',
			title: '民警',
			width: 40
		}, {
			field: 'organization',
			title: '办案单位',
			width: 60
		}, {
			field: 'inTime',
			title: '入区时间',
			width: 70,
			formatter: function (value, rec) {
				return valueToDate(value);
			}
		}, {
			field: 'sxDate',
			title: '首次审讯时间',
			width: 70,
			formatter: function (value, rec) {
				var dateStr = valueToDate(value);
				return dateStr!=''?dateStr:"未在办案中心审讯";
			}
		}]],
		pagination: true,
		pageList: [10, 20, 30, 40, 50, 100],
		rownumbers: true,
        toolbar:'#timeoutRecodetoolbar'
	});
	var p = $('#timeoutRecodeDatagrid').datagrid('getPager');
	$(p).pagination({});
}


