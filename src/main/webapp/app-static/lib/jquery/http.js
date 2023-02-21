var http = {
    index:null
    ,flag : 1
    ,data :{}
    ,base : ''
    ,post : function (url,param,callback) {

        if(http.flag == 0)
            return null;
        if(typeof param == 'function'){
            callback = param;
            param = {};
        }
        http.load();
        $.ajax({
            type: "POST"
            ,url: http.base + url
            ,data:param
            ,dataType:'json'
            ,success:function (res) {
                http.close();
                if(typeof callback == 'function')
                    callback(res);
            }
            ,error:function () {
                objectApp.alert('提示','操作失败！');
                http.close();
            }
        });
    }
    ,ajax : function (option) {
        console.log(http.flag)
        if(http.flag == 0)
            return null;
        option.url = http.base + option.url;
        option.type = option.type || (typeof option.data == 'undefined' ? 'GET' : 'POST');
        option.data = option.data || {};
        option.dataType = option.dataType || 'json';
        option.async = option.async || false;
        option.error = function () {
            http.close();
        }
        var callback = option.success;
        option.success = function (res) {
            http.close();
            if(typeof callback == 'function')
                callback(res);
        }
        http.load();
        $.ajax(option);
    }
    ,upload: function (url,formData,callback) {
        if(http.flag == 0)
            return null;
        http.load();
        $.ajax({
            type: "POST"
            ,url:http.base + url
            ,data:formData
            ,contentType: false // 注意这里应设为false
            ,processData: false
            ,cache: false
            ,dataType:'json'
            ,success:function (res) {
                http.close();
                if(typeof callback == 'function')
                    callback(res);
            }
            ,error:function () {
                http.close();
            }
        });

    }
    ,get :function (url,param,callback) {
        if(http.flag == 0)
            return null;
        if(typeof param == 'function'){
            callback = param;
            param = {};
        }
        http.load();
        $.ajax({
            type: "GET"
            ,url:http.base + url
            ,data:param
            ,dataType:'json'
            ,success:function (res) {
                http.close();
                if(typeof callback == 'function')
                    callback(res);
            }
            ,error:function () {
                http.close();
            }
        });
    }
    ,load:function () {
        http.flag = 0;
        var $load = $('.loading');
        if($load.length == 1){
            $load.show();
        }else{
            $('body').append('<div class="loading"><img src="'+ contextPath +'/app-static/images/loading-01.gif" alt=""></div>');
        }
    }
    ,close : function () {
        http.flag = 1;
        $('.loading').hide();
    }
    ,msg : {
        success : function (msg) {
            layer.msg(msg || '操作成功！',{icon: 1});
        }
        ,error:function(msg){
            layer.alert(msg, {
                icon: 5,
                closeBtn: 0
            });
        }
        ,alert : function (msg) {
            layer.alert(msg, {
                icon: 1,
                closeBtn: 0
            });
        }
    }
    ,status:{
        "403":function (msg) {
            http.msg.error(msg);
            window.location.href = '/logout';
        }
        ,"900":function (msg) {
            http.msg.error(msg);
        }
        ,none:function (msg) {
            http.msg.error(msg);
        }
    }
    ,getCode : function (type,key) {
        key = key || '';
        if(typeof window.top.httpData[type] == "undefined") {
            console.log('log:请求后台数据啦')
            $.ajax({
                type: "POST"
                , url: http.base + 'dictionaryData/data/map'
                , data: {type: type, key: key}
                , dataType: 'json'
                , async: false
                , success: function (res) {
                    window.top.httpData[type] = res.map;
                }
            })
        };
        return window.top.httpData[type];
    }
}