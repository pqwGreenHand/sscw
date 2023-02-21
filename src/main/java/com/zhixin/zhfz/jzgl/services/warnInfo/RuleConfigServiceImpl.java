package com.zhixin.zhfz.jzgl.services.warnInfo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.warnInfo.IRuleConfigMapper;
import com.zhixin.zhfz.jzgl.entity.RuleConfigEntity;

/**
 * 
 * @author cuichengwei
 */
@Service
public class RuleConfigServiceImpl implements IRuleConfigService {

	private static Logger logger = Logger.getLogger(RuleConfigServiceImpl.class);

	@Autowired
	private IRuleConfigMapper ruleConfigMapper;

	@Override
	public List<RuleConfigEntity> getRuleConfigByRuleId(Map<String, Object> map) throws Exception {
		return ruleConfigMapper.getRuleConfigByRuleId(map);
	}

	@Override
	public int countRuleConfig(Map<String, Object> map) throws Exception {
		return ruleConfigMapper.countRuleConfig(map);
	}

	@Override
	public void insertRuleConfig(RuleConfigEntity entity) throws Exception {
		ruleConfigMapper.insertRuleConfig(entity);
	}

	public List<RuleConfigEntity> getRuleConfigByClassName(String classname) {
		// TODO Auto-generated method stub
		return ruleConfigMapper.getRuleConfigByClassName(classname);
	}

}