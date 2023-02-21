<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8"
		 contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<%@ include file="../../common/common-head.jsp"%>
	<link rel="stylesheet" href="../security/css/swiper.min.css">
	<style>
		html, body {
			position: relative;
			height: 100%;
		}
		body {
			background: #eee;
			font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
			font-size: 14px;
			color:#000;
			margin: 0;
			padding: 0;
		}
		.swiper-container {
			width: 100%;
			height: 100%;
		}
		.swiper-slide {

			font-size: 18px;
			background: #fff;
			/* Center slide text vertically */
			display: -webkit-box;
			display: -ms-flexbox;
			display: -webkit-flex;
			display: flex;
			-webkit-box-pack: center;
			-ms-flex-pack: center;
			-webkit-justify-content: center;
			justify-content: center;
			-webkit-box-align: center;
			-ms-flex-align: center;
			-webkit-align-items: center;
			align-items: center;
		}
		.jzw-look-img {
			width: 240px;
			height: 215px;
		}
	</style>
</head>
<body>
<!-- Swiper -->
<div class="swiper-container">
	<div class="swiper-wrapper" style="background-color: #082040">
		<%--<div class="swiper-slide"><img src="../security/image/person1.png" style="height: 267px;width: 400px;"></div>
		<div class="swiper-slide"><img src="../security/image/person2.png" style="height: 267px;width: 400px;"></div>
		<div class="swiper-slide"><img src="../security/image/person3.png" style="height: 267px;width: 400px;"></div>
		<div class="swiper-slide"><img src="../security/image/person4.png" style="height: 267px;width: 400px;"></div>--%>
	</div>
	<!-- Add Pagination -->
	<div class="swiper-pagination"></div>
	<!-- Add Arrows -->
<%--	<div class="swiper-button-next"></div>
	<div class="swiper-button-prev"></div>--%>
</div>

<!-- Swiper JS -->
<script src="../security/js/swiper.min.js"></script>
<script src="lunbomarkes.js"></script>

<!-- Initialize Swiper -->
<script>
    var swiper = new Swiper('.swiper-container', {
        pagination: {
            el: '.swiper-pagination',
            type: 'custom',
            renderCustom: function (swiper, current, total) {
                return    "<font color='white'>体表标记照片</font>";
            }
        },
       /* navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },*/
    });
</script>
</body>
</html>