$(function(){
	$('#datagrid').datagrid({
		iconCls : 'icon-datagrid',
        region : 'center',
        width : '100%',
        height : '80%',
        fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
        loadMsg : '加载中...',
        method : 'get',
        url : 'getRecords.do',
        queryParams : {
            trefresh:new Date().getTime()
        },
        remoteSort : false,
        idField : 'id',
        singleSelect : false,
        frozenColumns : [ [ {
            title : 'id',
            field : 'id',
            width : 80,
            sortable : true,
            hidden : true
        } ] ],
		columns:[[
			{field:'userName',title:'用户名',width:50},
			{field:'zjhm',title:'证件号码',width:50},
			{field:'checkTime',title:'时间',width:50,
				formatter: function (value, row, index) {
                    return formatter(value, 'yyyy-MM-dd hh:mm:ss');
                }
			},
			{field:'checkType',title:'类型',width:50,
				formatter: function (value, row, index) {
                    if(value=='I'){
                        return "签到";
                    }
                    if(value=='O'){
                    	return "签退";
                    } 
                }
			
			},
			{field:'sensorId',title:'设备号',width:50},
			{field:'depart',title:'所属部门',width:50},
			{field:'图片',title:'图片',width:50,
				formatter: function (value, row, index) { 
					var url = "http://localhost:8080/test/"+formatter(row.photo, 'yyyyMMddhhmmss')+"-"+row.userName+".jpg";
                     return '<img src ="'+url+'"/ style="width:25px;height:25px">';  
                } 
			},
			{field:'操作',title:'操作',width:50,
				formatter: function (value, row, index) { 
                  return  '<span class="spanRow" ><a href="#" class="gridpicture" onclick="picture('+index+')">查看图片</a></span>'
                } 
			}
		]],
		pagination : true,
        pageList : [ 10, 20, 30, 40, 50, 100 ],
        rownumbers : true,
        // 行选择方法，进行条件触发
        toolbar:'#toolbar',
        onSelect : function(rowIndex, rowData) {
            
        },
        onDblClickRow : function(index) {
             
        },
        onLoadSuccess:function(data){
        	  
        }
	});
	
})

function picture(index){
    var row = $('#datagrid').datagrid('getRows')[index]; 
    var url = "http://localhost:8080/test/"+formatter(row.photo, 'yyyyMMddhhmmss')+"-"+row.userName+".jpg"
    $("#picDiv").attr("src", url); 
    showDialog('#picture_dialog', '出入记录图片'); 
}

function doSearch() {
    $('#datagrid').datagrid('load', {
        xm : $('#xm').val(),
        zjhm : $('#zjhm').val(),
        sensorId : $('#sensorId').val(),
        start_date : $('#start_date').datebox('getValue'),
		end_date : $('#end_date').datebox('getValue'),
        trefresh:new Date().getTime()
    });
}

function doClear() {
    $('#xm').prop('value','');
    $('#zjhm').prop('value','');
    $('#sensorId').prop('value','');
	$('#start_date').datebox('setValue', '');
	$('#end_date').datebox('setValue', '');
}

 
