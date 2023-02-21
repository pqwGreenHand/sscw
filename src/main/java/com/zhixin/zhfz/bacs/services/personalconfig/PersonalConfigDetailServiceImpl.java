package com.zhixin.zhfz.bacs.services.personalconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.dao.personalconfig.IPersonalConfigDetailMapper;
import com.zhixin.zhfz.bacs.entity.PersonalConfigDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalConfigDetailServiceImpl implements IPersonalConfigDetailService{
	
	@Autowired
	private IPersonalConfigDetailMapper dao;

	@Override
	public List<PersonalConfigDetailEntity> list(Map<String, Object> params) throws Exception {
		return dao.list(params);
	}

	@Override
	public void insert(PersonalConfigDetailEntity entity) throws Exception {
		dao.insert(entity);
	}

	@Override
	public void update(PersonalConfigDetailEntity entity) throws Exception {
		dao.update(entity);
	}

	@Override
	public void delete(int id) throws Exception {
		dao.delete(id);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return dao.count(params);
	}

	@Override
	public List<PersonalConfigDetailEntity> listDetailsByNameAndInterrogateAreaId(String name,int id) throws Exception {
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("interrogateAreaId", id);
		return dao.listDetailsByNameAndInterrogateAreaId(map);
	}

	@Override
	public List<PersonalConfigDetailEntity> checkurl(String areaid) {
		
		return dao.checkurl(areaid);
	}

	@Override
	public String queryBoxNumberById(int lockid) {
		return dao.queryBoxNumberById(lockid);
	}
	
	@Override
	public List<PersonalConfigDetailEntity> listDetailByCodeAndParam(String codeKey,String paramKey,Long areaId) throws Exception{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("codeKey", codeKey);
		params.put("paramKey", paramKey);
		params.put("areaId", areaId);
		return dao.listDetailByCodeAndParam(params);
	}
	
	@Override
	public List<PersonalConfigDetailEntity> listInParamDetailByOutParamId(Long id) throws Exception{
		return dao.listInParamDetailByOutParamId(id);
	}

	/**
	 * 查询个性化配置
	 * @param areaId
	 * @param name
	 * @return
	 */
	@Override
	public  Map<String, String> listConfigDetailByAreaAndName(int areaId, String name) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("areaId", areaId);
		param.put("name", name);
		List<PersonalConfigDetailEntity> pcdes = dao.listConfigDetailByAreaAndName(param);
		if (pcdes != null) {
			for (PersonalConfigDetailEntity pcds : pcdes) {
				result.put(pcds.getParamKey(), pcds.getParamValue());
			}
		}
		return result;
	}
}
