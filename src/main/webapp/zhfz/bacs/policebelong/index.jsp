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
<script type="text/javascript">
$(function(){

$('#area').combobox({    
    url:'../order/comboArea.do',    
    valueField:'id',    
    textField:'name',
    onLoadSuccess: function(data){
    	if(data.length==1){
        	$('#area').combobox('setValue', data[0].id);
    	} 
    }, 
    onChange:function(newValue,oldValue){
    	var areaid =$("#area").combobox('getValue');
		location.href='box.jsp?ssareaid='+areaid;
	}
});
});
</script>
<style type="text/css">
#Select_place{ width:380px; height:294px;background: url(image/sx_bk.png) no-repeat; position:absolute; top:35%; left:35%;}
#Select_place p{ text-align:center; font-size:20px; color:#FFF; font-family:"幼圆";}
#Select_place img{ position:absolute; top:100px; position:absolute; left:80%;}
.pls{position:absolute; top:25%; left:23%;width:200px;}
</style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<div id="Select_place">
	<p>温馨提示：请选择办案场所</p>
    <img src="image/sx_ren.png">
  
    <div class="pls">
	   办案场所:<input id="area" name="area" class="easyui-combobox" style="width: 200px" >
	</div>
</div>
</body>
</html>