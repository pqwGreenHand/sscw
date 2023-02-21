
//模式
var g_obj =  "";

//图片保存的目录
let g_savePicDir = "c:/tmp/";

function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window)
        return true;
    else
        return false;
}


//加载页面完成
window.onload = function () {
    SingleCamera.InitSDKAndInitUI();
}
//页面卸载
window.onunload = function () {

    SingleCamera.UInitCamera(g_obj);
}

//初始化并配置UI
let SingleCamera = {

    first:1,
    devIndex : 0,
    comparePath:"",
    compareBase64:"",
    previewDevIndex:0,
    realPicPath:"",
    encodeBase64Tag:0,

    //初始化UI
    InitSDKAndInitUI:function (obj){

        if (this.first == 1)
        {
            StartWebSocket();
            this.first = 0;
        }

    },



//反初始化
    UInitCamera:function (obj) {
        unload();
    },

}


