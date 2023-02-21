package com.zhixin.zhfz.bacs.services.serial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhixin.zhfz.bacs.dao.serial.IOutReportMapper;
import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;
import com.zhixin.zhfz.bacs.entity.OutReportEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutReportServiceImpl implements IOutReportService {
	private static Logger logger = LoggerFactory.getLogger(OutReportServiceImpl.class);

	@Autowired
	private IOperLogService operLogService;

	@Autowired
	private IOutReportMapper outReportMapper;

	@Override
	public LawDocProcessEntity getDocData(Long serialId, HttpServletRequest request) {
		LawDocProcessEntity lawDocProcessEntity = new LawDocProcessEntity();
		//
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		lawDocProcessEntity.setXmlFileName("办案区使用情况登记表.xml");
		lawDocProcessEntity.setDownFileName("办案区使用情况登记表.doc");
		lawDocProcessEntity.setFileType(1);
		// --------------------------------------------
		String filename1 = Utils.getUniqueId();
		String filename2 = ".doc";
		String fileName = filename1 + filename2;
		lawDocProcessEntity.setTempFileName(fileName);
		// ---------------------------------
		getDocMapData(lawDocProcessEntity, serialId, sessionInfo);
		return lawDocProcessEntity;
	}

	private void getDocMapData(LawDocProcessEntity result, Long serialId, SessionInfo sessionInfo) {
		SerialEntity entity = new SerialEntity();
		List<OutReportEntity> list = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map docMap = new HashMap();
		try {
			Map<String,Object> paramMap=new HashMap<>();
			paramMap.put("serialId", serialId);
			OutReportEntity outReportEntity=new OutReportEntity();
			//2.查询基本信息------------开始
			list = outReportMapper.getBaseInfo(paramMap);
			if(list!=null&&list.size()>0){
				outReportEntity=list.get(0);
				docMap.put("serialNo", outReportEntity.getSerialNo());
				docMap.put("name", outReportEntity.getName());
				docMap.put("sex", outReportEntity.getSex());
				
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				if(outReportEntity.getBirth()!=null){
					docMap.put("birth", dateFormat.format(outReportEntity.getBirth()));
				}
				
				docMap.put("mobile", outReportEntity.getMobile());
				docMap.put("certificateType", outReportEntity.getCertificateType());
				docMap.put("certificateNo", outReportEntity.getCertificateNo());
				docMap.put("caseReason", outReportEntity.getCaseReason());
				docMap.put("entranceProcedure", outReportEntity.getEntranceProcedure());
				docMap.put("personType", "嫌疑人");
				docMap.put("inTime", simpleDateFormat.format(outReportEntity.getInTime()));
				
				if(outReportEntity.getMedicalHistory()==null||outReportEntity.getMedicalHistory()==""){
					docMap.put("medicalHistory", "无");
				}else{
					docMap.put("medicalHistory", outReportEntity.getMedicalHistory());
				}
				
				if(outReportEntity.getInjuryDescription()==null||outReportEntity.getInjuryDescription()==""){
					docMap.put("injuryDescription", "无");
				}else{
					docMap.put("injuryDescription", outReportEntity.getInjuryDescription());
				}
				
				if(outReportEntity.getHasInjury()==1){
					docMap.put("hasInjury", "☑");
				}else{
					docMap.put("hasInjury", "□");
				}
				
				if(outReportEntity.getHasWine()==1){
					docMap.put("hasWine", "☑");
				}else{
					docMap.put("hasWine", "□");
				}
				
				if(outReportEntity.getHasPhoto()==1){
					docMap.put("hasPhoto", "☑");
				}else{
					docMap.put("hasPhoto", "□");
				}
				
				if(outReportEntity.getTempOutTime()!=null){
					docMap.put("tempOutTime", simpleDateFormat.format(outReportEntity.getTempOutTime()));
				}
				
				docMap.put("tempOutReason", outReportEntity.getTempOutReason());
				if(outReportEntity.getBackTime()!=null){
					docMap.put("backTime", simpleDateFormat.format(outReportEntity.getBackTime()));
				}
				
				if(outReportEntity.getOutTime()!=null){
					docMap.put("outTime", simpleDateFormat.format(outReportEntity.getOutTime()));
				}
				
				docMap.put("confirmResult", outReportEntity.getConfirmResult());
				
			}
			//查询基本信息------------结束	
			
			//2.查询暂存的随身物品--------------开始
			list=outReportMapper.getBelongs(paramMap);
			
			
			List<Map> listBelongs = new ArrayList<Map>();
			int belongsBlankRow=6;
			if(list!=null&&list.size()>0){
				if(list.size()<5){
					belongsBlankRow=belongsBlankRow-list.size();
				}
				logger.info("query belongs="+list.size());
				for (int i = 0; i < list.size(); i++) {
					outReportEntity = list.get(i);
					Map mapBelongs = new HashMap();
					mapBelongs.put("no", i + 1);
					mapBelongs.put("involvedName", outReportEntity.getInvolvedName());
					mapBelongs.put("description", outReportEntity.getDescription());
					mapBelongs.put("detailCount", outReportEntity.getDetailCount());
					mapBelongs.put("unit", outReportEntity.getUnit());
					mapBelongs.put("saveMethod", outReportEntity.getSaveMethod());
					mapBelongs.put("lockerNo", outReportEntity.getLockerNo());
					listBelongs.add(mapBelongs);
				}
			}
			for(int j=0;j<belongsBlankRow;j++){
				Map mapBelongs = new HashMap();
				mapBelongs.put("no", "");
				mapBelongs.put("involvedName", "");
				mapBelongs.put("description", "");
				mapBelongs.put("detailCount", "");
				mapBelongs.put("unit", "");
				mapBelongs.put("saveMethod", "");
				mapBelongs.put("lockerNo", "");
				listBelongs.add(mapBelongs);
			}
			docMap.put("listBelongs", listBelongs);
			
			//查询暂存的随身物品--------------结束
			//3.查询询讯问、候问活动轨迹--------------开始
			List<Map> listZhixinRecord = new ArrayList<Map>();
			List<OutReportEntity> list1=outReportMapper.getWaitingRecord1(paramMap);
			if(list1!=null&&list1.size()>0){
				logger.info("query waiting record="+list1.size());
				for (int i = 0; i < list1.size(); i++) {
					outReportEntity = list1.get(i);
					if(outReportEntity.getResult()!=null&&outReportEntity.getResult()!=""){
						Map mapWaitRecord = new HashMap();
						if(outReportEntity.getStartTime()!=null){
							mapWaitRecord.put("startTime",simpleDateFormat.format(outReportEntity.getStartTime()));
						}
						if(outReportEntity.getEndTime()!=null){
							mapWaitRecord.put("endTime", simpleDateFormat.format(outReportEntity.getEndTime()));
						}
						String roomname = outReportEntity.getResult();
						boolean ii= roomname.contains("&*&");
						if (ii){
							String str[] = roomname.split("&*&");
							mapWaitRecord.put("roomName",str[0] );
							if(outReportEntity.getDescription()==null) {
								outReportEntity.setDescription("");
							}
							//活动
							mapWaitRecord.put("cameraNo",str[2]+outReportEntity.getDescription());
						}else{
							mapWaitRecord.put("roomName",outReportEntity.getRoomName() );
							if(outReportEntity.getDescription()==null) {
								outReportEntity.setDescription("");
							}
							//活动
							mapWaitRecord.put("cameraNo",outReportEntity.getResult()+outReportEntity.getDescription());
						}
						listZhixinRecord.add(mapWaitRecord);
					}
				}
			}
			int recordBlankRows=11;
			if(listZhixinRecord!=null&&listZhixinRecord.size()>0){
				if(listZhixinRecord.size()<11){
					recordBlankRows=recordBlankRows-listZhixinRecord.size();
				}
			}
			for(int i = 0; i < recordBlankRows; i++){
				logger.info("default blank record="+recordBlankRows);
				Map mapBlankRecord = new HashMap();
				mapBlankRecord.put("startTime","");
				mapBlankRecord.put("endTime", "");
				mapBlankRecord.put("roomName", "");
				mapBlankRecord.put("cameraNo", "");
				listZhixinRecord.add(mapBlankRecord);
			}
			docMap.put("listRecord", listZhixinRecord);
			//3.查询询讯问、候问活动轨迹--------------结束
			//4.查询暂存的随身物品返还情况--------------开始
			list=outReportMapper.getBelongsDetail(paramMap);
			if(list!=null&&list.size()>0){
				logger.info("query belongs isGet="+list.size());
				int isGet=0;//默认是全部返还
				String msg="";
				for(int i=0;i<list.size();i++){
					outReportEntity=list.get(i);
					if(outReportEntity.getIsGet()==0){
						isGet++;
						msg+=outReportEntity.getInvolvedName()+" "+outReportEntity.getDetailCount()+""+
						outReportEntity.getUnit()+" "+outReportEntity.getDescription()==null?"":outReportEntity.getDescription()+";";
					}
				}
				logger.info("belongs no get involvedName="+msg);
				if(isGet==0){
					logger.info("belongs isGet All");
					docMap.put("ret1", "☑");
					docMap.put("ret2", "□");
					docMap.put("ret3", "□");
				}else{
					if(isGet<list.size()){
						logger.info("belongs isGet part");
						docMap.put("ret1", "□");
						docMap.put("ret2", "☑");
						docMap.put("ret3", "□");
						docMap.put("retResult", msg);
					}else{
						if(isGet==list.size()){
							logger.info("belongs no isGet");
							docMap.put("ret1", "□");
							docMap.put("ret2", "□");
							docMap.put("ret3", "☑");
							docMap.put("retResult", msg);
						}
					}
				}
			}else{
				logger.info("belongs is not exist");
				docMap.put("ret1", "☑");
				docMap.put("ret2", "□");
				docMap.put("ret3", "□");
			}
			//4.查询暂存的随身物品返还情况---------------结束
		} catch (Exception e) {
			logger.info("query Exception="+e.getMessage());
		}
		result.setData(docMap);
	}
}
