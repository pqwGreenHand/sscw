package com.zhixin.zhfz.zhag.services.rule;


import com.zhixin.zhfz.jzgl.services.warnInfo.IRuleConfigService;
import com.zhixin.zhfz.zhag.dao.rule.IAgRuleConfigMapper;
import com.zhixin.zhfz.zhag.entity.RuleConfigEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AgRuleConfigServiceImpl implements IAgRuleConfigService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AgRuleConfigServiceImpl.class);

	@Autowired
	private IAgRuleConfigMapper ruleConfigMapper;

	@Override
	public List<RuleConfigEntity> listAllRuleConfig(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleConfigMapper.listAllRuleConfig(map);
	}

	@Override
	public int countConfig(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleConfigMapper.countConfig(map);
	}

	@Override
	public List<RuleConfigEntity> listAllRuleConfigGj(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleConfigMapper.listAllRuleConfigGj(map);
	}

	@Override
	public int countConfigGj(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleConfigMapper.countConfigGj(map);
	}

	@Override
	public void insertRuleConfig(RuleConfigEntity entity) {
		// TODO Auto-generated method stub
		ruleConfigMapper.insertRuleConfig(entity);
	}

	@Override
	public void deleteRuleConfig(Long id) {
		// TODO Auto-generated method stub
		ruleConfigMapper.deleteRuleConfig(id);
	}

	@Override
	public void updateRuleConfig(RuleConfigEntity entity) {
		// TODO Auto-generated method stub
		ruleConfigMapper.updateRuleConfig(entity);
	}

	@Override
	public List<RuleConfigEntity> listAllRuleConfigInfo() {
		return ruleConfigMapper.listAllRuleConfigInfo();
	}


}
