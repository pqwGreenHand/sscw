package com.zhixin.zhfz.bacs.services.archives;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.entity.ArchivesTreeEntity;
import com.zhixin.zhfz.bacs.entity.BurnLogEntity;
import com.zhixin.zhfz.bacs.entity.DvdLogEntity;

public interface IElecArchivesService {

	public List<BurnLogEntity> list(Map<String, Object> map);

	public int count(Map<String, Object> map);
	
	public void insertDvdLog(DvdLogEntity dvdLogEntity);
	
	//查询全部dvd记录
	public List<DvdLogEntity> listByAll(Map<String,Object> map);
	
	public int countDvdLog(Map<String,Object>map);
	
	//案件树形展示 	  	  	  	
  	public List<ArchivesTreeEntity> listCaseTree(Map<String, Object> map);
  	
  	public int listCaseTreeCount(Map<String,Object>map);

	public List findFilePath(Map<String,Object>map);

}
