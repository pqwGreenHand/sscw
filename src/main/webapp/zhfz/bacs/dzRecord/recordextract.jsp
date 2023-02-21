<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>笔录提取</title>
<%@ include file="../../common/common-head.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/js/ke/themes/default/default.css" />
<link href="${ctx }/swfUpload/css/default.css" rel="stylesheet" type="text/css" />


<style type="text/css">
.headTitle {
	color: #000;
	font-family: "Microsoft Yahei", "微软雅黑", "黑体";
	font-size: 22px;
	font-weight: normal;
	height: 35px;
	line-height: 35px;
	overflow: hidden;
	text-align: center;
}

table {   
        border-collapse: collapse;   
        border: none;   
    }   

td {   
     border: solid #000 1px;   
     height:45px;
     width:120px;
 }  

</style>
<script type="text/javascript">

</script>
</head>
<body style="width: 90%; height: 100%; text-align:center; margin-left:auto; margin-right:auto;" >
		<h1 class="headTitle">笔录提取信息</h1>
			<table width="100%" >
				<tr>
				  <c:forEach var="item" items="${list}" varStatus="status">
					<td style="background-color:#ccc"><c:out value="${item.name }" /></td>
					<td><c:out value="${item.value }" /></td>
			      <c:if test="${status.count%6==0 }">
			        </tr>
			        <tr>
			      </c:if>
				  </c:forEach>
				</tr>
			</table>
        <br><br>
</body>
</html>