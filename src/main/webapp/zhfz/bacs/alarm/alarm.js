function init(){
	// 告警类型初始化
	$('#s_alarm_type').combobox({
	        url:'../combobox/listAlarmType.do',
	        valueField:'id',
	        textField:'value',
			onLoadSuccess:function(typeData){
	        	if(typeData != null && typeData.length > 0){
					jQuery.ajax({
						contentType: 'application/json',
						url: 'listAlarmCountByType.do',
						dataType: 'json',
						success: function (data) {
							console.log(data);
							$("#alarmTypeS").html('');
							var alarmType = "";
							$.each(typeData, function(name, value) {
								var image = '';
								if(value.value == "拆卸告警"){
									image = 'image/disassemble.png';
								} else if(value.value == "低电压告警"){
									image = 'image/lowVoltage.png';
								} else if(value.value == "脱离告警"){
									image = 'image/divorcedFrom.png';
								} else if(value.value == "非法闯入告警"){
									image = 'image/IntrudeInto.png';
								} else if(value.value == "SOS紧急呼叫告警"){
									image = 'image/sos.png';
								} else if(value.value == "单人讯询问告警"){
									image = 'image/Interrogation.png';
								} else if(value.value == "无人看守告警"){
									image = 'image/noPolice.png';
								} else{
									image = 'image/alarm.png';
								}
								var count = 0;
								if(data[value.id]){
									count = data[value.id];
								}
								alarmType += '<li onclick="doSearchDetail(this,'+value.id+')" id="alarmType'+value.id+'">'
									+'<img src="'+image+'" width="40%" height="40%">'
									+'<p class="number">'+count+'</p>'
									+'<p class="name">'+value.value+'</p>'
									+'</li>';
							})
							$("#alarmTypeS").html(alarmType);
						}
					})
				} else{
					var alarmType = '<li>'
						+'<img src="image/noAlarm.png" width="40%" height="40%">'
						+'<p class="number">'+0+'</p>'
						+'<p class="name">暂无告警类型</p>'
						+'</li>';
					$("#alarmTypeS").html(alarmType);
				}
			},
			onChange:function(newData,oldData){
				$(".menu li").removeClass("click");
				var aId= 'alarmType'+newData;
				$("#"+aId).addClass('click');
			}
	    });
}
$(function() {
init();
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
		url: 'listAlarm.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'id',
		sortOrder: 'desc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            //{field:'ck',checkbox:true},
            {title:'嫌疑人或者民警id',field:'id',width:80,sortable:true,hidden:true},
            {title:'摄像头编号',field:'cameraNo',width:60,hidden:true}
		]],
		columns:[[
                  {field:'name',title:'姓名',width:60},
				  {field:'alarmName',title:'告警名称',width:60},				  
				  {field:'alarmType',title:'告警类型',width:60,
		        		formatter:function(value,rec){
		                if(value==1){
		                    return "拆卸告警";
		                }
		                if(value==2){
		                    return "低电压告警";
		                }
		                if(value==3){
		                	return "脱离告警";
		                }
		                if(value==4){
		                	return "非法闯入告警";
		                }
		                if(value==5){
		                	return "SOS紧急呼叫告警";
		                }
		                if(value==6){
		                	return "单人讯询问告警";
		                }
		                if(value==7){
		                	return "无人看守告警";
		                }
		                return "";
		            }
            	  },
				{field:'roomName',title:'审讯室名称',width:60},
				{field:'cuffNo',title:'定位标签号',width:50},
				{field:'deviceNo',title:'定位设备号',width:50},
	            {field:'alarmTime',title:'告警时间',width:75,
	                formatter:function(value,rec){
	                    return valueToDate(value);
	                }
	            },	            
		       {
	          	field : '操作',
                title : '操作',
                width : 60,
                align : 'center',
                formatter : function(value, row, index) {
                var e ='';	
                if(isGridButton("trarkPlay")){
                        e ='<span class="spanRow"><a href="#" class="griddownload" onclick="playView('+index+')">播放</a></span>';
                }                   
                return e;
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
});

// 视频播放
function playView(index) {
    var row = $('#datagrid').datagrid('getRows')[index];
    jQuery.ajax({
        contentType: 'application/json',
        url: 'playBackVideo.do',
        dataType: 'json',
        data: {"cameraNo": row.cameraNo},
        success: function (data) {
            if (data != null) {
                if (data.error) {
                    $.messager.alert("错误", "获取海康回放视频参数失败：" + data.content);
                    return;
                }else {
                    var height = 600;
                    var width = 700;
                    var screenParam = "left=0,top=0,scrollbars,resizable=yes,toolbar=no',height=" + height + ",width=" + width;
                    window.open("playback.jsp?startTime=" + valueToDate(row.startTime) + "&endTime=" + valueToDate(row.endTime) + "&xml=" + encodeURIComponent(data.content), 'newwindow', screenParam);
                }
            } else {
                $.messager.alert("错误", "获取海康回放视频参数失败!");
            }
        },
        error: function (data) {
            $.messager.progress('close');
            if (data != null) {
                if (data.error)
                    $.messager.alert("错误", "获取海康参数异常:" + data.content);
            }
            else {
                $.messager.alert("错误", "获取海康参数异常");
            }
        }
    });
}

function doSearch() {
	$('#datagrid').datagrid('load', {
        name : $('#s_name').val(),
		alarmName : $('#s_alarm_name').val(),
		alarmType : $('#s_alarm_type').combobox('getValue'),
		startDate : $('#s_start_date').datebox('getValue'),
		endDate : $('#s_end_date').datebox('getValue'),
        areaId : $('#s_areaId').combobox("getValue"),
		trefresh:new Date().getTime()
	});
}

function doClear() {
    $('#s_name').val('');
	$('#s_alarm_name').val('');
	$('#s_alarm_type').combobox('setValue','');
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

function doSearchDetail(event,alarmType){
	$(".menu li").removeClass("click");
	$(event).addClass('click');
	$("#s_alarm_type").combobox("setValue",alarmType);
	$('#datagrid').datagrid('load', {
		alarmType : alarmType,
		trefresh:new Date().getTime()
	});
}