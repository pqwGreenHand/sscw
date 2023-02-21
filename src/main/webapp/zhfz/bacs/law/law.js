$(function () {
	$('#lawgrid').datagrid({
        iconCls:'icon-datagrid',
        region:'center',
        width:'100%',
		height:'100%',
        fitColumns:true,
        nowrap:false,
        striped:true,
        collapsible:false,
        loadMsg:'加载中.....',
        method:'get',
        url:'list.do',
        queryParams:{
            trefresh:new Date().getDate()
        },
        sortName:'id',
        sortOrder:'desc',
        remoteSort:false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {field:'ck',checkbox:true},
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'type',title:'法律分类',width:80},
            {field:'name',title:'法律名称',width:80},
            {field:'chapter',title:'章节',width:70},
            {field:'content',title:'内容',width:240},
            {field:'id',title:'操作',width:100,
                 formatter:function (value,row,index) {
                     var e='';
                     var d='';
                          if(isGridButton("lawEdit")){
                               e = '<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="lawEdit('+index+')">修改</a></span>';
                          }
                          if(isGridButton("lawRemove")){
                               d = '<span class="spanRow"><a href="#" class="griddel" name="nobutton" onclick="lawRemove('+value+')">删除</a></span>';
                          }
                     return e+d;
                 }
            }
        ]],
        pagination:true,
        pageList: [10,20,30,40,50,100],
        rownumbers: true,
        toolbar:'#lawToolbar'
	});
	var p = $('#lawgrid').datagrid('getPager');
	$(p).pagination({
        onBeforeRefresh:function () {
            
        }
    });

	loadLawType();
    loadLawName();
    $('#fudong').remove();
});

function loadLawType() {
    $('#type_cmb,#type_cmb_id').combobox({
        url:'../combobox/listLawType.do',
        valueField:'id',
        textField:'value',
        onLoadSuccess: function (data) {
            
        },
        onChange:function (newValue,oldValue) {
            
        }
    });
}

function loadLawName() {
    $('#name_cmb,#name_cmb_id').combobox({
        url:'../combobox/listLawName.do',
        valueField:'id',
        textField:'value',
        onLoadSuccess:function (data) {
            
        },
        onChange:function (newValue,oldValue) {
            
        }
    });
}

function saveEnterprise() {
    if(!checkForm('#law_form')){
        return;
    }
    var entForm = $('#law_form').serializeObject();
    entForm["type"] = $("#type_cmb_id").combobox("getText");
    entForm["name"] = $("#name_cmb_id").combobox("getText");
    var enterpriseinfo = JSON.stringify(entForm);
    $.messager.progress({
           title:'请等待',
           msg:'添加/修改数据中...'
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
            $('#lawgrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#law_dialog').dialog('close');
        },
        error : function(data){
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function lawAdd(){
    showDialog('#law_dialog','新增法律法规');
    $('#law_form').form('clear');
    url = 'addLawEntity.do';
}

function lawEdit(index){
    var row = $('#lawgrid').datagrid('getRows')[index];
    showDialog('#law_dialog','编辑法律法规');
    $('#law_form').form('clear');
    $('#law_form').form('load',row);


    url = 'updateLawEntity.do?id='+row.id;
}

function lawRemove(value){
    $.messager.confirm('删除确认','是否确定删除此数据？',function(r){
        if (r){
            $.messager.progress({
                title:'请等待',
                msg:'删除数据中...'
            });
            jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                url : 'removeLawEntity.do',
                data : '{"id":'+value+'}',
                dataType : 'json',
                success : function(data){
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000
                    });
                    $('#lawgrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
                    $.messager.progress('close');
                },
                error : function(data){
                    //exception in java
                    $.messager.progress('close');
                    $.messager.alert('错误', '未知错误!');
                }
            });
        }});
}

function doSearch() {
    $('#lawgrid').datagrid('load', {
        type : $('#type_cmb').combobox('getText'),
        name:$('#name_cmb').combobox('getText'),
        content:$('#s_content').textbox('getValue'),
        trefresh:new Date().getTime()

    });
}

function doClear() {
    $('#type_cmb').combobox('setText','');
    $('#name_cmb').combobox('setText','');
    $('#s_content').textbox('setValue','');
}