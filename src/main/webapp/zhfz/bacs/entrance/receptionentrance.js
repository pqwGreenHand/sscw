var currenPage = 1;
var pageSize = 6;
var totalPage = 0;
var currenMap = {};  // 当前集合的map集合
var searchMap = {};//查询条件的集合
$(function () {
     init();
     initEntranceData();
    loadArea();
});

function init(){
    $(".m-head .user").hover(function(){
        $(this).addClass("open");
    },function(){
        $(this).removeClass("open");
    });
    $(".m-head .user").on("mousemove","*",function(){
        $(this).parents(".user").addClass("open");
    });
    $(".page").on("mouseenter",".open-btn",function(){
        $(this).addClass("active");
    });
    $(".page").on("mouseleave",".open-btn",function(){
        $(this).remomveClass("active");
    });
}

function initEntranceData() {
    var data = {};
    data["page"] = currenPage;
    data["rows"] = pageSize;
    $.each(searchMap, function(name, value) {
        if(value != null && value != ""){
            data[name] = value;
        }
    });
    data["trefresh"] = new Date().getTime();
    $.ajax({
        contentType : 'application/json',
        url : contextPath + '/zhfz/bacs/receptionentrance/list2.do',
        data : data,
        dataType : 'json',
        success : function (data) {
            $('#recrptionItems').html('');
            var rows = data.rows;
            var count = data.total;
            var recrptionHtml = "";
            currenMap = {};
            for (var i=0;i< rows.length;i++){
                var row = rows[i];
                recrptionHtml += '<div class="item">' +
                    '<div class="item-in m-box"><div class="left">' +
                    '<img src="" onerror="imgNotFind()">' +
                    '</div>' +
                    '<div class="right">' +
                    '<p><i>姓&emsp;&emsp;名：</i><span>'+row.name+'('+row.certificateNo+')</span></p>' +
                    '<p><i>事&emsp;&emsp;由：</i><span>'+row.reason+'</span></p>' +
                    '<p><i>入区时间：</i>' +'<span>'+formatDateTimeText(new Date(row.inTime))+'</span></p>' +
                    '<p><i>工作单位：</i><span>'+row.department+'</span></p>' +
                    '<div><button class="m-btn-1 blue" onclick="update('+row.id+')">修改</button><button class="m-btn-1 blue" onclick="exit('+row.id+')">出区</button>' +
                    '</div></div></div></div>';
                currenMap[row.id] = row;
            }
            $("#recrptionItems").html(recrptionHtml);
            totalPage = parseInt(((count-1)/pageSize))+1;
            $("#pageLable").html(currenPage + "/" + totalPage);
            $("#totalSpan").html('当前显示'+((currenPage-1)*pageSize+1)+'-'+(currenPage*pageSize)+'条，共'+count+'条');
        },
        error : function () {
            $.messager.progress('close');
            $.messager.alert('错误', '系统错误!');
        }
    })
}

function doClear2() {
    $('#name2').val("");
    $('#certificateNo2').val("");
    $("#interrogatearea").combobox("setValue","");
}

function doSearch2(){
    currenPage = 1;
    pageSize = 6;
    var areaId = $("#interrogatearea").combobox("getValue");
    var name = $('#name2').val();
    var certificateNo =$('#certificateNo2').val();
    if(areaId != null){
        searchMap["areaId"] = areaId;
    }else{
        searchMap["areaId"] = "";
    }
    if(name != null){
        searchMap["name"] = name;
    }else{
        searchMap["name"] = "";
    }
    if(certificateNo != null){
        searchMap["certificateNo"] = certificateNo;
    }else{
        searchMap["certificateNo"] = "";
    }
    initEntranceData();
}

function toPage(type){
    switch(type){
        case 'first' : currenPage =1;
           // initEntranceData();
             break;
        case 'last'  :
            if(currenPage!=1) currenPage--;
            initEntranceData(); break;
        case 'next'  : if(currenPage!=totalPage) ++currenPage;
            initEntranceData(); break;
        case 'end' : currenPage =totalPage;
            initEntranceData();
        break;
    }
}

// 出区
function exit(id){
    if(confirm("是否确定出区该来访人员？")){
        jQuery.ajax({
            type : 'POST',
            async : false,
            dataType : 'json',
            url : contextPath + '/zhfz/bacs/receptionentrance/updateOutTime.do',
            data : {'receptionEntranceId':id},
            success : function(data){
                initEntranceData();
                $('#outReception').combogrid('grid').datagrid('load',{
                    trefresh:new Date().getTime()
                })
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
}


function update(id){
   $("#inPopup").show();
  //showDialog('#inPopup','修改访客记录信息');
    //$("#title").html("修改访客记录信息");
    jQuery.ajax({
        type : 'POST',
        async : false,
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/receptionentrance/findById.do',
        data : {'receptionEntranceId':id},
        success : function(data){
            $('#otherPersonForm').form('load',data[0]);
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });

}
//获取办案场所下拉框
function loadArea(){
    $('#interrogatearea').combobox({
        url: '../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            if(data == null && data.length > 0){
                $('#interrogatearea').combobox("setValue",areaId);
            }
        }
    });
}