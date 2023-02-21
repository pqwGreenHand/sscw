$(function(){
	$('#datagrid').datagrid({
		iconCls : 'icon-datagrid',
        region : 'center',
        width : '100%',
        height : '80%',
        fitColumns : true,
        nowrap : true,
        striped : true,
        collapsible : false,
        loadMsg : '加载中...',
        method : 'get',
        url : 'getLists.do',
        queryParams : {
            trefresh:new Date().getTime()
        },
        remoteSort : false,
        idField : 'id',
        singleSelect : true,
        frozenColumns : [ [ {
            title : 'id',
            field : 'id',
            width : 80,
            sortable : true,
            hidden : true
        } ] ],
		columns:[[
			{field:'ajbh',title:'案件编号',width:50},
			{field:'ajmc',title:'案件名称',width:50},
			{field:'xm',title:'嫌疑人姓名',width:50},
			{field:'zjhm',title:'嫌疑人证件号码',width:50},
			{field:'lrr',title:'主办民警',width:50},
			{field:'wpbh',title:'物品编号',width:50},
			{field:'wpmc',title:'物品名称',width:50},
			{field:'lrrq',title:'录入时间',width:50},
			{field:'organization',title:'主办单位',width:50} 
		]],
		pagination : true,
        pageList : [ 10, 20, 30, 40, 50, 100 ],
        rownumbers : true,
        // 行选择方法，进行条件触发
        toolbar:'#toolbar',
        onSelect : function(rowIndex, rowData) {
            
        },
        onDblClickRow : function(index) {
             
        } 
	});
	
})

function doSearch() {
    $('#datagrid').datagrid('load', {
        xm : $('#xm').val(),
        zjhm : $('#zjhm').val(),
        ajbh : $('#ajbh').val(),
        ajmc : $('#ajmc').val(),
        trefresh:new Date().getTime()
    });
}

function doClear() {
    $('#xm').prop('value','');
    $('#zjhm').prop('value','');
    $('#ajmc').prop('value','');
    $('#ajbh').prop('value','');
}

