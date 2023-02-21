


//开启webSocket
function StartWebSocket(){
	 var url = "ws://localhost:9000/";

	if('WebSocket' in window){
            ws = new WebSocket(url);
    }
    else if('MozWebSocket' in window){
        ws = new MozWebSocket(url);
    }else{
		alert("浏览器版本过低，请升级您的浏览器。\r\n浏览器要求：IE10+/Chrome14+/FireFox7+/Opera11+");
	}
   
	ws.onopen = function()
   {
      m_isConnectWS = true;
	  unload();
	  sendInitMsg();//初始化
	  sendGetPath(); //获取电脑中的路径
	  m_closed = false;
   };
	
	
   ws.onmessage = function (evt) 
   { 
   	
   	
   	if(typeof(evt.data)=="string"){
   		
   		var str = evt.data;
   		
   		if(str.length <= 0){
   			
   			return;
   		}
		
		if(str.indexOf("FileEncodeBase64") >=0){
			
			//var strs= new Array(); 
			//strs=str.split(m_splitTag);
			//var baseStr = strs[2];
			//if(SingleCamera.encodeBase64Tag == 1){

            //    SingleCamera.ShowRealPic(baseStr,true);
            //    var jsonObj = {FuncName:'camDeleteFile',argument:{FilePath:SingleCamera.realPicPath}};
            //    sendWsMessage(jsonObj);

			//}else if(SingleCamera.encodeBase64Tag == 0){

           //     SingleCamera.ShowComparePic(baseStr,true);
           //     SingleCamera.compareBase64 = baseStr;

			//}	
			return;
		}
		
		if(str.indexOf(m_splitTag)>=0){
			//视频的每一帧
			var strs= new Array();
			strs=str.split(m_splitTag); 
			$('#base64').val(strs[1]);
			setImageWithBase64(strs[1]);
		}else{
			//处理其他请求
			console.log(str);
			handleJsonStrMessage(str);
		}
		
		
   		
   	}
  
 	};
	
   ws.onclose = function()
   { 
      m_isConnectWS = false;
	var myimg = document.getElementById("video");
	myimg.src = "images/load1.gif";
		StartWebSocket();
   };
	
}

function sendWsMessage(jsonObj){
	var jsonStr = JSON.stringify(jsonObj);
	ws.send(jsonStr);
}

function handleJsonStrMessage(str){
	
	
	var jsonOBJ = JSON.parse(str);
	var name = jsonOBJ.FuncName;
	var re = jsonOBJ.result;
	//初始化
	if( name == "camInitCameraLib"){

		openDev();


		//var jsonObj = {FuncName:'camInitFace',argument:{}};
		//sendWsMessage(jsonObj);

		//获取设备名
		var jsonObj = {FuncName:'camGetDevName'};
		sendWsMessage(jsonObj);
		
		//获得麦克风
		jsonObj = {FuncName:'camGetAudioDevName'};
		sendWsMessage(jsonObj);
		
		//获得录像格式
		jsonObj = {FuncName:'camGetVideoEncodeName'};
		sendWsMessage(jsonObj);
			
	}
	//打开设备
	else if(name == "camOpenDev"){
		
		if(re == 0){

			//获取分辨率
			var jsonObj = {FuncName:'camGetResolution'};
			sendWsMessage(jsonObj);
			//获取视频格式
			var jsonObj = {FuncName:'camGetMediaTypeName'};
			sendWsMessage(jsonObj);
			 jsonObj = {FuncName:'camSetImageAutoCrop',argument:{CropType:0}};
			sendWsMessage(jsonObj);

			 jsonObj = {FuncName:'camSetLivingBodyState',argument:{bOpen:1}};
			sendWsMessage(jsonObj);

		}else{
			alert("打开失败" + re);
		}
		
	}
	//获取设备名
	else if(name == "camGetDevName"){

		configureDevInfo(re);
		
	}
	//视频格式
	else if(name == "camGetMediaTypeName")
	{
		configureVideoFormate(re);
		//configureVideoStyle(re);
	}
	
	//获取分辨率
	else if(name == "camGetResolution"){
		
		configureRestionInfo(re);
	}
	//设置分辨率
	else if(name == "camSetResolution"){
		
		if(re !=0){
			
			alert("设置分辨率失败");
		}
	}
	//拍照
	else if(name == "camCaptureImageFile"){
		
		if(re != 0){
			
			alert("拍照失败");
		}
		else
		{
			retCapture = re;
               
		}
		
		
	}
	//自动裁切
	else if(name == "camSetImageAutoCrop"){
		if(re != 0){
			
			alert("自动裁切失败");
		}
	}
	else if(name == "camZooIn"){
		if(re == 0){
			
			alert("视频放大成功");
		}
	}
	//Base64
	else if(name == "CaptureEncodeBase64"){		
			alert(re);
	}
	//条码识别
	else if(name == "coderRecognizeBarcode"){		
			alert(re);
	}
	//连拍
	else if(name == "camStartAutoCapture"){

		if(re == "0"){
			alert("连拍开启成功");
		}

	}
	//停止连拍
	else if(name == "camStopAutoCapture"){

		if(re == "0"){
			alert("停止连拍成功");
			AutoCaptureBack("-1000");
		}

	}
	//连拍回调
	else if(name == "AutoCaptureBack"){
		
		//if(re == "0"){
			
			AutoCaptureBack(re);
		//}else {
		//	AutoCaptureBack("-1000");
			//alert("连拍回调出错"+String(re));
		//}
		
	}
	//选择麦克风
	else if(name == "camGetAudioDevName"){
		MicrophoneName(re);
		//console.log(re);
	}
	//录像格式
	else if (name == "camGetVideoEncodeName"){	
		//console.log(re);
		VideoName(re);
	}
	//开始录像
	else if(name == "camStartRecord"){
		if(re == 0) {
			alert("开始录像");
			GetVoice();
		}
		else {
			alert("录像失败");
		}
	}
	//关闭录像
	else if(name == "camStopRecord"){
		if(re == 0) alert("录制成功");
		else alert("录制失败");
	}
	else if (name == "camGetMicrophoneVolumeLevel"){
		ShowVioce(re);
	}
}

	
	

