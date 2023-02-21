var contextPath = "<%=request.getContextPath()%>";
var menuMap = {};
var pid;
var count0;
var count1;
var systemName;

var roleId;
$(function () {
    mHead();
    initUserName();
    pid = getUrlParam("pid");
    initMenuFun(getUrlParam("pid"));
    mFoldingMenu();
    tabMenu();
    // asInform();
    // initTZCount();

    setInterval(function () {

        // initTZCount();

    }, 60000);

    countOrderRequest();
    countInterrogateSerial();

    init();

    // showTimeoutRecode();
    setTimeout(function () {
        // showTimeoutRecode();
    }, 30 * 60 * 1000);

    // showBaqOutAlarm();
    setTimeout(function () {
        // showBaqOutAlarm();
    }, 10 * 1000);
});

function showBaqOutAlarm() {
    $.get("../../../common/listBaqOutAlarmData.do", function (data) {
        if (data) {
            data = eval('(' + data + ')');
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                $.messager.show({
                    title: obj.title,
                    msg: obj.content,
                    timeout: 5000
                });
                $.get("../../../common/updateBaqOutAlarm.do?id=" + obj.id);
            }

        }
    });
}

var tempObj = {};

function showTimeoutRecode() {
    tempObj = {};
    $.get("../../../common/listTimeoutRecode.do", function (data) {
        if (data) {
            data = eval('(' + data + ')');
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                tempObj[obj.id] = obj.uuid;
                var dateStr = parseDateStr(obj.in_time, obj.sx_date);
                var html = '<div style="font-size: 16px;text-align: center"><span>嫌疑人【' + obj.name + '】，' + dateStr + '</span>' +
                    '<br/><a class="easyui-linkbutton" href="#" style="color: yellow" onclick="addException(' + obj.id + ')">不再提醒</a></div>';
                var ms = $.messager.show({
                    width: '350px',
                    height: '180px',
                    title: '提示信息',
                    msg: html,
                    timeout: 0
                });
                tempObj[obj.id] = ms;
            }
        }
    });
}

function parseDateStr(inTime, sxDate) {
    if (sxDate) {
        var inTimeStr = formatDateTimeText(new Date(inTime));
        var sxTimeStr = formatDateTimeText(new Date(sxDate));
        return "入区时间为【" + inTimeStr + "】,首次审讯时间为【" + sxTimeStr + "】，属于超期审讯！";
    } else {
        return "已入区【" + timeFn(inTime) + "】，还未开启审讯！";
    }
}

// 计算两个时间差 dateBegin 开始时间
function timeFn(dateBegin) {
    //如果时间格式是正确的，那下面这一步转化时间格式就可以不用了
    var dateEnd = new Date();//获取当前时间
    var dateDiff = dateEnd.getTime() - dateBegin;//时间差的毫秒数
    var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数
    var leave1 = dateDiff % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000))//计算出小时数
    //计算相差分钟数
    var leave2 = leave1 % (3600 * 1000)    //计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000))//计算相差分钟数
    //计算相差秒数
    var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000)

    var leave4 = leave3 % (60 * 1000)      //计算分钟数后剩余的毫秒数
    var minseconds = Math.round(leave4 / 1000)
    var timeFn = dayDiff + "天 " + hours + "小时 " + minutes + " 分钟" + seconds + " 秒";
    return timeFn;
}

function addException(id) {
    $.get("../../../common/updateTimeoutRecode.do?id=" + id);
    $(tempObj[id]).window("close");
}

function initMarqueespan() {

    countOrderRequest();
    countInterrogateSerial();
    countWaitingRecord();
    countRecord();
    countotherentrance();
    countkyouttime();
    var a1 = $('#spandata1').val();
    var a = '<a onclick="openorderhtml();" style=" color: #40ff3f;">' + a1 + '</a>';
    var b1 = $('#spandata2').val();
    var b = '<a onclick="opensuspectentrancehtml();" style=" color: #40ff3f;">' + b1 + '</a>';
    var c1 = $('#spandata3').val();
    var c = '<a onclick="opensuspectentrancehtml();" style=" color: #40ff3f;">' + c1 + '</a>';
    var d1 = $('#spandata4').val();
    var d = '<a onclick="opensecurityhtml();" style=" color: #40ff3f;">' + d1 + '</a>';
    var e1 = $('#spandata5').val();
    var e = '<a onclick="opensuspectentrancehtml();" style=" color: #40ff3f;">' + e1 + '</a>';
    var f1 = $('#spandata6').val();
    var f = '<a onclick="openwaithtml();" style=" color: #40ff3f;">' + f1 + '</a>';
    var g1 = $('#spandata7').val();
    var g = '<a onclick="opentxhtml();" style=" color: #40ff3f;">' + g1 + '</a>';
    var h1 = $('#spandata8').val();
    var h = '<a onclick="openreceptionentrancehtml();" style=" color: #40ff3f;">' + h1 + '</a>';
    var i1 = $('#spandata9').val();
    var i = '<a onclick="openJyOuttime()" style=" color: #40ff3f;">' + i1 + '</a>';
    var colorstr = "";

    colorstr = "<font color ='red'> | </font>";
    var str = "<font color='red' size='3'>" + a + colorstr + b + colorstr + c + colorstr + d + colorstr + e + colorstr + f + colorstr + g + colorstr + h + colorstr + i + "</font>";

    document.getElementById("marqueespan").innerHTML = str;
}

function init() {

    // initMarqueespan()
    //
    // setInterval(function () {
    //
    //     initMarqueespan();
    //
    // }, 60000);
}

function doSearchLinkCase() {
    $('#datagrid').datagrid('load', {
        name: $('#name').textbox('getValue'),
        serialNo: $('#rqbh').textbox('getValue'),
        trefresh: new Date().getTime()
    });
}

function doClearLinkCase() {

    $('#name').textbox('setValue', '');
    $('#rqbh').textbox('setValue', '');
}

/*羁押超时人员信息*/
function openJyOuttime() {
    $("#editDialog").show();
    $('#datagrid').datagrid({
        // title:'嫌疑人入区',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载数据中...',
        method: 'get',
        url: '../../bacs/statistics/listJyOutTime.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'id',
        sortOrder: 'desc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[{
            title: 'ID',
            field: 'id',
            width: 80,
            sortable: true,
            hidden: true
        }]],
        columns: [[
            {
                field: 'name',
                title: '嫌疑人',
                width: 40,
                /*formatter : function(value, rec) {
                    //console.log(value);
                    /!*return '<font color="yellow">'
                        + formatterColunmVal(value, 5)
                        + '</font>';*!/
                }*/
            },
            {
                field: 'writtenTime',
                title: '手续开具时间',
                width: 95,
                formatter: function (value, rec) {
                    return valueToDate(value);
                }
            },
            {
                field: 'sex',
                title: '性别',
                width: 25,
                formatter: function (value, rec) {
                    if (value == 0) {
                        return "未知的性别";
                    }
                    if (value == 1) {
                        return "男";
                    }
                    if (value == 2) {
                        return "女";
                    }
                    if (value == 9) {
                        return "未说明的性别";
                    }
                    return "";
                }
            },
            {
                field: 'cuffNo',
                title: '手环编号',
                width: 95,
                hidden: true
            },
            {
                field: 'certificateNo',
                title: '证件号码',
                width: 100
            },
            {
                field: 'inUserRealName1',
                title: '民警',
                width: 30,

            },
            {
                field: 'organization',
                title: '办案单位',
                width: 60
            },
            {
                field: 'status',
                title: '状态',
                width: 55,
                formatter: function (value, rec) {
                    // 0已入区 1已安检 2物品已暂存 3候问开始 4候问结束 5信息已采集
                    // 6审讯开始 7审讯结束 8物品已领取 9临时出区返回 10临时出区
                    // 11已出区
                    if (value == 0) {
                        return '<font color="green">已入区</font>';
                    } else if (value == 1) {
                        return '<font color="yellow">已安检</font>';
                    } else if (value == 2) {
                        return '<font color="orange">物品已暂存</font>';
                    } else if (value == 3) {
                        return '<font color="yellow">候问开始</font>';
                    } else if (value == 4) {
                        return '<font color="orange">候问结束</font>';
                    } else if (value == 5) {
                        return '<font color="yellow">信息已采集</font>';
                    } else if (value == 6) {
                        return '<font color="orange">审讯开始</font>';
                    } else if (value == 7) {
                        return '<font color="yellow">审讯结束</font>';
                    } else if (value == 8) {
                        return '<font color="orange">物品已领取</font>';
                    } else if (value == 9) {
                        return '<font color="gray">临时出区返回</font>';
                    } else if (value == 10) {
                        return '<font color="orange">临时出区</font>';
                    } else if (value == 11) {
                        return '<font color="gray">已出区</font>';
                    }
                }
            },
            {
                field: 'serialNo',
                title: '入区编号',
                width: 100
            },
            /* {
                 field : 'outTime',
                 title : '出区时间',
                 width : 95,
                 formatter : function(value, rec) {
                     if (value != null && value != '') {
                         return valueToDate(value);
                     } else {
                         return "";
                     }
                 }
             },*/
            {
                field: 'caseType',
                title: '案件性质',
                width: 80,
                formatter: function (value, rec) {
                    if (value == 1) {
                        return "行政";
                    } else if (value == 2) {
                        return "刑事";
                    }
                }
            },
            /*{
                field : 'caseNature',
                title : '案由',
                width : 80,
                formatter : function(value, rec) {
                    if (value == null || value == "") {
                        return '无';
                    } else {
                       return 'caseNature';
                    }
                }
            },*/
            {
                field: 'confirmResult',
                title: '裁决结果',
                width: 45,
                formatter: function (value, rec) {
                    if (value != null && value != '') {
                        return value;
                    } else {
                        return '<font color="red">未裁决</font>';
                    }
                }
            },
            {
                field: 'delay',
                title: '超期时长',
                width: 65,
                formatter: function (value, row, index) {
                    return "<font color='red'>已超期" + (((new Date() - row.writtenTime) / 60 / 60 / 1000).toFixed(0) - 24) + "小时</font>";
                }
            },
            {
                field: 'interrogateAreaName',
                title: '办案场所',
                width: 65
            }
        ]],
        pagination: true,
        pageSize: 20,
        pageList: [20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#jzAjxxToolbar',
        onLoadSuccess: function (data) {
            console.log(data);
        },

    });
    var p = $('#datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            $('#datagrid').datagrid('load', {
                trefresh: new Date().getTime()
            });
        }
    });
    $('#fudong').remove();

}

//已预约人数
function countOrderRequest() {

    // $('#spandata1').val("已预约 : 10人  ");
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../../bacs/statistics/countOrderRequest.do',
        data: {},
        dataType: 'json',
        async: false,
        success: function (data) {

            $('#spandata1').val("已预约 : " + data.total + "人  ");
            //initMarqueespan()
        },
        error: function (data) {
            //$.messager.alert('错误', '未知错误!');
        }
    });
}

//入区  出区  安检。。
function countInterrogateSerial() {

    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../../bacs/statistics/countSerial.do',
        data: {},
        dataType: 'json',
        async: false,
        success: function (data) {

            $('#spandata2').val("已入区 : " + data.total_rq + "人 ");
            $('#spandata3').val("已出区 : " + data.total_cq + "人 ");
            $('#spandata4').val("安检/信采/存物 : " + data.total_els + "人 ");
            $('#spandata5').val("临时出区 : " + data.total_lscq + "人 ");
        },
        error: function (data) {
            //$.messager.alert('错误', '未知错误!');
        }
    });
}

//看押中
function countWaitingRecord() {
    // $('#spandata6').val("看押中 : "+ 12+"人 ");
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../../bacs/statistics/countWaitingRecord.do',
        data: {},
        async: false,
        dataType: 'json',
        success: function (data) {
            $('#spandata6').val("看押中 : " + data.total + "人 ");
        },
        error: function (data) {
            //$.messager.alert('错误', '未知错误!');
        }
    });
}

//审讯中
function countRecord() {
    //$('#spandata7').val("审讯中  : "+ 12 +"人 ");
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../../bacs/statistics/countRecord.do',
        data: {},
        dataType: 'json',
        async: false,
        success: function (data) {
            $('#spandata7').val("审讯中  : " + data.total + "人 ");
        },
        error: function (data) {
            //$.messager.alert('错误', '未知错误!');
        }
    });
}

//其他人员
function countotherentrance() {

    jQuery.ajax({
        type: 'POST',
        async: false,
        contentType: 'application/json',
        url: '../../bacs/statistics/countotherentrance.do',
        data: {},
        dataType: 'json',
        success: function (data) {
            $('#spandata8').val("其他人员   : " + data.total + "人 ");
        },
        error: function (data) {

        }
    });
}

//羁押超时人数
function countkyouttime() {
    // $('#spandata9').val("羁押超时   : "+ 13+"人 ");
    jQuery.ajax({
        type: 'POST',
        async: false,
        contentType: 'application/json',
        url: '../../bacs/statistics/countkyouttime.do',
        data: {},
        dataType: 'json',
        success: function (data) {
            $('#spandata9').val("羁押超时   : " + data.count + "人 ");
        },
        error: function (data) {
            //$.messager.alert('错误', '未知错误!');
        }
    });
}

function initUserName() {
    $.get("../../../common/getSessionInfo.do", function (data) {
        var sessionObj = eval('(' + data + ')');
        $("#userName").html(sessionObj.user.realName);


        $("#loginName").val(sessionObj.user.loginName);
        $("#realName").val(sessionObj.user.realName);
        $("#jobTitle").val(sessionObj.user.jobTitle);
        $("#organizationName").val(sessionObj.user.organizationName);
        $("#certificateName").val(sessionObj.user.certificateName);
        $("#certificateNo").val(sessionObj.user.certificateNo);
        $("#mobile").val(sessionObj.user.mobile);
        $("#email").val(sessionObj.user.email);

        if (sessionObj.user.sex == 1) {
            $("input[name='sex'][value='1']").attr("checked", true);
        } else {
            $("input[name='sex'][value='2']").attr("checked", true);
        }
        $("#description").val(sessionObj.user.description);
    });
}

function initMenuFun(pid) {
    $.ajax({
        url: '../../../common/listMenuByPid.do?pid=' + pid,
        dataType: 'json',
        async: false,
        success: function (data) {
            var authInfo = data['authInfo'];
            var childAuths = data['childAuth'];
            var menuUi = '';
            $.each(childAuths, function (i, childAuthInfo) {
                menuUi += '<div class="item"><a><i style="background-image: url(static/folding-menu-4.png);"></i>' +
                    '<span>' + childAuthInfo.parentTitle + '</span></a>';
                var childs = childAuthInfo.lowerMenu;
                if (childs && childs.length > 0) {
                    var childUl = '<ul>';
                    $.each(childs, function (j, temp) {
                        // var temp = childs[j];
                        childUl += ' <li onclick="toAddTab(' + temp.id + ');"><a href="#">' + temp.title + '</a> </li>';
                        menuMap[temp.id] = {"title": temp.title, "url": temp.url};
                    })
                    menuUi += childUl + '</ul>';
                }
                menuUi += '</div>';
            });
            $("#menuUi").html(menuUi);
            addTab("首页", authInfo.url);
        },
        error: function () {
            $.messager.alert('错误', '网络连接已断开，请重新登录', 'warning', function () {
                location.href = '../../../login.jsp';
            });
        }
    });
}

function updateInform(id) {
    var informData = {};
    informData['id'] = id;
    informData['systemName'] = 'BA';
    var informForm = JSON.stringify(informData);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../../../zhfz/common/inform/updateInform.do',
        data: informForm,
        dataType: 'json',
        success: function (data) {
            $(tempObjInform[id]).window("close");
        },
        error: function (data) {
            $.messager.progress('close');
            //  $.messager.alert('错误', '未知错误!');
        }
    });
}

var tempObjInform = {};

function asInform() {
    var pid = getUrlParam("pid");

    if (pid == 1) {

        systemName = 'XT';

    } else if (pid == 2) {

        systemName = 'BA';

    } else if (pid == 3) {

        systemName = 'JZ';

    } else if (pid == 4) {
        systemName = 'SA';

    } else if (pid == 5) {

        systemName = 'KP';

    } else if (pid == 7) {

        systemName = 'PT';

    } else if (pid == 8) {
        systemName = 'SLA';
    }
    tempObjInform = {};
    jQuery.ajax({
        type: 'get',
        contentType: 'application/json',
        url: '../../../zhfz/common/inform/listInform.do',
        data: {
            trefresh: new Date().getDate(),
            systemName: systemName,
            title: "入区案件审核通知",
            page: 1,
            rows: 10
        },
        dataType: 'json',
        success: function (data) {
            console.log(data)
            var obj = data.rows;
            for (var i = 0; i < obj.length; i++) {
                tempObjInform[obj[i].id] = obj[i].id;
                var html = '<div style="font-size: 16px;text-align: center"><span>' + obj[i].content + '</span>' +
                    '<br/><a class="easyui-linkbutton" href="#" style="color: yellow" onclick="updateInform(' + obj[i].id + ')">不再提醒</a></div>';
                var ms = $.messager.show({
                    width: '350px',
                    height: '180px',
                    title: '案审提示信息',
                    msg: html,
                    timeout: 0
                });
                tempObjInform[obj[i].id] = ms;
                var elementById = document.getElementById('video');
                elementById.load();
                if (elementById.paused) {
                    elementById.play()
                } else {
                    elementById.pause()
                }

            }
        },
        error: function (data) {

        }
    });

    queryYytzInform()
}
function queryYytzInform(){
    tempObjInform = {};
    jQuery.ajax({
        type: 'get',
        contentType: 'application/json',
        url: '../../../zhfz/common/inform/listInform.do',
        data: {
            trefresh: new Date().getDate(),
            systemName: systemName,
            title: "预约通知",
            page: 1,
            rows: 10
        },
        dataType: 'json',
        success: function (data) {
            console.log(data)
            var obj = data.rows;
            for (var i = 0; i < obj.length; i++) {
                tempObjInform[obj[i].id] = obj[i].id;
                var html = '<div style="font-size: 16px;text-align: center"><span>' + obj[i].content + '</span>' +
                    '<br/><a class="easyui-linkbutton" href="#" style="color: yellow" onclick="updateInform(' + obj[i].id + ')">不再提醒</a></div>';
                var ms = $.messager.show({
                    width: '350px',
                    height: '180px',
                    title: '预约提示',
                    msg: html,
                    timeout: 0,
                    showType: null,
                    style: {}
                });
                tempObjInform[obj[i].id] = ms;
                var elementById = document.getElementById('video');
                elementById.load();
                if (elementById.paused) {
                    elementById.play()
                } else {
                    elementById.pause()
                }

            }
        },
        error: function (data) {

        }
    });
}

//通知
function initTZCount() {

    var pid = getUrlParam("pid");

    if (pid == 1) {

        systemName = 'XT';

    } else if (pid == 2) {

        systemName = 'BA';

    } else if (pid == 3) {

        systemName = 'JZ';

    } else if (pid == 4) {
        systemName = 'SA';

    } else if (pid == 5) {

        systemName = 'KP';

    } else if (pid == 7) {

        systemName = 'PT';

    } else if (pid == 8) {
        systemName = 'SLA';
    }
    jQuery.ajax({
        type: 'get',
        contentType: 'application/json',
        url: '../../../zhfz/common/inform/listInform.do',
        data: {
            trefresh: new Date().getDate(),
            systemName: systemName,
            page: 1,
            rows: 10
        },
        dataType: 'json',
        success: function (data) {

            var str = '<p id="pIdTZCount" style="color: red;margin-left: 25px">' + data.total + '</p>';

            $("#TZCount").html(str);

            //循环通知出区  ,在设为已读
            var cqData = data.rows;
            cqFun(cqData);


        },
        error: function (data) {

        }
    });

    jQuery.ajax({
        type: 'get',
        contentType: 'application/json',
        url: '../../../zhfz/common/schedule/listSchedule.do',
        data: {
            trefresh: new Date().getDate(),
            systemName: systemName,
            page: 1,
            rows: 10
        },
        dataType: 'json',
        success: function (data) {

            var str = '<p id="pIdDBCount" style="color: red;margin-left: 25px">' + data.total + '</p>';

            $("#DBCount").html(str);

            count1 = data.total;

        },
        error: function (data) {

        }
    });

}

//循环通知出区  ,在设为已读
function cqFun(cqData) {
    for (var i = 0; i < cqData.length; i++) {
        var id = cqData[i].id;
        var title = cqData[i].title;
        var content = cqData[i].content;
        if (title == '出区通知' || title == '告警通知' || title == '入区案件审核通知') {
            $.messager.show({
                title: title,
                msg: content,
                timeout: 5000
            });
            //设置已读
            var informData = {};
            informData['id'] = id;
            informData['systemName'] = 'BA';
            var informForm = JSON.stringify(informData);
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: '../../../zhfz/common/inform/updateInform.do',
                data: informForm,
                dataType: 'json',
                success: function (data) {
                },
                error: function (data) {
                    $.messager.progress('close');
                    //  $.messager.alert('错误', '未知错误!');
                }
            });

        }
    }

}

function toAddTab(titleId) {
    var title = menuMap[titleId].title;
    var url = menuMap[titleId].url;
    addTab(title, url);
}

function addTab(title, url) {
    // 先判断是否存在标题为title的选项卡
    var tab = $('#maintabs').tabs('exists', title);
    if (tab) {
        // 若存在，则直接打开
        $('#maintabs').tabs('select', title);
    } else {
        var height = $(window).height() - 180;
        // 否则创建
        $('#maintabs').tabs('add', {
            title: title,
            content: "<iframe style='background: none' allowTransparency='true'  width='100%' height='" + height + "'  frameborder='0' scrolling='auto'  src='" + url + "'></iframe>",
            closable: (title == '首页' ? false : true)
        });
    }
}

// 个人信息
function openUserInfo() {
    showDialog($('#userInfo'), "用户信息");
}

// 关闭
function closeUserInfo() {
    $("#userInfo").dialog('close');
}


//查看消息按钮
function lookInformButton() {

    showDialog($('#informInfo'), "消息");

    $("#informInfoIframe").attr('src', '../../../zhfz/common/inform/inform.jsp?pid=' + pid);
}

//查看待办按钮

function lookScheduleButton() {
    showDialog($('#scheduleInfo'), "待办");

    $("#scheduleInfoIframe").attr('src', '../../../zhfz/common/schedule/schedule.jsp?pid=' + pid);
}

function informAdd() {
    $('#informFb').form('clear');
    showDialog($('#informFb'), "公告消息发布");
}

function informSave() {
    var entForm = $('#informFbForm');
    var obj = entForm.serializeObject();
    console.log(obj)
    if (obj.title == null || obj.title == "") {
        $.messager.alert('提醒', '请输入公告标题');
        return;
    }
    if (obj.content == null || obj.content == "") {
        $.messager.alert('提醒', '请输入公告内容');
        return;
    }
    var enterpriseinfo = JSON.stringify(obj);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../inform/ggInformInsert.do',
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            closeInform();
            $.messager.alert(data.title, data.content);
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', '系统错误!' + data.Msg);
        }
    });
}

function closeInform() {
    $("#informFb").dialog('close');
}

// 修改密码
function openUpdatePw() {
    $('#keyForm').form('clear');
    showDialog($('#updatePw'), "修改密码");
}

// 修改密码保存
function updatePwdSave() {
    var entForm = $('#keyForm');
    var obj = entForm.serializeObject();
    if (!obj.oldPwd) {
        alert("请输入旧密码！");
        return;
    }
    if (obj.newPwd.length < 6) {
        alert("请输入6位及6位以上的新密码！");
        return;
    }
    if (obj.newPwd != obj.confirmPwd) {
        alert("输入的两次密码不一致！");
        return;
    }
    var enterpriseinfo = JSON.stringify(obj);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '../user/changePsw.do',
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            if (data.msg == '密码修改成功！') {
                closeUpdatePw();
                $.messager.show({
                    title: '提示',
                    msg: data.msg,
                    timeout: 3000
                });
            } else {
                $.messager.alert('错误', data.msg);
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('Error', '系统错误!' + data.Msg);
        }
    });
}

function closeUpdatePw() {
    $("#updatePw").dialog('close');
}

//tab菜单
function tabMenu() {
    $('#maintabs').tabs({
        onSelect: function (e, title, index) {
            var tab = $('#maintabs').tabs('getSelected'); //获取选定的面板
            $("#maintabs").tabs("update", {
                tab: tab,
                options: {
                    content: tab[0].innerHTML,
                }
            });
        }
    });
    $('#maintabs').tabs({
        onContextMenu: function (e, title, index) {
            $('#maintabs').tabs('select', index);//选择右键点的tab
            e.preventDefault();
            $('#tabmenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            }).data("tabTitle", title);
        }
    });

    //关闭当前标签页
    $("#closecur").bind("click", function () {
        var tab = $('#maintabs').tabs('getSelected');
        var index = $('#maintabs').tabs('getTabIndex', tab);
        if (isClosable(index)) {
            $('#maintabs').tabs('close', index);
        }
    });
    //关闭所有标签页
    $("#closeall").bind("click", function () {
        var tablist = $('#maintabs').tabs('tabs');
        for (var i = tablist.length - 1; i >= 0; i--) {
            if (isClosable(i)) {
                $('#maintabs').tabs('close', i);
            }

        }
    });
    //关闭非当前标签页（先关闭右侧，再关闭左侧）
    $("#closeother").bind("click", function () {
        colseOtherTab(true, true);
    });
    //关闭当前标签页右侧标签页
    $("#closeright").bind("click", function () {
        colseOtherTab(false, true);
    });
    //关闭当前标签页左侧标签页
    $("#closeleft").bind("click", function () {
        colseOtherTab(true, false);
    });

}

function colseOtherTab(closeLeft, closeRight) {
    var tablist = $('#maintabs').tabs('tabs');
    var tab = $('#maintabs').tabs('getSelected');
    var index = $('#maintabs').tabs('getTabIndex', tab);
    if (closeRight) {
        for (var i = tablist.length - 1; i > index; i--) {
            if (isClosable(i)) {
                $('#maintabs').tabs('close', i);
            }
        }
    }
    var num = index;
    var closeIndex = 0;
    if (closeLeft) {

        for (var i = 0; i < num; i++) {
            if (isClosable(closeIndex)) {
                $('#maintabs').tabs('close', closeIndex);
            } else {
                closeIndex = closeIndex + 1;

            }
        }
    }
    if (closeLeft) {
        $('#maintabs').tabs('select', closeIndex);
    } else {
        $('#maintabs').tabs('select', index);
    }

}

function isClosable(i) {
    var t = $('#maintabs').tabs('getTab', i);
    if (t) {
        return t.panel('options').closable;
    } else {
        return false;
    }

}

function closeOneTab() {
    $("#closecur").click();
}
