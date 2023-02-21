$(function(){
    initArea();
});

// 来访人员入区
function entrance() {
    $("#inPopup").show();
    doAllClear();
}

// 初始化办案场所
function initArea() {
     $('#areaId').combobox({
         url:'../order/comboArea.do',
         valueField : 'id',
         textField : 'name',
         onLoadSuccess: function(data){
             $('#areaId').combobox('setValue', data[0].id);
             var an = $('#areaId').combobox('getText')
             $('#areaName').val(an);
         }
     })
}

function closeMpopup(){
    doAllClear();
    /*showNoHideStepDiv("stepDiv1", "step1", 1);
    showNoHideStepDiv("stepDiv2", "step2", 0);*/
    $("#inPopup").hide();
}

//需要隐藏的步骤id；显示还是隐藏 type:0隐藏 1显示
/*function showNoHideStepDiv(stepDivId,stepId,type){
    var tempStep = $("#"+stepId);
    var tempStepDiv = $("#"+stepDivId);
    if(type==1){
        tempStepDiv.show();
        tempStep.addClass("now");
    }else{
        tempStepDiv.hide();
        tempStep.removeClass("now");
    }
}*/

function savePersonInfo(){

    if(!checkForm('#otherPersonForm')){
        return;
    }
    $.messager.progress({
        title:'请等待',
        msg:'保存数据中...'
    });

    var data1 = $('#otherPersonForm').serializeObject();
    data1['reason'] = $('#reason').combobox('getText');
    if(data1.id == "" || data1.id == null){
        data1['inTime'] = new Date();
        var jsonrtinfo = JSON.stringify(data1);
        jQuery.ajax({
            type : 'POST',
            contentType :  'application/json',
            async : false,
            url : contextPath + '/zhfz/bacs/receptionentrance/receptionInsert.do',
            data : jsonrtinfo,
            dataType : 'json',
            success : function(data){
                $.messager.progress('close');
                closeMpopup();
                initEntranceData();
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
            },
            error : function(data){
                $.messager.progress('close');
                $.messager.alert(data.title, data.content);
            }
        });
        return;
    }

    var jsonrtinfo = JSON.stringify(data1);
    console.log(jsonrtinfo);
    jQuery.ajax({
        type : 'POST',
        contentType :  'application/json',
        async : false,
        url : contextPath + '/zhfz/bacs/receptionentrance/updateById.do',
        data : jsonrtinfo,
        dataType : 'json',
        success : function(data){
            $.messager.progress('close');
             closeMpopup();
             initEntranceData();
            $.messager.show({
                title:data.title,
                msg:data.content,
                timeout:3000
            });
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });




}

function doAllClear(){
    // 清空来访人员信息
    $('#name1').val('');
    $('#department').val('');
    $('#certificateNo').val('');
    $('#phone').val('');
    $('#receiver').val('');
    $('#card').textbox('setText','');

}



