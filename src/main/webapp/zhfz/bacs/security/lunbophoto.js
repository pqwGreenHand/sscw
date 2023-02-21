$(function(){

})
function testPhotos(serialUUID,areaId,count){
//加载右上角的体表标记
    intlize();
    selectImagesPhoto(serialUUID,areaId,count);
}
function intlize(){
    var mySwiper = new Swiper('.swiper-container');
    mySwiper.removeAllSlides(); //移除slide
    var swiper = new Swiper('.swiper-container', {
        pagination: {
            el: '.swiper-pagination',
            type: 'custom',
            renderCustom: function (swiper, current, total) {
                return    "<font color='white'>伤口照片："+current + '/' + total+"</font>";           ;
            }
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        observer:true,
    });
}
function selectImagesPhoto(serialUUID,areaId,count){
var areaId= areaId;
var sysType = "ba";
var fileType ="aj";
var uuid = serialUUID;
    jQuery.ajax({
        type: "post",
        url: 'getImages.do?uuid='+serialUUID+"&type="+1+"&count="+count,
        async :false,
        dataType : 'json',
        success: function (data) {
             for(var i=0;i<data.length;i++){
                 $(".swiper-wrapper").append("<div class='swiper-slide' style='background-color: #082040'>"+fileUtils.look(sysType,fileType,uuid,areaId,data[i].picName)+"</div>")
             }
        }
    })
}
