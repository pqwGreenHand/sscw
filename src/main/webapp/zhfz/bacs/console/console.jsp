<!DOCTYPE html>

<%@ page language="java" pageEncoding="UTF-8"
		 contentType="text/html; charset=UTF-8"%>
<html lang="zh-cn">
<head>
	<meta http-equiv="refresh" content="3000">
	<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title></title>
	<link rel="stylesheet" href="style.css"/>
	<%@ include file="../../common/common-head.jsp"%>
	<link href="./css2.css" rel="stylesheet" type="text/css">
	<link href="css/main.css" rel="stylesheet" type="text/css">
	<link href="css/common.css" rel="stylesheet" type="text/css">
	<script type="text/javascript"	src="../../../js/echarts/echarts-all.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/js/common.js"></script>
	<script type="text/javascript" src="console.js"></script>
	<script type="text/javascript" src="../../../js/new_common/js/iscroll5.js"></script>
	<script type="text/javascript">
        $(function(){
            var winH = $(window).outerHeight();
            // 自定义滚动条
            var myScroll  = new IScroll('#wrapper', {
                scrollbars: 'custom',
                mouseWheel: true,
                resizeScrollbars:true,
                interactiveScrollbars: true,
                shrinkScrollbars: 'scale'
            });

            // 设置aside的高度
            $(".main-side,#wrapper,.menu-con").css({
                "height": winH - 51 + "px"
            });
            $(window).resize(function(){
                winH = $(window).outerHeight();
                $(".main-side,#wrapper,.menu-con").css({
                    "height": winH - 51 + "px"
                });
                myScroll.refresh();
            });
        });
	</script>
	<style type="text/css">
		*{margin:0;padding:0;}
		ul,li,ol{list-style:none; }
		img,a img{border:none;margin:0;padding:0;}
		a{ text-decoration:none;color:#fff; cursor:pointer;}
		.clear{ clear:both;}
		/*process_box*/
		.process_box{margin: 0 auto; width:100%; padding-top:10px;}
		.process_top{ width:600px; height:60px; text-align:center; margin: 0 auto; padding-top:30px;}
		/*.process_box div{float:left; margin-left:20px;margin-top:10px;margin-bottom:10px;}*/
		.process_box div{margin-top:10px;margin-bottom:10px;}
		.chat_aa{ width:100px; text-align:center;}
		.process_a{ width:154px; height:80px; background:url(image/gzlc_a.png) no-repeat;font-size:18px;}
		.process_a p,.process_b p,.process_c p,.process_d p,.process_e p,.process_f p,.process_g p,.process_h p{ color:#fff;text-align:center; line-height:54px;margin-bottom: 0px;}
		.process_a span{ color:#1a88ba;display: block;margin-top: -5px;}
		.process_b{ width:154px; height:80px; background: url(image/gzlc_b.png) no-repeat;font-size:18px;}
		.process_b span{ color:#428e4d; display: block;margin-top: -5px;}
		.process_c{ width:154px; height:80px; background: url(image/gzlc_c.png) no-repeat;font-size:18px;}
		.process_c span{ color:#428e4d; display: block;margin-top: -5px;}
		.process_d{ width:154px; height:80px; background: url(image/gzlc_d.png) no-repeat;font-size:18px;}
		.process_d span{ color:#13b5b1; display: block;margin-top: -5px;}
		.process_e{ width:154px; height:80px; background: url(image/gzlc_e.png) no-repeat;font-size:18px;}
		.process_e span{ color:#00479d;display: block;margin-top: -5px; }
		.process_f{ width:154px; height:80px; background: url(image/gzlc_f.png) no-repeat;font-size:18px;}
		.process_f span{ color:#556fb5; display: block;margin-top: -5px;}
		.process_g{ width:154px; height:80px; background: url(image/gzlc_n1.png) no-repeat;font-size:18px;}
		.process_g span{ color:#556fb1; display: block;margin-top: -5px;}
		.process_h{ width:154px; height:80px; background: url(image/gzlc_n2.png) no-repeat;font-size:18px;}
		.process_h span{ color:#556fb2;display: block;margin-top: -5px; }
	</style>
</head>
<body >
<!--begin: main-->
<img class="page-bg" src="image/page-1.png">
<img class="page-bg" src="image/page-1.png">
<div id="echart" class="sky-bg" >
	<!--begin: main-content-->
	<div class="main-content" style="margin-top: 10px;">
		<!--begin: 办案中心-->
		<div id="div01" class="bgfff caselist" >
			<ul id="centers" class="clearfix">

			</ul>
		</div>
		<!--end: 办案中心-->
		<!--begin: projlist-->
		<div  class="proj-con" style="height: 400px;">
			<!--begin: proj-list03-->
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr >
					<td style="width: 33%;height:400px">
						<!--begin: projbjbox-->
						<div class="projbjbox" style="height:400px">
							<div class="m-title-1" style="background-color: rgba(16,49,106,.3)"><span>案件统计</span></div>
							<div id="echartsCase" class="projbox-con"  style="height: 350px;">

							</div>
						</div>
						<!--end: projbjbox-->
					</td>
					<td width="20"></td>
					<td style="width: 33%">
						<!--begin: projbjbox-->
						<div class="projbjbox" style="height:400px">
							<div class="m-title-1" style="background-color: rgba(16,49,106,.3)"><span>柜子使用率</span></div>
							<div id="echartsVolume" class="projbox-con" style="height: 350px">

							</div>
						</div>
						<!--end: projbjbox-->
					</td>
					<td width="20"></td>
					<td style="width: 33%">
						<!--begin: projbjbox-->
						<div class="projbjbox" style="height:400px">
							<div class="m-title-1" style="background-color: rgba(16,49,106,.3)"><span>物品统计</span></div>
							<div class="projbox-con" id="echartsSscw"  style="height: 350px">

							</div>
						</div>
						<!--end: projbjbox-->
					</td>
				</tr>
			</table>
		</div>
		<!--end: projlist-->
		<div class="proj-con" style="width:100%;height: 240px;padding-top: 20px">
			<!--begin: proj-list03-->
			<table cellpadding="0" cellspacing="0" width="100%" >
				<tr >
					<td style="width: 60%;height:240px">
						<!--begin: projbjbox-->
						<div class="m-box" style="height:350px;width: 100%;margin-top: 10px">
							<div class="process_top" style=""><h3><span id="title" style="font-size:18px;color: #ff0000"></span><span style="font-size:18px;color: #fff">物品流转情况</span></h3></div>
							<div  style="height:65%;">

								<div class="process_box">

									<table border="1" bordercolor="#a0c6e5" width="70%" style="border-collapse:collapse;margin-left: 15%;border: 1px;">
										<tr>
											<%--<th style="padding:10px;font-size:14px;color: #fff">已存物  <span id="orderNum">0</span>件</th>
											<th style="padding:10px;font-size:14px;color: #fff">已移交  <span id="rqNum">0</span>件</th>
											<th style="padding:10px;font-size:14px;color: #fff">已接收  <span id="cqNum">0</span>件</th>--%>
												<th style="padding:10px;font-size:14px;color: #fff">已存物  <span id="countSscw">0</span>件</th>
												<th style="padding:10px;font-size:14px;color: #fff">已移交  <span id="countSsyj">0</span>件</th>
												<th style="padding:10px;font-size:14px;color: #fff">已接收  <span id="countSsjs">0</span>件</th>
                                               <th style="padding:10px;font-size:14px;color: #fff">已拒绝  <span id="countSsjj">0</span>件</th>
										<tr>
											<td width="25%" align="center" valign="middle" style="border-color: #FFFFFF;">
												<div class="process_b">
													<p style="font-size:18px;">已存物</p>
													<span style="font-size:18px;"></span>
												</div>
											</td>
											<td width="25%" align="center" valign="middle" style="border-color: #FFFFFF">
												<div class="process_h">
													<p style="font-size:18px;">已移交</p>
													<span style="font-size:18px;"></span>
												</div>

											</td>
											<td width="25%" align="center" valign="middle" style="border-color: #FFFFFF">
												<div class="process_d">
													<p style="font-size:18px;">已接收</p>
													<span style="font-size:18px;"></span>
												</div>
											</td>
                                            <td width="25%" align="center" valign="middle" style="border-color: #FFFFFF">
                                                <div class="process_f">
                                                    <p style="font-size:18px;">已拒绝</p>
                                                    <span style="font-size:18px;"></span>
                                                </div>
                                    </td>
										</tr>
									</table>
								</div>
						</div>
						</div>
						<!--end: projbjbox-->
					</td>
				</tr>
			</table>
		</div>
	<!--end:main-content-->
</div>
<!--end:main-->
</div>
</body>
</html>
