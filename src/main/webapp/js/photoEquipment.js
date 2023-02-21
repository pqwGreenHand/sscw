//保存签字类型
var SignType = "";
//启动设备预览
function GWQ_StartPreview(type){
    var ret=GWQ.GWQ_StartPreview(type);
    ReturnTip(ret);
}
/*function GWQ_StartPreviewShow(type){
    var ret=GWQ.GWQ_StartPreviewShow(type);
    ReturnTip(ret);
}*/
//预览回调
//保存预览的base64位数据
var preViewBase64 = "";
function afterPreview(imgBase64,imgId){
    preViewBase64 = imgBase64;
    $("#"+imgId).attr("src","data:image/jpeg;base64,"+imgBase64)
}
//抓拍预览照片
function getPreview(){
    if(preViewBase64 != null && preViewBase64 != ""){
        return preViewBase64;
    } else{
        return null;
    }
}
//关闭预览
function closePreview(type){
    var ret=GWQ.GWQ_StopPreview(type);
    ReturnTip(ret);
    preViewBase64 = "";
}
//启动双目摄像头
function startPhotoFrameWithIndex(){
    var browsern = browser["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    GWQ_CancelOperate();
    $.messager.progress({
        title: '请等待',
        msg: '请在设备上拍照并提交...'
    });
    var ret=GWQ.DoGWQ_GetFrameWithIndex(2);
    if(ret!=0){
        $.messager.progress('close');
        $.messager.alert('失败','返回错误码为'+ret);
    }
}
//关闭设备一体机所有当前操作
function GWQ_CancelOperate()
{
    var ret=GWQ.GWQ_CancelOperate();
    if(ret==0)  {
    } else
    {
        ReturnTip(ret);
    }
}
//启动抓拍照片
function GWQ_StartGetFrame()
{
    var browsern = myBrowser()["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    GWQ_CancelOperate();
    var ret=GWQ.GWQ_StartGetFrame();
    ReturnTip(ret);
}
//启动双目摄像头
function startPhotoFrameWithIndex(){
    var browsern = myBrowser()["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    $.messager.progress({
        title: '请等待',
        msg: '请在设备上拍照并提交...'
    });
    var ret=GWQ.DoGWQ_GetFrameWithIndex(2);
    if(ret!=0){
        $.messager.progress('close');
        $.messager.alert('失败','返回错误码为'+ret);
    }
}
//启动人证识别
function startPersonPhotoDistinguish(){
    var browsern = myBrowser()["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    var ret=GWQ.DoGWQ_StartFaceWithImg("");
    if(ret!=0){
        $.messager.alert('失败','返回错误码为'+ret);
    }
}
//获取抓拍图片
function GWQ_StopGetFrame()
{
    var browsern = myBrowser()["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.alert('提示','请使用IE浏览器');
        return null;
    }
    var ret=GWQ.GWQ_StopGetFrame();
    if(ret=="") {
        alert("获取图片失败");
        return null;
    }
    else {
        return ret;
    }
}
//打印
function printChoose(serialId,uuid,areaId,fileType){
    $.messager.progress({
        title: '请等待',
        msg: '下载中...'
    });
    jQuery.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: '../../common/signfile/getNewPdf.do',
        data: {
            serialId:serialId,
            fileType:fileType,
            trefresh:new Date().getTime()
        },
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            if(data.error){
                $.messager.alert("提示",data.content);
                return;
            }else {
                var row = data.callbackData.entity;
                if(row){
                    fileUtils.load('ba',fileTypeEntity.FILETYPRQZ,uuid,areaId,row.fileName);
                }
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
    $.messager.progress('close');
}
//下载PDF
function downLoadPdf(serialId,uuid,areaId,fileType){
    var url = "../../common/signfile/getNewPdf.do?serialId="+serialId+"&fileType="+fileType+"&trefresh="+new Date().getTime()
    jQuery.ajax({
        type: 'GET',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            if(data.error){
                $.messager.alert("提示",data.content);
                return;
            }else {
                var rowData = data.callbackData.entity;
                if(rowData){
                    fileUtils.load('ba',fileTypeEntity.FILETYPRQZ,uuid,areaId,rowData.fileName);
                }
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}
//判断浏览器
function myBrowser() {
    var rMsie = /(msie\s|trident\/7)([\w\.]+)/;
    var rTrident = /(trident)\/([\w.]+)/;
    var rEdge = /(chrome)\/([\w.]+)/;//IE
    var rFirefox = /(firefox)\/([\w.]+)/;  //火狐
    var rOpera = /(opera).+version\/([\w.]+)/;  //旧Opera
    var rNewOpera = /(opr)\/(.+)/;  //新Opera 基于谷歌
    var rChrome = /(chrome)\/([\w.]+)/; //谷歌
    var rUC = /(chrome)\/([\w.]+)/;//UC
    var rMaxthon = /(chrome)\/([\w.]+)/;//遨游
    var r2345 =  /(chrome)\/([\w.]+)/;//2345
    var rQQ =  /(chrome)\/([\w.]+)/;//QQ
    //var rMetasr =  /(metasr)\/([\w.]+)/;//搜狗
    var rSafari = /version\/([\w.]+).*(safari)/;
    var ua = navigator.userAgent.toLowerCase();
    var matchBS, matchBS2;
    //IE 低版
    matchBS = rMsie.exec(ua);
    if (matchBS != null) {
        matchBS2 = rTrident.exec(ua);
        if (matchBS2 != null) {
            switch (matchBS2[2]) {
                case "4.0":
                    return {
                        browser:
                            "Microsoft IE",
                        version: "IE: 8"  //内核版本号
                    };
                    break;
                case "5.0":
                    return {
                        browser:
                            "Microsoft IE",
                        version: "IE: 9"
                    };
                    break;
                case "6.0":
                    return {
                        browser:
                            "Microsoft IE",
                        version: "IE: 10"
                    };
                    break;
                case "7.0":
                    return {
                        browser:
                            "Microsoft IE",
                        version: "IE: 11"
                    };
                    break;
                default:
                    return {
                        browser:
                            "Microsoft IE",
                        version: "Undefined"
                    };
            }
        } else {
            return {
                browser: "Microsoft IE",
                version: "IE:"+matchBS[2] || "0"
            };
        }
    }
    //IE最新版
    matchBS = rEdge.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "Microsoft Edge",
            version: "Chrome/"+matchBS[2] || "0"
        };
    }
    //UC浏览器
    matchBS = rUC.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "UC",
            version: "Chrome/"+matchBS[2] || "0"
        };
    }
    //火狐浏览器
    matchBS = rFirefox.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "火狐",
            version: "Firefox/"+matchBS[2] || "0"
        };
    }
    //Oper浏览器
    matchBS = rOpera.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "Opera",
            version: "Chrome/"+matchBS[2] || "0"
        };
    }
    //遨游
    matchBS = rMaxthon.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "遨游",
            version: "Chrome/"+matchBS[2] || "0"
        };
    }
    //2345浏览器
    matchBS = r2345.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "2345",
            version: "Chrome/ "+matchBS[2] || "0"
        };
    }
    //QQ浏览器
    matchBS = rQQ.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        return {
            browser: "QQ",
            version: "Chrome/"+matchBS[2] || "0"
        };
    }
    //Safari（苹果）浏览器
    matchBS = rSafari.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent)) && (!(window.chrome)) && (!(window.opera))) {
        return {
            browser: "Safari",
            version: "Safari/"+matchBS[1] || "0"
        };
    }
    //谷歌浏览器
    matchBS = rChrome.exec(ua);
    if ((matchBS != null) && (!(window.attachEvent))) {
        matchBS2 = rNewOpera.exec(ua);
        if (matchBS2 == null) {
            return {
                browser: "谷歌",
                version: "Chrome/"+matchBS[2] || "0"
            };
        } else {
            return {
                browser: "Opera",
                version: "opr/"+matchBS2[2] || "0"
            };
        }
    }
}
//签字后回调函数
function AfterSignStart(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64,fileType){
    if(ErrorCode==0) {
        if(fileType != null && fileType != ''){
            var  base64 = SignPdfBase64;
            var form=new FormData();
            var formData = new FormData();
            formData.append("file", convertBase64UrlToBlob(SignPdfBase64),'a.pdf');
            formData.append("serialId",commonSerialId);
            formData.append('fileType',fileType);
            formData.append('trefresh',new Date().getTime());
            $.ajax({
                url: '../../common/signfile/uploadPdf.do',
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                processData: false, // 告诉jQuery不要去处理发送的数据
                success: function (data) {
                    if(data.error){
                        $.messager.progress('close');
                        $.messager.alert('错误', data.content);
                    }else {
                        $.messager.show({
                            title:'提示',
                            msg:"签字成功！",
                            timeout:3000
                        });
                        $.messager.progress('close');
                    }
                },
                error: function(data) {
                    $.messager.progress('close');
                    $.messager.alert('错误', data);
                }
            })
        } else{
            $.messager.progress('close');
            $.messager.alert('失败','word类型为空，请刷新页面');
        }

    } else if(ErrorCode==-9) {
        $.messager.progress('close');
        $.messager.alert('取消','设备已取消');
    }  else  {
        $.messager.progress('close');
        $.messager.alert('失败','返回错误码为'+ErrorCode);
    }
}

//调用人证一体机签章签字
//保存相关入区id
var commonSerialId = "";
function startSign(url,serialId,fileType){
    url = url+"&trefresh="+new Date().getTime();
    $.messager.progress({
        title: '请等待',
        msg: '请在设备上签字并提交'
    });
    var myBrow = myBrowser();
    var browsern = myBrow["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.progress('close');
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    jQuery.ajax({
        type: 'get',
        contentType: 'application/json',
        url: '../../common/signfile/getNewPdf.do',
        data: {
            serialId:serialId,
            fileType:fileType,
            trefresh:new Date().getTime()
        },
        dataType: 'json',
        success: function (data) {
            commonSerialId = serialId;
            if(data.error){
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    dataType:"json",
                    url: url,
                    success: function (data) {
                        var pdfPathBase64 = GWQ.WordToPDFBase64(data.callbackData);
                        if(pdfPathBase64 == null || pdfPathBase64 == ""){
                            $.messager.alert('失败','请检查设备连接，或者联系工程师');
                            $.messager.progress('close');
                            return;
                        }
                        var ret=GWQ.DoGWQ_StartSignEx(pdfPathBase64,"0,0,300,700|1,0,300,700",1,"D://sign.png",9999);
                        if(ret!=0){
                            ReturnTip(ret);
                            $.messager.progress('close');
                        }
                    },
                    error: function (data) {
                        console.log(data);
                        $.messager.alert('错误',data);
                        $.messager.progress('close');
                    }
                })
            } else {
                var pdfPathBase64 = data.callbackData.fileBase64;
                if(pdfPathBase64 == null || pdfPathBase64 == ""){
                    $.messager.alert('失败','获取文件数据失败');
                    $.messager.progress('close');
                    return;
                }
                var ret=GWQ.DoGWQ_StartSignEx(pdfPathBase64,"0,0,300,700|1,0,300,700",1,"D://sign.png",9999);
                if(ret!=0){
                    ReturnTip(ret);
                    $.messager.progress('close');
                }
            }
        },
        error:function(data){
            console.log(data);
            $.messager.alert("错误",data);
        }
    })
}

//调用人证一体机签章签字
//保存相关入区id
function cxstartSign(url,serialId,fileType){
    url = url+"&trefresh="+new Date().getTime();
    $.messager.progress({
        title: '请等待',
        msg: '请在设备上签字并提交'
    });
    var myBrow = myBrowser();
    var browsern = myBrow["browser"];
    if(!(browsern == "Microsoft IE")) {
        $.messager.progress('close');
        $.messager.alert('提示','请使用IE浏览器');
        return;
    }
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType:"json",
        url: url,
        success: function (data) {
            commonSerialId = serialId;
            var pdfPathBase64 = GWQ.WordToPDFBase64(data.callbackData);
            if(pdfPathBase64 == null || pdfPathBase64 == ""){
                $.messager.alert('失败','请检查设备连接，或者联系工程师');
                $.messager.progress('close');
                return;
            }
            var ret=GWQ.DoGWQ_StartSignEx(pdfPathBase64,"0,0,300,700|1,0,300,700",1,"D://sign.png",9999);
            if(ret!=0){
                ReturnTip(ret);
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
//转换64位编码
function convertBase64UrlToBlob(urlData){
    var bytes=window.atob(urlData);        //去掉url的头，并转换为byte

    //处理异常,将ascii码小于0的转换为大于0
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }
    return new Blob( [ab] , {type : 'application/pdf'});
}
//设备返回参数解析
function ReturnTip(ErrorCode)
{
    if(ErrorCode==0) {
    } else if(ErrorCode==-1) {
        alert("参数错误");
    }   else if (ErrorCode==-2)  {
        alert("超时");
    }  else if(ErrorCode==-3)  {
        alert("设备连接错误");
    }  else if(ErrorCode==-4) {
        alert("发送数据失败");
    } else if(ErrorCode==-5) {
        alert("读取数据失败");
    }  else if(ErrorCode==-6)  {
        alert("文件操作失败");
    } else if(ErrorCode==-7) {
        alert("设备返回错误信息");
    } else if(ErrorCode==-9) {
        alert("取消");
    }else {
        alert("失败，返回错误码为"+ErrorCode);
    }
}