<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.zhixin.zhfz.common.utils.ControllerTool" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String lawCaseId= request.getParameter("lawCaseId");
	Integer userid= ControllerTool.getUserID(request);
	
%>

<html>
<head>
	<%@ include file="../../common/common-head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/swfupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/single.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/fileprogress.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/swfUpload/handlers.js"></script>
<link href="<%=request.getContextPath()%>/swfUpload/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var upload_swfupload = {SWFObj:new Object()}; 
//var fileName[10] ;
var fileName=new Array();
var i=0;
var lawCaseId="<%=request.getParameter("lawCaseId")%>";
var interrogateSerialId=null;

	function upload_swfupload_load(){
		var loadSettings = {
		    //提交路径
			upload_url : "<%=request.getContextPath()%>/file/upload.do",
			//向后台传递额外的参数
			post_params : {
			   savePath: "/upload/2015-08-23/",
			    userid : <%=userid%>,
			    lawCaseId:lawCaseId,
				small : false,
				sw:120,
				sh:120,
				width : 800,
				height: 800,
				inputResult : window.location.href
			},
			file_size_limit : "5120000",
			file_types_description : "压缩包 图片 常用文档格式",
			file_types : "*.zip;*.rar;*.jpg;*.jpeg;*.gif;*.png;*.txt;*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.*",
			file_upload_limit : "0",
			file_queue_limit : "0",
			prevent_swf_caching :true,
				// 按钮的处理
			button_image_url : "<%=request.getContextPath()%>/swfUpload/images/XPButtonUploadText_61x22.png",
			button_placeholder_id : "spanButtonPlaceholder1_swfupload",
			button_width : 180,
			button_height : 50,
	        upload_success_handler:myUploadSuccess,
			// Flash Settings
			flash_url : "<%=request.getContextPath()%>/swfUpload/new/swfupload.swf",
	
			custom_settings : {
			    file_post_name : "filedata",
				progressTarget : "fsUploadProgress1_swfupload",
				cancelButtonId : "btnCancel1_swfupload",
				fileSiteName : "",
				fileTypeName : ""
			}
		}
		SWFLoad(upload_swfupload,loadSettings);
		try{
		 upload_swfupload.SWFObj.addPostParam("caseDataType",$('#caseDataType').combobox('getValue'));
		}catch(e){}
		
	}
	  addLoadEvent(comgrid1);
	  addLoadEvent(upload_swfupload_load);
	  
	  
	  function comgrid1(){
		  $('#interrogateSerialId').combogrid({    
				panelWidth:360,    
			    mode: 'remote',    
			    url: '../combogrid/getAllinterrogateSerial.do?lawCaseId='+lawCaseId,    
			    idField: 'id',    
			    textField: 'name',    
			    columns: [[    
			        {field:'serialNo',title:'专属编号',width:135},
					{field:'name',title:'姓名',width:60},
					{field:'certificateNo',title:'证件号码',width:150}
			    ]]  
			});
		  $("#interrogateSerialId").combobox("disable");
		 
	  }
	  

	  
	  
	  
	//制保留2位小数，如：2，会在2后面补上00.即2.00
	function toDecimal2(x) {
		var f = parseFloat(x);
		if (isNaN(f)) {
			return false;
		}
		var f = Math.round(x * 100) / 100;
		var s = f.toString();
		var rs = s.indexOf('.');
		if (rs < 0) {
			rs = s.length;
			s += '.';
		}
		while (s.length <= rs + 2) {
			s += '0';
		}
		return s;
	}
	function delFile(filePath,fileObj,swfObj){
	    var idObj = document.getElementById(fileObj.id);
	    var  filePath1=fileName[fileObj.id.toString().split("_")[fileObj.id.toString().split("_").length-1]];
	    var stats = swfObj.getStats();
		stats.successful_uploads -= 1;
		swfObj.setStats(stats);
	    $.post("<%=request.getContextPath()%>/interrogate/clue/delFile.do",{path:filePath1,},function(data, status){
	        var result = status; 
	       idObj.parentNode.removeChild(idObj); 
		});
	}
	
	function myUploadSuccess(fileObj,server_data) {
		fileName[i]=server_data;
		i++;
		uploadSuccess(fileObj,server_data,"/ictpm",this);
		
	}
	
	
	function save(){
		var filePath;
		interrogateSerialId=$('#interrogateSerialId').combobox('getValue');
		if(interrogateSerialId==''){
			var pWindow=window.dialogArguments;
//			pWindow.doThingsAfterAdd(lawCaseId);
			window.close();
		}else{
			if(fileName.length==0){
				var pWindow=window.dialogArguments;
//				pWindow.doThingsAfterAdd(lawCaseId);
				window.close();
			}else{
				filePath=fileName[0];
				if(fileName.length==1){
					$.post("<%=request.getContextPath()%>/interrogate/clue/updateByAuto.do",{path:filePath,interrogateSerialId:interrogateSerialId},function(data, status){
					       var result = status; 
					       var pWindow=window.dialogArguments;
//							pWindow.doThingsAfterAdd(lawCaseId);
					       window.close();
						});
					
				}else{
					for(var i = 1;i<fileName.length;i++){
							filePath=filePath+","+fileName[i];
					}
					 $.post("<%=request.getContextPath()%>/interrogate/clue/updateByAutoAll.do",{path:filePath,interrogateSerialId:interrogateSerialId},function(data, status){
					        var result = status; 
					       //idObj.parentNode.removeChild(idObj); 
					       //alert(data);
					       var pWindow=window.dialogArguments;
//							pWindow.doThingsAfterAdd(lawCaseId);	
					        window.close();
						});
					
				}
			}
		}
	}
	function delAll(){
		var filePath;
		if(fileName.length==0){
			var pWindow=window.dialogArguments;
//			pWindow.doThingsAfterAdd(lawCaseId);
			window.close();
		}else{
			filePath=fileName[0];
			if(fileName.length==1){
				$.post("<%=request.getContextPath()%>/interrogate/clue/delFile.do",{path:filePath},function(data, status){
				       var result = status; 
				       var pWindow=window.dialogArguments;
//						pWindow.doThingsAfterAdd(lawCaseId);
				       window.close();
					});
				
			}else{
				for(var i = 1;i<fileName.length;i++){
						filePath=filePath+","+fileName[i];
				}
				 $.post("<%=request.getContextPath()%>/interrogate/clue/delAllFile.do",{path:filePath},function(data, status){
				        var result = status; 
				       //idObj.parentNode.removeChild(idObj); 
				       //alert(data);
				       var pWindow=window.dialogArguments;
//						pWindow.doThingsAfterAdd(lawCaseId);	
				        window.close();
					});
				
			}
		}
		
	}
	function ifshow(aaaa){
		
		if(aaaa.checked){
			 $("#interrogateSerialId").combobox("enable"); 
			
		}else{
			 $("#interrogateSerialId").combobox("disable"); 
		}
	}
	
</script>
<style type="text/css">
	#tj_btn{text-align:center;padding-top:30px}
 	#tj_btn input.bc_out{width:88px;height:32px;border:none;background:url(<%=request.getContextPath()%>/image/bt_c.png);}
	#tj_btn input.bc_on{width:88px;height:32px;border:none;background: url(<%=request.getContextPath()%>/image/bt_b.png); color:#fff;}
	#pic{ vertical-align:top; padding-left:20px;}
</style>
</head>
<body >
<div class="mybody">
	<div>
		<div style="padding-left: 5px; margin-top:20px; width:100%;">
			<span id="spanButtonPlaceholder1_swfupload"></span> 
			<input id="btnCancel1_swfupload" type="hidden" value="取消上传" onclick="cancelQueue(upload_swfupload);"
				disabled="disabled"
				style="margin-left:8px; height:26px; font-size: 12px; width:70px;" />
			<span class="green">&nbsp;&nbsp;提示：	"支持多文件同时上传，单个文件上传限制2048MB(2GB)。"
			</span>
		</div>
		<div style="padding-top: 5px;">
		      &nbsp;  资料类型 
		    <input id="caseDataType" class="easyui-combobox" name="dept" data-options="valueField:'id',textField:'text',url:'./type_data.json',onChange:function(data1,data2){upload_swfupload.SWFObj.addPostParam('caseDataType',data1);}" />  
		      
		        &nbsp;  &nbsp;        
						<input class="easyui-checkbox" type="checkbox" onchange="ifshow(this)" />
						<span style="color: blue">是否关联嫌疑人</span>
    		<select id="interrogateSerialId" class="easyui-combogrid" name="interrogateSerialId" style="width:170px;"   ></select>
                        
		</div>
		<div class="fieldset flash" style="width:100%;padding:20px;">
			<div id="fsUploadProgress1_swfupload">
				<span class="legend">附件上传</span>
				
				<div style="clear:both;"></div>
			</div>
			
			
			<div style="clear:both;"></div>
		</div>
		<div align="center">
			<table>
				<tr>
					<td id="tj_btn">
					<!-- onmousemove="this.className='bc_on'" onmouseout="this.className='bc_out'" -->
						<input name="noButton" onclick="javascript:save();" type="button" name="tijiao1" value="确定上传" class="bc_out" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="noButton" onclick="javascript:delAll();" type="button" name="tijiao2" value="取消上传" class="bc_out" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>