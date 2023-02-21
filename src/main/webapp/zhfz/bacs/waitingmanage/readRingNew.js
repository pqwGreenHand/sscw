/**
 * 手环采集，获取手环序号
 * */
function ringCollect(comRD) {
    var result; //调comRD800库返回的结果
    var lSnr; //用于取序列号，这里只是当成dc_card函数的一个临时变量
    var ringComRD = {
        COM : 100,//端口号
        BAUT: 115200, // 波特率
        MODEL: 0, //模式
        SECTOR: 1,//扇区号
        ADDRESS: 4, //块地址
        INIT_ERROR: 10,//初始化失败
        CARD_ERROR:11,//寻卡失败
        LOAD_ERROR:12,//装载失败
        CHECK_ERROR:13,//核对密码失败
        READ_ERROR:14,//读卡失败
        EXIT_ERROR:15//关闭端口失败
    };
    //初始化端口
    result = comRD.dc_init(ringComRD.COM, ringComRD.BAUT);

    if(result <= 0){
        comRD.dc_exit();
        return ringComRD.INIT_ERROR;
    }
    //寻卡，返回卡的序列号
    result = comRD.dc_card(ringComRD.MODEL, lSnr);
    if(result != 0){
        comRD.dc_exit();
        return ringComRD.CARD_ERROR;
    }
    //将密码装入读写模块RAM中
    comRD.put_bstrSBuffer_asc = "FFFFFFFFFFFF"; //在调用dc_load_key前先设置属性rd.put_bstrSBuffer_asc
    result = comRD.dc_load_key(ringComRD.MODEL, ringComRD.SECTOR);

    if(result != 0){
        comRD.dc_exit();
        return ringComRD.LOAD_ERROR;
    }
    //核对密码函数
    result = comRD.dc_authentication(ringComRD.MODEL,ringComRD.SECTOR);

    if(result != 0){
        comRD.dc_exit();
        return ringComRD.CHECK_ERROR;
    }
    //读取卡中数据
    result = comRD.dc_read(ringComRD.ADDRESS);

    if(result != 0){
        comRD.dc_exit();
        return ringComRD.READ_ERROR;
    }
    //关闭端口
    result = comRD.dc_exit();
    if(result != 0){//返回值小于0表示失败
        return ringComRD.EXIT_ERROR;
    }
    //宁夏手环
    var ringNumber = comRD.get_bstrRBuffer_asc;
    if(ringNumber == null || ringNumber == ""){
        return;
    }
    var ringNum = ringNumber.substring(ringNumber.length - 4,ringNumber.length);
    var ringCode = parseInt(ringNum,16);
    return ringCode;
}

/**
 * 读取手环信息
 * */
function readRing(ringOcx) {
    //var ringOcx = document.getElementById("ocx");

    var ringCode = ringCollect(ringOcx);
    var ringComRD = {
        COM : 100,//端口号
        BAUT: 115200, // 波特率
        MODEL: 0, //模式
        SECTOR: 1,//扇区号
        ADDRESS: 4, //块地址
        INIT_ERROR: 10,//初始化失败
        CARD_ERROR:11,//寻卡失败
        LOAD_ERROR:12,//装载失败
        CHECK_ERROR:13,//核对密码失败
        READ_ERROR:14,//读卡失败
        EXIT_ERROR:15//关闭端口失败
    };
    if(ringCode == ringComRD.INIT_ERROR){//10 初始化失败
        return "";
    }else if(ringCode == ringComRD.LOAD_ERROR){// 12 装载失败
        return "";
    }else if(ringCode == ringComRD.EXIT_ERROR){// 15 关闭端口失败
        return "";
    }else if(ringCode == ringComRD.CHECK_ERROR){// 13 核对密码失败
        return "";
    }else if(ringCode == ringComRD.READ_ERROR){// 14 读卡失败
        return "";
    }else if(ringCode == ringComRD.CARD_ERROR){// 11 寻卡失败
        return "";
    }else{
        //document.getElementById("ringCode").value = ringCode;
    }

    return ringCode;
}