package com.zhixin.zhfz.jzgl.dao.warnInfo;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.RuleConfigEntity;

public interface IRuleConfigMapper {
	
	public List<RuleConfigEntity> getRuleConfigByRuleId(Map<String, Object> map) throws Exception;
	
	public int countRuleConfig(Map<String, Object> map) throws Exception;
	
	public void insertRuleConfig(RuleConfigEntity entity) throws Exception;

	public List<RuleConfigEntity> getRuleConfigByClassName(String classname);

}
