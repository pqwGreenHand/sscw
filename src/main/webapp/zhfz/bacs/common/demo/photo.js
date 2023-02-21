var dev1;//摄像头1
var dev2;//摄像头1
var video1;//照片框1
//var video2;//照片框2
var Name;
var inPictureName="";

$(function(){
	try{
//		//Unload();
		Load();
		document.getElementById('thumb1').Thumbnail_Clear(true);
	}catch(e){
		//alert("e:"+e)
		$.messager.alert('错误', '初始化高拍仪失败，请先关闭浏览器，重新进入页面!');
		return;
	}
});

function openDev(){
	
	if(m_closed){
		
		StartWebSocket();
	}

	//打开摄像头
	var jsonObj = {FuncName:'camOpenDev',argument:{devIndex:SingleCamera.devIndex, subtype:0,width:0,height:0}};
	sendWsMessage(jsonObj);
	//开始预览
	jsonObj = {FuncName:'camStartPreview'};
	sendWsMessage(jsonObj);
	
}

function plugin(){
	return document.getElementById('view1');
}

function view1(){
	return document.getElementById('view1');
}

function thumb1(){
	return document.getElementById('thumb1');
}

function addEvent(obj, name, func){
	if (obj.attachEvent) {
		obj.attachEvent("on"+name, func);
	} else {
		obj.addEventListener(name, func, false); 
	}
}

//初始化高拍仪
function Load(){
	
	// 添加事件
	addEvent(view1(), 'DevChange', function(type,idx,dbt){
		alert(type+"==="+idx+"==="+dbt);
		if (1 == type)
		{
			alert(type)
			alert(idx)
			alert(dbt)
			if (0 == idx)
			{
				alert(type)
				if (1 == dbt)
				{
					dev1 = plugin().Global_CreateDevice(1, 0);

					if (dev1 && plugin().Device_GetEloamType(dev1) ==1)
					{
						if (video1)
						{
							view1().View_SetText("", 0);
							plugin().Video_Release(video1);
							video1 = null;
						}
						video1 = plugin().Device_CreateVideo(dev1, 0, 1);
						if (video1)
						{
							view1().View_SelectVideo(video1);
							view1().View_SetText("打开视频中，请等待...", 0);
						}
					}
				}
				else if (2 == dbt)
				{
					if (video1)
					{
						view1().View_SetText("", 0);
						plugin().Video_Release(video1);
						video1 = null;
						plugin().Device_Release(dev1);
						dev1 = null;
					}
				}
			}
			else if (1 == idx)
			{
				if (1 == dbt)
				{
					dev2 = plugin().Global_CreateDevice(1, 1);
					if (dev2 &&  plugin().Device_GetEloamType(dev2) ==1)
					{
						if (video1)
						{
							view1().View_SetText("", 0);
							plugin().Video_Release(video1);
							video1 = null;
						}
						video1 = plugin().Device_CreateVideo(dev2, 0, 1);
						if (video1)
						{
							view1().View_SelectVideo(video1);
							view1().View_SetText("打开视频中，请等待...", 0);
						}
					}
				}
				else if (2 == dbt)
				{
					if (video1)
					{
						view1().View_SetText("", 0);
						plugin().Video_Release(video1);
						video1 = null;
						plugin().Device_Release(dev2);
						dev2 = null;
					}
				}
			}
		}
    });
	addEvent(view1(), 'IdCard', function(ret){
		if (1 == ret){
			var str = "";
			str += view1().Global_GetIdCardData(1);
			str += ";";
			str += view1().Global_GetIdCardData(2);
			str += ";";
			str += view1().Global_GetIdCardData(3);
			str += ";";		
			str += view1().Global_GetIdCardData(4);
			str += "年";		
			str += view1().Global_GetIdCardData(5);
			str += "月";		
			str += view1().Global_GetIdCardData(6);
			str += "日";
			str += ";";			
			str += view1().Global_GetIdCardData(7);
			str += ";";	 
			str += view1().Global_GetIdCardData(8);
			str += ";";	
			str += view1().Global_GetIdCardData(9);
			str += ";";	
			str += view1().Global_GetIdCardData(10);
			str += "年";		
			str += view1().Global_GetIdCardData(11);
			str += "月";		
			str += view1().Global_GetIdCardData(12);
			str += "日";
			str += "-";					
			str += view1().Global_GetIdCardData(13);
			str += "年";		
			str += view1().Global_GetIdCardData(14);
			str += "月";		
			str += view1().Global_GetIdCardData(15);
			str += "日";	 
			
		}
	});
	addEvent(view1(), 'MoveDetec', function(video, id){
		// 自动拍照事件	
	});
	
	view1().Global_SetWindowName("view");
	
	thumb1().Global_SetWindowName("thumb");
	
	view1().Global_InitDevs();	
	view1().Global_InitOcr();
	view1().Global_InitIdCard();
	view1().Global_InitBiokey();
	view1().Global_InitReader();
	view1().Global_InitMagneticCard();
	view1().Global_DiscernIdCard();
	view1().Global_ReaderStart();
	view1().Global_MagneticCardReaderStart();
}

//注销高拍仪
function Unload(){
	if (video1){
		view1().View_SetText("", 0);
		view1().Video_Release(video1);
		video1 = null;
	}
	if(dev1)
	{
		view1().Device_Release(dev1);
		dev1 = null;
	}
	if(dev2)
	{
		view1().Device_Release(dev2);
		dev2 = null;
	}
	
	view1().Global_DeinitIdCard();
	view1().Global_DeinitDevs();
}

//拍照
function inScan(){
	alert("11");
	document.getElementById('thumb1').Thumbnail_Clear(true);
	inIsPhoto=1;
	
	var imgList2 = view1().Video_CreateImageList(video1, 0, view1().View_GetObject());
	
	if (imgList2){
		var len = view1().ImageList_GetCount(imgList2);
		for (var i = 0; i < len; i++){
			var img = view1().ImageList_GetImage(imgList2, i);
			var date = new Date();
			var yy = date.getFullYear().toString();
			var mm = (date.getMonth() + 1).toString();
			var dd = date.getDate().toString();
			var hh = date.getHours().toString();
			var nn = date.getMinutes().toString();
			var ss = date.getSeconds().toString();
			var mi = date.getMilliseconds().toString();
			inPictureName = "D:\\" + yy + mm + dd + hh + nn + ss + mi + ".jpg";
			var b = view1().Image_Save(img, inPictureName, 0);
			if (b){
				view1().View_PlayCaptureEffect();
				thumb1().Thumbnail_Add(inPictureName);
			}
			view1().Image_Release(img);
		}
		view1().ImageList_Release(imgList2);
	}
}

//上传
function uploadphoto1(){
	//alert(window.location.protocol+"//"+window.location.host+contextPath);
	//alert(basePath);
	//alert(serialid+"--"+type+"--"+serialNo);
	try{
//	$.messager.progress({
//	   	 title:'请等待',
//	   	 msg:'上传中...'
//	});
	if(view1()){
		var http = null;
		var requestUrl = window.location.protocol+"//"+window.location.host+contextPath;
		if(1==type){
			http = view1().Global_CreateHttp(requestUrl+"/interrogate/belong/outgetpicture.do?serialid="+serialid);//upload.asp参照HttpUploadDemo
		}else if(3==type){
			http = view1().Global_CreateHttp(requestUrl+"/interrogate/entrance/serialWsPicture.do?serialid="+serialid);//upload.asp参照HttpUploadDemo
		}else{
			http = view1().Global_CreateHttp(requestUrl+"/interrogate/exhibit/outgetpicture.do?serialid="+serialid);//upload.asp参照HttpUploadDemo
		}
		if (http){
			var b = view1().Http_UploadImageFile(http, inPictureName, serialNo+".jpg");
			if (b)
			{
				//$.messager.progress('close');
				alert('上传成功!');
				//$('#photo_dialog').dialog('close');
				//Unload();
			}
			else
			{
				//$.messager.progress('close');
				alert( '上传失败!');
				//$('#photo_dialog').dialog('close');
				//Unload();
			}
			view1().Http_Release(http);
		}
		else
		{
			//$.messager.progress('close');
			alert( 'url 错误!');
		}
	}
	//$.messager.progress('close');
	//Unload();
  }catch(e){
	  alert(e);
  }
	window.close();
	window.opener.loadDatagrid();
	
}

