$(function() {
    loadArea();
$('#datagrid').datagrid({
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '加载数据中...',
		method: 'get',
		url: contextPath + '/zhfz/bacs/serial/otherPersonList.do',
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
            {title:'嫌疑人id',field:'id',width:80,sortable:true,hidden:true},
            {title:'案件id',field:'bCaseId',width:80,sortable:true,hidden:true}
		]],
		columns:[[	
				  {field:'serialNo',title:'入区编号',width:100},
				  {field:'personName',title:'姓名',width:40
//		                formatter:function(value,rec){
//		                    return '<font color="blue">'+value+'</font>';
//		                }
                  },	
				  {field:'sex',title:'性别',width:25,
		        		formatter:function(value,rec){
		                if(value==1){
		                    return "男";
		                }
		                if(value==2){
		                    return "女";
		                }
		                return "";
		            }
            	  },   
            	  {field:'type',title:'类型',width:25,
	                formatter:function(value,rec){
	                    if(value==1){
	                        return "事主";
	                    }
	                    if(value==2){
	                        return "证人";
	                    }
	                    if(value==3){
	                        return "见证人";
	                    }
	                    if(value==4){
	                        return "被侵害人";
	                    }
	                    if(value==5){
	                        return "报案人";
	                    }
	                    if(value==6){
	                        return "其它";
	                    }
	                    if(value==7){
	                        return "信息采集人";
	                    }
	                    if(value==0){
	                        return "嫌疑人";
	                    }
	                    if(value==8){
	                        return "民警";
	                    }
	                    if(value==9){
	                        return "社工";
	                    }
	                    if(value==10){
	                        return "家属";
	                    }
	                }
                  },       		          
		          {field:'certificateNo',title:'证件号码',width:80},
		          {field:'status',title:'状态',width:40,
	                formatter:function(value,rec){
	                    if(value==0){
	                        return '<font color="#0fea67">已入区</font>';
	                    }else if(value==1){
	                        return '<font color="#FF0000">已安检</font>';
	                    }else if(value==2){
	                        return '<font color="orange">物品已暂存</font>';
	                    }else if(value==3){
	                        return '<font color="#FF0000">候问开始</font>';
	                    }else if(value==4){
	                        return '<font color="orange">候问结束</font>';
	                    }else if(value==5){
	                        return '<font color="#FF0000">信息已采集</font>';
	                    }else if(value==6){
	                        return '<font color="orange">审讯开始</font>';
	                    }else if(value==7){
	                        return '<font color="#FF0000">审讯结束</font>';
	                    }else if(value==8){
	                        return '<font color="orange">物品已领取</font>';
	                    }else if(value==9){
	                        return '<font color="gray">临时出区返回</font>';
	                    }else if(value==10){
	                        return '<font color="orange">临时出区</font>';
	                    }else if(value==11){
	                        return '<font color="gray">已出区</font>';
	                    }
	                }
		          },
		            {field:'inTime',title:'入区时间',width:75,
		                formatter:function(value,rec){
		                    return valueToDate(value);
		                }
		            },
		            {field:'outTime',title:'出区时间',width:75,
		                formatter:function(value,rec){
		                    if(value!=null&&value!=''){
		                        return valueToDate(value);
		                    }else{
		                        return "";
		                    }
		
		                }
		            },
		          //{field:'ajmc',title:'案件名称',width:40},
		          //{field:'ajbh',title:'案件编号',width:40},
		          {field:'ajlx',title:'案件类型',width:40,
		        	  formatter:function(value,rec){
		        			  if(value==0){
									return '<font color="#DAA520">刑事</font>';
								}else if(value==1){
									return '<font color="#00EEEE">行政</font>';
								}else {
									return '<font color="yellow">警情</font>';
								}		        		 
					}},
				   {field:'caseNature',title:'案件性质',width:45,
		        	  formatter:function(value,rec){
							if(value==null||value==""){
								return '无';
							}else{
								return value;
							}
					  }
				   },
				  {field:'areaName',title:'办案场所',width:45},
//		          {
//		          	field : '操作',
//	                title : '操作',
//	                width : 80,
//	                align : 'center',
//	                formatter : function(value, row, index) {
//	                var e ='';	
//	                var o ='';
//	                if(row.outTime==''||row.outTime==null){
//	                    if(isGridButton("entranceEdit")){
//	                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="entranceEdit('+index+')">修改</a></span>';
//	                    }
//	                    if(isGridButton("exitBegin")){
//	                        o ='<span class="spanRow"><a href="#" class="gridout" onclick="exitBegin()">出区</a></span>';
//	                    }
//	                    return e + o;
//	                }
//	            }
//	        }		          		          		         		          
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
});


function doSearch() {
	$('#datagrid').datagrid('load', {
		personName : $('#s_name').val(),
		certificateNo : $('#s_certificateNo').val(),
        areaId : $('#s_areaId').combobox("getValue"),
		startDate : $('#s_start_date').datebox('getValue'),
		endDate : $('#s_end_date').datebox('getValue'),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#s_name').val('');
	$('#s_certificateNo').val('');
	$('#s_start_date').datebox('setValue','');
	$('#s_end_date').datebox('setValue','');
    $("#s_areaId").combobox("setValue","");
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