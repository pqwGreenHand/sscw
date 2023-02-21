<!DOCTYPE html>
<html>
<head>
  <title>Snap interaction example</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/js/openlayer/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/openlayer/ol.css" type="text/css">

<script src="<%=request.getContextPath()%>/js/openlayer/jquery-1.11.2.min.js"></script>
<script src="<%=request.getContextPath()%>/js/openlayer/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/openlayer/ol.js"></script>




  <script src="https://maps.googleapis.com/maps/api/js"></script>
  <style type="text/css">
    div.fill {
      width: 100%;
      height: 100%;
    }

    .map {
      width: 800px;
      height: 400px;
    }
  </style>
</head>
<body>


<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12">
      <div id="map" class="map">
        <!-- gmap用于加载google maps， olmap用于加载openlayer canvas。
        目标是加载完毕之后olmap覆盖与gmap之上并且拦截交互操作。
        开始时放在同一层的好处是都能根据父节点来设置长宽。也可以在js中动态生成div，渲染后插入 -->
        <div id="gmap" class="fill"></div>
        <div id="olmap" class="fill"></div>
      </div>
    </div>
  </div>
</div>




<script type="application/Javascript">
  // 加载google map并禁用地图的交互操作
  var gmap = new google.maps.Map(document.getElementById('gmap'), {
    disableDefaultUI: true,
    keyboardShortcuts: false,
    draggable: false,
    disableDoubleClickZoom: true,
    scrollwheel: false,
    streetViewControl: false
  });

  // ol.View 是openlayers用于控制地图的 坐标系标准 zoom center rotate等操作的对象，在实例化map时候需要使用
  var view = new ol.View({
    // make sure the view doesn't go beyond the 22 zoom levels of Google Maps
    maxZoom: 21,
    projection: 'EPSG:4326' // 设置为标准经纬度的坐标标准，十分重要！ 默认是'EPSG:3857'
  });

  // view 拖动时触发事件，根据当前的坐标转化为经纬度，调用谷歌地图setCenter方法同步地图位置
  view.on('change:center', function () {
    var center = view.getCenter();
    gmap.setCenter(new google.maps.LatLng(center[1], center[0])); // 注意顺序
  });

  // 同上，更改焦距时触发的时间
  view.on('change:resolution', function () {
    gmap.setZoom(view.getZoom());
  });

  // ol.source.Vector 作为 ol.layer.Vector的数据集，增删改feature的方法由source提供
  var vectorSource = new ol.source.Vector();

  var vector = new ol.layer.Vector({
    source: vectorSource
  });

  var olMapDiv = document.getElementById('olmap');
  var map = new ol.Map({
    layers: [vector], // 所使用的图层
    // 禁用掉默认的拖动、旋转等交互
    interactions: ol.interaction.defaults({
      altShiftDragRotate: false,
      dragPan: false,
      rotate: false
    }).extend([new ol.interaction.DragPan({kinetic: null})]),
    target: olMapDiv,
    view: view  // 这里可以使用 new ol.View({options}) 但是在这里需要通过手动设置来触发google maps调节到正确地zoom与center
  });
  view.setCenter([10.689697265625, -25.0927734375]); // 如果未设置view的坐标标准，这里千万要注意不要直接写经纬度
  view.setZoom(6);  // 设置缩放等级
  
  // 将openlayers容器放置到google地图容器中
  olMapDiv.parentNode.removeChild(olMapDiv);
  gmap.controls[google.maps.ControlPosition.TOP_LEFT].push(olMapDiv);
</script>


</body>
</html>