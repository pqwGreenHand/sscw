/**
 * Created by Administrator on 2017/4/26.
 */
var Validate = function (selector) {

    this.Vcode = null;

    var _form = $(selector || 'body');
    var regular = {
        phone: /^1[3|4|5|7|8]\d{9}$/
        ,
        idCode: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/
        ,
        email: /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/
        ,
        number: /^[0-9]*$/
        ,
        bankCode: /^(\d{16}|\d{19})$/
    };
    /**
     * 不带参数的方法
     * @type {[*]}
     */
    var fnNames = ['notNull', 'phone', 'idCode', 'email', 'bankCode', 'number', 'notZero','vcode'];
    /**
     * 带参数的方法
     * @type {[*]}
     */
    var fnName = {
        length: 'len'
        , min: 'min'
        , max: 'max'
        ,equalsTo: 'equalsTo'
        ,equals:'equals'
    };
    var fnNamesString = fnNames.toString();
    var Config = {
        ASYNC: false,
        BLUR: false,
        KEYUP: false
    };


    this.init = function (form) {
        utils.initDispose(form);
        var valids = _form.find('[data-validate]');
        if (Config.ASYNC) {
            return isAsync(valids);
        } else {
            return notAsync(valids);
        }
    };


    /**
     * 异步
     */
    var isAsync = function (valids) {
        var flags = true;
        var options = null;
        for (var i = 0; i < valids.length; i++) {
            options = valid(valids.eq(i));
            if (!options.flag) {
                flags = false;
            }
            utils.alert(options);
        }
        return flags;
    };

    /*
     * 同步
     */
    var notAsync = function (valids) {
        var options = null;
        for (var i = 0; i < valids.length; i++) {
            options = valid(valids.eq(i));
            if (!options.flag) {
                break;
            }
        }
        if (options != null) {
            utils.alert(options);
            return options.flag;
        } else {
            return true;
        }

    };


    /**
     * 单个元素校验
     */
    var valid = function (va) {
        var fns = utils.split(va);
        var options = {};
        options.name = va.attr('data-name') || '元素';
        options.msg = va.attr('data-msg');
        options.value = va.val() || va.html();
        options.othis = va;
        options.flag = true;
        for (var j = 0; j < fns.length; j++) {
            if (typeof fns[j] == 'string') { //如果为字符串只用调用一次
                options = methods[fns[j]](options);
            } else {//如果为数组，循环调用直到还回true或循环结束（注：在此要自定义msg）
                for (var k = 0; k < fns[j].length; k++) {
                    options.flag = true;
                    options = methods[fns[j][k]](options);
                    if (options.flag) {
                        break;
                    }
                }
            }
            if (!options.flag)
                break;
        }
        return options;
    };


    var methods = {
        notNull: function (options) {
            //console.log('notNull');
            if (utils.isNull(options.value)) {
                options.msg = options.msg || options.name + '不能为空！';
                options.flag = false;
            }
            return options;
        }
        , phone: function (options) {
            if (!utils.isNull(options.value) && !regular.phone.test(options.value)) {
                options.msg = options.msg || '手机号格式不正确！';
                options.flag = false;
            }
            return options;
        }
        , number: function (options) {
            if (!utils.isNull(options.value) && !regular.number.test(options.value)) {
                options.msg = options.msg || options.name + '只能输入数字！';
                options.flag = false;
            }
            return options;
        }
        , bankCode: function (options) {
            if (!utils.isNull(options.value) && !regular.bankCode.test(options.value)) {
                options.msg = options.msg || '银行卡号格式不正确！';
                options.flag = false;
            }
            return options;
        }
        , idCode: function (options) {
            if (!utils.isNull(options.value) && !regular.idCode.test(options.value)) {
                options.msg = options.msg || '身份证号格式不正确！';
                options.flag = false;
            }
            return options;
        }
        , email: function (options) {
            if (!utils.isNull(options.value) && !regular.email.test(options.value)) {
                options.msg = options.msg || '邮箱格式不正确！';
                options.flag = false;
            }
            return options;
        }
        , vcode : function (options) {
            if(validate.Vcode == null){
                utils.log('vcode:', '请先加载验证码插件！');
                return options;
            }
            if (!utils.isNull(options.value) && options.value.toUpperCase() != validate.Vcode.code) {
                validate.Vcode.init();
                options.msg = options.msg || '验证码不正确！';
                options.flag = false;
            }
            return options;
        }
        , notZero: function (options) {
            if (!utils.isNull(options.value) && parseInt(options.value) == 0) {
                options.msg = options.msg || options.name + '不能为零！';
                options.flag = false;
            }
            return options;
        }
        , len: function (options) {
            if (!utils.isNull(options.value)) {
                var len = utils.len(options.othis.attr('data-length'));
                var lent = options.value.length;
                if (lent < len[0] || lent > len[1]) {
                    options.msg = options.msg || options.name + '的长度在' + len[0] + '~' + len[1] + '位之间！';
                    options.flag = false;
                }
            }
            return options;
        }
        , min: function (options) {
            if (!utils.isNull(options.value) && !isNaN(options.value)) {
                var flouts = parseFloat(options.value);
                var minStr = $.trim(options.othis.attr('data-min'));
                if (minStr == '') {
                    utils.log('min:', 'data-min的值不能为空！');
                    return options;
                }
                if (isNaN(minStr)) {
                    utils.log('min:', 'data-min的值必须为数字！');
                    return options;
                }
                var min = parseFloat(minStr);
                if (flouts < min) {
                    options.msg = options.msg || options.name + '不能小于' + min + '！';
                    options.flag = false;
                }
            }
            return options;
        }
        , max: function (options) {
            if (!utils.isNull(options.value) && !isNaN(options.value)) {
                var flouts = parseFloat(options.value);
                var maxStr = $.trim(options.othis.attr('data-max'));
                if (maxStr == '') {
                    utils.log('max:', 'data-max的值不能为空！');
                    return options;
                }
                if (isNaN(maxStr)) {
                    utils.log('max:', 'data-max的值必须为数字！');
                    return options;
                }
                var max = parseFloat(maxStr);
                if (flouts > max) {
                    options.msg = options.msg || options.name + '不能大于' + max + '！';
                    options.flag = false;
                }
            }
            return options;
        }
        , equalsTo: function (options) {
            var clazz = options.othis.attr('data-equalsTo');
            if ($.trim(clazz) == '') {
                utils.log('equalsTo:', 'css选择器不能为null!');
                return options;
            }
            var $obj = $(clazz);
            if ($obj.length != 1) {
                utils.log('equalsTo:', 'css选择器选择不到对象或选择出多个对象!');
                return options;
            }
            var val = $obj.val() || $obj.html();
            if (!utils.isNull(options.value) && val != options.value) {
                options.flag = false;
                options.msg = options.msg || options.name + '两次不一致！';
            }
            return options;
        }
        ,equals : function (options) {
            //TODO 未完善
            var url = options.othis.attr('data-equals');
            $.ajax({
                url:url
                ,data:{email:options.value}
                ,async:false
                ,success : function (res) {
                    if(res.status != 0){
                        options.msg = res.msg;
                        options.flag = false;
                    }
                }
            });
            return options;
        }
    };

    var utils = {
        alert: function (options) {
            if (!options.flag)
                alert(options.msg);
        }
        , split: function (obj) {
            var strs = $.trim(obj.attr('data-methods') || '-').split(',');
            var s = null;
            var results = [];
            for (var i = 0; i < strs.length; i++) {
                if (strs[i].indexOf('|') > -1) {
                    s = strs[i].split('|');
                    var r = [];
                    for (var j = 0; j < s.length; j++) {
                        if ($.trim(s[j]) != '' && fnNamesString.indexOf(s[j]) > -1) {
                            r.push(s[j]);
                        }
                    }
                    results.push(r);
                } else {
                    if ($.trim(strs[i]) != '' && fnNamesString.indexOf(strs[i]) > -1) {
                        results.push(strs[i]);
                    }
                }
            }
            var data = null;
            for (var key in fnName) {
                data = obj.attr('data-' + key);
                if (data != undefined)
                    results.push(fnName[key]);
            }
            //alert(results.toString());
            return results;
        }
        , len: function (len) {
            var strs = len.split(',');
            var results = [];
            if (strs.length == 1) {
                results.push(parseInt(strs[0]));
                results.push(50);
                return results;
            } else {
                var min = parseInt(strs[0]);
                var max = parseInt(strs[1]);
                min = isNaN(min) ? 1 : min;
                max = isNaN(max) ? 40 : max;
                results.push(min);
                results.push(max);
                return results;
            }
        }
        , isNull: function (value) {
            if (value == null || value == '') {
                return true;
            } else {
                return false;
            }
        }
        , log: function () {
            for (var i = 1; i < arguments.length; i++) {
                console.log(arguments[i]);
            }
        }
        , initDispose: function (obj) {
            switch (typeof obj) {
                case 'string' :
                    _form = $(obj);
                    break;
                case  'boolean':
                    Config.ASYNC = obj;
                    break;
                case 'object':
                    if (obj.form)
                        _form = $(obj.form);
                    if (typeof obj.async != 'undefined')
                        Config.ASYNC = obj.async;
                    if (obj.alert)
                        utils.alert = obj.alert;
                    break;
                default :
                    break;
            }
        }
    };


    this.setRegular = function (options) {
        for (var key in options) {
            regular[key] = options[key];
        }
    };

    this.setAlert = function (fn) {
        utils.alert = fn;
    };

    this.setMethod = function (option, fn) {
        if (typeof option === 'string') {
            if (typeof fn === 'function') {
                if ($.trim(option) == '' || fnNamesString.indexOf(option) == -1)
                    fnNames.push(option);
                methods[option] = fn;
            } else {
                var strs = option.split(',');
                for (var i = 0; i < fn.length; i++) {
                    if ($.trim(strs[i]) == '' || fnNamesString.indexOf(strs[i]) == -1)
                        fnNames.push(strs[i]);
                    methods[strs[i]] = fn[i];
                }
            }
            fnNamesString = fnNames.toString();
        } else {
            if (typeof fn === 'function') {
                for (var key in option) {
                    fnName[key] = option[key];
                    methods[option[key]] = fn;
                }
            } else {
                var j = 0;
                for (var key in option) {
                    fnName[key] = option[key];
                    methods[option[key]] = fn[j];
                    j++;
                }
            }
        }

    };

    this.setVcode = function(obj){
        Vcode = obj;
    }

    /**
     * blur事件
     */
    var blur = function () {
        var valids = $('[data-validate*=blur]');
        valids.off('blur');
        valids.on('blur', function () {
            var _this = $(this);
            utils.alert(valid(_this));
        });
    };

    /**
     * kayup事件
     */
    var keyup = function () {
        var valids = $('[data-validate*=keyup]');
        valids.off('keyup');
        valids.on('keyup', function () {
            var _this = $(this);
            utils.alert(valid(_this));
        });
    };


    this.run = function () {
        blur();
        keyup();
    };

    this.run();


};

/**
 * 初始化对象
 */
$(function () {
    window.validate = new Validate();
});