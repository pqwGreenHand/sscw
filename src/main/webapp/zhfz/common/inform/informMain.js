var systemName;

$(function () {

     var pid=getUrlParam("pid");

    if (pid==1) {

        systemName='XT';

    }else if (pid==2) {

        systemName='BA';

    }else if (pid==3) {

        systemName='JZ';

    }else if (pid==4) {
        systemName='SA';

    }else if (pid==5) {

        systemName='KP';

    }else if (pid==7) {

        systemName='PT';

    }else if (pid==8) {

        systemName='SLA';

    }else {
        systemName='';
    }

	$('#informgrid').datagrid({
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
        url:'listInform.do',
        queryParams:{
            trefresh:new Date().getTime(),
            systemName:systemName
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
            {field:'senderName',title:'发送人',width:120},
            {field:'receiverName',title:'收件人',width:100},
            {field:'title',title:'标题',width:100},
            {field:'content',title:'内容',width:240},
            {field:'sendTime',title:'发送时间',width:240,formatter:function (value,row,index) {
                    return formatter(new Date(value),'yyyy-MM-dd hh:mm:ss');
                }},
            {field:'isRead',title:'查看状态',width:150,formatter:function (value) {
                    if (value == 0) {

                        return '未查看';
                    } else {
                        return '已查看';
                    }
                }},
            {field:'id',title:'操作',width:150,
                 formatter:function (value,row,index) {

                     return '<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="informEdit('+index+')">查看</a></span>';
                     // var e='';
                     // var d='';
                     //      if(isGridButton("lawEdit")){
                     //           e = '<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="lawEdit('+index+')">修改</a></span>';
                     //      }
                     //      if(isGridButton("lawRemove")){R
                     //           d = '<span class="spanRow"><a href="#" class="griddel" name="nobutton" onclick="lawRemove('+value+')">删除</a></span>';
                     //      }
                     // return e+d;
                 }
            }
        ]],
        pagination:true,
        pageList: [10,20,30,40,50,100],
        rownumbers: true,
        toolbar:'#informToolbar'
	});
	var p = $('#informgrid').datagrid('getPager');
	$(p).pagination({
        onBeforeRefresh:function () {
            
        }
    });

	// loadLawType();
    // loadLawName();
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
    if(!checkForm('#law_from')){
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
            $('#informgrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#inform_dialog').dialog('close');
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

function informEdit(index){
    var row = $('#informgrid').datagrid('getRows')[index];
    showDialog('#inform_dialog','查看消息成功');
    $('#inform_form').form('clear');
    $('#inform_form').form('load',row);

    var informData={};

    informData['id']=row.id;
    informData['systemName']=systemName;

    var informForm=JSON.stringify(informData);

    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : 'updateInform.do',
        data : informForm,
        dataType : 'json',
        success : function(data){
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
            $('#lawgrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $.messager.progress('close');

            jQuery.ajax({
                type : 'get',
                contentType : 'application/json',
                url : 'listInform.do',
                data : {
                    trefresh:new Date().getTime(),
                    systemName:systemName,
                    page:1,
                    rows:10
                },
                dataType : 'json',
                success : function(data){

                    window.parent.document.getElementById("pIdTZCount").innerHTML=data.total;

                },
                error : function(data){

                }
            });

        },
        error : function(data){
            //exception in java
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });

    // url = '/zhfz/bacs/inform/updateInform.do?id='+row.id;
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
    $('#informgrid').datagrid('load', {
        title:$('#s_title').textbox('getValue'),
        content:$('#s_content').textbox('getValue'),
        systemName:systemName,
        trefresh:new Date().getTime()

    });
}

function doClear() {
    $('#s_title').textbox('setValue','');
    $('#s_content').textbox('setValue','');
}