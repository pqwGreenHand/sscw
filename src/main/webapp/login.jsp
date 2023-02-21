<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
    String tStrSN = request.getHeader("serialnumber");//用户证书序列号
    if (tStrSN == null) {
        tStrSN = "";
    }
    String tStrDN = request.getHeader("dnname"); //用户证书主题
    if (tStrDN == null) {
        tStrDN = "";
    } else {
        tStrDN = new String(tStrDN.getBytes("ISO8859-1"), "GB2312"); //由于DN中可能存在中文,所以对DN进行转码
    }
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>登录</title>
    <link rel="stylesheet" href="./js/new_common/css/style2.css"/>
    <link rel="stylesheet" type="text/css" href="./css/common.css">
    <link rel="stylesheet" type="text/css" href="./css/main2.css">
    <link rel="stylesheet" type="text/css" href="./js/jquery-easyui-1.7.0/themes/bf/easyui2.css"/>
    <script type="text/javascript" src="./js/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="./js/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="./js/jquery.cookie.js"></script>
    <script type="text/javascript" src="./js/json2.js"></script>
    <script type="text/javascript" src="js/extraFunction.js"></script>
    <script type="text/javascript" src="./js/common3.js"></script>
    <script type="text/javascript">
        //定义serializeObject方法，序列化表单
        $.fn.serializeObject = function () {
            var o = {};
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
        $(function() {

               window.location.href='newpage/login.jsp' ;

            //防止页面后退
            history.pushState(null, null, document.URL);
            window.addEventListener('popstate', function () {
                history.pushState(null, null, document.URL);
            });
        })

        /**
         *
        $(function () {

            //获取地址中的用户名和密码
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = new Object();
            if (url.indexOf("remark") > -1) {

            } else {
                if (url.indexOf("?") != -1) {
                    var str = url.substr(1);
                    strs = str.split("&");
                    for (var i = 0; i < strs.length; i++) {
                        theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
                    }
                    console.log(theRequest);
                    $('#username')[0].value = theRequest['username'];
                    $('#password')[0].value = theRequest['password'];
                    $('#submit').prop('disabled', true);
                    $('#userNameErrText').html('');
                    $('#passwordErrText').html('');
                    $('#submit').prop('disabled', true);
                    $('#userNameErrText').html('');
                    $('#passwordErrText').html('');
                    var username = $('#username').val();
                    var password = $('#password').val();
                    if (!username) {
                        $('#userNameErrText').html('请输入用户名!');
                        $('#submit').prop('disabled', false);
                        return;
                    }
                    if (!password) {
                        $('#passwordErrText').html('请输入密码!');
                        $('#submit').prop('disabled', false);
                        return;
                    }

                    var elemLogininfo = $('#login_form');
                    var longinfo = elemLogininfo.serializeObject();
                    alert(longinfo)
                    var jsonlonginfo = JSON.stringify(longinfo);
                    alert(jsonlonginfo)
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        url: 'login.do',
                        data: jsonlonginfo,
                        dataType: 'json',
                        success: function (data) {
                            if (data.error == false) {
                                if ($('#rememberme').attr('checked')) {
                                    $.cookie('interrogate.username', username);
                                    $.cookie('interrogate.password', password);
                                } else {
                                    $.removeCookie('interrogate.username');
                                    $.removeCookie('interrogate.password');
                                }
                                location.href = 'home.html';

                            } else {
                                $('#userNameErrText').html('<font color="red" >' + data.content + '</font>');
                            }
                            $('#submit').prop('disabled', false);
                        },
                        error: function (data) {
                            $.messager.alert(data.title, data.content);
                            $('#submit').prop('disabled', false);
                        }
                    });
                }
            }


            $("#loginBoxHd li").click(function () {
                var index = $(this).index();
                $(this).parents("ul").children("li").removeClass("active");
                $(this).addClass("active");
                $("#loginBoxBd .part").removeClass("active");
                $("#loginBoxBd .part").eq(index).addClass("active");
            })

            $('#username')[0].focus();
            if ($.cookie('interrogate.username') && $.cookie('interrogate.password')) {
                $('#rememberme').attr("checked", true);
                $('#username')[0].value = $.cookie('interrogate.username');
                $('#password')[0].value = $.cookie('interrogate.password');
            }
            $('#login_form').bind('keyup', function (event) {
                if (event.keyCode == 13) {
                    login();
                }
            });
            initPlugin();

            var tStrDN = "<%=tStrDN%>";//用户证书序列号,截取获取身份证号
            if (tStrDN == null || tStrDN == "") {
                tStrDN = "";
            } else if (location.search.indexOf("?") != -1) {

            } else {
                var a = tStrDN.split(",");
                var cno = a[0].substring(a[0].length - 18, a[0].length);
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: './common/getUser.do?political=' + cno,
                    data: {},
                    dataType: 'json',
                    success: function (data) {
                        if (data != null) {
                            $('#username')[0].value = data.loginName;
                            $('#password')[0].value = data.password;
                            login();
                        } else {
                            $.messager.alert("提示", "系统未绑定证书编号");
                        }
                    },
                    error: function (data) {
                        $.messager.alert(data.title, data.content);
                    }
                });
            }

        });
         */
        function initPlugin() {
            $.ajax({
                url: "http://192.168.201.53:8089/zhfz-common-service/FileService/DownloadFile/Z:/v5_filepath/cj/plugins.txt",
                success: function (pluginsText) {
                    //通过ajax读取文本文件。
                    var pluginArr = pluginsText.split("\r\n");
                    var pluginHtml = '<tr><th>插件名称</th><th>插件描述</th><th>操作</th></tr>';
                    for (var index in pluginArr) {
                        if (index == 0) continue;
                        var paluginInfo = pluginArr[index];
                        var tempArr = paluginInfo.split('@');
                        if (tempArr.length > 2) {
                            var pluginName = tempArr[0].trim();
                            var pluginDesc = tempArr[1].trim();
                            var pluginFileName = tempArr[2].trim();
                            pluginHtml += '<tr><td><div class="fitem"><label>' + pluginName + '</label></div></td>' +
                                '<td><div class="fitem">' + pluginDesc + '</div></td> ' +
                                '<td><div class="fitem"><label></label><a href="http://192.168.201.53:8089/zhfz-common-service/FileService/DownloadFile/Z:/v5_filepath/cj/' + pluginFileName + '" ' +
                                'style="text-decoration:underline;color: white">点击下载</a></div></td> </tr>';
                        }
                    }
                    $("#pluginTable").html(pluginHtml);
                }
            });
        }

        function toLogin() {
            // $.get("common/checkIP.do", function (data) {
            //     if (data == true || data == 'true') {
                    login();
            //     } else {
            //         $.messager.alert('警告', '无效的客户端！！！');
            //     }
            // });
        }

        function login() {
            $('#submit').prop('disabled', true);
            $('#userNameErrText').html('');
            $('#passwordErrText').html('');
            var username = $('#username').val();
            var password = $('#password').val();
            if (!username) {
                $('#userNameErrText').html('请输入用户名!');
                $('#submit').prop('disabled', false);
                return;
            }
            if (!password) {
                $('#passwordErrText').html('请输入密码!');
                $('#submit').prop('disabled', false);
                return;
            }

            var elemLogininfo = $('#login_form');
            var longinfo = elemLogininfo.serializeObject();
            var jsonlonginfo = JSON.stringify(longinfo);
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: 'login.do',
                data: jsonlonginfo,
                dataType: 'json',
                success: function (data) {
                    if (data.error == false) {
                        if ($('#rememberme').attr('checked')) {
                            $.cookie('interrogate.username', username);
                            $.cookie('interrogate.password', password);
                        } else {
                            $.removeCookie('interrogate.username');
                            $.removeCookie('interrogate.password');
                        }
                        location.href = 'home.html';

                    } else {
                        $('#userNameErrText').html('<font color="red" >' + data.content + '</font>');
                    }
                    $('#submit').prop('disabled', false);
                },
                error: function (data) {
                    $.messager.alert(data.title, data.content);
                    $('#submit').prop('disabled', false);
                }
            });
        }

        function downflash() {
            showDialog('#plugin_download_dialog', '');
        }

        function resetLogin() {
            $('#userNameErrText').html('');
            $('#passwordErrText').html('');
            $('#login_form').each(function () {
                this.reset();
            });
        }

        function cleanLoginCookie() {
            $.removeCookie('interrogate.username');
            $.removeCookie('interrogate.password');
            $('#rememberme').attr("checked", false);
        }

        function login2() {
            // $.messager.alert('提示', '暂未开通...');

            window.location.href ="https://14.27.3.165/zhfz";
        }
    </script>
</head>
<body>
<div class="page login">
    <div class="main">
        <div class="logo">
            <span>平谷分局涉案人员随身财物管理智能化系统</span>
        </div>
        <div class="m-box">
            <div class="hd">
                <div class="title"><span>欢迎登陆</span></div>
                <!--<ul id="loginBoxHd">-->
                <!--<li class="active">账号登录</li>-->
                <!--&lt;!&ndash;<li>人脸识别</li>&ndash;&gt;-->
                <!--&lt;!&ndash;<li class="last-child">指纹扫描</li>&ndash;&gt;-->
                <!--</ul>-->
            </div>
            <form id="login_form">
                <div class="bd" id="loginBoxBd">
                    <div class="p1 part active">
                        <div class="certificate">
                            <a onclick="login2()">数字证书登录></a>
                        </div>
                        <label class="item i1">
                            <i></i>
                            <input type="text" id="username" name="username" value="" placeholder="请输入账号">
                        </label>
                        <p id="userNameErrText" class="w-tips" style="display: block;"></p>
                        <label class="item i2">
                            <i></i>
                            <input id="password" name="password" type="password" value="" placeholder="请输入密码">
                        </label>
                        <p id="passwordErrText" class="w-tips" style="display: block;"></p>
                        <div class="login-btn">
                            <a id="submit" class="submit-btn" onclick="toLogin();">登&nbsp;录</a>
                            <a class="empty-btn" onclick="resetLogin()">清&nbsp;空</a>
                        </div>
                        <div class="other">
                            <label>
                                <input type="checkBox" id="rememberme" name="rememberme" value="0" id="0">
                                <span>记住密码</span>
                            </label>
                            <label>
                                <span style="cursor: pointer" onclick="cleanLoginCookie();">清除记忆</span>
                            </label>
                            <a href="#" onclick="downflash()">插件下载</a>
                        </div>
                    </div>
                    <div class="p2 part">
                        <div class="m-box">
                            <a></a>
                            <div class="edges">
                                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                            </div>
                        </div>
                    </div>
                    <div class="p3 part">
                        <div class="m-box">
                            <a></a>
                            <div class="edges">
                                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="edges">
                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
            </div>
        </div>
    </div>

    <div id="plugin_download_dialog" class="easyui-dialog" closable="true" closed="true">
        <div class="plugin-header">插件下载</div>
        <table id="pluginTable" border="1" style="width: 100%;margin-top: 20px;">
        </table>
        <div style="margin-top: 50px;text-align: center">
            <a href="javascript:void(0)" name="noButton" class="easyui-linkbutton" iconCls="icon-cancel"
               onclick="javascript:$('#plugin_download_dialog').dialog('close')" style="width:90px;padding-right:4px">
                <span style="padding-right: 20px;">关闭</span>
            </a>
        </div>
    </div>

    <div class="footer">
        Copyright © 2019上海致昕信息科技有限公司<br/>全国客服热线 400-878-6668
    </div>
    <img class="page-bg" src="static/page-1.png">
</div>
</body>