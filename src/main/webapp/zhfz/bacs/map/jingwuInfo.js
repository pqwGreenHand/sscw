var dataM;
$(function(){
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'数据加载中...'
	});
	
	$('#tabBox').tabs({
	    border:false,
	    onSelect:function(title,index){
	    	addEchart1(dataM[index].todayPrisoners);
	    	addEchart2(dataM[index].weekCases);
	    }
	});
	
	loadData();
})


function loadData(){
	var oid=getUrlParam("oid");
	$.ajax({
		contentType : 'application/json',
		type:'POST',
		url:contextPath+'/zhfz/bacs/commonInfo/listJingwuInfo.do',
		dataType:'json',
		data : JSON.stringify({oid:oid}),
		success:function(data){
//			console.info(data);
			for(var i=0;i<data.length;i++){
				addTab(data[i]);
			}
			dataM=data;
			$('#tabBox').tabs('select',0);
			//addEchart1(dataM);
			$.messager.progress('close');
			//数据展示
			
		},
		error:function(){
			$.messager.alert('Error', '未知错误!');
		}
	})

	
}

function addTab(tdata){
	// 添加一个未选中状态的选项卡面板
	$('#tabBox').tabs('add',{
		title: tdata.orgName,
		selected: false,
		content:'<table id="prop_'+tdata.id+'"></table>'
	});


	
	
	$('#prop_'+tdata.id).propertygrid({    
		data:tdata.todayPrisoners,
		columns:[[    
          {field:'type',title:'类型',width:100},    
          {field:'num',title:'人数',width:100}   
      ]]  
	}); 
}

function addEchart1(tdata){
	var eChart = echarts.init(document.getElementById('echartDiv1'));
	option = {
		    title : {
		        text: '当日送警人数',
		        x:'center'
		    },
		    legend: {
		        x : 'center',
		        y : 'bottom',
		        data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true,
		                type: ['pie', 'funnel']
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'面积模式',
		            type:'pie',
		            radius : [30, 110],
		            center : ['55%', '50%'],
		            roseType : 'area',
		            data:toPieData(tdata)
		        }
		    ]
		};
	eChart.setOption(option);
//	console.info(eChart);
}

function toPieData(data){
	var d=[];
	for(var i=0;i<data.length;i++){
		d.push({value:data[i].num, name:data[i].type});

	}
//	console.info(JSON.stringify(d));
//	console.info(eval("("+JSON.stringify(d)+")"));
	return eval("("+JSON.stringify(d)+")");

}

function to7DayData(data){
	var d=[];
	for(var i=0;i<data.length;i++){
		d.push({value:data[i].num});

	}
	return eval("("+JSON.stringify(d)+")");

}

function to7DayTitle(data){
	var d=[];
	for(var i=0;i<data.length;i++){
		d.push({value:data[i].date});

	}
	return eval("("+JSON.stringify(d)+")");

}


function addEchart2(data){
//	console.info(data);
	option = {
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : to7DayTitle(data),
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'送警人数',
		            type:'bar',
		            barWidth: '60%',
		            data:to7DayData(data)
		        }
		    ]
		};

	var eChart = echarts.init(document.getElementById('echartDiv2'));
	eChart.setOption(option);
//	console.info(eChart);
}
