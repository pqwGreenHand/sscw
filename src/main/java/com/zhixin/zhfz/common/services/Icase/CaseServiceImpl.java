package com.zhixin.zhfz.common.services.Icase;

import com.zhixin.zhfz.common.dao.Icase.ICaseMapper;
import com.zhixin.zhfz.common.entity.CaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaseServiceImpl implements ICaseService {
    @Autowired
    private ICaseMapper caseMapper;

    @Override
    public List<CaseEntity> list(Map<String, Object> map) throws Exception {
        return caseMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return caseMapper.count(map);
    }


    @Override
    public int insertCase(CaseEntity caseEntity) throws Exception{
    	CaseEntity entity = caseMapper.isCaseInfo(caseEntity);
    	if(entity!=null) {
    		caseEntity.setId(entity.getId());
    	}else {
    		Integer caseId = caseMapper.insertSelective(caseEntity);
    		caseEntity.setId(caseEntity.getId());
    	}
        return caseEntity.getId();
    }

    @Override
    public int getCaseId(String ajbh) throws Exception {
        return caseMapper.getCaseId(ajbh);
    }

    @Override
    public void deleteCase(int caseId) throws Exception {
         caseMapper.deleteByPrimaryKey(caseId);
    }

    @Override
    public void updateCase(CaseEntity caseEntity) throws Exception{
       caseMapper.updateByPrimaryKeySelective(caseEntity);
    }
    @Override
    public CaseEntity getCaseById(Integer id) throws Exception {
        return caseMapper.getCaseById(id);
    }

    @Override
    public CaseEntity getCaseInfoById(Integer id) throws Exception {
        return caseMapper.getCaseInfoById(id);
    }

    @Override
    public List<CaseEntity> listCase(Integer areaId, String str) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaId",areaId);
        map.put("dataAuth",str);
        List<CaseEntity> list = caseMapper.listCase(map);
        return list;
    }

}
