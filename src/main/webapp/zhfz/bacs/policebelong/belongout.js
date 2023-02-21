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
		queryParams:{lockerId:lockId,trefresh:new Date().getTime()},
		url: 'listAllBelongdetByLockerId.do',
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'id',field:'id',width:80,sortable:true,hidden:true},
            {field:'belongingsId',title:'物品暂存ID',hidden:true},
            {field:'serialId',title:'serialId',hidden:true}
		]],
		columns:[[
            {field:'personName',title:'民警姓名',width:40},
            {field:'lockerIds',title:'储物柜编号',width:40,hidden:true},
			{field:'lockerId',title:'储物柜编号',width:70,
				formatter:function(field, rec, index){
					return rec.cabinetGroup+" "+rec.cabinetNo+"号柜";
				}},
			{field:'name',title:'物品名称',width:40},
			{field:'detailCount',title:'数量',width:20,},
			{field:'unit',title:'单位',width:40},
			{field:'saveMethod',title:'保管措施',width:80},
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
						if (isGridButton("pboxopens")) {
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
			var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
			var row = cg.datagrid('getSelected');
			$('#detid').datagrid('reload',
					{
					  enpId : row.policeId,
					  trefresh:new Date().getTime()
					}
			);
			return false;
		}
	});
	//选择唯一id
	$('#serialId').combogrid({    
		panelWidth:220,
	    mode: 'remote',    
	    url: '../../common/combogrid/getPoliceSerialNo.do?',
	    idField: 'policeId',
	    textField: 'name',
		columns: [[
			{field:'name',title:'民警姓名',width:100},
			{field:'certificateNo',title:'民警警号',width:100},
			{field:'policeCuffNo1',title:'卡片编号',width:100,hidden:true},
			{field:'areaId',title:'办案区域id',hidden:true},
			{field:'id',title:'id',hidden:true},
			{field:'caseId',title:'案件id',hidden:true},
			{field:'personId',title:'人员id',hidden:true},
			{field:'policeId',title:'民警入区Id',width:100}
		]],
	    onChange: function(data,date1){
	    	var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
	    	var row = cg.datagrid('getSelected');
	    	lockId =getUrlParam("s_lockerId");
	    	$('#detid').datagrid('load', {
				enpId : row.policeId,
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
	     url = 'editBoxopenout.do?id='+row.policeId;
	     
	 }else{
		 $.messager.alert('提示', '请选择唯一编号!');
	 }
}
//点击开柜填写开柜单(单个领取)
function boxopens(index){
  var rowData = $('#detid').datagrid('getRows')[index];

	showDialog('#openbill_dialog', '填写开柜单');
	$('#openbill_form').form('clear');
	$('#getWay').combobox('setValues', '');
	$('#belongingsId').prop('value', rowData.belongingsId);
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
	var belongingsId = rowData[0].belongingsId;
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
			$('#detid').datagrid('reload',{enpId:row.policeId,trefresh:new Date().getTime()});
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
   /* var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    var enterpriseinfo={"id":null,"serialId":row.id};
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title:'请等待',
        msg:'打印预览中...'
    });
    $('#number').val(9);
    $('#name').val('随身物品登记');
    $('#type').val(1);
    $('#userId').val(0);
    $('#dataId').val(row.id);
    $('#serialNo').val(row.serialNo);
    document.getElementById("lawdocInfo_belonginfo").submit();
    $.messager.progress('close');*/

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
    lawdocInfo["name"]="民警随身物品登记";
    lawdocInfo["type"]=1;
    lawdocInfo["userId"]=0;
    lawdocInfo["dataId"]=row.id;
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    fileUtils.read("/zhfz/lawdocProcess/download.do?number=53&name=民警随身物品登记&type=1&userId=0&policeId="+row.policeId+"&serialId="+row.id);
    $.messager.progress('close');

}
//下载
function securitydown(index){

    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');


     //var rowData = $('#detid').datagrid('getRows');

        var enterpriseinfo={"id":null,"serialId":row.id};
        var json_data = JSON.stringify(enterpriseinfo);
        $.messager.progress({
            title:'请等待',
            msg:'打印预览中...'
        });
        var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
        lawdocInfo["number"]=53;
        lawdocInfo["name"]="民警随身物品登记";
        lawdocInfo["type"]=1;
        lawdocInfo["userId"]=0;
        lawdocInfo["dataId"]=row.id;
        lawdocInfo["serialNo"]=row.serialNo;
        lawdocInfo["serialId"]=row.id;
        lawdocInfo["policeId"]=row.policeId;

        $('#number').val(53);
            $('#name').val('民警随身物品登记');
            $('#type').val(1);
            $('#userId').val(0);
            $('#dataId').val(row.id);
            $('#serialNo').val(row.serialNo);
            $('#serialId').val(row.id);
            $('#policeId').val(row.policeId);

        var lawdocInfojson = JSON.stringify(lawdocInfo);
        document.getElementById("lawdocInfo_belonginfo").submit();

    //fileUtils.read("/zhfz/lawdocProcess/download.do?number=53&name=民警随身物品登记&type=1&userId=0&policeId="+row.policeId+"&serialId="+row.id);
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
		panelWidth:220,
		mode: 'remote',
		url: '../../common/combogrid/getPoliceSerialNo.do?',
		idField: 'policeId',
		textField: 'name',
		columns: [[
			{field:'name',title:'民警姓名',width:100},
			{field:'certificateNo',title:'民警警号',width:100},
			{field:'policeCuffNo1',title:'卡片编号',width:100,hidden:true},
			{field:'areaId',title:'办案区域id',hidden:true},
			{field:'id',title:'id',hidden:true},
			{field:'caseId',title:'案件id',hidden:true},
			{field:'personId',title:'人员id',hidden:true},
			{field:'policeId',title:'民警入区Id',width:100}
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
		printChoose(row.id, row.uuid, row.areaId, 6);
	}else {
		$.message.alert("提示","请选择嫌疑人");
	}
}
//签字
function securitySign(){
	var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
	var row = cg.datagrid('getSelected');
	if(row != null){
		var url ="../../lawdocProcess/downloadBase64.do?number=53&name=民警随身物品登记&type=1&userId=0&policeId="+row.policeId+"&serialId="+row.id;
		startSign(url,row.id);
	}else {
		$.message.alert("提示","请选择嫌疑人");
	}
}
//pdf下载
function PdfDownLoad(){
	var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
	var row = cg.datagrid('getSelected');
	if(row != null){
		downLoadPdf(row.id, row.uuid, row.areaId, 10);
	}else {
		$.messager.alert("提示","请选择嫌疑人");
	}
}