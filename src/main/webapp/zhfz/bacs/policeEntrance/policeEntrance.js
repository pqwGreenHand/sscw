var currenPage = 1;
var pageSize = 6;
var totalPage = 0;
var currenMap = {};//当前集合的map集合  key：caseId  value：case对象
var data = {};
$(function(){
    caseAjlyCode = 1;
    initArea();
    init();
    //searchInit();
    initPoliceData();
    initCaseInfo();
    initAjlx2();
    addPoliceBindClickOut();
});

//function closeExit_dialog(){
//	initPoliceData();
//	$('#exit_dialog').dialog('close');
//}
function searchInit() {
    data['rows'] = pageSize;
    data['ajmc'] = $('#ajmc2').val();
    data['name'] = $('#name2').val();
    data['certificateNo'] = $('#certificateNo2').val();
    data['areaId'] = $('#areaId').combobox("getValue");
}

function initPoliceData(){
    data['page'] = currenPage;
    $.ajax({
        contentType : 'application/json',
        url : contextPath + "/zhfz/bacs/policeEntrance/list2.do",
        data : data,
        dataType : 'json',
        success : function(data){
            $("#policeItems").html('');
            currenMap = {};
            var rows = data.rows;
            var count = data.total;
            var policeItemsHtml = '';
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                policeItemsHtml += '<div class="item">' +
                	'<div class="item-in m-box"><div class="left">' +
                    '<img src="../../../static/policeLogo.jpg">' +
                    '</div>' +
                    '<div class="right">' +
                    //'<input type="hidden" id="bCaseId" name="bCaseId" value="'+ row.bCaseId +'">' +
                    //'<p><i>案件名称：</i><span>'+row.name+'('+row.certificateNo+')</span></p>' +
                    '<p><i>案件名称：</i><span>'+row.ajmc+'</span></p>' +
                    '<p><i>案件编号：</i><span>'+row.ajbh+'</span></p>' +
                    '<p><i>主办民警：</i><span>'+row.name+'('+row.certificateNo+')</span></p>' +
                    '<p><i>主办单位：</i><span>'+row.areaName+'</span></p>' +
                    '<div><button class="m-btn-1 blue" style="float: right;margin-right: 14px;" onclick="exit('+row.bCaseId+')">出区</button>' +
                    '<button class="m-btn-1 blue" style="float: right;margin-right: 14px;" onclick="add_police('+row.bCaseId+','+row.areaId+')">添加民警</button>' +
                    '</div></div></div></div>';
                    currenMap[row.id] = row;
            }
            $("#policeItems").html(policeItemsHtml);
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

// 出区
function exit(caseId){
    $('#exitDatagrid').datagrid({
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
		    idField:'code',
		    queryParams : {
		         caseId : caseId,
		         trefresh:new Date().getTime()
		    },
		    url : 'policeList.do',
		    singleSelect : true,
		    frozenColumns : [ [
		    // {field:'ck',checkbox:true},
		    { title : '入区id',field : 'id',width : 10,sortable : true,hidden : true},
		    { title : '案件id',field : 'bCaseId',width : 10,sortable : true,hidden : true},
            { title : '卡片id',field : 'cuffId',width : 10,sortable : true,hidden : true},
            { title : '民警id',field : 'policeId',width : 10,sortable : true,hidden : true},
            { title : '办案场所id',field : 'areaId',width : 10,sortable : true,hidden : true}
		    ] ],
		    columns : [[
		             {
		                field : 'name',
		                title : '姓名',
		                width : '30%',		                
		            },
		            {
		                field : 'certificateNo',
		                title : '民警警号',
		                width : '20%',		                
		            },		            
		             {field:'policeType',title:'民警类型',
		              formatter:function(value,rec){
		            		  if(value==0){
		                          return '<font color="#00FF00">主办民警</font>';
		                      }else{
		                          return '<font color="#00EEEE">协办民警</font>';
		                      }				
		            }},                               
		            {
		                field : 'cuffNo',
		                title : '民警卡片',
		                width : '25%',
		                
		            },                             
		            {
		                field : 'id',
		                title : '操作',
		                width : '25%',
		                align : 'left',
		                formatter : function(value, row, index) {		                              
		                    if (row.outTime == '' || row.outTime == null) {		                       
		                         return '<span class="spanRow"><a href="#" class="gridout" onclick="exit2('+index+')">出区</a></span>';
		                    }
		                }
		
		                }
		            ] ],
		    pagination : true,
		    pageList : [ 10, 20, 30, 40, 50, 100 ],
		    rownumbers : true,
		    onSelect : function(rowIndex, rowData) {
				// console.info(rowData.lx);】
//				$('#ajxxId').val(rowData.id);
//				$('#exitDatagrid').datagrid('load', {
//					ajxxId : rowData.id,
//					tfresh : new Date().getTime()
//				});
			},
		    // 当数据加载成功时触发
		    onLoadSuccess : function() {
		        showDialog('#exit_dialog','民警登记列表');      
		    },		        
		  });
	var p = $('#exitDatagrid').datagrid('getPager');
	$(p).pagination({
		onBeforeRefresh : function() {
			// alert('before refresh');
		}
	});
}

// 出区2
function exit2(index){
	var row = $('#exitDatagrid').datagrid('getRows')[index];
	$.messager.confirm('出区确认', '是否确定该民警('+row.name+')出区，卡号为['+row.cuffNo+']？', function(r) {
        if (r) {
        	 $.messager.progress({
                title:'请等待',
                msg:'正在解除卡片定位信息...'
            });
			jQuery.ajax({
		            type : 'POST',
		            async : false,
		            dataType : 'json',
		            url : 'updateOutTime2.do',
		            data : {'cuffId':row.cuffId,'policeEntranceId':row.id,'policeId':row.policeId,'cuffNo' :row.cuffNo,'areaId':row.areaId},
		            success : function(data){
		                if(data.code == 0){
                            $.messager.progress('close');
                            $.messager.alert(data.title, data.content);
                        }else {
                            $('#exitDatagrid').datagrid('load', {
                                caseId : row.bCaseId,
                                trefresh:new Date().getTime()
                            });
                            currenPage = 1;
                            initPoliceData();
                            $.messager.progress('close');
                            $.messager.show({
                                title:data.title,
                                msg:data.content,
                                timeout:3000
                            });
		                }
		            },
		            error : function(data){
		                $.messager.progress('close');
		                $.messager.alert(data.title, data.content);
		            }
		    });
		 }
    });		
}


function doSearch2() {
	currenPage = 1;
    searchInit();
    initPoliceData();
}

function doClear2() {
	$('#ajmc2').val('');
	$('#name2').val('');
	$('#certificateNo2').val('');
    $("#areaId").combobox("setValue","");
}

// 初始化案件信息
var CaseId=-1;
function initCaseInfo(caseSelectId){
    $('#caseId').combogrid({    
        panelWidth:650,
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
        	{field:'ajlxDesc',title:'案件类型',width:75},
            {field:'abmc',title:'案件性质',width:100},
            {field:'zbmjName',title:'主办民警',width:75},
            {field:'zbdwName',title:'主办单位',width:180},
            {field:'ajmc',title:'案件名称',width:180}
        ]] ,
        onLoadSuccess: function(data){
            if(caseSelectId != null && caseSelectId != ""){
                $('#caseId').combogrid('setValue',caseSelectId);
            }
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
             $('#policeAb').combobox({
                 url:contextPath + '/zhfz/bacs/combobox/listcrimetypebynature.do?nature=' +encodeURI(row.ajlx,"UTF-8"),
                 valueField:'id',
                 textField:'value',
                 onLoadSuccess:function(data){
                     $('#policeAb').combobox('setValue',row.ab);
                 }
             });
             $('#policeXjlx').combobox('setValue',row.ajlx);
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

// 初始化案件类型和案件性质
function initAjlx2(){
    codeCombo('policeXjlx', 'AJLX', '');
    $('#policeXjlx').combobox({
        onChange : function(val){
             //清空案件性质
             $('#policeAb').combobox('clear');
             var nature = $('#policeXjlx').combobox('getText');
             if(val!=""){
                 //加载案件性质
                 $('#policeAb').combobox({
                     url:contextPath + '/zhfz/bacs/combobox/listcrimetypebynature.do?nature=' +encodeURI(nature,"UTF-8"),
                     valueField:'id',
                     textField:'value'
                 });
             }
            
         }
    });
}


function toPage(type){
    switch(type){
        case 'first' : currenPage =1; initPoliceData(); break;
        case 'last'  : if(currenPage!=1) currenPage--; initPoliceData(); break;
        case 'next'  : if(currenPage!=totalPage) currenPage++; initPoliceData(); break;
        case 'end' : currenPage =totalPage; initPoliceData(); break;
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

// 添加民警弹出框
function add_police(caseId,areaId) {
    $('#addPolice_caseId').val(caseId);
    $('#addPolice_areaId').val(areaId);
    document.getElementById('addPolice_main').checked = true;
    $('#addPolice_Popup').show();
}

// 关闭添加民警弹出框
function closeAddPolice() {
    clearAddPolice();
    $('#addPolice_Popup').hide();
}

// 清空添加民警弹出框信息
function clearAddPolice() {
    $('#addPolice_cuffNo').val('');
    $('#addPolice_cuffId').val('');
    $('#addPolice_policeId').val('');
    $('#addPolice_policeNo').val('');
    $('#addPolice_caseId').val('');
    $('#addPolice_areaId').val('');
}

// 添加民警保存
function saveAddPolice() {
    var policeNo =$('#addPolice_policeNo').val();
    var policeId=$('#addPolice_policeId').val();
    var cuffNo=$('#addPolice_cuffNo').val();
    var cuffId=$('#addPolice_cuffId').val();
    var caseId = $('#addPolice_caseId').val();
    var areaId = $('#addPolice_areaId').val();
    if(!policeNo){
        $.messager.alert('警告', '请输入民警警号！');
        return;
    }
    if(!cuffNo){
        $.messager.alert('警告', '请刷入民警卡片！');
        return;
    }
    if(!cuffId){
        $.messager.alert('警告', '民警卡片错误,请重新刷卡片！');
        return;
    }
    var policeType;
    if(document.getElementById('main').checked){
        policeType=0;
    }
    if(document.getElementById('assist').checked){
        policeType=1;
    }
    // 判断当前民警是否在区，如果在区不能重复入区
    jQuery.ajax({
        type : 'POST',
        async : false,
        dataType : 'json',
        url : 'findPoliceEntranceByPoliceId.do',
        data : {'policeId': policeId},
        success : function(data){
            $.messager.progress('close');
            if(data.code == 0){
                if(data.callbackData !=null){
                    $('#policeNo').val('');
                    $('#policeId').val('');
                    $('#cuffNo').val('');
                    $('#cuffId').val('');
                    $.messager.alert(data.title, '该民警还未出区，不能重复入区!');
                }else {
                    // 添加民警
                    $.ajax({
                        type:'post',
                        dataType:'json',
                        async:false,
                        url:'mainAddPolice.do',
                        data:{'policeId':policeId,'cuffId':cuffId,'caseId':caseId,'areaId':areaId,'policeType':policeType,'cuffNo':cuffNo},
                        success:function (res) {
                            clearAddPolice();
                            $('#addPolice_Popup').hide();
                            $.messager.alert(res.title, res.content);
                        },
                        error:function () {
                            $.messager.progress('close');
                            $.messager.alert('提示', '添加民警失败!');
                        }
                    });
                }
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '添加民警失败!');
        }
    });
}

function addPoliceBindClickOut(){
    $('#addPolice_cuffNo').keydown(function(event){
        if(event.keyCode == 13){
            var data = checkRingNo($("#addPolice_cuffNo").val(),1);
            if(!(data.isError)){
                if(data.callbackData.cuffNo != null&&data.callbackData.cuffNo != ""){
                    if(data.callbackData.status == 0){
                        $("#addPolice_cuffNo").val(data.callbackData.cuffNo);
                        $("#addPolice_cuffId").val(data.callbackData.id);
                    }else{
                        $.messager.alert(data.title, data.content);
                        $("#addPolice_cuffNo").val("");
                        $("#addPolice_cuffId").val("");
                    }
                }
            }else{
                $.messager.alert(data.title, data.content);
                $("#addPolice_cuffNo").val("");
                $("#addPolice_cuffId").val("");
            }
        }
    });
}


function finduser2(index) {
    var userNo ;
    if(index==0){
        userNo= $('#policeNo').val();
    }else if(index==1){
        userNo= $('#e_policeNo').val();
    }else if(index==2){
        userNo= $('#ep_policeNo').val();
    }else if(index==3){
        userNo= $('#addPolice_policeNo').val();
    }
    if(!userNo){
        return;
    }
    var userNoInfo = {"userNo":userNo};
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/order/searchUser.do',
        data : jsonrtinfo,
        success : function(data) {
            if(data != null && data.id != null) {
                if(index==0){
                    $("#policeId").val(data.id);
                }else if(index==1){
                    $('#e_policeId').val(data.id);
                }else if(index==2){
                    $('#ep_policeId').val(data.id);
                }else if(index==3){
                    $('#addPolice_policeId').val(data.id);
                }
            }else{
                $.messager.alert('错误', userNo + '该警号不存在!');
                if(index==0){
                    $('#policeNo').val('');
                }else if(index==1){
                    $('#e_policeNo').val('');
                }else if(index==2){
                    $('#ep_policeNo').val('');
                }else if(index==3){
                    $('#addPolice_policeNo').val('');
                }
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在！');
            if(index==0){
                $('#policeNo').val('');
            }else if(index==1){
                $('#e_policeNo').val('');
            }else if(index==2){
                $('#ep_policeNo').val('');
            }else if(index==3){
                $('#addPolice_policeNo').val('');
            }
        }
    });
}