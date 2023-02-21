$(function () {
    initEntranceData();

    $('#outReception').combogrid({
        method:'get',
        url:'../receptionentrance/list.do',
        panelWidth:360,
        mode:'remote',
        idField:'id',
        textField:'name',
        columns: [[
            {field:'name',title:'姓名',width:50},
            {field:'reason',title:'事由',width:50},
            {field:'receiver',title:'经办人',width:80},
            {field:'certificateNo',title:'证件号码',width:180}
        ]],
        onLoadSuccess: function(data){

        },
        onChange:function(newValue,oldValue){

        },

    })

})


function out() {
    $("#outReception").combogrid("clear");
   $('#outPopup').show();
}

function closeMpopupOut(){
    $("#outPopup").hide();
}

// 出区
function outArea(){
    var id = $('#outReception').combogrid('getValue');
    if(id){
        jQuery.ajax({
            type : 'POST',
            async : false,
            dataType : 'json',
            url : contextPath + '/zhfz/bacs/receptionentrance/updateOutTime.do',
            data : {'receptionEntranceId':id},
            success : function(data){
                initEntranceData();
                $('#outReception').combogrid('grid').datagrid('load',{
                    trefresh:new Date().getTime()
                })
                $('#outReception').combogrid('setValue',"");
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
                $("#outPopup").hide();
            },
            error : function(data){
                $.messager.progress('close');
                $.messager.alert(data.title, data.content);
            }
        });
    }else{
        $.messager.alert("提示", "请选择需要出区的来访人员");
    }
}
