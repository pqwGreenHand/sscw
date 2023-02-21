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
    <title>用户管理</title>

    <%@ include file="../common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="height:100%;width: 100%">
        <form id="form">
            <input type="hidden" id="personId" name="personId">
            <input type="hidden" id="areaId" name="areaId">
            <table style="margin: 0 auto; padding: 10px;width: 90%">
                <tr>
                    <td>嫌疑人姓名:</td>
                    <td><input id="person_name" name="name" class="easyui-textbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>证件类型:</td>
                    <td><input id="person_certificate_type" name="certificateTypeId" class="easyui-combobox"
                               data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>证件号码:</td>
                    <td><input id="person_certificate_no" name="certificateNo" class="easyui-textbox"
                               data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td>
                        <input id="person_sex" name="sex" class="easyui-combobox" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>民族:</td>
                    <td><input id="person_nation" name="nation" class="easyui-combobox" data-options="required:true">
                    </td>
                </tr>
                <tr>
                    <td>国籍:</td>
                    <td><input id="person_country" name="country" class="easyui-combobox" data-options="required:true">
                    </td>
                </tr>

            </table>
        </form>
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
        var rowData = datagridPerson.datagrid('getSelected');
        $('#form').form('load',
            {
                personId: rowData.id,
                name: rowData.name,
                sex: rowData.sex,
                certificateTypeId: rowData.certificateTypeId,
                certificateNo: rowData.certificateNo,
                country: rowData.country,
                nation: rowData.nation,
                areaId: rowData.areaId
            });
        loadNation();
        loadSex();
        codeCombo('person_country', 'GJID', "");
        $('#person_certificate_type').combobox({
            url: '/zhfz/zhfz/common/combobox/certificateTypes.do',
            valueField: 'id',
            textField: 'value',
            required: true,
            onSelect: function (rec) {
                if (rec.id == 7) {
                    jQuery.ajax({
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json',
                        url: "/zhfz/zhfz/common/combobox/listWMSCount.do",
                        success: function (data) {
                            var count = data + 1;
                            $('#person_name').textbox('setValue', "无名氏" + count);
                            $("#person_certificate_no").textbox('setValue', "XXXXX.....XXXXX" + count);
                        },
                        error: function (data) {
                            $.messager.alert('错误', '未知错误!');
                            $.messager.progress('close');
                        }
                    });
                }
            }, onChange: function (n, o) {
                setWuMingShi();
            }
        });
    });

    function setWuMingShi() {
        var person_certificate_type = $('#person_certificate_type').combobox('getValue');
        if (person_certificate_type == 7) {
            $('#person_name').textbox('setValue', '无名氏');
            $('#person_certificate_no').textbox('setValue', 'XXXXX.....XXXXX');
        }
    }

    function loadSex() {
        codeCombo('person_sex', 'XBID', '');
    }

    function codeCombo(id, type, selectedId) {
        $('#' + id).combobox({
            url: "/zhfz/zhfz/common/code/listCodeByType.do?type=" + type,
            valueField: 'codeKey',
            textField: 'codeValue',
            editable: false,
            filter: function (q, row) {
                var opts = $(this).combobox('options');
                return row.codeValue.indexOf(q) > -1;//将从头位置匹配改为任意匹配
            },
            onLoadSuccess: function () {
                if (selectedId) {
                    //延迟一下，对于某些浏览器能set成功
                    setTimeout(function () {
                        $('#' + id).combobox('setValue', selectedId);
                    }, 10);
                }

            }
        });
    }

    function loadNation(nation) {
        nation = nation ? nation : 1;
        jQuery.ajax({
            type: 'get',
            async: true,
            dataType: 'json',
            url: "/zhfz/zhfz/common/code/listCodeByType.do?type=MZID",
            success: function (data) {
                if (data != null) {
                    $('#person_nation').combobox({
                        valueField: 'codeKey',
                        textField: 'codeValue',
                        onLoadSuccess: function () {
                            $('#person_nation').combobox('setValue', nation);
                        }
                    });
                    $('#edit_nation').combobox({
                        valueField: 'codeKey',
                        textField: 'codeValue',
                        onLoadSuccess: function () {
                            $('#edit_nation').combobox('setValue', nation);
                        }
                    });
                    $('#person_nation').combobox('loadData', data);
                    $('#edit_nation').combobox('loadData', data);
                }
            },
            error: function (data) {
                console.log(data);
            }
        });
    }
</script>
</body>
</html>