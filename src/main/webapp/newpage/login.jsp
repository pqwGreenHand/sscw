<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">

    <title>平谷分局涉案人员随身财物智能化管理系统</title>
    <link rel="shortcut icon" href="static/images/loginsj222.png" type="image/x-icon" />

    <link href="static/css/login-style.css" rel="stylesheet" type="text/css"/>
    <script src="static/plugin/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="static/js/cloud.js" type="text/javascript"></script>

    <script src="static/plugin/layer/layer.js" type="text/javascript"></script>
    <script src="static/js/utils.js" type="text/javascript"></script>

    <script>
        if (window != top) {
            top.location.href = location.href;
        }
    </script>
</head>

<body style="background-color:#1c77ac; background-image:url(static/images/light.png);
        background-repeat:no-repeat; background-position:center top; overflow:hidden;">
<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>

<%--<div class="logintop">
    <span>平谷分局涉案人员随身财物智能化管理系统</span>
    <ul>
        &lt;%&ndash;<li><a href="#">回首页</a></li>&ndash;%&gt;
        <li><a href="javascript:void(0);">帮助</a></li>
        <li><a href="javascript:void(0);">关于</a></li>
    </ul>
</div>--%>

<div class="loginbody">
    <div class="logo">
        <span>平谷分局涉案人员随身财物智能化管理系统</span>
    </div>
    <%--<span class="systemlogo">平谷分局涉案人员随身财物智能化管理系统</span>--%>
    <div class="loginbox loginbox3">
        <ul>
            <li><input id="username" name="" type="text" class="loginuser" placeholder="账号"/></li>
            <li><input id="password" name="" type="password" class="loginpwd" placeholder="密码"/></li>
            <%--<li class="yzm">
                <span><input id="captcha" type="text" placeholder="验证码"/></span>
                <cite>
                    <img alt="验证码" src="${ctx}/static/images/kaptcha.jpg" title="点击更换" id="img_captcha"
                         name="img_captcha" onclick="javascript:refreshCaptcha();"
                         style="width: 100%; height: 100%;cursor: pointer;"/>
                </cite>
            </li>--%>
            <li>
                <input name="" type="button" class="loginbtn" value="登录" onclick="javascript:login();"/>
                <%--<label><input name="" type="checkbox" value="" checked="checked"/>记住密码</label>--%>
                <label><a href="javascript:void(0);">忘记密码？</a></label>
            </li>
        </ul>
    </div>
</div>
<div class="loginbm">Copyright ©2022
    <a href="#">平谷分局随身财物智能化管理系统</a>
</div>

<script language="javascript">
    $(function () {
        $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
        $(window).resize(function () {
            $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
        })
    });

    /**
     * 点击刷新验证码
     */
    function refreshCaptcha() {
        $('#img_captcha').attr('src', '${ctx}/static/images/kaptcha.jpg?t=' + Math.floor(Math.random() * 100));
    }

    /**
     * 登录系统
     */
    function login() {
        // window.location.href = "indexMain.jsp";
        if (validate()) {
            var username = $('#username').val().trim();
            var password = $('#password').val().trim();
            if (!username) {
                $('#userNameErrText').html('请输入用户名!');
                return;
            }
            if (!password) {
                $('#passwordErrText').html('请输入密码!');
                return;
            }
            var enterpriseinfo={"username":username,"password":password};
            var json_data = JSON.stringify(enterpriseinfo);
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: '../login.do',
                data: json_data,
                dataType: 'json',
                success: function (data) {
                    if (data.error == false) {
                        window.location.href = "indexMain.jsp";
                        <%--window.location.href='${ctx}/common/listMenuByPidMvw.do?pid=2';--%>
                        <%--window.open('${ctx}/common/listMenuByPidMvw.do?pid=2')--%>
                        // initMenuFun(2)

                    } else {
                        U.msg(data.content);
                    }
                },
                error: function (data) {
                    U.msg(data.content);
                }
            });
        }
    }

    function initMenuFun(pid) {
        <%--window.open('${ctx}/common/listMenuByPidMvw.do?pid=' + pid)--%>
        $.ajax({
            url: '${ctx}/common/listMenuByPid.do?pid=' + pid,
            dataType: 'json',
            async: false,
            success: function (data) {
                var authInfo = data['authInfo'];
                var childAuths = data['childAuth'];
                window.location.href = "indexMain.jsp?childAuths="+JSON.stringify(childAuths);
            },
            error: function () {
                $.messager.alert('错误', '网络连接已断开，请重新登录', 'warning', function () {
                    location.href = '${ctx}/newpage/login.jsp';
                });
            }
        });
    }

    function validate() {
        var username = $('#username').val().trim();
        var password = $('#password').val().trim();

        if (U.isEmpty(username)) {
            U.msg('请输入用户名');
            return false;
        }
        if (U.isEmpty(password) || password == '密码') {
            U.msg('请输入密码');
            return false;
        }
        return true;
    }

</script>
</body>
</html>
