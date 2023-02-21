$(function(){

})
function testMakers(serialUUID,areaId,count){
//加载右上角的体表标记
    intlizeMakers();
    selectImagesMakers(serialUUID,areaId,count);
}
function intlizeMakers(){
    var mySwiper = new Swiper('.swiper-container');
    mySwiper.removeAllSlides(); //移除slide
    var swiper = new Swiper('.swiper-container', {
        pagination: {
            el: '.swiper-pagination',
            type: 'custom',
            renderCustom: function (swiper, current, total) {
                return    "<font color='white'>体表标记照片1张</font>";
            }
        },
    });
}
function selectImagesMakers(serialUUID,areaId,count){
    var areaId= areaId;
    var sysType = "ba";
    var fileType ="aj";
    var uuid = serialUUID;
    jQuery.ajax({
        type: "post",
        url: 'getImages.do?uuid='+serialUUID+"&type="+0+"&count="+count,
        async :false,
        dataType : 'json',
        success: function (data) {
            for(var i=0;i<data.length;i++){
                $(".swiper-wrapper").append("<div class='swiper-slide' style='background-color: #082040'>"+fileUtils.look(sysType,fileType,uuid,areaId,data[i].picName)+"</div>")
            }
            ready();
        }
    })
}
function ready() {
    $(".jzw-look-img").offsetWidth  = 200 + 'px';
    $('.jzw-look-img').offsetWidth  = 100 + 'px';
}