$.fn.serializeObject = function (param) {
    var o = $.extend(true,{},param || {});
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 参数解析方法（参数eg:“name:jzw,age:233”）
 **/
function parseParam(args, param) {
    var o = $.extend(true,{},param || {});
    if (typeof args === "string" && args !== '') {

        var arg = args.split(',');
        for (var int = 0; int < arg.length; int++) {
            var ar = arg[int].split(':');
            if(ar.length == 2)
                o[ar[0]] = ar[1];
        }
    }
    return o;
}
//上传文件类型
var fileTypeEntity = {
    //入区
   FILETYPRRQ : "RQ",
    //签章
    FILETYPRQZ : "QZ"
}
/**
 * 文件下载/预览工具
 * @ params [sysType(ba=办案场所,aj=案件资料;必填),
 *         fileType(AJ=安检 BL=笔录 GJ=轨迹 RQ=入区 WP=物品 SA=涉案 QT=其他 HM=虹膜等等;必填),
 *         uuid(案件uuid=>ba_case表下uuid 或 嫌疑人uuid=>ba_serial表下uuid;必填),
 *         sysId(区域id;必填),
 *         fileName(文件名;必填)]
 */
var fileUtils = {
    data:{}
    //图片预览，返回一个img对象
    ,look : function (sysType,fileType,uuid,sysId,fileName) {
        var img = '<img src="' + fileUtils.url(sysType,fileType,uuid,sysId,fileName) + '" class="jzw-look-img"/>'
        return img;
    }
    //图片下载，js调用一下就会自己下载
    ,load: function (sysType,fileType,uuid,sysId,fileName) {
        var $load = $('#jzw-file-load');
        if($load.length == 0){
            $('body').append('<a id="jzw-file-load" style="display:none;"><span></span></a>');
            $load = $('#jzw-file-load');
        }
        $load.attr('href',fileUtils.url(sysType,fileType,uuid,sysId,fileName)).find('span').trigger('click');
    }
    //返回一个图片/文件的全地址，可在浏览器打开
    ,url: function (sysType,fileType,uuid,sysId,fileName) {
        var params = {sysType:sysType,fileType:fileType,uuid:uuid,sysId:sysId,fileName:fileName};
        console.log("params:",params)
        return (fileUtils.originalUrl(params.sysType,params.sysId) + '/DownloadFile/{uuid}/{fileType}/{fileName}' ).format(params)+"?trefresh="+new Date().getTime();
    }
    ,read:function (uri,type) {
        type = type || 'word';
        var url
        if(uri.indexOf('http:')>-1){
            url = uri;
        }else
            url = 'http://' + window.location.host + contextPath + uri;
        readDoc(url,type);
    }
    //不可用
    ,originalUrl : function (sysType,sysId) {
        if(fileUtils.data[sysType + sysId] === undefined){
            jQuery.ajax({
                url: contextPath + '/zhfz/common/fileServiceConfig/url.do',
                type: 'GET',
                dataType: 'json',
                data: {sysType:sysType,sysId:sysId},
                async :false,
                success:function (res) {
                    console.log(res);
                    fileUtils.data[sysType + sysId] = res.callbackData;
                }

            })
                .done(function() {
                    console.log("success");
                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");
                });
        }
        return fileUtils.data[sysType + sysId];
    }

}
function getBrowser(){
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    var ver;
    (s = ua.match(/edge\/([\d.]+)/)) ? Sys.edge = s[1] :
        (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
            (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                            (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    if (Sys.edge) return 1;
    if (Sys.ie) return 0;
    if (Sys.firefox) return 1;
    if (Sys.chrome){ ver = Sys.chrome;ver.toLowerCase();var arr = ver.split('.');if(parseInt(arr[0])>43){return 1;}else{return 0;}}
    if (Sys.opera) return 1;
    if (Sys.safari) return 1;
    return 1;
}

function ShowPage(root,path)
{
    var pre = "WebOffice://|Officectrl|";//智能窗打开的固定参数
    var v=getBrowser();
    var strUrl;
    if(v==1 || path.indexOf('?pdf')){//当浏览器返回值为1时定义为使用智能窗方式打开网址
        strUrl = pre + root + path;
        window.open(strUrl,'_self');
    }
    else
    { //当浏览器返回值1以外的数据时采用传统方式打开网址
        strUrl = root + path;
        window.open(strUrl,'_blank');
    }
}

function readDoc(url,type)
{
    var strPath='read.jsp?'+type+',url=' + url;//'<%=request.getParameter("url")%>';
    var strRoot = decodeURI('http://' + window.location.host + contextPath + '/zhfz/common/weboffice/');
    ShowPage(strRoot,strPath);
}



function parser(s) {
    if (!s)
        return new Date();
    var sss = s.split('-');
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    var d = parseInt(ss[2], 10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
        return new Date(y, m - 1, d);
    } else {
        return new Date();
    }
}

function formatDateText(date) {
    return date.formatDate("yyyy-MM-dd");
}

function formatDateTimeText(date) {
    return date.formatDate("yyyy-MM-dd hh:mm:ss");
}

//把时间格式字符串转化为时间
//如下格式
//2006
//2006-01
//2006-01-01
//2006-01-01 12
//2006-01-01 12:12
//2006-01-01 12:12:12
function parseDate(dateStr) {
    var regexDT = /(\d{4})-?(\d{2})?-?(\d{2})?\s?(\d{2})?:?(\d{2})?:?(\d{2})?/g;
    var matchs = regexDT.exec(dateStr);
    var date = new Array();
    for (var i = 1; i < matchs.length; i++) {
        if (matchs[i] != undefined) {
            date[i] = matchs[i];
        } else {
            if (i <= 3) {
                date[i] = '01';
            } else {
                date[i] = '00';
            }
        }
    }
    return new Date(date[1], date[2] - 1, date[3], date[4], date[5], date[6]);
}

//为date类添加一个format方法
//yyyy 年
//MM 月
//dd 日
//hh 小时
//mm 分
//ss 秒
//qq 季度
//S  毫秒
Date.prototype.formatDate = function (format) //author: meizz
{
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

function formatter(date,ss) {
    ss = ss || 'yyyy-MM-dd';
    if(date == null)
        return '--/--';
    if(typeof date === 'number')
        date = new Date(date);
    else if(date.getTime() == 0)
        return '--/--';
    return date.formatDate(ss);
}

function jsprops(value) {
    var props = "";
    for (var p in value) {
        // 方法
        if (typeof(value[p]) == "function") {
            value[p]();
        } else {
            // p 为属性名称，obj[p]为对应属性的值
            props += p + "=" + value[p] + "\t";
        }
    }
    // 最后显示所有的属性
    $.messager.alert('Props', props);
}

//最大化弹出窗口
function openWindowMax(url) {
    var height = screen.availHeight - 40;
    var width = screen.availWidth - 15;
    var screenParam = "fullscreen=1,left=0,top=0,scrollbars,resizable=yes,toolbar=no',height=" + height + ",width=" + width;
    var winOpen = window.open("about:blank", "", screenParam);
    winOpen.location = url;
}
// 获取url参数 wangguhua
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}


function daysToDate(days) {
    var DAYOF_1970 = 719528;

    if (days < DAYOF_1970) {

        return new Date(0);
    } else {
        return new Date((days - DAYOF_1970) * 24 * 60 * 60 * 1000);
    }

}


function getTimeSign() {
    return timestamp = new Date().getTime();
}
/*
 * string 添加 startWith endWith方法
 * */
String.prototype.endWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substring(this.length - s.length) == s)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith = function (s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) == s)
        return true;
    else
        return false;
    return true;
}
/**
 * 字符串占位
 * @returns {String}
 */
String.prototype.format = function() {
    if(arguments.length == 0) return this;
    var param = arguments[0];
    var s = this;
    if(typeof(param) == 'object') {
        for(var key in param)
            s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
        return s;
    } else {
        for(var i = 0; i < arguments.length; i++)
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
        return s;
    }
}

/**
 * 数组处理
 * @param val
 * @returns {number}
 */
Array.prototype.indexOf = function(val,key) {
    for (var i = 0; i < this.length; i++) {
        if(key){
            if (this[i][key] == val[key]) return i;
        }else{
            if (this[i] == val) return i;
        }
    }
    return -1;
};

Array.prototype.remove = function(val,key) {
    var index = this.indexOf(val,key);
    if (index > -1) {
        this.splice(index, 1);
    }
};
Array.prototype.update = function(val,key){
    var index = this.indexOf(val,key);
    if (index > -1) {
        this[index] = val;
    }else
        this.push(val);
}


/*
 * 扫描未绑定的手环并赋值,对于easyui input 后方有textId_icon的赋值对错图标
 * */
function getUnbindCuffByAnyNo(no, textId, hideId, isEasyui) {

    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: contextPath + '/common/getCuffByAnyNo.do',
        dataType: 'json',
        async: false,
        data: JSON.stringify({"no": no}),
        success: function (data) {
            if (textId) {
                textId = idCheckWell(textId);
                if (data.id > 0 && data.status == 0) {
                    if (isEasyui) {
                        $(textId).textbox('setText', data.cuffNo);
                        setIcon(textId + "_icon", 'icon-ok');
                    } else {
                        $(textId).val(data.cuffNo);
                    }

                } else {
                    if (isEasyui) {
                        $(textId).textbox('setText', '');
                        setIcon(textId + "_icon", 'icon-no');
                        setTimeout(function () {
                            $.messager.alert('错误', '该卡片已被他人使用或无效!');
                        }, 10);

                    } else {
                        $(textId).val('');
                    }
                }
            }

            if (hideId) {
                hideId = idCheckWell(hideId);
                $(hideId).val(data.id);
            }
        },
        error: function () {
            $.messager.alert('错误', '未知错误!');
        }
    });
}


/*
 * 扫描绑定手环并赋值,对于easyui input 后方有textId_icon的赋值对错图标
 * */
function getBindCuffByAnyNo(no, textId, hideId, userId, userName, isEasyui) {

    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: contextPath + '/common/getCuffByAnyNo.do',
        dataType: 'json',
        async: false,
        data: JSON.stringify({"no": no}),
        success: function (data) {
            console.info(data);

            if (textId) {
                textId = idCheckWell(textId);
                if (data.id > 0 && data.status == 1) {
                    if (isEasyui) {
                        $(textId).textbox('setText', data.cuffNo);
                        setIcon(textId + "_icon", 'icon-ok');

                        if (userName) {
                            userName = idCheckWell(userName);
                            $(userName).textbox('setText', data.binderName);
                        }
                    } else {
                        $(textId).val(data.cuffNo);
                        if (userName) {
                            userName = idCheckWell(userName);
                            $(userName).val(data.binderName);
                        }
                    }

                } else {
                    if (isEasyui) {
                        $(textId).textbox('setText', '');
                        setIcon(textId + "_icon", 'icon-no');
                        setTimeout(function () {
                            $.messager.alert('错误', '该卡片未绑定人或无效');
                        }, 10);
                        if (userName) {
                            userName = idCheckWell(userName);
                            $(userName).textbox('setText', '');
                        }
                    } else {
                        $(textId).val('');
                        if (userName) {
                            userName = idCheckWell(userName);
                            $(userName).val('');
                        }
                    }
                }
            }

            if (hideId) {
                hideId = idCheckWell(hideId);
                $(hideId).val(data.id);
            }
            if (userId) {
                userId = idCheckWell(userId);
                if (data.userId) {
                    $(userId).val(data.userId);
                } else if (data.personId) {
                    $(userId).val(data.personId);
                }

            }

        },
        error: function () {
            $.messager.alert('错误', '未知错误!');
        }
    });
}


/*
 * 扫描手环并赋值,对于easyui input 后方有textId_icon的赋值对错图标
 * */
function getUserByCertificateNo(no, textId, hideId, isEasyui) {

    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: contextPath + '/common/getUserByCertificateNo.do',
        dataType: 'json',
        async: false,
        data: JSON.stringify({"no": no}),
        success: function (data) {
            if (textId) {
                textId = idCheckWell(textId);
                if (data.id > 0) {
                    if (isEasyui) {
                        $(textId).textbox('setText', data.realName);
                        setIcon(textId + "_icon", 'icon-ok');
                    } else {
                        $(textId).val(data.realName);
                    }

                } else {
                    if (isEasyui) {
                        $(textId).textbox('setText', '');
                        setIcon(textId + "_icon", 'icon-no');
                        //延迟0.01秒预防firefox不弹框(不明原因)
                        setTimeout(function () {
                            $.messager.alert('错误', '该警员编号无效!');
                        }, 10);

                    } else {
                        $(textId).val('');
                    }
                }
            }

            if (hideId) {
                hideId = idCheckWell(hideId);
                $(hideId).val(data.id);
            }
        },
        error: function () {
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function idCheckWell(id) {
    if (!id.startWith("#")) {
        id = "#" + id;
    }
    return id;
}
//仅改动小图标
function setIcon(id, icon) {
    id = idCheckWell(id);
    //l-btn-icon
    $(id).attr('class', icon);
}


function palyVoice(url) {
    $("#video").remove();
    $("body").append($('<video id="video" name="video" src="' + url + '" controls="controls" hidden="true"></video>'));
    document.getElementById('video').play();

    if (-1 != navigator.userAgent.indexOf("MSIE")) {
        //不是微软IE浏览器，则调用Flash来播放音乐

    }
    else {
        //是微软IE浏览器，则调用微软的Player对象来直接播放音乐

    }

}

/*
 * 运行视频工具
 * */
function runExe(path) {
    try {
        window.location = path;
    } catch (e) {
        try {
            var wsh = new ActiveXObject("WScript.Shell");
            wsh.Run(path);
        } catch (x) {
            $.messager.alert("提示", "未注册或未正确安装客户端程序！" + e);
        }
    }
}

//手环校验
function checkRingNo(icNo,type){
    var returnData ={};
    var cuff ={};
    cuff["icNo"]=icNo;
    cuff["type"]=type;
    var jsonrtinfo = JSON.stringify(cuff);
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : contextPath+'/zhfz/bacs/cuff/getCuffNoByIcNoAndType.do',
        data : jsonrtinfo,
        async:false,
        dataType : 'json',
        success : function(data){
            if(data.error){
                returnData['isError'] = true;
                returnData['title'] = data.title;
                returnData['content'] = data.content;
            }else{
                returnData['isError'] = false;
                returnData['callbackData'] = data.callbackData;
                returnData['title'] = data.title;
                returnData['content'] = data.content;
            }
        },
        error : function(data){
            returnData['isError'] = true;
            returnData['title'] = data.title;
            returnData['content'] = data.content + data.callbackData;
        }
    });
    return returnData;
}

// 图片找不到，默认一张图片
function imgNotFind(url){
    var img = event.srcElement;
    url = url || contextPath + '/static/login-8.png';
    if(img.src == url){
        img.onerror = null; //控制不要一直跳动
        return null;
    }else{
        img.src= url;
    }
}





//大于等于0的整数校验
function checkPositiveInteger(value){
    if(value != null && value != '' && value != 0){
        return /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/.test(value);
    }else{
        return true;
    }
}