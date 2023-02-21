var currenPage = 1;
var pageSize = 6;
var totalPage = 0;
var data = {};
$(function(){
    initArea();
    init();
    searchInit();
    initOtherPersonData();
    initCaseInfo();
    initOtherPersonInfo();
});

function searchInit() {
    data['rows'] = pageSize;
    data['personName'] = $('#personName').val();
    data['certificateNo'] = $('#certificateNo').val();
    data['areaId'] = $('#areaId').combobox("getValue");
}

function initOtherPersonData(){
    data['page'] = currenPage;
    $.ajax({
        contentType : 'application/json',
        url : contextPath + '/zhfz/bacs/serial/otherPersonList2.do',
        data : data,
        dataType : 'json',
        success : function(data){
            $("#otherPersonItems").html('');
            currenMap = {};
            var rows = data.rows;
            var count = data.total;
            var otherPersonItemsHtml = '';
            var item = $("#item").html();
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                row.inTime = formatDateTimeText(new Date(row.inTime));
                if(row.inPhotoId == null || row.inPhotoId == ''){
                    row.url = "../../../static/login-8.png";
                }else{
                    row.url = fileUtils.url("ba","RQ",row.uuid,row.areaId,row.inPhotoId);
                }
                otherPersonItemsHtml += item.format(row);
//                otherPersonItemsHtml += '<div class="item">' +
//                	'<div class="item-in m-box"><div class="left">' +
//                    '<img src="../../../static/locker-11.png">' +
//                    '</div>' +
//                    '<div class="right">' +
//                    //'<input type="hidden" id="bCaseId" name="bCaseId" value="'+ row.bCaseId +'">' +
//                    '<p><i>姓&emsp;&emsp;名：</i><span>'+row.personName+'</span></p>' +
//                    '<p><i>证件号码：</i><span>'+row.certificateNo+'</span></p>' +
//                    '<p><i>入区时间：</i>' +'<span>'+formatDateTimeText(new Date(row.inTime))+'</span></p>' +
//                    '<p><i>办案单位：</i><span>'+row.areaName+'</span></p>' +
//                    '<div><button class="m-btn-1 blue" onclick="exit('+row.id+',\"'+row.serialNo+'\",\"'+row.uuid+'\",'+row.areaId+',\"'+row.inPhotoId+'\")">出区</button>' +
//                    '</div></div></div></div>';
            }
            $("#otherPersonItems").html(otherPersonItemsHtml);
            totalPage = parseInt(((count-1)/pageSize))+1;
            $("#pageLable").html(currenPage + "/" + totalPage);
            $("#totalSpan").html('当前显示'+((currenPage-1)*pageSize+1)+'-'+(currenPage*pageSize)+'条，共'+count+'条');
        },
        error : function(){
            $.messager.progress('close');
            $.messager.alert('错误', '系统错误!');
        }
    });
}


// 快捷出区
function exit(serialId,serialNo,uuid,areaId,inPhotoId){
	$('#serialId').val(serialId);
    $('#serialNo').val(serialNo);
    $('#s_inPhotoId').val(inPhotoId);
    $("#inPopup").removeClass("in");
	$("#inPopup").removeClass("out");
	$("#inPopup").addClass("exit");
	$("#inphoto").prop("src",fileUtils.url("ba","RQ",uuid,areaId,inPhotoId));
	load(); 
	$("#hd").css("display","none");//隐藏div
	showNoHideStepDiv("stepDiv1", "step1", 0);
    showNoHideStepDiv("stepDiv2", "step2", 1);
	$("#inPopup").show();	
}



function doSearch2() {
	currenPage = 1;
    searchInit();
    initOtherPersonData();
}

function doClear2() {
	$('#personName').val('');
	$('#certificateNo').val('');
    $("#areaId").combobox("setValue","");
}

// 初始化案件信息
var CaseId=-1;
function initCaseInfo(){    
    $('#caseId').combogrid({    
        panelWidth:420,    
        mode: 'remote',    
        url: contextPath + '/zhfz/common/case/listCase.do',
        idField: 'id',    
        textField: 'ajmc',
        frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 80,
				sortable : true,
				hidden : true
			} ] ],    
        columns: [[    
        	{field:'ab',title:'案件性质ID',width:100,hidden:true},
            {field:'ajmc',title:'案件名称',width:180},
            {field:'zbdwName',title:'主办单位',width:100},
            {field:'zbmjName',title:'主办民警',width:100}
        ]] ,
        onLoadSuccess: function(data){
            //$('#caseId').combogrid('setValue',-1);
        } ,
        pagination : true,
		pageSize:50,
		pageList : [50, 100],
		rownumbers : true,
        onChange:function(newValue, oldValue){
            var cg = $('#caseId').combogrid('grid');   // 获取数据表格对象
            var row = cg.datagrid('getSelected');   // 获取选择的行
            CaseId=row.id;            
            if(CaseId!=-1){
             //加载案件性质
             $('#ab').combobox({
                 url:contextPath + '/zhfz/bacs/combobox/listcrimetypebynature.do?nature=' +encodeURI(row.ajlx,"UTF-8"),
                 valueField:'id',
                 textField:'value'
             });
             $('#ajlx').combobox('setValue',row.ajlx);             
             $('#ab').combobox('setText',row.caseNature);
             $('#ab').combobox('setValue',row.ab);
             $('#afsj').datetimebox('setValue', dateFormat("yyyy-MM-dd hh:mm:ss",new Date(row.afsj)));
             $('#afdd').val(row.afdd);          
            }else{
                $('#ajlx').combobox('select','');
                $('#ab').combobox('select','');
                $('#afsj').datetimebox('setValue','');
                $('#afdd').val('');                
            }           
        }
       });
}

function toPage(type){
    switch(type){
        case 'first' : currenPage =1; initOtherPersonData(); break;
        case 'last'  : if(currenPage!=1) currenPage--; initOtherPersonData(); break;
        case 'next'  : if(currenPage!=totalPage) currenPage++; initOtherPersonData(); break;
        case 'end' : currenPage =totalPage; initOtherPersonData(); break;
    }
}

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
        $(this).removeClass("active");
    });
}

function dateFormat(fmt,date){   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

//加载办案场所
function initArea(){
    $.ajax({
        url : contextPath + '/zhfz/bacs/order/comboArea.do',
        dataType : 'json',
        async : false,
        success : function (data) {
            $('#areaId').combobox({
                data: data,
                valueField: 'id',
                textField: 'name',
                width : 160
            });
            if(data != null && data.length > 0){
                $('#areaId').combobox('setValue',data[0].id);
            }
        }
    });
}