var roomData = null,listRoom = [];
function init(){
    $('#query-combobox').combobox({
        url: '../combobox/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('#query-combobox').combobox('select', data[0].id);
        },
        onChange: function () {
            doSearch();
        }
    });
}
$(function () {
    init();
});
function doSearch() {
    $.messager.progress({
        title: '请等待',
        msg: '数据加载中...'
    });
    var params = {trefresh:new Date().getTime()}
    jQuery.ajax({
        type: 'POST',
        url: 'queryRoomInfo.do',
        data:$('#search').serializeObject(params),
        dataType: 'json',
        success: function (data) {
        	createDiv(data);
//            listRoom = [];
//            $.messager.progress('close');
//            var box = $('#box').html();
//            var s = '';
//            $.each(data,function (index,obj) {
//                dealObj(obj,index);
//                s += box.format(obj);
//            });
//            roomData = data;
//            $('.context').html(s);
//            $('#leisure-room').combobox({
//                data:listRoom,
//                valueField:'id',
//                textField:'nameValue'
//            });
//            $('#right').html('');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}
//拼接div
function  createDiv(a){
	var  data=a.list;
	listRoom = [];
  $.messager.progress('close');
//  var box = $('#box').html();
  var s = '';
  for (var i = 0; i < data.length; i ++) {
	  var status="空闲";
	  if(data[i][0].roomStatus==0){
		  status = "<font  size=5px>"+data[i][0].name + '-空闲中</font>';
		  s+='<div class="jzw-box none" onclick="look('+data[i][0].id+')" >';
		  s+='<div class="jzw-bg">';
		  s+='<div class="jzw-title">'+status+'</div>';
		  s+='<img src="./img/bg.png" alt=""></div></div>';
      }else if (data[i][0].roomStatus==1){
    	  status = "<font color='orange' size=5px>"+data[i][0].name + '-已预约</font>';
    	  s+='<div class="jzw-box none" onclick="look('+data[i][0].id+')" >';
		  s+='<div class="jzw-text" >';
		  s+='<div class="jzw-title" >'+status+'</div>';
		  s+='<div style="text-align:center;font-size:18px;line-height:23px;ertical-align:middle">'
		  s+='嫌疑人姓名：'+data[i][0].personName+"</br></br>";
		  s+='证件号码：'+data[i][0].certificateNo+"</br></br>";
		  s+='<font color="red">已预约</font>';
          s+='<button onclick="empty('+data[i][0].areaId+','+data[i][0].id+')" style="margin-left: 30px;background:#2f435e;color:#f2f2f2;padding: 10px 30px 10px 30px;font-size: 16px">置空</button>';
		  s+='</div></div></div>';
      }else if (data[i][0].roomStatus==2){
    	  status = "<font color='red' size=5px>"+data[i][0].name + '-已占用</font>';
    	  s+='<div class="jzw-box none" onclick="look('+data[i][0].id+')" >';
		  s+='<div class="jzw-text" >';
		  s+='<div class="jzw-title" >'+status+'</div>';
		  s+='<div style="text-align:center;font-size:18px;line-height:23px;ertical-align:middle">'
		  s+='嫌疑人姓名：'+data[i][0].personName+"</br></br>";
		  s+='证件号码：'+data[i][0].certificateNo+"</br></br>";
		  s+='<font color="red">正在审讯中</font>';
          s+='<button onclick="empty('+data[i][0].areaId+','+data[i][0].id+')" style="margin-left: 30px;background:#2f435e;color:#f2f2f2;padding: 10px 30px 10px 30px;font-size: 16px">置空</button>';
		  s+='</div></div></div>';
      }
	  
	  
//	  <div class="jzw-box {display}" onclick="look('{index}')" >
//		<div class="jzw-bg">
//			<div class="jzw-title">{name}(空闲)</div>
//			<img src="./img/bg.png" alt="">
//		</div>
//		<div class="jzw-left">
//			<div class="jzw-title">{name}</div>
//			<div class="jzw-img">
//				<img src="{url}" onerror="imgNotFind()" alt="">
//				<div class="jzw-btn-group">
//					<span class="jzw-btn" onclick="doClear('{id}','{serialId}')">置空</span>
//				</div>
//			</div>
//		</div>
  }
//  $.each(data,function (index,obj) {
//      dealObj(obj,index);
//      s += 
//  });
  roomData = data;
  $('.context').html( s);
  $('#leisure-room').combobox({
      data:listRoom,
      valueField:'id',
      textField:'nameValue'
  });
  $('#right').html('');
}


// 置空讯询问室
function empty(areaId,roomId) {
    var params = {"areaId":areaId,"roomId":roomId};
    var jsonrtinfo = JSON.stringify(params);
    console.log(jsonrtinfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'updateRoomInfo.do',
        data: jsonrtinfo,
        dataType: 'json',
        success: function (data) {
            doSearch();
            $.messager.show({
                title:data.title,
                msg:data.content,
                timeout:3000
            });
        },
        error: function (data) {
            $.messager.alert('错误', '置空失败!');
        }
    })
}

function dealObj(obj,index) {
    obj['index'] = index;
    if(obj.serialUUID == null){
        obj['display'] = 'none';
        if(obj.roomStatus==0){
        	obj['nameValue'] = obj.name + '-空闲';
        }else if (obj.roomStatus==1){
        	obj['nameValue'] = obj.name + '-已预约';
        }else if (obj.roomStatus==2){
        	obj['nameValue'] = obj.name + '-已占用';
        }
        listRoom.push(obj);
        obj.url = 'http:/url/pic.58pic.com/58pic/15/68/59/71X58PICNjx_1024.jpg';
    }
    else{
        obj['display'] = 'block';
        obj.url = fileUtils.url('ba','RQ',obj.serialUUID,obj.areaId,obj.inPhotoName);
        console.log(obj.url)
    }
    obj.outTime = formatter(obj.outTime,'yyyy-MM-dd hh:mm:ss');
    obj.endTime = formatter(obj.endTime,'yyyy-MM-dd hh:mm:ss');
    obj.caseType = ['刑事','行政','警情'][obj.caseType];
    obj.sex = {0:'未知性别',1:'男',2:'女',9:'未说明性别'}[obj.sex];
}

function look(index) {
    var info = $('#info').html();
    $('#right').html(info.format(roomData[index]));
}

function saveEnterprise(_this) {
    if(!checkForm('#arraign-form')){
        return;
    }
    if($("#policeIdOut").val() == null || $("#policeIdOut").val() == ''){
        $.messager.alert('提示',"请输入准确且已入区的民警警号");
        return;
    }
    var g = $('#save-combgrid').combogrid('grid');	// get datagrid object
    var row = g.datagrid('getSelected');
    $('#room-name').val(row.roomName);
    $('#room-id').val(row.roomId);
    $('#serialId').val(row.serialId);
    $('#recordId').val(row.recordId);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    console.log("---"+$('#arraign-form').serializeObject());
    jQuery.ajax({
        type: 'POST',
        url: 'arraign.do',
        data: $('#arraign-form').serializeObject(),
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            if(!data.error){
                doSearch();
                $('#certificate-dialog').dialog('close');
            }


        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function doArraign() {
    $('#save-combgrid').combogrid({
        panelWidth:360,
        // value:'006',
        idField:'personId',
        textField:'name',
        url:'../combobox/getAllDetainSerialNo.do?areaId=' + $('#query-combobox').combobox('getValue'),
        columns:[[
            {field: 'serialNo', title: '入区编号', width: 135},
            {field: 'name', title: '姓名', width: 60},
            {field: 'certificateNo', title: '证件号码', width: 150},
            {field: 'recordId', title: '候问记录ID', hidden: true},
            {field: 'serialId', title: '入区ID', hidden:true},
            {field: 'personId', title: '人员ID', hidden: true}
        ]],
        onSelect:function (newVal,oldVal) {
            var g = $('#save-combgrid').combogrid('grid');	// get datagrid object
            var row = g.datagrid('getSelected');
            $('#room-name').val(row.roomName);
            $('#room-id').val(row.roomId);
            $('#serialId').val(row.serialId);
            $('#recordId').val(row.recordId);
        }
    });
    showDialog('#certificate-dialog', "提讯嫌疑人");
    $('#arraign-form').form('clear');
}

function doClear(id,serialId) {
    debugger;
    $.messager.confirm('删除确认', '是否确定删除此数据？', function (r){
        if(r){
            messagershow();
            jQuery.ajax({
                type: 'POST',
                url: 'clear.do',
                data: {roomId: id,serialId:serialId,tresh:new Date().getTime()},
                dataType: 'json',
                success: function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.content,
                        timeout: 3000
                    });
                    doSearch();
                    $.messager.progress('close');
                },
                error: function (data) {
                    //exception in java
                    $.messager.progress('close');
                    $.messager.alert('错误', '未知错误!');
                }
            });
        }
    });
}

// 验证警察编号是否存在searchInUser1
function finduser() {
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
        url : 'checkPolceNo.do',
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


function policeCard(_this,event) {
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

function personCard(_this,event){
    _this = $(_this);
    if(event.keyCode == 13){
        var data = checkRingNo(_this.val(),0);
        if(!data.isError && data.callbackData.status == 1
            && data.callbackData.cuffNo != null && data.callbackData.cuffNo != ""){
            $('#save-combgrid').combogrid('setValue', data.callbackData.personId);
            var g = $('#save-combgrid').combogrid('grid');	// get datagrid object
            var row = g.datagrid('getSelected');
            $('#room-name').val(row.roomName);
            $('#room-id').val(row.roomId);
            $('#serialId').val(row.serialId);
            $('#save-combgrid').combogrid('setValue', row);
        }else{
            $.messager.alert(data.title, data.content);
        }
    }
}