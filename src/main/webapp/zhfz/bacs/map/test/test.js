var attribution = new ol.Attribution({
  html: 'Tiles ' +
      'National Library of Scotland</a>'
});

var style = new ol.style.Style({  
  fill: new ol.style.Fill({ //矢量图层填充颜色，以及透明度  
    color: 'rgba(255, 0, 0, 0.2)'  
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
	    color: 'rgba(0, 0, 255, 0.2)'  
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


var style2 = new ol.style.Style({  
  fill: new ol.style.Fill({ //矢量图层填充颜色，以及透明度  
    color: 'rgba(0, 0, 255, 0.02)' 
	
  }),  
  stroke: new ol.style.Stroke({ //边界样式  
    color: '#ff0000',  
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

var my_vector=new ol.layer.Vector({
	source: new ol.source.Vector({
	  url: contextPath+'/map/getRegionGeoMapData.do',// 110100.json bei_jing.geo.json b1.json

	  format: new ol.format.GeoJSON()
	}),
	style: function(feature, resolution) {  
		
		var id=feature.getId();
		if(id.indexOf("110105")>-1)
		{
			style_cy.getText().setText( feature.get('name'));  
			return [style_cy]; 
		}else{
			style.getText().setText( feature.get('name'));  
			return [style]; 
		}
		 
	  }  
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
                    url: '/interrogate-geomap-web/map/{z}/{x}/{y}.png'
                })
            }),
			my_vector
        ],
        view: new ol.View({
        	projection: 'EPSG:4326',
            //center: ol.proj.transform([116.50, 40], 'EPSG:4326', 'EPSG:3857'),
        	center: [116.50, 40],
            zoom: 11,
            minZoom: 10,
			maxZoom: 14
        })

});


//选择事件
  // select interaction working on "singleclick"
  var selectSingleClick = new ol.interaction.Select({
		style:style2
  });

  // select interaction working on "click"
  var selectClick = new ol.interaction.Select({
	condition: ol.events.condition.click,
	style:style2
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
	for(var i=0;i<selected.length;i++)
	{
		var ss=selected[i];
		var id=ss.getId();
		console.info(id);
		console.info(ss.getProperties().name);
	}


}


 var draw; // global so we can remove it later

function addInteraction() {
        draw = new ol.interaction.Draw({
          features: features,
          type: 'Polygon'
        });
        map.addInteraction(draw);
      }

function  removeInteraction(){
	map.removeInteraction(draw);
  };


  var features = new ol.Collection();
  var featureOverlay = new ol.layer.Vector({
	source: new ol.source.Vector({features: features}),
	style: new ol.style.Style({
	  fill: new ol.style.Fill({
		color: 'rgba(255, 255, 255, 0.05)'
	  }),
	  stroke: new ol.style.Stroke({
		color: '#ffcc33',
		width: 2
	  }),
	  image: new ol.style.Circle({
		radius: 7,
		fill: new ol.style.Fill({
		  color: '#ffcc33'
		})
	  })
	})
  });
  featureOverlay.setMap(map);

var modify = new ol.interaction.Modify({
        features: features,
        // the SHIFT key must be pressed to delete vertices, so
        // that new vertices can be drawn at the same position
        // of existing vertices
        deleteCondition: function(event) {
          return ol.events.condition.shiftKeyOnly(event) &&
              ol.events.condition.singleClick(event);
        }
      });

map.addInteraction(modify);


function ok(){
console.info(featureOverlay.getSource().getFeatures());
var format = new ol.format.GeoJSON();
var ff=format.writeFeatures(featureOverlay.getSource().getFeatures());

console.info(ff);
//,{dataProjection:'EPSG:3857',featureProjection:'EPSG:4326'}
//console.info(format.writeFeatures(my_vector.getSource().getFeatures())   );
}


