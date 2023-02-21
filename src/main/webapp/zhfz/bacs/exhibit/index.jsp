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
$('#Select_place').show();
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
/*#Select_place{ width:380px; height:294px;background: url(image/sx_bk.png) no-repeat; position:absolute; top:35%; left:35%;}
#Select_place p{ text-align:center; font-size:20px; color:#FFF; font-family:"幼圆";}
#Select_place img{ position:absolute; top:100px; position:absolute; left:80%;}
.pls{position:absolute; top:25%; left:23%;width:200px;}*/
</style>
</head>
<body class="easyui-layout" style="width: 100%; height: 100%;">
<div id="Select_place" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box"  style="width: 600px;height: 300px;">
		<div class="popup-inner">
			<div class="title">
				<div><i style="background-image: url(../policeEntrance/static/popup-2.png);"></i><span>请选择办案场所</span></div>
				<a name="noButton" class="close" onclick="closeMpopupOut()"></a>
			</div>
			<div id="stepDiv3" class="bd">
				<form id="addOutPoliceForm" method="post">
					<table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 129px;margin-top: 75px;">
						<tr>
							<td><label for="txtname">办案场所：</label></td>
							<td>
								<input id="area" name="area" class="easyui-combobox" style="width: 200px" >
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="edges">
			</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
		</div>
	</div>
</div>
<%--<div id="Select_place">
	<p>温馨提示：请选择办案场所</p>
    <img src="image/sx_ren.png">
  
    <div class="pls">
	   办案场所:<input id="area" name="area" class="easyui-combobox" style="width: 200px" >
	</div>
</div>--%>
</body>
</html>