<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script language=javascript>
        var id = <%=request.getParameter("id")%>; //word的文件名

        var varpath = decodeURI(window.location.href);
        var strRoot = varpath.substring(0, varpath.lastIndexOf('/') + 1);//取得打开路径
        var strOpenUrl = strRoot + "look.do?id=" + id + "&a=look.doc"; //取得打开路径和文件名
        function WebOpen() {
            obj = document.getElementById('WebOffice');//获得控件对象
            if (obj) setTimeout('openfile()', 1000);//等待控件初始化完毕，时间可以根据网络速度设定。
        }

        function openfile() {
            document.getElementById('WebOffice').Open(strOpenUrl,true,"Word.Document","","");
        }

    </script>
</head>
<body topmargin=0 leftmargin=0 onload="javascript:WebOpen();">
<%--<div style="text-align:left;font-size:15px;">--%>
    <%--<script language=javascript src="cssjs/webofficetip.js" charset="utf-8"></script>--%>
    <%--<a href="javascript:SmartOpen();">智能窗口链接</a>--%>
<%--</div>--%>
<script language=javascript src="cssjs/webofficeocx.js" charset="utf-8"></script>

</body>
</html>
