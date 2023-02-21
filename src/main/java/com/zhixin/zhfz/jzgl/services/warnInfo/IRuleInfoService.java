package com.zhixin.zhfz.jzgl.services.warnInfo;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.RuleInfoEntity;

/**
 * IRuleInfoService table: rule_infoIRuleConfigMapper
 * 
 * @author cuichengwei
 */
public interface IRuleInfoService  {

    public List<RuleInfoEntity> getRuleAllRoot() throws Exception;
	
    public List<RuleInfoEntity> getRuleChildrenByRuleId(Map<String, Object> map) throws Exception;
	
}