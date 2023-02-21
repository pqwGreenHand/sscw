
var xAxisData =[];
var seriesData = [];
var countSscw=0;//已预约数
var countSsyj=0;//已入区数
var countSsjs=0;//已出区数
var countSsjj=0;//已出区数
$(function(){
    // hiddendiv("#echart");
    var areaId = null;
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "queryCenters.do",
        async : false,
        dataType: 'json',
        success: function (data) {
            if(data==null||data[0].id<1){
                //显示流程
                $("#content_vhart").show();
                //隐藏echatrs
                //$("#liuchengtu").hidden();
                // hiddendiv("#echart");
            }else{
                for(var i = 0;i<data.length;i++){
                    $("#centers").append("<li id='"+data[i].id+"' class='caselist01'><a  href='javascript:void(0);' onclick='selectArea("+data[i].id+",\""+data[i].name+ "\")'><i class='icon'></i>"+data[i].name+"</a></li>");
                }
                $("#echart").show();
            }
            //centerId = data[0].id;
            //name = data[0].name;
        }
    });
    selectArea(areaId);
    // $('div.process_box').find('div.process_a').bind('click',function(){listInterrogateSerial_cq();});
    // $('div.process_box').find('div.process_b').bind('click',function(){listOrderRequest();});
    // $('div.process_box').find('div.process_c').bind('click',function(){listInterrogateSerial_rq();});
    // $('div.process_box').find('div.process_d').bind('click',function(){listRecord();});
    // $('div.process_box').find('div.process_e').bind('click',function(){listWaitingRecord();});
    // $('div.process_box').find('div.process_f').bind('click',function(){listInterrogateSerial_els();});
    // $('div.process_box').find('div.process_g').bind('click',function(){listInterrogateSerial_lscq();});
    // $('div.process_box').find('div.process_h').bind('click',function(){listOtherentrance();});

});
function doRefresh(){
    location.reload();
}



function selectArea(areaId,name){
    $("#title").text(name);
    var countSscw=0;//已存物
    var countSsyj=0;//已移交
    var countSsjs=0;//已接收
    var countSsjj=0;//已接拒绝
    var xsCount=0;//刑事案件数
    var xzCount=0;//行政案件数
    var jqCount=0;//警情案件数
    bianse(areaId);
  var x=[];
    $.ajax({
        type: "POST",
        async: false,
        url: 'queryCaseType.do?areaId=' + areaId,
        //data: "{'areaId':" + areaId + "}",
        dataType: "json",
        success: function (data) {
            console.log(data)
            var series = [];
            var count = 0;
            for (k in data) {
                x.push(k);
                series.push({"value": data[k], "name": k});
                if (data[k] != 0) {
                    count++;
                }
                if (count == 0) {
                    x.splice(0, x.length);
                    x.push("暂无"+k);
                    series.splice(0, series.length);
                    series.push({"value":0, "name": "暂无"+k});
                }
                var mychart1 = echarts.init(document.getElementById('echartsCase'));
                option = {
                    title: {
                        // text: '案件数',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },

                    legend: {
                        x: 'center',
                        y: 'bottom',
                        data: x,
                        textStyle: {//图例文字的样式
                            color: '#FFFFFF'
                        },
                    },

                    calculable: true,
                    series: [
                        {
                            name: '案件统计',
                            type: 'pie',
                            radius: '100',
                            center: ['60%', '40%'],
                            data: series,
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        formatter: '{b} : {c} \n ({d}%)'
                                    },
                                    labelLine: {
                                        lineStyle: {
                                            width: 1,
                                            type: 'solid'
                                        }
                                    }
                                },
                                /*控制中心是否显示文字*/
                                emphasis: {
                                    label: {
                                        show: false,
                                        position: 'center',
                                        textStyle: {
                                            fontSize: '10',
                                            fontWeight: 'bold'
                                        }
                                    }
                                }
                            }
                        }
                    ]
                };
                mychart1.setOption(option);
            }
        }
    } )
//仪表盘候问容积率
    var count = 0;
    $.ajax({
        type: "POST",
        async: false,
        url: 'queryBelongCountByAreaId.do?areaId='+areaId,
        data:"{'areaId':"+areaId+"}",
        dataType: "json",
        success: function (result) {
            var mychart2 = echarts.init(document.getElementById('echartsVolume'));
            count = result;
            option = {
                tooltip : {
                    formatter: "{a} <br/>{b} : {c}%"

                },
                title : {
                    textStyle: {
                        color:"white"
                    }
                },
                series: [
                    {
                        name: '办案中心柜门使用率',
                        type: 'gauge',
                        detail: {formatter:result+'%'},
                        data: [{value: result, name: '柜门使用率'}],
                        title : {               //设置仪表盘中间显示文字样式
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontSize: 12,
                                color:"white"
                            }
                        },
                    }
                ]
            };
            mychart2.setOption(option,true);
        }
    });


    //物品流转统计
    $.ajax({
        type: "POST",
        async: false,
        url: 'queryCscwType.do?areaId='+areaId,
        dataType: "json",
        success: function (result) {
            var data = [];
            var keys=[];
            var count = 0;
            for(var i = 0;i<result.belongList.length;i++){
                keys.push(result.belongList[i].name);
                data.push({"value":result.belongList[i].belongCount,"name":result.belongList[i].name});
                if(result.belongList[i].belongCount!=0){
                    count++;
                }
            }
            if(count==0){
                keys.splice(0,keys.length);
                keys.push("暂无物品");
                data.splice(0,data.length);
                data.push({"value":1,"name":"暂无物品"});
            }
            var mychart3 = echarts.init(document.getElementById('echartsSscw'));
            option = {
                title: {
                    //text: '办案中心物品流转统计',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x: 'center',
                    y: 'bottom',
                    data: keys,
                    textStyle:{//图例文字的样式
                        color:'#FFFFFF',
                    },
                },
                calculable: true,
                series: [
                    {
                        name: '办案中心在存物品对比',
                        type: 'pie',
                        radius : '100',
                        center: ['45%', '40%'],
                        data: data,
                        itemStyle : {
                            normal : {
                                label:{
                                    show:true,
                                    formatter: '{b} : {c} \n ({d}%)'
                                },
                                labelLine:{
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                }
                            },
                            /*控制中心是否显示文字*/
                            emphasis : {
                                label : {
                                    show : false,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '10',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        }
                    }
                ]
            };
            mychart3.setOption(option);
        }
    });


    jQuery.ajax({
        async : false,
        type : 'POST',
        contentType : 'application/json',
        url : 'countSscw.do?areaId='+areaId,
        dataType : 'json',
        success : function(data){
            $('div.process_b').children('span').text(data.count+'件');
            $('#countSscw').text(data.count);
            countSscw+=data.count;
        }
    });


    jQuery.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'countSsyj.do?areaId=' + areaId,
        dataType: 'json',
        success: function (data) {
            $('div.process_h').children('span').text(data.count + '件');
            $('#countSsyj').text(data.count);
            countSsyj += data.count;
        }
    })


    jQuery.ajax({
        async : false,
        type : 'POST',
        contentType : 'application/json',
        url : 'countSsjs.do?areaId='+areaId,
        dataType : 'json',
        success : function(data){
            $('div.process_d').children('span').text(data.count+'件');
            countSsjs+=data.count;
            $('#countSsjs').text(data.count);

        }
    });

    jQuery.ajax({
        async : false,
        type : 'POST',
        contentType : 'application/json',
        url : 'countSsjj.do?areaId='+areaId,
        dataType : 'json',
        success : function(data){
            $('div.process_f').children('span').text(data.count+'件');
            countSsjs+=data.count;
            $('#countSsjj').text(data.count);

        }
    });

}
function bianse(centerId) {
    $("#div01 li").attr("class","caselist01");
    $("#"+centerId+"").attr("class","caselist02");
}