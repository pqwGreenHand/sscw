var photoName = "";
//type:0：主页点击、1：历史页面点击
var clickType;
//保存图片base64位数据
var photoBase64Src = "";
//嫌疑人出入区在区界面嫌疑人照片
function photoEdit(serialId) {
	clickType = 1;
	phototype = 'photoEdit';
	var row = currenMap[serialId];
	if(row != null){
		$("#editphotoDialog").show();
		$("#editphotoDiv1").show();
		photoName = row.inPhotoId;
		var e = fileUtils.url("ba","RQ",row.uuid,row.areaId,row.inPhotoId);
		$("#oldphoto").attr("src",e);
		$("#editphotoSerialId").val(row.id);
		$("#newPhoto").attr("src","");
		//启动预览
		 GWQ_StartPreview(1);
        //GWQ_StartPreviewShow(0);
	}
}
function closeEditDialog(){
	if(preViewBase64 != null && preViewBase64 != ''){
		closePreview(1);
	}
	$('#editphotoDialog').hide();
}
//嫌疑人出入区主页查看查看嫌疑人照片
function photoEdit2(index) {
	clickType = 2;
	phototype = 'photoEdit';
	var row = $('#datagrid').datagrid('getRows')[index];
	if(row != null){
		$("#editphotoDialog").show();
		$("#editphotoDiv1").show();
		photoName = row.inPhotoId;
		var e = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,row.uuid,row.areaId,row.inPhotoId);
		$("#oldphoto").attr("src",e);
		$("#editphotoSerialId").val(row.id);
		$("#newPhoto").attr("src","");
		//启动预览
		 GWQ_StartPreview(1);
        //GWQ_StartPreviewShow(0)
	}
}
//抓拍图片
function getPhoto(){
	var base64 = getPreview();
	if(base64 != null) {
		photoBase64Src = base64;
		$("#newPhoto").attr("src","data:image/jpeg;base64,"+photoBase64Src)
	} else{
		$.messager.alert("提示","预览数据获取失败，请检查设备是否连接正常");
	}
}
//提交修改的照片
function uploadPhoto(){
	if(photoBase64Src != '' && photoBase64Src != null){
		$.messager .confirm( '提醒','是否确定修改并上传此照片？',
			function (r) {
				$.messager.progress({
					title: '请等待',
					msg: '图片上传中...'
				});
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
							closePreview(1);
							if(clickType == 2){
								$('#datagrid').datagrid('load', {
									areaId:$("#interrogatearea").combobox("getValue"),
									name: $('#e_person').val(),
									certificateNo: $('#e_certificateNo').val(),
									start_date: $('#e_start_date').datebox('getValue'),
									end_date: $('#e_end_date').datebox('getValue'),
									trefresh: new Date().getTime()
								});
							} else if(clickType === 1) {
								initSerialData();
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

//设备提交图片后的回调函数
function changeP(ErrorCode,imgBase64){
	if(ErrorCode==0)
	{
		$.messager.progress('close');
		photoBase64Src = imgBase64;
		$("#newPhoto").attr("src",'data:image/png;base64,'+imgBase64);
	}
	else if(ErrorCode==-9)
	{
		$.messager.progress('close');
		$.messager.alert('取消','设备已取消');
	}
	else
	{
		$.messager.progress('close');
		$.messager.alert('失败','返回错误码为'+ErrorCode);
	}
}