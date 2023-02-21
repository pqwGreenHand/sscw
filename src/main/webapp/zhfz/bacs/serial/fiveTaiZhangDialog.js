//保存签字类型
var SIGNTYPE = "";

var CurrFlag3 = "";
//五合一台账打印
function  FivePringTaizhang(serialId) {
    $("#FivePringTaizhangDialog").show();
    $("#FivePringTaizhangDiv1").show();
    $("#fivePringTaizhangSerialId").val(serialId);
    CurrFlag3 = typeof currenMap != "undefined" ? true : false;
}

//五合一原始打印
function fiveOrigPrint(type){
	//1 入区，2安检，3随身，4涉案，5出区
	 var serialId =  $("#fivePringTaizhangSerialId").val();
     //强制让表格选中一行
     $('#datagrid').datagrid('selectRecord',serialId);
     //获取选中行数据
     var row = $('#datagrid').datagrid("getSelected");
     if(row != null){
         $.messager.progress({
             title:'请等待',
             msg:'打印预览中...'
         });
         if(type=='1'){
        	 fileUtils.read("/zhfz/lawdocProcess/download.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&serialUUID="+row.uuid+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
         }else if(type=='2'){
        	 fileUtils.read("/zhfz/lawdocProcess/download.do?number=11&name=违法犯罪嫌疑人人身安全检查情况登记表&serialUUID="+row.uuid+"&serialNo="+row.serialNo+"&serialId="+row.id);
         }else if(type=='3'){
        	 fileUtils.read("/zhfz/lawdocProcess/download.do?number=9&name=随身物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
         }else if(type=='4'){
        	 fileUtils.read("/zhfz/lawdocProcess/download.do?number=48&name=涉案物品临时保管台账&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
         }else if(type=='5'){
        	 fileUtils.read("/zhfz/lawdocProcess/download.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialUUID="+row.uuid+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
         }else if(type=='6'){
        	 fileUtils.read("/zhfz/lawdocProcess/download.do?number=58&name=涉案人员五合一台账 &type=1&userId=0&serialUUID="+row.uuid+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
         }
         
         $.messager.progress('close');
     }else{
         $.messager.alert('提示',"未选中数据，请刷新页面！");
     }
}



//五合一签字
function fiveSign(type){
	//1 入区，2安检，3随身，4涉案，5出区
	var serialId = $("#SignTaizhangSerialId").val();
	var url = "";
	if(type=='1'){
		SIGNTYPE = '0';
		url = "../../lawdocProcess/downloadBase64.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
    }else if(type=='2'){
    	SIGNTYPE = '3';
    	url = "../../lawdocProcess/downloadBase64.do?number=11&name=违法犯罪嫌疑人人身安全检查情况登记表&serialUUID="+row.uuid+"&serialNo="+row.serialNo+"&serialId="+row.id
    }else if(type=='3'){
    	SIGNTYPE = '5';
    	var url = "../../lawdocProcess/downloadBase64.do?number=9&name=随身物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
    }else if(type=='4'){
    	SIGNTYPE = '6';
    	url ="../../lawdocProcess/downloadBase64.do?number=48&name=涉案物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
    }else if(type=='5'){
    	SIGNTYPE = '2';
        url = "../../lawdocProcess/downloadBase64.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialNo="+row.serialNo;
    }else if(type=='6'){
    	SIGNTYPE = '11';
        url = "../../lawdocProcess/downloadBase64.do?number=58&name=涉案人员五合一台账&type=1&userId=0&serialNo="+row.serialNo;
    }
    //强制让表格选中一行
    $('#datagrid').datagrid('selectRecord',serialId);
    //获取选中行数据
    var row = $('#datagrid').datagrid("getSelected");
    if(row != null){
        signStart(url,serialId);
    }else{
        $.messager.alert('提示',"未选中数据，请刷新页面！");
    } 
}



//五合一签字打印
function fiveSignPrint(type){
	if(type=='1'){
		SIGNTYPE = '0';
    }else if(type=='2'){
    	SIGNTYPE = '3';
    }else if(type=='3'){
    	SIGNTYPE = '5';
    }else if(type=='4'){
    	SIGNTYPE = '6';
    }else if(type=='5'){
    	SIGNTYPE = '2';
    }else if(type=='6'){
    	SIGNTYPE = '2';
    }
	
    var serialId =  $("#PringTaizhangSerialId").val();
    //强制让表格选中一行
    $('#datagrid').datagrid('selectRecord',serialId);
    //获取选中行数据
    var row = $('#datagrid').datagrid("getSelected");
    if(row != null){
        printChoose(row.id,row.uuid,row.areaId,SIGNTYPE);
    }else{
        $.messager.alert('提示',"未选中数据，请刷新页面！");
    }
   
}