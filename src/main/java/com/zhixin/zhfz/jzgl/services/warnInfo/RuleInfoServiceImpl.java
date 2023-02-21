package com.zhixin.zhfz.jzgl.services.warnInfo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.warnInfo.IRuleInfoMapper;
import com.zhixin.zhfz.jzgl.entity.RuleInfoEntity;

/**
 * 
 * @author cuichengwei
 */
 @Service
public class RuleInfoServiceImpl implements IRuleInfoService {

	private static Logger logger = Logger.getLogger(RuleInfoServiceImpl.class);
	
	@Autowired
	private IRuleInfoMapper ruleMapper = null;

	@Override
	public List<RuleInfoEntity> getRuleAllRoot() throws Exception {
		return ruleMapper.getRuleAllRoot();
	}

	@Override
	public List<RuleInfoEntity> getRuleChildrenByRuleId(Map<String, Object> map) throws Exception {
		return ruleMapper.getRuleChildrenByRuleId(map);
	}
		
}