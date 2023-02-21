<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
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

	<link rel="shortcut icon" href="../../../favicon.ico">
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
	<style>
	.zeromodal-container button{
		margin-right:20px;
	}
		#table table {
			width: 100%;
			font-size: 14px;
			border: 1px solid #eee
		}
		.sweetAlert {
			width: 22em;
			margin: 0 auto;
			left: 0;
			right: 0;
		}
		table thead th {
			background: #f5f5f5;
			padding: 10px;
			text-align: left;
		}

		table tbody td {
			padding: 10px;
			text-align: left;
			border-bottom: 1px solid #eee;
			border-right: 1px solid #eee;
		}

		table tbody td span {
			margin: 0 10px;
			cursor: pointer;
		}


		input {
			border: 1px solid #ccc;
			padding: 5px;
			border-radius: 3px;
			margin-right: 15px;
		}

		button {
			background: #008cd5;
			border: 0;
			padding: 4px 15px;
			border-radius: 3px;
			color: #fff;
		}




		.title span {
			float: right;
			cursor: pointer;
		}


		.content input {
			width: 270px;
			margin-bottom: 15px;
		}
	</style>
</head>
		<div class="row" >
			<div class="col-md-12 col-sm-12" >
				<form method="post" class="form-horizontal" id="doc20Form">
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>被提解人:</h4></label>
							<div class="col-md-9">
								<input type="text" name="xm" id="xm" class="form-control" placeholder="姓名"  required="" aria-required="true" />	
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>年龄:</h4></label>
							<div class="col-md-9">
								<input type="text" name="nl" id="nl" class="form-control" placeholder="年龄"  required="" aria-required="true" />	
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>性别:</h4></label>
							<div class="col-md-9">
								<select id="xb" name="xb" class="form-control">
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>住址:</h4></label>
							<div class="col-md-9">
								<input type="text" name="zz" id="zz" class="form-control" placeholder="住址"  required="" aria-required="true" />	
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>身份证号:</h4></label>
							<div class="col-md-9">
								<input type="text" name="sfz" id="sfz" class="form-control" placeholder="身份证号"  required="" aria-required="true" />	
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>案由:</h4></label>
							<div class="col-md-9">
								<input type="text" name="ay" id="ay" class="form-control" placeholder="案由"  required="" aria-required="true" />	
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>寄押处所:</h4></label>
							<div class="col-md-9">
								<input type="text" name="jycs" id="jycs" class="form-control" placeholder="寄押处所"  required="" aria-required="true" />	
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>执行寄押时间:</h4></label>
							<div class="col-md-9">
								<input onclick="laydate({istime: false,format: 'YYYY-MM-DD'})" class="form-control layer-date" name="jysj" id="jysj" placeholder="执行寄押时间" >
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>提解单位:</h4></label>
							<div class="col-md-9">
								<select id="tjdw" name="tjdw" class="form-control">
									<option value="">请选择单位</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>提解人:</h4></label>
							<div class="col-md-9">
								<input type="text" name="tjr" id="tjr" class="form-control" placeholder="提解人"  required="" aria-required="true" />	
								<span class="glyphicon glyphicon-plus" style="position:absolute;top:10px;right:20px;z-index:10" onclick="ry(1)"></span>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>协助单位:</h4></label>
							<div class="col-md-9">
								<select id="xzdw" name="xzdw" class="form-control">
									<option value="">请选择单位</option>
								</select>							
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>协助人:</h4></label>
							<div class="col-md-9">
								<input type="text" name="xzr" id="xzr" class="form-control" placeholder="协助人"  required="" aria-required="true"/>	
								<span class="glyphicon glyphicon-plus" style="position:absolute;top:10px;right:20px;z-index:10" onclick="ry(2)"></span>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>批准人:</h4></label>
							<div class="col-md-9">
								<input type="text" name="pzr" id="pzr" class="form-control" placeholder="批准人"  required="" aria-required="true"/>	
								<span class="glyphicon glyphicon-plus" style="position:absolute;top:10px;right:20px;z-index:10" onclick="ry(3)"></span>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>批准时间:</h4></label>
							<div class="col-md-9">
								<input onclick="laydate({istime: false,format: 'YYYY-MM-DD'})" class="form-control layer-date" name="pzsj" id="pzsj" placeholder="批准时间" >
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>填发人:</h4></label>
							<div class="col-md-9">
								<input type="text" name="tfr" id="tfr" class="form-control" placeholder="填发人"  required="" aria-required="true" />	
								<span class="glyphicon glyphicon-plus" style="position:absolute;top:10px;right:20px;z-index:10" onclick="ry(4)"></span>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label  class="col-md-3 control-label"><h4>填发时间:</h4></label>
							<div class="col-md-9">
								<input onclick="laydate({istime: false,format: 'YYYY-MM-DD'})" class="form-control layer-date" name="tfsj" id="tfsj" placeholder="填发时间" >
							</div>
						</div>
					</div>
				</form>
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
<script src="../../../evaluation/js/zeroModal.min.js"></script>
<script src="../../../evaluation/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="../../../evaluation/js/extendPagination.js"></script>
<script src="../../../evaluation/js/demo/form-validate-demo.min.js"></script>
<script src="../../../evaluation/js/plugins/validate/jquery.validate.min.js"></script>
<script src="../../../evaluation/js/plugins/validate/messages_zh.min.js"></script>
<script src="../../../evaluation/js/demo/form-validate-demo.min.js"></script>
<script src="doc20.js"></script>

</html>