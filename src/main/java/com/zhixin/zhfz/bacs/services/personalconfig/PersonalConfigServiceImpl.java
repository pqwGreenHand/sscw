package com.zhixin.zhfz.bacs.services.personalconfig;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhixin.zhfz.bacs.dao.personalconfig.IPersonalConfigDetailMapper;
import com.zhixin.zhfz.bacs.dao.personalconfig.IPersonalConfigMapper;
import com.zhixin.zhfz.bacs.entity.PersonalConfigDetailEntity;
import com.zhixin.zhfz.bacs.entity.PersonalConfigEntity;
import com.zhixin.zhfz.bacs.entity.PersonalConfigFusionEntity;
import com.zhixin.zhfz.common.common.ConfigJsonUtil;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonalConfigServiceImpl implements IPersonalConfigService {

	private static Logger logger = LoggerFactory.getLogger(PersonalConfigServiceImpl.class);

	private static JSONArray configJsonArray = null;

	private final static String configJsonFileName = "common/personal_config.txt";

	@Autowired
	private IPersonalConfigMapper dao;
	@Autowired
	private IPersonalConfigDetailMapper detaildao;


	@Override
	public List<PersonalConfigEntity> list(Map<String, Object> params) throws Exception {
		return dao.list(params);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {
		return dao.count(params);
	}

	@Override
	public void insert(PersonalConfigEntity entity) throws Exception {
		dao.insert(entity);
	}
	@Override
	public void update(PersonalConfigEntity entity) throws Exception {
		dao.update(entity);
	}

	@Override
	public void delete(int id) throws Exception {
		dao.delete(id);
		detaildao.deleteByPersonalConfigId(id);
	}

	@Override
	public void initArea(int areaId, HttpServletRequest request) {
		initConfigJsonArray();
		if (configJsonArray != null) {
			for (int i = 0; i < configJsonArray.size(); i++) {
				JSONObject json = configJsonArray.getJSONObject(i);
				if (!json.isEmpty()) {
					PersonalConfigEntity entity = new PersonalConfigEntity();

					try {
						entity.setType(json.getString("type"));
						entity.setAreaId(areaId);
						entity.setDesc(json.getString("name"));
						entity.setIsEnable(json.getInteger("isEnable"));
						entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
						entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
						dao.insert(entity);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	@Override
	public List<PersonalConfigFusionEntity> getDetailByType(Map<String, Object> map) {
		return dao.getDetailByType(map);
	}

	@Override
	public Map<String, String> listConfigDetailByAreaAndName(int areaId, String name) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("areaId", areaId);
		param.put("name", name);
		List<PersonalConfigDetailEntity> pcdes = detaildao.listConfigDetailByAreaAndName(param);
		if (pcdes!=null){
			for (PersonalConfigDetailEntity pcds : pcdes){
				result.put(pcds.getParamKey(), pcds.getParamValue());
			}
		}
		return result;
	}

	private void initConfigJsonArray() {
		if (configJsonArray == null) {
			configJsonArray = ConfigJsonUtil.readJsonArray(configJsonFileName);
		}
	}

}
