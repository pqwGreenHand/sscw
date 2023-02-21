<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <title>阅读Word示例</title>
    <script language=javascript>
        var index = window.location.href.indexOf("url");
        var strOpenUrl = window.location.href.substring(index+4,window.location.href.length);
        // alert(strOpenUrl)
        function WebOpen()
        {
            obj = document.getElementById('WebOffice');//获得控件对象
            if (obj)setTimeout('openfile()',3000);//等待控件初始化完毕，时间可以根据网络速度设定。
        }
        function openfile()
        {
            var type = "Word.Document"
            if(window.location.href.indexOf('?pdf') > -1)
                type = 'pdf';
            document.getElementById('WebOffice').Titlebar=false;
            document.getElementById('WebOffice').Menubar=false;
            // document.getElementById('WebOffice').Toolbars=false;
            document.getElementById('WebOffice').Open(strOpenUrl,true,type,"","");
            document.getElementById('WebOffice').ProtectDoc(1,2,'123');
        }
        function SmartOpen()
        {
            location.href=strSmartUrl;
        }
    </script>
</head>
<body topmargin=0 leftmargin=0 onload="javascript:WebOpen();">
<div style="text-align:left;font-size:15px;">
    <%--<script language=javascript src="cssjs/webofficetip.js" charset="utf-8"></script>--%>
    <%--<a href="javascript:SmartOpen();">智能窗口链接</a>--%>
</div>
<%--<script language=javascript src="webofficetip.js" charset="utf-8"></script>--%>
<script language=javascript src="webofficeocx.js" charset="utf-8"></script>


</body></html>
