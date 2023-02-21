function xqHtml(currentRow){
	var status = "";
    if (currentRow.status == 0) {
    	status = "已入区";
    } else if (currentRow.status == 1) {
        status = "已安检";
    } else if (currentRow.status == 2) {
        status = "物品已暂存";
    } else if (currentRow.status == 3) {
        status = "候问开始";
    } else if (currentRow.status == 4) {
        status = "候问结束";
    } else if (currentRow.status == 5) {
        status = "信息已采集";
    } else if (currentRow.status == 6) {
        status = "审讯开始";
    } else if (currentRow.status == 7) {
        status = "审讯结束";
    } else if (currentRow.status == 8) {
       status = "物品已领取";
    } else if (currentRow.status == 9) {
       status = "临时出区返回";
    } else if (currentRow.status == 10) {
       status = "临时出区";
    } else if (currentRow.status == 11) {
       status = "已出区";
    }

    // 0已入区 1已安检 2物品已暂存 3候问开始 4候问结束 5信息已采集 6审讯开始 7审讯结束
    // 8物品已领取 9临时出区返回 10临时出区 11已出区

    var sex = "";
    if (currentRow.sex == 0) {
        sex = "未知的性别";
    }
    if (currentRow.sex == 1) {
        sex = "男";
    }
    if (currentRow.sex == 2) {
        sex = "女";
    }
    if (currentRow.sex == 9) {
        sex = "未说明的性别";
    }

    var type = "";
    if (currentRow.type == 0) {
        type = "嫌疑人";
    } else if (currentRow.type == 1) {
    	type = "事主";
    } else if (currentRow.type == 2) {
    	type = "证人";
    } else if (currentRow.type == 3) {
    	type = "见证人";
    } else if (currentRow.type == 4) {
    	type = "被侵害人";
    } else if (currentRow.type == 5) {
    	type = "报案人";
    } else if (currentRow.type == 6) {
    	type = "其它";
    }

    var outType = "";
    if (currentRow.outType == 0) {
        outType = "正常出区";
    } else if (currentRow.outType == 1) {
       outType = "临时出区";
    } else if (currentRow.outType == 2) {
       outType = "特殊出区";
    } else if (currentRow.outType == 3) {
       outType = "其他出区";
    }

    if (currentRow.interrogateCaseName == ""
        || currentRow.interrogateCaseName == null) {
        currentRow.interrogateCaseName = "无";
    }
    if (currentRow.cuffNo == ""
        || currentRow.cuffNo == null) {
        currentRow.cuffNo = "无";
    }
    if (currentRow.inPhotoId == ""
        || currentRow.inPhotoId == null) {
        currentRow.inPhotoId = "无";
    }
    if (currentRow.inUserRealName1 == ""
        || currentRow.inUserRealName1 == null) {
        currentRow.inUserRealName1 = "无";
    }
    if (currentRow.inUserRealName2 == ""
        || currentRow.inUserRealName2 == null) {
        currentRow.inUserRealName2 = "无";
    }
    if (currentRow.outReason == ""
        || currentRow.outReason == null) {
        currentRow.outReason = "无";
    }
    if (currentRow.outType == ""
        || currentRow.outType == null) {
        currentRow.outType = "无";
    }
    if (currentRow.outRegisterUserRealName == ""
        || currentRow.outRegisterUserRealName == null) {
        currentRow.outRegisterUserRealName = "无";
    }
    if (currentRow.outUserRealName1 == ""
        || currentRow.outUserRealName1 == null) {
        currentRow.outUserRealName1 = "无";
    }
    if (currentRow.outUserRealName2 == ""
        || currentRow.outUserRealName2 == null) {
        currentRow.outUserRealName2 = "无";
    }
    if (currentRow.outPhotoId == ""
        || currentRow.outPhotoId == null) {
        currentRow.outPhotoId = "无";
    }
    if (currentRow.orderNo == ""
        || currentRow.orderNo == null) {
        currentRow.orderNo = "无";
    }
    if (currentRow.policeCuffNo1 == ""
        || currentRow.policeCuffNo1 == null) {
        currentRow.policeCuffNo1 = "无";
    }
    if (currentRow.policeCuffNo2 == ""
        || currentRow.policeCuffNo2 == null) {
        currentRow.policeCuffNo2 = "无";
    }
    var caseType = "";
    if(currentRow.caseType==1){
    	caseType="行政";
    }
    if(currentRow.caseType==2){
    	caseType="刑事";
    }
    var sfsyjd = "";
    if(currentRow.sfsyjd==0){
    	sfsyjd = "未送押解队";
    }
    if(currentRow.sfsyjd==1){
    	sfsyjd = "已送押解队";
    }
    var sftjcl = "";
    if(currentRow.sftjcl==0){
    	sftjcl = "未提交";
    }
    if(currentRow.sftjcl==1){
    	sftjcl = "已提交";
    }
    
    var inPhoto = fileUtils.url("ba",fileTypeEntity.FILETYPRRQ,currentRow.uuid,currentRow.areaId,currentRow.inPhotoId);
    var xqHtmlBody = ' <table  id="tt" class="colinfo_table">'
        + ' <tr>'
        + ' 	<td class="detailLable">姓名</td>'
        + ' <td>'
        + currentRow.name
        + '</td>'
        + ' 	<td class="detailLable">性别</td>'
        + ' 	<td>'
        +  sex
        + '</td>'
        + ' 	<td class="detailLable">入区编号</td>'
        + ' 	<td colspan="2">'
        + currentRow.serialNo
        + '</td>'
        + ' 	<td rowspan="3"><img alt="" heigth="135" width="100" onmouseover="onBigImage(event,this)" onmouseout="mvBigImage()" src="'
        + inPhoto
        + '"></td>'
        + ' </tr>'
        + ' 	<tr>'
        + ' 	<td class="detailLable">uuid</td>'
        + ' 	<td>'
        + currentRow.uuid
        + '</td>		'
        + ' 	<td class="detailLable">嫌疑人手环编号</td>'
        + ' 	<td>'
        + currentRow.cuffNo
        + '</td>'
        + ' 	<td class="detailLable">入区照片</td>'
        + ' 	<td colspan="2">'
        + currentRow.inPhotoId
        + '</td>	'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">状态</td>'
        + ' 	<td>'
        +  status
        + '</td>'
        + ' 	<td class="detailLable">状态改变时间</td>'
        + ' 	<td>'
        + valueToDate(currentRow.statusChangeTime)
        + '</td>	'
        + ' 	<td class="detailLable">案件名称</td>'
        + ' 	<td colspan="2">'
        + currentRow.interrogateCaseName
        + '</td>	'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">办案场所</td>'
        + ' 	<td>'
        + currentRow.interrogateAreaName
        + '</td>'
        + ' 	<td class="detailLable">预约编号</td>'
        + ' 	<td>'
        + currentRow.orderNo
        + '</td>'
        + '     <td class="detailLable">入区手续</td>'
        + '     <td>'
        + currentRow.entranceProcedure
        + '</td>'
        + '     <td class="detailLable">手续开具时间</td>'
        + '     <td>'
        + valueToDate(currentRow.writtenTime)
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">入区协办民警</td>'
        + ' 	<td>'
        + currentRow.inUserRealName2
        + '</td>'
        + ' 	<td class="detailLable">入区记录民警</td>'
        + ' 	<td>'
        + currentRow.inRegisterUserRealName
        + '</td>'
        + ' 	<td class="detailLable">入区主办民警</td>'
        + ' 	<td >'
        + currentRow.inUserRealName1
        + '</td>'
        + ' 	<td class="detailLable">入区时间</td>'
        + ' 	<td>'
        + valueToDate(currentRow.inTime)
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">出区照片</td>'
        + ' 	<td>'
        + currentRow.outPhotoId
        + '</td>'
        + ' 	<td class="detailLable">出区原因</td>'
        + ' 	<td>'
        + currentRow.outReason
        + '</td>'
        + ' 	<td class="detailLable">出区类型</td>'
        + ' 	<td>'
        +  outType
        + '</td>'
        + ' 	<td class="detailLable">出区记录民警</td>'
        + ' 	<td>'
        + currentRow.outRegisterUserRealName
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">出区时间</td>'
        + ' 	<td>'
        + valueToDate(currentRow.outTime)
        + '</td>'
        + ' 	<td class="detailLable">出区协办民警</td>'
        + ' 	<td>'
        + currentRow.outUserRealName2
        + '</td>'
        + ' 	<td class="detailLable">裁决时间</td>'
        + ' 	<td>'
        + valueToDate(currentRow.confirmTime)
        + '</td>'
        + ' 	<td class="detailLable">出区主办民警</td>'
        + ' 	<td>'
        + currentRow.outUserRealName1
        + '</td>'
        + ' </tr>'
        + ' <tr>'
        + ' 	<td class="detailLable">主办民警卡号</td>'
        + ' 	<td>'
        + currentRow.policeCuffNo1
        + '</td>'
        + ' 	<td class="detailLable">协办民警卡号</td>'
        + ' 	<td >'
        + currentRow.policeCuffNo2
        + '</td>'
        + '     <td class="detailLable">裁决结果</td>'
        + '     <td>'
        + currentRow.confirmResult
        + '</td>' 
        
        + '</td>'
        + '     <td class="detailLable">案件性质</td>'
        + '     <td>'
        +  caseType
        + '</td>' 
        
        + ' </tr>' 
        
        + ' <tr>'
        + ' 	<td class="detailLable">案由</td>'
        + ' 	<td colspan=3>'
        + currentRow.caseNature
        + '</td>'
        
        + '     <td class="detailLable">是否送押解队</td>'
        + '     <td>'
        +     sfsyjd
        + '</td>' 
        
        + '     <td class="detailLable">是否提交材料</td>'
        + '     <td>'
        +     sftjcl
        + '</td>' 
     
        
        + ' </tr>' 
        
        + ' </table> ';
    return xqHtmlBody;
}