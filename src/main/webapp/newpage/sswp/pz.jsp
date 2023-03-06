<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>拍照2</title>
</head>
<%@ include file="../common.jsp" %>
<body>

<table class="main_form1">
    <tr>
        <td>
            <!-- 入区照片框 -->
            <video id="v" style="width: 300px;height: 300px"></video>
        </td>
        <td>
            <canvas id="canvas" style="display:none;"></canvas>
            <br/>
            <img src="" id="photo" alt="photo" style="float: left;">

        </td>
    </tr>
    <tr>
        <button id="button_take">拍照</button>
        <a href="javascript:void(0)" onclick="securityDownLoad()" data-options="iconCls:'icon-print'"
           class="easyui-linkbutton button-line-blue"
           style="width: 100px;height:40px;margin-left: 10px;">台&nbsp;账</a>
    </tr>
</table>



</body>

<script>
    !(function () {
        var mediaStreamTrack;
        // 老的浏览器可能根本没有实现 mediaDevices，所以我们可以先设置一个空的对象
        if (navigator.mediaDevices === undefined) {
            navigator.mediaDevices = {};
        }
        if (navigator.mediaDevices.getUserMedia === undefined) {
            navigator.mediaDevices.getUserMedia = function (constraints) {
                // 首先，如果有getUserMedia的话，就获得它
                var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

                // 一些浏览器根本没实现它 - 那么就返回一个error到promise的reject来保持一个统一的接口
                if (!getUserMedia) {
                    return Promise.reject(new Error('getUserMedia is not implemented in this browser'));
                }

                // 否则，为老的navigator.getUserMedia方法包裹一个Promise
                return new Promise(function (resolve, reject) {
                    getUserMedia.call(navigator, constraints, resolve, reject);
                });
            }
        }

        var constraints = {
            video: true,
            audio: false
        };
        var videoPlaying = false;
        var v = document.getElementById('v');
        var promise = navigator.mediaDevices.getUserMedia(constraints);
        promise.then(function (stream) {
            /* 使用这个stream stream */
            if ("srcObject" in v) {
                v.srcObject = stream;
            } else {
                // 防止再新的浏览器里使用它，应为它已经不再支持了
                v.src = window.URL.createObjectURL(stream);
            }
            v.onloadedmetadata = function (e) {
                v.play();
                mediaStreamTrack = stream;
                videoPlaying = true;
            };
        })
            .catch(function (err) {
                /* 处理error */
                console.error(err.name + ": " + err.message);
            });
        document.getElementById('button_take').addEventListener('click', function () {
            if (videoPlaying) {
                var canvas = document.getElementById('canvas');
                canvas.width = v.videoWidth;
                canvas.height = v.videoHeight;
                canvas.getContext('2d').drawImage(v, 0, 0);
                var data = canvas.toDataURL('image/webp');
                document.getElementById('photo').setAttribute('src', data);
                console.log(data)
                // stopVideoOnly(mediaStreamTrack);
                //   canvas.getContext("2d").drawImage(Img,0,0,width,height); //将图片绘制到canvas中
            }
        }, false);
    })();

    // stop both mic and camera
    function stopBothVideoAndAudio(stream) {
        stream.getTracks().forEach(function (track) {
            if (track.readyState == 'live') {
                track.stop();
            }
        });
    }

    // stop only camera
    function stopVideoOnly(stream) {
        stream.getTracks().forEach(function (track) {
            if (track.readyState == 'live' && track.kind === 'video') {
                track.stop();
            }
        });
    }

    // stop only mic
    function stopAudioOnly(stream) {
        stream.getTracks().forEach(function (track) {
            if (track.readyState == 'live' && track.kind === 'audio') {
                track.stop();
            }
        });
    }
</script>
</html>