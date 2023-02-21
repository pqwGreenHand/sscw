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
    <title>随身物品首页</title>
    <%@ include file="../common.jsp" %>
</head>

<body style="background-color: #a5d3ec">
<div id="dlg">
    <form id="form">
        <table style="margin: 0 auto; padding: 120px;width: 90%">
            <tr>
                <td style="font-size: 18px">办案场所:</td>
                <td><input id="area" name="area" class="easyui-combobox" style="width: 250px;height:40px"/></td>
            </tr>
        </table>
    </form>
</div>
<style scoped>
    .panel-tool-close {
        display: none !important;
    }
</style>
<script>
    $(function () {
        add();
        $('#area').combobox({
            url: '/zhfz/zhfz/bacs/order/comboArea.do',
            valueField: 'id',
            textField: 'name',
            onLoadSuccess: function (data) {
                if (data.length == 1) {
                    $('#area').combobox('setValue', data[0].id);
                }
            },
            onChange: function (newValue, oldValue) {
                var areaid =$("#area").combobox('getValue');
                location.href='box.jsp?ssareaid='+areaid;
                // location.href = '../ajxx/ajxx.jsp';
            }
        });

    });


    function add() {
        dialog = $("#dlg").dialog({
            title: '选择场所',
            width: 640,
            height: 360,
            maximizable: false,
            modal: true
        });
    }

</script>
</body>
</html>