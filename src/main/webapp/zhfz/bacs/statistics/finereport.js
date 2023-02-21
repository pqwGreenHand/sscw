function doSearch() {

   // alert("11")
    var	queryType="cell1";
    var weekValue = [];
    var weekDateBegin = "";
    var weekDateEnd = "" ;
    var queryDate = $('#queryDate').datebox('getValue');

    //根据当前日期获取最近星期五的日期
    var data4 = "";
    var data5 = "";

    var ss = [];
    ss = queryDate.split("-");
    var year = ss[0];
    var month = ss[1];
    var day = ss[2];

    var yeardata =  parseInt(year);
    var monthdata =(parseFloat(month)-1<0?0:parseFloat(month)-1);
    var daydata = parseInt(day);

    var today=new Date(yeardata,monthdata,daydata); //月份为0-11
    var yesterday_milliseconds=today.getTime()-1000*60*60*24;
    var tomrrow_milliseconds=today.getTime()+1000*60*60*24;
    var yesterday=new Date();
    var tomrrow = new Date();
    yesterday.setTime(yesterday_milliseconds);
    tomrrow.setTime(tomrrow_milliseconds);
    var strYear=yesterday.getFullYear();
    var strDay=yesterday.getDate();
    var strMonth=yesterday.getMonth()+1;

    var strYear1=tomrrow.getFullYear();
    var strDay1=tomrrow.getDate();
    var strMonth1=tomrrow.getMonth()+1;
    if(strMonth<10)
    {
        strMonth="0"+strMonth;
    }
    if(strDay<10)
    {
        strDay="0"+strDay;
    }

    if(strMonth1<10)
    {
        strMonth1="0"+strMonth1;
    }
    if(strDay1<10)
    {
        strDay1="0"+strDay1;
    }

    var strYesterday=strYear+"-"+strMonth+"-"+strDay;
    var strTomorrow=strYear1+"-"+strMonth1+"-"+strDay1;
    var queryAreaId=$('#areaId').val();
    var projectBasePath=$('#projectBasePath').val();

    if(queryDate!=null &&queryDate!="" && queryAreaId!=null && queryAreaId!=""){

        var reportUrl="http://10.11.229.140:8080/interrogate-report/";
        //----start
        $.ajax({
            type:'POST',
            url:'queryWeekDate.do',
            dataType:'json',
            data : {queryDate : queryDate},
            success:function(data){
                //alert(data);
                weekValue = data.title.split("@");
                data4 = weekValue[0];
                data5 = weekValue[1];
                var wourl2=reportUrl+"ReportServer?reportlet=hdrb.cpt&op=view&data="+queryDate+"&data1="+strYesterday+"&data2="+strTomorrow+"&area="+queryAreaId+"&type="+queryType+"&data4="+data4+"&data5="+data5;
                openWindowMax(wourl2);
            },
            error:function(){
                $.messager.alert('Error', '未知错误!');
            }
        })
        //------end
    }else{
        alert("信息选择不全");
    }
}

function doClear(){
    //alert("清除");
    $('#queryDate').datebox('clear');
    document.getElementsByName("checkTheme").checked=false;
}

//最大化弹出窗口
function openWindowMax(url){
    var height = screen.availHeight - 40;
    var width = screen.availWidth - 15;
    var screenParam = "left=0,top=0,scrollbars,resizable=yes,toolbar=no',height=" + height + ",width=" + width;
    var winOpen = window.open("about:blank","",screenParam);
    winOpen.location = url;
}

function showQueryFrame() {
    $('#sdialog').dialog({
        title: 'My Dialog',
        closed: false,
        cache: false,
        modal: true
    });
    $('#sdialog').dialog('refresh');
    $('#key_form').form('clear');
}