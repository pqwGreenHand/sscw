$(function(){
	initCaseInfo();
    initAjlx2();
    bindClick();
    loadArea();
});

// 民警入区
function entrance() {
    doAllClear();
    initCaseInfo();
    initAjlx2();
    loadAreaPolice();
    loadOrderInfo();
    $("#inPopup").show();    
}
//打开关联案件弹框
function linkCase(){
    // showDialog('#link_case_dialog','关联案件');
    $("#link_case_dialog").show();
    loadLinkCaseList();
    if($('#link_case_datagrid')) {
        $('#link_case_datagrid').datagrid({
            onLoadSuccess: function (data) {
                if($("#caseId")){
                    var caseId = $("#caseId").val();
                    if (caseId != null && caseId != '' && data != null && data.rows != null && data.rows.length != 0) {
                        for (var i=0;i<data.rows.length;i++){
                            if(caseId == data.rows[i].id){
                                $('#link_case_datagrid').datagrid("selectRow", i);
                            }
                        }
                    }
                }
            }
        });
    }
}
//关联案件保存
function linkCaseSave(){
    var erow = $('#link_case_datagrid').datagrid('getSelected');
    if(erow==null)
    {
        $.messager.alert('提示', '请先选择一行!');
        return;
    } else{
        initCaseInfo(erow.id);
        $("#link_case_dialog").hide();
    }
}
function loadPoliceList(policeEntranceId){
    $('#policegrid').datagrid({
	    iconCls : 'icon-datagrid',
	    region : 'center',
	    width : '100%',
	    height : '100%',
	    fitColumns : true,
	    nowrap : false,
	    striped : true,
	    collapsible : false,
	    loadMsg : 'Loading...',
	    method : 'get',
	    remoteSort : false,
	     //idField:'code',
	     queryParams : {
	         trefresh:new Date().getTime()
	     },
	    //url : 'policeList.do',
	    singleSelect : true,
	    frozenColumns : [ [
	    // {field:'ck',checkbox:true},
	    {
	        title : 'id',
	        field : 'id',
	        width : 10,
	        sortable : true,
	        hidden : true
	    } ] ],
	    columns : [ [
	            {
	                field : 'policeNo',
	                title : '民警警号',
	                width : '25%',	                
	            },	            
	             {field:'policeType',title:'民警类型',width:'25%',
		              formatter:function(value,rec){
		                if(value==0){
		                    return '<font color="#0fea67">主办民警</font>';
		                }else{
		                    return '<font color="yellow">协办民警</font>';
		                }
	            }},                               
	            {
	                field : 'cuffNo',
	                title : '民警卡片',
	                width : '25%',
	                
	            }, 
	            {field : 'policeId',title : '民警ID', hidden : true},  
	            {field : 'cuffId',title : '卡片ID', hidden : true}, 
	            {field : 'caseId',title : '案件ID', hidden : true},                        
	            {
	                field : 'id',
	                title : '操作',
	                width : '25%',
	                align : 'left',
	                formatter : function(value, row, index) {
	                    return '<span class="spanRow"><a href="#" class="griddel" onclick="deleteRow('
	                            + index + ')">删除</a></span>';
	                }
	            } ] ],
	    pagination : false,
	    pageList : [ 10, 20, 30, 40, 50, 100 ],
	    rownumbers : true,
	    // 当数据加载成功时触发
	    onLoadSuccess : function() {
	
	    },
	    
	});
}

// 下一步
function saveStepDiv1Data(){
    var orderRequestId = $('#orderRequestId').combogrid('getValue');
	if (!checkForm('#caseInfoForm')) {
        return;
    }
	var caseId = $("#caseId").combobox("getValue");
	/*if(caseId == null || caseId == ''){
        $.messager.alert("提示","请选择案件信息");
        return;
    }*/
    showNoHideStepDiv("stepDiv1", "step1", 0);
    showNoHideStepDiv("stepDiv2", "step2", 1);
    // 加载民警信息列表
    loadPoliceList();
    var cg = $('#orderRequestId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected'); // 获取选择的行
    $('#policeNo').val(row.certificateNo);
    $('#policeId').val(row.policeId);
    document.getElementById('main').checked = true;
    var rows = $("#policegrid").datagrid("getRows");
    if(rows != null && rows.length > 0) {
        for(var i = rows.length - 1; i >= 0; i--){
            var index = $('#policegrid').datagrid('getRowIndex',rows[i]);
            $('#policegrid').datagrid('deleteRow', index);
        }
    }
    loadPoliceList2(orderRequestId);
}

function finduser1(index) {
    var userNo ;
    if(index==0){
        userNo= $('#policeNo').val();
    }else if(index==1){
        userNo= $('#e_policeNo').val();
    }else if(index==2){
        userNo= $('#ep_policeNo').val();
    }
    if(!userNo){    	
    	return;
    }     
    var userNoInfo = {"userNo":userNo};
    var jsonrtinfo = JSON.stringify(userNoInfo);       
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/order/searchUser.do',
        data : jsonrtinfo,
        success : function(data) {
            if(data != null && data.id != null) {
               if(index==0){
                   $("#policeId").val(data.id); 
                }else if(index==1){
                    $('#e_policeId').val(data.id);
                }else if(index==2){
                    $('#ep_policeId').val(data.id);
                }
              }else{
                $.messager.alert('错误', userNo + '该警号不存在!');
                if(index==0){
                   $('#policeNo').val('');                   
                }else if(index==1){
                    $('#e_policeNo').val('');
                }else if(index==2){
                    $('#ep_policeNo').val('');
                }
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在！');
               if(index==0){
                   $('#policeNo').val('');
                }else if(index==1){
                    $('#e_policeNo').val('');
                }else if(index==2){
                    $('#ep_policeNo').val('');
                }
        }
    });
}

// 添加主办民警信息
function AddPolice(){
    var policeNo =$('#policeNo').val();
    var policeId=$('#policeId').val();
    var cuffNo=$('#cuffNo').val();
    var cuffId=$('#cuffId').val();
    var caseId = $('#caseId').combogrid("getValue");
    if(!policeNo){
        $.messager.alert('警告', '请输入民警警号！');
        return;
    }   
    if(!cuffNo){
        $.messager.alert('警告', '请刷入民警卡片！');
        return;
    }
    if(!cuffId){
        $.messager.alert('警告', '民警卡片错误,请重新刷卡片！');
        return;
    }

    // 判断当前卡片是否已绑定
    var rows = $("#policegrid").datagrid("getRows");
    if(rows != null && rows.length > 0) {
        for(var i = rows.length - 1; i >= 0; i--){
            if(policeNo == rows[i].policeNo){
                $('#policeNo').val('');
                $('#policeId').val('');
                $.messager.alert('警告', '该民警已入区！');
                return;
            }
            if(cuffNo == rows[i].cuffNo){
                $('#cuffNo').val('');
                $('#cuffId').val('');
                $.messager.alert('警告', '该民警卡片已绑定！');
                return;
            }
        }
    }

    var policeType;
   if(document.getElementById('main').checked){
       policeType=0;
   }
   if(document.getElementById('assist').checked){
       policeType=1;
   }
   //第一次加必须是主办民警
   var rows = $("#policegrid").datagrid("getRows");
   if(policeType==1&&rows.length==0){
        $.messager.alert('警告', '请先添加主办民警！');
        return;
   }
   //主办民警只能添加一次
   if(policeType==0&&rows.length==1){
   		$.messager.alert('警告', '主办民警只能添加一个！');
        return;
   }
   // 判断当前民警是否在区，如果在区不能重复入区
    jQuery.ajax({
        type : 'POST',
        async : false,
        dataType : 'json',
        url : 'findPoliceEntranceByPoliceId.do',
        data : {'policeId': policeId},
        success : function(data){
            $.messager.progress('close');
            if(data.code == 0){
                if(data.callbackData !=null){
                    $('#policeNo').val('');
                    $('#policeId').val('');
                    $('#cuffNo').val('');
                    $('#cuffId').val('');
                    $.messager.alert(data.title, '该民警还未出区，不能重复入区!');
                }else {
                    // 动态给table添加一行数据
                    $('#policegrid').datagrid('appendRow',{
                        policeNo: policeNo,
                        policeType: policeType,
                        cuffNo: cuffNo,
                        policeId : policeId,
                        cuffId : cuffId,
                        caseId : caseId
                    });
                    // 点击添加后清空表单数据
                    $('#policeNo').val('');
                    $('#policeId').val('');
                    document.getElementById('assist').checked = true;
                    $('#cuffNo').val('');
                    $('#cuffId').val('');
                    // 更新民警卡片状态为已绑定
                    /*jQuery.ajax({
                        type : 'POST',
                        async : false,
                        dataType : 'json',
                        url : 'updateCuffStatus.do',
                        data : {'cuffId': cuffId},
                        success : function(datas){
                            $.messager.progress('close');
                            if(datas.code == 0){
                                // 动态给table添加一行数据
                                $('#policegrid').datagrid('appendRow',{
                                    policeNo: policeNo,
                                    policeType: policeType,
                                    cuffNo: cuffNo,
                                    policeId : policeId,
                                    cuffId : cuffId,
                                    caseId : caseId
                                });
                                // 点击添加后清空表单数据
                                $('#policeNo').val('');
                                $('#policeId').val('');
                                document.getElementById('assist').checked = true;
                                $('#cuffNo').val('');
                                $('#cuffId').val('');
                            }else {
                                $.messager.progress('close');
                                $.messager.alert(data.title, data.content);
                            }
                        },
                        error : function(data){
                            $.messager.progress('close');
                            $.messager.alert('提示', '民警卡片状态更新失败!');
                        }
                    });*/
                }
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '添加民警失败!');
        }
    });
}

// 删除民警信息
function deleteRow(index){
	$('#policegrid').datagrid('deleteRow',index);
}

// 完成
function endEntrance(){
	// 获取主办民警所有行数据
	var rows = $("#policegrid").datagrid("getRows");
	if(rows.length == 0){
        $.messager.alert('提示', '请添加民警!');
        return;
    }
	var caseInfo = $('#caseInfoForm').serializeObject();
	var ab = $('#policeAb').combogrid("getValue");
	caseInfo.ab = ab;
	var a = {};
	a['list'] = rows;
	a['case'] = caseInfo;
 	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
    });
    jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                dataType : 'json',
                url : 'addPoliceEntrance.do',
                data : JSON.stringify(a),
                success : function(data){
                    if(data.code == 1) {
                        //initPoliceData();
                        $('#datagrid').datagrid('reload');
                        $.messager.progress('close');
                        //$.messager.alert(data.title, data.content);
                        $.messager.show({
                            title: data.title,
                            msg: data.content,
                            timeout: 3000
                        });
                        document.getElementById('assist').checked = true;
                        closeMpopup();
                        var rows = $("#policegrid").datagrid("getRows");
                        if (rows != null && rows.length > 0) {
                            for (var i = rows.length - 1; i >= 0; i--) {
                                var index = $('#policegrid').datagrid('getRowIndex', rows[i]);
                                $('#policegrid').datagrid('deleteRow', index);
                            }
                        }
                    }
                    if(data.code == 0){
                        $.messager.progress('close');
                        $.messager.alert(data.title, data.content);
                        return;
                    }
                },
                error : function(data){
                    $.messager.progress('close');
                    $.messager.alert('提示', '民警入区失败!');
                }
            });     
}

function backStepDiv1(){
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv1", "step1", 1);
}

//需要隐藏的步骤id；显示还是隐藏 type:0隐藏 1显示
function showNoHideStepDiv(stepDivId,stepId,type){
    var tempStep = $("#"+stepId);
    var tempStepDiv = $("#"+stepDivId);
    if(type==1){
        tempStepDiv.show();
        tempStep.addClass("now");
    }else{
        tempStepDiv.hide();
        tempStep.removeClass("now");
    }
}

function closeMpopup(){
    doAllClear();
    showNoHideStepDiv("stepDiv1", "step1", 1);
    showNoHideStepDiv("stepDiv2", "step2", 0);
    //showNoHideStepDiv("stepDiv3", "step3", 0);
    $("#inPopup").hide();
}

function bindClick(){
	$('#cuffNo').keydown(function(event){
		if(event.keyCode == 13){
			var data = checkRingNo($("#cuffNo").val(),1);
			if(!(data.isError)){
				if(data.callbackData.cuffNo != null&&data.callbackData.cuffNo != ""){
					if(data.callbackData.status == 0){
						$("#cuffNo").val(data.callbackData.cuffNo);
	                    $("#cuffId").val(data.callbackData.id);
					}else{
						$.messager.alert(data.title, data.content);
						$("#cuffNo").val("");
                        $("#cuffId").val("");
					}					
				}	             
            }else{               
                $.messager.alert(data.title, data.content);
                $("#cuffNo").val("");
                $("#cuffId").val("");
            }			
		}
	});
}

function doAllClear(){
	// 清空民警信息
    $('#policeNo').val('');                    
    $('#policeId').val('');
    $('#cuffNo').val('');
    $('#cuffId').val(''); 
    // 清空案件信息	                
    $("#caseId").combogrid('setText','');
    $('#ajlx').combobox('setValue','');
    $('#policeAb').combobox('setValue','');
    //$('#policeAfsj').datetimebox('setValue','');
    $('#afdd').val('');
}

//加载办案场所
function loadAreaPolice(){
    $('#police_areaId').combobox({
        url: contextPath + '/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            if(data != null && data.length > 0){
                $('#police_areaId').combobox('setValue',data[0].id);
            }
        }
    });
}

// 加载预约信息列表
function loadOrderInfo() {
    //加载预约信息列表
    $('#orderRequestId').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: ' ../../common/combogrid/getAllOrderInfo.do',
        idField: 'id',
        textField: 'name',
        columns: [[{
            field: 'orderNo',
            title: '预约编号',
            width: 180
        }, {
            field: 'name',
            title: '民警姓名',
            width: 80
        }, {
            field: 'certificateNo',
            title: '民警警号',
            width: 100
        }, {
            field: 'policeId',
            title: '预约民警ID',
            width: 50,
            hidden: true
        },]],
        onSelect:function (rowIndex, rowData){
            $('#police_areaId').combobox('setValue', rowData.areaId);
            $('#policeXjlx').combobox('setValue', rowData.orderAjlx);
            $('#policeAb').combobox('setValue', rowData.orderAb);
            $('#afdd').val(rowData.description);
        }
    });
}

function loadPoliceList2(orderRequestId){
    $('#policegrid').datagrid({
        iconCls : 'icon-datagrid',
        region : 'center',
        width : '100%',
        height : '100%',
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        loadMsg : 'Loading...',
        method : 'get',
        remoteSort : false,
        //idField:'code',
        queryParams : {
            orderRequestId : orderRequestId,
            trefresh:new Date().getTime()
        },
        url : 'policeList.do',
        singleSelect : true,
        frozenColumns : [ [
            // {field:'ck',checkbox:true},
            {
                title : 'id',
                field : 'id',
                width : 10,
                sortable : true,
                hidden : true
            } ] ],
        columns : [ [
            {
                field : 'policeNo',
                title : '民警警号',
                width : '25%',
            },
            {field:'policeType',title:'民警类型',width:'25%',
                formatter:function(value,rec){
                    if(value==0){
                        return '<font color="#0fea67">主办民警</font>';
                    }else{
                        return '<font color="yellow">协办民警</font>';
                    }
                }},
            {
                field : 'cuffNo',
                title : '民警卡片',
                width : '25%',

            },
            {field : 'policeId',title : '民警ID', hidden : true},
            {field : 'cuffId',title : '卡片ID', hidden : true},
            {field : 'caseId',title : '案件ID', hidden : true},
            {
                field : 'id',
                title : '操作',
                width : '25%',
                align : 'left',
                formatter : function(value, row, index) {
                    return '<span class="spanRow"><a href="#" class="griddel" onclick="deleteRow('
                        + index + ')">删除</a></span>';
                }
            } ] ],
        pagination : false,
        pageList : [ 10, 20, 30, 40, 50, 100 ],
        rownumbers : true,
        // 当数据加载成功时触发
        onLoadSuccess : function() {

        },

    });
}