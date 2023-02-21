<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="renderer" content="webkit">
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<title>南沙区分局执法数据分析系统</title>
		<meta name="keywords" content="南沙区分局执法数据分析系统">
		<meta name="description" content="南沙区分局执法数据分析系统">
		<!--[if lt IE 9]>
		<meta http-equiv="refresh" content="0;ie.html" />
		<![endif]-->

		<link rel="shortcut icon" href="../favicon.ico">
		<link href="../../../evaluation/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
		<link href="../../../evaluation/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
		<link href="../../../evaluation/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
		<link href="../../../evaluation/css/animate.min.css" rel="stylesheet">
		<link href="../../../evaluation/css/plugins/chosen/chosen.css" rel="stylesheet">
		<link href="../../../evaluation/css/style.min862f.css?v=4.1.0" rel="stylesheet">
		<link href="../../../evaluation/css/plugins/iCheck/custom.css" rel="stylesheet">
		<link href="../../../evaluation/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
		<link href="../../../evaluation/css/info/evaluation/evaluation.css" rel="stylesheet">
		<link href="../../../evaluation/css/info/evaluation/evaluation.css" rel="stylesheet">
		<link href="../../../evaluation/css/zeroModal.css" rel="stylesheet">

	</head>
	<div align="center">
						<div  align="center">


									<form method="post" class="form-horizontal" id="paramForm">
										<div class="col-md-12">
											<!-- 报案人 -->
											<div class="form-group">
												<label  class="col-md-4 control-label">民警姓名:</label>
												<div class="col-md-5">
													<input type="text" name="mjxm" id="mjxm" class="form-control" placeholder="请输入民警姓名(模糊)" />
												</div>
											</div>
										</div>
										<div class="col-md-12" >
											<div class="form-group">
												<label  class="col-md-4 control-label">民警警号:</label>
												<div class="col-md-5">
													<input type="text" name="mjjh" id="mjjh" class="form-control" placeholder="请输入民警警号(模糊)" />
												</div>
											</div>
										</div>
										
										<div class="col-md-12">
										<div class="form-group">
											<label  class="col-md-4 control-label">民警单位:</label>
											<div class="col-md-5">
												<select class="form-control"  name="oraniztionId" id="mjdw"  length="10"  onchange="dwchange()"">
													<option value="100" >请选择民警单位</option>
												</select>
											</div>
										</div>
										</div>


											<div  align="center">
												<div class="col-md-8 col-md-offset-2">
													<button class="btn btn-success" type="button" id="selectUserButton">查询</button>
													<button class="btn btn-warning" type="button" onclick="cancelFzcxCommon()">重置</button>
												</div>
											</div>

									</form>
 </div>

						</div>

	<%--<input id="shldName" name="shldName" type="text">--%>
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-md-12">
						<!-- Example Pagination -->
						<div class="example-wrap">
							<div class="example">


								<table id="commonUserTable"></table>
							</div>
						</div>
						<!-- End Example Pagination -->
					</div>

				</div>
			</div>


	<script src="../../../evaluation/js/jquery.min.js?v=2.1.4"></script>
	<script src="../../../evaluation/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="../../../evaluation/js/content.min.js?v=1.0.0"></script>
	<script src="../../../evaluation/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script src="../../../evaluation/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
	<script src="../../../evaluation/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script src="../../../evaluation/js/plugins/iCheck/icheck.min.js"></script>
	<script src="../../../evaluation/js/plugins/layer/laydate/laydate.js"></script>
	<script src="ry.js"></script>
	<script src="../../../evaluation/js/zeroModal.min.js"></script>
	<script src="../../../evaluation/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="../../../evaluation/js/extendPagination.js"></script>

</html>