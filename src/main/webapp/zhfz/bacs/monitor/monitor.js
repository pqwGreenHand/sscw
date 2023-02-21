 function selectArea(bmId) {
     if(bmId == '42'){
         // 大峪派出所
         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_dayu&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }
     if(bmId == '43'){
         // 月季园派出所
         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_yuejiyuan&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }
     // 交通支队
     if(bmId == '46'){
         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_jtzd&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }
     // 三家店
     if(bmId == '47'){
         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_sjd&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }
     // 妙峰山
     if(bmId == '48'){
         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_mfs&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }
     // 潭柘寺
     if(bmId == '44'){

         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_tzs&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }
     if(bmId == "1"){
         // 办案中心
         window.open('http://10.12.84.11/cas/remoteLogin?service=http%3a%2f%2f10.12.84.11%2fvas%2fweb%2fplayvideo.action&loginType=9&username=zx_mtg&password=3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2');
     }

 }
$(function () {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath+"/zhfz/bacs/console/queryCenters.do",
        async : false,
        dataType: 'json',
        success: function (data) {
            if(data==null||data[0].id<1){
                //显示流程
                $("#content_vhart").show();
                //隐藏echatrs
                //$("#liuchengtu").hidden();
                // hiddendiv("#echart");
            }else{
                for(var i = 0;i<data.length;i++){
                    $("#centers").append("<li id='"+data[i].id+"' class='caselist01'><a  href='javascript:void(0);' onclick='selectArea("+data[i].id+")'><i class='icon'></i>"+data[i].name+"监控</a></li>");
                }
                $("#echart").show();
            }
            //centerId = data[0].id;
            //name = data[0].name;
        }
    });
})