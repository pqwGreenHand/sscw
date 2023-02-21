package com.zhixin.zhfz.bacs.services.lawdoc;

import java.util.HashMap;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;

public class FiveDoc {

     //五合一台账数据转换	
	public  static void getFiveDoc(LawDocProcessEntity result,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params =  result.getData();
		//入区 
		if("RQ".equals(type)) {
			map.put("RQareaName1", formatMap(params,"areaName1"));
	        map.put("RQserailNO", formatMap(params,"serailNO"));
	        map.put("RQsendUser",formatMap(params,"sendUser")); 
	        map.put("RQdate", formatMap(params,"date"));
	        map.put("RQpersonName", formatMap(params,"personName"));
	        map.put("RQcertificateType", formatMap(params,"certificateType"));
	        map.put("RQcertificateNO", formatMap(params,"certificateNO"));
	        map.put("RQareaName2", formatMap(params,"areaName2"));
	        map.put("RQareaName3", formatMap(params,"areaName3"));
	        map.put("RQmainusername",formatMap(params,"mainusername"));
	        map.put("RQworkspce",formatMap(params,"workspce"));
	        map.put("RQimage", formatMap(params,"image"));
	        map.put("RQbarcode", formatMap(params,"barcode"));
		}
        //安检
		if("AJ".equals(type)) {
	        map.put("AJareaName", formatMap(params,"areaName"));
	        map.put("AJpersonName", formatMap(params,"personName"));
	        map.put("AJcheckedStartTime", formatMap(params,"checkedStartTime"));
	        map.put("AJcheckedEndTime", formatMap(params,"checkedEndTime"));
	        map.put("AJpersonName", formatMap(params,"personName"));
	        map.put("AJsex", formatMap(params,"sex"));
	        map.put("AJbirth", formatMap(params,"birth"));
	        map.put("AJcaseType", formatMap(params,"caseType"));
	        map.put("AJinvolvedReason", formatMap(params,"involvedReason"));
	        map.put("AJmedicalHistory", formatMap(params,"medicalHistory"));
	        map.put("AJtype", formatMap(params,"type"));
	        map.put("AJphysical", formatMap(params,"physical"));
	        map.put("AJphysicalDescription", formatMap(params,"physicalDescription"));
	        map.put("AJinjury", formatMap(params,"injury"));
	        map.put("AJinjuryDescription", formatMap(params,"injuryDescription"));
	        map.put("AJotherDescription", formatMap(params,"otherDescription"));
		}
        //随身
        if("SS".equals(type)) {
	        map.put("SSpoliceStation", formatMap(params,"policeStation"));
	        map.put("SSbranch", formatMap(params,"branch"));
	        map.put("SSname", formatMap(params,"name"));
	        map.put("SSidentityCard", formatMap(params,"identityCard"));
	        map.put("SScaseReason", formatMap(params,"caseReason"));
	        map.put("SSsource", "随身携带");
	        map.put("SScaseReason", formatMap(params,"caseReason"));
	        map.put("SSlist", formatMap(params,"list"));
        }
        //涉案
        if("SA".equals(type)) {
        	map.put("SAlist", formatMap(params,"list"));
        }
        //出区
        if("CQ".equals(type)) {
	        map.put("CQdepartment", formatMap(params,"department"));
	        map.put("CQapprover", formatMap(params,"approver"));
	        map.put("CQname", formatMap(params,"name"));
	        map.put("CQsex", formatMap(params,"sex"));
	        map.put("CQage", formatMap(params,"age"));
	        map.put("CQisThisCity", "是");
	        map.put("CQcaseType", formatMap(params,"caseType"));
	        map.put("CQinReason", formatMap(params,"inReason"));
	        map.put("CQinTime", formatMap(params,"inTime"));
	        map.put("CQlist", formatMap(params,"list"));
	        map.put("CQoutTime", formatMap(params,"outTime"));
	        map.put("CQoutReason", formatMap(params,"outReason"));
        }
        
        result.setData(map);
	}
	
	private static Object formatMap(Map<String, Object> params,String mapKey) {
		if(params.get(mapKey)!=null) {
			return params.get(mapKey);
		}else {
			return "";
		}
		
	}
	 
 
}
