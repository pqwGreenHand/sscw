<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.zhixin.zhfz.common.entity.SessionInfo"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String cid =request.getParameter("id");
	String cinterrogateSerialId =request.getParameter("interrogateSerialId");
	java.util.Date dateleft1 =new java.util.Date();
	SimpleDateFormat smfleft = new SimpleDateFormat("yyyy-MM-dd");
	String dateleft = smfleft.format(dateleft1); 
	SessionInfo info2 = (SessionInfo) request.getSession().getAttribute("sessionInfo");
	String areaName="";
	Integer areaId=0;
	if(info2!=null){
		if(info2.getCurrentArea()!=null){
			if(info2.getCurrentArea().getName()!=null &&!"".equals(info2.getCurrentArea().getName())){
				areaName=	info2.getCurrentArea().getName();
				areaId = info2.getCurrentArea().getId();;
			}
		}
	}
	
	Date date=new Date();//取时间
	 Calendar calendar = new GregorianCalendar();
	 calendar.setTime(date);
	 calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
	 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	 String dateString = formatter.format(date);
	 
	 System.out.println(dateString);
%>
<html>
<head>
<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="finereport.js"></script>
<script type="text/javascript" src="changeps.js">
$(function (){
	showQueryFrame("报告查询");
	})
</script>
</head>
<%-- <body class="easyui-layout" style="width: 100%; height: 100%;">
	<table align="center">
		<tr>	
			<td>查询日期 :</td>
			<td width="120px" height="50px"><input id="queryDate" class="easyui-datebox" style="width: 150px" value="<%=dateleft%>"></td>	<td width="50px" height="50px"></td>
			<td>查询类型 :</td>		
			<td width="50px" height="50px"><input type="checkbox"
				id="dayReport" name="checkTheme" value="cell1" checked="checked">日报</td>					
			<td width="50px" height="50px"><input type="checkbox"
				id="weekReport" name="checkTheme" value="cell2" checked="checked">周报</td>
			<td width="50px" height="50px"><input type="checkbox"
				id="monthReport" name="checkTheme" value="cell3" checked="checked">月报</td>
				<td width="50px" height="50px"><input type="checkbox"
				id="quarterReport" name="checkTheme" value="cell5" checked="checked">季报</td>
			<td width="50px" height="50px"><input type="checkbox"
				id="yearReport" name="checkTheme" value="cell4" checked="checked">年报</td><td width="50px" height="50px"></td>
			<td><a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-search" onclick="doSearch()" name="noButton">查询</a></td><td width="25px" height="50px"></td>
			<td><a href="#" class="easyui-linkbutton" name="noButton"
				iconCls="icon-clear" onclick="doClear()">清除</a></td>
		</tr>
	</table>
</body> --%>
<body class="easyui-layout" style="width: 100%; height: 100%; background-color:#0c2d59;">
	<div id="sdialog" title="" class="easyui-dialog" style="width: 250; height: 200; padding: 10px 20px;"  buttons="#changeps-buttons">
		<div class="ftitle">统计条件</div>
			<table align="center">
		<%-- <tr>	
			<td>统计日期 :</td>
			<td width="120px" height="50px"><input id="queryDate" class="easyui-datebox" style="width: 150px" value="<%=dateleft%>"></td>	<td width="50px" height="50px"></td>
		</tr> --%>
		<tr>
			 <td align="top">日统计 :</td>
			<td width="200px" height="60px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="queryDate" class="easyui-datebox" style="width: 150px" value="<%=dateString%>"><br/>
			<!-- <input type="radio" id="weekReport" name="checkTheme" value="cell2" checked="checked">周统计<br/>	
			<input type="radio" id="monthReport" name="checkTheme" value="cell3" checked="checked">月统计<br/>	
			<input type="radio" id="quarterReport" name="checkTheme" value="cell5" checked="checked">季统计<br/>	
			<input type="radio" id="yearReport" name="checkTheme" value="cell4" checked="checked">年统计</td> -->
		</tr>	
		<tr height="10px"><tr>
		<tr>
			<td colspan="2" align="center">
				<a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearch()" name="noButton">生成报表</a>
			</td>
		</tr>
	</table>
	</div>
</body>
<input type="hidden" name="areaId" id="areaId" value="<%=areaId%>">
<input type="hidden" name="projectBasePath" id="projectBasePath" value="<%=basePath%>">
<input type="hidden" id="dayReport" name="checkTheme" value="cell1" checked="checked">

</html>