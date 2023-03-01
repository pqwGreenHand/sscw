<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>物品轨迹</title>

    <%@ include file="../common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="height:100%;width: 100%">
        <input type="hidden" id="id" value='<%=request.getParameter("id")%>'></input>
        <iframe name="myiframe3" id="sjz" src="sjz.jsp" frameborder="0" scrolling="no"
                style="height:100%;width:100%"></iframe>
    </div>
</div>
<style scoped>
    #form tr {
        line-height: 35px;
    }

    #form tr input, select {
        width: 220px;
    }
</style>
<script>
    $(function () {
        var id = document.getElementById('id').value;
        $("#sjz").attr('src', 'sjz.jsp?id='+id);
    });

</script>
</body>
</html>