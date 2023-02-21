<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>test Example</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/openlayer/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/openlayer/ol.css" type="text/css">

<script src="<%=request.getContextPath()%>/js/openlayer/jquery-1.11.2.min.js"></script>
<script src="<%=request.getContextPath()%>/js/openlayer/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/openlayer/ol.js"></script>
<script type="text/javascript">
var servletPath="<%=request.getServletPath()%>";
var contextPath="<%=request.getContextPath()%>";
</script>

</head>
<body>
<div class="container-fluid">

<div class="row-fluid">
  <div class="span12">
    <div id="map" class="map" style='width:800px;height:800px;'></div>
  </div>
  <a herf='#' onclick='addInteraction()' >add</a>
  <a herf='#' onclick='removeInteraction()' >remove</a>
  <a herf='#' onclick='ok()' >ok</a>
</div>

</div>
<script src="test.js"></script>
</body>
</html>