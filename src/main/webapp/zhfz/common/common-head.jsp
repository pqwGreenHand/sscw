<%@ page import="com.zhixin.zhfz.common.entity.SessionInfo" %>
<%
	String easyui_version = "jquery-easyui-1.7.0";
	String css="bf";
%>

<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/js/<%=easyui_version%>/themes/<%=css%>/easyui2.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/js/<%=easyui_version%>/themes/icon.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/js/<%=easyui_version%>/demo/demo.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/main.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/main2.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/button.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/gridbutton.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/popup.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/<%=easyui_version%>/jquery.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/<%=easyui_version%>/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/<%=easyui_version%>/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/extraFunction.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/common2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/photoEquipment.js"></script>
<script type="text/javascript">
<%
    SessionInfo info = ((SessionInfo)session.getAttribute("sessionInfo"));
    String orgPid = null;
    if(info.getCurrentOrg() != null && info.getCurrentOrg().getPid() != null)
        orgPid = info.getCurrentOrg().getPid();
%>
var servletPath="<%=request.getServletPath()%>";
var contextPath="<%=request.getContextPath()%>";
var pageButtonAuthority={};
var gridButtonAuthority={};
var navigationAuthority={};
var cuffMap={};
var labelMap={};
var sessionInfo = {org:{pid:"<%=orgPid%>"}};

$(function(){
	//加载按钮权限
	jQuery.ajax({
		async : false,
		method : "POST",
		url : contextPath+'/common/listButtonAuthority.do',
		success : function(data){
			var obj = eval('('+data+')');
			gridButtonAuthority = obj.grid;
			pageButtonAuthority = obj.page;
			navigationAuthority = obj.navigation;
		},
		error : function(data){

		}
	});

	//先全部隐藏
	$("a:not(.tabs-inner)").each(function() {
		var name = this.name;
		var classsName = this.className;
		if(name!='noButton' && classsName!='textbox-icon combo-arrow'){//textbox-icon combo-arrow(下拉框)
			$(this).hide();
		}
	});
	$("input[type=button]").each(function() {
		var name = this.name;
		if(name!='noButton'){
			$(this).hide();
		}
	});
	//显示有权限的
	$("a:not(.tabs-inner)").each(function(){
		var id = this.id;
		var index = $.inArray(id, pageButtonAuthority);
		if(index>-1){
			$(this).show();
		}
	});
	$("input[type=button]").each(function(){
		var id = this.id;
		var index = $.inArray(id, pageButtonAuthority);
		if(index>-1){
			$(this).show();
		}
	});
});

	function isGridButton(id){
		return $.inArray(id, gridButtonAuthority)!=-1;
	}

function isShowNavigation(id){
	return $.inArray(id, navigationAuthority)!=-1;
}

function updateQuickClass(arr){
	//清空
	$.each($("#content_vhart div button"), function(i, v){
		$(v).attr("style", "background: url(images/1_03.png) no-repeat center;");
	});
	//修改图标
	$.each(arr, function(index, value) {
		$("#"+value).attr("style", "background: url(images/2_03.png);");
	});
}


function onBigImage(e,obj){
	var pos = getMousePos(e);
	var bigger = '<div id="tooltip"><img src="'+obj.src +'"/></div>';
 	$("body").append(bigger);
 	var zIndex = 99;
 	if($(obj).closest('.window').css('z-index')){
 		zIndex = $(obj).closest('.window').css('z-index') +1;
 	}
 	$("#tooltip").css({
 		left:pos.x,
 		top:pos.y,
 		"z-index":zIndex
 	}).show(1000);
}

function mvBigImage(){
	$("#tooltip").remove();
}

function getMousePos(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var x = e.pageX || e.clientX + scrollX;
    var y = e.pageY || e.clientY + scrollY;
    return { 'x': x, 'y': y };
  }

//格式化列数值
function formatterColunmVal(colunmVal, size){
	var content = colunmVal;
	if(colunmVal && colunmVal.length>size){
		var tempVal = colunmVal.substring(0,size) + "...";
		content = '<span  title="' + colunmVal + '">' + tempVal + '</span>';
	}
	return content;
}
</script>