$(function(){
	try{
        var xml=document.getElementById("xml");
        var xmlData=decodeURIComponent(xml.value).replace(/#/g,".");
        var startTime=document.getElementById("startTime");
        var startTimeData=decodeURIComponent(startTime.value);
        var endTime=document.getElementById("endTime");
        var endTimeData=decodeURIComponent(endTime.value);
        var ocxObj = document.getElementById("spb");
        var languageType = 1;
        var ret = ocxObj.SPB_Init(languageType);
        if (ret != 0) {
            $.messager.alert("错误","单路回放初始化失败");
            return;
        }
        ocxObj.SPB_SetToolBar("1,2,3,4");
       var ret = ocxObj.SPB_StartPlayBack(xmlData, startTimeData, endTimeData);
        if (ret != 0) {
            $.messager.alert("错误","单路回放失败：");
        }
	}catch(e){
		$.messager.alert('错误', '单路回放失败!');
		return;
	}
});

function InitSpb() {
    var ocxObj = document.getElementById("spb");
    var languageType = 1;
    var ret = ocxObj.SPB_Init(languageType);
}

function UninitSpb() {
    var ocxObj = document.getElementById("spb");
    var ret = ocxObj.SPB_Uninit();
    if (ret != 0) {
        $.messager.alert("错误","单路回放反初始化失败");
    }
    window.close();
}