var rgba_Array = new Array();

function addRgba(r,g,b){
	var l=rgba_Array.length;
	rgba_Array[l] = new Array();
	rgba_Array[l][0] = r;
	rgba_Array[l][1] = g;
	rgba_Array[l][2] = b;
}

function getRgba(i,lucency){
	if(i>=rgba_Array.length){
		return 'rgba(128,128,128,1)' ;
	}else{
		if(!lucency){
			lucency=1;
		}
		var col=rgba_Array[i] ;
		return 'rgba('+col[0]+','+col[1]+','+col[2]+','+lucency+')' ;
	}
}
//init 随机颜色 10个
addRgba(255,0,0);//红色
addRgba(255,255,0);//黄色
addRgba(0,0,255);//蓝色
addRgba(218,112,214);//淡紫色
addRgba(255,215,0);//金黄
addRgba(192,192,192);//灰色
addRgba(3,168,158);//锰蓝
addRgba(138,43,226);//紫罗兰
addRgba(88,87,86);//象牙黑
addRgba(25,25,112);//深蓝





//init 常用颜色


addRgba(0,0,0);//黑色
addRgba(255,255,255);//白色
addRgba(88,87,86);//象牙黑
addRgba(202,235,216);//天蓝灰
addRgba(128,138,135);//冷灰
addRgba(192,192,192);//灰色
addRgba(128,118,105);//暖灰
addRgba(251,255,242);//象牙灰
addRgba(118,128,105);//石板灰
addRgba(250,240,230);//亚麻灰
addRgba(245,245,245);//白烟灰
addRgba(255,235,205);//杏仁灰
addRgba(252,230,202);//蛋壳灰
addRgba(255,245,238);//贝壳灰
addRgba(255,0,0);//红色
addRgba(255,255,0);//黄色
addRgba(227,23,13);//镉红
addRgba(255,153,18);//镉黄
addRgba(156,102,31);//砖红
addRgba(227,207,87);//香蕉黄
addRgba(255,127,80);//珊瑚红
addRgba(255,215,0);//金黄
addRgba(255,99,71);//番茄红
addRgba(255,125,64);//肉黄
addRgba(255,192,203);//粉红
addRgba(255,227,132);//粉黄
addRgba(176,23,31);//印度红
addRgba(255,128,0);//橘黄
addRgba(255,0,255);//深红
addRgba(237,145,33);//萝卜黄
addRgba(116,0,0);//黑红
addRgba(85,102,0);//黑黄
addRgba(0,255,0);//绿色
addRgba(128,42,42);//棕色
addRgba(0,255,255);//青色
addRgba(199,97,20);//土色
addRgba(127,255,0);//黄绿色
addRgba(244,164,95);//沙棕色
addRgba(64,224,205);//青绿色
addRgba(210,180,140);//棕褐色
addRgba(8,46,84);//靛青色
addRgba(188,143,143);//玫瑰红
addRgba(34,139,34);//森林绿
addRgba(160,82,45);//赫色
addRgba(107,142,35);//草绿色
addRgba(199,97,20);//肖贡土色
addRgba(0,0,255);//蓝色
addRgba(3,168,158);//锰蓝
addRgba(218,112,214);//淡紫色
addRgba(25,25,112);//深蓝
addRgba(138,43,226);//紫罗兰
addRgba(0,199,140);//土耳其蓝
addRgba(153,51,250);//胡紫色
