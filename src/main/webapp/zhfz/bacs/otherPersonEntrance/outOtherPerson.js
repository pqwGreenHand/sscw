$(function(){
	initOtherPersonInfo();
});

// 其他人出区
function out(){
	load(); 
	initOtherPersonInfo();
	$("#inPopup").removeClass("in");
	$("#inPopup").removeClass("exit");
	$("#inPopup").addClass("out");
	$("#hd").css("display","none");//隐藏div
	showNoHideStepDiv("stepDiv1", "step1", 0);
    showNoHideStepDiv("stepDiv2", "step2", 1);
	$("#inPopup").show(); 	
}

// 初始化其他人信息
function initOtherPersonInfo(){    
    $('#otherPersonId').combogrid({    
        panelWidth:490,    
        mode: 'remote',    
        url: contextPath + '/zhfz/bacs/serial/otherPersonList2.do',
        idField: 'id',    
        textField: 'personName',
        frozenColumns : [ [ {
				title : '嫌疑人id',
				field : 'id',
				width : 80,
				sortable : true,
				hidden : true
			} ] ],    
        columns: [[
        	{field:'serialNo',title:'入区编号',width:180},
            {field:'personName',title:'姓名',width:80},
            {field:'sex',title:'性别',width:50,
        		formatter:function(value,rec){
                if(value==1){
                    return "男";
                }
                if(value==2){
                    return "女";
                }
                return "";
            }
            },
            {field:'certificateNo',title:'证件号码',width:180},
        ]] ,
        onLoadSuccess: function(data){
            //$('#caseId').combogrid('setValue',-1);
        } ,
        pagination : true,
		pageSize:50,
		pageList : [50, 100],
		rownumbers : true,
        onChange:function(newValue, oldValue){
            var cg = $('#otherPersonId').combogrid('grid');   // 获取数据表格对象
            var row = cg.datagrid('getSelected');   // 获取选择的行
            $('#serialId').val(row.id);
            $('#serialNo').val(row.serialNo);
            $('#s_inPhotoId').val(row.inPhotoId);
            $("#inphoto").prop("src",fileUtils.url("ba","otherRQ",row.uuid,row.areaId,row.inPhotoId));
        }
       });
}

function closeOutMpopup(){	
    $("#outPopup").hide();
    $('#serialId').val('');
    $('#serialNo').val('');
    $('#s_inPhotoId').val('');
}

// 出区完成
function addOutPhoto() {
	var serialId = $('#serialId').val();
	var serialNo = $('#serialNo').val();
    var inPhotoId = $('#s_inPhotoId').val();
    //debugger;
	if(serialNo == '' || serialNo == null){
        $.messager.alert("提示", "请选择其他人信息！");
        return;
	}
    var canvas = document.getElementById("canvas");
    var str = canvas.toDataURL("image/png");
    var len = str.length;
    if(inPhotoId != '' && inPhotoId != 'null') {
        if (isPhoto == 1 && len > 5000) {
            if (confirm('是否确定入区和出区为同一个人？')) {
                var _blob = dataURLtoBlob(str);
                var formData = new FormData();
                formData.append("file", _blob, 'b.png');
                formData.append("serialId", serialId);
                formData.append("serialNo", serialNo);
                jQuery.ajax({
                    type: 'POST',
                    url: contextPath + '/zhfz/bacs/serial/outgetpicture.do',
                    data: formData,
                    contentType: false, // 注意这里应设为false
                    processData: false,
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        $.messager.progress('close');
                        isPhoto = 0;
                        $.messager.show({
                            title: data.title,
                            msg: data.content,
                            timeout: 3000
                        });
                        currenPage = 1;
                        initOtherPersonData();
                        initOtherPersonInfo();
                        closeMpopup();
                        $('#serialId').val('');
                        $('#serialNo').val('');
                        $('#s_inPhotoId').val('');
                    },
                    error: function (data) {
                        $.messager.progress('close');
                        $.messager.alert(data.title, data.content);
                    }
                });
            } else {
                //$.messager.alert('警告', '请先点击“允许”按钮，再拍照!');
            }
        } else {
            $.messager.alert('警告', '请先点击“允许”按钮，然后进行拍照!');
        }
    }else {
        jQuery.ajax({
            type: 'POST',
            url: contextPath + '/zhfz/bacs/serial/otherPersonOut.do',
            data: {'serialId' : serialId},
            dataType: 'json',
            success: function (data) {
                $.messager.progress('close');
                isPhoto = 0;
                $.messager.show({
                    title: data.title,
                    msg: data.content,
                    timeout: 3000
                });
                currenPage = 1;
                initOtherPersonData();
                initOtherPersonInfo();
                closeMpopup();
                $('#serialId').val('');
                $('#serialNo').val('');
                $('#s_inPhotoId').val('');
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.alert(data.title, data.content);
            }
        });
	}
}