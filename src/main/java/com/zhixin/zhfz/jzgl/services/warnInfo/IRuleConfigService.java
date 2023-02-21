package com.zhixin.zhfz.jzgl.services.warnInfo;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.RuleConfigEntity;

/**
 * IRuleConfigService
 * 
 * @author cuichengwei
 */
public interface IRuleConfigService  {
	
    public List<RuleConfigEntity> getRuleConfigByRuleId(Map<String, Object> map) throws Exception;
    
    public int countRuleConfig(Map<String, Object> map) throws Exception;
    
    public void insertRuleConfig(RuleConfigEntity entity) throws Exception;
	
}