var waritRoom;
$(function() {
	//初始化查询加载下拉框
    InitializatingSelect();
})
function InitializatingSelect (){
    //加载办案单位下拉框
    $('#areaCombobox').combobox({
        url:'../order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('#areaCombobox').combobox('setValue', data[0].id);
        },
        onChange: function (newValue, oldValue) {
            var areaId = $('#areaCombobox').combobox('getValue');
            //初始化侯问室
            InitializatingRoom(areaId)
            //初始化统计信息
            InitializatingStatisticalInformation(areaId);
        }
    });
    //隐藏操作嫌疑人的button-bar-div
    $("#butBar").hide();
}

function InitializatingRoom(areaId){
     $.ajax({
            async: false,
            type: 'POST',
            contentType: 'application/json',
            url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
            dataType: 'json',
            success: function (data) {
                $("#boxRoom").empty();
                var list = data.list;
                if (list.length > 0) {
                    for (var i=0; i < list.length; i++) {
                        var count = 0;
                        var clist = list[i];
                        if (clist.length > 0) {
                            for (var j = 0; j < clist.length; j++) {
                                //先统一拼接好侯问室
                                $("#boxRoom").append("<li ><div id='"+clist[j].roomId+"' onclick='freshWaitingMessage("+clist[j].roomId+","+clist[j].areaId+")'><a style='display: inline'><span>"+clist[j].roomName+"</span><i></i></a>   </div></li>");
                                //再拼接人员
                                //房间全是男的
                                if(clist[j].male!=0 && clist[j].female==0 && clist[j].other==0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span>"+clist[j].male+"</span>男</p></div>")
                                }
                                //房间全是女的
                                if(clist[j].male==0 && clist[j].female!=0 && clist[j].other==0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span>"+clist[j].female+"</span>女</p></div>")
                                }
                                //房间有男有女
                                if(clist[j].male!=0 && clist[j].female!=0 && clist[j].other==0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span>"+clist[j].male+"</span>男</p></div> <div><p><span>"+clist[j].female+"</span>女</p></div>")
                                }
                                //房间无男无女无未知性别（无人）的时候
                                if(clist[j].male==0 && clist[j].female==0 && clist[j].other==0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span></span>空</p></div> ")
                                }
                                //以下是特殊情况（有未知性别的时候）
                                //当房间都是未知性别的时候
                                if(clist[j].other!=0 && clist[j].male==0&& clist[j].female==0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span>"+clist[j].other+"</span>未知性别</p></div> ")
                                }
                                //当房间有未知性别有男
                                if(clist[j].other!=0 && clist[j].male!=0&& clist[j].female==0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span>"+clist[j].other+"</span>未知性别</p></div> <div><p><span>"+clist[j].male+"</span>男</p></div>")
                                }
                                //当房间有未知性别有女
                                if(clist[j].other!=0 && clist[j].male==0&& clist[j].female!=0){
                                    $("#"+clist[j].roomId+"").append("<div><i></i><p><span>"+clist[j].other+"</span>未知性别</p></div> <div><p><span>"+clist[j].female+"</span>女</p></div>")
                                }
                                //当房间有未知性别有女有男
                                if(clist[j].other!=0 && clist[j].male!=0&& clist[j].female!=0){
                                    $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].other+"</span>未知性别</p></div> <div><p><span style='font-size:10px'>"+clist[j].female+"</span>女</p></div> <div><p><span style='font-size:10px'>"+clist[j].male+"</span>男</p><i></i></div>")
                                }
                            }
                        }else{
                            $.messager.alert('提醒', '还未维护候问室！');
                        }
                    }
                }
            }
     })
}
//刷新侯问室的人员关押情况
function freshWaitingMessage(roomId,areaId){
    document.getElementById('tongjiliandong1').style.background="#173463";
    document.getElementById('tongjiliandong2').style.background="#173463";
    document.getElementById('tongjiliandong3').style.background="#173463";
    document.getElementById('tongjiliandong4').style.background="#173463";
    document.getElementById('tongjiliandong5').style.background="#173463";
    document.getElementById('tongjiliandong6').style.background="#173463";
    sessionStorage.removeItem("serialId");
    $("#roomId1").val(roomId);
    $("#recordId2").val("");
    //先获取input的存储的值
    var color = $("#roomId").val();
    $("#roomId").val(roomId);
    //将上次点击的div颜色复原
    if(color==""){
        document.getElementById(roomId).style.background="#008B00";
    }else if(color!=""){
        if(roomId!=color){
            document.getElementById(roomId).style.background="#008B00";
            document.getElementById(color).style.background="#173463";
        }else{
            document.getElementById(roomId).style.background="#008B00";
        }
    }
//加载查询房间的嫌疑人信息
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listSuspect.do?areaId=' + areaId + '&roomId='+roomId+'&trefresh=' + new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

//点击嫌疑人照片刷新嫌疑人的信息
function freshSuspect(isJuvenile,isGravida,isGravidaProve,gravidaMonth,isDisease,isDiseaseProve,officer,personName,recordName,sendUserName,inRoomTime,caseType,caseNature,recordId,getResult,status,serialId,sex){
    $("#serialId1").val(serialId)
    if(sex=="0"){
        sex ="未知性别"
    }
    if(sex=="9"){
        sex ="未知性别"
    }
    if(sex=="1"){
        sex ="男"
    }
    if(sex=="2"){
        sex ="女"
    }
/*    var oUl = document.getElementById(serialId);
    var Lis = oUl.getElementsByTagName('i');
    for(var j = 0;j<Lis.length;j++){
        Lis[1].className="i11"
        Lis[2].className="i12"
        Lis[3].className="i13"
        Lis[4].className="i14"
    }
    console.log($("#"+serialId+"i1"))*/
   if(typeof (sessionStorage.serialId)!="undefined" && sessionStorage.serialId != "null"){
      document.getElementById(sessionStorage.serialId+"i1").className="i1"
      document.getElementById(sessionStorage.serialId+"i2").className="i2"
      document.getElementById(sessionStorage.serialId+"i3").className="i3"
      document.getElementById(sessionStorage.serialId+"i4").className="i4"
   }
    sessionStorage.serialId = serialId;
    document.getElementById(serialId+"i1").className="i11"
    document.getElementById(serialId+"i2").className="i12"
    document.getElementById(serialId+"i3").className="i13"
    document.getElementById(serialId+"i4").className="i14"
   /* var oUl2 = document.getElementById("suspectMan");
    var Lis2 = oUl2.getElementsByTagName('i');
    for(var j = 0;j<Lis2.length;j++){
        Lis2[j].className="i1"
    }*/
    if(status==0){
        //隐藏返回侯问室的按钮
        $("#backRoomDiv").hide();
    }else{
        $("#backRoomDiv").show();
        $('#changerRoomDiv').hide();
    }
    //设置选中的serialiD
    $("#recordId2").val(recordId)
    //显示操作嫌疑人的bar-div1554777272
    var caseType1 = ""
    if(caseType == 0){
        caseType1 = "刑事";
    }else if(caseType ==1){
        caseType1 = "行政";
    }else if(caseType ==2){
        caseType1 = "刑事";
    }else{
        caseType1 = "其他";
    }
    if(getResult=="null" || getResult==""){
        getResult = recordName;
    }
    waritRoom=recordName;
    if(isJuvenile == 1){
        isJuvenile = '是';
    }else {
        isJuvenile = '否';
    }
    if(isGravida == 1){
        isGravida = '是';
    }else {
        isGravida = '否';
    }
    if(isGravidaProve == 1){
        isGravidaProve = '是';
    }else {
        isGravidaProve = '否';
    }
    if(isDisease == 1){
        isDisease = '是';
    }else {
        isDisease = '否';
    }
    if(isDiseaseProve == 1){
        isDiseaseProve = '是';
    }else {
        isDiseaseProve = '否';
    }
    if(sendUserName=='null'){
    	sendUserName="否"
    }
    if(caseNature=='null'){
    	caseNature="否"
    }
    if(caseType1=='null'){
    	caseType1="否"
    }
    if(gravidaMonth=='null'){
    	gravidaMonth=0
    }
    $("#oneSuspectInfo").empty();
    $("#oneSuspectInfo").append("<li><i>姓名：</i><span>"+personName+"</span>&nbsp&nbsp<i>性别：</i><span >"+sex+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>进入时间：</i><span>"+formatDateTime(inRoomTime)+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>侯问室：</i><span>"+recordName+"</span>&nbsp&nbsp<i>当前状态：</i><span id='spanText' style='background-color: red'>"+getResult+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>押送民警：</i><span>"+sendUserName+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>案件类型：</i><span>"+caseType1+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>案件性质：</i><span>"+caseNature+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>是否未成年：</i><span>"+isJuvenile+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>是否孕妇：</i><span>"+isGravida+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>怀孕月数：</i><span>"+gravidaMonth+"个月</span></li>");
    $("#oneSuspectInfo").append("<li><i>是否有怀孕证明：</i><span>"+isGravidaProve+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>是否有疾病：</i><span>"+isDisease+"</span></li>");
    $("#oneSuspectInfo").append("<li><i>是否有疾病证明：</i><span>"+isDiseaseProve+"</span></li>");
    $("#butBar").show();
}

//时间转化
function formatDateTime(inputTime) {
    var date = new Date(parseInt(inputTime));
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};
//初始化统计信息
function InitializatingStatisticalInformation(areaId){
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'selectPeopleCount.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            $("#boxTj").empty();
            //拼接侯问室总人数统计
            $("#boxTj").append("<div id='tongjiliandong1' class='item' ><a style='cursor: default' onclick='tongjiliandong1()'><span>侯问室总人数</span><p><i>"+data.countWaitingRoom+"</i>人</p></a></div>");
            //八大罪
            $("#boxTj").append("<div id='tongjiliandong2' class='item' ><a style='cursor: default' onclick='tongjiliandong2()'><span> 重点嫌疑人总人数</span><p><i>"+data.countZhongdian+"</i>人</p></a></div>");
            //未成年人总人数
            $("#boxTj").append("<div id='tongjiliandong3' class='item' ><a style='cursor: default' onclick='tongjiliandong3()'><span>未成年总人数</span><p><i>"+data.countIsJuvenile+"</i>人</p></a></div>");
            //传染病总人数
            $("#boxTj").append("<div id='tongjiliandong4' class='item' ><a style='cursor: default' onclick='tongjiliandong4()'><span>传染病总人数</span><p><i>"+data.countIsDisease+"</i>人</p></a></div>");
            //涉毒总人数
            $("#boxTj").append("<div id='tongjiliandong5' class='item' ><a style='cursor: default' onclick='tongjiliandong5()'><span>涉毒总人数</span><p><i>"+data.countshedu+"</i>人</p></a></div>");
            //孕妇总人数
            $("#boxTj").append("<div id='tongjiliandong6' class='item' ><a style='cursor: default' onclick='tongjiliandong6()'><span>孕妇总人数</span><p><i>"+data.countIsGravida+"</i>人</p></a></div>");
        }
    })
}

//看押
function detain(){
	$('#suspectNameWait').combogrid('setValue','');
    $("#personInPhoto").empty();
    var areaId = $('#areaCombobox').combobox('getValue');
    $("#detainDiv").show();
    //初始化
    initlizeDetain(areaId);
}

function initlizeDetain(areaId){
    //清楚缓存  var   row = null;
	roomIdSelect=='';
    $('#detainForm').form('clear');
 /*   $("#inphoto_detain").attr("src", "");*/
    $('#suspectNoWait').focus();
    var roomIdSelect = $("#roomId").val();
    //初始化嫌疑人列表
    $('#suspectNameWait').combogrid({
            panelWidth: 420,
            mode: 'remote',
            url: 'getAllDetainSuspectSerialNo.do?areaId=' + areaId,
            idField: 'id',
            textField: 'name',
            columns: [[{
                field: 'serialNo',
                title: '入区编号',
                width: 150
            }, {
                field: 'name',
                title: '姓名',
                width: 100
            }, {
                field: 'certificateNo',
                title: '证件号码',
                width: 170
            }, {
                field: 'areaId',
                title: '办案区域id',
                hidden: true
            }, {
                field: 'caseId',
                title: '案件id',
                hidden: true
            }]],
            onChange: function (value) {
                var cg = $('#suspectNameWait').combogrid('grid'); // 获取数据表格对象
                var row = cg.datagrid('getSelected');
                var roomWaitId = $('#roomWait').combobox('getValue');	// 获取数据表格对象
                if (row != null) {
                	recommendRoom(row.id);
                    var areaId= row.areaId;
                    var sysType = "ba";
                    var fileType ="rq";
                    var uuid = row.uuid;
                    var picName = row.inPhotoName;
                    $("#policeNoWait").val(row.sendUserNo);
                    $("#sendUserId1").val(row.inUserId1);
                    $('#personId').val(row.personId);
                    $('#caseId').val(row.caseId);
                    $('#areaId').val(row.areaId);
                    $('#serialId').val(row.id);
                    $('#roommId').val(roomWaitId);
                    $("#personInPhoto").empty();
                    $("#personInPhoto").append(fileUtils.look(sysType,fileType,uuid,areaId,picName))
                    var aImg=document.getElementById("personInPhoto").getElementsByTagName('img');
                    for(var i=0;i<aImg.length;i++){
                        aImg[i].style.height="150px";
                        aImg[i].style.width="150px";
                    }
                    alarm();
                }
            }
        });
    //初始化侯问室
    if(roomIdSelect==''){
        $('#roomWait').combobox({
            url: 'groupRoom.do?areaId=' + areaId,
            valueField: 'roomId',
            textField: 'roomName',
            groupField: 'areaName',
            formatter: function (row) {
                return row.roomName + '[' + row.male + '男' + row.female + '女]';
            },
            onLoadSuccess: function () {
                var data = $('#roomWait').combobox('getData');
                var flag = 0;
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length;i++) {
                        //优先选择空的侯问室
                        if (data[i].female == 0 && data[i].male == 0  && data[i].other == 0) {
                            $("#roomWait").combobox('select', data[i].roomId);
                            flag = 1;
                            break
                        }
                    }
                    if (flag == 0) {
                        $("#roomWait").combobox('select', data[0].roomId);
                    }
                }
            },
            onChange: function(value){
                $('#roommId').val(value);
                	alarm();
            }
        });
    }else if(roomIdSelect != ''){
        $('#roomWait').combobox({
            url: 'groupRoom.do?areaId=' + areaId,
            valueField: 'roomId',
            textField: 'roomName',
            groupField: 'areaName',
            formatter: function (row) {
                return row.roomName + '[' + row.male + '男' + row.female + '女]';
            },
            onLoadSuccess: function () {
                $("#roomWait").combobox('select', roomIdSelect);
            },
            onChange: function(value){
                $('#roommId').val(value);
                	alarm();
            }
        });
    }
}

//推荐功能室方法
function recommendRoom(serialId){
	$.ajax({
		type : 'GET',
		url : 'recommendRoom.do?t='+new Date().getTime(),
		data : 'serialId='+serialId,
		dataType : 'json',
		success : function(data){
			if(data!=-1){
				$('#roomWait').combobox('setValue',data);
			}
		},
		error : function(data){
			$.messager.alert('错误', '未知错误!'+data);
		}
	})
}
function alarm(){
	var cg = $('#suspectNameWait').combogrid('grid'); // 获取数据表格对象
	var row = cg.datagrid('getSelected');
	if( row!=null ){
	    var result="";
//	    var  flag=true;
	    	
	    	 //判断性别开始
	        var data = $("#roomWait").combobox('getData');
	        var roomId = $('#roomWait').combobox('getValue');
	        if (data != null && data.length > 0) {
	            for (var i = 0; i < data.length; i++) {
	                if (data[i].roomId == roomId) {
	                    if (row.sex == 1) {
	                        if (data[i].female > 0) {
	                            $.messager.alert("警告",
	                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
	                            return;
	                        }
	                        if (data[i].other > 0) {
	                            $.messager.alert("警告",
	                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
	                            return;
	                        }
	                    } else if (row.sex == 2) {
	                        if (data[i].male > 0) {
	                            $.messager.alert("警告",
	                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
	                            return;
	                        }
	                        if (data[i].other > 0) {
	                            $.messager.alert("警告",
	                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
	                            return;
	                        }
	                    }else if (row.sex == 0 || row.sex == 9) {
	                        if (data[i].male > 0) {
	                            $.messager.alert("警告",
	                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
	                            return;
	                        }
	                        if (data[i].female > 0) {
	                            $.messager.alert("警告",
	                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
	                            return;
	                        }
	                    }
	                    break;
	                }
	            }
	            //判断性别结束
	        }
	           sameCase(row,result);
	        }
    
}
function sameCase(row,result){
	 var roomId = $('#roomWait').combobox('getValue');
     var serialId= row.id;
     var areaId  = $('#areaCombobox').combobox('getValue');
     
	//判断是否是未成年人
	if(row.personType=="未成年人" ||row.isJuvenile==1){
		result+="-未成年";
	}
	 if(row.personType=="-孕妇" ||row.isGravida==1){
		//判断是否是孕妇
		result+="-孕妇";
	}
	 if(row.personType=="特殊疾病" ||row.isDisease==1){
		//判断是否特殊疾病
		result+="-特殊疾病";
	}
   
    $.ajax({
    	async:false,
        type: 'POST',
        url: 'queryRoomSameCase.do?roomId='+roomId+"&serialId="+serialId+"&areaId="+areaId,
        dataType: 'json',
        success: function (data) {
            if (data != -1) {
                var roomData = $('#roomWait').combobox('getText');
                // alert(data[0].roomName);
                	result="-有同案"+result;
                	 $.messager.show({
                	        id:'提示',
                	        title:'通知提示',
                	        msg:roomData +result +"-请注意协调！",
                	        timeout:5000,
                	        showType:'slide'
                	    });
//            		$.messager.alert("警告", roomData +result +"-请注意协调！");
            }else{
            	if(result==""){
            		
            	}else{
            		 $.messager.show({
             	        id:'提示',
             	        title:'通知提示',
             	        msg:"该嫌疑人为"+result+"-请注意协调！",
             	        timeout:5000,
             	        showType:'slide'
             	    });
//            		$.messager.alert("警告", "该嫌疑人为"+result+"-请注意协调！");
            	}
            	
            }
        }
    });
}
function finduser() {
    var userNo = $('#policeNoWait').val();
    if(userNo==null||userNo.length==0){
        return;
    }
    $('#userNo').val(userNo)
    var userNoInfo = $('#userNoInfo').serializeObject();
    userNoInfo["userNo"] = userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : '../order/searchUser.do',
        data : jsonrtinfo,
        success : function(data) {
            if (data != null && data.id != null) {
                $("#sendUserId1").val(data.id);
            } else {
                $.messager.alert('错误', userNo + '该警号不存在，请找系统管理员维护！');
                $("#sendUserId1").val("");
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');
            $("#sendUserId1").val("");
        }
    });
}

function saveDetainMessage() {
    if (!checkForm('#detainForm')) {
        return;
    }

    // start-----------------------------
    var cg = $('#suspectNameWait').combogrid('grid'); // 获取数据表格对象
    var row = cg.datagrid('getSelected');

    if(row != null){
        $.ajax({
            type: 'Get',
            url: 'queryRecord.do?sId='+row.id,
            dataType: 'json',
            success: function (data) {

                if(data.length >0){
                    $.messager.alert("提示",
                        "该嫌疑人处于审讯状态，请终止审讯！");
                    return;
                }
            }
        })
        var roomId = $('#roomWait').combobox('getValue');
        //判断性别开始
        var data = $("#roomWait").combobox('getData');
        if (data != null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].roomId == roomId) {
                    if (row.sex == 1) {
                        if (data[i].female > 0) {
                            $.messager.alert("警告",
                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
                            return;
                        }
                        if (data[i].other > 0) {
                            $.messager.alert("警告",
                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
                            return;
                        }
                    } else if (row.sex == 2) {
                        if (data[i].male > 0) {
                            $.messager.alert("警告",
                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
                            return;
                        }
                        if (data[i].other > 0) {
                            $.messager.alert("警告",
                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
                            return;
                        }
                    }else if (row.sex == 0 || row.sex == 9) {
                        if (data[i].male > 0) {
                            $.messager.alert("警告",
                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
                            return;
                        }
                        if (data[i].female > 0) {
                            $.messager.alert("警告",
                                "侯问室男女必须分离,请注意协调,不能进行看押操作！");
                            return;
                        }
                    }
                    break;
                }
            }
        }
        var roomId = $('#roomWait').combobox('getValue');
        var serialId= row.id;
        var areaId  = $('#areaCombobox').combobox('getValue');
        $.ajax({
            type: 'POST',
            url: 'queryRoomSameCase.do?roomId='+roomId+"&serialId="+serialId+"&areaId="+areaId,
            dataType: 'json',
            success: function (data) {
                if (data != -1) {
                    var roomData = $('#roomWait').combobox('getText');
                    // alert(data[0].roomName);
                    $.messager.alert("警告", roomData + "有同案,请注意协调！");
                    return;
                }
                }
        })
    }
    // end ---------------------------------
    //判断性别结束
    	 var cg = $('#suspectNameWait').combogrid('grid'); // 获取数据表格对象
    	  var row = cg.datagrid('getSelected');
        $('#personId').val(row.personId);
        $('#caseId').val(row.caseId);
        $('#areaId').val(row.areaId);
        /*if ($('#personId').val() == '' || $('#sendUserId1').val() == '') {
            $.messager.alert('', '请检查数据');
            return;
        }*/
        $.messager.progress({
            title: '请等待',
            msg: '数据处理中...'
        });
        var data = JSON.stringify($('#detainForm').serializeObject());
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'insert.do',
            data: data,
            dataType: 'json',
            success: function (data) {
                console.log(data)
                $.messager.progress('close');
                $.messager.show({
                    title: data.title,
                    msg: data.content,
                    timeout: 5000
                });
                document.getElementById("detainForm").reset();
                $('#detainDiv').hide()
                //刷新各个功能室
                //刷新
                var areaId2 = $('#areaCombobox').combobox('getValue');
                InitializatingRoom(areaId2)
                //初始化统计信息
                InitializatingStatisticalInformation(areaId2);
                //
                var roomId1 = $("#roomId1").val();
                freshWaitingMessage(roomId1,areaId2);
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert('错误', '未知错误!' + data);
            }
        });
    
}

function cancerDetainMessage(){
    $('#detainDiv').hide()
}
function checkForm(form_)
{
    var flag=$(form_).form('enableValidation').form('validate');
    if (!flag) {
        $.messager.alert('检查警告', '请检查有问题的数据！','info',function(){
            $(form_).form('enableValidation').form('validate');
        });
        return false;
    }else{
        return true;
    }
}
//更改侯问室
function changeRecordRoom(){
    var recordId =  $("#recordId2").val()
    if(recordId==""){
        $.messager.alert('提示', '请选择左方的一个嫌疑人!');
    }else{
        $("#changeRoomDiv").show()
        var areaId = $('#areaCombobox').combobox('getValue');
        changeRoom(areaId);
    }
}
function changeRoomMessage(roomId,areaId){
    //先获取input的存储的值
    var color = $("#roomId2").val()
    $("#roomId2").val(roomId)
    //将上次点击的div颜色复原
    if(color==""){
        document.getElementById(areaId+"_"+roomId).style.background="#008B00";
    }else if(color!=""){
        if(roomId!=color){
            document.getElementById(areaId+"_"+roomId).style.background="#008B00";
            document.getElementById(areaId+"_"+color).style.background="#173463";
        }else{
            document.getElementById(areaId+"_"+roomId).style.background="#008B00";
        }
    }
}

function saveChangeRoom(){
    var roomId1 = $("#roomId1").val();
    var roomId = $("#roomId2").val();
    var recordId = $("#recordId2").val();
    var areaId2 = $('#areaCombobox').combobox('getValue');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'changeRoom.do?roomId='+roomId+"&recordId="+recordId,
        success: function (data) {
            if(data=="updateSuccess"){
                $("#changeRoomDiv").hide()
                $.messager.show({
                    title: "提醒",
                    msg: "更换侯问室成功！",
                    timeout: 5000
                });
                InitializatingRoom(areaId2)
                //重新加载
                freshWaitingMessage(roomId1,areaId2);
            }else{
                $.messager.alert('错误', '更换侯问室失败！');
            }
        }
    })
}

function changeRoom(areaId){
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            $("#boxRoomChange").empty();
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            //先统一拼接好侯问室
                            $("#boxRoomChange").append("<li ><div id='"+clist[j].areaId+"_"+clist[j].roomId+"' onclick='changeRoomMessage("+clist[j].roomId+","+clist[j].areaId+")'><a style='display: inline;cursor: default'><span>"+clist[j].roomName+"</span></a>   </div></li>");
                            //再拼接人员
                            //房间全是男的
                            if(clist[j].male!=0 && clist[j].female==0 && clist[j].other==0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].male+"</span><span style='font-size:10px'>男</span></p></div>")
                            }
                            //房间全是女的
                            if(clist[j].male==0 && clist[j].female!=0 && clist[j].other==0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].female+"</span><span style='font-size:10px'>女</span></p></div>")
                            }
                            //房间有男有女
                            if(clist[j].male!=0 && clist[j].female!=0 && clist[j].other==0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].male+"</span><span style='font-size:10px'>男</span></p></div> <div><p><span style='font-size:10px'>"+clist[j].female+"</span><span style='font-size:10px'>女</span></p><i></i></div>")
                            }
                            //房间无男无女无未知性别（无人）的时候
                            if(clist[j].male==0 && clist[j].female==0 && clist[j].other==0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'></span><span style='font-size:10px'>空</span></p></div> ")
                            }
                            //以下是特殊情况（有未知性别的时候）
                            //当房间都是未知性别的时候
                            if(clist[j].other!=0 && clist[j].male==0&& clist[j].female==0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].other+"</span><span style='font-size:10px'>未知性别</span></p></div> ")
                            }
                            //当房间有未知性别有男
                            if(clist[j].other!=0 && clist[j].male!=0&& clist[j].female==0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].other+"</span><span style='font-size:10px'>未知性别</span></p></div> <div><p><span style='font-size:10px'>"+clist[j].male+"</span><span style='font-size:10px'>男</span></p></div>")
                            }
                            //当房间有未知性别有女
                            if(clist[j].other!=0 && clist[j].male==0&& clist[j].female!=0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].other+"</span><span style='font-size:10px'>未知性别</span></p></div> <div><p><span style='font-size:10px'>"+clist[j].female+"</span><span style='font-size:10px'>女</span></p><i></i></div>")
                            }
                            //当房间有未知性别有女有男
                            if(clist[j].other!=0 && clist[j].male!=0&& clist[j].female!=0){
                                $("#"+clist[j].areaId+"_"+clist[j].roomId+"").append("<div><i></i><p><span style='font-size:10px'>"+clist[j].other+"</span><span style='font-size:10px'>未知性别</span></p></div> <div><p><span style='font-size:10px'>"+clist[j].female+"</span><span style='font-size:10px'>女</span></p></div> <div><p><span style='font-size:10px'>"+clist[j].male+"</span><span style='font-size:10px'>男</span></p><i></i></div>")
                            }
                        }
                    }else{
                        $.messager.alert('提醒', '还未维护候问室！');
                    }
                }
            }
        }
    })
}

function  cancerChangeRoom() {
    $('#changeRoomDiv').hide()
}
function refreshRoom(){
    var areaId = $('#areaCombobox').combobox('getValue');
    //刷新侯问室
    InitializatingRoom(areaId)
   //初始化统计信息
    InitializatingStatisticalInformation(areaId);
    $("#suspectMan").empty();
    $("#oneSuspectInfo").empty();
    $("#oneSuspectInfo").append("<li><i>请选择一个上方的侯问室</i></li>")
    $.messager.show({
        title: "提醒",
        msg: "刷新侯问室成功！",
        timeout: 1000
    });
}

//选择去向
function changeDirection(){
    var recordId =  $("#recordId2").val()
    $("#direction2").val("")
    document.getElementById('xxcj').style.background="#173463";
    document.getElementById('njs').style.background="#173463";
    document.getElementById('wsj').style.background="#173463";
    document.getElementById('brs').style.background="#173463";
    document.getElementById('yws').style.background="#173463";
    document.getElementById('xjs').style.background="#173463";
    document.getElementById('dna').style.background="#173463";
    document.getElementById('cq').style.background="#173463";
    document.getElementById('qt').style.background="#173463";
    if(recordId==""){
        $.messager.alert('提示', '请选择左方的一个嫌疑人!');
    }else{
        $("#directRoomDiv").show()
    }
}

function selectDirection(param){
var id = $("#recordId2").val()
  if(param=="xxcj"){
      document.getElementById('xxcj').style.background="#008B00";
      document.getElementById('njs').style.background="#173463";
      document.getElementById('wsj').style.background="#173463";
      document.getElementById('brs').style.background="#173463";
      document.getElementById('yws').style.background="#173463";
      document.getElementById('xjs').style.background="#173463";
      document.getElementById('dna').style.background="#173463";
      document.getElementById('cq').style.background="#173463";
      document.getElementById('qt').style.background="#173463";
      $("#direction2").val("信息采集")
  }
  if(param=="njs"){
      document.getElementById('xxcj').style.background="#173463";
      document.getElementById('njs').style.background="#008B00";
      document.getElementById('wsj').style.background="#173463";
      document.getElementById('brs').style.background="#173463";
      document.getElementById('yws').style.background="#173463";
      document.getElementById('xjs').style.background="#173463";
      document.getElementById('dna').style.background="#173463";
      document.getElementById('cq').style.background="#173463";
      document.getElementById('qt').style.background="#173463";
      $("#direction2").val("尿检室")
    }
    if(param=="wsj"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#008B00";
        document.getElementById('brs').style.background="#173463";
        document.getElementById('yws').style.background="#173463";
        document.getElementById('xjs').style.background="#173463";
        document.getElementById('dna').style.background="#173463";
        document.getElementById('cq').style.background="#173463";
        document.getElementById('qt').style.background="#173463";
        $("#direction2").val("卫生间")
    }
    if(param=="brs"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#173463";
        document.getElementById('brs').style.background="#008B00";
        document.getElementById('yws').style.background="#173463";
        document.getElementById('xjs').style.background="#173463";
        document.getElementById('dna').style.background="#173463";
        document.getElementById('cq').style.background="#173463";
        document.getElementById('qt').style.background="#173463";
        $("#direction2").val("辨认室")
    }
    if(param=="yws"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#173463";
        document.getElementById('brs').style.background="#173463";
        document.getElementById('yws').style.background="#008B00";
        document.getElementById('xjs').style.background="#173463";
        document.getElementById('dna').style.background="#173463";
        document.getElementById('cq').style.background="#173463";
        document.getElementById('qt').style.background="#173463";
        $("#direction2").val("医务室")
    }
    if(param=="xjs"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#173463";
        document.getElementById('brs').style.background="#173463";
        document.getElementById('yws').style.background="#173463";
        document.getElementById('xjs').style.background="#008B00";
        document.getElementById('dna').style.background="#173463";
        document.getElementById('cq').style.background="#173463";
        document.getElementById('qt').style.background="#173463";
        $("#direction2").val("醒酒室")
    }
    if(param=="dna"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#173463";
        document.getElementById('brs').style.background="#173463";
        document.getElementById('yws').style.background="#173463";
        document.getElementById('xjs').style.background="#173463";
        document.getElementById('dna').style.background="#008B00";
        document.getElementById('cq').style.background="#173463";
        document.getElementById('qt').style.background="#173463";
        $("#direction2").val("DNA")
    }
    if(param=="cq"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#173463";
        document.getElementById('brs').style.background="#173463";
        document.getElementById('yws').style.background="#173463";
        document.getElementById('xjs').style.background="#173463";
        document.getElementById('dna').style.background="#173463";
        document.getElementById('cq').style.background="#008B00";
        document.getElementById('qt').style.background="#173463";
        $("#direction2").val("出区")
    }
    if(param=="qt"){
        document.getElementById('xxcj').style.background="#173463";
        document.getElementById('njs').style.background="#173463";
        document.getElementById('wsj').style.background="#173463";
        document.getElementById('brs').style.background="#173463";
        document.getElementById('yws').style.background="#173463";
        document.getElementById('xjs').style.background="#173463";
        document.getElementById('dna').style.background="#173463";
        document.getElementById('cq').style.background="#173463";
        document.getElementById('qt').style.background="#008B00";
        $("#direction2").val("其他")
    }
}

function saveChangeDirect(){
    var id= $("#recordId").val();
    var direction= $("#room-name").val();
    var serialId1= $("#serialId").val();
    var roomId3= $("#roomId3").val();
    var user1 = $("#user1Val").val();
    var user2 = $("#user2Val").val();
    if(direction==''){
        $.messager.alert('提醒', '请选择一个去向！');
        return;
    }
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    $.ajax({
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        url: 'saveDirection.do?id='+id+"&direction="+encodeURIComponent(direction)+"&user1="+user1+"&user2="+user2+"&serialId1="+encodeURIComponent(serialId1)+"&roomId3="+encodeURIComponent(roomId3),
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $('#directRoomDiv').hide();
            $.messager.show({
                title: data.title,
                msg: data.content,
                timeout: 5000
            });
            $("#tiXunDialog").hide();
            var areaId = $('#areaCombobox').combobox('getValue');
            var roomId1= $("#roomId1").val()
            //初始化侯问室
            InitializatingRoom(areaId)
            //初始化统计信息
            InitializatingStatisticalInformation(areaId);
            //重设去向
            // document.getElementById("spanText").innerHTML = direction
            //展示返回侯问室的按钮
            $("#backRoomDiv").show();
            //重新加载
            freshWaitingMessage(roomId1,areaId);
            //
            //var serialId1 = $("#serialId1").val();
            var oDiv = document.getElementById(serialId1); //获取元素div
            oDiv.click();
        }
    })
}

function cancerChangeDirect(){
    $("#direction2").val("");
    $('#directRoomDiv').hide()
}

function goBackRoom(){
    // 返回
    var id = $("#recordId2").val();
        $.messager.progress({
            title: '请等待',
            msg: '数据处理中...'
        });
        $.ajax({
            type: 'POST',
            url: 'goBack.do',
            data: {
                id: id,
                direction: ""
            },
            dataType: 'json',
            success: function (data) {
                $.messager.progress('close');
                $.messager.show({
                    title: '提示',
                    msg: data.content,
                    timeout: 5000
                });
                doRefreshMessage();
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert('错误', '未知错误!' + data);
            }
        });
}

function doRefreshMessage(){
    var areaId = $('#areaCombobox').combobox('getValue');
    //初始化侯问室
    InitializatingRoom(areaId)
    //初始化统计信息
    InitializatingStatisticalInformation(areaId);
    //重设去向
    document.getElementById("spanText").innerHTML = waritRoom
    //隐藏返回侯问室的按钮
     $("#backRoomDiv").hide();
    //
    var roomId1= $("#roomId1").val()
    freshWaitingMessage(roomId1,areaId);

    var serialId1 = $("#serialId1").val()
    var oDiv = document.getElementById(serialId1); //获取元素div
    oDiv.click();
}

function history(){
    $('#hisDiv').show()
    var areaId = $('#areaCombobox').combobox('getValue');
    $('#room_his').combobox({
        url: 'groupRoom.do?areaId=' + areaId,
        valueField: 'roomId',
        textField: 'roomName',
        onLoadSuccess: function (data) {
            $('#room_his').combobox('setValue', '');
        }
    });
    $('#histroy_datagrid').datagrid({
        iconCls : 'icon-datagrid',
        region : 'center',
        width : '1200px',
        height : '400px',
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        loadMsg : '加载中...',
        method : 'get',
        url : 'listRecord.do',
        queryParams : {
            trefresh:new Date().getTime(),
            areaId:$('#areaCombobox').combobox('getValue')
        },
        sortName : 'id',
        sortOrder : 'desc',
        remoteSort : false,
        idField : 'id',
        singleSelect : true,
        frozenColumns : [ [ {
            title : 'id',
            field : 'id',
            width : 80,
            sortable : true,
            hidden : true
        } ] ],
        columns : [ [
            {
                field : 'personName',
                title:'嫌疑人',
                align : 'center',
                width:40,
            },{
                field : 'caseType',
                title:'案件类型',
                align : 'center',
                width:40,
                formatter : function(value) {
                    if(value == 0){
                        return "刑事";
                    }else if(value==1){
                        return "行政";
                    }else if(value==2){
                        return "刑事";
                    }else{
                        return "其他";
                    }
                }
            },{
                field : 'caseNature',
                title : '案件性质',
                align : 'center',
                width : 80
            },{
                field : 'sex',
                title : '性别',
                align : 'center',
                width : 50,
                formatter : function(value) {
                    if (value == 1) {
                        return '男';
                    }
                    if (value == 2) {
                        return '女';
                    }
                    if (value == 0) {
                        return '未知性别';
                    }
                    if (value == 9) {
                        return '未说明性别';
                    }
                }
            },{
                field : 'personcertNo',
                title : '证件号码',
                align : 'center',
                width : 120
            },{
                field : 'roomName',
                title : '侯问室',
                align : 'center',
                width : 80
            },{
                field : 'status',
                title : '状态',
                align : 'center',
                width : 50,
                formatter : function(value) {
                    if (value == 0)
                        return '<font color="#00CC66">已看押</font>';
                    if (value == 1)
                        return '已提讯';
                }
            }, {
                field: 'sendUserName1',
                title: '送押民警',
                width: 80,
            },
            {
                field: 'getUserName1',
                title: '提讯民警',
                width: 70,
                formatter : function(value, row) {
                    var str = "";
                    if(row.getUserName1) str += row.getUserName1;
                    if(str && str.length>0) str += ",";
                    if(row.getUserName2) str += row.getUserName2;
                    return str;
                }
            },
            {
                field: 'inTime',
                title: '进入时间',
                width: 70,
                formatter : function(value) {
                    if(value!=null && value!=''){
                        return  valueToDate(value);
                    }else{
                        return "";
                    }
                }
            },
            {
                field: 'outTime',
                title: '离开时间',
                width: 80,
                formatter : function(value) {
                    if(value!=null && value!=''){
                        return  valueToDate(value);
                    }else{
                        return "";
                    }

                }
            },
            {
                field: 'getResult',
                title: '去向',
                width: 80,
            },
        ] ],
        pagination : true,
        pageList : [ 10, 20, 30, 40, 50, 100 ],
        rownumbers : true,
        toolbar:'#toolbar',
    });
}




function doSearchWait() {
    var areaId = $('#areaCombobox').combobox('getValue');
    $.messager.progress({
        title: '请等待',
        msg: '数据处理中...'
    });
    $('#histroy_datagrid').datagrid('load', {
        cardId: $('#zjhm').val(),
        roomId: $('#room_his').combobox('getValue'),
        personName: $('#s_personName_his').val(),
        beginTime: $('#d_begin_date_his').datebox('getValue'),
        endTime: $('#d_end_date_his').datebox('getValue'),
        trefresh: new Date().getTime(),
        areaId:areaId,
    });
    $.messager.progress('close');
}

function doClearWairt(){
    $('#s_personName_his').prop('value', '');
    $('#zjhm').prop('value', '');
    $('#d_begin_date_his').datebox('setValue', '');
    $('#d_end_date_his').datebox('setValue', '');
    $('#room_his').combobox('setValue', '');
}

//键盘监听事件
function enterKeyEventWait(){
    var e = e || window.event;
    if (e.keyCode == 13) {
        checkRing($("#suspectNoWait").val());
    }
}


//手环校验
//手环校验
function checkRing(icNo) {
    var checkData = checkRingNo(icNo, 0);
    if (checkData.isError) {
        $.messager.alert("提示", "手环不存在");
        $('#suspectNoWait').val('');
        $('#suspectNameWait').combogrid('grid').datagrid('reload');
        $("#policeNoWait").val("")
        $("#sendUserId1").val("")
        $('#personId').val("");
        $('#caseId').val("");
        $('#areaId').val("");
        $('#serialId').val("");
        $("#personInPhoto").empty()
        return;
    }else{
        if (checkData.callbackData.cuffNo) {
            var cuff = $('#cuffInfo').serializeObject();
            cuff["cuffNo"] = checkData.callbackData.cuffNo;
            cuff["type"] = 0;
            var jsonrtinfo = JSON.stringify(cuff);
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: '../serial/getSerialByCuffNo.do',
                data: jsonrtinfo,
                dataType: 'json',
                success: function (data) {
                    if(data.callbackData!= null){
                    	var status = data.callbackData.status
                    	if(status == 3 || status == 6){
                    		$.messager.alert('提醒', '该手环绑定嫌疑人处于看押中或审讯中!');
                    	}else{
                            var id = data.callbackData.id;
                            $('#suspectNameWait').combogrid('setValue', id);
                            $('#suspectNoWait').val(data.callbackData.cuffNo);
                            finduser();
                    	}
                    }
                    else{
                        $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                    }

                },
                error: function (data) {
                    $.messager.alert('错误', '未知错误!');
                }
            })
        }else{
            $.messager.alert('警告', '读卡失败!');
            $('#suspectNoWait').val('');
        }
    }
}

// 等候管理首页导出
function doExport() {
    $('#areaId_exp').val($('#areaCombobox').combobox("getValue"));
    document.getElementById("exportForm").submit();
}

// 记录里面导出
function waitingmanageExport(){
    var data=$('#histroy_datagrid').datagrid('getData');
    if(data){
        var total = data.total;
        if(total>5000){
            $.messager.alert("提示", "导出条数["+total+"]超出最大值5000！");
            return;
        }
    }
    $('#s_areaId').val($('#areaCombobox').combobox("getValue"));
    document.getElementById("exportHistoryForm").submit();
}

function tiXun() {
    tixunFormClear2(); // 清空提讯表单数据
	$("#tiXunDialog").show();
}

function tixunFormClear2(){
    $('#arraign-form')[0].reset();
}

// 提讯弹出框关闭
function closeTiXunDialog(){
    $('#tiXunDialog').hide();
}

//提讯选择去向
function tiXunChangeDirection(){
    if (!checkForm('#arraign-form')) {
        return;
    }
    document.getElementById('xxcj').style.background="#173463";
    document.getElementById('njs').style.background="#173463";
    document.getElementById('wsj').style.background="#173463";
    document.getElementById('brs').style.background="#173463";
    document.getElementById('yws').style.background="#173463";
    document.getElementById('xjs').style.background="#173463";
    document.getElementById('dna').style.background="#173463";
    document.getElementById('cq').style.background="#173463";
    document.getElementById('qt').style.background="#173463";
    // 加载所有空闲的审讯室
    var recordRoomHtml = '';
    var areaId = $('#areaCombobox').combobox("getValue");
    jQuery.ajax({
        type: 'POST',
        url: contextPath + '/zhfz/bacs/arraign/listAllRecordRoom.do',
        data:{'areaId':areaId},
        dataType: 'json',
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var row = data[i];
                recordRoomHtml += '<li>' +
                    '<div ondblclick="selectRecordRoom(\''+data[i].name+'\','+data[i].id+')" id="'+data[i].areaId+'_'+data[i].id+'" name="other">' +
                    '<div><i style="background-image: url(../waitingmanage/image/xxcj.png);"></i></div>' +
                    '<a name="noButton">' +
                    '<span>'+data[i].name+'-空闲</span>' +
                    '</a>' +
                    '</div></li>';
            }
            $("#recordRoom").html(recordRoomHtml);
            $("#directRoomDiv").show();
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function selectRecordRoom(roomName,roomId) {
        $("#room-name").val(roomName);
        $("#directRoomDiv").hide();
        if(roomId != null && roomId != ''){
            $("#roomId3").val(roomId);
        }
}

// 验证警察编号是否存在searchInUser1
function finduserTiXun() {
    var userNo = $('#policeNoOut').val();
    if(userNo==null||userNo.length==0){
        return;
    }
    var userNoInfo ={};
    userNoInfo["userNo"] = userNo;
    var jsonrtinfo = JSON.stringify(userNoInfo);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/arraign/checkPolceNo.do',
        data : jsonrtinfo,
        success : function(data) {
            if(data.error){
                $.messager.alert(data.title, data.content);
                $("#policeIdOut").val('');
            }else if(data.callbackData != null){
                $("#policeIdOut").val(data.callbackData.id);
            }
        },
        error : function(data) {
            $("#policeIdOut").val('');
            $.messager.alert('错误', '警号不存在，请找系统管理员维护！');
        }
    });
}

function policeCardTiXun(_this,event) {
    _this = $(_this);
    if(event.keyCode == 13){
        var data = checkRingNo(_this.val(),1);
        if(!data.isError && data.callbackData.status == 1
            && data.callbackData.cuffNo != null && data.callbackData.cuffNo != ""){
            $('#policCuff').val(data.callbackData.cuffNo);
            // 获取民警警号
            jQuery.ajax({
                type : 'POST',
                async : false,
                dataType : 'json',
                url : contextPath + '/zhfz/bacs/policeEntrance/findPoliceNoById.do',
                data : {'userId':data.callbackData.userId},
                success: function(returnData){
                    $('#policeNoOut').val(returnData.callbackData.certificateNo);
                    $('#policeIdOut').val(returnData.callbackData.id);
                },
                error: function(data){
                    $.messager.progress('close');
                    $.messager.alert('提示', '获取民警警号失败!');
                    $('#policeNoOut').val("");
                    $('#policeIdOut').val("");
                }
            });
        }else{
            $.messager.alert(data.title, data.content);
            $('#policeNoOut').val("");
            $('#policeIdOut').val("");
        }
    }
}

function personCardTiXun(_this,event){
    _this = $(_this);
    if(event.keyCode == 13){
        var data = checkRingNo(_this.val(),0);
        if(!data.isError && data.callbackData.status == 1
            && data.callbackData.cuffNo != null && data.callbackData.cuffNo != ""){
            $('#suspectNoTiXun').val(data.callbackData.cuffNo);
            $('#save-combgrid').combogrid('setValue', data.callbackData.personId);
           /* var g = $('#save-combgrid').combogrid('grid');	// get datagrid object
            var row = g.datagrid('getSelected');
            $('#serialId2').val(row.serialId);
            $('#save-combgrid').combogrid('setValue', row);*/
        }else{
            $.messager.alert(data.title, data.content);
        }
    }
}

function tongjiliandong1() {
    var areaId = $('#areaCombobox').combobox("getValue");
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            document.getElementById(clist[j].roomId).style.background="#173463";
                        }
                    }
                }
            }
        }
    });
    document.getElementById('tongjiliandong1').style.background="#008B00";
    document.getElementById('tongjiliandong2').style.background="#173463";
    document.getElementById('tongjiliandong3').style.background="#173463";
    document.getElementById('tongjiliandong4').style.background="#173463";
    document.getElementById('tongjiliandong5').style.background="#173463";
    document.getElementById('tongjiliandong6').style.background="#173463";
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listWaitingRoomAll.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

function tongjiliandong2() {
    var areaId = $('#areaCombobox').combobox("getValue");
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            document.getElementById(clist[j].roomId).style.background="#173463";
                        }
                    }
                }
            }
        }
    });
    document.getElementById('tongjiliandong2').style.background="#008B00";
    document.getElementById('tongjiliandong1').style.background="#173463";
    document.getElementById('tongjiliandong3').style.background="#173463";
    document.getElementById('tongjiliandong4').style.background="#173463";
    document.getElementById('tongjiliandong5').style.background="#173463";
    document.getElementById('tongjiliandong6').style.background="#173463";
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listZhongdianAll.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

function tongjiliandong3() {
    var areaId = $('#areaCombobox').combobox("getValue");
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            document.getElementById(clist[j].roomId).style.background="#173463";
                        }
                    }
                }
            }
        }
    });
    document.getElementById('tongjiliandong3').style.background="#008B00";
    document.getElementById('tongjiliandong2').style.background="#173463";
    document.getElementById('tongjiliandong1').style.background="#173463";
    document.getElementById('tongjiliandong4').style.background="#173463";
    document.getElementById('tongjiliandong5').style.background="#173463";
    document.getElementById('tongjiliandong6').style.background="#173463";
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listIsJuvenileAll.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

function tongjiliandong4() {
    var areaId = $('#areaCombobox').combobox("getValue");
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            document.getElementById(clist[j].roomId).style.background="#173463";
                        }
                    }
                }
            }
        }
    });
    document.getElementById('tongjiliandong4').style.background="#008B00";
    document.getElementById('tongjiliandong2').style.background="#173463";
    document.getElementById('tongjiliandong3').style.background="#173463";
    document.getElementById('tongjiliandong1').style.background="#173463";
    document.getElementById('tongjiliandong5').style.background="#173463";
    document.getElementById('tongjiliandong6').style.background="#173463";
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listIsDiseaseAll.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

function tongjiliandong5() {
    var areaId = $('#areaCombobox').combobox("getValue");
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            document.getElementById(clist[j].roomId).style.background="#173463";
                        }
                    }
                }
            }
        }
    });
    document.getElementById('tongjiliandong5').style.background="#008B00";
    document.getElementById('tongjiliandong2').style.background="#173463";
    document.getElementById('tongjiliandong3').style.background="#173463";
    document.getElementById('tongjiliandong4').style.background="#173463";
    document.getElementById('tongjiliandong1').style.background="#173463";
    document.getElementById('tongjiliandong6').style.background="#173463";
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listsheduAll.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

function tongjiliandong6() {
    var areaId = $('#areaCombobox').combobox("getValue");
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'personRoomCountList.do?areaId=' + areaId + '&trefresh='+ new Date().getTime(),
        dataType: 'json',
        success: function (data) {
            var list = data.list;
            if (list.length > 0) {
                for (var i=0; i < list.length; i++) {
                    var count = 0;
                    var clist = list[i];
                    if (clist.length > 0) {
                        for (var j = 0; j < clist.length; j++) {
                            document.getElementById(clist[j].roomId).style.background="#173463";
                        }
                    }
                }
            }
        }
    });
    document.getElementById('tongjiliandong6').style.background="#008B00";
    document.getElementById('tongjiliandong2').style.background="#173463";
    document.getElementById('tongjiliandong3').style.background="#173463";
    document.getElementById('tongjiliandong4').style.background="#173463";
    document.getElementById('tongjiliandong5').style.background="#173463";
    document.getElementById('tongjiliandong1').style.background="#173463";
    $.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listIsGravidaAll.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            var sysType = "ba";
            var fileType ="rq";
            var areaId= "";
            var uuid = "";
            var picName = "";
            var areaId = "";
            if(data !="error"){
                $("#suspectMan").empty();
                $("#oneSuspectInfo").empty()
                if(data.length==0){
                    $("#oneSuspectInfo").append("<li><i>该房间暂时未看押嫌疑人</i></li>")
                }else{
                    $("#oneSuspectInfo").append("<li><i>请选择左侧一个嫌疑人</i></li>")
                }
                for(var i=0;i<data.length;i++){
                    uuid = data[i].uuid;
                    picName = data[i].inPhotoName;
                    areaId = data[i].areaId;
                    var getResult = ""
                    if(data[i].getResult!=null && data[i].getResult!='' ){
                        getResult = data[i].getResult;
                    }
                    $("#suspectMan").append("<div  class='item m-box now' id='"+data[i].serialId+"'  onclick='freshSuspect(\""+data[i].isJuvenile+"\",\""+data[i].isGravida+"\",\""+data[i].isGravidaProve+"\",\""+data[i].gravidaMonth+"\",\""+data[i].isDisease+"\",\""+data[i].isDiseaseProve+"\",\""+data[i].officer+"\",\""+data[i].personName+"\",\""+data[i].recordName+"\",\""+data[i].sendUserName1+"\",\""+data[i].inTime+"\",\""+data[i].caseType+"\",\""+data[i].caseNature+"\",\""+data[i].recordId+"\",\""+data[i].getResult+"\",\""+data[i].status+"\",\""+data[i].serialId+"\",\""+data[i].sex+"\")'><i>"+fileUtils.look(sysType,fileType,uuid,areaId,picName)+"</i><span>"+data[i].personName+"</span><div class='edges'></i><i class='i1' id='"+data[i].serialId+"i1'></i><i class='i2' id='"+data[i].serialId+"i2'></i></i><i class='i3' id='"+data[i].serialId+"i3'></i><i class='i4' id='"+data[i].serialId+"i4'></i></div><font color='red'>"+getResult+"</font></div>");
                }

                var aImg=document.getElementById("personnel").getElementsByTagName('img');
                for(var i=0;i<aImg.length;i++){
                    aImg[i].style.height="110%";
                    aImg[i].style.width="110%";
                }
            }else{
                $.messager.alert('错误', '获取侯问数据异常');
            }
        }
    })
    //重置嫌疑人的信息列表
    /*$("#oneSuspectInfo").empty()*/
    $("#butBar").hide();
}

function readRingNo(type){
	var number2="";

    //新读卡器新增代码开始
    var ringOcx = document.getElementById("ocx");
    number2=readRing(ringOcx);
    
	if(number2!=""){
		//alert("读卡成功！number2= "+number2);
		var cuff = $('#cuffInfo').serializeObject();
		cuff["cuffNo"]=number2;
		 cuff["type"] = 0;
		var jsonrtinfo = JSON.stringify(cuff);
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
            url: '../serial/getSerialByCuffNo.do',//看押专用
			data : jsonrtinfo,
			dataType : 'json',
			success : function(data){
				//$.messager.alert(data.title, data.content); getSerialByCuffNo
				var id=data.callbackData.id;
				if(id!=null&&id!=""){
					if(type=='detain'){
                        $('#suspectNameWait').combogrid('setValue', id);
                        $('#suspectNoWait').val(data.callbackData.cuffNo);
                        finduser();
					}
					if(type=='arraign'){
						  	$('#suspectNoTiXun').val(data.callbackData.cuffNo);
				            $('#save-combgrid').combogrid('setValue',data.callbackData.personId);
					}
				}else{
					$.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
				}
			},
			error : function(data){
				$.messager.alert('错误', '未知错误!');
			}
		});  
	}else{
		alert("读卡失败！");
	}
}

function readRingNoUser1(type){
    var number2="";

    //新读卡器新增代码开始
    var ringOcx = document.getElementById("ocx");
    number2=readRing(ringOcx);

    if(number2!=""){
        //alert("读卡成功！number2= "+number2);
        var cuff = $('#cuffInfo').serializeObject();
        cuff["cuffNo"]=number2;
        cuff["type"] = 0;
        var jsonrtinfo = JSON.stringify(cuff);
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url: '../serial/getSerialByCuffNo.do',//看押专用
            data : jsonrtinfo,
            dataType : 'json',
            success : function(data){
                //$.messager.alert(data.title, data.content); getSerialByCuffNo
                var id=data.callbackData.id;
                if(id!=null&&id!=""){
                    if(type=='detain'){
                        $('#suspectNameWait').combogrid('setValue', id);
                        $('#suspectNoWait').val(data.callbackData.cuffNo);
                        finduser();
                    }
                    if(type=='arraign'){
                        $('#suspectNoTiXun').val(data.callbackData.cuffNo);
                        $('#save-combgrid').combogrid('setValue',data.callbackData.personId);
                    }
                }else{
                    $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                }
            },
            error : function(data){
                $.messager.alert('错误', '未知错误!');
            }
        });
    }else{
        alert("读卡失败！");
    }
}

function tixunFormClear() {
    $("#user1Text").val('');
    $("#user1Val").val('');
    $("#user2Text").val('');
    $("#user2Val").val('');
    if ($('#xianYiRenDataGrid').datagrid()){
        $('#xianYiRenDataGrid').datagrid('loadData',{total:0,rows:[]});
    }
}

function showXianYiRenDialog(){
    tixunFormClear();
    $("#xianyirenDialog").show();
}

function queryUserByCuff(inputId){
    var number2="";//14470

    //新读卡器新增代码开始
    var ringOcx = document.getElementById("ocx");
    number2=readRing(ringOcx);

    if(number2!=""){
        $.get("../../../common/queryUserByCuffNo.do?cuffNo="+number2, function(data){
            if(data){
                data = eval("("+data+")");
                if(data){
                    $("#"+inputId+"Text").val(data.real_name+"("+data.certificate_no+")");
                    $("#"+inputId+"Val").val(data.id);
                    queryXianYiRenData();
                }else{
                    $("#"+inputId+"Text").val('');
                    $("#"+inputId+"Val").val('');
                    $.messager.alert('错误', '无效的卡片或者未绑定民警!');
                }
            }else{
                $("#"+inputId+"Text").val('');
                $("#"+inputId+"Val").val('');
                $.messager.alert('错误', '无效的卡片或者未绑定民警!');
            }
        });
    }else{
        alert("读卡失败！");
    }

}

function queryXianYiRenData(){
    var user1 = $("#user1Val").val();
    if(user1){
        $('#xianYiRenDataGrid').datagrid({
            width: '100%',
            height: '100%',
            fitColumns: true,
            rownumbers: true,
            striped: true,
            singleSelect: true,
            nowrap:false,
            url : '../../../common/querySerialByUser12.do',
            queryParams : {
                trefresh:new Date().getTime(),
                user1:user1
            },
            columns : [ [
                {field : 'name',title:'嫌疑人姓名',align : 'center',width:20},
                {field : 'certificate_no',title:'证件号码',align : 'center',width:40},
                {field : 'in_time',title:'入区时间',align : 'center',width:40,formatter:function(value){return formatter(value, 'yyyy-MM-dd hh:mm:ss');}},
                {field : 'real_name',title:'送押民警',align : 'center',width:20},
                {field : 'orgname',title:'办案单位',align : 'center',width:40}
            ] ],
            rowStyler:function(index,row){
                if (row.isKy==0){
                    return 'background-color:pink;color:blue;font-weight:bold;';
                }
            }
        });
    }else {
        $.messager.alert('错误', '刷卡失败!');
    }

}

function checkCuffNo(){
    if($("#user1Val").val()){
        var number2="";//41875
        //新读卡器新增代码开始
        var ringOcx = document.getElementById("ocx");
        number2=readRing(ringOcx);
        if(number2!=""){
            var serialData = $('#xianYiRenDataGrid').datagrid("getData");
            if(serialData){
                var flag = false;
                for (var i = 0; i < serialData.rows.length; i++) {
                    var obj = serialData.rows[i];
                    if(obj.cuff_no==number2 && obj.isKy==0){
                        $.messager.confirm('确认框', '确认要提讯嫌疑人【'+obj.name+'】？', function(r){
                            if (r){
                                $("#serialId").val(obj.id);
                                $("#save-combgrid").val(obj.name);
                                $("#recordId").val(obj.record_id);
                                $("#xianyirenDialog").hide();
                            }
                        });
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    $.messager.alert('错误', '该手环嫌疑人未在侯问室!');
                }
            }else{
                $.messager.alert('错误', '该手环嫌疑人未在侯问室!');
            }

        }else{
            $.messager.alert('错误', '扫描手环失败!');
        }
    }else{
        $.messager.alert('错误', '请先刷民警卡片!');
    }

}