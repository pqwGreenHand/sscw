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
		// sortName : 'serialId',
		// sortOrder : 'desc',
		// remoteSort : false,
		// idField : 'serialId',
		singleSelect : true,
		frozenColumns : [ [

		{
			title : 'ID',
			field : 'serialId',
			width : 80,
			sortable : true,
			hidden : true
		} ] ],
		columns : [ [{
			field : 'name',
			title : '姓名',
			width : 100,
			align : 'center'
		}, {
			field : 'sex',
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
			field : 'certificate_no',
			title : '证件号码',
			width : 200,
			align : 'center'
		}, {
			field : 'ajmc',
			title : '案件名称',
			width : 300,
			align : 'center'
		},
		// 0刑事 1行政
		{
			field : 'ajlx',
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
			field : 'abmc',
			title : '主案别',
			width : 200,
			align : 'center'
		}
		] ],
		pagination : true,
		pageList : [ 10, 20, 30, 40, 50, 100 ],
		// pageList: [20, 40, 60, 80, 100],
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
		name : $('#personName').val(),
		sex : $("#personSex").combobox('getValue'),
		ajlx : $('#caseType').combobox('getValue'),
		abmc : $('#caseProperties').val(),
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

	$('#outBetweenTime').datetimebox('clear');
	$('#outBetweenTimeMax').datetimebox('clear');
}

function getDatetime(data) {
	var dt = new Date(data);
	return (dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate()
			+ ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt
			.getSeconds()).replace(/([\-\: ])(\d{1})(?!\d)/g, '$10$2');
}

function exportData() {
	showDialog('#exportExcel_dialog', '选择列名');
	// 设置默认选项
	// 姓名，性别，案件类型，入区时间，出区时间，出区去向
	$('#workbetweenTime1').val('');
}

function wsexport() {
	var name = $('#personName').val();
	var sex = $("#personSex").combobox('getValue');
	var ajlx = $('#caseType').combobox('getValue');
	var abmc = $('#caseProperties').val();
	var trefresh = new Date().getTime();
	window.location = "../../export/exportZip.do?name=" +encodeURIComponent(name) + "&sex=" + encodeURIComponent(sex) + "&ajlx="+ encodeURIComponent(ajlx)+ "&abmc="+encodeURIComponent(abmc);
}

// 交班记录
function exportData1() {
	showDialog('#exportExcel_dialog', '选择列名');
	// 设置默认选项
	var currentTime = CurentTime();
	$('#betweenTime').datetimebox('clear');
	$('#betweenTimeMax').datetimebox('clear');
	$('#workbetweenTime1').val(currentTime);
	// 姓名，性别，案件类型，入区时间，出区时间，出区去向
}

// 看押记录
function exportData2() {
	var currentTime = CurentTime();
	//alert(currentTime);
	showDialog('#exportExce2_dialog', '选择时间段');
	// 设置默认选项
//	$('#kyBeginTime').datebox('clear');
//	$('#kyEndTime').datebox('clear');
	//$('#kyEndTime').datebox('setValue', currentTime);
	// 姓名，性别，案件类型，入区时间，出区时间，出区去向
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

	var outBetweenTime = $('#outBetweenTime').datebox('getValue');
	var outBetweenTimeMax = $('#outBetweenTimeMax').datebox('getValue');
	var value = personName + "@" + personType + "@" + personSex + "@"
			+ caseType + "@" + caseProperties + "@" + betweenTime + "@"
			+ betweenTimeMax+ "@" + outBetweenTime + "@" + outBetweenTimeMax;
	// alert(value);
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
	$('#outBetweenTime1').val(outBetweenTime);
	$('#outBetweenTimeMax1').val(outBetweenTimeMax);

	// 2请求后台
	document.getElementById("exportForm").submit();
	$.messager.progress('close');
	// 3打印表格

}

// 提交看押时间
function savekyData() {
	// 获取时间段
	var dataTimebegin = $('#kyBeginTimeData').datebox('getValue');
	var dataTimeend = $('#kyEndTimeData').datebox('getValue');
	if(dataTimebegin !="" &&dataTimebegin !=null && dataTimeend !="" && dataTimeend!=null){
		//alert("======看押记录======"+dataTimebegin+"============"+dataTimeend);
		
		if(isDate(dataTimebegin) && isDate(dataTimeend)){
			$('#kyBeginTime').val(dataTimebegin);
			$('#kyEndTime').val(dataTimeend);
			var result = compareTime(dataTimebegin,dataTimeend,"开始时间必须小于结束时间");
			if(result){
				$.messager.progress({
					title : '请等待',
					msg : '下载中...'
				});
				// 2请求后台并且下载表格
				document.getElementById("exportEscortForm").submit();
				$.messager.progress('close');		
			}	
		};
	}
	else{
		alert("请补全信息！");	
	}
	
}

function CurentTime() {
	var now = new Date();
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var hh = now.getHours(); // 时
	var mm = now.getMinutes(); // 分
	var ss = now.getSeconds();
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month + "-";
	if (day < 10)
		clock += "0";
	clock += day + " ";
	if (hh < 10)
		clock += "0";
	clock += hh + ":";
	if (mm < 10)
		clock += '0';
	clock += mm;
	clock += ":";
	if (ss < 10)
		clock += '0';
	clock += ss;
	return (clock);
}

function compareTime(startdate, enddate,info) {
    var arr = startdate.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();
    var arrs = enddate.split("-");
    var endtime = new Date(arrs[0], arrs[1], arrs[2]);
    var endimes = endtime.getTime();
    if (starttimes > endimes) {
       alert(info);
        return false;
    }else
        return true;    
}

function   isDate(strDate){ 
    var   strSeparator = "-";   //日期分隔符 
    var   strDateArray; 
    var   intYear; 
    var   intMonth; 
    var   intDay; 
    var   boolLeapYear; 
    //var strDate=form1.a.value   //表单中的日期值
    strDateArray = strDate.split(strSeparator); 
     
    if(strDateArray.length!=3)    {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   }
     
    intYear = parseInt(strDateArray[0],10); 
    intMonth = parseInt(strDateArray[1],10); 
    intDay   =   parseInt(strDateArray[2],10); 
     
    if(isNaN(intYear)||isNaN(intMonth)||isNaN(intDay))   { alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false; }
     
    if(intMonth>12||intMonth<1) {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   } 
     
    if((intMonth==1||intMonth==3||intMonth==5||intMonth==7||intMonth==8||intMonth==10||intMonth==12)&&(intDay>31||intDay<1))   {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   } 
     
    if((intMonth==4||intMonth==6||intMonth==9||intMonth==11)&&(intDay>30||intDay<1))   {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   }
     
    if(intMonth==2){ 
          if(intDay<1)   {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   }
           
          boolLeapYear   =   false;  
        if((intYear%4==0 && intYear %100!=0)||(intYear %400==0))
{
boolLeapYear=true;
}
           
          if(boolLeapYear){ 
                if(intDay>29) {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   }
          } 
          else{ 
                if(intDay>28) {   alert('提示: 日期格式错误! \r\n 请依【YYYY-MM-DD】格式输入！'); return   false;   }
          } 
    } 
     
    return   true; 
}

//出区原因下拉
/*
违法犯罪嫌疑人去向：
0、符合调解相关规定调解后离开
1、排除嫌疑离开
2、证据不足暂时离开待查
3、拟立为行政案件离开待查
4、拟立为刑事案件离开待查
5、送拘留所执行行政拘留
6、送收容所执行收容教育
7、送戒毒所执行强制戒毒
8、送看守所执行刑事拘留
9、取保候审手续办理完毕离开
10、监视居住手续办理完毕离开
11、行政拘留转取保候审后离开
12、刑事拘留转监视居住后离开 
*/
var firstDirection=[
["送拘留所执行行政拘留","送看守所执行刑事拘留","行政拘留不执行","未达到法定年龄不予以处理","行政拘留暂停执行","排除嫌疑离开","符合调解相关规定调解后离开","送收容所执行收容教育","送戒毒所执行强制戒毒","社区戒毒","刑事拘留转取保候审后离开","直保","刑事拘留转监视居住后离开 ","直接取保候审"],
         ["验伤","辨认现场","起赃","其它"],
         ["突发疾病","其它"]
         ];
function getFirstReason(){
	//获得类型下拉框的对象
   var dataType=document.getElementById("dataType");
   //获得去向下拉框的对象
   var direction=document.getElementById("direction");
 //得到对应类型的去向数组
   var dataTypeDirection=firstDirection[dataType.selectedIndex - 1];
   //清空去向下拉框，仅留提示选项
   direction.length=1;
 //将去向数组中的值填充到去向下拉框中
   for(var i=0;i<dataTypeDirection.length;i++){
   	direction[i+1]=new Option(dataTypeDirection[i],dataTypeDirection[i]);
   }
}

function exportDataEdit(index){
	alert("进来了")
	//debugger;
	var rowData = $('#datagrid').datagrid('getRows')[index]; 
	//alert("-----"+rowData.outType);
	$('#certificateTypeId').combobox({   
	    url:'../combobox/certificateTypes.do',   
	    valueField:'id',   
	    textField:'value' ,
	    onLoadSuccess: function(data){    
            $('#certificateType').combobox('setValue', rowData.certificateTypeId);    
        }
	});
	
	$('#organizationId').combobox({   
	    url:'../combobox/listAllOrganization.do',   
	    valueField:'id',   
	    textField:'value' ,
	    onLoadSuccess: function(data){    
            $('#organizationId').combobox('setValue', rowData.organizationId);    
        }
	});
	
	
		
		if(rowData.personType==0){
			//$('#exportData_dialog').layout('expand','south');
			showDialog('#exportData_dialog','编辑数据');
		}else{
			$('#exportData_dialog').layout('collapse','south');
			showDialog('#exportData_dialog','编辑数据');
		}
	    $('#person_form').form('clear');
	    $('#person_form').form('load',rowData);
	    
	    $('#entrance_form').form('clear');
	    $('#entrance_form').form('load',rowData);
	    
	    $('#security_form').form('clear');
	    $('#security_form').form('load',rowData);
	    
	    $('#sex').combobox('select',rowData.personSex);
	    $('#certificateTypeId').combobox('setValue',rowData.certificateTypeId);
	    
	    
	    $('#caseType').combobox('setValue', rowData.caseType);
	    //-------------------------------------------------------20160408
	    //案件性质
	    var ajxz = rowData.caseType;
	    //主案别
	    var zab = rowData.crimeSection;
	    //罪名
	    var zmid = rowData.crimeTypeId;
	    //加载罪名
			$('#crimeTypeId').combobox({
				url : '../combobox/listcrimetypebynature.do?nature='+encodeURIComponent(ajxz),
				valueField : 'id',
				textField : 'value'
			});
		$('#crimeTypeId').combobox('select',zmid);	
	    //-----------------------------------------------------------20160408
	    //下拉框赋值
		if(rowData.outType!=null){
			var type=rowData.outType;
			//alert("type="+type);
			 $("#edataType").val(type);
			//var t1 = document.getElementById("edataType");   
	       /* for(i=0;i<t1.length;i++){//给select赋值  
	            if(type==t1.options[i].value){  
	                t1.options[i].selected=true;
	            }  
	        }*/
			 getEditReason();
	        //var t2 = document.getElementById("edirection"); 
	       var reason=rowData.outReason.replace(/\s+/g,"");
	       //alert("reason"+reason);
	       var index=reason.indexOf("，");
//	       alert("index="+index);
	       var erct="";
	       var ers="";
	       if(index!=-1){
	    	   erct=reason.substring(0,index);
	    	   ers=reason.substring(index+1,reason.length);
	       }else{
	    	   erct=reason;
	       }
	        //alert("reason="+reason);
	        $("#edirection").val(erct);
	        $("#editReason").val(ers);
	        
	        $("#confirmResult").val(rowData.confirmResult);
	       /* for(i=0;i<t2.length;i++){//给select赋值  
	            if(reason==t2.options[i].value){  
	                t2.options[i].selected=true;
	            }  
	        }*/
			//$('#edataType').combobox('select', rowData.outType);
			//$('#edirection').combobox('select', rowData.outReason.replace(/\s+/g,""));
		}else{
			$("#edataType").val("");
			$("#edirection").val("");
	        $("#editReason").val("");
		}
		
		//人身检查
		$("#securityId").val(rowData.securityId);
		if(rowData.type==0){
			document.getElementById("ecommon").checked = "checked";
		}else{
			document.getElementById("etotal").checked = "checked";
		}
		 $('#editMedicalHistory').val(rowData.medicalHistory);
		 $('#editPhysicalDescription').val(rowData.physicalDescription);
		 $('#editInjuryDescription').val(rowData.injuryDescription);
		 $('#editDangerous').val(rowData.dangerous);
		 $('#editOtherDescription').val(rowData.otherDescription);

}

function savePerson(){
	if($("#name").textbox('getValue')=='' ||
			$("#sex").combobox('getValue')==''||
			$("#certificateTypeId").combobox('getValue')==''||
			$("#personCertificateNo").textbox('getValue')==''
	){
		$.messager.alert('提示', '每个栏位不能为空');
		return;
	}
	var personDataForm={};
	personDataForm["id"]=$("#personId").val();
	personDataForm["name"]=$("#name").textbox('getValue');
	personDataForm["sex"]=$("#sex").combobox('getValue');
	personDataForm["certificateTypeId"]=$("#certificateTypeId").combobox('getValue');
	personDataForm["certificateNo"]=$("#personCertificateNo").textbox('getValue');
	var personDataFormJson = JSON.stringify(personDataForm);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../person/update.do',
		data : personDataFormJson,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.show({
				title:'提示',
				msg:data.content,
				timeout:3000
			});
			$('#datagrid').datagrid('load', {
				trefresh:new Date().getTime()
			});
			//$('#exportData_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('Error', '未知错误!'+data);
		}
	});  
}

//验证警察编号是否存在searchInUser1
function finduser1() {
	var userNo = $('#policeNo').val()
	// console.info("===a=a==a"+userNo);
	var userNoInfo={}; 
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
				$("#userId").val(data.id);
				$('#policeman').val(data.realName);
			} else {
				$.messager.alert('错误', userNo + '该警号不存在!');
				$('#policeman').val('');
				$('#policeNo').val('');
				$('#policeNo').focus();
				
			}
		},
		error : function(data) {
			$.messager.alert('错误', '警号不存在！');
			$('#policeman').val('');
			$('#policeNo').val('');
			$('#policeNo').focus();
		}
	});
}

//保存入出区信息
function saveEntrance(){
	if(!checkForm('#entrance_form')){
		return;
	}
	if($("#organizationId").combobox('getText')==''||$("#organizationId").combobox('getValue')==$("#organizationId").combobox('getText')
			){
		$.messager.alert('错误', '办案单位必须为下拉框的值！');
		return;
	}
	
	var EditExportDataForm = $('#entrance_form');
	EditExportDataForm["organizationId"]=$("#organizationId").combobox('getValue');
	var EditExportDataFormJson = JSON.stringify(EditExportDataForm.serializeObject());
	
	
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'exportDataEdit.do',
		data : EditExportDataFormJson,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.show({
				title:'提示',
				msg:data.content,
				timeout:3000
			});
			$('#datagrid').datagrid('load', {
				trefresh:new Date().getTime()
			});
			//$('#exportData_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('Error', '未知错误!'+data);
		}
	});  
}

//人身检查
function saveSecurity(){
	var security = $('#securityinfo').serializeObject();
	security["id"]=$('#securityId').val();
	security["type"]=$("input[name='editType']:checked").val();
	if($('#editPhysicalDescription').val()==null||$('#editPhysicalDescription').val()==''){
		security["physical"]=0;
	}else{
		security["physical"]=1;
	}
	if($('#editInjuryDescription').val()==null||$('#editInjuryDescription').val()==''){
		security["injury"]=0;
	}else{
		security["injury"]=1;
	}
	
	security["medicalHistory"]=$('#editMedicalHistory').val();
	security["physicalDescription"]=$('#editPhysicalDescription').val();
	security["injuryDescription"]=$('#editInjuryDescription').val();
	security["dangerous"]=$('#editDangerous').val();
	security["otherDescription"]=$('#editOtherDescription').val();
	var jsonrtinfo = JSON.stringify(security);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'保存数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '../security/update.do',
		data : jsonrtinfo,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.show({
				title:'提示',
				msg:data.content,
				timeout:3000
			});
			$('#datagrid').datagrid('load', {
				trefresh:new Date().getTime()
			});
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('错误', '未知错误!');
		}
	}); 
	
}

function saveEditExprtData(){
	//alert("==保存修改数据==");
	if($("#name").textbox('getValue')=='' ||
			$("#sex").combobox('getValue')!=''||
			$("#certificateType").combobox('getValue')!=''||
			$("#certificateNo").textbox('getValue')==''
	){
		$.messager.alert('提示', '每个栏位不能为空');
		return;
	}
	var EditExportDataForm;
	EditExportDataFormJson["name"]=$("#name").textbox('getValue');
    EditExportDataFormJson["sex"]=$("#sex").combobox('getValue');
    EditExportDataFormJson["certificateType"]=$("#certificateType").combobox('getValue');
    EditExportDataFormJson["certificateNo"]=$("#certificateNo").textbox('getValue');
	
	var EditExportDataForm = $('#data_form');
	var EditExportDataFormJson = JSON.stringify(EditExportDataForm.serializeObject());
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : EditExportDataFormJson,
		dataType : 'json',
		success : function(data){
			$.messager.progress('close');
			$.messager.alert(data.title, data.content); 
			$('#datagrid').datagrid('load', {
				trefresh:new Date().getTime()
			});
			$('#exportData_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			$.messager.alert('Error', '未知错误!'+data);
		}
	});  
}

function getEditReason(){
	//获得类型下拉框的对象
    var dataType=document.getElementById("edataType");
    //获得去向下拉框的对象
    var direction=document.getElementById("edirection");
  //得到对应类型的去向数组
    var dataTypeDirection=firstDirection[dataType.selectedIndex - 1];
    //清空去向下拉框，仅留提示选项
    direction.length=1;
  //将去向数组中的值填充到去向下拉框中
    for(var i=0;i<dataTypeDirection.length;i++){
    	direction[i+1]=new Option(dataTypeDirection[i],dataTypeDirection[i]);
    }
}

function changeValue(sel){
	var caseTypeValue = sel;
	$('#crimeTypeId').combobox({
		url : '../combobox/listcrimetypebynature.do?nature='+caseTypeValue,
		valueField : 'id',
		textField : 'value'
	
	});
}