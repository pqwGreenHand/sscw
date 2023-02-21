var isPhoto = 0;
$(function(){
	initCaseInfo();
    initCertificateType();
    loadArea();
});

// 其他人入区
function entrance() {
	load();
    $("#otherPersonForm").form("clear");
	$("#inPopup").removeClass("out");
	$("#inPopup").removeClass("exit");
	$("#inPopup").addClass("in");
	$("#hd").css("display","block");//显示div	
    $("#inPopup").show();      
}

// 初始化证件类型
function initCertificateType(){
	$('#certificateTypeId1').combobox({
        url:'../combobox/certificateTypes.do',
        valueField:'id',
        textField:'value',
        onLoadSuccess: function(data){
            $('#certificateTypeId1').combobox('setValue', data[0].id);
        }
    });
}

// 保存基本信息
function savePersonInfo(){
    if (!checkForm('#otherPersonForm')) {
        return;
    }
    var caseId = $('#caseId').combogrid("getValue");
    $.messager.progress({
        title:'请等待',
        msg:'保存数据中...'
    });
    var otherPersonForm = $('#otherPersonForm').serializeObject();
    otherPersonForm["sex"] = $("#sex1").combobox('getValue');
    otherPersonForm["type"] = $('#type1').combobox('getValue');
    otherPersonForm["birth"] = $('#birth').val();
    otherPersonForm["nation"] = $('#nation').val();
    otherPersonForm["country"] = $('#country').val();
    otherPersonForm["censusRegister"] = $('#censusRegister').val();
    otherPersonForm["caseId"] = caseId;
    otherPersonForm["isPhoto"] = 0;
    otherPersonForm["areaId"] = $('#s_areaId').combobox("getValue");
    var jsonrtinfo = JSON.stringify(otherPersonForm);
	jQuery.ajax({
        type : 'POST',
        contentType: 'application/json',
        async : false,
        dataType : 'json',
        url : contextPath + '/zhfz/bacs/serial/otherInsert.do',
        data : jsonrtinfo,
        success : function(data){        	                                      
			$.messager.progress('close');
            $.messager.show({
				title:data.title,
				msg:data.content,
				timeout:3000
			});
            currenPage = 1;
            initOtherPersonData();
			closeMpopup();	                
        },
        error : function(data){
            $.messager.progress('close');
            $.messager.alert(data.title, data.content);
        }
    });
}

// 拍照(下一步)
var idErrorMessage = "";
function saveStepDiv1Data(next){
	if (!checkForm('#otherPersonForm')) {
        return;
    }
    var certificateTypeId = $('#certificateTypeId1').combobox('getValue');
	var person_certificate_no=$("#certificateNo1").val();
    if(certificateTypeId==111){
		idErrorMessage = checkId(person_certificate_no);
	}
	if(idErrorMessage==null || idErrorMessage==""||idErrorMessage==true){		
		showNoHideStepDiv("stepDiv1", "step1", 0);
    	showNoHideStepDiv("stepDiv2", "step2", 1);
	}else{
		$.messager.alert('提示', idErrorMessage);
	}    
}


function photoScan() {
    isPhoto = 1;
    _register();
    webcam.capture();
    changeFilter();
    void (0);
}

// 完成
function addPhoto() {
    var canvas = document.getElementById("canvas");
    var str = canvas.toDataURL("image/png");
    var len = str.length;
    if (isPhoto == 1 && len > 5000){
        if (confirm('此处“下一步”将进行数据插入，不能进行修改，是否确定提交数据？')) {         
            var caseId = $('#caseId').combogrid("getValue");
		    $.messager.progress({
		        title:'请等待',
		        msg:'保存数据中...'
		    });
		    var otherPersonForm = $('#otherPersonForm').serializeObject();
		    otherPersonForm["sex"] = $("#sex1").combobox('getValue');
		    otherPersonForm["type"] = $('#type1').combobox('getValue');
		    otherPersonForm["birth"] = $('#birth').val();
		    otherPersonForm["nation"] = $('#nation').val();
		    otherPersonForm["country"] = $('#country').val();
		    otherPersonForm["censusRegister"] = $('#censusRegister').val();
		    otherPersonForm["caseId"] = caseId;
            otherPersonForm["isPhoto"] = 1;
		    var jsonrtinfo = JSON.stringify(otherPersonForm);
			jQuery.ajax({
		        type : 'POST',
		        contentType: 'application/json',
		        async : false,
		        dataType : 'json',
		        url : contextPath + '/zhfz/bacs/serial/otherInsert.do',
		        data : jsonrtinfo,
		        success : function(data){        	                                      
                    $.messager.progress('close');
                    $.messager.progress({
                        title: '请等待',
                        msg: '入区已完成，正在上传照片，请稍等...'
                    });
					var _blob = dataURLtoBlob(str);
			        var formData = new FormData();			
			        formData.append("file",_blob,'a.png');
			        formData.append("serialId",data.callbackData.id);
                    jQuery.ajax({
                                type: 'POST',
                                url: contextPath + '/zhfz/bacs/serial/ingetpicture.do',
                                data: formData,
                                contentType: false, // 注意这里应设为false
					            processData: false,
					            cache: false,
                                dataType: 'json',
                                success: function (data) {                                   
                                    $.messager.progress('close');
                                    isPhoto = 0;                                                                     
                                    $.messager.show({
                                        title:data.title,
                                        msg:data.content,
                                        timeout:3000
                                    });                                      
                                    currenPage = 1;
                                    initOtherPersonData();
                                    closeMpopup();                                                                  
                                },
                                error: function (data) {
                                    $.messager.progress('close');
                                    $.messager.alert(data.title, data.content);
                                }
                            });		                
		        },
		        error : function(data){
		            $.messager.progress('close');
		            $.messager.alert(data.title, data.content);
		        }
		    });                    
        }else {
            $.messager.alert('警告', '请先点击“允许”按钮，然后进行拍照!');
        }
    }else{
        // 未拍照，直接点击完成
        savePersonInfo();
    }   
}

function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], { type: mime });
}

function backStepDiv1(){
    showNoHideStepDiv("stepDiv2", "step2", 0);
    showNoHideStepDiv("stepDiv1", "step1", 1);
}

//需要隐藏的步骤id；显示还是隐藏 type:0隐藏 1显示
function showNoHideStepDiv(stepDivId,stepId,type){
    var tempStep = $("#"+stepId);
    var tempStepDiv = $("#"+stepDivId);
    if(type==1){
        tempStepDiv.show();
        tempStep.addClass("now");
    }else{
        tempStepDiv.hide();
        tempStep.removeClass("now");
    }
}

function closeMpopup(){
    $('#serialId').val('');
    $('#serialNo').val('');
    $('#s_inPhotoId').val('');
	showNoHideStepDiv("stepDiv1", "step1", 1);
    showNoHideStepDiv("stepDiv2", "step2", 0);
    $("#inPopup").hide();
    doAllClear();    
}

function doAllClear(){
	// 清空其他人信息
    $('#name1').val('');                                  
    $("#caseId").combogrid('setText','');
    //$('#sex1').combobox('setValue','');
    //$('#type1').combobox('setValue','');
    //$('#certificateTypeId1').combobox('setValue','');
    $('#certificateNo1').val('');
    $("#otherPersonId").combogrid('setText','');
    $("#inphoto").removeAttr("src");
}

function load(){
	jQuery("body").append("<div id=\"flash\"></div>");

        var canvas = document.getElementById("canvas");

        if (canvas.getContext) {
            ctx = document.getElementById("canvas").getContext("2d");
            ctx.clearRect(0, 0, 240, 180);

            var img = new Image();
            img.src = "image/logo.gif";
            img.onload = function() {
                ctx.drawImage(img, 129, 89);
            }
            image = ctx.getImageData(0, 0, 240, 180);
        }

        var pageSize = getPageSize();
        jQuery("#flash").css({
            height : pageSize[1] + "px"
        });
}

//加载办案场所
function loadArea(){
    $('#s_areaId').combobox({
        url: contextPath + '/zhfz/bacs/order/comboArea.do',
        valueField: 'id',
        textField: 'name',
        onLoadSuccess: function (data) {
            if(data != null && data.length > 0){
                $('#s_areaId').combobox('setValue',data[0].id);
            }
        }
    });
}