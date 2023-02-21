$(function(){
	var content = getUrlParam('content');

	document.getElementById("myIFramePrint").src = contextPath+"/officefile/"+content;
	
	
	

	
})
function docPrint(){
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'打印中...'
	    });
	 var iframe = document.getElementById("myIFramePrint");
     iframe.contentWindow.focus();
     iframe.contentWindow.print();
     $.messager.progress('close');
}
function downloadFile(){
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'下载中...'
	    });
	document.getElementById("lawdocInfoinframe").submit();
    $.messager.progress('close');	
}
function docClose(){
//	alert("==准备关闭==")
	window.close();
}