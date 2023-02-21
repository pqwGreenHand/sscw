<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
  <%@ include file="../../common/common-head.jsp"%>
  <link rel="stylesheet" type="text/css" href="css/common.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css.css" />
  <script type="text/javascript" src="index.js"></script>
  <script type="text/javascript" src="inOtherPerson.js"></script>
  <script type="text/javascript" src="outOtherPerson.js"></script>
  <script type="text/javascript" src="js/jquery.webcam.js"></script>
  <script type="text/javascript" src="js/common.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/IdcardUtil.js"></script>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">

  <div class="page locker-home">
    <div class="main">
      <div class="box">
        <a id="rqButton" class="item i1 m-box" name="noButton" onclick="entrance()">
          <span>其他人入区</span>
          <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
          </div>
        </a>
        <a id="cqButton" class="item i2 m-box" name="noButton" onclick="out()">
          <span>其他人出区</span>
          <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
          </div>
        </a>
        <a id="rqlsButton" name="noButton" class="item i3 m-box" href="entranceList.jsp">
          <span>登记历史</span>
          <div class="edges">
            <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
          </div>
        </a>
      </div>
      <div class="content m-box" style="margin-top: -6px;">
        <div class="control-bar">
          <div class="m-input">
            <span>姓名：</span>
            <label>
              <input type="text" id="personName" name="personName" placeholder="请输入">
            </label>
          </div>
          <div class="m-input">
            <span>证件号码：</span>
            <label>
              <input type="text" id="certificateNo" name="certificateNo" placeholder="请输入">
            </label>
          </div>
          <div class="m-input">
            <span>办案场所：</span>
            <label>
              <input type="text" id="areaId" name="areaId" class="easyui-combobox">
            </label>
          </div>
          <button class="m-btn-1 blue" onclick="doSearch2()">查询</button>
          <button class="m-btn-1 blue" onclick="doClear2()">清除</button>
        </div>
        <div class="content-in">
          <div id="otherPersonItems" class="list" style="overflow:auto;"></div>          
          <div class="paging">
            <a name="noButton" class="btn i1" title="首页" onclick="toPage('first')"></a>
            <a name="noButton" class="btn i2" title="上一页" onclick="toPage('last')"></a>
            <a name="noButton" class="num"><span>第</span></a>
            <a name="noButton" class="num active"><span id="pageLable">1/1</span></a>
            <a name="noButton" class="num"><span>页</span></a>
            <a name="noButton" class="btn i3" title="下一页" onclick="toPage('next')"></a>
            <a name="noButton" class="btn i4" title="尾页" onclick="toPage('end')"></a>
            <span id="totalSpan" style="right: 55px; color: rgb(0, 168, 255); font-size: 16px; position: fixed; bottom: 10px;"></span>
          </div>
        </div>
        <div class="edges">
          <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
        </div>
      </div>
    </div>
	<div id="exit_dialog" class="easyui-dialog"
		style="width: 550px; height:400px; padding: 10px 20px" closed="true">
		<div data-options="region:'center',split:true" style="width: 100%; height: 100%;">
			<table id="exitDatagrid"></table>
		</div>
   </div>
    <div class="sky-bg" id="particles-js"></div>
    <img class="page-bg" src="../../../static/page-1.png">
  </div>  
  <%@include file="inOtherPersonDialog.jsp" %>
  <%-- <%@include file="outOtherPersonDialog.jsp" %> --%>
  <script src="../../../js/plugs/sky/particles.min.js"></script>
  <script src="../../../js/plugs/sky/app.js"></script>
  <script type="text/javascript">
    var pos = 0;
    var ctx = null;
    var cam = null;
    var image = null;

    var filter_on = false;
    var filter_id = 0;

    function changeFilter() {
        if (filter_on) {
            filter_id = (filter_id + 1) & 7;
        }
    }

    function toggleFilter(obj) {
        if (filter_on = !filter_on) {
            obj.parentNode.style.borderColor = "#c00";
        } else {
            obj.parentNode.style.borderColor = "#333";
        }
    }

    //入区摄像头
    jQuery("#webcam").webcam(
        {

            width : 240,
            height : 180,
            mode : "callback",
            swffile : "jscam_canvas_only.swf",

            onSave : function(data) {

                var col = data.split(";");
                var img = image;

                if (false == filter_on) {

                    for (var i = 0; i < 240; i++) {
                        var tmp = parseInt(col[i]);
                        img.data[pos + 0] = (tmp >> 16) & 0xff;
                        img.data[pos + 1] = (tmp >> 8) & 0xff;
                        img.data[pos + 2] = tmp & 0xff;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }

                } else {

                    var id = filter_id;
                    var r, g, b;
                    var r1 = Math.floor(Math.random() * 255);
                    var r2 = Math.floor(Math.random() * 255);
                    var r3 = Math.floor(Math.random() * 255);

                    for (var i = 0; i < 240; i++) {
                        var tmp = parseInt(col[i]);

                        /* Copied some xcolor methods here to be faster than calling all methods inside of xcolor and to not serve complete library with every req */

                        if (id == 0) {
                            r = (tmp >> 16) & 0xff;
                            g = 0xff;
                            b = 0xff;
                        } else if (id == 1) {
                            r = 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = 0xff;
                        } else if (id == 2) {
                            r = 0xff;
                            g = 0xff;
                            b = tmp & 0xff;
                        } else if (id == 3) {
                            r = 0xff ^ ((tmp >> 16) & 0xff);
                            g = 0xff ^ ((tmp >> 8) & 0xff);
                            b = 0xff ^ (tmp & 0xff);
                        } else if (id == 4) {

                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            var v = Math.min(Math.floor(.35 + 13 * (r
                                + g + b) / 60), 255);
                            r = v;
                            g = v;
                            b = v;
                        } else if (id == 5) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            if ((r += 32) < 0)
                                r = 0;
                            if ((g += 32) < 0)
                                g = 0;
                            if ((b += 32) < 0)
                                b = 0;
                        } else if (id == 6) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            if ((r -= 32) < 0)
                                r = 0;
                            if ((g -= 32) < 0)
                                g = 0;
                            if ((b -= 32) < 0)
                                b = 0;
                        } else if (id == 7) {
                            r = (tmp >> 16) & 0xff;
                            g = (tmp >> 8) & 0xff;
                            b = tmp & 0xff;
                            r = Math.floor(r / 255 * r1);
                            g = Math.floor(g / 255 * r2);
                            b = Math.floor(b / 255 * r3);
                        }

                        img.data[pos + 0] = r;
                        img.data[pos + 1] = g;
                        img.data[pos + 2] = b;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }
                }

                if (pos >= 0x2A300) {
                    ctx.putImageData(img, 0, 0);
                    pos = 0;
                }
            },

            onCapture : function() {
                webcam.save();

                jQuery("#flash").css("display", "block");
                jQuery("#flash").fadeOut(100, function() {
                    jQuery("#flash").css("opacity", 1);
                });
            },
        });
    
  
    

    function getPageSize() {

        var xScroll, yScroll;

        if (window.innerHeight && window.scrollMaxY) {
            xScroll = window.innerWidth + window.scrollMaxX;
            yScroll = window.innerHeight + window.scrollMaxY;
        } else if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac
            xScroll = document.body.scrollWidth;
            yScroll = document.body.scrollHeight;
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
            xScroll = document.body.offsetWidth;
            yScroll = document.body.offsetHeight;
        }

        var windowWidth, windowHeight;

        if (self.innerHeight) { // all except Explorer
            if (document.documentElement.clientWidth) {
                windowWidth = document.documentElement.clientWidth;
            } else {
                windowWidth = self.innerWidth;
            }
            windowHeight = self.innerHeight;
        } else if (document.documentElement
            && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
            windowWidth = document.documentElement.clientWidth;
            windowHeight = document.documentElement.clientHeight;
        } else if (document.body) { // other Explorers
            windowWidth = document.body.clientWidth;
            windowHeight = document.body.clientHeight;
        }

        // for small pages with total height less then height of the viewport
        if (yScroll < windowHeight) {
            pageHeight = windowHeight;
        } else {
            pageHeight = yScroll;
        }

        // for small pages with total width less then width of the viewport
        if (xScroll < windowWidth) {
            pageWidth = xScroll;
        } else {
            pageWidth = windowWidth;
        }

        return [ pageWidth, pageHeight ];
    }

    window.addEventListener("load", function() {
        jQuery("body").append("<div id=\"flash\"></div>");

        var canvas = document.getElementById("canvas");

        if (canvas.getContext) {
            ctx = document.getElementById("canvas").getContext("2d");
            ctx.clearRect(0, 0, 240, 180);

            var img = new Image();
            img.src = "image/logo.gif";
            img.onload = function() {
                ctx.drawImage(img, 129, 89);
            }
            image = ctx.getImageData(0, 0, 240, 180);
        }

        var pageSize = getPageSize();
        jQuery("#flash").css({
            height : pageSize[1] + "px"
        });

    }, false);
    
   

    window.addEventListener("resize", function() {

        var pageSize = getPageSize();
        jQuery("#flash").css({
            height : pageSize[1] + "px"
        });

    }, false);             
</script>
<script type="text/template" id="item">
<div class="item">
<div class="item-in m-box">
<div class="left">
  <img src="{url}" onerror="imgNotFind()">
</div>
<div class="right"><p><i>姓  名：</i>
<span>{personName}</span></p>
<p><i>证件号码：</i>
<span>{certificateNo}</span></p>
<p><i>入区时间：</i><span>{inTime}</span>
</p><p><i>办案场所：</i><span>{areaName}</span></p>
<div><button class="m-btn-1 blue" style="float: right;margin-right: 14px;" onclick="exit('{id}','{serialNo}','{uuid}','{areaId}','{inPhotoId}')">出区</button>
</div></div></div></div>

</script>
</body>
</html>