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
            <table style="margin: 0 auto; padding: 10px;width: 90%">
                <tr>
                    <td>案件类型:</td>
                    <td><input id="ajlx" name="ajlx" class="easyui-combobox" data-options="required:true"/> <font
                            color="red" style="size: 15px">&nbsp;*</font></td>
                    <td>案由:</td>
                    <td><input id="ab" name="ab" class="easyui-combobox" data-options="required:true"/> <font
                            color="red" style="size: 15px">&nbsp;*</font></td>
                </tr>
                <tr>
                    <td>主办民警:</td>
                    <td><input id="zbmjCertificateNo" name="zbmjCertificateNo" class="easyui-textbox"
                               data-options="required:true,events:{blur: function(){checkUserId(this)}}"/> <font color="red" style="size: 15px">&nbsp;*</font>
                        <input id="zbmjId" name="zbmjId" type="hidden">
                        <input id="zbmjName" name="zbmjName" type="hidden">
                    </td>
                    <td>主办单位:</td>
                    <td><input id="zbdwId" name="zbdwId" class="easyui-combobox"/> <font color="red" style="size: 15px">&nbsp;*</font>
                    </td>
                </tr>
                <tr>
                    <td>案发时间:</td>
                    <td><input id="afsj" name="afsj" class="easyui-datetimebox" data-options="required:true"/> <font color="red" style="size: 15px">&nbsp;*</font>
                    </td>
                    <td>案件编号:</td>
                    <td><input id="ajbh" name="ajbh" class="easyui-textbox" /></td>
                </tr>
                <tr>
                    <td>简要案情:</td>
                    <td colspan="3"><input id="zyaq" name="zyaq" class="easyui-textbox"  data-options="required:true" style="width:635px;height:50px"/></td>
                </tr>
                <tr>
                    <td>案发地点:</td>
                    <td colspan="3"><input id="province" name="province" class="easyui-combobox" data-options="required:true"
                                           style="width:155px;"/>
                        <input  id="city" name="city" class="easyui-combobox" data-options="required:true"
                               style="width:155px;"/>
                        <input id="district" name="district" class="easyui-combobox" data-options="required:true"
                               style="width:155px;"/>
                        <input id="street" name="street" class="easyui-combobox" data-options="required:true"
                               style="width:155px;"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3"><input id="involvedAddress" name="involvedAddress" class="easyui-textbox" style="width:635px;height:50px"/></td>
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
        loadAjlx('', '');
        loadZbdw();
        loadPersonalConfig();
    });

    //加载主办单位
    function loadZbdw(zbdwId) {
        $("#zbdwId").combobox({
            url: '/zhfz/zhfz/common/combobox/listAllOrganizationNameCombobox.do',
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function () {
                if (zbdwId != null) {
                    $("#zbdwId").combobox("setValue", zbdwId);
                }
            }
        });
    }
    function loadAjlx(ajlx, ab) {
        $('#ajlx').combobox({
            url: "/zhfz/zhfz/common/code/listCodeByType.do?type=AJLX&tresh=" + new Date().getTime(),
            valueField: 'codeKey',
            textField: 'codeValue',
            editable: false,
            filter: function (q, row) {
                var opts = $(this).combobox('options');
                return row.codeValue.indexOf(q) > -1;//将从头位置匹配改为任意匹配
            },
            onLoadSuccess: function () {
                $('#ajlx').combobox('setValue', ajlx);
                if (ab != null && ab != '') {
                    loadAb(ajlx, ab);
                }
            },
            onChange: function (newValue, oldValue) {
                loadAb(newValue, "");
            }
        });
    }

    //加载案由
    function loadAb(natureValue, caseNatureId) {
        var natureValue2 = $("#ajlx").combobox("getText");
        if (natureValue2 != null && natureValue2 != '') {
            if (natureValue2 != natureValue) {
                natureValue = natureValue2;
            }
        }
        //加载犯罪类型
        $('#ab').combobox({
            url: '/zhfz/zhfz/bacs/combobox/listcrimetypebynature.do?nature=' + encodeURI(natureValue, "UTF-8") + '&tresh=' + new Date().getTime(),
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function (data) {
                $('#ab').combobox('setValue', caseNatureId);
            }
        })
    }

    //校验输入的警号
    function checkUserId(obj) {
        if (obj.value == null || obj.value == "") {
            return;
        }
        var userNo = obj.value;
        if (userNo.indexOf('(') >= 0) {
            userNo = userNo.substring(0, userNo.indexOf('('));
        }
        var userNoInfo = $('#userNoInfo').serializeObject();
        userNoInfo["userNo"] = userNo;
        userNoInfo['tresh'] = new Date().getTime();
        var jsonrtinfo = JSON.stringify(userNoInfo);
        jQuery.ajax({
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            url: '/zhfz/zhfz/bacs/order/searchUser.do',
            data: jsonrtinfo,
            success: function (data) {
                if (data != null && data.id != null) {
                    $("#zbdwId").combobox("setValue", data.organizationId);
                    $("#zbmjId").val(data.id);
                    $("#zbmjName").val(data.realName);
                    $("#zbmjCertificateNo").textbox('setValue',userNo + "(" + data.realName + ")");
                    return true;
                } else {
                    U.msg(userNo + '该警号不存在，请找系统管理员维护！');
                    $("#zbmjId").val("");
                    $("#zbmjName").val("");
                    return false;
                }
            },
            error: function (data) {
                U.msg(userNo + '该警号不存在，请找系统管理员维护！');
                $("#zbmjId").val("");
                $("#zbmjName").val("");
                return false;
            }
        });
    }

    //加载配置
    function loadPersonalConfig() {
        var arr;
        jQuery.ajax({
            type: 'get',
            async: false,
            dataType: 'json',
            url: '/zhfz/zhfz/bacs/personalconfig/listConfigDetailByAreaAndName.do?name=' + encodeURIComponent('案发地址绑定'),
            success: function (data) {
                if (data != null && data.error == false && data.callbackData != null) {

                    if (data.callbackData["regionids"] != "" && data.callbackData["regionids"] != null) {
                        arr = data.callbackData["regionids"].split('*');
                    }
                }
                BindAddress(arr);
            }
        });
    }

    //加载省市县村三级联动
    function BindAddress(arr) {
        var province = $('#province').combobox({
            url: '/zhfz/zhfz/common/region/list.do?level=1&trefresh=' + new Date().getTime(),
            valueField: 'code',
            textField: 'name',
            onChange: function (newValue, oldValue) {
                //城市
                jQuery.ajax({
                    type: 'get',
                    dataType: 'json',
                    url: '/zhfz/zhfz/common/region/list.do?level=2&parentId=' + newValue + "&trefresh=" + new Date().getTime(),
                    success: function (data) {
                        city.combobox("clear").combobox('loadData', data);
                        district.combobox("clear");
                        street.combobox("clear");
                    }
                });
            },
            onLoadSuccess: function () {
                if (arr != null && arr.length > 0 && arr[0] != "") {
                    $('#province').combobox('setValue', arr[0]);
                } else {
                    var provinceData = $("#province").combobox("getData");
                    if (provinceData != null && provinceData.length > 0) {
                        $('#province').combobox('setValue', '11');
                    } else {
                        $('#province').combobox('setText', "省");
                    }
                }
            }
        });
        var city = $('#city').combobox({
            valueField: 'code', //值字段
            textField: 'name', //显示的字段
            editable: true,
            onChange: function (newValue, oldValue) {
                console.log(newValue + "+++" + oldValue);
                if (newValue != "") {
                    //区县
                    $.get('/zhfz/zhfz/common/region/list.do', {
                        level: 3,
                        trefresh: new Date().getTime(),
                        parentId: newValue
                    }, function (data) {
                        district.combobox("clear").combobox('loadData', data);
                        street.combobox("clear");
                    }, 'json');
                }
            },
            onLoadSuccess: function () {
                if (arr != null && arr.length > 1 && arr[1] != "") {
                    $('#city').combobox('setValue', arr[1]);
                } else {
                    var cityData = $("#city").combobox("getData");
                    if (cityData != null && cityData.length > 0) {
                        $('#city').combobox('setValue', '1101');
                    } else {
                        $('#city').combobox('setText', "城市");
                    }
                }
            }
        });
        var district = $('#district').combobox({
            valueField: 'code', //值字段
            textField: 'name', //显示的字段
            editable: true,
            onChange: function (newValue, oldValue) {
                if (newValue != "") {
                    $.get('/zhfz/zhfz/common/region/list.do', {
                        level: 4,
                        trefresh: new Date().getTime(),
                        parentId: newValue
                    }, function (data) {
                        street.combobox("clear").combobox('loadData', data);
                    }, 'json');
                }
            },
            onLoadSuccess: function () {
                if (arr != null && arr.length > 2 && arr[2] != "") {
                    $('#district').combobox('setValue', arr[2]);
                } else {
                    var districtData = $("#district").combobox("getData");
                    if (districtData != null && districtData.length > 0) {
                        //平谷区
                        $('#district').combobox('setValue', '110117');
                    } else {
                        $('#district').combobox('setText', "区县");
                    }
                }
            }
        });
        var street = $('#street').combobox({
            valueField: 'code', //值字段
            textField: 'name', //显示的字段
            editable: true,
            onLoadSuccess: function () {
                if (arr != null && arr.length > 3 && arr[3] != "") {
                    $('#street').combobox('setValue', arr[3]);
                } else {
                    var streetData = $("#street").combobox("getData");
                    if (streetData != null && streetData.length > 0) {
                        $('#street').combobox('setValue', streetData[0].code);
                    } else {
                        $('#street').combobox('setText', "街道/乡镇");
                    }
                }
            }
        });
    }
</script>
</body>
</html>