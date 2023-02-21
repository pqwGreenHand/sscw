var photoName = "";
//type:0：主页点击、1：历史页面点击
var clickType;
//保存图片base64位数据
var photoBase64Src = "";
//嫌疑人出入区主页查看嫌疑人照片
function photoEdit(serialId) {
	clickType = 1;
	initEditSwf();
	var row = currenMap[serialId];
	if(row != null){
		$("#editphotoDialog").show();
		$("#editphotoDiv1").show();
		photoName = row.inPhotoId;
		var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoId);
		$("#oldphoto").attr("src",e);
		$("#editphotoSerialId").val(row.id);
		$("#changePhotoTips").show();
		$("#newPhoto").hide();
		$("#newPhoto").attr("src","");
	}
}
//嫌疑人出入区历史界面查看嫌疑人照片
function photoEdit2(index) {
	clickType = 1;
	phototype = 'photoEdit';
	var row = $('#datagrid').datagrid('getRows')[index];
	if(row != null){
		$("#editphotoDialog").show();
		$("#editphotoDiv1").show();
		photoName = row.inPhotoId;
		var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoId);
		$("#oldphoto").attr("src",e);
		$("#editphotoSerialId").val(row.id);
		$("#changePhotoTips").show();
		$("#newPhoto").hide();
		$("#newPhoto").attr("src","");
	}
}
//提交修改的照片
function uploadPhoto(){
	if(!$("#newPhoto").is(":hidden") && photoBase64Src != '' && photoBase64Src != null){
		$.messager .confirm( '提醒','是否确定修改并上传此照片？',
			function (r) {
				var formData = new FormData();
				var blob = b64toBlob(photoBase64Src, 'image/png');
				formData.append("file", blob, 'a.png');
				formData.append("photoName", photoName);
				formData.append("serialId", $('#editphotoSerialId').val());
				jQuery.ajax({
					type: 'POST',
					url: "editpicture.do",
					data: formData,
					contentType: false, // 注意这里应设为false
					processData: false,
					cache: false,
					dataType: 'json',
					success: function (data) {
						$.messager.progress('close');
						if(data.isError){
							$.messager.alert(data.title, data.content+data.callbackData);
						}else{
							$.messager.alert(data.title, data.content);
							$("#editphotoDialog").hide();
							if(clickType == 0){
								initSerialData();
							}else if(clickType == 1){
								$('#datagrid').datagrid('load', {
									areaId:$("#interrogatearea").combobox("getValue"),
									name: $('#e_person').val(),
									certificateNo: $('#e_certificateNo').val(),
									status: $('#e_status').combobox('getValue'),
									start_date: $('#e_start_date').datebox('getValue'),
									end_date: $('#e_end_date').datebox('getValue'),
									trefresh: new Date().getTime()
								});
							}
						}
					},
					error : function(data){
						$.messager.progress('close');
						$.messager.alert('错误', '未知错误!');
					}
				});
			}
		)
	} else{
		$.messager.alert('提示', '请在页面上点击确认拍照，并在设备点击确认拍照，提交照片!');
	}
}
//初始化拍照按钮
function initEditSwf() {
	jQuery("body").append("<div id=\"flash2\"></div>");
	var canvas = document.getElementById("editCanvas");
	if (canvas.getContext) {
		ctx = document.getElementById("editCanvas").getContext("2d");
		ctx.clearRect(0, 0, 240, 180);
		var img = new Image();
		img.src = "image/logo.gif";
		img.onload = function() {
			ctx.drawImage(img, 129, 89);
		}
		image = ctx.getImageData(0, 0, 240, 180);
	}
	jQuery("#flash2").css({ height: "150px" });
}
function editPhotoScan(){
	_register();
	webcam.capture();
	changeFilter();
	void(0);
}