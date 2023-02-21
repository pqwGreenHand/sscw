//保存签字类型
var SIGNTYPE = "";

var CurrFlag = "";
//台账打印
function  PringTaizhang(serialId) {
    $("#PringTaizhangDialog").show();
    $("#PringTaizhangDiv1").show();
    $("#PringTaizhangSerialId").val(serialId);
    CurrFlag = typeof currenMap != "undefined" ? true : false;
}
//台账签字
function  SignTaizhang(serialId) {
    $("#SignTaizhangDialog").show();
    $("#SignTaizhangDiv").show();
    $("#SignTaizhangSerialId").val(serialId);
    CurrFlag = typeof currenMap != "undefined" ? true : false;
}
//五合一台账签字
function WUSign(){
    SIGNTYPE = '1';
    var serialId = $("#SignTaizhangSerialId").val();
    var url = "downloadWordBase64.do?exportSerialId="+serialId+"&trefresh="+new Date().getTime();
    signStart(url,serialId);
}
//入区台账签字
function RuSign(){
    var serialId = $("#SignTaizhangSerialId").val();
    if(CurrFlag){
        SignType = '0';
        var row = currenMap[serialId];
        console.log("入区台账签字"+serialId+"SignType:"+SignType)
        var url = "../../lawdocProcess/downloadBase64.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
        startSign(url,serialId,SignType);
    } else{
        //强制让表格选中一行
        console.log("入区台账签字"+serialId);
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            SignType = '0';
            var url = "../../lawdocProcess/downloadBase64.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
            startSign(url,serialId,SignType);
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}
//出区台账签字
function CuSign(){
    if(CurrFlag){
        $.messager.alert('提示',"嫌疑人未出区，无法签字");
    } else{
        var serialId =  $("#SignTaizhangSerialId").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            if(row.status < 11){
                $.messager.alert('提示',"嫌疑人未出区，无法签字");
                return;
            }else{
                SIGNTYPE = '2';
                var url = "../../lawdocProcess/downloadBase64.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialNo="+row.serialNo;
                signStart(url,serialId);
            }
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}
//五合一台账打印
function WuPrint(){
    if(CurrFlag){
        var serialId =  $("#PringTaizhangSerialId").val();
        var row = currenMap[serialId];
        if( row != null && row.id != null){
            printChoose(row.id,row.uuid,row.areaId,1);
        } else {
            $.messager.alert('提示',"数据为空，请刷新页面");
        }
    } else{
        var serialId =  $("#PringTaizhangSerialId").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            printChoose(row.id,row.uuid,row.areaId,1);
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}
//入区台账打印
function RuPrint(){
    if(CurrFlag){
        var serialId =  $("#PringTaizhangSerialId").val();
        var row = currenMap[serialId];
        if( row != null && row.id != null){
            printChoose(row.id,row.uuid,row.areaId,0);
        } else {
            $.messager.alert('提示',"数据为空，请刷新页面");
        }
    } else{
        var serialId =  $("#PringTaizhangSerialId").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            printChoose(row.id,row.uuid,row.areaId,0);
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}
//出区台账打印
function CuPrint(){
    if(CurrFlag){
        $.messager.alert('提示',"嫌疑人未出区，无法打印出区台账");
    } else{
        var serialId =  $("#PringTaizhangSerialId").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            if(row.status < 11){
                $.messager.alert('提示',"嫌疑人未出区，无法签字");
                return;
            }else{
                printChoose(row.id,row.uuid,row.areaId,2);
            }
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}

//启动签字
function signStart(url,serialId){
    $.messager.progress({
        title: '请等待',
        msg: '请在设备上签字并提交'
    });
    var browsern = browser["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.progress('close');
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    baseSerialId = serialId;
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType:"json",
        url: url,
        success: function (data) {
            baseSerialId = serialId;
            var pdfPathBase64 = GWQ.WordToPDFBase64(data.callbackData);
            if(pdfPathBase64 == null || pdfPathBase64 == ""){
                $.messager.alert('失败','请检查设备连接，或者联系工程师');
                $.messager.progress('close');
                return;
            }
            // var ret=GWQ.DoGWQ_StartSign(pdfPathBase64,"","0,0,300,700","预留字段",100);
            var ret=GWQ.DoGWQ_StartSignEx(pdfPathBase64,"0,0,300,700|1,0,300,700",1,"D://sign.png",100);
            if(ret!=0){
                $.messager.alert('失败','返回错误码为'+ret);
                $.messager.progress('close');
            }
        },
        error: function (data) {
            console.log(data);
            $.messager.alert('错误',data);
            $.messager.progress('close');
        }
    })
}
// //签字提交后调用函数
// function afterSign(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64){
//     alert(SIGNTYPE)
//     if(ErrorCode==0) {
//         if(SIGNTYPE != null && SIGNTYPE != ''){
//             var  base64 = SignPdfBase64;
//             var form=new FormData();
//             var formData = new FormData();
//             formData.append("file", convertBase64UrlToBlob(SignPdfBase64),'a.pdf');
//             formData.append("serialId",baseSerialId);
//             formData.append('fileType',SIGNTYPE);
//             $.ajax({
//                 url: '../../common/signfile/uploadPdf.do',
//                 type: 'POST',
//                 data: formData,
//                 async: false,
//                 cache: false,
//                 contentType: false, // 告诉jQuery不要去设置Content-Type请求头
//                 processData: false, // 告诉jQuery不要去处理发送的数据
//                 success: function (data) {
//                     if(data.error){
//                         $.messager.progress('close');
//                         $.messager.alert('错误', data.content);
//                     }else {
//                         $.messager.show({
//                             title:'提示',
//                             msg:"签字成功！",
//                             timeout:3000
//                         });
//                         $.messager.progress('close');
//                     }
//                 },
//                 error: function(data) {
//                     $.messager.progress('close');
//                     $.messager.alert('错误', data);
//                 }
//             })
//         } else{
//             $.messager.progress('close');
//             $.messager.alert('失败','word类型为空，请刷新页面');
//         }
//
//     } else if(ErrorCode==-9) {
//         $.messager.progress('close');
//         $.messager.alert('取消','设备已取消');
//     }  else  {
//         $.messager.progress('close');
//         $.messager.alert('失败','返回错误码为'+ErrorCode);
//     }
// }
//重新入区台账签字
function cxRuSign(){
    var serialId = $("#cxSignTaizhangSerialId").val();
    if(CurrFlag){
        SignType = '0';
        var row = currenMap[serialId];
        var url = "../../lawdocProcess/downloadBase64.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
        cxstartSign(url,serialId,SignType);
    } else{
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            SignType = '0';
            var url = "../../lawdocProcess/downloadBase64.do?number=14&name=违法犯罪嫌疑人进入办案场所凭证&type=1&userId=0&dataId="+row.id+"&serialNo="+row.serialNo+"&serialId="+row.id;
            cxstartSign(url,serialId,SignType);
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}
//五合一台账签字
function cxWUSign(){
    SignType = '1';
    var serialId = $("#SignTaizhangSerialId").val();
    var url = "downloadWordBase64.do?exportSerialId="+serialId+"&trefresh="+new Date().getTime();
    cxstartSign(url,serialId,SignType);
}
//出区台账签字
function cxCuSign(){
    if(CurrFlag){
        $.messager.alert('提示',"嫌疑人未出区，无法签字");
    } else{
        var serialId =  $("#SignTaizhangSerialId").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
            if(row.status < 11){
                $.messager.alert('提示',"嫌疑人未出区，无法签字");
                return;
            }else{
                SignType = '2';
                var url = "../../lawdocProcess/downloadBase64.do?number=2&name=涉案人员进出办案区登记台账&type=1&userId=0&serialNo="+row.serialNo;
                cxstartSign(url,serialId,SignType);
            }
        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}
//临时出区台账签字
function cxlsCuSign(){
    if(CurrFlag){
        $.messager.alert('提示',"嫌疑人未出区，无法签字");
    } else{
        var serialId =  $("#SignTaizhangSerialId").val();
        //强制让表格选中一行
        $('#datagrid').datagrid('selectRecord',serialId);
        //获取选中行数据
        var row = $('#datagrid').datagrid("getSelected");
        if(row != null){
//			 if(row.status != 10){
//	                $.messager.alert('提示',"嫌疑人未临时出区，无法签字");
//	                return;
//	            }else{
            SignType = '99';
            var url = "../../lawdocProcess/downloadBase64.do?number=99&name=涉案人员临时出区登记台账&type=1&userId=0&serialNo="+row.serialNo;
            cxstartSign(url,serialId,SignType);
//	            }

        }else{
            $.messager.alert('提示',"未选中数据，请刷新页面！");
        }
    }
}