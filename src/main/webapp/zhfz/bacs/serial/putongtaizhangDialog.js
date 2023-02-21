var CurrFlag2 = "";
//台账打印
function  PringTZ(serialId) {
    $("#PringTaizhangDialog2").show();
    $("#PringTaizhangDiv2").show();
    $("#PringTaizhangSerialId2").val(serialId);
    CurrFlag2 = typeof currenMap != "undefined" ? true : false;
}

//五合一台账打印
function WuPrint2(){
        var serialId =  $("#PringTaizhangSerialId2").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            $.messager.progress({
                title:'请等待',
                msg:'打印预览中...'
            });
            fileUtils.read("/zhfz/lawdocProcess/download.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&serialUUID="+row.serialUUID+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
            $.messager.progress('close');
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
}
//入区台账打印
function RuPrint2(){
        var serialId =  $("#PringTaizhangSerialId2").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            $.messager.progress({
                title:'请等待',
                msg:'打印预览中...'
            });
            fileUtils.read("/zhfz/lawdocProcess/download.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&serialUUID="+row.uuid+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
            $.messager.progress('close');
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
}
//出区台账打印
function CuPrint2(){
    var serialId =  $("#PringTaizhangSerialId2").val();
    //强制让表格选中一行
    $('#datagrid').datagrid('selectRecord',serialId);
    //获取选中行数据
    var row = $('#datagrid').datagrid("getSelected");
    if(row != null){
        if(row.status < 11){
            $.messager.alert('提示',"嫌疑人未出区，无法打印出区台账");
            return;
        }else{
            $.messager.progress({
                title:'请等待',
                msg:'打印预览中...'
            });
            fileUtils.read("/zhfz/lawdocProcess/download.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialUUID="+row.uuid+"&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id);
            $.messager.progress('close');
        }
    }else{
        $.messager.alert('提示',"未选中数据，请刷新页面！");
    }
}