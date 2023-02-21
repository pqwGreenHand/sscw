//人像对比方法
function faceCompareButton(){
    //alert("==人像对比方法===")
    showDialog('#constactImage_dialog', '人像对比');
    $('#constactImage_dialog').form('clear');
    $('#constractResult').val('');
    $('#imageStatus1').val('未上传');
    $('#imageStatus2').val('未上传');
}


$(function() {
    $('#datagrid').datagrid({
        // title:'嫌疑人入区',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: '../../bacs/serial/list.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[{
            title: 'ID',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[
            {
                field: 'name',
                title: '嫌疑人',
                width: 40,
                formatter: function (value, rec) {
                    return '<font color="yellow">'
                        + formatterColunmVal(value, 5)
                        + '</font>';
                }
            },
            {
                field: 'inTime',
                title: '入区时间',
                width: 70,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {
                field: 'sex',
                title: '性别',
                width: 30,
                formatter: function (value, rec) {
                    if (value == 0) {
                        return "未知的性别";
                    }
                    if (value == 1) {
                        return "男";
                    }
                    if (value == 2) {
                        return "女";
                    }
                    if (value == 9) {
                        return "未说明的性别";
                    }
                    return "";
                }
            },
            {
                field: 'cuffNo',
                title: '手环编号',
                width: 95,
                hidden: true
            },
            {
                field: 'certificateNo',
                title: '证件号码',
                width: 100
            },
            {
                field: 'inUserRealName1',
                title: '民警',
                width: 30,
                hidden: true
            },
            {
                field: 'organization',
                title: '办案单位',
                width: 60
            },
            {
                field: 'sfxxcj',
                title: '是否信息采集',
                width: 70,
                formatter: function (value) {
                    if (value == 0) {
                        return '<font color="red">未采集</font>';
                    } else if (value == 1) {
                        return '<font color="#0fea67">已采集</font>';
                    } else if (value == 2) {
                        return '<font color="#0fea67">所内采集</font>';
                    }
                    else if (value == 3) {
                        return '<font color="#0fea67">办案中心内采集</font>';
                    }
                }
            },
            {
                field: 'status',
                title: '状态',
                width: 55,
                formatter: function (value, rec) {
                    // 0已入区 1已安检 2物品已暂存 3候问开始 4候问结束 5信息已采集
                    // 6审讯开始 7审讯结束 8物品已领取 9临时出区返回 10临时出区
                    // 11已出区
                    if (value == 0) {
                        return '<font color="green">已入区</font>';
                    } else if (value == 1) {
                        return '<font color="yellow">已安检</font>';
                    } else if (value == 2) {
                        return '<font color="orange">物品已暂存</font>';
                    } else if (value == 3) {
                        return '<font color="yellow">候问开始</font>';
                    } else if (value == 4) {
                        return '<font color="orange">候问结束</font>';
                    } else if (value == 5) {
                        return '<font color="yellow">信息已采集</font>';
                    } else if (value == 6) {
                        return '<font color="orange">审讯开始</font>';
                    } else if (value == 7) {
                        return '<font color="yellow">审讯结束</font>';
                    } else if (value == 8) {
                        return '<font color="orange">物品已领取</font>';
                    } else if (value == 9) {
                        return '<font color="gray">临时出区返回</font>';
                    } else if (value == 10) {
                        return '<font color="orange">临时出区</font>';
                    } else if (value == 11) {
                        return '<font color="gray">已出区</font>';
                    }
                }
            },
            {
                field: 'outReason',
                title: '出区去向',
                width: 70,
                formatter: function (value) {
                    return formatterColunmVal(value, 10);
                }
            },
            {
                field: 'outTime',
                title: '出区时间',
                width: 70,
                formatter: function (value, rec) {
                    if (value != null && value != '') {
                        return valueToDate(value);
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'caseType',
                title: '案件性质',
                width: 80,
                formatter: function (value, rec) {
                    if (value == 1) {
                        return "行政";
                    } else if (value == 2) {
                        return "刑事";
                    }
                }
            },
            {
                field: 'caseNature',
                title: '案由',
                width: 80,
                formatter: function (value, rec) {
                    if (value == null || value == "") {
                        return '无';
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'confirmResult',
                title: '裁决结果',
                width: 45,
                formatter: function (value, rec) {
                    if (value != null && value != '') {
                        return value;
                    } else {
                        return '<font color="red">未裁决</font>';
                    }
                }
            },
            {
                field: 'interrogateAreaName',
                title: '办案场所',
                width: 65
            },
            {
                field: 'id',
                title: '操作',
                width: 200,
                align: 'center',
                formatter: function (value, row, index) {

                    var f = '<span class="spanRow"><a href="#" class="gridcontrast" onclick="balance222('
                        + index + ')">对比</a ></span>';
                    return f ;

                }
            }]],
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar',
        onDblClickRow: function (index) {
         /*   var currentRow = $("#datagrid").datagrid("getSelected");
            $('#serailDetail').html(xqHtml(currentRow));
            showDialog('#serailDetail', '入出区详情');*/
        },
        onLoadSuccess:function(data){
           // console.log(data);
        }
    });
    var p = $('#datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            // $('#datagrid').datagrid('reload',{trefresh:new Date().getTime()}
            // );
            var es = $('#e_status').combobox('getValue');
            $('#datagrid').datagrid('load', {
                name: $('#e_person').val(),
                certificateNo: $('#e_certificateNo').val(),
                status: es,
                start_date: $('#e_start_date').datebox('getValue'),
                end_date: $('#e_end_date').datebox('getValue'),
                trefresh: new Date().getTime()
            });
            return false;
        }
    });

});
/*$('#fudong').remove();*/



//人像比对
function balance222(index) {
    $("#inPopup1").show();
    $("#stepDiv3").show();

    var row = $("#datagrid").datagrid("getRows")[index];

    var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoId);

    $("#dialogSerialImg").attr("src",e);

    var rowData = JSON.stringify(row);
    
    $.messager.progress({
        title: '请等待',
        msg: '数据对比中...'
    });
    
    jQuery.ajax({
        type: 'POST',
        url: "personIngetpicture.do",
        data: rowData,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            if(data.isError){
                $.messager.alert(data.title, data.content);
                var resultData = data.callbackData;
                if(resultData != null){
                    console.log(resultData);
                }
            }else{
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
                //关闭预览
                /*closePreview(1);
                showNoHideStepDiv("stepDiv2", "step2", 0);
                showNoHideStepDiv("stepDiv2", "step2", 0);
                showNoHideStepDiv("stepDiv3", "step3", 1);*/
                //$("#dialogSerialImg").attr("src", 'data:image/png;base64,' + inSerialPhotoBase64);
                /*if($('#datagrid')){
                    var es = $('#e_status').combobox('getValue');
                    var areaId = $('#interrogatearea').combobox('getValue');
                    $('#datagrid').datagrid('load', {
                        name : $('#e_person').val(),
                        certificateNo : $('#e_certificateNo').val(),
                        status : es,
                        areaId : areaId,
                        start_date : $('#e_start_date').datebox('getValue'),
                        end_date : $('#e_end_date').datebox('getValue'),
                        trefresh : new Date().getTime()
                    })
                } else{
                    initSerialData();
                }*/
                // 人口库
                var resultData = data.callbackData;
                if(resultData != null){
                    console.log(resultData);
                    var onevOneResult = resultData.onevOneResult.similarity;
                    if(onevOneResult!=null&&onevOneResult!=""){
                        onevOneResult=onevOneResult.substring(0, 4)+"%";
                    }else{
                        onevOneResult=0;
                    }
                    $('#photoDistinguish').text(onevOneResult);
                    var renKou = resultData.personWarehouse;
                    if(renKou != null && renKou.person_id != null && renKou.person_id != ""){
                        $("#renkou").removeClass('without');
                        renkouData = renKou;
                        $("#renkou").attr("onmouseover","showDetail(event,'renkou')");
                        $("#renkou").attr("onmouseout","hideDetail()");
                    }else{
                        renkouData = {};
                        $("#renkou").addClass('without');
                        $("#renkou").attr("onmouseover","");
                        $("#renkou").attr("onmouseout","");
                    }
                    var zaitao = resultData.largeWarehouse;
                    if(zaitao != null && zaitao.person_id != null && zaitao.person_id != ""){
                        zaitaoData = zaitao;
                        $("#zaitao").removeClass('without');
                        $("#zaitao").attr("onmouseover","showDetail(event,'zaitao')");
                        $("#zaitao").attr("onmouseout","hideDetail()");
                        $("#feizaitao").addClass('without');
                    } else{
                        zaitaoData ={};
                        $("#feizaitao").removeClass('without');
                        $("#zaitao").attr("onmouseover","");
                        $("#zaitao").attr("onmouseout","");
                        $("#zaitao").addClass('without');
                    }
                    var qianke = resultData.oldWarehouse;
                    if(qianke != null && qianke.person_id != null & qianke.person_id != ""){
                        qiankeData =qianke;
                        $("#qianke").removeClass('without');
                        $("#feiqianke").addClass('without');
                        $("#qianke").attr("onmouseover","showDetail(event,'qianke')");
                        $("#qianke").attr("onmouseout","hideDetail()");
                    } else{
                        qiankeData = {};
                        $("#feiqianke").removeClass('without');
                        $("#qianke").attr("onmouseover","");
                        $("#qianke").attr("onmouseout","");
                        $("#qianke").addClass('without');
                    }
                }
                // 历史对比结果start
                var certificateNoResult = row.certificateNo;
                jQuery.ajax({
                    type : 'POST',
                    //contentType : 'application/json',
                    dataType : 'json',
                    async: true,
                    url : contextPath + '/zhfz/bacs/serial/listHistory.do',
                    data : {'certificateNo' : certificateNoResult},
                    success : function(data) {
                        if(data != null && data.total > 0) {
                            $("#historyResult").removeClass('without');
                            historyResultData = data.rows;
                            $("#historyResult").attr("onmouseover","showHistoryResultDetail(event)");
                            $("#historyResult").attr("onmouseout","hideDetail()");
                        }else {
                            historyResultData = {};
                            $("#historyResult").addClass('without');
                            $("#historyResult").attr("onmouseover","");
                            $("#historyResult").attr("onmouseout","");
                        }
                    },
                    error : function(data) {
                        $.messager.alert('提示', '历史对比结果错误！');
                    }
                });
                // 历史对比结果end
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });

    //showNoHideStepDiv("stepDiv3", 1);
}

// 检索
function closeMpopup() {

    $("#inPopup1").hide();
}

//需要隐藏的步骤id；显示还是隐藏 type:0隐藏 1显示
function showNoHideStepDiv(stepDivId,type){
    var tempStepDiv = $("#"+stepDivId);
    if(type==1){
        tempStepDiv.show();
    }else{
        tempStepDiv.hide();
    }
}

//搜缩
function doSearch() {
    var es = $('#e_status').combobox('getValue');
    var caseType = $('#e_ajlx').combobox('getValue');
    var areaId = $('#interrogatearea').combobox('getValue');
    $('#datagrid').datagrid('load', {
        name : $('#e_person').val(),
        certificateNo : $('#e_certificateNo').val(),
        status : es,
        caseType:caseType,
        areaId : areaId,
        start_date : $('#e_start_date').datebox('getValue'),
        end_date : $('#e_end_date').datebox('getValue'),
        trefresh : new Date().getTime()
    });
}

//清空
function doClear() {
    $('#e_person').val('');
    $('#e_certificateNo').val('');
    $('#e_status').combobox('setValues', '');
    $('#interrogatearea').combobox('setValues', '');
    $('#e_start_date').datebox('setValue', '');
    $('#e_end_date').datebox('setValue', '');
}


function imgChange(img) {
    // 生成一个文件读取的对象
    var reader = new FileReader();
    reader.onload = function (ev) {
        // base64码
        var imgFile =ev.target.result;//或e.target都是一样的
        document.querySelector("img").src= ev.target.result;
    }
    //发起异步读取文件请求，读取结果为data:url的字符串形式，
    reader.readAsDataURL(img.files[0]);
}

rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
function setImagePreview() {
    $('#imageStatus1').val('未上传');
    $('#path1').val('');
    var docObj = document.getElementById("uploadImage");
    var imgObjPreview = document.getElementById("uploadPreview");
    if (document.getElementById("uploadImage").files.length === 0) {
        return;
    }
    var oFile = document.getElementById("uploadImage").files[0];
    if (!rFilter.test(oFile.type)) {
        /* document.getElementById("picerror").innerHTML="您上传的图片格式不正确，请重新选择!"; */
        alert("您上传的图片格式不正确，请重新选择!");
        return;
    }
    document.getElementById('uploadPreview').style.display = "block";
    if (docObj.files && docObj.files[0]) {
        /*火狐下，直接设img属性*/
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '180px';
        imgObjPreview.style.height = '180px';
        /*imgObjPreview.src = docObj.files[0].getAsDataURL();*/
        /*火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式*/
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    } else {
        /*IE下，使用滤镜*/
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("uploadPreview");
        /*必须设置初始大小*/
        localImagId.style.width = "180px";
        localImagId.style.height = "180px";
        /*图片异常的捕捉，防止用户修改后缀来伪造图片*/
        try {
            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)"; localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        } catch (e) {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}


//2image
rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
function setImagePreview1() {
    $('#imageStatus2').val('未上传');
    $('#path2').val('');
    var docObj = document.getElementById("uploadImage1");
    var imgObjPreview = document.getElementById("uploadPreview1");
    if (document.getElementById("uploadImage1").files.length === 0) {
        return;
    }
    var oFile = document.getElementById("uploadImage1").files[0];
    if (!rFilter.test(oFile.type)) {
        /* document.getElementById("picerror").innerHTML="您上传的图片格式不正确，请重新选择!"; */
        alert("您上传的图片格式不正确，请重新选择!");
        return;
    }
    document.getElementById('uploadPreview1').style.display = "block";
    if (docObj.files && docObj.files[0]) {
        /*火狐下，直接设img属性*/
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '180px';
        imgObjPreview.style.height = '180px';
        /*imgObjPreview.src = docObj.files[0].getAsDataURL();*/
        /*火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式*/
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    } else {
        /*IE下，使用滤镜*/
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("uploadPreview1");
        /*必须设置初始大小*/
        localImagId.style.width = "180px";
        localImagId.style.height = "180px";
        /*图片异常的捕捉，防止用户修改后缀来伪造图片*/
        try {
            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)"; localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        } catch (e) {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
    return true;
}

//保存数据start
function saveDataImage(){
    // alert("uploading...1.");

    //z这里不请求后台接口，直接将图片放到表单信息中
    // var docObj = document.getElementById("uploadImage1");
    // document.formone.submit();
    var formData = new FormData();
    formData.append('file', $("#uploadImage")[0].files[0]);

    // formData.append("file", _blob, 'b.png');
    jQuery.ajax({
        type: 'POST',
        url: 'uploadImage.do',
        data: formData,
        contentType: false, // 注意这里应设为false
        processData: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            $('#path1').val(data.imgSrc);
            $('#imageStatus1').val('已上传');
            //回显图片
            $("#uploadPreview").attr("src","");
            $("#uploadPreview").attr("src","imageshow.do?path=combinePhotoSavePath&image="+data.imgSrc+"");
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });

}
function saveDataImage1(){
    // alert("uploading...2.");
    var formData = new FormData();
    formData.append('file1', $("#uploadImage1")[0].files[0]);

    // formData.append("file", _blob, 'b.png');
    jQuery.ajax({
        type: 'POST',
        url: 'uploadImageOne.do',
        data: formData,
        contentType: false, // 注意这里应设为false
        processData: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            $('#path2').val(data.imgSrc);
            $('#imageStatus2').val('已上传');
            //回显图片
            $("#uploadPreview1").attr("src","");
            $("#uploadPreview1").attr("src","imageshow.do?path=combinePhotoSavePath&image="+data.imgSrc+"");
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

function closePage(){
    $('#constactImage_dialog').dialog('close');
}

function saveIamge(){
    var name1 =  $('#path1').val();
    var name2 =  $('#path2').val();
//alert("图片一: "+name1);
//alert("图片二: "+name2);
    if(name1!=null && name1!="" && name2!=null && name2!=""){
        var form1v1 = $('#form1v1').serializeObject();
        var jsonrtinfo = JSON.stringify(form1v1);
        //alert(jsonrtinfo);
        $.messager.progress({
            title : '请等待',
            msg : '人像对比中...'
        });
        $.ajax({
            type:'POST',
            url:'1v1Compare.do',
            dataType:'json',
            contentType : 'application/json',
            data : jsonrtinfo,
            success:function(data){
                var similarityResult=data.similarity;

                var  similarity = "" ;
                if(similarityResult!=null&&similarityResult!=""){
                    similarity=similarityResult.substring(0, 4)+"%";
                }else{
                    similarity=0;
                }
                $('#constractResult').val(similarity);
                $.messager.progress('close');
            },
            error:function(){
                //$.messager.alert('Error', '未知错误!');
                $.messager.progress('close');
            }
        })
    }
    else{
        if(name1=="" || name1==null){
            alert('图一未上传');
        }
        else if(name2=="" || name2==null){
            alert('图二未上传');
        }
    }
}

function queryUrlT(type){
    $.ajax({
        contentType:'applicaiton/json',
        url: '../personalconfig/getAqjgByType.do?type='+type,
        dataType : 'json',
        success:function(data){
            if(!data.error){
                var resultUrl = data.callbackData
                openWindowMax(resultUrl);
            }else{
                $.messager.alert(data.title, data.content);
            }
        },
        error:function(data){
            $.messager.alert("错误", "系统错误");
        }
    });
}

function csStart() {
    jQuery.ajax({
        type: 'POST',
        url: contextPath + "/zhfz/common/cshelper/getCsUrl.do",
        data: {type: 4, id: 0},
        dataType: 'json',
        success: function (data) {
            if (data.error) {
                $.messager.alert(data.title, data.content);
            } else {
                try{
                    window.location = data.callbackData;
                }catch(e){
                    $.messager.alert("提示", "未正确安装客户端程序！"+e);
                }

            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

//详情展示
function showDetail(e,type){
    var obj={};
    if(type == 'qianke'){
        obj = qiankeData;
    }
    if(type == 'zaitao'){
        obj = zaitaoData;
    }
    if(type == 'renkou'){
        obj = renkouData;
    }
    if(type == 'historyResult'){
        obj = historyResultData;
    }
    var pos = getMousePos(e);
    var bigger = '<div id="tooltip" class="detailTip">' +
        '<table>' +
        '<tr> <td>姓名</td><td>'+obj.name+'</td></tr>' +
        '<tr> <td>身份证号</td><td>'+obj.person_id+'</td></tr>' +
        '<tr> <td>出生日期</td><td>'+obj.born_year+'</td></tr>' +
        '<tr> <td>地址</td><td>'+obj.address+'</td></tr>' +
        '<tr> <td>证件图片</td><td><img src='+obj.face_image_url+'/></td></tr>' +
        '</table>' +
        '</div>';
    $("body").append(bigger);
    var zIndex = 99;
    if($(obj).closest('.window').css('z-index')){
        zIndex = $(obj).closest('.window').css('z-index') +1;
    }
    $("#tooltip").css({
        left:pos.x,
        top:pos.y,
        "z-index":zIndex
    }).show(1000);
}

function hideDetail(){
    $("#tooltip").remove();
}