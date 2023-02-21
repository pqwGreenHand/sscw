<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>后台管理界面平台</title>

    <%@ include file="common.jsp" %>

    <script src="${ctxstatic}/plugin/Highcharts-5.0.0/js/highcharts.js"></script>
    <script src="${ctxstatic}/plugin/justgage-1.2.2/raphael-2.1.4.min.js"></script>
    <script src="${ctxstatic}/plugin/justgage-1.2.2/justgage.js"></script>

</head>
<style type="text/css">
    * {
        margin: 0;
        padding: 0;
    }

    ul, li, ol {
        list-style: none;
    }

    img, a img {
        border: none;
        margin: 0;
        padding: 0;
    }

    a {
        text-decoration: none;
        color: #fff;
        cursor: pointer;
    }

    .clear {
        clear: both;
    }

    /*process_box*/
    .process_box {
        margin: 0 auto;
        width: 100%;
        padding-top: 10px;
    }

    .process_top {
        width: 600px;
        height: 60px;
        text-align: center;
        margin: 0 auto;
        padding-top: 30px;
    }

    /*.process_box div{float:left; margin-left:20px;margin-top:10px;margin-bottom:10px;}*/
    .process_box div {
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .chat_aa {
        width: 100px;
        text-align: center;
    }

    .process_a {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_a.png) no-repeat;
        font-size: 18px;
    }

    .process_a p, .process_b p, .process_c p, .process_d p, .process_e p, .process_f p, .process_g p, .process_h p {
        color: #fff;
        text-align: center;
        line-height: 54px;
        margin-bottom: 0px;
    }

    .process_a span {
        color: #1a88ba;
        display: block;
        margin-top: -5px;
    }

    .process_b {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_b.png) no-repeat;
        font-size: 18px;
    }

    .process_b span {
        color: #428e4d;
        display: block;
        margin-top: -5px;
    }

    .process_c {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_c.png) no-repeat;
        font-size: 18px;
    }

    .process_c span {
        color: #428e4d;
        display: block;
        margin-top: -5px;
    }

    .process_d {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_d.png) no-repeat;
        font-size: 18px;
    }

    .process_d span {
        color: #13b5b1;
        display: block;
        margin-top: -5px;
    }

    .process_e {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_e.png) no-repeat;
        font-size: 18px;
    }

    .process_e span {
        color: #00479d;
        display: block;
        margin-top: -5px;
    }

    .process_f {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_f.png) no-repeat;
        font-size: 18px;
    }

    .process_f span {
        color: #556fb5;
        display: block;
        margin-top: -5px;
    }

    .process_g {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_n1.png) no-repeat;
        font-size: 18px;
    }

    .process_g span {
        color: #556fb1;
        display: block;
        margin-top: -5px;
    }

    .process_h {
        width: 154px;
        height: 80px;
        background: url(image/gzlc_n2.png) no-repeat;
        font-size: 18px;
    }

    .process_h span {
        color: #556fb2;
        display: block;
        margin-top: -5px;
    }
</style>
<body style="padding: 20px">
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" style="height:40%">
        <div class="theme-user-info-panel">
            <div class="left">
                <img src="${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png" width="86" height="86"
                     onerror="javascript:this.src='${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png'">
            </div>
            <div class="right">

                <style>
                    .gauge {
                        width: 130px;
                        height: 130px;
                    }
                </style>
                <script>
                    $(function () {

                        jQuery.ajax({
                            async: false,
                            type: 'POST',
                            contentType: 'application/json',
                            url: '${ctx}/zhfz/bacs/console/countTj.do',
                            dataType: 'json',
                            success: function (data) {

                                $("#gg1").attr("data-value",data['ajcount']);
                                $("#gg2").attr("data-value",data['xscount']);
                                $("#gg3").attr("data-value",data['xzcount']);
                                $("#gg4").attr("data-value",data['rycount']);
                                $("#gg5").attr("data-value",data['wpcount']);

                            }
                        });


                        var dflt = {
                            min: 0,
                            max: 2800,
                            donut: true,
                            gaugeWidthScale: 0.6,
                            counter: true,
                            hideInnerShadow: true
                        };

                        var gg1 = new JustGage({
                            id: 'gg1',
                            value: 125,
                            defaults: dflt
                        });
                        var gg2 = new JustGage({
                            id: 'gg2',
                            defaults: dflt
                        });
                        var gg3 = new JustGage({
                            id: 'gg3',
                            defaults: dflt
                        });
                        var gg4 = new JustGage({
                            id: 'gg4',
                            defaults: dflt
                        });
                        var gg5 = new JustGage({
                            id: 'gg5',
                            defaults: dflt
                        });
                    });
                </script>
                <ul>
                    <li>
                        <div id="gg1" class="gauge" data-value=""></div>
                        <span>案件总数</span></li>
                    <li>
                        <div id="gg2" class="gauge" data-value="250"></div>
                        <span>刑事案件</span></li>
                    <li>
                        <div id="gg3" class="gauge" data-value="2000"></div>
                        <span>行政案件</span></li>
                    <li>
                        <div id="gg4" class="gauge" data-value="200"></div>
                        <span>嫌疑人数</span></li>
                    <li>
                        <div id="gg5" class="gauge" data-value="687"></div>
                        <span>物品总数</span></li>
                </ul>
            </div>
            <div class="center">
                <h1>匿名<span class="color-warning badge">未认证</span></h1>
                <h2>管理员</h2>
                <dl>
                    <dt>examples@insdep.com</dt>
                    <dd>13000000000</dd>
                </dl>
            </div>

        </div>
    </div>
    <div data-options="region:'center',border:false">

        <div id="user-info-more" class="easyui-tabs theme-tab-blue-line theme-tab-body-unborder"
             data-options="tools:'#tab-tools',fit:true">

            <div title="统计图" id="charts-layout">
                <!--统计开始-->

                <div id="charts" style="height:400px;"></div>
                <script type="text/javascript">
                    $(function () {

                        jQuery.ajax({
                            async: false,
                            type: 'POST',
                            contentType: 'application/json',
                            url: '${ctx}/zhfz/bacs/console/countSscwTj.do',
                            dataType: 'json',
                            success: function (data) {
                                console.log(data)
                                var datas = [];
                                var keys=[];
                                for(var i = 0;i<data.length;i++){
                                    keys.push(data[i]["name"]);
                                    datas.push(data[i]["count"]);
                                }
                                $('#charts').highcharts({
                                    chart: {
                                        type: 'spline',
                                        events: {
                                            load: function () {

                                            }
                                        }
                                    },
                                    title: {
                                        text: '各单位存取物品统计'
                                    },
                                    subtitle: {
                                        text: '各单位存物、取物排名统计'
                                    },
                                    xAxis: {
                                        // type: 'date',
                                        type: 'category',
                                        categories: keys
                                    },
                                    yAxis: {
                                        title: {
                                            text: ' 件数'
                                        },
                                        min: 0,
                                        minorGridLineWidth: 0,
                                        gridLineWidth: 0,
                                        alternateGridColor: null,
                                        plotBands: [{ // Light air
                                            from: 0.3,
                                            to: 1.5,
                                            color: 'rgba(68, 170, 213, 0.1)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }, { // Light breeze
                                            from: 1.5,
                                            to: 3.3,
                                            color: 'rgba(0, 0, 0, 0)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }, { // Gentle breeze
                                            from: 3.3,
                                            to: 5.5,
                                            color: 'rgba(68, 170, 213, 0.1)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }, { // Moderate breeze
                                            from: 5.5,
                                            to: 8,
                                            color: 'rgba(0, 0, 0, 0)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }, { // Fresh breeze
                                            from: 8,
                                            to: 11,
                                            color: 'rgba(68, 170, 213, 0.1)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }, { // Strong breeze
                                            from: 11,
                                            to: 14,
                                            color: 'rgba(0, 0, 0, 0)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }, { // High wind
                                            from: 14,
                                            to: 1000,
                                            color: 'rgba(68, 170, 213, 0.1)',
                                            label: {
                                                text: '',
                                                style: {
                                                    color: '#606060'
                                                }
                                            }
                                        }]
                                    },
                                    tooltip: {
                                        valueSuffix: ' 件'
                                    },
                                    plotOptions: {
                                        spline: {
                                            lineWidth: 4,
                                            states: {
                                                hover: {
                                                    lineWidth: 5
                                                }
                                            },
                                            marker: {
                                                enabled: false
                                            },
                                            // pointInterval: 3600000, // one hour
                                            // pointStart: Date.UTC(2009, 9, 6, 0, 0, 0)
                                        }
                                    },
                                    series: [{
                                        name: '存物',
                                        data: datas

                                    }]
                                    ,
                                    navigation: {
                                        menuItemStyle: {
                                            fontSize: '10px'
                                        }
                                    }
                                });


                                /*$('#user-info-more').tabs({
                                 onSelect: function(){
                                 //重置宽度
                                 var chart = $('#charts').highcharts();
                                 chart.reflow();
                                 }
                                 });
                                 */
                            }
                        });


                    });

                    document.addEventListener("DOMContentLoaded", function () {
                        //完成所有页面处理后
                        //重置宽度
                        var chart = $('#charts').highcharts();
                        chart.reflow();
                    });
                </script>

                <!--统计结束-->
            </div>
            <div title="流转情况" data-options="closable:true" style="padding:10px">

                <div class="process_top" style=""><h3><span id="title" style="font-size:18px;color: black"></span><span
                        style="font-size:18px;color: black">物品流转情况</span></h3></div>

                <div style="height:65%;">
                    <div class="process_box">
                        <table border="1" bordercolor="black" width="70%"
                               style="border-collapse:collapse;margin-left: 15%;border: 1px;">
                            <tr>
                                <th style="text-align:center;padding:10px;font-size:14px;color: black">已存物 <span
                                        id="countSscw">0</span>件
                                </th>
                                <th style="text-align:center;padding:10px;font-size:14px;color: black">已移交 <span
                                        id="countSsyj">0</span>件
                                </th>
                                <th style="text-align:center;padding:10px;font-size:14px;color: black">已接收 <span
                                        id="countSsjs">0</span>件
                                </th>
                                <th style="text-align:center;padding:10px;font-size:14px;color: black">已拒绝 <span
                                        id="countSsjj">0</span>件
                                </th>
                            <tr>
                                <td width="25%" align="center" valign="middle" style="border-color: black;">
                                    <div class="process_b">
                                        <p style="font-size:18px;">已存物</p>
                                        <span style="font-size:18px;"></span>
                                    </div>
                                </td>
                                <td width="25%" align="center" valign="middle" style="border-color: black">
                                    <div class="process_h">
                                        <p style="font-size:18px;">已移交</p>
                                        <span style="font-size:18px;"></span>
                                    </div>

                                </td>
                                <td width="25%" align="center" valign="middle" style="border-color: black">
                                    <div class="process_d">
                                        <p style="font-size:18px;">已接收</p>
                                        <span style="font-size:18px;"></span>
                                    </div>
                                </td>
                                <td width="25%" align="center" valign="middle" style="border-color: black">
                                    <div class="process_f">
                                        <p style="font-size:18px;">已拒绝</p>
                                        <span style="font-size:18px;"></span>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <div id="tab-tools">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-set'"></a>
    </div>
</div>
</div>
<script>
    $(function () {
        loadAreaId("");
    });

    function loadAreaId(areaId) {
        jQuery.ajax({
            async: false,
            type: 'POST',
            contentType: 'application/json',
            url: '${ctx}/zhfz/bacs/console/countSscw.do?areaId=' + areaId,
            dataType: 'json',
            success: function (data) {
                $('div.process_b').children('span').text(data.count + '件');
                $('#countSscw').text(data.count);
                countSscw += data.count;
            }
        });


        jQuery.ajax({
            async: false,
            type: 'POST',
            contentType: 'application/json',
            url: '${ctx}/zhfz/bacs/console/countSsyj.do?areaId=' + areaId,
            dataType: 'json',
            success: function (data) {
                $('div.process_h').children('span').text(data.count + '件');
                $('#countSsyj').text(data.count);
                countSsyj += data.count;
            }
        })


        jQuery.ajax({
            async: false,
            type: 'POST',
            contentType: 'application/json',
            url: '${ctx}/zhfz/bacs/console/countSsjs.do?areaId=' + areaId,
            dataType: 'json',
            success: function (data) {
                $('div.process_d').children('span').text(data.count + '件');
                countSsjs += data.count;
                $('#countSsjs').text(data.count);

            }
        });

        jQuery.ajax({
            async: false,
            type: 'POST',
            contentType: 'application/json',
            url: '${ctx}/zhfz/bacs/console/countSsjj.do?areaId=' + areaId,
            dataType: 'json',
            success: function (data) {
                $('div.process_f').children('span').text(data.count + '件');
                countSsjs += data.count;
                $('#countSsjj').text(data.count);

            }
        });
    }
</script>
</body>
</html>