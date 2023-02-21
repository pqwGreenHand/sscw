package com.zhixin.zhfz.bacs.services.archives;

import com.zhixin.zhfz.bacs.dao.archives.IBurnLogMapper;
import com.zhixin.zhfz.bacs.dao.archives.IDvdLogMapper;
import com.zhixin.zhfz.bacs.dao.clue.IInterrogateCaseMapper;
import com.zhixin.zhfz.bacs.entity.ArchivesTreeEntity;
import com.zhixin.zhfz.bacs.entity.BurnLogEntity;
import com.zhixin.zhfz.bacs.entity.DvdLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ElecArchivesServiceImpl implements IElecArchivesService {
	
	@Autowired
	private IBurnLogMapper burnMapper;
	
	@Autowired
	private IDvdLogMapper dvdMapper;
	@Autowired
	private IInterrogateCaseMapper treeMapper;

	@Override
	public List<BurnLogEntity> list(Map<String, Object> map) {
		return burnMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return burnMapper.count(map);
	}

	@Override
	public List<DvdLogEntity> listByAll(Map<String, Object> map) {
		return dvdMapper.listByAll(map);
	}

	@Override
	public int countDvdLog(Map<String, Object> map) {
		return dvdMapper.count(map);
	}

	@Override
	public List<ArchivesTreeEntity> listCaseTree(Map<String, Object> map) {
		// 获取所有案件
		List<ArchivesTreeEntity> caseList = burnMapper.listCase(map);
		List<Integer> listParams = new ArrayList<Integer>();
		for(ArchivesTreeEntity en : caseList){
			if(en.getCaseId()!=null && en.getCaseId() != ""){
				listParams.add(Integer.parseInt(en.getCaseId()));
			}
		}
		if(listParams.size()==0){
			listParams.add(-99);
		}
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("caseIdList",listParams);
		List<ArchivesTreeEntity> returnlist = burnMapper.listCaseTree(paramMap);		
		return shortList(returnlist);
	}
	
	private List<ArchivesTreeEntity> shortList(List<ArchivesTreeEntity> list) {
		if(list==null) return new ArrayList<>();
		List<ArchivesTreeEntity> rstList = new ArrayList<ArchivesTreeEntity>();
		Map<String, ArchivesTreeEntity> map = new LinkedHashMap<String, ArchivesTreeEntity>();
		for (ArchivesTreeEntity entity : list) {
			map.put(entity.getCaseId(), entity);
		}
		for (ArchivesTreeEntity entity : map.values()) {
			ArchivesTreeEntity caseEntity = new ArchivesTreeEntity();
			if (entity.getCaseId() != null) {
				caseEntity.setId(entity.getCaseId());				
				caseEntity.setCaseId(entity.getCaseId());
				caseEntity.setName(entity.getAjmc());
				caseEntity.setAreaId(entity.getAreaId());
				for(ArchivesTreeEntity e : list){
					if (e.getCaseId().equals(entity.getCaseId())) {
						ArchivesTreeEntity persenEntity = new ArchivesTreeEntity();
						
						persenEntity.setCaseId(entity.getCaseId());
						persenEntity.setAjmc(entity.getAjmc());
						
						persenEntity.setId(e.getPersonId());
						persenEntity.setPersonId(e.getPersonId());
						persenEntity.setName(e.getPersonName());												
						persenEntity.setPersonName(e.getPersonName());
						persenEntity.setAreaId(e.getAreaId());
						
						caseEntity.addChildren(persenEntity);
					}
				}
				rstList.add(caseEntity);
			}
		}
		return rstList;
	}

	@Override
	public int listCaseTreeCount(Map<String, Object> map) {
		return burnMapper.listCaseCount(map);
	}

	@Override
	public void insertDvdLog(DvdLogEntity dvdLogEntity) {
		dvdMapper.insert(dvdLogEntity);
	}

	@Override
	public List findFilePath(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return  treeMapper.findFilePath(map);
	}
	
	
}
