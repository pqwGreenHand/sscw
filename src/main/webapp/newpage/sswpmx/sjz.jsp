<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>jQuery swiper横向时间轴滑块</title>
    <!--swiper框架样式-->
    <link rel="stylesheet" type="text/css" href="statics/css/swiper-3.4.2.min.css"/>

    <!--主要样式-->
    <link rel="stylesheet" type="text/css" href="statics/css/style.css"/>

    <!--插件-->
    <script src="../../js/jquery.min.js"></script>
    <script src="statics/js/swiper-3.4.2.jquery.min.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
<input type="hidden" id="id" value='<%=request.getParameter("id")%>'></input>
<div class="product-section product-features">
    <%--<div class="product-title">全流程时间轴</div>--%>
    <div class="product-wrapper">
        <div class="product-body swiper-container">
            <ul class="features-slide swiper-wrapper" id="page">
                <li class="features-item swiper-slide dark">
                    <h3>暂无轨迹</h3>
                    <i></i> <a class="features-info">
                    <p class="features-info-i"></p>
                    <p class="features-info-s"> 暂无轨迹 </p>
                </a></li>
            </ul>
        </div>
        <div class="swiper-button-prev"></div>
        <div class="swiper-button-next swiper-button-disabled"></div>
    </div>
</div>

<!---调用方法-->
<script type="text/javascript">
    $(function () {
        var mySwiper = new Swiper(".swiper-container", {
            slidesPerView: 4, //默认是显示4个
            initialSlide: 0, //默认从第几个显示，999是为了让右边没有
            spaceBetween: 0, //间距
            speed: 1000,//速度
            prevButton: ".swiper-button-prev", //左右按钮
            nextButton: ".swiper-button-next"
        })
        var id = document.getElementById('id').value;
        if (id == 'undefined') {
            alert("请先选择物品")
        } else {
            $.ajax(
                {
                    type: 'post',
                    url: '/zhfz/zhfz/bacs/belong/getSjz.do',
                    data: 'id=' + id,
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        if (data.result.length > 0) {
                            $('#page').html('');
                            var xqHtmlBody = "";
                            for (var i = 0; i < data.result.length; i++) {
                                xqHtmlBody += '<li class="features-item swiper-slide">'
                                    + '<h3>' + data.result[i].startTime + '</h3>'
                                    + '<i onclick="test(\'' + data.result[i].url + '\')"></i> <a class="features-info"> <p class="features-info-i"></p>'
                                    + ' <p class="features-info-s"> ' + data.result[i].name + '</p></a></li>';

                            }

                            $('#page').html(xqHtmlBody);
                            var mySwiper = new Swiper(".swiper-container", {
                                slidesPerView: 3, //默认是显示4个
                                initialSlide: 0, //默认从第几个显示，999是为了让右边没有
                                spaceBetween: 0, //间距
                                speed: 1000,//速度
                                prevButton: ".swiper-button-prev", //左右按钮
                                nextButton: ".swiper-button-next"
                            })
                        }

                    }
                }
            )
        }

    })

    function test(url) {
        window.open(url, "_blank");
    }
</script>
</body>
</html>