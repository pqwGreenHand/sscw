var typeData=[];
//保存单个摄像头处理的url
var url="";
//保存添加NVR的数量
var cameraCount = 0;
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
        sortName: 'id',
        sortOrder: 'asc',
        remoteSort: false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'areaName',title:'所属办案场所',width:130},
            {field:'areaId',title:'所属办案场所ID',width:20,hidden:true},
            {field:'roomName',title:'所属功能室',width:100},
            {field:'roomId',title:'所属功能室ID',width:20,hidden:true},
            {field:'nvrName',title:'所属NVR设备',width:100},
            {field:'nvrId',title:'所属NVR设备ID',width:20,hidden:true},
            {field:'name',title:'名称',width:80},
            {field:'ip',title:'IP地址',width:70},
            {field:'port',title:'端口',width:70},
            {field:'account',title:'账号',width:70},
            {field:'password',title:'密码',width:70},
            {
                field:'type',
                title:'类型',
                width:60,
                formatter : function(value){
                    var str = "";
                    if(value==0)
                        str = "全景摄像头";
                    if(value==1)
                        str = "对焦摄像头";
                    return str;
                }
            },
            {field:'cameraNo',title:'监控点编号',width:80},
            {field:'channel',title:'通道',width:50},
            {
                field:'vender',
                title:'厂商',
                width:100,
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
            {field:'screen',title:'是否主画面',width:80},
            {
                field:'download',
                title:'是否下载',
                width:60,
                formatter : function(value){
                    var str = "";
                    if(value==0)
                        str = "否";
                    if(value==1)
                        str = "是";
                    return str;
                }
            },
            {field:'id',title:'操作',width:100,
                formatter:function(value,row,index){
                    var e='';
                    var d='';
                    if(isGridButton("cameraEdit")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="cameraEdit('+index+')">修改</a></span>';
                    }
                    if(isGridButton("cameraRemove")){
                        d ='<span class="spanRow"><a href="#" class="griddel" name="nobutton" onclick="cameraRemove('+value+')">删除</a></span>';
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
        }
    });
    loadArea();
    $('#fudong').remove();
});
//加载管辖的办案场所
function loadArea(){
    $('#area_cmb,#area_cmb_id,#n_area_cmb_id,#list_area_cmb_id').combobox({
        url:'../combobox/getAllInterrogateAreaName.do',
        valueField:'id',
        textField:'value',
        onLoadSuccess: function(data){

        },
        onChange:function(newValue,oldValue){
            //三个下拉框相互管理，三个为一组
            var cobId = this.id;
            if(cobId == "area_cmb"){
                //当清除查询条件的时候不关联
                if($(this).combobox("getValue")){
                    loadRoom(newValue,"room_cmb");
                    loadNvr(newValue,"nvr_cmb");
                }
            }else if(cobId == "area_cmb_id"){
                //当清除表单的时候不关联
                if($(this).combobox("getValue")) {
                    loadRoom(newValue, "room_cmb_id");
                    loadNvr(newValue, "nvr_cmb_id");
                }
            }else if(cobId == "list_area_cmb_id"){
                //当清除表单的时候不关联
                if($(this).combobox("getValue")) {
                    loadNvr(newValue, "list_nvr_cmb_id");
                    loadListRoom(newValue);
                }
            }
        }
    });
}



//加载办案场所对应的功能室
function loadRoom(areaId,roomCobId){
    $('#'+roomCobId).combobox({
        url:'../combobox/listAllRoomNameCombobox.do?areaId='+areaId,
        valueField:'id',
        textField:'value',
        onLoadSuccess: function(data){
            if(data!= null && !(roomCobId == "room_cmb_id" && url.indexOf("update") >= 0)){
                $('#'+roomCobId).combobox('setValue', data[0].id);
            }
           $("#room_cmb").combobox("setValue","");
        },
        onChange:function(newValue,oldValue){

        }
    });
}
//批量添加时加载办案场所对应的功能室
function loadListRoom(area){
    $(".list_room_id").combobox({
        url: '../combobox/listAllRoomNameCombobox.do?areaId=' + area,
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {
            if (data != null ) {
                $(".list_room_id").combobox('setValue', data[0].id);
            }
        },
        onChange: function (newValue, oldValue) {

        }
    });
}
//加载NVR下拉框
function loadNvr(areaId,nvrCobId){
    $('#'+nvrCobId).combobox({
        url:'../nvr/comboNvr.do?areaId='+areaId,
        valueField:'id',
        textField:'name',
        onLoadSuccess: function(data){
            if(data!= null && data.length != 0 && nvrCobId != "nvr_cmb_" ){
                $('#'+nvrCobId).combobox('setValue', data[0].id);
            }
            $("#nvr_cmb").combobox("setValue","");
        },
        onChange:function(newValue,oldValue){
        }
    });
}
//保存单个摄像头修改/添加的信息
function saveEnterprise(type){
    if(!checkForm('#camera_form')){
        return;
    }

    if(!checkIp($('#ip').val())){
        $.messager.alert('错误', 'IP格式：192.168.1.20，IP地址格式不正确!');
        $('#ip').focus();
        return;
    }

    if($("#is_download").is(':checked')){
        $("#is_download").attr("value",1);
    }else{
        $("#is_download").attr("value",0);
    }
    if($("#is_screen").is(':checked')){
        $("#is_screen").attr("value","1");//主画面
    }else{
        $("#is_screen").attr("value","0");//副画面
    }

    var entForm = $('#camera_form');
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
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
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#camera_dialog').dialog('close');
        },
        error : function(data){
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}
//批量保存摄像头
function saveCarmeraList(){
    if(cameraCount <= 0){
        $.messager.alert('错误', '请添加摄像头详细信息');
        return;
    }
    if(!checkForm('#certificate_form')){
        return;
    }
    if(!checkForm('#certificateDetail_form')) {
        return;
    }

    var cameraIpList = $(".cameraIp");
    if(cameraIpList.length != null){
        for(var i = 0;i<cameraIpList.length; i++){
            if(!checkIp(cameraIpList[i].value)){
                $.messager.alert('错误', 'IP格式：192.168.1.20，IP地址格式不正确!');
                cameraIpList[i].focus();
                return;
            }
        }
    }
    var screenList = $(".list_screen");
    if(screenList.length != null){
        for(var i = 0;i<screenList.length; i++){
            if($(screenList[i]).is(':checked')){
                $(screenList[i]).prop('value',"1");//主画面
            }else{
                $(screenList[i]).prop('value',"0");//副画面
            }
            $(screenList[i]).prop("checked","checked");
        }
    }
    var downloadList = $(".list_download");
    if(downloadList.length != null){
        for(var i = 0;i<downloadList.length; i++){
            if($(downloadList[i]).is(':checked')){
                $(downloadList[i]).prop('value',"1");//下载
            }else{
                $(downloadList[i]).prop('value',"0");//不下载
            }
            $(downloadList[i]).prop("checked","checked");
        }
    }
    var cameraForm = $('#certificate_form').serializeObject();
    cameraForm["port"] = $("#port").val();
    var listForm = $('#certificateDetail_form').serializeObject();
    $.messager.progress({
        title:'请等待',
        msg:'添加/修改数据中...'
    });
    cameraForm["listForm"] = listForm;
    cameraForm["cameraCount"] = cameraCount;
    var enterpriseinfo = JSON.stringify(cameraForm);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "addCameraList.do",
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#certificategrid').datagrid('reload', {trefresh: new Date().getTime()});// reload the data
            $('#certificate_dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    })
}

//摄像头保存修改内容
function cameraEdit(index){
    var row = $('#certificategrid').datagrid('getRows')[index];
    url = 'update.do?id='+row.id;
    showDialog('#camera_dialog','编辑摄像头信息');
    $('#camera_form').form('clear');
    $('#camera_form').form('load',row);
    $("#area_cmb_id").combobox('setValue',row.areaId);
    $("#room_cmb_id").combobox('setValue',row.roomId);
    $('#nvr_cmb_id').combobox('setValue',row.nvrId);
    if(row.download==1){
        $("#is_download").attr("checked",true);
        $("#is_download").prop("checked", true);
    }else{
        $("#is_download").attr("checked",false);
        $("#is_download").prop("checked",false);
    }
    if(row.screen=="主画面"){
        $("#is_screen").attr("checked",true);
        $("#is_screen").prop("checked", true);
    }else{
        $("#is_screen").attr("checked",false);
        $("#is_screen").prop("checked", false);
    }

}
//删除摄像头
function cameraRemove(value){
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
function cameraAddDetail(){
    var number = $("#addNumber").val();
    var Str="";
    if(number == "" ){
        $.messager.alert('错误', '请输入添加摄像头个数!');
    }
    $.messager.progress({
        title:'请等待',
        msg:'请等待...'
    });
    for(var i=0;i<number;i++){
        cameraCount = cameraCount + 1;
        Str += '<div id="cameraList'+i+'" class="fitem" style="border: #046aa0 solid 1px;height: 90px;padding: 0px;margin: 0px;text-align: left;line-height: 45px">';
        Str += '<label style="width:80px;margin-left: 15px">摄像头名称:</label> <input name="name" class="easyui-validatebox" required="true" value="摄像头'+cameraCount+'">';
        Str += '<label style="margin-left: 15px;width:80px;">摄像头通道号:</label> <input name="channel" class="easyui-numberbox"required="true" value="80">';

        Str += '<label  style="margin-left: 15px;width:80px;">摄像头IP:</label> <input name="ip" class="easyui-validatebox cameraIp" required="true" value="192.168.0.1"><br/>'
        Str += '<label style="width:80px;margin-left: 15px">监控点编号:</label> <input name="camera_no" class="easyui-numberbox"required="true" value="80">';
        Str += '<label style="margin-left: 15px;width:80px;">所在房间:</label> <select style="width: 170px;"  name="room_id" class="easyui-combobox list_room_id"required="true" editable="true"></select>';
        Str += '<label style="margin-left: 15px;width:80px;">类型:</label><select style="width:170px" editable="false" name="type" required="true" class="easyui-combobox">';
        Str += '<option value="0">全景摄像头</option> <option value="1">对焦摄像头</option></select>';
        Str += '<input style="margin-left: 15px" type="checkbox" name="screen" class="list_screen"/>是否主画面<input class="list_download" type="checkbox" name="download" "/>是否下载';
        Str += '<img style="margin-left: 35px;height: 20px;display: inline;" src="../../../js/jquery-easyui-1.7.0/themes/icons/cancel.png" onclick="deleteCameraList('+"'cameraList"+i+"'"+')">';
        Str += ' </div>';
    }
    $("#certificateDetail_form").append(Str);
    $.parser.parse($("#certificateDetail_form"));
    var listAreaid = $("#list_area_cmb_id").combobox('getValue');
    if(listAreaid != null && listAreaid != "") {
        loadListRoom(listAreaid);
    }
    $.messager.progress('close');
}
function deleteCameraList(nvrListId){
    document.getElementById("certificateDetail_form").removeChild(document.getElementById(nvrListId));
    cameraCount = cameraCount-1;
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



function cameraAdd(){
    showDialog('#camera_dialog','新增摄像头信息');
    $('#camera_form').form('clear');
    url="add.do";

}
function cameraListAdd(){
    showDialog('#certificate_dialog','批量新增摄像头信息');
    $('#certificate_form').form('clear');
    $('#certificateDetail_form').form('clear');
    $("#certificateDetail_form").html("");
    $("#list_type_cmb_id").combobox('setValue','1');
    cameraCount = 0;
}
function doSearch() {
    $('#certificategrid').datagrid('load', {
        areaId:$('#area_cmb').combobox('getValue'),
        roomId:$('#room_cmb').combobox('getValue'),
        nvrId:$('#nvr_cmb').combobox('getValue'),
        name:$('#s_name').textbox('getValue'),
        trefresh:new Date().getTime()

    });
}

function doClear() {
    $('#area_cmb').combobox('setValue','');
    $('#room_cmb').combobox('setValue','');
    $('#nvr_cmb').combobox('setValue','');
    $('#s_name').textbox('setValue','');
}