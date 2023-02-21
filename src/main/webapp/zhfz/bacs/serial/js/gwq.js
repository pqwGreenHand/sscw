//标签切换用的方法

window.onload=function(){	
function $(id){return document.getElementById(id)}
var menu=$("topTags").getElementsByTagName("ul")[0];//顶部菜单容器
var tags=menu.getElementsByTagName("li");//顶部菜单
var ck=$("leftMenu").getElementsByTagName("ul")[0].getElementsByTagName("li");//左侧菜单
var j;
//点击左侧菜单增加新标签
for(i=0;i<ck.length;i++){
ck[i].onclick=function(){
$("welcome").style.display="none"//欢迎内容隐藏
//循环取得当前索引
for(j=0;j<8;j++){
if(this==ck[j]){
if($("p"+j)==null){
openNew(j,this.innerHTML);//设置标签显示文字
 }
clearStyle();
$("p"+j).style.backgroundColor="yellow";
clearContent();
$("c"+j).style.display="block";
   }
 }
return false;
  }
 }
//增加或删除标签
function openNew(id,name){
var tagMenu=document.createElement("li");
tagMenu.id="p"+id;
tagMenu.innerHTML=name+"   "+"<img src='./img/close.jpg' style='vertical-align:middle'/>";
//标签点击事件
tagMenu.onclick=function(evt){
clearStyle();
tagMenu.style.backgroundColor="yellow";
clearContent();
$("c"+id).style.display="block";
}
//标签内关闭图片点击事件
tagMenu.getElementsByTagName("img")[0].onclick=function(evt){
evt=(evt)?evt:((window.event)?window.event:null);
if(evt.stopPropagation){evt.stopPropagation()} //取消opera和Safari冒泡行为;
this.parentNode.parentNode.removeChild(tagMenu);//删除当前标签
var color=tagMenu.style.backgroundColor;
//设置如果关闭一个标签时，让最后一个标签得到焦点
if(color=="#ffff00"||color=="yellow"){//区别浏览器对颜色解释
if(tags.length-1>=0){
clearStyle();
tags[tags.length-1].style.backgroundColor="yellow";
clearContent();
var cc=tags[tags.length-1].id.split("p");
$("c"+cc[1]).style.display="block";
 }
else{
clearContent();
$("welcome").style.display="block"
   }
  }
}
menu.appendChild(tagMenu);
}
//清除标签样式
function clearStyle(){
for(i=0;i<tags.length;i++){
menu.getElementsByTagName("li")[i].style.backgroundColor="#FFCC00";
  }
}
//清除内容
function clearContent(){
for(i=0;i<ck.length;i++){
$("c"+i).style.display="none";
 }
}
}




//函数功能的调用
function GWQ_SetVolume(Value)
{
	var ret=GWQ.GWQ_SetVolume(Value);
	if(ret==0)
	{
		alert("成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
}

function GWQ_SetBrightness(Value)
{
	var ret=GWQ.GWQ_SetBrightness(Value);
	if(ret==0)
	{
		alert("成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}	
	
}

function GWQ_TellerInfo(TellerName,TellerNo,TellerPhoto)
{
	if(TellerPhoto=="")
		TellerPhoto="C:\\Program Files (x86)\\GWQOcx\\test.jpg";
	var ret=GWQ.GWQ_TellerInfo(TellerName,TellerNo,TellerPhoto);
	if(ret==0)
	{
		alert("展示成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
}

function GWQ_CallNumber(TellerName,TellerNo,TellerPhoto,number)
{
	if(TellerPhoto=="")
		TellerPhoto="C:\\Program Files (x86)\\GWQOcx\\test.jpg";
	var ret=GWQ.GWQ_CallNumber(TellerName,TellerNo,TellerPhoto,number);
	if(ret==0)
		alert("叫号成功");
	else
		alert("失败，返回错误码为"+ret);
}

function GWQ_PaymentInfo(TellerPhoto,Info)
{
	if(TellerPhoto=="")
		TellerPhoto="C:\\Program Files (x86)\\GWQOcx\\QR_Code.jpg";
	var ret=GWQ.GWQ_PaymentInfo(TellerPhoto,Info);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);	
}

function GWQ_CancelOperate()
{
	var ret=GWQ.GWQ_CancelOperate();
	if(ret==0)
	{
		alert("关闭成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
}

function DoGWQ_StartInfo(VoiceStr,Info)
{
	var ret=GWQ.DoGWQ_StartInfo(VoiceStr,Info);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function OnAfterGWQ_StartInfo(ErrorCode)
{
	
	if(ErrorCode==0)
	{
		alert("信息交互成功");
	}
	else if(ErrorCode==-9)
	{
		alert("信息交互取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function GWQ_StartEvaluator(TellerName,TellerNo,TellerPhoto)
{
	var ret=GWQ.DoGWQ_StartEvaluator(TellerName,TellerNo,TellerPhoto);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function OnAfterGWQ_StartEvaluator(ErrorCode,EvaluatorLevel)
{
	if(ErrorCode==0)
	{
		if(EvaluatorLevel==1)
			alert("非常满意");
		if(EvaluatorLevel==2)
			alert("满意");
		if(EvaluatorLevel==3)
			alert("一般");
		if(EvaluatorLevel==4)
			alert("非常不满意");			
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}


function DoGWQ_StartSign(PDFPath,XmlPath,mylocation,VoiceStr,timeout)
{
	if(PDFPath=="") PDFPath="C:\\Program Files (x86)\\GWQOcx\\test.pdf";
	var ret=GWQ.DoGWQ_StartSign(PDFPath,XmlPath,mylocation,VoiceStr,timeout);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function DoGWQ_PDFConfirm(PDFPath,timeout)
{
	if(PDFPath=="") PDFPath="C:\\Program Files (x86)\\GWQOcx\\test.pdf";
	var ret=GWQ.DoGWQ_PDFConfirm(PDFPath,timeout);
	if(ret==0)
		alert("启动成功");
	else
		alert("启动失败，返回错误码为"+ret);
}

function OnAfertGWQ_PDFConfirm(ErrorCode)
{
	if(ErrorCode==0)
	{
		alert("确认无误");
	}
	else if(ErrorCode==-9)
	{
		alert("有误");
	}
	else if(ErrorCode==-2)
	{
		alert("超时");
	}
	else
	{
		alert("确认失败，返回错误码为"+ErrorCode);
	}
}

function GWQ_ShowPDF(PDFPath,timeout)
{
	if(PDFPath=="") PDFPath="C:\\Program Files (x86)\\GWQOcx\\test.pdf";
	var ret=GWQ.GWQ_ShowPDF(PDFPath,timeout);
	if(ret==0)
	{
		alert("成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
}

function OnAfterGWQ_StartSign(ErrorCode,SignPdfBase64,SignNameBase64,FingerPrintBase64,MixBase64)
{
	if(ErrorCode==0)
	{
		alert("电子签名成功");
	}
	else if(ErrorCode==-9)
	{
		alert("电子签名取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function GWQ_FileUpload(FileName)
{
	if(FileName=="") FileName="C:\\Program Files (x86)\\GWQOcx\\ad.jpg";
	var ret=GWQ.GWQ_FileUpload(FileName);
	if(ret==0)
	{
		alert("上传文件成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
	
}

function GWQ_FileDelete(FileName)
{
	var ret=GWQ.GWQ_FileDelete(FileName);
	if(ret==0)
	{
		alert("删除成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
}

function GWQ_GetFileList()
{
	var ret=GWQ.GWQ_GetFileList();
	if(ret!="")
	{
		alert(ret);
	}
	else
	{
		alert("获取文件列表失败");
	}
}

function GWQ_Update()
{
	var ret=GWQ.GWQ_Update();
	if(ret==0)
	{
		alert("成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}
}

function DoGWQ_ReadID(name,sex,nation,birth,addr,id_num)
{
	document.getElementById(name).value="";
	document.getElementById(sex).value="";
	document.getElementById(nation).value="";
	document.getElementById(birth).value="";
	document.getElementById(addr).value="";
	document.getElementById(id_num).value="";
	//alert(rrrr);
	//name.value="1111";
	//sex.value="";
	//nation.value="";
	//birth.value="";
	//addr.value="";
	//id_num.value="";
	var ret=GWQ.DoGWQ_ReadID();
	if(ret==0)
	{
		alert("启动成功");
	}
	else
	{
		alert("失败，返回错误码为"+ret);
	}		
}

function OnAfterGWQ_ReadID(ErrorCode,JsonData,name,sex,nation,birth,addr,id_num)
{
	
	if(ErrorCode==0)
	{
		var obj = JSON.parse(JsonData);
		document.getElementById(name).value=obj.name;
		document.getElementById(sex).value=obj.sex;
		document.getElementById(nation).value=obj.nation;
		document.getElementById(birth).value=obj.birth;
		document.getElementById(addr).value=obj.addr;
		document.getElementById(id_num).value=obj.id_num;
		//document.getElementById(HeadImg1).src=obj.base64_ID;
		//alert(obj.base64_ID);
		//alert(obj.name);
	}
	else if(ErrorCode==-9)
	{
		alert("取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function DoGWQ_StartFace(name,sex,nation,birth,addr,id_num)
{
	document.getElementById(name).value="";
	document.getElementById(sex).value="";
	document.getElementById(nation).value="";
	document.getElementById(birth).value="";
	document.getElementById(addr).value="";
	document.getElementById(id_num).value="";
	
	var ret=GWQ.DoGWQ_StartFace();
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function DoGWQ_StartFaceWithImg(name,sex,nation,birth,addr,id_num,photo)
{
	document.getElementById(name).value="";
	document.getElementById(sex).value="";
	document.getElementById(nation).value="";
	document.getElementById(birth).value="";
	document.getElementById(addr).value="";
	document.getElementById(id_num).value="";
	
	var ret=GWQ.DoGWQ_StartFaceWithImg(photo);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function OnAfterGWQ_StartFace(ErrorCode,JsonData,name,sex,nation,birth,addr,id_num)
{
	if(ErrorCode==0)
	{
		//alert(JsonData);
		var obj = JSON.parse(JsonData);
		document.getElementById(name).value=obj.name;
		document.getElementById(sex).value=obj.sex;
		document.getElementById(nation).value=obj.nation;
		document.getElementById(birth).value=obj.birth;
		document.getElementById(addr).value=obj.addr;
		document.getElementById(id_num).value=obj.id_num;
		if(obj.passFlag==true)
			alert("比对通过");
		else
			alert("比对失败");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}
	
function DoGWQ_SignName(XmlPath,SignNamePath)
{
	var ret=GWQ.DoGWQ_SignName(XmlPath,SignNamePath,"预留字段");
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function OnAfterGWQ_SignName(ErrorCode)
{
	if(ErrorCode==0)
	{
		alert("成功");
	}
	else if(ErrorCode==-9)
	{
		alert("取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function GWQ_GetVer(version)
{
	document.getElementById(version).value="";
	var ret=GWQ.GWQ_GetVer();
	//var rett=GWQ.GWQ_Bind(0);
	if(ret!="")
	{
		document.getElementById(version).value=ret;
	}
	else
	{
		alert("获取版本号失败");
	}
}

function OnAfterGWQStartKeyboard(ErrorCode,iDisplayResult,keyboard)
{
	document.getElementById(keyboard).value="";
	if(ErrorCode==0)
	{
		document.getElementById(keyboard).value=iDisplayResult;
	}
	else if(ErrorCode==-9)
	{
		alert("取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function DoGWQ_StartKeyboard(TimeOut,num)
{
	var ret=GWQ.DoGWQ_StartKeyboard(TimeOut,num);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function OnAfterGWQ_GetFrame(ErrorCode)
{
	if(ErrorCode==0)
	{
		alert("成功");
	}
	else if(ErrorCode==-9)
	{
		alert("取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function GWQ_GetFrame(filename)
{
	var ret=GWQ.GWQ_GetFrame(filename);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}

function OnAfterGWQ_FPOperation(ErrorCode,Info)
{
	if(ErrorCode==0)
	{
		alert(Info);
	}
	else if(ErrorCode==-2)
	{
		alert("超时");
	}
	else if(ErrorCode==-9)
	{
		alert("取消");
	}
	else
	{
		alert("失败，返回错误码为"+ErrorCode);
	}
}

function DoGWQ_FPOperation(Type,Name,Number,Timeout)
{
	var ret=GWQ.DoGWQ_FPOperation(Type,Name,Number,Timeout);
	if(ret==0)
		alert("启动成功");
	else
		alert("失败，返回错误码为"+ret);
}
function WordToPDF(word,pdf)
{
	var ret=GWQ.WordToPDF(word,pdf);
	if(ret==0)
		alert("转换成功");
	else
		alert("失败，返回错误码为"+ret);
}
