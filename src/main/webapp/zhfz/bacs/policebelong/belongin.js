$(function(){
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            var data = checkRingNo($("#cuffNumber").textbox('getValue'),1);
            console.log(data)
            readRingNo(data.callbackData.cuffNo);
        }
    })
});
$(function(){
    //民警下拉框
    $('#serialId').combogrid({
        panelWidth:170,
        mode: 'remote',
        url: '../../common/combogrid/getPoliceSerialNo.do',
        idField: 'policeId',
        textField: 'name',
        columns: [[
            {field:'name',title:'民警姓名',width:70},
            {field:'certificateNo',title:'民警警号',width:70},
            {field:'policeCuffNo1',title:'卡片编号',width:100,hidden:true},
            {field:'areaId',title:'办案区域id',hidden:true},
            {field:'id',title:'id',hidden:true},
            {field:'caseId',title:'案件id',hidden:true},
            {field:'personId',title:'人员id',hidden:true}
        ]],
        onChange: function (data, date1) {
            $('#lockerId').combobox('reload');
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.policeId, trefresh: new Date().getTime()
                });
            }
        }
    });
    //民警存物
    $('#detid').datagrid({
        title:'存储民警物品',
        iconCls:'icon-datagrid',
        region: 'center',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: 'Loading...',
        method: 'get',
        queryParams:{enpId:'ss',trefresh:new Date().getTime()},
        url: 'listAllBelongdet2.do',
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {title:'id',field:'detail_id',width:80,sortable:true,hidden:true},
            {title:'belongingsId',field:'belongingsId',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'personName',title:'民警姓名',width:30},
            {field:'lockerId',title:'储物柜编号',width:60,
                formatter:function(field, rec, index){
                    return rec.cabinetGroup+" "+rec.cabinetNo+"号柜";
                }},
            {field:'name',title:'物品名称',width:50},
            {field:'detailCount',title:'数量',width:20},
            {field:'unit',title:'单位',width:40},
            {field:'saveMethod',title:'保管措施',width:80},
            {field:'description',title:'特征',width:140},
            {field:'id',title:'操作',width:80,
                formatter:function(field, rec, index){
                    if (isGridButton("pdetRemove")) {
                        var e = '<span class="spanRow"><a id="sdetRemove" href="#" class="griddel" onclick="detRemove('+index+')">删除</a></span>';
                    }else{
                        var e = '';
                    }
                    if (isGridButton("pdetEdit")) {
                        var f = '<span class="spanRow"><a id="sdetEdit" href="#" class="gridedit" onclick="detEdit('+index+')">修改</a></span>';
                    }else{
                        var f = '';
                    }
                    return e + f;
                }
            }
        ]],
        rownumbers:true,
        toolbar:'#areaToolbar',
        onLoadSuccess:function(data){
            if (null != data && data.rows.length > 0) {
                var lockerId = data.rows[0].lockerId;
                $('#lockerId').combobox('setValue', lockerId);
            }else{
                var s = $('#id1').val();
                if(s==null || s==""){
                    $('#lockerId').combobox('setValue', '');
                }
            }
        }
    });
    var p = $('#detid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(){
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            $('#detid').datagrid('reload',{
                enpId : row.policeId,
                trefresh:new Date().getTime()
            });
            return false;
        }
    });
    var lockerId_=getUrlParam('s_lockerId');
    var lockerId_ulr="listBelongLockerBox.do?timeSign="+getTimeSign();
    $('#lockerId').combobox({
        url:lockerId_ulr,
        valueField:'id',
        textField:'value'
    });
    if(lockerId_!=null){
        $('#lockerId').combobox('setValue',lockerId_);
    }
    $.messager.progress({
        title:'请等待',
        msg:'正在加载控件...'
    });
    $.messager.progress('close');
});

//新增保存
function belongsave(){
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    $('#caseId').val(row.caseId);
    $('#areaId').val(row.areaId);
    $('#policeId').val(row.id);
    var data = $('#detinfo_form').serializeObject();
    var rows = $("#belonggrid").datagrid("getRows");
    console.log(data);
    var a = {};
    a['list'] = rows;
    a['data'] = data;
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : 'belongsave.do',
        data : JSON.stringify(a),
        dataType : 'json',
        success : function(data){
            if(data.error){
                $.messager.alert('提示', data.content);
            } else{
                $.messager.progress({
                    title:'请等待',
                    msg:'添加存物信息中...'
                });
            }
            $.messager.progress('close');
            $('#detid').datagrid('reload', {
                enpId : row.policeId,
                trefresh:new Date().getTime()
            });
            $('#names1').prop('value', '');
            $('#detailCount').prop('value', '');
            $('#description').textbox('setValue','');
            $("#belonggrid").datagrid('loadData',{total:0,rows:[]})
            $("#inPopup").hide();
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '保存失败（belongsave）!');
        }
    });
}
//编辑随身物品详情
function detEdit(index){
    if(isGridButton("sdetEdit")){
        var rowData = $('#detid').datagrid('getRows')[index];
        showDialog('#det_dialog','编辑随身物品详情');
        $('#det_form').form('clear');
        $('#det_form').form('load',rowData);
        url = 'editBelongdet.do?id='+rowData.id;
    }else{
        $.messager.alert('提示', '权限不足，请更换账号或联系管理员!');
    }
}
//查看图片
function showImages(){
    $("#showPic_dialog").html("");
    var rowData = $('#detid').datagrid('getRows');
    if(!rowData||rowData.length==0){
        $.messager.alert('提示', '请扫描手环或者选择专属编号！');
        return;
    }
    var belongingsId = rowData[0].belongingsId;
    $.messager.progress({
        title:'请等待',
        msg:'加载数据中...'
    });
    jQuery.ajax({
        type : 'POST',
        url : 'getImages.do',
        data : {belongingsId:belongingsId},
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            for(var i=0;i<data.length;i++){
                var url = fileUtils.url('ba','MJ',data[i].uuid,data[i].areaId,data[i].url);
                $("#showPic_dialog").append('<img width="450" src="'+url+'" /><br/><br/>');
            }
            showDialog('#showPic_dialog','照片');
        },
        error : function(data){
            $.messager.progress('close');
            //exception in java
            $.messager.alert('Error', '未知错误!');
        }
    });
}
//编辑随身物品详情保存
function savedet(){
    if(!checkForm('#det_form')){
        return;
    }
    var entForm = $('#det_form');
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
            $.messager.alert(data.title, data.content);
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            $('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});
            $('#det_dialog').dialog('close');
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('Error', '未知错误!'+data.content);
        }
    });
}
//删除随身物品详情
function detRemove(index){
    if(isGridButton("pdetRemove")){
        $.messager.confirm('删除确认','是否确定删除此数据？',function(r){
            var rowData = $('#detid').datagrid('getRows')[index];
            var belongingsId = rowData.belongingsId;
            var detailId=rowData.id;
            if (r){
                jQuery.ajax({
                    type : 'POST',
                    url : 'removeBelongdet.do?timeSign='+getTimeSign(),
                    data : {belongingsId:belongingsId,detailId:detailId},
                    dataType : 'json',
                    success : function(data){
                        $.messager.alert(data.title, data.content);
                        var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
                        var row = cg.datagrid('getSelected');
                        $('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});// reload the data
                        $.messager.progress('close');
                    },
                    error : function(data){
                        $.messager.alert('错误', '删除失败（removeBelongdet）!');
                    }
                });

            }
        });
    }else{
        $.messager.alert('提示', '权限不足，请更换账号或联系管理员!');
    }
}
function areaSearch() {
    $('#belongid').datagrid('load', {
        lockerId_t : $('#s_lockerId').val(),
        trefresh:new Date().getTime()
    });
}
function areadoClear() {
    $('#names1').prop('value', '');
    $('#detailCount').prop('value', '');
    $('#description').textbox('setValue','');
}
//预览
function securityPrint(index){

    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');


     //var rowData = $('#detid').datagrid('getRows');

    fileUtils.read("/zhfz/lawdocProcess/download.do?number=53&name=民警随身物品登记&type=1&userId=0&policeId="+row.policeId+"&serialId="+row.id);
                                       $.messager.progress('close');
}
//下载
function securitydown(index){

    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');


     //var rowData = $('#detid').datagrid('getRows');

        var enterpriseinfo={"id":null,"serialId":row.id};
        var json_data = JSON.stringify(enterpriseinfo);
        $.messager.progress({
            title:'请等待',
            msg:'打印预览中...'
        });
        var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
        lawdocInfo["number"]=53;
        lawdocInfo["name"]="民警随身物品登记";
        lawdocInfo["type"]=1;
        lawdocInfo["userId"]=0;
        lawdocInfo["dataId"]=row.id;
        lawdocInfo["serialNo"]=row.serialNo;
        lawdocInfo["serialId"]=row.id;
        lawdocInfo["policeId"]=row.policeId;

        $('#number').val(53);
            $('#name').val('民警随身物品登记');
            $('#type').val(1);
            $('#userId').val(0);
            $('#dataId').val(row.id);
            $('#serialNo').val(row.serialNo);
            $('#serialId').val(row.id);
            $('#policeId').val(row.policeId);

        var lawdocInfojson = JSON.stringify(lawdocInfo);
        document.getElementById("lawdocInfo_belonginfo").submit();

    //fileUtils.read("/zhfz/lawdocProcess/download.do?number=53&name=民警物品登记&type=1&userId=0&policeId="+row.policeId+"&serialId="+row.id);
     $.messager.progress('close');
}

//打开拍照
function showphotowid(){
    var cg  = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(row==null){
        $.messager.alert('提示', '请先选择专属编号!');
    }else{
        var shu = $('#detid').datagrid('getRows').length;
        if(shu==0){
            $.messager.alert('提示', '请先存物!');
        }else{
            var height = 400;
            var width = 500;
            var screenParam = "left=0,top=0,scrollbars,resizable=yes,toolbar=no',height=" + height + ",width=" + width;
            var winOpen = window.open("about:blank","",screenParam);
            winOpen.location = "../common/photo.jsp?type=3&serialid="+row.id+"&serialNo="+row.serialNo;//type=1 随身拍照
        }
    }
}
//开柜
function belongsavebox(){
    var cuffNumber=$('#cuffNumber').textbox('getValue');
    var ss=$('#lockerId').combobox('getValue');
    if(ss==''||ss==null){
        $.messager.alert('提示', '请选择储物柜编号！');
        return false;
    }
    jQuery.ajax({
        type : 'GET',
        url :'belongsavebox.do?lockid='+ss,
        dataType : 'json',
        success : function(data){
            if(data && data.error){
                $.messager.alert('错误','开柜失败！'+ data.content);
            }
        },
        error : function(data){
            $.messager.alert('错误', '开柜失败（belongsavebox）!');
        }
    });
}
//手环扫描
function readRingNo(number){
    if(number){
        $('#cuffNumber').textbox('setValue',number);
        //选择唯一id
        $('#serialId').combogrid({
            panelWidth:360,
            mode: 'remote',
            url: '../../common/combogrid/getPoliceSerialNo.do?cuffNo='+number,
            idField: 'id',
            textField: 'name',
            columns: [[
                {field:'name',title:'姓名',width:60},
                {field:'certificateNo',title:'证件号码',width:150},
                {field:'policeName1',title:'主办民警',width:70},
                {field:'policeName2',title:'协办民警',width:70},
                {field:'serialNo',title:'入区编号',width:135},
                {field:'areaId',title:'办案区域id',hidden:true},
                {field:'caseId',title:'案件id',hidden:true},
                {field:'personId',title:'人员id',hidden:true}
            ]],
            onLoadSuccess: function (data) {
                var id = data.rows[0].id ? data.rows[0].id : null;
                if (id != null && id != "") {
                    $('#serialId').combogrid('setValue',id);
                }
            }
        });
    }else{
        $.messager.alert('提示', '读卡失败!');
        $('#cuffNumber').textbox('setValue','');
    }
}
//打开新增弹框
function belongAddList(){
    $("#inPopup").show();
    $('#belong_form').form('clear');
    $('#belongDetail_form').form('clear');
    $("#belongDetail_form").html("");
    $('#belonggrid').datagrid({
        iconCls : 'icon-datagrid',
        region : 'center',
        width : '100%',
        height : '100%',
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        loadMsg : 'Loading...',
        method : 'get',
        remoteSort : false,
        queryParams : {
            trefresh:new Date().getTime()
        },
        singleSelect : true,
        frozenColumns : [ [
            {
                title : 'id',
                field : 'id',
                width : 10,
                sortable : true,
                hidden : true
            } ] ],
        columns : [ [
            {
                field : 'name',
                title : '物品名称',
                width : '25%',
            },
            {
                field : 'description',
                title : '特征',
                width : '25%',

            },
            {field : 'detailCount',title : '数量',width : '20%'},
            {field : 'unit',title : '单位',width : '10%'},
            {
                field : 'id',
                title : '操作',
                width : '20%',
                align : 'left',
                formatter : function(value, row, index) {
                   /* return '<span class="spanRow"><a href="#" class="griddel" onclick="deleteRow('
                        + index + ')">删除</a></span>';*/
                    return '<span class="spanRow"><a href="#" class="griddel" onclick="deleteRow(this)">删除</a></span>';
                }
            } ] ],
        pagination : false,
        pageList : [ 10, 20, 30, 40, 50, 100 ],
        rownumbers : true,
        // 当数据加载成功时触发
        onLoadSuccess : function() {
        },
    });
}
//关闭弹出框
function closeMpopup(){
    $(".m-popup").hide();
}
//随身物品增加
function belongAdd(){
    if($("#names1").val()==null||$("#names1").val()==""||$("#detailCount").val()==null||$("#detailCount").val()==""||$("#unit").val()==null||$("#unit").val()==""){
        $.messager.alert('提示', '信息填写不完整!');
        return ;
    }
    var data = $('#detinfo_form').serializeObject();
// 动态给table添加一行数据
    $('#belonggrid').datagrid('appendRow',{
        name: data.name,
        description: data.description,
        detailCount: data.detailCount,
        unit : data.unit
    });
    $('#unit').combobox('clear');
    $('#detailCount').textbox('setValue','');
    $('#description').textbox('setValue','');
    $('#names1').combobox('clear');
}
// 删除存物信息
function deleteRow(target){
    // $('#belonggrid').datagrid('deleteRow',index);
    $('#belonggrid').datagrid('deleteRow',getRowIndex(target));
}
function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

//PDF打印
function signPrint(index){
    var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(row != null) {
        printChoose(row.id, row.uuid, row.areaId, 6);
    }else {
        $.messager.alert("提示","请选择嫌疑人");
    }
}
//签字
function securitySign(){
    var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(row != null){
        var url ="../../lawdocProcess/downloadBase64.do?number=53&name=民警随身物品登记&type=1&userId=0&policeId="+row.policeId+"&serialId="+row.id;
        startSign(url,row.id);
    }else {
        $.messager.alert("提示","请选择嫌疑人");
    }
}
//pdf下载
function PdfDownLoad(){
    var cg = $('#serialId').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(row != null){
        downLoadPdf(row.id, row.uuid, row.areaId, 7);
    }else {
        $.messager.alert("提示","请选择嫌疑人");
    }
}