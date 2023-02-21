package com.zhixin.zhfz.bacs.dao.archives;

import com.zhixin.zhfz.bacs.entity.ArchivesTreeEntity;
import com.zhixin.zhfz.bacs.entity.BurnLogEntity;

import java.util.List;
import java.util.Map;

public interface IBurnLogMapper {

	public List<BurnLogEntity> list(Map<String, Object> map);

	public int count(Map<String, Object> map);
	
	//案件树形展示 	  	  	
  	public List<ArchivesTreeEntity> listCase(Map<String, Object> map);
  	
  	public int listCaseCount(Map<String,Object>map);
  	
  	public List<ArchivesTreeEntity> listCaseTree(Map<String, Object> map);

	public int listCaseTreeCount(Map<String,Object>map);
}
