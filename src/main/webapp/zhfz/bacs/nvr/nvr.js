var typeData=[];
var editUrl;
//保存添加NVR的数量
var nvrCount = 0;
$(function(){
    $('#certificategrid').datagrid({
        iconCls:'icon-datagrid',
        region: 'center',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: '加载中.....',
        method: 'get',
        url: 'list.do',
        queryParams : {
            trefresh:new Date().getTime()
        },
        sortName: 'type',
        sortOrder: 'asc',
        remoteSort: false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'areaName',title:'所属办案场所',width:8},
            {field:'areaId',title:'所属办案场所ID',width:10,hidden:true},
            {field:'name',title:'NVR名称',width:10},
            {field:'ip',title:'IP地址',width:10},
            {field:'ipBack',title:'备用IP',width:10},
            {field:'account',title:'账号',width:10},
            {field:'password',title:'密码',width:10},
            {field:'port',title:'端口',width:10},
            {
                field:'vender',
                title:'设备厂商',
                width:10,
                formatter : function(value){
                    var str = "";
                    if(value==1)
                        str = "海康威视";
                    if(value==2)
                        str = "大华";
                    if(value==3)
                        str = "天地伟业";
                    if(value==4)
                        str = "科达";
                    if(value==5)
                        str = "宇视";
                    return str;
                }
            },
            {
                field:'type',
                title:'类型',
                width:10,
                formatter : function(value){
                    var str = "";
                    if(value==1)
                        str = "海康威视";
                    if(value==2)
                        str = "大华";
                    if(value==3)
                        str = "天地伟业";
                    if(value==4)
                        str = "科达";
                    if(value==5)
                        str = "宇视";
                    return str;
                }
            },
            {field:'id',title:'操作',width:8,
                formatter:function(value,row,index){
                    var e='';
                    var d='';
                    if(isGridButton("nvrEdit")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="nvrEdit('+index+')">修改</a></span>';
                    }
                    if(isGridButton("nvrRemove")){
                        d ='<span class="spanRow"><a href="#" class="griddel" name="nobutton" onclick="nvrRemove('+value+')">删除</a></span>';
                    }
                    return e+d;
                }}
        ]],
        pagination:true,
        pageList: [10,20,30,40,50,100],
        rownumbers:true,
        toolbar:'#certificateToolbar'
    });
    var p = $('#certificategrid').datagrid('getPager');

    $(p).pagination({
        onBeforeRefresh:function(){
            //alert('before refresh');
            $('#certificategrid').datagrid('reload',{
                type : $('#s_certificate_name').val(),
                codeKey:$('#s_certificate_val').val(),
                trefresh:new Date().getTime()});
            return false;
        }
    });
    loadArea();
    $('#fudong').remove();
});
//加载管辖的办案场所
function loadArea(){
    $('#area_cmb,#area_cmb_id,#n_area_cmb_id').combobox({
        url:'../combobox/getAllInterrogateAreaName.do',
        valueField:'id',
        textField:'value',
        onLoadSuccess: function(data){
            $('#area_cmb,#area_cmb_id,#n_area_cmb_id').combobox('setValue', data[0].id);
        },
        onChange:function(newValue,oldValue){
        }
    });
}
function saveEnterprise(){
    if(nvrCount <= 0){
        $.messager.alert('错误', '请添加NVR详细信息');
        return;
    }
    if(!checkForm('#certificate_form')){
        return;
    }
    if(!checkForm('#certificateDetail_form')){
        return;
    }
    var nvrIpList = $(".nvrIp");
    if(nvrIpList.length != null){
        for(var i = 0;i<nvrIpList.length; i++){
            if(!checkIp(nvrIpList[i].value)){
                $.messager.alert('错误', 'IP格式：192.168.1.20，IP地址格式不正确!');
                nvrIpList[i].focus();
                return;
            }
        }
    }
    var nvrIpBackList = $(".nvrIp_back");
    if(nvrIpBackList.length != null){
        for(var i = 0;i<nvrIpBackList.length; i++){
            if(!checkIp(nvrIpBackList[i].value)){
                $.messager.alert('错误', 'IP格式：192.168.1.20，IP地址格式不正确!');
                $(nvrIpBackList[i]).focus();
                return;
            }
        }
    }
    var entForm = $('#certificate_form').serializeObject();
    var listForm = $('#certificateDetail_form').serializeObject();
    entForm["listForm"] = listForm;
    entForm["nvrCount"] = nvrCount;
    entForm["port"] = $("#port").val();
    var enterpriseinfo = JSON.stringify(entForm);
    console.log(enterpriseinfo);
    $.messager.progress({
        title:'请等待',
        msg:'添加/修改数据中...'
    });
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : "nvrAddList.do",
        data : enterpriseinfo,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#certificate_dialog').dialog('close');
        },
        error : function(data){
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}
//保存新增/编辑信息
function saveNvrEdit(){

    if(!checkForm('#nvr_edit_form')){
        return;
    }

    if(!checkIp($('#nvr_edit_ip').val())){
        $.messager.alert('错误', 'IP格式：192.168.1.20，IP地址格式不正确!');
        $('#nvr_edit_ip').focus();
        return;
    }

    if($('#nvr_edit_ipBack').val().length>0&&!checkIp($('#nvr_edit_ipBack').val())){
        $.messager.alert('错误', 'IP格式：192.168.1.20，备用IP地址格式不正确!');
        $('#nvr_edit_ipBack').focus();
        return;
    }
    var nvr_edit_form = $('#nvr_edit_form').serializeObject();
    var enterpriseinfo = JSON.stringify(nvr_edit_form);
    $.messager.progress({
        title:'请等待',
        msg:'添加/修改数据中...'
    });
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : editUrl,
        data : enterpriseinfo,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#nvr_edit_dialog').dialog('close');
        },
        error : function(data){
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}
//批量新增添加详细信息
function nvrAddListDetail(){
    var number = $("#addNumber").val();
    var Str="";
    if(number == "" ){
        $.messager.alert('错误', '请输入添加NVR个数!');
    }
    $.messager.progress({
        title:'请等待'
    });
    for(var i=0;i<number;i++){
        nvrCount += 1;
        Str += '<div id="nvrList'+i+'" name="nvrList" class="fitem" style="border: #046aa0 solid 1px;height: 45px;padding: 0px;margin: 0px;text-align: left;line-height: 45px">';
        Str += '<label style="width: 80px;padding-left: 20px">NVR名称:</label> <input style="width: 150px;" id="name'+i+'" name="name" class="easyui-validatebox " required="true" value="nvr'+nvrCount+'">';
        Str += '<label style="width: 80px;margin-left: 20px;">IP:</label> <input  style="width: 150px;" id="ip'+i+'" name="ip" class="easyui-validatebox nvrIp" required="true" value="192.168.0.1">';
        Str += '<label style="width: 80px;margin-left: 20px;">备用IP:</label> <input  style="width: 150px;" id="ip_back'+i+'" name="ip_back" class="easyui-validatebox nvrIp_back" value="192.168.0.1" >'
        Str += '<img style="margin-left: 35px;height: 20px;display: inline;" src="../../../js/jquery-easyui-1.7.0/themes/icons/cancel.png" onclick="deleteNvrList('+"'nvrList"+i+"'"+')">';
        Str += ' </div>';
    }
    $("#certificateDetail_form").append(Str);
    $.parser.parse($("#certificateDetail_form"));
    $.messager.progress('close');
}
//删除批量新增的选中行
function deleteNvrList(nvrListId){
    document.getElementById("certificateDetail_form").removeChild(document.getElementById(nvrListId));
    nvrCount = nvrCount-1;
}
//IP地址校验
function checkIp(ip)
{
    var pcount = 0
    var ip_length = ip.length
    var ip_letters = "1234567890."
    for (p=0; p < ip_length; p++)
    {
        var ip_char = ip.charAt(p)
        if (ip_letters.indexOf(ip_char) == -1)
        {
            return false
        }
    }
    for (var u = 0; u < ip_length; u++) { (ip.substr(u,1) == ".") ? pcount++ : pcount }
    if(pcount != 3) { return false }
    firstp = ip.indexOf(".")
    lastp = ip.lastIndexOf(".")
    str1 = ip.substring(0,firstp)
    ipstr_tmp = ip.substring(0,lastp)
    secondp = ipstr_tmp.lastIndexOf(".")
    str2 = ipstr_tmp.substring(firstp+1,secondp)
    str3 = ipstr_tmp.substring(secondp+1,lastp)
    str4 = ip.substring(lastp+1,ip_length)
    if (str1 == '' || str2 == '' || str3 == '' || str4 == '') { return false }
    if (str1.length > 3 || str2.length > 3 || str3.length > 3 || str4.length > 3) { return false }
    if (str1 <= 0 || str1 > 255) { return false }
    else if (str2 < 0 || str2 > 255) { return false }
    else if (str3 < 0 || str3 > 255) { return false }
    else if (str4 < 0 || str4 > 255) { return false }
    return true
}
//打开批量新增弹框
function nvrAddList(){
    showDialog('#certificate_dialog','批量新增NVR');
    $('#certificate_form').form('clear');
    $('#certificateDetail_form').form('clear');
    $("#certificateDetail_form").html("");
    $("#type_cmb_id").combobox('setValue','1');
    nvrCount = 0;
}
//单个新增NVR
function nvrAdd(){
    $('#nvr_edit_form').form('clear');
    showDialog('#nvr_edit_dialog','新增NVR设备');
    $('#nvr_edit_form').form('clear');
    editUrl = 'nvrAdd.do';
}

function nvrEdit(index){
    var row = $('#certificategrid').datagrid('getRows')[index];
    showDialog('#nvr_edit_dialog','编辑NVR数据');
    $('#nvr_edit_form').form('clear');
    $('#nvr_edit_form').form('load',row);
    editUrl = 'update.do?id='+row.id;
}

function nvrRemove(value){
    $.messager.confirm('删除确认','是否确定删除此数据？',function(r){
        if (r){
            $.messager.progress({
                title:'请等待',
                msg:'删除数据中...'
            });
            jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                url : 'delete.do',
                data : '{"id":'+value+'}',
                dataType : 'json',
                success : function(data){
                    $.messager.show({
                        title:'提示',
                        msg:data.content,
                        timeout:3000
                    });
                    $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
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
    $('#certificategrid').datagrid('load', {
        areaId : $('#area_cmb').combobox('getValue'),
        name:$('#s_name').textbox('getValue'),
        trefresh:new Date().getTime()
    });
}

function doClear() {
    $('#area_cmb').combobox('setValue','');
    $('#s_name').textbox('setValue','');
}
