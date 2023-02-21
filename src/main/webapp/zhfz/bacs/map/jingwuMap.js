
var style_other = new ol.style.Style({
  fill: new ol.style.Fill({ //矢量图层填充颜色，以及透明度  
    color: 'rgba(255, 255, 255, 0.15)'  
  }),  
  stroke: new ol.style.Stroke({ //边界样式  
    color: '#319FD3',  
    width: 1  
  }),  
  text: new ol.style.Text({ //文本样式  
    font: '14px Calibri,sans-serif',  
    fill: new ol.style.Fill({  
      color: '#000'  
    }),  
    stroke: new ol.style.Stroke({  
      color: '#fff',  
      width: 3  
    })  
  })  
});  

var style_cy = new ol.style.Style({  
	  fill: new ol.style.Fill({ //矢量图层填充颜色，以及透明度  
	    color: 'rgba(0, 0, 255, 0.15)'  
	  }),  
	  stroke: new ol.style.Stroke({ //边界样式  
	    color: '#319FD3',  
	    width: 1  
	  }),  
	  text: new ol.style.Text({ //文本样式  
	    font: '14px Calibri,sans-serif',  
	    fill: new ol.style.Fill({  
	      color: '#000'  
	    }),  
	    stroke: new ol.style.Stroke({  
	      color: '#fff',  
	      width: 3  
	    })  
	  })  
	});  


var style_select = new ol.style.Style({  
  fill: new ol.style.Fill({ //矢量图层填充颜色，以及透明度  
    color: 'rgba(0, 255, 255, 0.4)' 
	
  }),  
  stroke: new ol.style.Stroke({ //边界样式  
    color: '#ff0000',  
    width: 3  
  }),  
  text: new ol.style.Text({ //文本样式  
    font: '14px Calibri,sans-serif',  
    fill: new ol.style.Fill({  
      color: '#000'  
    }),  
    stroke: new ol.style.Stroke({  
      color: '#fff',  
      width: 3  
    })  
  }) ,
  image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
      src: contextPath+'/js/jingwuMap/jinghui2.gif'
    }))
});  

var my_vector=new ol.layer.Vector({
	source: new ol.source.Vector({
	  url: contextPath+'/zhfz/bacs/map/getRegionGeoMapData.do',// 110100.json bei_jing.geo.json b1.json

	  format: new ol.format.GeoJSON()
	}),
	style: function(feature, resolution) {  
		
		var id=feature.getId();
		if(id.indexOf("110105")>-1)
		{
			//朝阳区颜色
			var randomColor=id%10;
			style_cy.getText().setText( feature.get('name'));  
			
			//随机颜色 js/common/color.js
			style_cy.getText().setText( feature.get('name'));  
			style_cy.getFill().setColor(getRgba(randomColor,0.2));
			return [style_cy]; 
		}else{
			//其他颜色
			style_other.getText().setText( feature.get('name'));  
			return [style_other]; 
		}
		 
	  }  
  });

/*
 * org 派出所位置
 * */



var orgStyle = new ol.style.Style({
        image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
          src: contextPath+'/js/jingwuMap/jinghui1.gif'
        }))
      });

var org_vector=new ol.layer.Vector({
	source: new ol.source.Vector({
	  url: contextPath+'/zhfz/bacs/map/getOrgGeoMapData.do',// 110100.json bei_jing.geo.json
	//url: 'p2.json',
	  format: new ol.format.GeoJSON()
	}),
	style: orgStyle //orgStyle 

  });




var map = new ol.Map({
  target: 'map',
  controls: ol.control.defaults({
    attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
      collapsible: false
    })
  }),
   layers: [
            new ol.layer.Tile({
                source: new ol.source.XYZ({
                   // url: '/interrogate-geomap/map/{z}/{x}/{y}.png'
                })
            }),
			my_vector,
			org_vector
        ],
        view: new ol.View({
        	projection: 'EPSG:4326',
            //center: ol.proj.transform([116.50, 40], 'EPSG:4326', 'EPSG:3857'),
        	center: [116.50, 39.95],
        	extent:[115.6,39.75,116.9,40.55],
            zoom: 12,
            minZoom: 10,
			maxZoom: 16
        })

});


//选择事件
  // select interaction working on "singleclick"
  var selectSingleClick = new ol.interaction.Select({
		style:style_select
  });

  // select interaction working on "click"
  var selectClick = new ol.interaction.Select({
	condition: ol.events.condition.click,
	style:style_select
  });

  // select interaction working on "pointermove"
 // var selectPointerMove = new ol.interaction.Select({
//	condition: ol.events.condition.pointerMove
 // });

  //map.addInteraction(selectSingleClick);
  map.addInteraction(selectClick);

var aaa=null;
var bbb=null;
  selectClick.on('select', function(e) {
	 // console.info(e);
		console.info( e.target.getFeatures());
		//console.info( e.selected);

	 aaa=e.target.getFeatures();
	bbb=e;

	getInfo(e.selected);
  });

function getInfo(selected){
	debugger;
	var selId,selName;
	for(var i=0;i<selected.length;i++)
	{
		var feature=selected[i];
		style_select.getText().setText( feature.get('name')); 
		var id=feature.getId();

		selId=id;
		selName=feature.getProperties().name;
//		console.info(feature);
//		console.info(id);
//		console.info(feature.getProperties().name);
	}
	if(selId.indexOf("110105")>-1 || selId.indexOf("o_")>-1 ){
		openBox(selId,selName);
	}

}

function openBox(selId,selName){
	console.info("id===="+selId+"===name==="+selName);
	$('#win').window({ 
		title:selName+"信息",
	    width:'80%',    
	    height:'70%',
	    collapsible:false,
	    minimizable:false,
	    draggable:false,
	    shadow:true,
	    modal:true,
	    content:"<iframe width='100%' height='100%'  id='iframe' frameborder='0' scrolling='auto'  src='jingwuInfo.jsp?oid="+selId+"'></iframe>"
	});  
}
