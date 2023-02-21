

function load(){
	//启动服务
	//window.open("CamServer://");
	StartWebSocket();
}

function unload(){
	
	if(m_closed){
		return;
	}
	//反初始化
	var jsonObj = {FuncName:'camUnInitCameraLib'};
	sendWsMessage(jsonObj);

}

//配置UI中的存储路径
function configureUIPath(path)
{
	document.getElementById("saveText").value = path;
	document.getElementById("Distory").value = path;
	//录像地址
	var filePath = path + "\\Video.AVI";
	document.getElementById("SaveVieoText").value = filePath;
	//读卡的头像地址
	filePath = path + "\\Head.bmp";
	document.getElementById("HeadPath").value = filePath;
	//图像合并的生成路径
	document.getElementById("pdftext").value = path;
	document.getElementById("UploadsaveText").value = path;
}

//获取电脑中的特殊路径setImageWithBase64
function sendGetPath()
{
	
	var jsonObj = {FuncName:'GetSpecialFolderPath',argument:{nFolder:5}};
	sendWsMessage(jsonObj);
}

//初始化
function sendInitMsg(){
	
	var jsonObj = {FuncName:'camInitCameraLib'};
	sendWsMessage(jsonObj);
}
//设置预览区域
function sendPreZoneSize(){
	
	var w = document.getElementById("myCanvas").width;
	var h = document.getElementById("myCanvas").height;
	
	var jsonObj = {FuncName:'PreZoneSize',argument:{width:w,height:h}};
	sendWsMessage(jsonObj);
	
}
//打开设备
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
var timer = 0;
function addlist(obj,path) 
{
	 $('#parentUl').html('');
　　var ul = document.getElementById(obj);
    
　//　var li = document.createElement("li");

　　var img = document.createElement("img");
　　img.setAttribute("width", "100%");
　　img.setAttribute("height", "100%");　　
  	img.setAttribute("id", "newImg");
　　img.setAttribute("alt", "图片不存在");
  	img.setAttribute("title", path);	
	  img.onclick = function(){showpicture(path)};
  　img.src = "file://" + path ;
	  
　　//li.appendChild(img);
　//　ul.appendChild(li);	
    ul.appendChild(img);	
	//不知道有没有用
	clearTimeout(timer); ;

}


function showpicture(path)
{
	var jsonObj = {FuncName:'camShowImage',argument:{FilePath:path}};
	sendWsMessage(jsonObj);
}
function DevSetting(){
	var objSelect = document.getElementById("device");	
	var jsonObj = {FuncName:'camShowImageSettingWindow',argument:{devIndex:objSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}
//关闭设备
function closeDev(){
	
	var objSelect = document.getElementById("device");
	var jsonObj = {FuncName:'camCloseDev',argument:{devIndex:objSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}

//显示设备名
function configureDevInfo(names){	

	//设备名字
	var objSelect = document.getElementById("devicList");
	objSelect.options.length = 0;
	
	for (var i = 0; i < names.length;i++ ) {
	
		var op = new Option(names[i],i);
		objSelect.options[objSelect.length] = op;
		
	}
	objSelect[SingleCamera.devIndex].selected = true;
	//设置设备
	objSelect.onchange = function(){
		
		//打开摄像头
		var jsonObj = {FuncName:'camOpenDev',argument:{devIndex:objSelect.selectedIndex, subtype:0,width:0,height:0}};
		sendWsMessage(jsonObj);
		
	}
	
}
var videoNum = 0;
var ResolutionNum = 0;
//显示视频格式
function configureVideoFormate(names)
{
	var objSelect = document.getElementById("videoStyle");
	objSelect.options.length = 0;
	
	for (var i = 0; i < names.length;i++ ) {
		if(names[i].length <=0){
			continue;
		}
		var op = new Option(names[i],i);
		objSelect.options[objSelect.length] = op;
	}
	//objSelect.Option.selected = ResolutionNum;
	objSelect[videoNum].selected = true;
	//设置分辨率
	objSelect.onchange = function(){
		
		var jsonObj = {FuncName:'camSetMediaType',argument:{index:objSelect.selectedIndex}};
		ResolutionNum = objSelect.selectedIndex;
		sendWsMessage(jsonObj);
	
	}
	
}


//设置 分辨率
function configureRestionInfo(names){
	
	var objSelect = document.getElementById("reslutionList");
	objSelect.options.length = 0;
	
	for (var i = 0; i < names.length;i++ ) {
		if(names[i].length <=0){
			continue;
		}
		var op = new Option(names[i],i);
		objSelect.options[objSelect.length] = op;
	}
	//objSelect.Option.selected = ResolutionNum;
	objSelect[ResolutionNum].selected = true;
	//设置分辨率
	objSelect.onchange = function(){
		
		var jsonObj = {FuncName:'camSetResolution',argument:{index:objSelect.selectedIndex}};
		ResolutionNum = objSelect.selectedIndex;
		sendWsMessage(jsonObj);
	
	}
}

function configureVideoStyle(names){
	
	var objSelect = document.getElementById("videoStyle");
	objSelect.options.length = 0;
	
	for (var i = 1; i < names.length;i++ ) {
	
		var op = new Option(names[i],i);
		objSelect.options[objSelect.length] = op;
		
	}

	//设置视频格式
	objSelect.onchange = function(){
		
		//sendWsMessage("SetMediaType"+m_splitTag+String(objSelect.selectedIndex));
	
	}
	
}

function ResizeImage(imageDest, W, H)
{
//显示框宽度W,高度H 
var image = new Image();
image.src = imageDest.src;
image.width = 2592;
image.height = 1944;
if(image.width>0 && image.height>0)
{
    //比较纵横比
    if(image.width/image.height >= W/H)//相对显示框：宽>高
    {
     if(image.width > W) //宽度大于显示框宽度W，应压缩高度
     {
               imageDest.width = W; 
               imageDest.height = (image.height*W)/image.width;   
              }
     else //宽度少于或等于显示框宽度W，图片完全显示
     {
               imageDest.width = image.width;       
               imageDest.height = image.height;   
              }
    }
    else//同理
    {
     if(image.height > H)
     {
               imageDest.height = H;
               imageDest.width = (image.width*H)/image.height;
              }
     else
     {
               imageDest.width = image.width;
               imageDest.height = image.height;
              }
    }
}
}
//
//显示每一帧
function setImageWithBase64(str){

	var myimg = document.getElementById("video");
	myimg.src = "data:image/png;base64,"+str;
	//ResizeImage(myimg, myimg.width, myimg.height);
}


//旋转
function SetRotationStyle(){
	var objSelect = document.getElementById("rotationStyle");
	var jsonObj = {FuncName:'camSetImageRotateMode',argument:{rotateMode:objSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}
//裁切
function SetCutStyle(){
	
	var objSelect = document.getElementById("cutStyle");
	
	var jsonObj = {FuncName:'camSetImageAutoCrop',argument:{CropType:objSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}
//图像处理
function setImageAdjust(){
	var objectSelect = document.getElementById("imageAdjust");
	var jsonObj = {FuncName:'camSetImageAdjust',argument:{Type:objectSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}
//颜色
function SetColorStyle(){
	var objectSelect = document.getElementById("colorStyle");
	var jsonObj = {FuncName:'camSetImageColorStyle',argument:{itype:objectSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}
function SetImageType(){
	var objectSelect = document.getElementById("imageType").value;
	
}
//判断文件存在
function CheckImgExists(imgurl) {  
	var ret  = "file://" + imgurl ;
    var ImgObj = new Image(); //判断图片是否存在  
    ImgObj.src = ret;  
    //没有图片，则返回-1  
    if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {  
        return true;  
    } else {  
        return false;
    }  
} 
//拍照
var filepath
function Capture(){
	var time = new Date();
    var checktime = time.getHours();
	 filepath = 'C:\\tmp\\'  + time.getYear() + time.getMonth ()+ time.getDate() + time.getDate ()+ time.getTime()+".jpg";
	 var jsonObj = {FuncName:'camCaptureImageFile',argument:{filePath:filepath}};
    //var jsonObj = {FuncName:'CaptureEncodeBase64'};
   // base64 =jsonObj;
	 sendWsMessage(jsonObj);
   	timer = setTimeout(function(){addlist("parentUl",filepath);},2000);
   // CaptureBase64Ex(); 
   CaptureBase64();
   
   // var jsonObj = {FuncName:'CaptureEncodeBase64'};
}


//上传
function uploadphoto(index){
   var parentUl = document.getElementById("parentUl");
   var base64 = $('#base64').val();
	//var base64 = "1234566";
	try{

	if(parentUl){
		var http = null;
		var requestUrl = window.location.protocol+"//"+window.location.host+contextPath;
		if(1==type){
			// http =parentUl.Global_CreateHttp(requestUrl+"/interrogate/belong/outgetpicture.do?serialid="+serialid);//upload.asp参照HttpUploadDemo
            http = "/zhfz/bacs/belong/outgetpicture.do?serialid="+serialid+"&serialNo="+serialNo+"&&base64="+base64;
		}else if(3==type){
			http = parentUl.Global_CreateHttp(requestUrl+"/interrogate/entrance/serialWsPicture.do?serialid="+serialid);//upload.asp参照HttpUploadDemo
		}else{
			http = parentUl.Global_CreateHttp(requestUrl+"/interrogate/exhibit/outgetpicture.do?serialid="+serialid);//upload.asp参照HttpUploadDemo
		}
		var url = '';
		if (index == 0){
            url = '/zhfz/bacs/belong/outgetpicturenew.do'
		} else {
			url = '/interrogate/security/outgetpicturenew.do'
		}
		if (http){
			jQuery.ajax({
				async:false,
				type : 'POST',
				url : contextPath+url,
				data : {serialid:serialid,base64:base64,serialNo:serialNo},
				dataType : 'json',
				success : function(data){
					alert( '上传成功');
				},
				error : function(data){
					alert( '上传失败');

				}
			});
		} else{
			alert( 'url 错误!');
		}

	}
  }catch(e){
	  alert(e);
  }
    window.close();
    window.opener.loadDatagrid();
}




function CaptureBase64Ex(){
	var jsonObj = {FuncName:'CaptureEncodeBase64'};
	sendWsMessage(jsonObj);
}
function CaptureBarcode(){
	var time = new Date();
    var checktime = time.getHours();
	var filepath = document.getElementById("saveText").value  + time.getYear() + time.getMonth ()+ time.getDate() + time.getDate ()+ time.getTime()+"." + document.getElementById("imageType").value;
	var jsonObj = {FuncName:'camCaptureImageFile',argument:{filePath:filepath}};
	sendWsMessage(jsonObj);
	var jsonObjCode = {FuncName:'coderRecognizeBarcode',argument:{filePath:filepath}};
	sendWsMessage(jsonObjCode);
}	
//文件上传
function HttpUpload(type){

	if(type == 1)	
	{
		var filepath = document.getElementById("UploadsaveText").value;
		var urlpath = document.getElementById("urlText").value;
		var jsonObj = {FuncName:'camUpdataFileHttp',argument:{filePath:filepath, url:urlpath,param:"param:123",response:""}};
	}else if(type ==2)
	{
		var filepath = document.getElementById("UploadsaveText").value;	
		var FtpAddressText = document.getElementById("FtpAddressText").value;			
		var user = document.getElementById("user").value;
		var pwd = document.getElementById("pwd").value;
		var iport = document.getElementById("iport").value;	
		var floder = document.getElementById("floder").value;		
		var jsonObj = {FuncName:'camUpdataFileFtp',argument:{strfilePath:filepath,strftpPath:FtpAddressText,struserName:user,struserPsd:pwd,iPort:parseInt(iport),strtargetName:floder}};
	}
	sendWsMessage(jsonObj);
	
}


//图片base64
function CaptureBase64()
{
	var filepath = document.getElementById("saveText").value;
	var jsonObj = {FuncName:'FileEncodeBase64',argument:{filePath:filepath}};
	sendWsMessage(jsonObj);
}
//删除文件
function DeleteFile(){
	
	var filepath = document.getElementById("saveText").value;
	var jsonObj = {FuncName:'camDeleteFile',argument:{FilePath:filepath}};
	sendWsMessage(jsonObj);
}
//查看文件
function OpenFile(){
	
	var filepath = document.getElementById("saveText").value;
	var jsonObj = {FuncName:'camShowImage',argument:{FilePath:filepath}};
	sendWsMessage(jsonObj);
}

//遍历文件夹
function FindJPGFile()
{
	var filepath = document.getElementById("Distory").value;
	var jsonObj = {FuncName:'getFolderDayFileA',argument:{Dictpry:filepath}};
	sendWsMessage(jsonObj);
}
//设置DPI
function DPISet(){
	
	var xdpi = document.getElementById("dpix").value;
	var jsonObj = {FuncName:'camSetImageDPI',argument:{xDPI:parseInt(xdpi),xDPI:parseInt(xdpi)}};
	sendWsMessage(jsonObj);
}
//设置JPG压缩率
function JPGQSet()
{
	var JPGQ = document.getElementById("jpg").value;
	var jsonObj = {FuncName:'camSetImageJPGQuanlity',argument:{quanlity:parseInt(JPGQ)}};
	sendWsMessage(jsonObj);
}

//设置手动裁剪区域
function SetCusCrop(obj){

	if(obj.checked)
	{
		var left = 0;
		var right = 0;
		var top = 100;
		var bottom = 100;

		var jsonObj = {FuncName:'camSetImageCusCropRect',argument:{left:parseInt(left),right:parseInt(right),top:parseInt(top),bottom:parseInt(bottom)}};
		sendWsMessage(jsonObj);
	}
	else{
		var left = 0;
		var right = 0;
		var top = 0;
		var bottom = 0;

		var jsonObj = {FuncName:'camSetImageCusCropRect',argument:{left:parseInt(left),right:parseInt(right),top:parseInt(top),bottom:parseInt(bottom)}};
		sendWsMessage(jsonObj);
	}
}
//设置手动裁剪区域
function CropZoneSet(){

	var left = document.getElementById("left").value;
	var right = document.getElementById("right").value;
	var top = document.getElementById("top").value;
	var bottom = document.getElementById("bottom").value;

	var jsonObj = {FuncName:'camSetImageCusCropRect',argument:{left:parseInt(left),right:parseInt(right),top:parseInt(top),bottom:parseInt(bottom)}};
	sendWsMessage(jsonObj);
	
}
function SetDenoise(obj)
{
	var jsonObj = {FuncName:'camSetImageDenoise',argument:{IsAvailabel:obj.checked}};
	sendWsMessage(jsonObj);
	
}
function showBase64info(str)
{
	
	alert("Base64数据为："+ str);
	
}

var AutoCaptureTime = 0;
//连拍
function continuCapture(){
	
	var filepath = document.getElementById("saveText").value + "\\autoCapture.jpg"; 
	var jsonObj = {FuncName:'camStartAutoCapture',argument:{type:0,param:4,filePath:filepath}};
	AutoCaptureTime = 4;
	sendWsMessage(jsonObj);
	//timer = setTimeout(function(){addlist("parentUl",filepath);},2000);
}
//定时连拍
function timeCapture(){
	
	var filepath = document.getElementById("saveText").value + "\\autoCapture.jpg"; 
	var jsonObj = {FuncName:'camStartAutoCapture',argument:{type:1,param:4,filePath:filepath}};
	AutoCaptureTime = 4;
	sendWsMessage(jsonObj);
	//timer = setTimeout(function(){addlist("parentUl",filepath);},2000);
	
}
//停止连拍
function StopAutoCapture(){
	
	var jsonObj = {FuncName:'camStopAutoCapture',argument:{}};
	sendWsMessage(jsonObj);
}

//处理回调
function AutoCaptureBack(re){
	
	var progress0 = document.getElementById("autoCaptureProgress");
	
	
	progress0.value = re * 100 / AutoCaptureTime ;
	

		
}




//获得麦克风名字
function MicrophoneName(names){	

	//设备名字
		
	var objSelect = document.getElementById("Microphone");
	objSelect.options.length = 0;
	
	for (var i = 0; i < names.length;i++ ) {
	
		var op = new Option(names[i],i);
		objSelect.options[objSelect.length] = op;
		
	}
}
//获得视频格式名字					
function VideoName(names){	

	//设备名字
	var objSelect = document.getElementById("VideoType");
	objSelect.options.length = 0;
	for (var i = 0; i < names.length;i++ ) {
	
		var op = new Option(names[i],i);
		objSelect.options[objSelect.length] = op;
		
	}
	
	
	objSelect.onchange = function(){
		
		var pathobj = document.getElementById("SaveVieoText");	
		var savepath = pathobj.value ;	
		var index = objSelect.selectedIndex;  
		var text = objSelect.options[index].text; 
		var str = savepath.indexOf(".") + 1;
		str = savepath.substr(0,str) + text;
		//pathobj.setAttribute("value",str);
		pathobj.value = str;
	}
}	
//开始视频名字
function StartVideo(){
	var savepath = document.getElementById("SaveVieoText").value;
	var objSelect = document.getElementById("devicList");
	var CurMicphone = document.getElementById("Microphone");
	var CurVideoFormat = document.getElementById("VideoType");
	//console.log(savepath,CurMicphone.selectedIndex,CurVideoFormat.selectedIndex);
	var jsonObj = {FuncName:'camStartRecord',argument:{filePath:savepath,micphone:CurMicphone.selectedIndex,videoFormat:CurVideoFormat.selectedIndex}};
	sendWsMessage(jsonObj);
}
//关闭视频
function StopVideo(){
	var objSelect = document.getElementById("devicList");
	var jsonObj = {FuncName:'camStopRecord',argument:{devIndex:objSelect.selectedIndex}};
	sendWsMessage(jsonObj);
	var objSelect = document.getElementById("voice");
	objSelect.setAttribute("Value",0);
}
//录音声音反馈
function GetVoice(){
	var objSelect = document.getElementById("voice");
	var jsonObj = {FuncName:'camGetMicrophoneVolumeLevel',argument:{devIndex:objSelect.selectedIndex}};
	sendWsMessage(jsonObj);
}
function ShowVioce(volume){
	var objSelect = document.getElementById("voice");
	//console.log(objSelect.value);
	objSelect.setAttribute("Value",volume);
}
//合并PDF
var count = 0;
function addFile()
{
		count++;

        var newDiv = "<div id=divUpload" + count + ">"
            + " <input  id=file"+count+" type=text width=1000 size=50 name=upload/>"
             +"<a href=javascript:delUpload('divUpload" + count+"')>删除</a>"
            + " </div>";

        var newDiv2 = "<div id=index" + count + ">" + count + " </div>";
        document.getElementById("uploadContent").insertAdjacentHTML("beforeEnd", newDiv);
        document.getElementById("Div2").insertAdjacentHTML("beforeEnd", newDiv2);
}
function delUpload(diva) {
	document.getElementById("Div2").removeChild(document.getElementById("index"+count));
    count--;
    document.getElementById(diva).parentNode.removeChild(document.getElementById(diva));  
}
function CombineFile()
{
    for(var i = 1;i<count+1;i++)
    {
        var path = document.getElementById("file" + i).value;
        if(path==null)
        {
           continue;
        }
        if(path.value=="")
        {
           continue;
        }
        var ret = AddFileToPDFList(path);
    }
       var fileext =".pdf";
	   var strFolder = document.getElementById("pdftext").value;
	   var myDate = new Date();
	   var myName = "Image_"+myDate.getFullYear()+(myDate.getMonth()+1)+myDate.getDate()+"_"+myDate.getHours()+myDate.getMinutes()+myDate.getSeconds()+myDate.getMilliseconds();

	   var newFile = strFolder + "\\" + myName + fileext ;
       var result = CombinePDF(newFile, 50); 
}

function AddFileToPDFList(path)
{
	var filepath = path;
	var jsonObj = {FuncName:'camAddFileToPDFList',argument:{filePath:filepath}};
	sendWsMessage(jsonObj);
}

function CombinePDF(path,JQ)
{
	var JpegQuaility = JQ;
	var filepath = path;
	var jsonObj = {FuncName:'camCombinePDF',argument:{filePath:filepath,JpegQuality:JpegQuaility}};
	sendWsMessage(jsonObj);
}
function CombinePicture()
{

    var path1 = document.getElementById("file1").value;
    var path2 = document.getElementById("file2").value;
    var fileext =".jpg";
	var strFolder = document.getElementById("pdftext").value;
	var myDate = new Date();
	var myName = "Image_"+myDate.getFullYear()+(myDate.getMonth()+1)+myDate.getDate()+"_"+myDate.getHours()+myDate.getMinutes()+myDate.getSeconds()+myDate.getMilliseconds();
	var newFile = strFolder + "\\" + myName + fileext ;

	var objectSelect = document.getElementById("CombineType").selectedIndex;
	var iType = 0;
	if(objectSelect == 0){
		iType = 7;
	}else{
		iType = 4;
	}

	var ioffsetX = 0;
	var ioffsetY = 0;

	var jsonObj = {FuncName:'camCombineImage',argument:{filePath1:path1,filePath2:path2,PdfPath:newFile,Type:iType,offsetX:ioffsetX,offsetY:ioffsetY}};
	sendWsMessage(jsonObj);
}
 

//图片添加路径  
function imgFormatter(value,row,index){  
     if('' != value && null != value){  
    var strs = new Array(); //定义一数组   
    if(value.substr(value.length-1,1)==","){  
        value=value.substr(0,value.length-1)  
    }  
        strs = value.split(","); //字符分割   
  var rvalue ="";            
    for (i=0;i<strs.length ;i++ ){   
        rvalue += "<img onclick=download(\""+strs[i]+"\") style='width:66px; height:60px;margin-left:3px;' src='<%=path%>" + strs[i] + "' title='点击查看图片'/>";  
        }   
    return  rvalue;        
     }  
    }  
	

function SetVideoParameter()
{
	var vp1 = document.getElementById("VideoSetPara1").value;
    var vp2 = document.getElementById("VideoSetPara2").value;
	var vSettingValue = document.getElementById("VideoSetting").value;
    var vIsVideoSetAuto = document.getElementById("IsVideoSetAuto").value;	


	var jsonObj = {FuncName:'camSetVideoParameter',argument:{ipara1:parseInt(vp1),ipara2:parseInt(vp2),ilvalue:parseInt(vSettingValue),iflag:parseInt(vIsVideoSetAuto)}};

	sendWsMessage(jsonObj);
	
}


var nCount = 0;
function showImageFile(szPath)
{
	
	var szCount = "video" + nCount;
	document.getElementById(szCount).src = szPath;
	nCount = nCount + 1;
	if(nCount==14)
	{
		nCount = 0;
	}
}
//视频预览 放大
function ZoonMin()
{

	var jsonObj = {FuncName:'camZooIn'};
	sendWsMessage(jsonObj);
}
function Zoomout()
{
	var jsonObj = {FuncName:'camZoomOut'};
	sendWsMessage(jsonObj);
}
function OriginalPreview()
{
	var jsonObj = {FuncName:'camOptimalPreview'};
	sendWsMessage(jsonObj);
}
function OptimalPreview()
{
	var jsonObj = {FuncName:'camOriginalPreview'};
	sendWsMessage(jsonObj);
}
function ShowDevSettingWindow()
{
	var jsonObj = {FuncName:'camShowDevSettingWindow'};
	sendWsMessage(jsonObj);
}
function ShowImageSettingWindow()
{
	var jsonObj = {FuncName:'camShowImageSettingWindow'};
	sendWsMessage(jsonObj);
}
function Buzzer()
{
	var jsonObj = {FuncName:'EnableBuzzer',argument:{nCount:parseInt(2),Duration:parseInt(300),Interval:parseInt(500)}};
	sendWsMessage(jsonObj);
}
function AutoFoucs()
{
	var jsonObj = {FuncName:'camAutoFocus',argument:{camAutoFocus:parseInt(0)}};
	sendWsMessage(jsonObj);
}