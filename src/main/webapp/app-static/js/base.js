function _back(params) {
    objectApp.history.back(params);
}

function _href(url,params){
    objectApp.history.href(url,params);
}

function _home(){
    var list = JSON.parse(sessionStorage.histories);
    var l = [list[0],list[1]];
    sessionStorage.histories = JSON.stringify(l);
    window.location.href = contextPath + '/zhfz/bacsapp/main.jsp';
}

$.fn.serializeObject = function (param) {
    var o = $.extend(true,{},param || {});
    var a = this.serializeArray();
    var $radio = $('input[type=radio],input[type=checkbox]', this);
    var temp = {};
    $.each($radio, function () {
        if (!temp.hasOwnProperty(this.name)) {
            if ($("input[name='" + this.name + "']:checked").length == 0) {
                temp[this.name] = '';
                a.push({name: this.name, value: ''});
            }
        }
    });
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


/**
 * uuid
 * 8 character ID (base=2)
 * uuid(8, 2) "01001010"
 * 8 character ID (base=10)
 * uuid(8, 10) "47473046"
 * 8 character ID (base=16)
 * uuid(8, 16) "098F4D35"
 */
window.UUID = {
    uuid:[]
    ,randomUUID :function(len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
        } else {
            var r;
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random()*16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        this.uuid = uuid;
        return this;
    }
    ,toString : function (spile) {
        spile = spile || '';
        var uuid = this.uuid;
        for(var i = 4; i < uuid.length - 1; i+=5){
            uuid[i] = spile;
        }
        return this.uuid.join('');
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

Array.prototype.indexOfObject= function(val,key) {
    var obj = {};
    obj[key] = val;
    var index = this.indexOf(obj,key);
    if (index > -1) {
       return this[index];
    }else{
        return null;
    }
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

/**
 * 数字前不够位补零
 * @param val
 */
Number.prototype.prefixInt = function (val) {
    val = val || 5;
    return (Array(val).join('0') + this).slice(-val);;
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
        return (fileUtils.originalUrl(params.sysType,params.sysId) + '/DownloadFile/{uuid}/{fileType}/{fileName}' ).format(params) + '?time=' + new Date().getTime();
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
    ss = ss || 'yyyy-MM-dd hh:mm:ss';
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
            props += p + "=" + value[p] + "";
        }
    }
    // 最后显示所有的属性
    $.messager.alert('Props', props);
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

function hisNext(start,total,limit) {
    var s = (start + 1) * limit;
    if(s <= total){
        return true;
    }else if(s - total < limit){
        return true
    }
    return false;
}

/**
 * android api
 * @type {{cookie: android.cookie, openBluetooth: android.openBluetooth, api: *, takePhoto: android.takePhoto, isBluetoothOpen: (function(): *)}}
 */
const android = {
    api : function () {
        return androidAPI || null;
    }
    ,cookie:function (key,value) {
        if(value === undefined)
            return this.api().cookie(key);
        else
            this.api().cookie(key,value);
    }
    ,takePhoto : function () {
        this.api().takePhoto();
    }
    ,isBluetoothOpen:function () {
        return this.api().isBluetoothOpen();
    }
    ,openBluetooth:function () {
        this.api().openBluetooth();
    }
    ,connect:function (address) {
        this.api().connect(address);
    }
    ,isConnect:function () {
        return this.api().isConnect();
    }
}


var AppObject = function () {

    var histories = JSON.parse(sessionStorage.histories || '[]');
    
    this.method = function () {
        return deal;
    }
    var startX,startY,endX, endY,scrollTopStart,scrollTopEnd,scrollHeight,clientHeight,_element,_loadMsg,transitionHeight;


    this.draft = function () {
        _element = document.getElementById('container');
        _loadMsg = document.getElementById('load-msg');
        clientHeight = getClientHeight();
        document.addEventListener('touchstart', function (ev) {
            startX = ev.touches[0].pageX;
            startY = ev.touches[0].pageY;
            scrollHeight = getScrollHeight();
            scrollTopStart = getScrollTop();
            if(scrollTopStart == 0){
                _element.style.position = 'relative';
                _element.style.transition = 'transform 0s';
            }
        }, false);

        document.addEventListener('touchmove',function (ev) {
            if(scrollTopStart == 0){
                transitionHeight = ev.touches[0].pageY - startY;
                if(transitionHeight > 0 && transitionHeight < 40){
                    _loadMsg.innerHTML = '下拉刷新数据';
                    _element.style.transform = 'translateY('+transitionHeight+'px)';
                }
                if(transitionHeight > 35){
                    _loadMsg.innerHTML = '释放更新数据';
                }
            }
        },false)

        document.addEventListener('touchend', function (ev) {
            endX = ev.changedTouches[0].pageX;
            endY = ev.changedTouches[0].pageY;
            scrollTopEnd = getScrollTop();
            var direction = GetSlideDirection(startX, startY, endX, endY);
            switch (direction) {
                case 0:
                    if(scrollHeight - 1 <= scrollTopEnd + clientHeight <= scrollHeight + 1){
                        deal.upOut();
                    }else
                        deal.up();
                    break;
                case 1:
                    deal.right();
                    break;
                case 2:
                    if(scrollTopStart == 0){
                        _element.style.transition = 'transform 0.5s ease 0.5s';
                        _element.style.transform = 'translateY(0px)';
                        deal.downOut();
                    } else
                        deal.down();
                    break;
                case 3:
                    deal.left();
                    break;
                default:
                    break;
            }
        }, false);
    }

    function GetSlideDirection(startX, startY, endX, endY) {
        var dy = startY - endY,_dy = Math.abs(dy);
        var dx = endX - startX,_dx = Math.abs(dx);
        var result = 5;
        if(_dx / _dy > 5 && _dx > 10){
            if(dx > 0)
                result = 1;
            else
                result = 3;
        }else if(_dx / _dy < 0.2 && _dy > 10){
            if(dy > 0)
                result = 0;
            else
                result = 2;
        }
        return result;
    }

    var deal = {
        up : function () {
            console.log('up');
        },
        down : function () {
            console.log('down');
        }
        ,left : function () {
            console.log('left');
            //history.back(1);
        }
        ,right : function () {
            console.log('right');
            //history.back(-1);
        }
        ,downOut : function () {
            console.log('downOut');
            return null;
        }
        ,upOut:function () {
            console.log('upOut')
            return null;
        }
    }

    // 获取当前滚动条的位置
    function getScrollTop() {
        var scrollTop = 0;
        if (document.documentElement && document.documentElement.scrollTop) {
            scrollTop = document.documentElement.scrollTop;
        } else if (document.body) {
            scrollTop = document.body.scrollTop;
        }
        return scrollTop;
    }

    // 获取当前可视范围的高度
    function getClientHeight() {
        var clientHeight = 0;
        if (document.body.clientWidth && document.documentElement.clientHeight) {
            clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
        }
        else {
            clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
        }
        return clientHeight;
    }

    // 获取文档完整的高度
    function getScrollHeight() {
        return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
    }

    this.takePhoto = function () {
        android.takePhoto();
    }
    this.photoCallback = function () {
        this.image.append(arguments);
    }

    this.image = {
        img:null
        ,total:0
        ,base64List:[]
        ,photoList:null
        ,append:function (list) {
            if(this.photoList == null){
                this.photoList = $('#photo-list');
            }
            var total = list.length;
            this.total += total;
            this.dealWidth();
            var img = '';
            for(var i = 0; i < total; i++){
                var uuid = UUID.randomUUID(8).toString();
                img += '<img onclick="objectApp.image.look(this,\'show\')" data-uuid="' + uuid +'" src="data:image/png;base64,'+list[i]+'" />';
                var obj = {uuid:uuid,base64:list[i]};
                this.base64List.push(obj);
            }
            //alert(this.base64List);
            this.photoList.append(img);
        }
        ,look:function (_this,remove) {
            this.img = _this;
            remove = remove || 'hide';
            var $panel = createPanel();
            var temp = $('#model-temp-image').html();
            $panel.html(temp.format('图片浏览',_this.src,'关闭',remove,'移除'));
            $('#modal-image').modal();
        }
        ,remove:function () {
            var _this = $(this.img),obj={uuid:_this.attr('data-uuid'),base64:null};
            this.base64List.remove(obj,'uuid');
            _this.remove();
            this.total -= 1
            this.dealWidth();
        }
        ,dealWidth:function () {
            this.photoList.css('width',(120*this.total + 120 )+ 'px');
        }
        ,blob:function(urlData){
            //alert(urlData)
            var bytes=window.atob(urlData);        //去掉url的头，并转换为byte
            //处理异常,将ascii码小于0的转换为大于0
            var ab = new ArrayBuffer(bytes.length);
            var ia = new Uint8Array(ab);
            for (var i = 0; i < bytes.length; i++) {
                ia[i] = bytes.charCodeAt(i);
            }
            return new Blob( [ab] , {type : 'mimeString'});
        }
        ,createFormData:function (data) {
            var formData = new FormData();
            //alert(this.total);
            for(var i=0;i<this.total;i++){
                //alert('i'+ i);
                formData.append('files',this.blob(this.base64List[i].base64),UUID.randomUUID().toString()+".jpg");
            }
            data = data || {}
            $.each(data,function (k,v) {
                //alert("k:" + k);
                if(typeof v !== 'object')
                    formData.append(k,v);
            });
            return formData;
        }
    }



    this.code = {
        serialStatus:function (value) {
            return ['<span class="status-0">已入区</span>','<span class="status-1">已安检</span>','<span class="status-2">物品已暂存</span>','<span class="status-3">候问中</span>','<span class="status-4">候问结束</span>','<span class="status-5">信息已采集</span>','<span class="status-6">审讯中</span>','<span class="status-7">审讯结束</span>','<span class="status-8">物品已领取</span>','<span class="status-9">临时出区返回</span>','<span class="status-10">临时出区</span>','<span class="status-11">已出区</span>'][value];
        },
        orderStatus:function (value) {
            return ['<span class="status-0">已预约</span>','<span class="status-1">已推迟</span>','<span class="status-2">已到达</span>','<span class="status-3">已取消</span>','<span class="status-4">已过期</span>'][value];
        },
        noticeStatus:function (value) {
            return ['<span class="status-0">未读</span>','<span class="status-1">已读</span>'][value];
        },
        todoStatus:function (value) {
            return ['<span class="status-0">未处理</span>','<span class="status-1">已处理</span>'][value];
        },
        sexStatus:function (value) {
            return ['未知的性别','男','女','','','','','','','未说明的性别'][value];
        }
    }



    this.alert = function (title,msg) {
        this.scrollUnable();
        $('.modal-backdrop').remove();
        var $panel = createPanel();
        var temp = $('#model-temp-alert').html();
        $panel.html(temp.format(title,msg,'关闭'));
        $('#modal-alert').modal();
    }
    this.confirm = function (title,msg,func) {
        this.scrollUnable();
        var $panel = createPanel();
        var temp = $('#model-temp-confirm').html();
        $panel.html(temp.format(title,msg,'否','是'));
        $('#modal-confirm').modal();
        this.confirmDeal = func;
    }
    this.confirmDeal = function(){
    }

    this.imgNotFind = function(url){
        var img = event.srcElement;
        url = url || contextPath + '/static/login-8.png';
        if(img.src == url){
            img.onerror = null; //控制不要一直跳动
            return null;
        }else{
            img.src= url;
        }
    }

    function createPanel() {
        var $panel = $('#_temp');
        if($panel.length == 0){
            $('body').append('<div id="_temp"></div>');
            $panel = $('#_temp');
        }
        return $panel;
    }
    this.scrollUnable = function () {
        var tops = $(document).scrollTop();//当页面滚动时，把当前距离赋值给页面，这样保持页面滚动条不动
        if(tops > 0)
            $(document).one("scroll",function (){$(document).scrollTop(tops); })
    }

    this.history = {
        href:function (url,params) {
            url = contextPath + '/zhfz/bacsapp' + url;
            params = params || {};
            histories.push({url:url,params:params});
            sessionStorage.histories = JSON.stringify(histories);
            window.location.href = this.createUrl(url,params);
        }
        ,back:function (params) {
           this.go(-1,params);
        }
        ,go:function (index,params) {
            var obj = this.get(index);
            // console.log(obj);
            sessionStorage.histories = JSON.stringify(histories);
            window.location.href = this.createUrl(obj.url,params || obj.params);
        }
        ,get:function (index) {
            var length = histories.length + index - 1 || 0;
            // console.log(length);
            // console.log(histories);
            while (histories.length > length + 1)
                histories.pop();
            // console.log(histories)
            return histories[length];
        }
        ,createUrl:function (url,params) {
            var s = url;
            if(url.indexOf('?') == -1)
                s += '?th=1'
            $.each(params,function (k,v) {
                s += '&' + k + '=' + v;
            });
            return s;
        }
    }

    this.dropdown = {
        init:function (selector,selected,def) {
            var _this = $(selector);
            var options = parseParam(_this.data('options') || '',dropdownFun.defaultOptions);
            var data = eval(options.data);
            dropdownFun.init(_this,data,options);
            if(data.length == 0){
                var obj = {};
                obj[options.valueField] = '';
                obj[options.textField] = '无数据'
                data = [obj];
                dropdownFun.textField = false;
            }
            selected = selected || data[0];
            if(typeof selected != 'object'){
                var obj;
                if(def){
                    obj = data[0];
                }else{
                    obj = {};
                    obj[options.valueField] = '';
                    obj[options.textField] = '';
                }
                selected = data.indexOfObject(selected,options.valueField) || obj;
            }
            dropdownFun.setValue(selected[options.valueField]);
            if(!dropdownFun.options.vue)
                _this.val(dropdownFun.getTextField(selected));
            eval(dropdownFun.options.onchange + '(_this,selected)');
        }
        ,run:function (_this) {
            _this = $(_this);
            var selected = _this.val();
            objectApp.scrollUnable();
            var $panel = createPanel();
            var temp = $('#model-temp-dropdown').html();
            var options = parseParam(_this.data('options') || '',dropdownFun.defaultOptions);
            dropdownFun.init(_this,eval(options.data),options);
            $panel.html(temp.format(selected, dropdownFun.createOption(''),dropdownFun.options.type));
            $('#modal-dropdown').modal();
        }
        ,search:function (_this) {
            var selected = $.trim($(_this).val());
            $('#modal-dropdown .items').html(dropdownFun.createOption(selected));
        }
        ,selected:function (_this) {
            _this = $(_this);
            var selected = _this.text();
            var index = parseInt(_this.data('index'));
            if(!dropdownFun.options.vue) //如果设置vue为true 要重写callback方法
                dropdownFun.$this.val(selected);
            if(dropdownFun.options.type == 'select'){
                dropdownFun.setValue(_this.data('value'));
                eval(dropdownFun.options.onchange + '(dropdownFun.$this,dropdownFun.data[index])');
            }else{
                dropdownFun.setValue(selected);
                eval(dropdownFun.options.onchange + '(dropdownFun.$this,selected)');
            }
            $('#modal-dropdown .close').click();
        }
        ,form:function (formKey) {
            formKey = formKey || 'default';
            return dropdownFun.form[formKey];
        }
        ,ok:function () {
            var selected = $('#modal-dropdown .search').val();
            if(dropdownFun.options.vue) //如果设置vue为true 要重写callback方法
                eval(dropdownFun.options.onchange + '(dropdownFun.$this,selected)');
            else
                dropdownFun.$this.val(selected);
            $('#modal-dropdown .close').click();
        }
    }

    var dropdownFun = {
        selectOption:'<div class="item" data-index="{2}" onclick="objectApp.dropdown.selected(this)" data-value="{0}">{1}</div>'
        ,datalistOption:'<div class="item" data-index="{1}" onclick="objectApp.dropdown.selected(this)">{0}</div>'
        ,noOption:'<div class="item" style="text-align: center;">暂无数据</div>'
        ,textField: false
        ,form : {}
        ,data : null
        ,$this : null
        ,options : null
        ,defaultOptions : {valueField:'id',textField:'value',type:'select',form:'default',name:false,onchange:'dropdownFun.onchange',vue:false}
        ,createOption:function (selected) {
            if(this.options.type == 'select')
                return this.createSelectOption(selected);
            else
                return this.createDatalistOption(selected);
        }
        ,createSelectOption : function (selected) {
            var op = '',textField;
            $.each(this.data,function (i,item) {
                textField = dropdownFun.getTextField(item);
                if(textField.indexOf(selected) > -1){
                    op += dropdownFun.selectOption.format(item[dropdownFun.options.valueField],textField,i);
                }
            });
            if(op == '')
                op = this.noOption;
            return op;
        }
        ,createDatalistOption : function (selected) {
            var op = '',textField;
            $.each(this.data,function (i,item) {
                textField = dropdownFun.getTextField(item);
                if(textField.indexOf(selected) > -1){
                    op += dropdownFun.datalistOption.format(textField,i);
                }
            });
            if(op == '')
                op = this.noOption;
            return op;
        }
        ,init:function ($this,data,options) {
            this.$this = $this;
            this.data = data;
            this.options = options;
            if(options.textField.indexOf('-') > -1){
                this.textField = options.textField.split('-');
            }else
                this.textField = false;
        }
        ,setValue(value){
            if(this.options.name){
                this.form[this.options.form] = this.form[this.options.form] || {};
                this.form[this.options.form][this.options.name] = value;
            }
        }
        ,getTextField:function (data) {
            if(this.textField){
               return data[this.textField[0]] + '-' + data[this.textField[1]];
            }else{
                return data[this.options.textField];
            }
        }
        ,onchange:function ($this,selected) {
            //这个方法不用重写,自己写自己的方法,用eval调用
            //$this->触发弹窗的输入框
            //selected->被选中的数据
            //当vue设置成true时,要写一个这样的方法,自己设置值
        }
    }

}
window.objectApp = new AppObject();
// 加减
function numberDeal(clazz,type) {
    var inp = $(clazz),num = parseInt(inp.val());
    if(type == 0 && num > 0)
        num--
    else if(type == 1)
        num++
    inp.val(num);
}

