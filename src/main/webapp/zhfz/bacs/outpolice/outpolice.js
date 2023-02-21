$(function(){
    $('#organizationId1').combobox({
        url:'../../common/combobox/listOutOrganizationNameCombobox.do',
        valueField:'id',
        textField:'value'
    });

    $('#user_datagrid').datagrid({
        iconCls:'icon-datagrid',
        region: 'center',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '正在加载...',
        method: 'get',
        url: 'getOutpoliceInfo.do',
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
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[

            {field:'realName',title:'姓名',width:150},
            {field:'organizationName',title:'单位名称',width:150},
            {field:'certificateNo',title:'警号',width:150},
            {field:'jobTitle',title:'职务',width:150},
            {field:'mobile',title:'手机号码',width:150},
            {field:'isActive',title:'是否激活',width:70,
                formatter:function(value,rec){
                    return formatTF(value);
                }
            },
            {field:'操作',title:'操作',width:200,
                formatter : function(value, row, index) {
                    var e ='';
                    var d ='';
                    if(isGridButton("editInfo")){
                        e = '<span class="spanRow"><a href="javascript:void(0)" class="gridedit" onclick="editInfo('
                            + index + ')">修改</a> </span>';
                    }
                 /*   if(isGridButton("deleteUser")){
                        d = '<span class="spanRow"><a href="javascript:void(0)" class="griddel" onclick="deleteUser('
                            + index + ')")">删除</a></span>';
                    }*/
                    return e ;
                }
            }
        ]],
        pagination:true,
        pageSize:15,
        pageList: [15,20,30,40,50,100],
        rownumbers:true,
        toolbar:'#toolbar',
        //行选择方法，进行条件触发
        // onSelect: function(rowIndex, rowData){
        //     rolegridData(rowData);
        // }

    });
    var p = $('#user_datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(){
            doSearch();
            return false;
        }
    });
    $('#s_certificate_type_id').combobox({
        url:'../../common/combobox/certificateTypes.do',
        valueField:'id',
        textField:'value',
    });
    $('#fudong').remove();
});





function doSearch() {
    $('#user_datagrid').datagrid('load', {
        organizationId : $('#organizationId1').combobox('getValue'),
        real_name_t : $('#s_real_name').textbox('getValue'),
        certificate_no_t : $('#s_certificate_no').textbox('getValue'),

        trefresh:new Date().getTime()
    });
}

function doClear() {

    $('#organizationId1').combobox('clear');
    $('#s_real_name').textbox('setValue','');
    $('#s_certificate_no').textbox('setValue','');
}



function addInfo(){

    $('#organizationId').combobox({
        url:'../../common/combobox/listOutOrganizationNameCombobox.do',
        valueField:'id',
        textField:'value'
    });
    $("#user_dialog").show();
    $('#user_form').form('clear');
    $('#sex').combobox('setValue',1);
    url = 'add.do';
}
function editInfo(index){


    //下拉选择部门--w.xb
    $('#organizationId').combobox({
        url:'../../common/combobox/listOutOrganizationNameCombobox.do',
        valueField:'id',
        textField:'value'
    });
    var row = $('#user_datagrid').datagrid('getRows')[index];
    if (row){
        $("#user_dialog").show();
        $('#user_form').form('clear');
        $('#certificateTypeId').combobox('setValue',row.certificateTypeId);
        $('#user_form').form('load',row);
        $('#certificateTypeId').combobox('setValue',row.certificateTypeId);
        $('#organizationId').combobox('setValue',row.organizationId);
        $('#roleId').combobox('setValue',row.roleId);
        $('#id').val(row.id);
        url = 'edit.do?id='+row.id;
    } else{
        $.messager.alert('提示', '请选择一行!');
    }
}
function saveData(){
    var sex = $('#sex').combobox('getValue');
    if($("#realName").val()!=null&&$("#realName").val()!=''&&
        sex!=null&&sex!=''&&
        $("#organizationId").combobox('getValue')!=null&&$("#organizationId").combobox('getValue')!=''&&
        $("#certificateNo").val()!=null&&$("#certificateNo").val()!=''
    ){
        var url="";
        var id =  $('#id').val();
        if(id!=null && id.length>0){
            url="";
            url ="edit.do";

            $.messager.progress({
                title:'请等待',
                msg:'修改用户中...'
            });
        }	else
        {	url="";
            url = "add.do";
            $.messager.progress({
                title:'请等待',
                msg:'添加用户中...'
            });
        }
        var elemUserinfo = $('#user_form');
        var userinfo = elemUserinfo.serializeObject();
        var jsonuserinfo = JSON.stringify(userinfo);
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : url,
            data : jsonuserinfo,
            dataType : 'json',
            success : function(data){
                $('#user_datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
                refrshAuthority();
                $.messager.progress('close');
                $('#user_dialog').hide();
                if(url == 'edit.do'){
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000
                    });
                }else{
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000
                    });
                    $("#realName").prop('value','');
                    $("#certificateNo").prop('value','');
                    $("#loginName").prop('value','');
                    $("#mobile").prop('value','');
                    $("#telephone").prop('value','');
                    $("#email").prop('value','');

                    $("#type").combobox('clear');
                    $("#description").prop('value','');
                }

            },
            error : function(data){
                $('#user_dialog').hide();
                $.messager.alert('错误', '修改失败!');
            }
        });

    }else{
        $.messager.alert('警告', '信息填写不完整!');
    }
}


function refrshAuthority() {
    $.ajax({
        //async: false,
        type: 'POST',
        url: '../../common/authority/refrshAuthority.do',
        dataType: 'json',
        success: function (data) {
            $.messager.show({
                title:'提示',
                msg:"刷新权限成功",
                timeout:3000
            });
        },
        error: function () {
            $.messager.alert('警告', '刷新失败（refrshAuthority）!');
        }
    })
}


function addOrg(){
    $('#UpOrgSelect').css('display','block');

    showDialog('#organization_dialog','新增办案单位');
    $('#organization_form').form('clear');
    url = 'addOrg.do';
}

function saveOrganization(){
    if(!checkForm('#organization_form')){
        return;
    }
    var orgForm = $('#organization_form');
    var organizationinfo = JSON.stringify(orgForm.serializeObject());
    $.messager.progress({
        title:'请等待',
        msg:'数据处理中...'
    });
    jQuery.ajax({
        type : 'Post',
        contentType : 'application/json',
        url : url,
        data :organizationinfo,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
            $('#organization_dialog').dialog('close');
            $('#organizationId').combobox('reload',
                {
                    trefresh:new Date().getTime()
                });
            $('#organizationId1').combobox('reload',
                {
                    trefresh:new Date().getTime()
                });
        },
        error : function(data){
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}