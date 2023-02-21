$(function(){
	 $(document).keydown(function (event) {
	        if (event.keyCode == 13) {
	            var data = checkRingNo($(event.target).val(),0);
	            if(data.callbackData){
	                readRingNo(data.callbackData.cuffNo);
	            }else{
	                $.messager.show({
	                    title:'提示',
	                    msg:"无效的手环！！",
	                    timeout:3000
	                });
	                $(event.target).val('');
	            }
	        }
	    });
	 
    $('#names1').combobox({
        onChange: function (n,o) {
            var str = "";
            if(n=="手机"){
                str="部";
            }else if(n=="人民币"){
                str="张";
            }else if(n=="银行卡"){
                str="张";
            }else if(n=="钥匙"){
                str="串";
            }else if(n=="身份证"){
                str="张";
            }else if(n=="钱包"){
                str="个";
            }else if(n=="打火机"){
                str="个";
            }else if(n=="香烟"){
                str="盒";
            }else if(n=="手表"){
                str="个";
            }else if(n=="项链"){
                str="串";
            }else if(n=="戒指"){
                str="个";
            }else if(n=="耳环"){
                str="个";
            }else{
                str="";
            }
            $('#unit').textbox('setValue', str);
        }
    });
});
$(function(){
    
    //嫌疑人随身储物
    $('#detid').datagrid({
        title:'存储涉案物品',
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
        queryParams:{trefresh:new Date().getTime()},
        url: 'listAllExhibitdet2.do',
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {title:'id',field:'detail_id',width:80,sortable:true,hidden:true},
            {title:'exhibitId',field:'exhibitId',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'personName',title:'嫌疑人名称',width:30},
            {field:'lockerId',title:'储物柜编号',width:60,
                formatter:function(field, rec, index){
                    return rec.cabinetGroup+" "+rec.cabinetNo+"号柜";
                }},
            {field:'name',title:'物品名称',width:50},
            {field:'detailCount',title:'数量',width:20},
            {field:'unit',title:'单位',width:40},
            {field:'description',title:'特征',width:140},
            {field:'id',title:'操作',width:80,
                formatter:function(field, rec, index){
                    if (isGridButton("edetRemove")) {
                        var e = '<span class="spanRow"><a id="sdetRemove" href="#" class="griddel" onclick="detRemove('+index+')">删除</a></span>';
                    }else{
                        var e = '';
                    }
                    if (isGridButton("edetEdit")) {
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
                enpId : row.id,
                trefresh:new Date().getTime()
            });
            return false;
        }
    });
    var lockerId_=getUrlParam('s_lockerId');
    var lockerId_ulr="listExhibitLockerBox.do?timeSign="+getTimeSign();
    $('#lockerId').combobox({
        url:lockerId_ulr,
        valueField:'id',
        textField:'value'
    });
    if(lockerId_!=null){
//		lockerId_ulr+="&lockerId="+lockerId_;
        $('#lockerId').combobox('setValue',lockerId_);
        //$('#lockerId').val(lockerId_)
    }

    $.messager.progress({
        title:'请等待',
        msg:'正在加载控件...'
    });

    $.messager.progress('close');
    /*$('#fudong').remove();*/
    //checkPrint();
    
    //嫌疑人下拉框
    $('#serialIdQuery').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getSuspectSerialNo.do?type=belongin',
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'name', title: '姓名', width: 60},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {field: 'policeName1', title: '主办民警', width: 70},
            {field: 'policeName2', title: '协办民警', width: 70},
            {field: 'serialNo', title: '入区编号', width: 135},
            {field: 'areaId', title: '办案区域id', hidden: true},
            {field: 'caseId', title: '案件id', hidden: true},
            {field: 'personId', title: '人员id', hidden: true}
        ]],
        onChange: function (data, date1) {
            var cg = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, trefresh: new Date().getTime()
                });
               // $('#serialId').combogrid('setValue',row.id);
            }
        }
    });
 
    
  //嫌疑人下拉框
    $('#serialId').combogrid({
        panelWidth: 360,
        mode: 'remote',
        url: '../../common/combogrid/getSuspectSerialNo.do?type=exhibitin',
        idField: 'id',
        textField: 'name',
        columns: [[
            {field: 'name', title: '姓名', width: 60},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {field: 'policeName1', title: '主办民警', width: 70},
            {field: 'policeName2', title: '协办民警', width: 70},
            {field: 'serialNo', title: '入区编号', width: 135},
            {field: 'areaId', title: '办案区域id', hidden: true},
            {field: 'caseId', title: '案件id', hidden: true},
            {field: 'personId', title: '人员id', hidden: true}
        ]],
        onChange: function (data, date1) {
            $('#lockerId').combobox('reload');
            var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
            var row = cg.datagrid('getSelected');
            if (row != null) {
                $('#detid').datagrid('load', {
                    enpId: row.id, trefresh: new Date().getTime()
                });
                $('#serialIdQuery').combogrid('setValue', row.id);
            }
            
        }
    });
    
});

//新增保存
function belongsave(){
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    $('#caseId').val(row.caseId);
    $('#areaId').val(row.areaId);
    var data = $('#detinfo_form').serializeObject();
    var rows = $("#belonggrid").datagrid("getRows");
    var a = {};
    a['list'] = rows;
    a['data'] = data;
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : 'exhibitsave.do',
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
                enpId : row.id,
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
        url = 'editExhibitdet.do?id='+rowData.id;
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
    var exhibitId = rowData[0].exhibitId;
    $.messager.progress({
        title:'请等待',
        msg:'加载数据中...'
    });
    jQuery.ajax({
        type : 'POST',
        url : 'getImages.do',
        data : {exhibitId:exhibitId},
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
            for(var i=0;i<data.length;i++){
                var url = fileUtils.url('ba','SA',data[i].uuid,data[i].areaId,data[i].url);
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
            // $.messager.alert(data.title, data.content);
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
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
    if(isGridButton("sdetRemove")){
        $.messager.confirm('删除确认','是否确定删除此数据？',function(r){
            var rowData = $('#detid').datagrid('getRows')[index];
            var exhibitId = rowData.exhibitId;
            var detailId=rowData.id;
            if (r){
                jQuery.ajax({
                    type : 'POST',
                    url : 'removeExhibitdet.do?timeSign='+getTimeSign(),
                    data : {exhibitId:exhibitId,detailId:detailId},
                    dataType : 'json',
                    success : function(data){
                        $.messager.alert(data.title, data.content);
                        var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
                        var row = cg.datagrid('getSelected');
                        $('#detid').datagrid('reload',{enpId:row.id,trefresh:new Date().getTime()});// reload the data
                        $.messager.progress('close');
                    },
                    error : function(data){
                        $.messager.alert('错误', '删除失败（removeExhibitdet）!');
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
function securityPrint(index){
    var cg = $('#serialId').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    var enterpriseinfo={"id":null,"serialId":row.id};
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title:'请等待',
        msg:'打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"]=48;
    lawdocInfo["name"]="涉案物品登记";
    lawdocInfo["type"]=1;
    lawdocInfo["userId"]=0;
    lawdocInfo["dataId"]=row.id;
    lawdocInfo["serialNo"]=row.serialNo;
    lawdocInfo["serialId"]=row.id;
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    $('#number').val(9);
    $('#name').val('涉案物品登记');
    $('#type').val(1);
    $('#userId').val(0);
    $('#dataId').val(row.id);
    $('#serialNo').val(row.serialNo);
    $('#serialId').val(row.id);
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    //document.getElementById("lawdocInfo_belonginfo").submit();// ********
     fileUtils.read("/zhfz/lawdocProcess/download.do?number=48&name=涉案物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
    $.messager.progress('close');
}
function securityDownLoad(index){
    var cg  = $('#serialIdQuery').combogrid('grid');	// 获取数据表格对象
    var row = cg.datagrid('getSelected');
    console.log(row);
    if(!row || !row.id){
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
        return;
    }
    var enterpriseinfo={"id":null,"serialId":row.id};
    var json_data = JSON.stringify(enterpriseinfo);
    $.messager.progress({
        title:'请等待',
        msg:'打印预览中...'
    });
    var lawdocInfo = $('#lawdocInfo_belonginfo').serializeObject();
    lawdocInfo["number"]=10;
    lawdocInfo["name"]="涉案物品临时保管台账";
    lawdocInfo["type"]=2;
    lawdocInfo["userId"]=0;
    lawdocInfo["dataId"]=row.id;
    lawdocInfo["serialNo"]=row.serialNo;
    lawdocInfo["serialId"]=row.id;
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    $('#number').val(10);
    $('#name').val('涉案物品临时保管台账');
    $('#type').val(2);
    $('#userId').val(0);
    $('#dataId').val(row.id);
    $('#serialNo').val(row.serialNo);
    $('#serialId').val(row.id);
    var lawdocInfojson = JSON.stringify(lawdocInfo);
    document.getElementById("lawdocInfo_belonginfo").submit();// ********
    //fileUtils.read("/zhfz/lawdocProcess/download.do?number=48&name=涉案物品登记&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
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
            winOpen.location = "../common/photo.jsp?type=2&serialid="+row.id+"&serialNo="+row.serialNo;//type=1 随身拍照
        }
    }
}
//开柜
function belongsavebox(){
   /* var cuffNumber=$('#cuffNumber').textbox('getValue');
    var ss=$('#lockerId').combobox('getValue');
    if(ss==''||ss==null){
        $.messager.alert('提示', '请选择储物柜编号！');
        return false;
    }*/
    //取第一条
	var ss = "";
    var row =$('#detid').datagrid('getRows')[0];
	if(row){
		ss = row.lockerId;
	}
    jQuery.ajax({
        type : 'GET',
        url :'exhibitsavebox.do?lockid='+ss,
        dataType : 'json',
        success : function(data){
            if(data && data.error){
                $.messager.alert('错误','开柜失败！'+ data.content);
            }
        },
        error : function(data){
            $.messager.alert('错误', '开柜失败（exhibitsavebox）!');
        }
    });
}
//手环扫描
function readRingNo(number){
    if(number){
        var cuff = $('#cuffInfo').serializeObject();
        cuff["cuffNo"]=number;
        cuff["type"]=0;
        var jsonrtinfo = JSON.stringify(cuff);
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : '../serial/getSerialByCuffNo.do',
            data : jsonrtinfo,
            dataType : 'json',
            async: false,
            success : function(data){
                var id=data.callbackData?data.callbackData.id:null;
                if(id!=null&&id!=""){
                    $('#cuffNumber').textbox('setValue',data.callbackData.cuffNo);
                    $('#serialId').combogrid('setValue',id);
                    $('#cuffNumberQuery').textbox('setValue',data.callbackData.cuffNo);
                    $('#serialIdQuery').combogrid('setValue',id);
                    $.ajax({
                        async: false,
                        type : 'POST',
                        url :'../belong/getCurrentStatus.do',
                        dataType : 'json',
                        data : {
                            serialId : id
                        },
                        success : function(dataresult) {
                            if (dataresult.content=="已安检") {
                            	$('#cuffNumberQuery').textbox('setValue','');
                            }
                            else{
                                if(dataresult.content=="未安检"){
                                    $.messager.alert('警告', '请先录入嫌疑人的人身安全检查信息!');
                                    $('#cuffNumber').textbox('setValue','');
                                    $('#cuffNumberQuery').textbox('setValue','');
                                }
                            }
                        },
                        error : function() {
                            $.messager.alert('警告', '请先录入嫌疑人的人身安全检查信息!');
                            $('#cuffNumber').textbox('setValue','');
                            $('#cuffNumberQuery').textbox('setValue','');
                        }
                    });
                }else{
                    $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                    $('#cuffNumber').textbox('setValue','');
                    $('#cuffNumberQuery').textbox('setValue','');
                }
            },
            error : function(data){
                $.messager.alert('错误', '未知错误!');
                $('#cuffNumber').textbox('setValue','');
                $('#cuffNumberQuery').textbox('setValue','');
            }
        });
    }else{
        $.messager.alert('提示', '读卡失败!');
        $('#cuffNumber').textbox('setValue','');
        $('#cuffNumberQuery').textbox('setValue','');
    }
}
function formClear(){
	 $('#cuffNumber').textbox('setValue','');
	 $('#serialId').combogrid('setValue','');
	 $('#names1').combobox('setValue', '');
	 $('#detailCount').textbox('setValue','');
	 $('#unit').combobox('setValue', '');
	 $('#description').textbox('setValue','');
	 $('#belonggrid').datagrid('loadData', { total: 0, rows: [] });
	  
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
	formClear();
    $('#cuffNumber').textbox().next('span').find('input').focus();
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
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(row != null) {
        printChoose(row.id, row.uuid, row.areaId, 6);
    }else {
        $.message.alert("提示","请选择嫌疑人");
    }
}
//签字
function securitySign(){
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(!row || !row.id){
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
    }else{
        var url ="../../lawdocProcess/downloadBase64.do?number=10&name=涉案物品临时保管台账&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
        SignType = 6;
        startSign(url,row.id,6);
    }
}
//重新签字
function cxsecuritySign(){
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(!row || !row.id){
        $.messager.alert('提示', '请扫描手环或选择嫌疑人!');
    }else{
        var url ="../../lawdocProcess/downloadBase64.do?number=10&name=涉案物品临时保管台账&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
        SignType = 6;
        cxstartSign(url,row.id,6);
    }
}

//pdf下载
function PdfDownLoad(){
    var cg = $('#serialIdQuery').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');
    if(row != null){
        downLoadPdf(row.id, row.uuid, row.areaId, 6);
    }else {
        $.messager.alert("提示","请选择嫌疑人");
    }
}