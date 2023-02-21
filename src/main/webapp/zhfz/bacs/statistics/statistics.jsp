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
    <%@ include file="../../common/common-head.jsp"%>
    <link rel="stylesheet" type="text/css"	href="css.css">
<style>

</style>
</head>
<body  >

<div id="Function_box">
	<div class="Functiona">
        <!-- <a href="javascript:open_win('../duty/rota.jsp');"  name="noButton"><img src="image/zb_a.png" class="icon-img" /><img src="image/zb_b.png" class="icon-hover" /><br/><font color="aqua" >值日表格</font></a> -->
        <a href="javascript:open_win('queryExport/queryExport.jsp');"  name="noButton"><img src="image/excel_a.png" class="icon-img" /><img src="image/zb_b.png" class="icon-hover" /><br/><font color="aqua" >自定义查询</font></a>
	</div>
    <div class="Functionb">
       <!--  <a href="javascript:open_win('serialandcase.html');" name="noButton"><img src="image/show1.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >办案展示</font></a> -->
        <a href="javascript:open_win('export/export.jsp');" name="noButton"><img src="image/excel_a.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >数据导出</font></a>
	</div>
	 <div class="Functionc">
        <!--  <a href="javascript:open_win('export/export.jsp');" name="noButton"><img src="image/excel_a.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >数据导出</font></a> -->
         <a href="javascript:open_win('account/dailyAccount.jsp');" name="noButton"><img src="image/excel_a.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >日台账</font></a>
	</div>
	  <div class="Functiond">
	  <a href="javascript:open_win('account/generalAccount.jsp');" name="noButton"><img src="image/excel_a.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >总台账</font></a>
          <!-- <a href="javascript:open_win('datastatistics.jsp');" name="noButton"><img src="image/sj_a.png" class="icon-img" /><img src="image/sj_b.png" class="icon-hover" /><br/><font color="aqua" >数据统计</font></a> -->
	</div>
	<div class="Functione">
       <!--  <a href="javascript:open_win('echartspie.jsp');" name="noButton"><img src="image/sj_a.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >办案区统计</font></a> -->
    </div>
   	<div class="Functionf">
        <!-- <a href="javascript:open_win('areahistory.jsp');" name="noButton"><img src="image/sj_a.png" class="icon-img" /><img src="../../image/index/sj_b.png" class="icon-hover" /><br/><font color="aqua" >办案区历史数据查询</font></a> -->
    </div> 
 
</div>

<script type="text/javascript">
    $(function(){
    	$.ajaxSettings.async = false;
    	$.get("../../common/getCssName.do", function(data){
    		if(data=='black'){
    		$("body").css("background","#333")
        	$("a").css("color","#fff")
    		}
    	});
    	
//        var HtmlBody='';
//        HtmlBody += '<div class="Function"><a href="#" onclick="openRemoteRecord()"><img src="/image/index/aqjg_a.png" class="icon-img" /><img src="/image/index/aqjg_b.png" class="icon-hover" /><br/>远程</a></div>';
//        $('#Function_box').append(HtmlBody);

        $('.Function a').hover(function(){
            $(this).addClass('hover');
        },function(){
            $(this).removeClass('hover');
        });
    });
    
    function open_win(url) 
	{
		window.open(url);
	}
    
</script>
</body>
</html>