<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page  import="com.zhixin.zhfz.common.utils.PropertyUtil"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<!-- <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>  -->
		<script type="text/javascript" src="json2.js"></script> 
		<link rel="stylesheet" type="text/css"	href="../../js/jquery-easyui-1.4.3/themes/gray/easyui.css">
		<link rel="stylesheet" type="text/css"	href="../../js/jquery-easyui-1.4.3/themes/icon.css">
		<link rel="stylesheet" type="text/css"	href="../../js/jquery-easyui-1.4.3/demo/demo.css">
		<script type="text/javascript"	src="../../js/jquery-easyui-1.4.3/jquery.min.js"></script>
		<script type="text/javascript"	src="../../js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
	</head>
	<body onload="loaddate()">
	<script language="javascript">	
  
	var SvrID="dev1";
	var MsID="dev1";
	var MsIP="127.0.0.1";
	var MsPort="8000";
	
	var jobId="test";
	var init=-1;	
	var caseId=<%=request.getParameter("caseId") %>;
	var personId=<%=request.getParameter("personId") %>;
	//alert("caseId"+caseId);
	//alert("personId"+personId);
	//TODO
	var disk="disk1";
	//alert("disk"+disk);
	
  	function loaddate(){
  		$('#recorddate').combobox({   
  			//editable:false,  
  			url:'getRecordPersionCombobox.do?personId='+personId+'&caseId='+caseId, 
  			onChange:function(id ,value){
  				gateinfo(id);
  		    },
  		 	onLoadSuccess: function(data){
  			  	//alert("data[0]"+data[0]);
  		    },
  			width:300,
  			valueField:'id',   
  			textField:'value' 
  		});
  		/*
  		$('#dataInterrogateCaseId').combobox({   
  			url:'../combobox/caseCombobox.do',   
  			valueField:'id',   
  			textField:'value' ,
  		    onLoadSuccess: function(data){    
  	            $('#dataInterrogateCaseId').combobox('setValue', data[0].id);    
  	        } 
  		});*/
  	}
	
	function gateinfo(record){
		//alert("gateinfo:"+record);
		jQuery.ajax({
  			type : 'POST',
  			url : 'getPersionInfo.do',
  			data : 'caseId='+caseId+'&personId='+personId,
  			dataType : 'json',
  			success : function(data){
  				$('#starttime').val(data.start_time);
  				 //document.getElementById("starttime").value=data.start_time;
  				 document.getElementById("stoptime").value=data.start_time;
  				 document.getElementById("suspecter").value=data.suspecter;
  				 document.getElementById("case_name").value=data.case_name;
  				 document.getElementById("inquirer").value=data.askname;
  				 document.getElementById("burn_person").value=data.recordname;
  				 document.getElementById('burnTerm').value=data.start_time;
  			},
  			error : function(data){
  				//exception in java
  				$.messager.alert('Error', '未知错误!');
  			}
  		}); 
	}
	
//选择文件
function browseFolder() {  
    try {  
        var Message = "请选择文件夹";   
        var Shell = new ActiveXObject("Shell.Application");  
        var Folder = Shell.BrowseForFolder(0,Message,0);   
        if (Folder != null) {  
            Folder = Folder.items();  
            Folder = Folder.item();   
            Folder = Folder.Path;  
            if (Folder.charAt(Folder.length - 1) != "\\") {  
                Folder = Folder + "\\";  
            }  
            document.getElementById("dataPath").value = Folder;  
            return Folder;  
        }  
    } catch (e) {  
        alert(e.message);  
    }  
}  

	

// 	 window.onbeforeunload = function(){
	 
// 	 	var vOcxburnTerm = document.getElementById('burnTerm');
// 	 	vOcxburnTerm.UninitAx();
// 	 	init=-1;
	 	
// 	 }

	function ApplyBurnStatus() {
		/*
		vJsonCmd = {
			"config_type":1,//配置文件版本 1为致昕对接版本
			"job_id":this.jobId
			}
		*/
		try
		{
			var vOcxburnTerm = document.getElementById('burnTerm');
			if(vOcxburnTerm != null)
			{
				var GisVedioInfo = {
				"config_type":1,
				"job_id":jobId
				};
				var GisVedioInfoObject = eval(GisVedioInfo);
				var vJsonCmd = JSON.stringify(GisVedioInfoObject); 
				vRet = vOcxburnTerm.ApplyStatus(vJsonCmd,SvrID,MsID,MsIP,MsPort);
				alert(vRet);
			}
		} catch(e) {
		   alert("错误:"+e); 
		}
		return 0;
	}

	function StopBurn() {
		
		try
		{
		    var vOcxburnTerm = document.getElementById('burnTerm');
			if(vOcxburnTerm != null)
			{
				var GisVedioInfo = {
				"config_type":1,
				"job_id":jobId
				};
				var GisVedioInfoObject = eval(GisVedioInfo);
				var vJsonCmd = JSON.stringify(GisVedioInfoObject); 
				
				vRet = vOcxburnTerm.StopBurn(vJsonCmd,SvrID,MsID,MsIP,MsPort);
				alert(vRet);
			}
		}
		catch(e)
		{
			alert("错误:"+e); 
		}
	}
	
	String.prototype.replaceAll  = function(s1,s2){return this.replace(new RegExp(s1,"gm"),s2);}
  
	function recordc(){
		$.messager.progress({
		   	 title:'请等待',
		   	 msg:'刻录中请稍后...'
		});
		var vRet;
		var vJsonCmd;
		
		var file = document.getElementById("dataPath").value; 
		file=file.substr(0, file.length-1);
		var b=/\\/gi;
		file=file.replace(b,'\\\\');;
		var starttime = document.getElementById("starttime").value;
		var stoptime = document.getElementById("stoptime").value;
		var suspecter = document.getElementById("suspecter").value;
		var case_name = document.getElementById("case_name").value;
		
		var inquirer = document.getElementById("inquirer").value;
		var burn_person = document.getElementById("burn_person").value
		   
		
		var vOcxburnTerm = document.getElementById('burnTerm');
				
		try{	
			//控件初始化
			if(init==-1){
				vRet=vOcxburnTerm.InitAx(1);
				if(vRet!=0){
					alert("刻录机初始化错误:"+e);
					$.messager.progress('close');
					return;	
				}
				init=1;
			}
			var GisVedioInfo = {
			"config_type":1,
			"job_id":"test",
			"burn_mode":0,
			"burn_type":18,
			"close_disk":0,
			"password":"123456",
			"cdtitle":case_name,
			"disk_info": {
				"enable":1,
				"burn_person":burn_person,
				"case_name":case_name,
				"inquirer":inquirer,
				"starttime":starttime,
				"stoptime":stoptime,
				"suspecter":suspecter
			},
			"lable_path":disk
			//"lable_path":"disk1"
			};
			
			var GisVedioInfoObject = eval(GisVedioInfo);
			var vJsonCmd = JSON.stringify(GisVedioInfoObject); 
			vRet = vOcxburnTerm.ApplyBurn(vJsonCmd,SvrID,MsID,MsIP,MsPort);
			if(vRet==1){
				alert("刻录申请错误:"+e);
				$.messager.progress('close');
				return;	
			}
			/*
			var i=10;
			while(i>0){
				if(job_id=="-1"){
					console.log("wait job_id");
					sleep(2);
				}else  break;
				i=i-1;
			}*/
			var GisVedioInfo = {
					"config_type":1,
					"job_id":jobId,
					"data_path":file
					};
			GisVedioInfoObject = eval(GisVedioInfo);
			vJsonCmd = JSON.stringify(GisVedioInfoObject); 
			//vJsonCmd = "{config_type:1,job_id:"+jobId+",data_path:"+file+"}";
			vRet = vOcxburnTerm.StartBurn(vJsonCmd,SvrID,MsID,MsIP,MsPort);
			if(vRet==1){
				
				$.messager.progress('close');
				alert("启动刻录任务错误:"+e);
				return;					
			}		
		}catch(e){
			$.messager.progress('close');
			alert("错误:"+e); 
		}
		
	}	   
	
  </script>
  
<script language="javascript" for="burnTerm" event="OnReplyEvent( _sType,_sReply )">
{ 
	//console.log("type=%s,Reply=%s",_sType,_sReply);
	if (_sType == "inquest_burn_reply") {
		eval('var contact = ' + _sReply );
		jobId = contact.job_id;
		//console.log("刻录任务：%s",jobId);
		
	}else if(_sType == "start_burn_reply"){
		jQuery("#status").text("刻录开始");
		///console.log("刻录开始");
	}else if(_sType == "burn_completed"){
		jQuery("#status").text("刻录完成"); 
		//console.log("刻录完成");
	}else if(_sType == "logon_fail"){
		jQuery("#status").text("与刻录服务通讯失败");
		//console.log("与刻录服务通讯失败");
	}
}

</script>

	
	<div class="easyui-navpanel">
			<div class="m-toolbar" style=" height: 50px">
				<div class="m-title">刻录信息</div>
				请选择案例：<select id="recorddate" class="easyui-combobox"></select>
				<object id="burnTerm" classid="clsid:311A6397-B8C7-4CFA-92AB-333722C49682" style="width:0px; height:0px;"></object>	
			</div>
		<ul class="m-list" style="width: 90%">
			<li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件路径:</span><input id="dataPath" class=""  style="width:55%" value="">
			<input type="button" style="width: 60px"  onclick="browseFolder()" value="请选择">
			</li>
			<li><span>审讯开始时间:</span><input id="starttime" disabled="disabled"  style="width:75%" value=""></li>
			<li><span>审讯结束时间:</span><input id="stoptime"   disabled="disabled" style="width:75%" value=""></li>
			<li><span>被审讯人姓名:</span><input id="suspecter"  disabled="disabled" style="width:75%" value=""></li>
			<li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;案件名称:</span><input id="case_name" disabled="disabled" style="width:75%" value=""></li>
			<li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审讯民警:</span><input id="inquirer"  disabled="disabled" style="width:75%" value=""></li>
			<li><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记录民警:</span><input id="burn_person"  disabled="disabled" style="width:75%" value=""></li>
			
		</ul>
		<br>
		<table> 
	<tr>
		<td>	
			<input type="button" value="刻录" onclick="javascript:recordc();" />
<!-- 			<input type="button" value="刻录状态" onclick="ApplyBurnStatus();" /> -->
			<input type="button" value="结束刻录" onclick="StopBurn();" />
		</td>
	</tr>
	</table>     
		
		
   </div>
  </body>
</html>
