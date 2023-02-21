// 民警出区
function out(){
	$("#outPopup").show();
	$('#exit_cuffNo').focus();
}

$(function(){
	bindClickOut();
});

   
// 手动选择
//function cheackOut(obj, selectId){
//    $(selectId).combo("readonly", !obj.checked);
//}

// 检验警号是否存在
function finduserOut(index) {
    var userNo ;
    if(index==0){
        userNo= $('#policeNoOut').val();
    }else if(index==1){
        userNo= $('#e_policeNo').val();
    }else if(index==2){
        userNo= $('#ep_policeNo').val();
    }else if(index==3){
        userNo= $('#add_policeNo').val();
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
                   $("#policeIdOut").val(data.id); 
                }else if(index==1){
                    $('#e_policeId').val(data.id);
                }else if(index==2){
                    $('#ep_policeId').val(data.id);
                }else if(index==3){
                    $('#add_policeId').val(data.id);                    
                }
              }else{
                $.messager.alert('错误', userNo + '该警号不存在!');
                if(index==0){
                   $('#policeNoOut').val('');                   
                }else if(index==1){
                    $('#e_policeNo').val('');
                }else if(index==2){
                    $('#ep_policeNo').val('');
                }else if(index==3){
                    $('#add_policeNo').val('');
                } 
            }
        },
        error : function(data) {
            $.messager.alert('错误', '警号不存在！');
               if(index==0){
                   $('#policeNoOut').val('');
                }else if(index==1){
                    $('#e_policeNo').val('');
                }else if(index==2){
                    $('#ep_policeNo').val('');
                }else if(index==3){
                    $('#add_policeNo').val('');
                }
        }
    });
}

// 民警出区保存
function save(){
    var cuffId = $('#exit_cuffId').val();
    var cuffNo = $('#exit_cuffNo').val();
    var policeId = $('#policeIdOut').val();

    if(cuffId == null || cuffId == ''){
        $.messager.alert("提示","请刷民警卡片");
        return;
    }
    if(cuffNo == null || cuffNo == ''){
        $.messager.alert("提示","请刷民警卡片");
        return;
    }
    if(policeId == null || policeId == ''){
        $.messager.alert("提示","请输入民警卡片");
        return;
    }

    $.messager.progress({
        title:'请等待',
        msg:'添加/修改数据中...'
    });

    jQuery.ajax({
        type : 'POST',
        async : true,
        dataType : 'json',
        url : 'querySerialBySendUserId.do',
        data : {'policeId':policeId},
        success : function (data) {
            if(data.length > 0 ){
                $.messager.confirm("提示", "该案件还有嫌疑人在区,确认出区吗？", function (data) {
                    if(data) {
                        policeOut(cuffId,cuffNo,policeId);
                    }else {
                        $.messager.progress('close');
                    }

                });

            } else {
                policeOut(cuffId,cuffNo,policeId);
            }

        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '系统异常!');
        }
    });
}


function policeOut(cuffId,cuffNo,policeId) {
    // 更新民警出区时间
    jQuery.ajax({
        type : 'POST',
        async : true,
        dataType : 'json',
        url : 'updateOutTime.do',
        data : {'cuffId':cuffId,'policeId':policeId,'cuffNo' : cuffNo},
        success : function(data){
            if(data.code == 0){
                $.messager.progress('close');
                //$.messager.alert(data.title, data.content);
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
                $('#datagrid').datagrid('reload');
            }else{
                $.messager.progress('close');
                //$.messager.alert(data.title, data.content);
                $.messager.show({
                    title:data.title,
                    msg:data.content,
                    timeout:3000
                });
                closeMpopupOut();
                //initPoliceData();
                $('#datagrid').datagrid('reload');
            }
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert('提示', '出区失败!');
        }
    });

}

function closeMpopupOut(){
	outClear();
    $("#outPopup").hide();
}

function outClear(){
	$("#exit_cuffNo").val("");
    $("#exit_cuffId").val("");
    $("#policeNoOut").val("");
    $("#policeIdOut").val("");
}

function bindClickOut(){
	$('#exit_cuffNo').keydown(function(event){ 
		if(event.keyCode == 13){
			var data = checkRingNo($("#exit_cuffNo").val(),1);
			if(!(data.isError)){
				if(data.callbackData.cuffNo != null&&data.callbackData.cuffNo != ""){
					if(data.callbackData.status == 1){
						$("#exit_cuffNo").val(data.callbackData.cuffNo);
	                    $("#exit_cuffId").val(data.callbackData.id);
	                    var userId = data.callbackData.userId;
					    // 获取民警警号
						jQuery.ajax({
					            type : 'POST',
					            async : false,
					            dataType : 'json',
					            url : 'findPoliceNoById.do',
					            data : {'userId':userId},
					            success : function(returnData){                  
              						$('#policeNoOut').val(returnData.callbackData.certificateNo);
              						$('#policeIdOut').val(returnData.callbackData.id);
					            },
					            error : function(data){
					                $.messager.progress('close');
					                $.messager.alert('提示', '获取民警警号失败!');
					            }
					    });							                    
					}else{
						$.messager.alert(data.title, data.content);
						$("#exit_cuffNo").val("");
                        $("#exit_cuffId").val("");
					}					
				}	             
            }else{               
                $.messager.alert(data.title, data.content);
                $("#exit_cuffNo").val("");
                $("#exit_cuffId").val("");
            }			
		}
	});
}