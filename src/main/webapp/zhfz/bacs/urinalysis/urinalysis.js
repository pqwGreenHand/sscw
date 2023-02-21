$(function(){
	$('#urinalysis_grid').datagrid({
		//title:'尿检管理',
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
		url: 'listUrinalysis.do',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		queryParams : {
			trefresh:new Date().getTime()
		},
		frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'id',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'personName',title:'姓名',width:80},
            {field:'sex',title:'性别',width:70
            	,
				formatter:function(value,row,index){
					if(value==0){
						return '未知的性别';
					} 
					if(value==1){
						return '男';
					}
					if(value==2){
						return '女';
					} 
					if(value==9){
						return '未说明的性别';
					}
					return '';
				}
            },
			{field:'certificateNo',title:'证件号码',width:150},
            {field:'crimeType',title:'案(事)件性质',width:150},
            {field:'mainUserName',title:'主办民警',width:100},
            {field:'checkUserName',title:'登记民警',width:100},
            {field:'urinalysisTime',title:'尿检时间',width:150,
            	formatter:function(value,rec){
	        		  if(value!=null&&value!=''){
	        			  return valueToDate(value);
	        		  }else{
	        			  return "";
	        		  }
						
					}},
			{field:'testMethod',title:'检测方法',width:80 },
			{field:'result',title:'检测结果',width:260},
			{field:'zhuangtai',title:'是否尿检',width:70 ,height:100,
				formatter:function(value,row,index){
					if(row.id != null && row.id != 0){
						return '已尿检';
					}else{
						return '<span style="color: red">未尿检</span>';
					}
				}
			},
			{field:'id',title:'操作',width:200,
				formatter:function(value,row,index){
			        var e = '';
			        var d='';
			        var f='';
					var g='';
					var h="";
					var i='';
					var k='';
					var m='';
					if(row.id != null && row.id != 0) {
						if (isGridButton('urinalysisEdit')) {
							e = '<span class="spanRow"><a href="#" class="gridedit" onclick="urinalysisEdit(' + index + ')">修改</a></span>';
						}
						if (isGridButton('urinalysisPhoto')) {
							d = '<span class="spanRow"><a href="#" class="gridpicture" onclick="urinalysisPhoto(' + row.id + ')">照片</a></span>';
						}
						if (isGridButton('inPrintFile')) {
							f = '<span class="spanRow"><a href="#" class="gridprint" onclick="inPrintFile(' + row.id + ')">预览</a></span>';
						}
						if (isGridButton('urinalysisRemove')) {
							g = '<span class="spanRow"><a href="#" class="gridpicture" onclick="urinalysisRemove(' + index + ')">删除</a></span>';
						}
						if (isGridButton('urinalysisSign')) {
							h = '<span class="spanRow"><a href="#" class="gridedit" onclick="urinalysisSign(' + index + ')">签字</a></span>';
						}
						if (isGridButton('urinalysisSignPrint')) {
							i = '<span class="spanRow"><a href="#" class="gridprint" onclick="signPrint(' + index + ')">PDF预览</a></span>';
							k = '<span class="spanRow"><a href="#" class="gridprint" onclick="PdfDownLoad(' + index + ')">PDF下载</a></span>';
						}
					} else{
						m = '<span class="spanRow"><a href="#" class="gridedit" onclick="urinalysisAdd()">尿检</a></span>';
					}
					return e+g+f+i+d+h+k+m;
				}
			}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		//行选择方法，进行条件触发
		onSelect: function(rowIndex, rowData){
		},
		toolbar:'#urinalysisToolbar'
		
	});
	var p = $('#urinalysis_grid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
		}
	});
	loadDrugType();
	$('#fudong').remove();
	$('#interrogatearea').combobox({
        url : '../combobox/comboArea.do',
        valueField : 'id',
        textField : 'name',
        onLoadSuccess : function(data) {
        },
        onChange : function(newValue, oldValue){
        	urinalysisSearch();
		}
    });
});
function initSerialId(serialId){
	$('#serialId').combogrid({
		panelWidth:460,
	    mode: 'remote',    
	    url: '../../common/combogrid/getSuspectSerialNo.do?type=record&tresh='+new Date().getTime(),
	    idField: 'id',
	    textField: 'name',
	    columns: [[    
		  {field:'name',title:'姓名',width:100},
		  {field:'certificateNo',title:'证件号码',width:150},
		  {field:'policeName1',title:'主办民警',width:70},
		  {field:'serialNo',title:'入区编号',width:135},
		  {field:'areaId',title:'办案区域id',hidden:true},
		  {field:'caseId',title:'案件id',hidden:true},
		  {field:'personId',title:'人员id',hidden:true}
	    ]],
		onLoadSuccess:function(data){
			if(!$('#serialId').combogrid('options').required){
				$('#serialId').combogrid({
					required:true
				})
			}
			if(serialId != null && serialId != ""){
				$('#serialId').combogrid("setValue",serialId);
			}
		},
	    onChange: function(data,date1){
	    	
	    }
	});
}

function loadDrugType(){	
	$('#MDMA_Result').combobox({
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
	
	$('#KET_Result').combobox({  
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
	
	$('#MOP_Result').combobox({  
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
	
	$('#MET_Result').combobox({  
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
	
	$('#COC_Result').combobox({  
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
	
	$('#DAMA_Result').combobox({  
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
	$('#OTHER_Result').combobox({
	    url:'result.json',  
	    valueField:'id',  
	    textField:'text'  
	}); 
}


function finduser1() {
	var userNo = $('#checkUserNo').val();
	if(userNo==null||userNo.length==0){
		return;
	}
	if(userNo.indexOf('(') >= 0){
		userNo = userNo.substring(0,userNo.indexOf('('));
	}
	var userNoInfo = $('#userNoInfo').serializeObject();
	userNoInfo["userNo"] = userNo;
	var jsonrtinfo = JSON.stringify(userNoInfo);
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		dataType : 'json',
		url : '../order/searchUser.do',
		data : jsonrtinfo,
		success : function(data) {
			if (data != null && data.id != null) {
				$("#checkUserNo").val(data.certificateNo+"("+data.realName+")")
			} else {
				$.messager.alert('错误', userNo + '该警号不存在!');
				$("#checkUserNo").val('');
			}
		},
		error : function(data) {
			$.messager.alert('错误', '警号不存在！');
			$("#checkUserNo").val('');
		}
	});
}
function cleanUrinalysis(){
	$('#urinalysis_form1').form('clear');
	$('#urinalysis_form2').form('clear');
}

function urinalysisAdd(){
	$("#urinalysis_dialog").show();
	$("#step_div1").show();
	$("#step_div2").hide();
	$('#serialId').combogrid({
		required:false
	});
	$('#urinalysis_form1').form('clear');
	$('#urinalysis_form2').form('clear');
	$("#id").val("");
	initSerialId();
	$.get("../../../common/getSessionInfo.do", function(data){
		var sessionObj = eval('('+data+')');
		var user = sessionObj.user;
		$("#checkUserNo").val(user.certificateNo +"("+ user.realName+')');
	});
	$("#cuffNumber").focus();
}

function urinalysisEdit(index){
	var row = $('#urinalysis_grid').datagrid('getRows')[index];
	$("#urinalysis_dialog").show();
	$("#step_div1").show();
	$("#step_div2").hide();
    $('#urinalysis_form').form('clear');
    $('#urinalysis_form1').form('load',row);
	initSerialId(row.serialId);
    //清空所有勾选的，选择的
    $("#is_MDMA").attr("checked",false);
	$("#is_KET").attr("checked",false);
	$("#is_MOP").attr("checked",false);
	$("#is_MET").attr("checked",false);
	$("#is_COC").attr("checked",false);
	$("#is_DAMA").attr("checked",false);
	$("#is_OTHER").attr("checked",false);
	$("#MDMA_Result").combobox("setText",'');
	$("#KET_Result").combobox("setText",'');
	$("#MOP_Result").combobox("setText",'');
	$("#MET_Result").combobox("setText",'');
	$("#COC_Result").combobox("setText",'');
	$("#DAMA_Result").combobox("setText",'');
	$("#OTHER_Result").combobox("setText",'');
    var result=row.result.split(';');
    if(result.length>0){
    	for(var key in result){
    		var values=result[key].split("呈");
    		switch(values[0]){
    		  case "MDMA（摇头丸）":
    			  $("#MDMA_Result").combobox("setText",values[1]);
    			  $("#is_MDMA").attr("checked",true);
    		      $("#is_MDMA").prop("checked",true);
    			  break;
    		  case "KET（氯胺酮）":
    			  $("#KET_Result").combobox("setText",values[1]);
    			  $("#is_KET").attr("checked",true);
    		      $("#is_KET").prop("checked",true);
    		  break;
    		  case "MOP（吗啡）":
    			  $("#MOP_Result").combobox("setText",values[1]);
    			  $("#is_MOP").attr("checked",true);
    		      $("#is_MOP").prop("checked",true);
    			  break;
    		  case "MET（甲基苯丙胺）":
    			  $("#MET_Result").combobox("setText",values[1]);
    			  $("#is_MET").attr("checked",true);
    		      $("#is_MET").prop("checked",true);
    			  break;
    		  case "COC（可卡因）":
    			  $("#COC_Result").combobox("setText",values[1]);
    			  $("#is_COC").attr("checked",true);
    		      $("#is_COC").prop("checked",true);
    			  break;
    		  case "大麻":
    			  $("#DAMA_Result").combobox("setText",values[1]);
    			  $("#is_DAMA").attr("checked",true);
    		      $("#is_DAMA").prop("checked",true);
    			  break;
    		  case "其它":
    			  $("#OTHER_Result").combobox("setText",values[1]);
    			  $("#is_OTHER").attr("checked",true);
    		      $("#is_OTHER").prop("checked",true);
    			  break;
    		  
    		}
    	}
    }
	$('#id').attr('readonly','readonly');
	$('#id').addClass('readonlyCss');
	showdiv("#step_div1");
    hiddendiv("#step_div2");
	$("#step1").addClass("now");
	$("#step2").removeClass("now");
}
//保存尿检
function saveUrinalysis(type){
	var urinalysisForm = $('#urinalysis_form1').serializeObject();
	var result="";
	if($("#is_MDMA").is(':checked')){
		result=result+"MDMA（摇头丸）呈"+$("#MDMA_Result").combobox("getText")+";";
	}
	if($("#is_KET").is(':checked')){
		result=result+"KET（氯胺酮）呈"+$("#KET_Result").combobox("getText")+";";
	}
	if($("#is_MOP").is(':checked')){
		result=result+"MOP（吗啡）呈"+$("#MOP_Result").combobox("getText")+";";
	}
	if($("#is_MET").is(':checked')){
		result=result+"MET（甲基苯丙胺）呈"+$("#MET_Result").combobox("getText")+";";
	}
	if($("#is_COC").is(':checked')){
		result=result+"COC（可卡因）呈"+$("#COC_Result").combobox("getText")+";";
	}
	if($("#is_DAMA").is(':checked')){
		result=result+"大麻呈"+$("#DAMA_Result").combobox("getText")+";";
	}
	if($("#is_OTHER").is(':checked')){
		result=result+"其它呈"+$("#OTHER_Result").combobox("getText")+";";
	}
	urinalysisForm["result"]=result;
	var userNo = urinalysisForm["checkUserNo"];
	if(userNo != null && userNo.indexOf('(') >= 0){
		userNo = userNo.substring(0,userNo.indexOf('('));
	}
	urinalysisForm['checkUserNo'] = userNo;
	var url ="";
	if(type == 'add'){
		url = "addUrinalysis.do";
	}else if(type == 'edit'){
		url = "editUrinalysis.do";
	}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : JSON.stringify(urinalysisForm),
		dataType : 'json',
		success : function(data){
			$.messager.show({
				title:'提示',
				msg:data.content,
				timeout:3000
			});
			$.messager.progress('close');
			if(!data.error){
				$('#urinalysis_grid').datagrid('reload');
				if(data.callbackData != null && data.callbackData != ""){
					$("#id").val(data.callbackData);
				}
				$("#step1").addClass("active");
				$("#step1").removeClass("now");
				$("#step2").addClass("now");
				$("#photoList").html("");
				loadPhotoList();
				initSwf();
				upload_swfupload_load();
			}
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	});
}

function caseNext(index){
	if($('#urinalysis_form1').form('validate')){
		if($("#checkUserNo").val()==''||$("#checkUserNo").val()==null){
			$.messager.alert('提示', '请填写民警编号!');
			return;
		}
		if(!$("#is_MDMA").is(':checked')&&
			!$("#is_KET").is(':checked')&&
			!$("#is_MOP").is(':checked')&&
			!$("#is_MET").is(':checked')&&
			!$("#is_COC").is(':checked')&&
			!$("#is_DAMA").is(':checked')&&
			!$("#is_OTHER").is(':checked')
			){
			$.messager.alert('提示', '请选择毒品种类及检测结果!');
			return;			
		}
		if(  ($("#is_MDMA").is(':checked')&&$("#MDMA_Result").combobox("getText")=="")
			||($("#is_KET").is(':checked')&&$("#KET_Result").combobox("getText")=="")
			||($("#is_MOP").is(':checked')&&$("#MOP_Result").combobox("getText")=="")
			||($("#is_MET").is(':checked')&&$("#MET_Result").combobox("getText")=="")
			||($("#is_COC").is(':checked')&&$("#COC_Result").combobox("getText")=="")
			||($("#is_DAMA").is(':checked')&&$("#DAMA_Result").combobox("getText")=="")
			||($("#is_OTHER").is(':checked')&&$("#OTHER_Result").combobox("getText")=="")
			){
			$.messager.alert('提示', '请选择已勾选毒品种类的检测结果!');
			return;		
		}
		showdiv("#step_div2");
		hiddendiv("#step_div1");
		if($("#id").val() == null ||$("#id").val() == "" ){
			saveUrinalysis("add");
		}else{
			saveUrinalysis("edit");
		}
	}else{
		$.messager.alert('警告', '信息填写不完整!');
	}
}

function securityLast(index){
	showdiv("#step_div1");
	hiddendiv("#step_div2");
	$("#step1").addClass("now");
	$("#step1").removeClass("active");
	$("#step2").removeClass("now");
}


function urinalysisSaveData(){
	var canvas = document.getElementById("canvas");
	var str=canvas.toDataURL("image/png");
	var len=str.length;
	if (len > 5000) {
		var urinalysisId = $("#id").val();
		var row = $('#urinalysis_grid').datagrid('getSelected');
		$.messager.confirm('警告', '是否确定提交?', function (r) {
			if (r) {
				if (urinalysisId == null || urinalysisId == "") {
					if (row == null || row.id == null) {
						$.messager.alert('Error', '请刷新页面');
						return;
					} else {
						urinalysisId = row.id;
					}
				}
				var _blob = dataURLtoBlob(str);
				var formData = new FormData();
				formData.append("file", _blob, 'a.png');
				formData.append("urinalysisId", urinalysisId);
				console.log(formData);
				jQuery.ajax({
					type: 'POST',
					url: "addUrinalysisPhoto.do",
					data: formData,
					contentType: false, // 注意这里应设为false
					processData: false,
					cache: false,
					dataType: 'json',
					success: function (data) {
						$.messager.show({
							title: '提示',
							msg: data.content,
							timeout: 3000
						});
						$.messager.progress('close');
						loadPhotoList();
						$('#photo_dialog').dialog('close');
					},
					error: function (data) {
						$.messager.progress('close');
						$.messager.alert('错误', '未知错误!');
					}
				});
			}
		})
	}else{
		$.messager.alert('提示', '请先点击允许再点击拍照！');
	}
}
//删除尿检
function urinalysisRemove(index){
	var row = $('#urinalysis_grid').datagrid('getRows')[index];
    if (row){
        $.messager.confirm('警告','是否删除该数据?',function(r){
            if (r){
            	$.messager.progress({
           	   	 title:'请等待',
           	   	 msg:'删除数据中...'
           	    });
            	jQuery.ajax({
        			type : 'POST',
        			url : 'deleteUrinalysis.do',
        			data :{
        				'urinalysisId':row.id
					},
        			dataType : 'json',
        			success : function(data){
        				$.messager.progress('close');
        				$.messager.show({
        					title:'提示',
        					msg:data.content,
        					timeout:3000
        				});
        				$('#urinalysis_grid').datagrid('reload');// reload the data
        			},
        			error : function(data){
        				$.messager.progress('close');
        				$.messager.alert('Error', 'Unknown Error!');
        			}
        		});

            }
        });
    } else{
		$.messager.alert('Message', '请选择要删除数据!');
	}
}

function urinalysisClear(){
	$('#s_name').textbox('setValue','');
	$('#s_no').textbox('setValue','');
	$('#interrogatearea').combobox('setValue','');
	$('#start').datetimebox('setValue','');
	$('#end').datetimebox('setValue','');
}

function urinalysisSearch(){

	$('#urinalysis_grid').datagrid('load', {
		name : $('#s_name').textbox('getValue'),
		no : $('#s_no').textbox('getValue'),
		areaid:$('#interrogatearea').combobox('getValue'),
		start:$('#start').datetimebox('getValue'),
		end:$('#end').datetimebox('getValue'),
		tfresh:new Date().getTime()
	});
}

//绑定手环回车事件
$(function(){
	$('#cuffNumber').keydown(function(event){ 
		if(event.keyCode == 13){
			checkRing($("#cuffNumber").val());
		}
	}); 
});
//手环校验
function checkRing(icNo){
	var checkData = checkRingNo(icNo,0);
	console.log(checkData);
	if(checkData.isError){
		$.messager.alert("提示",checkData.callbackData);
		return;
	}else{
		if(checkData.callbackData.status == 0 ||checkData.callbackData.status == 3){
			$.messager.alert("手环未绑定或者已临时出区");
			$("#cuffNumber").val("");
			return;
		}
		if(checkData.callbackData.status == 1){
			$("#cuffNumber").val(checkData.callbackData.cuffNo);
			readRingNo(checkData.callbackData.cuffNo);
		}
	}
}
//手环扫描
function readRingNo(number){
	//选择唯一id
	$('#serialId').combogrid({
		panelWidth:360,    
	    mode: 'remote',    
	    url: contextPath+'/zhfz/common/combogrid/getSuspectSerialNo.do?type=record',
	    idField: 'id',    
	    textField: 'name',
		required:"true",
	    columns: [[    
		  {field:'name',title:'姓名',width:60},
		  {field:'certificateNo',title:'证件号码',width:150},
		  {field:'policeName1',title:'主办民警',width:70},
		  {field:'policeName2',title:'协办民警',width:70},
		  {field:'serialNo',title:'入区编号',width:135},
		  {field:'areaId',title:'办案区域id',hidden:true},
		  {field:'caseId',title:'案件id',hidden:true},
		  {field:'personId',title:'人员id',hidden:true}
	    ]]
	});
	if(number){
		var cuff = $('#cuffInfo').serializeObject();
		cuff["cuffNo"]=number;
		cuff["type"]=0;
		var jsonrtinfo = JSON.stringify(cuff);
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : contextPath+'/zhfz/bacs/serial/getSerialByCuffNo.do',
			data : jsonrtinfo,
			dataType : 'json',
			success : function(data){
				var id=data.callbackData?data.callbackData.id:null;
				if(id!=null&&id!=""){
				   $('#cuffNumber').val( data.callbackData.cuffNo);
				   $('#serialId').combogrid('setValue',id);
					$.ajax({
						async: false,
						type : 'POST',
						url :'getCurrentStatus.do',
						dataType : 'json',
						data : {
							serialId : id
						},
						success : function(dataresult) {
							if (dataresult.content=="已安检") {
							}
							else{
								if(dataresult.content=="未安检"){
									$.messager.alert('警告', '请先录入嫌疑人的人身安全检查信息!');
									$('#cuffNumber').val('');
								}
							}
						},
						error : function() {
							$.messager.alert('警告', '请先录入嫌疑人的人身安全检查信息!');
							$('#cuffNumber').val('');
						}
					});
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
	}
}
//台账
function inPrintFile(id){
//	$('#urinalysis_grid').datagrid("selectRow",index);
//	$.messager.progress({
//	   	 title:'请等待',
//	   	 msg:'打印预览中...'
//	    });
	$('#id_ef').val(id);
//	document.getElementById("exprortForm").submit();
	    fileUtils.read("/zhfz/bacs/urinalysis/download.do?id="+id);
	$.messager.progress('close');

}
/* 拍照  ************/
var isPhoto=0;
function photoScan(){
	isPhoto=1;
	_register();
	webcam.capture();
	changeFilter();
	void(0);
}
function openPhotoDialog(){
    showDialog('#photo_dialog','拍照');
	$('#urinalysis_form2').form('clear');
}
var pos = 0;
var ctx = null;
var cam = null;
var image = null;

var filter_on = false;
var filter_id = 0;

function changeFilter() {
	 if (filter_on) {
		 filter_id = (filter_id + 1) & 7;
	 }
}

function toggleFilter(obj) {
	if (filter_on =!filter_on) {
	 obj.parentNode.style.borderColor = "#c00";
	} else {
	 obj.parentNode.style.borderColor = "#333";
	}
}


$(function(){
	//摄像头
	jQuery("#webcam").webcam({

	 width: 240,
	 height: 180,
	 mode: "callback",
	 swffile: "jscam_canvas_only.swf",
	 onSave: function(data) {
		 var col = data.split(";");
		 var img = image;
		 if (false == filter_on) {
			 for(var i = 0; i < 240; i++) {
				 var tmp = parseInt(col[i]);
				 img.data[pos + 0] = (tmp >> 16) & 0xff;
				 img.data[pos + 1] = (tmp >> 8) & 0xff;
				 img.data[pos + 2] = tmp & 0xff;
				 img.data[pos + 3] = 0xff;
				 pos+= 4;
			 }
		 } else {
			 var id = filter_id;
			 var r,g,b;
			 var r1 = Math.floor(Math.random() * 255);
			 var r2 = Math.floor(Math.random() * 255);
			 var r3 = Math.floor(Math.random() * 255);

			 for(var i = 0; i < 240; i++) {
				 var tmp = parseInt(col[i]);

 /* Copied some xcolor methods here to be faster than calling all methods inside of xcolor and to not serve complete library with every req */

			 if (id == 0) {
				 r = (tmp >> 16) & 0xff;
				 g = 0xff;
				 b = 0xff;
				 } else if (id == 1) {
				 r = 0xff;
				 g = (tmp >> 8) & 0xff;
				 b = 0xff;
				 } else if (id == 2) {
				 r = 0xff;
				 g = 0xff;
				 b = tmp & 0xff;
				 } else if (id == 3) {
				 r = 0xff ^ ((tmp >> 16) & 0xff);
				 g = 0xff ^ ((tmp >> 8) & 0xff);
				 b = 0xff ^ (tmp & 0xff);
				 } else if (id == 4) {
				
				 r = (tmp >> 16) & 0xff;
				 g = (tmp >> 8) & 0xff;
				 b = tmp & 0xff;
				 var v = Math.min(Math.floor(.35 + 13 * (r + g + b) / 60), 255);
				 r = v;
				 g = v;
				 b = v;
			 } else if (id == 5) {
				 r = (tmp >> 16) & 0xff;
				 g = (tmp >> 8) & 0xff;
				 b = tmp & 0xff;
				 if ((r+= 32) < 0) r = 0;
				 if ((g+= 32) < 0) g = 0;
				 if ((b+= 32) < 0) b = 0;
			 } else if (id == 6) {
				 r = (tmp >> 16) & 0xff;
				 g = (tmp >> 8) & 0xff;
				 b = tmp & 0xff;
				 if ((r-= 32) < 0) r = 0;
				 if ((g-= 32) < 0) g = 0;
				 if ((b-= 32) < 0) b = 0;
				 } else if (id == 7) {
				 r = (tmp >> 16) & 0xff;
				 g = (tmp >> 8) & 0xff;
				 b = tmp & 0xff;
				 r = Math.floor(r / 255 * r1);
				 g = Math.floor(g / 255 * r2);
				 b = Math.floor(b / 255 * r3);
			 }

			 img.data[pos + 0] = r;
			 img.data[pos + 1] = g;
			 img.data[pos + 2] = b;
			 img.data[pos + 3] = 0xff;
			 pos+= 4;
		}
		}

		 if (pos >= 0x2A300) {
		 ctx.putImageData(img, 0, 0);
		 pos = 0;
		 }
		 },
		 onCapture: function () {
			 webcam.save();
			 jQuery("#flash").css("display", "block");
			 jQuery("#flash").fadeOut(100, function () {
				 jQuery("#flash").css("opacity", 1);
			 });
		 },
	});
});

function initSwf() {
	 jQuery("body").append("<div id=\"flash\"></div>");
	 var canvas = document.getElementById("canvas");
	 if (canvas.getContext) {
		 ctx = document.getElementById("canvas").getContext("2d");
		 ctx.clearRect(0, 0, 240, 180);
		 var img = new Image();
		 img.src = "image/logo.gif";
		 img.onload = function() {
		 ctx.drawImage(img, 129, 89);
	 }
	 image = ctx.getImageData(0, 0, 240, 180);
	 }
	 jQuery("#flash").css({ height: "150px" });
}
var fileName = new Array();
var fileIndex =0;
var upload_swfupload = {SWFObj:new Object()};
function upload_swfupload_load(){
	var loadSettings = {
	//提交路径
	upload_url : "upload.do",
	//向后台传递额外的参数
	post_params : {
        urinalysisId:$("#id").val(),
		small : false,
		sw:120,
		sh:120,
		width : 800,
		height: 800,
		inputResult : window.location.href
	},
	file_size_limit : "1024000",
	file_types_description : "图片",
	file_types : "*.jpg;*.jpeg;*.gif;*.png;",
	file_upload_limit : "0",
	file_queue_limit : "0",
	prevent_swf_caching :true,
	// 按钮的处理
	button_image_url : "../../../swfUpload/images/background_c.png",
	button_placeholder_id : "spanButtonPlaceholder1_swfupload",
	button_width : 125,
	button_height : 50,
    upload_success_handler:myUploadSuccess,
	upload_start_handler:myUploadStart,
	auto: false, //选完文件后是否自动上传
	flash_url : "../../../swfUpload/new/swfupload.swf",

	custom_settings : {
		file_post_name : "filedata",
		cancelButtonId : "btnCancel1_swfupload",
		fileSiteName : "",
		fileTypeName : ""
	}
}
	SWFLoad(upload_swfupload,loadSettings);
}
//上传前调用
function myUploadStart(){
	$.messager.progress({
		title: '请等待',
		msg: '图片上传中...'
	});
}
function myUploadSuccess(fileObj,server_data) {
	$.messager.progress('close');
	fileName[fileIndex]=server_data;
    fileIndex = fileIndex + 1;
	uploadSuccess(fileObj,server_data,"/ictpm",this);
	$.messager.show({
		title:"提示",
		msg:"上传成功",
		timeout:3000
	});
	loadPhotoList();
}
//加载尿检图片List
function loadPhotoList(){
	var urinalysisId = $("#id").val();
    $.ajax({
        async: false,
        type : 'POST',
        url :'getPhotoList.do',
        dataType : 'json',
        data : {
            urinalysisId : urinalysisId,
			trefsh:new Date().getTime()
        },
        success : function(data) {
        	console.log(data);
            var imgStr = "";
            if(data != null && data.length >0){
            	for (var i=0;i<data.length;i++){
					imgStr += '<div className="imgDiv" style="float: left;margin-left: 25px;margin-top: 10px;">';
					var e = fileUtils.url("ba","nj",data[i].uuid,data[i].areaId,data[i].url);
					imgStr += '<img src="'+e+'" onMouseOver="onBigImage(event,this)" onMouseOut="mvBigImage()" style=""';
					imgStr += 'height="80px";width="100px">';
					imgStr += '<a name="noButton" className="close" style="cursor: pointer;margin-left:80px;margin-top: -80px;background-image:url(image/popup-1.png);width: 30px;height: 30px;display: inline-block;" onclick="deletdPhoto('+data[i].id+')"></a>'
					imgStr += '</div>';
				}
            }
            $("#photoList").html(imgStr);
        },
        error : function() {
        }
    });
}
function deletdPhoto(photoId){
	$.messager.confirm('警告','是否删除该图片?',function(r){
		if(r){
			$.messager.progress({
				title:'请等待',
				msg:'添加/修改数据中...'
			});
			$.ajax({
				async: false,
				type : 'POST',
				url :'deletePhotoByPhotoId.do',
				dataType : 'json',
				data : {
					photoId : photoId
				},
				success : function(data) {
					$.messager.show({
						title:data.title,
						msg:data.content,
						timeout:3000
					});
					$.messager.progress('close');
					loadPhotoList();
				},
				error : function(data) {
					$.messager.alert(data.content);
				}
			});
		}
	})
}
function urinalysisEnd(){
	var imgDiv = $("#photoList").html();
	var urinalysisId =$("#id").val();
	var row = $('#urinalysis_grid').datagrid('getSelected');
	if(urinalysisId == null || urinalysisId == ""){
		if(row == null || row.id == null){
			$('#urinalysis_dialog').hide();
			$("#fastDiv").show();
			return;
		}
	}
	if(imgDiv == ''){
		$.messager.alert('警告', '尿检照片未上传!');
	}
	$('#urinalysis_dialog').hide();
	$("#fastDiv").show();
}
//显示照片信息
function urinalysisPhoto(serialid){
	$("#step1").removeClass("active");
	$("#step1").removeClass("now");
	$("#step2").addClass("now");
	$("#urinalysis_dialog").show();
	$("#step_div1").hide();
	$("#step_div2").show();
	$("#fastDiv").hide();
	$("#id").val(serialid);
	loadPhotoList();
	initSwf();
	upload_swfupload_load();
}

function dataURLtoBlob(dataurl) {
	var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
		bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
	while (n--) {
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], { type: mime });
}

//PDF打印
function signPrint(index){
	var row = $('#urinalysis_grid').datagrid('getRows')[index];
	console.log(row);
	printChoose(row.serialId,row.serialUUID,row.areaId,4);
}
//签字
function urinalysisSign(index) {
	var row = $('#urinalysis_grid').datagrid('getRows')[index];
	console.log(row);
	var url = "../../bacs/urinalysis/downloadBase64.do?id="+row.id;
	startSign(url,row.serialId);
}

//pdf下载
function PdfDownLoad(index){
	var row = $('#urinalysis_grid').datagrid('getRows')[index];
	downLoadPdf(row.serialId, row.serialUUID, row.areaId, 4);
}