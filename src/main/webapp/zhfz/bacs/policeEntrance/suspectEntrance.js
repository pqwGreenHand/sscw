$(function() {
	caseAjlyCode = 1;
    loadArea();
$('#datagrid').datagrid({
		iconCls:'icon-datagrid',
		region: 'center',
		width:'100%',
		height:'90%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: 'list.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true},
            {title:'案件id',field:'bCaseId',width:80,sortable:true,hidden:true}
		]],
		columns:[[
				  {field:'ajmc',title:'案件名称',width:40,hidden:true},
		          {field:'ajbh',title:'案件编号',width:40,hidden:true},
		          {field:'name',title:'姓名',width:15
		        	  //,formatter:function(value,rec){
		        		 //return '<font color="yellow">'+value+'</font>';}
				  },
		          {field:'certificateNo',title:'警号',width:15},
			      {field:'cuffNo',title:'卡片号',width:15},
				  {field:'ajlx',title:'案件类型',width:15,
		        	  formatter:function(value,rec){
		        			  if(value==2){
									return '<font color="#DAA520">刑事</font>';
								}else if(value==1){
									return '<font color="#00EEEE">行政</font>';
								}
					}},
				   {field:'caseNature',title:'案件性质',width:25,
		        	  formatter:function(value,rec){
							if(value==null||value==""){
								return '无';
							}else{
								return value;
							}
					  }
				   },
				  {field:'areaName',title:'办案场所',width:25},
			      {field:'orgName',title:'办案单位',width:25},
				  {field:'policeTotalCount',title:'入区总人数',width:20,hidden:true},
		          {field:'policeInCount',title:'在区人数',width:20,hidden:true},
		          {field:'inTime',title:'入区时间',width:20,
		        	  formatter:function(value,rec){
							return valueToDate(value);
						}
		          },
				{field:'outTime',title:'出区时间',width:20,
					formatter:function(value,rec){
						return valueToDate(value);
					}
				},
				{field:'outStatus',title:'状态',width:15,
					formatter:function(value,rec){
						if(rec.outTime != null){
							return '<font color="#40ff3f">已出区</font>';
						}else {
							return '<font color="red">未出区</font>';
						}
					}
				},
		         {
					field : '操作',
					title : '操作',
					width : 20,
					formatter : function(value, row, index) {

                        if(row.outTime == null){
                            return '<span class="spanRow"><a href="#" class="gridout" onclick="outPolice('+index+')">出区</a></span>';
                        }else{
                            return '<span class="spanRow"><a href="#" class="gridadd" onclick="addToPolice('+index+')">添加民警</a></span>';
                        }
						// return '<span class="spanRow"><a href="#" class="gridout" onclick="exitList('+row.bCaseId+')">详情</a></span>';
					}
				}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#toolbar'
	});
	var p = $('#datagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh:function(){
        $('#datagrid').datagrid('load', {
				 trefresh:new Date().getTime()
          });
		}
	});
    $('#fudong').remove();
    bindaddClick();
});


function bindaddClick(){
    $('#addcuffNo').keydown(function(event){
        if(event.keyCode == 13){
            var data = checkRingNo($("#addcuffNo").val(),1);
            if(!(data.isError)){
                if(data.callbackData.cuffNo != null&&data.callbackData.cuffNo != ""){
                    if(data.callbackData.status == 0){
                        $("#addcuffNo").val(data.callbackData.cuffNo);
                        $("#addcuffId").val(data.callbackData.id);
                    }else{
                        $.messager.alert(data.title, data.content);
                        $("#addcuffNo").val("");
                        $("#addcuffId").val("");
                    }
                }
            }else{
                $.messager.alert(data.title, data.content);
                $("#addcuffNo").val("");
                $("#addcuffId").val("");
            }
        }
    });
}
 function addToPolice(index){
     $("#addInPopup").show();
     $('#addToPoliceForm').form('clear');
     var row = $('#datagrid').datagrid('getRows')[index];
     $('#addpoliceNo').val(row.certificateNo);
     $('#addpoliceId').val(row.policeId);
     $('#orderRequestId1').val(row.orderRequestId);
     $('#areaId1').val(row.areaId);
     $('#opPid').val(row.opPid);
     $('#opUserId').val(row.opUserId);
     $('#policeType').val(row.policeType);
     $('#addcuffNo').focus();
 }
function closeMpopupAddPolice(){
    outClear();
    $("#addInPopup").hide();
}

function savePolice(){
    var policeNo =$('#addpoliceNo').val();
    var policeId=$('#addpoliceId').val();
    var cuffNo=$('#addcuffNo').val();
    var cuffId=$('#addcuffId').val();
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
    // 判断当前民警是否在区，如果在区不能重复入区
    jQuery.ajax({
        type : 'POST',
        async : false,
        dataType : 'json',
        url : 'findPoliceEntranceByPoliceId.do',
        data : {'policeId': policeId},
        success : function(data){
                if(data.callbackData !=null){
                    $('#addpoliceNo').val('');
                    $('#addpoliceId').val('');
                    $('#addcuffNo').val('');
                    $('#addcuffId').val('');
                    $.messager.alert(data.title, '该民警还未出区，不能重复入区!');
                }else {
                    var Form = $('#addToPoliceForm');
                    var policeForm = JSON.stringify(Form.serializeObject());
                    $.messager.progress({
                        title:'请等待',
                        msg:'添加/修改数据中...'
                    });
                    jQuery.ajax({
                        type : 'POST',
                        contentType : 'application/json',
                        dataType : 'json',
                        url : 'addPolice.do',
                        data : policeForm,
                        success : function(data){
                            if(data.code == 1) {
                                $('#datagrid').datagrid('reload');
                                $.messager.progress('close');
                                $.messager.show({
                                    title: data.title,
                                    msg: data.content,
                                    timeout: 3000
                                });
                                $("#addInPopup").hide();
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
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '添加民警失败!');
        }
    });
}

function finduser3(index) {
    var userNo ;
    if(index==0){
        userNo= $('#addpoliceNo').val();
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
                    $("#addpoliceId").val(data.id);
                }
            }else{
                $.messager.alert('错误', userNo + '该警号不存在!');
                if(index==0){
                    $('#addpoliceNo').val('');
                }
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在！');
            if(index==0){
                $('#addpoliceNo').val('');
            }
        }
    });
}


function outPolice(index){
    var row = $('#datagrid').datagrid('getRows')[index];

    jQuery.ajax({
        type : 'POST',
        async : true,
        dataType : 'json',
        url : 'querySerialBySendUserId.do',
        data : {'policeId':row.policeId},
        success : function (data) {
            if(data.length > 0 ){
                $.messager.confirm("提示", "该案件还有嫌疑人在区,确认出区吗？", function (data) {
                    if(data) {
                        outPolice1(row);
                    }else {
                        $.messager.progress('close');
                    }

                });

            } else {
                outPolice1(row);
            }

        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '系统异常!');
        }
    });

}
function outPolice1(row){
    jQuery.ajax({
        type : 'POST',
        async : true,
        dataType : 'json',
        url : 'updateOutTime.do',
        data : {'cuffId':row.cuffId,'policeId':row.policeId,'cuffNo' : row.cuffNo},
        success : function(data){
            if(data.code == 0){
                $.messager.progress('close');
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
                // $('#datagrid').datagrid('reload');
                $('#datagrid').datagrid('load',{
                    queryParams: {
                        trefresh:new Date().getTime()
                    }
                });
            }else{
                $.messager.progress('close');
                //$.messager.alert(data.title, data.content);
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
                closeMpopupOut();
                // $('#datagrid').datagrid('reload');
                $('#datagrid').datagrid('load',{
                    queryParams: {
                        trefresh:new Date().getTime()
                    }
                });
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '出区失败!');
        }
    });
}
// 出区
function exitList(caseId){
    $('#exitDatagrid2').datagrid({
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
		    idField:'code',
		    queryParams : {
		         caseId : caseId,
		         trefresh:new Date().getTime()
		    },
		    url : 'policeList.do',
		    singleSelect : true,
		    frozenColumns : [ [
		    // {field:'ck',checkbox:true},
		    { title : '入区id',field : 'id',width : 10,sortable : true,hidden : true},
		    { title : '案件id',field : 'bCaseId',width : 10,sortable : true,hidden : true},
		    ] ],
		    columns : [[
		             {
		                field : 'name',
		                title : '姓名',
		                width : '30%',
		            },
		            {
		                field : 'certificateNo',
		                title : '民警警号',
		                width : '20%',
		            },
		             {field:'policeType',title:'民警类型',
		              formatter:function(value,rec){
		            		  if(value==0){
		                          return '<font color="#00FF00">主办民警</font>';
		                      }else{
		                          return '<font color="#00EEEE">协办民警</font>';
		                      }
		            }},
		            {
		                field : 'cuffNo',
		                title : '民警卡片',
		                width : '25%',

		            },
		            {
		                field : 'id',
		                title : '操作',
		                width : '25%',
		                align : 'left',
		                formatter : function(value, row, index) {
		                    if (row.outTime == '' || row.outTime == null) {
		                         return '<span class="spanRow"><a href="#" class="gridout" onclick="exit3('+index+')">出区</a></span>';
		                    }
		                }

		                }
		            ] ],
		    pagination : true,
		    pageList : [ 10, 20, 30, 40, 50, 100 ],
		    rownumbers : true,
		    onSelect : function(rowIndex, rowData) {
				// console.info(rowData.lx);】
//				$('#ajxxId').val(rowData.id);
//				$('#exitDatagrid').datagrid('load', {
//					ajxxId : rowData.id,
//					tfresh : new Date().getTime()
//				});
			},
		    // 当数据加载成功时触发
		    onLoadSuccess : function() {
		        showDialog('#exit_dialog2','民警登记列表');
		    },
		  });
	var p = $('#exitDatagrid2').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
			// alert('before refresh');
		}
	});
}

// 出区
function exit3(index){
	var row = $('#exitDatagrid2').datagrid('getRows')[index];
	$.messager.confirm('出区确认', '是否确定该民警('+row.name+')出区，卡号为['+row.cuffNo+']？', function(r) {
        if (r) {
        	 $.messager.progress({
                title:'请等待',
                msg:'正在解除卡片定位信息...'
            });
			jQuery.ajax({
		            type : 'POST',
		            async : false,
		            dataType : 'json',
		            url : 'updateOutTime2.do',
		            data : {'policeEntranceId':row.id},
		            success : function(data){
		            	$('#exitDatagrid2').datagrid('load', {
	                        caseId : row.bCaseId,
	                        trefresh:new Date().getTime()
                        });
						$.messager.progress('close');
		                $.messager.show({
							title:data.title,
							msg:data.content,
							timeout:3000
						});
		            },
		            error : function(data){
		                $.messager.progress('close');
		                $.messager.alert(data.title, data.content);
		            }
		    });
		 }
    });
}

function doSearch() {
	$('#datagrid').datagrid('load', {
		name : $('#s_name').val(),
		areaId : $('#s_areaId').combobox("getValue"),
		certificateNo : $('#s_certificateNo').val(),
		startDate : $('#s_start_date').datebox('getValue'),
		endDate : $('#s_end_date').datebox('getValue'),
		status : $('#s_status').combobox("getValue"),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_name').val('');
	$('#s_certificateNo').val('');
	$('#s_start_date').datebox('setValue','');
	$('#s_end_date').datebox('setValue','');
    $("#s_areaId").combobox("setValue","");
	$('#s_status').combobox("setValue","");
}

//加载办案场所
function loadArea(){
    $('#s_areaId').combobox({
        url: contextPath + '/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            /*if(data != null && data.length > 0){
                $('#s_areaId').combobox('setValue',data[0].id);
            }*/
        }
    });
}