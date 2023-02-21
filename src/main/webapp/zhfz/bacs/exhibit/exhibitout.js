var lockId =null;
$(function(){
	$('#cuffNumber').keydown(function(e){
		if(e.keyCode==13){
			var data = checkRingNo($("#cuffNumber").textbox('getValue'),0);
			readRingNo(data.callbackData.cuffNo);
		}
	});
	lockId =getUrlParam("s_lockerId");
	$('#detid').datagrid({
		title:'随身物品详情',
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
		queryParams:{enpId:-99,trefresh:new Date().getTime()},
		url: 'listAllExhibitdetByLockerId.do',
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'id',field:'id',width:80,sortable:true,hidden:true},
            {field:'exhibitId',title:'物品暂存ID',hidden:true},
            {field:'serialId',title:'serialId',hidden:true}
		]],
		columns:[[
            {field:'personName',title:'嫌疑人姓名',width:40},
            {field:'lockerIds',title:'储物柜编号',width:40,hidden:true},
			{field:'lockerId',title:'储物柜编号',width:70,
				formatter:function(field, rec, index){
					return rec.cabinetGroup+" "+rec.cabinetNo+"号柜";
				}},
			{field:'name',title:'物品名称',width:40},
			{field:'detailCount',title:'数量',width:20,},
			{field:'unit',title:'单位',width:40},
			{field:'description',title:'特征',width:130},
			{field:'isGet',title:'是否已领取',width:40,
				formatter:function(value,rec){
					if('true'==value ||"1"==value){
						return '<font color="gray">已领取</font>'
					}
					else if("0"==value){
						return '<font color="green">未领取</font>'
					}
				}},
	        {field:'id',title:'操作',width:40,
					formatter:function(value, row, index){
						if (isGridButton("eboxopens")) {
							var e ='<span class="spanRow"><a href="#" class="gridopenbox" onclick="boxopens('+index+')">领取</a></span>'
							if(row.isGet=='0'){
								return e;
							}
						}
					}
			}
			]],
		rownumbers:true,
		toolbar:'#areaToolbar'
	});

	var p = $('#detid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
			var row = cg.datagrid('getSelected');
			$('#detid').datagrid('reload',
					{
					  enpId : row.id,
					  trefresh:new Date().getTime()
					}
			);
			return false;
		}
	});
	//选择唯一id
	$('#serialId').combogrid({    
		panelWidth:360,    
	    mode: 'remote',    
	    url: '../../common/combogrid/getSuspectSerialNo.do?type=exhibitout',
	    idField: 'id',    
	    textField: 'name',    
	    columns: [[    
	               	  {field:'name',title:'姓名',width:60},
			          {field:'certificateNo',title:'证件号码',width:150},
			          {field:'policeName1',title:'主办民警',width:70},
			          {field:'policeName2',title:'协办民警',width:70},
			          {field:'serialNo',title:'入区编号',width:135},
			          {field:'areaId',title:'办案区域id',hidden:true},
			          {field:'caseId',title:'案件id',hidden:true},
			          {field:'personId',title:'人员id',hidden:true}
	    ]],
	    onChange: function(data,date1){
	    	var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
	    	var row = cg.datagrid('getSelected');
	    	lockId =getUrlParam("s_lockerId");
	    	$('#detid').datagrid('load', {
				enpId : row.id,
				trefresh:new Date().getTime()
			});
	    }
	}); 
//通过lockId 查询 interrogate_serial_id
	if(lockId !=null && lockId!=''){
		jQuery.ajax({
			type : 'POST',
			url : 'getSerialIdByLockId.do',
			data : {lockId:lockId},
			dataType : 'json',
			success : function(data){
				$('#serialId').combogrid('setValue',data);
			},
			error : function(data){
			}
		});  
	}
	$.messager.progress({
		title:'请等待',
		msg:'正在加载控件...'
	});
	$.messager.progress('close');
	$('#getWay').combobox({
		onChange: function(value){
			if(value==1){
				var rows = $('#detid').datagrid('getRows');
				 if(rows!=null && rows.length>0){
					 $('#getPerson').prop('value',rows[0].personName);
				 }
			}
		}
	});
	$('#fudong').remove();
});

function areadoClear() {
	$('#serialId').combogrid('setValue', '请选择唯一编号');    
}
//---------------------------------------------------------------------------------------------
//点击开柜填写开柜单    全部提取
function boxopen(index){
	
	 var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
	 var row = cg.datagrid('getSelected');
	 
	 if(row!=null &&row!=''){
		 
		 //查看是否有未提取的记录
		 var rows = $('#detid').datagrid('getRows');
		 if(rows==null || rows.length ==0){
			 $.messager.alert('提示', '没有存柜记录!');
			 return false;
		 }
		 var flag=false;
		 for(var i=0;i<rows.length;i++){
			 if(rows[i].isGet=='0'){
				 flag=true;
				 break;
			 }
		 }
		 if(!flag){
			 $.messager.alert('提示', '物品已全部领取!');
			 return false;
		 }
		 showDialog('#openbill_dialog','填写开柜单');
	     $('#openbill_form').form('clear');
	     $('#getWay').combobox('setValues', '');
	     
	     url = 'editBoxopenout.do?id='+row.id;  
	     
	 }else{
//		 $.messager.progress('close');
		 $.messager.alert('提示', '请选择唯一编号!');
	 }
}
//点击开柜填写开柜单(单个领取)
function boxopens(index){
  var rowData = $('#detid').datagrid('getRows')[index];

	showDialog('#openbill_dialog', '填写开柜单');
	$('#openbill_form').form('clear');
	$('#getWay').combobox('setValues', '');
	$('#exhibitId').prop('value', rowData.exhibitId);
	$('#lockerId').prop('value', rowData.lockerId);
	url = 'editBoxopenouts.do?id='+rowData.id;
}

//查看图片
function showImages(){
	$("#showPic_dialog").html("");
	var rowData = $('#detid').datagrid('getRows');
	//alert("rowData"+rowData);
	//alert("rowData.rows.length"+rowData.length);
	if(!rowData||rowData.length==0){
		$.messager.alert('提示', '请扫描手环或者选择专属编号！');
		return;
	}
	var exhibitId = rowData[0].exhibitId;
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'加载数据中...'
	});
	jQuery.ajax({
		type : 'POST',
		url : 'getImages.do',
		data : {exhibitId:exhibitId},
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			for(var i=0;i<data.length;i++){
				var url = '../imageshow.do?path='+ encodeURI(data[i]);
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

// 开柜单保存
function saveOpeninfo(){
	if(!checkForm('#openbill_form')){
		return;
	}
	  
	var entForm = $('#openbill_form');
	var enterpriseinfo = JSON.stringify(entForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'数据处理中...'
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
			var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
	    	var row = cg.datagrid('getSelected');
			$('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
			$('#openbill_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '保存失败!'+data.content);
		}
	});  
}

//台账
function securityPrint(index){
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    var enterpriseinfo={"id":null,"serialId":row.id};
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title:'请等待',
        msg:'打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"]=9;
    lawdocInfo["name"]="涉案物品登记";
    lawdocInfo["type"]=1;
    lawdocInfo["userId"]=0;
    lawdocInfo["dataId"]=row.id;
    var lawdocInfojson = JSON.stringify(lawdocInfo);
	$('#number').val(52);
	$('#name').val('涉案物品登记');
	$('#type').val(1);
	$('#userId').val(0);
	$('#dataId').val(row.id);
	$('#serialNo').val(row.serialNo);
	$('#serialId').val(row.id);
	var lawdocInfojson = JSON.stringify(lawdocInfo);
	//document.getElementById("lawdocInfo_belonginfo").submit();// ********
	fileUtils.read("/zhfz/lawdocProcess/download.do?number=52&name=涉案物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
	$.messager.progress('close');
}

function securityDownLoad(index){
	var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
	var row = cg.datagrid('getSelected');
	var enterpriseinfo={"id":null,"serialId":row.id};
	var json_data = JSON.stringify(enterpriseinfo);
	$.messager.progress({
		title:'请等待',
		msg:'打印预览中...'
	});
	var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
	lawdocInfo["number"]=52;
	lawdocInfo["name"]="涉案物品登记";
	lawdocInfo["type"]=1;
	lawdocInfo["userId"]=0;
	lawdocInfo["dataId"]=row.id;
	var lawdocInfojson = JSON.stringify(lawdocInfo);
	$('#number').val(52);
	$('#name').val('涉案物品登记');
	$('#type').val(1);
	$('#userId').val(0);
	$('#dataId').val(row.id);
	$('#serialNo').val(row.serialNo);
	$('#serialId').val(row.id);
	var lawdocInfojson = JSON.stringify(lawdocInfo);
	document.getElementById("lawdocInfo_belonginfo").submit();// ********
	//fileUtils.read("/zhfz/lawdocProcess/download.do?number=48&name=涉案物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
	$.messager.progress('close');
}
function doSearch(){
	var id = $('#serialId').combogrid('getValue');
	if(id==null || id== '' || id=='请选择唯一编号'){
		$.messager.alert('', '请选择唯一编号!');
		return false;
	}
	$('#detid').datagrid('reload',{enpId:id,trefresh:new Date().getTime()});
}
//---------------------------------------------------------------------------------------------
//取物开柜
function boxoutopen(){
    var cuffNumber=$('#cuffNumber').val();
    // if(cuffNumber==''||cuffNumber==null)
    // {
    //     $.messager.alert('提示', '请扫描嫌疑人手环或者民警卡片!');
    //     return false;
    // }
	var id = $('#serialId').combogrid('getValue');
	if(id==null || id== '' || id=='请选择唯一编号'){
		$.messager.alert('提示', '请选择唯一编号!');
		return false;
	}
	open2();
}
//取物开柜
function open2(){
	var ids = $("#id1").val();
	if(!ids){
		var a =$('#detid').datagrid('getRows')[0];
		if(a){
			ids = a.lockerId;
		}
	}
		jQuery.ajax({
			type : 'GET',
			contentType : 'application/json',
			url :'boxoutopen.do?lockid='+ids,
			dataType : 'json',
			success : function(data){
				$.messager.progress('close');
				$.messager.alert('通知', data.content);
			},
			error : function(data){
				$.messager.alert('错误', '开柜失败（boxoutopen）!');
			}
		});
}

//手环扫描
function readRingNo(number){
	//选择唯一id
	$('#serialId').combogrid({
		panelWidth:360,    
	    mode: 'remote',    
	    url: '../combogrid/getSuspectSerialNo.do?type=exhibitout',
	    idField: 'id',    
	    textField: 'name',    
	    columns: [[    
	        {field:'serialNo',title:'入区编号',width:135},
			          {field:'name',title:'姓名',width:60},
			          {field:'certificateNo',title:'证件号码',width:150},
			          {field:'interrogateAreaId',title:'办案区域id',hidden:true},
			          {field:'caseId',title:'案件id',hidden:true},
			          {field:'personId',title:'案件id',hidden:true}
	    ]]
	}); 
	if(number){
		var cuff = $('#cuffInfo').serializeObject();
		cuff["cuffNo"]=number;
		var jsonrtinfo = JSON.stringify(cuff);
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : '../serial/getSerialByCuffNo.do',
			data : jsonrtinfo,
			dataType : 'json',
			success : function(data){
				var id=data.callbackData?data.callbackData.id:null;
				if(id!=null&&id!=""){
				   $('#cuffNumber').val( data.callbackData.cuffNo);
				   $('#serialId').combogrid('setValue',id);
				}else{
					$.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
					$('#cuffNumber').val('');
				}
			},
			error : function(data){
				$.messager.alert('错误', '未知错误!');
				$('#cuffNumber').val('');
			}
		});  
	}else{
		alert("读卡失败！");
		$('#cuffNumber').val('');
	}
}

//PDF打印
function signPrint(index){
	var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
	var row = cg.datagrid('getSelected');
	if(row != null) {
		printChoose(row.id, row.uuid, row.areaId, 9);
	}else {
		$.messager.alert("提示","请选择嫌疑人");
	}
}
//签字
function securitySign(){
	var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
	var row = cg.datagrid('getSelected');
	if(row != null){
		var url ="../../lawdocProcess/downloadBase64.do?number=48&name=涉案物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
		startSign(url,row.id);
	}else {
		$.messager.alert("提示","请选择嫌疑人");
	}
}
//pdf下载
function PdfDownLoad(){
	var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
	var row = cg.datagrid('getSelected');
	if(row != null){
		downLoadPdf(row.id, row.uuid, row.areaId, 9);
	}else {
		$.messager.alert("提示","请选择嫌疑人");
	}
}